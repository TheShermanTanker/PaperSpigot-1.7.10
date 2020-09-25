package net.minecraft.util.io.netty.channel.udt;

import java.net.InetSocketAddress;
import net.minecraft.util.io.netty.channel.Channel;

public interface UdtChannel extends Channel {
  UdtChannelConfig config();
  
  InetSocketAddress localAddress();
  
  InetSocketAddress remoteAddress();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channe\\udt\UdtChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */