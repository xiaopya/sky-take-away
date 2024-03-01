package com.sky.service;

import com.sky.dto.EmailDTO;

public interface EmailService {
    //发邮件的方法
    boolean sendEmail(EmailDTO emailDTO);
}
