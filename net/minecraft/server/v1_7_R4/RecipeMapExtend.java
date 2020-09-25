/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RecipeMapExtend
/*    */   extends ShapedRecipes
/*    */ {
/*    */   public RecipeMapExtend() {
/* 12 */     super(3, 3, new ItemStack[] { new ItemStack(Items.PAPER), new ItemStack(Items.PAPER), new ItemStack(Items.PAPER), new ItemStack(Items.PAPER), new ItemStack(Items.MAP, 0, 32767), new ItemStack(Items.PAPER), new ItemStack(Items.PAPER), new ItemStack(Items.PAPER), new ItemStack(Items.PAPER) }new ItemStack(Items.MAP_EMPTY, 0, 0));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean a(InventoryCrafting paramInventoryCrafting, World paramWorld) {
/* 21 */     if (!super.a(paramInventoryCrafting, paramWorld)) return false; 
/* 22 */     ItemStack itemStack = null;
/*    */     
/* 24 */     for (byte b = 0; b < paramInventoryCrafting.getSize() && itemStack == null; b++) {
/* 25 */       ItemStack itemStack1 = paramInventoryCrafting.getItem(b);
/* 26 */       if (itemStack1 != null && itemStack1.getItem() == Items.MAP) itemStack = itemStack1;
/*    */     
/*    */     } 
/* 29 */     if (itemStack == null) return false; 
/* 30 */     WorldMap worldMap = Items.MAP.getSavedMap(itemStack, paramWorld);
/* 31 */     if (worldMap == null) return false; 
/* 32 */     return (worldMap.scale < 4);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack a(InventoryCrafting paramInventoryCrafting) {
/* 37 */     ItemStack itemStack = null;
/*    */     
/* 39 */     for (byte b = 0; b < paramInventoryCrafting.getSize() && itemStack == null; b++) {
/* 40 */       ItemStack itemStack1 = paramInventoryCrafting.getItem(b);
/* 41 */       if (itemStack1 != null && itemStack1.getItem() == Items.MAP) itemStack = itemStack1;
/*    */     
/*    */     } 
/* 44 */     itemStack = itemStack.cloneItemStack();
/* 45 */     itemStack.count = 1;
/*    */     
/* 47 */     if (itemStack.getTag() == null) itemStack.setTag(new NBTTagCompound()); 
/* 48 */     itemStack.getTag().setBoolean("map_is_scaling", true);
/*    */     
/* 50 */     return itemStack;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\RecipeMapExtend.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */