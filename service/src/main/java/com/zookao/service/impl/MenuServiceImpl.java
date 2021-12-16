package com.zookao.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zookao.common.base.BusinessException;
import com.zookao.common.base.CodeEnum;
import com.zookao.common.base.Constant;
import com.zookao.common.util.ComUtil;
import com.zookao.persistence.entity.Menu;
import com.zookao.persistence.entity.RoleToMenu;
import com.zookao.persistence.entity.UserToRole;
import com.zookao.persistence.mapper.MenuMapper;
import com.zookao.service.MenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zookao.service.RoleToMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 菜单表 服务实现类
 *
 * @author zookao
 * @since 2021-12-14
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private RoleToMenuService roleToMenuService;

    @Override
    public List<Menu> findMenuByRoleId(Integer roleId) {
        return this.baseMapper.findMenuByRoleId(roleId);
    }

    @Override
    public  List<Menu> treeMenuList(Integer pId, List<Menu> list) {
        List<Menu> IteratorMenuList = new ArrayList<>();
        for (Menu m : list) {
            if (m.getParentId().equals(pId)) {
                List<Menu> childMenuList = treeMenuList(m.getId(), list);
                m.setChildMenu(childMenuList);
                IteratorMenuList.add(m);
            }
        }
        return IteratorMenuList;
    }

    @Override
    public List<Menu> getMenuList(Integer page) {
        Page<Menu> menuPage = new Page<>(page, 10);
        IPage<Menu> _p = this.page(menuPage, null);
        List<Menu> records = _p.getRecords();
        return records;
    }

    @Override
    public Menu addOneMenu(JSONObject requestJson) throws BusinessException {
        Menu menu = requestJson.toJavaObject(Menu.class);
        Menu check = this.getOne(new QueryWrapper<Menu>().eq("name", menu.getName()));
        if(check == null){
            boolean result = this.save(menu);
            return menu;
        }else{
            throw new BusinessException(CodeEnum.PARAM_ERROR.getMsg(), CodeEnum.PARAM_ERROR.getCode());
        }
    }

    @Override
    public Boolean deleteOneMenu(JSONObject requestJson) throws BusinessException {
        Integer menuId = requestJson.getInteger("id");
        if (ComUtil.isEmpty(this.getById(menuId))) {
            throw new BusinessException("菜单不存在");
        }
        if(!ComUtil.isEmpty(roleToMenuService.list(new QueryWrapper<RoleToMenu>().eq("menu_id", menuId)))){
            throw new BusinessException("删除失败，菜单已绑定角色，请先处理");
        }
        return this.removeById(menuId);
    }
}
