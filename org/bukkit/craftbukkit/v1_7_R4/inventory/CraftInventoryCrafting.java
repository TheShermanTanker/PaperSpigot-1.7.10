/*     */ package org.bukkit.craftbukkit.v1_7_R4.inventory;
/*     */ 
/*     */ import net.minecraft.server.v1_7_R4.IInventory;
/*     */ import net.minecraft.server.v1_7_R4.IRecipe;
/*     */ import net.minecraft.server.v1_7_R4.InventoryCrafting;
/*     */ import net.minecraft.server.v1_7_R4.ItemStack;
/*     */ import org.bukkit.inventory.CraftingInventory;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.Recipe;
/*     */ import org.bukkit.util.Java15Compat;
/*     */ 
/*     */ public class CraftInventoryCrafting extends CraftInventory implements CraftingInventory {
/*     */   private final IInventory resultInventory;
/*     */   
/*     */   public CraftInventoryCrafting(InventoryCrafting inventory, IInventory resultInventory) {
/*  16 */     super((IInventory)inventory);
/*  17 */     this.resultInventory = resultInventory;
/*     */   }
/*     */   
/*     */   public IInventory getResultInventory() {
/*  21 */     return this.resultInventory;
/*     */   }
/*     */   
/*     */   public IInventory getMatrixInventory() {
/*  25 */     return this.inventory;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSize() {
/*  30 */     return getResultInventory().getSize() + getMatrixInventory().getSize();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContents(ItemStack[] items) {
/*  35 */     int resultLen = (getResultInventory().getContents()).length;
/*  36 */     int len = (getMatrixInventory().getContents()).length + resultLen;
/*  37 */     if (len > items.length) {
/*  38 */       throw new IllegalArgumentException("Invalid inventory size; expected " + len + " or less");
/*     */     }
/*  40 */     setContents(items[0], (ItemStack[])Java15Compat.Arrays_copyOfRange((Object[])items, 1, items.length));
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack[] getContents() {
/*  45 */     ItemStack[] items = new ItemStack[getSize()];
/*  46 */     ItemStack[] mcResultItems = getResultInventory().getContents();
/*     */     
/*  48 */     int i = 0;
/*  49 */     for (i = 0; i < mcResultItems.length; i++) {
/*  50 */       items[i] = CraftItemStack.asCraftMirror(mcResultItems[i]);
/*     */     }
/*     */     
/*  53 */     ItemStack[] mcItems = getMatrixInventory().getContents();
/*     */     
/*  55 */     for (int j = 0; j < mcItems.length; j++) {
/*  56 */       items[i + j] = CraftItemStack.asCraftMirror(mcItems[j]);
/*     */     }
/*     */     
/*  59 */     return items;
/*     */   }
/*     */   
/*     */   public void setContents(ItemStack result, ItemStack[] contents) {
/*  63 */     setResult(result);
/*  64 */     setMatrix(contents);
/*     */   }
/*     */ 
/*     */   
/*     */   public CraftItemStack getItem(int index) {
/*  69 */     if (index < getResultInventory().getSize()) {
/*  70 */       ItemStack itemStack = getResultInventory().getItem(index);
/*  71 */       return (itemStack == null) ? null : CraftItemStack.asCraftMirror(itemStack);
/*     */     } 
/*  73 */     ItemStack item = getMatrixInventory().getItem(index - getResultInventory().getSize());
/*  74 */     return (item == null) ? null : CraftItemStack.asCraftMirror(item);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setItem(int index, ItemStack item) {
/*  80 */     if (index < getResultInventory().getSize()) {
/*  81 */       getResultInventory().setItem(index, (item == null) ? null : CraftItemStack.asNMSCopy(item));
/*     */     } else {
/*  83 */       getMatrixInventory().setItem(index - getResultInventory().getSize(), (item == null) ? null : CraftItemStack.asNMSCopy(item));
/*     */     } 
/*     */   }
/*     */   
/*     */   public ItemStack[] getMatrix() {
/*  88 */     ItemStack[] items = new ItemStack[getSize()];
/*  89 */     ItemStack[] matrix = getMatrixInventory().getContents();
/*     */     
/*  91 */     for (int i = 0; i < matrix.length; i++) {
/*  92 */       items[i] = CraftItemStack.asCraftMirror(matrix[i]);
/*     */     }
/*     */     
/*  95 */     return items;
/*     */   }
/*     */   
/*     */   public ItemStack getResult() {
/*  99 */     ItemStack item = getResultInventory().getItem(0);
/* 100 */     if (item != null) return CraftItemStack.asCraftMirror(item); 
/* 101 */     return null;
/*     */   }
/*     */   
/*     */   public void setMatrix(ItemStack[] contents) {
/* 105 */     if ((getMatrixInventory().getContents()).length > contents.length) {
/* 106 */       throw new IllegalArgumentException("Invalid inventory size; expected " + (getMatrixInventory().getContents()).length + " or less");
/*     */     }
/*     */     
/* 109 */     ItemStack[] mcItems = getMatrixInventory().getContents();
/*     */     
/* 111 */     for (int i = 0; i < mcItems.length; i++) {
/* 112 */       if (i < contents.length) {
/* 113 */         ItemStack item = contents[i];
/* 114 */         if (item == null || item.getTypeId() <= 0) {
/* 115 */           mcItems[i] = null;
/*     */         } else {
/* 117 */           mcItems[i] = CraftItemStack.asNMSCopy(item);
/*     */         } 
/*     */       } else {
/* 120 */         mcItems[i] = null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setResult(ItemStack item) {
/* 126 */     ItemStack[] contents = getResultInventory().getContents();
/* 127 */     if (item == null || item.getTypeId() <= 0) {
/* 128 */       contents[0] = null;
/*     */     } else {
/* 130 */       contents[0] = CraftItemStack.asNMSCopy(item);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Recipe getRecipe() {
/* 135 */     IRecipe recipe = ((InventoryCrafting)getInventory()).currentRecipe;
/* 136 */     return (recipe == null) ? null : recipe.toBukkitRecipe();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\inventory\CraftInventoryCrafting.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */