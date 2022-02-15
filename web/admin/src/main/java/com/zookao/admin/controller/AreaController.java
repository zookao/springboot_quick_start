package com.zookao.admin.controller;


import com.alibaba.fastjson.JSONObject;
import com.zookao.admin.annotation.Log;
import com.zookao.admin.annotation.Pass;
import com.zookao.admin.annotation.ValidationParam;
import com.zookao.admin.helper.ResponseHelper;
import com.zookao.admin.helper.ResponseModel;
import com.zookao.persistence.entity.Cnarea2020;
import com.zookao.persistence.entity.Role;
import com.zookao.service.Cnarea2020Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 中国行政地区表 前端控制器
 *
 * @author zookao
 * @since 2022-02-15
 */
@Api(tags = "区域规划")
@RestController
@RequestMapping("/area")
public class AreaController {

    @Autowired
    private Cnarea2020Service cnarea2020Service;

    @ApiOperation(value = "所有省", produces = "application/json")
    @GetMapping("/level0")
    @Pass
    public ResponseModel<List<Cnarea2020>> level0() throws Exception {
        return ResponseHelper.succeed(cnarea2020Service.allLevel0());
    }

    @ApiOperation(value = "下级", produces = "application/json")
    @GetMapping("/getByParent")
    @Pass
    public ResponseModel<List<Cnarea2020>> getByParent(@RequestParam(value = "code") Long code) throws Exception {
        return ResponseHelper.succeed(cnarea2020Service.getByParent(code));
    }
}
