package com.zookao.admin.mq;

import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * User: zookao
 * Date: 2021-12-20
 * 去掉注解使用
 */
// @Component
// @EnableScheduling
public class RocketmqProducer {

    @Autowired
    private RocketMQTemplate rocketMQTemplate;

    // 秒（0~59） 例如0/5表示每5秒
    // 分（0~59）
    // 时（0~23）
    // 日（0~31）的某天，需计算
    // 月（0~11）
    // 周几（ 可填1-7 或 SUN/MON/TUE/WED/THU/FRI/SAT）
    // @Scheduled(cron = "0/5 * * * * ?")
    //或直接指定时间间隔，例如：5秒
    @Scheduled(fixedRate = 5000)
    public void syncSend() {
        for (int i = 0; i < 7; i++) {
            // 同步消息
            Message<String> message = new GenericMessage<String>("同步测试生产者" + i);
            SendResult syncSend = rocketMQTemplate.syncSend("test_producer", message);
            System.out.println(syncSend);
        }
    }

    public void asyncSend() {
        for (int i = 0; i < 7; i++) {
            // 异步消息
            Message<String> message = new GenericMessage<String>("异步测试生产者" + i);
            rocketMQTemplate.asyncSend("test_producer", message, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.println(sendResult);
                }

                @Override
                public void onException(Throwable e) {
                    System.out.println("发送失败");
                }
            });
        }
    }

    public void sendOneWay() {
        for (int i = 0; i < 7; i++) {
            // 单向发送消息
            Message<String> message = new GenericMessage<String>("测试生产者" + i);
            rocketMQTemplate.sendOneWay("test_producer", message);
            System.out.println("只发送一次");
        }
    }

    public void syncSendOrder() {
        // 发送有序消息
        String[] tags = new String[]{"TagA", "TagC", "TagD"};
        for (int i = 0; i < 7; i++) {
            // 加个时间前缀
            Message<String> message = new GenericMessage<String>("我是顺序消息" + i);
            SendResult sendResult = rocketMQTemplate.syncSendOrderly("test_producer:" + tags[i % tags.length], message, i + "");
            System.out.println(sendResult);
        }
    }

    /**
     * 发送延时消息
     * 在start版本中 延时消息一共分为18个等级分别为：1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h<br/>
     */
    public void sendDelayMsg() {
        Message<String> message = new GenericMessage<String>("测试延时消息");
        rocketMQTemplate.syncSend("test_producer", message, 3000, 5);
    }
}
