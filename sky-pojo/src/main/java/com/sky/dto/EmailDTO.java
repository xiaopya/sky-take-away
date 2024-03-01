package com.sky.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: cyl
 * @description: TODO 邮箱类
 * @date: 2024/3/1 19:32
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmailDTO {
    //收件人 ，是一个邮箱地址
    private String to;

    //邮件正文内容
    private String content;

    //邮件标题
    private String title;
}
