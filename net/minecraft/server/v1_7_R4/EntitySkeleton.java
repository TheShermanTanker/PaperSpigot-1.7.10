/*     */ package net.minecraft.server.v1_7_R4;
/*     */ import java.util.Calendar;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.entity.EntityCombustEvent;
/*     */ import org.bukkit.event.entity.EntityShootBowEvent;
/*     */ 
/*     */ public class EntitySkeleton extends EntityMonster implements IRangedEntity {
/*   9 */   private PathfinderGoalArrowAttack bp = new PathfinderGoalArrowAttack(this, 1.0D, 20, 60, 15.0F);
/*  10 */   private PathfinderGoalMeleeAttack bq = new PathfinderGoalMeleeAttack(this, EntityHuman.class, 1.2D, false);
/*     */   
/*     */   public EntitySkeleton(World world) {
/*  13 */     super(world);
/*  14 */     this.goalSelector.a(1, new PathfinderGoalFloat(this));
/*  15 */     this.goalSelector.a(2, new PathfinderGoalRestrictSun(this));
/*  16 */     this.goalSelector.a(3, new PathfinderGoalFleeSun(this, 1.0D));
/*  17 */     this.goalSelector.a(5, new PathfinderGoalRandomStroll(this, 1.0D));
/*  18 */     this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
/*  19 */     this.goalSelector.a(6, new PathfinderGoalRandomLookaround(this));
/*  20 */     this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, false));
/*  21 */     this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, 0, true));
/*  22 */     if (world != null && !world.isStatic) {
/*  23 */       bZ();
/*     */     }
/*     */   }
/*     */   
/*     */   protected void aD() {
/*  28 */     super.aD();
/*  29 */     getAttributeInstance(GenericAttributes.d).setValue(0.25D);
/*     */   }
/*     */   
/*     */   protected void c() {
/*  33 */     super.c();
/*  34 */     this.datawatcher.a(13, new Byte((byte)0));
/*     */   }
/*     */   
/*     */   public boolean bk() {
/*  38 */     return true;
/*     */   }
/*     */   
/*     */   protected String t() {
/*  42 */     return "mob.skeleton.say";
/*     */   }
/*     */   
/*     */   protected String aT() {
/*  46 */     return "mob.skeleton.hurt";
/*     */   }
/*     */   
/*     */   protected String aU() {
/*  50 */     return "mob.skeleton.death";
/*     */   }
/*     */   
/*     */   protected void a(int i, int j, int k, Block block) {
/*  54 */     makeSound("mob.skeleton.step", 0.15F, 1.0F);
/*     */   }
/*     */   
/*     */   public boolean n(Entity entity) {
/*  58 */     if (super.n(entity)) {
/*  59 */       if (getSkeletonType() == 1 && entity instanceof EntityLiving) {
/*  60 */         ((EntityLiving)entity).addEffect(new MobEffect(MobEffectList.WITHER.id, 200));
/*     */       }
/*     */       
/*  63 */       return true;
/*     */     } 
/*  65 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public EnumMonsterType getMonsterType() {
/*  70 */     return EnumMonsterType.UNDEAD;
/*     */   }
/*     */   
/*     */   public void e() {
/*  74 */     if (this.world.w() && !this.world.isStatic) {
/*  75 */       float f = d(1.0F);
/*     */       
/*  77 */       if (f > 0.5F && this.random.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && this.world.i(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ))) {
/*  78 */         boolean flag = true;
/*  79 */         ItemStack itemstack = getEquipment(4);
/*     */         
/*  81 */         if (itemstack != null) {
/*  82 */           if (itemstack.g()) {
/*  83 */             itemstack.setData(itemstack.j() + this.random.nextInt(2));
/*  84 */             if (itemstack.j() >= itemstack.l()) {
/*  85 */               a(itemstack);
/*  86 */               setEquipment(4, (ItemStack)null);
/*     */             } 
/*     */           } 
/*     */           
/*  90 */           flag = false;
/*     */         } 
/*     */         
/*  93 */         if (flag) {
/*     */           
/*  95 */           EntityCombustEvent event = new EntityCombustEvent((Entity)getBukkitEntity(), 8);
/*  96 */           this.world.getServer().getPluginManager().callEvent((Event)event);
/*     */           
/*  98 */           if (!event.isCancelled()) {
/*  99 */             setOnFire(event.getDuration());
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 106 */     if (this.world.isStatic && getSkeletonType() == 1) {
/* 107 */       a(0.72F, 2.34F);
/*     */     }
/*     */     
/* 110 */     super.e();
/*     */   }
/*     */   
/*     */   public void ab() {
/* 114 */     super.ab();
/* 115 */     if (this.vehicle instanceof EntityCreature) {
/* 116 */       EntityCreature entitycreature = (EntityCreature)this.vehicle;
/*     */       
/* 118 */       this.aM = entitycreature.aM;
/*     */     } 
/*     */   }
/*     */   
/*     */   public void die(DamageSource damagesource) {
/* 123 */     super.die(damagesource);
/* 124 */     if (damagesource.i() instanceof EntityArrow && damagesource.getEntity() instanceof EntityHuman) {
/* 125 */       EntityHuman entityhuman = (EntityHuman)damagesource.getEntity();
/* 126 */       double d0 = entityhuman.locX - this.locX;
/* 127 */       double d1 = entityhuman.locZ - this.locZ;
/*     */       
/* 129 */       if (d0 * d0 + d1 * d1 >= 2500.0D) {
/* 130 */         entityhuman.a(AchievementList.v);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected Item getLoot() {
/* 136 */     return Items.ARROW;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dropDeathLoot(boolean flag, int i) {
/* 143 */     if (getSkeletonType() == 1) {
/* 144 */       int m = this.random.nextInt(3 + i) - 1;
/*     */       
/* 146 */       for (int k = 0; k < m; k++) {
/* 147 */         a(Items.COAL, 1);
/*     */       }
/*     */     } else {
/* 150 */       int m = this.random.nextInt(3 + i);
/*     */       
/* 152 */       for (int k = 0; k < m; k++) {
/* 153 */         a(Items.ARROW, 1);
/*     */       }
/*     */     } 
/*     */     
/* 157 */     int j = this.random.nextInt(3 + i);
/*     */     
/* 159 */     for (byte b = 0; b < j; b++) {
/* 160 */       a(Items.BONE, 1);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void getRareDrop(int i) {
/* 165 */     if (getSkeletonType() == 1) {
/* 166 */       a(new ItemStack(Items.SKULL, 1, 1), 0.0F);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void bC() {
/* 171 */     super.bC();
/* 172 */     setEquipment(0, new ItemStack(Items.BOW));
/*     */   }
/*     */   
/*     */   public GroupDataEntity prepare(GroupDataEntity groupdataentity) {
/* 176 */     groupdataentity = super.prepare(groupdataentity);
/* 177 */     if (this.world.worldProvider instanceof WorldProviderHell && aI().nextInt(5) > 0) {
/* 178 */       this.goalSelector.a(4, this.bq);
/* 179 */       setSkeletonType(1);
/* 180 */       setEquipment(0, new ItemStack(Items.STONE_SWORD));
/* 181 */       getAttributeInstance(GenericAttributes.e).setValue(4.0D);
/*     */     } else {
/* 183 */       this.goalSelector.a(4, this.bp);
/* 184 */       bC();
/* 185 */       bD();
/*     */     } 
/*     */     
/* 188 */     h((this.random.nextFloat() < 0.55F * this.world.b(this.locX, this.locY, this.locZ)));
/* 189 */     if (getEquipment(4) == null) {
/* 190 */       Calendar calendar = this.world.V();
/*     */       
/* 192 */       if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.random.nextFloat() < 0.25F) {
/* 193 */         setEquipment(4, new ItemStack((this.random.nextFloat() < 0.1F) ? Blocks.JACK_O_LANTERN : Blocks.PUMPKIN));
/* 194 */         this.dropChances[4] = 0.0F;
/*     */       } 
/*     */     } 
/*     */     
/* 198 */     return groupdataentity;
/*     */   }
/*     */   
/*     */   public void bZ() {
/* 202 */     this.goalSelector.a(this.bq);
/* 203 */     this.goalSelector.a(this.bp);
/* 204 */     ItemStack itemstack = be();
/*     */     
/* 206 */     if (itemstack != null && itemstack.getItem() == Items.BOW) {
/* 207 */       this.goalSelector.a(4, this.bp);
/*     */     } else {
/* 209 */       this.goalSelector.a(4, this.bq);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a(EntityLiving entityliving, float f) {
/* 214 */     EntityArrow entityarrow = new EntityArrow(this.world, this, entityliving, 1.6F, (14 - this.world.difficulty.a() * 4));
/* 215 */     int i = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_DAMAGE.id, be());
/* 216 */     int j = EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_KNOCKBACK.id, be());
/*     */     
/* 218 */     entityarrow.b((f * 2.0F) + this.random.nextGaussian() * 0.25D + (this.world.difficulty.a() * 0.11F));
/* 219 */     if (i > 0) {
/* 220 */       entityarrow.b(entityarrow.e() + i * 0.5D + 0.5D);
/*     */     }
/*     */     
/* 223 */     if (j > 0) {
/* 224 */       entityarrow.setKnockbackStrength(j);
/*     */     }
/*     */     
/* 227 */     if (EnchantmentManager.getEnchantmentLevel(Enchantment.ARROW_FIRE.id, be()) > 0 || getSkeletonType() == 1) {
/*     */       
/* 229 */       EntityCombustEvent entityCombustEvent = new EntityCombustEvent((Entity)entityarrow.getBukkitEntity(), 100);
/* 230 */       this.world.getServer().getPluginManager().callEvent((Event)entityCombustEvent);
/*     */       
/* 232 */       if (!entityCombustEvent.isCancelled()) {
/* 233 */         entityarrow.setOnFire(entityCombustEvent.getDuration());
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 239 */     EntityShootBowEvent event = CraftEventFactory.callEntityShootBowEvent(this, be(), entityarrow, 0.8F);
/* 240 */     if (event.isCancelled()) {
/* 241 */       event.getProjectile().remove();
/*     */       
/*     */       return;
/*     */     } 
/* 245 */     if (event.getProjectile() == entityarrow.getBukkitEntity()) {
/* 246 */       this.world.addEntity(entityarrow);
/*     */     }
/*     */ 
/*     */     
/* 250 */     makeSound("random.bow", 1.0F, 1.0F / (aI().nextFloat() * 0.4F + 0.8F));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSkeletonType() {
/* 255 */     return this.datawatcher.getByte(13);
/*     */   }
/*     */   
/*     */   public void setSkeletonType(int i) {
/* 259 */     this.datawatcher.watch(13, Byte.valueOf((byte)i));
/* 260 */     this.fireProof = (i == 1);
/* 261 */     if (i == 1) {
/* 262 */       a(0.72F, 2.34F);
/*     */     } else {
/* 264 */       a(0.6F, 1.8F);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 269 */     super.a(nbttagcompound);
/* 270 */     if (nbttagcompound.hasKeyOfType("SkeletonType", 99)) {
/* 271 */       byte b0 = nbttagcompound.getByte("SkeletonType");
/*     */       
/* 273 */       setSkeletonType(b0);
/*     */     } 
/*     */     
/* 276 */     bZ();
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/* 280 */     super.b(nbttagcompound);
/* 281 */     nbttagcompound.setByte("SkeletonType", (byte)getSkeletonType());
/*     */   }
/*     */   
/*     */   public void setEquipment(int i, ItemStack itemstack) {
/* 285 */     super.setEquipment(i, itemstack);
/* 286 */     if (!this.world.isStatic && i == 0) {
/* 287 */       bZ();
/*     */     }
/*     */   }
/*     */   
/*     */   public double ad() {
/* 292 */     return super.ad() - 0.5D;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntitySkeleton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */