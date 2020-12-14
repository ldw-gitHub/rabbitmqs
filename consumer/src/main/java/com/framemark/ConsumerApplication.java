/**
 * 
 * @date 2019年8月8日
 */
package com.framemark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动
 * @author liudawei
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ConsumerApplication {

	/**
	 * 保证幂等性(重复消费)
	 * 要保证消息的幂等性，这个要结合业务的类型来进行处理。下面提供几个思路供参考：
	 * （1）、可在内存中维护一个set，只要从消息队列里面获取到一个消息，先查询这个消息在不在set里面，如果在表示已消费过，直接丢弃；如果不在，则在消费后将其加入set当中。
	 * （2）、如何要写数据库，可以拿唯一键先去数据库查询一下，如果不存在在写，如果存在直接更新或者丢弃消息。
	 * （3）、如果是写redis那没有问题，每次都是set，天然的幂等性。
	 * （4）、让生产者发送消息时，每条消息加一个全局的唯一id，然后消费时，将该id保存到redis里面。消费时先去redis里面查一下有么有，没有再消费。
	 * （5）、数据库操作可以设置唯一键，防止重复数据的插入，这样插入只会报错而不会插入重复数据。
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(ConsumerApplication.class, args);
	}

}
