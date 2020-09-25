/*    */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*    */ import net.minecraft.server.v1_7_R4.EntityAgeable;
/*    */ import net.minecraft.server.v1_7_R4.EntityCreature;
/*    */ import net.minecraft.server.v1_7_R4.EntityLiving;
/*    */ import net.minecraft.server.v1_7_R4.EntityVillager;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*    */ import org.bukkit.entity.Villager;
/*    */ 
/*    */ public class CraftVillager extends CraftAgeable implements Villager {
/*    */   public CraftVillager(CraftServer server, EntityVillager entity) {
/* 11 */     super(server, (EntityAgeable)entity);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityVillager getHandle() {
/* 16 */     return (EntityVillager)this.entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 21 */     return "CraftVillager";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 25 */     return EntityType.VILLAGER;
/*    */   }
/*    */   
/*    */   public Villager.Profession getProfession() {
/* 29 */     return Villager.Profession.getProfession(getHandle().getProfession());
/*    */   }
/*    */   
/*    */   public void setProfession(Villager.Profession profession) {
/* 33 */     Validate.notNull(profession);
/* 34 */     getHandle().setProfession(profession.getId());
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftVillager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */