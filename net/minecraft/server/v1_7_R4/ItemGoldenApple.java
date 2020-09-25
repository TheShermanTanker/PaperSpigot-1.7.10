/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemGoldenApple
/*    */   extends ItemFood
/*    */ {
/*    */   public ItemGoldenApple(int paramInt, float paramFloat, boolean paramBoolean) {
/* 12 */     super(paramInt, paramFloat, paramBoolean);
/*    */     
/* 14 */     a(true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EnumItemRarity f(ItemStack paramItemStack) {
/* 24 */     if (paramItemStack.getData() == 0) {
/* 25 */       return EnumItemRarity.RARE;
/*    */     }
/* 27 */     return EnumItemRarity.EPIC;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void c(ItemStack paramItemStack, World paramWorld, EntityHuman paramEntityHuman) {
/* 32 */     if (!paramWorld.isStatic) paramEntityHuman.addEffect(new MobEffect(MobEffectList.ABSORPTION.id, 2400, 0));
/*    */     
/* 34 */     if (paramItemStack.getData() > 0) {
/* 35 */       if (!paramWorld.isStatic) {
/* 36 */         paramEntityHuman.addEffect(new MobEffect(MobEffectList.REGENERATION.id, 600, 4));
/* 37 */         paramEntityHuman.addEffect(new MobEffect(MobEffectList.RESISTANCE.id, 6000, 0));
/* 38 */         paramEntityHuman.addEffect(new MobEffect(MobEffectList.FIRE_RESISTANCE.id, 6000, 0));
/*    */       } 
/*    */     } else {
/* 41 */       super.c(paramItemStack, paramWorld, paramEntityHuman);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ItemGoldenApple.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */