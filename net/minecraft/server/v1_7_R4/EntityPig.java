/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*     */ import org.bukkit.event.entity.CreatureSpawnEvent;
/*     */ 
/*     */ public class EntityPig extends EntityAnimal {
/*     */   private final PathfinderGoalPassengerCarrotStick bp;
/*     */   
/*     */   public EntityPig(World world) {
/*  10 */     super(world);
/*  11 */     a(0.9F, 0.9F);
/*  12 */     getNavigation().a(true);
/*  13 */     this.goalSelector.a(0, new PathfinderGoalFloat(this));
/*  14 */     this.goalSelector.a(1, new PathfinderGoalPanic(this, 1.25D));
/*  15 */     this.goalSelector.a(2, this.bp = new PathfinderGoalPassengerCarrotStick(this, 0.3F));
/*  16 */     this.goalSelector.a(3, new PathfinderGoalBreed(this, 1.0D));
/*  17 */     this.goalSelector.a(4, new PathfinderGoalTempt(this, 1.2D, Items.CARROT_STICK, false));
/*  18 */     this.goalSelector.a(4, new PathfinderGoalTempt(this, 1.2D, Items.CARROT, false));
/*  19 */     this.goalSelector.a(5, new PathfinderGoalFollowParent(this, 1.1D));
/*  20 */     this.goalSelector.a(6, new PathfinderGoalRandomStroll(this, 1.0D));
/*  21 */     this.goalSelector.a(7, new PathfinderGoalLookAtPlayer(this, EntityHuman.class, 6.0F));
/*  22 */     this.goalSelector.a(8, new PathfinderGoalRandomLookaround(this));
/*     */   }
/*     */   
/*     */   public boolean bk() {
/*  26 */     return true;
/*     */   }
/*     */   
/*     */   protected void aD() {
/*  30 */     super.aD();
/*  31 */     getAttributeInstance(GenericAttributes.maxHealth).setValue(10.0D);
/*  32 */     getAttributeInstance(GenericAttributes.d).setValue(0.25D);
/*     */   }
/*     */   
/*     */   protected void bn() {
/*  36 */     super.bn();
/*     */   }
/*     */   
/*     */   public boolean bE() {
/*  40 */     ItemStack itemstack = ((EntityHuman)this.passenger).be();
/*     */     
/*  42 */     return (itemstack != null && itemstack.getItem() == Items.CARROT_STICK);
/*     */   }
/*     */   
/*     */   protected void c() {
/*  46 */     super.c();
/*  47 */     this.datawatcher.a(16, Byte.valueOf((byte)0));
/*     */   }
/*     */   
/*     */   public void b(NBTTagCompound nbttagcompound) {
/*  51 */     super.b(nbttagcompound);
/*  52 */     nbttagcompound.setBoolean("Saddle", hasSaddle());
/*     */   }
/*     */   
/*     */   public void a(NBTTagCompound nbttagcompound) {
/*  56 */     super.a(nbttagcompound);
/*  57 */     setSaddle(nbttagcompound.getBoolean("Saddle"));
/*     */   }
/*     */   
/*     */   protected String t() {
/*  61 */     return "mob.pig.say";
/*     */   }
/*     */   
/*     */   protected String aT() {
/*  65 */     return "mob.pig.say";
/*     */   }
/*     */   
/*     */   protected String aU() {
/*  69 */     return "mob.pig.death";
/*     */   }
/*     */   
/*     */   protected void a(int i, int j, int k, Block block) {
/*  73 */     makeSound("mob.pig.step", 0.15F, 1.0F);
/*     */   }
/*     */   
/*     */   public boolean a(EntityHuman entityhuman) {
/*  77 */     if (super.a(entityhuman))
/*  78 */       return true; 
/*  79 */     if (hasSaddle() && !this.world.isStatic && (this.passenger == null || this.passenger == entityhuman)) {
/*  80 */       entityhuman.mount(this);
/*  81 */       return true;
/*     */     } 
/*  83 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected Item getLoot() {
/*  88 */     return isBurning() ? Items.GRILLED_PORK : Items.PORK;
/*     */   }
/*     */   
/*     */   protected void dropDeathLoot(boolean flag, int i) {
/*  92 */     int j = this.random.nextInt(3) + 1 + this.random.nextInt(1 + i);
/*     */     
/*  94 */     for (int k = 0; k < j; k++) {
/*  95 */       if (isBurning()) {
/*  96 */         a(Items.GRILLED_PORK, 1);
/*     */       } else {
/*  98 */         a(Items.PORK, 1);
/*     */       } 
/*     */     } 
/*     */     
/* 102 */     if (hasSaddle()) {
/* 103 */       a(Items.SADDLE, 1);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean hasSaddle() {
/* 108 */     return ((this.datawatcher.getByte(16) & 0x1) != 0);
/*     */   }
/*     */   
/*     */   public void setSaddle(boolean flag) {
/* 112 */     if (flag) {
/* 113 */       this.datawatcher.watch(16, Byte.valueOf((byte)1));
/*     */     } else {
/* 115 */       this.datawatcher.watch(16, Byte.valueOf((byte)0));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void a(EntityLightning entitylightning) {
/* 120 */     if (!this.world.isStatic) {
/* 121 */       EntityPigZombie entitypigzombie = new EntityPigZombie(this.world);
/*     */ 
/*     */       
/* 124 */       if (CraftEventFactory.callPigZapEvent(this, entitylightning, entitypigzombie).isCancelled()) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 129 */       entitypigzombie.setEquipment(0, new ItemStack(Items.GOLD_SWORD));
/* 130 */       entitypigzombie.setPositionRotation(this.locX, this.locY, this.locZ, this.yaw, this.pitch);
/*     */       
/* 132 */       this.world.addEntity(entitypigzombie, CreatureSpawnEvent.SpawnReason.LIGHTNING);
/* 133 */       die();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void b(float f) {
/* 138 */     super.b(f);
/* 139 */     if (f > 5.0F && this.passenger instanceof EntityHuman) {
/* 140 */       ((EntityHuman)this.passenger).a(AchievementList.u);
/*     */     }
/*     */   }
/*     */   
/*     */   public EntityPig b(EntityAgeable entityageable) {
/* 145 */     return new EntityPig(this.world);
/*     */   }
/*     */   
/*     */   public boolean c(ItemStack itemstack) {
/* 149 */     return (itemstack != null && itemstack.getItem() == Items.CARROT);
/*     */   }
/*     */   
/*     */   public PathfinderGoalPassengerCarrotStick ca() {
/* 153 */     return this.bp;
/*     */   }
/*     */   
/*     */   public EntityAgeable createChild(EntityAgeable entityageable) {
/* 157 */     return b(entityageable);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityPig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */