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
 * 操作日志表
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
@TableName("qs_operation_log")
@ApiModel(value = "OperationLog对象", description = "操作日志表")
public class OperationLog extends Model<OperationLog> {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("日志描述")
    private String description;

    @ApiModelProperty("方法参数")
    private String args;

    @ApiModelProperty("用户主键")
    private Integer userId;

    @ApiModelProperty("类名称")
    private String className;

    @ApiModelProperty("方法名称")
    private String methodName;

    private String ip;

    @ApiModelProperty("创建时间")
    private Long createTime;

    @ApiModelProperty("模块名称")
    private String modelName;

    @ApiModelProperty("操作")
    private String action;

    @ApiModelProperty("是否成功 1:成功 2异常")
    private Integer succeed;

    @ApiModelProperty("异常堆栈信息")
    private String message;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
