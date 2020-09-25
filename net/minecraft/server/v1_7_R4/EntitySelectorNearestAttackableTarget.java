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
/*    */ class EntitySelectorNearestAttackableTarget
/*    */   implements IEntitySelector
/*    */ {
/*    */   EntitySelectorNearestAttackableTarget(PathfinderGoalNearestAttackableTarget paramPathfinderGoalNearestAttackableTarget, IEntitySelector paramIEntitySelector) {}
/*    */   
/*    */   public boolean a(Entity paramEntity) {
/* 32 */     if (!(paramEntity instanceof EntityLiving)) return false; 
/* 33 */     if (this.d != null && !this.d.a(paramEntity)) return false; 
/* 34 */     return this.e.a((EntityLiving)paramEntity, false);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntitySelectorNearestAttackableTarget.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */