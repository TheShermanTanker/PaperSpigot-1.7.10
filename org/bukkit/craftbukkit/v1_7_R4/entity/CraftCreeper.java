/*    */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*    */ import net.minecraft.server.v1_7_R4.EntityCreature;
/*    */ import net.minecraft.server.v1_7_R4.EntityCreeper;
/*    */ import net.minecraft.server.v1_7_R4.EntityLiving;
/*    */ import net.minecraft.server.v1_7_R4.EntityMonster;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*    */ import org.bukkit.entity.Creeper;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.entity.CreeperPowerEvent;
/*    */ 
/*    */ public class CraftCreeper extends CraftMonster implements Creeper {
/*    */   public CraftCreeper(CraftServer server, EntityCreeper entity) {
/* 13 */     super(server, (EntityMonster)entity);
/*    */   }
/*    */   
/*    */   public boolean isPowered() {
/* 17 */     return getHandle().isPowered();
/*    */   }
/*    */   
/*    */   public void setPowered(boolean powered) {
/* 21 */     CraftServer server = this.server;
/* 22 */     Creeper entity = (Creeper)getHandle().getBukkitEntity();
/*    */     
/* 24 */     if (powered) {
/* 25 */       CreeperPowerEvent event = new CreeperPowerEvent(entity, CreeperPowerEvent.PowerCause.SET_ON);
/* 26 */       server.getPluginManager().callEvent((Event)event);
/*    */       
/* 28 */       if (!event.isCancelled()) {
/* 29 */         getHandle().setPowered(true);
/*    */       }
/*    */     } else {
/* 32 */       CreeperPowerEvent event = new CreeperPowerEvent(entity, CreeperPowerEvent.PowerCause.SET_OFF);
/* 33 */       server.getPluginManager().callEvent((Event)event);
/*    */       
/* 35 */       if (!event.isCancelled()) {
/* 36 */         getHandle().setPowered(false);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityCreeper getHandle() {
/* 43 */     return (EntityCreeper)this.entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 48 */     return "CraftCreeper";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 52 */     return EntityType.CREEPER;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftCreeper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */