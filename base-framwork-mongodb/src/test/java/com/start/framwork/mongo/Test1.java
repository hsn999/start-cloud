package com.start.framwork.mongo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.start.framwork.mongo.eneity.User;
import com.start.framwork.mongo.service.impl.MongoService;



@RunWith(SpringRunner.class)
@SpringBootTest
public class Test1 {
	@Autowired
	private MongoService mongoService;

	
	@Test
	public void selectTest() {
		System.out.println(mongoService.seleteUser("name", "张三"));
	}
	
	@Test
	public void pselectTest() {
		System.out.println(mongoService.pagingSeleteUser(2, 2));
	}

	@Test
	public void updateTest() throws Exception {
		User user = new User();
		
		user.setName("阿Y");
		user.setAge(179);
		user.setClassId("2");
		user.setSex("F");
				
		mongoService.updateUser("name","阿Y",user);
	}

}
