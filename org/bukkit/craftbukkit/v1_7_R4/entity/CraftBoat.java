/*    */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_7_R4.Entity;
/*    */ import net.minecraft.server.v1_7_R4.EntityBoat;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*    */ import org.bukkit.entity.Boat;
/*    */ import org.bukkit.entity.EntityType;
/*    */ 
/*    */ public class CraftBoat extends CraftVehicle implements Boat {
/*    */   public CraftBoat(CraftServer server, EntityBoat entity) {
/* 11 */     super(server, (Entity)entity);
/*    */   }
/*    */   
/*    */   public double getMaxSpeed() {
/* 15 */     return (getHandle()).maxSpeed;
/*    */   }
/*    */   
/*    */   public void setMaxSpeed(double speed) {
/* 19 */     if (speed >= 0.0D) {
/* 20 */       (getHandle()).maxSpeed = speed;
/*    */     }
/*    */   }
/*    */   
/*    */   public double getOccupiedDeceleration() {
/* 25 */     return (getHandle()).occupiedDeceleration;
/*    */   }
/*    */   
/*    */   public void setOccupiedDeceleration(double speed) {
/* 29 */     if (speed >= 0.0D) {
/* 30 */       (getHandle()).occupiedDeceleration = speed;
/*    */     }
/*    */   }
/*    */   
/*    */   public double getUnoccupiedDeceleration() {
/* 35 */     return (getHandle()).unoccupiedDeceleration;
/*    */   }
/*    */   
/*    */   public void setUnoccupiedDeceleration(double speed) {
/* 39 */     (getHandle()).unoccupiedDeceleration = speed;
/*    */   }
/*    */   
/*    */   public boolean getWorkOnLand() {
/* 43 */     return (getHandle()).landBoats;
/*    */   }
/*    */   
/*    */   public void setWorkOnLand(boolean workOnLand) {
/* 47 */     (getHandle()).landBoats = workOnLand;
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityBoat getHandle() {
/* 52 */     return (EntityBoat)this.entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 57 */     return "CraftBoat";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 61 */     return EntityType.BOAT;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftBoat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */