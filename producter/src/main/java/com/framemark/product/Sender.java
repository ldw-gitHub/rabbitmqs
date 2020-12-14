/**
 * @date 2019年8月9日
 */
package com.framemark.product;

import com.framemark.config.DirectConfig;
import com.framemark.config.FanoutConfig;
import com.framemark.config.TopicConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 创建生产者
 * 通过注入AmqpTemplate接口的实例来实现消息的发送，AmqpTemplate接口定义了一套针对AMQP协议的基础操作。
 * 在Spring Boot中会根据配置来注入其具体实现。
 *
 * @author liudawei
 */
@Component
public class Sender {

    Logger logger = LoggerFactory.getLogger(Sender.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * exchange有多个种类：direct(直连交换机)，fanout（扇形交换机），topic（主题交换机），header（首端交换机）（非路由键匹配，功  能和direct类似，很少用）。
     * 前三种类似集合对应关系那样，（direct）1:1,（fanout）1：N,（topic）N:1
     *
     * direct： 1:1类似完全匹配
     *
     * fanout：1：N  可以把一个消息并行发布到多个队列上去，简单的说就是，当多个队列绑定到fanout的交换器,那么交换器一次性拷贝多个消息分别发送到绑定的队列上，
     * 每个队列有这个消息的副本。
     *
     * ps：这个可以在业务上实现并行处理多个任务，比如，用户上传图片功能，当消息到达交换器上，它可以同时路由到积分增加队列和其它队列上，达到并行处理的目的，
     * 并且易扩展，以后有什么并行任务的时候，直接绑定到fanout交换器不需求改动之前的代码。
     *
     * topic   N:1 ，多个交换器可以路由消息到同一个队列。根据模糊匹配，比如一个队列的routing key 为*.test ，那么凡是到达交换器的消息中的routing key
     * 后缀.test都被路由到这个队列上。
     */

    /**
     * @description: 普通字符串发送
     * @author: liudawei
     * @date: 2020/11/2 17:41
     * @param:
     * @return: void
     */
    public void send(String msg) {
        for (int i = 0; i < 100; i++) {
            String content = "hello: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            //direct类型
            rabbitTemplate.convertAndSend(DirectConfig.DIRECT_QUEUENAME_A, content);
            // topic类型
            rabbitTemplate.convertAndSend(TopicConfig.TOPIC_EXCHANGE_NAME, "topic.message", content);
            // fanout类型
            rabbitTemplate.convertAndSend(FanoutConfig.FANOUT_EXCHANGE_NAME, "", content);
        }
    }

}
