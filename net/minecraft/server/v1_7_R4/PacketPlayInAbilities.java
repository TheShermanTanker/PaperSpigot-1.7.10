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
/*     */ public class PacketPlayInAbilities
/*     */   extends Packet
/*     */ {
/*     */   private boolean a;
/*     */   private boolean b;
/*     */   private boolean c;
/*     */   private boolean d;
/*     */   private float e;
/*     */   private float f;
/*     */   
/*     */   public PacketPlayInAbilities() {}
/*     */   
/*     */   public PacketPlayInAbilities(PlayerAbilities paramPlayerAbilities) {
/*  26 */     a(paramPlayerAbilities.isInvulnerable);
/*  27 */     b(paramPlayerAbilities.isFlying);
/*  28 */     c(paramPlayerAbilities.canFly);
/*  29 */     d(paramPlayerAbilities.canInstantlyBuild);
/*  30 */     a(paramPlayerAbilities.a());
/*  31 */     b(paramPlayerAbilities.b());
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(PacketDataSerializer paramPacketDataSerializer) {
/*  36 */     byte b = paramPacketDataSerializer.readByte();
/*     */     
/*  38 */     a(((b & 0x1) > 0));
/*  39 */     b(((b & 0x2) > 0));
/*  40 */     c(((b & 0x4) > 0));
/*  41 */     d(((b & 0x8) > 0));
/*  42 */     a(paramPacketDataSerializer.readFloat());
/*  43 */     b(paramPacketDataSerializer.readFloat());
/*     */   }
/*     */ 
/*     */   
/*     */   public void b(PacketDataSerializer paramPacketDataSerializer) {
/*  48 */     byte b = 0;
/*     */     
/*  50 */     if (c()) b = (byte)(b | 0x1); 
/*  51 */     if (isFlying()) b = (byte)(b | 0x2); 
/*  52 */     if (e()) b = (byte)(b | 0x4); 
/*  53 */     if (f()) b = (byte)(b | 0x8);
/*     */     
/*  55 */     paramPacketDataSerializer.writeByte(b);
/*  56 */     paramPacketDataSerializer.writeFloat(this.e);
/*  57 */     paramPacketDataSerializer.writeFloat(this.f);
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(PacketPlayInListener paramPacketPlayInListener) {
/*  62 */     paramPacketPlayInListener.a(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public String b() {
/*  67 */     return String.format("invuln=%b, flying=%b, canfly=%b, instabuild=%b, flyspeed=%.4f, walkspped=%.4f", new Object[] { Boolean.valueOf(c()), Boolean.valueOf(isFlying()), Boolean.valueOf(e()), Boolean.valueOf(f()), Float.valueOf(g()), Float.valueOf(h()) });
/*     */   }
/*     */   
/*     */   public boolean c() {
/*  71 */     return this.a;
/*     */   }
/*     */   
/*     */   public void a(boolean paramBoolean) {
/*  75 */     this.a = paramBoolean;
/*     */   }
/*     */   
/*     */   public boolean isFlying() {
/*  79 */     return this.b;
/*     */   }
/*     */   
/*     */   public void b(boolean paramBoolean) {
/*  83 */     this.b = paramBoolean;
/*     */   }
/*     */   
/*     */   public boolean e() {
/*  87 */     return this.c;
/*     */   }
/*     */   
/*     */   public void c(boolean paramBoolean) {
/*  91 */     this.c = paramBoolean;
/*     */   }
/*     */   
/*     */   public boolean f() {
/*  95 */     return this.d;
/*     */   }
/*     */   
/*     */   public void d(boolean paramBoolean) {
/*  99 */     this.d = paramBoolean;
/*     */   }
/*     */   
/*     */   public float g() {
/* 103 */     return this.e;
/*     */   }
/*     */   
/*     */   public void a(float paramFloat) {
/* 107 */     this.e = paramFloat;
/*     */   }
/*     */   
/*     */   public float h() {
/* 111 */     return this.f;
/*     */   }
/*     */   
/*     */   public void b(float paramFloat) {
/* 115 */     this.f = paramFloat;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayInAbilities.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */