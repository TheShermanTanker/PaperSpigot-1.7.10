/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Calendar;
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
/*     */ public class EntityBat
/*     */   extends EntityAmbient
/*     */ {
/*     */   private ChunkCoordinates h;
/*     */   
/*     */   public EntityBat(World paramWorld) {
/*  24 */     super(paramWorld);
/*     */     
/*  26 */     a(0.5F, 0.9F);
/*  27 */     setAsleep(true);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void c() {
/*  32 */     super.c();
/*     */     
/*  34 */     this.datawatcher.a(16, new Byte((byte)0));
/*     */   }
/*     */ 
/*     */   
/*     */   protected float bf() {
/*  39 */     return 0.1F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected float bg() {
/*  44 */     return super.bg() * 0.95F;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String t() {
/*  49 */     if (isAsleep() && this.random.nextInt(4) != 0) {
/*  50 */       return null;
/*     */     }
/*  52 */     return "mob.bat.idle";
/*     */   }
/*     */ 
/*     */   
/*     */   protected String aT() {
/*  57 */     return "mob.bat.hurt";
/*     */   }
/*     */ 
/*     */   
/*     */   protected String aU() {
/*  62 */     return "mob.bat.death";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean S() {
/*  68 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void o(Entity paramEntity) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void bo() {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void aD() {
/*  83 */     super.aD();
/*     */     
/*  85 */     getAttributeInstance(GenericAttributes.maxHealth).setValue(6.0D);
/*     */   }
/*     */   
/*     */   public boolean isAsleep() {
/*  89 */     return ((this.datawatcher.getByte(16) & 0x1) != 0);
/*     */   }
/*     */   
/*     */   public void setAsleep(boolean paramBoolean) {
/*  93 */     byte b = this.datawatcher.getByte(16);
/*  94 */     if (paramBoolean) {
/*  95 */       this.datawatcher.watch(16, Byte.valueOf((byte)(b | 0x1)));
/*     */     } else {
/*  97 */       this.datawatcher.watch(16, Byte.valueOf((byte)(b & 0xFFFFFFFE)));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean bk() {
/* 103 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void h() {
/* 109 */     super.h();
/*     */ 
/*     */     
/* 112 */     this.motX = this.motY = this.motZ = 0.0D;
/* 113 */     this.locY = MathHelper.floor(this.locY) + 1.0D - this.length;
/*     */     
/* 115 */     this.motY *= 0.6000000238418579D;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void bn() {
/* 122 */     super.bn();
/*     */     
/* 124 */     if (isAsleep()) {
/* 125 */       if (!this.world.getType(MathHelper.floor(this.locX), (int)this.locY + 1, MathHelper.floor(this.locZ)).r()) {
/* 126 */         setAsleep(false);
/* 127 */         this.world.a((EntityHuman)null, 1015, (int)this.locX, (int)this.locY, (int)this.locZ, 0);
/*     */       } else {
/*     */         
/* 130 */         if (this.random.nextInt(200) == 0) {
/* 131 */           this.aO = this.random.nextInt(360);
/*     */         }
/*     */         
/* 134 */         if (this.world.findNearbyPlayer(this, 4.0D) != null) {
/* 135 */           setAsleep(false);
/* 136 */           this.world.a((EntityHuman)null, 1015, (int)this.locX, (int)this.locY, (int)this.locZ, 0);
/*     */         }
/*     */       
/*     */       } 
/*     */     } else {
/*     */       
/* 142 */       if (this.h != null && (!this.world.isEmpty(this.h.x, this.h.y, this.h.z) || this.h.y < 1)) {
/* 143 */         this.h = null;
/*     */       }
/* 145 */       if (this.h == null || this.random.nextInt(30) == 0 || this.h.e((int)this.locX, (int)this.locY, (int)this.locZ) < 4.0F) {
/* 146 */         this.h = new ChunkCoordinates((int)this.locX + this.random.nextInt(7) - this.random.nextInt(7), (int)this.locY + this.random.nextInt(6) - 2, (int)this.locZ + this.random.nextInt(7) - this.random.nextInt(7));
/*     */       }
/*     */       
/* 149 */       double d1 = this.h.x + 0.5D - this.locX;
/* 150 */       double d2 = this.h.y + 0.1D - this.locY;
/* 151 */       double d3 = this.h.z + 0.5D - this.locZ;
/*     */       
/* 153 */       this.motX += (Math.signum(d1) * 0.5D - this.motX) * 0.10000000149011612D;
/* 154 */       this.motY += (Math.signum(d2) * 0.699999988079071D - this.motY) * 0.10000000149011612D;
/* 155 */       this.motZ += (Math.signum(d3) * 0.5D - this.motZ) * 0.10000000149011612D;
/*     */       
/* 157 */       float f1 = (float)(Math.atan2(this.motZ, this.motX) * 180.0D / 3.1415927410125732D) - 90.0F;
/* 158 */       float f2 = MathHelper.g(f1 - this.yaw);
/* 159 */       this.be = 0.5F;
/* 160 */       this.yaw += f2;
/*     */       
/* 162 */       if (this.random.nextInt(100) == 0 && this.world.getType(MathHelper.floor(this.locX), (int)this.locY + 1, MathHelper.floor(this.locZ)).r()) {
/* 163 */         setAsleep(true);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean g_() {
/* 171 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void b(float paramFloat) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void a(double paramDouble, boolean paramBoolean) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean az() {
/* 186 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean damageEntity(DamageSource paramDamageSource, float paramFloat) {
/* 192 */     if (isInvulnerable()) return false; 
/* 193 */     if (!this.world.isStatic && 
/* 194 */       isAsleep()) {
/* 195 */       setAsleep(false);
/*     */     }
/*     */ 
/*     */     
/* 199 */     return super.damageEntity(paramDamageSource, paramFloat);
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(NBTTagCompound paramNBTTagCompound) {
/* 204 */     super.a(paramNBTTagCompound);
/*     */     
/* 206 */     this.datawatcher.watch(16, Byte.valueOf(paramNBTTagCompound.getByte("BatFlags")));
/*     */   }
/*     */ 
/*     */   
/*     */   public void b(NBTTagCompound paramNBTTagCompound) {
/* 211 */     super.b(paramNBTTagCompound);
/*     */     
/* 213 */     paramNBTTagCompound.setByte("BatFlags", this.datawatcher.getByte(16));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean canSpawn() {
/* 219 */     int i = MathHelper.floor(this.boundingBox.b);
/* 220 */     if (i >= 63) return false;
/*     */     
/* 222 */     int j = MathHelper.floor(this.locX);
/* 223 */     int k = MathHelper.floor(this.locZ);
/*     */     
/* 225 */     int m = this.world.getLightLevel(j, i, k);
/* 226 */     byte b = 4;
/* 227 */     Calendar calendar = this.world.V();
/*     */     
/* 229 */     if ((calendar.get(2) + 1 == 10 && calendar.get(5) >= 20) || (calendar.get(2) + 1 == 11 && calendar.get(5) <= 3)) {
/* 230 */       b = 7;
/* 231 */     } else if (this.random.nextBoolean()) {
/* 232 */       return false;
/*     */     } 
/*     */     
/* 235 */     if (m > this.random.nextInt(b)) return false;
/*     */     
/* 237 */     return super.canSpawn();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityBat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */