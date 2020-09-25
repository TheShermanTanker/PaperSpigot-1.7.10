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
/*    */ public class PacketPlayInTransaction
/*    */   extends Packet
/*    */ {
/*    */   private int a;
/*    */   private short b;
/*    */   private boolean c;
/*    */   
/*    */   public void a(PacketPlayInListener paramPacketPlayInListener) {
/* 24 */     paramPacketPlayInListener.a(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) {
/* 29 */     this.a = paramPacketDataSerializer.readByte();
/* 30 */     this.b = paramPacketDataSerializer.readShort();
/* 31 */     this.c = (paramPacketDataSerializer.readByte() != 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) {
/* 36 */     paramPacketDataSerializer.writeByte(this.a);
/* 37 */     paramPacketDataSerializer.writeShort(this.b);
/* 38 */     paramPacketDataSerializer.writeByte(this.c ? 1 : 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public String b() {
/* 43 */     return String.format("id=%d, uid=%d, accepted=%b", new Object[] { Integer.valueOf(this.a), Short.valueOf(this.b), Boolean.valueOf(this.c) });
/*    */   }
/*    */   
/*    */   public int c() {
/* 47 */     return this.a;
/*    */   }
/*    */   
/*    */   public short d() {
/* 51 */     return this.b;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayInTransaction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */