package com.zookao.persistence.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 中国行政地区表
 *
 * @author zookao
 * @since 2022-02-15
 */
@Getter
@Setter
@TableName("cnarea_2020")
@ApiModel(value = "Cnarea2020对象", description = "中国行政地区表")
public class Cnarea2020 extends Model<Cnarea2020> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty("层级")
    private Boolean level;

    @ApiModelProperty("父级行政代码")
    private Long parentCode;

    @ApiModelProperty("行政代码")
    private Long areaCode;

    @ApiModelProperty("邮政编码")
    private Integer zipCode;

    @ApiModelProperty("区号")
    private String cityCode;

    @ApiModelProperty("名称")
    private String name;

    @ApiModelProperty("简称")
    private String shortName;

    @ApiModelProperty("组合名")
    private String mergerName;

    @ApiModelProperty("拼音")
    private String pinyin;

    @ApiModelProperty("经度")
    private BigDecimal lng;

    @ApiModelProperty("纬度")
    private BigDecimal lat;


    @Override
    public Serializable pkVal() {
        return this.id;
    }

}
