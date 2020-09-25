/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class PathfinderGoalFloat
/*    */   extends PathfinderGoal {
/*    */   private EntityInsentient a;
/*    */   
/*    */   public PathfinderGoalFloat(EntityInsentient entityinsentient) {
/*  8 */     this.a = entityinsentient;
/*  9 */     entityinsentient.goalFloat = this;
/* 10 */     a(4);
/* 11 */     entityinsentient.getNavigation().e(true);
/*    */   }
/*    */   
/*    */   public boolean a() {
/* 15 */     return (this.a.M() || this.a.P());
/*    */   }
/*    */   
/*    */   public void e() {
/* 19 */     if (this.a.aI().nextFloat() < 0.8F)
/* 20 */       this.a.getControllerJump().a(); 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PathfinderGoalFloat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */