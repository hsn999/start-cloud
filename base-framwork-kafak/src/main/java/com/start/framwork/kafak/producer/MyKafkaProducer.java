package com.start.framwork.kafak.producer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;


@Component
public class MyKafkaProducer {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    private static Gson gson = new GsonBuilder().create();

    //发送消息方法
    public void send(String topic,String msg) {
        Message message = new Message();
        message.setId("KFK_"+System.currentTimeMillis());
        message.setMsg(msg);
        message.setSendTime(new Date());
        kafkaTemplate.send(topic, gson.toJson(message));
    }

}
