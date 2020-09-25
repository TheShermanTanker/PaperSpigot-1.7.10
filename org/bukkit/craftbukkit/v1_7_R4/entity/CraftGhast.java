/*    */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*    */ import net.minecraft.server.v1_7_R4.Entity;
/*    */ import net.minecraft.server.v1_7_R4.EntityFlying;
/*    */ import net.minecraft.server.v1_7_R4.EntityGhast;
/*    */ import net.minecraft.server.v1_7_R4.EntityLiving;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*    */ import org.bukkit.entity.EntityType;
/*    */ import org.bukkit.entity.Ghast;
/*    */ 
/*    */ public class CraftGhast extends CraftFlying implements Ghast {
/*    */   public CraftGhast(CraftServer server, EntityGhast entity) {
/* 12 */     super(server, (EntityFlying)entity);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityGhast getHandle() {
/* 17 */     return (EntityGhast)this.entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 22 */     return "CraftGhast";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 26 */     return EntityType.GHAST;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftGhast.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */