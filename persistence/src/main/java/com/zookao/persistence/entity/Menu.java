package com.zookao.persistence.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

/**
 * 菜单表
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
@TableName("qs_menu")
@ApiModel(value = "Menu对象", description = "菜单表")
public class Menu extends Model<Menu> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("父菜单主键")
    private Integer parentId;

    @ApiModelProperty("代码控制权限标识符")
    private String code;

    @ApiModelProperty("菜单名称")
    private String name;

    @ApiModelProperty("菜单的序号")
    private Integer sort;
    @ApiModelProperty("权限类型")
    private Integer type;

    @TableField(exist = false)
    private List<Menu> childMenu;

    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
