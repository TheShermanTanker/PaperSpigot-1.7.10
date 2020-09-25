package net.minecraft.util.io.netty.channel.udt;

import net.minecraft.util.io.netty.buffer.ByteBufAllocator;
import net.minecraft.util.io.netty.channel.MessageSizeEstimator;
import net.minecraft.util.io.netty.channel.RecvByteBufAllocator;

public interface UdtServerChannelConfig extends UdtChannelConfig {
  int getBacklog();
  
  UdtServerChannelConfig setBacklog(int paramInt);
  
  UdtServerChannelConfig setConnectTimeoutMillis(int paramInt);
  
  UdtServerChannelConfig setMaxMessagesPerRead(int paramInt);
  
  UdtServerChannelConfig setWriteSpinCount(int paramInt);
  
  UdtServerChannelConfig setAllocator(ByteBufAllocator paramByteBufAllocator);
  
  UdtServerChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator paramRecvByteBufAllocator);
  
  UdtServerChannelConfig setAutoRead(boolean paramBoolean);
  
  UdtServerChannelConfig setProtocolReceiveBufferSize(int paramInt);
  
  UdtServerChannelConfig setProtocolSendBufferSize(int paramInt);
  
  UdtServerChannelConfig setReceiveBufferSize(int paramInt);
  
  UdtServerChannelConfig setReuseAddress(boolean paramBoolean);
  
  UdtServerChannelConfig setSendBufferSize(int paramInt);
  
  UdtServerChannelConfig setSoLinger(int paramInt);
  
  UdtServerChannelConfig setSystemReceiveBufferSize(int paramInt);
  
  UdtServerChannelConfig setSystemSendBufferSize(int paramInt);
  
  UdtServerChannelConfig setWriteBufferHighWaterMark(int paramInt);
  
  UdtServerChannelConfig setWriteBufferLowWaterMark(int paramInt);
  
  UdtServerChannelConfig setMessageSizeEstimator(MessageSizeEstimator paramMessageSizeEstimator);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channe\\udt\UdtServerChannelConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */