/*    */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*    */ import net.minecraft.server.v1_7_R4.EntityAnimal;
/*    */ import net.minecraft.server.v1_7_R4.EntityCreature;
/*    */ import net.minecraft.server.v1_7_R4.EntityLiving;
/*    */ import net.minecraft.server.v1_7_R4.EntityPig;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*    */ import org.bukkit.entity.EntityType;
/*    */ 
/*    */ public class CraftPig extends CraftAnimals implements Pig {
/*    */   public CraftPig(CraftServer server, EntityPig entity) {
/* 11 */     super(server, (EntityAnimal)entity);
/*    */   }
/*    */   
/*    */   public boolean hasSaddle() {
/* 15 */     return getHandle().hasSaddle();
/*    */   }
/*    */   
/*    */   public void setSaddle(boolean saddled) {
/* 19 */     getHandle().setSaddle(saddled);
/*    */   }
/*    */   
/*    */   public EntityPig getHandle() {
/* 23 */     return (EntityPig)this.entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 28 */     return "CraftPig";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 32 */     return EntityType.PIG;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftPig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */