package com.zhuoheng.mail_demo.service.impl;

import com.zhuoheng.mail_demo.service.MailService;
import com.zhuoheng.mail_demo.utils.MailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpSession;
import java.util.Random;

/**
 * @author lzhuoheng
 */
@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private HttpSession session;
    @Value("${spring.mail.username}")
    private String myMail;
    @Override
    public void sendMail(String username) {
        if (StringUtils.isEmpty(username) || !MailUtil.checkEmail(username)) {
            throw new RuntimeException("请输入正确邮箱");
        }
        //生成六位随机验证码
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            code.append(random.nextInt(10));
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(myMail);
        message.setTo(username);
        message.setSubject("验证码");
        message.setText("您的验证码为 " + code.toString()+" 有效期15分钟，请不要告诉他人哦！");
        javaMailSender.send(message);
        //设置过期时间为15分钟
        session.setMaxInactiveInterval(900);
        session.setAttribute(username, code.toString());
    }

}
