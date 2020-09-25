/*    */ package net.minecraft.util.io.netty.channel.socket.nio;
/*    */ 
/*    */ import java.net.ProtocolFamily;
/*    */ import java.net.StandardProtocolFamily;
/*    */ import net.minecraft.util.io.netty.channel.socket.InternetProtocolFamily;
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
/*    */ final class ProtocolFamilyConverter
/*    */ {
/*    */   public static ProtocolFamily convert(InternetProtocolFamily family) {
/* 36 */     switch (family) {
/*    */       case IPv4:
/* 38 */         return StandardProtocolFamily.INET;
/*    */       case IPv6:
/* 40 */         return StandardProtocolFamily.INET6;
/*    */     } 
/* 42 */     throw new IllegalArgumentException();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\socket\nio\ProtocolFamilyConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */