package com.zookao.service.impl;

import com.zookao.persistence.entity.OperationLog;
import com.zookao.persistence.mapper.OperationLogMapper;
import com.zookao.service.OperationLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 操作日志表 服务实现类
 *
 * @author zookao
 * @since 2021-12-14
 */
@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLog> implements OperationLogService {

}
