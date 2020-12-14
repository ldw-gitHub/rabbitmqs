package com.framemark.config;

import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @description 通配符交换机  N：1 多个交换器可以路由消息到同一个队列
 * 动态添加队列
 * durable  是否持久化
 * autoDelete 是否自动删除
 * @author: liudawei
 * @date: 2020/11/3 14:03
 */
@Configuration
public class TopicDesignConfig {

    public final static String TOPIC_EXCHANGE_NAME = "ldw.TopicExchange.design";

    public static String TOPIC_DESIGN_QUEUE_NAME = "topic.design";

    public static Map<String, String> TOPIC_QUEUES = new HashMap<>();


    @Bean(name = "exchangeDesign")
    public TopicExchange exchange() {
        return new TopicExchange(TOPIC_EXCHANGE_NAME, true, false);
    }

    public void bindingExchangeMessage(String routingKey, ConnectionFactory connectionFactory) throws IOException {
        connectionFactory.createConnection().createChannel(false).queueDeclare(routingKey, true, false, false, null);
        connectionFactory.createConnection().createChannel(false).queueBind(routingKey, TOPIC_EXCHANGE_NAME, routingKey);
        TOPIC_QUEUES.put(routingKey, TOPIC_EXCHANGE_NAME);
    }


}
