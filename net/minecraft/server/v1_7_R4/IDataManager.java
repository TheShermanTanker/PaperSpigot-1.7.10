package net.minecraft.server.v1_7_R4;

import java.io.File;
import java.util.UUID;

public interface IDataManager {
  WorldData getWorldData();
  
  void checkSession() throws ExceptionWorldConflict;
  
  IChunkLoader createChunkLoader(WorldProvider paramWorldProvider);
  
  void saveWorldData(WorldData paramWorldData, NBTTagCompound paramNBTTagCompound);
  
  void saveWorldData(WorldData paramWorldData);
  
  IPlayerFileData getPlayerFileData();
  
  void a();
  
  File getDirectory();
  
  File getDataFile(String paramString);
  
  String g();
  
  UUID getUUID();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\IDataManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */