package com.zookao.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zookao.persistence.entity.Cnarea2020;
import com.zookao.persistence.mapper.Cnarea2020Mapper;
import com.zookao.service.Cnarea2020Service;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 中国行政地区表 服务实现类
 *
 * @author zookao
 * @since 2022-02-15
 */
@Service
public class Cnarea2020ServiceImpl extends ServiceImpl<Cnarea2020Mapper, Cnarea2020> implements Cnarea2020Service {

    @Override
    public List<Cnarea2020> allLevel0() {
        return this.baseMapper.selectAllLevel0();
    }

    @Override
    public List<Cnarea2020> getByParent(Long parent) {
        return this.baseMapper.getByParent(parent);
    }
}
