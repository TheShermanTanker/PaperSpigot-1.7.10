/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemExpBottle
/*    */   extends Item
/*    */ {
/*    */   public ItemExpBottle() {
/* 10 */     a(CreativeModeTab.f);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman) {
/* 20 */     if (!paramEntityHuman.abilities.canInstantlyBuild) {
/* 21 */       paramItemStack.count--;
/*    */     }
/* 23 */     paramWorld.makeSound(paramEntityHuman, "random.bow", 0.5F, 0.4F / (g.nextFloat() * 0.4F + 0.8F));
/* 24 */     if (!paramWorld.isStatic) paramWorld.addEntity(new EntityThrownExpBottle(paramWorld, paramEntityHuman)); 
/* 25 */     return paramItemStack;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemExpBottle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */