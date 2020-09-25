package org.bukkit.metadata;

import java.util.List;
import org.bukkit.plugin.Plugin;

public interface MetadataStore<T> {
  void setMetadata(T paramT, String paramString, MetadataValue paramMetadataValue);
  
  List<MetadataValue> getMetadata(T paramT, String paramString);
  
  boolean hasMetadata(T paramT, String paramString);
  
  void removeMetadata(T paramT, String paramString, Plugin paramPlugin);
  
  void invalidateAll(Plugin paramPlugin);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\metadata\MetadataStore.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */