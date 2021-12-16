package com.zookao.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.zookao.common.base.BusinessException;
import com.zookao.persistence.entity.Menu;

import java.util.List;

/**
 * 菜单表 服务类
 *
 * @author zookao
 * @since 2021-12-14
 */
public interface MenuService extends IService<Menu> {

    List<Menu> findMenuByRoleId(Integer roleId);

    List<Menu> treeMenuList(Integer pId, List<Menu> list);

    List<Menu> getMenuList(Integer page);

    Menu addOneMenu(JSONObject requestJson) throws BusinessException;

    Boolean deleteOneMenu(JSONObject requestJson) throws BusinessException;
}
