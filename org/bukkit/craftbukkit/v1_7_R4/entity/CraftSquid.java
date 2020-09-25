/*    */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*    */ import net.minecraft.server.v1_7_R4.Entity;
/*    */ import net.minecraft.server.v1_7_R4.EntityCreature;
/*    */ import net.minecraft.server.v1_7_R4.EntityLiving;
/*    */ import net.minecraft.server.v1_7_R4.EntitySquid;
/*    */ import net.minecraft.server.v1_7_R4.EntityWaterAnimal;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*    */ import org.bukkit.entity.EntityType;
/*    */ 
/*    */ public class CraftSquid extends CraftWaterMob implements Squid {
/*    */   public CraftSquid(CraftServer server, EntitySquid entity) {
/* 12 */     super(server, (EntityWaterAnimal)entity);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntitySquid getHandle() {
/* 17 */     return (EntitySquid)this.entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 22 */     return "CraftSquid";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 26 */     return EntityType.SQUID;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftSquid.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */