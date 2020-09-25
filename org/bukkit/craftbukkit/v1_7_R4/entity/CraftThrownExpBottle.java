/*    */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*    */ import net.minecraft.server.v1_7_R4.Entity;
/*    */ import net.minecraft.server.v1_7_R4.EntityProjectile;
/*    */ import net.minecraft.server.v1_7_R4.EntityThrownExpBottle;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*    */ import org.bukkit.entity.EntityType;
/*    */ 
/*    */ public class CraftThrownExpBottle extends CraftProjectile implements ThrownExpBottle {
/*    */   public CraftThrownExpBottle(CraftServer server, EntityThrownExpBottle entity) {
/* 10 */     super(server, (Entity)entity);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityThrownExpBottle getHandle() {
/* 15 */     return (EntityThrownExpBottle)this.entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 20 */     return "EntityThrownExpBottle";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 24 */     return EntityType.THROWN_EXP_BOTTLE;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftThrownExpBottle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */