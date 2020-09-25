/*    */ package net.minecraft.util.io.netty.channel.local;
/*    */ 
/*    */ import java.net.SocketAddress;
/*    */ import java.util.concurrent.ConcurrentMap;
/*    */ import net.minecraft.util.io.netty.channel.Channel;
/*    */ import net.minecraft.util.io.netty.channel.ChannelException;
/*    */ import net.minecraft.util.io.netty.util.internal.PlatformDependent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class LocalChannelRegistry
/*    */ {
/* 27 */   private static final ConcurrentMap<LocalAddress, Channel> boundChannels = PlatformDependent.newConcurrentHashMap();
/*    */ 
/*    */   
/*    */   static LocalAddress register(Channel channel, LocalAddress oldLocalAddress, SocketAddress localAddress) {
/* 31 */     if (oldLocalAddress != null) {
/* 32 */       throw new ChannelException("already bound");
/*    */     }
/* 34 */     if (!(localAddress instanceof LocalAddress)) {
/* 35 */       throw new ChannelException("unsupported address type: " + localAddress.getClass().getSimpleName());
/*    */     }
/*    */ 
/*    */     
/* 39 */     LocalAddress addr = (LocalAddress)localAddress;
/* 40 */     if (LocalAddress.ANY.equals(addr)) {
/* 41 */       addr = new LocalAddress(channel);
/*    */     }
/*    */     
/* 44 */     Channel boundChannel = boundChannels.putIfAbsent(addr, channel);
/* 45 */     if (boundChannel != null) {
/* 46 */       throw new ChannelException("address already in use by: " + boundChannel);
/*    */     }
/* 48 */     return addr;
/*    */   }
/*    */   
/*    */   static Channel get(SocketAddress localAddress) {
/* 52 */     return boundChannels.get(localAddress);
/*    */   }
/*    */   
/*    */   static void unregister(LocalAddress localAddress) {
/* 56 */     boundChannels.remove(localAddress);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\local\LocalChannelRegistry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */