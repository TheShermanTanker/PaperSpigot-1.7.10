package org.bukkit.metadata;

import java.util.List;
import org.bukkit.plugin.Plugin;

public interface Metadatable {
  void setMetadata(String paramString, MetadataValue paramMetadataValue);
  
  List<MetadataValue> getMetadata(String paramString);
  
  boolean hasMetadata(String paramString);
  
  void removeMetadata(String paramString, Plugin paramPlugin);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\metadata\Metadatable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */