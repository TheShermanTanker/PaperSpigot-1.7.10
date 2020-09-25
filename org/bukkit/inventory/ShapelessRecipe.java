/*     */ package org.bukkit.inventory;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.material.MaterialData;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ShapelessRecipe
/*     */   implements Recipe
/*     */ {
/*     */   private ItemStack output;
/*  18 */   private List<ItemStack> ingredients = new ArrayList<ItemStack>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShapelessRecipe(ItemStack result) {
/*  34 */     this.output = new ItemStack(result);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShapelessRecipe addIngredient(MaterialData ingredient) {
/*  44 */     return addIngredient(1, ingredient);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShapelessRecipe addIngredient(Material ingredient) {
/*  54 */     return addIngredient(1, ingredient, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public ShapelessRecipe addIngredient(Material ingredient, int rawdata) {
/*  67 */     return addIngredient(1, ingredient, rawdata);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShapelessRecipe addIngredient(int count, MaterialData ingredient) {
/*  78 */     return addIngredient(count, ingredient.getItemType(), ingredient.getData());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShapelessRecipe addIngredient(int count, Material ingredient) {
/*  89 */     return addIngredient(count, ingredient, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public ShapelessRecipe addIngredient(int count, Material ingredient, int rawdata) {
/* 103 */     Validate.isTrue((this.ingredients.size() + count <= 9), "Shapeless recipes cannot have more than 9 ingredients");
/*     */ 
/*     */     
/* 106 */     if (rawdata == -1) {
/* 107 */       rawdata = 32767;
/*     */     }
/*     */     
/* 110 */     while (count-- > 0) {
/* 111 */       this.ingredients.add(new ItemStack(ingredient, 1, (short)rawdata));
/*     */     }
/* 113 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShapelessRecipe removeIngredient(Material ingredient) {
/* 125 */     return removeIngredient(ingredient, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShapelessRecipe removeIngredient(MaterialData ingredient) {
/* 137 */     return removeIngredient(ingredient.getItemType(), ingredient.getData());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShapelessRecipe removeIngredient(int count, Material ingredient) {
/* 150 */     return removeIngredient(count, ingredient, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShapelessRecipe removeIngredient(int count, MaterialData ingredient) {
/* 163 */     return removeIngredient(count, ingredient.getItemType(), ingredient.getData());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public ShapelessRecipe removeIngredient(Material ingredient, int rawdata) {
/* 178 */     return removeIngredient(1, ingredient, rawdata);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public ShapelessRecipe removeIngredient(int count, Material ingredient, int rawdata) {
/* 194 */     Iterator<ItemStack> iterator = this.ingredients.iterator();
/* 195 */     while (count > 0 && iterator.hasNext()) {
/* 196 */       ItemStack stack = iterator.next();
/* 197 */       if (stack.getType() == ingredient && stack.getDurability() == rawdata) {
/* 198 */         iterator.remove();
/* 199 */         count--;
/*     */       } 
/*     */     } 
/* 202 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getResult() {
/* 211 */     return this.output.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<ItemStack> getIngredientList() {
/* 220 */     ArrayList<ItemStack> result = new ArrayList<ItemStack>(this.ingredients.size());
/* 221 */     for (ItemStack ingredient : this.ingredients) {
/* 222 */       result.add(ingredient.clone());
/*     */     }
/* 224 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\inventory\ShapelessRecipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */