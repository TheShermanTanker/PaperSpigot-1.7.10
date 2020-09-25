/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ public class ItemSoup
/*    */   extends ItemFood
/*    */ {
/*    */   public ItemSoup(int paramInt) {
/*  8 */     super(paramInt, false);
/*    */     
/* 10 */     e(1);
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack b(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman) {
/* 15 */     super.b(paramItemStack, paramWorld, paramEntityHuman);
/*    */     
/* 17 */     return new ItemStack(Items.BOWL);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemSoup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */