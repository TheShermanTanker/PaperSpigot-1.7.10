/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RecipesCrafting
/*     */ {
/*     */   public void a(CraftingManager paramCraftingManager) {
/*   8 */     paramCraftingManager.registerShapedRecipe(new ItemStack(Blocks.CHEST), new Object[] { "###", "# #", "###", Character.valueOf('#'), Blocks.WOOD });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  15 */     paramCraftingManager.registerShapedRecipe(new ItemStack(Blocks.TRAPPED_CHEST), new Object[] { "#-", Character.valueOf('#'), Blocks.CHEST, Character.valueOf('-'), Blocks.TRIPWIRE_SOURCE });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  20 */     paramCraftingManager.registerShapedRecipe(new ItemStack(Blocks.ENDER_CHEST), new Object[] { "###", "#E#", "###", Character.valueOf('#'), Blocks.OBSIDIAN, Character.valueOf('E'), Items.EYE_OF_ENDER });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  27 */     paramCraftingManager.registerShapedRecipe(new ItemStack(Blocks.FURNACE), new Object[] { "###", "# #", "###", Character.valueOf('#'), Blocks.COBBLESTONE });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  34 */     paramCraftingManager.registerShapedRecipe(new ItemStack(Blocks.WORKBENCH), new Object[] { "##", "##", Character.valueOf('#'), Blocks.WOOD });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  40 */     paramCraftingManager.registerShapedRecipe(new ItemStack(Blocks.SANDSTONE), new Object[] { "##", "##", Character.valueOf('#'), new ItemStack(Blocks.SAND, 1, 0) });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  46 */     paramCraftingManager.registerShapedRecipe(new ItemStack(Blocks.SANDSTONE, 4, 2), new Object[] { "##", "##", Character.valueOf('#'), Blocks.SANDSTONE });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  52 */     paramCraftingManager.registerShapedRecipe(new ItemStack(Blocks.SANDSTONE, 1, 1), new Object[] { "#", "#", Character.valueOf('#'), new ItemStack(Blocks.STEP, 1, 1) });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  58 */     paramCraftingManager.registerShapedRecipe(new ItemStack(Blocks.QUARTZ_BLOCK, 1, 1), new Object[] { "#", "#", Character.valueOf('#'), new ItemStack(Blocks.STEP, 1, 7) });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  64 */     paramCraftingManager.registerShapedRecipe(new ItemStack(Blocks.QUARTZ_BLOCK, 2, 2), new Object[] { "#", "#", Character.valueOf('#'), new ItemStack(Blocks.QUARTZ_BLOCK, 1, 0) });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  70 */     paramCraftingManager.registerShapedRecipe(new ItemStack(Blocks.SMOOTH_BRICK, 4), new Object[] { "##", "##", Character.valueOf('#'), Blocks.STONE });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  76 */     paramCraftingManager.registerShapedRecipe(new ItemStack(Blocks.IRON_FENCE, 16), new Object[] { "###", "###", Character.valueOf('#'), Items.IRON_INGOT });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  82 */     paramCraftingManager.registerShapedRecipe(new ItemStack(Blocks.THIN_GLASS, 16), new Object[] { "###", "###", Character.valueOf('#'), Blocks.GLASS });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  88 */     paramCraftingManager.registerShapedRecipe(new ItemStack(Blocks.REDSTONE_LAMP_OFF, 1), new Object[] { " R ", "RGR", " R ", Character.valueOf('R'), Items.REDSTONE, Character.valueOf('G'), Blocks.GLOWSTONE });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  95 */     paramCraftingManager.registerShapedRecipe(new ItemStack(Blocks.BEACON, 1), new Object[] { "GGG", "GSG", "OOO", Character.valueOf('G'), Blocks.GLASS, Character.valueOf('S'), Items.NETHER_STAR, Character.valueOf('O'), Blocks.OBSIDIAN });
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 102 */     paramCraftingManager.registerShapedRecipe(new ItemStack(Blocks.NETHER_BRICK, 1), new Object[] { "NN", "NN", Character.valueOf('N'), Items.NETHER_BRICK });
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\RecipesCrafting.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */