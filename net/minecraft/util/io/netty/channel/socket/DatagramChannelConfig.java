package net.minecraft.util.io.netty.channel.socket;

import java.net.InetAddress;
import java.net.NetworkInterface;
import net.minecraft.util.io.netty.buffer.ByteBufAllocator;
import net.minecraft.util.io.netty.channel.ChannelConfig;
import net.minecraft.util.io.netty.channel.MessageSizeEstimator;
import net.minecraft.util.io.netty.channel.RecvByteBufAllocator;

public interface DatagramChannelConfig extends ChannelConfig {
  int getSendBufferSize();
  
  DatagramChannelConfig setSendBufferSize(int paramInt);
  
  int getReceiveBufferSize();
  
  DatagramChannelConfig setReceiveBufferSize(int paramInt);
  
  int getTrafficClass();
  
  DatagramChannelConfig setTrafficClass(int paramInt);
  
  boolean isReuseAddress();
  
  DatagramChannelConfig setReuseAddress(boolean paramBoolean);
  
  boolean isBroadcast();
  
  DatagramChannelConfig setBroadcast(boolean paramBoolean);
  
  boolean isLoopbackModeDisabled();
  
  DatagramChannelConfig setLoopbackModeDisabled(boolean paramBoolean);
  
  int getTimeToLive();
  
  DatagramChannelConfig setTimeToLive(int paramInt);
  
  InetAddress getInterface();
  
  DatagramChannelConfig setInterface(InetAddress paramInetAddress);
  
  NetworkInterface getNetworkInterface();
  
  DatagramChannelConfig setNetworkInterface(NetworkInterface paramNetworkInterface);
  
  DatagramChannelConfig setMaxMessagesPerRead(int paramInt);
  
  DatagramChannelConfig setWriteSpinCount(int paramInt);
  
  DatagramChannelConfig setConnectTimeoutMillis(int paramInt);
  
  DatagramChannelConfig setAllocator(ByteBufAllocator paramByteBufAllocator);
  
  DatagramChannelConfig setRecvByteBufAllocator(RecvByteBufAllocator paramRecvByteBufAllocator);
  
  DatagramChannelConfig setAutoRead(boolean paramBoolean);
  
  DatagramChannelConfig setMessageSizeEstimator(MessageSizeEstimator paramMessageSizeEstimator);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\socket\DatagramChannelConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */