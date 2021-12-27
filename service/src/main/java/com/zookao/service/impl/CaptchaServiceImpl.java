package com.zookao.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.ShearCaptcha;
import cn.hutool.captcha.generator.MathGenerator;
import com.zookao.persistence.entity.Captcha;
import com.zookao.service.CaptchaService;
import com.zookao.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * User: zookao
 * Date: 2021-12-27
 */
@Service
public class CaptchaServiceImpl implements CaptchaService {
    @Autowired
    private RedisService redisService;

    @Override
    public Captcha generate() throws Exception {
        ShearCaptcha captcha = CaptchaUtil.createShearCaptcha(140, 35, 2, 4);
        captcha.setGenerator(new MathGenerator(1));
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        String imageBase64 = captcha.getImageBase64Data();
        String code = captcha.getCode();
        redisService.set(uuid, code);
        redisService.expire(uuid, 600);
        Captcha image = Captcha.builder().uuid(uuid).imageBase64(imageBase64).build();
        return image;
    }

    @Override
    public Boolean verify(String redisCode, String userCode) {
        return new MathGenerator().verify(redisCode, userCode);
    }
}
