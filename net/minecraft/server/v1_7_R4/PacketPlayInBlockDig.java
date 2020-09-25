/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ public class PacketPlayInBlockDig
/*    */   extends Packet
/*    */ {
/*    */   private int a;
/*    */   private int b;
/*    */   private int c;
/*    */   private int face;
/*    */   private int e;
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) {
/* 14 */     this.e = packetdataserializer.readUnsignedByte();
/*    */     
/* 16 */     if (packetdataserializer.version < 16) {
/*    */       
/* 18 */       this.a = packetdataserializer.readInt();
/* 19 */       this.b = packetdataserializer.readUnsignedByte();
/* 20 */       this.c = packetdataserializer.readInt();
/*    */     } else {
/*    */       
/* 23 */       long position = packetdataserializer.readLong();
/* 24 */       this.a = packetdataserializer.readPositionX(position);
/* 25 */       this.b = packetdataserializer.readPositionY(position);
/* 26 */       this.c = packetdataserializer.readPositionZ(position);
/*    */     } 
/*    */     
/* 29 */     this.face = packetdataserializer.readUnsignedByte();
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) {
/* 33 */     packetdataserializer.writeByte(this.e);
/* 34 */     packetdataserializer.writeInt(this.a);
/* 35 */     packetdataserializer.writeByte(this.b);
/* 36 */     packetdataserializer.writeInt(this.c);
/* 37 */     packetdataserializer.writeByte(this.face);
/*    */   }
/*    */   
/*    */   public void a(PacketPlayInListener packetplayinlistener) {
/* 41 */     packetplayinlistener.a(this);
/*    */   }
/*    */   
/*    */   public int c() {
/* 45 */     return this.a;
/*    */   }
/*    */   
/*    */   public int d() {
/* 49 */     return this.b;
/*    */   }
/*    */   
/*    */   public int e() {
/* 53 */     return this.c;
/*    */   }
/*    */   
/*    */   public int f() {
/* 57 */     return this.face;
/*    */   }
/*    */   
/*    */   public int g() {
/* 61 */     return this.e;
/*    */   }
/*    */   
/*    */   public void handle(PacketListener packetlistener) {
/* 65 */     a((PacketPlayInListener)packetlistener);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayInBlockDig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */