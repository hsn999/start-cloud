#docker 安装kafka
1、下载镜像

这里使用了wurstmeister/kafka和wurstmeister/zookeeper这两个版本的镜像


docker pull wurstmeister/zookeeper

docker pull wurstmeister/kafka

在命令中运行docker images验证两个镜像已经安装完毕

<<<<<<< HEAD

docker-compose.yml ：

version: '2'
services:
  zookeeper:
    image: wurstmeister/zookeeper
    ports:
      - "2181:2181"
  kafka:
    image: wurstmeister/kafka
    ports:
      - "9092:9092"
    environment:
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://:9092
      KAFKA_LISTENERS: PLAINTEXT://:9092
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://kafka001:9092
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock


启动之后会提示无法连接kafka001:9092的错误，docker ps查看容器id

docker exec -it  容器id /bin/bash,进入容器，编辑hosts指定ip

#vi hosts

192.168.100.22  kafka001 

保存后重新启动容器，这样外部程序就可以访问kafka了

springboot 配置：

#kafka
# 指定kafka 代理地址，可以多个
spring.kafka.bootstrap-servers=kafka001:9092
# 指定listener 容器中的线程数，用于提高并发量
spring.kafka.listener.concurrency=1
# 每次批量发送消息的数量
spring.kafka.producer.batch-size=1000
# 指定默认消费者group id
spring.kafka.consumer.group-id=myGroup
# 指定默认topic id
spring.kafka.template.default-topic=topic-1
spring.kafka.consumer.group-id=test-consumer-group

*****记得windows也要配hosts



手动启动zookeeper容器
=======
2.启动

启动zookeeper容器
docker run -d --name zookeeper -p 2181:2181 -t wurstmeister/zookeeper

启动kafka容器

<<<<<<< HEAD
docker run -d --name kafka --publish 9092:9092 --link zookeeper --env KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 --env KAFKA_ADVERTISED_HOST_NAME=kafka001 --env KAFKA_ADVERTISED_PORT=9092 --volume /etc/localtime:/etc/localtime wurstmeister/kafka:latest
=======
docker run -d --name kafka --publish 9092:9092 --link zookeeper --env KAFKA_ZOOKEEPER_CONNECT=zookeeper:2181 --env KAFKA_ADVERTISED_HOST_NAME=192.168.59.101 --env KAFKA_ADVERTISED_PORT=9092 --volume /etc/localtime:/etc/localtime wurstmeister/kafka:latest

192.168.59.101 改为宿主机器的IP地址，如果不这么设置，可能会导致在别的机器上访问不到kafka。

3. 测试kafka

进入kafka容器的命令行

 

运行 docker ps，找到kafka的 CONTAINER ID，运行 docker exec -it ${CONTAINER ID} /bin/bash，进入kafka容器。
进入kafka默认目录 /opt/kafka_2.11-0.10.1.0
