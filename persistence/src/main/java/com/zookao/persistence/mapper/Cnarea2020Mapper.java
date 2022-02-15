package com.zookao.persistence.mapper;

import com.zookao.persistence.entity.Cnarea2020;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 中国行政地区表 Mapper 接口
 *
 * @author zookao
 * @since 2022-02-15
 */
@Mapper
public interface Cnarea2020Mapper extends BaseMapper<Cnarea2020> {
    @Select("select * from cnarea_2020 where level=0")
    List<Cnarea2020> selectAllLevel0();

    @Select("select * from cnarea_2020 where parent_code=#{parent}")
    List<Cnarea2020> getByParent(Long parent);
}
