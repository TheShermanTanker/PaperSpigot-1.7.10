package net.minecraft.util.io.netty.channel.udt;

import net.minecraft.util.io.netty.buffer.ByteBufAllocator;
import net.minecraft.util.io.netty.channel.ChannelConfig;
import net.minecraft.util.io.netty.channel.MessageSizeEstimator;
import net.minecraft.util.io.netty.channel.RecvByteBufAllocator;

public interface UdtChannelConfig extends ChannelConfig {
  int getProtocolReceiveBufferSize();
  
  int getProtocolSendBufferSize();
  
  int getReceiveBufferSize();
  
  int getSendBufferSize();
  
  int getSoLinger();
  
  int getSystemReceiveBufferSize();
  
  int getSystemSendBufferSize();
  
  boolean isReuseAddress();
  
  UdtChannelConfig setConnectTimeoutMillis(int paramInt);
  
  UdtChannelConfig setMaxMessagesPerRead(int paramInt);
  
  UdtChannelConfig setWriteSpinCount(int paramInt);
  
  UdtChannelConfig setAllocator(ByteBufAllocator paramByteBufAllocator);
  
  UdtChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator paramRecvByteBufAllocator);
  
  UdtChannelConfig setAutoRead(boolean paramBoolean);
  
  UdtChannelConfig setWriteBufferHighWaterMark(int paramInt);
  
  UdtChannelConfig setWriteBufferLowWaterMark(int paramInt);
  
  UdtChannelConfig setMessageSizeEstimator(MessageSizeEstimator paramMessageSizeEstimator);
  
  UdtChannelConfig setProtocolReceiveBufferSize(int paramInt);
  
  UdtChannelConfig setProtocolSendBufferSize(int paramInt);
  
  UdtChannelConfig setReceiveBufferSize(int paramInt);
  
  UdtChannelConfig setReuseAddress(boolean paramBoolean);
  
  UdtChannelConfig setSendBufferSize(int paramInt);
  
  UdtChannelConfig setSoLinger(int paramInt);
  
  UdtChannelConfig setSystemReceiveBufferSize(int paramInt);
  
  UdtChannelConfig setSystemSendBufferSize(int paramInt);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channe\\udt\UdtChannelConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */