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
/*    */ public class PacketStatusInPing
/*    */   extends Packet
/*    */ {
/*    */   private long a;
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) {
/* 21 */     this.a = paramPacketDataSerializer.readLong();
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) {
/* 26 */     paramPacketDataSerializer.writeLong(this.a);
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketStatusInListener paramPacketStatusInListener) {
/* 31 */     paramPacketStatusInListener.a(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean a() {
/* 36 */     return true;
/*    */   }
/*    */   
/*    */   public long c() {
/* 40 */     return this.a;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketStatusInPing.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */