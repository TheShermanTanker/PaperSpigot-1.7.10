/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import net.minecraft.util.org.apache.commons.lang3.StringUtils;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*     */ import org.bukkit.event.entity.EntityTargetEvent;
/*     */ import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
/*     */ 
/*     */ public abstract class PathfinderGoalTarget
/*     */   extends PathfinderGoal
/*     */ {
/*     */   protected EntityCreature c;
/*     */   protected boolean d;
/*     */   private boolean a;
/*     */   private int b;
/*     */   private int e;
/*     */   private int f;
/*     */   
/*     */   public PathfinderGoalTarget(EntityCreature entitycreature, boolean flag) {
/*  20 */     this(entitycreature, flag, false);
/*     */   }
/*     */   
/*     */   public PathfinderGoalTarget(EntityCreature entitycreature, boolean flag, boolean flag1) {
/*  24 */     this.c = entitycreature;
/*  25 */     this.d = flag;
/*  26 */     this.a = flag1;
/*     */   }
/*     */   
/*     */   public boolean b() {
/*  30 */     EntityLiving entityliving = this.c.getGoalTarget();
/*     */     
/*  32 */     if (entityliving == null)
/*  33 */       return false; 
/*  34 */     if (!entityliving.isAlive()) {
/*  35 */       return false;
/*     */     }
/*  37 */     double d0 = f();
/*     */     
/*  39 */     if (this.c.f(entityliving) > d0 * d0) {
/*  40 */       return false;
/*     */     }
/*  42 */     if (this.d) {
/*  43 */       if (this.c.getEntitySenses().canSee(entityliving)) {
/*  44 */         this.f = 0;
/*  45 */       } else if (++this.f > 60) {
/*  46 */         return false;
/*     */       } 
/*     */     }
/*     */     
/*  50 */     return (!(entityliving instanceof EntityPlayer) || !((EntityPlayer)entityliving).playerInteractManager.isCreative());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected double f() {
/*  56 */     AttributeInstance attributeinstance = this.c.getAttributeInstance(GenericAttributes.b);
/*     */     
/*  58 */     return (attributeinstance == null) ? 16.0D : attributeinstance.getValue();
/*     */   }
/*     */   
/*     */   public void c() {
/*  62 */     this.b = 0;
/*  63 */     this.e = 0;
/*  64 */     this.f = 0;
/*     */   }
/*     */   
/*     */   public void d() {
/*  68 */     this.c.setGoalTarget((EntityLiving)null);
/*     */   }
/*     */   
/*     */   protected boolean a(EntityLiving entityliving, boolean flag) {
/*  72 */     if (entityliving == null)
/*  73 */       return false; 
/*  74 */     if (entityliving == this.c)
/*  75 */       return false; 
/*  76 */     if (!entityliving.isAlive())
/*  77 */       return false; 
/*  78 */     if (!this.c.a(entityliving.getClass())) {
/*  79 */       return false;
/*     */     }
/*  81 */     if (this.c instanceof EntityOwnable && StringUtils.isNotEmpty(((EntityOwnable)this.c).getOwnerUUID())) {
/*  82 */       if (entityliving instanceof EntityOwnable && ((EntityOwnable)this.c).getOwnerUUID().equals(((EntityOwnable)entityliving).getOwnerUUID())) {
/*  83 */         return false;
/*     */       }
/*     */       
/*  86 */       if (entityliving == ((EntityOwnable)this.c).getOwner()) {
/*  87 */         return false;
/*     */       }
/*  89 */     } else if (entityliving instanceof EntityHuman && !flag && ((EntityHuman)entityliving).abilities.isInvulnerable) {
/*  90 */       return false;
/*     */     } 
/*     */     
/*  93 */     if (!this.c.b(MathHelper.floor(entityliving.locX), MathHelper.floor(entityliving.locY), MathHelper.floor(entityliving.locZ)))
/*  94 */       return false; 
/*  95 */     if (this.d && !this.c.getEntitySenses().canSee(entityliving)) {
/*  96 */       return false;
/*     */     }
/*  98 */     if (this.a) {
/*  99 */       if (--this.e <= 0) {
/* 100 */         this.b = 0;
/*     */       }
/*     */       
/* 103 */       if (this.b == 0) {
/* 104 */         this.b = a(entityliving) ? 1 : 2;
/*     */       }
/*     */       
/* 107 */       if (this.b == 2) {
/* 108 */         return false;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 113 */     EntityTargetEvent.TargetReason reason = EntityTargetEvent.TargetReason.RANDOM_TARGET;
/*     */     
/* 115 */     if (this instanceof PathfinderGoalDefendVillage) {
/* 116 */       reason = EntityTargetEvent.TargetReason.DEFEND_VILLAGE;
/* 117 */     } else if (this instanceof PathfinderGoalHurtByTarget) {
/* 118 */       reason = EntityTargetEvent.TargetReason.TARGET_ATTACKED_ENTITY;
/* 119 */     } else if (this instanceof PathfinderGoalNearestAttackableTarget) {
/* 120 */       if (entityliving instanceof EntityHuman) {
/* 121 */         reason = EntityTargetEvent.TargetReason.CLOSEST_PLAYER;
/*     */       }
/* 123 */     } else if (this instanceof PathfinderGoalOwnerHurtByTarget) {
/* 124 */       reason = EntityTargetEvent.TargetReason.TARGET_ATTACKED_OWNER;
/* 125 */     } else if (this instanceof PathfinderGoalOwnerHurtTarget) {
/* 126 */       reason = EntityTargetEvent.TargetReason.OWNER_ATTACKED_TARGET;
/*     */     } 
/*     */     
/* 129 */     EntityTargetLivingEntityEvent event = CraftEventFactory.callEntityTargetLivingEvent(this.c, entityliving, reason);
/* 130 */     if (event.isCancelled() || event.getTarget() == null) {
/* 131 */       this.c.setGoalTarget((EntityLiving)null);
/* 132 */       return false;
/* 133 */     }  if (entityliving.getBukkitEntity() != event.getTarget()) {
/* 134 */       this.c.setGoalTarget((EntityLiving)((CraftEntity)event.getTarget()).getHandle());
/*     */     }
/* 136 */     if (this.c instanceof EntityCreature) {
/* 137 */       this.c.target = ((CraftEntity)event.getTarget()).getHandle();
/*     */     }
/*     */ 
/*     */     
/* 141 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean a(EntityLiving entityliving) {
/* 147 */     this.e = 10 + this.c.aI().nextInt(5);
/* 148 */     PathEntity pathentity = this.c.getNavigation().a(entityliving);
/*     */     
/* 150 */     if (pathentity == null) {
/* 151 */       return false;
/*     */     }
/* 153 */     PathPoint pathpoint = pathentity.c();
/*     */     
/* 155 */     if (pathpoint == null) {
/* 156 */       return false;
/*     */     }
/* 158 */     int i = pathpoint.a - MathHelper.floor(entityliving.locX);
/* 159 */     int j = pathpoint.c - MathHelper.floor(entityliving.locZ);
/*     */     
/* 161 */     return ((i * i + j * j) <= 2.25D);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PathfinderGoalTarget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */