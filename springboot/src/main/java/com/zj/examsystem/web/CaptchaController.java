package com.zj.examsystem.web;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;

@Controller
@RequestMapping("/captcha")
public class CaptchaController
{
    @Resource
    private Producer captchaProducer;

    // 获取验证码图片
    @RequestMapping("/getCaptchaCode")
    public void getCaptchaCode(HttpServletRequest request, HttpServletResponse response) throws IOException
    {
        HttpSession session = request.getSession();

        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");

        //生成验证码文本
        String capText = captchaProducer.createText();
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
        System.out.println("生成验证码文本====" + capText);
        //利用生成的字符串构建图片
        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi, "jpg", out);

        try {
            out.flush();
        } finally {
            out.close();
        }
    }

    // 前端输入的验证码与生成的对比
    @RequestMapping("/checkCaptchaCode")
    @ResponseBody
    public String checkCaptchaCode(HttpServletRequest request, HttpServletResponse response, String captchaCode)
    {
        System.out.println("页面输入验证码====" + captchaCode);
        if ("".equals(captchaCode)) {
            return "输入为空";
        }

        response.setCharacterEncoding("UTF-8");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);

        String generateCode = (String) request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        if (generateCode.equals(captchaCode)) {
            return "验证成功";
        } else {
            return "输入错误";
        }
    }
}