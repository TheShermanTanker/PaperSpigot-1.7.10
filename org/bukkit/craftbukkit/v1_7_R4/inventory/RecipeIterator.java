/*    */ package org.bukkit.craftbukkit.v1_7_R4.inventory;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import net.minecraft.server.v1_7_R4.CraftingManager;
/*    */ import net.minecraft.server.v1_7_R4.IRecipe;
/*    */ import net.minecraft.server.v1_7_R4.ItemStack;
/*    */ import net.minecraft.server.v1_7_R4.RecipesFurnace;
/*    */ import org.bukkit.inventory.Recipe;
/*    */ 
/*    */ public class RecipeIterator
/*    */   implements Iterator<Recipe> {
/*    */   private final Iterator<IRecipe> recipes;
/*    */   private final Iterator<ItemStack> smeltingCustom;
/*    */   private final Iterator<ItemStack> smeltingVanilla;
/* 15 */   private Iterator<?> removeFrom = null;
/*    */   
/*    */   public RecipeIterator() {
/* 18 */     this.recipes = CraftingManager.getInstance().getRecipes().iterator();
/* 19 */     this.smeltingCustom = (RecipesFurnace.getInstance()).customRecipes.keySet().iterator();
/* 20 */     this.smeltingVanilla = (RecipesFurnace.getInstance()).recipes.keySet().iterator();
/*    */   }
/*    */   
/*    */   public boolean hasNext() {
/* 24 */     return (this.recipes.hasNext() || this.smeltingCustom.hasNext() || this.smeltingVanilla.hasNext());
/*    */   }
/*    */   public Recipe next() {
/*    */     ItemStack item;
/* 28 */     if (this.recipes.hasNext()) {
/* 29 */       this.removeFrom = this.recipes;
/* 30 */       return ((IRecipe)this.recipes.next()).toBukkitRecipe();
/*    */     } 
/*    */     
/* 33 */     if (this.smeltingCustom.hasNext()) {
/* 34 */       this.removeFrom = this.smeltingCustom;
/* 35 */       item = this.smeltingCustom.next();
/*    */     } else {
/* 37 */       this.removeFrom = this.smeltingVanilla;
/* 38 */       item = this.smeltingVanilla.next();
/*    */     } 
/*    */     
/* 41 */     CraftItemStack stack = CraftItemStack.asCraftMirror(RecipesFurnace.getInstance().getResult(item));
/*    */     
/* 43 */     return new CraftFurnaceRecipe(stack, CraftItemStack.asCraftMirror(item));
/*    */   }
/*    */ 
/*    */   
/*    */   public void remove() {
/* 48 */     if (this.removeFrom == null) {
/* 49 */       throw new IllegalStateException();
/*    */     }
/* 51 */     this.removeFrom.remove();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\inventory\RecipeIterator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */