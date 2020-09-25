/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemBookAndQuill
/*    */   extends Item
/*    */ {
/*    */   public ItemBookAndQuill() {
/* 10 */     e(1);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman) {
/* 15 */     paramEntityHuman.b(paramItemStack);
/* 16 */     return paramItemStack;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean s() {
/* 21 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean a(NBTTagCompound paramNBTTagCompound) {
/* 26 */     if (paramNBTTagCompound == null) {
/* 27 */       return false;
/*    */     }
/* 29 */     if (!paramNBTTagCompound.hasKeyOfType("pages", 9)) {
/* 30 */       return false;
/*    */     }
/*    */     
/* 33 */     NBTTagList nBTTagList = paramNBTTagCompound.getList("pages", 8);
/* 34 */     for (byte b = 0; b < nBTTagList.size(); b++) {
/* 35 */       String str = nBTTagList.getString(b);
/*    */       
/* 37 */       if (str == null) {
/* 38 */         return false;
/*    */       }
/* 40 */       if (str.length() > 256) {
/* 41 */         return false;
/*    */       }
/*    */     } 
/*    */     
/* 45 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemBookAndQuill.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */