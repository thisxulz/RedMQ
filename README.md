# RedMQ
基于redis的消息队列

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

### 缺点：
重度依赖redis<br>

### Example

#### 发送消息
```
String topicName = "testTopic";
String groupName = "testGroup";
DefaultProducer defaultProducer = new DefaultProducer(topicName, groupName);
for(int i=0;i<100000;i++) {
  String message = "i am message " + i;
  defaultProducer.sendMessage(message);
}
```
#### 处理消息
```
String topicName = "testTopic";
String groupName = "testGroup";
DefaultConsumer defaultConsumer = new DefaultConsumer(topicName, groupName);
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

查看消息队列，安全关闭消费者分组<br>
![](https://img.zcool.cn/zcool-diy/ibebfaed20dfd80e2b9638951e2030ba21.png@500w_500h_1e)

在线查看、注册消息主题<br>
![](https://img.zcool.cn/zcool-diy/ibb25d94da872f051ce2cb8caddd43d2f5.png@500w_500h_1e)

在线查看、注册消费者分组<br>
![](https://img.zcool.cn/zcool-diy/ib7efdaa1f84477153c417a0f3e59c0d0e.png@500w_500h_1e)
