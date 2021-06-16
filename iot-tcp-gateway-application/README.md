### 整改了下网上的iot网关代码
- 继承AbstractChannelReadHandler，重写channelRead方法
- 实现Sender接口，参照TcpSender
- 实现ICodec接口，配置编码规则
