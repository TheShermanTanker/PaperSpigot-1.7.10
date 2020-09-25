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
/*    */ public class PacketPlayInSetCreativeSlot
/*    */   extends Packet
/*    */ {
/*    */   private int slot;
/*    */   private ItemStack b;
/*    */   
/*    */   public void a(PacketPlayInListener paramPacketPlayInListener) {
/* 23 */     paramPacketPlayInListener.a(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) {
/* 28 */     this.slot = paramPacketDataSerializer.readShort();
/* 29 */     this.b = paramPacketDataSerializer.c();
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) {
/* 34 */     paramPacketDataSerializer.writeShort(this.slot);
/* 35 */     paramPacketDataSerializer.a(this.b);
/*    */   }
/*    */   
/*    */   public int c() {
/* 39 */     return this.slot;
/*    */   }
/*    */   
/*    */   public ItemStack getItemStack() {
/* 43 */     return this.b;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayInSetCreativeSlot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */