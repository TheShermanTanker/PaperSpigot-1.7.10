/*     */ package org.bukkit.craftbukkit.v1_7_R4.block;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import net.minecraft.server.v1_7_R4.BiomeBase;
/*     */ import net.minecraft.server.v1_7_R4.Block;
/*     */ import net.minecraft.server.v1_7_R4.BlockCocoa;
/*     */ import net.minecraft.server.v1_7_R4.BlockRedstoneWire;
/*     */ import net.minecraft.server.v1_7_R4.Blocks;
/*     */ import net.minecraft.server.v1_7_R4.EnumSkyBlock;
/*     */ import net.minecraft.server.v1_7_R4.GameProfileSerializer;
/*     */ import net.minecraft.server.v1_7_R4.Item;
/*     */ import net.minecraft.server.v1_7_R4.ItemStack;
/*     */ import net.minecraft.server.v1_7_R4.NBTBase;
/*     */ import net.minecraft.server.v1_7_R4.NBTTagCompound;
/*     */ import net.minecraft.server.v1_7_R4.TileEntitySkull;
/*     */ import net.minecraft.server.v1_7_R4.World;
/*     */ import org.bukkit.Chunk;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.block.Biome;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.block.BlockFace;
/*     */ import org.bukkit.block.BlockState;
/*     */ import org.bukkit.block.PistonMoveReaction;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.CraftChunk;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.inventory.CraftItemStack;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.util.CraftMagicNumbers;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.metadata.MetadataValue;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.util.BlockVector;
/*     */ 
/*     */ public class CraftBlock implements Block {
/*     */   private final CraftChunk chunk;
/*     */   private final int x;
/*     */   private final int y;
/*     */   private final int z;
/*     */   
/*     */   public CraftBlock(CraftChunk chunk, int x, int y, int z) {
/*  44 */     this.x = x;
/*  45 */     this.y = y;
/*  46 */     this.z = z;
/*  47 */     this.chunk = chunk;
/*     */   }
/*     */   
/*     */   private Block getNMSBlock() {
/*  51 */     return CraftMagicNumbers.getBlock(this);
/*     */   }
/*     */   
/*     */   private static Block getNMSBlock(int type) {
/*  55 */     return CraftMagicNumbers.getBlock(type);
/*     */   }
/*     */   
/*     */   public World getWorld() {
/*  59 */     return this.chunk.getWorld();
/*     */   }
/*     */   
/*     */   public Location getLocation() {
/*  63 */     return new Location(getWorld(), this.x, this.y, this.z);
/*     */   }
/*     */   
/*     */   public Location getLocation(Location loc) {
/*  67 */     if (loc != null) {
/*  68 */       loc.setWorld(getWorld());
/*  69 */       loc.setX(this.x);
/*  70 */       loc.setY(this.y);
/*  71 */       loc.setZ(this.z);
/*  72 */       loc.setYaw(0.0F);
/*  73 */       loc.setPitch(0.0F);
/*     */     } 
/*     */     
/*  76 */     return loc;
/*     */   }
/*     */   
/*     */   public BlockVector getVector() {
/*  80 */     return new BlockVector(this.x, this.y, this.z);
/*     */   }
/*     */   
/*     */   public int getX() {
/*  84 */     return this.x;
/*     */   }
/*     */   
/*     */   public int getY() {
/*  88 */     return this.y;
/*     */   }
/*     */   
/*     */   public int getZ() {
/*  92 */     return this.z;
/*     */   }
/*     */   
/*     */   public Chunk getChunk() {
/*  96 */     return (Chunk)this.chunk;
/*     */   }
/*     */   
/*     */   public void setData(byte data) {
/* 100 */     (this.chunk.getHandle()).world.setData(this.x, this.y, this.z, data, 3);
/*     */   }
/*     */   
/*     */   public void setData(byte data, boolean applyPhysics) {
/* 104 */     if (applyPhysics) {
/* 105 */       (this.chunk.getHandle()).world.setData(this.x, this.y, this.z, data, 3);
/*     */     } else {
/* 107 */       (this.chunk.getHandle()).world.setData(this.x, this.y, this.z, data, 2);
/*     */     } 
/*     */   }
/*     */   
/*     */   public byte getData() {
/* 112 */     return (byte)this.chunk.getHandle().getData(this.x & 0xF, this.y & 0xFF, this.z & 0xF);
/*     */   }
/*     */   
/*     */   public void setType(Material type) {
/* 116 */     setTypeId(type.getId());
/*     */   }
/*     */   
/*     */   public boolean setTypeId(int type) {
/* 120 */     return setTypeId(type, true);
/*     */   }
/*     */   
/*     */   public boolean setTypeId(int type, boolean applyPhysics) {
/* 124 */     return setTypeIdAndData(type, getData(), applyPhysics);
/*     */   }
/*     */   
/*     */   public boolean setTypeIdAndData(int type, byte data, boolean applyPhysics) {
/* 128 */     if (applyPhysics) {
/* 129 */       return (this.chunk.getHandle()).world.setTypeAndData(this.x, this.y, this.z, getNMSBlock(type), data, 3);
/*     */     }
/* 131 */     boolean success = (this.chunk.getHandle()).world.setTypeAndData(this.x, this.y, this.z, getNMSBlock(type), data, 2);
/* 132 */     if (success) {
/* 133 */       (this.chunk.getHandle()).world.notify(this.x, this.y, this.z);
/*     */     }
/* 135 */     return success;
/*     */   }
/*     */ 
/*     */   
/*     */   public Material getType() {
/* 140 */     return Material.getMaterial(getTypeId());
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public int getTypeId() {
/* 146 */     return CraftMagicNumbers.getId(this.chunk.getHandle().getType(this.x & 0xF, this.y & 0xFF, this.z & 0xF));
/*     */   }
/*     */   
/*     */   public byte getLightLevel() {
/* 150 */     return (byte)(this.chunk.getHandle()).world.getLightLevel(this.x, this.y, this.z);
/*     */   }
/*     */   
/*     */   public byte getLightFromSky() {
/* 154 */     return (byte)this.chunk.getHandle().getBrightness(EnumSkyBlock.SKY, this.x & 0xF, this.y & 0xFF, this.z & 0xF);
/*     */   }
/*     */   
/*     */   public byte getLightFromBlocks() {
/* 158 */     return (byte)this.chunk.getHandle().getBrightness(EnumSkyBlock.BLOCK, this.x & 0xF, this.y & 0xFF, this.z & 0xF);
/*     */   }
/*     */ 
/*     */   
/*     */   public Block getFace(BlockFace face) {
/* 163 */     return getRelative(face, 1);
/*     */   }
/*     */   
/*     */   public Block getFace(BlockFace face, int distance) {
/* 167 */     return getRelative(face, distance);
/*     */   }
/*     */   
/*     */   public Block getRelative(int modX, int modY, int modZ) {
/* 171 */     return getWorld().getBlockAt(getX() + modX, getY() + modY, getZ() + modZ);
/*     */   }
/*     */   
/*     */   public Block getRelative(BlockFace face) {
/* 175 */     return getRelative(face, 1);
/*     */   }
/*     */   
/*     */   public Block getRelative(BlockFace face, int distance) {
/* 179 */     return getRelative(face.getModX() * distance, face.getModY() * distance, face.getModZ() * distance);
/*     */   }
/*     */   
/*     */   public BlockFace getFace(Block block) {
/* 183 */     BlockFace[] values = BlockFace.values();
/*     */     
/* 185 */     for (BlockFace face : values) {
/* 186 */       if (getX() + face.getModX() == block.getX() && getY() + face.getModY() == block.getY() && getZ() + face.getModZ() == block.getZ())
/*     */       {
/*     */ 
/*     */         
/* 190 */         return face;
/*     */       }
/*     */     } 
/*     */     
/* 194 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 199 */     return "CraftBlock{chunk=" + this.chunk + ",x=" + this.x + ",y=" + this.y + ",z=" + this.z + ",type=" + getType() + ",data=" + getData() + '}';
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static BlockFace notchToBlockFace(int notch) {
/* 209 */     switch (notch) {
/*     */       case 0:
/* 211 */         return BlockFace.DOWN;
/*     */       case 1:
/* 213 */         return BlockFace.UP;
/*     */       case 2:
/* 215 */         return BlockFace.NORTH;
/*     */       case 3:
/* 217 */         return BlockFace.SOUTH;
/*     */       case 4:
/* 219 */         return BlockFace.WEST;
/*     */       case 5:
/* 221 */         return BlockFace.EAST;
/*     */     } 
/* 223 */     return BlockFace.SELF;
/*     */   }
/*     */ 
/*     */   
/*     */   public static int blockFaceToNotch(BlockFace face) {
/* 228 */     switch (face) {
/*     */       case SIGN:
/* 230 */         return 0;
/*     */       case SIGN_POST:
/* 232 */         return 1;
/*     */       case WALL_SIGN:
/* 234 */         return 2;
/*     */       case CHEST:
/* 236 */         return 3;
/*     */       case TRAPPED_CHEST:
/* 238 */         return 4;
/*     */       case BURNING_FURNACE:
/* 240 */         return 5;
/*     */     } 
/* 242 */     return 7;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockState getState() {
/* 247 */     Material material = getType();
/*     */     
/* 249 */     switch (material) {
/*     */       case SIGN:
/*     */       case SIGN_POST:
/*     */       case WALL_SIGN:
/* 253 */         return new CraftSign(this);
/*     */       case CHEST:
/*     */       case TRAPPED_CHEST:
/* 256 */         return new CraftChest(this);
/*     */       case BURNING_FURNACE:
/*     */       case FURNACE:
/* 259 */         return new CraftFurnace(this);
/*     */       case DISPENSER:
/* 261 */         return new CraftDispenser(this);
/*     */       case DROPPER:
/* 263 */         return new CraftDropper(this);
/*     */       case HOPPER:
/* 265 */         return new CraftHopper(this);
/*     */       case MOB_SPAWNER:
/* 267 */         return new CraftCreatureSpawner(this);
/*     */       case NOTE_BLOCK:
/* 269 */         return new CraftNoteBlock(this);
/*     */       case JUKEBOX:
/* 271 */         return new CraftJukebox(this);
/*     */       case BREWING_STAND:
/* 273 */         return new CraftBrewingStand(this);
/*     */       case SKULL:
/* 275 */         return new CraftSkull(this);
/*     */       case COMMAND:
/* 277 */         return new CraftCommandBlock(this);
/*     */       case BEACON:
/* 279 */         return new CraftBeacon(this);
/*     */     } 
/* 281 */     return new CraftBlockState(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public Biome getBiome() {
/* 286 */     return getWorld().getBiome(this.x, this.z);
/*     */   }
/*     */   
/*     */   public void setBiome(Biome bio) {
/* 290 */     getWorld().setBiome(this.x, this.z, bio);
/*     */   }
/*     */   
/*     */   public static Biome biomeBaseToBiome(BiomeBase base) {
/* 294 */     if (base == null) {
/* 295 */       return null;
/*     */     }
/*     */     
/* 298 */     return BIOME_MAPPING[base.id];
/*     */   }
/*     */   
/*     */   public static BiomeBase biomeToBiomeBase(Biome bio) {
/* 302 */     if (bio == null) {
/* 303 */       return null;
/*     */     }
/* 305 */     return BIOMEBASE_MAPPING[bio.ordinal()];
/*     */   }
/*     */   
/*     */   public double getTemperature() {
/* 309 */     return getWorld().getTemperature(this.x, this.z);
/*     */   }
/*     */   
/*     */   public double getHumidity() {
/* 313 */     return getWorld().getHumidity(this.x, this.z);
/*     */   }
/*     */   
/*     */   public boolean isBlockPowered() {
/* 317 */     return ((this.chunk.getHandle()).world.getBlockPower(this.x, this.y, this.z) > 0);
/*     */   }
/*     */   
/*     */   public boolean isBlockIndirectlyPowered() {
/* 321 */     return (this.chunk.getHandle()).world.isBlockIndirectlyPowered(this.x, this.y, this.z);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 326 */     if (o == this) return true; 
/* 327 */     if (!(o instanceof CraftBlock)) return false; 
/* 328 */     CraftBlock other = (CraftBlock)o;
/*     */     
/* 330 */     return (this.x == other.x && this.y == other.y && this.z == other.z && getWorld().equals(other.getWorld()));
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 335 */     return this.y << 24 ^ this.x ^ this.z ^ getWorld().hashCode();
/*     */   }
/*     */   
/*     */   public boolean isBlockFacePowered(BlockFace face) {
/* 339 */     return (this.chunk.getHandle()).world.isBlockFacePowered(this.x, this.y, this.z, blockFaceToNotch(face));
/*     */   }
/*     */   
/*     */   public boolean isBlockFaceIndirectlyPowered(BlockFace face) {
/* 343 */     int power = (this.chunk.getHandle()).world.getBlockFacePower(this.x, this.y, this.z, blockFaceToNotch(face));
/*     */     
/* 345 */     Block relative = getRelative(face);
/* 346 */     if (relative.getType() == Material.REDSTONE_WIRE) {
/* 347 */       return (Math.max(power, relative.getData()) > 0);
/*     */     }
/*     */     
/* 350 */     return (power > 0);
/*     */   }
/*     */   
/*     */   public int getBlockPower(BlockFace face) {
/* 354 */     int power = 0;
/* 355 */     BlockRedstoneWire wire = Blocks.REDSTONE_WIRE;
/* 356 */     World world = (this.chunk.getHandle()).world;
/* 357 */     if ((face == BlockFace.DOWN || face == BlockFace.SELF) && world.isBlockFacePowered(this.x, this.y - 1, this.z, 0)) power = wire.getPower(world, this.x, this.y - 1, this.z, power); 
/* 358 */     if ((face == BlockFace.UP || face == BlockFace.SELF) && world.isBlockFacePowered(this.x, this.y + 1, this.z, 1)) power = wire.getPower(world, this.x, this.y + 1, this.z, power); 
/* 359 */     if ((face == BlockFace.EAST || face == BlockFace.SELF) && world.isBlockFacePowered(this.x + 1, this.y, this.z, 2)) power = wire.getPower(world, this.x + 1, this.y, this.z, power); 
/* 360 */     if ((face == BlockFace.WEST || face == BlockFace.SELF) && world.isBlockFacePowered(this.x - 1, this.y, this.z, 3)) power = wire.getPower(world, this.x - 1, this.y, this.z, power); 
/* 361 */     if ((face == BlockFace.NORTH || face == BlockFace.SELF) && world.isBlockFacePowered(this.x, this.y, this.z - 1, 4)) power = wire.getPower(world, this.x, this.y, this.z - 1, power); 
/* 362 */     if ((face == BlockFace.SOUTH || face == BlockFace.SELF) && world.isBlockFacePowered(this.x, this.y, this.z + 1, 5)) power = wire.getPower(world, this.x, this.y, this.z - 1, power); 
/* 363 */     return (power > 0) ? power : (((face == BlockFace.SELF) ? isBlockIndirectlyPowered() : isBlockFaceIndirectlyPowered(face)) ? 15 : 0);
/*     */   }
/*     */   
/*     */   public int getBlockPower() {
/* 367 */     return getBlockPower(BlockFace.SELF);
/*     */   }
/*     */   
/*     */   public boolean isEmpty() {
/* 371 */     return (getType() == Material.AIR);
/*     */   }
/*     */   
/*     */   public boolean isLiquid() {
/* 375 */     return (getType() == Material.WATER || getType() == Material.STATIONARY_WATER || getType() == Material.LAVA || getType() == Material.STATIONARY_LAVA);
/*     */   }
/*     */   
/*     */   public PistonMoveReaction getPistonMoveReaction() {
/* 379 */     return PistonMoveReaction.getById(getNMSBlock().getMaterial().getPushReaction());
/*     */   }
/*     */   
/*     */   private boolean itemCausesDrops(ItemStack item) {
/* 383 */     Block block = getNMSBlock();
/* 384 */     Item itemType = (item != null) ? Item.getById(item.getTypeId()) : null;
/* 385 */     return (block != null && (block.getMaterial().isAlwaysDestroyable() || (itemType != null && itemType.canDestroySpecialBlock(block))));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean breakNaturally() {
/* 390 */     Block block = getNMSBlock();
/* 391 */     byte data = getData();
/* 392 */     boolean result = false;
/*     */     
/* 394 */     if (block != null && block != Blocks.AIR) {
/* 395 */       block.dropNaturally((this.chunk.getHandle()).world, this.x, this.y, this.z, data, 1.0F, 0);
/* 396 */       result = true;
/*     */     } 
/*     */     
/* 399 */     setTypeId(Material.AIR.getId());
/* 400 */     return result;
/*     */   }
/*     */   
/*     */   public boolean breakNaturally(ItemStack item) {
/* 404 */     if (itemCausesDrops(item)) {
/* 405 */       return breakNaturally();
/*     */     }
/* 407 */     return setTypeId(Material.AIR.getId());
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<ItemStack> getDrops() {
/* 412 */     List<ItemStack> drops = new ArrayList<ItemStack>();
/*     */     
/* 414 */     Block block = getNMSBlock();
/* 415 */     if (block != Blocks.AIR) {
/* 416 */       byte data = getData();
/*     */       
/* 418 */       int count = block.getDropCount(0, (this.chunk.getHandle()).world.random);
/* 419 */       for (int i = 0; i < count; i++) {
/* 420 */         Item item = block.getDropType(data, (this.chunk.getHandle()).world.random, 0);
/* 421 */         if (item != null)
/*     */         {
/* 423 */           if (Blocks.SKULL == block) {
/* 424 */             ItemStack nmsStack = new ItemStack(item, 1, block.getDropData((this.chunk.getHandle()).world, this.x, this.y, this.z));
/* 425 */             TileEntitySkull tileentityskull = (TileEntitySkull)(this.chunk.getHandle()).world.getTileEntity(this.x, this.y, this.z);
/*     */             
/* 427 */             if (tileentityskull.getSkullType() == 3 && tileentityskull.getGameProfile() != null) {
/* 428 */               nmsStack.setTag(new NBTTagCompound());
/* 429 */               NBTTagCompound nbttagcompound = new NBTTagCompound();
/*     */               
/* 431 */               GameProfileSerializer.serialize(nbttagcompound, tileentityskull.getGameProfile());
/* 432 */               nmsStack.getTag().set("SkullOwner", (NBTBase)nbttagcompound);
/*     */             } 
/*     */             
/* 435 */             drops.add(CraftItemStack.asBukkitCopy(nmsStack));
/*     */           }
/* 437 */           else if (Blocks.COCOA == block) {
/* 438 */             int dropAmount = (BlockCocoa.c(data) >= 2) ? 3 : 1;
/* 439 */             for (int j = 0; j < dropAmount; j++) {
/* 440 */               drops.add(new ItemStack(Material.INK_SACK, 1, (short)3));
/*     */             }
/*     */           } else {
/* 443 */             drops.add(new ItemStack(CraftMagicNumbers.getMaterial(item), 1, (short)block.getDropData(data)));
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/* 448 */     return drops;
/*     */   }
/*     */   
/*     */   public Collection<ItemStack> getDrops(ItemStack item) {
/* 452 */     if (itemCausesDrops(item)) {
/* 453 */       return getDrops();
/*     */     }
/* 455 */     return Collections.emptyList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 461 */   private static final Biome[] BIOME_MAPPING = new Biome[(BiomeBase.getBiomes()).length];
/* 462 */   private static final BiomeBase[] BIOMEBASE_MAPPING = new BiomeBase[(Biome.values()).length]; static {
/* 463 */     BIOME_MAPPING[BiomeBase.OCEAN.id] = Biome.OCEAN;
/* 464 */     BIOME_MAPPING[BiomeBase.PLAINS.id] = Biome.PLAINS;
/* 465 */     BIOME_MAPPING[BiomeBase.DESERT.id] = Biome.DESERT;
/* 466 */     BIOME_MAPPING[BiomeBase.EXTREME_HILLS.id] = Biome.EXTREME_HILLS;
/* 467 */     BIOME_MAPPING[BiomeBase.FOREST.id] = Biome.FOREST;
/* 468 */     BIOME_MAPPING[BiomeBase.TAIGA.id] = Biome.TAIGA;
/* 469 */     BIOME_MAPPING[BiomeBase.SWAMPLAND.id] = Biome.SWAMPLAND;
/* 470 */     BIOME_MAPPING[BiomeBase.RIVER.id] = Biome.RIVER;
/* 471 */     BIOME_MAPPING[BiomeBase.HELL.id] = Biome.HELL;
/* 472 */     BIOME_MAPPING[BiomeBase.SKY.id] = Biome.SKY;
/* 473 */     BIOME_MAPPING[BiomeBase.FROZEN_OCEAN.id] = Biome.FROZEN_OCEAN;
/* 474 */     BIOME_MAPPING[BiomeBase.FROZEN_RIVER.id] = Biome.FROZEN_RIVER;
/* 475 */     BIOME_MAPPING[BiomeBase.ICE_PLAINS.id] = Biome.ICE_PLAINS;
/* 476 */     BIOME_MAPPING[BiomeBase.ICE_MOUNTAINS.id] = Biome.ICE_MOUNTAINS;
/* 477 */     BIOME_MAPPING[BiomeBase.MUSHROOM_ISLAND.id] = Biome.MUSHROOM_ISLAND;
/* 478 */     BIOME_MAPPING[BiomeBase.MUSHROOM_SHORE.id] = Biome.MUSHROOM_SHORE;
/* 479 */     BIOME_MAPPING[BiomeBase.BEACH.id] = Biome.BEACH;
/* 480 */     BIOME_MAPPING[BiomeBase.DESERT_HILLS.id] = Biome.DESERT_HILLS;
/* 481 */     BIOME_MAPPING[BiomeBase.FOREST_HILLS.id] = Biome.FOREST_HILLS;
/* 482 */     BIOME_MAPPING[BiomeBase.TAIGA_HILLS.id] = Biome.TAIGA_HILLS;
/* 483 */     BIOME_MAPPING[BiomeBase.SMALL_MOUNTAINS.id] = Biome.SMALL_MOUNTAINS;
/* 484 */     BIOME_MAPPING[BiomeBase.JUNGLE.id] = Biome.JUNGLE;
/* 485 */     BIOME_MAPPING[BiomeBase.JUNGLE_HILLS.id] = Biome.JUNGLE_HILLS;
/* 486 */     BIOME_MAPPING[BiomeBase.JUNGLE_EDGE.id] = Biome.JUNGLE_EDGE;
/* 487 */     BIOME_MAPPING[BiomeBase.DEEP_OCEAN.id] = Biome.DEEP_OCEAN;
/* 488 */     BIOME_MAPPING[BiomeBase.STONE_BEACH.id] = Biome.STONE_BEACH;
/* 489 */     BIOME_MAPPING[BiomeBase.COLD_BEACH.id] = Biome.COLD_BEACH;
/* 490 */     BIOME_MAPPING[BiomeBase.BIRCH_FOREST.id] = Biome.BIRCH_FOREST;
/* 491 */     BIOME_MAPPING[BiomeBase.BIRCH_FOREST_HILLS.id] = Biome.BIRCH_FOREST_HILLS;
/* 492 */     BIOME_MAPPING[BiomeBase.ROOFED_FOREST.id] = Biome.ROOFED_FOREST;
/* 493 */     BIOME_MAPPING[BiomeBase.COLD_TAIGA.id] = Biome.COLD_TAIGA;
/* 494 */     BIOME_MAPPING[BiomeBase.COLD_TAIGA_HILLS.id] = Biome.COLD_TAIGA_HILLS;
/* 495 */     BIOME_MAPPING[BiomeBase.MEGA_TAIGA.id] = Biome.MEGA_TAIGA;
/* 496 */     BIOME_MAPPING[BiomeBase.MEGA_TAIGA_HILLS.id] = Biome.MEGA_TAIGA_HILLS;
/* 497 */     BIOME_MAPPING[BiomeBase.EXTREME_HILLS_PLUS.id] = Biome.EXTREME_HILLS_PLUS;
/* 498 */     BIOME_MAPPING[BiomeBase.SAVANNA.id] = Biome.SAVANNA;
/* 499 */     BIOME_MAPPING[BiomeBase.SAVANNA_PLATEAU.id] = Biome.SAVANNA_PLATEAU;
/* 500 */     BIOME_MAPPING[BiomeBase.MESA.id] = Biome.MESA;
/* 501 */     BIOME_MAPPING[BiomeBase.MESA_PLATEAU_F.id] = Biome.MESA_PLATEAU_FOREST;
/* 502 */     BIOME_MAPPING[BiomeBase.MESA_PLATEAU.id] = Biome.MESA_PLATEAU;
/*     */ 
/*     */     
/* 505 */     BIOME_MAPPING[BiomeBase.PLAINS.id + 128] = Biome.SUNFLOWER_PLAINS;
/* 506 */     BIOME_MAPPING[BiomeBase.DESERT.id + 128] = Biome.DESERT_MOUNTAINS;
/* 507 */     BIOME_MAPPING[BiomeBase.FOREST.id + 128] = Biome.FLOWER_FOREST;
/* 508 */     BIOME_MAPPING[BiomeBase.TAIGA.id + 128] = Biome.TAIGA_MOUNTAINS;
/* 509 */     BIOME_MAPPING[BiomeBase.SWAMPLAND.id + 128] = Biome.SWAMPLAND_MOUNTAINS;
/* 510 */     BIOME_MAPPING[BiomeBase.ICE_PLAINS.id + 128] = Biome.ICE_PLAINS_SPIKES;
/* 511 */     BIOME_MAPPING[BiomeBase.JUNGLE.id + 128] = Biome.JUNGLE_MOUNTAINS;
/* 512 */     BIOME_MAPPING[BiomeBase.JUNGLE_EDGE.id + 128] = Biome.JUNGLE_EDGE_MOUNTAINS;
/* 513 */     BIOME_MAPPING[BiomeBase.COLD_TAIGA.id + 128] = Biome.COLD_TAIGA_MOUNTAINS;
/* 514 */     BIOME_MAPPING[BiomeBase.SAVANNA.id + 128] = Biome.SAVANNA_MOUNTAINS;
/* 515 */     BIOME_MAPPING[BiomeBase.SAVANNA_PLATEAU.id + 128] = Biome.SAVANNA_PLATEAU_MOUNTAINS;
/* 516 */     BIOME_MAPPING[BiomeBase.MESA.id + 128] = Biome.MESA_BRYCE;
/* 517 */     BIOME_MAPPING[BiomeBase.MESA_PLATEAU_F.id + 128] = Biome.MESA_PLATEAU_FOREST_MOUNTAINS;
/* 518 */     BIOME_MAPPING[BiomeBase.MESA_PLATEAU.id + 128] = Biome.MESA_PLATEAU_MOUNTAINS;
/* 519 */     BIOME_MAPPING[BiomeBase.BIRCH_FOREST.id + 128] = Biome.BIRCH_FOREST_MOUNTAINS;
/* 520 */     BIOME_MAPPING[BiomeBase.BIRCH_FOREST_HILLS.id + 128] = Biome.BIRCH_FOREST_HILLS_MOUNTAINS;
/* 521 */     BIOME_MAPPING[BiomeBase.ROOFED_FOREST.id + 128] = Biome.ROOFED_FOREST_MOUNTAINS;
/* 522 */     BIOME_MAPPING[BiomeBase.MEGA_TAIGA.id + 128] = Biome.MEGA_SPRUCE_TAIGA;
/* 523 */     BIOME_MAPPING[BiomeBase.EXTREME_HILLS.id + 128] = Biome.EXTREME_HILLS_MOUNTAINS;
/* 524 */     BIOME_MAPPING[BiomeBase.EXTREME_HILLS_PLUS.id + 128] = Biome.EXTREME_HILLS_PLUS_MOUNTAINS;
/* 525 */     BIOME_MAPPING[BiomeBase.MEGA_TAIGA_HILLS.id + 128] = Biome.MEGA_SPRUCE_TAIGA_HILLS;
/*     */ 
/*     */ 
/*     */     
/* 529 */     for (int i = 0; i < BIOME_MAPPING.length; i++) {
/* 530 */       if (BiomeBase.getBiome(i) != null && BIOME_MAPPING[i] == null) {
/* 531 */         throw new IllegalArgumentException("Missing Biome mapping for BiomeBase[" + i + ", " + BiomeBase.getBiome(i) + "]");
/*     */       }
/* 533 */       if (BIOME_MAPPING[i] != null) {
/* 534 */         BIOMEBASE_MAPPING[BIOME_MAPPING[i].ordinal()] = BiomeBase.getBiome(i);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setMetadata(String metadataKey, MetadataValue newMetadataValue) {
/* 540 */     this.chunk.getCraftWorld().getBlockMetadata().setMetadata(this, metadataKey, newMetadataValue);
/*     */   }
/*     */   
/*     */   public List<MetadataValue> getMetadata(String metadataKey) {
/* 544 */     return this.chunk.getCraftWorld().getBlockMetadata().getMetadata(this, metadataKey);
/*     */   }
/*     */   
/*     */   public boolean hasMetadata(String metadataKey) {
/* 548 */     return this.chunk.getCraftWorld().getBlockMetadata().hasMetadata(this, metadataKey);
/*     */   }
/*     */   
/*     */   public void removeMetadata(String metadataKey, Plugin owningPlugin) {
/* 552 */     this.chunk.getCraftWorld().getBlockMetadata().removeMetadata(this, metadataKey, owningPlugin);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\block\CraftBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */