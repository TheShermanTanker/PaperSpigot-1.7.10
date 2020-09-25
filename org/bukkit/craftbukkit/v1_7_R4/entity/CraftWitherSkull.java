/*    */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*    */ import net.minecraft.server.v1_7_R4.Entity;
/*    */ import net.minecraft.server.v1_7_R4.EntityFireball;
/*    */ import net.minecraft.server.v1_7_R4.EntityWitherSkull;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*    */ import org.bukkit.entity.EntityType;
/*    */ 
/*    */ public class CraftWitherSkull extends CraftFireball implements WitherSkull {
/*    */   public CraftWitherSkull(CraftServer server, EntityWitherSkull entity) {
/* 10 */     super(server, (EntityFireball)entity);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setCharged(boolean charged) {
/* 15 */     getHandle().setCharged(charged);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isCharged() {
/* 20 */     return getHandle().isCharged();
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityWitherSkull getHandle() {
/* 25 */     return (EntityWitherSkull)this.entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 30 */     return "CraftWitherSkull";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 34 */     return EntityType.WITHER_SKULL;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftWitherSkull.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */