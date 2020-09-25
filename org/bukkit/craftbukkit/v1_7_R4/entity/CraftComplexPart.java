/*    */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*    */ import net.minecraft.server.v1_7_R4.Entity;
/*    */ import net.minecraft.server.v1_7_R4.EntityComplexPart;
/*    */ import net.minecraft.server.v1_7_R4.EntityEnderDragon;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*    */ import org.bukkit.entity.ComplexEntityPart;
/*    */ import org.bukkit.entity.ComplexLivingEntity;
/*    */ import org.bukkit.entity.EntityType;
/*    */ import org.bukkit.event.entity.EntityDamageEvent;
/*    */ 
/*    */ public class CraftComplexPart extends CraftEntity implements ComplexEntityPart {
/*    */   public CraftComplexPart(CraftServer server, EntityComplexPart entity) {
/* 13 */     super(server, (Entity)entity);
/*    */   }
/*    */   
/*    */   public ComplexLivingEntity getParent() {
/* 17 */     return (ComplexLivingEntity)((EntityEnderDragon)(getHandle()).owner).getBukkitEntity();
/*    */   }
/*    */ 
/*    */   
/*    */   public void setLastDamageCause(EntityDamageEvent cause) {
/* 22 */     getParent().setLastDamageCause(cause);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityDamageEvent getLastDamageCause() {
/* 27 */     return getParent().getLastDamageCause();
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityComplexPart getHandle() {
/* 32 */     return (EntityComplexPart)this.entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 37 */     return "CraftComplexPart";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 41 */     return EntityType.COMPLEX_PART;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftComplexPart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */