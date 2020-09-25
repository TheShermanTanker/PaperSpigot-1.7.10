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
/*     */ public class EntityEnderSignal
/*     */   extends Entity
/*     */ {
/*     */   private double a;
/*     */   private double b;
/*     */   private double c;
/*     */   private int d;
/*     */   private boolean e;
/*     */   
/*     */   public EntityEnderSignal(World paramWorld) {
/*  22 */     super(paramWorld);
/*  23 */     a(0.25F, 0.25F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void c() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityEnderSignal(World paramWorld, double paramDouble1, double paramDouble2, double paramDouble3) {
/*  38 */     super(paramWorld);
/*  39 */     this.d = 0;
/*     */     
/*  41 */     a(0.25F, 0.25F);
/*     */     
/*  43 */     setPosition(paramDouble1, paramDouble2, paramDouble3);
/*  44 */     this.height = 0.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(double paramDouble1, int paramInt, double paramDouble2) {
/*  49 */     double d1 = paramDouble1 - this.locX, d2 = paramDouble2 - this.locZ;
/*  50 */     float f = MathHelper.sqrt(d1 * d1 + d2 * d2);
/*     */     
/*  52 */     if (f > 12.0F) {
/*  53 */       this.a = this.locX + d1 / f * 12.0D;
/*  54 */       this.c = this.locZ + d2 / f * 12.0D;
/*  55 */       this.b = this.locY + 8.0D;
/*     */     } else {
/*  57 */       this.a = paramDouble1;
/*  58 */       this.b = paramInt;
/*  59 */       this.c = paramDouble2;
/*     */     } 
/*     */     
/*  62 */     this.d = 0;
/*  63 */     this.e = (this.random.nextInt(5) > 0);
/*     */   }
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
/*     */   public void h() {
/*  80 */     this.S = this.locX;
/*  81 */     this.T = this.locY;
/*  82 */     this.U = this.locZ;
/*  83 */     super.h();
/*     */     
/*  85 */     this.locX += this.motX;
/*  86 */     this.locY += this.motY;
/*  87 */     this.locZ += this.motZ;
/*     */     
/*  89 */     float f1 = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
/*  90 */     this.yaw = (float)(Math.atan2(this.motX, this.motZ) * 180.0D / 3.1415927410125732D);
/*  91 */     this.pitch = (float)(Math.atan2(this.motY, f1) * 180.0D / 3.1415927410125732D);
/*     */     
/*  93 */     while (this.pitch - this.lastPitch < -180.0F)
/*  94 */       this.lastPitch -= 360.0F; 
/*  95 */     while (this.pitch - this.lastPitch >= 180.0F) {
/*  96 */       this.lastPitch += 360.0F;
/*     */     }
/*  98 */     while (this.yaw - this.lastYaw < -180.0F)
/*  99 */       this.lastYaw -= 360.0F; 
/* 100 */     while (this.yaw - this.lastYaw >= 180.0F) {
/* 101 */       this.lastYaw += 360.0F;
/*     */     }
/* 103 */     this.pitch = this.lastPitch + (this.pitch - this.lastPitch) * 0.2F;
/* 104 */     this.yaw = this.lastYaw + (this.yaw - this.lastYaw) * 0.2F;
/*     */     
/* 106 */     if (!this.world.isStatic) {
/* 107 */       double d1 = this.a - this.locX, d2 = this.c - this.locZ;
/* 108 */       float f3 = (float)Math.sqrt(d1 * d1 + d2 * d2);
/* 109 */       float f4 = (float)Math.atan2(d2, d1);
/* 110 */       double d3 = f1 + (f3 - f1) * 0.0025D;
/* 111 */       if (f3 < 1.0F) {
/* 112 */         d3 *= 0.8D;
/* 113 */         this.motY *= 0.8D;
/*     */       } 
/* 115 */       this.motX = Math.cos(f4) * d3;
/* 116 */       this.motZ = Math.sin(f4) * d3;
/*     */       
/* 118 */       if (this.locY < this.b) {
/* 119 */         this.motY += (1.0D - this.motY) * 0.014999999664723873D;
/*     */       } else {
/* 121 */         this.motY += (-1.0D - this.motY) * 0.014999999664723873D;
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 126 */     float f2 = 0.25F;
/* 127 */     if (M()) {
/* 128 */       for (byte b = 0; b < 4; b++) {
/* 129 */         this.world.addParticle("bubble", this.locX - this.motX * f2, this.locY - this.motY * f2, this.locZ - this.motZ * f2, this.motX, this.motY, this.motZ);
/*     */       }
/*     */     } else {
/*     */       
/* 133 */       this.world.addParticle("portal", this.locX - this.motX * f2 + this.random.nextDouble() * 0.6D - 0.3D, this.locY - this.motY * f2 - 0.5D, this.locZ - this.motZ * f2 + this.random.nextDouble() * 0.6D - 0.3D, this.motX, this.motY, this.motZ);
/*     */     } 
/*     */     
/* 136 */     if (!this.world.isStatic) {
/* 137 */       setPosition(this.locX, this.locY, this.locZ);
/*     */       
/* 139 */       this.d++;
/* 140 */       if (this.d > 80 && !this.world.isStatic) {
/* 141 */         die();
/* 142 */         if (this.e) {
/* 143 */           this.world.addEntity(new EntityItem(this.world, this.locX, this.locY, this.locZ, new ItemStack(Items.EYE_OF_ENDER)));
/*     */         } else {
/* 145 */           this.world.triggerEffect(2003, (int)Math.round(this.locX), (int)Math.round(this.locY), (int)Math.round(this.locZ), 0);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void b(NBTTagCompound paramNBTTagCompound) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(NBTTagCompound paramNBTTagCompound) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float d(float paramFloat) {
/* 166 */     return 1.0F;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean av() {
/* 176 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityEnderSignal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */