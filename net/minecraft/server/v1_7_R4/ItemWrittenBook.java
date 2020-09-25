/*    */ package net.minecraft.server.v1_7_R4;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemWrittenBook
/*    */   extends Item
/*    */ {
/*    */   public ItemWrittenBook() {
/* 22 */     e(1);
/*    */   }
/*    */ 
/*    */   
/*    */   public static boolean a(NBTTagCompound paramNBTTagCompound) {
/* 27 */     if (!ItemBookAndQuill.a(paramNBTTagCompound)) {
/* 28 */       return false;
/*    */     }
/*    */     
/* 31 */     if (!paramNBTTagCompound.hasKeyOfType("title", 8)) {
/* 32 */       return false;
/*    */     }
/* 34 */     String str = paramNBTTagCompound.getString("title");
/* 35 */     if (str == null || str.length() > 16) {
/* 36 */       return false;
/*    */     }
/*    */     
/* 39 */     if (!paramNBTTagCompound.hasKeyOfType("author", 8)) {
/* 40 */       return false;
/*    */     }
/*    */     
/* 43 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public String n(ItemStack paramItemStack) {
/* 48 */     if (paramItemStack.hasTag()) {
/* 49 */       NBTTagCompound nBTTagCompound = paramItemStack.getTag();
/*    */       
/* 51 */       String str = nBTTagCompound.getString("title");
/* 52 */       if (!UtilColor.b(str)) {
/* 53 */         return str;
/*    */       }
/*    */     } 
/* 56 */     return super.n(paramItemStack);
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman) {
/* 74 */     paramEntityHuman.b(paramItemStack);
/* 75 */     return paramItemStack;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean s() {
/* 80 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemWrittenBook.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */