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
/*    */ public class ItemCoal
/*    */   extends Item
/*    */ {
/*    */   public ItemCoal() {
/* 17 */     a(true);
/* 18 */     setMaxDurability(0);
/* 19 */     a(CreativeModeTab.l);
/*    */   }
/*    */ 
/*    */   
/*    */   public String a(ItemStack paramItemStack) {
/* 24 */     if (paramItemStack.getData() == 1) {
/* 25 */       return "item.charcoal";
/*    */     }
/* 27 */     return "item.coal";
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemCoal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */