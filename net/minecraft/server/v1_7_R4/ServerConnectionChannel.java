/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import net.minecraft.util.io.netty.channel.Channel;
/*    */ import net.minecraft.util.io.netty.channel.ChannelException;
/*    */ import net.minecraft.util.io.netty.channel.ChannelHandler;
/*    */ import net.minecraft.util.io.netty.channel.ChannelInitializer;
/*    */ import net.minecraft.util.io.netty.channel.ChannelOption;
/*    */ import net.minecraft.util.io.netty.handler.timeout.ReadTimeoutHandler;
/*    */ 
/*    */ class ServerConnectionChannel extends ChannelInitializer {
/*    */   final ServerConnection a;
/*    */   
/*    */   ServerConnectionChannel(ServerConnection serverconnection) {
/* 14 */     this.a = serverconnection;
/*    */   }
/*    */   
/*    */   protected void initChannel(Channel channel) {
/*    */     try {
/* 19 */       channel.config().setOption(ChannelOption.IP_TOS, Integer.valueOf(24));
/* 20 */     } catch (ChannelException channelexception) {}
/*    */ 
/*    */ 
/*    */     
/*    */     try {
/* 25 */       channel.config().setOption(ChannelOption.TCP_NODELAY, Boolean.valueOf(false));
/* 26 */     } catch (ChannelException channelexception1) {}
/*    */ 
/*    */ 
/*    */     
/* 30 */     channel.pipeline().addLast("timeout", (ChannelHandler)new ReadTimeoutHandler(30)).addLast("legacy_query", (ChannelHandler)new LegacyPingHandler(this.a)).addLast("splitter", (ChannelHandler)new PacketSplitter()).addLast("decoder", (ChannelHandler)new PacketDecoder(NetworkManager.h)).addLast("prepender", (ChannelHandler)new PacketPrepender()).addLast("encoder", (ChannelHandler)new PacketEncoder(NetworkManager.h));
/* 31 */     NetworkManager networkmanager = new NetworkManager(false);
/*    */     
/* 33 */     ServerConnection.a(this.a).add(networkmanager);
/* 34 */     channel.pipeline().addLast("packet_handler", (ChannelHandler)networkmanager);
/* 35 */     networkmanager.a(new HandshakeListener(ServerConnection.b(this.a), networkmanager));
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ServerConnectionChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */