package com.zookao.persistence.mapper;

import com.zookao.persistence.entity.UserToRole;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户角色关系表 Mapper 接口
 *
 * @author zookao
 * @since 2021-12-14
 */
@Mapper
public interface UserToRoleMapper extends BaseMapper<UserToRole> {

}
