/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ 
/*    */ public class RecipeBookClone extends ShapelessRecipes implements IRecipe {
/*    */   public RecipeBookClone() {
/*  7 */     super(new ItemStack(Items.WRITTEN_BOOK, 0, -1), Arrays.asList(new ItemStack[] { new ItemStack(Items.BOOK_AND_QUILL, 0, 0) }));
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean a(InventoryCrafting inventoryCrafting, World paramWorld) {
/* 12 */     int i = 0;
/* 13 */     ItemStack itemStack = null;
/* 14 */     for (int j = 0; j < inventoryCrafting.getSize(); j++) {
/* 15 */       ItemStack itemStack1 = inventoryCrafting.getItem(j);
/* 16 */       if (itemStack1 != null) {
/* 17 */         if (itemStack1.getItem() == Items.WRITTEN_BOOK) {
/* 18 */           if (itemStack != null) {
/* 19 */             return false;
/*    */           }
/* 21 */           itemStack = itemStack1;
/* 22 */         } else if (itemStack1.getItem() == Items.BOOK_AND_QUILL) {
/* 23 */           i++;
/*    */         } else {
/* 25 */           return false;
/*    */         } 
/*    */       }
/*    */     } 
/* 29 */     return (itemStack != null && i > 0);
/*    */   }
/*    */   
/*    */   public ItemStack a(InventoryCrafting inventoryCrafting) {
/* 33 */     int i = 0;
/* 34 */     ItemStack itemStack = null;
/* 35 */     for (int j = 0; j < inventoryCrafting.getSize(); j++) {
/* 36 */       ItemStack itemStack2 = inventoryCrafting.getItem(j);
/* 37 */       if (itemStack2 != null) {
/* 38 */         if (itemStack2.getItem() == Items.WRITTEN_BOOK) {
/* 39 */           if (itemStack != null) {
/* 40 */             return null;
/*    */           }
/* 42 */           itemStack = itemStack2;
/* 43 */         } else if (itemStack2.getItem() == Items.BOOK_AND_QUILL) {
/* 44 */           i++;
/*    */         } else {
/* 46 */           return null;
/*    */         } 
/*    */       }
/*    */     } 
/* 50 */     if (itemStack == null || i < 1) {
/* 51 */       return null;
/*    */     }
/* 53 */     ItemStack itemStack1 = new ItemStack(Items.WRITTEN_BOOK, i + 1);
/* 54 */     itemStack1.setTag((NBTTagCompound)itemStack.getTag().clone());
/* 55 */     if (itemStack.hasName()) {
/* 56 */       itemStack1.c(itemStack.getName());
/*    */     }
/* 58 */     return itemStack1;
/*    */   }
/*    */   
/*    */   public int a() {
/* 62 */     return 9;
/*    */   }
/*    */   
/*    */   public ItemStack b() {
/* 66 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\RecipeBookClone.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */