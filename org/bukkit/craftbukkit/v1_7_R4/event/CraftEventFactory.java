/*     */ package org.bukkit.craftbukkit.v1_7_R4.event;
/*     */ import com.google.common.base.Function;
/*     */ import java.util.EnumMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.server.v1_7_R4.Block;
/*     */ import net.minecraft.server.v1_7_R4.Container;
/*     */ import net.minecraft.server.v1_7_R4.DamageSource;
/*     */ import net.minecraft.server.v1_7_R4.Entity;
/*     */ import net.minecraft.server.v1_7_R4.EntityHuman;
/*     */ import net.minecraft.server.v1_7_R4.EntityInsentient;
/*     */ import net.minecraft.server.v1_7_R4.EntityLiving;
/*     */ import net.minecraft.server.v1_7_R4.EntityPlayer;
/*     */ import net.minecraft.server.v1_7_R4.Item;
/*     */ import net.minecraft.server.v1_7_R4.ItemStack;
/*     */ import net.minecraft.server.v1_7_R4.Items;
/*     */ import net.minecraft.server.v1_7_R4.Statistic;
/*     */ import net.minecraft.server.v1_7_R4.World;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Statistic;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.block.BlockFace;
/*     */ import org.bukkit.block.BlockState;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.CraftStatistic;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.block.CraftBlockState;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftEntity;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftHumanEntity;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftLivingEntity;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.entity.CraftPlayer;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.EntityType;
/*     */ import org.bukkit.entity.Item;
/*     */ import org.bukkit.entity.LivingEntity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.entity.Projectile;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.event.block.BlockDamageEvent;
/*     */ import org.bukkit.event.block.BlockFadeEvent;
/*     */ import org.bukkit.event.block.BlockIgniteEvent;
/*     */ import org.bukkit.event.block.BlockMultiPlaceEvent;
/*     */ import org.bukkit.event.block.BlockPlaceEvent;
/*     */ import org.bukkit.event.block.BlockRedstoneEvent;
/*     */ import org.bukkit.event.block.NotePlayEvent;
/*     */ import org.bukkit.event.entity.CreatureSpawnEvent;
/*     */ import org.bukkit.event.entity.CreeperPowerEvent;
/*     */ import org.bukkit.event.entity.EntityBreakDoorEvent;
/*     */ import org.bukkit.event.entity.EntityChangeBlockEvent;
/*     */ import org.bukkit.event.entity.EntityDamageByBlockEvent;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.event.entity.EntityDamageEvent;
/*     */ import org.bukkit.event.entity.EntityDeathEvent;
/*     */ import org.bukkit.event.entity.EntityShootBowEvent;
/*     */ import org.bukkit.event.entity.EntityTameEvent;
/*     */ import org.bukkit.event.entity.EntityTargetEvent;
/*     */ import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
/*     */ import org.bukkit.event.entity.ExpBottleEvent;
/*     */ import org.bukkit.event.entity.FoodLevelChangeEvent;
/*     */ import org.bukkit.event.entity.HorseJumpEvent;
/*     */ import org.bukkit.event.entity.ItemDespawnEvent;
/*     */ import org.bukkit.event.entity.ItemSpawnEvent;
/*     */ import org.bukkit.event.entity.PigZapEvent;
/*     */ import org.bukkit.event.entity.PlayerDeathEvent;
/*     */ import org.bukkit.event.entity.PlayerLeashEntityEvent;
/*     */ import org.bukkit.event.entity.PotionSplashEvent;
/*     */ import org.bukkit.event.entity.ProjectileHitEvent;
/*     */ import org.bukkit.event.entity.ProjectileLaunchEvent;
/*     */ import org.bukkit.event.entity.SpawnerSpawnEvent;
/*     */ import org.bukkit.event.inventory.PrepareItemCraftEvent;
/*     */ import org.bukkit.event.player.PlayerBucketEmptyEvent;
/*     */ import org.bukkit.event.player.PlayerBucketFillEvent;
/*     */ import org.bukkit.event.player.PlayerEditBookEvent;
/*     */ import org.bukkit.event.player.PlayerExpChangeEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.event.player.PlayerLevelChangeEvent;
/*     */ import org.bukkit.event.player.PlayerStatisticIncrementEvent;
/*     */ import org.bukkit.event.player.PlayerUnleashEntityEvent;
/*     */ import org.bukkit.event.server.ServerListPingEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public class CraftEventFactory {
/*  86 */   public static final DamageSource MELTING = CraftDamageSource.copyOf(DamageSource.BURN);
/*  87 */   public static final DamageSource POISON = CraftDamageSource.copyOf(DamageSource.MAGIC);
/*     */   
/*     */   public static Block blockDamage;
/*     */   public static Entity entityDamage;
/*     */   
/*     */   private static boolean canBuild(CraftWorld world, Player player, int x, int z) {
/*  93 */     WorldServer worldServer = world.getHandle();
/*  94 */     int spawnSize = Bukkit.getServer().getSpawnRadius();
/*     */     
/*  96 */     if ((world.getHandle()).dimension != 0) return true; 
/*  97 */     if (spawnSize <= 0) return true; 
/*  98 */     if (((CraftServer)Bukkit.getServer()).getHandle().getOPs().isEmpty()) return true; 
/*  99 */     if (player.isOp()) return true;
/*     */     
/* 101 */     ChunkCoordinates chunkcoordinates = worldServer.getSpawn();
/*     */     
/* 103 */     int distanceFromSpawn = Math.max(Math.abs(x - chunkcoordinates.x), Math.abs(z - chunkcoordinates.z));
/* 104 */     return (distanceFromSpawn > spawnSize);
/*     */   }
/*     */   
/*     */   public static <T extends Event> T callEvent(T event) {
/* 108 */     Bukkit.getServer().getPluginManager().callEvent((Event)event);
/* 109 */     return event;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BlockMultiPlaceEvent callBlockMultiPlaceEvent(World world, EntityHuman who, List<BlockState> blockStates, int clickedX, int clickedY, int clickedZ) {
/* 116 */     CraftWorld craftWorld = world.getWorld();
/* 117 */     CraftServer craftServer = world.getServer();
/* 118 */     Player player = (who == null) ? null : (Player)who.getBukkitEntity();
/*     */     
/* 120 */     Block blockClicked = craftWorld.getBlockAt(clickedX, clickedY, clickedZ);
/*     */     
/* 122 */     boolean canBuild = true;
/* 123 */     for (int i = 0; i < blockStates.size(); i++) {
/* 124 */       if (!canBuild(craftWorld, player, ((BlockState)blockStates.get(i)).getX(), ((BlockState)blockStates.get(i)).getZ())) {
/* 125 */         canBuild = false;
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 130 */     BlockMultiPlaceEvent event = new BlockMultiPlaceEvent(blockStates, blockClicked, player.getItemInHand(), player, canBuild);
/* 131 */     craftServer.getPluginManager().callEvent((Event)event);
/*     */     
/* 133 */     return event;
/*     */   }
/*     */   
/*     */   public static BlockPlaceEvent callBlockPlaceEvent(World world, EntityHuman who, BlockState replacedBlockState, int clickedX, int clickedY, int clickedZ) {
/* 137 */     CraftWorld craftWorld = world.getWorld();
/* 138 */     CraftServer craftServer = world.getServer();
/*     */     
/* 140 */     Player player = (who == null) ? null : (Player)who.getBukkitEntity();
/*     */     
/* 142 */     Block blockClicked = craftWorld.getBlockAt(clickedX, clickedY, clickedZ);
/* 143 */     Block placedBlock = replacedBlockState.getBlock();
/*     */     
/* 145 */     boolean canBuild = canBuild(craftWorld, player, placedBlock.getX(), placedBlock.getZ());
/*     */     
/* 147 */     BlockPlaceEvent event = new BlockPlaceEvent(placedBlock, replacedBlockState, blockClicked, player.getItemInHand(), player, canBuild);
/* 148 */     craftServer.getPluginManager().callEvent((Event)event);
/*     */     
/* 150 */     return event;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SpawnerSpawnEvent callSpawnerSpawnEvent(Entity spawnee, int spawnerX, int spawnerY, int spawnerZ) {
/* 157 */     CraftEntity entity = spawnee.getBukkitEntity();
/* 158 */     BlockState state = entity.getWorld().getBlockAt(spawnerX, spawnerY, spawnerZ).getState();
/*     */     
/* 160 */     if (!(state instanceof CreatureSpawner)) {
/* 161 */       state = null;
/*     */     }
/*     */     
/* 164 */     SpawnerSpawnEvent event = new SpawnerSpawnEvent((Entity)entity, (CreatureSpawner)state);
/* 165 */     entity.getServer().getPluginManager().callEvent((Event)event);
/* 166 */     return event;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PlayerBucketEmptyEvent callPlayerBucketEmptyEvent(EntityHuman who, int clickedX, int clickedY, int clickedZ, int clickedFace, ItemStack itemInHand) {
/* 173 */     return (PlayerBucketEmptyEvent)getPlayerBucketEvent(false, who, clickedX, clickedY, clickedZ, clickedFace, itemInHand, Items.BUCKET);
/*     */   }
/*     */   
/*     */   public static PlayerBucketFillEvent callPlayerBucketFillEvent(EntityHuman who, int clickedX, int clickedY, int clickedZ, int clickedFace, ItemStack itemInHand, Item bucket) {
/* 177 */     return (PlayerBucketFillEvent)getPlayerBucketEvent(true, who, clickedX, clickedY, clickedZ, clickedFace, itemInHand, bucket);
/*     */   }
/*     */   private static PlayerEvent getPlayerBucketEvent(boolean isFilling, EntityHuman who, int clickedX, int clickedY, int clickedZ, int clickedFace, ItemStack itemstack, Item item) {
/*     */     PlayerBucketEmptyEvent playerBucketEmptyEvent;
/* 181 */     Player player = (who == null) ? null : (Player)who.getBukkitEntity();
/* 182 */     CraftItemStack itemInHand = CraftItemStack.asNewCraftStack(item);
/* 183 */     Material bucket = CraftMagicNumbers.getMaterial(itemstack.getItem());
/*     */     
/* 185 */     CraftWorld craftWorld = (CraftWorld)player.getWorld();
/* 186 */     CraftServer craftServer = (CraftServer)player.getServer();
/*     */     
/* 188 */     Block blockClicked = craftWorld.getBlockAt(clickedX, clickedY, clickedZ);
/* 189 */     BlockFace blockFace = CraftBlock.notchToBlockFace(clickedFace);
/*     */     
/* 191 */     PlayerEvent event = null;
/* 192 */     if (isFilling) {
/* 193 */       PlayerBucketFillEvent playerBucketFillEvent = new PlayerBucketFillEvent(player, blockClicked, blockFace, bucket, (ItemStack)itemInHand);
/* 194 */       playerBucketFillEvent.setCancelled(!canBuild(craftWorld, player, clickedX, clickedZ));
/*     */     } else {
/* 196 */       playerBucketEmptyEvent = new PlayerBucketEmptyEvent(player, blockClicked, blockFace, bucket, (ItemStack)itemInHand);
/* 197 */       playerBucketEmptyEvent.setCancelled(!canBuild(craftWorld, player, clickedX, clickedZ));
/*     */     } 
/*     */     
/* 200 */     craftServer.getPluginManager().callEvent((Event)playerBucketEmptyEvent);
/*     */     
/* 202 */     return (PlayerEvent)playerBucketEmptyEvent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PlayerInteractEvent callPlayerInteractEvent(EntityHuman who, Action action, ItemStack itemstack) {
/* 209 */     if (action != Action.LEFT_CLICK_AIR && action != Action.RIGHT_CLICK_AIR) {
/* 210 */       throw new IllegalArgumentException(String.format("%s performing %s with %s", new Object[] { who, action, itemstack }));
/*     */     }
/* 212 */     return callPlayerInteractEvent(who, action, 0, 256, 0, 0, itemstack);
/*     */   }
/*     */   
/*     */   public static PlayerInteractEvent callPlayerInteractEvent(EntityHuman who, Action action, int clickedX, int clickedY, int clickedZ, int clickedFace, ItemStack itemstack) {
/* 216 */     Player player = (who == null) ? null : (Player)who.getBukkitEntity();
/* 217 */     CraftItemStack itemInHand = CraftItemStack.asCraftMirror(itemstack);
/*     */     
/* 219 */     CraftWorld craftWorld = (CraftWorld)player.getWorld();
/* 220 */     CraftServer craftServer = (CraftServer)player.getServer();
/*     */     
/* 222 */     Block blockClicked = craftWorld.getBlockAt(clickedX, clickedY, clickedZ);
/* 223 */     BlockFace blockFace = CraftBlock.notchToBlockFace(clickedFace);
/*     */     
/* 225 */     if (clickedY > 255) {
/* 226 */       blockClicked = null;
/* 227 */       switch (action) {
/*     */         case FALL_ONE_CM:
/* 229 */           action = Action.LEFT_CLICK_AIR;
/*     */           break;
/*     */         case BOAT_ONE_CM:
/* 232 */           action = Action.RIGHT_CLICK_AIR;
/*     */           break;
/*     */       } 
/*     */     
/*     */     } 
/* 237 */     if (itemInHand.getType() == Material.AIR || itemInHand.getAmount() == 0) {
/* 238 */       itemInHand = null;
/*     */     }
/*     */     
/* 241 */     PlayerInteractEvent event = new PlayerInteractEvent(player, action, (ItemStack)itemInHand, blockClicked, blockFace);
/* 242 */     craftServer.getPluginManager().callEvent((Event)event);
/*     */     
/* 244 */     return event;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EntityShootBowEvent callEntityShootBowEvent(EntityLiving who, ItemStack itemstack, EntityArrow entityArrow, float force) {
/* 251 */     LivingEntity shooter = (LivingEntity)who.getBukkitEntity();
/* 252 */     CraftItemStack itemInHand = CraftItemStack.asCraftMirror(itemstack);
/* 253 */     Arrow arrow = (Arrow)entityArrow.getBukkitEntity();
/*     */     
/* 255 */     if (itemInHand != null && (itemInHand.getType() == Material.AIR || itemInHand.getAmount() == 0)) {
/* 256 */       itemInHand = null;
/*     */     }
/*     */     
/* 259 */     EntityShootBowEvent event = new EntityShootBowEvent(shooter, (ItemStack)itemInHand, (Projectile)arrow, force);
/* 260 */     Bukkit.getPluginManager().callEvent((Event)event);
/*     */     
/* 262 */     return event;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BlockDamageEvent callBlockDamageEvent(EntityHuman who, int x, int y, int z, ItemStack itemstack, boolean instaBreak) {
/* 269 */     Player player = (who == null) ? null : (Player)who.getBukkitEntity();
/* 270 */     CraftItemStack itemInHand = CraftItemStack.asCraftMirror(itemstack);
/*     */     
/* 272 */     CraftWorld craftWorld = (CraftWorld)player.getWorld();
/* 273 */     CraftServer craftServer = (CraftServer)player.getServer();
/*     */     
/* 275 */     Block blockClicked = craftWorld.getBlockAt(x, y, z);
/*     */     
/* 277 */     BlockDamageEvent event = new BlockDamageEvent(player, blockClicked, (ItemStack)itemInHand, instaBreak);
/* 278 */     craftServer.getPluginManager().callEvent((Event)event);
/*     */     
/* 280 */     return event;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static CreatureSpawnEvent callCreatureSpawnEvent(EntityLiving entityliving, CreatureSpawnEvent.SpawnReason spawnReason) {
/* 287 */     LivingEntity entity = (LivingEntity)entityliving.getBukkitEntity();
/* 288 */     CraftServer craftServer = (CraftServer)entity.getServer();
/*     */     
/* 290 */     CreatureSpawnEvent event = new CreatureSpawnEvent(entity, spawnReason);
/* 291 */     craftServer.getPluginManager().callEvent((Event)event);
/* 292 */     return event;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static EntityTameEvent callEntityTameEvent(EntityInsentient entity, EntityHuman tamer) {
/* 299 */     CraftEntity craftEntity = entity.getBukkitEntity();
/* 300 */     CraftHumanEntity craftHumanEntity = (tamer != null) ? tamer.getBukkitEntity() : null;
/* 301 */     CraftServer craftServer = (CraftServer)craftEntity.getServer();
/*     */     
/* 303 */     entity.persistent = true;
/*     */     
/* 305 */     EntityTameEvent event = new EntityTameEvent((LivingEntity)craftEntity, (AnimalTamer)craftHumanEntity);
/* 306 */     craftServer.getPluginManager().callEvent((Event)event);
/* 307 */     return event;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ItemSpawnEvent callItemSpawnEvent(EntityItem entityitem) {
/* 314 */     Item entity = (Item)entityitem.getBukkitEntity();
/* 315 */     CraftServer craftServer = (CraftServer)entity.getServer();
/*     */     
/* 317 */     ItemSpawnEvent event = new ItemSpawnEvent(entity, entity.getLocation());
/*     */     
/* 319 */     craftServer.getPluginManager().callEvent((Event)event);
/* 320 */     return event;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ItemDespawnEvent callItemDespawnEvent(EntityItem entityitem) {
/* 327 */     Item entity = (Item)entityitem.getBukkitEntity();
/*     */     
/* 329 */     ItemDespawnEvent event = new ItemDespawnEvent(entity, entity.getLocation());
/*     */     
/* 331 */     entity.getServer().getPluginManager().callEvent((Event)event);
/* 332 */     return event;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PotionSplashEvent callPotionSplashEvent(EntityPotion potion, Map<LivingEntity, Double> affectedEntities) {
/* 339 */     ThrownPotion thrownPotion = (ThrownPotion)potion.getBukkitEntity();
/*     */     
/* 341 */     PotionSplashEvent event = new PotionSplashEvent(thrownPotion, affectedEntities);
/* 342 */     Bukkit.getPluginManager().callEvent((Event)event);
/* 343 */     return event;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BlockFadeEvent callBlockFadeEvent(Block block, Block type) {
/* 350 */     BlockState state = block.getState();
/* 351 */     state.setTypeId(Block.getId(type));
/*     */     
/* 353 */     BlockFadeEvent event = new BlockFadeEvent(block, state);
/* 354 */     Bukkit.getPluginManager().callEvent((Event)event);
/* 355 */     return event;
/*     */   }
/*     */   
/*     */   public static void handleBlockSpreadEvent(Block block, Block source, Block type, int data) {
/* 359 */     BlockState state = block.getState();
/* 360 */     state.setTypeId(Block.getId(type));
/* 361 */     state.setRawData((byte)data);
/*     */     
/* 363 */     BlockSpreadEvent event = new BlockSpreadEvent(block, source, state);
/* 364 */     Bukkit.getPluginManager().callEvent((Event)event);
/*     */     
/* 366 */     if (!event.isCancelled()) {
/* 367 */       state.update(true);
/*     */     }
/*     */   }
/*     */   
/*     */   public static EntityDeathEvent callEntityDeathEvent(EntityLiving victim) {
/* 372 */     return callEntityDeathEvent(victim, new ArrayList<ItemStack>(0));
/*     */   }
/*     */   
/*     */   public static EntityDeathEvent callEntityDeathEvent(EntityLiving victim, List<ItemStack> drops) {
/* 376 */     CraftLivingEntity entity = (CraftLivingEntity)victim.getBukkitEntity();
/* 377 */     EntityDeathEvent event = new EntityDeathEvent((LivingEntity)entity, drops, victim.getExpReward());
/* 378 */     CraftWorld world = (CraftWorld)entity.getWorld();
/* 379 */     Bukkit.getServer().getPluginManager().callEvent((Event)event);
/*     */     
/* 381 */     victim.expToDrop = event.getDroppedExp();
/*     */     
/* 383 */     for (ItemStack stack : event.getDrops()) {
/* 384 */       if (stack == null || stack.getType() == Material.AIR || stack.getAmount() == 0)
/*     */         continue; 
/* 386 */       world.dropItemNaturally(entity.getLocation(), stack);
/*     */     } 
/*     */     
/* 389 */     return event;
/*     */   }
/*     */   
/*     */   public static PlayerDeathEvent callPlayerDeathEvent(EntityPlayer victim, List<ItemStack> drops, String deathMessage, boolean keepInventory) {
/* 393 */     CraftPlayer entity = victim.getBukkitEntity();
/* 394 */     PlayerDeathEvent event = new PlayerDeathEvent((Player)entity, drops, victim.getExpReward(), 0, deathMessage);
/* 395 */     event.setKeepInventory(keepInventory);
/* 396 */     World world = entity.getWorld();
/* 397 */     Bukkit.getServer().getPluginManager().callEvent((Event)event);
/*     */     
/* 399 */     victim.keepLevel = event.getKeepLevel();
/* 400 */     victim.newLevel = event.getNewLevel();
/* 401 */     victim.newTotalExp = event.getNewTotalExp();
/* 402 */     victim.expToDrop = event.getDroppedExp();
/* 403 */     victim.newExp = event.getNewExp();
/*     */     
/* 405 */     if (event.getKeepInventory()) {
/* 406 */       return event;
/*     */     }
/*     */     
/* 409 */     for (ItemStack stack : event.getDrops()) {
/* 410 */       if (stack == null || stack.getType() == Material.AIR)
/*     */         continue; 
/* 412 */       world.dropItemNaturally(entity.getLocation(), stack);
/*     */     } 
/*     */     
/* 415 */     return event;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ServerListPingEvent callServerListPingEvent(Server craftServer, InetAddress address, String motd, int numPlayers, int maxPlayers) {
/* 422 */     ServerListPingEvent event = new ServerListPingEvent(address, motd, numPlayers, maxPlayers);
/* 423 */     craftServer.getPluginManager().callEvent((Event)event);
/* 424 */     return event;
/*     */   }
/*     */   
/*     */   private static EntityDamageEvent handleEntityDamageEvent(Entity entity, DamageSource source, Map<EntityDamageEvent.DamageModifier, Double> modifiers, Map<EntityDamageEvent.DamageModifier, Function<? super Double, Double>> modifierFunctions) {
/* 428 */     if (source.isExplosion()) {
/*     */       EntityDamageByEntityEvent entityDamageByEntityEvent;
/* 430 */       Entity damager = entityDamage;
/* 431 */       entityDamage = null;
/*     */       
/* 433 */       if (damager == null) {
/* 434 */         EntityDamageByBlockEvent entityDamageByBlockEvent = new EntityDamageByBlockEvent(null, (Entity)entity.getBukkitEntity(), EntityDamageEvent.DamageCause.BLOCK_EXPLOSION, modifiers, modifierFunctions);
/* 435 */       } else if (entity instanceof EntityEnderDragon && ((EntityEnderDragon)entity).bC == damager) {
/* 436 */         EntityDamageEvent event = new EntityDamageEvent((Entity)entity.getBukkitEntity(), EntityDamageEvent.DamageCause.ENTITY_EXPLOSION, modifiers, modifierFunctions);
/*     */       } else {
/* 438 */         EntityDamageEvent.DamageCause damageCause; if (damager instanceof org.bukkit.entity.TNTPrimed) {
/* 439 */           damageCause = EntityDamageEvent.DamageCause.BLOCK_EXPLOSION;
/*     */         } else {
/* 441 */           damageCause = EntityDamageEvent.DamageCause.ENTITY_EXPLOSION;
/*     */         } 
/* 443 */         entityDamageByEntityEvent = new EntityDamageByEntityEvent((Entity)damager.getBukkitEntity(), (Entity)entity.getBukkitEntity(), damageCause, modifiers, modifierFunctions);
/*     */       } 
/*     */       
/* 446 */       callEvent(entityDamageByEntityEvent);
/*     */       
/* 448 */       if (!entityDamageByEntityEvent.isCancelled()) {
/* 449 */         entityDamageByEntityEvent.getEntity().setLastDamageCause((EntityDamageEvent)entityDamageByEntityEvent);
/*     */       }
/* 451 */       return (EntityDamageEvent)entityDamageByEntityEvent;
/* 452 */     }  if (source instanceof net.minecraft.server.v1_7_R4.EntityDamageSource) {
/* 453 */       Entity damager = source.getEntity();
/* 454 */       EntityDamageEvent.DamageCause damageCause = EntityDamageEvent.DamageCause.ENTITY_ATTACK;
/*     */       
/* 456 */       if (source instanceof EntityDamageSourceIndirect) {
/* 457 */         damager = ((EntityDamageSourceIndirect)source).getProximateDamageSource();
/* 458 */         if (damager.getBukkitEntity() instanceof ThrownPotion) {
/* 459 */           damageCause = EntityDamageEvent.DamageCause.MAGIC;
/* 460 */         } else if (damager.getBukkitEntity() instanceof Projectile) {
/* 461 */           damageCause = EntityDamageEvent.DamageCause.PROJECTILE;
/*     */         } 
/* 463 */       } else if ("thorns".equals(source.translationIndex)) {
/* 464 */         damageCause = EntityDamageEvent.DamageCause.THORNS;
/*     */       } 
/*     */       
/* 467 */       return callEntityDamageEvent(damager, entity, damageCause, modifiers, modifierFunctions);
/* 468 */     }  if (source == DamageSource.OUT_OF_WORLD) {
/* 469 */       EntityDamageEvent event = (EntityDamageEvent)callEvent(new EntityDamageByBlockEvent(null, (Entity)entity.getBukkitEntity(), EntityDamageEvent.DamageCause.VOID, modifiers, modifierFunctions));
/* 470 */       if (!event.isCancelled()) {
/* 471 */         event.getEntity().setLastDamageCause(event);
/*     */       }
/* 473 */       return event;
/* 474 */     }  if (source == DamageSource.LAVA) {
/* 475 */       EntityDamageEvent event = (EntityDamageEvent)callEvent(new EntityDamageByBlockEvent(null, (Entity)entity.getBukkitEntity(), EntityDamageEvent.DamageCause.LAVA, modifiers, modifierFunctions));
/* 476 */       if (!event.isCancelled()) {
/* 477 */         event.getEntity().setLastDamageCause(event);
/*     */       }
/* 479 */       return event;
/* 480 */     }  if (blockDamage != null) {
/* 481 */       EntityDamageEvent.DamageCause damageCause = null;
/* 482 */       Block damager = blockDamage;
/* 483 */       blockDamage = null;
/* 484 */       if (source == DamageSource.CACTUS) {
/* 485 */         damageCause = EntityDamageEvent.DamageCause.CONTACT;
/*     */       } else {
/* 487 */         throw new RuntimeException(String.format("Unhandled damage of %s by %s from %s", new Object[] { entity, damager, source.translationIndex }));
/*     */       } 
/* 489 */       EntityDamageEvent event = (EntityDamageEvent)callEvent(new EntityDamageByBlockEvent(damager, (Entity)entity.getBukkitEntity(), damageCause, modifiers, modifierFunctions));
/* 490 */       if (!event.isCancelled()) {
/* 491 */         event.getEntity().setLastDamageCause(event);
/*     */       }
/* 493 */       return event;
/* 494 */     }  if (entityDamage != null) {
/* 495 */       EntityDamageEvent.DamageCause damageCause = null;
/* 496 */       CraftEntity damager = entityDamage.getBukkitEntity();
/* 497 */       entityDamage = null;
/* 498 */       if (source == DamageSource.ANVIL || source == DamageSource.FALLING_BLOCK) {
/* 499 */         damageCause = EntityDamageEvent.DamageCause.FALLING_BLOCK;
/* 500 */       } else if (damager instanceof LightningStrike) {
/* 501 */         damageCause = EntityDamageEvent.DamageCause.LIGHTNING;
/* 502 */       } else if (source == DamageSource.FALL) {
/* 503 */         damageCause = EntityDamageEvent.DamageCause.FALL;
/*     */       } else {
/* 505 */         throw new RuntimeException(String.format("Unhandled damage of %s by %s from %s", new Object[] { entity, damager.getHandle(), source.translationIndex }));
/*     */       } 
/* 507 */       EntityDamageEvent event = (EntityDamageEvent)callEvent(new EntityDamageByEntityEvent((Entity)damager, (Entity)entity.getBukkitEntity(), damageCause, modifiers, modifierFunctions));
/* 508 */       if (!event.isCancelled()) {
/* 509 */         event.getEntity().setLastDamageCause(event);
/*     */       }
/* 511 */       return event;
/*     */     } 
/*     */     
/* 514 */     EntityDamageEvent.DamageCause cause = null;
/* 515 */     if (source == DamageSource.FIRE) {
/* 516 */       cause = EntityDamageEvent.DamageCause.FIRE;
/* 517 */     } else if (source == DamageSource.STARVE) {
/* 518 */       cause = EntityDamageEvent.DamageCause.STARVATION;
/* 519 */     } else if (source == DamageSource.WITHER) {
/* 520 */       cause = EntityDamageEvent.DamageCause.WITHER;
/* 521 */     } else if (source == DamageSource.STUCK) {
/* 522 */       cause = EntityDamageEvent.DamageCause.SUFFOCATION;
/* 523 */     } else if (source == DamageSource.DROWN) {
/* 524 */       cause = EntityDamageEvent.DamageCause.DROWNING;
/* 525 */     } else if (source == DamageSource.BURN) {
/* 526 */       cause = EntityDamageEvent.DamageCause.FIRE_TICK;
/* 527 */     } else if (source == MELTING) {
/* 528 */       cause = EntityDamageEvent.DamageCause.MELTING;
/* 529 */     } else if (source == POISON) {
/* 530 */       cause = EntityDamageEvent.DamageCause.POISON;
/* 531 */     } else if (source == DamageSource.MAGIC) {
/* 532 */       cause = EntityDamageEvent.DamageCause.MAGIC;
/* 533 */     } else if (source == DamageSource.FALL) {
/* 534 */       cause = EntityDamageEvent.DamageCause.FALL;
/* 535 */     } else if (source == DamageSource.GENERIC) {
/* 536 */       return new EntityDamageEvent((Entity)entity.getBukkitEntity(), null, modifiers, modifierFunctions);
/*     */     } 
/*     */     
/* 539 */     if (cause != null) {
/* 540 */       return callEntityDamageEvent(null, entity, cause, modifiers, modifierFunctions);
/*     */     }
/*     */     
/* 543 */     throw new RuntimeException(String.format("Unhandled damage of %s from %s", new Object[] { entity, source.translationIndex }));
/*     */   }
/*     */   
/*     */   private static EntityDamageEvent callEntityDamageEvent(Entity damager, Entity damagee, EntityDamageEvent.DamageCause cause, Map<EntityDamageEvent.DamageModifier, Double> modifiers, Map<EntityDamageEvent.DamageModifier, Function<? super Double, Double>> modifierFunctions) {
/*     */     EntityDamageEvent event;
/* 548 */     if (damager != null) {
/* 549 */       EntityDamageByEntityEvent entityDamageByEntityEvent = new EntityDamageByEntityEvent((Entity)damager.getBukkitEntity(), (Entity)damagee.getBukkitEntity(), cause, modifiers, modifierFunctions);
/*     */     } else {
/* 551 */       event = new EntityDamageEvent((Entity)damagee.getBukkitEntity(), cause, modifiers, modifierFunctions);
/*     */     } 
/*     */     
/* 554 */     callEvent(event);
/*     */     
/* 556 */     if (!event.isCancelled()) {
/* 557 */       event.getEntity().setLastDamageCause(event);
/*     */     }
/*     */     
/* 560 */     return event;
/*     */   }
/*     */   
/* 563 */   private static final Function<? super Double, Double> ZERO = Functions.constant(Double.valueOf(-0.0D));
/*     */   
/*     */   public static EntityDamageEvent handleLivingEntityDamageEvent(Entity damagee, DamageSource source, double rawDamage, double hardHatModifier, double blockingModifier, double armorModifier, double resistanceModifier, double magicModifier, double absorptionModifier, Function<Double, Double> hardHat, Function<Double, Double> blocking, Function<Double, Double> armor, Function<Double, Double> resistance, Function<Double, Double> magic, Function<Double, Double> absorption) {
/* 566 */     Map<EntityDamageEvent.DamageModifier, Double> modifiers = new EnumMap<EntityDamageEvent.DamageModifier, Double>(EntityDamageEvent.DamageModifier.class);
/* 567 */     Map<EntityDamageEvent.DamageModifier, Function<? super Double, Double>> modifierFunctions = new EnumMap<EntityDamageEvent.DamageModifier, Function<? super Double, Double>>(EntityDamageEvent.DamageModifier.class);
/* 568 */     modifiers.put(EntityDamageEvent.DamageModifier.BASE, Double.valueOf(rawDamage));
/* 569 */     modifierFunctions.put(EntityDamageEvent.DamageModifier.BASE, ZERO);
/* 570 */     if (source == DamageSource.FALLING_BLOCK || source == DamageSource.ANVIL) {
/* 571 */       modifiers.put(EntityDamageEvent.DamageModifier.HARD_HAT, Double.valueOf(hardHatModifier));
/* 572 */       modifierFunctions.put(EntityDamageEvent.DamageModifier.HARD_HAT, hardHat);
/*     */     } 
/* 574 */     if (damagee instanceof EntityHuman) {
/* 575 */       modifiers.put(EntityDamageEvent.DamageModifier.BLOCKING, Double.valueOf(blockingModifier));
/* 576 */       modifierFunctions.put(EntityDamageEvent.DamageModifier.BLOCKING, blocking);
/*     */     } 
/* 578 */     modifiers.put(EntityDamageEvent.DamageModifier.ARMOR, Double.valueOf(armorModifier));
/* 579 */     modifierFunctions.put(EntityDamageEvent.DamageModifier.ARMOR, armor);
/* 580 */     modifiers.put(EntityDamageEvent.DamageModifier.RESISTANCE, Double.valueOf(resistanceModifier));
/* 581 */     modifierFunctions.put(EntityDamageEvent.DamageModifier.RESISTANCE, resistance);
/* 582 */     modifiers.put(EntityDamageEvent.DamageModifier.MAGIC, Double.valueOf(magicModifier));
/* 583 */     modifierFunctions.put(EntityDamageEvent.DamageModifier.MAGIC, magic);
/* 584 */     modifiers.put(EntityDamageEvent.DamageModifier.ABSORPTION, Double.valueOf(absorptionModifier));
/* 585 */     modifierFunctions.put(EntityDamageEvent.DamageModifier.ABSORPTION, absorption);
/* 586 */     return handleEntityDamageEvent(damagee, source, modifiers, modifierFunctions);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean handleNonLivingEntityDamageEvent(Entity entity, DamageSource source, double damage) {
/* 591 */     return handleNonLivingEntityDamageEvent(entity, source, damage, true);
/*     */   }
/*     */   
/*     */   public static boolean handleNonLivingEntityDamageEvent(Entity entity, DamageSource source, double damage, boolean cancelOnZeroDamage) {
/* 595 */     if (entity instanceof net.minecraft.server.v1_7_R4.EntityEnderCrystal && !(source instanceof net.minecraft.server.v1_7_R4.EntityDamageSource)) {
/* 596 */       return false;
/*     */     }
/*     */     
/* 599 */     EnumMap<EntityDamageEvent.DamageModifier, Double> modifiers = new EnumMap<EntityDamageEvent.DamageModifier, Double>(EntityDamageEvent.DamageModifier.class);
/* 600 */     EnumMap<EntityDamageEvent.DamageModifier, Function<? super Double, Double>> functions = new EnumMap<EntityDamageEvent.DamageModifier, Function<? super Double, Double>>(EntityDamageEvent.DamageModifier.class);
/*     */     
/* 602 */     modifiers.put(EntityDamageEvent.DamageModifier.BASE, Double.valueOf(damage));
/* 603 */     functions.put(EntityDamageEvent.DamageModifier.BASE, ZERO);
/*     */     
/* 605 */     EntityDamageEvent event = handleEntityDamageEvent(entity, source, modifiers, functions);
/* 606 */     if (event == null) {
/* 607 */       return false;
/*     */     }
/* 609 */     return (event.isCancelled() || (cancelOnZeroDamage && event.getDamage() == 0.0D));
/*     */   }
/*     */   
/*     */   public static PlayerLevelChangeEvent callPlayerLevelChangeEvent(Player player, int oldLevel, int newLevel) {
/* 613 */     PlayerLevelChangeEvent event = new PlayerLevelChangeEvent(player, oldLevel, newLevel);
/* 614 */     Bukkit.getPluginManager().callEvent((Event)event);
/* 615 */     return event;
/*     */   }
/*     */   
/*     */   public static PlayerExpChangeEvent callPlayerExpChangeEvent(EntityHuman entity, int expAmount) {
/* 619 */     Player player = (Player)entity.getBukkitEntity();
/* 620 */     PlayerExpChangeEvent event = new PlayerExpChangeEvent(player, expAmount);
/* 621 */     Bukkit.getPluginManager().callEvent((Event)event);
/* 622 */     return event;
/*     */   }
/*     */   
/*     */   public static void handleBlockGrowEvent(World world, int x, int y, int z, Block type, int data) {
/* 626 */     Block block = world.getWorld().getBlockAt(x, y, z);
/* 627 */     CraftBlockState state = (CraftBlockState)block.getState();
/* 628 */     state.setTypeId(Block.getId(type));
/* 629 */     state.setRawData((byte)data);
/*     */     
/* 631 */     BlockGrowEvent event = new BlockGrowEvent(block, (BlockState)state);
/* 632 */     Bukkit.getPluginManager().callEvent((Event)event);
/*     */     
/* 634 */     if (!event.isCancelled()) {
/* 635 */       state.update(true);
/*     */     }
/*     */   }
/*     */   
/*     */   public static FoodLevelChangeEvent callFoodLevelChangeEvent(EntityHuman entity, int level) {
/* 640 */     FoodLevelChangeEvent event = new FoodLevelChangeEvent((HumanEntity)entity.getBukkitEntity(), level);
/* 641 */     entity.getBukkitEntity().getServer().getPluginManager().callEvent((Event)event);
/* 642 */     return event;
/*     */   }
/*     */   
/*     */   public static PigZapEvent callPigZapEvent(Entity pig, Entity lightning, Entity pigzombie) {
/* 646 */     PigZapEvent event = new PigZapEvent((Pig)pig.getBukkitEntity(), (LightningStrike)lightning.getBukkitEntity(), (PigZombie)pigzombie.getBukkitEntity());
/* 647 */     pig.getBukkitEntity().getServer().getPluginManager().callEvent((Event)event);
/* 648 */     return event;
/*     */   }
/*     */   
/*     */   public static HorseJumpEvent callHorseJumpEvent(Entity horse, float power) {
/* 652 */     HorseJumpEvent event = new HorseJumpEvent((Horse)horse.getBukkitEntity(), power);
/* 653 */     horse.getBukkitEntity().getServer().getPluginManager().callEvent((Event)event);
/* 654 */     return event;
/*     */   }
/*     */   
/*     */   public static EntityChangeBlockEvent callEntityChangeBlockEvent(Entity entity, Block block, Material material) {
/* 658 */     return callEntityChangeBlockEvent(entity, block, material, 0);
/*     */   }
/*     */   
/*     */   public static EntityChangeBlockEvent callEntityChangeBlockEvent(Entity entity, Block block, Material material) {
/* 662 */     return callEntityChangeBlockEvent((Entity)entity.getBukkitEntity(), block, material, 0);
/*     */   }
/*     */   
/*     */   public static EntityChangeBlockEvent callEntityChangeBlockEvent(Entity entity, Block block, Material material, boolean cancelled) {
/* 666 */     return callEntityChangeBlockEvent((Entity)entity.getBukkitEntity(), block, material, 0, cancelled);
/*     */   }
/*     */   
/*     */   public static EntityChangeBlockEvent callEntityChangeBlockEvent(Entity entity, int x, int y, int z, Block type, int data) {
/* 670 */     Block block = entity.world.getWorld().getBlockAt(x, y, z);
/* 671 */     Material material = CraftMagicNumbers.getMaterial(type);
/*     */     
/* 673 */     return callEntityChangeBlockEvent((Entity)entity.getBukkitEntity(), block, material, data);
/*     */   }
/*     */   
/*     */   public static EntityChangeBlockEvent callEntityChangeBlockEvent(Entity entity, Block block, Material material, int data) {
/* 677 */     return callEntityChangeBlockEvent(entity, block, material, data, false);
/*     */   }
/*     */   
/*     */   public static EntityChangeBlockEvent callEntityChangeBlockEvent(Entity entity, Block block, Material material, int data, boolean cancelled) {
/* 681 */     EntityChangeBlockEvent event = new EntityChangeBlockEvent(entity, block, material, (byte)data);
/* 682 */     event.setCancelled(cancelled);
/* 683 */     entity.getServer().getPluginManager().callEvent((Event)event);
/* 684 */     return event;
/*     */   }
/*     */   
/*     */   public static CreeperPowerEvent callCreeperPowerEvent(Entity creeper, Entity lightning, CreeperPowerEvent.PowerCause cause) {
/* 688 */     CreeperPowerEvent event = new CreeperPowerEvent((Creeper)creeper.getBukkitEntity(), (LightningStrike)lightning.getBukkitEntity(), cause);
/* 689 */     creeper.getBukkitEntity().getServer().getPluginManager().callEvent((Event)event);
/* 690 */     return event;
/*     */   }
/*     */   
/*     */   public static EntityTargetEvent callEntityTargetEvent(Entity entity, Entity target, EntityTargetEvent.TargetReason reason) {
/* 694 */     EntityTargetEvent event = new EntityTargetEvent((Entity)entity.getBukkitEntity(), (target == null) ? null : (Entity)target.getBukkitEntity(), reason);
/* 695 */     entity.getBukkitEntity().getServer().getPluginManager().callEvent((Event)event);
/* 696 */     return event;
/*     */   }
/*     */   
/*     */   public static EntityTargetLivingEntityEvent callEntityTargetLivingEvent(Entity entity, EntityLiving target, EntityTargetEvent.TargetReason reason) {
/* 700 */     EntityTargetLivingEntityEvent event = new EntityTargetLivingEntityEvent((Entity)entity.getBukkitEntity(), (LivingEntity)target.getBukkitEntity(), reason);
/* 701 */     entity.getBukkitEntity().getServer().getPluginManager().callEvent((Event)event);
/* 702 */     return event;
/*     */   }
/*     */   
/*     */   public static EntityBreakDoorEvent callEntityBreakDoorEvent(Entity entity, int x, int y, int z) {
/* 706 */     CraftEntity craftEntity = entity.getBukkitEntity();
/* 707 */     Block block = craftEntity.getWorld().getBlockAt(x, y, z);
/*     */     
/* 709 */     EntityBreakDoorEvent event = new EntityBreakDoorEvent((LivingEntity)craftEntity, block);
/* 710 */     craftEntity.getServer().getPluginManager().callEvent((Event)event);
/*     */     
/* 712 */     return event;
/*     */   }
/*     */   
/*     */   public static Container callInventoryOpenEvent(EntityPlayer player, Container container) {
/* 716 */     if (player.activeContainer != player.defaultContainer) {
/* 717 */       player.playerConnection.a(new PacketPlayInCloseWindow(player.activeContainer.windowId));
/*     */     }
/*     */     
/* 720 */     CraftServer server = player.world.getServer();
/* 721 */     CraftPlayer craftPlayer = player.getBukkitEntity();
/* 722 */     player.activeContainer.transferTo(container, (CraftHumanEntity)craftPlayer);
/*     */     
/* 724 */     InventoryOpenEvent event = new InventoryOpenEvent(container.getBukkitView());
/* 725 */     server.getPluginManager().callEvent((Event)event);
/*     */     
/* 727 */     if (event.isCancelled()) {
/* 728 */       container.transferTo(player.activeContainer, (CraftHumanEntity)craftPlayer);
/* 729 */       return null;
/*     */     } 
/*     */     
/* 732 */     return container;
/*     */   }
/*     */   
/*     */   public static ItemStack callPreCraftEvent(InventoryCrafting matrix, ItemStack result, InventoryView lastCraftView, boolean isRepair) {
/* 736 */     CraftInventoryCrafting inventory = new CraftInventoryCrafting(matrix, matrix.resultInventory);
/* 737 */     inventory.setResult((ItemStack)CraftItemStack.asCraftMirror(result));
/*     */     
/* 739 */     PrepareItemCraftEvent event = new PrepareItemCraftEvent((CraftingInventory)inventory, lastCraftView, isRepair);
/* 740 */     Bukkit.getPluginManager().callEvent((Event)event);
/*     */     
/* 742 */     ItemStack bitem = event.getInventory().getResult();
/*     */     
/* 744 */     return CraftItemStack.asNMSCopy(bitem);
/*     */   }
/*     */   
/*     */   public static ProjectileLaunchEvent callProjectileLaunchEvent(Entity entity) {
/* 748 */     Projectile bukkitEntity = (Projectile)entity.getBukkitEntity();
/* 749 */     ProjectileLaunchEvent event = new ProjectileLaunchEvent((Entity)bukkitEntity);
/* 750 */     Bukkit.getPluginManager().callEvent((Event)event);
/* 751 */     return event;
/*     */   }
/*     */   
/*     */   public static ProjectileHitEvent callProjectileHitEvent(Entity entity) {
/* 755 */     ProjectileHitEvent event = new ProjectileHitEvent((Projectile)entity.getBukkitEntity());
/* 756 */     entity.world.getServer().getPluginManager().callEvent((Event)event);
/* 757 */     return event;
/*     */   }
/*     */   
/*     */   public static ExpBottleEvent callExpBottleEvent(Entity entity, int exp) {
/* 761 */     ThrownExpBottle bottle = (ThrownExpBottle)entity.getBukkitEntity();
/* 762 */     ExpBottleEvent event = new ExpBottleEvent(bottle, exp);
/* 763 */     Bukkit.getPluginManager().callEvent((Event)event);
/* 764 */     return event;
/*     */   }
/*     */   
/*     */   public static BlockRedstoneEvent callRedstoneChange(World world, int x, int y, int z, int oldCurrent, int newCurrent) {
/* 768 */     BlockRedstoneEvent event = new BlockRedstoneEvent(world.getWorld().getBlockAt(x, y, z), oldCurrent, newCurrent);
/* 769 */     world.getServer().getPluginManager().callEvent((Event)event);
/* 770 */     return event;
/*     */   }
/*     */   
/*     */   public static NotePlayEvent callNotePlayEvent(World world, int x, int y, int z, byte instrument, byte note) {
/* 774 */     NotePlayEvent event = new NotePlayEvent(world.getWorld().getBlockAt(x, y, z), Instrument.getByType(instrument), new Note(note));
/* 775 */     world.getServer().getPluginManager().callEvent((Event)event);
/* 776 */     return event;
/*     */   }
/*     */   
/*     */   public static void callPlayerItemBreakEvent(EntityHuman human, ItemStack brokenItem) {
/* 780 */     CraftItemStack item = CraftItemStack.asCraftMirror(brokenItem);
/* 781 */     PlayerItemBreakEvent event = new PlayerItemBreakEvent((Player)human.getBukkitEntity(), (ItemStack)item);
/* 782 */     Bukkit.getPluginManager().callEvent((Event)event);
/*     */   }
/*     */   
/*     */   public static BlockIgniteEvent callBlockIgniteEvent(World world, int x, int y, int z, int igniterX, int igniterY, int igniterZ) {
/* 786 */     CraftWorld craftWorld = world.getWorld();
/* 787 */     Block igniter = craftWorld.getBlockAt(igniterX, igniterY, igniterZ);
/*     */     
/* 789 */     switch (igniter.getType())
/*     */     { case FALL_ONE_CM:
/*     */       case BOAT_ONE_CM:
/* 792 */         cause = BlockIgniteEvent.IgniteCause.LAVA;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 802 */         event = new BlockIgniteEvent(craftWorld.getBlockAt(x, y, z), cause, igniter);
/* 803 */         world.getServer().getPluginManager().callEvent((Event)event);
/* 804 */         return event;case CLIMB_ONE_CM: cause = BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL; event = new BlockIgniteEvent(craftWorld.getBlockAt(x, y, z), cause, igniter); world.getServer().getPluginManager().callEvent((Event)event); return event; }  BlockIgniteEvent.IgniteCause cause = BlockIgniteEvent.IgniteCause.SPREAD; BlockIgniteEvent event = new BlockIgniteEvent(craftWorld.getBlockAt(x, y, z), cause, igniter); world.getServer().getPluginManager().callEvent((Event)event); return event;
/*     */   }
/*     */   
/*     */   public static BlockIgniteEvent callBlockIgniteEvent(World world, int x, int y, int z, Entity igniter) {
/* 808 */     CraftWorld craftWorld = world.getWorld();
/* 809 */     CraftEntity craftEntity = igniter.getBukkitEntity();
/*     */     
/* 811 */     switch (craftEntity.getType())
/*     */     { case FALL_ONE_CM:
/* 813 */         cause = BlockIgniteEvent.IgniteCause.ENDER_CRYSTAL;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 826 */         event = new BlockIgniteEvent(craftWorld.getBlockAt(x, y, z), cause, (Entity)craftEntity);
/* 827 */         world.getServer().getPluginManager().callEvent((Event)event);
/* 828 */         return event;case BOAT_ONE_CM: cause = BlockIgniteEvent.IgniteCause.LIGHTNING; event = new BlockIgniteEvent(craftWorld.getBlockAt(x, y, z), cause, (Entity)craftEntity); world.getServer().getPluginManager().callEvent((Event)event); return event;case CLIMB_ONE_CM: case DIVE_ONE_CM: cause = BlockIgniteEvent.IgniteCause.FIREBALL; event = new BlockIgniteEvent(craftWorld.getBlockAt(x, y, z), cause, (Entity)craftEntity); world.getServer().getPluginManager().callEvent((Event)event); return event; }  BlockIgniteEvent.IgniteCause cause = BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL; BlockIgniteEvent event = new BlockIgniteEvent(craftWorld.getBlockAt(x, y, z), cause, (Entity)craftEntity); world.getServer().getPluginManager().callEvent((Event)event); return event;
/*     */   }
/*     */   
/*     */   public static BlockIgniteEvent callBlockIgniteEvent(World world, int x, int y, int z, Explosion explosion) {
/* 832 */     CraftWorld craftWorld = world.getWorld();
/* 833 */     CraftEntity craftEntity = (explosion.source == null) ? null : explosion.source.getBukkitEntity();
/*     */     
/* 835 */     BlockIgniteEvent event = new BlockIgniteEvent(craftWorld.getBlockAt(x, y, z), BlockIgniteEvent.IgniteCause.EXPLOSION, (Entity)craftEntity);
/* 836 */     world.getServer().getPluginManager().callEvent((Event)event);
/* 837 */     return event;
/*     */   }
/*     */   
/*     */   public static BlockIgniteEvent callBlockIgniteEvent(World world, int x, int y, int z, BlockIgniteEvent.IgniteCause cause, Entity igniter) {
/* 841 */     BlockIgniteEvent event = new BlockIgniteEvent(world.getWorld().getBlockAt(x, y, z), cause, (Entity)igniter.getBukkitEntity());
/* 842 */     world.getServer().getPluginManager().callEvent((Event)event);
/* 843 */     return event;
/*     */   }
/*     */   
/*     */   public static void handleInventoryCloseEvent(EntityHuman human) {
/* 847 */     if (human.activeContainer == human.defaultContainer)
/* 848 */       return;  InventoryCloseEvent event = new InventoryCloseEvent(human.activeContainer.getBukkitView());
/* 849 */     human.world.getServer().getPluginManager().callEvent((Event)event);
/* 850 */     human.activeContainer.transferTo(human.defaultContainer, human.getBukkitEntity());
/*     */   }
/*     */   
/*     */   public static void handleEditBookEvent(EntityPlayer player, ItemStack newBookItem) {
/* 854 */     int itemInHandIndex = player.inventory.itemInHandIndex;
/*     */     
/* 856 */     PlayerEditBookEvent editBookEvent = new PlayerEditBookEvent((Player)player.getBukkitEntity(), player.inventory.itemInHandIndex, (BookMeta)CraftItemStack.getItemMeta(player.inventory.getItemInHand()), (BookMeta)CraftItemStack.getItemMeta(newBookItem), (newBookItem.getItem() == Items.WRITTEN_BOOK));
/* 857 */     player.world.getServer().getPluginManager().callEvent((Event)editBookEvent);
/* 858 */     ItemStack itemInHand = player.inventory.getItem(itemInHandIndex);
/*     */ 
/*     */     
/* 861 */     if (itemInHand != null && itemInHand.getItem() == Items.BOOK_AND_QUILL) {
/* 862 */       if (!editBookEvent.isCancelled()) {
/* 863 */         CraftItemStack.setItemMeta(itemInHand, (ItemMeta)editBookEvent.getNewBookMeta());
/* 864 */         if (editBookEvent.isSigning()) {
/* 865 */           itemInHand.setItem(Items.WRITTEN_BOOK);
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/* 870 */       Slot slot = player.activeContainer.getSlot((IInventory)player.inventory, itemInHandIndex);
/* 871 */       player.playerConnection.sendPacket((Packet)new PacketPlayOutSetSlot(player.activeContainer.windowId, slot.rawSlotIndex, itemInHand));
/*     */     } 
/*     */   }
/*     */   
/*     */   public static PlayerUnleashEntityEvent callPlayerUnleashEntityEvent(EntityInsentient entity, EntityHuman player) {
/* 876 */     PlayerUnleashEntityEvent event = new PlayerUnleashEntityEvent((Entity)entity.getBukkitEntity(), (Player)player.getBukkitEntity());
/* 877 */     entity.world.getServer().getPluginManager().callEvent((Event)event);
/* 878 */     return event;
/*     */   }
/*     */   
/*     */   public static PlayerLeashEntityEvent callPlayerLeashEntityEvent(EntityInsentient entity, Entity leashHolder, EntityHuman player) {
/* 882 */     PlayerLeashEntityEvent event = new PlayerLeashEntityEvent((Entity)entity.getBukkitEntity(), (Entity)leashHolder.getBukkitEntity(), (Player)player.getBukkitEntity());
/* 883 */     entity.world.getServer().getPluginManager().callEvent((Event)event);
/* 884 */     return event;
/*     */   }
/*     */   public static Cancellable handleStatisticsIncrease(EntityHuman entityHuman, Statistic statistic, int current, int incrementation) {
/*     */     PlayerStatisticIncrementEvent playerStatisticIncrementEvent;
/* 888 */     CraftPlayer craftPlayer = ((EntityPlayer)entityHuman).getBukkitEntity();
/*     */     
/* 890 */     if (statistic instanceof Achievement) {
/* 891 */       if (current != 0) {
/* 892 */         return null;
/*     */       }
/* 894 */       PlayerAchievementAwardedEvent playerAchievementAwardedEvent = new PlayerAchievementAwardedEvent((Player)craftPlayer, CraftStatistic.getBukkitAchievement((Achievement)statistic));
/*     */     } else {
/* 896 */       Statistic stat = CraftStatistic.getBukkitStatistic(statistic);
/* 897 */       switch (stat) {
/*     */         
/*     */         case FALL_ONE_CM:
/*     */         case BOAT_ONE_CM:
/*     */         case CLIMB_ONE_CM:
/*     */         case DIVE_ONE_CM:
/*     */         case FLY_ONE_CM:
/*     */         case HORSE_ONE_CM:
/*     */         case MINECART_ONE_CM:
/*     */         case PIG_ONE_CM:
/*     */         case PLAY_ONE_TICK:
/*     */         case SWIM_ONE_CM:
/*     */         case WALK_ONE_CM:
/* 910 */           return null;
/*     */       } 
/*     */       
/* 913 */       if (stat.getType() == Statistic.Type.UNTYPED) {
/* 914 */         playerStatisticIncrementEvent = new PlayerStatisticIncrementEvent((Player)craftPlayer, stat, current, current + incrementation);
/* 915 */       } else if (stat.getType() == Statistic.Type.ENTITY) {
/* 916 */         EntityType entityType = CraftStatistic.getEntityTypeFromStatistic(statistic);
/* 917 */         playerStatisticIncrementEvent = new PlayerStatisticIncrementEvent((Player)craftPlayer, stat, current, current + incrementation, entityType);
/*     */       } else {
/* 919 */         Material material = CraftStatistic.getMaterialFromStatistic(statistic);
/* 920 */         playerStatisticIncrementEvent = new PlayerStatisticIncrementEvent((Player)craftPlayer, stat, current, current + incrementation, material);
/*     */       } 
/*     */     } 
/* 923 */     entityHuman.world.getServer().getPluginManager().callEvent((Event)playerStatisticIncrementEvent);
/* 924 */     return (Cancellable)playerStatisticIncrementEvent;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\event\CraftEventFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */