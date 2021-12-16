package com.zookao.persistence.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 用户角色关系表
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
@TableName("qs_user_to_role")
@ApiModel(value = "UserToRole对象", description = "用户角色关系表")
public class UserToRole extends Model<UserToRole> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("用户编号")
    private Integer userId;

    @ApiModelProperty("角色代号")
    private Integer roleId;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
