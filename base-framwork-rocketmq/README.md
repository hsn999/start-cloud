## Centos 安装示例 (RocketMQ Download & Build from Release)

yum install java
yum install maven

### Download & Build from Release

  > wget http://mirrors.hust.edu.cn/apache/rocketmq/4.4.0/rocketmq-all-4.4.0-source-release.zip
  
  > unzip rocketmq-all-4.4.0-source-release.zip
  
  > cd rocketmq-all-4.4.0/
 
  > mvn -Prelease-all -DskipTests clean install -U
  
  > cd distribution/target/apache-rocketmq
  
* Start Name Server
  
  > nohup sh bin/mqnamesrv &
  
  > tail -f ~/logs/rocketmqlogs/namesrv.log
  
  The Name Server boot success...
  
* Start Broker
  
  > nohup sh bin/mqbroker -n localhost:9876 &
  
  > tail -f ~/logs/rocketmqlogs/broker.log 
  
  The broker[%s, 172.30.30.233:10911] boot success...
  
* Send & Receive Messages
Before sending/receiving messages, we need to tell clients the location of name servers. RocketMQ provides multiple ways to achieve this. For simplicity, we use environment variable NAMESRV_ADDR

 > export NAMESRV_ADDR=localhost:9876
 
 > sh bin/tools.sh org.apache.rocketmq.example.quickstart.Producer
 
 SendResult [sendStatus=SEND_OK, msgId= ...

 > sh bin/tools.sh org.apache.rocketmq.example.quickstart.Consumer
 
 ConsumeMessageThread_%d Receive New Messages: [MessageExt...

* Shutdown Servers

 > sh bin/mqshutdown broker
 
The mqbroker(36695) is running...

Send shutdown request to mqbroker(36695) OK

 > sh bin/mqshutdown namesrv
 
The mqnamesrv(36664) is running...

Send shutdown request to mqnamesrv(36664) OK

搭建RocketMQ web控制台
1、访问 https://github.com/apache/rocketmq-externals/ ， 
使用git将代码clone下来

2、修改项目配置信息 
这是一个用springboot编写的工程，进入到 rocketmq-externals\rocketmq-console\src\main\resources 目录下，编辑 application.properties 文件， 修改mq的连接地址信息：

rocketmq.config.namesrvAddr=localhost:9876
1
3、启动项目
在rocketmq-externals\rocketmq-console目录下，执行：

mvn spring-boot:run
1
等待启动成功。

[2018-03-05 10:54:19.823]  INFO Initializing ProtocolHandler ["http-nio-8080"]
[2018-03-05 10:54:19.853]  INFO Starting ProtocolHandler [http-nio-8080]
[2018-03-05 10:54:19.893]  INFO Using a shared selector for servlet write/read
[2018-03-05 10:54:19.929]  INFO Tomcat started on port(s): 8080 (http)
[2018-03-05 10:54:19.940]  INFO Started App in 9.61 seconds (JVM running for 18.979)
4、浏览器访问：http://localhost:8080
