package org.bukkit.command;

import org.bukkit.Server;
import org.bukkit.permissions.Permissible;

public interface CommandSender extends Permissible {
  void sendMessage(String paramString);
  
  void sendMessage(String[] paramArrayOfString);
  
  Server getServer();
  
  String getName();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\CommandSender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */