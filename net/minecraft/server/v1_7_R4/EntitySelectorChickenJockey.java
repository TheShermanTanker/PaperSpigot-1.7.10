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
/*    */ final class EntitySelectorChickenJockey
/*    */   implements IEntitySelector
/*    */ {
/*    */   public boolean a(Entity paramEntity) {
/* 17 */     return (paramEntity.isAlive() && paramEntity.passenger == null && paramEntity.vehicle == null);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntitySelectorChickenJockey.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */