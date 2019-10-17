package com.start.storage.service;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.start.framework.rocketmq.producer.SyncProducer;
import com.start.framwork.kafak.producer.MyKafkaProducer;
import com.start.storage.config.RocketMQProperties;
import com.start.storage.entity.Storage;
import com.start.storage.repository.StorageDAO;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;

/**
 * Program Name: springcloud-nacos-seata
 * <p>
 * Description:
 * <p>
 *
 */
@Service
public class StorageService {

    @Resource
    private StorageDAO storageDAO;
    
    @Autowired
    SyncProducer syncProducer;
    
    @Autowired
    RocketMQProperties rocketMQProperties;
    
    @Autowired
    private MyKafkaProducer kafkaProducer;

    /**
     * 减库存
     * 
     * @param commodityCode
     * @param count
     */
    @Transactional(rollbackFor = Exception.class)
    public void deduct(String commodityCode, int count) {
        if (commodityCode.equals("product-2")) {
            throw new RuntimeException("异常:模拟业务异常:Storage branch exception");
        }

        QueryWrapper<Storage> wrapper = new QueryWrapper<>();
        wrapper.setEntity(new Storage().setCommodityCode(commodityCode));
        Storage storage = storageDAO.selectOne(wrapper);
        storage.setCount(storage.getCount() - count);

        storageDAO.updateById(storage);
        
        //异步通知第三方系统
        try {
			syncProducer.send(rocketMQProperties.topic, rocketMQProperties.tag, storage.toString());
		} catch (UnsupportedEncodingException | InterruptedException | RemotingException | MQClientException
				| MQBrokerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        //kafka
        kafkaProducer.send("test", storage.toString());
        
    }
}
