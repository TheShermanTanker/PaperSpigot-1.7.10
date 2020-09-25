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
/*    */ public class PacketPlayOutEntityStatus
/*    */   extends Packet
/*    */ {
/*    */   private int a;
/*    */   private byte b;
/*    */   
/*    */   public PacketPlayOutEntityStatus() {}
/*    */   
/*    */   public PacketPlayOutEntityStatus(Entity paramEntity, byte paramByte) {
/* 20 */     this.a = paramEntity.getId();
/* 21 */     this.b = paramByte;
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) {
/* 26 */     this.a = paramPacketDataSerializer.readInt();
/* 27 */     this.b = paramPacketDataSerializer.readByte();
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) {
/* 32 */     paramPacketDataSerializer.writeInt(this.a);
/* 33 */     paramPacketDataSerializer.writeByte(this.b);
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketPlayOutListener paramPacketPlayOutListener) {
/* 38 */     paramPacketPlayOutListener.a(this);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutEntityStatus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */