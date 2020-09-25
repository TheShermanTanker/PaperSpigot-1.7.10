/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemCarrotStick
/*    */   extends Item
/*    */ {
/*    */   public ItemCarrotStick() {
/*  9 */     a(CreativeModeTab.e);
/* 10 */     e(1);
/* 11 */     setMaxDurability(25);
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
/*    */   public ItemStack a(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman) {
/* 26 */     if (paramEntityHuman.am() && paramEntityHuman.vehicle instanceof EntityPig) {
/* 27 */       EntityPig entityPig = (EntityPig)paramEntityHuman.vehicle;
/*    */       
/* 29 */       if (entityPig.ca().h() && paramItemStack.l() - paramItemStack.getData() >= 7) {
/* 30 */         entityPig.ca().g();
/* 31 */         paramItemStack.damage(7, paramEntityHuman);
/*    */         
/* 33 */         if (paramItemStack.count == 0) {
/* 34 */           ItemStack itemStack = new ItemStack(Items.FISHING_ROD);
/* 35 */           itemStack.setTag(paramItemStack.tag);
/* 36 */           return itemStack;
/*    */         } 
/*    */       } 
/*    */     } 
/*    */     
/* 41 */     return paramItemStack;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemCarrotStick.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */