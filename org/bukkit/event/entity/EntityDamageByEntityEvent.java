/*    */ package org.bukkit.event.entity;
/*    */ 
/*    */ import com.google.common.base.Function;
/*    */ import java.util.Map;
/*    */ import org.bukkit.entity.Entity;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityDamageByEntityEvent
/*    */   extends EntityDamageEvent
/*    */ {
/*    */   private final Entity damager;
/*    */   
/*    */   @Deprecated
/*    */   public EntityDamageByEntityEvent(Entity damager, Entity damagee, EntityDamageEvent.DamageCause cause, int damage) {
/* 16 */     this(damager, damagee, cause, damage);
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public EntityDamageByEntityEvent(Entity damager, Entity damagee, EntityDamageEvent.DamageCause cause, double damage) {
/* 21 */     super(damagee, cause, damage);
/* 22 */     this.damager = damager;
/*    */   }
/*    */   
/*    */   public EntityDamageByEntityEvent(Entity damager, Entity damagee, EntityDamageEvent.DamageCause cause, Map<EntityDamageEvent.DamageModifier, Double> modifiers, Map<EntityDamageEvent.DamageModifier, ? extends Function<? super Double, Double>> modifierFunctions) {
/* 26 */     super(damagee, cause, modifiers, modifierFunctions);
/* 27 */     this.damager = damager;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Entity getDamager() {
/* 36 */     return this.damager;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\entity\EntityDamageByEntityEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */