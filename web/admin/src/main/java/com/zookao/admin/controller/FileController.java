package com.zookao.admin.controller;

import com.zookao.admin.annotation.Log;
import com.zookao.admin.annotation.Pass;
import com.zookao.admin.helper.ResponseHelper;
import com.zookao.admin.helper.ResponseModel;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * User: zookao
 * Date: 2021-12-24
 */
@Api(tags = "文件上传下载")
@RestController
@RequestMapping("/file")
public class FileController {

    @ApiOperation(value = "文件上传", notes = "", produces = "multipart/form-data")
    @PostMapping("/upload")
    @Log(action = "upload", modelName = "File", description = "文件上传")
    @Pass
    public ResponseModel upload(@RequestParam("file") MultipartFile[] multipartFiles) throws IOException {
        System.out.println("multipartFiles = " + multipartFiles);
        Date date = new Date();
        String folder = new SimpleDateFormat("yyyyMMdd").format(date);

        File rootPath = new File(ResourceUtils.getURL("classpath:").getPath());
        File parentPath = new File(rootPath.getAbsolutePath(), "static/upload/" + folder);
        if (!parentPath.exists() && !parentPath.isDirectory()) {
            parentPath.mkdirs();
        }
        if (multipartFiles != null && multipartFiles.length > 0) {
            for (int i = 0; i < multipartFiles.length; i++) {
                String filename = multipartFiles[i].getOriginalFilename();
                System.out.println("filename = " + filename);
                String extension = filename.split("\\.")[filename.split("\\.").length - 1];
                String filePath = parentPath + "/" + UUID.randomUUID().toString().replaceAll("-", "") + "." + extension;
                multipartFiles[i].transferTo(new File(filePath));
            }
        }
        return ResponseHelper.succeed(null);
    }

    @ApiOperation(value = "文件下载", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", example = "1.png", required = true, dataType = "String", paramType = "query",dataTypeClass = String.class)
    })
    @GetMapping("/download")
    @Log(action = "download", modelName = "File", description = "文件下载")
    @Pass
    public Object download(@RequestParam("file") String fileName, HttpServletResponse response) throws FileNotFoundException {
        File file = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "static/" + fileName);
        OutputStream os = null;
        InputStream is = null;
        try {
            // 取得输出流
            os = response.getOutputStream();
            // 清空输出流
            response.reset();
            response.setContentType("application/x-download;charset=utf-8");
            response.setHeader("Content-Disposition", "attachment;filename=" + file.getName());
            //读取流
            is = new FileInputStream(file);
            if (is == null) {
                return ResponseHelper.failed(null);
            }
            //复制
            IOUtils.copy(is, response.getOutputStream());
            response.getOutputStream().flush();
        } catch (IOException e) {
            return ResponseHelper.failed(null);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ResponseHelper.succeed(null);
    }
}
