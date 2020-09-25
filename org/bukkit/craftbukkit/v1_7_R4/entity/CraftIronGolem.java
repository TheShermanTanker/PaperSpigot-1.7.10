/*    */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*    */ import net.minecraft.server.v1_7_R4.EntityCreature;
/*    */ import net.minecraft.server.v1_7_R4.EntityGolem;
/*    */ import net.minecraft.server.v1_7_R4.EntityIronGolem;
/*    */ import net.minecraft.server.v1_7_R4.EntityLiving;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*    */ 
/*    */ public class CraftIronGolem extends CraftGolem implements IronGolem {
/*    */   public CraftIronGolem(CraftServer server, EntityIronGolem entity) {
/* 10 */     super(server, (EntityGolem)entity);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityIronGolem getHandle() {
/* 15 */     return (EntityIronGolem)this.entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 20 */     return "CraftIronGolem";
/*    */   }
/*    */   
/*    */   public boolean isPlayerCreated() {
/* 24 */     return getHandle().isPlayerCreated();
/*    */   }
/*    */   
/*    */   public void setPlayerCreated(boolean playerCreated) {
/* 28 */     getHandle().setPlayerCreated(playerCreated);
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityType getType() {
/* 33 */     return EntityType.IRON_GOLEM;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftIronGolem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */