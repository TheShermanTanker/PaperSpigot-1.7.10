package net.minecraft.util.gnu.trove.stack;

public interface TLongStack {
  long getNoEntryValue();
  
  void push(long paramLong);
  
  long pop();
  
  long peek();
  
  int size();
  
  void clear();
  
  long[] toArray();
  
  void toArray(long[] paramArrayOflong);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\stack\TLongStack.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */