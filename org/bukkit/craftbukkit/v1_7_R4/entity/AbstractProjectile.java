/*    */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_7_R4.Entity;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*    */ import org.bukkit.entity.Projectile;
/*    */ 
/*    */ public abstract class AbstractProjectile extends CraftEntity implements Projectile {
/*    */   private boolean doesBounce;
/*    */   
/*    */   public AbstractProjectile(CraftServer server, Entity entity) {
/* 11 */     super(server, entity);
/* 12 */     this.doesBounce = false;
/*    */   }
/*    */   
/*    */   public boolean doesBounce() {
/* 16 */     return this.doesBounce;
/*    */   }
/*    */   
/*    */   public void setBounce(boolean doesBounce) {
/* 20 */     this.doesBounce = doesBounce;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\AbstractProjectile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */