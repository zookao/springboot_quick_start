package com.zookao.admin.controller;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.sax.handler.RowHandler;
import com.zookao.persistence.entity.UserExcel;
import org.junit.Test;

import java.util.List;

/**
 * User: zookao
 * Date: 2021-12-24
 */
public class ExcelController {

    private RowHandler createRowHandler() {
        return new RowHandler() {
            @Override
            public void handle(int sheetIndex, long rowIndex, List<Object> rowlist) {
                Console.log("[{}] [{}] {}", sheetIndex, rowIndex, rowlist);
            }
        };
    }

    @Test
    public void testExcel() {
        ExcelUtil.readBySax("user.xlsx", 0, createRowHandler());
        // ExcelReader reader = ExcelUtil.getReader(ResourceUtil.getStream("user.xlsx"),"sheet1");
        // List<UserExcel> all = reader.readAll(UserExcel.class);
        // all.forEach(System.out::println);
    }
}
