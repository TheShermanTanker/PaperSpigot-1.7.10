package net.minecraft.server.v1_7_R4;

public interface ISourceBlock extends ILocationSource {
  double getX();
  
  double getY();
  
  double getZ();
  
  int getBlockX();
  
  int getBlockY();
  
  int getBlockZ();
  
  int h();
  
  TileEntity getTileEntity();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ISourceBlock.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */