/*    */ package net.minecraft.server.v1_7_R4;
/*    */ 
/*    */ import org.github.paperspigot.PaperSpigotConfig;
/*    */ 
/*    */ public class MobEffectAttackDamage
/*    */   extends MobEffectList {
/*    */   protected MobEffectAttackDamage(int i, boolean flag, int j) {
/*  8 */     super(i, flag, j);
/*    */   }
/*    */ 
/*    */   
/*    */   public double a(int i, AttributeModifier attributemodifier) {
/* 13 */     return (this.id == MobEffectList.WEAKNESS.id) ? (PaperSpigotConfig.weaknessEffectModifier * (i + 1)) : (PaperSpigotConfig.strengthEffectModifier * (i + 1));
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\MobEffectAttackDamage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */