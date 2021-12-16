package com.zookao.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zookao.common.base.BusinessException;
import com.zookao.persistence.entity.Role;

import java.util.List;

/**
 * 角色表 服务类
 *
 * @author zookao
 * @since 2021-12-14
 */
public interface RoleService extends IService<Role> {
    List<Role> getRoleList(Integer page);

    Role addOneRole(JSONObject requestJson) throws BusinessException;

    Boolean deleteOneRole(JSONObject requestJson) throws BusinessException;
}
