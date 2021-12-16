package com.zookao.persistence.mapper;

import com.zookao.persistence.entity.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜单表 Mapper 接口
 *
 * @author zookao
 * @since 2021-12-14
 */
@Mapper
public interface MenuMapper extends BaseMapper<Menu> {
    List<Menu> findMenuByRoleId(@Param("roleId") Integer roleId);
}
