package com.zookao.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zookao.common.base.BusinessException;
import com.zookao.persistence.entity.User;
import com.zookao.persistence.entity.UserToRole;

import java.util.List;
import java.util.Map;

/**
 * 用户表 服务类
 *
 * @author zookao
 * @since 2021-12-14
 */
public interface UserService extends IService<User> {
    User checkAndRegisterUser(JSONObject requestJson) throws Exception;

    User register(User user, String roleName) throws Exception;

    Map<String, Object> checkUsernameAndPassword(JSONObject requestJson) throws Exception;

    Map<String, Object> getLoginUserAndMenuInfo(User user);

    List<User> getAdminList(Integer page);

    Boolean deleteOneUser(JSONObject requestJson) throws BusinessException;
}
