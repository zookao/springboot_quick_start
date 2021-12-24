package com.zookao.persistence.entity;

import io.swagger.annotations.ApiModel;
import lombok.*;

import java.io.Serializable;

/**
 *
 * @author zookao
 * @since 2021-12-24
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@ApiModel(value = "excel导入用户对象")
public class UserExcel implements Serializable {
    private String mobile;
    private String username;
    private String password;
    private String email;
}
