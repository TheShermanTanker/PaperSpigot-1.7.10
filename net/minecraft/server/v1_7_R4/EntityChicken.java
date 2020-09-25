/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ public class EntityChicken
/*     */   extends EntityAnimal {
/*     */   public float bp;
/*     */   public float bq;
/*     */   public float br;
/*     */   public float bs;
/*   9 */   public float bt = 1.0F;
/*     */   public int bu;
/*     */   public boolean bv;
/*     */   
/*     */   public EntityChicken(World world) {
/*  14 */     super(world);
/*  15 */     a(0.3F, 0.7F);
/*  16 */     this.bu = this.random.nextInt(6000) + 6000;
/*  17 */     this.goalSelector.a(0, new PathfinderGoalFloat(this));
/*  18 */     this.goalSelector.a(1, new PathfinderGoalPanic(this, 1.4D));
/*  19 */     this.goalSelector.a(2, new PathfinderGoalBreed(this, 1.0D));
/*  20 */     this.goalSelector.a(3, new PathfinderGoalTempt(this, 1.0D, Items.SEEDS, false));
/*  21 */     this.goalSelector.a(4, new PathfinderGoalFollowParent(this, 1.1D));
/*  22 */     this.goalSelector.a(5, new PathfinderGoalRandomStroll(this, 1.0D));
/*  23 */     this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
/*  24 */     this.goalSelector.a(7, new PathfinderGoalRandomLookaround(this));
/*     */   }
/*     */   
/*     */   public boolean bk() {
/*  28 */     return true;
/*     */   }
/*     */   
/*     */   protected void aD() {
/*  32 */     super.aD();
/*  33 */     getAttributeInstance(GenericAttributes.maxHealth).setValue(4.0D);
/*  34 */     getAttributeInstance(GenericAttributes.d).setValue(0.25D);
/*     */   }
/*     */ 
/*     */   
/*     */   public void e() {
/*  39 */     if (isChickenJockey()) {
/*  40 */       this.persistent = !isTypeNotPersistent();
/*     */     }
/*     */     
/*  43 */     super.e();
/*  44 */     this.bs = this.bp;
/*  45 */     this.br = this.bq;
/*  46 */     this.bq = (float)(this.bq + (this.onGround ? -1 : 4) * 0.3D);
/*  47 */     if (this.bq < 0.0F) {
/*  48 */       this.bq = 0.0F;
/*     */     }
/*     */     
/*  51 */     if (this.bq > 1.0F) {
/*  52 */       this.bq = 1.0F;
/*     */     }
/*     */     
/*  55 */     if (!this.onGround && this.bt < 1.0F) {
/*  56 */       this.bt = 1.0F;
/*     */     }
/*     */     
/*  59 */     this.bt = (float)(this.bt * 0.9D);
/*  60 */     if (!this.onGround && this.motY < 0.0D) {
/*  61 */       this.motY *= 0.6D;
/*     */     }
/*     */     
/*  64 */     this.bp += this.bt * 2.0F;
/*  65 */     if (!this.world.isStatic && !isBaby() && !isChickenJockey() && --this.bu <= 0) {
/*  66 */       makeSound("mob.chicken.plop", 1.0F, (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
/*  67 */       a(Items.EGG, 1);
/*  68 */       this.bu = this.random.nextInt(6000) + 6000;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void b(float f) {}
/*     */   
/*     */   protected String t() {
/*  75 */     return "mob.chicken.say";
/*     */   }
/*     */   
/*     */   protected String aT() {
/*  79 */     return "mob.chicken.hurt";
/*     */   }
/*     */   
/*     */   protected String aU() {
/*  83 */     return "mob.chicken.hurt";
/*     */   }
/*     */   
/*     */   protected void a(int i, int j, int k, Block block) {
/*  87 */     makeSound("mob.chicken.step", 0.15F, 1.0F);
/*     */   }
/*     */   
/*     */   protected Item getLoot() {
/*  91 */     return Items.FEATHER;
/*     */   }
/*     */   
/*     */   protected void dropDeathLoot(boolean flag, int i) {
/*  95 */     int j = this.random.nextInt(3) + this.random.nextInt(1 + i);
/*     */     
/*  97 */     for (int k = 0; k < j; k++) {
/*  98 */       a(Items.FEATHER, 1);
/*     */     }
/*     */     
/* 101 */     if (isBurning()) {
/* 102 */       a(Items.COOKED_CHICKEN, 1);
/*     */     } else {
/* 104 */       a(Items.RAW_CHICKEN, 1);
/*     */     } 
/*     */   }
/*     */   
/*     */   public EntityChicken b(EntityAgeable entityageable) {
/* 109 */     return new EntityChicken(this.world);
/*     */   }
/*     */   
/*     */   public boolean c(ItemStack itemstack) {
/* 113 */     return (itemstack != null && itemstack.getItem() instanceof ItemSeeds);
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 117 */     super.a(nbttagcompound);
/* 118 */     this.bv = nbttagcompound.getBoolean("IsChickenJockey");
/*     */   }
/*     */   
/*     */   protected int getExpValue(EntityHuman entityhuman) {
/* 122 */     return isChickenJockey() ? 10 : super.getExpValue(entityhuman);
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/* 126 */     super.b(nbttagcompound);
/* 127 */     nbttagcompound.setBoolean("IsChickenJockey", this.bv);
/*     */   }
/*     */   
/*     */   protected boolean isTypeNotPersistent() {
/* 131 */     return (isChickenJockey() && this.passenger == null);
/*     */   }
/*     */   
/*     */   public void ac() {
/* 135 */     super.ac();
/* 136 */     float f = MathHelper.sin(this.aM * 3.1415927F / 180.0F);
/* 137 */     float f1 = MathHelper.cos(this.aM * 3.1415927F / 180.0F);
/* 138 */     float f2 = 0.1F;
/* 139 */     float f3 = 0.0F;
/*     */     
/* 141 */     this.passenger.setPosition(this.locX + (f2 * f), this.locY + (this.length * 0.5F) + this.passenger.ad() + f3, this.locZ - (f2 * f1));
/* 142 */     if (this.passenger instanceof EntityLiving) {
/* 143 */       ((EntityLiving)this.passenger).aM = this.aM;
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isChickenJockey() {
/* 148 */     return this.bv;
/*     */   }
/*     */   
/*     */   public void i(boolean flag) {
/* 152 */     this.bv = flag;
/*     */   }
/*     */   
/*     */   public EntityAgeable createChild(EntityAgeable entityageable) {
/* 156 */     return b(entityageable);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityChicken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */