/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class PacketPlayOutRelEntityMoveLook
/*    */   extends PacketPlayOutEntity {
/*    */   private boolean onGround;
/*    */   
/*    */   public PacketPlayOutRelEntityMoveLook() {
/*  8 */     this.g = true;
/*    */   }
/*    */   
/*    */   public PacketPlayOutRelEntityMoveLook(int i, byte b0, byte b1, byte b2, byte b3, byte b4, boolean onGround) {
/* 12 */     super(i);
/* 13 */     this.b = b0;
/* 14 */     this.c = b1;
/* 15 */     this.d = b2;
/* 16 */     this.e = b3;
/* 17 */     this.f = b4;
/* 18 */     this.g = true;
/* 19 */     this.onGround = onGround;
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) {
/* 23 */     super.a(packetdataserializer);
/* 24 */     this.b = packetdataserializer.readByte();
/* 25 */     this.c = packetdataserializer.readByte();
/* 26 */     this.d = packetdataserializer.readByte();
/* 27 */     this.e = packetdataserializer.readByte();
/* 28 */     this.f = packetdataserializer.readByte();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) {
/* 32 */     super.b(packetdataserializer);
/* 33 */     packetdataserializer.writeByte(this.b);
/* 34 */     packetdataserializer.writeByte(this.c);
/* 35 */     packetdataserializer.writeByte(this.d);
/* 36 */     packetdataserializer.writeByte(this.e);
/* 37 */     packetdataserializer.writeByte(this.f);
/*    */     
/* 39 */     if (packetdataserializer.version >= 22)
/*    */     {
/* 41 */       packetdataserializer.writeBoolean(this.onGround);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public String b() {
/* 47 */     return super.b() + String.format(", xa=%d, ya=%d, za=%d, yRot=%d, xRot=%d", new Object[] { Byte.valueOf(this.b), Byte.valueOf(this.c), Byte.valueOf(this.d), Byte.valueOf(this.e), Byte.valueOf(this.f) });
/*    */   }
/*    */   
/*    */   public void handle(PacketListener packetlistener) {
/* 51 */     a((PacketPlayOutListener)packetlistener);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutRelEntityMoveLook.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */