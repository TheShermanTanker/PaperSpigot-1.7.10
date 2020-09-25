/*    */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*    */ import net.minecraft.server.v1_7_R4.Entity;
/*    */ import net.minecraft.server.v1_7_R4.EntityCreature;
/*    */ import net.minecraft.server.v1_7_R4.EntityLiving;
/*    */ import net.minecraft.server.v1_7_R4.EntityMonster;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*    */ import org.bukkit.entity.Monster;
/*    */ 
/*    */ public class CraftMonster extends CraftCreature implements Monster {
/*    */   public CraftMonster(CraftServer server, EntityMonster entity) {
/* 11 */     super(server, (EntityCreature)entity);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityMonster getHandle() {
/* 16 */     return (EntityMonster)this.entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 21 */     return "CraftMonster";
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftMonster.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */