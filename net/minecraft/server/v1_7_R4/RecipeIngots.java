/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RecipeIngots
/*    */ {
/*  8 */   private Object[][] a = new Object[][] { { Blocks.GOLD_BLOCK, new ItemStack(Items.GOLD_INGOT, 9) }, { Blocks.IRON_BLOCK, new ItemStack(Items.IRON_INGOT, 9) }, { Blocks.DIAMOND_BLOCK, new ItemStack(Items.DIAMOND, 9) }, { Blocks.EMERALD_BLOCK, new ItemStack(Items.EMERALD, 9) }, { Blocks.LAPIS_BLOCK, new ItemStack(Items.INK_SACK, 9, 4) }, { Blocks.REDSTONE_BLOCK, new ItemStack(Items.REDSTONE, 9) }, { Blocks.COAL_BLOCK, new ItemStack(Items.COAL, 9, 0) }, { Blocks.HAY_BLOCK, new ItemStack(Items.WHEAT, 9) } };
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
/*    */   public void a(CraftingManager paramCraftingManager) {
/* 20 */     for (byte b = 0; b < this.a.length; b++) {
/* 21 */       Block block = (Block)this.a[b][0];
/* 22 */       ItemStack itemStack = (ItemStack)this.a[b][1];
/* 23 */       paramCraftingManager.registerShapedRecipe(new ItemStack(block), new Object[] { "###", "###", "###", Character.valueOf('#'), itemStack });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 30 */       paramCraftingManager.registerShapedRecipe(itemStack, new Object[] { "#", Character.valueOf('#'), block });
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 35 */     paramCraftingManager.registerShapedRecipe(new ItemStack(Items.GOLD_INGOT), new Object[] { "###", "###", "###", Character.valueOf('#'), Items.GOLD_NUGGET });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 41 */     paramCraftingManager.registerShapedRecipe(new ItemStack(Items.GOLD_NUGGET, 9), new Object[] { "#", Character.valueOf('#'), Items.GOLD_INGOT });
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\RecipeIngots.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */