/*    */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*    */ import net.minecraft.server.v1_7_R4.Entity;
/*    */ import net.minecraft.server.v1_7_R4.EntityFireball;
/*    */ import net.minecraft.server.v1_7_R4.EntityLargeFireball;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*    */ import org.bukkit.entity.EntityType;
/*    */ 
/*    */ public class CraftLargeFireball extends CraftFireball implements LargeFireball {
/*    */   public CraftLargeFireball(CraftServer server, EntityLargeFireball entity) {
/* 10 */     super(server, (EntityFireball)entity);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setYield(float yield) {
/* 15 */     super.setYield(yield);
/* 16 */     (getHandle()).yield = (int)yield;
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityLargeFireball getHandle() {
/* 21 */     return (EntityLargeFireball)this.entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 26 */     return "CraftLargeFireball";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 30 */     return EntityType.FIREBALL;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftLargeFireball.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */