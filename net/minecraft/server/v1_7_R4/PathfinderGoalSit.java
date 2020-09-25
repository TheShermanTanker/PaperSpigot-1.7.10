/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class PathfinderGoalSit
/*    */   extends PathfinderGoal {
/*    */   private EntityTameableAnimal entity;
/*    */   private boolean willSit;
/*    */   
/*    */   public PathfinderGoalSit(EntityTameableAnimal entitytameableanimal) {
/*  9 */     this.entity = entitytameableanimal;
/* 10 */     a(5);
/*    */   }
/*    */   
/*    */   public boolean a() {
/* 14 */     if (!this.entity.isTamed())
/* 15 */       return (this.willSit && this.entity.getGoalTarget() == null); 
/* 16 */     if (this.entity.M())
/* 17 */       return false; 
/* 18 */     if (!this.entity.onGround) {
/* 19 */       return false;
/*    */     }
/* 21 */     EntityLiving entityliving = this.entity.getOwner();
/*    */     
/* 23 */     return (entityliving == null) ? true : ((this.entity.f(entityliving) < 144.0D && entityliving.getLastDamager() != null) ? false : this.willSit);
/*    */   }
/*    */ 
/*    */   
/*    */   public void c() {
/* 28 */     this.entity.getNavigation().h();
/* 29 */     this.entity.setSitting(true);
/*    */   }
/*    */   
/*    */   public void d() {
/* 33 */     this.entity.setSitting(false);
/*    */   }
/*    */   
/*    */   public void setSitting(boolean flag) {
/* 37 */     this.willSit = flag;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PathfinderGoalSit.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */