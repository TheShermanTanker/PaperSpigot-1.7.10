/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemNameTag
/*    */   extends Item
/*    */ {
/*    */   public ItemNameTag() {
/*  9 */     a(CreativeModeTab.i);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean a(ItemStack paramItemStack, EntityHuman paramEntityHuman, EntityLiving paramEntityLiving) {
/* 14 */     if (!paramItemStack.hasName()) return false;
/*    */     
/* 16 */     if (paramEntityLiving instanceof EntityInsentient) {
/* 17 */       EntityInsentient entityInsentient = (EntityInsentient)paramEntityLiving;
/* 18 */       entityInsentient.setCustomName(paramItemStack.getName());
/* 19 */       entityInsentient.bF();
/* 20 */       paramItemStack.count--;
/* 21 */       return true;
/*    */     } 
/*    */     
/* 24 */     return super.a(paramItemStack, paramEntityHuman, paramEntityLiving);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemNameTag.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */