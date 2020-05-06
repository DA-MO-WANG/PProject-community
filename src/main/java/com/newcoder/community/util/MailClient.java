package com.newcoder.community.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class MailClient {
    /*获取对应的类的日志对象*/
    private static final Logger logger = LoggerFactory.getLogger(MailClient.class);
    /*Spring boot中提供的JavaMailSender接口，用来实现发送邮件*/
    @Autowired
    private JavaMailSender mailSender;
    /*这个注解是把外部配置文件的值动态注入到Bean中：这个邮件的发送人一般是不变的*/
    @Value("${spring.mail.username}")
    private String from;
    public void sendMail(String to, String subject, String content) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            /*true意思是邮件内容支持html格式*/
            helper.setText(content,true);
            mailSender.send(helper.getMimeMessage());
        } catch (MessagingException e) {
            /*利用日志把错误信息打印*/
            logger.error("发送邮件失败："+e.getMessage());
        }

    }



}
