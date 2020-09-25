/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import org.bukkit.craftbukkit.v1_7_R4.TrigMath;
/*     */ 
/*     */ public class EntitySquid
/*     */   extends EntityWaterAnimal {
/*     */   public float bp;
/*     */   public float bq;
/*     */   public float br;
/*     */   public float bs;
/*     */   public float bt;
/*     */   public float bu;
/*     */   public float bv;
/*     */   public float bw;
/*     */   private float bx;
/*     */   private float by;
/*     */   private float bz;
/*     */   private float bA;
/*     */   private float bB;
/*     */   private float bC;
/*     */   
/*     */   public EntitySquid(World world) {
/*  23 */     super(world);
/*  24 */     a(0.95F, 0.95F);
/*  25 */     this.by = 1.0F / (this.random.nextFloat() + 1.0F) * 0.2F;
/*     */   }
/*     */   
/*     */   protected void aD() {
/*  29 */     super.aD();
/*  30 */     getAttributeInstance(GenericAttributes.maxHealth).setValue(10.0D);
/*     */   }
/*     */   
/*     */   protected String t() {
/*  34 */     return null;
/*     */   }
/*     */   
/*     */   protected String aT() {
/*  38 */     return null;
/*     */   }
/*     */   
/*     */   protected String aU() {
/*  42 */     return null;
/*     */   }
/*     */   
/*     */   protected float bf() {
/*  46 */     return 0.4F;
/*     */   }
/*     */   
/*     */   protected Item getLoot() {
/*  50 */     return Item.getById(0);
/*     */   }
/*     */   
/*     */   protected boolean g_() {
/*  54 */     return false;
/*     */   }
/*     */   
/*     */   protected void dropDeathLoot(boolean flag, int i) {
/*  58 */     int j = this.random.nextInt(3 + i) + 1;
/*     */     
/*  60 */     for (int k = 0; k < j; k++) {
/*  61 */       a(new ItemStack(Items.INK_SACK, 1, 0), 0.0F);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void e() {
/*  72 */     super.e();
/*  73 */     this.bq = this.bp;
/*  74 */     this.bs = this.br;
/*  75 */     this.bu = this.bt;
/*  76 */     this.bw = this.bv;
/*  77 */     this.bt += this.by;
/*  78 */     if (this.bt > 6.2831855F) {
/*  79 */       this.bt -= 6.2831855F;
/*  80 */       if (this.random.nextInt(10) == 0) {
/*  81 */         this.by = 1.0F / (this.random.nextFloat() + 1.0F) * 0.2F;
/*     */       }
/*     */     } 
/*     */     
/*  85 */     if (M()) {
/*     */ 
/*     */       
/*  88 */       if (this.bt < 3.1415927F) {
/*  89 */         float f1 = this.bt / 3.1415927F;
/*  90 */         this.bv = MathHelper.sin(f1 * f1 * 3.1415927F) * 3.1415927F * 0.25F;
/*  91 */         if (f1 > 0.75D) {
/*  92 */           this.bx = 1.0F;
/*  93 */           this.bz = 1.0F;
/*     */         } else {
/*  95 */           this.bz *= 0.8F;
/*     */         } 
/*     */       } else {
/*  98 */         this.bv = 0.0F;
/*  99 */         this.bx *= 0.9F;
/* 100 */         this.bz *= 0.99F;
/*     */       } 
/*     */       
/* 103 */       if (!this.world.isStatic) {
/* 104 */         this.motX = (this.bA * this.bx);
/* 105 */         this.motY = (this.bB * this.bx);
/* 106 */         this.motZ = (this.bC * this.bx);
/*     */       } 
/*     */       
/* 109 */       float f = MathHelper.sqrt(this.motX * this.motX + this.motZ * this.motZ);
/*     */       
/* 111 */       this.aM += (-((float)TrigMath.atan2(this.motX, this.motZ)) * 180.0F / 3.1415927F - this.aM) * 0.1F;
/* 112 */       this.yaw = this.aM;
/* 113 */       this.br += 3.1415927F * this.bz * 1.5F;
/*     */       
/* 115 */       this.bp += (-((float)TrigMath.atan2(f, this.motY)) * 180.0F / 3.1415927F - this.bp) * 0.1F;
/*     */     } else {
/* 117 */       this.bv = MathHelper.abs(MathHelper.sin(this.bt)) * 3.1415927F * 0.25F;
/* 118 */       if (!this.world.isStatic) {
/* 119 */         this.motX = 0.0D;
/* 120 */         this.motY -= 0.08D;
/* 121 */         this.motY *= 0.9800000190734863D;
/* 122 */         this.motZ = 0.0D;
/*     */       } 
/*     */       
/* 125 */       this.bp = (float)(this.bp + (-90.0F - this.bp) * 0.02D);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void e(float f, float f1) {
/* 130 */     move(this.motX, this.motY, this.motZ);
/*     */   }
/*     */   
/*     */   protected void bq() {
/* 134 */     this.aU++;
/* 135 */     if (this.aU > 100) {
/* 136 */       this.bA = this.bB = this.bC = 0.0F;
/* 137 */     } else if (this.random.nextInt(50) == 0 || !this.inWater || (this.bA == 0.0F && this.bB == 0.0F && this.bC == 0.0F)) {
/* 138 */       float f = this.random.nextFloat() * 3.1415927F * 2.0F;
/*     */       
/* 140 */       this.bA = MathHelper.cos(f) * 0.2F;
/* 141 */       this.bB = -0.1F + this.random.nextFloat() * 0.2F;
/* 142 */       this.bC = MathHelper.sin(f) * 0.2F;
/*     */     } 
/*     */     
/* 145 */     w();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSpawn() {
/* 150 */     return (this.locY > this.world.paperSpigotConfig.squidMinSpawnHeight && this.locY < this.world.paperSpigotConfig.squidMaxSpawnHeight && super.canSpawn());
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntitySquid.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */