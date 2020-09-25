/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PacketPlayInBlockPlace
/*    */   extends Packet
/*    */ {
/*    */   private int a;
/*    */   private int b;
/*    */   private int c;
/*    */   private int d;
/*    */   private ItemStack e;
/*    */   private float f;
/*    */   private float g;
/*    */   private float h;
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) {
/* 18 */     if (packetdataserializer.version < 16) {
/*    */       
/* 20 */       this.a = packetdataserializer.readInt();
/* 21 */       this.b = packetdataserializer.readUnsignedByte();
/* 22 */       this.c = packetdataserializer.readInt();
/*    */     } else {
/*    */       
/* 25 */       long position = packetdataserializer.readLong();
/* 26 */       this.a = packetdataserializer.readPositionX(position);
/* 27 */       this.b = packetdataserializer.readPositionY(position);
/* 28 */       this.c = packetdataserializer.readPositionZ(position);
/*    */     } 
/*    */     
/* 31 */     this.d = packetdataserializer.readUnsignedByte();
/* 32 */     this.e = packetdataserializer.c();
/* 33 */     this.f = packetdataserializer.readUnsignedByte() / 16.0F;
/* 34 */     this.g = packetdataserializer.readUnsignedByte() / 16.0F;
/* 35 */     this.h = packetdataserializer.readUnsignedByte() / 16.0F;
/*    */   }
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) {
/* 39 */     packetdataserializer.writeInt(this.a);
/* 40 */     packetdataserializer.writeByte(this.b);
/* 41 */     packetdataserializer.writeInt(this.c);
/* 42 */     packetdataserializer.writeByte(this.d);
/* 43 */     packetdataserializer.a(this.e);
/* 44 */     packetdataserializer.writeByte((int)(this.f * 16.0F));
/* 45 */     packetdataserializer.writeByte((int)(this.g * 16.0F));
/* 46 */     packetdataserializer.writeByte((int)(this.h * 16.0F));
/*    */   }
/*    */   
/*    */   public void a(PacketPlayInListener packetplayinlistener) {
/* 50 */     packetplayinlistener.a(this);
/*    */   }
/*    */   
/*    */   public int c() {
/* 54 */     return this.a;
/*    */   }
/*    */   
/*    */   public int d() {
/* 58 */     return this.b;
/*    */   }
/*    */   
/*    */   public int e() {
/* 62 */     return this.c;
/*    */   }
/*    */   
/*    */   public int getFace() {
/* 66 */     return this.d;
/*    */   }
/*    */   
/*    */   public ItemStack getItemStack() {
/* 70 */     return this.e;
/*    */   }
/*    */   
/*    */   public float h() {
/* 74 */     return this.f;
/*    */   }
/*    */   
/*    */   public float i() {
/* 78 */     return this.g;
/*    */   }
/*    */   
/*    */   public float j() {
/* 82 */     return this.h;
/*    */   }
/*    */   
/*    */   public void handle(PacketListener packetlistener) {
/* 86 */     a((PacketPlayInListener)packetlistener);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayInBlockPlace.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */