package net.minecraft.util.gnu.trove.stack;

public interface TFloatStack {
  float getNoEntryValue();
  
  void push(float paramFloat);
  
  float pop();
  
  float peek();
  
  int size();
  
  void clear();
  
  float[] toArray();
  
  void toArray(float[] paramArrayOffloat);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\stack\TFloatStack.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */