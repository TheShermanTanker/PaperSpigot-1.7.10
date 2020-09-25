package org.bukkit.plugin;

import com.avaje.ebean.EbeanServer;
import java.io.File;
import java.io.InputStream;
import java.util.logging.Logger;
import org.bukkit.Server;
import org.bukkit.command.TabExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.generator.ChunkGenerator;

public interface Plugin extends TabExecutor {
  File getDataFolder();
  
  PluginDescriptionFile getDescription();
  
  FileConfiguration getConfig();
  
  InputStream getResource(String paramString);
  
  void saveConfig();
  
  void saveDefaultConfig();
  
  void saveResource(String paramString, boolean paramBoolean);
  
  void reloadConfig();
  
  PluginLoader getPluginLoader();
  
  Server getServer();
  
  boolean isEnabled();
  
  void onDisable();
  
  void onLoad();
  
  void onEnable();
  
  boolean isNaggable();
  
  void setNaggable(boolean paramBoolean);
  
  EbeanServer getDatabase();
  
  ChunkGenerator getDefaultWorldGenerator(String paramString1, String paramString2);
  
  Logger getLogger();
  
  String getName();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\plugin\Plugin.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */