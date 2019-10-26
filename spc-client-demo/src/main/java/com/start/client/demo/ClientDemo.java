package com.start.client.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import springfox.documentation.swagger2.annotations.EnableSwagger2;


//@EnableDiscoveryClient
@EnableFeignClients
@EnableSwagger2
@SpringBootApplication(scanBasePackages = {"com.start"}, exclude = DataSourceAutoConfiguration.class)
public class ClientDemo
{
    public static void main( String[] args )
    {
    	SpringApplication.run(ClientDemo.class, args);
    	System.out.println( "Hello World!" );
    }
}
