package com.zj.examsystem.config.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {
    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

    // 在未受到权限验证的用户访问受保护的资源时触发，抛出AuthenticationException异常
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        String errMsg = authException.getMessage();
        logger.error("Unauthorized error: {}", errMsg);

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");

        if ("Bad credentials".equals(errMsg)) {
            errMsg = "密码错误，请重试";
        }
        PrintWriter printWriter = response.getWriter();
        printWriter.print(errMsg);
        printWriter.flush();
        printWriter.close();
    }
}
