/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftShapedRecipe;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.util.CraftMagicNumbers;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.Recipe;
/*     */ import org.bukkit.inventory.ShapedRecipe;
/*     */ 
/*     */ public class ShapedRecipes implements IRecipe {
/*     */   private int width;
/*     */   private int height;
/*     */   
/*     */   public ShapedRecipes(int i, int j, ItemStack[] aitemstack, ItemStack itemstack) {
/*  17 */     this.width = i;
/*  18 */     this.height = j;
/*  19 */     this.items = aitemstack;
/*  20 */     this.result = itemstack;
/*     */   }
/*     */   private ItemStack[] items; public ItemStack result; private boolean e;
/*     */   
/*     */   public ShapedRecipe toBukkitRecipe() {
/*  25 */     CraftItemStack result = CraftItemStack.asCraftMirror(this.result);
/*  26 */     CraftShapedRecipe recipe = new CraftShapedRecipe((ItemStack)result, this);
/*  27 */     switch (this.height) {
/*     */       case 1:
/*  29 */         switch (this.width) {
/*     */           case 1:
/*  31 */             recipe.shape(new String[] { "a" });
/*     */             break;
/*     */           case 2:
/*  34 */             recipe.shape(new String[] { "ab" });
/*     */             break;
/*     */           case 3:
/*  37 */             recipe.shape(new String[] { "abc" });
/*     */             break;
/*     */         } 
/*     */         break;
/*     */       case 2:
/*  42 */         switch (this.width) {
/*     */           case 1:
/*  44 */             recipe.shape(new String[] { "a", "b" });
/*     */             break;
/*     */           case 2:
/*  47 */             recipe.shape(new String[] { "ab", "cd" });
/*     */             break;
/*     */           case 3:
/*  50 */             recipe.shape(new String[] { "abc", "def" });
/*     */             break;
/*     */         } 
/*     */         break;
/*     */       case 3:
/*  55 */         switch (this.width) {
/*     */           case 1:
/*  57 */             recipe.shape(new String[] { "a", "b", "c" });
/*     */             break;
/*     */           case 2:
/*  60 */             recipe.shape(new String[] { "ab", "cd", "ef" });
/*     */             break;
/*     */           case 3:
/*  63 */             recipe.shape(new String[] { "abc", "def", "ghi" });
/*     */             break;
/*     */         } 
/*     */         break;
/*     */     } 
/*  68 */     char c = 'a';
/*  69 */     for (ItemStack stack : this.items) {
/*  70 */       if (stack != null) {
/*  71 */         recipe.setIngredient(c, CraftMagicNumbers.getMaterial(stack.getItem()), stack.getData());
/*     */       }
/*  73 */       c = (char)(c + 1);
/*     */     } 
/*  75 */     return (ShapedRecipe)recipe;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack b() {
/*  80 */     return this.result;
/*     */   }
/*     */   
/*     */   public boolean a(InventoryCrafting inventorycrafting, World world) {
/*  84 */     for (int i = 0; i <= 3 - this.width; i++) {
/*  85 */       for (int j = 0; j <= 3 - this.height; j++) {
/*  86 */         if (a(inventorycrafting, i, j, true)) {
/*  87 */           return true;
/*     */         }
/*     */         
/*  90 */         if (a(inventorycrafting, i, j, false)) {
/*  91 */           return true;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/*  96 */     return false;
/*     */   }
/*     */   
/*     */   private boolean a(InventoryCrafting inventorycrafting, int i, int j, boolean flag) {
/* 100 */     for (int k = 0; k < 3; k++) {
/* 101 */       for (int l = 0; l < 3; l++) {
/* 102 */         int i1 = k - i;
/* 103 */         int j1 = l - j;
/* 104 */         ItemStack itemstack = null;
/*     */         
/* 106 */         if (i1 >= 0 && j1 >= 0 && i1 < this.width && j1 < this.height) {
/* 107 */           if (flag) {
/* 108 */             itemstack = this.items[this.width - i1 - 1 + j1 * this.width];
/*     */           } else {
/* 110 */             itemstack = this.items[i1 + j1 * this.width];
/*     */           } 
/*     */         }
/*     */         
/* 114 */         ItemStack itemstack1 = inventorycrafting.b(k, l);
/*     */         
/* 116 */         if (itemstack1 != null || itemstack != null) {
/* 117 */           if ((itemstack1 == null && itemstack != null) || (itemstack1 != null && itemstack == null)) {
/* 118 */             return false;
/*     */           }
/*     */           
/* 121 */           if (itemstack.getItem() != itemstack1.getItem()) {
/* 122 */             return false;
/*     */           }
/*     */           
/* 125 */           if (itemstack.getData() != 32767 && itemstack.getData() != itemstack1.getData()) {
/* 126 */             return false;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 132 */     return true;
/*     */   }
/*     */   
/*     */   public ItemStack a(InventoryCrafting inventorycrafting) {
/* 136 */     ItemStack itemstack = b().cloneItemStack();
/*     */     
/* 138 */     if (this.e) {
/* 139 */       for (int i = 0; i < inventorycrafting.getSize(); i++) {
/* 140 */         ItemStack itemstack1 = inventorycrafting.getItem(i);
/*     */         
/* 142 */         if (itemstack1 != null && itemstack1.hasTag()) {
/* 143 */           itemstack.setTag((NBTTagCompound)itemstack1.tag.clone());
/*     */         }
/*     */       } 
/*     */     }
/*     */     
/* 148 */     return itemstack;
/*     */   }
/*     */   
/*     */   public int a() {
/* 152 */     return this.width * this.height;
/*     */   }
/*     */   
/*     */   public ShapedRecipes c() {
/* 156 */     this.e = true;
/* 157 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<ItemStack> getIngredients() {
/* 163 */     return Arrays.asList(this.items);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ShapedRecipes.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */