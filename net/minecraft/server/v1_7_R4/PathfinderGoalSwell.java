/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class PathfinderGoalSwell
/*    */   extends PathfinderGoal
/*    */ {
/*    */   EntityCreeper a;
/*    */   
/*    */   public PathfinderGoalSwell(EntityCreeper entitycreeper) {
/*  9 */     this.a = entitycreeper;
/* 10 */     a(1);
/*    */   }
/*    */   
/*    */   public boolean a() {
/* 14 */     EntityLiving entityliving = this.a.getGoalTarget();
/*    */     
/* 16 */     return (this.a.cb() > 0 || (entityliving != null && this.a.f(entityliving) < 9.0D));
/*    */   }
/*    */   
/*    */   public void c() {
/* 20 */     this.a.getNavigation().h();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void d() {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void e() {
/* 30 */     EntityLiving b = this.a.getGoalTarget();
/* 31 */     if (b == null) {
/* 32 */       this.a.a(-1);
/* 33 */     } else if (this.a.f(b) > 49.0D) {
/* 34 */       this.a.a(-1);
/* 35 */     } else if (!this.a.getEntitySenses().canSee(b)) {
/* 36 */       this.a.a(-1);
/*    */     } else {
/* 38 */       this.a.a(1);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PathfinderGoalSwell.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */