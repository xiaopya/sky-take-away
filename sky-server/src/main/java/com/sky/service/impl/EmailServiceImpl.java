package com.sky.service.impl;

import com.sky.context.BaseContext;
import com.sky.dto.EmailDTO;
import com.sky.service.EmailService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import javax.validation.constraints.Email;

/**
 * @author: cyl
 * @description: TODO 邮箱发送实现类
 * @date: 2024/3/1 19:33
 */
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Value("${spring.mail.username}")
    private String from;

    @Override
    public boolean sendEmail(EmailDTO emailDTO) {
        //发一个简单邮件
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        //设置发件人账号
        mailMessage.setFrom(from);
        //设置接收人账号
        mailMessage.setTo(emailDTO.getTo());
        //设置邮件标题
        mailMessage.setSubject(emailDTO.getTitle());
        //设置邮件正文内容
        mailMessage.setText(emailDTO.getContent());
        //发送邮件
        javaMailSender.send(mailMessage);

        return true;
    }
}
