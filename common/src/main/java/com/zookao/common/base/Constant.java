package com.zookao.common.base;

import com.google.common.collect.Sets;

import java.util.Set;

public class Constant {

    //无需校验权限的url集合
    public static Set<String> METHOD_URL_SET = Sets.newConcurrentHashSet();
    //根菜单节点
    public static final Integer ROOT_MENU = 0;
    //菜单类型，1：菜单  2：按钮操作
    public static final int TYPE_MENU = 1;
    //菜单类型，1：菜单  2：按钮操作
    public static final int TYPE_BUTTON = 2;

    public class RoleType {
        //超级管理员
        public static final String SYSADMIN = "sysadmin";
        //管理员
        public static final String ADMIN = "admin";
    }
}
