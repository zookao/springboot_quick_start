package com.zookao.admin.mq;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.stereotype.Component;

/**
 * 注意：Consumer里抛出异常才会重试，所以使用者不要把Consumer里的整个代码try-catch
 * User: zookao
 * Date: 2021-12-20
 */
@Slf4j
@Component
@RocketMQMessageListener(topic = "test_producer", consumerGroup = "admin")
public class RocketmqConsumer implements RocketMQListener<String> {

    @Override
    public void onMessage(String message) {
        log.info("[onMessage][线程编号:{} 消息内容：{}]", Thread.currentThread().getId(), message);
    }
}
