/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PacketPlayOutAbilities
/*     */   extends Packet
/*     */ {
/*     */   private boolean a;
/*     */   private boolean b;
/*     */   private boolean c;
/*     */   private boolean d;
/*     */   private float e;
/*     */   private float f;
/*     */   
/*     */   public PacketPlayOutAbilities() {}
/*     */   
/*     */   public PacketPlayOutAbilities(PlayerAbilities paramPlayerAbilities) {
/*  28 */     a(paramPlayerAbilities.isInvulnerable);
/*  29 */     b(paramPlayerAbilities.isFlying);
/*  30 */     c(paramPlayerAbilities.canFly);
/*  31 */     d(paramPlayerAbilities.canInstantlyBuild);
/*  32 */     a(paramPlayerAbilities.a());
/*  33 */     b(paramPlayerAbilities.b());
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(PacketDataSerializer paramPacketDataSerializer) {
/*  38 */     byte b = paramPacketDataSerializer.readByte();
/*     */     
/*  40 */     a(((b & 0x1) > 0));
/*  41 */     b(((b & 0x2) > 0));
/*  42 */     c(((b & 0x4) > 0));
/*  43 */     d(((b & 0x8) > 0));
/*  44 */     a(paramPacketDataSerializer.readFloat());
/*  45 */     b(paramPacketDataSerializer.readFloat());
/*     */   }
/*     */ 
/*     */   
/*     */   public void b(PacketDataSerializer paramPacketDataSerializer) {
/*  50 */     byte b = 0;
/*     */     
/*  52 */     if (c()) b = (byte)(b | 0x1); 
/*  53 */     if (d()) b = (byte)(b | 0x2); 
/*  54 */     if (e()) b = (byte)(b | 0x4); 
/*  55 */     if (f()) b = (byte)(b | 0x8);
/*     */     
/*  57 */     paramPacketDataSerializer.writeByte(b);
/*  58 */     paramPacketDataSerializer.writeFloat(this.e);
/*  59 */     paramPacketDataSerializer.writeFloat(this.f);
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(PacketPlayOutListener paramPacketPlayOutListener) {
/*  64 */     paramPacketPlayOutListener.a(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public String b() {
/*  69 */     return String.format("invuln=%b, flying=%b, canfly=%b, instabuild=%b, flyspeed=%.4f, walkspped=%.4f", new Object[] { Boolean.valueOf(c()), Boolean.valueOf(d()), Boolean.valueOf(e()), Boolean.valueOf(f()), Float.valueOf(g()), Float.valueOf(h()) });
/*     */   }
/*     */   
/*     */   public boolean c() {
/*  73 */     return this.a;
/*     */   }
/*     */   
/*     */   public void a(boolean paramBoolean) {
/*  77 */     this.a = paramBoolean;
/*     */   }
/*     */   
/*     */   public boolean d() {
/*  81 */     return this.b;
/*     */   }
/*     */   
/*     */   public void b(boolean paramBoolean) {
/*  85 */     this.b = paramBoolean;
/*     */   }
/*     */   
/*     */   public boolean e() {
/*  89 */     return this.c;
/*     */   }
/*     */   
/*     */   public void c(boolean paramBoolean) {
/*  93 */     this.c = paramBoolean;
/*     */   }
/*     */   
/*     */   public boolean f() {
/*  97 */     return this.d;
/*     */   }
/*     */   
/*     */   public void d(boolean paramBoolean) {
/* 101 */     this.d = paramBoolean;
/*     */   }
/*     */   
/*     */   public float g() {
/* 105 */     return this.e;
/*     */   }
/*     */   
/*     */   public void a(float paramFloat) {
/* 109 */     this.e = paramFloat;
/*     */   }
/*     */   
/*     */   public float h() {
/* 113 */     return this.f;
/*     */   }
/*     */   
/*     */   public void b(float paramFloat) {
/* 117 */     this.f = paramFloat;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayOutAbilities.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */