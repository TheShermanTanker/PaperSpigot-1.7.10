/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ public class ItemCloth
/*    */   extends ItemBlock
/*    */ {
/*    */   public ItemCloth(Block paramBlock) {
/*  8 */     super(paramBlock);
/*    */     
/* 10 */     setMaxDurability(0);
/* 11 */     a(true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int filterData(int paramInt) {
/* 21 */     return paramInt;
/*    */   }
/*    */ 
/*    */   
/*    */   public String a(ItemStack paramItemStack) {
/* 26 */     return getName() + "." + ItemDye.a[BlockCloth.b(paramItemStack.getData())];
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemCloth.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */