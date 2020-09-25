/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutHeldItemSlot
/*    */   extends Packet
/*    */ {
/*    */   private int a;
/*    */   
/*    */   public PacketPlayOutHeldItemSlot() {}
/*    */   
/*    */   public PacketPlayOutHeldItemSlot(int paramInt) {
/* 17 */     this.a = paramInt;
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) {
/* 22 */     this.a = paramPacketDataSerializer.readByte();
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) {
/* 27 */     paramPacketDataSerializer.writeByte(this.a);
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketPlayOutListener paramPacketPlayOutListener) {
/* 32 */     paramPacketPlayOutListener.a(this);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutHeldItemSlot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */