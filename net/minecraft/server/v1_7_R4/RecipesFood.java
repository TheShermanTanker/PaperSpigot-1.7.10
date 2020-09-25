/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RecipesFood
/*    */ {
/*    */   public void a(CraftingManager paramCraftingManager) {
/*  9 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.MUSHROOM_SOUP), new Object[] { Blocks.BROWN_MUSHROOM, Blocks.RED_MUSHROOM, Items.BOWL });
/*    */ 
/*    */     
/* 12 */     paramCraftingManager.registerShapedRecipe(new ItemStack(Items.COOKIE, 8), new Object[] { "#X#", Character.valueOf('X'), new ItemStack(Items.INK_SACK, 1, 3), Character.valueOf('#'), Items.WHEAT });
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 17 */     paramCraftingManager.registerShapedRecipe(new ItemStack(Blocks.MELON), new Object[] { "MMM", "MMM", "MMM", Character.valueOf('M'), Items.MELON });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 24 */     paramCraftingManager.registerShapedRecipe(new ItemStack(Items.MELON_SEEDS), new Object[] { "M", Character.valueOf('M'), Items.MELON });
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 29 */     paramCraftingManager.registerShapedRecipe(new ItemStack(Items.PUMPKIN_SEEDS, 4), new Object[] { "M", Character.valueOf('M'), Blocks.PUMPKIN });
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 34 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.PUMPKIN_PIE), new Object[] { Blocks.PUMPKIN, Items.SUGAR, Items.EGG });
/*    */ 
/*    */ 
/*    */     
/* 38 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.FERMENTED_SPIDER_EYE), new Object[] { Items.SPIDER_EYE, Blocks.BROWN_MUSHROOM, Items.SUGAR });
/*    */ 
/*    */     
/* 41 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.BLAZE_POWDER, 2), new Object[] { Items.BLAZE_ROD });
/*    */ 
/*    */     
/* 44 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.MAGMA_CREAM), new Object[] { Items.BLAZE_POWDER, Items.SLIME_BALL });
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\RecipesFood.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */