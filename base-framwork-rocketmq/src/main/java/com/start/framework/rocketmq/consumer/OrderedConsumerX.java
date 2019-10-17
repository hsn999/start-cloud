package com.start.framework.rocketmq.consumer;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;

public class OrderedConsumerX {

    public static void main(String[] args) throws MQClientException {

        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("please_rename_unique_group_name");

        consumer.setNamesrvAddr("192.168.37.139:9876");

        consumer.subscribe("TopicTest", "TagA");

        //顺序消费，注意这里的监听接口
        consumer.registerMessageListener(new MessageListenerOrderly() {

            @Override
            public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
                MessageExt messageExt = list.get(0);
                try {
                    byte[] body = messageExt.getBody();
                    String tags = messageExt.getTags();
                    String topic = messageExt.getTopic();
                    String keys = messageExt.getKeys();
                    System.out.println("body:"+new String(body, RemotingHelper.DEFAULT_CHARSET)+" tags:"+tags+" topic:"+topic+" keys:"+keys);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return ConsumeOrderlyStatus.SUSPEND_CURRENT_QUEUE_A_MOMENT;
                }
                return ConsumeOrderlyStatus.SUCCESS;
            }
        });

        consumer.start();
        System.out.printf("Consumer Started.%n");
    }
}
