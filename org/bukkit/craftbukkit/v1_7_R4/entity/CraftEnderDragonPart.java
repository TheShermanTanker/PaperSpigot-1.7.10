/*    */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*    */ import net.minecraft.server.v1_7_R4.Entity;
/*    */ import net.minecraft.server.v1_7_R4.EntityComplexPart;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*    */ import org.bukkit.entity.ComplexLivingEntity;
/*    */ import org.bukkit.entity.EnderDragon;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.util.NumberConversions;
/*    */ 
/*    */ public class CraftEnderDragonPart extends CraftComplexPart implements EnderDragonPart {
/*    */   public CraftEnderDragonPart(CraftServer server, EntityComplexPart entity) {
/* 12 */     super(server, entity);
/*    */   }
/*    */ 
/*    */   
/*    */   public EnderDragon getParent() {
/* 17 */     return (EnderDragon)super.getParent();
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityComplexPart getHandle() {
/* 22 */     return (EntityComplexPart)this.entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 27 */     return "CraftEnderDragonPart";
/*    */   }
/*    */   
/*    */   public void damage(double amount) {
/* 31 */     getParent().damage(amount);
/*    */   }
/*    */   
/*    */   public void damage(double amount, Entity source) {
/* 35 */     getParent().damage(amount, source);
/*    */   }
/*    */   
/*    */   public double getHealth() {
/* 39 */     return getParent().getHealth();
/*    */   }
/*    */   
/*    */   public void setHealth(double health) {
/* 43 */     getParent().setHealth(health);
/*    */   }
/*    */   
/*    */   public double getMaxHealth() {
/* 47 */     return getParent().getMaxHealth();
/*    */   }
/*    */   
/*    */   public void setMaxHealth(double health) {
/* 51 */     getParent().setMaxHealth(health);
/*    */   }
/*    */   
/*    */   public void resetMaxHealth() {
/* 55 */     getParent().resetMaxHealth();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftEnderDragonPart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */