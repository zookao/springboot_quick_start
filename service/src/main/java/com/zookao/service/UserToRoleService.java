package com.zookao.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zookao.common.base.BusinessException;
import com.zookao.persistence.entity.UserToRole;

/**
 * 用户角色关系表 服务类
 *
 * @author zookao
 * @since 2021-12-14
 */
public interface UserToRoleService extends IService<UserToRole> {

    UserToRole selectByUserId(Integer userId);

    UserToRole bind(JSONObject requestJson) throws BusinessException;
}
