package net.minecraft.util.io.netty.channel;

import java.net.SocketAddress;
import net.minecraft.util.io.netty.util.AttributeMap;

public interface Channel extends AttributeMap, ChannelOutboundInvoker, ChannelPropertyAccess, Comparable<Channel> {
  EventLoop eventLoop();
  
  Channel parent();
  
  ChannelConfig config();
  
  boolean isOpen();
  
  boolean isRegistered();
  
  boolean isActive();
  
  ChannelMetadata metadata();
  
  SocketAddress localAddress();
  
  SocketAddress remoteAddress();
  
  ChannelFuture closeFuture();
  
  boolean isWritable();
  
  Channel flush();
  
  Channel read();
  
  Unsafe unsafe();
  
  public static interface Unsafe {
    SocketAddress localAddress();
    
    SocketAddress remoteAddress();
    
    void register(EventLoop param1EventLoop, ChannelPromise param1ChannelPromise);
    
    void bind(SocketAddress param1SocketAddress, ChannelPromise param1ChannelPromise);
    
    void connect(SocketAddress param1SocketAddress1, SocketAddress param1SocketAddress2, ChannelPromise param1ChannelPromise);
    
    void disconnect(ChannelPromise param1ChannelPromise);
    
    void close(ChannelPromise param1ChannelPromise);
    
    void closeForcibly();
    
    @Deprecated
    void deregister(ChannelPromise param1ChannelPromise);
    
    void beginRead();
    
    void write(Object param1Object, ChannelPromise param1ChannelPromise);
    
    void flush();
    
    ChannelPromise voidPromise();
    
    ChannelOutboundBuffer outboundBuffer();
  }
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\Channel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */