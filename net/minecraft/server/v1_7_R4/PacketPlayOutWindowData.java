/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutWindowData
/*    */   extends Packet
/*    */ {
/*    */   private int a;
/*    */   private int b;
/*    */   private int c;
/*    */   
/*    */   public PacketPlayOutWindowData() {}
/*    */   
/*    */   public PacketPlayOutWindowData(int paramInt1, int paramInt2, int paramInt3) {
/* 19 */     this.a = paramInt1;
/* 20 */     this.b = paramInt2;
/* 21 */     this.c = paramInt3;
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketPlayOutListener paramPacketPlayOutListener) {
/* 26 */     paramPacketPlayOutListener.a(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) {
/* 31 */     this.a = paramPacketDataSerializer.readUnsignedByte();
/* 32 */     this.b = paramPacketDataSerializer.readShort();
/* 33 */     this.c = paramPacketDataSerializer.readShort();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) {
/* 39 */     paramPacketDataSerializer.writeByte(this.a);
/* 40 */     paramPacketDataSerializer.writeShort(this.b);
/* 41 */     paramPacketDataSerializer.writeShort(this.c);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutWindowData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */