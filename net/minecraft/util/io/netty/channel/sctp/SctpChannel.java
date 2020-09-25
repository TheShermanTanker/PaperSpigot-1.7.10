package net.minecraft.util.io.netty.channel.sctp;

import com.sun.nio.sctp.Association;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Set;
import net.minecraft.util.io.netty.channel.Channel;
import net.minecraft.util.io.netty.channel.ChannelFuture;
import net.minecraft.util.io.netty.channel.ChannelPromise;

public interface SctpChannel extends Channel {
  SctpServerChannel parent();
  
  Association association();
  
  InetSocketAddress localAddress();
  
  Set<InetSocketAddress> allLocalAddresses();
  
  SctpChannelConfig config();
  
  InetSocketAddress remoteAddress();
  
  Set<InetSocketAddress> allRemoteAddresses();
  
  ChannelFuture bindAddress(InetAddress paramInetAddress);
  
  ChannelFuture bindAddress(InetAddress paramInetAddress, ChannelPromise paramChannelPromise);
  
  ChannelFuture unbindAddress(InetAddress paramInetAddress);
  
  ChannelFuture unbindAddress(InetAddress paramInetAddress, ChannelPromise paramChannelPromise);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\sctp\SctpChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */