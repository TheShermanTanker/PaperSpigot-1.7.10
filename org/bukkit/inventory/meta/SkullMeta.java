package org.bukkit.inventory.meta;

public interface SkullMeta extends ItemMeta {
  String getOwner();
  
  boolean hasOwner();
  
  boolean setOwner(String paramString);
  
  SkullMeta clone();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\inventory\meta\SkullMeta.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */