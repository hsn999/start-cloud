package com.start.framework.rocketmq.controller;

/*
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.start.framework.rocketmq.producer.Producer;

import java.io.UnsupportedEncodingException;

*//**
 * @Author: hsn
 * @Description: 测试
 *//*
@RestController
public class TestController {
    @Autowired
    private SyncProducer producer;

    @RequestMapping("/syncSend")
    public String pushMsg(String msg){
        try {
            return producer.send("XXTopic","tag-001",msg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (RemotingException e) {
            e.printStackTrace();
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (MQBrokerException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "ERROR";
    }
}
*/