/*     */ package net.minecraft.server.v1_7_R4;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftLivingEntity;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*     */ import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
/*     */ 
/*     */ public class EntityIronGolem extends EntityGolem {
/*     */   private int bq;
/*     */   Village bp;
/*     */   
/*     */   public EntityIronGolem(World world) {
/*  11 */     super(world);
/*  12 */     a(1.4F, 2.9F);
/*  13 */     getNavigation().a(true);
/*  14 */     this.goalSelector.a(1, new PathfinderGoalMeleeAttack(this, 1.0D, true));
/*  15 */     this.goalSelector.a(2, new PathfinderGoalMoveTowardsTarget(this, 0.9D, 32.0F));
/*  16 */     this.goalSelector.a(3, new PathfinderGoalMoveThroughVillage(this, 0.6D, true));
/*  17 */     this.goalSelector.a(4, new PathfinderGoalMoveTowardsRestriction(this, 1.0D));
/*  18 */     this.goalSelector.a(5, new PathfinderGoalOfferFlower(this));
/*  19 */     this.goalSelector.a(6, new PathfinderGoalRandomStroll(this, 0.6D));
/*  20 */     this.goalSelector.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
/*  21 */     this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
/*  22 */     this.targetSelector.a(1, new PathfinderGoalDefendVillage(this));
/*  23 */     this.targetSelector.a(2, new PathfinderGoalHurtByTarget(this, false));
/*  24 */     this.targetSelector.a(3, new PathfinderGoalNearestAttackableTarget(this, EntityInsentient.class, 0, false, true, IMonster.a));
/*     */   }
/*     */   private int br; private int bs;
/*     */   protected void c() {
/*  28 */     super.c();
/*  29 */     this.datawatcher.a(16, Byte.valueOf((byte)0));
/*     */   }
/*     */   
/*     */   public boolean bk() {
/*  33 */     return true;
/*     */   }
/*     */   
/*     */   protected void bp() {
/*  37 */     if (--this.bq <= 0) {
/*  38 */       this.bq = 70 + this.random.nextInt(50);
/*  39 */       this.bp = this.world.villages.getClosestVillage(MathHelper.floor(this.locX), MathHelper.floor(this.locY), MathHelper.floor(this.locZ), 32);
/*  40 */       if (this.bp == null) {
/*  41 */         bX();
/*     */       } else {
/*  43 */         ChunkCoordinates chunkcoordinates = this.bp.getCenter();
/*     */         
/*  45 */         a(chunkcoordinates.x, chunkcoordinates.y, chunkcoordinates.z, (int)(this.bp.getSize() * 0.6F));
/*     */       } 
/*     */     } 
/*     */     
/*  49 */     super.bp();
/*     */   }
/*     */   
/*     */   protected void aD() {
/*  53 */     super.aD();
/*  54 */     getAttributeInstance(GenericAttributes.maxHealth).setValue(100.0D);
/*  55 */     getAttributeInstance(GenericAttributes.d).setValue(0.25D);
/*     */   }
/*     */   
/*     */   protected int j(int i) {
/*  59 */     return i;
/*     */   }
/*     */   
/*     */   protected void o(Entity entity) {
/*  63 */     if (entity instanceof IMonster && aI().nextInt(20) == 0) {
/*     */       
/*  65 */       EntityTargetLivingEntityEvent event = CraftEventFactory.callEntityTargetLivingEvent(this, (EntityLiving)entity, EntityTargetEvent.TargetReason.COLLISION);
/*  66 */       if (!event.isCancelled()) {
/*  67 */         if (event.getTarget() == null) {
/*  68 */           setGoalTarget((EntityLiving)null);
/*     */         } else {
/*  70 */           setGoalTarget(((CraftLivingEntity)event.getTarget()).getHandle());
/*     */         } 
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/*  76 */     super.o(entity);
/*     */   }
/*     */   
/*     */   public void e() {
/*  80 */     super.e();
/*  81 */     if (this.br > 0) {
/*  82 */       this.br--;
/*     */     }
/*     */     
/*  85 */     if (this.bs > 0) {
/*  86 */       this.bs--;
/*     */     }
/*     */     
/*  89 */     if (this.motX * this.motX + this.motZ * this.motZ > 2.500000277905201E-7D && this.random.nextInt(5) == 0) {
/*  90 */       int i = MathHelper.floor(this.locX);
/*  91 */       int j = MathHelper.floor(this.locY - 0.20000000298023224D - this.height);
/*  92 */       int k = MathHelper.floor(this.locZ);
/*  93 */       Block block = this.world.getType(i, j, k);
/*     */       
/*  95 */       if (block.getMaterial() != Material.AIR) {
/*  96 */         this.world.addParticle("blockcrack_" + Block.getId(block) + "_" + this.world.getData(i, j, k), this.locX + (this.random.nextFloat() - 0.5D) * this.width, this.boundingBox.b + 0.1D, this.locZ + (this.random.nextFloat() - 0.5D) * this.width, 4.0D * (this.random.nextFloat() - 0.5D), 0.5D, (this.random.nextFloat() - 0.5D) * 4.0D);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean a(Class<?> oclass) {
/* 102 */     return (isPlayerCreated() && EntityHuman.class.isAssignableFrom(oclass)) ? false : super.a(oclass);
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/* 106 */     super.b(nbttagcompound);
/* 107 */     nbttagcompound.setBoolean("PlayerCreated", isPlayerCreated());
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/* 111 */     super.a(nbttagcompound);
/* 112 */     setPlayerCreated(nbttagcompound.getBoolean("PlayerCreated"));
/*     */   }
/*     */   
/*     */   public boolean n(Entity entity) {
/* 116 */     this.br = 10;
/* 117 */     this.world.broadcastEntityEffect(this, (byte)4);
/* 118 */     boolean flag = entity.damageEntity(DamageSource.mobAttack(this), (7 + this.random.nextInt(15)));
/*     */     
/* 120 */     if (flag) {
/* 121 */       entity.motY += 0.4000000059604645D;
/*     */     }
/*     */     
/* 124 */     makeSound("mob.irongolem.throw", 1.0F, 1.0F);
/* 125 */     return flag;
/*     */   }
/*     */   
/*     */   public Village bZ() {
/* 129 */     return this.bp;
/*     */   }
/*     */   
/*     */   public void a(boolean flag) {
/* 133 */     this.bs = flag ? 400 : 0;
/* 134 */     this.world.broadcastEntityEffect(this, (byte)11);
/*     */   }
/*     */   
/*     */   protected String aT() {
/* 138 */     return "mob.irongolem.hit";
/*     */   }
/*     */   
/*     */   protected String aU() {
/* 142 */     return "mob.irongolem.death";
/*     */   }
/*     */   
/*     */   protected void a(int i, int j, int k, Block block) {
/* 146 */     makeSound("mob.irongolem.walk", 1.0F, 1.0F);
/*     */   }
/*     */   
/*     */   protected void dropDeathLoot(boolean flag, int i) {
/* 150 */     int j = this.random.nextInt(3);
/*     */     
/*     */     int k;
/*     */     
/* 154 */     for (k = 0; k < j; k++) {
/* 155 */       a(Item.getItemOf(Blocks.RED_ROSE), 1, 0.0F);
/*     */     }
/*     */     
/* 158 */     k = 3 + this.random.nextInt(3);
/*     */     
/* 160 */     for (int l = 0; l < k; l++) {
/* 161 */       a(Items.IRON_INGOT, 1);
/*     */     }
/*     */   }
/*     */   
/*     */   public int cb() {
/* 166 */     return this.bs;
/*     */   }
/*     */   
/*     */   public boolean isPlayerCreated() {
/* 170 */     return ((this.datawatcher.getByte(16) & 0x1) != 0);
/*     */   }
/*     */   
/*     */   public void setPlayerCreated(boolean flag) {
/* 174 */     byte b0 = this.datawatcher.getByte(16);
/*     */     
/* 176 */     if (flag) {
/* 177 */       this.datawatcher.watch(16, Byte.valueOf((byte)(b0 | 0x1)));
/*     */     } else {
/* 179 */       this.datawatcher.watch(16, Byte.valueOf((byte)(b0 & 0xFFFFFFFE)));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void die(DamageSource damagesource) {
/* 184 */     if (!isPlayerCreated() && this.killer != null && this.bp != null) {
/* 185 */       this.bp.a(this.killer.getName(), -5);
/*     */     }
/*     */     
/* 188 */     super.die(damagesource);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityIronGolem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */