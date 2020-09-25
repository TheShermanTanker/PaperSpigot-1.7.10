package net.minecraft.util.io.netty.channel;

import java.net.SocketAddress;

public interface ChannelOutboundHandler extends ChannelHandler {
  void bind(ChannelHandlerContext paramChannelHandlerContext, SocketAddress paramSocketAddress, ChannelPromise paramChannelPromise) throws Exception;
  
  void connect(ChannelHandlerContext paramChannelHandlerContext, SocketAddress paramSocketAddress1, SocketAddress paramSocketAddress2, ChannelPromise paramChannelPromise) throws Exception;
  
  void disconnect(ChannelHandlerContext paramChannelHandlerContext, ChannelPromise paramChannelPromise) throws Exception;
  
  void close(ChannelHandlerContext paramChannelHandlerContext, ChannelPromise paramChannelPromise) throws Exception;
  
  @Deprecated
  void deregister(ChannelHandlerContext paramChannelHandlerContext, ChannelPromise paramChannelPromise) throws Exception;
  
  void read(ChannelHandlerContext paramChannelHandlerContext) throws Exception;
  
  void write(ChannelHandlerContext paramChannelHandlerContext, Object paramObject, ChannelPromise paramChannelPromise) throws Exception;
  
  void flush(ChannelHandlerContext paramChannelHandlerContext) throws Exception;
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\ChannelOutboundHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */