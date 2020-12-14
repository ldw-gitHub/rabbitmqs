package com.framemark.consumer;

import com.framemark.config.TopicDesignConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @description
 * @author: liudawei
 * @date: 2020/11/9 16:01
 */
@Component("TopicDesignReceiver")
public class TopicDesignReceiver {

    Logger logger = LoggerFactory.getLogger(TopicDesignReceiver.class);

    @Autowired
    RabbitTemplate rabbitTemplate;
    // 获取所有listener container

    @Autowired
    RabbitListenerEndpointRegistry registry;
    /**
     * 所有的队列监听容器MAP
     */
    private final Map<String, SimpleMessageListenerContainer> allQueue2ContainerMap = new ConcurrentHashMap<>();

    public void receiverMsg(String clientId) {
        String queueName = TopicDesignConfig.TOPIC_DESIGN_QUEUE_NAME + clientId;
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(rabbitTemplate.getConnectionFactory());
        container.setQueueNames(queueName);
        container.setExposeListenerChannel(true);
        //设置每个消费者获取的最大的消息数量
//        container.setPrefetchCount(5);
        //消费者个数
//        container.setConcurrentConsumers(1);
        //设置确认模式为手工确认
        container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        //监听处理类
        container.setMessageListener((ChannelAwareMessageListener) (message, channel) -> {
            try {
                logger.info(new String(message.getBody()));
                throw new Exception("测试报错！！！");
                // 告诉服务器收到这条消息，已经被消费，可以在队列删除，这样以后就不会再发，否则消息服务器以为这条消息没处理掉
//                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } catch (Exception e) {
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
                logger.error(queueName + "消费者失败： " + e.getMessage(), e);
            }
        });


    }

}
