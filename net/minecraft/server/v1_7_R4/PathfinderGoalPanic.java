/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class PathfinderGoalPanic
/*    */   extends PathfinderGoal {
/*    */   private EntityCreature a;
/*    */   private double b;
/*    */   private double c;
/*    */   private double d;
/*    */   private double e;
/*    */   
/*    */   public PathfinderGoalPanic(EntityCreature entitycreature, double d0) {
/* 12 */     this.a = entitycreature;
/* 13 */     this.b = d0;
/* 14 */     a(1);
/*    */   }
/*    */   
/*    */   public boolean a() {
/* 18 */     if (this.a.getLastDamager() == null && !this.a.isBurning()) {
/* 19 */       return false;
/*    */     }
/* 21 */     Vec3D vec3d = RandomPositionGenerator.a(this.a, 5, 4);
/*    */     
/* 23 */     if (vec3d == null) {
/* 24 */       return false;
/*    */     }
/* 26 */     this.c = vec3d.a;
/* 27 */     this.d = vec3d.b;
/* 28 */     this.e = vec3d.c;
/* 29 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void c() {
/* 35 */     this.a.getNavigation().a(this.c, this.d, this.e, this.b);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean b() {
/* 40 */     if (this.a.ticksLived - this.a.aK() > 100) {
/* 41 */       this.a.b((EntityLiving)null);
/* 42 */       return false;
/*    */     } 
/*    */     
/* 45 */     return !this.a.getNavigation().g();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PathfinderGoalPanic.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */