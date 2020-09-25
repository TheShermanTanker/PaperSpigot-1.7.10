/*    */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_7_R4.Entity;
/*    */ import net.minecraft.server.v1_7_R4.EntityArrow;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*    */ import org.bukkit.entity.Arrow;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.EntityType;
/*    */ import org.bukkit.entity.LivingEntity;
/*    */ import org.bukkit.projectiles.ProjectileSource;
/*    */ 
/*    */ public class CraftArrow extends AbstractProjectile implements Arrow {
/*    */   public CraftArrow(CraftServer server, EntityArrow entity) {
/* 15 */     super(server, (Entity)entity);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 76 */     this.spigot = new Arrow.Spigot()
/*    */       {
/*    */         
/*    */         public double getDamage()
/*    */         {
/* 81 */           return CraftArrow.this.getHandle().e();
/*    */         }
/*    */ 
/*    */         
/*    */         public void setDamage(double damage)
/*    */         {
/* 87 */           CraftArrow.this.getHandle().b(damage);
/*    */         }
/*    */       };
/*    */   }
/*    */   private final Arrow.Spigot spigot;
/*    */   public Arrow.Spigot spigot() {
/* 93 */     return this.spigot;
/*    */   }
/*    */   
/*    */   public void setKnockbackStrength(int knockbackStrength) {
/*    */     Validate.isTrue((knockbackStrength >= 0), "Knockback cannot be negative");
/*    */     getHandle().setKnockbackStrength(knockbackStrength);
/*    */   }
/*    */   
/*    */   public int getKnockbackStrength() {
/*    */     return (getHandle()).knockbackStrength;
/*    */   }
/*    */   
/*    */   public boolean isCritical() {
/*    */     return getHandle().isCritical();
/*    */   }
/*    */   
/*    */   public void setCritical(boolean critical) {
/*    */     getHandle().setCritical(critical);
/*    */   }
/*    */   
/*    */   public ProjectileSource getShooter() {
/*    */     return (getHandle()).projectileSource;
/*    */   }
/*    */   
/*    */   public void setShooter(ProjectileSource shooter) {
/*    */     if (shooter instanceof LivingEntity) {
/*    */       (getHandle()).shooter = (Entity)((CraftLivingEntity)shooter).getHandle();
/*    */     } else {
/*    */       (getHandle()).shooter = null;
/*    */     } 
/*    */     (getHandle()).projectileSource = shooter;
/*    */   }
/*    */   
/*    */   public EntityArrow getHandle() {
/*    */     return (EntityArrow)this.entity;
/*    */   }
/*    */   
/*    */   public String toString() {
/*    */     return "CraftArrow";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/*    */     return EntityType.ARROW;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftArrow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */