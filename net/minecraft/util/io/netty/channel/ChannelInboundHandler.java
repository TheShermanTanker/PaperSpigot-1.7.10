package net.minecraft.util.io.netty.channel;

public interface ChannelInboundHandler extends ChannelHandler {
  void channelRegistered(ChannelHandlerContext paramChannelHandlerContext) throws Exception;
  
  @Deprecated
  void channelUnregistered(ChannelHandlerContext paramChannelHandlerContext) throws Exception;
  
  void channelActive(ChannelHandlerContext paramChannelHandlerContext) throws Exception;
  
  void channelInactive(ChannelHandlerContext paramChannelHandlerContext) throws Exception;
  
  void channelRead(ChannelHandlerContext paramChannelHandlerContext, Object paramObject) throws Exception;
  
  void channelReadComplete(ChannelHandlerContext paramChannelHandlerContext) throws Exception;
  
  void userEventTriggered(ChannelHandlerContext paramChannelHandlerContext, Object paramObject) throws Exception;
  
  void channelWritabilityChanged(ChannelHandlerContext paramChannelHandlerContext) throws Exception;
  
  void exceptionCaught(ChannelHandlerContext paramChannelHandlerContext, Throwable paramThrowable) throws Exception;
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\ChannelInboundHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */