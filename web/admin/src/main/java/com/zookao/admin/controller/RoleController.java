package com.zookao.admin.controller;


import com.alibaba.fastjson.JSONObject;
import com.zookao.admin.annotation.Log;
import com.zookao.admin.annotation.ValidationParam;
import com.zookao.admin.helper.ResponseHelper;
import com.zookao.admin.helper.ResponseModel;
import com.zookao.persistence.entity.Menu;
import com.zookao.persistence.entity.Role;
import com.zookao.persistence.entity.RoleToMenu;
import com.zookao.persistence.entity.UserToRole;
import com.zookao.service.RoleService;
import com.zookao.service.RoleToMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 角色表 前端控制器
 *
 * @author zookao
 * @since 2021-12-14
 */
@Api(tags="角色接口")
@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleToMenuService roleToMenuService;

    @ApiOperation(value = "添加角色", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "requestJson", example = "{\"name\":\"角色名\"}", required = true, dataType = "String", paramType = "body",dataTypeClass = String.class)
    })
    @PostMapping("/add")
    @Log(action = "add", modelName = "Role", description = "添加角色")
    @RequiresPermissions(value = {"role:add"})
    public ResponseModel<Role> add(@ValidationParam("name") @RequestBody JSONObject requestJson) throws Exception {
        return ResponseHelper.succeed(roleService.addOneRole(requestJson));
    }

    @ApiOperation(value = "角色列表")
    @GetMapping("/index")
    @RequiresPermissions("role:index")
    public ResponseModel<List<Role>> index(@RequestParam(value = "page",defaultValue = "1") Integer page){
        return ResponseHelper.succeed(roleService.getRoleList(page));
    }

    @ApiOperation(value = "角色绑定权限", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "requestJson", example = "{\"roleId\":\"1\",\"menuId\":[\"1\",\"2\"]}", required = true, dataType = "String", paramType = "body",dataTypeClass = String.class)
    })
    @PostMapping("/role-to-menu")
    @Log(action = "roleToMenu", modelName = "Role", description = "角色绑定权限")
    @RequiresPermissions(value = {"role:roleToMenu"})
    public ResponseModel roleToMenu(
            @ValidationParam("roleId,menuId") @RequestBody JSONObject requestJson) throws Exception {
        return ResponseHelper.succeed(roleToMenuService.binds(requestJson));
    }

    @ApiOperation(value = "删除", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "requestJson", example = "{\"id\":\"1\"}", required = true, dataType = "String", paramType = "body",dataTypeClass = String.class)
    })
    @PostMapping("/delete")
    @Log(action = "delete", modelName = "Role", description = "删除")
    @RequiresPermissions(value = {"role:delete"})
    public ResponseModel delete(@ValidationParam("id") @RequestBody JSONObject requestJson) throws Exception {
        return ResponseHelper.succeed(roleService.deleteOneRole(requestJson));
    }
}
