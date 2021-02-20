/**
 * 
 * @date 2019年8月9日
 */
package com.framemark;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 生产者
 * @author liudawei
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class ProducterApplication {
	
	public static void main(String[] args) {
		/**
		 * Springboot整合Elasticsearch 在项目启动前设置一下的属性，防止报错
		 * 解决netty冲突后初始化client时还会抛出异常
		 * java.lang.IllegalStateException: availableProcessors is already set to [4], rejecting [4]
		 */
		System.setProperty("es.set.netty.runtime.available.processors", "false");
		SpringApplication.run(ProducterApplication.class, args);
	}

}
