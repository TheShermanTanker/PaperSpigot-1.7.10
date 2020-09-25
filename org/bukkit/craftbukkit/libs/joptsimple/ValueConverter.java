package org.bukkit.craftbukkit.libs.joptsimple;

public interface ValueConverter<V> {
  V convert(String paramString);
  
  Class<V> valueType();
  
  String valuePattern();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\joptsimple\ValueConverter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */