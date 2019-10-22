package com.start.zuul.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * Spring boot app.
 *
 * @author shuaicj 2017/10/18
 */
@SpringBootApplication
@EnableZuulProxy
public class ZuulGateway {

    public static void main(String[] args) {

        SpringApplication.run(ZuulGateway.class, args);
    }
}
