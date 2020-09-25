/*     */ package net.minecraft.server.v1_7_R4;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*     */ import org.bukkit.event.entity.CreatureSpawnEvent;
/*     */ 
/*     */ public class EntityOcelot extends EntityTameableAnimal {
/*     */   public boolean spawnBonus = true;
/*     */   
/*     */   public EntityOcelot(World world) {
/*   9 */     super(world);
/*  10 */     a(0.6F, 0.8F);
/*  11 */     getNavigation().a(true);
/*  12 */     this.goalSelector.a(1, new PathfinderGoalFloat(this));
/*  13 */     this.goalSelector.a(2, this.bp);
/*  14 */     this.goalSelector.a(3, this.bq = new PathfinderGoalTempt(this, 0.6D, Items.RAW_FISH, true));
/*  15 */     this.goalSelector.a(4, new PathfinderGoalAvoidPlayer(this, EntityHuman.class, 16.0F, 0.8D, 1.33D));
/*  16 */     this.goalSelector.a(5, new PathfinderGoalFollowOwner(this, 1.0D, 10.0F, 5.0F));
/*  17 */     this.goalSelector.a(6, new PathfinderGoalJumpOnBlock(this, 1.33D));
/*  18 */     this.goalSelector.a(7, new PathfinderGoalLeapAtTarget(this, 0.3F));
/*  19 */     this.goalSelector.a(8, new PathfinderGoalOcelotAttack(this));
/*  20 */     this.goalSelector.a(9, new PathfinderGoalBreed(this, 0.8D));
/*  21 */     this.goalSelector.a(10, new PathfinderGoalRandomStroll(this, 0.8D));
/*  22 */     this.goalSelector.a(11, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 10.0F));
/*  23 */     this.targetSelector.a(1, new PathfinderGoalRandomTargetNonTamed(this, EntityChicken.class, 750, false));
/*     */   }
/*     */   private PathfinderGoalTempt bq;
/*     */   protected void c() {
/*  27 */     super.c();
/*  28 */     this.datawatcher.a(18, Byte.valueOf((byte)0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void h() {
/*  37 */     if (this.world.spigotConfig.altHopperTicking && isSitting()) {
/*  38 */       int xi = MathHelper.floor(this.boundingBox.a);
/*  39 */       int yi = MathHelper.floor(this.boundingBox.b) - 1;
/*  40 */       int zi = MathHelper.floor(this.boundingBox.c);
/*  41 */       int xf = MathHelper.floor(this.boundingBox.d);
/*  42 */       int yf = MathHelper.floor(this.boundingBox.e) - 1;
/*  43 */       int zf = MathHelper.floor(this.boundingBox.f);
/*  44 */       for (int a = xi; a <= xf; a++) {
/*  45 */         for (int c = zi; c <= zf; c++) {
/*  46 */           for (int b = yi; b <= yf; b++) {
/*  47 */             this.world.updateChestAndHoppers(a, b, c);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*  52 */     super.h();
/*     */   }
/*     */ 
/*     */   
/*     */   public void bp() {
/*  57 */     if (getControllerMove().a()) {
/*  58 */       double d0 = getControllerMove().b();
/*     */       
/*  60 */       if (d0 == 0.6D) {
/*  61 */         setSneaking(true);
/*  62 */         setSprinting(false);
/*  63 */       } else if (d0 == 1.33D) {
/*  64 */         setSneaking(false);
/*  65 */         setSprinting(true);
/*     */       } else {
/*  67 */         setSneaking(false);
/*  68 */         setSprinting(false);
/*     */       } 
/*     */     } else {
/*  71 */       setSneaking(false);
/*  72 */       setSprinting(false);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean isTypeNotPersistent() {
/*  77 */     return !isTamed();
/*     */   }
/*     */   
/*     */   public boolean bk() {
/*  81 */     return true;
/*     */   }
/*     */   
/*     */   protected void aD() {
/*  85 */     super.aD();
/*  86 */     getAttributeInstance(GenericAttributes.maxHealth).setValue(10.0D);
/*  87 */     getAttributeInstance(GenericAttributes.d).setValue(0.30000001192092896D);
/*     */   }
/*     */   
/*     */   protected void b(float f) {}
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/*  93 */     super.b(nbttagcompound);
/*  94 */     nbttagcompound.setInt("CatType", getCatType());
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/*  98 */     super.a(nbttagcompound);
/*  99 */     setCatType(nbttagcompound.getInt("CatType"));
/*     */   }
/*     */   
/*     */   protected String t() {
/* 103 */     return isTamed() ? (ce() ? "mob.cat.purr" : ((this.random.nextInt(4) == 0) ? "mob.cat.purreow" : "mob.cat.meow")) : "";
/*     */   }
/*     */   
/*     */   protected String aT() {
/* 107 */     return "mob.cat.hitt";
/*     */   }
/*     */   
/*     */   protected String aU() {
/* 111 */     return "mob.cat.hitt";
/*     */   }
/*     */   
/*     */   protected float bf() {
/* 115 */     return 0.4F;
/*     */   }
/*     */   
/*     */   protected Item getLoot() {
/* 119 */     return Items.LEATHER;
/*     */   }
/*     */   
/*     */   public boolean n(Entity entity) {
/* 123 */     return entity.damageEntity(DamageSource.mobAttack(this), 3.0F);
/*     */   }
/*     */   
/*     */   public boolean damageEntity(DamageSource damagesource, float f) {
/* 127 */     if (isInvulnerable()) {
/* 128 */       return false;
/*     */     }
/* 130 */     this.bp.setSitting(false);
/* 131 */     return super.damageEntity(damagesource, f);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void dropDeathLoot(boolean flag, int i) {}
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/* 138 */     ItemStack itemstack = entityhuman.inventory.getItemInHand();
/*     */     
/* 140 */     if (isTamed()) {
/* 141 */       if (e(entityhuman) && !this.world.isStatic && !c(itemstack)) {
/* 142 */         this.bp.setSitting(!isSitting());
/*     */       }
/* 144 */     } else if (this.bq.f() && itemstack != null && itemstack.getItem() == Items.RAW_FISH && entityhuman.f(this) < 9.0D) {
/* 145 */       if (!entityhuman.abilities.canInstantlyBuild) {
/* 146 */         itemstack.count--;
/*     */       }
/*     */       
/* 149 */       if (itemstack.count <= 0) {
/* 150 */         entityhuman.inventory.setItem(entityhuman.inventory.itemInHandIndex, (ItemStack)null);
/*     */       }
/*     */       
/* 153 */       if (!this.world.isStatic)
/*     */       {
/* 155 */         if (this.random.nextInt(3) == 0 && !CraftEventFactory.callEntityTameEvent(this, entityhuman).isCancelled()) {
/* 156 */           setTamed(true);
/* 157 */           setCatType(1 + this.world.random.nextInt(3));
/* 158 */           setOwnerUUID(entityhuman.getUniqueID().toString());
/* 159 */           i(true);
/* 160 */           this.bp.setSitting(true);
/* 161 */           this.world.broadcastEntityEffect(this, (byte)7);
/*     */         } else {
/* 163 */           i(false);
/* 164 */           this.world.broadcastEntityEffect(this, (byte)6);
/*     */         } 
/*     */       }
/*     */       
/* 168 */       return true;
/*     */     } 
/*     */     
/* 171 */     return super.a(entityhuman);
/*     */   }
/*     */   
/*     */   public EntityOcelot b(EntityAgeable entityageable) {
/* 175 */     EntityOcelot entityocelot = new EntityOcelot(this.world);
/*     */     
/* 177 */     if (isTamed()) {
/* 178 */       entityocelot.setOwnerUUID(getOwnerUUID());
/* 179 */       entityocelot.setTamed(true);
/* 180 */       entityocelot.setCatType(getCatType());
/*     */     } 
/*     */     
/* 183 */     return entityocelot;
/*     */   }
/*     */   
/*     */   public boolean c(ItemStack itemstack) {
/* 187 */     return (itemstack != null && itemstack.getItem() == Items.RAW_FISH);
/*     */   }
/*     */   
/*     */   public boolean mate(EntityAnimal entityanimal) {
/* 191 */     if (entityanimal == this)
/* 192 */       return false; 
/* 193 */     if (!isTamed())
/* 194 */       return false; 
/* 195 */     if (!(entityanimal instanceof EntityOcelot)) {
/* 196 */       return false;
/*     */     }
/* 198 */     EntityOcelot entityocelot = (EntityOcelot)entityanimal;
/*     */     
/* 200 */     return !entityocelot.isTamed() ? false : ((ce() && entityocelot.ce()));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCatType() {
/* 205 */     return this.datawatcher.getByte(18);
/*     */   }
/*     */   
/*     */   public void setCatType(int i) {
/* 209 */     this.datawatcher.watch(18, Byte.valueOf((byte)i));
/*     */   }
/*     */   
/*     */   public boolean canSpawn() {
/* 213 */     if (this.world.random.nextInt(3) == 0) {
/* 214 */       return false;
/*     */     }
/* 216 */     if (this.world.b(this.boundingBox) && this.world.getCubes(this, this.boundingBox).isEmpty() && !this.world.containsLiquid(this.boundingBox)) {
/* 217 */       int i = MathHelper.floor(this.locX);
/* 218 */       int j = MathHelper.floor(this.boundingBox.b);
/* 219 */       int k = MathHelper.floor(this.locZ);
/*     */       
/* 221 */       if (j < 63) {
/* 222 */         return false;
/*     */       }
/*     */       
/* 225 */       Block block = this.world.getType(i, j - 1, k);
/*     */       
/* 227 */       if (block == Blocks.GRASS || block.getMaterial() == Material.LEAVES) {
/* 228 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 232 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/* 237 */     return hasCustomName() ? getCustomName() : (isTamed() ? LocaleI18n.get("entity.Cat.name") : super.getName());
/*     */   }
/*     */   
/*     */   public GroupDataEntity prepare(GroupDataEntity groupdataentity) {
/* 241 */     groupdataentity = super.prepare(groupdataentity);
/* 242 */     if (this.spawnBonus && this.world.random.nextInt(7) == 0) {
/* 243 */       for (int i = 0; i < 2; i++) {
/* 244 */         EntityOcelot entityocelot = new EntityOcelot(this.world);
/*     */         
/* 246 */         entityocelot.setPositionRotation(this.locX, this.locY, this.locZ, this.yaw, 0.0F);
/* 247 */         entityocelot.setAge(-24000);
/* 248 */         this.world.addEntity(entityocelot, CreatureSpawnEvent.SpawnReason.OCELOT_BABY);
/*     */       } 
/*     */     }
/*     */     
/* 252 */     return groupdataentity;
/*     */   }
/*     */   
/*     */   public EntityAgeable createChild(EntityAgeable entityageable) {
/* 256 */     return b(entityageable);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityOcelot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */