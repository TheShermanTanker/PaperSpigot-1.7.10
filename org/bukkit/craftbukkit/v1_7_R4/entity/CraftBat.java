/*    */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*    */ import net.minecraft.server.v1_7_R4.EntityAmbient;
/*    */ import net.minecraft.server.v1_7_R4.EntityBat;
/*    */ import net.minecraft.server.v1_7_R4.EntityLiving;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*    */ import org.bukkit.entity.EntityType;
/*    */ 
/*    */ public class CraftBat extends CraftAmbient implements Bat {
/*    */   public CraftBat(CraftServer server, EntityBat entity) {
/* 10 */     super(server, (EntityAmbient)entity);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityBat getHandle() {
/* 15 */     return (EntityBat)this.entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 20 */     return "CraftBat";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 24 */     return EntityType.BAT;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isAwake() {
/* 29 */     return !getHandle().isAsleep();
/*    */   }
/*    */ 
/*    */   
/*    */   public void setAwake(boolean state) {
/* 34 */     getHandle().setAsleep(!state);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftBat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */