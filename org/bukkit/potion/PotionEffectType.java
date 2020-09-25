/*     */ package org.bukkit.potion;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.lang.Validate;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class PotionEffectType
/*     */ {
/*  15 */   public static final PotionEffectType SPEED = new PotionEffectTypeWrapper(1);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  20 */   public static final PotionEffectType SLOW = new PotionEffectTypeWrapper(2);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  25 */   public static final PotionEffectType FAST_DIGGING = new PotionEffectTypeWrapper(3);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  30 */   public static final PotionEffectType SLOW_DIGGING = new PotionEffectTypeWrapper(4);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  35 */   public static final PotionEffectType INCREASE_DAMAGE = new PotionEffectTypeWrapper(5);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  40 */   public static final PotionEffectType HEAL = new PotionEffectTypeWrapper(6);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  45 */   public static final PotionEffectType HARM = new PotionEffectTypeWrapper(7);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  50 */   public static final PotionEffectType JUMP = new PotionEffectTypeWrapper(8);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  55 */   public static final PotionEffectType CONFUSION = new PotionEffectTypeWrapper(9);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  60 */   public static final PotionEffectType REGENERATION = new PotionEffectTypeWrapper(10);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  65 */   public static final PotionEffectType DAMAGE_RESISTANCE = new PotionEffectTypeWrapper(11);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  70 */   public static final PotionEffectType FIRE_RESISTANCE = new PotionEffectTypeWrapper(12);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  75 */   public static final PotionEffectType WATER_BREATHING = new PotionEffectTypeWrapper(13);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   public static final PotionEffectType INVISIBILITY = new PotionEffectTypeWrapper(14);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  85 */   public static final PotionEffectType BLINDNESS = new PotionEffectTypeWrapper(15);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  90 */   public static final PotionEffectType NIGHT_VISION = new PotionEffectTypeWrapper(16);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  95 */   public static final PotionEffectType HUNGER = new PotionEffectTypeWrapper(17);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 100 */   public static final PotionEffectType WEAKNESS = new PotionEffectTypeWrapper(18);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 105 */   public static final PotionEffectType POISON = new PotionEffectTypeWrapper(19);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 111 */   public static final PotionEffectType WITHER = new PotionEffectTypeWrapper(20);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 116 */   public static final PotionEffectType HEALTH_BOOST = new PotionEffectTypeWrapper(21);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 122 */   public static final PotionEffectType ABSORPTION = new PotionEffectTypeWrapper(22);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 127 */   public static final PotionEffectType SATURATION = new PotionEffectTypeWrapper(23);
/*     */   
/*     */   private final int id;
/*     */   
/*     */   protected PotionEffectType(int id) {
/* 132 */     this.id = id;
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
/*     */   public PotionEffect createEffect(int duration, int amplifier) {
/* 145 */     return Potion.getBrewer().createEffect(this, duration, amplifier);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract double getDurationModifier();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public int getId() {
/* 163 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract String getName();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract boolean isInstant();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object obj) {
/* 182 */     if (obj == null) {
/* 183 */       return false;
/*     */     }
/* 185 */     if (!(obj instanceof PotionEffectType)) {
/* 186 */       return false;
/*     */     }
/* 188 */     PotionEffectType other = (PotionEffectType)obj;
/* 189 */     if (this.id != other.id) {
/* 190 */       return false;
/*     */     }
/* 192 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 197 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 202 */     return "PotionEffectType[" + this.id + ", " + getName() + "]";
/*     */   }
/*     */   
/* 205 */   private static final PotionEffectType[] byId = new PotionEffectType[24];
/* 206 */   private static final Map<String, PotionEffectType> byName = new HashMap<String, PotionEffectType>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static boolean acceptingNew = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static PotionEffectType getById(int id) {
/* 219 */     if (id >= byId.length || id < 0)
/* 220 */       return null; 
/* 221 */     return byId[id];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PotionEffectType getByName(String name) {
/* 231 */     Validate.notNull(name, "name cannot be null");
/* 232 */     return byName.get(name.toLowerCase());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerPotionEffectType(PotionEffectType type) {
/* 243 */     if (byId[type.id] != null || byName.containsKey(type.getName().toLowerCase()))
/* 244 */       throw new IllegalArgumentException("Cannot set already-set type"); 
/* 245 */     if (!acceptingNew) {
/* 246 */       throw new IllegalStateException("No longer accepting new potion effect types (can only be done by the server implementation)");
/*     */     }
/*     */ 
/*     */     
/* 250 */     byId[type.id] = type;
/* 251 */     byName.put(type.getName().toLowerCase(), type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void stopAcceptingRegistrations() {
/* 258 */     acceptingNew = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PotionEffectType[] values() {
/* 267 */     return (PotionEffectType[])byId.clone();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\potion\PotionEffectType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */