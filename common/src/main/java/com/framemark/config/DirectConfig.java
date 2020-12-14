package com.framemark.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @description 直连交换机  1：1 类似完全匹配
 * durable  是否持久化
 * @author: liudawei
 * @date: 2020/11/3 11:55
 */
@Configuration
public class DirectConfig {

    public final static String DIRECT_QUEUENAME_A = "queue.A";
    public final static String DIRECT_QUEUENAME_B = "queue.B";

    @Bean
    public Queue QueueA() {
        return new Queue(DIRECT_QUEUENAME_A, true);
    }

    @Bean
    public Queue QueueB() {
        return new Queue(DIRECT_QUEUENAME_B, true);
    }
}
