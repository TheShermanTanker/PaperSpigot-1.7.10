/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*     */ import org.bukkit.event.entity.EntityRegainHealthEvent;
/*     */ import org.bukkit.event.entity.EntityTargetEvent;
/*     */ 
/*     */ public class EntityWolf
/*     */   extends EntityTameableAnimal
/*     */ {
/*     */   private float bq;
/*     */   private float br;
/*     */   private boolean bs;
/*     */   private boolean bt;
/*     */   private float bu;
/*     */   private float bv;
/*     */   
/*     */   public EntityWolf(World world) {
/*  18 */     super(world);
/*  19 */     a(0.6F, 0.8F);
/*  20 */     getNavigation().a(true);
/*  21 */     this.goalSelector.a(1, new PathfinderGoalFloat(this));
/*  22 */     this.goalSelector.a(2, this.bp);
/*  23 */     this.goalSelector.a(3, new PathfinderGoalLeapAtTarget(this, 0.4F));
/*  24 */     this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this, 1.0D, true));
/*  25 */     this.goalSelector.a(5, new PathfinderGoalFollowOwner(this, 1.0D, 10.0F, 2.0F));
/*  26 */     this.goalSelector.a(6, new PathfinderGoalBreed(this, 1.0D));
/*  27 */     this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, 1.0D));
/*  28 */     this.goalSelector.a(8, new PathfinderGoalBeg(this, 8.0F));
/*  29 */     this.goalSelector.a(9, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
/*  30 */     this.goalSelector.a(9, new PathfinderGoalRandomLookaround(this));
/*  31 */     this.targetSelector.a(1, new PathfinderGoalOwnerHurtByTarget(this));
/*  32 */     this.targetSelector.a(2, new PathfinderGoalOwnerHurtTarget(this));
/*  33 */     this.targetSelector.a(3, new PathfinderGoalHurtByTarget(this, true));
/*  34 */     this.targetSelector.a(4, new PathfinderGoalRandomTargetNonTamed(this, EntitySheep.class, 200, false));
/*  35 */     setTamed(false);
/*     */   }
/*     */   
/*     */   protected void aD() {
/*  39 */     super.aD();
/*  40 */     getAttributeInstance(GenericAttributes.d).setValue(0.30000001192092896D);
/*  41 */     if (isTamed()) {
/*  42 */       getAttributeInstance(GenericAttributes.maxHealth).setValue(20.0D);
/*     */     } else {
/*  44 */       getAttributeInstance(GenericAttributes.maxHealth).setValue(8.0D);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean bk() {
/*  49 */     return true;
/*     */   }
/*     */   
/*     */   public void setGoalTarget(EntityLiving entityliving) {
/*  53 */     super.setGoalTarget(entityliving);
/*  54 */     if (entityliving == null) {
/*  55 */       setAngry(false);
/*  56 */     } else if (!isTamed()) {
/*  57 */       setAngry(true);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void bp() {
/*  62 */     this.datawatcher.watch(18, Float.valueOf(getHealth()));
/*     */   }
/*     */   
/*     */   protected void c() {
/*  66 */     super.c();
/*  67 */     this.datawatcher.a(18, new Float(getHealth()));
/*  68 */     this.datawatcher.a(19, new Byte((byte)0));
/*  69 */     this.datawatcher.a(20, new Byte((byte)BlockCloth.b(1)));
/*     */   }
/*     */   
/*     */   protected void a(int i, int j, int k, Block block) {
/*  73 */     makeSound("mob.wolf.step", 0.15F, 1.0F);
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/*  77 */     super.b(nbttagcompound);
/*  78 */     nbttagcompound.setBoolean("Angry", isAngry());
/*  79 */     nbttagcompound.setByte("CollarColor", (byte)getCollarColor());
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/*  83 */     super.a(nbttagcompound);
/*  84 */     setAngry(nbttagcompound.getBoolean("Angry"));
/*  85 */     if (nbttagcompound.hasKeyOfType("CollarColor", 99)) {
/*  86 */       setCollarColor(nbttagcompound.getByte("CollarColor"));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected String t() {
/*  92 */     return isAngry() ? "mob.wolf.growl" : ((this.random.nextInt(3) == 0) ? ((isTamed() && this.datawatcher.getFloat(18) < getMaxHealth() / 2.0F) ? "mob.wolf.whine" : "mob.wolf.panting") : "mob.wolf.bark");
/*     */   }
/*     */   
/*     */   protected String aT() {
/*  96 */     return "mob.wolf.hurt";
/*     */   }
/*     */   
/*     */   protected String aU() {
/* 100 */     return "mob.wolf.death";
/*     */   }
/*     */   
/*     */   protected float bf() {
/* 104 */     return 0.4F;
/*     */   }
/*     */   
/*     */   protected Item getLoot() {
/* 108 */     return Item.getById(-1);
/*     */   }
/*     */   
/*     */   public void e() {
/* 112 */     super.e();
/* 113 */     if (!this.world.isStatic && this.bs && !this.bt && !bS() && this.onGround) {
/* 114 */       this.bt = true;
/* 115 */       this.bu = 0.0F;
/* 116 */       this.bv = 0.0F;
/* 117 */       this.world.broadcastEntityEffect(this, (byte)8);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void h() {
/* 122 */     super.h();
/* 123 */     this.br = this.bq;
/* 124 */     if (ck()) {
/* 125 */       this.bq += (1.0F - this.bq) * 0.4F;
/*     */     } else {
/* 127 */       this.bq += (0.0F - this.bq) * 0.4F;
/*     */     } 
/*     */     
/* 130 */     if (ck()) {
/* 131 */       this.g = 10;
/*     */     }
/*     */     
/* 134 */     if (L()) {
/* 135 */       this.bs = true;
/* 136 */       this.bt = false;
/* 137 */       this.bu = 0.0F;
/* 138 */       this.bv = 0.0F;
/* 139 */     } else if ((this.bs || this.bt) && this.bt) {
/* 140 */       if (this.bu == 0.0F) {
/* 141 */         makeSound("mob.wolf.shake", bf(), (this.random.nextFloat() - this.random.nextFloat()) * 0.2F + 1.0F);
/*     */       }
/*     */       
/* 144 */       this.bv = this.bu;
/* 145 */       this.bu += 0.05F;
/* 146 */       if (this.bv >= 2.0F) {
/* 147 */         this.bs = false;
/* 148 */         this.bt = false;
/* 149 */         this.bv = 0.0F;
/* 150 */         this.bu = 0.0F;
/*     */       } 
/*     */       
/* 153 */       if (this.bu > 0.4F) {
/* 154 */         float f = (float)this.boundingBox.b;
/* 155 */         int i = (int)(MathHelper.sin((this.bu - 0.4F) * 3.1415927F) * 7.0F);
/*     */         
/* 157 */         for (int j = 0; j < i; j++) {
/* 158 */           float f1 = (this.random.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
/* 159 */           float f2 = (this.random.nextFloat() * 2.0F - 1.0F) * this.width * 0.5F;
/*     */           
/* 161 */           this.world.addParticle("splash", this.locX + f1, (f + 0.8F), this.locZ + f2, this.motX, this.motY, this.motZ);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public float getHeadHeight() {
/* 168 */     return this.length * 0.8F;
/*     */   }
/*     */   
/*     */   public int x() {
/* 172 */     return isSitting() ? 20 : super.x();
/*     */   }
/*     */   
/*     */   public boolean damageEntity(DamageSource damagesource, float f) {
/* 176 */     if (isInvulnerable()) {
/* 177 */       return false;
/*     */     }
/* 179 */     Entity entity = damagesource.getEntity();
/*     */     
/* 181 */     this.bp.setSitting(false);
/* 182 */     if (entity != null && !(entity instanceof EntityHuman) && !(entity instanceof EntityArrow)) {
/* 183 */       f = (f + 1.0F) / 2.0F;
/*     */     }
/*     */     
/* 186 */     return super.damageEntity(damagesource, f);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean n(Entity entity) {
/* 191 */     int i = isTamed() ? 4 : 2;
/*     */     
/* 193 */     return entity.damageEntity(DamageSource.mobAttack(this), i);
/*     */   }
/*     */   
/*     */   public void setTamed(boolean flag) {
/* 197 */     super.setTamed(flag);
/* 198 */     if (flag) {
/* 199 */       getAttributeInstance(GenericAttributes.maxHealth).setValue(20.0D);
/*     */     } else {
/* 201 */       getAttributeInstance(GenericAttributes.maxHealth).setValue(8.0D);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/* 206 */     ItemStack itemstack = entityhuman.inventory.getItemInHand();
/*     */     
/* 208 */     if (isTamed()) {
/* 209 */       if (itemstack != null) {
/* 210 */         if (itemstack.getItem() instanceof ItemFood) {
/* 211 */           ItemFood itemfood = (ItemFood)itemstack.getItem();
/*     */           
/* 213 */           if (itemfood.i() && this.datawatcher.getFloat(18) < 20.0F) {
/* 214 */             if (!entityhuman.abilities.canInstantlyBuild) {
/* 215 */               itemstack.count--;
/*     */             }
/*     */             
/* 218 */             heal(itemfood.getNutrition(itemstack), EntityRegainHealthEvent.RegainReason.EATING);
/* 219 */             if (itemstack.count <= 0) {
/* 220 */               entityhuman.inventory.setItem(entityhuman.inventory.itemInHandIndex, (ItemStack)null);
/*     */             }
/*     */             
/* 223 */             return true;
/*     */           } 
/* 225 */         } else if (itemstack.getItem() == Items.INK_SACK) {
/* 226 */           int i = BlockCloth.b(itemstack.getData());
/*     */           
/* 228 */           if (i != getCollarColor()) {
/* 229 */             setCollarColor(i);
/* 230 */             if (!entityhuman.abilities.canInstantlyBuild && --itemstack.count <= 0) {
/* 231 */               entityhuman.inventory.setItem(entityhuman.inventory.itemInHandIndex, (ItemStack)null);
/*     */             }
/*     */             
/* 234 */             return true;
/*     */           } 
/*     */         } 
/*     */       }
/*     */       
/* 239 */       if (e(entityhuman) && !this.world.isStatic && !c(itemstack)) {
/* 240 */         this.bp.setSitting(!isSitting());
/* 241 */         this.bc = false;
/* 242 */         setPathEntity((PathEntity)null);
/* 243 */         setTarget((Entity)null);
/*     */         
/* 245 */         if (getGoalTarget() != null) {
/* 246 */           CraftEventFactory.callEntityTargetEvent(this, null, EntityTargetEvent.TargetReason.FORGOT_TARGET);
/*     */         }
/*     */         
/* 249 */         setGoalTarget((EntityLiving)null);
/*     */       } 
/* 251 */     } else if (itemstack != null && itemstack.getItem() == Items.BONE && !isAngry()) {
/* 252 */       if (!entityhuman.abilities.canInstantlyBuild) {
/* 253 */         itemstack.count--;
/*     */       }
/*     */       
/* 256 */       if (itemstack.count <= 0) {
/* 257 */         entityhuman.inventory.setItem(entityhuman.inventory.itemInHandIndex, (ItemStack)null);
/*     */       }
/*     */       
/* 260 */       if (!this.world.isStatic)
/*     */       {
/* 262 */         if (this.random.nextInt(3) == 0 && !CraftEventFactory.callEntityTameEvent(this, entityhuman).isCancelled()) {
/* 263 */           setTamed(true);
/* 264 */           setPathEntity((PathEntity)null);
/*     */           
/* 266 */           if (getGoalTarget() != null) {
/* 267 */             CraftEventFactory.callEntityTargetEvent(this, null, EntityTargetEvent.TargetReason.FORGOT_TARGET);
/*     */           }
/*     */           
/* 270 */           setGoalTarget((EntityLiving)null);
/* 271 */           this.bp.setSitting(true);
/* 272 */           setHealth(getMaxHealth());
/* 273 */           setOwnerUUID(entityhuman.getUniqueID().toString());
/* 274 */           i(true);
/* 275 */           this.world.broadcastEntityEffect(this, (byte)7);
/*     */         } else {
/* 277 */           i(false);
/* 278 */           this.world.broadcastEntityEffect(this, (byte)6);
/*     */         } 
/*     */       }
/*     */       
/* 282 */       return true;
/*     */     } 
/*     */     
/* 285 */     return super.a(entityhuman);
/*     */   }
/*     */   
/*     */   public boolean c(ItemStack itemstack) {
/* 289 */     return (itemstack == null) ? false : (!(itemstack.getItem() instanceof ItemFood) ? false : ((ItemFood)itemstack.getItem()).i());
/*     */   }
/*     */   
/*     */   public int bB() {
/* 293 */     return 8;
/*     */   }
/*     */   
/*     */   public boolean isAngry() {
/* 297 */     return ((this.datawatcher.getByte(16) & 0x2) != 0);
/*     */   }
/*     */   
/*     */   public void setAngry(boolean flag) {
/* 301 */     byte b0 = this.datawatcher.getByte(16);
/*     */     
/* 303 */     if (flag) {
/* 304 */       this.datawatcher.watch(16, Byte.valueOf((byte)(b0 | 0x2)));
/*     */     } else {
/* 306 */       this.datawatcher.watch(16, Byte.valueOf((byte)(b0 & 0xFFFFFFFD)));
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getCollarColor() {
/* 311 */     return this.datawatcher.getByte(20) & 0xF;
/*     */   }
/*     */   
/*     */   public void setCollarColor(int i) {
/* 315 */     this.datawatcher.watch(20, Byte.valueOf((byte)(i & 0xF)));
/*     */   }
/*     */   
/*     */   public EntityWolf b(EntityAgeable entityageable) {
/* 319 */     EntityWolf entitywolf = new EntityWolf(this.world);
/* 320 */     String s = getOwnerUUID();
/*     */     
/* 322 */     if (s != null && s.trim().length() > 0) {
/* 323 */       entitywolf.setOwnerUUID(s);
/* 324 */       entitywolf.setTamed(true);
/*     */     } 
/*     */     
/* 327 */     return entitywolf;
/*     */   }
/*     */   
/*     */   public void m(boolean flag) {
/* 331 */     if (flag) {
/* 332 */       this.datawatcher.watch(19, Byte.valueOf((byte)1));
/*     */     } else {
/* 334 */       this.datawatcher.watch(19, Byte.valueOf((byte)0));
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean mate(EntityAnimal entityanimal) {
/* 339 */     if (entityanimal == this)
/* 340 */       return false; 
/* 341 */     if (!isTamed())
/* 342 */       return false; 
/* 343 */     if (!(entityanimal instanceof EntityWolf)) {
/* 344 */       return false;
/*     */     }
/* 346 */     EntityWolf entitywolf = (EntityWolf)entityanimal;
/*     */     
/* 348 */     return !entitywolf.isTamed() ? false : (entitywolf.isSitting() ? false : ((ce() && entitywolf.ce())));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean ck() {
/* 353 */     return (this.datawatcher.getByte(19) == 1);
/*     */   }
/*     */   
/*     */   protected boolean isTypeNotPersistent() {
/* 357 */     return !isTamed();
/*     */   }
/*     */   
/*     */   public boolean a(EntityLiving entityliving, EntityLiving entityliving1) {
/* 361 */     if (!(entityliving instanceof EntityCreeper) && !(entityliving instanceof EntityGhast)) {
/* 362 */       if (entityliving instanceof EntityWolf) {
/* 363 */         EntityWolf entitywolf = (EntityWolf)entityliving;
/*     */         
/* 365 */         if (entitywolf.isTamed() && entitywolf.getOwner() == entityliving1) {
/* 366 */           return false;
/*     */         }
/*     */       } 
/*     */       
/* 370 */       return (entityliving instanceof EntityHuman && entityliving1 instanceof EntityHuman && !((EntityHuman)entityliving1).a((EntityHuman)entityliving)) ? false : ((!(entityliving instanceof EntityHorse) || !((EntityHorse)entityliving).isTame()));
/*     */     } 
/* 372 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityAgeable createChild(EntityAgeable entityageable) {
/* 377 */     return b(entityageable);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityWolf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */