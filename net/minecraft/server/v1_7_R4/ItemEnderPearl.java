/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemEnderPearl
/*    */   extends Item
/*    */ {
/*    */   public ItemEnderPearl() {
/*  9 */     this.maxStackSize = 16;
/* 10 */     a(CreativeModeTab.f);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman) {
/* 15 */     if (paramEntityHuman.abilities.canInstantlyBuild) return paramItemStack;
/*    */     
/* 17 */     paramItemStack.count--;
/* 18 */     paramWorld.makeSound(paramEntityHuman, "random.bow", 0.5F, 0.4F / (g.nextFloat() * 0.4F + 0.8F));
/* 19 */     if (!paramWorld.isStatic) paramWorld.addEntity(new EntityEnderPearl(paramWorld, paramEntityHuman)); 
/* 20 */     return paramItemStack;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemEnderPearl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */