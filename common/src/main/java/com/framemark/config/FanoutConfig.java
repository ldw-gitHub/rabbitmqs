package com.framemark.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description 扇形交换机  1：N 可以把一个消息并行发布到多个队列上
 * durable  是否持久化
 * autoDelete 是否自动删除
 * @author: liudawei
 * @date: 2020/11/3 14:19
 */
@Configuration
public class FanoutConfig {
    public final static String FANOUT_EXCHANGE_NAME = "ldw.fanoutExchange";

    public final static String FANOUT_QUEUENAME_A = "fanout.A";
    public final static String FANOUT_QUEUENAME_B = "fanout.B";
    public final static String FANOUT_QUEUENAME_C = "fanout.C";

    @Bean(name = "Amessage")
    public Queue AMessage() {
        return new Queue(FANOUT_QUEUENAME_A);
    }

    @Bean(name = "Bmessage")
    public Queue BMessage() {
        return new Queue(FANOUT_QUEUENAME_B);
    }

    @Bean(name = "Cmessage")
    public Queue CMessage() {
        return new Queue(FANOUT_QUEUENAME_C);
    }

    @Bean
    FanoutExchange fanoutExchange() {
        //配置广播路由器
        return new FanoutExchange(FANOUT_EXCHANGE_NAME,true,false);
    }

    @Bean
    Binding bindingExchangeA(@Qualifier("Amessage") Queue AMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(AMessage).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeB(@Qualifier("Bmessage") Queue BMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(BMessage).to(fanoutExchange);
    }

    @Bean
    Binding bindingExchangeC(@Qualifier("Cmessage") Queue CMessage, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(CMessage).to(fanoutExchange);
    }
}
