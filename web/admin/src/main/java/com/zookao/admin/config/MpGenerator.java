package com.zookao.admin.config;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class MpGenerator {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://192.168.56.102:3306/quick_start?useUnicode=true&characterEncoding=utf8&useSSL=false", "root", "root")
                .globalConfig(builder -> {
                    builder.author("zookao") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("D://Desktop")
                            // .outputDir(System.getProperty("user.dir") + "/src/main/java") //指定输出目录，默认值: windows:D://
                            .disableOpenDir()
                            .build(); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.zookao.persistence") // 设置父包名
                            .moduleName("") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "D://Desktop//xml"))
                            // .pathInfo(Collections.singletonMap(OutputFile.mapperXml, "src/main/resources/mapper")) //路径配置信息，Collections.singletonMap(OutputFile.mapperXml, "D://")
                            .build(); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("qs_menu", "qs_operation_log", "qs_role", "qs_role_to_menu", "qs_user", "qs_user_to_role","cnarea_2020") // 设置需要生成的表名
                            .addTablePrefix("qs_")
                            .entityBuilder()
                            .enableLombok()
                            .enableActiveRecord()//开启 ActiveRecord 模型，默认值:false
                            .naming(NamingStrategy.underline_to_camel)
                            .columnNaming(NamingStrategy.underline_to_camel)
                            .idType(IdType.AUTO)
                            .controllerBuilder()//controller 策略配置
                            .enableHyphenStyle()//开启驼峰转连字符，默认值:false
                            .enableRestStyle()//开启生成@RestController 控制器
                            .formatFileName("%sController")
                            .serviceBuilder()//service 策略配置
                            .formatServiceFileName("%sService")
                            .formatServiceImplFileName("%sServiceImpl")
                            .mapperBuilder()//mapper 策略配置
                            .superClass(BaseMapper.class)
                            .enableMapperAnnotation()//开启 @Mapper 注解，默认值:false
                            .enableBaseResultMap()//启用 BaseResultMap 生成，默认值:false
                            .enableBaseColumnList()
                            .formatMapperFileName("%sMapper")//转换 mapper 接口文件名称后缀，mapper目录下的，例如：UserLoginMapper（有@Mapper）
                            .formatXmlFileName("%sMapper")//转换 xml 文件名称后缀，例如：UserLoginMapper.xml，Mybatis的xml映射文件
                            .build();
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
