package com.start.framework.rocketmq.producer;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;

/**
 * @Author: hsn
 * @Description:异步生产者
 */

@Component
public class AsyncProducer {

    /**
     * 生产者的组名
     */
    @Value("${apache.rocketmq.producer.asyncProducerGroup}")
    private String producerGroup;

    private DefaultMQProducer producer;
    /**
     * NameServer 地址
     */
    @Value("${apache.rocketmq.namesrvAddr}")
    private String namesrvAddr;

    @PostConstruct
    public void defaultMQProducer() {

        //生产者的组名
        producer= new DefaultMQProducer(producerGroup);
        //指定NameServer地址，多个地址以 ; 隔开
        producer.setNamesrvAddr(namesrvAddr);
        producer.setVipChannelEnabled(false);
        producer.setRetryTimesWhenSendAsyncFailed(0);
        try {
            producer.start();
            System.out.println("-------->:AsyncProducer启动了");
        } catch (MQClientException e) {
            e.printStackTrace();
        }
    }

    public void Send(String topic,String tags,String body) throws InterruptedException, RemotingException, MQClientException, MQBrokerException, UnsupportedEncodingException {
        Message message = new Message(topic, tags, body.getBytes(RemotingHelper.DEFAULT_CHARSET));
        producer.send(message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
               
                    sendResult.getMsgId();
            }
            @Override
            public void onException(Throwable e) {
                System.out.printf("Exception  %n",  e);
                e.printStackTrace();
            }
        });
    }

}
