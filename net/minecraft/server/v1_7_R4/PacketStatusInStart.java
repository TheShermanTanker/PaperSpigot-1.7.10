/*    */ package net.minecraft.server.v1_7_R4;
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
/*    */ public class PacketStatusInStart
/*    */   extends Packet
/*    */ {
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) {}
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) {}
/*    */   
/*    */   public void a(PacketStatusInListener paramPacketStatusInListener) {
/* 25 */     paramPacketStatusInListener.a(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean a() {
/* 30 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketStatusInStart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */