package com.start.framwork.mongo.eneity;

import lombok.Data;

@Data
public class User {
	private String name;
	private Integer age;
	private String classId;
	private String sex;

	@Override
	public String toString() {
		return "User{" + "name='" + name + '\'' + ", age=" + age + ", classId='" + classId + '\'' + ", sex='" + sex
				+ '\'' + '}';
	}
}
