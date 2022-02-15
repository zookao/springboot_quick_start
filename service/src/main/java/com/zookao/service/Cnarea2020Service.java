package com.zookao.service;

import com.zookao.persistence.entity.Cnarea2020;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 中国行政地区表 服务类
 *
 * @author zookao
 * @since 2022-02-15
 */
public interface Cnarea2020Service extends IService<Cnarea2020> {
    public List<Cnarea2020> allLevel0();
    public List<Cnarea2020> getByParent(Long parent);
}
