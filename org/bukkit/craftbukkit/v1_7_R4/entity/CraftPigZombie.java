/*    */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*    */ import net.minecraft.server.v1_7_R4.Entity;
/*    */ import net.minecraft.server.v1_7_R4.EntityCreature;
/*    */ import net.minecraft.server.v1_7_R4.EntityLiving;
/*    */ import net.minecraft.server.v1_7_R4.EntityPigZombie;
/*    */ import net.minecraft.server.v1_7_R4.EntityZombie;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*    */ import org.bukkit.entity.EntityType;
/*    */ 
/*    */ public class CraftPigZombie extends CraftZombie implements PigZombie {
/*    */   public CraftPigZombie(CraftServer server, EntityPigZombie entity) {
/* 12 */     super(server, (EntityZombie)entity);
/*    */   }
/*    */   
/*    */   public int getAnger() {
/* 16 */     return (getHandle()).angerLevel;
/*    */   }
/*    */   
/*    */   public void setAnger(int level) {
/* 20 */     (getHandle()).angerLevel = level;
/*    */   }
/*    */   
/*    */   public void setAngry(boolean angry) {
/* 24 */     setAnger(angry ? 400 : 0);
/*    */   }
/*    */   
/*    */   public boolean isAngry() {
/* 28 */     return (getAnger() > 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityPigZombie getHandle() {
/* 33 */     return (EntityPigZombie)this.entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 38 */     return "CraftPigZombie";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 42 */     return EntityType.PIG_ZOMBIE;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftPigZombie.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */