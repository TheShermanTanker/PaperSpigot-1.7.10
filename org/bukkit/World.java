/*      */ package org.bukkit;
/*      */ 
/*      */ import java.io.File;
/*      */ import java.util.Collection;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.UUID;
/*      */ import org.bukkit.block.Biome;
/*      */ import org.bukkit.block.Block;
/*      */ import org.bukkit.entity.Arrow;
/*      */ import org.bukkit.entity.CreatureType;
/*      */ import org.bukkit.entity.Entity;
/*      */ import org.bukkit.entity.EntityType;
/*      */ import org.bukkit.entity.FallingBlock;
/*      */ import org.bukkit.entity.Item;
/*      */ import org.bukkit.entity.LightningStrike;
/*      */ import org.bukkit.entity.LivingEntity;
/*      */ import org.bukkit.entity.Player;
/*      */ import org.bukkit.generator.BlockPopulator;
/*      */ import org.bukkit.generator.ChunkGenerator;
/*      */ import org.bukkit.inventory.ItemStack;
/*      */ import org.bukkit.metadata.Metadatable;
/*      */ import org.bukkit.plugin.messaging.PluginMessageRecipient;
/*      */ import org.bukkit.util.Vector;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public interface World
/*      */   extends PluginMessageRecipient, Metadatable
/*      */ {
/*      */   Block getBlockAt(int paramInt1, int paramInt2, int paramInt3);
/*      */   
/*      */   Block getBlockAt(Location paramLocation);
/*      */   
/*      */   @Deprecated
/*      */   int getBlockTypeIdAt(int paramInt1, int paramInt2, int paramInt3);
/*      */   
/*      */   @Deprecated
/*      */   int getBlockTypeIdAt(Location paramLocation);
/*      */   
/*      */   int getHighestBlockYAt(int paramInt1, int paramInt2);
/*      */   
/*      */   int getHighestBlockYAt(Location paramLocation);
/*      */   
/*      */   Block getHighestBlockAt(int paramInt1, int paramInt2);
/*      */   
/*      */   Block getHighestBlockAt(Location paramLocation);
/*      */   
/*      */   Chunk getChunkAt(int paramInt1, int paramInt2);
/*      */   
/*      */   Chunk getChunkAt(Location paramLocation);
/*      */   
/*      */   Chunk getChunkAt(Block paramBlock);
/*      */   
/*      */   void getChunkAtAsync(int paramInt1, int paramInt2, ChunkLoadCallback paramChunkLoadCallback);
/*      */   
/*      */   void getChunkAtAsync(Location paramLocation, ChunkLoadCallback paramChunkLoadCallback);
/*      */   
/*      */   void getChunkAtAsync(Block paramBlock, ChunkLoadCallback paramChunkLoadCallback);
/*      */   
/*      */   boolean isChunkLoaded(Chunk paramChunk);
/*      */   
/*      */   Chunk[] getLoadedChunks();
/*      */   
/*      */   void loadChunk(Chunk paramChunk);
/*      */   
/*      */   boolean isChunkLoaded(int paramInt1, int paramInt2);
/*      */   
/*      */   boolean isChunkInUse(int paramInt1, int paramInt2);
/*      */   
/*      */   void loadChunk(int paramInt1, int paramInt2);
/*      */   
/*      */   boolean loadChunk(int paramInt1, int paramInt2, boolean paramBoolean);
/*      */   
/*      */   boolean unloadChunk(Chunk paramChunk);
/*      */   
/*      */   boolean unloadChunk(int paramInt1, int paramInt2);
/*      */   
/*      */   boolean unloadChunk(int paramInt1, int paramInt2, boolean paramBoolean);
/*      */   
/*      */   boolean unloadChunk(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2);
/*      */   
/*      */   boolean unloadChunkRequest(int paramInt1, int paramInt2);
/*      */   
/*      */   boolean unloadChunkRequest(int paramInt1, int paramInt2, boolean paramBoolean);
/*      */   
/*      */   boolean regenerateChunk(int paramInt1, int paramInt2);
/*      */   
/*      */   boolean refreshChunk(int paramInt1, int paramInt2);
/*      */   
/*      */   Item dropItem(Location paramLocation, ItemStack paramItemStack);
/*      */   
/*      */   Item dropItemNaturally(Location paramLocation, ItemStack paramItemStack);
/*      */   
/*      */   Arrow spawnArrow(Location paramLocation, Vector paramVector, float paramFloat1, float paramFloat2);
/*      */   
/*      */   boolean generateTree(Location paramLocation, TreeType paramTreeType);
/*      */   
/*      */   boolean generateTree(Location paramLocation, TreeType paramTreeType, BlockChangeDelegate paramBlockChangeDelegate);
/*      */   
/*      */   Entity spawnEntity(Location paramLocation, EntityType paramEntityType);
/*      */   
/*      */   @Deprecated
/*      */   LivingEntity spawnCreature(Location paramLocation, EntityType paramEntityType);
/*      */   
/*      */   @Deprecated
/*      */   LivingEntity spawnCreature(Location paramLocation, CreatureType paramCreatureType);
/*      */   
/*      */   LightningStrike strikeLightning(Location paramLocation);
/*      */   
/*      */   LightningStrike strikeLightningEffect(Location paramLocation);
/*      */   
/*      */   List<Entity> getEntities();
/*      */   
/*      */   List<LivingEntity> getLivingEntities();
/*      */   
/*      */   @Deprecated
/*      */   <T extends Entity> Collection<T> getEntitiesByClass(Class<T>... paramVarArgs);
/*      */   
/*      */   <T extends Entity> Collection<T> getEntitiesByClass(Class<T> paramClass);
/*      */   
/*      */   Collection<Entity> getEntitiesByClasses(Class<?>... paramVarArgs);
/*      */   
/*      */   List<Player> getPlayers();
/*      */   
/*      */   String getName();
/*      */   
/*      */   UUID getUID();
/*      */   
/*      */   Location getSpawnLocation();
/*      */   
/*      */   boolean setSpawnLocation(int paramInt1, int paramInt2, int paramInt3);
/*      */   
/*      */   long getTime();
/*      */   
/*      */   void setTime(long paramLong);
/*      */   
/*      */   long getFullTime();
/*      */   
/*      */   void setFullTime(long paramLong);
/*      */   
/*      */   boolean hasStorm();
/*      */   
/*      */   void setStorm(boolean paramBoolean);
/*      */   
/*      */   int getWeatherDuration();
/*      */   
/*      */   void setWeatherDuration(int paramInt);
/*      */   
/*      */   boolean isThundering();
/*      */   
/*      */   void setThundering(boolean paramBoolean);
/*      */   
/*      */   int getThunderDuration();
/*      */   
/*      */   void setThunderDuration(int paramInt);
/*      */   
/*      */   boolean createExplosion(double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat);
/*      */   
/*      */   boolean createExplosion(double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat, boolean paramBoolean);
/*      */   
/*      */   boolean createExplosion(double paramDouble1, double paramDouble2, double paramDouble3, float paramFloat, boolean paramBoolean1, boolean paramBoolean2);
/*      */   
/*      */   boolean createExplosion(Location paramLocation, float paramFloat);
/*      */   
/*      */   boolean createExplosion(Location paramLocation, float paramFloat, boolean paramBoolean);
/*      */   
/*      */   Environment getEnvironment();
/*      */   
/*      */   long getSeed();
/*      */   
/*      */   boolean getPVP();
/*      */   
/*      */   void setPVP(boolean paramBoolean);
/*      */   
/*      */   ChunkGenerator getGenerator();
/*      */   
/*      */   void save();
/*      */   
/*      */   List<BlockPopulator> getPopulators();
/*      */   
/*      */   <T extends Entity> T spawn(Location paramLocation, Class<T> paramClass) throws IllegalArgumentException;
/*      */   
/*      */   @Deprecated
/*      */   FallingBlock spawnFallingBlock(Location paramLocation, Material paramMaterial, byte paramByte) throws IllegalArgumentException;
/*      */   
/*      */   @Deprecated
/*      */   FallingBlock spawnFallingBlock(Location paramLocation, int paramInt, byte paramByte) throws IllegalArgumentException;
/*      */   
/*      */   void playEffect(Location paramLocation, Effect paramEffect, int paramInt);
/*      */   
/*      */   void playEffect(Location paramLocation, Effect paramEffect, int paramInt1, int paramInt2);
/*      */   
/*      */   <T> void playEffect(Location paramLocation, Effect paramEffect, T paramT);
/*      */   
/*      */   <T> void playEffect(Location paramLocation, Effect paramEffect, T paramT, int paramInt);
/*      */   
/*      */   ChunkSnapshot getEmptyChunkSnapshot(int paramInt1, int paramInt2, boolean paramBoolean1, boolean paramBoolean2);
/*      */   
/*      */   void setSpawnFlags(boolean paramBoolean1, boolean paramBoolean2);
/*      */   
/*      */   boolean getAllowAnimals();
/*      */   
/*      */   boolean getAllowMonsters();
/*      */   
/*      */   Biome getBiome(int paramInt1, int paramInt2);
/*      */   
/*      */   void setBiome(int paramInt1, int paramInt2, Biome paramBiome);
/*      */   
/*      */   double getTemperature(int paramInt1, int paramInt2);
/*      */   
/*      */   double getHumidity(int paramInt1, int paramInt2);
/*      */   
/*      */   int getMaxHeight();
/*      */   
/*      */   int getSeaLevel();
/*      */   
/*      */   boolean getKeepSpawnInMemory();
/*      */   
/*      */   void setKeepSpawnInMemory(boolean paramBoolean);
/*      */   
/*      */   boolean isAutoSave();
/*      */   
/*      */   void setAutoSave(boolean paramBoolean);
/*      */   
/*      */   void setDifficulty(Difficulty paramDifficulty);
/*      */   
/*      */   Difficulty getDifficulty();
/*      */   
/*      */   File getWorldFolder();
/*      */   
/*      */   WorldType getWorldType();
/*      */   
/*      */   boolean canGenerateStructures();
/*      */   
/*      */   long getTicksPerAnimalSpawns();
/*      */   
/*      */   void setTicksPerAnimalSpawns(int paramInt);
/*      */   
/*      */   long getTicksPerMonsterSpawns();
/*      */   
/*      */   void setTicksPerMonsterSpawns(int paramInt);
/*      */   
/*      */   int getMonsterSpawnLimit();
/*      */   
/*      */   void setMonsterSpawnLimit(int paramInt);
/*      */   
/*      */   int getAnimalSpawnLimit();
/*      */   
/*      */   void setAnimalSpawnLimit(int paramInt);
/*      */   
/*      */   int getWaterAnimalSpawnLimit();
/*      */   
/*      */   void setWaterAnimalSpawnLimit(int paramInt);
/*      */   
/*      */   int getAmbientSpawnLimit();
/*      */   
/*      */   void setAmbientSpawnLimit(int paramInt);
/*      */   
/*      */   void playSound(Location paramLocation, Sound paramSound, float paramFloat1, float paramFloat2);
/*      */   
/*      */   String[] getGameRules();
/*      */   
/*      */   String getGameRuleValue(String paramString);
/*      */   
/*      */   boolean setGameRuleValue(String paramString1, String paramString2);
/*      */   
/*      */   boolean isGameRule(String paramString);
/*      */   
/*      */   Spigot spigot();
/*      */   
/*      */   public static interface ChunkLoadCallback
/*      */   {
/*      */     void onLoad(Chunk param1Chunk);
/*      */   }
/*      */   
/*      */   public static class Spigot
/*      */   {
/*      */     public void playEffect(Location location, Effect effect) {
/* 1183 */       throw new UnsupportedOperationException("Not supported yet.");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void playEffect(Location location, Effect effect, int id, int data, float offsetX, float offsetY, float offsetZ, float speed, int particleCount, int radius) {
/* 1210 */       throw new UnsupportedOperationException("Not supported yet.");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public LightningStrike strikeLightning(Location loc, boolean isSilent) {
/* 1222 */       throw new UnsupportedOperationException("Not supported yet.");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public LightningStrike strikeLightningEffect(Location loc, boolean isSilent) {
/* 1234 */       throw new UnsupportedOperationException("Not supported yet.");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public enum Environment
/*      */   {
/* 1249 */     NORMAL(0),
/*      */ 
/*      */ 
/*      */     
/* 1253 */     NETHER(-1),
/*      */ 
/*      */ 
/*      */     
/* 1257 */     THE_END(1);
/*      */     
/*      */     private final int id;
/* 1260 */     private static final Map<Integer, Environment> lookup = new HashMap<Integer, Environment>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     static {
/* 1290 */       for (Environment env : values())
/* 1291 */         lookup.put(Integer.valueOf(env.getId()), env); 
/*      */     }
/*      */     
/*      */     Environment(int id) {
/*      */       this.id = id;
/*      */     }
/*      */     
/*      */     @Deprecated
/*      */     public int getId() {
/*      */       return this.id;
/*      */     }
/*      */     
/*      */     @Deprecated
/*      */     public static Environment getEnvironment(int id) {
/*      */       return lookup.get(Integer.valueOf(id));
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\World.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */