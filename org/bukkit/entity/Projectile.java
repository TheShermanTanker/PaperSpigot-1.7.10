package org.bukkit.entity;

import org.bukkit.projectiles.ProjectileSource;

public interface Projectile extends Entity {
  @Deprecated
  LivingEntity getShooter();
  
  ProjectileSource getShooter();
  
  @Deprecated
  void setShooter(LivingEntity paramLivingEntity);
  
  void setShooter(ProjectileSource paramProjectileSource);
  
  boolean doesBounce();
  
  void setBounce(boolean paramBoolean);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\entity\Projectile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */