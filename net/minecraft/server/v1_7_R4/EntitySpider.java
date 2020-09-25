/*     */ package net.minecraft.server.v1_7_R4;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.event.entity.EntityTargetEvent;
/*     */ 
/*     */ public class EntitySpider extends EntityMonster {
/*     */   public EntitySpider(World world) {
/*   8 */     super(world);
/*   9 */     a(1.4F, 0.9F);
/*     */   }
/*     */   
/*     */   protected void c() {
/*  13 */     super.c();
/*  14 */     this.datawatcher.a(16, new Byte((byte)0));
/*     */   }
/*     */   
/*     */   public void h() {
/*  18 */     super.h();
/*  19 */     if (!this.world.isStatic) {
/*  20 */       a(this.positionChanged);
/*     */     }
/*     */   }
/*     */   
/*     */   protected void aD() {
/*  25 */     super.aD();
/*  26 */     getAttributeInstance(GenericAttributes.maxHealth).setValue(16.0D);
/*  27 */     getAttributeInstance(GenericAttributes.d).setValue(0.800000011920929D);
/*     */   }
/*     */   
/*     */   protected Entity findTarget() {
/*  31 */     float f = d(1.0F);
/*     */     
/*  33 */     if (f < 0.5F) {
/*  34 */       double d0 = 16.0D;
/*     */       
/*  36 */       return this.world.findNearbyVulnerablePlayer(this, d0);
/*     */     } 
/*  38 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String t() {
/*  43 */     return "mob.spider.say";
/*     */   }
/*     */   
/*     */   protected String aT() {
/*  47 */     return "mob.spider.say";
/*     */   }
/*     */   
/*     */   protected String aU() {
/*  51 */     return "mob.spider.death";
/*     */   }
/*     */   
/*     */   protected void a(int i, int j, int k, Block block) {
/*  55 */     makeSound("mob.spider.step", 0.15F, 1.0F);
/*     */   }
/*     */   
/*     */   protected void a(Entity entity, float f) {
/*  59 */     float f1 = d(1.0F);
/*     */     
/*  61 */     if (f1 > 0.5F && this.random.nextInt(100) == 0) {
/*     */       
/*  63 */       EntityTargetEvent event = new EntityTargetEvent((Entity)getBukkitEntity(), null, EntityTargetEvent.TargetReason.FORGOT_TARGET);
/*  64 */       this.world.getServer().getPluginManager().callEvent((Event)event);
/*     */       
/*  66 */       if (!event.isCancelled()) {
/*  67 */         if (event.getTarget() == null) {
/*  68 */           this.target = null;
/*     */         } else {
/*  70 */           this.target = ((CraftEntity)event.getTarget()).getHandle();
/*     */         } 
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*  76 */     } else if (f > 2.0F && f < 6.0F && this.random.nextInt(10) == 0) {
/*  77 */       if (this.onGround) {
/*  78 */         double d0 = entity.locX - this.locX;
/*  79 */         double d1 = entity.locZ - this.locZ;
/*  80 */         float f2 = MathHelper.sqrt(d0 * d0 + d1 * d1);
/*     */         
/*  82 */         this.motX = d0 / f2 * 0.5D * 0.800000011920929D + this.motX * 0.20000000298023224D;
/*  83 */         this.motZ = d1 / f2 * 0.5D * 0.800000011920929D + this.motZ * 0.20000000298023224D;
/*  84 */         this.motY = 0.4000000059604645D;
/*     */       } 
/*     */     } else {
/*  87 */       super.a(entity, f);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected Item getLoot() {
/*  93 */     return Items.STRING;
/*     */   }
/*     */   
/*     */   protected void dropDeathLoot(boolean flag, int i) {
/*  97 */     super.dropDeathLoot(flag, i);
/*  98 */     if (flag && (this.random.nextInt(3) == 0 || this.random.nextInt(1 + i) > 0)) {
/*  99 */       a(Items.SPIDER_EYE, 1);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean h_() {
/* 104 */     return bZ();
/*     */   }
/*     */   
/*     */   public void as() {}
/*     */   
/*     */   public EnumMonsterType getMonsterType() {
/* 110 */     return EnumMonsterType.ARTHROPOD;
/*     */   }
/*     */   
/*     */   public boolean d(MobEffect mobeffect) {
/* 114 */     return (mobeffect.getEffectId() == MobEffectList.POISON.id) ? false : super.d(mobeffect);
/*     */   }
/*     */   
/*     */   public boolean bZ() {
/* 118 */     return ((this.datawatcher.getByte(16) & 0x1) != 0);
/*     */   }
/*     */   
/*     */   public void a(boolean flag) {
/* 122 */     byte b0 = this.datawatcher.getByte(16);
/*     */     
/* 124 */     if (flag) {
/* 125 */       b0 = (byte)(b0 | 0x1);
/*     */     } else {
/* 127 */       b0 = (byte)(b0 & 0xFFFFFFFE);
/*     */     } 
/*     */     
/* 130 */     this.datawatcher.watch(16, Byte.valueOf(b0));
/*     */   }
/*     */   
/*     */   public GroupDataEntity prepare(GroupDataEntity groupdataentity) {
/* 134 */     Object object = super.prepare(groupdataentity);
/*     */     
/* 136 */     if (this.world.random.nextInt(100) == 0) {
/* 137 */       EntitySkeleton entityskeleton = new EntitySkeleton(this.world);
/*     */       
/* 139 */       entityskeleton.setPositionRotation(this.locX, this.locY, this.locZ, this.yaw, 0.0F);
/* 140 */       entityskeleton.prepare((GroupDataEntity)null);
/* 141 */       this.world.addEntity(entityskeleton, CreatureSpawnEvent.SpawnReason.JOCKEY);
/* 142 */       entityskeleton.mount(this);
/*     */     } 
/*     */     
/* 145 */     if (object == null) {
/* 146 */       object = new GroupDataSpider();
/* 147 */       if (this.world.difficulty == EnumDifficulty.HARD && this.world.random.nextFloat() < 0.1F * this.world.b(this.locX, this.locY, this.locZ)) {
/* 148 */         ((GroupDataSpider)object).a(this.world.random);
/*     */       }
/*     */     } 
/*     */     
/* 152 */     if (object instanceof GroupDataSpider) {
/* 153 */       int i = ((GroupDataSpider)object).a;
/*     */       
/* 155 */       if (i > 0 && MobEffectList.byId[i] != null) {
/* 156 */         addEffect(new MobEffect(i, 2147483647));
/*     */       }
/*     */     } 
/*     */     
/* 160 */     return (GroupDataEntity)object;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntitySpider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */