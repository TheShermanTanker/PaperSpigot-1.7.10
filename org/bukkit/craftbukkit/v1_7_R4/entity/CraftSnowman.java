/*    */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*    */ import net.minecraft.server.v1_7_R4.EntityCreature;
/*    */ import net.minecraft.server.v1_7_R4.EntityGolem;
/*    */ import net.minecraft.server.v1_7_R4.EntityLiving;
/*    */ import net.minecraft.server.v1_7_R4.EntitySnowman;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*    */ 
/*    */ public class CraftSnowman extends CraftGolem implements Snowman {
/*    */   public CraftSnowman(CraftServer server, EntitySnowman entity) {
/* 10 */     super(server, (EntityGolem)entity);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntitySnowman getHandle() {
/* 15 */     return (EntitySnowman)this.entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 20 */     return "CraftSnowman";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 24 */     return EntityType.SNOWMAN;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftSnowman.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */