/*     */ package org.bukkit.potion;
/*     */ 
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.configuration.serialization.ConfigurationSerializable;
/*     */ import org.bukkit.configuration.serialization.SerializableAs;
/*     */ import org.bukkit.entity.LivingEntity;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @SerializableAs("PotionEffect")
/*     */ public class PotionEffect
/*     */   implements ConfigurationSerializable
/*     */ {
/*     */   private static final String AMPLIFIER = "amplifier";
/*     */   private static final String DURATION = "duration";
/*     */   private static final String TYPE = "effect";
/*     */   private static final String AMBIENT = "ambient";
/*     */   private final int amplifier;
/*     */   private final int duration;
/*     */   private final PotionEffectType type;
/*     */   private final boolean ambient;
/*     */   
/*     */   public PotionEffect(PotionEffectType type, int duration, int amplifier, boolean ambient) {
/*  40 */     Validate.notNull(type, "effect type cannot be null");
/*  41 */     this.type = type;
/*  42 */     this.duration = duration;
/*  43 */     this.amplifier = amplifier;
/*  44 */     this.ambient = ambient;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PotionEffect(PotionEffectType type, int duration, int amplifier) {
/*  56 */     this(type, duration, amplifier, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PotionEffect(Map<String, Object> map) {
/*  65 */     this(getEffectType(map), getInt(map, "duration"), getInt(map, "amplifier"), getBool(map, "ambient"));
/*     */   }
/*     */   
/*     */   private static PotionEffectType getEffectType(Map<?, ?> map) {
/*  69 */     int type = getInt(map, "effect");
/*  70 */     PotionEffectType effect = PotionEffectType.getById(type);
/*  71 */     if (effect != null) {
/*  72 */       return effect;
/*     */     }
/*  74 */     throw new NoSuchElementException(map + " does not contain " + "effect");
/*     */   }
/*     */   
/*     */   private static int getInt(Map<?, ?> map, Object key) {
/*  78 */     Object num = map.get(key);
/*  79 */     if (num instanceof Integer) {
/*  80 */       return ((Integer)num).intValue();
/*     */     }
/*  82 */     throw new NoSuchElementException(map + " does not contain " + key);
/*     */   }
/*     */   
/*     */   private static boolean getBool(Map<?, ?> map, Object key) {
/*  86 */     Object bool = map.get(key);
/*  87 */     if (bool instanceof Boolean) {
/*  88 */       return ((Boolean)bool).booleanValue();
/*     */     }
/*  90 */     throw new NoSuchElementException(map + " does not contain " + key);
/*     */   }
/*     */   
/*     */   public Map<String, Object> serialize() {
/*  94 */     return (Map<String, Object>)ImmutableMap.of("effect", Integer.valueOf(this.type.getId()), "duration", Integer.valueOf(this.duration), "amplifier", Integer.valueOf(this.amplifier), "ambient", Boolean.valueOf(this.ambient));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean apply(LivingEntity entity) {
/* 111 */     return entity.addPotionEffect(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 116 */     if (this == obj) {
/* 117 */       return true;
/*     */     }
/* 119 */     if (!(obj instanceof PotionEffect)) {
/* 120 */       return false;
/*     */     }
/* 122 */     PotionEffect that = (PotionEffect)obj;
/* 123 */     return (this.type.equals(that.type) && this.ambient == that.ambient && this.amplifier == that.amplifier && this.duration == that.duration);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAmplifier() {
/* 134 */     return this.amplifier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDuration() {
/* 144 */     return this.duration;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PotionEffectType getType() {
/* 153 */     return this.type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAmbient() {
/* 162 */     return this.ambient;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 167 */     int hash = 1;
/* 168 */     hash = hash * 31 + this.type.hashCode();
/* 169 */     hash = hash * 31 + this.amplifier;
/* 170 */     hash = hash * 31 + this.duration;
/* 171 */     hash ^= 572662306 >> (this.ambient ? 1 : -1);
/* 172 */     return hash;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 177 */     return this.type.getName() + (this.ambient ? ":(" : ":") + this.duration + "t-x" + this.amplifier + (this.ambient ? ")" : "");
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\potion\PotionEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */