/*    */ package org.bukkit.craftbukkit.v1_7_R4.inventory;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ import net.minecraft.server.v1_7_R4.CraftingManager;
/*    */ import net.minecraft.server.v1_7_R4.ItemStack;
/*    */ import net.minecraft.server.v1_7_R4.ShapedRecipes;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.util.CraftMagicNumbers;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.ShapedRecipe;
/*    */ 
/*    */ public class CraftShapedRecipe
/*    */   extends ShapedRecipe implements CraftRecipe {
/*    */   private ShapedRecipes recipe;
/*    */   
/*    */   public CraftShapedRecipe(ItemStack result) {
/* 17 */     super(result);
/*    */   }
/*    */   
/*    */   public CraftShapedRecipe(ItemStack result, ShapedRecipes recipe) {
/* 21 */     this(result);
/* 22 */     this.recipe = recipe;
/*    */   }
/*    */   
/*    */   public static CraftShapedRecipe fromBukkitRecipe(ShapedRecipe recipe) {
/* 26 */     if (recipe instanceof CraftShapedRecipe) {
/* 27 */       return (CraftShapedRecipe)recipe;
/*    */     }
/* 29 */     CraftShapedRecipe ret = new CraftShapedRecipe(recipe.getResult());
/* 30 */     String[] shape = recipe.getShape();
/* 31 */     ret.shape(shape);
/* 32 */     Map<Character, ItemStack> ingredientMap = recipe.getIngredientMap();
/* 33 */     for (Iterator<Character> i$ = ingredientMap.keySet().iterator(); i$.hasNext(); ) { char c = ((Character)i$.next()).charValue();
/* 34 */       ItemStack stack = ingredientMap.get(Character.valueOf(c));
/* 35 */       if (stack != null) {
/* 36 */         ret.setIngredient(c, stack.getType(), stack.getDurability());
/*    */       } }
/*    */     
/* 39 */     return ret;
/*    */   }
/*    */ 
/*    */   
/*    */   public void addToCraftingManager() {
/* 44 */     String[] shape = getShape();
/* 45 */     Map<Character, ItemStack> ingred = getIngredientMap();
/* 46 */     int datalen = shape.length;
/* 47 */     datalen += ingred.size() * 2;
/* 48 */     int i = 0;
/* 49 */     Object[] data = new Object[datalen];
/* 50 */     for (; i < shape.length; i++) {
/* 51 */       data[i] = shape[i];
/*    */     }
/* 53 */     for (Iterator<Character> i$ = ingred.keySet().iterator(); i$.hasNext(); ) { char c = ((Character)i$.next()).charValue();
/* 54 */       ItemStack mdata = ingred.get(Character.valueOf(c));
/* 55 */       if (mdata == null)
/* 56 */         continue;  data[i] = Character.valueOf(c);
/* 57 */       i++;
/* 58 */       int id = mdata.getTypeId();
/* 59 */       short dmg = mdata.getDurability();
/* 60 */       data[i] = new ItemStack(CraftMagicNumbers.getItem(id), 1, dmg);
/* 61 */       i++; }
/*    */     
/* 63 */     CraftingManager.getInstance().registerShapedRecipe(CraftItemStack.asNMSCopy(getResult()), data);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\inventory\CraftShapedRecipe.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */