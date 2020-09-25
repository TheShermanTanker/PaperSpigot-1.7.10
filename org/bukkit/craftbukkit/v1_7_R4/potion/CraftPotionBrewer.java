/*    */ package org.bukkit.craftbukkit.v1_7_R4.potion;
/*    */ 
/*    */ import com.google.common.collect.Maps;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import net.minecraft.server.v1_7_R4.MobEffect;
/*    */ import net.minecraft.server.v1_7_R4.PotionBrewer;
/*    */ import org.bukkit.potion.PotionBrewer;
/*    */ import org.bukkit.potion.PotionEffect;
/*    */ import org.bukkit.potion.PotionEffectType;
/*    */ 
/*    */ public class CraftPotionBrewer
/*    */   implements PotionBrewer
/*    */ {
/* 17 */   private static final Map<Integer, Collection<PotionEffect>> cache = Maps.newHashMap();
/*    */   
/*    */   public Collection<PotionEffect> getEffectsFromDamage(int damage) {
/* 20 */     if (cache.containsKey(Integer.valueOf(damage))) {
/* 21 */       return cache.get(Integer.valueOf(damage));
/*    */     }
/* 23 */     List<?> mcEffects = PotionBrewer.getEffects(damage, false);
/* 24 */     List<PotionEffect> effects = new ArrayList<PotionEffect>();
/* 25 */     if (mcEffects == null) {
/* 26 */       return effects;
/*    */     }
/* 28 */     for (Object raw : mcEffects) {
/* 29 */       if (raw == null || !(raw instanceof MobEffect))
/*    */         continue; 
/* 31 */       MobEffect mcEffect = (MobEffect)raw;
/* 32 */       PotionEffect effect = new PotionEffect(PotionEffectType.getById(mcEffect.getEffectId()), mcEffect.getDuration(), mcEffect.getAmplifier());
/*    */ 
/*    */       
/* 35 */       effects.add(effect);
/*    */     } 
/*    */     
/* 38 */     cache.put(Integer.valueOf(damage), effects);
/*    */     
/* 40 */     return effects;
/*    */   }
/*    */   
/*    */   public PotionEffect createEffect(PotionEffectType potion, int duration, int amplifier) {
/* 44 */     return new PotionEffect(potion, potion.isInstant() ? 1 : (int)(duration * potion.getDurationModifier()), amplifier);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\potion\CraftPotionBrewer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */