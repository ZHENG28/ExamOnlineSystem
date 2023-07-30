package com.zj.examsystem.config;

import com.zj.examsystem.config.security.AuthEntryPointJwt;
import com.zj.examsystem.config.security.AuthTokenFilter;
import com.zj.examsystem.config.security.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@Configuration
// 告知框架 security 的存在
@EnableWebSecurity
// 提供 AOP security 的方法（如@PreAuthorize, @PostAuthorize）
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private CustomUserDetailsService userDetailsService;

    @Resource
    private AuthEntryPointJwt unauthorizedHandler;

    @Bean
    public PasswordEncoder getBCPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(getDaoAuthenticationProvider());
    }

    @Bean
    public AuthenticationProvider getDaoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(getBCPasswordEncoder());
        daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
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
}
