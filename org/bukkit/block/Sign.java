package org.bukkit.block;

public interface Sign extends BlockState {
  String[] getLines();
  
  String getLine(int paramInt) throws IndexOutOfBoundsException;
  
  void setLine(int paramInt, String paramString) throws IndexOutOfBoundsException;
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\block\Sign.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */