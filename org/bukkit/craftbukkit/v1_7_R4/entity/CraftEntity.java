/*     */ package org.bukkit.craftbukkit.v1_7_R4.entity;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import net.minecraft.server.v1_7_R4.Entity;
/*     */ import net.minecraft.server.v1_7_R4.EntityAmbient;
/*     */ import net.minecraft.server.v1_7_R4.EntityAnimal;
/*     */ import net.minecraft.server.v1_7_R4.EntityArrow;
/*     */ import net.minecraft.server.v1_7_R4.EntityBat;
/*     */ import net.minecraft.server.v1_7_R4.EntityBlaze;
/*     */ import net.minecraft.server.v1_7_R4.EntityBoat;
/*     */ import net.minecraft.server.v1_7_R4.EntityCaveSpider;
/*     */ import net.minecraft.server.v1_7_R4.EntityChicken;
/*     */ import net.minecraft.server.v1_7_R4.EntityComplexPart;
/*     */ import net.minecraft.server.v1_7_R4.EntityCow;
/*     */ import net.minecraft.server.v1_7_R4.EntityCreature;
/*     */ import net.minecraft.server.v1_7_R4.EntityCreeper;
/*     */ import net.minecraft.server.v1_7_R4.EntityEgg;
/*     */ import net.minecraft.server.v1_7_R4.EntityEnderCrystal;
/*     */ import net.minecraft.server.v1_7_R4.EntityEnderDragon;
/*     */ import net.minecraft.server.v1_7_R4.EntityEnderPearl;
/*     */ import net.minecraft.server.v1_7_R4.EntityEnderSignal;
/*     */ import net.minecraft.server.v1_7_R4.EntityEnderman;
/*     */ import net.minecraft.server.v1_7_R4.EntityExperienceOrb;
/*     */ import net.minecraft.server.v1_7_R4.EntityFallingBlock;
/*     */ import net.minecraft.server.v1_7_R4.EntityFireball;
/*     */ import net.minecraft.server.v1_7_R4.EntityFireworks;
/*     */ import net.minecraft.server.v1_7_R4.EntityFishingHook;
/*     */ import net.minecraft.server.v1_7_R4.EntityFlying;
/*     */ import net.minecraft.server.v1_7_R4.EntityGhast;
/*     */ import net.minecraft.server.v1_7_R4.EntityGiantZombie;
/*     */ import net.minecraft.server.v1_7_R4.EntityHanging;
/*     */ import net.minecraft.server.v1_7_R4.EntityHorse;
/*     */ import net.minecraft.server.v1_7_R4.EntityHuman;
/*     */ import net.minecraft.server.v1_7_R4.EntityIronGolem;
/*     */ import net.minecraft.server.v1_7_R4.EntityItem;
/*     */ import net.minecraft.server.v1_7_R4.EntityItemFrame;
/*     */ import net.minecraft.server.v1_7_R4.EntityLargeFireball;
/*     */ import net.minecraft.server.v1_7_R4.EntityLeash;
/*     */ import net.minecraft.server.v1_7_R4.EntityLightning;
/*     */ import net.minecraft.server.v1_7_R4.EntityLiving;
/*     */ import net.minecraft.server.v1_7_R4.EntityMagmaCube;
/*     */ import net.minecraft.server.v1_7_R4.EntityMinecartAbstract;
/*     */ import net.minecraft.server.v1_7_R4.EntityMinecartChest;
/*     */ import net.minecraft.server.v1_7_R4.EntityMinecartCommandBlock;
/*     */ import net.minecraft.server.v1_7_R4.EntityMinecartFurnace;
/*     */ import net.minecraft.server.v1_7_R4.EntityMinecartHopper;
/*     */ import net.minecraft.server.v1_7_R4.EntityMinecartMobSpawner;
/*     */ import net.minecraft.server.v1_7_R4.EntityMinecartTNT;
/*     */ import net.minecraft.server.v1_7_R4.EntityMonster;
/*     */ import net.minecraft.server.v1_7_R4.EntityMushroomCow;
/*     */ import net.minecraft.server.v1_7_R4.EntityOcelot;
/*     */ import net.minecraft.server.v1_7_R4.EntityPainting;
/*     */ import net.minecraft.server.v1_7_R4.EntityPig;
/*     */ import net.minecraft.server.v1_7_R4.EntityPigZombie;
/*     */ import net.minecraft.server.v1_7_R4.EntityPlayer;
/*     */ import net.minecraft.server.v1_7_R4.EntityPotion;
/*     */ import net.minecraft.server.v1_7_R4.EntitySheep;
/*     */ import net.minecraft.server.v1_7_R4.EntitySilverfish;
/*     */ import net.minecraft.server.v1_7_R4.EntitySkeleton;
/*     */ import net.minecraft.server.v1_7_R4.EntitySlime;
/*     */ import net.minecraft.server.v1_7_R4.EntitySmallFireball;
/*     */ import net.minecraft.server.v1_7_R4.EntitySnowball;
/*     */ import net.minecraft.server.v1_7_R4.EntitySnowman;
/*     */ import net.minecraft.server.v1_7_R4.EntitySpider;
/*     */ import net.minecraft.server.v1_7_R4.EntitySquid;
/*     */ import net.minecraft.server.v1_7_R4.EntityTNTPrimed;
/*     */ import net.minecraft.server.v1_7_R4.EntityThrownExpBottle;
/*     */ import net.minecraft.server.v1_7_R4.EntityVillager;
/*     */ import net.minecraft.server.v1_7_R4.EntityWaterAnimal;
/*     */ import net.minecraft.server.v1_7_R4.EntityWeather;
/*     */ import net.minecraft.server.v1_7_R4.EntityWitch;
/*     */ import net.minecraft.server.v1_7_R4.EntityWither;
/*     */ import net.minecraft.server.v1_7_R4.EntityWitherSkull;
/*     */ import net.minecraft.server.v1_7_R4.EntityWolf;
/*     */ import net.minecraft.server.v1_7_R4.EntityZombie;
/*     */ import org.bukkit.EntityEffect;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.event.entity.EntityDamageEvent;
/*     */ import org.bukkit.event.player.PlayerTeleportEvent;
/*     */ import org.bukkit.metadata.MetadataValue;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.util.Vector;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class CraftEntity
/*     */   implements Entity
/*     */ {
/*     */   protected final CraftServer server;
/*     */   protected Entity entity;
/*     */   private EntityDamageEvent lastDamageEvent;
/*     */   private final Entity.Spigot spigot;
/*     */   
/*     */   public CraftEntity(CraftServer server, Entity entity) {
/* 414 */     this.spigot = new Entity.Spigot()
/*     */       {
/*     */         
/*     */         public boolean isInvulnerable()
/*     */         {
/* 419 */           return CraftEntity.this.getHandle().isInvulnerable();
/*     */         }
/*     */       };
/*     */     this.server = server;
/*     */     this.entity = entity;
/*     */   } public Entity.Spigot spigot() {
/* 425 */     return this.spigot;
/*     */   }
/*     */   
/*     */   public static CraftEntity getEntity(CraftServer server, Entity entity) {
/*     */     if (entity instanceof EntityLiving) {
/*     */       if (entity instanceof EntityHuman) {
/*     */         if (entity instanceof EntityPlayer)
/*     */           return new CraftPlayer(server, (EntityPlayer)entity); 
/*     */         return new CraftHumanEntity(server, (EntityHuman)entity);
/*     */       } 
/*     */       if (entity instanceof EntityCreature) {
/*     */         if (entity instanceof EntityAnimal) {
/*     */           if (entity instanceof EntityChicken)
/*     */             return new CraftChicken(server, (EntityChicken)entity); 
/*     */           if (entity instanceof EntityCow) {
/*     */             if (entity instanceof EntityMushroomCow)
/*     */               return new CraftMushroomCow(server, (EntityMushroomCow)entity); 
/*     */             return new CraftCow(server, (EntityCow)entity);
/*     */           } 
/*     */           if (entity instanceof EntityPig)
/*     */             return new CraftPig(server, (EntityPig)entity); 
/*     */           if (entity instanceof net.minecraft.server.v1_7_R4.EntityTameableAnimal) {
/*     */             if (entity instanceof EntityWolf)
/*     */               return new CraftWolf(server, (EntityWolf)entity); 
/*     */             if (entity instanceof EntityOcelot)
/*     */               return new CraftOcelot(server, (EntityOcelot)entity); 
/*     */           } else {
/*     */             if (entity instanceof EntitySheep)
/*     */               return new CraftSheep(server, (EntitySheep)entity); 
/*     */             if (entity instanceof EntityHorse)
/*     */               return new CraftHorse(server, (EntityHorse)entity); 
/*     */             return new CraftAnimals(server, (EntityAnimal)entity);
/*     */           } 
/*     */         } else {
/*     */           if (entity instanceof EntityMonster) {
/*     */             if (entity instanceof EntityZombie) {
/*     */               if (entity instanceof EntityPigZombie)
/*     */                 return new CraftPigZombie(server, (EntityPigZombie)entity); 
/*     */               return new CraftZombie(server, (EntityZombie)entity);
/*     */             } 
/*     */             if (entity instanceof EntityCreeper)
/*     */               return new CraftCreeper(server, (EntityCreeper)entity); 
/*     */             if (entity instanceof EntityEnderman)
/*     */               return new CraftEnderman(server, (EntityEnderman)entity); 
/*     */             if (entity instanceof EntitySilverfish)
/*     */               return new CraftSilverfish(server, (EntitySilverfish)entity); 
/*     */             if (entity instanceof EntityGiantZombie)
/*     */               return new CraftGiant(server, (EntityGiantZombie)entity); 
/*     */             if (entity instanceof EntitySkeleton)
/*     */               return new CraftSkeleton(server, (EntitySkeleton)entity); 
/*     */             if (entity instanceof EntityBlaze)
/*     */               return new CraftBlaze(server, (EntityBlaze)entity); 
/*     */             if (entity instanceof EntityWitch)
/*     */               return new CraftWitch(server, (EntityWitch)entity); 
/*     */             if (entity instanceof EntityWither)
/*     */               return new CraftWither(server, (EntityWither)entity); 
/*     */             if (entity instanceof EntitySpider) {
/*     */               if (entity instanceof EntityCaveSpider)
/*     */                 return new CraftCaveSpider(server, (EntityCaveSpider)entity); 
/*     */               return new CraftSpider(server, (EntitySpider)entity);
/*     */             } 
/*     */             return new CraftMonster(server, (EntityMonster)entity);
/*     */           } 
/*     */           if (entity instanceof EntityWaterAnimal) {
/*     */             if (entity instanceof EntitySquid)
/*     */               return new CraftSquid(server, (EntitySquid)entity); 
/*     */             return new CraftWaterMob(server, (EntityWaterAnimal)entity);
/*     */           } 
/*     */           if (entity instanceof net.minecraft.server.v1_7_R4.EntityGolem) {
/*     */             if (entity instanceof EntitySnowman)
/*     */               return new CraftSnowman(server, (EntitySnowman)entity); 
/*     */             if (entity instanceof EntityIronGolem)
/*     */               return new CraftIronGolem(server, (EntityIronGolem)entity); 
/*     */           } else {
/*     */             if (entity instanceof EntityVillager)
/*     */               return new CraftVillager(server, (EntityVillager)entity); 
/*     */             return new CraftCreature(server, (EntityCreature)entity);
/*     */           } 
/*     */         } 
/*     */       } else {
/*     */         if (entity instanceof EntitySlime) {
/*     */           if (entity instanceof EntityMagmaCube)
/*     */             return new CraftMagmaCube(server, (EntityMagmaCube)entity); 
/*     */           return new CraftSlime(server, (EntitySlime)entity);
/*     */         } 
/*     */         if (entity instanceof EntityFlying) {
/*     */           if (entity instanceof EntityGhast)
/*     */             return new CraftGhast(server, (EntityGhast)entity); 
/*     */           return new CraftFlying(server, (EntityFlying)entity);
/*     */         } 
/*     */         if (entity instanceof EntityEnderDragon)
/*     */           return new CraftEnderDragon(server, (EntityEnderDragon)entity); 
/*     */         if (entity instanceof EntityAmbient) {
/*     */           if (entity instanceof EntityBat)
/*     */             return new CraftBat(server, (EntityBat)entity); 
/*     */           return new CraftAmbient(server, (EntityAmbient)entity);
/*     */         } 
/*     */         return new CraftLivingEntity(server, (EntityLiving)entity);
/*     */       } 
/*     */     } else {
/*     */       if (entity instanceof EntityComplexPart) {
/*     */         EntityComplexPart part = (EntityComplexPart)entity;
/*     */         if (part.owner instanceof EntityEnderDragon)
/*     */           return new CraftEnderDragonPart(server, (EntityComplexPart)entity); 
/*     */         return new CraftComplexPart(server, (EntityComplexPart)entity);
/*     */       } 
/*     */       if (entity instanceof EntityExperienceOrb)
/*     */         return new CraftExperienceOrb(server, (EntityExperienceOrb)entity); 
/*     */       if (entity instanceof EntityArrow)
/*     */         return new CraftArrow(server, (EntityArrow)entity); 
/*     */       if (entity instanceof EntityBoat)
/*     */         return new CraftBoat(server, (EntityBoat)entity); 
/*     */       if (entity instanceof net.minecraft.server.v1_7_R4.EntityProjectile) {
/*     */         if (entity instanceof EntityEgg)
/*     */           return new CraftEgg(server, (EntityEgg)entity); 
/*     */         if (entity instanceof EntitySnowball)
/*     */           return new CraftSnowball(server, (EntitySnowball)entity); 
/*     */         if (entity instanceof EntityPotion)
/*     */           return new CraftThrownPotion(server, (EntityPotion)entity); 
/*     */         if (entity instanceof EntityEnderPearl)
/*     */           return new CraftEnderPearl(server, (EntityEnderPearl)entity); 
/*     */         if (entity instanceof EntityThrownExpBottle)
/*     */           return new CraftThrownExpBottle(server, (EntityThrownExpBottle)entity); 
/*     */       } else {
/*     */         if (entity instanceof EntityFallingBlock)
/*     */           return new CraftFallingSand(server, (EntityFallingBlock)entity); 
/*     */         if (entity instanceof EntityFireball) {
/*     */           if (entity instanceof EntitySmallFireball)
/*     */             return new CraftSmallFireball(server, (EntitySmallFireball)entity); 
/*     */           if (entity instanceof EntityLargeFireball)
/*     */             return new CraftLargeFireball(server, (EntityLargeFireball)entity); 
/*     */           if (entity instanceof EntityWitherSkull)
/*     */             return new CraftWitherSkull(server, (EntityWitherSkull)entity); 
/*     */           return new CraftFireball(server, (EntityFireball)entity);
/*     */         } 
/*     */         if (entity instanceof EntityEnderSignal)
/*     */           return new CraftEnderSignal(server, (EntityEnderSignal)entity); 
/*     */         if (entity instanceof EntityEnderCrystal)
/*     */           return new CraftEnderCrystal(server, (EntityEnderCrystal)entity); 
/*     */         if (entity instanceof EntityFishingHook)
/*     */           return new CraftFish(server, (EntityFishingHook)entity); 
/*     */         if (entity instanceof EntityItem)
/*     */           return new CraftItem(server, (EntityItem)entity); 
/*     */         if (entity instanceof EntityWeather) {
/*     */           if (entity instanceof EntityLightning)
/*     */             return new CraftLightningStrike(server, (EntityLightning)entity); 
/*     */           return new CraftWeather(server, (EntityWeather)entity);
/*     */         } 
/*     */         if (entity instanceof EntityMinecartAbstract) {
/*     */           if (entity instanceof EntityMinecartFurnace)
/*     */             return new CraftMinecartFurnace(server, (EntityMinecartFurnace)entity); 
/*     */           if (entity instanceof EntityMinecartChest)
/*     */             return new CraftMinecartChest(server, (EntityMinecartChest)entity); 
/*     */           if (entity instanceof EntityMinecartTNT)
/*     */             return new CraftMinecartTNT(server, (EntityMinecartTNT)entity); 
/*     */           if (entity instanceof EntityMinecartHopper)
/*     */             return new CraftMinecartHopper(server, (EntityMinecartHopper)entity); 
/*     */           if (entity instanceof EntityMinecartMobSpawner)
/*     */             return new CraftMinecartMobSpawner(server, (EntityMinecartMobSpawner)entity); 
/*     */           if (entity instanceof net.minecraft.server.v1_7_R4.EntityMinecartRideable)
/*     */             return new CraftMinecartRideable(server, (EntityMinecartAbstract)entity); 
/*     */           if (entity instanceof EntityMinecartCommandBlock)
/*     */             return new CraftMinecartCommand(server, (EntityMinecartCommandBlock)entity); 
/*     */         } else {
/*     */           if (entity instanceof EntityHanging) {
/*     */             if (entity instanceof EntityPainting)
/*     */               return new CraftPainting(server, (EntityPainting)entity); 
/*     */             if (entity instanceof EntityItemFrame)
/*     */               return new CraftItemFrame(server, (EntityItemFrame)entity); 
/*     */             if (entity instanceof EntityLeash)
/*     */               return new CraftLeash(server, (EntityLeash)entity); 
/*     */             return new CraftHanging(server, (EntityHanging)entity);
/*     */           } 
/*     */           if (entity instanceof EntityTNTPrimed)
/*     */             return new CraftTNTPrimed(server, (EntityTNTPrimed)entity); 
/*     */           if (entity instanceof EntityFireworks)
/*     */             return new CraftFirework(server, (EntityFireworks)entity); 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     throw new AssertionError(("Unknown entity " + entity == null) ? null : entity.getClass());
/*     */   }
/*     */   
/*     */   public Location getLocation() {
/*     */     return new Location(getWorld(), this.entity.locX, this.entity.locY, this.entity.locZ, this.entity.yaw, this.entity.pitch);
/*     */   }
/*     */   
/*     */   public Location getLocation(Location loc) {
/*     */     if (loc != null) {
/*     */       loc.setWorld(getWorld());
/*     */       loc.setX(this.entity.locX);
/*     */       loc.setY(this.entity.locY);
/*     */       loc.setZ(this.entity.locZ);
/*     */       loc.setYaw(this.entity.yaw);
/*     */       loc.setPitch(this.entity.pitch);
/*     */     } 
/*     */     return loc;
/*     */   }
/*     */   
/*     */   public Vector getVelocity() {
/*     */     return new Vector(this.entity.motX, this.entity.motY, this.entity.motZ);
/*     */   }
/*     */   
/*     */   public void setVelocity(Vector vel) {
/*     */     this.entity.motX = vel.getX();
/*     */     this.entity.motY = vel.getY();
/*     */     this.entity.motZ = vel.getZ();
/*     */     this.entity.velocityChanged = true;
/*     */   }
/*     */   
/*     */   public boolean isOnGround() {
/*     */     if (this.entity instanceof EntityArrow)
/*     */       return ((EntityArrow)this.entity).isInGround(); 
/*     */     return this.entity.onGround;
/*     */   }
/*     */   
/*     */   public World getWorld() {
/*     */     return (World)this.entity.world.getWorld();
/*     */   }
/*     */   
/*     */   public boolean teleport(Location location) {
/*     */     return teleport(location, PlayerTeleportEvent.TeleportCause.PLUGIN);
/*     */   }
/*     */   
/*     */   public boolean teleport(Location location, PlayerTeleportEvent.TeleportCause cause) {
/*     */     if (this.entity.passenger != null || this.entity.dead)
/*     */       return false; 
/*     */     this.entity.mount(null);
/*     */     if (!location.getWorld().equals(getWorld())) {
/*     */       this.entity.teleportTo(location, cause.equals(PlayerTeleportEvent.TeleportCause.NETHER_PORTAL));
/*     */       return true;
/*     */     } 
/*     */     this.entity.setLocation(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch());
/*     */     return true;
/*     */   }
/*     */   
/*     */   public boolean teleport(Entity destination) {
/*     */     return teleport(destination.getLocation());
/*     */   }
/*     */   
/*     */   public boolean teleport(Entity destination, PlayerTeleportEvent.TeleportCause cause) {
/*     */     return teleport(destination.getLocation(), cause);
/*     */   }
/*     */   
/*     */   public List<Entity> getNearbyEntities(double x, double y, double z) {
/*     */     List<Entity> notchEntityList = this.entity.world.getEntities(this.entity, this.entity.boundingBox.grow(x, y, z));
/*     */     List<Entity> bukkitEntityList = new ArrayList<Entity>(notchEntityList.size());
/*     */     for (Entity e : notchEntityList)
/*     */       bukkitEntityList.add(e.getBukkitEntity()); 
/*     */     return bukkitEntityList;
/*     */   }
/*     */   
/*     */   public int getEntityId() {
/*     */     return this.entity.getId();
/*     */   }
/*     */   
/*     */   public int getFireTicks() {
/*     */     return this.entity.fireTicks;
/*     */   }
/*     */   
/*     */   public int getMaxFireTicks() {
/*     */     return this.entity.maxFireTicks;
/*     */   }
/*     */   
/*     */   public void setFireTicks(int ticks) {
/*     */     this.entity.fireTicks = ticks;
/*     */   }
/*     */   
/*     */   public void remove() {
/*     */     this.entity.dead = true;
/*     */   }
/*     */   
/*     */   public boolean isDead() {
/*     */     return !this.entity.isAlive();
/*     */   }
/*     */   
/*     */   public boolean isValid() {
/*     */     return (this.entity.isAlive() && this.entity.valid);
/*     */   }
/*     */   
/*     */   public Server getServer() {
/*     */     return (Server)this.server;
/*     */   }
/*     */   
/*     */   public Vector getMomentum() {
/*     */     return getVelocity();
/*     */   }
/*     */   
/*     */   public void setMomentum(Vector value) {
/*     */     setVelocity(value);
/*     */   }
/*     */   
/*     */   public Entity getPassenger() {
/*     */     return isEmpty() ? null : (getHandle()).passenger.getBukkitEntity();
/*     */   }
/*     */   
/*     */   public boolean setPassenger(Entity passenger) {
/*     */     if (passenger instanceof CraftEntity) {
/*     */       ((CraftEntity)passenger).getHandle().setPassengerOf(getHandle());
/*     */       return true;
/*     */     } 
/*     */     return false;
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/*     */     return ((getHandle()).passenger == null);
/*     */   }
/*     */   
/*     */   public boolean eject() {
/*     */     if ((getHandle()).passenger == null)
/*     */       return false; 
/*     */     (getHandle()).passenger.setPassengerOf(null);
/*     */     return true;
/*     */   }
/*     */   
/*     */   public float getFallDistance() {
/*     */     return (getHandle()).fallDistance;
/*     */   }
/*     */   
/*     */   public void setFallDistance(float distance) {
/*     */     (getHandle()).fallDistance = distance;
/*     */   }
/*     */   
/*     */   public void setLastDamageCause(EntityDamageEvent event) {
/*     */     this.lastDamageEvent = event;
/*     */   }
/*     */   
/*     */   public EntityDamageEvent getLastDamageCause() {
/*     */     return this.lastDamageEvent;
/*     */   }
/*     */   
/*     */   public UUID getUniqueId() {
/*     */     return (getHandle()).uniqueID;
/*     */   }
/*     */   
/*     */   public int getTicksLived() {
/*     */     return (getHandle()).ticksLived;
/*     */   }
/*     */   
/*     */   public void setTicksLived(int value) {
/*     */     if (value <= 0)
/*     */       throw new IllegalArgumentException("Age must be at least 1 tick"); 
/*     */     (getHandle()).ticksLived = value;
/*     */   }
/*     */   
/*     */   public Entity getHandle() {
/*     */     return this.entity;
/*     */   }
/*     */   
/*     */   public void playEffect(EntityEffect type) {
/*     */     (getHandle()).world.broadcastEntityEffect(getHandle(), type.getData());
/*     */   }
/*     */   
/*     */   public void setHandle(Entity entity) {
/*     */     this.entity = entity;
/*     */   }
/*     */   
/*     */   public String toString() {
/*     */     return "CraftEntity{id=" + getEntityId() + '}';
/*     */   }
/*     */   
/*     */   public boolean equals(Object obj) {
/*     */     if (obj == null)
/*     */       return false; 
/*     */     if (getClass() != obj.getClass())
/*     */       return false; 
/*     */     CraftEntity other = (CraftEntity)obj;
/*     */     return (getEntityId() == other.getEntityId());
/*     */   }
/*     */   
/*     */   public int hashCode() {
/*     */     int hash = 7;
/*     */     hash = 29 * hash + getEntityId();
/*     */     return hash;
/*     */   }
/*     */   
/*     */   public void setMetadata(String metadataKey, MetadataValue newMetadataValue) {
/*     */     this.server.getEntityMetadata().setMetadata(this, metadataKey, newMetadataValue);
/*     */   }
/*     */   
/*     */   public List<MetadataValue> getMetadata(String metadataKey) {
/*     */     return this.server.getEntityMetadata().getMetadata(this, metadataKey);
/*     */   }
/*     */   
/*     */   public boolean hasMetadata(String metadataKey) {
/*     */     return this.server.getEntityMetadata().hasMetadata(this, metadataKey);
/*     */   }
/*     */   
/*     */   public void removeMetadata(String metadataKey, Plugin owningPlugin) {
/*     */     this.server.getEntityMetadata().removeMetadata(this, metadataKey, owningPlugin);
/*     */   }
/*     */   
/*     */   public boolean isInsideVehicle() {
/*     */     return ((getHandle()).vehicle != null);
/*     */   }
/*     */   
/*     */   public boolean leaveVehicle() {
/*     */     if ((getHandle()).vehicle == null)
/*     */       return false; 
/*     */     getHandle().setPassengerOf(null);
/*     */     return true;
/*     */   }
/*     */   
/*     */   public Entity getVehicle() {
/*     */     if ((getHandle()).vehicle == null)
/*     */       return null; 
/*     */     return (getHandle()).vehicle.getBukkitEntity();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\entity\CraftEntity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */