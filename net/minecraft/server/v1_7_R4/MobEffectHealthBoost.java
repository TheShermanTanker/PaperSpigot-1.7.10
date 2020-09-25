/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ public class MobEffectHealthBoost
/*    */   extends MobEffectList
/*    */ {
/*    */   public MobEffectHealthBoost(int paramInt1, boolean paramBoolean, int paramInt2) {
/*  8 */     super(paramInt1, paramBoolean, paramInt2);
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(EntityLiving paramEntityLiving, AttributeMapBase paramAttributeMapBase, int paramInt) {
/* 13 */     super.a(paramEntityLiving, paramAttributeMapBase, paramInt);
/* 14 */     if (paramEntityLiving.getHealth() > paramEntityLiving.getMaxHealth())
/* 15 */       paramEntityLiving.setHealth(paramEntityLiving.getMaxHealth()); 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\MobEffectHealthBoost.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */