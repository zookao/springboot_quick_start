package com.zookao.admin.shiro;

import com.zookao.common.base.Constant;
import com.zookao.common.util.ComUtil;
import com.zookao.common.util.JWTUtil;
import com.zookao.persistence.entity.Menu;
import com.zookao.persistence.entity.User;
import com.zookao.persistence.entity.UserToRole;
import com.zookao.service.*;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MyRealm extends AuthorizingRealm {
    private UserService userService;
    private UserToRoleService userToRoleService;
    private MenuService menuService;
    private RoleService roleService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if (userService == null) {
            this.userService = SpringContextBeanService.getBean(UserService.class);
        }
        if (userToRoleService == null) {
            this.userToRoleService = SpringContextBeanService.getBean(UserToRoleService.class);
        }
        if (menuService == null) {
            this.menuService = SpringContextBeanService.getBean(MenuService.class);
        }
        if (roleService == null) {
            this.roleService = SpringContextBeanService.getBean(RoleService.class);
        }

        Integer userId = JWTUtil.getUserId(principals.toString());
        User user = userService.getById(userId);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        if (null != user) {
            UserToRole userToRole = userToRoleService.selectByUserId(user.getId());
            //控制菜单级别按钮  类中用@RequiresPermissions("user:list") 对应数据库中code字段来控制controller
            ArrayList<String> pers = new ArrayList<>();
            List<Menu> menuList = menuService.findMenuByRoleId(userToRole.getRoleId());
            for (Menu per : menuList) {
                if (!ComUtil.isEmpty(per.getCode())) {
                    pers.add(String.valueOf(per.getCode()));
                }
            }
            Set<String> permission = new HashSet<>(pers);
            simpleAuthorizationInfo.addStringPermissions(permission);
        }
        return simpleAuthorizationInfo;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        if (userService == null) {
            this.userService = SpringContextBeanService.getBean(UserService.class);
        }
        String token = (String) auth.getCredentials();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        if (Constant.METHOD_URL_SET.contains(request.getRequestURI())) {
            request.setAttribute("currentUser", new User());
            return new SimpleAuthenticationInfo(token, token, this.getName());
        }
        // 解密获得username，用于和数据库进行对比
        Integer userId = JWTUtil.getUserId(token);
        if (userId == null) {
            throw new AuthenticationException("token invalid");
        }
        User userBean = userService.getById(userId);
        if (userBean == null) {
            throw new AuthenticationException("User didn't existed!");
        }
        if (!JWTUtil.verify(token, userId, userBean.getPassword())) {
            throw new AuthenticationException("Username or password error");
        }
        return new SimpleAuthenticationInfo(token, token, this.getName());
    }
}
