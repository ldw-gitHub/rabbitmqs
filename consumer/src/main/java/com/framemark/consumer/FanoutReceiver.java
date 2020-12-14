/**
 * @date 2019年8月13日
 */
package com.framemark.consumer;

import com.framemark.config.FanoutConfig;
import com.framemark.model.Girlfriend;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

/**
 * 消费者
 *
 * @author liudawei
 */
@Component("FanoutReceiver")
public class FanoutReceiver {

    Logger logger = LoggerFactory.getLogger(FanoutReceiver.class);

    @RabbitListener(queues = {FanoutConfig.FANOUT_QUEUENAME_A})
    public void handlerA(Girlfriend content, Channel channel, Message message) throws IOException {
        System.out.println("FANOUT_QUEUENAME_A接收到 ：" + content.toString());

        try {
            // 告诉服务器收到这条消息，已经被消费，可以在队列删除，这样以后就不会再发，否则消息服务器以为这条消息没处理掉
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            logger.error("FANOUT_QUEUENAME_A消费者失败： " + e.getMessage(), e);
        }

    }

    @RabbitListener(queues = {FanoutConfig.FANOUT_QUEUENAME_B})
    public void handlerB(Girlfriend content, Channel channel, Message message) {
        System.out.println("FANOUT_QUEUENAME_B接收到 ：" + content.toString());

        try {
            // 告诉服务器收到这条消息，已经被消费，可以在队列删除，这样以后就不会再发，否则消息服务器以为这条消息没处理掉
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            logger.error("FANOUT_QUEUENAME_B消费者失败： " + e.getMessage(), e);
        }

    }


    @RabbitListener(queues = {FanoutConfig.FANOUT_QUEUENAME_C})
    public void handlerC(Girlfriend content, Channel channel, Message message) throws IOException {
        System.out.println("FANOUT_QUEUENAME_C接收到 ：" + content);

        try {
            // 告诉服务器收到这条消息，已经被消费，可以在队列删除，这样以后就不会再发，否则消息服务器以为这条消息没处理掉
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            logger.error("FANOUT_QUEUENAME_C消费者失败： " + e.getMessage(), e);
        }

    }

}
