package com.zookao.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zookao.common.base.BusinessException;
import com.zookao.common.base.CodeEnum;
import com.zookao.common.util.ComUtil;
import com.zookao.persistence.entity.UserToRole;
import com.zookao.persistence.mapper.UserToRoleMapper;
import com.zookao.service.UserToRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户角色关系表 服务实现类
 *
 * @author zookao
 * @since 2021-12-14
 */
@Service
public class UserToRoleServiceImpl extends ServiceImpl<UserToRoleMapper, UserToRole> implements UserToRoleService {

    @Override
    public UserToRole selectByUserId(Integer userId) {
        QueryWrapper<UserToRole> qw = new QueryWrapper<>();
        qw.eq("id", userId);
        List<UserToRole> userToRoleList = this.list(qw);
        return ComUtil.isEmpty(userToRoleList)? null: userToRoleList.get(0);
    }

    @Override
    public UserToRole bind(JSONObject requestJson) throws BusinessException {
        UserToRole userToRole = requestJson.toJavaObject(UserToRole.class);
        UserToRole check = this.getOne(new QueryWrapper<UserToRole>().eq("user_id", userToRole.getUserId()).and(i -> i.eq("role_id", userToRole.getRoleId())));
        if(check == null){
            boolean result = this.save(userToRole);
            return userToRole;
        }else{
            check.setRoleId(userToRole.getRoleId());
            this.save(check);
            return check;
        }
    }
}
