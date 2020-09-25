/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class PathfinderGoalRestrictSun
/*    */   extends PathfinderGoal
/*    */ {
/*    */   private EntityCreature a;
/*    */   
/*    */   public PathfinderGoalRestrictSun(EntityCreature paramEntityCreature) {
/*  9 */     this.a = paramEntityCreature;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean a() {
/* 14 */     return this.a.world.w();
/*    */   }
/*    */ 
/*    */   
/*    */   public void c() {
/* 19 */     this.a.getNavigation().d(true);
/*    */   }
/*    */ 
/*    */   
/*    */   public void d() {
/* 24 */     this.a.getNavigation().d(false);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PathfinderGoalRestrictSun.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */