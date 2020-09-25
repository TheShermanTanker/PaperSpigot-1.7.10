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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ final class DispenseBehaviorExpBottle
/*    */   extends DispenseBehaviorProjectile
/*    */ {
/*    */   protected IProjectile a(World paramWorld, IPosition paramIPosition) {
/* 44 */     return new EntityThrownExpBottle(paramWorld, paramIPosition.getX(), paramIPosition.getY(), paramIPosition.getZ());
/*    */   }
/*    */ 
/*    */   
/*    */   protected float a() {
/* 49 */     return super.a() * 0.5F;
/*    */   }
/*    */ 
/*    */   
/*    */   protected float b() {
/* 54 */     return super.b() * 1.25F;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\DispenseBehaviorExpBottle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */