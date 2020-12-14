/*
package com.framemark.service;

import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.stereotype.Service;

*/
/**
 * @description
 * @author: liudawei
 * @date: 2020/11/13 17:51
 *//*

@Service
public class HandleService implements ChannelAwareMessageListener {
    private static final Logger logger = LoggerFactory.getLogger(HandleService.class);

    */
/**
     * @param
     * 1、处理成功，这种时候用basicAck确认消息；
     * 2、可重试的处理失败，这时候用basicNack将消息重新入列；
     *
     * 3、不可重试的处理失败，这时候使用basicNack将消息丢弃。
     *    basicNack(long deliveryTag, boolean multiple, boolean requeue)
     *    deliveryTag:该消息的index
     *    multiple：是否批量.true:将一次性拒绝所有小于deliveryTag的消息。
     *    requeue：被拒绝的是否重新入队列
     *//*

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        byte[] body = message.getBody();
        logger.info("接收到消息:" + new String(body));
        JSONObject jsonObject = null;
        try {
            jsonObject = JSONObject.parseObject(new String(body));
            if (true) {
                logger.info("消息消费成功");
                //确认消息消费成功
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } else {          //消费失败
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            }
        } catch (JSONException e) {
            //消息丢弃
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
            logger.error("This message:" + jsonObject + " conversion JSON error ");
        }
    }
}*/
