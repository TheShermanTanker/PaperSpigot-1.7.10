/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class PacketPlayOutEntityTeleport
/*    */   extends Packet {
/*    */   private int a;
/*    */   private int b;
/*    */   private int c;
/*    */   private int d;
/*    */   private byte e;
/*    */   private byte f;
/*    */   private boolean onGround;
/*    */   private boolean heightCorrection;
/*    */   
/*    */   public PacketPlayOutEntityTeleport() {}
/*    */   
/*    */   public PacketPlayOutEntityTeleport(Entity entity) {
/* 17 */     this.a = entity.getId();
/* 18 */     this.b = MathHelper.floor(entity.locX * 32.0D);
/* 19 */     this.c = MathHelper.floor(entity.locY * 32.0D);
/* 20 */     this.d = MathHelper.floor(entity.locZ * 32.0D);
/* 21 */     this.e = (byte)(int)(entity.yaw * 256.0F / 360.0F);
/* 22 */     this.f = (byte)(int)(entity.pitch * 256.0F / 360.0F);
/*    */   }
/*    */   
/*    */   public PacketPlayOutEntityTeleport(int i, int j, int k, int l, byte b0, byte b1, boolean onGround, boolean heightCorrection) {
/* 26 */     this.a = i;
/* 27 */     this.b = j;
/* 28 */     this.c = k;
/* 29 */     this.d = l;
/* 30 */     this.e = b0;
/* 31 */     this.f = b1;
/* 32 */     this.onGround = onGround;
/* 33 */     this.heightCorrection = heightCorrection;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PacketPlayOutEntityTeleport(int i, int j, int k, int l, byte b0, byte b1, boolean onGround) {
/* 40 */     this(i, j, k, l, b0, b1, onGround, false);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PacketPlayOutEntityTeleport(int i, int j, int k, int l, byte b0, byte b1) {
/* 47 */     this(i, j, k, l, b0, b1, false, false);
/*    */   }
/*    */   
/*    */   public void a(PacketDataSerializer packetdataserializer) {
/* 51 */     this.a = packetdataserializer.readInt();
/* 52 */     this.b = packetdataserializer.readInt();
/* 53 */     this.c = packetdataserializer.readInt();
/* 54 */     this.d = packetdataserializer.readInt();
/* 55 */     this.e = packetdataserializer.readByte();
/* 56 */     this.f = packetdataserializer.readByte();
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(PacketDataSerializer packetdataserializer) {
/* 61 */     if (packetdataserializer.version < 16) {
/*    */       
/* 63 */       packetdataserializer.writeInt(this.a);
/*    */     } else {
/*    */       
/* 66 */       packetdataserializer.b(this.a);
/*    */     } 
/*    */     
/* 69 */     packetdataserializer.writeInt(this.b);
/* 70 */     packetdataserializer.writeInt((packetdataserializer.version >= 16 && this.heightCorrection) ? (this.c - 16) : this.c);
/* 71 */     packetdataserializer.writeInt(this.d);
/* 72 */     packetdataserializer.writeByte(this.e);
/* 73 */     packetdataserializer.writeByte(this.f);
/*    */     
/* 75 */     if (packetdataserializer.version >= 22)
/*    */     {
/* 77 */       packetdataserializer.writeBoolean(this.onGround);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(PacketPlayOutListener packetplayoutlistener) {
/* 83 */     packetplayoutlistener.a(this);
/*    */   }
/*    */   
/*    */   public void handle(PacketListener packetlistener) {
/* 87 */     a((PacketPlayOutListener)packetlistener);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutEntityTeleport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */