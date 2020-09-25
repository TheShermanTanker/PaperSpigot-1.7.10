/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PathfinderGoalFollowOwner
/*    */   extends PathfinderGoal
/*    */ {
/*    */   private EntityTameableAnimal d;
/*    */   private EntityLiving e;
/*    */   World a;
/*    */   private double f;
/*    */   private Navigation g;
/*    */   private int h;
/*    */   float b;
/*    */   float c;
/*    */   private boolean i;
/*    */   
/*    */   public PathfinderGoalFollowOwner(EntityTameableAnimal paramEntityTameableAnimal, double paramDouble, float paramFloat1, float paramFloat2) {
/* 23 */     this.d = paramEntityTameableAnimal;
/* 24 */     this.a = paramEntityTameableAnimal.world;
/* 25 */     this.f = paramDouble;
/* 26 */     this.g = paramEntityTameableAnimal.getNavigation();
/* 27 */     this.c = paramFloat1;
/* 28 */     this.b = paramFloat2;
/* 29 */     a(3);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean a() {
/* 34 */     EntityLiving entityLiving = this.d.getOwner();
/* 35 */     if (entityLiving == null) return false; 
/* 36 */     if (this.d.isSitting()) return false; 
/* 37 */     if (this.d.f(entityLiving) < (this.c * this.c)) return false; 
/* 38 */     this.e = entityLiving;
/* 39 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean b() {
/* 44 */     return (!this.g.g() && this.d.f(this.e) > (this.b * this.b) && !this.d.isSitting());
/*    */   }
/*    */ 
/*    */   
/*    */   public void c() {
/* 49 */     this.h = 0;
/* 50 */     this.i = this.d.getNavigation().a();
/* 51 */     this.d.getNavigation().a(false);
/*    */   }
/*    */ 
/*    */   
/*    */   public void d() {
/* 56 */     this.e = null;
/* 57 */     this.g.h();
/* 58 */     this.d.getNavigation().a(this.i);
/*    */   }
/*    */ 
/*    */   
/*    */   public void e() {
/* 63 */     this.d.getControllerLook().a(this.e, 10.0F, this.d.x());
/* 64 */     if (this.d.isSitting())
/*    */       return; 
/* 66 */     if (--this.h > 0)
/* 67 */       return;  this.h = 10;
/*    */     
/* 69 */     if (this.g.a(this.e, this.f))
/* 70 */       return;  if (this.d.bN())
/* 71 */       return;  if (this.d.f(this.e) < 144.0D) {
/*    */       return;
/*    */     }
/* 74 */     int i = MathHelper.floor(this.e.locX) - 2;
/* 75 */     int j = MathHelper.floor(this.e.locZ) - 2;
/* 76 */     int k = MathHelper.floor(this.e.boundingBox.b);
/* 77 */     for (byte b = 0; b <= 4; b++) {
/* 78 */       for (byte b1 = 0; b1 <= 4; b1++) {
/* 79 */         if (b < 1 || b1 < 1 || b > 3 || b1 > 3)
/*    */         {
/*    */           
/* 82 */           if (World.a(this.a, i + b, k - 1, j + b1) && !this.a.getType(i + b, k, j + b1).r() && !this.a.getType(i + b, k + 1, j + b1).r()) {
/* 83 */             this.d.setPositionRotation(((i + b) + 0.5F), k, ((j + b1) + 0.5F), this.d.yaw, this.d.pitch);
/* 84 */             this.g.h();
/*    */             return;
/*    */           } 
/*    */         }
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PathfinderGoalFollowOwner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */