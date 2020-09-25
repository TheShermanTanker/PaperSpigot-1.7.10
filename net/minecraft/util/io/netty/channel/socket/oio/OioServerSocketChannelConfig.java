package net.minecraft.util.io.netty.channel.socket.oio;

import net.minecraft.util.io.netty.buffer.ByteBufAllocator;
import net.minecraft.util.io.netty.channel.MessageSizeEstimator;
import net.minecraft.util.io.netty.channel.RecvByteBufAllocator;
import net.minecraft.util.io.netty.channel.socket.ServerSocketChannelConfig;

public interface OioServerSocketChannelConfig extends ServerSocketChannelConfig {
  OioServerSocketChannelConfig setSoTimeout(int paramInt);
  
  int getSoTimeout();
  
  OioServerSocketChannelConfig setBacklog(int paramInt);
  
  OioServerSocketChannelConfig setReuseAddress(boolean paramBoolean);
  
  OioServerSocketChannelConfig setReceiveBufferSize(int paramInt);
  
  OioServerSocketChannelConfig setPerformancePreferences(int paramInt1, int paramInt2, int paramInt3);
  
  OioServerSocketChannelConfig setConnectTimeoutMillis(int paramInt);
  
  OioServerSocketChannelConfig setMaxMessagesPerRead(int paramInt);
  
  OioServerSocketChannelConfig setWriteSpinCount(int paramInt);
  
  OioServerSocketChannelConfig setAllocator(ByteBufAllocator paramByteBufAllocator);
  
  OioServerSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator paramRecvByteBufAllocator);
  
  OioServerSocketChannelConfig setAutoRead(boolean paramBoolean);
  
  OioServerSocketChannelConfig setWriteBufferHighWaterMark(int paramInt);
  
  OioServerSocketChannelConfig setWriteBufferLowWaterMark(int paramInt);
  
  OioServerSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator paramMessageSizeEstimator);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\socket\oio\OioServerSocketChannelConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */