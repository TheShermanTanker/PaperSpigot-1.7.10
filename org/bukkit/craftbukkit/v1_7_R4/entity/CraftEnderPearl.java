/*    */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*    */ import net.minecraft.server.v1_7_R4.Entity;
/*    */ import net.minecraft.server.v1_7_R4.EntityEnderPearl;
/*    */ import net.minecraft.server.v1_7_R4.EntityProjectile;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*    */ import org.bukkit.entity.EntityType;
/*    */ 
/*    */ public class CraftEnderPearl extends CraftProjectile implements EnderPearl {
/*    */   public CraftEnderPearl(CraftServer server, EntityEnderPearl entity) {
/* 10 */     super(server, (Entity)entity);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityEnderPearl getHandle() {
/* 15 */     return (EntityEnderPearl)this.entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 20 */     return "CraftEnderPearl";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 24 */     return EntityType.ENDER_PEARL;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftEnderPearl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */