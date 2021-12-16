package com.zookao.admin.controller;


import com.alibaba.fastjson.JSONObject;
import com.zookao.admin.annotation.Log;
import com.zookao.admin.annotation.ValidationParam;
import com.zookao.admin.helper.ResponseHelper;
import com.zookao.admin.helper.ResponseModel;
import com.zookao.persistence.entity.Menu;
import com.zookao.service.MenuService;
import com.zookao.service.RedisService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单表 前端控制器
 *
 * @author zookao
 * @since 2021-12-14
 */
@Api(tags="菜单\\权限接口")
@RestController
@RequestMapping("/menu")
// @ApiIgnore
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private RedisService redisService;

    @ApiOperation(value = "添加菜单", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "requestJson", example = "{\"parent_id\":\"0\",\"code\":\"admin:index\",\"name\":\"用户管理\",\"sort\":\"0\",\"type\":\"1\"}", required = true, dataType = "String", paramType = "body")
    })
    @PostMapping("/add")
    @Log(action = "add", modelName = "Menu", description = "添加菜单")
    @RequiresPermissions(value = {"menu:add"})
    public ResponseModel<Menu> add(@ValidationParam("parent_id,code,name,sort,type") @RequestBody JSONObject requestJson) throws Exception {
        return ResponseHelper.succeed(menuService.addOneMenu(requestJson));
    }

    @ApiOperation(value = "菜单列表")
    @GetMapping("/index")
    @RequiresPermissions("menu:index")
    public ResponseModel<List<Menu>> index(@RequestParam(value = "page",defaultValue = "1") Integer page) throws Exception {
        return ResponseHelper.succeed(menuService.getMenuList(page));
    }

    @ApiOperation(value = "删除", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "requestJson", example = "{\"id\":\"1\"}", required = true, dataType = "String", paramType = "body")
    })
    @PostMapping("/delete")
    @Log(action = "delete", modelName = "Menu", description = "删除")
    @RequiresPermissions(value = {"menu:delete"})
    public ResponseModel delete(@ValidationParam("id") @RequestBody JSONObject requestJson) throws Exception {
        return ResponseHelper.succeed(menuService.deleteOneMenu(requestJson));
    }
}
