/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PathfinderGoalMoveTowardsRestriction
/*    */   extends PathfinderGoal
/*    */ {
/*    */   private EntityCreature a;
/*    */   private double b;
/*    */   private double c;
/*    */   private double d;
/*    */   private double e;
/*    */   
/*    */   public PathfinderGoalMoveTowardsRestriction(EntityCreature paramEntityCreature, double paramDouble) {
/* 16 */     this.a = paramEntityCreature;
/* 17 */     this.e = paramDouble;
/* 18 */     a(1);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean a() {
/* 23 */     if (this.a.bU()) return false; 
/* 24 */     ChunkCoordinates chunkCoordinates = this.a.bV();
/* 25 */     Vec3D vec3D = RandomPositionGenerator.a(this.a, 16, 7, Vec3D.a(chunkCoordinates.x, chunkCoordinates.y, chunkCoordinates.z));
/* 26 */     if (vec3D == null) return false; 
/* 27 */     this.b = vec3D.a;
/* 28 */     this.c = vec3D.b;
/* 29 */     this.d = vec3D.c;
/* 30 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean b() {
/* 35 */     return !this.a.getNavigation().g();
/*    */   }
/*    */ 
/*    */   
/*    */   public void c() {
/* 40 */     this.a.getNavigation().a(this.b, this.c, this.d, this.e);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PathfinderGoalMoveTowardsRestriction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */