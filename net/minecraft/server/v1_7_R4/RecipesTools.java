/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RecipesTools
/*    */ {
/*  9 */   private String[][] a = new String[][] { { "XXX", " # ", " # " }, { "X", "#", "#" }, { "XX", "X#", " #" }, { "XX", " #", " #" } };
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
/* 27 */   private Object[][] b = new Object[][] { { Blocks.WOOD, Blocks.COBBLESTONE, Items.IRON_INGOT, Items.DIAMOND, Items.GOLD_INGOT }, { Items.WOOD_PICKAXE, Items.STONE_PICKAXE, Items.IRON_PICKAXE, Items.DIAMOND_PICKAXE, Items.GOLD_PICKAXE }, { Items.WOOD_SPADE, Items.STONE_SPADE, Items.IRON_SPADE, Items.DIAMOND_SPADE, Items.GOLD_SPADE }, { Items.WOOD_AXE, Items.STONE_AXE, Items.IRON_AXE, Items.DIAMOND_AXE, Items.GOLD_AXE }, { Items.WOOD_HOE, Items.STONE_HOE, Items.IRON_HOE, Items.DIAMOND_HOE, Items.GOLD_HOE } };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void a(CraftingManager paramCraftingManager) {
/* 37 */     for (byte b = 0; b < (this.b[0]).length; b++) {
/* 38 */       Object object = this.b[0][b];
/*    */       
/* 40 */       for (byte b1 = 0; b1 < this.b.length - 1; b1++) {
/* 41 */         Item item = (Item)this.b[b1 + 1][b];
/* 42 */         paramCraftingManager.registerShapedRecipe(new ItemStack(item), new Object[] { this.a[b1], Character.valueOf('#'), Items.STICK, Character.valueOf('X'), object });
/*    */       } 
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 48 */     paramCraftingManager.registerShapedRecipe(new ItemStack(Items.SHEARS), new Object[] { " #", "# ", Character.valueOf('#'), Items.IRON_INGOT });
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\RecipesTools.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */