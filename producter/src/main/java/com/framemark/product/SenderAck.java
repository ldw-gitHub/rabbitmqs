package com.framemark.product;

import com.alibaba.fastjson.JSONObject;
import com.framemark.config.DirectConfig;
import com.framemark.config.FanoutConfig;
import com.framemark.config.TopicConfig;
import com.framemark.config.TopicDesignConfig;
import com.framemark.model.Girlfriend;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @description 发送多种类型数据 and ack，发送初始化队列
 * @author: liudawei
 * @date: 2020/10/28 14:21
 */
@Component
public class SenderAck implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback {

    Logger logger = LoggerFactory.getLogger(SenderAck.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private TopicDesignConfig topicDesignConfig;
    @Autowired
    private TopicExchange exchangeDesign;


    /**
     * direct： 直接交换机  1：1 完全匹配模型，把消息交给所有绑定到交换机的队列
     * 使用场景：不同的消息被不同的队列消费
     * <p>
     * fanout： 扇形分叉 广播交换机 1：N 把消息交给符合指定routing key 的队列
     * <p>
     * topic： 通配符，把消息交给符合routing pattern（路由模式）的队列
     * <p>
     * 自动ACK：消息一旦被接收，消费者自动发送ACK
     * 手动ACK：消息接收后，不会发送ACK，需要手动调用
     */

    public void send(String msg) {

        // 设置回调对象
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnCallback(this);

        //构建回调返回的数据
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());


        // 字符串 Fanout.Receiver1接收到 ：111
        if (StringUtils.isEmpty(msg)) {
            msg = "你好，现在是 ： " + new Date() + ";";
        }

        rabbitTemplate.convertAndSend(TopicConfig.TOPIC_EXCHANGE_NAME, TopicConfig.TOPIC_QUEUENAME_A, msg + "TOPIC_QUEUENAME_A", correlationData);
        rabbitTemplate.convertAndSend(TopicConfig.TOPIC_EXCHANGE_NAME, TopicConfig.TOPIC_QUEUENAME_B, msg + "TOPIC_QUEUENAME_B", correlationData);

        // JSONObject  Fanout.Receiver1接收到 ：{"date":1604311799408,"name":"ldw","age":"30"}
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "ldw-json");
        jsonObject.put("age", "30");
        jsonObject.put("date", new Date());

        rabbitTemplate.convertAndSend(DirectConfig.DIRECT_QUEUENAME_A, jsonObject, correlationData);

        // Map  Fanout.Receiver1接收到 ：{date=Mon Nov 02 18:27:03 CST 2020, name=ldw, age=30}
        Map<String, Object> params = new HashMap<>();
        params.put("name", "ldw-map");
        params.put("age", "30");
        params.put("date", new Date());

        rabbitTemplate.convertAndSend(DirectConfig.DIRECT_QUEUENAME_B, params, correlationData);

        // model  Fanout.Receiver1接收到 ：Girlfriend{name='范冰冰', age=13, date=Mon Nov 02 18:23:35 CST 2020}
        // 类必须序列化，消费者类id一直才能反序列化
        Girlfriend girlfriend = new Girlfriend();
        girlfriend.setAge(13);
        girlfriend.setDate(new Date());
        girlfriend.setName("范冰冰");

        rabbitTemplate.convertAndSend(FanoutConfig.FANOUT_EXCHANGE_NAME, "", girlfriend, correlationData);

    }


    public void sendDesign(String msg) throws IOException {
        // 字符串 Fanout.Receiver1接收到 ：111
        if (StringUtils.isEmpty(msg)) {
            msg = "你好，现在是 ： " + new Date() + ";";
        }
        // 设置回调对象
//        rabbitTemplate.setConfirmCallback(this);
//        rabbitTemplate.setReturnCallback(this);
        //构建回调返回的数据

        if (exchangeDesign != null) {
            ConnectionFactory connectionFactory = rabbitTemplate.getConnectionFactory();
            for (int i = 0; i < 5; i++) {
                CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
                String routingKey = TopicDesignConfig.TOPIC_DESIGN_QUEUE_NAME + i;
                if (TopicDesignConfig.TOPIC_QUEUES.get(routingKey) == null) {
                    topicDesignConfig.bindingExchangeMessage(routingKey, connectionFactory);
                }
                rabbitTemplate.convertAndSend(exchangeDesign.getName(), routingKey, msg + "----" + routingKey, correlationData);
            }
        }

    }


    /**
     * 消息回调确认方法
     * 如果消息没有到exchange，则confirm回调，ack=false
     * 如果消息到达exchange，则confirm回调，ack=true
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        logger.info("confirm--message: 回调消息ID为： " + correlationData.getId());
        if (b) {
            logger.info("confirm--message: 消息发送成功");
        } else {
            logger.info("confirm--message: 消息发送失败" + s);
        }
    }

    /**
     * exchange到queue成功，则不回调return
     * exchange到queue失败，则回调return（需要设置spring.rabbitmq.template.mandatory=true，否则不回调，消息丢失）
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        logger.info("return--message:" + new String(message.getBody()) + ",replyCode:" + replyCode
                + ",replyText:" + replyText + ",exchange:" + exchange + ",routingKey:" + routingKey);

    }
}
