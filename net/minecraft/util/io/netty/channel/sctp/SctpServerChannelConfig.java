package net.minecraft.util.io.netty.channel.sctp;

import com.sun.nio.sctp.SctpStandardSocketOptions;
import net.minecraft.util.io.netty.buffer.ByteBufAllocator;
import net.minecraft.util.io.netty.channel.ChannelConfig;
import net.minecraft.util.io.netty.channel.MessageSizeEstimator;
import net.minecraft.util.io.netty.channel.RecvByteBufAllocator;

public interface SctpServerChannelConfig extends ChannelConfig {
  int getBacklog();
  
  SctpServerChannelConfig setBacklog(int paramInt);
  
  int getSendBufferSize();
  
  SctpServerChannelConfig setSendBufferSize(int paramInt);
  
  int getReceiveBufferSize();
  
  SctpServerChannelConfig setReceiveBufferSize(int paramInt);
  
  SctpStandardSocketOptions.InitMaxStreams getInitMaxStreams();
  
  SctpServerChannelConfig setInitMaxStreams(SctpStandardSocketOptions.InitMaxStreams paramInitMaxStreams);
  
  SctpServerChannelConfig setMaxMessagesPerRead(int paramInt);
  
  SctpServerChannelConfig setWriteSpinCount(int paramInt);
  
  SctpServerChannelConfig setConnectTimeoutMillis(int paramInt);
  
  SctpServerChannelConfig setAllocator(ByteBufAllocator paramByteBufAllocator);
  
  SctpServerChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator paramRecvByteBufAllocator);
  
  SctpServerChannelConfig setAutoRead(boolean paramBoolean);
  
  SctpServerChannelConfig setWriteBufferHighWaterMark(int paramInt);
  
  SctpServerChannelConfig setWriteBufferLowWaterMark(int paramInt);
  
  SctpServerChannelConfig setMessageSizeEstimator(MessageSizeEstimator paramMessageSizeEstimator);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\sctp\SctpServerChannelConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */