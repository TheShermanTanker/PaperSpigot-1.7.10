/*    */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*    */ import net.minecraft.server.v1_7_R4.Entity;
/*    */ import net.minecraft.server.v1_7_R4.EntityEnderCrystal;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*    */ import org.bukkit.entity.EnderCrystal;
/*    */ import org.bukkit.entity.EntityType;
/*    */ 
/*    */ public class CraftEnderCrystal extends CraftEntity implements EnderCrystal {
/*    */   public CraftEnderCrystal(CraftServer server, EntityEnderCrystal entity) {
/* 10 */     super(server, (Entity)entity);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityEnderCrystal getHandle() {
/* 15 */     return (EntityEnderCrystal)this.entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 20 */     return "CraftEnderCrystal";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 24 */     return EntityType.ENDER_CRYSTAL;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftEnderCrystal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */