/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemSnowball
/*    */   extends Item
/*    */ {
/*    */   public ItemSnowball() {
/*  9 */     this.maxStackSize = 16;
/* 10 */     a(CreativeModeTab.f);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman) {
/* 15 */     if (!paramEntityHuman.abilities.canInstantlyBuild) {
/* 16 */       paramItemStack.count--;
/*    */     }
/* 18 */     paramWorld.makeSound(paramEntityHuman, "random.bow", 0.5F, 0.4F / (g.nextFloat() * 0.4F + 0.8F));
/* 19 */     if (!paramWorld.isStatic) paramWorld.addEntity(new EntitySnowball(paramWorld, paramEntityHuman)); 
/* 20 */     return paramItemStack;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemSnowball.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */