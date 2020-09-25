/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemGlassBottle
/*    */   extends Item
/*    */ {
/*    */   public ItemGlassBottle() {
/* 12 */     a(CreativeModeTab.k);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman) {
/* 22 */     MovingObjectPosition movingObjectPosition = a(paramWorld, paramEntityHuman, true);
/* 23 */     if (movingObjectPosition == null) return paramItemStack;
/*    */     
/* 25 */     if (movingObjectPosition.type == EnumMovingObjectType.BLOCK) {
/* 26 */       int i = movingObjectPosition.b;
/* 27 */       int j = movingObjectPosition.c;
/* 28 */       int k = movingObjectPosition.d;
/*    */       
/* 30 */       if (!paramWorld.a(paramEntityHuman, i, j, k)) {
/* 31 */         return paramItemStack;
/*    */       }
/* 33 */       if (!paramEntityHuman.a(i, j, k, movingObjectPosition.face, paramItemStack)) {
/* 34 */         return paramItemStack;
/*    */       }
/* 36 */       if (paramWorld.getType(i, j, k).getMaterial() == Material.WATER) {
/*    */         
/* 38 */         paramItemStack.count--;
/* 39 */         if (paramItemStack.count <= 0) {
/* 40 */           return new ItemStack(Items.POTION);
/*    */         }
/* 42 */         if (!paramEntityHuman.inventory.pickup(new ItemStack(Items.POTION))) {
/* 43 */           paramEntityHuman.drop(new ItemStack(Items.POTION, 1, 0), false);
/*    */         }
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 49 */     return paramItemStack;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemGlassBottle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */