/*    */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*    */ import net.minecraft.server.v1_7_R4.Entity;
/*    */ import net.minecraft.server.v1_7_R4.EntityAmbient;
/*    */ import net.minecraft.server.v1_7_R4.EntityLiving;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*    */ import org.bukkit.entity.EntityType;
/*    */ 
/*    */ public class CraftAmbient extends CraftLivingEntity implements Ambient {
/*    */   public CraftAmbient(CraftServer server, EntityAmbient entity) {
/* 10 */     super(server, (EntityLiving)entity);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityAmbient getHandle() {
/* 15 */     return (EntityAmbient)this.entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 20 */     return "CraftAmbient";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 24 */     return EntityType.UNKNOWN;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftAmbient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */