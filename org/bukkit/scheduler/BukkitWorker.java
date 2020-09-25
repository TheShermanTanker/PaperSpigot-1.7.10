package org.bukkit.scheduler;

import org.bukkit.plugin.Plugin;

public interface BukkitWorker {
  int getTaskId();
  
  Plugin getOwner();
  
  Thread getThread();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\scheduler\BukkitWorker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */