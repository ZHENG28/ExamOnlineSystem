package com.zj.examsystem.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zj.examsystem.config.security.CustomUserDetailsService;
import com.zj.examsystem.config.security.AuthEntryPointJwt;
import com.zj.examsystem.config.security.AuthTokenFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Configuration
// 告知框架 security 的存在
@EnableWebSecurity
// 提供 AOP security 的方法（如@PreAuthorize, @PostAuthorize）
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(getBCPasswordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder getBCPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 配置了 CORS(Cross-Origin Resource Sharing) 和 CSRF(Cross-Site Request Forgery)
        http.cors()
                .and().csrf().disable()
                // 异常处理机制
                .exceptionHandling().authenticationEntryPoint(unauthorizedHandler)
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                // 权限设置
//                .and().authorizeRequests()
//                .antMatchers("/sys/login", "/sys/logout").permitAll()
//                .antMatchers("/captcha/getCaptchaCode", "/captcha/checkCaptchaCode").permitAll()
//                .anyRequest().authenticated(); // 任何请求都需要认证

        // 放在所有的认证过滤器之前
        http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {//验证失败返回JSON格式信息
        return (request, response, exception) -> {
            Map<String, Object> map = new HashMap<>();
            map.put("code", 401);
            map.put("message", "验证码错误");
            response.setContentType("application/json;charset=utf-8");
            PrintWriter out = response.getWriter();
            out.write(new ObjectMapper().writeValueAsString(map));
            out.flush();
            out.close();
        };
    }
}
