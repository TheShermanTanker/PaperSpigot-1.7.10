package net.minecraft.util.io.netty.channel;

import java.net.SocketAddress;

interface ChannelOutboundInvoker {
  ChannelFuture bind(SocketAddress paramSocketAddress);
  
  ChannelFuture connect(SocketAddress paramSocketAddress);
  
  ChannelFuture connect(SocketAddress paramSocketAddress1, SocketAddress paramSocketAddress2);
  
  ChannelFuture disconnect();
  
  ChannelFuture close();
  
  @Deprecated
  ChannelFuture deregister();
  
  ChannelFuture bind(SocketAddress paramSocketAddress, ChannelPromise paramChannelPromise);
  
  ChannelFuture connect(SocketAddress paramSocketAddress, ChannelPromise paramChannelPromise);
  
  ChannelFuture connect(SocketAddress paramSocketAddress1, SocketAddress paramSocketAddress2, ChannelPromise paramChannelPromise);
  
  ChannelFuture disconnect(ChannelPromise paramChannelPromise);
  
  ChannelFuture close(ChannelPromise paramChannelPromise);
  
  @Deprecated
  ChannelFuture deregister(ChannelPromise paramChannelPromise);
  
  ChannelOutboundInvoker read();
  
  ChannelFuture write(Object paramObject);
  
  ChannelFuture write(Object paramObject, ChannelPromise paramChannelPromise);
  
  ChannelOutboundInvoker flush();
  
  ChannelFuture writeAndFlush(Object paramObject, ChannelPromise paramChannelPromise);
  
  ChannelFuture writeAndFlush(Object paramObject);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\ChannelOutboundInvoker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */