/*    */ package org.bukkit.inventory;
/*    */ 
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.material.MaterialData;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FurnaceRecipe
/*    */   implements Recipe
/*    */ {
/*    */   private ItemStack output;
/*    */   private ItemStack ingredient;
/*    */   
/*    */   public FurnaceRecipe(ItemStack result, Material source) {
/* 20 */     this(result, source, 0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public FurnaceRecipe(ItemStack result, MaterialData source) {
/* 30 */     this(result, source.getItemType(), source.getData());
/*    */   }
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
/*    */   @Deprecated
/*    */   public FurnaceRecipe(ItemStack result, Material source, int data) {
/* 44 */     this.output = new ItemStack(result);
/* 45 */     this.ingredient = new ItemStack(source, 1, (short)data);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public FurnaceRecipe setInput(MaterialData input) {
/* 55 */     return setInput(input.getItemType(), input.getData());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public FurnaceRecipe setInput(Material input) {
/* 65 */     return setInput(input, 0);
/*    */   }
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
/*    */   @Deprecated
/*    */   public FurnaceRecipe setInput(Material input, int data) {
/* 79 */     this.ingredient = new ItemStack(input, 1, (short)data);
/* 80 */     return this;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack getInput() {
/* 89 */     return this.ingredient.clone();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack getResult() {
/* 98 */     return this.output.clone();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\inventory\FurnaceRecipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */