package com.zj.examsystem.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zj.examsystem.config.security.jwt.JwtUtils;
import com.zj.examsystem.config.security.payload.request.LoginRequest;
import com.zj.examsystem.config.security.payload.response.JwtResponse;
import com.zj.examsystem.model.Student;
import com.zj.examsystem.model.TestHistory;
import com.zj.examsystem.repository.StudentRepository;
import com.zj.examsystem.serviceImpl.SystemServiceImpl1;
import com.zj.examsystem.serviceImpl.TestHistoryServiceImpl1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/sys")
public class SystemController1 {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private TestHistoryServiceImpl1 testHistoryService;

    @Autowired
    private SystemServiceImpl1 systemService;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    private StudentRepository studentRepo;

    //    @Autowired
    //    Cache caffeineCache;

    /**
     * 0. 校验验证码
     * 1. 校验用户名和密码
     * 2. 更新SecurityContext中的内容
     * 3. 生成该访问用户的JWT令牌
     * 4. 从Authentication中获取UserDetails
     * 5. 将JWT和UserDetails放入响应包中
     *
     * @param loginRequest 登录请求
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        // 加上身份验证
        loginRequest.setAccount(loginRequest.getIdentity() + " " + loginRequest.getAccount());
        // 创建Token及认证
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getAccount(), loginRequest.getPassword()));
        // 保存认证信息
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 生成自定义的Token
        String jwt = jwtUtils.generateJwtToken(authentication, loginRequest.getIdentity());
        // 获取到已认证过的用户信息
        User userDetails = (User) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        // 为什么不需要放在缓存中
        //        caffeineCache.put(CacheName.USER, userDetails.getUsername(), userDetails);

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getUsername(),
                roles));
    }

    @GetMapping("/findUsername")
    @ResponseBody
    public Object findUsername(Integer role, String account) {
        return systemService.findUsername(role, account);
    }

    @PostMapping("/getFinishTime")
    @ResponseBody
    public Object getStuChartData(String account) {
        Student stu = studentRepo.findByAccount(account).orElseThrow();
        List<TestHistory> historyList = testHistoryService.getStuChartData(stu.getId());
        return systemService.countChartData(historyList);
    }

    // 教师首页图表存在BUG：只能看到自己教授的科目的完成测验的次数
    @PostMapping("/getFinishStuNum")
    @ResponseBody
    public Object getTchChartData() {
        //        List<TestHistory> historyList = testHistoryService.findAll();
        List<TestHistory> historyList = null;
        return systemService.countChartData(historyList);
    }

    @GetMapping("/findInfo")
    @ResponseBody
    public Object findUserInfo(Integer role, String account) {
        return systemService.findUserInfo(role, account);
    }

    @PostMapping("/updateInfo")
    @ResponseBody
    public ResponseEntity<?> updateInfo(@RequestBody String userInfo) throws JsonProcessingException {
        Map<String, String> jsonMap = new ObjectMapper().readValue(userInfo, Map.class);
        String role = jsonMap.get("role");
        String account = jsonMap.get("account");
        String username = jsonMap.get("username");
        String password = jsonMap.get("password");
        return systemService.updateInfo(role, account, username, password);
    }
}