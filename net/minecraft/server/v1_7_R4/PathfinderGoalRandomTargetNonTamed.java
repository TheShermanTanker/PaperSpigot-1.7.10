/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class PathfinderGoalRandomTargetNonTamed
/*    */   extends PathfinderGoalNearestAttackableTarget
/*    */ {
/*    */   private EntityTameableAnimal a;
/*    */   
/*    */   public PathfinderGoalRandomTargetNonTamed(EntityTameableAnimal paramEntityTameableAnimal, Class paramClass, int paramInt, boolean paramBoolean) {
/*  9 */     super(paramEntityTameableAnimal, paramClass, paramInt, paramBoolean);
/* 10 */     this.a = paramEntityTameableAnimal;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean a() {
/* 15 */     return (!this.a.isTamed() && super.a());
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PathfinderGoalRandomTargetNonTamed.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */