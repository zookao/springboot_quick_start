package com.zookao.admin.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;
import com.zookao.admin.annotation.Pass;
import com.zookao.persistence.entity.UserExcel;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

/**
 * User: zookao
 * Date: 2021-12-24
 * https://github.com/alibaba/easyexcel
 */
@Slf4j
@RestController
@RequestMapping("/excel")
public class ExcelController {

    @GetMapping("/read")
    @Pass
    public void readExcel() throws FileNotFoundException {
        File file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "static/user.xlsx");
        EasyExcel.read(file, UserExcel.class, new PageReadListener<UserExcel>(dataList -> {
            for (UserExcel user : dataList) {
                log.info("读取到一条数据{}", JSON.toJSONString(user));
            }
        })).sheet(0).doRead();
    }

    @GetMapping("/write")
    @Pass
    public void download(HttpServletResponse response) throws IOException {
        // swagger 会导致各种问题，请直接用浏览器或者用postman
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        // 这里URLEncoder.encode可以防止中文乱码
        String fileName = URLEncoder.encode("测试", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        //写入
        EasyExcel.write(response.getOutputStream(), UserExcel.class).sheet("模板").doWrite(data());
    }

    private List<UserExcel> data() {
        List<UserExcel> list = ListUtils.newArrayList();
        for (int i = 0; i < 7; i++) {
            list.add(UserExcel.builder().username("zookao"+i).mobile("1760000000"+i).email("zookao"+i+"@admin.com").password("111111").build());
        }
        return list;
    }
}
