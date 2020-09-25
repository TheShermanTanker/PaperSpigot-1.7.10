/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ 
/*    */ public class MobEffectAbsorption
/*    */   extends MobEffectList
/*    */ {
/*    */   protected MobEffectAbsorption(int paramInt1, boolean paramBoolean, int paramInt2) {
/*  8 */     super(paramInt1, paramBoolean, paramInt2);
/*    */   }
/*    */ 
/*    */   
/*    */   public void a(EntityLiving paramEntityLiving, AttributeMapBase paramAttributeMapBase, int paramInt) {
/* 13 */     paramEntityLiving.setAbsorptionHearts(paramEntityLiving.getAbsorptionHearts() - (4 * (paramInt + 1)));
/* 14 */     super.a(paramEntityLiving, paramAttributeMapBase, paramInt);
/*    */   }
/*    */ 
/*    */   
/*    */   public void b(EntityLiving paramEntityLiving, AttributeMapBase paramAttributeMapBase, int paramInt) {
/* 19 */     paramEntityLiving.setAbsorptionHearts(paramEntityLiving.getAbsorptionHearts() + (4 * (paramInt + 1)));
/* 20 */     super.b(paramEntityLiving, paramAttributeMapBase, paramInt);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\MobEffectAbsorption.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */