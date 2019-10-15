package com.start.framework.redis.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.start.framework.redis.redis.util.ICacheUtil;
import com.start.framework.redis.util.SpringUtil;

import net.sf.json.JSONObject;

@RestController
@Configuration
@DependsOn("springUtil")
public class UserController {/*

	
	 * @Autowired SpringUtil SpringUtil;
	 

	
	 * @Autowired
	 * 
	 * @Qualifier("jedisClusterUtil")
	 
	private ICacheUtil cacheUtil;

	private static ApplicationContext applicationContext;

	public UserController(@Value("${model.name}") String name) {
		System.out.println("--------------+++++++++++++" + name);
		// applicationContext.getBean(name);
		cacheUtil = (ICacheUtil) SpringUtil.getBean(name);
		System.out.println("init cacheUtil:" + name);
	}

	
	 * @GetMapping("/{id}") public User findById(@PathVariable Long id) { User
	 * findOne = this.userRepository.findOne(id); return findOne; }
	 

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public void register(@RequestParam("userInfo") Map<String, String> userInfo) throws Exception {
		// logger.info("==== register {} ", JSON.toJSONString(userInfo));
		System.out.println(userInfo);
	}

	@RequestMapping("/addToQueue")
	void addToQueue(@RequestParam("key") String key, @RequestParam("map") String map) {
		Map map1 = JSON.parseObject(map);
		cacheUtil.addToQueue(key, map1);

	}

	@RequestMapping("/getFromQueue")
	Map<String, String> getFromQueue(@RequestParam("key") String key) {
		Map<String, String> map = cacheUtil.getFromQueue(key);
		return map;
	}

	@RequestMapping("/getMapByKey")
	Map<String, String> getMapByKey(@RequestParam("key") String key) throws Exception {
		Map<String, String> map = cacheUtil.getMapByKey(key);
		return map;
	}

	@RequestMapping("/checkExists")
	Boolean checkExists(@RequestParam("key") String key) throws Exception {
		Boolean result = cacheUtil.checkExists(key);
		return result;
	}

	@RequestMapping("/checkListMember")
	Boolean checkListMember(@RequestParam("key") String key, @RequestParam("value") String value) throws Exception {
		System.out.println("checkListMember---user phone----:" + value);
		Boolean result = cacheUtil.checkListMember(key, value);
		return result;
	}

	@RequestMapping("/getKeys")
	Set<String> getKeys(@RequestParam("key") String key) {
		Set<String> set = cacheUtil.getKeys(key);
		return set;
	}

	@RequestMapping("/getSetByKey")
	Set<String> getSetByKey(@RequestParam("key") String key) throws Exception {
		Set<String> set = cacheUtil.getSetByKey(key);
		return set;
	}

	@RequestMapping("/hset")
	void hset(@RequestParam("key") String key, @RequestParam("field") String field,
			@RequestParam("value") String value) {
		cacheUtil.hset(key, field, value);
	}

	@RequestMapping("/removeFromSet")
	void removeFromSet(@RequestParam("key") String key, @RequestParam("value") String value) {
		cacheUtil.removeFromSet(key, value);

	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/setMapByKey")
	void setMapByKey(@RequestParam("key") String key, @RequestParam("hash") String hash) throws Exception {
		System.out.println("hash------------" + hash);
		JSONObject jsonObject = JSONObject.fromObject(hash);
		// Map<String, String> tmp = JSONObject.fromObject(jsonObject);
		Map<String, String> tmp = new HashMap<String, String>();
		jsonObject.forEach((key1, value) -> {
			if (value instanceof JSONObject) {
				System.out.println("setMapByKey不可包含JSONObject");
			} else {
				tmp.put((String) key1, (String) value);
			}
		});

		// Map<String, String> map = yy(hash);
		System.out.println("ok-----------------" + tmp.toString());
		// Map<String, String> map1 = new HashMap<String,String>();
		// map1.putAll(map);

		
		 * Map map = new HashMap<String,String>(); map.put("order", tmp.get("order"));
		 * map.put("phone", tmp.get("phone").toString()); map.put("username",
		 * tmp.get("username").toString()); map.put("email",
		 * tmp.get("email").toString()); map.put("score", tmp.get("score").toString());
		 * map.put("status", "dealing"); // 处理完为 done map.put("notifyUrl",
		 * tmp.get("notifyUrl").toString()); // 处理完为 done map.put("orderType",
		 * tmp.get("orderType").toString()); map.put("orderTime",
		 * tmp.get("orderTime").toString()); map.put("appId",
		 * tmp.get("appId").toString());
		 
		cacheUtil.setMapByKey(key, tmp);
	}

	@RequestMapping("/setSetByKey")
	void setSetByKey(@RequestParam("key") String key, @RequestParam("value") String value) {
		cacheUtil.setSetByKey(key, value);
	}

	@RequestMapping("/getStringByKey")
	String getStringByKey(@RequestParam("key") String key) {
		String value = cacheUtil.getStringByKey(key);
		return value;
	}

	@RequestMapping("/setStringByKey")
	void setStringByKey(@RequestParam("key") String key, @RequestParam("value") String value) {
		cacheUtil.setStringByKey(key, value);

	}

	@RequestMapping("/setPointMapByKey")
	void setPointMapByKey(@RequestParam("key") String key, @RequestParam("hash") String hash) throws Exception {
		Map tmp = JSON.parseObject(hash);

		Map map = tmp;
		// Map map = new HashMap<String, String>();
		// BeanUtils.copyProperties(tmp, map);
		// map.put("appId", tmp.get("appId").toString());
		// map.put("uuid", tmp.get("uuid").toString());
		// map.put("txid", tmp.get("txid").toString());
		// map.put("status", tmp.get("status").toString());
		// map.put("time", tmp.get("time").toString());
		// map.put("cash", tmp.get("cash").toString());
		// map.put("point", tmp.get("point").toString());
		// map.put("desc", tmp.get("desc").toString());
		cacheUtil.setMapByKey(key, map);
	}

	@RequestMapping("/setMapByKeyExtend")
	void setMapByKeyExtend(@RequestParam("key") String key, @RequestParam("hash") String hash) throws Exception {
		System.out.println("hash------------" + hash);
		Map tmp = JSON.parseObject(hash);

		Map map = new HashMap<String, String>();
		map.put("uuid", tmp.get("uuid").toString());
		map.put("count", tmp.get("count").toString());
		cacheUtil.setMapByKey(key, map);
	}

	@RequestMapping("/delKey")
	void delKey(@RequestParam("key") String key) {
		cacheUtil.del(key);
	}

	@RequestMapping("/getDistLock")
	boolean distLock(@RequestParam("lockKey") String lockKey, @RequestParam("requestId") String requestId,
			@RequestParam("expireTime") int expireTime) {
		return cacheUtil.tryGetDistributedLock(lockKey, requestId, expireTime);
	}

	@RequestMapping("/reGetDistLock")
	boolean reDistLock(@RequestParam("lockKey") String lockKey, @RequestParam("requestId") String requestId,
			@RequestParam("expireTime") int expireTime) {
		return cacheUtil.reGetDistributedLock(lockKey, requestId, expireTime);
	}

	@RequestMapping("/releaseDistLock")
	boolean releaseDistLock(@RequestParam("lockKey") String lockKey, @RequestParam("requestId") String requestId) {
		return cacheUtil.releaseDistributedLock(lockKey, requestId);
	}

	private Map<String, String> yy(String x) {
		String str1 = x.replaceAll("\\{|\\}", "");// singInfo是一个map
													// toString后的字符串。
		String str2 = str1.replaceAll(" ", "");
		String str3 = str2.replaceAll(",", "&");

		Map<String, String> map = null;
		if ((null != str3) && (!"".equals(str3.trim()))) {
			String[] resArray = str3.split("&");
			if (0 != resArray.length) {
				map = new HashMap(resArray.length);
				for (String arrayStr : resArray) {
					if ((null != arrayStr) && (!"".equals(arrayStr.trim()))) {
						int index = arrayStr.indexOf("=");
						if (-1 != index) {
							map.put(arrayStr.substring(0, index), arrayStr.substring(index + 1));
						}
					}
				}
			}
		}
		return map;

	}

*/}
