/**
 * @date 2019年8月13日
 */
package com.framemark.consumer;

import com.alibaba.fastjson.JSONObject;
import com.framemark.config.DirectConfig;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;


/**
 * 消费者
 *
 * @author liudawei
 */
@Component("DirectReceiver")
public class DirectReceiver {

    Logger logger = LoggerFactory.getLogger(DirectReceiver.class);

    @RabbitListener(queues = {DirectConfig.DIRECT_QUEUENAME_A})
    public void handlerA(JSONObject content, Channel channel, Message message) throws IOException {
        System.out.println("DIRECT_QUEUENAME_A接收到 ：" + content.toString());

        try {
            // 告诉服务器收到这条消息，已经被消费，可以在队列删除，这样以后就不会再发，否则消息服务器以为这条消息没处理掉
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            logger.error("DIRECT_QUEUENAME_A消费者失败： " + e.getMessage(), e);
        }

//        try {
//            Thread.sleep(2000);
//        } catch (Exception e) {
//
//        }
    }

    @RabbitListener(queues = {DirectConfig.DIRECT_QUEUENAME_B})
    public void handlerB(JSONObject content, Channel channel, Message message) throws IOException {
        System.out.println("DIRECT_QUEUENAME_B接收到 ：" + content.toString());

        try {
            // 告诉服务器收到这条消息，已经被消费，可以在队列删除，这样以后就不会再发，否则消息服务器以为这条消息没处理掉
            // false 为手动确认，可以在消费异常时，防止消费丢失
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            logger.error("DIRECT_QUEUENAME_B消费者失败： " + e.getMessage(), e);
        }

//        try {
//            Thread.sleep(2000);
//        } catch (Exception e) {
//
//        }
    }

}
