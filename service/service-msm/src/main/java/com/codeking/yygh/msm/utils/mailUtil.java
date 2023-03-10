package com.codeking.yygh.msm.utils;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class mailUtil {
    public static void sendSimpleMail(JavaMailSender javaMailSender,String from,String to,String subject,String code){
        SimpleMailMessage message = new SimpleMailMessage();
        //邮件设置
        message.setFrom(from);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(code);
        javaMailSender.send(message);
    }
}