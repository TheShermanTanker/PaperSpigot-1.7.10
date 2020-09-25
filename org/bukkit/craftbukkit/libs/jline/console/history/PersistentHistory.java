package org.bukkit.craftbukkit.libs.jline.console.history;

import java.io.IOException;

public interface PersistentHistory extends History {
  void flush() throws IOException;
  
  void purge() throws IOException;
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\jline\console\history\PersistentHistory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */