/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketStatusOutPong
/*    */   extends Packet
/*    */ {
/*    */   private long a;
/*    */   
/*    */   public PacketStatusOutPong() {}
/*    */   
/*    */   public PacketStatusOutPong(long paramLong) {
/* 16 */     this.a = paramLong;
/*    */   }
/*    */ 
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
/*    */   public void a(PacketStatusOutListener paramPacketStatusOutListener) {
/* 31 */     paramPacketStatusOutListener.a(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean a() {
/* 36 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketStatusOutPong.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */