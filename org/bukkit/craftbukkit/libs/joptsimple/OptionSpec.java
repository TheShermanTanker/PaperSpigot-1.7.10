package org.bukkit.craftbukkit.libs.joptsimple;

import java.util.Collection;
import java.util.List;

public interface OptionSpec<V> {
  List<V> values(OptionSet paramOptionSet);
  
  V value(OptionSet paramOptionSet);
  
  Collection<String> options();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\joptsimple\OptionSpec.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */