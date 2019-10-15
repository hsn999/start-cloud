package com.start.framework.redis.redis.util.cluster;

import java.util.HashSet;
import java.util.Set;
import com.start.framework.redis.util.RedisConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
/**
 * @author <a href="h_sn999@126.com">hsn</a>
 * @Discription
 * @Data 2017-9-12
 * @Version 1.0.0
 */

//@Configuration
public class JedisClusterConfig{   


    public static JedisCluster getJedisCluster() {
        String[] serverArray = RedisConfig.getNodes().split(",");//获取服务器数组(这里要相信自己的输入，所以没有考虑空指针问题)
        Set<HostAndPort> nodes = new HashSet<>();

        for (String ipPort : serverArray) {
            String[] ipPortPair = ipPort.split(":");
            nodes.add(new HostAndPort(ipPortPair[0].trim(), Integer.valueOf(ipPortPair[1].trim())));
        }

        return new JedisCluster(nodes, RedisConfig.getCommandTimeout());
    } 
    
    public static String getName(){
		return null;
    	
    }

}