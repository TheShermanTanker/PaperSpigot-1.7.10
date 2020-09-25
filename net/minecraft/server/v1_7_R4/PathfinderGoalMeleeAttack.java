/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*     */ import org.bukkit.event.entity.EntityTargetEvent;
/*     */ 
/*     */ public class PathfinderGoalMeleeAttack extends PathfinderGoal {
/*     */   World a;
/*     */   EntityCreature b;
/*     */   int c;
/*     */   double d;
/*     */   boolean e;
/*     */   PathEntity f;
/*     */   Class g;
/*     */   private int h;
/*     */   private double i;
/*     */   private double j;
/*     */   private double k;
/*     */   
/*     */   public PathfinderGoalMeleeAttack(EntityCreature entitycreature, Class oclass, double d0, boolean flag) {
/*  20 */     this(entitycreature, d0, flag);
/*  21 */     this.g = oclass;
/*     */   }
/*     */   
/*     */   public PathfinderGoalMeleeAttack(EntityCreature entitycreature, double d0, boolean flag) {
/*  25 */     this.b = entitycreature;
/*  26 */     this.a = entitycreature.world;
/*  27 */     this.d = d0;
/*  28 */     this.e = flag;
/*  29 */     a(3);
/*     */   }
/*     */   
/*     */   public boolean a() {
/*  33 */     EntityLiving entityliving = this.b.getGoalTarget();
/*     */     
/*  35 */     if (entityliving == null)
/*  36 */       return false; 
/*  37 */     if (!entityliving.isAlive())
/*  38 */       return false; 
/*  39 */     if (this.g != null && !this.g.isAssignableFrom(entityliving.getClass())) {
/*  40 */       return false;
/*     */     }
/*  42 */     this.f = this.b.getNavigation().a(entityliving);
/*  43 */     return (this.f != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean b() {
/*  48 */     EntityLiving entityliving = this.b.getGoalTarget();
/*     */ 
/*     */     
/*  51 */     EntityTargetEvent.TargetReason reason = (this.b.getGoalTarget() == null) ? EntityTargetEvent.TargetReason.FORGOT_TARGET : EntityTargetEvent.TargetReason.TARGET_DIED;
/*  52 */     if (this.b.getGoalTarget() == null || (this.b.getGoalTarget() != null && !this.b.getGoalTarget().isAlive())) {
/*  53 */       CraftEventFactory.callEntityTargetEvent(this.b, null, reason);
/*     */     }
/*     */ 
/*     */     
/*  57 */     return (entityliving == null) ? false : (!entityliving.isAlive() ? false : (!this.e ? (!this.b.getNavigation().g()) : this.b.b(MathHelper.floor(entityliving.locX), MathHelper.floor(entityliving.locY), MathHelper.floor(entityliving.locZ))));
/*     */   }
/*     */   
/*     */   public void c() {
/*  61 */     this.b.getNavigation().a(this.f, this.d);
/*  62 */     this.h = 0;
/*     */   }
/*     */   
/*     */   public void d() {
/*  66 */     this.b.getNavigation().h();
/*     */   }
/*     */   
/*     */   public void e() {
/*  70 */     EntityLiving entityliving = this.b.getGoalTarget();
/*     */     
/*  72 */     this.b.getControllerLook().a(entityliving, 30.0F, 30.0F);
/*  73 */     double d0 = this.b.e(entityliving.locX, entityliving.boundingBox.b, entityliving.locZ);
/*  74 */     double d1 = (this.b.width * 2.0F * this.b.width * 2.0F + entityliving.width);
/*     */     
/*  76 */     this.h--;
/*  77 */     if ((this.e || this.b.getEntitySenses().canSee(entityliving)) && this.h <= 0 && ((this.i == 0.0D && this.j == 0.0D && this.k == 0.0D) || entityliving.e(this.i, this.j, this.k) >= 1.0D || this.b.aI().nextFloat() < 0.05F)) {
/*  78 */       this.i = entityliving.locX;
/*  79 */       this.j = entityliving.boundingBox.b;
/*  80 */       this.k = entityliving.locZ;
/*  81 */       this.h = 4 + this.b.aI().nextInt(7);
/*  82 */       if (d0 > 1024.0D) {
/*  83 */         this.h += 10;
/*  84 */       } else if (d0 > 256.0D) {
/*  85 */         this.h += 5;
/*     */       } 
/*     */       
/*  88 */       if (!this.b.getNavigation().a(entityliving, this.d)) {
/*  89 */         this.h += 15;
/*     */       }
/*     */     } 
/*     */     
/*  93 */     this.c = Math.max(this.c - 1, 0);
/*  94 */     if (d0 <= d1 && this.c <= 20) {
/*  95 */       this.c = 20;
/*  96 */       if (this.b.be() != null) {
/*  97 */         this.b.ba();
/*     */       }
/*     */       
/* 100 */       this.b.n(entityliving);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PathfinderGoalMeleeAttack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */