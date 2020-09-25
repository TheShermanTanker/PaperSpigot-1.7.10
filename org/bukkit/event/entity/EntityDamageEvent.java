/*     */ package org.bukkit.event.entity;
/*     */ 
/*     */ import com.google.common.base.Function;
/*     */ import com.google.common.base.Functions;
/*     */ import com.google.common.collect.ImmutableMap;
/*     */ import java.util.EnumMap;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.event.Cancellable;
/*     */ import org.bukkit.event.HandlerList;
/*     */ import org.bukkit.util.NumberConversions;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityDamageEvent
/*     */   extends EntityEvent
/*     */   implements Cancellable
/*     */ {
/*  21 */   private static final HandlerList handlers = new HandlerList();
/*  22 */   private static final DamageModifier[] MODIFIERS = DamageModifier.values();
/*  23 */   private static final Function<? super Double, Double> ZERO = Functions.constant(Double.valueOf(-0.0D));
/*     */   private final Map<DamageModifier, Double> modifiers;
/*     */   private final Map<DamageModifier, ? extends Function<? super Double, Double>> modifierFunctions;
/*     */   private final Map<DamageModifier, Double> originals;
/*     */   private boolean cancelled;
/*     */   private final DamageCause cause;
/*     */   
/*     */   @Deprecated
/*     */   public EntityDamageEvent(Entity damagee, DamageCause cause, int damage) {
/*  32 */     this(damagee, cause, damage);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public EntityDamageEvent(Entity damagee, DamageCause cause, double damage) {
/*  37 */     this(damagee, cause, new EnumMap<DamageModifier, Double>((Map<DamageModifier, ? extends Double>)ImmutableMap.of(DamageModifier.BASE, Double.valueOf(damage))), new EnumMap<DamageModifier, Function<? super Double, Double>>((Map<DamageModifier, ? extends Function<? super Double, Double>>)ImmutableMap.of(DamageModifier.BASE, ZERO)));
/*     */   }
/*     */   
/*     */   public EntityDamageEvent(Entity damagee, DamageCause cause, Map<DamageModifier, Double> modifiers, Map<DamageModifier, ? extends Function<? super Double, Double>> modifierFunctions) {
/*  41 */     super(damagee);
/*  42 */     Validate.isTrue(modifiers.containsKey(DamageModifier.BASE), "BASE DamageModifier missing");
/*  43 */     Validate.isTrue(!modifiers.containsKey(null), "Cannot have null DamageModifier");
/*  44 */     Validate.noNullElements(modifiers.values(), "Cannot have null modifier values");
/*  45 */     Validate.isTrue(modifiers.keySet().equals(modifierFunctions.keySet()), "Must have a modifier function for each DamageModifier");
/*  46 */     Validate.noNullElements(modifierFunctions.values(), "Cannot have null modifier function");
/*  47 */     this.originals = new EnumMap<DamageModifier, Double>(modifiers);
/*  48 */     this.cause = cause;
/*  49 */     this.modifiers = modifiers;
/*  50 */     this.modifierFunctions = modifierFunctions;
/*     */   }
/*     */   
/*     */   public boolean isCancelled() {
/*  54 */     return this.cancelled;
/*     */   }
/*     */   
/*     */   public void setCancelled(boolean cancel) {
/*  58 */     this.cancelled = cancel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getOriginalDamage(DamageModifier type) throws IllegalArgumentException {
/*  69 */     Double damage = this.originals.get(type);
/*  70 */     if (damage != null) {
/*  71 */       return damage.doubleValue();
/*     */     }
/*  73 */     if (type == null) {
/*  74 */       throw new IllegalArgumentException("Cannot have null DamageModifier");
/*     */     }
/*  76 */     return 0.0D;
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
/*     */   public void setDamage(DamageModifier type, double damage) throws IllegalArgumentException, UnsupportedOperationException {
/*  90 */     if (!this.modifiers.containsKey(type)) {
/*  91 */       throw (type == null) ? new IllegalArgumentException("Cannot have null DamageModifier") : new UnsupportedOperationException(type + " is not applicable to " + getEntity());
/*     */     }
/*  93 */     this.modifiers.put(type, Double.valueOf(damage));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDamage(DamageModifier type) throws IllegalArgumentException {
/* 104 */     Validate.notNull(type, "Cannot have null DamageModifier");
/* 105 */     Double damage = this.modifiers.get(type);
/* 106 */     return (damage == null) ? 0.0D : damage.doubleValue();
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
/*     */   public boolean isApplicable(DamageModifier type) throws IllegalArgumentException {
/* 121 */     Validate.notNull(type, "Cannot have null DamageModifier");
/* 122 */     return this.modifiers.containsKey(type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getDamage() {
/* 132 */     return getDamage(DamageModifier.BASE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final double getFinalDamage() {
/* 142 */     double damage = 0.0D;
/* 143 */     for (DamageModifier modifier : MODIFIERS) {
/* 144 */       damage += getDamage(modifier);
/*     */     }
/* 146 */     return damage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public int getDamage() {
/* 156 */     return NumberConversions.ceil(getDamage());
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
/*     */   public void setDamage(double damage) {
/* 170 */     double remaining = damage;
/* 171 */     double oldRemaining = getDamage(DamageModifier.BASE);
/* 172 */     for (DamageModifier modifier : MODIFIERS) {
/* 173 */       if (isApplicable(modifier)) {
/*     */ 
/*     */ 
/*     */         
/* 177 */         Function<? super Double, Double> modifierFunction = this.modifierFunctions.get(modifier);
/* 178 */         double newVanilla = ((Double)modifierFunction.apply(Double.valueOf(remaining))).doubleValue();
/* 179 */         double oldVanilla = ((Double)modifierFunction.apply(Double.valueOf(oldRemaining))).doubleValue();
/* 180 */         double difference = oldVanilla - newVanilla;
/*     */ 
/*     */         
/* 183 */         double old = getDamage(modifier);
/* 184 */         if (old > 0.0D) {
/* 185 */           setDamage(modifier, Math.max(0.0D, old - difference));
/*     */         } else {
/* 187 */           setDamage(modifier, Math.min(0.0D, old - difference));
/*     */         } 
/* 189 */         remaining += newVanilla;
/* 190 */         oldRemaining += oldVanilla;
/*     */       } 
/*     */     } 
/* 193 */     setDamage(DamageModifier.BASE, damage);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void setDamage(int damage) {
/* 203 */     setDamage(damage);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DamageCause getCause() {
/* 212 */     return this.cause;
/*     */   }
/*     */ 
/*     */   
/*     */   public HandlerList getHandlers() {
/* 217 */     return handlers;
/*     */   }
/*     */   
/*     */   public static HandlerList getHandlerList() {
/* 221 */     return handlers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum DamageModifier
/*     */   {
/* 232 */     BASE,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 237 */     HARD_HAT,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 242 */     BLOCKING,
/*     */ 
/*     */ 
/*     */     
/* 246 */     ARMOR,
/*     */ 
/*     */ 
/*     */     
/* 250 */     RESISTANCE,
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
/* 261 */     MAGIC,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 266 */     ABSORPTION;
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
/*     */   public enum DamageCause
/*     */   {
/* 280 */     CONTACT,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 286 */     ENTITY_ATTACK,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 292 */     PROJECTILE,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 298 */     SUFFOCATION,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 304 */     FALL,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 310 */     FIRE,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 316 */     FIRE_TICK,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 322 */     MELTING,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 328 */     LAVA,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 334 */     DROWNING,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 340 */     BLOCK_EXPLOSION,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 347 */     ENTITY_EXPLOSION,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 353 */     VOID,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 359 */     LIGHTNING,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 365 */     SUICIDE,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 371 */     STARVATION,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 377 */     POISON,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 383 */     MAGIC,
/*     */ 
/*     */ 
/*     */     
/* 387 */     WITHER,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 395 */     FALLING_BLOCK,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 402 */     THORNS,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 408 */     CUSTOM;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\entity\EntityDamageEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */