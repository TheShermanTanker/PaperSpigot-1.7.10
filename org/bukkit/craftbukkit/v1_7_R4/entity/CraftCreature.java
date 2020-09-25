/*    */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*    */ import net.minecraft.server.v1_7_R4.Entity;
/*    */ import net.minecraft.server.v1_7_R4.EntityCreature;
/*    */ import net.minecraft.server.v1_7_R4.EntityLiving;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*    */ import org.bukkit.entity.Creature;
/*    */ import org.bukkit.entity.LivingEntity;
/*    */ 
/*    */ public class CraftCreature extends CraftLivingEntity implements Creature {
/*    */   public CraftCreature(CraftServer server, EntityCreature entity) {
/* 11 */     super(server, (EntityLiving)entity);
/*    */   }
/*    */   
/*    */   public void setTarget(LivingEntity target) {
/* 15 */     EntityCreature entity = getHandle();
/* 16 */     if (target == null) {
/* 17 */       entity.target = null;
/* 18 */       entity.setGoalTarget(null);
/* 19 */     } else if (target instanceof CraftLivingEntity) {
/* 20 */       entity.target = (Entity)((CraftLivingEntity)target).getHandle();
/* 21 */       entity.pathEntity = entity.world.findPath((Entity)entity, entity.target, 16.0F, true, false, false, true);
/* 22 */       entity.setGoalTarget(((CraftLivingEntity)target).getHandle());
/*    */     } 
/*    */   }
/*    */   
/*    */   public CraftLivingEntity getTarget() {
/* 27 */     if ((getHandle()).target == null) return null; 
/* 28 */     if (!((getHandle()).target instanceof EntityLiving)) return null;
/*    */     
/* 30 */     return (CraftLivingEntity)(getHandle()).target.getBukkitEntity();
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityCreature getHandle() {
/* 35 */     return (EntityCreature)this.entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 40 */     return "CraftCreature";
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftCreature.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */