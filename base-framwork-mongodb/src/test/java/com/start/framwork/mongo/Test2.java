package com.start.framwork.mongo;

import com.start.framwork.mongo.eneity.User;
import com.start.framwork.mongo.util.MyClassUtil;

public class Test2 {

	public static void main(String[] args) throws IllegalAccessException {
		// TODO Auto-generated method stub

		User user = new User();
		user.setName("小明11");
		user.setAge(18);
		user.setClassId("6");
		user.setSex("M");

		String[] field = MyClassUtil.getFieldName(user);
		//System.out.println(field.toString());
		
		for(int j = 0; j < field.length; j++) {
		
		   Object v = MyClassUtil.getFieldValueByName(field[j],user);
		   
		   System.out.println(field[j]+"---"+v);
		}

	}

}
