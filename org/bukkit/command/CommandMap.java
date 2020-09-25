package org.bukkit.command;

import java.util.List;

public interface CommandMap {
  void registerAll(String paramString, List<Command> paramList);
  
  boolean register(String paramString1, String paramString2, Command paramCommand);
  
  boolean register(String paramString, Command paramCommand);
  
  boolean dispatch(CommandSender paramCommandSender, String paramString) throws CommandException;
  
  void clearCommands();
  
  Command getCommand(String paramString);
  
  List<String> tabComplete(CommandSender paramCommandSender, String paramString) throws IllegalArgumentException;
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\CommandMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */