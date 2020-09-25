package net.minecraft.util.io.netty.channel.sctp;

import com.sun.nio.sctp.SctpStandardSocketOptions;
import net.minecraft.util.io.netty.buffer.ByteBufAllocator;
import net.minecraft.util.io.netty.channel.ChannelConfig;
import net.minecraft.util.io.netty.channel.MessageSizeEstimator;
import net.minecraft.util.io.netty.channel.RecvByteBufAllocator;

public interface SctpChannelConfig extends ChannelConfig {
  boolean isSctpNoDelay();
  
  SctpChannelConfig setSctpNoDelay(boolean paramBoolean);
  
  int getSendBufferSize();
  
  SctpChannelConfig setSendBufferSize(int paramInt);
  
  int getReceiveBufferSize();
  
  SctpChannelConfig setReceiveBufferSize(int paramInt);
  
  SctpStandardSocketOptions.InitMaxStreams getInitMaxStreams();
  
  SctpChannelConfig setInitMaxStreams(SctpStandardSocketOptions.InitMaxStreams paramInitMaxStreams);
  
  SctpChannelConfig setConnectTimeoutMillis(int paramInt);
  
  SctpChannelConfig setMaxMessagesPerRead(int paramInt);
  
  SctpChannelConfig setWriteSpinCount(int paramInt);
  
  SctpChannelConfig setAllocator(ByteBufAllocator paramByteBufAllocator);
  
  SctpChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator paramRecvByteBufAllocator);
  
  SctpChannelConfig setAutoRead(boolean paramBoolean);
  
  SctpChannelConfig setWriteBufferHighWaterMark(int paramInt);
  
  SctpChannelConfig setWriteBufferLowWaterMark(int paramInt);
  
  SctpChannelConfig setMessageSizeEstimator(MessageSizeEstimator paramMessageSizeEstimator);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\sctp\SctpChannelConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */