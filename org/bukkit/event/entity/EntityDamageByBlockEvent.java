/*    */ package org.bukkit.event.entity;
/*    */ 
/*    */ import com.google.common.base.Function;
/*    */ import java.util.Map;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.entity.Entity;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityDamageByBlockEvent
/*    */   extends EntityDamageEvent
/*    */ {
/*    */   private final Block damager;
/*    */   
/*    */   @Deprecated
/*    */   public EntityDamageByBlockEvent(Block damager, Entity damagee, EntityDamageEvent.DamageCause cause, int damage) {
/* 17 */     this(damager, damagee, cause, damage);
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public EntityDamageByBlockEvent(Block damager, Entity damagee, EntityDamageEvent.DamageCause cause, double damage) {
/* 22 */     super(damagee, cause, damage);
/* 23 */     this.damager = damager;
/*    */   }
/*    */   
/*    */   public EntityDamageByBlockEvent(Block damager, Entity damagee, EntityDamageEvent.DamageCause cause, Map<EntityDamageEvent.DamageModifier, Double> modifiers, Map<EntityDamageEvent.DamageModifier, ? extends Function<? super Double, Double>> modifierFunctions) {
/* 27 */     super(damagee, cause, modifiers, modifierFunctions);
/* 28 */     this.damager = damager;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Block getDamager() {
/* 37 */     return this.damager;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\entity\EntityDamageByBlockEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */