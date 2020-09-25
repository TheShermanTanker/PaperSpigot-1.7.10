package net.minecraft.util.gnu.trove.stack;

public interface TShortStack {
  short getNoEntryValue();
  
  void push(short paramShort);
  
  short pop();
  
  short peek();
  
  int size();
  
  void clear();
  
  short[] toArray();
  
  void toArray(short[] paramArrayOfshort);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\stack\TShortStack.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */