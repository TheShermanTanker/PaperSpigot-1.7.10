/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ public class PacketPlayInSteerVehicle
/*    */   extends Packet
/*    */ {
/*    */   private float a;
/*    */   private float b;
/*    */   private boolean c;
/*    */   private boolean d;
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) {
/* 13 */     this.a = packetdataserializer.readFloat();
/* 14 */     this.b = packetdataserializer.readFloat();
/*    */     
/* 16 */     if (packetdataserializer.version < 16) {
/*    */       
/* 18 */       this.c = packetdataserializer.readBoolean();
/* 19 */       this.d = packetdataserializer.readBoolean();
/*    */     } else {
/* 21 */       int flags = packetdataserializer.readUnsignedByte();
/* 22 */       this.c = ((flags & 0x1) != 0);
/* 23 */       this.d = ((flags & 0x2) != 0);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) {
/* 29 */     packetdataserializer.writeFloat(this.a);
/* 30 */     packetdataserializer.writeFloat(this.b);
/* 31 */     packetdataserializer.writeBoolean(this.c);
/* 32 */     packetdataserializer.writeBoolean(this.d);
/*    */   }
/*    */   
/*    */   public void a(PacketPlayInListener packetplayinlistener) {
/* 36 */     packetplayinlistener.a(this);
/*    */   }
/*    */   
/*    */   public float c() {
/* 40 */     return this.a;
/*    */   }
/*    */   
/*    */   public float d() {
/* 44 */     return this.b;
/*    */   }
/*    */   
/*    */   public boolean e() {
/* 48 */     return this.c;
/*    */   }
/*    */   
/*    */   public boolean f() {
/* 52 */     return this.d;
/*    */   }
/*    */   
/*    */   public void handle(PacketListener packetlistener) {
/* 56 */     a((PacketPlayInListener)packetlistener);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayInSteerVehicle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */