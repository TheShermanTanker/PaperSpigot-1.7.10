/*     */ package org.bukkit;
/*     */ 
/*     */ import com.avaje.ebean.config.ServerConfig;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.File;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.logging.Logger;
/*     */ import net.md_5.bungee.api.chat.BaseComponent;
/*     */ import org.bukkit.command.CommandException;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.command.ConsoleCommandSender;
/*     */ import org.bukkit.command.PluginCommand;
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.inventory.InventoryType;
/*     */ import org.bukkit.help.HelpMap;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.InventoryHolder;
/*     */ import org.bukkit.inventory.ItemFactory;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.Recipe;
/*     */ import org.bukkit.map.MapView;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ import org.bukkit.plugin.ServicesManager;
/*     */ import org.bukkit.plugin.messaging.Messenger;
/*     */ import org.bukkit.plugin.messaging.PluginMessageRecipient;
/*     */ import org.bukkit.scheduler.BukkitScheduler;
/*     */ import org.bukkit.scoreboard.ScoreboardManager;
/*     */ import org.bukkit.util.CachedServerIcon;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface Server
/*     */   extends PluginMessageRecipient
/*     */ {
/*     */   public static final String BROADCAST_CHANNEL_ADMINISTRATIVE = "bukkit.broadcast.admin";
/*     */   public static final String BROADCAST_CHANNEL_USERS = "bukkit.broadcast.user";
/*     */   
/*     */   String getName();
/*     */   
/*     */   String getVersion();
/*     */   
/*     */   String getBukkitVersion();
/*     */   
/*     */   @Deprecated
/*     */   Player[] getOnlinePlayers();
/*     */   
/*     */   Collection<? extends Player> getOnlinePlayers();
/*     */   
/*     */   int getMaxPlayers();
/*     */   
/*     */   int getPort();
/*     */   
/*     */   int getViewDistance();
/*     */   
/*     */   String getIp();
/*     */   
/*     */   String getServerName();
/*     */   
/*     */   String getServerId();
/*     */   
/*     */   String getWorldType();
/*     */   
/*     */   boolean getGenerateStructures();
/*     */   
/*     */   boolean getAllowEnd();
/*     */   
/*     */   boolean getAllowNether();
/*     */   
/*     */   boolean hasWhitelist();
/*     */   
/*     */   void setWhitelist(boolean paramBoolean);
/*     */   
/*     */   Set<OfflinePlayer> getWhitelistedPlayers();
/*     */   
/*     */   void reloadWhitelist();
/*     */   
/*     */   int broadcastMessage(String paramString);
/*     */   
/*     */   String getUpdateFolder();
/*     */   
/*     */   File getUpdateFolderFile();
/*     */   
/*     */   long getConnectionThrottle();
/*     */   
/*     */   int getTicksPerAnimalSpawns();
/*     */   
/*     */   int getTicksPerMonsterSpawns();
/*     */   
/*     */   Player getPlayer(String paramString);
/*     */   
/*     */   Player getPlayerExact(String paramString);
/*     */   
/*     */   List<Player> matchPlayer(String paramString);
/*     */   
/*     */   Player getPlayer(UUID paramUUID);
/*     */   
/*     */   PluginManager getPluginManager();
/*     */   
/*     */   BukkitScheduler getScheduler();
/*     */   
/*     */   ServicesManager getServicesManager();
/*     */   
/*     */   List<World> getWorlds();
/*     */   
/*     */   World createWorld(WorldCreator paramWorldCreator);
/*     */   
/*     */   boolean unloadWorld(String paramString, boolean paramBoolean);
/*     */   
/*     */   boolean unloadWorld(World paramWorld, boolean paramBoolean);
/*     */   
/*     */   World getWorld(String paramString);
/*     */   
/*     */   World getWorld(UUID paramUUID);
/*     */   
/*     */   @Deprecated
/*     */   MapView getMap(short paramShort);
/*     */   
/*     */   MapView createMap(World paramWorld);
/*     */   
/*     */   void reload();
/*     */   
/*     */   Logger getLogger();
/*     */   
/*     */   PluginCommand getPluginCommand(String paramString);
/*     */   
/*     */   void savePlayers();
/*     */   
/*     */   boolean dispatchCommand(CommandSender paramCommandSender, String paramString) throws CommandException;
/*     */   
/*     */   void configureDbConfig(ServerConfig paramServerConfig);
/*     */   
/*     */   boolean addRecipe(Recipe paramRecipe);
/*     */   
/*     */   List<Recipe> getRecipesFor(ItemStack paramItemStack);
/*     */   
/*     */   Iterator<Recipe> recipeIterator();
/*     */   
/*     */   void clearRecipes();
/*     */   
/*     */   void resetRecipes();
/*     */   
/*     */   Map<String, String[]> getCommandAliases();
/*     */   
/*     */   int getSpawnRadius();
/*     */   
/*     */   void setSpawnRadius(int paramInt);
/*     */   
/*     */   boolean getOnlineMode();
/*     */   
/*     */   boolean getAllowFlight();
/*     */   
/*     */   boolean isHardcore();
/*     */   
/*     */   boolean useExactLoginLocation();
/*     */   
/*     */   void shutdown();
/*     */   
/*     */   int broadcast(String paramString1, String paramString2);
/*     */   
/*     */   @Deprecated
/*     */   OfflinePlayer getOfflinePlayer(String paramString);
/*     */   
/*     */   OfflinePlayer getOfflinePlayer(UUID paramUUID);
/*     */   
/*     */   Set<String> getIPBans();
/*     */   
/*     */   void banIP(String paramString);
/*     */   
/*     */   void unbanIP(String paramString);
/*     */   
/*     */   Set<OfflinePlayer> getBannedPlayers();
/*     */   
/*     */   BanList getBanList(BanList.Type paramType);
/*     */   
/*     */   Set<OfflinePlayer> getOperators();
/*     */   
/*     */   GameMode getDefaultGameMode();
/*     */   
/*     */   void setDefaultGameMode(GameMode paramGameMode);
/*     */   
/*     */   ConsoleCommandSender getConsoleSender();
/*     */   
/*     */   File getWorldContainer();
/*     */   
/*     */   OfflinePlayer[] getOfflinePlayers();
/*     */   
/*     */   Messenger getMessenger();
/*     */   
/*     */   HelpMap getHelpMap();
/*     */   
/*     */   Inventory createInventory(InventoryHolder paramInventoryHolder, InventoryType paramInventoryType);
/*     */   
/*     */   Inventory createInventory(InventoryHolder paramInventoryHolder, InventoryType paramInventoryType, String paramString);
/*     */   
/*     */   Inventory createInventory(InventoryHolder paramInventoryHolder, int paramInt) throws IllegalArgumentException;
/*     */   
/*     */   Inventory createInventory(InventoryHolder paramInventoryHolder, int paramInt, String paramString) throws IllegalArgumentException;
/*     */   
/*     */   int getMonsterSpawnLimit();
/*     */   
/*     */   int getAnimalSpawnLimit();
/*     */   
/*     */   int getWaterAnimalSpawnLimit();
/*     */   
/*     */   int getAmbientSpawnLimit();
/*     */   
/*     */   boolean isPrimaryThread();
/*     */   
/*     */   String getMotd();
/*     */   
/*     */   String getShutdownMessage();
/*     */   
/*     */   Warning.WarningState getWarningState();
/*     */   
/*     */   ItemFactory getItemFactory();
/*     */   
/*     */   ScoreboardManager getScoreboardManager();
/*     */   
/*     */   CachedServerIcon getServerIcon();
/*     */   
/*     */   CachedServerIcon loadServerIcon(File paramFile) throws IllegalArgumentException, Exception;
/*     */   
/*     */   CachedServerIcon loadServerIcon(BufferedImage paramBufferedImage) throws IllegalArgumentException, Exception;
/*     */   
/*     */   void setIdleTimeout(int paramInt);
/*     */   
/*     */   int getIdleTimeout();
/*     */   
/*     */   @Deprecated
/*     */   UnsafeValues getUnsafe();
/*     */   
/*     */   Spigot spigot();
/*     */   
/*     */   public static class Spigot
/*     */   {
/*     */     public YamlConfiguration getConfig() {
/* 916 */       throw new UnsupportedOperationException("Not supported yet.");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void broadcast(BaseComponent component) {
/* 926 */       throw new UnsupportedOperationException("Not supported yet.");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void broadcast(BaseComponent... components) {
/* 937 */       throw new UnsupportedOperationException("Not supported yet.");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public double[] getTPS() {
/* 943 */       throw new UnsupportedOperationException("Not supported yet.");
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\Server.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */