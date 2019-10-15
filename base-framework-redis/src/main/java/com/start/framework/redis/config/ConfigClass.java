package com.start.framework.redis.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import com.start.framework.redis.redis.util.ICacheUtil;
import com.start.framework.redis.util.SpringUtil;
 
/**
 * @author <a href="h_sn999@126.com">hsn</a>
 * @Discription
 * @Data 2017-9-12
 * @Version 1.0.0
 */

/**
 *classpath路径：locations={"classpath:application-bean1.xml","classpath:application-bean2.xml"}
 * file路径： locations ={"file:d:/test/application-bean1.xml"};
 */
@Configuration
//@ImportResource(locations={"classpath:application-bean.xml"})
//@ImportResource(locations={"file:d:/test/application-bean1.xml"})
public class ConfigClass {
	
	@Value("${model.name}") 
	String name;	
	
	@Bean
	public ICacheUtil cacheUtil() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		System.out.println("init   " + name +"-----------");
		Class<?> c=Class.forName(name);		
		return (ICacheUtil)c.newInstance();
	}

 
}