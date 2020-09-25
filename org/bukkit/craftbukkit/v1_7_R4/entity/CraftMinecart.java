/*    */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_7_R4.Entity;
/*    */ import net.minecraft.server.v1_7_R4.EntityMinecartAbstract;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*    */ import org.bukkit.entity.Minecart;
/*    */ import org.bukkit.util.NumberConversions;
/*    */ import org.bukkit.util.Vector;
/*    */ 
/*    */ public abstract class CraftMinecart extends CraftVehicle implements Minecart {
/*    */   public CraftMinecart(CraftServer server, EntityMinecartAbstract entity) {
/* 12 */     super(server, (Entity)entity);
/*    */   }
/*    */   
/*    */   public void setDamage(double damage) {
/* 16 */     getHandle().setDamage((float)damage);
/*    */   }
/*    */   
/*    */   public double getDamage() {
/* 20 */     return getHandle().getDamage();
/*    */   }
/*    */   
/*    */   public double getMaxSpeed() {
/* 24 */     return (getHandle()).maxSpeed;
/*    */   }
/*    */   
/*    */   public void setMaxSpeed(double speed) {
/* 28 */     if (speed >= 0.0D) {
/* 29 */       (getHandle()).maxSpeed = speed;
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean isSlowWhenEmpty() {
/* 34 */     return (getHandle()).slowWhenEmpty;
/*    */   }
/*    */   
/*    */   public void setSlowWhenEmpty(boolean slow) {
/* 38 */     (getHandle()).slowWhenEmpty = slow;
/*    */   }
/*    */   
/*    */   public Vector getFlyingVelocityMod() {
/* 42 */     return getHandle().getFlyingVelocityMod();
/*    */   }
/*    */   
/*    */   public void setFlyingVelocityMod(Vector flying) {
/* 46 */     getHandle().setFlyingVelocityMod(flying);
/*    */   }
/*    */   
/*    */   public Vector getDerailedVelocityMod() {
/* 50 */     return getHandle().getDerailedVelocityMod();
/*    */   }
/*    */   
/*    */   public void setDerailedVelocityMod(Vector derailed) {
/* 54 */     getHandle().setDerailedVelocityMod(derailed);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityMinecartAbstract getHandle() {
/* 59 */     return (EntityMinecartAbstract)this.entity;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftMinecart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */