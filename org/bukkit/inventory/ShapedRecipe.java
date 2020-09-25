/*     */ package org.bukkit.inventory;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.material.MaterialData;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ShapedRecipe
/*     */   implements Recipe
/*     */ {
/*     */   private ItemStack output;
/*     */   private String[] rows;
/*  17 */   private Map<Character, ItemStack> ingredients = new HashMap<Character, ItemStack>();
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
/*     */   public ShapedRecipe(ItemStack result) {
/*  31 */     this.output = new ItemStack(result);
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
/*     */   public ShapedRecipe shape(String... shape) {
/*  46 */     Validate.notNull(shape, "Must provide a shape");
/*  47 */     Validate.isTrue((shape.length > 0 && shape.length < 4), "Crafting recipes should be 1, 2, 3 rows, not ", shape.length);
/*     */     
/*  49 */     for (String row : shape) {
/*  50 */       Validate.notNull(row, "Shape cannot have null rows");
/*  51 */       Validate.isTrue((row.length() > 0 && row.length() < 4), "Crafting rows should be 1, 2, or 3 characters, not ", row.length());
/*     */     } 
/*  53 */     this.rows = new String[shape.length];
/*  54 */     for (int i = 0; i < shape.length; i++) {
/*  55 */       this.rows[i] = shape[i];
/*     */     }
/*     */ 
/*     */     
/*  59 */     HashMap<Character, ItemStack> newIngredients = new HashMap<Character, ItemStack>();
/*  60 */     for (String row : shape) {
/*  61 */       char[] arr$; int len$; int i$; for (arr$ = row.toCharArray(), len$ = arr$.length, i$ = 0; i$ < len$; ) { Character c = Character.valueOf(arr$[i$]);
/*  62 */         newIngredients.put(c, this.ingredients.get(c)); i$++; }
/*     */     
/*     */     } 
/*  65 */     this.ingredients = newIngredients;
/*     */     
/*  67 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShapedRecipe setIngredient(char key, MaterialData ingredient) {
/*  78 */     return setIngredient(key, ingredient.getItemType(), ingredient.getData());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ShapedRecipe setIngredient(char key, Material ingredient) {
/*  89 */     return setIngredient(key, ingredient, 0);
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
/*     */   public ShapedRecipe setIngredient(char key, Material ingredient, int raw) {
/* 103 */     Validate.isTrue(this.ingredients.containsKey(Character.valueOf(key)), "Symbol does not appear in the shape:", key);
/*     */ 
/*     */     
/* 106 */     if (raw == -1) {
/* 107 */       raw = 32767;
/*     */     }
/*     */     
/* 110 */     this.ingredients.put(Character.valueOf(key), new ItemStack(ingredient, 1, (short)raw));
/* 111 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<Character, ItemStack> getIngredientMap() {
/* 120 */     HashMap<Character, ItemStack> result = new HashMap<Character, ItemStack>();
/* 121 */     for (Map.Entry<Character, ItemStack> ingredient : this.ingredients.entrySet()) {
/* 122 */       if (ingredient.getValue() == null) {
/* 123 */         result.put(ingredient.getKey(), null); continue;
/*     */       } 
/* 125 */       result.put(ingredient.getKey(), ((ItemStack)ingredient.getValue()).clone());
/*     */     } 
/*     */     
/* 128 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String[] getShape() {
/* 137 */     return (String[])this.rows.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getResult() {
/* 146 */     return this.output.clone();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\inventory\ShapedRecipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */