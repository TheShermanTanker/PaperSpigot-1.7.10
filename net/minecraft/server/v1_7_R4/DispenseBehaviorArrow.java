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
/*    */ final class DispenseBehaviorArrow
/*    */   extends DispenseBehaviorProjectile
/*    */ {
/*    */   protected IProjectile a(World paramWorld, IPosition paramIPosition) {
/* 23 */     EntityArrow entityArrow = new EntityArrow(paramWorld, paramIPosition.getX(), paramIPosition.getY(), paramIPosition.getZ());
/* 24 */     entityArrow.fromPlayer = 1;
/*    */     
/* 26 */     return entityArrow;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\DispenseBehaviorArrow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */