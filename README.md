# springcloud 脚手架 含：nacos mybits mybitsplus seata redis kafka rocketMq mongodb springsecurity swagger2 zuul 等

**分布式事务组件AT模式，数据库采用mysql**




demo中使用的相关版本号，具体请看代码。如果搭建个人demo不成功，验证是否是由版本导致，由于目前这几个项目更新比较频繁，版本稍有变化便会出现许多奇怪问题

* seata 0.8.1
* spring-cloud-alibaba-seata 2.1.0.RELEASE
* spring-cloud-starter-alibaba-nacos-discovery  0.2.1.RELEASE
* springboot 2.0.6.RELEASE
* springcloud Finchley.RELEASE


具体项目：

* base-common 基础配置及工具，含swagger配置
* base-framework-mysql mysqlPlus配置
* base-framework-mongodb mogo組件
* base-framework-kafka 
* base-framework-rocketMq
* base-framework-redis
* base-framework-jwt
* spc-account-server ,账务服务整合mybits
* spc-order-serrver 订单服务，整合mybitsPlus
* spc-storage-server库存服务，整合mybitsPlus
* spc-client-demo 测试kafka、rocketmq、redis、mongo 可以使用此服务
* spc-zuul-service zuul聚合全部 的api文档
* spc-auth-center 认证中心，结合spring security及jwt创建token
* spc-zuul-gateway zuul对外网关




----------

## 1. 服务端配置

### 1.1 Nacos-server

版本为nacos-server-1.1.3，demo采用本地单机部署方式，请参考 [Nacos 快速开始](https://nacos.io/zh-cn/docs/quick-start.html)

### 1.2 Seata-server

seata-server为release版本0.8.0，demo采用本地单机部署，从此处下载 [https://github.com/seata/seata/releases](https://github.com/seata/seata/releases) 并解压

#### 1.2.1 修改conf/registry.conf 配置

设置type、设置serverAddr为你的nacos节点地址。

#### 1.2.2 修改conf/nacos-config.txt 配置

service.vgroup_mapping.${your-service-gruop}=default，中间的${your-service-gruop}为自己定义的服务组名称，服务中的application.properties文件里配置服务组名称。

demo中有两个服务，分别是storage-service和order-service，所以配置如下

~~~properties
service.vgroup_mapping.storage-service-group=default
service.vgroup_mapping.order-service-group=default
~~~



#### 1.2.3 启动seata-server

**分两步，如下**

~~~shell
# 初始化seata 的nacos配置
cd conf
sh nacos-config.sh 192.168.21.89

# 启动seata-server
cd bin
sh seata-server.sh -p 8091 -m file
~~~


### 1.3 zipkin

配置启动参考：https://github.com/hsn999/start-cloud/tree/master/spc-zipkin-server

### 1.4 zookeeper 及 kafka

参考： https://github.com/hsn999/start-cloud/blob/master/base-framwork-kafak/README.md

### 1.5 ReocketMq

参考： https://github.com/hsn999/start-cloud/blob/master/base-framwork-rocketmq/README.md

### 1.6 Mongo & Redis 略。。。。


----------

## 2. 应用配置

### 2.1 数据库初始化

~~~SQL
-- 创建 order库、业务表、undo_log表
create database seata_order;
use seata_order;

DROP TABLE IF EXISTS `order_tbl`;
CREATE TABLE `order_tbl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(255) DEFAULT NULL,
  `commodity_code` varchar(255) DEFAULT NULL,
  `count` int(11) DEFAULT 0,
  `money` int(11) DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `undo_log`
(
  `id`            BIGINT(20)   NOT NULL AUTO_INCREMENT,
  `branch_id`     BIGINT(20)   NOT NULL,
  `xid`           VARCHAR(100) NOT NULL,
  `context`       VARCHAR(128) NOT NULL,
  `rollback_info` LONGBLOB     NOT NULL,
  `log_status`    INT(11)      NOT NULL,
  `log_created`   DATETIME     NOT NULL,
  `log_modified`  DATETIME     NOT NULL,
  `ext`           VARCHAR(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_undo_log` (`xid`, `branch_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;


-- 创建 storage库、业务表、undo_log表
create database seata_storage;
use seata_storage;

DROP TABLE IF EXISTS `storage_tbl`;
CREATE TABLE `storage_tbl` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `commodity_code` varchar(255) DEFAULT NULL,
  `count` int(11) DEFAULT 0,
  PRIMARY KEY (`id`),
  UNIQUE KEY (`commodity_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `undo_log`
(
  `id`            BIGINT(20)   NOT NULL AUTO_INCREMENT,
  `branch_id`     BIGINT(20)   NOT NULL,
  `xid`           VARCHAR(100) NOT NULL,
  `context`       VARCHAR(128) NOT NULL,
  `rollback_info` LONGBLOB     NOT NULL,
  `log_status`    INT(11)      NOT NULL,
  `log_created`   DATETIME     NOT NULL,
  `log_modified`  DATETIME     NOT NULL,
  `ext`           VARCHAR(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_undo_log` (`xid`, `branch_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;
  
-- 创建 account库、业务表、undo_log表
create database seata_account;
use seata_account;
  
  CREATE TABLE `account` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_id` bigint(11) DEFAULT NULL COMMENT '用户id',
  `total` decimal(10,0) DEFAULT '0' COMMENT '总额度',
  `used` decimal(10,0) DEFAULT '0' COMMENT '已用余额',
  `residue` decimal(10,0) DEFAULT '0' COMMENT '剩余可用额度',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;


  
CREATE TABLE `undo_log`
(
  `id`            BIGINT(20)   NOT NULL AUTO_INCREMENT,
  `branch_id`     BIGINT(20)   NOT NULL,
  `xid`           VARCHAR(100) NOT NULL,
  `context`       VARCHAR(128) NOT NULL,
  `rollback_info` LONGBLOB     NOT NULL,
  `log_status`    INT(11)      NOT NULL,
  `log_created`   DATETIME     NOT NULL,
  `log_modified`  DATETIME     NOT NULL,
  `ext`           VARCHAR(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `ux_undo_log` (`xid`, `branch_id`)
) ENGINE = InnoDB
  AUTO_INCREMENT = 1
  DEFAULT CHARSET = utf8;


-- 初始化库存模拟数据
INSERT INTO seata_storage.storage_tbl (id, commodity_code, count) VALUES (1, 'product-1', 9999999);
INSERT INTO seata_storage.storage_tbl (id, commodity_code, count) VALUES (2, 'product-2', 0);
INSERT INTO seat_account.account (`id`, `user_id`, `total`, `used`, `residue`) VALUES ('1', '1', '1000', '0', '100');
~~~

### 2.2 应用配置

见代码

几个重要的配置

1. 每个应用的resource里需要配置一个registry.conf ，demo中与seata-server里的配置相同
2. application.propeties 的各个配置项，注意spring.cloud.alibaba.seata.tx-service-group 是服务组名称，与nacos-config.txt 配置的service.vgroup_mapping.${your-service-gruop}具有对应关系


## 3. 认证中心，结合spring security及jwt创建token

JSON Web Token（JWT）是目前最流行的跨域身份验证解决方案，在微服务环境下，我们可以借助JWT实现服务器的身份认证

基本过程如下图：

![Image text](https://github.com/hsn999/start-cloud/blob/master/src/doc/jwt.png)

 

JWT的原则是在服务器身份验证之后，将生成一个JSON对象并将其发送回用户，如下所示。

{

"UserName": "xxx",

"Role": "Admin，User",

"Expire": "2019-06-01 10:13:26"

}

之后，当用户与服务器通信时，客户在请求中发回JSON对象。服务器仅依赖于这个JSON对象来标识用户。为了防止用户篡改数据，服务器将在生成对象时添加签名（有关详细信息，请参阅下文）。

服务器不保存任何会话数据，即服务器变为无状态，使其更容易扩展。

 

项目分成几个模块

        <module>spc-zuul-gateway</module>
        <module>base-framwork-jwt</module>
        <module>spc-auth-center</module>


## 4 其他配置
### 4.1 redis組件配置
redis安装配置可参考项目doc目录文档

增加配置文件 redisconf.properties
~~~
spring.redis.single.host=127.0.0.1
spring.redis.single.port=6379

spring.redis.cluster.nodes=192.168.1.42:7001,192.168.1.42:7002,192.168.1.42:7003,192.168.1.42:7004,192.168.1.42:7005,192.168.1.42:7006   
spring.redis.cluster.nodes.commandTimeout=5000
~~~

application.properties增加model.name指定redis类型
model.name=com.start.framework.redis.redis.util.standalone.JedisUtil（单独一个server）
model.name=com.start.framework.redis.redis.util.cluster.JedisClusterUtil（集群）


使用Redis简单队列
addToQueue---加入队列
getFromQueue----从队列取出

----------

## 5. 测试

![Image text](https://github.com/hsn999/start-cloud/blob/master/src/doc/nacos.png)

1. 访问 localhost:8899/swagger-ui.html 可查看聚合的相关接口

![Image text](https://github.com/hsn999/start-cloud/blob/master/src/doc/zuul1.png)

2. 分布式事务成功，模拟正常下单、扣库存

   localhost:9091/swagger-ui.html

![Image text](https://github.com/hsn999/start-cloud/blob/master/src/doc/zuul2.png)
![Image text](https://github.com/hsn999/start-cloud/blob/master/src/doc/swagger.png)
   commit为成功提交    、rollback为失败回滚 

3. 测试kafka、rocketmq、redis、mongo 可以使用storege服务

   http://127.0.0.1:9092/swagger-ui.html

4.  测试链路监控
    http://127.0.0.1:9411
![Image text](https://github.com/hsn999/start-cloud/blob/master/src/doc/zipkin2.jpg)
![Image text](https://github.com/hsn999/start-cloud/blob/master/src/doc/zipkin1.jpg)
![Image text](https://github.com/hsn999/start-cloud/blob/master/src/doc/zipkin3.ipg)
