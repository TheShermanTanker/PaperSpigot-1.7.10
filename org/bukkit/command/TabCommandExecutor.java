package org.bukkit.command;

import java.util.List;

@Deprecated
public interface TabCommandExecutor extends CommandExecutor {
  List<String> onTabComplete();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\TabCommandExecutor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */