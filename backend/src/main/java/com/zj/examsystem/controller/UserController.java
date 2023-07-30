package com.zj.examsystem.controller;

import com.zj.examsystem.config.security.JwtUtils;
import com.zj.examsystem.entity.User;
import com.zj.examsystem.service.UserService;
import com.zj.examsystem.utils.response.BaseResponseEntity;
import com.zj.examsystem.utils.response.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    /**
     * 0. 校验验证码
     * 1. 校验用户名和密码
     * 2. 更新SecurityContext中的内容
     * 3. 生成该访问用户的JWT令牌
     * 4. 从Authentication中获取UserDetails
     * 5. 将JWT和UserDetails放入响应包中
     *
     * @param user 登录请求
     * @return
     */
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody User user) {
        // 创建Token及认证
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getRoleId() + " " + user.getAccount(), user.getPassword()));
        // 保存认证信息
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 生成自定义的Token
        String jwt = jwtUtils.generateJwtToken(authentication, user.getRoleId());

        // 获取到已认证过的用户信息
        User userDetails = (User) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("token", jwt);
        response.put("id", userDetails.getUserId());
        response.put("roles", userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/save")
    @ResponseBody
    public BaseResponseEntity save(User user, String status) {
        Boolean result = user.getUserId() == null ? userService.saveUser(user) : userService.updateUser(user);
        return result ? BaseResponseEntity.ok(status + "成功", null) : BaseResponseEntity.error(ResponseCode.FAIL, status + "失败");
    }

    @PostMapping("/delete")
    @ResponseBody
    public BaseResponseEntity delete(Integer[] userId) {
        int result = userService.deleteUser(userId);
        return result != 0 ? BaseResponseEntity.ok("删除成功", result) :
                BaseResponseEntity.error(ResponseCode.FAIL, "删除失败");
    }

    @GetMapping("/findAll")
    @ResponseBody
    public BaseResponseEntity findAll(Integer pageno, Integer size) {
        return BaseResponseEntity.ok("", userService.findAll(pageno, size));
    }

    @GetMapping("/findInfoById")
    @ResponseBody
    public BaseResponseEntity findInfoById(Integer userId) {
        return BaseResponseEntity.ok("", userService.findById(userId));
    }

    @GetMapping("/loadTeacherByMajorId")
    @ResponseBody
    public BaseResponseEntity loadTeacherByMajorId(Integer majorId) {
        return BaseResponseEntity.ok("", userService.findTeacherByMajorId(majorId));
    }

    @GetMapping("/loadStudentByClazzId")
    @ResponseBody
    public BaseResponseEntity loadStudentByClazzId(Integer clazzId) {
        return BaseResponseEntity.ok("", userService.findStudentByClazzId(clazzId));
    }

    @GetMapping("/loadHomeData")
    @ResponseBody
    public BaseResponseEntity loadHomeData(Integer userId, Integer roleId) {
        Map<String, Object> result = new HashMap<>();
        switch (roleId) {
            case 1: // admin
                result = userService.loadAdminData(userId);
                break;
            case 2: // teacher
                result = userService.loadTeacherData(userId);
                break;
            case 3: // student
                result = userService.loadStudentData(userId);
                break;
        }
        return BaseResponseEntity.ok("", result);
    }
}