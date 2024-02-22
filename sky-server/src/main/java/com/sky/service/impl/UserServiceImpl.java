package com.sky.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sky.constant.MessageConstant;
import com.sky.dto.UserLoginDTO;
import com.sky.entity.User;
import com.sky.exception.LoginFailedException;
import com.sky.mapper.UserMapper;
import com.sky.properties.WeChatProperties;
import com.sky.service.UserService;
import com.sky.utils.HttpClientUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;

/**
 * @author: cyl
 * @description: TODO 用户实现类
 * @date: 2024/2/22 19:50
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    // 微信服务接口地址
    public static final String WX_LOGIN = "https://api.weixin.qq.com/sns/jscode2session";
    @Autowired
    private WeChatProperties weChatProperties;
    @Autowired
    private UserMapper userMapper;

    @Override
    public User wxLogin(UserLoginDTO userLoginVO) {

        String openid = getOpenId(userLoginVO.getCode());

        // 处理用户id的情况
        if(openid == null){
            throw  new LoginFailedException(MessageConstant.LOGIN_FAILED);
        }

        // 需要为新用户自动完成注册
        User user = userMapper.getByOpenId(openid);

        if(user == null){
            user = User.builder()
                    .openid(openid)
                    .createTime(LocalDateTime.now())
                    .build();
            log.info("User created: {}",user);
            userMapper.insert(user);
        }

        return user;
    }

    /**
     * 获取用户的openid
     */
    private String getOpenId(String code){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("appid",weChatProperties.getAppid());
        hashMap.put("secret",weChatProperties.getSecret());
        hashMap.put("js_code",code);
        hashMap.put("grant_type","authorization_code");
        String json = HttpClientUtil.doGet(WX_LOGIN, hashMap);
        JSONObject jsonObject = JSON.parseObject(json);
        return jsonObject.getString("openid");
    }
}
