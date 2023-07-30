package com.zj.examsystem.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KaptchaConfig {
    @Bean
    public DefaultKaptcha getKaptchaBean() {
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        properties.setProperty("kaptcha.textproducer.char.string", "123456789");//验证码字符范围
        properties.setProperty("kaptcha.border.color", "245,248,249");//图片边框颜色
        properties.setProperty("kaptcha.textproducer.font.color", "black");//字体颜色
        properties.setProperty("kaptcha.textproducer.char.space", "2");//文字间隔
        properties.setProperty("kaptcha.image.width", "125");//图片宽度
        properties.setProperty("kaptcha.image.height", "45");//图片高度
        properties.setProperty("kaptcha.session.key", "code");//session的key
        properties.setProperty("kaptcha.textproducer.char.length", "4");//长度
        properties.setProperty("kaptcha.textproducer.font.names", "宋体,楷体,微软雅黑");//字体
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}