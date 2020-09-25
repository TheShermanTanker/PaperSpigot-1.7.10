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
/*    */ public class PacketPlayOutAttachEntity
/*    */   extends Packet
/*    */ {
/*    */   private int a;
/*    */   private int b;
/*    */   private int c;
/*    */   
/*    */   public PacketPlayOutAttachEntity() {}
/*    */   
/*    */   public PacketPlayOutAttachEntity(int paramInt, Entity paramEntity1, Entity paramEntity2) {
/* 22 */     this.a = paramInt;
/* 23 */     this.b = paramEntity1.getId();
/* 24 */     this.c = (paramEntity2 != null) ? paramEntity2.getId() : -1;
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketDataSerializer paramPacketDataSerializer) {
/* 29 */     this.b = paramPacketDataSerializer.readInt();
/* 30 */     this.c = paramPacketDataSerializer.readInt();
/* 31 */     this.a = paramPacketDataSerializer.readUnsignedByte();
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(PacketDataSerializer paramPacketDataSerializer) {
/* 36 */     paramPacketDataSerializer.writeInt(this.b);
/* 37 */     paramPacketDataSerializer.writeInt(this.c);
/* 38 */     paramPacketDataSerializer.writeByte(this.a);
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketPlayOutListener paramPacketPlayOutListener) {
/* 43 */     paramPacketPlayOutListener.a(this);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutAttachEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */