/*    */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_7_R4.Entity;
/*    */ import net.minecraft.server.v1_7_R4.EntityLiving;
/*    */ import net.minecraft.server.v1_7_R4.EntityProjectile;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*    */ import org.bukkit.entity.LivingEntity;
/*    */ import org.bukkit.entity.Projectile;
/*    */ import org.bukkit.projectiles.ProjectileSource;
/*    */ 
/*    */ public abstract class CraftProjectile extends AbstractProjectile implements Projectile {
/*    */   public CraftProjectile(CraftServer server, Entity entity) {
/* 13 */     super(server, entity);
/*    */   }
/*    */   
/*    */   public ProjectileSource getShooter() {
/* 17 */     return (getHandle()).projectileSource;
/*    */   }
/*    */   
/*    */   public void setShooter(ProjectileSource shooter) {
/* 21 */     if (shooter instanceof CraftLivingEntity) {
/* 22 */       (getHandle()).shooter = (EntityLiving)((CraftLivingEntity)shooter).entity;
/* 23 */       if (shooter instanceof CraftHumanEntity) {
/* 24 */         (getHandle()).shooterName = ((CraftHumanEntity)shooter).getName();
/*    */       }
/*    */     } else {
/* 27 */       (getHandle()).shooter = null;
/* 28 */       (getHandle()).shooterName = null;
/*    */     } 
/* 30 */     (getHandle()).projectileSource = shooter;
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityProjectile getHandle() {
/* 35 */     return (EntityProjectile)this.entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 40 */     return "CraftProjectile";
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftProjectile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */