package com.sky.controller.admin;

import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author: cyl
 * @description: TODO 通用接口
 * @date: 2024/2/17 14:30
 */
@RestController
@RequestMapping("/admin/common")
@Slf4j
@Api("通用接口")
public class CommonController {

    @Autowired
    private AliOssUtil aliOssUtil;

    @PostMapping("/upload")
    @ApiOperation("文件上传")
    public Result<String> upload(MultipartFile file) {
        try {
            log.info("文件上传: {}",file);
            String url = aliOssUtil.upload(file);
            log.info("文件上传成功: {}",url);
            return Result.success(url);
        } catch (Exception e) {
            log.error("文件上传失败: {}",e.getMessage());
        }

        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}
