package net.minecraft.server.v1_7_R4;

import java.util.List;

public interface ICommand extends Comparable {
  String getCommand();
  
  String c(ICommandListener paramICommandListener);
  
  List b();
  
  void execute(ICommandListener paramICommandListener, String[] paramArrayOfString);
  
  boolean canUse(ICommandListener paramICommandListener);
  
  List tabComplete(ICommandListener paramICommandListener, String[] paramArrayOfString);
  
  boolean isListStart(String[] paramArrayOfString, int paramInt);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ICommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */