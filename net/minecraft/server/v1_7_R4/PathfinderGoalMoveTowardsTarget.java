/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PathfinderGoalMoveTowardsTarget
/*    */   extends PathfinderGoal
/*    */ {
/*    */   private EntityCreature a;
/*    */   private EntityLiving b;
/*    */   private double c;
/*    */   private double d;
/*    */   private double e;
/*    */   private double f;
/*    */   private float g;
/*    */   
/*    */   public PathfinderGoalMoveTowardsTarget(EntityCreature paramEntityCreature, double paramDouble, float paramFloat) {
/* 17 */     this.a = paramEntityCreature;
/* 18 */     this.f = paramDouble;
/* 19 */     this.g = paramFloat;
/* 20 */     a(1);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean a() {
/* 25 */     this.b = this.a.getGoalTarget();
/* 26 */     if (this.b == null) return false; 
/* 27 */     if (this.b.f(this.a) > (this.g * this.g)) return false; 
/* 28 */     Vec3D vec3D = RandomPositionGenerator.a(this.a, 16, 7, Vec3D.a(this.b.locX, this.b.locY, this.b.locZ));
/* 29 */     if (vec3D == null) return false; 
/* 30 */     this.c = vec3D.a;
/* 31 */     this.d = vec3D.b;
/* 32 */     this.e = vec3D.c;
/* 33 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean b() {
/* 38 */     return (!this.a.getNavigation().g() && this.b.isAlive() && this.b.f(this.a) < (this.g * this.g));
/*    */   }
/*    */ 
/*    */   
/*    */   public void d() {
/* 43 */     this.b = null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void c() {
/* 48 */     this.a.getNavigation().a(this.c, this.d, this.e, this.f);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PathfinderGoalMoveTowardsTarget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */