package com.zookao.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zookao.common.base.BusinessException;
import com.zookao.common.base.CodeEnum;
import com.zookao.common.util.ComUtil;
import com.zookao.persistence.entity.Role;
import com.zookao.persistence.entity.RoleToMenu;
import com.zookao.persistence.entity.UserToRole;
import com.zookao.persistence.mapper.RoleMapper;
import com.zookao.service.RoleService;
import com.zookao.service.UserToRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色表 服务实现类
 *
 * @author zookao
 * @since 2021-12-14
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private UserToRoleService userToRoleService;

    @Override
    public List<Role> getRoleList(Integer page) {
        Page<Role> rolePage = new Page<>(page, 10);
        IPage<Role> _p = this.page(rolePage, null);
        List<Role> records = _p.getRecords();
        return records;
    }

    @Override
    public Role addOneRole(JSONObject requestJson) throws BusinessException {
        Role role = requestJson.toJavaObject(Role.class);
        Role check = this.getOne(new QueryWrapper<Role>().eq("name", role.getName()));
        if(check == null){
            boolean result = this.save(role);
            return role;
        }else{
            throw new BusinessException(CodeEnum.PARAM_ERROR.getMsg(), CodeEnum.PARAM_ERROR.getCode());
        }
    }

    @Override
    public Boolean deleteOneRole(JSONObject requestJson) throws BusinessException {
        Integer roleId = requestJson.getInteger("id");
        if (ComUtil.isEmpty(this.getById(roleId))) {
            throw new BusinessException(CodeEnum.INVALID_ROLE.getMsg(), CodeEnum.INVALID_ROLE.getCode());
        }
        if(!ComUtil.isEmpty(userToRoleService.list(new QueryWrapper<UserToRole>().eq("role_id", roleId)))){
            throw new BusinessException("删除失败，角色已绑定管理员，请先处理");
        }
        return this.removeById(roleId);
    }
}
