/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RecipesWeapons
/*    */ {
/*  7 */   private String[][] a = new String[][] { { "X", "X", "#" } };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 13 */   private Object[][] b = new Object[][] { { Blocks.WOOD, Blocks.COBBLESTONE, Items.IRON_INGOT, Items.DIAMOND, Items.GOLD_INGOT }, { Items.WOOD_SWORD, Items.STONE_SWORD, Items.IRON_SWORD, Items.DIAMOND_SWORD, Items.GOLD_SWORD } };
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void a(CraftingManager paramCraftingManager) {
/* 19 */     for (byte b = 0; b < (this.b[0]).length; b++) {
/* 20 */       Object object = this.b[0][b];
/*    */       
/* 22 */       for (byte b1 = 0; b1 < this.b.length - 1; b1++) {
/* 23 */         Item item = (Item)this.b[b1 + 1][b];
/* 24 */         paramCraftingManager.registerShapedRecipe(new ItemStack(item), new Object[] { this.a[b1], Character.valueOf('#'), Items.STICK, Character.valueOf('X'), object });
/*    */       } 
/*    */     } 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 32 */     paramCraftingManager.registerShapedRecipe(new ItemStack(Items.BOW, 1), new Object[] { " #X", "# X", " #X", Character.valueOf('X'), Items.STRING, Character.valueOf('#'), Items.STICK });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 40 */     paramCraftingManager.registerShapedRecipe(new ItemStack(Items.ARROW, 4), new Object[] { "X", "#", "Y", Character.valueOf('Y'), Items.FEATHER, Character.valueOf('X'), Items.FLINT, Character.valueOf('#'), Items.STICK });
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\RecipesWeapons.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */