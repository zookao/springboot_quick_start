package com.zookao.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zookao.persistence.entity.RoleToMenu;
import com.zookao.persistence.entity.UserToRole;
import com.zookao.persistence.mapper.RoleToMenuMapper;
import com.zookao.service.RoleToMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.shiro.util.Assert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 角色菜单表 服务实现类
 *
 * @author zookao
 * @since 2021-12-14
 */
@Service
public class RoleToMenuServiceImpl extends ServiceImpl<RoleToMenuMapper, RoleToMenu> implements RoleToMenuService {

    @Override
    @Transactional
    public Boolean binds(JSONObject requestJson) {
        Integer roleId = requestJson.getInteger("roleId");
        JSONArray menuIds = requestJson.getJSONArray("menuId");
        List<Integer> integers = JSONObject.parseArray(menuIds.toJSONString(), Integer.class);

        List<RoleToMenu> modelList = new ArrayList<>();
        for (Integer integer : integers) {
            RoleToMenu check = this.getOne(new QueryWrapper<RoleToMenu>().eq("role_id", roleId).and(i -> i.eq("menu_id", integer)));
            if (check == null) {
                modelList.add(RoleToMenu.builder().roleId(roleId).menuId(integer).build());
            }
        }
        this.remove(new QueryWrapper<RoleToMenu>().eq("role_id",roleId));
        return this.saveBatch(modelList);
    }
}
