package com.framemark.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description 通配符交换机  N：1 多个交换器可以路由消息到同一个队列
 * durable  是否持久化
 * autoDelete 是否自动删除
 * @author: liudawei
 * @date: 2020/11/3 14:03
 */
@Configuration
public class TopicConfig {

    public final static String TOPIC_EXCHANGE_NAME = "ldw.TopicExchange";
    public final static String TOPIC_QUEUENAME_A = "topic.insert";
    public final static String TOPIC_QUEUENAME_B = "topic.update";

    @Bean(name = "insert")
    public Queue queueInsert() {
        return new Queue(TOPIC_QUEUENAME_A);
    }

    @Bean(name = "update")
    public Queue queueUpdate() {
        return new Queue(TOPIC_QUEUENAME_B);
    }

    @Bean(name = "exchange")
    public TopicExchange exchange() {
        return new TopicExchange(TOPIC_EXCHANGE_NAME, true, false);
    }

    @Bean
    Binding bindingExchangeMessage(@Qualifier("insert") Queue queueMessage, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessage).to(exchange).with(TOPIC_QUEUENAME_A);
    }

    @Bean
    Binding bindingExchangeMessages(@Qualifier("update") Queue queueMessages, TopicExchange exchange) {
        return BindingBuilder.bind(queueMessages).to(exchange).with(TOPIC_QUEUENAME_B);
    }


}
