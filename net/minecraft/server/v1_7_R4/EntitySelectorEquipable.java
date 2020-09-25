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
/*    */ public class EntitySelectorEquipable
/*    */   implements IEntitySelector
/*    */ {
/*    */   private final ItemStack d;
/*    */   
/*    */   public EntitySelectorEquipable(ItemStack paramItemStack) {
/* 31 */     this.d = paramItemStack;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean a(Entity paramEntity) {
/* 36 */     if (!paramEntity.isAlive()) return false; 
/* 37 */     if (!(paramEntity instanceof EntityLiving)) return false; 
/* 38 */     EntityLiving entityLiving = (EntityLiving)paramEntity;
/* 39 */     if (entityLiving.getEquipment(EntityInsentient.b(this.d)) != null) return false;
/*    */     
/* 41 */     if (entityLiving instanceof EntityInsentient)
/* 42 */       return ((EntityInsentient)entityLiving).bJ(); 
/* 43 */     if (entityLiving instanceof EntityHuman) {
/* 44 */       return true;
/*    */     }
/*    */     
/* 47 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\EntitySelectorEquipable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */