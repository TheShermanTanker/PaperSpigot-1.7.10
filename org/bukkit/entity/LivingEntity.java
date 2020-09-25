package org.bukkit.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.projectiles.ProjectileSource;

public interface LivingEntity extends Entity, Damageable, ProjectileSource {
  double getEyeHeight();
  
  double getEyeHeight(boolean paramBoolean);
  
  Location getEyeLocation();
  
  @Deprecated
  List<Block> getLineOfSight(HashSet<Byte> paramHashSet, int paramInt);
  
  @Deprecated
  Block getTargetBlock(HashSet<Byte> paramHashSet, int paramInt);
  
  @Deprecated
  List<Block> getLastTwoTargetBlocks(HashSet<Byte> paramHashSet, int paramInt);
  
  @Deprecated
  Egg throwEgg();
  
  @Deprecated
  Snowball throwSnowball();
  
  @Deprecated
  Arrow shootArrow();
  
  int getRemainingAir();
  
  void setRemainingAir(int paramInt);
  
  int getMaximumAir();
  
  void setMaximumAir(int paramInt);
  
  int getMaximumNoDamageTicks();
  
  void setMaximumNoDamageTicks(int paramInt);
  
  double getLastDamage();
  
  @Deprecated
  int getLastDamage();
  
  void setLastDamage(double paramDouble);
  
  @Deprecated
  void setLastDamage(int paramInt);
  
  int getNoDamageTicks();
  
  void setNoDamageTicks(int paramInt);
  
  Player getKiller();
  
  boolean addPotionEffect(PotionEffect paramPotionEffect);
  
  boolean addPotionEffect(PotionEffect paramPotionEffect, boolean paramBoolean);
  
  boolean addPotionEffects(Collection<PotionEffect> paramCollection);
  
  boolean hasPotionEffect(PotionEffectType paramPotionEffectType);
  
  void removePotionEffect(PotionEffectType paramPotionEffectType);
  
  Collection<PotionEffect> getActivePotionEffects();
  
  boolean hasLineOfSight(Entity paramEntity);
  
  boolean getRemoveWhenFarAway();
  
  void setRemoveWhenFarAway(boolean paramBoolean);
  
  EntityEquipment getEquipment();
  
  void setCanPickupItems(boolean paramBoolean);
  
  boolean getCanPickupItems();
  
  void setCustomName(String paramString);
  
  String getCustomName();
  
  void setCustomNameVisible(boolean paramBoolean);
  
  boolean isCustomNameVisible();
  
  boolean isLeashed();
  
  Entity getLeashHolder() throws IllegalStateException;
  
  boolean setLeashHolder(Entity paramEntity);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\entity\LivingEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */