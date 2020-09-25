/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.UUID;
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
/*     */ public class EntityWitch
/*     */   extends EntityMonster
/*     */   implements IRangedEntity
/*     */ {
/*  23 */   private static final UUID bp = UUID.fromString("5CD17E52-A79A-43D3-A529-90FDE04B181E");
/*  24 */   private static final AttributeModifier bq = (new AttributeModifier(bp, "Drinking speed penalty", -0.25D, 0)).a(false);
/*     */ 
/*     */   
/*  27 */   private static final Item[] br = new Item[] { Items.GLOWSTONE_DUST, Items.SUGAR, Items.REDSTONE, Items.SPIDER_EYE, Items.GLASS_BOTTLE, Items.SULPHUR, Items.STICK, Items.STICK };
/*     */ 
/*     */   
/*     */   private int bs;
/*     */ 
/*     */ 
/*     */   
/*     */   public EntityWitch(World paramWorld) {
/*  35 */     super(paramWorld);
/*     */     
/*  37 */     this.goalSelector.a(1, new PathfinderGoalFloat(this));
/*  38 */     this.goalSelector.a(2, new PathfinderGoalArrowAttack(this, 1.0D, 60, 10.0F));
/*  39 */     this.goalSelector.a(2, new PathfinderGoalRandomStroll(this, 1.0D));
/*  40 */     this.goalSelector.a(3, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
/*  41 */     this.goalSelector.a(3, new PathfinderGoalRandomLookaround(this));
/*     */     
/*  43 */     this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, false));
/*  44 */     this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, 0, true));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void c() {
/*  49 */     super.c();
/*     */     
/*  51 */     getDataWatcher().a(21, Byte.valueOf((byte)0));
/*     */   }
/*     */ 
/*     */   
/*     */   protected String t() {
/*  56 */     return "mob.witch.idle";
/*     */   }
/*     */ 
/*     */   
/*     */   protected String aT() {
/*  61 */     return "mob.witch.hurt";
/*     */   }
/*     */ 
/*     */   
/*     */   protected String aU() {
/*  66 */     return "mob.witch.death";
/*     */   }
/*     */   
/*     */   public void a(boolean paramBoolean) {
/*  70 */     getDataWatcher().watch(21, Byte.valueOf(paramBoolean ? 1 : 0));
/*     */   }
/*     */   
/*     */   public boolean bZ() {
/*  74 */     return (getDataWatcher().getByte(21) == 1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void aD() {
/*  79 */     super.aD();
/*     */     
/*  81 */     getAttributeInstance(GenericAttributes.maxHealth).setValue(26.0D);
/*  82 */     getAttributeInstance(GenericAttributes.d).setValue(0.25D);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean bk() {
/*  87 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void e() {
/*  92 */     if (!this.world.isStatic) {
/*  93 */       if (bZ()) {
/*  94 */         if (this.bs-- <= 0) {
/*  95 */           a(false);
/*  96 */           ItemStack itemStack = be();
/*  97 */           setEquipment(0, (ItemStack)null);
/*     */           
/*  99 */           if (itemStack != null && itemStack.getItem() == Items.POTION) {
/* 100 */             List list = Items.POTION.g(itemStack);
/* 101 */             if (list != null) {
/* 102 */               for (MobEffect mobEffect : list) {
/* 103 */                 addEffect(new MobEffect(mobEffect));
/*     */               }
/*     */             }
/*     */           } 
/*     */           
/* 108 */           getAttributeInstance(GenericAttributes.d).b(bq);
/*     */         } 
/*     */       } else {
/* 111 */         short s = -1;
/*     */         
/* 113 */         if (this.random.nextFloat() < 0.15F && a(Material.WATER) && !hasEffect(MobEffectList.WATER_BREATHING)) {
/* 114 */           s = 8237;
/* 115 */         } else if (this.random.nextFloat() < 0.15F && isBurning() && !hasEffect(MobEffectList.FIRE_RESISTANCE)) {
/* 116 */           s = 16307;
/* 117 */         } else if (this.random.nextFloat() < 0.05F && getHealth() < getMaxHealth()) {
/* 118 */           s = 16341;
/* 119 */         } else if (this.random.nextFloat() < 0.25F && getGoalTarget() != null && !hasEffect(MobEffectList.FASTER_MOVEMENT) && getGoalTarget().f(this) > 121.0D) {
/* 120 */           s = 16274;
/* 121 */         } else if (this.random.nextFloat() < 0.25F && getGoalTarget() != null && !hasEffect(MobEffectList.FASTER_MOVEMENT) && getGoalTarget().f(this) > 121.0D) {
/* 122 */           s = 16274;
/*     */         } 
/*     */         
/* 125 */         if (s > -1) {
/* 126 */           setEquipment(0, new ItemStack(Items.POTION, 1, s));
/* 127 */           this.bs = be().n();
/* 128 */           a(true);
/* 129 */           AttributeInstance attributeInstance = getAttributeInstance(GenericAttributes.d);
/* 130 */           attributeInstance.b(bq);
/* 131 */           attributeInstance.a(bq);
/*     */         } 
/*     */       } 
/*     */       
/* 135 */       if (this.random.nextFloat() < 7.5E-4F) {
/* 136 */         this.world.broadcastEntityEffect(this, (byte)15);
/*     */       }
/*     */     } 
/*     */     
/* 140 */     super.e();
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
/*     */   protected float applyMagicModifier(DamageSource paramDamageSource, float paramFloat) {
/* 156 */     paramFloat = super.applyMagicModifier(paramDamageSource, paramFloat);
/*     */     
/* 158 */     if (paramDamageSource.getEntity() == this) paramFloat = 0.0F; 
/* 159 */     if (paramDamageSource.isMagic()) paramFloat = (float)(paramFloat * 0.15D);
/*     */     
/* 161 */     return paramFloat;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void dropDeathLoot(boolean paramBoolean, int paramInt) {
/* 166 */     int i = this.random.nextInt(3) + 1;
/* 167 */     for (byte b = 0; b < i; b++) {
/* 168 */       int j = this.random.nextInt(3);
/* 169 */       Item item = br[this.random.nextInt(br.length)];
/* 170 */       if (paramInt > 0) j += this.random.nextInt(paramInt + 1);
/*     */       
/* 172 */       for (byte b1 = 0; b1 < j; b1++) {
/* 173 */         a(item, 1);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(EntityLiving paramEntityLiving, float paramFloat) {
/* 180 */     if (bZ())
/*     */       return; 
/* 182 */     EntityPotion entityPotion = new EntityPotion(this.world, this, 32732);
/* 183 */     entityPotion.pitch -= -20.0F;
/* 184 */     double d1 = paramEntityLiving.locX + paramEntityLiving.motX - this.locX;
/* 185 */     double d2 = paramEntityLiving.locY + paramEntityLiving.getHeadHeight() - 1.100000023841858D - this.locY;
/* 186 */     double d3 = paramEntityLiving.locZ + paramEntityLiving.motZ - this.locZ;
/* 187 */     float f = MathHelper.sqrt(d1 * d1 + d3 * d3);
/*     */     
/* 189 */     if (f >= 8.0F && !paramEntityLiving.hasEffect(MobEffectList.SLOWER_MOVEMENT)) {
/* 190 */       entityPotion.setPotionValue(32698);
/* 191 */     } else if (paramEntityLiving.getHealth() >= 8.0F && !paramEntityLiving.hasEffect(MobEffectList.POISON)) {
/* 192 */       entityPotion.setPotionValue(32660);
/* 193 */     } else if (f <= 3.0F && !paramEntityLiving.hasEffect(MobEffectList.WEAKNESS) && this.random.nextFloat() < 0.25F) {
/* 194 */       entityPotion.setPotionValue(32696);
/*     */     } 
/*     */     
/* 197 */     entityPotion.shoot(d1, d2 + (f * 0.2F), d3, 0.75F, 8.0F);
/*     */     
/* 199 */     this.world.addEntity(entityPotion);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityWitch.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */