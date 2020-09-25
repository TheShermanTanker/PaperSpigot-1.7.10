package org.bukkit.scheduler;

import org.bukkit.plugin.Plugin;

public interface BukkitTask {
  int getTaskId();
  
  Plugin getOwner();
  
  boolean isSync();
  
  void cancel();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\scheduler\BukkitTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */