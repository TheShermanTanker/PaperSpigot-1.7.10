package net.minecraft.server.v1_7_R4;

public interface IBlockAccess {
  Block getType(int paramInt1, int paramInt2, int paramInt3);
  
  TileEntity getTileEntity(int paramInt1, int paramInt2, int paramInt3);
  
  int getData(int paramInt1, int paramInt2, int paramInt3);
  
  int getBlockPower(int paramInt1, int paramInt2, int paramInt3, int paramInt4);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\IBlockAccess.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */