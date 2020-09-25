/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class PathfinderGoalOwnerHurtByTarget
/*    */   extends PathfinderGoalTarget
/*    */ {
/*    */   EntityTameableAnimal a;
/*    */   EntityLiving b;
/*    */   private int e;
/*    */   
/*    */   public PathfinderGoalOwnerHurtByTarget(EntityTameableAnimal paramEntityTameableAnimal) {
/* 11 */     super(paramEntityTameableAnimal, false);
/* 12 */     this.a = paramEntityTameableAnimal;
/* 13 */     a(1);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean a() {
/* 18 */     if (!this.a.isTamed()) return false; 
/* 19 */     EntityLiving entityLiving = this.a.getOwner();
/* 20 */     if (entityLiving == null) return false; 
/* 21 */     this.b = entityLiving.getLastDamager();
/* 22 */     int i = entityLiving.aK();
/* 23 */     return (i != this.e && a(this.b, false) && this.a.a(this.b, entityLiving));
/*    */   }
/*    */ 
/*    */   
/*    */   public void c() {
/* 28 */     this.c.setGoalTarget(this.b);
/*    */     
/* 30 */     EntityLiving entityLiving = this.a.getOwner();
/* 31 */     if (entityLiving != null) {
/* 32 */       this.e = entityLiving.aK();
/*    */     }
/*    */     
/* 35 */     super.c();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PathfinderGoalOwnerHurtByTarget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */