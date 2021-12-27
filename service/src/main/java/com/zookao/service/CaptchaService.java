package com.zookao.service;

import com.zookao.persistence.entity.Captcha;

/**
 * User: zookao
 * Date: 2021-12-27
 */
public interface CaptchaService {
    public Captcha generate() throws Exception;

    public Boolean verify(String redisCode, String userCode);
}
