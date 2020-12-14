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
		SpringApplication.run(ProducterApplication.class, args);
	}

}
