/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RecipesDyes
/*    */ {
/*    */   public void a(CraftingManager paramCraftingManager) {
/*    */     byte b;
/* 11 */     for (b = 0; b < 16; b++) {
/* 12 */       paramCraftingManager.registerShapelessRecipe(new ItemStack(Blocks.WOOL, 1, BlockCloth.c(b)), new Object[] { new ItemStack(Items.INK_SACK, 1, b), new ItemStack(Item.getItemOf(Blocks.WOOL), 1, 0) });
/*    */       
/* 14 */       paramCraftingManager.registerShapedRecipe(new ItemStack(Blocks.STAINED_HARDENED_CLAY, 8, BlockCloth.c(b)), new Object[] { "###", "#X#", "###", Character.valueOf('#'), new ItemStack(Blocks.HARDENED_CLAY), Character.valueOf('X'), new ItemStack(Items.INK_SACK, 1, b) });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 20 */       paramCraftingManager.registerShapedRecipe(new ItemStack(Blocks.STAINED_GLASS, 8, BlockCloth.c(b)), new Object[] { "###", "#X#", "###", Character.valueOf('#'), new ItemStack(Blocks.GLASS), Character.valueOf('X'), new ItemStack(Items.INK_SACK, 1, b) });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 26 */       paramCraftingManager.registerShapedRecipe(new ItemStack(Blocks.STAINED_GLASS_PANE, 16, b), new Object[] { "###", "###", Character.valueOf('#'), new ItemStack(Blocks.STAINED_GLASS, 1, b) });
/*    */     } 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 33 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.INK_SACK, 1, 11), new Object[] { new ItemStack(Blocks.YELLOW_FLOWER, 1, 0) });
/* 34 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.INK_SACK, 1, 1), new Object[] { new ItemStack(Blocks.RED_ROSE, 1, 0) });
/* 35 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.INK_SACK, 3, 15), new Object[] { Items.BONE });
/* 36 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.INK_SACK, 2, 9), new Object[] { new ItemStack(Items.INK_SACK, 1, 1), new ItemStack(Items.INK_SACK, 1, 15) });
/*    */     
/* 38 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.INK_SACK, 2, 14), new Object[] { new ItemStack(Items.INK_SACK, 1, 1), new ItemStack(Items.INK_SACK, 1, 11) });
/*    */     
/* 40 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.INK_SACK, 2, 10), new Object[] { new ItemStack(Items.INK_SACK, 1, 2), new ItemStack(Items.INK_SACK, 1, 15) });
/*    */     
/* 42 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.INK_SACK, 2, 8), new Object[] { new ItemStack(Items.INK_SACK, 1, 0), new ItemStack(Items.INK_SACK, 1, 15) });
/*    */     
/* 44 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.INK_SACK, 2, 7), new Object[] { new ItemStack(Items.INK_SACK, 1, 8), new ItemStack(Items.INK_SACK, 1, 15) });
/*    */     
/* 46 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.INK_SACK, 3, 7), new Object[] { new ItemStack(Items.INK_SACK, 1, 0), new ItemStack(Items.INK_SACK, 1, 15), new ItemStack(Items.INK_SACK, 1, 15) });
/*    */     
/* 48 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.INK_SACK, 2, 12), new Object[] { new ItemStack(Items.INK_SACK, 1, 4), new ItemStack(Items.INK_SACK, 1, 15) });
/*    */     
/* 50 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.INK_SACK, 2, 6), new Object[] { new ItemStack(Items.INK_SACK, 1, 4), new ItemStack(Items.INK_SACK, 1, 2) });
/*    */     
/* 52 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.INK_SACK, 2, 5), new Object[] { new ItemStack(Items.INK_SACK, 1, 4), new ItemStack(Items.INK_SACK, 1, 1) });
/*    */     
/* 54 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.INK_SACK, 2, 13), new Object[] { new ItemStack(Items.INK_SACK, 1, 5), new ItemStack(Items.INK_SACK, 1, 9) });
/*    */     
/* 56 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.INK_SACK, 3, 13), new Object[] { new ItemStack(Items.INK_SACK, 1, 4), new ItemStack(Items.INK_SACK, 1, 1), new ItemStack(Items.INK_SACK, 1, 9) });
/*    */     
/* 58 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.INK_SACK, 4, 13), new Object[] { new ItemStack(Items.INK_SACK, 1, 4), new ItemStack(Items.INK_SACK, 1, 1), new ItemStack(Items.INK_SACK, 1, 1), new ItemStack(Items.INK_SACK, 1, 15) });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 64 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.INK_SACK, 1, 12), new Object[] { new ItemStack(Blocks.RED_ROSE, 1, 1) });
/* 65 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.INK_SACK, 1, 13), new Object[] { new ItemStack(Blocks.RED_ROSE, 1, 2) });
/* 66 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.INK_SACK, 1, 7), new Object[] { new ItemStack(Blocks.RED_ROSE, 1, 3) });
/* 67 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.INK_SACK, 1, 1), new Object[] { new ItemStack(Blocks.RED_ROSE, 1, 4) });
/* 68 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.INK_SACK, 1, 14), new Object[] { new ItemStack(Blocks.RED_ROSE, 1, 5) });
/* 69 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.INK_SACK, 1, 7), new Object[] { new ItemStack(Blocks.RED_ROSE, 1, 6) });
/* 70 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.INK_SACK, 1, 9), new Object[] { new ItemStack(Blocks.RED_ROSE, 1, 7) });
/* 71 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.INK_SACK, 1, 7), new Object[] { new ItemStack(Blocks.RED_ROSE, 1, 8) });
/*    */     
/* 73 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.INK_SACK, 2, 11), new Object[] { new ItemStack(Blocks.DOUBLE_PLANT, 1, 0) });
/* 74 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.INK_SACK, 2, 13), new Object[] { new ItemStack(Blocks.DOUBLE_PLANT, 1, 1) });
/* 75 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.INK_SACK, 2, 1), new Object[] { new ItemStack(Blocks.DOUBLE_PLANT, 1, 4) });
/* 76 */     paramCraftingManager.registerShapelessRecipe(new ItemStack(Items.INK_SACK, 2, 9), new Object[] { new ItemStack(Blocks.DOUBLE_PLANT, 1, 5) });
/*    */     
/* 78 */     for (b = 0; b < 16; b++) {
/* 79 */       paramCraftingManager.registerShapedRecipe(new ItemStack(Blocks.WOOL_CARPET, 3, b), new Object[] { "##", Character.valueOf('#'), new ItemStack(Blocks.WOOL, 1, b) });
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\RecipesDyes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */