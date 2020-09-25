/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Calendar;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftLivingEntity;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.entity.CreatureSpawnEvent;
/*     */ import org.bukkit.event.entity.EntityCombustByEntityEvent;
/*     */ import org.bukkit.event.entity.EntityCombustEvent;
/*     */ import org.bukkit.event.entity.EntityTargetEvent;
/*     */ import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
/*     */ import org.github.paperspigot.PaperSpigotConfig;
/*     */ 
/*     */ public class EntityZombie extends EntityMonster {
/*  18 */   protected static final IAttribute bp = (new AttributeRanged("zombie.spawnReinforcements", 0.0D, 0.0D, 1.0D)).a("Spawn Reinforcements Chance");
/*  19 */   private static final UUID bq = UUID.fromString("B9766B59-9566-4402-BC1F-2EE2A276D836");
/*     */   
/*  21 */   private static final AttributeModifier br = new AttributeModifier(bq, "Baby speed boost", PaperSpigotConfig.babyZombieMovementSpeed, 1);
/*  22 */   private final PathfinderGoalBreakDoor bs = new PathfinderGoalBreakDoor(this);
/*     */   private int bt;
/*     */   private boolean bu = false;
/*  25 */   private float bv = -1.0F;
/*     */   private float bw;
/*  27 */   private int lastTick = MinecraftServer.currentTick;
/*     */   
/*     */   public EntityZombie(World world) {
/*  30 */     super(world);
/*  31 */     getNavigation().b(true);
/*  32 */     this.goalSelector.a(0, new PathfinderGoalFloat(this));
/*  33 */     this.goalSelector.a(2, new PathfinderGoalMeleeAttack(this, EntityHuman.class, 1.0D, false));
/*  34 */     if (world.spigotConfig.zombieAggressiveTowardsVillager) this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this, EntityVillager.class, 1.0D, true)); 
/*  35 */     this.goalSelector.a(5, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
/*  36 */     this.goalSelector.a(6, new PathfinderGoalMoveThroughVillage(this, 1.0D, false));
/*  37 */     this.goalSelector.a(7, new PathfinderGoalRandomStroll(this, 1.0D));
/*  38 */     this.goalSelector.a(8, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
/*  39 */     this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
/*  40 */     this.targetSelector.a(1, new PathfinderGoalHurtByTarget(this, true));
/*  41 */     this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, 0, true));
/*  42 */     if (world.spigotConfig.zombieAggressiveTowardsVillager) this.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget(this, EntityVillager.class, 0, false)); 
/*  43 */     a(0.6F, 1.8F);
/*     */   }
/*     */   
/*     */   protected void aD() {
/*  47 */     super.aD();
/*  48 */     getAttributeInstance(GenericAttributes.b).setValue(40.0D);
/*  49 */     getAttributeInstance(GenericAttributes.d).setValue(0.23000000417232513D);
/*  50 */     getAttributeInstance(GenericAttributes.e).setValue(3.0D);
/*  51 */     getAttributeMap().b(bp).setValue(this.random.nextDouble() * 0.10000000149011612D);
/*     */   }
/*     */   
/*     */   protected void c() {
/*  55 */     super.c();
/*  56 */     getDataWatcher().a(12, Byte.valueOf((byte)0));
/*  57 */     getDataWatcher().a(13, Byte.valueOf((byte)0));
/*  58 */     getDataWatcher().a(14, Byte.valueOf((byte)0));
/*     */   }
/*     */   
/*     */   public int aV() {
/*  62 */     int i = super.aV() + 2;
/*     */     
/*  64 */     if (i > 20) {
/*  65 */       i = 20;
/*     */     }
/*     */     
/*  68 */     return i;
/*     */   }
/*     */   
/*     */   protected boolean bk() {
/*  72 */     return true;
/*     */   }
/*     */   
/*     */   public boolean bZ() {
/*  76 */     return this.bu;
/*     */   }
/*     */   
/*     */   public void a(boolean flag) {
/*  80 */     if (this.bu != flag) {
/*  81 */       this.bu = flag;
/*  82 */       if (flag) {
/*  83 */         this.goalSelector.a(1, this.bs);
/*     */       } else {
/*  85 */         this.goalSelector.a(this.bs);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isBaby() {
/*  91 */     return (getDataWatcher().getByte(12) == 1);
/*     */   }
/*     */   
/*     */   protected int getExpValue(EntityHuman entityhuman) {
/*  95 */     if (isBaby()) {
/*  96 */       this.b = (int)(this.b * 2.5F);
/*     */     }
/*     */     
/*  99 */     return super.getExpValue(entityhuman);
/*     */   }
/*     */   
/*     */   public void setBaby(boolean flag) {
/* 103 */     getDataWatcher().watch(12, Byte.valueOf((byte)(flag ? 1 : 0)));
/* 104 */     if (this.world != null && !this.world.isStatic) {
/* 105 */       AttributeInstance attributeinstance = getAttributeInstance(GenericAttributes.d);
/*     */       
/* 107 */       attributeinstance.b(br);
/* 108 */       if (flag) {
/* 109 */         attributeinstance.a(br);
/*     */       }
/*     */     } 
/*     */     
/* 113 */     k(flag);
/*     */   }
/*     */   
/*     */   public boolean isVillager() {
/* 117 */     return (getDataWatcher().getByte(13) == 1);
/*     */   }
/*     */   
/*     */   public void setVillager(boolean flag) {
/* 121 */     getDataWatcher().watch(13, Byte.valueOf((byte)(flag ? 1 : 0)));
/*     */   }
/*     */   
/*     */   public void e() {
/* 125 */     if (this.world.w() && !this.world.isStatic && !isBaby()) {
/* 126 */       float f = d(1.0F);
/*     */       
/* 128 */       if (f > 0.5F && this.random.nextFloat() * 30.0F < (f - 0.4F) * 2.0F && this.world.i(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ))) {
/* 129 */         boolean flag = true;
/* 130 */         ItemStack itemstack = getEquipment(4);
/*     */         
/* 132 */         if (itemstack != null) {
/* 133 */           if (itemstack.g()) {
/* 134 */             itemstack.setData(itemstack.j() + this.random.nextInt(2));
/* 135 */             if (itemstack.j() >= itemstack.l()) {
/* 136 */               a(itemstack);
/* 137 */               setEquipment(4, (ItemStack)null);
/*     */             } 
/*     */           } 
/*     */           
/* 141 */           flag = false;
/*     */         } 
/*     */         
/* 144 */         if (flag) {
/*     */           
/* 146 */           EntityCombustEvent event = new EntityCombustEvent((Entity)getBukkitEntity(), 8);
/* 147 */           this.world.getServer().getPluginManager().callEvent((Event)event);
/*     */           
/* 149 */           if (!event.isCancelled()) {
/* 150 */             setOnFire(event.getDuration());
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 157 */     if (am() && getGoalTarget() != null && this.vehicle instanceof EntityChicken) {
/* 158 */       ((EntityInsentient)this.vehicle).getNavigation().a(getNavigation().e(), 1.5D);
/*     */     }
/*     */     
/* 161 */     super.e();
/*     */   }
/*     */   
/*     */   public boolean damageEntity(DamageSource damagesource, float f) {
/* 165 */     if (!super.damageEntity(damagesource, f)) {
/* 166 */       return false;
/*     */     }
/* 168 */     EntityLiving entityliving = getGoalTarget();
/*     */     
/* 170 */     if (entityliving == null && bT() instanceof EntityLiving) {
/* 171 */       entityliving = (EntityLiving)bT();
/*     */     }
/*     */     
/* 174 */     if (entityliving == null && damagesource.getEntity() instanceof EntityLiving) {
/* 175 */       entityliving = (EntityLiving)damagesource.getEntity();
/*     */     }
/*     */     
/* 178 */     if (entityliving != null && this.world.difficulty == EnumDifficulty.HARD && this.random.nextFloat() < getAttributeInstance(bp).getValue()) {
/* 179 */       int i = MathHelper.floor(this.locX);
/* 180 */       int j = MathHelper.floor(this.locY);
/* 181 */       int k = MathHelper.floor(this.locZ);
/* 182 */       EntityZombie entityzombie = new EntityZombie(this.world);
/*     */       
/* 184 */       for (int l = 0; l < 50; l++) {
/* 185 */         int i1 = i + MathHelper.nextInt(this.random, 7, 40) * MathHelper.nextInt(this.random, -1, 1);
/* 186 */         int j1 = j + MathHelper.nextInt(this.random, 7, 40) * MathHelper.nextInt(this.random, -1, 1);
/* 187 */         int k1 = k + MathHelper.nextInt(this.random, 7, 40) * MathHelper.nextInt(this.random, -1, 1);
/*     */         
/* 189 */         if (World.a(this.world, i1, j1 - 1, k1) && this.world.getLightLevel(i1, j1, k1) < 10) {
/* 190 */           entityzombie.setPosition(i1, j1, k1);
/* 191 */           if (this.world.b(entityzombie.boundingBox) && this.world.getCubes(entityzombie, entityzombie.boundingBox).isEmpty() && !this.world.containsLiquid(entityzombie.boundingBox)) {
/* 192 */             this.world.addEntity(entityzombie, CreatureSpawnEvent.SpawnReason.REINFORCEMENTS);
/*     */             
/* 194 */             EntityTargetLivingEntityEvent event = CraftEventFactory.callEntityTargetLivingEvent(entityzombie, entityliving, EntityTargetEvent.TargetReason.REINFORCEMENT_TARGET);
/* 195 */             if (!event.isCancelled()) {
/* 196 */               if (event.getTarget() == null) {
/* 197 */                 entityzombie.setGoalTarget((EntityLiving)null);
/*     */               } else {
/* 199 */                 entityzombie.setGoalTarget(((CraftLivingEntity)event.getTarget()).getHandle());
/*     */               } 
/*     */             }
/*     */             
/* 203 */             entityzombie.prepare((GroupDataEntity)null);
/* 204 */             getAttributeInstance(bp).a(new AttributeModifier("Zombie reinforcement caller charge", -0.05000000074505806D, 0));
/* 205 */             entityzombie.getAttributeInstance(bp).a(new AttributeModifier("Zombie reinforcement callee charge", -0.05000000074505806D, 0));
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/* 212 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void h() {
/* 217 */     if (!this.world.isStatic && cc()) {
/* 218 */       int i = ce();
/*     */ 
/*     */       
/* 221 */       int elapsedTicks = MinecraftServer.currentTick - this.lastTick;
/* 222 */       this.lastTick = MinecraftServer.currentTick;
/* 223 */       i *= elapsedTicks;
/*     */ 
/*     */       
/* 226 */       this.bt -= i;
/* 227 */       if (this.bt <= 0) {
/* 228 */         cd();
/*     */       }
/*     */     } 
/*     */     
/* 232 */     super.h();
/*     */   }
/*     */   
/*     */   public boolean n(Entity entity) {
/* 236 */     boolean flag = super.n(entity);
/*     */     
/* 238 */     if (flag) {
/* 239 */       int i = this.world.difficulty.a();
/*     */       
/* 241 */       if (be() == null && isBurning() && this.random.nextFloat() < i * 0.3F) {
/*     */         
/* 243 */         EntityCombustByEntityEvent event = new EntityCombustByEntityEvent((Entity)getBukkitEntity(), (Entity)entity.getBukkitEntity(), 2 * i);
/* 244 */         this.world.getServer().getPluginManager().callEvent((Event)event);
/*     */         
/* 246 */         if (!event.isCancelled()) {
/* 247 */           entity.setOnFire(event.getDuration());
/*     */         }
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 253 */     return flag;
/*     */   }
/*     */   
/*     */   protected String t() {
/* 257 */     return "mob.zombie.say";
/*     */   }
/*     */   
/*     */   protected String aT() {
/* 261 */     return "mob.zombie.hurt";
/*     */   }
/*     */   
/*     */   protected String aU() {
/* 265 */     return "mob.zombie.death";
/*     */   }
/*     */   
/*     */   protected void a(int i, int j, int k, Block block) {
/* 269 */     makeSound("mob.zombie.step", 0.15F, 1.0F);
/*     */   }
/*     */   
/*     */   protected Item getLoot() {
/* 273 */     return Items.ROTTEN_FLESH;
/*     */   }
/*     */   
/*     */   public EnumMonsterType getMonsterType() {
/* 277 */     return EnumMonsterType.UNDEAD;
/*     */   }
/*     */   
/*     */   protected void getRareDrop(int i) {
/* 281 */     switch (this.random.nextInt(3)) {
/*     */       case 0:
/* 283 */         a(Items.IRON_INGOT, 1);
/*     */         break;
/*     */       
/*     */       case 1:
/* 287 */         a(Items.CARROT, 1);
/*     */         break;
/*     */       
/*     */       case 2:
/* 291 */         a(Items.POTATO, 1);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   protected void bC() {
/* 296 */     super.bC();
/* 297 */     if (this.random.nextFloat() < ((this.world.difficulty == EnumDifficulty.HARD) ? 0.05F : 0.01F)) {
/* 298 */       int i = this.random.nextInt(3);
/*     */       
/* 300 */       if (i == 0) {
/* 301 */         setEquipment(0, new ItemStack(Items.IRON_SWORD));
/*     */       } else {
/* 303 */         setEquipment(0, new ItemStack(Items.IRON_SPADE));
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/* 309 */     super.b(nbttagcompound);
/* 310 */     if (isBaby()) {
/* 311 */       nbttagcompound.setBoolean("IsBaby", true);
/*     */     }
/*     */     
/* 314 */     if (isVillager()) {
/* 315 */       nbttagcompound.setBoolean("IsVillager", true);
/*     */     }
/*     */     
/* 318 */     nbttagcompound.setInt("ConversionTime", cc() ? this.bt : -1);
/* 319 */     nbttagcompound.setBoolean("CanBreakDoors", bZ());
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 323 */     super.a(nbttagcompound);
/* 324 */     if (nbttagcompound.getBoolean("IsBaby")) {
/* 325 */       setBaby(true);
/*     */     }
/*     */     
/* 328 */     if (nbttagcompound.getBoolean("IsVillager")) {
/* 329 */       setVillager(true);
/*     */     }
/*     */     
/* 332 */     if (nbttagcompound.hasKeyOfType("ConversionTime", 99) && nbttagcompound.getInt("ConversionTime") > -1) {
/* 333 */       a(nbttagcompound.getInt("ConversionTime"));
/*     */     }
/*     */     
/* 336 */     a(nbttagcompound.getBoolean("CanBreakDoors"));
/*     */   }
/*     */   
/*     */   public void a(EntityLiving entityliving) {
/* 340 */     super.a(entityliving);
/* 341 */     if ((this.world.difficulty == EnumDifficulty.NORMAL || this.world.difficulty == EnumDifficulty.HARD) && entityliving instanceof EntityVillager) {
/* 342 */       if (this.world.difficulty != EnumDifficulty.HARD && this.random.nextBoolean()) {
/*     */         return;
/*     */       }
/*     */       
/* 346 */       EntityZombie entityzombie = new EntityZombie(this.world);
/*     */       
/* 348 */       entityzombie.k(entityliving);
/* 349 */       this.world.kill(entityliving);
/* 350 */       entityzombie.prepare((GroupDataEntity)null);
/* 351 */       entityzombie.setVillager(true);
/* 352 */       if (entityliving.isBaby()) {
/* 353 */         entityzombie.setBaby(true);
/*     */       }
/*     */       
/* 356 */       this.world.addEntity(entityzombie, CreatureSpawnEvent.SpawnReason.INFECTION);
/* 357 */       this.world.a((EntityHuman)null, 1016, (int)this.locX, (int)this.locY, (int)this.locZ, 0);
/*     */     } 
/*     */   }
/*     */   
/*     */   public GroupDataEntity prepare(GroupDataEntity groupdataentity) {
/* 362 */     Object object = super.prepare(groupdataentity);
/* 363 */     float f = this.world.b(this.locX, this.locY, this.locZ);
/*     */     
/* 365 */     h((this.random.nextFloat() < 0.55F * f));
/* 366 */     if (object == null) {
/* 367 */       object = new GroupDataZombie(this, (this.world.random.nextFloat() < 0.05F), (this.world.random.nextFloat() < 0.05F), (EmptyClassZombie)null);
/*     */     }
/*     */     
/* 370 */     if (object instanceof GroupDataZombie) {
/* 371 */       GroupDataZombie groupdatazombie = (GroupDataZombie)object;
/*     */       
/* 373 */       if (groupdatazombie.b) {
/* 374 */         setVillager(true);
/*     */       }
/*     */       
/* 377 */       if (groupdatazombie.a) {
/* 378 */         setBaby(true);
/* 379 */         if (this.world.random.nextFloat() < 0.05D) {
/* 380 */           List<EntityChicken> list = this.world.a(EntityChicken.class, this.boundingBox.grow(5.0D, 3.0D, 5.0D), IEntitySelector.b);
/*     */           
/* 382 */           if (!list.isEmpty()) {
/* 383 */             EntityChicken entitychicken = list.get(0);
/*     */             
/* 385 */             entitychicken.i(true);
/* 386 */             mount(entitychicken);
/*     */           } 
/* 388 */         } else if (this.world.random.nextFloat() < 0.05D) {
/* 389 */           EntityChicken entitychicken1 = new EntityChicken(this.world);
/*     */           
/* 391 */           entitychicken1.setPositionRotation(this.locX, this.locY, this.locZ, this.yaw, 0.0F);
/* 392 */           entitychicken1.prepare((GroupDataEntity)null);
/* 393 */           entitychicken1.i(true);
/* 394 */           this.world.addEntity(entitychicken1, CreatureSpawnEvent.SpawnReason.MOUNT);
/* 395 */           mount(entitychicken1);
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 400 */     a((this.random.nextFloat() < f * 0.1F));
/* 401 */     bC();
/* 402 */     bD();
/* 403 */     if (getEquipment(4) == null) {
/* 404 */       Calendar calendar = this.world.V();
/*     */       
/* 406 */       if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31 && this.random.nextFloat() < 0.25F) {
/* 407 */         setEquipment(4, new ItemStack((this.random.nextFloat() < 0.1F) ? Blocks.JACK_O_LANTERN : Blocks.PUMPKIN));
/* 408 */         this.dropChances[4] = 0.0F;
/*     */       } 
/*     */     } 
/*     */     
/* 412 */     getAttributeInstance(GenericAttributes.c).a(new AttributeModifier("Random spawn bonus", this.random.nextDouble() * 0.05000000074505806D, 0));
/* 413 */     double d0 = this.random.nextDouble() * 1.5D * this.world.b(this.locX, this.locY, this.locZ);
/*     */     
/* 415 */     if (d0 > 1.0D) {
/* 416 */       getAttributeInstance(GenericAttributes.b).a(new AttributeModifier("Random zombie-spawn bonus", d0, 2));
/*     */     }
/*     */     
/* 419 */     if (this.random.nextFloat() < f * 0.05F) {
/* 420 */       getAttributeInstance(bp).a(new AttributeModifier("Leader zombie bonus", this.random.nextDouble() * 0.25D + 0.5D, 0));
/* 421 */       getAttributeInstance(GenericAttributes.maxHealth).a(new AttributeModifier("Leader zombie bonus", this.random.nextDouble() * 3.0D + 1.0D, 2));
/* 422 */       a(true);
/*     */     } 
/*     */     
/* 425 */     return (GroupDataEntity)object;
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/* 429 */     ItemStack itemstack = entityhuman.bF();
/*     */     
/* 431 */     if (itemstack != null && itemstack.getItem() == Items.GOLDEN_APPLE && itemstack.getData() == 0 && isVillager() && hasEffect(MobEffectList.WEAKNESS)) {
/* 432 */       if (!entityhuman.abilities.canInstantlyBuild) {
/* 433 */         itemstack.count--;
/*     */       }
/*     */       
/* 436 */       if (itemstack.count <= 0) {
/* 437 */         entityhuman.inventory.setItem(entityhuman.inventory.itemInHandIndex, (ItemStack)null);
/*     */       }
/*     */       
/* 440 */       if (!this.world.isStatic) {
/* 441 */         a(this.random.nextInt(2401) + 3600);
/*     */       }
/*     */       
/* 444 */       return true;
/*     */     } 
/* 446 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void a(int i) {
/* 451 */     this.bt = i;
/* 452 */     getDataWatcher().watch(14, Byte.valueOf((byte)1));
/* 453 */     removeEffect(MobEffectList.WEAKNESS.id);
/* 454 */     addEffect(new MobEffect(MobEffectList.INCREASE_DAMAGE.id, i, Math.min(this.world.difficulty.a() - 1, 0)));
/* 455 */     this.world.broadcastEntityEffect(this, (byte)16);
/*     */   }
/*     */   
/*     */   protected boolean isTypeNotPersistent() {
/* 459 */     return !cc();
/*     */   }
/*     */   
/*     */   public boolean cc() {
/* 463 */     return (getDataWatcher().getByte(14) == 1);
/*     */   }
/*     */   
/*     */   protected void cd() {
/* 467 */     EntityVillager entityvillager = new EntityVillager(this.world);
/*     */     
/* 469 */     entityvillager.k(this);
/* 470 */     entityvillager.prepare((GroupDataEntity)null);
/* 471 */     entityvillager.cd();
/* 472 */     if (isBaby()) {
/* 473 */       entityvillager.setAge(-24000);
/*     */     }
/*     */     
/* 476 */     this.world.kill(this);
/* 477 */     this.world.addEntity(entityvillager, CreatureSpawnEvent.SpawnReason.CURED);
/* 478 */     entityvillager.addEffect(new MobEffect(MobEffectList.CONFUSION.id, 200, 0));
/* 479 */     this.world.a((EntityHuman)null, 1017, (int)this.locX, (int)this.locY, (int)this.locZ, 0);
/*     */   }
/*     */   
/*     */   protected int ce() {
/* 483 */     int i = 1;
/*     */     
/* 485 */     if (this.random.nextFloat() < 0.01F) {
/* 486 */       int j = 0;
/*     */       
/* 488 */       for (int k = (int)this.locX - 4; k < (int)this.locX + 4 && j < 14; k++) {
/* 489 */         for (int l = (int)this.locY - 4; l < (int)this.locY + 4 && j < 14; l++) {
/* 490 */           for (int i1 = (int)this.locZ - 4; i1 < (int)this.locZ + 4 && j < 14; i1++) {
/* 491 */             Block block = this.world.getType(k, l, i1);
/*     */             
/* 493 */             if (block == Blocks.IRON_FENCE || block == Blocks.BED) {
/* 494 */               if (this.random.nextFloat() < 0.3F) {
/* 495 */                 i++;
/*     */               }
/*     */               
/* 498 */               j++;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 505 */     return i;
/*     */   }
/*     */   
/*     */   public void k(boolean flag) {
/* 509 */     a(flag ? 0.5F : 1.0F);
/*     */   }
/*     */   
/*     */   protected final void a(float f, float f1) {
/* 513 */     boolean flag = (this.bv > 0.0F && this.bw > 0.0F);
/*     */     
/* 515 */     this.bv = f;
/* 516 */     this.bw = f1;
/* 517 */     if (!flag) {
/* 518 */       a(1.0F);
/*     */     }
/*     */   }
/*     */   
/*     */   protected final void a(float f) {
/* 523 */     super.a(this.bv * f, this.bw * f);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityZombie.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */