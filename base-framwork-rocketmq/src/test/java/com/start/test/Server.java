package com.start.test;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;

public class Server {
	DefaultMQProducer producer;
	
	public static void main(String[] args) throws Exception {
		Server server =new Server();
		server.producer = defaultMQProducer();
		
		
		for (int i = 0; i < 100; i++) {
            int orderId = i % 10;
            
            server.sendInOrder(orderId, "TopicTest", "TagA", "ooooooooo"+orderId+"----"+i);
		}
		
		
		server.producer.shutdown();
	}
	
	
	public static DefaultMQProducer  defaultMQProducer() {

		// 生产者的组名
		DefaultMQProducer producer = new DefaultMQProducer("TopicTestGroup");
		// 指定NameServer地址，多个地址以 ; 隔开
		producer.setNamesrvAddr("127.0.0.1:9876");
		producer.setVipChannelEnabled(false);
		try {
			producer.start();
			System.out.println("-------->:producer启动了");
		} catch (MQClientException e) {
			e.printStackTrace();
		}
		return producer;
		
		
	}

	/**
	 * 同步发送
	 * 
	 * @param topic
	 * @param tags
	 * @param body
	 * @return
	 * @throws InterruptedException
	 * @throws RemotingException
	 * @throws MQClientException
	 * @throws MQBrokerException
	 * @throws UnsupportedEncodingException
	 */
	public String send(String topic, String tags, String body) throws InterruptedException, RemotingException,
			MQClientException, MQBrokerException, UnsupportedEncodingException {
		Message message = new Message(topic, tags, body.getBytes(RemotingHelper.DEFAULT_CHARSET));
		StopWatch stop = new StopWatch();
		stop.start();
		SendResult result = producer.send(message);
		System.out.println("发送响应：MsgId:" + result.getMsgId() + "，发送状态:" + result.getSendStatus());
		stop.stop();
		return "{\"MsgId\":\"" + result.getMsgId() + "\"}";
	}

	/**
	 * 按照订单id顺序发送
	 * 
	 * @param orderId
	 * @param topic
	 * @param tags
	 * @param body
	 * @return
	 * @throws MQClientException
	 * @throws RemotingException
	 * @throws MQBrokerException
	 * @throws InterruptedException
	 * @throws UnsupportedEncodingException
	 */
	public String sendInOrder(int orderId, String topic, String tags, String body) throws MQClientException,
			RemotingException, MQBrokerException, InterruptedException, UnsupportedEncodingException {

		Message msg = new Message(topic, tags, body.getBytes(RemotingHelper.DEFAULT_CHARSET));
		SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
			@Override
			public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
				Integer id = (Integer) arg;
				int index = id % mqs.size();
				return mqs.get(index);
			}
		}, 1);//这个1是指定队列的下标
		
		return "{\"MsgId\":\"" + sendResult.getMsgId() + "\"}";
	}

	
}
