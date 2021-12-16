package com.zookao.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zookao.common.base.BusinessException;
import com.zookao.common.base.CodeEnum;
import com.zookao.common.base.Constant;
import com.zookao.common.util.ComUtil;
import com.zookao.common.util.JWTUtil;
import com.zookao.common.util.StringUtil;
import com.zookao.persistence.entity.Menu;
import com.zookao.persistence.entity.Role;
import com.zookao.persistence.entity.User;
import com.zookao.persistence.entity.UserToRole;
import com.zookao.persistence.mapper.UserMapper;
import com.zookao.service.MenuService;
import com.zookao.service.RoleService;
import com.zookao.service.UserService;
import com.zookao.service.UserToRoleService;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户表 服务实现类
 *
 * @author zookao
 * @since 2021-12-14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserToRoleService userToRoleService;
    @Autowired
    RoleService roleService;
    @Autowired
    MenuService menuService;

    @Override
    public User checkAndRegisterUser(JSONObject requestJson) throws Exception {
        //可直接转为java对象,简化操作,不用再set一个个属性
        User userRegister = requestJson.toJavaObject(User.class);
        if (!StringUtil.checkMobileNumber(userRegister.getMobile())) {
            throw new BusinessException(CodeEnum.MOBILE_ERROR.getMsg(), CodeEnum.MOBILE_ERROR.getCode());
        }
        if (!StringUtil.checkEmail(userRegister.getEmail())) {
            throw new BusinessException(CodeEnum.MOBILE_ERROR.getMsg(), CodeEnum.EMAIL_ERROR.getCode());
        }
        if (!userRegister.getPassword().equals(requestJson.getString("rePassword"))) {
            throw new BusinessException(CodeEnum.INVALID_RE_PASSWORD.getMsg(), CodeEnum.INVALID_RE_PASSWORD.getCode());
        }
        User check = this.getOne(new QueryWrapper<User>().eq("username", userRegister.getUsername()));
        if(check != null){
            throw new BusinessException(CodeEnum.INVALID_USER_EXIST.getMsg());
        }
        userRegister.setPassword(BCrypt.hashpw(requestJson.getString("password"), BCrypt.gensalt()));
        User registerUser = this.register(userRegister, Constant.RoleType.ADMIN);
        //默认注册普通用户
        return registerUser;
    }

    @Override
    public User register(User user, String roleName) throws Exception {
        user.setCreateTime(System.currentTimeMillis());
        boolean result = this.save(user);
        if (result) {
            Role role = roleService.getOne(new QueryWrapper<Role>().eq("name", roleName));
            if (role == null) {
                throw new BusinessException(CodeEnum.INVALID_ROLE.getMsg());
            }
            UserToRole userToRole = new UserToRole();
            userToRole.setUserId(user.getId());
            userToRole.setRoleId(role.getId());
            userToRoleService.save(userToRole);
        }
        return user;
    }

    @Override
    public Map<String, Object> checkUsernameAndPassword(JSONObject requestJson) throws Exception {
        //@ValidationParam注解已经验证过mobile和passWord参数，所以可以直接使用
        String username = requestJson.getString("username");
        User user = this.getOne(new QueryWrapper<User>().eq("username", username));
        if (ComUtil.isEmpty(user) || !BCrypt.checkpw(requestJson.getString("password"), user.getPassword())) {
            throw new BusinessException(CodeEnum.INVALID_USERNAME_PASSWORD.getMsg(), CodeEnum.INVALID_USERNAME_PASSWORD.getCode());
        }
        return this.getLoginUserAndMenuInfo(user);
    }

    @Override
    public Map<String, Object> getLoginUserAndMenuInfo(User user) {
        Map<String, Object> result = new HashMap<>();
        UserToRole userToRole = userToRoleService.selectByUserId(user.getId());
        user.setToken(JWTUtil.sign(user.getId(), user.getPassword()));
        result.put("user", user);
        //根据角色主键查询启用的菜单权限
        List<Menu> menuList = menuService.findMenuByRoleId(userToRole.getRoleId());
        List<Menu> retMenuList = menuService.treeMenuList(Constant.ROOT_MENU, menuList);
        result.put("menuList", retMenuList);
        return result;
    }

    @Override
    public List<User> getAdminList(Integer page) {
        Page<User> userPage = new Page<>(page, 10);
        IPage<User> _p = this.page(userPage, null);
        List<User> records = _p.getRecords();
        return records;
    }

    @Override
    public Boolean deleteOneUser(JSONObject requestJson) throws BusinessException {
        Integer userId = requestJson.getInteger("id");
        User user = this.getById(userId);
        if (ComUtil.isEmpty(user)) {
            throw new BusinessException("管理员不存在");
        }
        userToRoleService.remove(new QueryWrapper<UserToRole>().eq("user_id",userId));
        return this.removeById(userId);
    }
}
