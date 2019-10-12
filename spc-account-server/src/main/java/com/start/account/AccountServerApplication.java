package com.start.account;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 账户服务
 * 
 * @author
 */
@SpringBootApplication(scanBasePackages = {"com.start"}, exclude = DataSourceAutoConfiguration.class)
@MapperScan("com.start.account.dao")
@EnableDiscoveryClient
@EnableSwagger2
public class AccountServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountServerApplication.class, args);
	}

}
