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
/*     */ public class EntityBlaze
/*     */   extends EntityMonster
/*     */ {
/*  14 */   private float bp = 0.5F;
/*     */   
/*     */   private int bq;
/*     */   
/*     */   private int br;
/*     */   
/*     */   public EntityBlaze(World paramWorld) {
/*  21 */     super(paramWorld);
/*     */     
/*  23 */     this.fireProof = true;
/*  24 */     this.b = 10;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void aD() {
/*  29 */     super.aD();
/*  30 */     getAttributeInstance(GenericAttributes.e).setValue(6.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void c() {
/*  35 */     super.c();
/*     */     
/*  37 */     this.datawatcher.a(16, new Byte((byte)0));
/*     */   }
/*     */ 
/*     */   
/*     */   protected String t() {
/*  42 */     return "mob.blaze.breathe";
/*     */   }
/*     */ 
/*     */   
/*     */   protected String aT() {
/*  47 */     return "mob.blaze.hit";
/*     */   }
/*     */ 
/*     */   
/*     */   protected String aU() {
/*  52 */     return "mob.blaze.death";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public float d(float paramFloat) {
/*  62 */     return 1.0F;
/*     */   }
/*     */ 
/*     */   
/*     */   public void e() {
/*  67 */     if (!this.world.isStatic) {
/*     */       
/*  69 */       if (L()) {
/*  70 */         damageEntity(DamageSource.DROWN, 1.0F);
/*     */       }
/*     */       
/*  73 */       this.bq--;
/*  74 */       if (this.bq <= 0) {
/*  75 */         this.bq = 100;
/*  76 */         this.bp = 0.5F + (float)this.random.nextGaussian() * 3.0F;
/*     */       } 
/*     */       
/*  79 */       if (bT() != null && (bT()).locY + bT().getHeadHeight() > this.locY + getHeadHeight() + this.bp) {
/*  80 */         this.motY += (0.30000001192092896D - this.motY) * 0.30000001192092896D;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  85 */     if (this.random.nextInt(24) == 0) {
/*  86 */       this.world.makeSound(this.locX + 0.5D, this.locY + 0.5D, this.locZ + 0.5D, "fire.fire", 1.0F + this.random.nextFloat(), this.random.nextFloat() * 0.7F + 0.3F);
/*     */     }
/*     */ 
/*     */     
/*  90 */     if (!this.onGround && this.motY < 0.0D) {
/*  91 */       this.motY *= 0.6D;
/*     */     }
/*     */     
/*  94 */     for (byte b = 0; b < 2; b++) {
/*  95 */       this.world.addParticle("largesmoke", this.locX + (this.random.nextDouble() - 0.5D) * this.width, this.locY + this.random.nextDouble() * this.length, this.locZ + (this.random.nextDouble() - 0.5D) * this.width, 0.0D, 0.0D, 0.0D);
/*     */     }
/*     */     
/*  98 */     super.e();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void a(Entity paramEntity, float paramFloat) {
/* 103 */     if (this.attackTicks <= 0 && paramFloat < 2.0F && paramEntity.boundingBox.e > this.boundingBox.b && paramEntity.boundingBox.b < this.boundingBox.e) {
/* 104 */       this.attackTicks = 20;
/* 105 */       n(paramEntity);
/* 106 */     } else if (paramFloat < 30.0F) {
/* 107 */       double d1 = paramEntity.locX - this.locX;
/* 108 */       double d2 = paramEntity.boundingBox.b + (paramEntity.length / 2.0F) - this.locY + (this.length / 2.0F);
/* 109 */       double d3 = paramEntity.locZ - this.locZ;
/*     */       
/* 111 */       if (this.attackTicks == 0) {
/* 112 */         this.br++;
/* 113 */         if (this.br == 1) {
/* 114 */           this.attackTicks = 60;
/* 115 */           a(true);
/* 116 */         } else if (this.br <= 4) {
/* 117 */           this.attackTicks = 6;
/*     */         } else {
/* 119 */           this.attackTicks = 100;
/* 120 */           this.br = 0;
/* 121 */           a(false);
/*     */         } 
/*     */         
/* 124 */         if (this.br > 1) {
/* 125 */           float f = MathHelper.c(paramFloat) * 0.5F;
/*     */           
/* 127 */           this.world.a((EntityHuman)null, 1009, (int)this.locX, (int)this.locY, (int)this.locZ, 0);
/* 128 */           for (byte b = 0; b < 1; b++) {
/* 129 */             EntitySmallFireball entitySmallFireball = new EntitySmallFireball(this.world, this, d1 + this.random.nextGaussian() * f, d2, d3 + this.random.nextGaussian() * f);
/* 130 */             entitySmallFireball.locY = this.locY + (this.length / 2.0F) + 0.5D;
/* 131 */             this.world.addEntity(entitySmallFireball);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */       
/* 136 */       this.yaw = (float)(Math.atan2(d3, d1) * 180.0D / 3.1415927410125732D) - 90.0F;
/*     */       
/* 138 */       this.bn = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void b(float paramFloat) {}
/*     */ 
/*     */   
/*     */   protected Item getLoot() {
/* 148 */     return Items.BLAZE_ROD;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isBurning() {
/* 153 */     return bZ();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void dropDeathLoot(boolean paramBoolean, int paramInt) {
/* 158 */     if (paramBoolean) {
/* 159 */       int i = this.random.nextInt(2 + paramInt);
/* 160 */       for (byte b = 0; b < i; b++) {
/* 161 */         a(Items.BLAZE_ROD, 1);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean bZ() {
/* 167 */     return ((this.datawatcher.getByte(16) & 0x1) != 0);
/*     */   }
/*     */   
/*     */   public void a(boolean paramBoolean) {
/* 171 */     byte b = this.datawatcher.getByte(16);
/* 172 */     if (paramBoolean) {
/* 173 */       b = (byte)(b | 0x1);
/*     */     } else {
/* 175 */       b = (byte)(b & 0xFFFFFFFE);
/*     */     } 
/* 177 */     this.datawatcher.watch(16, Byte.valueOf(b));
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean j_() {
/* 182 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityBlaze.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */