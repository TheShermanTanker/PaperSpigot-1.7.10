/*    */ package org.bukkit.potion;
/*    */ 
/*    */ public enum PotionType {
/*  4 */   WATER(0, null, 0),
/*  5 */   REGEN(1, PotionEffectType.REGENERATION, 2),
/*  6 */   SPEED(2, PotionEffectType.SPEED, 2),
/*  7 */   FIRE_RESISTANCE(3, PotionEffectType.FIRE_RESISTANCE, 1),
/*  8 */   POISON(4, PotionEffectType.POISON, 2),
/*  9 */   INSTANT_HEAL(5, PotionEffectType.HEAL, 2),
/* 10 */   NIGHT_VISION(6, PotionEffectType.NIGHT_VISION, 1),
/* 11 */   WEAKNESS(8, PotionEffectType.WEAKNESS, 1),
/* 12 */   STRENGTH(9, PotionEffectType.INCREASE_DAMAGE, 2),
/* 13 */   SLOWNESS(10, PotionEffectType.SLOW, 1),
/* 14 */   INSTANT_DAMAGE(12, PotionEffectType.HARM, 2),
/* 15 */   WATER_BREATHING(13, PotionEffectType.WATER_BREATHING, 1),
/* 16 */   INVISIBILITY(14, PotionEffectType.INVISIBILITY, 1);
/*    */   
/*    */   private final int damageValue;
/*    */   private final int maxLevel;
/*    */   private final PotionEffectType effect;
/*    */   
/*    */   PotionType(int damageValue, PotionEffectType effect, int maxLevel) {
/* 23 */     this.damageValue = damageValue;
/* 24 */     this.effect = effect;
/* 25 */     this.maxLevel = maxLevel;
/*    */   }
/*    */   
/*    */   public PotionEffectType getEffectType() {
/* 29 */     return this.effect;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public int getDamageValue() {
/* 38 */     return this.damageValue;
/*    */   }
/*    */   
/*    */   public int getMaxLevel() {
/* 42 */     return this.maxLevel;
/*    */   }
/*    */   
/*    */   public boolean isInstant() {
/* 46 */     return (this.effect == null) ? true : this.effect.isInstant();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @Deprecated
/*    */   public static PotionType getByDamageValue(int damage) {
/* 55 */     for (PotionType type : values()) {
/* 56 */       if (type.damageValue == damage)
/* 57 */         return type; 
/*    */     } 
/* 59 */     return null;
/*    */   }
/*    */   
/*    */   public static PotionType getByEffect(PotionEffectType effectType) {
/* 63 */     if (effectType == null)
/* 64 */       return WATER; 
/* 65 */     for (PotionType type : values()) {
/* 66 */       if (effectType.equals(type.effect))
/* 67 */         return type; 
/*    */     } 
/* 69 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\potion\PotionType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */