package org.bukkit.metadata;

import org.bukkit.plugin.Plugin;

public interface MetadataValue {
  Object value();
  
  int asInt();
  
  float asFloat();
  
  double asDouble();
  
  long asLong();
  
  short asShort();
  
  byte asByte();
  
  boolean asBoolean();
  
  String asString();
  
  Plugin getOwningPlugin();
  
  void invalidate();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\metadata\MetadataValue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */