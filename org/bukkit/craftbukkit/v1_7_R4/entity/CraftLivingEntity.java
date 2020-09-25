/*     */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import net.minecraft.server.v1_7_R4.DamageSource;
/*     */ import net.minecraft.server.v1_7_R4.Entity;
/*     */ import net.minecraft.server.v1_7_R4.EntityArrow;
/*     */ import net.minecraft.server.v1_7_R4.EntityEgg;
/*     */ import net.minecraft.server.v1_7_R4.EntityEnderDragon;
/*     */ import net.minecraft.server.v1_7_R4.EntityEnderPearl;
/*     */ import net.minecraft.server.v1_7_R4.EntityFireball;
/*     */ import net.minecraft.server.v1_7_R4.EntityFishingHook;
/*     */ import net.minecraft.server.v1_7_R4.EntityHuman;
/*     */ import net.minecraft.server.v1_7_R4.EntityInsentient;
/*     */ import net.minecraft.server.v1_7_R4.EntityLargeFireball;
/*     */ import net.minecraft.server.v1_7_R4.EntityLiving;
/*     */ import net.minecraft.server.v1_7_R4.EntityPlayer;
/*     */ import net.minecraft.server.v1_7_R4.EntityPotion;
/*     */ import net.minecraft.server.v1_7_R4.EntitySmallFireball;
/*     */ import net.minecraft.server.v1_7_R4.EntitySnowball;
/*     */ import net.minecraft.server.v1_7_R4.EntityThrownExpBottle;
/*     */ import net.minecraft.server.v1_7_R4.EntityWitherSkull;
/*     */ import net.minecraft.server.v1_7_R4.GenericAttributes;
/*     */ import net.minecraft.server.v1_7_R4.MobEffect;
/*     */ import net.minecraft.server.v1_7_R4.MobEffectList;
/*     */ import net.minecraft.server.v1_7_R4.World;
/*     */ import net.minecraft.server.v1_7_R4.WorldServer;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftEntityEquipment;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
/*     */ import org.bukkit.entity.Arrow;
/*     */ import org.bukkit.entity.Egg;
/*     */ import org.bukkit.entity.EnderPearl;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.EntityType;
/*     */ import org.bukkit.entity.Fireball;
/*     */ import org.bukkit.entity.Fish;
/*     */ import org.bukkit.entity.LivingEntity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.entity.Projectile;
/*     */ import org.bukkit.entity.SmallFireball;
/*     */ import org.bukkit.entity.Snowball;
/*     */ import org.bukkit.entity.ThrownExpBottle;
/*     */ import org.bukkit.entity.ThrownPotion;
/*     */ import org.bukkit.entity.WitherSkull;
/*     */ import org.bukkit.event.player.PlayerTeleportEvent;
/*     */ import org.bukkit.inventory.EntityEquipment;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ import org.bukkit.potion.PotionEffectType;
/*     */ import org.bukkit.projectiles.ProjectileSource;
/*     */ import org.bukkit.util.BlockIterator;
/*     */ import org.bukkit.util.NumberConversions;
/*     */ import org.bukkit.util.Vector;
/*     */ 
/*     */ public class CraftLivingEntity
/*     */   extends CraftEntity implements LivingEntity {
/*     */   private CraftEntityEquipment equipment;
/*     */   
/*     */   public CraftLivingEntity(CraftServer server, EntityLiving entity) {
/*  68 */     super(server, (Entity)entity);
/*     */     
/*  70 */     if (entity instanceof EntityInsentient) {
/*  71 */       this.equipment = new CraftEntityEquipment(this);
/*     */     }
/*     */   }
/*     */   
/*     */   public double getHealth() {
/*  76 */     return Math.min(Math.max(0.0F, getHandle().getHealth()), getMaxHealth());
/*     */   }
/*     */   
/*     */   public void setHealth(double health) {
/*  80 */     if (health < 0.0D || health > getMaxHealth()) {
/*  81 */       throw new IllegalArgumentException("Health must be between 0 and " + getMaxHealth());
/*     */     }
/*     */     
/*  84 */     if (this.entity instanceof EntityPlayer && health == 0.0D) {
/*  85 */       ((EntityPlayer)this.entity).die(DamageSource.GENERIC);
/*     */     }
/*     */     
/*  88 */     getHandle().setHealth((float)health);
/*     */   }
/*     */   
/*     */   public double getMaxHealth() {
/*  92 */     return getHandle().getMaxHealth();
/*     */   }
/*     */   
/*     */   public void setMaxHealth(double amount) {
/*  96 */     Validate.isTrue((amount > 0.0D), "Max health must be greater than 0");
/*     */     
/*  98 */     getHandle().getAttributeInstance(GenericAttributes.maxHealth).setValue(amount);
/*     */     
/* 100 */     if (getHealth() > amount) {
/* 101 */       setHealth(amount);
/*     */     }
/*     */   }
/*     */   
/*     */   public void resetMaxHealth() {
/* 106 */     setMaxHealth(getHandle().getMaxHealth());
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public Egg throwEgg() {
/* 111 */     return launchProjectile(Egg.class);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public Snowball throwSnowball() {
/* 116 */     return launchProjectile(Snowball.class);
/*     */   }
/*     */   
/*     */   public double getEyeHeight() {
/* 120 */     return getHandle().getHeadHeight();
/*     */   }
/*     */   
/*     */   public double getEyeHeight(boolean ignoreSneaking) {
/* 124 */     return getEyeHeight();
/*     */   }
/*     */   
/*     */   private List<Block> getLineOfSight(HashSet<Byte> transparent, int maxDistance, int maxLength) {
/* 128 */     if (maxDistance > 120) {
/* 129 */       maxDistance = 120;
/*     */     }
/* 131 */     ArrayList<Block> blocks = new ArrayList<Block>();
/* 132 */     BlockIterator<Block> blockIterator = new BlockIterator(this, maxDistance);
/* 133 */     while (blockIterator.hasNext()) {
/* 134 */       Block block = blockIterator.next();
/* 135 */       blocks.add(block);
/* 136 */       if (maxLength != 0 && blocks.size() > maxLength) {
/* 137 */         blocks.remove(0);
/*     */       }
/* 139 */       int id = block.getTypeId();
/* 140 */       if ((transparent == null) ? (
/* 141 */         id != 0) : 
/*     */ 
/*     */ 
/*     */         
/* 145 */         !transparent.contains(Byte.valueOf((byte)id))) {
/*     */         break;
/*     */       }
/*     */     } 
/*     */     
/* 150 */     return blocks;
/*     */   }
/*     */   
/*     */   public List<Block> getLineOfSight(HashSet<Byte> transparent, int maxDistance) {
/* 154 */     return getLineOfSight(transparent, maxDistance, 0);
/*     */   }
/*     */   
/*     */   public Block getTargetBlock(HashSet<Byte> transparent, int maxDistance) {
/* 158 */     List<Block> blocks = getLineOfSight(transparent, maxDistance, 1);
/* 159 */     return blocks.get(0);
/*     */   }
/*     */   
/*     */   public List<Block> getLastTwoTargetBlocks(HashSet<Byte> transparent, int maxDistance) {
/* 163 */     return getLineOfSight(transparent, maxDistance, 2);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public Arrow shootArrow() {
/* 168 */     return launchProjectile(Arrow.class);
/*     */   }
/*     */   
/*     */   public int getRemainingAir() {
/* 172 */     return getHandle().getAirTicks();
/*     */   }
/*     */   
/*     */   public void setRemainingAir(int ticks) {
/* 176 */     getHandle().setAirTicks(ticks);
/*     */   }
/*     */   
/*     */   public int getMaximumAir() {
/* 180 */     return (getHandle()).maxAirTicks;
/*     */   }
/*     */   
/*     */   public void setMaximumAir(int ticks) {
/* 184 */     (getHandle()).maxAirTicks = ticks;
/*     */   }
/*     */   
/*     */   public void damage(double amount) {
/* 188 */     damage(amount, (Entity)null);
/*     */   }
/*     */   
/*     */   public void damage(double amount, Entity source) {
/* 192 */     DamageSource reason = DamageSource.GENERIC;
/*     */     
/* 194 */     if (source instanceof org.bukkit.entity.HumanEntity) {
/* 195 */       reason = DamageSource.playerAttack(((CraftHumanEntity)source).getHandle());
/* 196 */     } else if (source instanceof LivingEntity) {
/* 197 */       reason = DamageSource.mobAttack(((CraftLivingEntity)source).getHandle());
/*     */     } 
/*     */     
/* 200 */     if (this.entity instanceof EntityEnderDragon) {
/* 201 */       ((EntityEnderDragon)this.entity).dealDamage(reason, (float)amount);
/*     */     } else {
/* 203 */       this.entity.damageEntity(reason, (float)amount);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Location getEyeLocation() {
/* 208 */     Location loc = getLocation();
/* 209 */     loc.setY(loc.getY() + getEyeHeight());
/* 210 */     return loc;
/*     */   }
/*     */   
/*     */   public int getMaximumNoDamageTicks() {
/* 214 */     return (getHandle()).maxNoDamageTicks;
/*     */   }
/*     */   
/*     */   public void setMaximumNoDamageTicks(int ticks) {
/* 218 */     (getHandle()).maxNoDamageTicks = ticks;
/*     */   }
/*     */   
/*     */   public double getLastDamage() {
/* 222 */     return (getHandle()).lastDamage;
/*     */   }
/*     */   
/*     */   public void setLastDamage(double damage) {
/* 226 */     (getHandle()).lastDamage = (float)damage;
/*     */   }
/*     */   
/*     */   public int getNoDamageTicks() {
/* 230 */     return (getHandle()).noDamageTicks;
/*     */   }
/*     */   
/*     */   public void setNoDamageTicks(int ticks) {
/* 234 */     (getHandle()).noDamageTicks = ticks;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityLiving getHandle() {
/* 239 */     return (EntityLiving)this.entity;
/*     */   }
/*     */   
/*     */   public void setHandle(EntityLiving entity) {
/* 243 */     setHandle((Entity)entity);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 248 */     return "CraftLivingEntity{id=" + getEntityId() + '}';
/*     */   }
/*     */   
/*     */   public Player getKiller() {
/* 252 */     return ((getHandle()).killer == null) ? null : (Player)(getHandle()).killer.getBukkitEntity();
/*     */   }
/*     */   
/*     */   public boolean addPotionEffect(PotionEffect effect) {
/* 256 */     return addPotionEffect(effect, false);
/*     */   }
/*     */   
/*     */   public boolean addPotionEffect(PotionEffect effect, boolean force) {
/* 260 */     if (hasPotionEffect(effect.getType())) {
/* 261 */       if (!force) {
/* 262 */         return false;
/*     */       }
/* 264 */       removePotionEffect(effect.getType());
/*     */     } 
/* 266 */     getHandle().addEffect(new MobEffect(effect.getType().getId(), effect.getDuration(), effect.getAmplifier(), effect.isAmbient()));
/* 267 */     return true;
/*     */   }
/*     */   
/*     */   public boolean addPotionEffects(Collection<PotionEffect> effects) {
/* 271 */     boolean success = true;
/* 272 */     for (PotionEffect effect : effects) {
/* 273 */       success &= addPotionEffect(effect);
/*     */     }
/* 275 */     return success;
/*     */   }
/*     */   
/*     */   public boolean hasPotionEffect(PotionEffectType type) {
/* 279 */     return getHandle().hasEffect(MobEffectList.byId[type.getId()]);
/*     */   }
/*     */   
/*     */   public void removePotionEffect(PotionEffectType type) {
/* 283 */     getHandle().removeEffect(type.getId());
/*     */   }
/*     */   
/*     */   public Collection<PotionEffect> getActivePotionEffects() {
/* 287 */     List<PotionEffect> effects = new ArrayList<PotionEffect>();
/* 288 */     for (Object raw : (getHandle()).effects.values()) {
/* 289 */       if (!(raw instanceof MobEffect))
/*     */         continue; 
/* 291 */       MobEffect handle = (MobEffect)raw;
/* 292 */       effects.add(new PotionEffect(PotionEffectType.getById(handle.getEffectId()), handle.getDuration(), handle.getAmplifier(), handle.isAmbient()));
/*     */     } 
/* 294 */     return effects;
/*     */   }
/*     */   
/*     */   public <T extends Projectile> T launchProjectile(Class<? extends T> projectile) {
/* 298 */     return launchProjectile(projectile, (Vector)null);
/*     */   }
/*     */   
/*     */   public <T extends Projectile> T launchProjectile(Class<? extends T> projectile, Vector velocity) {
/*     */     EntityLargeFireball entityLargeFireball;
/* 303 */     WorldServer worldServer = ((CraftWorld)getWorld()).getHandle();
/* 304 */     Entity launch = null;
/*     */     
/* 306 */     if (Snowball.class.isAssignableFrom(projectile)) {
/* 307 */       EntitySnowball entitySnowball = new EntitySnowball((World)worldServer, getHandle());
/* 308 */     } else if (Egg.class.isAssignableFrom(projectile)) {
/* 309 */       EntityEgg entityEgg = new EntityEgg((World)worldServer, getHandle());
/* 310 */     } else if (EnderPearl.class.isAssignableFrom(projectile)) {
/* 311 */       EntityEnderPearl entityEnderPearl = new EntityEnderPearl((World)worldServer, getHandle());
/* 312 */     } else if (Arrow.class.isAssignableFrom(projectile)) {
/* 313 */       EntityArrow entityArrow = new EntityArrow((World)worldServer, getHandle(), 1.0F);
/* 314 */     } else if (ThrownPotion.class.isAssignableFrom(projectile)) {
/* 315 */       EntityPotion entityPotion = new EntityPotion((World)worldServer, getHandle(), CraftItemStack.asNMSCopy(new ItemStack(Material.POTION, 1)));
/* 316 */     } else if (ThrownExpBottle.class.isAssignableFrom(projectile)) {
/* 317 */       EntityThrownExpBottle entityThrownExpBottle = new EntityThrownExpBottle((World)worldServer, getHandle());
/* 318 */     } else if (Fish.class.isAssignableFrom(projectile) && getHandle() instanceof EntityHuman) {
/* 319 */       EntityFishingHook entityFishingHook = new EntityFishingHook((World)worldServer, (EntityHuman)getHandle());
/* 320 */     } else if (Fireball.class.isAssignableFrom(projectile)) {
/* 321 */       Location location = getEyeLocation();
/* 322 */       Vector direction = location.getDirection().multiply(10);
/*     */       
/* 324 */       if (SmallFireball.class.isAssignableFrom(projectile)) {
/* 325 */         EntitySmallFireball entitySmallFireball = new EntitySmallFireball((World)worldServer, getHandle(), direction.getX(), direction.getY(), direction.getZ());
/* 326 */       } else if (WitherSkull.class.isAssignableFrom(projectile)) {
/* 327 */         EntityWitherSkull entityWitherSkull = new EntityWitherSkull((World)worldServer, getHandle(), direction.getX(), direction.getY(), direction.getZ());
/*     */       } else {
/* 329 */         entityLargeFireball = new EntityLargeFireball((World)worldServer, getHandle(), direction.getX(), direction.getY(), direction.getZ());
/*     */       } 
/*     */       
/* 332 */       ((EntityFireball)entityLargeFireball).projectileSource = (ProjectileSource)this;
/* 333 */       entityLargeFireball.setPositionRotation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
/*     */     } 
/*     */     
/* 336 */     Validate.notNull(entityLargeFireball, "Projectile not supported");
/*     */     
/* 338 */     if (velocity != null) {
/* 339 */       ((Projectile)entityLargeFireball.getBukkitEntity()).setVelocity(velocity);
/*     */     }
/*     */     
/* 342 */     worldServer.addEntity((Entity)entityLargeFireball);
/* 343 */     return (T)entityLargeFireball.getBukkitEntity();
/*     */   }
/*     */   
/*     */   public EntityType getType() {
/* 347 */     return EntityType.UNKNOWN;
/*     */   }
/*     */   
/*     */   public boolean hasLineOfSight(Entity other) {
/* 351 */     return getHandle().hasLineOfSight(((CraftEntity)other).getHandle());
/*     */   }
/*     */   
/*     */   public boolean getRemoveWhenFarAway() {
/* 355 */     return (getHandle() instanceof EntityInsentient && !((EntityInsentient)getHandle()).persistent);
/*     */   }
/*     */   
/*     */   public void setRemoveWhenFarAway(boolean remove) {
/* 359 */     if (getHandle() instanceof EntityInsentient) {
/* 360 */       ((EntityInsentient)getHandle()).persistent = !remove;
/*     */     }
/*     */   }
/*     */   
/*     */   public EntityEquipment getEquipment() {
/* 365 */     return (EntityEquipment)this.equipment;
/*     */   }
/*     */   
/*     */   public void setCanPickupItems(boolean pickup) {
/* 369 */     if (getHandle() instanceof EntityInsentient) {
/* 370 */       ((EntityInsentient)getHandle()).canPickUpLoot = pickup;
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean getCanPickupItems() {
/* 375 */     return (getHandle() instanceof EntityInsentient && ((EntityInsentient)getHandle()).canPickUpLoot);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean teleport(Location location, PlayerTeleportEvent.TeleportCause cause) {
/* 380 */     if (getHealth() == 0.0D) {
/* 381 */       return false;
/*     */     }
/*     */     
/* 384 */     return super.teleport(location, cause);
/*     */   }
/*     */   
/*     */   public void setCustomName(String name) {
/* 388 */     if (!(getHandle() instanceof EntityInsentient)) {
/*     */       return;
/*     */     }
/*     */     
/* 392 */     if (name == null) {
/* 393 */       name = "";
/*     */     }
/*     */ 
/*     */     
/* 397 */     if (name.length() > 64) {
/* 398 */       name = name.substring(0, 64);
/*     */     }
/*     */     
/* 401 */     ((EntityInsentient)getHandle()).setCustomName(name);
/*     */   }
/*     */   
/*     */   public String getCustomName() {
/* 405 */     if (!(getHandle() instanceof EntityInsentient)) {
/* 406 */       return null;
/*     */     }
/*     */     
/* 409 */     String name = ((EntityInsentient)getHandle()).getCustomName();
/*     */     
/* 411 */     if (name == null || name.length() == 0) {
/* 412 */       return null;
/*     */     }
/*     */     
/* 415 */     return name;
/*     */   }
/*     */   
/*     */   public void setCustomNameVisible(boolean flag) {
/* 419 */     if (getHandle() instanceof EntityInsentient) {
/* 420 */       ((EntityInsentient)getHandle()).setCustomNameVisible(flag);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isCustomNameVisible() {
/* 425 */     return (getHandle() instanceof EntityInsentient && ((EntityInsentient)getHandle()).getCustomNameVisible());
/*     */   }
/*     */   
/*     */   public boolean isLeashed() {
/* 429 */     if (!(getHandle() instanceof EntityInsentient)) {
/* 430 */       return false;
/*     */     }
/* 432 */     return (((EntityInsentient)getHandle()).getLeashHolder() != null);
/*     */   }
/*     */   
/*     */   public Entity getLeashHolder() throws IllegalStateException {
/* 436 */     if (!isLeashed()) {
/* 437 */       throw new IllegalStateException("Entity not leashed");
/*     */     }
/* 439 */     return ((EntityInsentient)getHandle()).getLeashHolder().getBukkitEntity();
/*     */   }
/*     */   
/*     */   private boolean unleash() {
/* 443 */     if (!isLeashed()) {
/* 444 */       return false;
/*     */     }
/* 446 */     ((EntityInsentient)getHandle()).unleash(true, false);
/* 447 */     return true;
/*     */   }
/*     */   
/*     */   public boolean setLeashHolder(Entity holder) {
/* 451 */     if (getHandle() instanceof net.minecraft.server.v1_7_R4.EntityWither || !(getHandle() instanceof EntityInsentient)) {
/* 452 */       return false;
/*     */     }
/*     */     
/* 455 */     if (holder == null) {
/* 456 */       return unleash();
/*     */     }
/*     */     
/* 459 */     if (holder.isDead()) {
/* 460 */       return false;
/*     */     }
/*     */     
/* 463 */     unleash();
/* 464 */     ((EntityInsentient)getHandle()).setLeashHolder(((CraftEntity)holder).getHandle(), true);
/* 465 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftLivingEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */