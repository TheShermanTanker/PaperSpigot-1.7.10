/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import org.bukkit.event.entity.CreatureSpawnEvent;
/*     */ 
/*     */ public class PathfinderGoalBreed extends PathfinderGoal {
/*     */   private EntityAnimal d;
/*     */   World a;
/*     */   private EntityAnimal e;
/*     */   int b;
/*     */   double c;
/*     */   
/*     */   public PathfinderGoalBreed(EntityAnimal entityanimal, double d0) {
/*  16 */     this.d = entityanimal;
/*  17 */     this.a = entityanimal.world;
/*  18 */     this.c = d0;
/*  19 */     a(3);
/*     */   }
/*     */   
/*     */   public boolean a() {
/*  23 */     if (!this.d.ce()) {
/*  24 */       return false;
/*     */     }
/*  26 */     this.e = f();
/*  27 */     return (this.e != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean b() {
/*  32 */     return (this.e.isAlive() && this.e.ce() && this.b < 60);
/*     */   }
/*     */   
/*     */   public void d() {
/*  36 */     this.e = null;
/*  37 */     this.b = 0;
/*     */   }
/*     */   
/*     */   public void e() {
/*  41 */     this.d.getControllerLook().a(this.e, 10.0F, this.d.x());
/*  42 */     this.d.getNavigation().a(this.e, this.c);
/*  43 */     this.b++;
/*  44 */     if (this.b >= 60 && this.d.f(this.e) < 9.0D) {
/*  45 */       g();
/*     */     }
/*     */   }
/*     */   
/*     */   private EntityAnimal f() {
/*  50 */     float f = 8.0F;
/*  51 */     List list = this.a.a(this.d.getClass(), this.d.boundingBox.grow(f, f, f));
/*  52 */     double d0 = Double.MAX_VALUE;
/*  53 */     EntityAnimal entityanimal = null;
/*  54 */     Iterator<EntityAnimal> iterator = list.iterator();
/*     */     
/*  56 */     while (iterator.hasNext()) {
/*  57 */       EntityAnimal entityanimal1 = iterator.next();
/*     */       
/*  59 */       if (this.d.mate(entityanimal1) && this.d.f(entityanimal1) < d0) {
/*  60 */         entityanimal = entityanimal1;
/*  61 */         d0 = this.d.f(entityanimal1);
/*     */       } 
/*     */     } 
/*     */     
/*  65 */     return entityanimal;
/*     */   }
/*     */   
/*     */   private void g() {
/*  69 */     EntityAgeable entityageable = this.d.createChild(this.e);
/*     */     
/*  71 */     if (entityageable != null) {
/*     */       
/*  73 */       if (entityageable instanceof EntityTameableAnimal && ((EntityTameableAnimal)entityageable).isTamed()) {
/*  74 */         entityageable.persistent = true;
/*     */       }
/*     */       
/*  77 */       EntityHuman entityhuman = this.d.cd();
/*     */       
/*  79 */       if (entityhuman == null && this.e.cd() != null) {
/*  80 */         entityhuman = this.e.cd();
/*     */       }
/*     */       
/*  83 */       if (entityhuman != null) {
/*  84 */         entityhuman.a(StatisticList.x);
/*  85 */         if (this.d instanceof EntityCow) {
/*  86 */           entityhuman.a(AchievementList.H);
/*     */         }
/*     */       } 
/*     */       
/*  90 */       this.d.setAge(6000);
/*  91 */       this.e.setAge(6000);
/*  92 */       this.d.cf();
/*  93 */       this.e.cf();
/*  94 */       entityageable.setAge(-24000);
/*  95 */       entityageable.setPositionRotation(this.d.locX, this.d.locY, this.d.locZ, 0.0F, 0.0F);
/*  96 */       this.a.addEntity(entityageable, CreatureSpawnEvent.SpawnReason.BREEDING);
/*  97 */       Random random = this.d.aI();
/*     */       
/*  99 */       for (int i = 0; i < 7; i++) {
/* 100 */         double d0 = random.nextGaussian() * 0.02D;
/* 101 */         double d1 = random.nextGaussian() * 0.02D;
/* 102 */         double d2 = random.nextGaussian() * 0.02D;
/*     */         
/* 104 */         this.a.addParticle("heart", this.d.locX + (random.nextFloat() * this.d.width * 2.0F) - this.d.width, this.d.locY + 0.5D + (random.nextFloat() * this.d.length), this.d.locZ + (random.nextFloat() * this.d.width * 2.0F) - this.d.width, d0, d1, d2);
/*     */       } 
/*     */       
/* 107 */       if (this.a.getGameRules().getBoolean("doMobLoot"))
/* 108 */         this.a.addEntity(new EntityExperienceOrb(this.a, this.d.locX, this.d.locY, this.d.locZ, random.nextInt(7) + 1)); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PathfinderGoalBreed.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */