/*    */ package org.bukkit.potion;
/*    */ 
/*    */ public class PotionEffectTypeWrapper extends PotionEffectType {
/*    */   protected PotionEffectTypeWrapper(int id) {
/*  5 */     super(id);
/*    */   }
/*    */ 
/*    */   
/*    */   public double getDurationModifier() {
/* 10 */     return getType().getDurationModifier();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 15 */     return getType().getName();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PotionEffectType getType() {
/* 24 */     return PotionEffectType.getById(getId());
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isInstant() {
/* 29 */     return getType().isInstant();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\potion\PotionEffectTypeWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */