/*     */ package net.minecraft.server.v1_7_R4;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.event.entity.EntityCombustByEntityEvent;
/*     */ import org.bukkit.event.entity.EntityTargetEvent;
/*     */ 
/*     */ public abstract class EntityMonster extends EntityCreature implements IMonster {
/*     */   public EntityMonster(World world) {
/*   9 */     super(world);
/*  10 */     this.b = 5;
/*     */   }
/*     */   
/*     */   public void e() {
/*  14 */     bb();
/*  15 */     float f = d(1.0F);
/*     */     
/*  17 */     if (f > 0.5F) {
/*  18 */       this.aU += 2;
/*     */     }
/*     */     
/*  21 */     super.e();
/*     */   }
/*     */   
/*     */   public void h() {
/*  25 */     super.h();
/*  26 */     if (!this.world.isStatic && this.world.difficulty == EnumDifficulty.PEACEFUL) {
/*  27 */       die();
/*     */     }
/*     */   }
/*     */   
/*     */   protected String H() {
/*  32 */     return "game.hostile.swim";
/*     */   }
/*     */   
/*     */   protected String O() {
/*  36 */     return "game.hostile.swim.splash";
/*     */   }
/*     */   
/*     */   protected Entity findTarget() {
/*  40 */     EntityHuman entityhuman = this.world.findNearbyVulnerablePlayer(this, 16.0D);
/*     */     
/*  42 */     return (entityhuman != null && hasLineOfSight(entityhuman)) ? entityhuman : null;
/*     */   }
/*     */   
/*     */   public boolean damageEntity(DamageSource damagesource, float f) {
/*  46 */     if (isInvulnerable())
/*  47 */       return false; 
/*  48 */     if (super.damageEntity(damagesource, f)) {
/*  49 */       Entity entity = damagesource.getEntity();
/*     */       
/*  51 */       if (this.passenger != entity && this.vehicle != entity) {
/*  52 */         if (entity != this)
/*     */         {
/*  54 */           if (entity != this.target && (this instanceof EntityBlaze || this instanceof EntityEnderman || this instanceof EntitySpider || this instanceof EntityGiantZombie || this instanceof EntitySilverfish)) {
/*  55 */             EntityTargetEvent event = CraftEventFactory.callEntityTargetEvent(this, entity, EntityTargetEvent.TargetReason.TARGET_ATTACKED_ENTITY);
/*     */             
/*  57 */             if (!event.isCancelled()) {
/*  58 */               if (event.getTarget() == null) {
/*  59 */                 this.target = null;
/*     */               } else {
/*  61 */                 this.target = ((CraftEntity)event.getTarget()).getHandle();
/*     */               } 
/*     */             }
/*     */           } else {
/*  65 */             this.target = entity;
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/*  70 */         return true;
/*     */       } 
/*  72 */       return true;
/*     */     } 
/*     */     
/*  75 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String aT() {
/*  80 */     return "game.hostile.hurt";
/*     */   }
/*     */   
/*     */   protected String aU() {
/*  84 */     return "game.hostile.die";
/*     */   }
/*     */   
/*     */   protected String o(int i) {
/*  88 */     return (i > 4) ? "game.hostile.hurt.fall.big" : "game.hostile.hurt.fall.small";
/*     */   }
/*     */   
/*     */   public boolean n(Entity entity) {
/*  92 */     float f = (float)getAttributeInstance(GenericAttributes.e).getValue();
/*  93 */     int i = 0;
/*     */     
/*  95 */     if (entity instanceof EntityLiving) {
/*  96 */       f += EnchantmentManager.a(this, (EntityLiving)entity);
/*  97 */       i += EnchantmentManager.getKnockbackEnchantmentLevel(this, (EntityLiving)entity);
/*     */     } 
/*     */     
/* 100 */     boolean flag = entity.damageEntity(DamageSource.mobAttack(this), f);
/*     */     
/* 102 */     if (flag) {
/* 103 */       if (i > 0) {
/* 104 */         entity.g((-MathHelper.sin(this.yaw * 3.1415927F / 180.0F) * i * 0.5F), 0.1D, (MathHelper.cos(this.yaw * 3.1415927F / 180.0F) * i * 0.5F));
/* 105 */         this.motX *= 0.6D;
/* 106 */         this.motZ *= 0.6D;
/*     */       } 
/*     */       
/* 109 */       int j = EnchantmentManager.getFireAspectEnchantmentLevel(this);
/*     */       
/* 111 */       if (j > 0) {
/*     */         
/* 113 */         EntityCombustByEntityEvent combustEvent = new EntityCombustByEntityEvent((Entity)getBukkitEntity(), (Entity)entity.getBukkitEntity(), j * 4);
/* 114 */         Bukkit.getPluginManager().callEvent((Event)combustEvent);
/*     */         
/* 116 */         if (!combustEvent.isCancelled()) {
/* 117 */           entity.setOnFire(combustEvent.getDuration());
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 122 */       if (entity instanceof EntityLiving) {
/* 123 */         EnchantmentManager.a((EntityLiving)entity, this);
/*     */       }
/*     */       
/* 126 */       EnchantmentManager.b(this, entity);
/*     */     } 
/*     */     
/* 129 */     return flag;
/*     */   }
/*     */   
/*     */   protected void a(Entity entity, float f) {
/* 133 */     if (this.attackTicks <= 0 && f < 2.0F && entity.boundingBox.e > this.boundingBox.b && entity.boundingBox.b < this.boundingBox.e) {
/* 134 */       this.attackTicks = 20;
/* 135 */       n(entity);
/*     */     } 
/*     */   }
/*     */   
/*     */   public float a(int i, int j, int k) {
/* 140 */     return 0.5F - this.world.n(i, j, k);
/*     */   }
/*     */   
/*     */   protected boolean j_() {
/* 144 */     int i = MathHelper.floor(this.locX);
/* 145 */     int j = MathHelper.floor(this.boundingBox.b);
/* 146 */     int k = MathHelper.floor(this.locZ);
/*     */     
/* 148 */     if (this.world.b(EnumSkyBlock.SKY, i, j, k) > this.random.nextInt(32)) {
/* 149 */       return false;
/*     */     }
/* 151 */     int l = this.world.getLightLevel(i, j, k);
/*     */     
/* 153 */     if (this.world.P()) {
/* 154 */       int i1 = this.world.j;
/*     */       
/* 156 */       this.world.j = 10;
/* 157 */       l = this.world.getLightLevel(i, j, k);
/* 158 */       this.world.j = i1;
/*     */     } 
/*     */     
/* 161 */     return (l <= this.random.nextInt(8));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canSpawn() {
/* 166 */     return (this.world.difficulty != EnumDifficulty.PEACEFUL && j_() && super.canSpawn());
/*     */   }
/*     */   
/*     */   protected void aD() {
/* 170 */     super.aD();
/* 171 */     getAttributeMap().b(GenericAttributes.e);
/*     */   }
/*     */   
/*     */   protected boolean aG() {
/* 175 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntityMonster.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */