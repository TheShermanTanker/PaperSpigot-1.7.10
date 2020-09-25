/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.entity.CreeperPowerEvent;
/*     */ import org.bukkit.event.entity.ExplosionPrimeEvent;
/*     */ 
/*     */ public class EntityCreeper extends EntityMonster {
/*     */   private int bp;
/*     */   private int fuseTicks;
/*  12 */   private int maxFuseTicks = 30;
/*  13 */   private int explosionRadius = 3;
/*  14 */   private int record = -1;
/*     */   
/*     */   public EntityCreeper(World world) {
/*  17 */     super(world);
/*  18 */     this.goalSelector.a(1, new PathfinderGoalFloat(this));
/*  19 */     this.goalSelector.a(2, new PathfinderGoalSwell(this));
/*  20 */     this.goalSelector.a(3, new PathfinderGoalAvoidPlayer(this, EntityOcelot.class, 6.0F, 1.0D, 1.2D));
/*  21 */     this.goalSelector.a(4, new PathfinderGoalMeleeAttack(this, 1.0D, false));
/*  22 */     this.goalSelector.a(5, new PathfinderGoalRandomStroll(this, 0.8D));
/*  23 */     this.goalSelector.a(6, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 8.0F));
/*  24 */     this.goalSelector.a(6, new PathfinderGoalRandomLookaround(this));
/*  25 */     this.targetSelector.a(1, new PathfinderGoalNearestAttackableTarget(this, EntityHuman.class, 0, true));
/*  26 */     this.targetSelector.a(2, new PathfinderGoalHurtByTarget(this, false));
/*     */   }
/*     */   
/*     */   protected void aD() {
/*  30 */     super.aD();
/*  31 */     getAttributeInstance(GenericAttributes.d).setValue(0.25D);
/*     */   }
/*     */   
/*     */   public boolean bk() {
/*  35 */     return true;
/*     */   }
/*     */   
/*     */   public int ax() {
/*  39 */     return (getGoalTarget() == null) ? 3 : (3 + (int)(getHealth() - 1.0F));
/*     */   }
/*     */   
/*     */   protected void b(float f) {
/*  43 */     super.b(f);
/*  44 */     this.fuseTicks = (int)(this.fuseTicks + f * 1.5F);
/*  45 */     if (this.fuseTicks > this.maxFuseTicks - 5) {
/*  46 */       this.fuseTicks = this.maxFuseTicks - 5;
/*     */     }
/*     */   }
/*     */   
/*     */   protected void c() {
/*  51 */     super.c();
/*  52 */     this.datawatcher.a(16, Byte.valueOf((byte)-1));
/*  53 */     this.datawatcher.a(17, Byte.valueOf((byte)0));
/*  54 */     this.datawatcher.a(18, Byte.valueOf((byte)0));
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/*  58 */     super.b(nbttagcompound);
/*  59 */     if (this.datawatcher.getByte(17) == 1) {
/*  60 */       nbttagcompound.setBoolean("powered", true);
/*     */     }
/*     */     
/*  63 */     nbttagcompound.setShort("Fuse", (short)this.maxFuseTicks);
/*  64 */     nbttagcompound.setByte("ExplosionRadius", (byte)this.explosionRadius);
/*  65 */     nbttagcompound.setBoolean("ignited", cc());
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/*  69 */     super.a(nbttagcompound);
/*  70 */     this.datawatcher.watch(17, Byte.valueOf((byte)(nbttagcompound.getBoolean("powered") ? 1 : 0)));
/*  71 */     if (nbttagcompound.hasKeyOfType("Fuse", 99)) {
/*  72 */       this.maxFuseTicks = nbttagcompound.getShort("Fuse");
/*     */     }
/*     */     
/*  75 */     if (nbttagcompound.hasKeyOfType("ExplosionRadius", 99)) {
/*  76 */       this.explosionRadius = nbttagcompound.getByte("ExplosionRadius");
/*     */     }
/*     */     
/*  79 */     if (nbttagcompound.getBoolean("ignited")) {
/*  80 */       cd();
/*     */     }
/*     */   }
/*     */   
/*     */   public void h() {
/*  85 */     if (isAlive()) {
/*  86 */       this.bp = this.fuseTicks;
/*  87 */       if (cc()) {
/*  88 */         a(1);
/*     */       }
/*     */       
/*  91 */       int i = cb();
/*     */       
/*  93 */       if (i > 0 && this.fuseTicks == 0) {
/*  94 */         makeSound("creeper.primed", 1.0F, 0.5F);
/*     */       }
/*     */       
/*  97 */       this.fuseTicks += i;
/*  98 */       if (this.fuseTicks < 0) {
/*  99 */         this.fuseTicks = 0;
/*     */       }
/*     */       
/* 102 */       if (this.fuseTicks >= this.maxFuseTicks) {
/* 103 */         this.fuseTicks = this.maxFuseTicks;
/* 104 */         ce();
/*     */       } 
/*     */     } 
/*     */     
/* 108 */     super.h();
/*     */   }
/*     */   
/*     */   protected String aT() {
/* 112 */     return "mob.creeper.say";
/*     */   }
/*     */   
/*     */   protected String aU() {
/* 116 */     return "mob.creeper.death";
/*     */   }
/*     */ 
/*     */   
/*     */   public void die(DamageSource damagesource) {
/* 121 */     if (damagesource.getEntity() instanceof EntitySkeleton) {
/* 122 */       int i = Item.getId(Items.RECORD_1);
/* 123 */       int j = Item.getId(Items.RECORD_12);
/* 124 */       int k = i + this.random.nextInt(j - i + 1);
/*     */ 
/*     */ 
/*     */       
/* 128 */       this.record = k;
/*     */     } 
/*     */ 
/*     */     
/* 132 */     super.die(damagesource);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void dropDeathLoot(boolean flag, int i) {
/* 137 */     super.dropDeathLoot(flag, i);
/*     */ 
/*     */     
/* 140 */     if (this.record != -1) {
/* 141 */       a(Item.getById(this.record), 1);
/* 142 */       this.record = -1;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean n(Entity entity) {
/* 148 */     return true;
/*     */   }
/*     */   
/*     */   public boolean isPowered() {
/* 152 */     return (this.datawatcher.getByte(17) == 1);
/*     */   }
/*     */   
/*     */   protected Item getLoot() {
/* 156 */     return Items.SULPHUR;
/*     */   }
/*     */   
/*     */   public int cb() {
/* 160 */     return this.datawatcher.getByte(16);
/*     */   }
/*     */   
/*     */   public void a(int i) {
/* 164 */     this.datawatcher.watch(16, Byte.valueOf((byte)i));
/*     */   }
/*     */   
/*     */   public void a(EntityLightning entitylightning) {
/* 168 */     super.a(entitylightning);
/*     */     
/* 170 */     if (CraftEventFactory.callCreeperPowerEvent(this, entitylightning, CreeperPowerEvent.PowerCause.LIGHTNING).isCancelled()) {
/*     */       return;
/*     */     }
/*     */     
/* 174 */     setPowered(true);
/*     */   }
/*     */   
/*     */   public void setPowered(boolean powered) {
/* 178 */     if (!powered) {
/* 179 */       this.datawatcher.watch(17, Byte.valueOf((byte)0));
/*     */     } else {
/* 181 */       this.datawatcher.watch(17, Byte.valueOf((byte)1));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean a(EntityHuman entityhuman) {
/* 187 */     ItemStack itemstack = entityhuman.inventory.getItemInHand();
/*     */     
/* 189 */     if (itemstack != null && itemstack.getItem() == Items.FLINT_AND_STEEL) {
/* 190 */       this.world.makeSound(this.locX + 0.5D, this.locY + 0.5D, this.locZ + 0.5D, "fire.ignite", 1.0F, this.random.nextFloat() * 0.4F + 0.8F);
/* 191 */       entityhuman.ba();
/* 192 */       if (!this.world.isStatic) {
/* 193 */         cd();
/* 194 */         itemstack.damage(1, entityhuman);
/* 195 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 199 */     return super.a(entityhuman);
/*     */   }
/*     */   
/*     */   private void ce() {
/* 203 */     if (!this.world.isStatic) {
/* 204 */       boolean flag = this.world.getGameRules().getBoolean("mobGriefing");
/*     */ 
/*     */       
/* 207 */       float radius = isPowered() ? 6.0F : 3.0F;
/*     */       
/* 209 */       ExplosionPrimeEvent event = new ExplosionPrimeEvent((Entity)getBukkitEntity(), radius, false);
/* 210 */       this.world.getServer().getPluginManager().callEvent((Event)event);
/* 211 */       if (!event.isCancelled()) {
/* 212 */         this.world.createExplosion(this, this.locX, this.locY, this.locZ, event.getRadius(), event.getFire(), flag);
/* 213 */         die();
/*     */       } else {
/* 215 */         this.fuseTicks = 0;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean cc() {
/* 222 */     return (this.datawatcher.getByte(18) != 0);
/*     */   }
/*     */   
/*     */   public void cd() {
/* 226 */     this.datawatcher.watch(18, Byte.valueOf((byte)1));
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityCreeper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */