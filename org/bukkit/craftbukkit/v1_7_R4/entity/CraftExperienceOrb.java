/*    */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*    */ import net.minecraft.server.v1_7_R4.Entity;
/*    */ import net.minecraft.server.v1_7_R4.EntityExperienceOrb;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*    */ import org.bukkit.entity.EntityType;
/*    */ import org.bukkit.entity.ExperienceOrb;
/*    */ 
/*    */ public class CraftExperienceOrb extends CraftEntity implements ExperienceOrb {
/*    */   public CraftExperienceOrb(CraftServer server, EntityExperienceOrb entity) {
/* 10 */     super(server, (Entity)entity);
/*    */   }
/*    */   
/*    */   public int getExperience() {
/* 14 */     return (getHandle()).value;
/*    */   }
/*    */   
/*    */   public void setExperience(int value) {
/* 18 */     (getHandle()).value = value;
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityExperienceOrb getHandle() {
/* 23 */     return (EntityExperienceOrb)this.entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 28 */     return "CraftExperienceOrb";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 32 */     return EntityType.EXPERIENCE_ORB;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftExperienceOrb.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */