package com.start.client.demo.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.start.client.demo.config.RocketMQProperties;
import com.start.framework.rocketmq.producer.SyncProducer;
import com.start.framwork.kafak.producer.MyKafkaProducer;
import com.start.framwork.mongo.eneity.StorageChange;
import com.start.framwork.mongo.service.impl.MongoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.io.UnsupportedEncodingException;
import java.util.Date;

@Configuration
@EnableWebMvc
@RestController
@Slf4j
@Api(value="/test1", tags="测试接口模块")
public class TService {

	@Autowired
	SyncProducer syncProducer;

	@Autowired
	RocketMQProperties rocketMQProperties;

	@Autowired
	private MyKafkaProducer kafkaProducer;

	@Autowired
	private MongoService mongoService;

	
	@ApiOperation(value="xxxx", notes = "names")
	@RequestMapping("/test")
	public String printDate(@RequestParam(name = "username", required = false) String username) {
		log.info("req: username={}", username);

		// 异步通知第三方系统
		try {
			syncProducer.send(rocketMQProperties.topic, rocketMQProperties.tag, username.toString());
		} catch (UnsupportedEncodingException | InterruptedException | RemotingException | MQClientException
				| MQBrokerException e) { // TODO Auto-generated catch block
			e.printStackTrace();
		}

		// kafka
		kafkaProducer.send("test", username.toString());

		StorageChange storageChange = new StorageChange();
		storageChange.setId("109".toString());
		storageChange.setProductNmae("p0012345");
		storageChange.setQuantity(1);
		storageChange.setType("deduct");

		// mongo
		mongoService.mongoSaveStorageChange(storageChange);

		return new Date().toString();
	}
}
