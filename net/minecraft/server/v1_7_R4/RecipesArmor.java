/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RecipesArmor
/*    */ {
/*  7 */   private String[][] a = new String[][] { { "XXX", "X X" }, { "X X", "XXX", "XXX" }, { "XXX", "X X", "X X" }, { "X X", "X X" } };
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 31 */   private Object[][] b = new Object[][] { { Items.LEATHER, Blocks.FIRE, Items.IRON_INGOT, Items.DIAMOND, Items.GOLD_INGOT }, { Items.LEATHER_HELMET, Items.CHAINMAIL_HELMET, Items.IRON_HELMET, Items.DIAMOND_HELMET, Items.GOLD_HELMET }, { Items.LEATHER_CHESTPLATE, Items.CHAINMAIL_CHESTPLATE, Items.IRON_CHESTPLATE, Items.DIAMOND_CHESTPLATE, Items.GOLD_CHESTPLATE }, { Items.LEATHER_LEGGINGS, Items.CHAINMAIL_LEGGINGS, Items.IRON_LEGGINGS, Items.DIAMOND_LEGGINGS, Items.GOLD_LEGGINGS }, { Items.LEATHER_BOOTS, Items.CHAINMAIL_BOOTS, Items.IRON_BOOTS, Items.DIAMOND_BOOTS, Items.GOLD_BOOTS } };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void a(CraftingManager paramCraftingManager) {
/* 40 */     for (byte b = 0; b < (this.b[0]).length; b++) {
/*    */       
/* 42 */       Object object = this.b[0][b];
/*    */       
/* 44 */       for (byte b1 = 0; b1 < this.b.length - 1; b1++) {
/* 45 */         Item item = (Item)this.b[b1 + 1][b];
/*    */         
/* 47 */         paramCraftingManager.registerShapedRecipe(new ItemStack(item), new Object[] { this.a[b1], Character.valueOf('X'), object });
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\RecipesArmor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */