/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class PacketPlayOutRelEntityMove
/*    */   extends PacketPlayOutEntity {
/*    */   private boolean onGround;
/*    */   
/*    */   public PacketPlayOutRelEntityMove() {}
/*    */   
/*    */   public PacketPlayOutRelEntityMove(int i, byte b0, byte b1, byte b2, boolean onGround) {
/* 10 */     super(i);
/* 11 */     this.b = b0;
/* 12 */     this.c = b1;
/* 13 */     this.d = b2;
/* 14 */     this.onGround = onGround;
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) {
/* 18 */     super.a(packetdataserializer);
/* 19 */     this.b = packetdataserializer.readByte();
/* 20 */     this.c = packetdataserializer.readByte();
/* 21 */     this.d = packetdataserializer.readByte();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) {
/* 25 */     super.b(packetdataserializer);
/* 26 */     packetdataserializer.writeByte(this.b);
/* 27 */     packetdataserializer.writeByte(this.c);
/* 28 */     packetdataserializer.writeByte(this.d);
/*    */     
/* 30 */     if (packetdataserializer.version >= 22)
/*    */     {
/* 32 */       packetdataserializer.writeBoolean(this.onGround);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public String b() {
/* 38 */     return super.b() + String.format(", xa=%d, ya=%d, za=%d", new Object[] { Byte.valueOf(this.b), Byte.valueOf(this.c), Byte.valueOf(this.d) });
/*    */   }
/*    */   
/*    */   public void handle(PacketListener packetlistener) {
/* 42 */     a((PacketPlayOutListener)packetlistener);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutRelEntityMove.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */