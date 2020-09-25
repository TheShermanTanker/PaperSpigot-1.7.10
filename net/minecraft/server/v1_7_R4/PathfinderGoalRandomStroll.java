/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PathfinderGoalRandomStroll
/*    */   extends PathfinderGoal
/*    */ {
/*    */   private EntityCreature a;
/*    */   private double b;
/*    */   private double c;
/*    */   private double d;
/*    */   private double e;
/*    */   
/*    */   public PathfinderGoalRandomStroll(EntityCreature paramEntityCreature, double paramDouble) {
/* 16 */     this.a = paramEntityCreature;
/* 17 */     this.e = paramDouble;
/* 18 */     a(1);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean a() {
/* 23 */     if (this.a.aN() >= 100) return false; 
/* 24 */     if (this.a.aI().nextInt(120) != 0) return false;
/*    */     
/* 26 */     Vec3D vec3D = RandomPositionGenerator.a(this.a, 10, 7);
/* 27 */     if (vec3D == null) return false; 
/* 28 */     this.b = vec3D.a;
/* 29 */     this.c = vec3D.b;
/* 30 */     this.d = vec3D.c;
/* 31 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean b() {
/* 36 */     return !this.a.getNavigation().g();
/*    */   }
/*    */ 
/*    */   
/*    */   public void c() {
/* 41 */     this.a.getNavigation().a(this.b, this.c, this.d, this.e);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PathfinderGoalRandomStroll.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */