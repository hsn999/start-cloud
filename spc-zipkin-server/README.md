# zipkin 环境部署

参考   https://blog.csdn.net/h_sn9999/article/details/102698763

一、安装zk和kafka

请参照：https://blog.csdn.net/h_sn9999/article/details/102622751

二、安装 elasticsearch

1、下载elasticsearch-5.5.0.zip

wget https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-5.5.0.zip

2、解压

unzip elasticsearch-5.5.0.zip

3、移动到固定的目录

mv /elasticsearch-5.5.0 /usr/software/el

4、el属于开箱即用，进入目录

cd elasticsearch-5.5.0/bin ./elasticsearch

三、系统数据的处理流程

Zipkin 是一款开源的分布式实时数据追踪系统，其主要功能是聚集来自各个异构系统的实时监控数据，用来追踪微服务架构下的系统延时问题。Zipkin 的用户界面可以呈现一幅关联图表，以显示有多少被追踪的请求通过了每一层应用。在微服务架构中，一次用户请求可能会由后台若干个服务负责处理，那么每个处理请求的服务就可以理解为一个 Span，因此 Span 是一个树形结构，以体现服务之间的调用关系。Zipkin 的用户界面除了可以查看 Span 的依赖关系之外，还以瀑布图的形式显示了每个 Span 的耗时情况，可以一目了然的看到各个服务的性能状况。打开每个 Span，还有更详细的数据以键值对的形式呈现。

下图借用前辈画的

![Image text](https://github.com/hsn999/start-cloud/blob/master/src/doc/2019102312224112.png)


 




四、Zipkin Server 

https://github.com/openzipkin/zipkin/releases

Running via Java:

curl -sSL https://zipkin.io/quickstart.sh | bash -s io.zipkin:zipkin-server:LATEST:slim zipkin.jar
java -jar zipkin.jar
Running via Docker:

docker run -d -p 9411:9411 openzipkin/zipkin-slim

download jar： https://mvnrepository.com/artifact/io.zipkin.java/zipkin-server

java -DKAFKA_BOOTSTRAP_SERVERS=kafka001:9092 -DSTORAGE_TYPE=elasticsearch -DES_HOSTS=http://localhost:9300 -jar zipkin.jar

 

五、应用配置

项目依赖：


<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <!-- <parent>
        <groupId>demo</groupId>
        <artifactId>zipkin-demo</artifactId>
        <version>1.0.0.BUILD-SNAPSHOT</version>
    </parent> -->
    <modelVersion>4.0.0</modelVersion>

    <artifactId>backend</artifactId>
    <version>1.0-SNAPSHOT</version>

    <name>backend</name>
    <!-- FIXME change it to the project's website -->
    <url>http://www.example.com</url>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>1.8</maven.compiler.source>
        <maven.compiler.target>1.8</maven.compiler.target>
                <java.version>1.8</java.version>
        <spring-cloud.version>Greenwich.SR1</spring-cloud.version>
         <maven-jar-plugin.version>3.1.1</maven-jar-plugin.version>
    </properties>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.1.5.RELEASE</version>
        <relativePath/> <!-- lookup parent from repository -->
    </parent>
 

 
    <dependencies>
        <!--kafka依赖-->
        <dependency>
            <groupId>org.springframework.kafka</groupId>
            <artifactId>spring-kafka</artifactId>
        </dependency>
        <!--zipkin客户端依赖，其包含了sleuth依赖-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-zipkin</artifactId>
        </dependency>
 
        <!--feign依赖-->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-starter-openfeign</artifactId>
        </dependency>
        <!--actuator依赖-->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-actuator</artifactId>
        </dependency>
        
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <optional>true</optional>
        </dependency>
    </dependencies>
 
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>${spring-cloud.version}</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
 
 
    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
            </plugin>
        </plugins>
    </build>
 
 
</project>

 

 

application.yaml：
server:
  port: 9000

spring:
  application:
    name: app2
  zipkin:
    base-url: http://localhost:9411
    sender:
      type: kafka
  sleuth:
    sampler:
      percentage: 1.0
  kafka:
    bootstrap-servers: kafka001:9092

 

详细代码请参考：https://github.com/hsn999/zipkin
       或者：https://github.com/hsn999/start-cloud