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
/*    */ class DispenseBehaviorThrownPotion
/*    */   extends DispenseBehaviorProjectile
/*    */ {
/*    */   DispenseBehaviorThrownPotion(DispenseBehaviorPotion paramDispenseBehaviorPotion, ItemStack paramItemStack) {}
/*    */   
/*    */   protected IProjectile a(World paramWorld, IPosition paramIPosition) {
/* 67 */     return new EntityPotion(paramWorld, paramIPosition.getX(), paramIPosition.getY(), paramIPosition.getZ(), this.b.cloneItemStack());
/*    */   }
/*    */ 
/*    */   
/*    */   protected float a() {
/* 72 */     return super.a() * 0.5F;
/*    */   }
/*    */ 
/*    */   
/*    */   protected float b() {
/* 77 */     return super.b() * 1.25F;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\DispenseBehaviorThrownPotion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */