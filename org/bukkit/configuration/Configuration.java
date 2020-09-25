package org.bukkit.configuration;

import java.util.Map;

public interface Configuration extends ConfigurationSection {
  void addDefault(String paramString, Object paramObject);
  
  void addDefaults(Map<String, Object> paramMap);
  
  void addDefaults(Configuration paramConfiguration);
  
  void setDefaults(Configuration paramConfiguration);
  
  Configuration getDefaults();
  
  ConfigurationOptions options();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\configuration\Configuration.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */