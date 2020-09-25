/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayOutTransaction
/*    */   extends Packet
/*    */ {
/*    */   private int a;
/*    */   private short b;
/*    */   private boolean c;
/*    */   
/*    */   public PacketPlayOutTransaction() {}
/*    */   
/*    */   public PacketPlayOutTransaction(int paramInt, short paramShort, boolean paramBoolean) {
/* 19 */     this.a = paramInt;
/* 20 */     this.b = paramShort;
/* 21 */     this.c = paramBoolean;
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
/* 33 */     this.c = paramPacketDataSerializer.readBoolean();
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) {
/* 38 */     paramPacketDataSerializer.writeByte(this.a);
/* 39 */     paramPacketDataSerializer.writeShort(this.b);
/* 40 */     paramPacketDataSerializer.writeBoolean(this.c);
/*    */   }
/*    */ 
/*    */   
/*    */   public String b() {
/* 45 */     return String.format("id=%d, uid=%d, accepted=%b", new Object[] { Integer.valueOf(this.a), Short.valueOf(this.b), Boolean.valueOf(this.c) });
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutTransaction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */