package com.zookao.persistence.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 用户表
 *
 * @author zookao
 * @since 2021-12-14
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@TableName("qs_user")
@ApiModel(value = "User对象", description = "用户表")
public class User extends Model<User> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("是电话号码，也是账号（登录用）")
    private String mobile;

    @ApiModelProperty("姓名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("创建时间")
    private Long createTime;

    @ApiModelProperty("状态值（1：启用，2：禁用，3：删除）")
    private Integer status;

    @TableField(exist = false)
    private String token;

    @TableField(exist = false)
    private String roleName;

    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
