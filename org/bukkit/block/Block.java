package org.bukkit.block;

import java.util.Collection;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.Metadatable;

public interface Block extends Metadatable {
  @Deprecated
  byte getData();
  
  Block getRelative(int paramInt1, int paramInt2, int paramInt3);
  
  Block getRelative(BlockFace paramBlockFace);
  
  Block getRelative(BlockFace paramBlockFace, int paramInt);
  
  Material getType();
  
  @Deprecated
  int getTypeId();
  
  byte getLightLevel();
  
  byte getLightFromSky();
  
  byte getLightFromBlocks();
  
  World getWorld();
  
  int getX();
  
  int getY();
  
  int getZ();
  
  Location getLocation();
  
  Location getLocation(Location paramLocation);
  
  Chunk getChunk();
  
  @Deprecated
  void setData(byte paramByte);
  
  @Deprecated
  void setData(byte paramByte, boolean paramBoolean);
  
  void setType(Material paramMaterial);
  
  @Deprecated
  boolean setTypeId(int paramInt);
  
  @Deprecated
  boolean setTypeId(int paramInt, boolean paramBoolean);
  
  @Deprecated
  boolean setTypeIdAndData(int paramInt, byte paramByte, boolean paramBoolean);
  
  BlockFace getFace(Block paramBlock);
  
  BlockState getState();
  
  Biome getBiome();
  
  void setBiome(Biome paramBiome);
  
  boolean isBlockPowered();
  
  boolean isBlockIndirectlyPowered();
  
  boolean isBlockFacePowered(BlockFace paramBlockFace);
  
  boolean isBlockFaceIndirectlyPowered(BlockFace paramBlockFace);
  
  int getBlockPower(BlockFace paramBlockFace);
  
  int getBlockPower();
  
  boolean isEmpty();
  
  boolean isLiquid();
  
  double getTemperature();
  
  double getHumidity();
  
  PistonMoveReaction getPistonMoveReaction();
  
  boolean breakNaturally();
  
  boolean breakNaturally(ItemStack paramItemStack);
  
  Collection<ItemStack> getDrops();
  
  Collection<ItemStack> getDrops(ItemStack paramItemStack);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\block\Block.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */