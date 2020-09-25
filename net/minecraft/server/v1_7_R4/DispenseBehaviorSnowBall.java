/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class DispenseBehaviorSnowBall
/*    */   extends DispenseBehaviorProjectile
/*    */ {
/*    */   protected IProjectile a(World paramWorld, IPosition paramIPosition) {
/* 38 */     return new EntitySnowball(paramWorld, paramIPosition.getX(), paramIPosition.getY(), paramIPosition.getZ());
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\DispenseBehaviorSnowBall.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */