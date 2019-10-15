package com.start.framework.redis.util;

import java.io.IOException;
import java.util.Properties;

/**
 * @Description : 读取redis 配置文件
 */
public class RedisConfig {
	private static Properties properties = new Properties();
	//InputStream isInputStream = ReadProperties.class.getClassLoader().getResourceAsStream("config.properties"); 

	static {
		try {
			properties.load(RedisConfig.class.getResourceAsStream("/redisconf.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @Description : 获取redis 主机
	 * @return :String
	 */
	public static String getHostName() {
		return properties.getProperty("spring.redis.single.host");
	}

	/**
	 * 
	 * @Description : 获取redis 端口
	 * @return :String
	 */
	public static Integer getPort() {
		String port = properties.getProperty("spring.redis.single.port");
		if (port != null) {
			return Integer.valueOf(port);
		}
		return null;
	}
	
	
	public static String getNodes() {
		return properties.getProperty("spring.redis.cluster.nodes");
	}
	
	public static int getCommandTimeout() {
		return Integer.parseInt(properties.getProperty("spring.redis.cluster.nodes.commandTimeout"));
	}

}
