/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.List;
/*     */ 
/*     */ public class PacketPlayOutSpawnEntityLiving
/*     */   extends Packet {
/*     */   private int a;
/*     */   private int b;
/*     */   private int c;
/*     */   private int d;
/*     */   private int e;
/*     */   private int f;
/*     */   private int g;
/*     */   private int h;
/*     */   private byte i;
/*     */   private byte j;
/*     */   private byte k;
/*     */   private DataWatcher l;
/*     */   private List m;
/*     */   
/*     */   public PacketPlayOutSpawnEntityLiving() {}
/*     */   
/*     */   public PacketPlayOutSpawnEntityLiving(EntityLiving entityliving) {
/*  24 */     this.a = entityliving.getId();
/*  25 */     this.b = (byte)EntityTypes.a(entityliving);
/*  26 */     this.c = entityliving.as.a(entityliving.locX);
/*  27 */     this.d = MathHelper.floor(entityliving.locY * 32.0D);
/*  28 */     this.e = entityliving.as.a(entityliving.locZ);
/*  29 */     this.i = (byte)(int)(entityliving.yaw * 256.0F / 360.0F);
/*  30 */     this.j = (byte)(int)(entityliving.pitch * 256.0F / 360.0F);
/*  31 */     this.k = (byte)(int)(entityliving.aO * 256.0F / 360.0F);
/*  32 */     double d0 = 3.9D;
/*  33 */     double d1 = entityliving.motX;
/*  34 */     double d2 = entityliving.motY;
/*  35 */     double d3 = entityliving.motZ;
/*     */     
/*  37 */     if (d1 < -d0) {
/*  38 */       d1 = -d0;
/*     */     }
/*     */     
/*  41 */     if (d2 < -d0) {
/*  42 */       d2 = -d0;
/*     */     }
/*     */     
/*  45 */     if (d3 < -d0) {
/*  46 */       d3 = -d0;
/*     */     }
/*     */     
/*  49 */     if (d1 > d0) {
/*  50 */       d1 = d0;
/*     */     }
/*     */     
/*  53 */     if (d2 > d0) {
/*  54 */       d2 = d0;
/*     */     }
/*     */     
/*  57 */     if (d3 > d0) {
/*  58 */       d3 = d0;
/*     */     }
/*     */     
/*  61 */     this.f = (int)(d1 * 8000.0D);
/*  62 */     this.g = (int)(d2 * 8000.0D);
/*  63 */     this.h = (int)(d3 * 8000.0D);
/*  64 */     this.l = entityliving.getDataWatcher();
/*     */   }
/*     */   
/*     */   public void a(PacketDataSerializer packetdataserializer) {
/*  68 */     this.a = packetdataserializer.a();
/*  69 */     this.b = packetdataserializer.readByte() & 0xFF;
/*  70 */     this.c = packetdataserializer.readInt();
/*  71 */     this.d = packetdataserializer.readInt();
/*  72 */     this.e = packetdataserializer.readInt();
/*  73 */     this.i = packetdataserializer.readByte();
/*  74 */     this.j = packetdataserializer.readByte();
/*  75 */     this.k = packetdataserializer.readByte();
/*  76 */     this.f = packetdataserializer.readShort();
/*  77 */     this.g = packetdataserializer.readShort();
/*  78 */     this.h = packetdataserializer.readShort();
/*  79 */     this.m = DataWatcher.b(packetdataserializer);
/*     */   }
/*     */   
/*     */   public void b(PacketDataSerializer packetdataserializer) {
/*  83 */     packetdataserializer.b(this.a);
/*  84 */     packetdataserializer.writeByte(this.b & 0xFF);
/*  85 */     packetdataserializer.writeInt(this.c);
/*  86 */     packetdataserializer.writeInt(this.d);
/*  87 */     packetdataserializer.writeInt(this.e);
/*  88 */     packetdataserializer.writeByte(this.i);
/*  89 */     packetdataserializer.writeByte(this.j);
/*  90 */     packetdataserializer.writeByte(this.k);
/*  91 */     packetdataserializer.writeShort(this.f);
/*  92 */     packetdataserializer.writeShort(this.g);
/*  93 */     packetdataserializer.writeShort(this.h);
/*  94 */     this.l.a(packetdataserializer, packetdataserializer.version);
/*     */   }
/*     */   
/*     */   public void a(PacketPlayOutListener packetplayoutlistener) {
/*  98 */     packetplayoutlistener.a(this);
/*     */   }
/*     */   
/*     */   public String b() {
/* 102 */     return String.format("id=%d, type=%d, x=%.2f, y=%.2f, z=%.2f, xd=%.2f, yd=%.2f, zd=%.2f", new Object[] { Integer.valueOf(this.a), Integer.valueOf(this.b), Float.valueOf(this.c / 32.0F), Float.valueOf(this.d / 32.0F), Float.valueOf(this.e / 32.0F), Float.valueOf(this.f / 8000.0F), Float.valueOf(this.g / 8000.0F), Float.valueOf(this.h / 8000.0F) });
/*     */   }
/*     */   
/*     */   public void handle(PacketListener packetlistener) {
/* 106 */     a((PacketPlayOutListener)packetlistener);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutSpawnEntityLiving.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */