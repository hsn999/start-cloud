package com.start.framwork.mongo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.start.framwork.mongo.eneity.Order;
import com.start.framwork.mongo.eneity.User;
import com.start.framwork.mongo.service.impl.MongoService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MongoTest {
	@Autowired
	private MongoService mongoService;

	@Test
	public void saveTest() {
		User user = new User();
		user.setName("张三");
		user.setAge(18);
		user.setClassId("1");
		user.setSex("M");
		mongoService.mongoSaveUser(user);

		user.setName("阿Y");
		user.setAge(17);
		user.setClassId("2");
		user.setSex("F");
		mongoService.mongoSaveUser(user);
		
		user.setName("阿Z");
		user.setAge(17);
		user.setClassId("2");
		user.setSex("F");
		mongoService.mongoSaveUser(user);
		
		user.setName("阿K");
		user.setAge(17);
		user.setClassId("3");
		user.setSex("F");
		mongoService.mongoSaveUser(user);
		
		
		user.setName("阿P");
		user.setAge(167);
		user.setClassId("5");
		user.setSex("F");
		mongoService.mongoSaveUser(user);

		Order order = new Order();
		order.setId("1");
		order.setName("test");
		order.setProductNmae("product-001");
		order.setQuantity(2);
		mongoService.mongoSaveOrder(order);
	}
}
