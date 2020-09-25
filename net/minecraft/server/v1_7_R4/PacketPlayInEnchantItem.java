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
/*    */ public class PacketPlayInEnchantItem
/*    */   extends Packet
/*    */ {
/*    */   private int a;
/*    */   private int b;
/*    */   
/*    */   public void a(PacketPlayInListener paramPacketPlayInListener) {
/* 22 */     paramPacketPlayInListener.a(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) {
/* 27 */     this.a = paramPacketDataSerializer.readByte();
/* 28 */     this.b = paramPacketDataSerializer.readByte();
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) {
/* 33 */     paramPacketDataSerializer.writeByte(this.a);
/* 34 */     paramPacketDataSerializer.writeByte(this.b);
/*    */   }
/*    */ 
/*    */   
/*    */   public String b() {
/* 39 */     return String.format("id=%d, button=%d", new Object[] { Integer.valueOf(this.a), Integer.valueOf(this.b) });
/*    */   }
/*    */   
/*    */   public int c() {
/* 43 */     return this.a;
/*    */   }
/*    */   
/*    */   public int d() {
/* 47 */     return this.b;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayInEnchantItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */