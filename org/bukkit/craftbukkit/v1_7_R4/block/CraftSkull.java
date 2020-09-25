/*     */ package org.bukkit.craftbukkit.v1_7_R4.block;
/*     */ 
/*     */ import net.minecraft.server.v1_7_R4.MinecraftServer;
/*     */ import net.minecraft.server.v1_7_R4.TileEntitySkull;
/*     */ import net.minecraft.util.com.mojang.authlib.GameProfile;
/*     */ import org.bukkit.SkullType;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.block.BlockFace;
/*     */ import org.bukkit.block.Skull;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.CraftWorld;
/*     */ 
/*     */ public class CraftSkull
/*     */   extends CraftBlockState implements Skull {
/*     */   private static final int MAX_OWNER_LENGTH = 16;
/*     */   private final TileEntitySkull skull;
/*     */   private GameProfile profile;
/*     */   private SkullType skullType;
/*     */   private byte rotation;
/*     */   
/*     */   public CraftSkull(Block block) {
/*  21 */     super(block);
/*     */     
/*  23 */     CraftWorld world = (CraftWorld)block.getWorld();
/*  24 */     this.skull = (TileEntitySkull)world.getTileEntityAt(getX(), getY(), getZ());
/*  25 */     this.profile = this.skull.getGameProfile();
/*  26 */     this.skullType = getSkullType(this.skull.getSkullType());
/*  27 */     this.rotation = (byte)this.skull.getRotation();
/*     */   }
/*     */   
/*     */   static SkullType getSkullType(int id) {
/*  31 */     switch (id) {
/*     */       case 0:
/*  33 */         return SkullType.SKELETON;
/*     */       case 1:
/*  35 */         return SkullType.WITHER;
/*     */       case 2:
/*  37 */         return SkullType.ZOMBIE;
/*     */       case 3:
/*  39 */         return SkullType.PLAYER;
/*     */       case 4:
/*  41 */         return SkullType.CREEPER;
/*     */     } 
/*  43 */     throw new AssertionError(id);
/*     */   }
/*     */ 
/*     */   
/*     */   static int getSkullType(SkullType type) {
/*  48 */     switch (type) {
/*     */       case NORTH:
/*  50 */         return 0;
/*     */       case NORTH_NORTH_EAST:
/*  52 */         return 1;
/*     */       case NORTH_EAST:
/*  54 */         return 2;
/*     */       case EAST_NORTH_EAST:
/*  56 */         return 3;
/*     */       case EAST:
/*  58 */         return 4;
/*     */     } 
/*  60 */     throw new AssertionError(type);
/*     */   }
/*     */ 
/*     */   
/*     */   static byte getBlockFace(BlockFace rotation) {
/*  65 */     switch (rotation) {
/*     */       case NORTH:
/*  67 */         return 0;
/*     */       case NORTH_NORTH_EAST:
/*  69 */         return 1;
/*     */       case NORTH_EAST:
/*  71 */         return 2;
/*     */       case EAST_NORTH_EAST:
/*  73 */         return 3;
/*     */       case EAST:
/*  75 */         return 4;
/*     */       case EAST_SOUTH_EAST:
/*  77 */         return 5;
/*     */       case SOUTH_EAST:
/*  79 */         return 6;
/*     */       case SOUTH_SOUTH_EAST:
/*  81 */         return 7;
/*     */       case SOUTH:
/*  83 */         return 8;
/*     */       case SOUTH_SOUTH_WEST:
/*  85 */         return 9;
/*     */       case SOUTH_WEST:
/*  87 */         return 10;
/*     */       case WEST_SOUTH_WEST:
/*  89 */         return 11;
/*     */       case WEST:
/*  91 */         return 12;
/*     */       case WEST_NORTH_WEST:
/*  93 */         return 13;
/*     */       case NORTH_WEST:
/*  95 */         return 14;
/*     */       case NORTH_NORTH_WEST:
/*  97 */         return 15;
/*     */     } 
/*  99 */     throw new IllegalArgumentException("Invalid BlockFace rotation: " + rotation);
/*     */   }
/*     */ 
/*     */   
/*     */   static BlockFace getBlockFace(byte rotation) {
/* 104 */     switch (rotation) {
/*     */       case 0:
/* 106 */         return BlockFace.NORTH;
/*     */       case 1:
/* 108 */         return BlockFace.NORTH_NORTH_EAST;
/*     */       case 2:
/* 110 */         return BlockFace.NORTH_EAST;
/*     */       case 3:
/* 112 */         return BlockFace.EAST_NORTH_EAST;
/*     */       case 4:
/* 114 */         return BlockFace.EAST;
/*     */       case 5:
/* 116 */         return BlockFace.EAST_SOUTH_EAST;
/*     */       case 6:
/* 118 */         return BlockFace.SOUTH_EAST;
/*     */       case 7:
/* 120 */         return BlockFace.SOUTH_SOUTH_EAST;
/*     */       case 8:
/* 122 */         return BlockFace.SOUTH;
/*     */       case 9:
/* 124 */         return BlockFace.SOUTH_SOUTH_WEST;
/*     */       case 10:
/* 126 */         return BlockFace.SOUTH_WEST;
/*     */       case 11:
/* 128 */         return BlockFace.WEST_SOUTH_WEST;
/*     */       case 12:
/* 130 */         return BlockFace.WEST;
/*     */       case 13:
/* 132 */         return BlockFace.WEST_NORTH_WEST;
/*     */       case 14:
/* 134 */         return BlockFace.NORTH_WEST;
/*     */       case 15:
/* 136 */         return BlockFace.NORTH_NORTH_WEST;
/*     */     } 
/* 138 */     throw new AssertionError(rotation);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasOwner() {
/* 143 */     return (this.profile != null);
/*     */   }
/*     */   
/*     */   public String getOwner() {
/* 147 */     return hasOwner() ? this.profile.getName() : null;
/*     */   }
/*     */   
/*     */   public boolean setOwner(String name) {
/* 151 */     if (name == null || name.length() > 16) {
/* 152 */       return false;
/*     */     }
/*     */     
/* 155 */     GameProfile profile = MinecraftServer.getServer().getUserCache().getProfile(name);
/* 156 */     if (profile == null) {
/* 157 */       return false;
/*     */     }
/*     */     
/* 160 */     if (this.skullType != SkullType.PLAYER) {
/* 161 */       this.skullType = SkullType.PLAYER;
/*     */     }
/*     */     
/* 164 */     this.profile = profile;
/* 165 */     return true;
/*     */   }
/*     */   
/*     */   public BlockFace getRotation() {
/* 169 */     return getBlockFace(this.rotation);
/*     */   }
/*     */   
/*     */   public void setRotation(BlockFace rotation) {
/* 173 */     this.rotation = getBlockFace(rotation);
/*     */   }
/*     */   
/*     */   public SkullType getSkullType() {
/* 177 */     return this.skullType;
/*     */   }
/*     */   
/*     */   public void setSkullType(SkullType skullType) {
/* 181 */     this.skullType = skullType;
/*     */     
/* 183 */     if (skullType != SkullType.PLAYER) {
/* 184 */       this.profile = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean update(boolean force, boolean applyPhysics) {
/* 190 */     boolean result = super.update(force, applyPhysics);
/*     */     
/* 192 */     if (result) {
/* 193 */       if (this.skullType == SkullType.PLAYER) {
/* 194 */         this.skull.setGameProfile(this.profile);
/*     */       } else {
/* 196 */         this.skull.setSkullType(getSkullType(this.skullType));
/*     */       } 
/*     */       
/* 199 */       this.skull.setRotation(this.rotation);
/* 200 */       this.skull.update();
/*     */     } 
/*     */     
/* 203 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\block\CraftSkull.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */