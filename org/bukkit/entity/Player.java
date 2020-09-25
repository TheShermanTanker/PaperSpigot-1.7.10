/*      */ package org.bukkit.entity;
/*      */ 
/*      */ import java.net.InetSocketAddress;
/*      */ import java.util.Set;
/*      */ import net.md_5.bungee.api.chat.BaseComponent;
/*      */ import org.bukkit.Achievement;
/*      */ import org.bukkit.Effect;
/*      */ import org.bukkit.Instrument;
/*      */ import org.bukkit.Location;
/*      */ import org.bukkit.Material;
/*      */ import org.bukkit.Note;
/*      */ import org.bukkit.OfflinePlayer;
/*      */ import org.bukkit.Sound;
/*      */ import org.bukkit.Statistic;
/*      */ import org.bukkit.WeatherType;
/*      */ import org.bukkit.command.CommandSender;
/*      */ import org.bukkit.conversations.Conversable;
/*      */ import org.bukkit.map.MapView;
/*      */ import org.bukkit.plugin.messaging.PluginMessageRecipient;
/*      */ import org.bukkit.scoreboard.Scoreboard;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public interface Player
/*      */   extends HumanEntity, Conversable, CommandSender, OfflinePlayer, PluginMessageRecipient
/*      */ {
/*      */   String getDisplayName();
/*      */   
/*      */   void setDisplayName(String paramString);
/*      */   
/*      */   String getPlayerListName();
/*      */   
/*      */   void setPlayerListName(String paramString);
/*      */   
/*      */   void setCompassTarget(Location paramLocation);
/*      */   
/*      */   Location getCompassTarget();
/*      */   
/*      */   InetSocketAddress getAddress();
/*      */   
/*      */   void sendRawMessage(String paramString);
/*      */   
/*      */   void kickPlayer(String paramString);
/*      */   
/*      */   void chat(String paramString);
/*      */   
/*      */   boolean performCommand(String paramString);
/*      */   
/*      */   boolean isSneaking();
/*      */   
/*      */   void setSneaking(boolean paramBoolean);
/*      */   
/*      */   boolean isSprinting();
/*      */   
/*      */   void setSprinting(boolean paramBoolean);
/*      */   
/*      */   void saveData();
/*      */   
/*      */   void loadData();
/*      */   
/*      */   void setSleepingIgnored(boolean paramBoolean);
/*      */   
/*      */   boolean isSleepingIgnored();
/*      */   
/*      */   @Deprecated
/*      */   void playNote(Location paramLocation, byte paramByte1, byte paramByte2);
/*      */   
/*      */   void playNote(Location paramLocation, Instrument paramInstrument, Note paramNote);
/*      */   
/*      */   void playSound(Location paramLocation, Sound paramSound, float paramFloat1, float paramFloat2);
/*      */   
/*      */   @Deprecated
/*      */   void playSound(Location paramLocation, String paramString, float paramFloat1, float paramFloat2);
/*      */   
/*      */   @Deprecated
/*      */   void playEffect(Location paramLocation, Effect paramEffect, int paramInt);
/*      */   
/*      */   <T> void playEffect(Location paramLocation, Effect paramEffect, T paramT);
/*      */   
/*      */   @Deprecated
/*      */   void sendBlockChange(Location paramLocation, Material paramMaterial, byte paramByte);
/*      */   
/*      */   @Deprecated
/*      */   boolean sendChunkChange(Location paramLocation, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfbyte);
/*      */   
/*      */   @Deprecated
/*      */   void sendBlockChange(Location paramLocation, int paramInt, byte paramByte);
/*      */   
/*      */   void sendSignChange(Location paramLocation, String[] paramArrayOfString) throws IllegalArgumentException;
/*      */   
/*      */   void sendMap(MapView paramMapView);
/*      */   
/*      */   void updateInventory();
/*      */   
/*      */   void awardAchievement(Achievement paramAchievement);
/*      */   
/*      */   void removeAchievement(Achievement paramAchievement);
/*      */   
/*      */   boolean hasAchievement(Achievement paramAchievement);
/*      */   
/*      */   void incrementStatistic(Statistic paramStatistic) throws IllegalArgumentException;
/*      */   
/*      */   void decrementStatistic(Statistic paramStatistic) throws IllegalArgumentException;
/*      */   
/*      */   void incrementStatistic(Statistic paramStatistic, int paramInt) throws IllegalArgumentException;
/*      */   
/*      */   void decrementStatistic(Statistic paramStatistic, int paramInt) throws IllegalArgumentException;
/*      */   
/*      */   void setStatistic(Statistic paramStatistic, int paramInt) throws IllegalArgumentException;
/*      */   
/*      */   int getStatistic(Statistic paramStatistic) throws IllegalArgumentException;
/*      */   
/*      */   void incrementStatistic(Statistic paramStatistic, Material paramMaterial) throws IllegalArgumentException;
/*      */   
/*      */   void decrementStatistic(Statistic paramStatistic, Material paramMaterial) throws IllegalArgumentException;
/*      */   
/*      */   int getStatistic(Statistic paramStatistic, Material paramMaterial) throws IllegalArgumentException;
/*      */   
/*      */   void incrementStatistic(Statistic paramStatistic, Material paramMaterial, int paramInt) throws IllegalArgumentException;
/*      */   
/*      */   void decrementStatistic(Statistic paramStatistic, Material paramMaterial, int paramInt) throws IllegalArgumentException;
/*      */   
/*      */   void setStatistic(Statistic paramStatistic, Material paramMaterial, int paramInt) throws IllegalArgumentException;
/*      */   
/*      */   void incrementStatistic(Statistic paramStatistic, EntityType paramEntityType) throws IllegalArgumentException;
/*      */   
/*      */   void decrementStatistic(Statistic paramStatistic, EntityType paramEntityType) throws IllegalArgumentException;
/*      */   
/*      */   int getStatistic(Statistic paramStatistic, EntityType paramEntityType) throws IllegalArgumentException;
/*      */   
/*      */   void incrementStatistic(Statistic paramStatistic, EntityType paramEntityType, int paramInt) throws IllegalArgumentException;
/*      */   
/*      */   void decrementStatistic(Statistic paramStatistic, EntityType paramEntityType, int paramInt);
/*      */   
/*      */   void setStatistic(Statistic paramStatistic, EntityType paramEntityType, int paramInt);
/*      */   
/*      */   void setPlayerTime(long paramLong, boolean paramBoolean);
/*      */   
/*      */   long getPlayerTime();
/*      */   
/*      */   long getPlayerTimeOffset();
/*      */   
/*      */   boolean isPlayerTimeRelative();
/*      */   
/*      */   void resetPlayerTime();
/*      */   
/*      */   void setPlayerWeather(WeatherType paramWeatherType);
/*      */   
/*      */   WeatherType getPlayerWeather();
/*      */   
/*      */   void resetPlayerWeather();
/*      */   
/*      */   void giveExp(int paramInt);
/*      */   
/*      */   void giveExpLevels(int paramInt);
/*      */   
/*      */   float getExp();
/*      */   
/*      */   void setExp(float paramFloat);
/*      */   
/*      */   int getLevel();
/*      */   
/*      */   void setLevel(int paramInt);
/*      */   
/*      */   int getTotalExperience();
/*      */   
/*      */   void setTotalExperience(int paramInt);
/*      */   
/*      */   float getExhaustion();
/*      */   
/*      */   void setExhaustion(float paramFloat);
/*      */   
/*      */   float getSaturation();
/*      */   
/*      */   void setSaturation(float paramFloat);
/*      */   
/*      */   int getFoodLevel();
/*      */   
/*      */   void setFoodLevel(int paramInt);
/*      */   
/*      */   Location getBedSpawnLocation();
/*      */   
/*      */   void setBedSpawnLocation(Location paramLocation);
/*      */   
/*      */   void setBedSpawnLocation(Location paramLocation, boolean paramBoolean);
/*      */   
/*      */   boolean getAllowFlight();
/*      */   
/*      */   void setAllowFlight(boolean paramBoolean);
/*      */   
/*      */   void hidePlayer(Player paramPlayer);
/*      */   
/*      */   void showPlayer(Player paramPlayer);
/*      */   
/*      */   boolean canSee(Player paramPlayer);
/*      */   
/*      */   @Deprecated
/*      */   boolean isOnGround();
/*      */   
/*      */   boolean isFlying();
/*      */   
/*      */   void setFlying(boolean paramBoolean);
/*      */   
/*      */   void setFlySpeed(float paramFloat) throws IllegalArgumentException;
/*      */   
/*      */   void setWalkSpeed(float paramFloat) throws IllegalArgumentException;
/*      */   
/*      */   float getFlySpeed();
/*      */   
/*      */   float getWalkSpeed();
/*      */   
/*      */   @Deprecated
/*      */   void setTexturePack(String paramString);
/*      */   
/*      */   void setResourcePack(String paramString);
/*      */   
/*      */   Scoreboard getScoreboard();
/*      */   
/*      */   void setScoreboard(Scoreboard paramScoreboard) throws IllegalArgumentException, IllegalStateException;
/*      */   
/*      */   boolean isHealthScaled();
/*      */   
/*      */   void setHealthScaled(boolean paramBoolean);
/*      */   
/*      */   void setHealthScale(double paramDouble) throws IllegalArgumentException;
/*      */   
/*      */   double getHealthScale();
/*      */   
/*      */   Spigot spigot();
/*      */   
/*      */   public static class Spigot
/*      */     extends Entity.Spigot
/*      */   {
/*      */     public InetSocketAddress getRawAddress() {
/* 1049 */       throw new UnsupportedOperationException("Not supported yet.");
/*      */     }
/*      */ 
/*      */     
/*      */     public void playEffect(Location location, Effect effect, int id, int data, float offsetX, float offsetY, float offsetZ, float speed, int particleCount, int radius) {
/* 1054 */       throw new UnsupportedOperationException("Not supported yet.");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean getCollidesWithEntities() {
/* 1064 */       throw new UnsupportedOperationException("Not supported yet.");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setCollidesWithEntities(boolean collides) {
/* 1075 */       throw new UnsupportedOperationException("Not supported yet.");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void respawn() {
/* 1083 */       throw new UnsupportedOperationException("Not supported yet.");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public String getLocale() {
/* 1093 */       throw new UnsupportedOperationException("Not supported yet.");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public Set<Player> getHiddenPlayers() {
/* 1103 */       throw new UnsupportedOperationException("Not supported yet.");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void sendMessage(BaseComponent component) {
/* 1113 */       throw new UnsupportedOperationException("Not supported yet.");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void sendMessage(BaseComponent... components) {
/* 1124 */       throw new UnsupportedOperationException("Not supported yet.");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean getAffectsSpawning() {
/* 1135 */       throw new UnsupportedOperationException("Not supported yet.");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setAffectsSpawning(boolean affects) {
/* 1146 */       throw new UnsupportedOperationException("Not supported yet");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int getViewDistance() {
/* 1156 */       throw new UnsupportedOperationException("Not supported yet");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void setViewDistance(int viewDistance) {
/* 1166 */       throw new UnsupportedOperationException("Not supported yet");
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\entity\Player.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */