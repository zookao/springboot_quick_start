package com.zookao.persistence.entity;

import io.swagger.annotations.ApiModel;
import lombok.*;

/**
 * 验证码
 *
 * @author zookao
 * @since 2021-12-23
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "验证码对象")
public class Captcha {

    private String uuid;
    private String code;
    private String imageBase64;
}
