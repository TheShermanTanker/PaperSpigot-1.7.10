/*    */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*    */ import net.minecraft.server.v1_7_R4.EntityAgeable;
/*    */ import net.minecraft.server.v1_7_R4.EntityAnimal;
/*    */ import net.minecraft.server.v1_7_R4.EntityChicken;
/*    */ import net.minecraft.server.v1_7_R4.EntityCreature;
/*    */ import net.minecraft.server.v1_7_R4.EntityLiving;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*    */ import org.bukkit.entity.EntityType;
/*    */ 
/*    */ public class CraftChicken extends CraftAnimals implements Chicken {
/*    */   public CraftChicken(CraftServer server, EntityChicken entity) {
/* 12 */     super(server, (EntityAnimal)entity);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityChicken getHandle() {
/* 17 */     return (EntityChicken)this.entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 22 */     return "CraftChicken";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 26 */     return EntityType.CHICKEN;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftChicken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */