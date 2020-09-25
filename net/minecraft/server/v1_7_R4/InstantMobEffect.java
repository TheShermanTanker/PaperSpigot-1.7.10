/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ public class InstantMobEffect
/*    */   extends MobEffectList {
/*    */   public InstantMobEffect(int paramInt1, boolean paramBoolean, int paramInt2) {
/*  6 */     super(paramInt1, paramBoolean, paramInt2);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isInstant() {
/* 11 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean a(int paramInt1, int paramInt2) {
/* 16 */     return (paramInt1 >= 1);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\InstantMobEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */