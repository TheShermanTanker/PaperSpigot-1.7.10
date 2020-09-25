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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PacketPlayInFlying
/*     */   extends Packet
/*     */ {
/*     */   protected double x;
/*     */   protected double y;
/*     */   protected double z;
/*     */   protected double stance;
/*     */   protected float yaw;
/*     */   protected float pitch;
/*     */   protected boolean g;
/*     */   protected boolean hasPos;
/*     */   protected boolean hasLook;
/*     */   
/*     */   public void a(PacketPlayInListener paramPacketPlayInListener) {
/* 137 */     paramPacketPlayInListener.a(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(PacketDataSerializer paramPacketDataSerializer) {
/* 142 */     this.g = (paramPacketDataSerializer.readUnsignedByte() != 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public void b(PacketDataSerializer paramPacketDataSerializer) {
/* 147 */     paramPacketDataSerializer.writeByte(this.g ? 1 : 0);
/*     */   }
/*     */   
/*     */   public double c() {
/* 151 */     return this.x;
/*     */   }
/*     */   
/*     */   public double d() {
/* 155 */     return this.y;
/*     */   }
/*     */   
/*     */   public double e() {
/* 159 */     return this.z;
/*     */   }
/*     */   
/*     */   public double f() {
/* 163 */     return this.stance;
/*     */   }
/*     */   
/*     */   public float g() {
/* 167 */     return this.yaw;
/*     */   }
/*     */   
/*     */   public float h() {
/* 171 */     return this.pitch;
/*     */   }
/*     */   
/*     */   public boolean i() {
/* 175 */     return this.g;
/*     */   }
/*     */   
/*     */   public boolean j() {
/* 179 */     return this.hasPos;
/*     */   }
/*     */   
/*     */   public boolean k() {
/* 183 */     return this.hasLook;
/*     */   }
/*     */   
/*     */   public void a(boolean paramBoolean) {
/* 187 */     this.hasPos = paramBoolean;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketPlayInFlying.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */