package net.minecraft.util.gnu.trove.stack;

public interface TCharStack {
  char getNoEntryValue();
  
  void push(char paramChar);
  
  char pop();
  
  char peek();
  
  int size();
  
  void clear();
  
  char[] toArray();
  
  void toArray(char[] paramArrayOfchar);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\stack\TCharStack.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */