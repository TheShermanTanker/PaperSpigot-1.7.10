/*    */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*    */ 
/*    */ import net.minecraft.server.v1_7_R4.Entity;
/*    */ import net.minecraft.server.v1_7_R4.EntityFishingHook;
/*    */ import net.minecraft.server.v1_7_R4.EntityHuman;
/*    */ import net.minecraft.server.v1_7_R4.MathHelper;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*    */ import org.bukkit.entity.EntityType;
/*    */ import org.bukkit.entity.Fish;
/*    */ import org.bukkit.entity.LivingEntity;
/*    */ import org.bukkit.projectiles.ProjectileSource;
/*    */ 
/*    */ public class CraftFish extends AbstractProjectile implements Fish {
/* 15 */   private double biteChance = -1.0D;
/*    */   
/*    */   public CraftFish(CraftServer server, EntityFishingHook entity) {
/* 18 */     super(server, (Entity)entity);
/*    */   }
/*    */   
/*    */   public ProjectileSource getShooter() {
/* 22 */     if ((getHandle()).owner != null) {
/* 23 */       return (ProjectileSource)(getHandle()).owner.getBukkitEntity();
/*    */     }
/*    */     
/* 26 */     return null;
/*    */   }
/*    */   
/*    */   public void setShooter(ProjectileSource shooter) {
/* 30 */     if (shooter instanceof CraftHumanEntity) {
/* 31 */       (getHandle()).owner = (EntityHuman)((CraftHumanEntity)shooter).entity;
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public EntityFishingHook getHandle() {
/* 37 */     return (EntityFishingHook)this.entity;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 42 */     return "CraftFish";
/*    */   }
/*    */   
/*    */   public EntityType getType() {
/* 46 */     return EntityType.FISHING_HOOK;
/*    */   }
/*    */   
/*    */   public double getBiteChance() {
/* 50 */     EntityFishingHook hook = getHandle();
/*    */     
/* 52 */     if (this.biteChance == -1.0D) {
/* 53 */       if (hook.world.isRainingAt(MathHelper.floor(hook.locX), MathHelper.floor(hook.locY) + 1, MathHelper.floor(hook.locZ))) {
/* 54 */         return 0.0033333333333333335D;
/*    */       }
/* 56 */       return 0.002D;
/*    */     } 
/* 58 */     return this.biteChance;
/*    */   }
/*    */   
/*    */   public void setBiteChance(double chance) {
/* 62 */     Validate.isTrue((chance >= 0.0D && chance <= 1.0D), "The bite chance must be between 0 and 1.");
/* 63 */     this.biteChance = chance;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftFish.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */