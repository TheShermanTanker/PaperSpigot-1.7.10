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
/*    */ public class PacketPlayOutAnimation
/*    */   extends Packet
/*    */ {
/*    */   private int a;
/*    */   private int b;
/*    */   
/*    */   public PacketPlayOutAnimation() {}
/*    */   
/*    */   public PacketPlayOutAnimation(Entity paramEntity, int paramInt) {
/* 26 */     this.a = paramEntity.getId();
/* 27 */     this.b = paramInt;
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) {
/* 32 */     this.a = paramPacketDataSerializer.a();
/* 33 */     this.b = paramPacketDataSerializer.readUnsignedByte();
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) {
/* 38 */     paramPacketDataSerializer.b(this.a);
/* 39 */     paramPacketDataSerializer.writeByte(this.b);
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketPlayOutListener paramPacketPlayOutListener) {
/* 44 */     paramPacketPlayOutListener.a(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public String b() {
/* 49 */     return String.format("id=%d, type=%d", new Object[] { Integer.valueOf(this.a), Integer.valueOf(this.b) });
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutAnimation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */