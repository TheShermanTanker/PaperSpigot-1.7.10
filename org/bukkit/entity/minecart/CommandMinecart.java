package org.bukkit.entity.minecart;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Minecart;

public interface CommandMinecart extends Minecart, CommandSender {
  String getCommand();
  
  void setCommand(String paramString);
  
  void setName(String paramString);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\entity\minecart\CommandMinecart.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */