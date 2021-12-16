package com.zookao.admin.controller;


import com.alibaba.fastjson.JSONObject;
import com.zookao.admin.annotation.AccessLimit;
import com.zookao.admin.annotation.Log;
import com.zookao.admin.annotation.Pass;
import com.zookao.admin.annotation.ValidationParam;
import com.zookao.admin.helper.ResponseHelper;
import com.zookao.admin.helper.ResponseModel;
import com.zookao.persistence.entity.User;
import com.zookao.persistence.entity.UserToRole;
import com.zookao.service.UserService;
import com.zookao.service.UserToRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 用户表 前端控制器
 *
 * @author zookao
 * @since 2021-12-14
 */
@Api(tags="管理员接口")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserToRoleService userToRoleService;

    @ApiOperation(value = "添加管理员", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "requestJson", example = "{\"username\":\"admin\",\"mobile\":\"13888888888\",</br>" +
                    "\"password\":\"123456\",\"rePassword\":\"123456\",\"email\":\"zookao@126.com\"}", required = true, dataType = "String", paramType = "body",dataTypeClass = String.class)
    })
    @PostMapping("/add")
    @Log(action = "add", modelName = "User", description = "添加管理员")
    // @Pass
    @RequiresPermissions(value = {"admin:add"})
    public ResponseModel<User> add(@ValidationParam("username,password,rePassword,mobile,email") @RequestBody JSONObject requestJson) throws Exception {
        return ResponseHelper.succeed(userService.checkAndRegisterUser(requestJson));
    }

    @ApiOperation(value = "管理员列表")
    @GetMapping("/index")
    @RequiresPermissions("admin:index")
     //每x秒往令牌桶里放y个令牌，即1秒10个请求
    @AccessLimit(perSecond=10,timeOut = 1,timeOutUnit = TimeUnit.SECONDS)
    public ResponseModel<List<User>> index(@RequestParam(value = "page",defaultValue = "1") Integer page){
        return ResponseHelper.succeed(userService.getAdminList(page));
    }

    @ApiOperation(value = "用户绑定角色", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "requestJson", example = "{\"userId\":\"1\",\"roleId\":\"1\"}", required = true, dataType = "String", paramType = "body",dataTypeClass = String.class)
    })
    @PostMapping("/user-to-role")
    @Log(action = "userToRole", modelName = "User", description = "用户绑定角色")
    @RequiresPermissions(value = {"admin:userToRole"})
    public ResponseModel<UserToRole> userToRole(
            @ValidationParam("userId,roleId") @RequestBody JSONObject requestJson) throws Exception {
        return ResponseHelper.succeed(userToRoleService.bind(requestJson));
    }

    @ApiOperation(value = "登录", notes = "", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "requestJson", example = "{\"username\":\"admin\",\"password\":\"111111\"}", required = true, dataType = "String", paramType = "body",dataTypeClass = String.class)
    })
    @PostMapping("/login")
    @Log(action = "login", modelName = "User", description = "后台登录")
    @Pass
    public ResponseModel<Map<String, Object>> login(
            @ValidationParam("username,password") @RequestBody JSONObject requestJson) throws Exception {
        return ResponseHelper.succeed(userService.checkUsernameAndPassword(requestJson));
    }

    @ApiOperation(value = "删除", produces = "application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "requestJson", example = "{\"id\":\"1\"}", required = true, dataType = "String", paramType = "body",dataTypeClass = String.class)
    })
    @PostMapping("/delete")
    @Log(action = "delete", modelName = "User", description = "删除")
    @RequiresPermissions(value = {"admin:delete"})
    public ResponseModel delete(@ValidationParam("id") @RequestBody JSONObject requestJson) throws Exception {
        return ResponseHelper.succeed(userService.deleteOneUser(requestJson));
    }
}
