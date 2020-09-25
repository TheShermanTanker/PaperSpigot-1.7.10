package net.minecraft.util.gnu.trove.stack;

public interface TByteStack {
  byte getNoEntryValue();
  
  void push(byte paramByte);
  
  byte pop();
  
  byte peek();
  
  int size();
  
  void clear();
  
  byte[] toArray();
  
  void toArray(byte[] paramArrayOfbyte);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\stack\TByteStack.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */