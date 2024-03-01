package com.sky.controller.admin;

import com.sky.dto.EmailDTO;
import com.sky.result.Result;
import com.sky.service.EmailService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: cyl
 * @description: TODO 邮箱
 * @date: 2024/3/1 19:38
 */
@RestController
@RequestMapping("/admin/email")
@Slf4j
@Api("邮箱")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping
    public Result<Boolean> sendEmail(@RequestBody EmailDTO emailDTO) {
        log.info("用户邮箱数据：{}",emailDTO);
        boolean b = emailService.sendEmail(emailDTO);
        return Result.success(b);
    }
}
