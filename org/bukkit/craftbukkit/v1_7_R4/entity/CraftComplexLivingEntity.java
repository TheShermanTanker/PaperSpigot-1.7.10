/*    */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*    */ import net.minecraft.server.v1_7_R4.Entity;
/*    */ import net.minecraft.server.v1_7_R4.EntityLiving;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*    */ import org.bukkit.entity.ComplexLivingEntity;
/*    */ 
/*    */ public abstract class CraftComplexLivingEntity extends CraftLivingEntity implements ComplexLivingEntity {
/*    */   public CraftComplexLivingEntity(CraftServer server, EntityLiving entity) {
/*  9 */     super(server, entity);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityLiving getHandle() {
/* 14 */     return (EntityLiving)this.entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 19 */     return "CraftComplexLivingEntity";
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftComplexLivingEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */