package com.zookao.persistence.mapper;

import com.zookao.persistence.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色表 Mapper 接口
 *
 * @author zookao
 * @since 2021-12-14
 */
@Mapper
public interface RoleMapper extends BaseMapper<Role> {

}
