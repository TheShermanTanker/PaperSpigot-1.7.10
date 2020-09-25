/*    */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*    */ import net.minecraft.server.v1_7_R4.Entity;
/*    */ import net.minecraft.server.v1_7_R4.EntityCreature;
/*    */ import net.minecraft.server.v1_7_R4.EntityLiving;
/*    */ import net.minecraft.server.v1_7_R4.EntityMonster;
/*    */ import net.minecraft.server.v1_7_R4.EntityZombie;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*    */ import org.bukkit.entity.EntityType;
/*    */ 
/*    */ public class CraftZombie extends CraftMonster implements Zombie {
/*    */   public CraftZombie(CraftServer server, EntityZombie entity) {
/* 12 */     super(server, (EntityMonster)entity);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityZombie getHandle() {
/* 17 */     return (EntityZombie)this.entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 22 */     return "CraftZombie";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 26 */     return EntityType.ZOMBIE;
/*    */   }
/*    */   
/*    */   public boolean isBaby() {
/* 30 */     return getHandle().isBaby();
/*    */   }
/*    */   
/*    */   public void setBaby(boolean flag) {
/* 34 */     getHandle().setBaby(flag);
/*    */   }
/*    */   
/*    */   public boolean isVillager() {
/* 38 */     return getHandle().isVillager();
/*    */   }
/*    */   
/*    */   public void setVillager(boolean flag) {
/* 42 */     getHandle().setVillager(flag);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftZombie.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */