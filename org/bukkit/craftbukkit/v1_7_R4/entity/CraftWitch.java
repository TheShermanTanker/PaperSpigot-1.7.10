/*    */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*    */ import net.minecraft.server.v1_7_R4.EntityCreature;
/*    */ import net.minecraft.server.v1_7_R4.EntityLiving;
/*    */ import net.minecraft.server.v1_7_R4.EntityMonster;
/*    */ import net.minecraft.server.v1_7_R4.EntityWitch;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*    */ 
/*    */ public class CraftWitch extends CraftMonster implements Witch {
/*    */   public CraftWitch(CraftServer server, EntityWitch entity) {
/* 10 */     super(server, (EntityMonster)entity);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityWitch getHandle() {
/* 15 */     return (EntityWitch)this.entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 20 */     return "CraftWitch";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 24 */     return EntityType.WITCH;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftWitch.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */