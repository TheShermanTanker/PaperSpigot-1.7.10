package net.minecraft.util.io.netty.channel.socket.oio;

import net.minecraft.util.io.netty.buffer.ByteBufAllocator;
import net.minecraft.util.io.netty.channel.MessageSizeEstimator;
import net.minecraft.util.io.netty.channel.RecvByteBufAllocator;
import net.minecraft.util.io.netty.channel.socket.SocketChannelConfig;

public interface OioSocketChannelConfig extends SocketChannelConfig {
  OioSocketChannelConfig setSoTimeout(int paramInt);
  
  int getSoTimeout();
  
  OioSocketChannelConfig setTcpNoDelay(boolean paramBoolean);
  
  OioSocketChannelConfig setSoLinger(int paramInt);
  
  OioSocketChannelConfig setSendBufferSize(int paramInt);
  
  OioSocketChannelConfig setReceiveBufferSize(int paramInt);
  
  OioSocketChannelConfig setKeepAlive(boolean paramBoolean);
  
  OioSocketChannelConfig setTrafficClass(int paramInt);
  
  OioSocketChannelConfig setReuseAddress(boolean paramBoolean);
  
  OioSocketChannelConfig setPerformancePreferences(int paramInt1, int paramInt2, int paramInt3);
  
  OioSocketChannelConfig setAllowHalfClosure(boolean paramBoolean);
  
  OioSocketChannelConfig setConnectTimeoutMillis(int paramInt);
  
  OioSocketChannelConfig setMaxMessagesPerRead(int paramInt);
  
  OioSocketChannelConfig setWriteSpinCount(int paramInt);
  
  OioSocketChannelConfig setAllocator(ByteBufAllocator paramByteBufAllocator);
  
  OioSocketChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator paramRecvByteBufAllocator);
  
  OioSocketChannelConfig setAutoRead(boolean paramBoolean);
  
  OioSocketChannelConfig setWriteBufferHighWaterMark(int paramInt);
  
  OioSocketChannelConfig setWriteBufferLowWaterMark(int paramInt);
  
  OioSocketChannelConfig setMessageSizeEstimator(MessageSizeEstimator paramMessageSizeEstimator);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\socket\oio\OioSocketChannelConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */