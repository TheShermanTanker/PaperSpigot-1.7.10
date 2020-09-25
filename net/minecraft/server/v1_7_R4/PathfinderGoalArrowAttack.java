/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import org.bukkit.craftbukkit.v1_7_R4.event.CraftEventFactory;
/*     */ import org.bukkit.event.entity.EntityTargetEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PathfinderGoalArrowAttack
/*     */   extends PathfinderGoal
/*     */ {
/*     */   private final EntityInsentient a;
/*     */   private final IRangedEntity b;
/*     */   private EntityLiving c;
/*     */   
/*     */   public PathfinderGoalArrowAttack(IRangedEntity irangedentity, double d0, int i, float f) {
/*  19 */     this(irangedentity, d0, i, i, f);
/*     */   }
/*     */ 
/*     */   
/*  23 */   private int d = -1; private double e; private int f; private int g; public PathfinderGoalArrowAttack(IRangedEntity irangedentity, double d0, int i, int j, float f) {
/*  24 */     if (!(irangedentity instanceof EntityLiving)) {
/*  25 */       throw new IllegalArgumentException("ArrowAttackGoal requires Mob implements RangedAttackMob");
/*     */     }
/*  27 */     this.b = irangedentity;
/*  28 */     this.a = (EntityInsentient)irangedentity;
/*  29 */     this.e = d0;
/*  30 */     this.g = i;
/*  31 */     this.h = j;
/*  32 */     this.i = f;
/*  33 */     this.j = f * f;
/*  34 */     a(3);
/*     */   }
/*     */   private int h; private float i; private float j;
/*     */   
/*     */   public boolean a() {
/*  39 */     EntityLiving entityliving = this.a.getGoalTarget();
/*     */     
/*  41 */     if (entityliving == null) {
/*  42 */       return false;
/*     */     }
/*  44 */     this.c = entityliving;
/*  45 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean b() {
/*  50 */     return (a() || !this.a.getNavigation().g());
/*     */   }
/*     */ 
/*     */   
/*     */   public void d() {
/*  55 */     EntityTargetEvent.TargetReason reason = this.c.isAlive() ? EntityTargetEvent.TargetReason.FORGOT_TARGET : EntityTargetEvent.TargetReason.TARGET_DIED;
/*  56 */     CraftEventFactory.callEntityTargetEvent((Entity)this.b, null, reason);
/*     */     
/*  58 */     this.c = null;
/*  59 */     this.f = 0;
/*  60 */     this.d = -1;
/*     */   }
/*     */   
/*     */   public void e() {
/*  64 */     double d0 = this.a.e(this.c.locX, this.c.boundingBox.b, this.c.locZ);
/*  65 */     boolean flag = this.a.getEntitySenses().canSee(this.c);
/*     */     
/*  67 */     if (flag) {
/*  68 */       this.f++;
/*     */     } else {
/*  70 */       this.f = 0;
/*     */     } 
/*     */     
/*  73 */     if (d0 <= this.j && this.f >= 20) {
/*  74 */       this.a.getNavigation().h();
/*     */     } else {
/*  76 */       this.a.getNavigation().a(this.c, this.e);
/*     */     } 
/*     */     
/*  79 */     this.a.getControllerLook().a(this.c, 30.0F, 30.0F);
/*     */ 
/*     */     
/*  82 */     if (--this.d == 0) {
/*  83 */       if (d0 > this.j || !flag) {
/*     */         return;
/*     */       }
/*     */       
/*  87 */       float f = MathHelper.sqrt(d0) / this.i;
/*  88 */       float f1 = f;
/*     */       
/*  90 */       if (f < 0.1F) {
/*  91 */         f1 = 0.1F;
/*     */       }
/*     */       
/*  94 */       if (f1 > 1.0F) {
/*  95 */         f1 = 1.0F;
/*     */       }
/*     */       
/*  98 */       this.b.a(this.c, f1);
/*  99 */       this.d = MathHelper.d(f * (this.h - this.g) + this.g);
/* 100 */     } else if (this.d < 0) {
/* 101 */       float f = MathHelper.sqrt(d0) / this.i;
/* 102 */       this.d = MathHelper.d(f * (this.h - this.g) + this.g);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PathfinderGoalArrowAttack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */