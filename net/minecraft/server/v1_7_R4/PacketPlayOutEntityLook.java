/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class PacketPlayOutEntityLook
/*    */   extends PacketPlayOutEntity {
/*    */   private boolean onGround;
/*    */   
/*    */   public PacketPlayOutEntityLook() {
/*  8 */     this.g = true;
/*    */   }
/*    */   
/*    */   public PacketPlayOutEntityLook(int i, byte b0, byte b1, boolean onGround) {
/* 12 */     super(i);
/* 13 */     this.e = b0;
/* 14 */     this.f = b1;
/* 15 */     this.g = true;
/* 16 */     this.onGround = onGround;
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) {
/* 20 */     super.a(packetdataserializer);
/* 21 */     this.e = packetdataserializer.readByte();
/* 22 */     this.f = packetdataserializer.readByte();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) {
/* 26 */     super.b(packetdataserializer);
/* 27 */     packetdataserializer.writeByte(this.e);
/* 28 */     packetdataserializer.writeByte(this.f);
/*    */     
/* 30 */     if (packetdataserializer.version >= 22)
/*    */     {
/* 32 */       packetdataserializer.writeBoolean(this.onGround);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public String b() {
/* 38 */     return super.b() + String.format(", yRot=%d, xRot=%d", new Object[] { Byte.valueOf(this.e), Byte.valueOf(this.f) });
/*    */   }
/*    */   
/*    */   public void handle(PacketListener packetlistener) {
/* 42 */     a((PacketPlayOutListener)packetlistener);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutEntityLook.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */