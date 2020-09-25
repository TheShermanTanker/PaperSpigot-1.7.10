/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class PacketPlayOutPosition
/*    */   extends Packet
/*    */ {
/*    */   private double a;
/*    */   private double b;
/*    */   private double c;
/*    */   private float d;
/*    */   private float e;
/*    */   private boolean f;
/*    */   private byte relativeBitMask;
/*    */   
/*    */   public PacketPlayOutPosition() {}
/*    */   
/*    */   public PacketPlayOutPosition(double d0, double d1, double d2, float f, float f1, boolean flag) {
/* 17 */     this(d0, d1, d2, f, f1, flag, (byte)0);
/*    */   }
/*    */   
/*    */   public PacketPlayOutPosition(double d0, double d1, double d2, float f, float f1, boolean flag, byte relativeBitMask) {
/* 21 */     this.a = d0;
/* 22 */     this.b = d1;
/* 23 */     this.c = d2;
/* 24 */     this.d = f;
/* 25 */     this.e = f1;
/* 26 */     this.f = flag;
/* 27 */     this.relativeBitMask = relativeBitMask;
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) {
/* 32 */     this.a = packetdataserializer.readDouble();
/* 33 */     this.b = packetdataserializer.readDouble();
/* 34 */     this.c = packetdataserializer.readDouble();
/* 35 */     this.d = packetdataserializer.readFloat();
/* 36 */     this.e = packetdataserializer.readFloat();
/* 37 */     this.f = packetdataserializer.readBoolean();
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) {
/* 42 */     packetdataserializer.writeDouble(this.a);
/* 43 */     packetdataserializer.writeDouble(this.b - ((packetdataserializer.version >= 16) ? 1.62D : 0.0D));
/* 44 */     packetdataserializer.writeDouble(this.c);
/* 45 */     packetdataserializer.writeFloat(this.d);
/* 46 */     packetdataserializer.writeFloat(this.e);
/* 47 */     if (packetdataserializer.version < 16) {
/*    */       
/* 49 */       packetdataserializer.writeBoolean(this.f);
/*    */     } else {
/*    */       
/* 52 */       packetdataserializer.writeByte(this.relativeBitMask);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketPlayOutListener packetplayoutlistener) {
/* 58 */     packetplayoutlistener.a(this);
/*    */   }
/*    */   
/*    */   public void handle(PacketListener packetlistener) {
/* 62 */     a((PacketPlayOutListener)packetlistener);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutPosition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */