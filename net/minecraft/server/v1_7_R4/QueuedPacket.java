/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import net.minecraft.util.io.netty.util.concurrent.GenericFutureListener;
/*    */ 
/*    */ 
/*    */ class QueuedPacket
/*    */ {
/*    */   private final Packet a;
/*    */   private final GenericFutureListener[] b;
/*    */   
/*    */   public QueuedPacket(Packet packet, GenericFutureListener... agenericfuturelistener) {
/* 12 */     this.a = packet;
/* 13 */     this.b = agenericfuturelistener;
/*    */   }
/*    */   
/*    */   static Packet a(QueuedPacket queuedpacket) {
/* 17 */     return queuedpacket.a;
/*    */   }
/*    */   
/*    */   static GenericFutureListener[] b(QueuedPacket queuedpacket) {
/* 21 */     return queuedpacket.b;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\QueuedPacket.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */