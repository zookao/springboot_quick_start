package com.zookao.service;

import com.alibaba.fastjson.JSONObject;
import com.zookao.persistence.entity.RoleToMenu;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 角色菜单表 服务类
 *
 * @author zookao
 * @since 2021-12-14
 */
public interface RoleToMenuService extends IService<RoleToMenu> {
    Boolean binds(JSONObject requestJson);
}
