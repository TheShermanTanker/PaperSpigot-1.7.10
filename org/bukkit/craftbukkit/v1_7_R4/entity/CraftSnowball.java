/*    */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*    */ import net.minecraft.server.v1_7_R4.Entity;
/*    */ import net.minecraft.server.v1_7_R4.EntityProjectile;
/*    */ import net.minecraft.server.v1_7_R4.EntitySnowball;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*    */ import org.bukkit.entity.EntityType;
/*    */ 
/*    */ public class CraftSnowball extends CraftProjectile implements Snowball {
/*    */   public CraftSnowball(CraftServer server, EntitySnowball entity) {
/* 10 */     super(server, (Entity)entity);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntitySnowball getHandle() {
/* 15 */     return (EntitySnowball)this.entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 20 */     return "CraftSnowball";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 24 */     return EntityType.SNOWBALL;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftSnowball.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */