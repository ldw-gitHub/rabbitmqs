package com.framemark.consumer;

import com.framemark.config.FanoutConfig;
import com.framemark.config.TopicConfig;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @description
 * @author: liudawei
 * @date: 2020/11/5 10:41
 */
@Component("TopicReceiver")
public class TopicReceiver {

    Logger logger = LoggerFactory.getLogger(TopicReceiver.class);

    @RabbitListener(queues = {TopicConfig.TOPIC_QUEUENAME_A})
    public void handlerA(String content, Channel channel, Message message) throws IOException {
        System.out.println("TOPIC_QUEUENAME_A接收到 ：" + content);

        try {
            // 告诉服务器收到这条消息，已经被消费，可以在队列删除，这样以后就不会再发，否则消息服务器以为这条消息没处理掉
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            logger.error("TOPIC_QUEUENAME_A消费者失败： " + e.getMessage(), e);
        }

    }

    @RabbitListener(queues = {TopicConfig.TOPIC_QUEUENAME_B})
    public void handlerB(String content, Channel channel, Message message) throws IOException {
        System.out.println("TOPIC_QUEUENAME_B接收到 ：" + content);

        try {
            // 告诉服务器收到这条消息，已经被消费，可以在队列删除，这样以后就不会再发，否则消息服务器以为这条消息没处理掉
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            logger.error("TOPIC_QUEUENAME_B消费者失败： " + e.getMessage(), e);
        }

    }


}
