# RedMQ
基于redis的消息队列

### maven配置:
<dependency>
			<groupId>com.github.thisxulz</groupId>
			<artifactId>redmq-client</artifactId>
			<version>0.0.2</version>
</dependency>

### 优点：
消息不丢失<br>
消息不重复<br>
分布式缓存锁<br>
在线安全关闭消费队列(防消息丢失)<br>
在线查看未处理消息数<br>
在线查看消费线程数<br>
在线注册topic和group<br>
提供restfulAPI<br>
实时监控/报警<br>
兼容单机和集群<br>

### 缺点：
重度依赖redis<br>

### Example

首先需要在线或者调用api注册topic和group，未注册的topic和group无法发送和接收消息
支持P2P，Publish/Subscribe

#### 发送消息
```
String topicName = "testTopic";
//groupName为空时，消息发送给topic下注册的所有消费者分组，
//groupName不为空，消息只发送给对应的group分组
String groupName = "testGroup";
DefaultProducer defaultProducer = new DefaultProducer(topicName, groupName);
for(int i=0;i<100000;i++) {
  String message = "i am message " + i;
  defaultProducer.sendMessage(message);
}
```
#### 处理消息
```
//同一个分组无法重复消息同一条消息,依赖redis的list实现
String topicName = "testTopic";
String groupName = "testGroup";
//可控制消费者线程数，默认单线程处理消息，可分布式部署多个节点处理消息
DefaultConsumer defaultConsumer = new DefaultConsumer(topicName, groupName, 2);
defaultConsumer.setMessageLinser(new MessageLinser() {
  @Override
  public MessageStatus consumeMessage(String message) {
    System.out.println(message);
    return MessageStatus.SUCCESS;
  }
});
defaultConsumer.start();
```
### 接口文档地址
http://localhost:8000/doc.html <br>
![](https://img.zcool.cn/zcool-diy/ibcaa52068564d3757e561cf4b84991f71.png@500w_500h_1e)

### 在线管理地址
http://localhost:8000/msgs <br>

在线管理 clone 代码到本地
修改 redmq-web 的配置文件 redmq.properties，配置redis单机或集群地址
根目录执行命令 mvn clean package
cd redmq-web
java -jar target/redmq-web.war
即可访问

查看消息队列，安全关闭消费者分组<br>
![](https://img.zcool.cn/zcool-diy/ibebfaed20dfd80e2b9638951e2030ba21.png@500w_500h_1e)

在线查看、注册消息主题<br>
![](https://img.zcool.cn/zcool-diy/ibb25d94da872f051ce2cb8caddd43d2f5.png@500w_500h_1e)

在线查看、注册消费者分组<br>
![](https://img.zcool.cn/zcool-diy/ib7efdaa1f84477153c417a0f3e59c0d0e.png@500w_500h_1e)
