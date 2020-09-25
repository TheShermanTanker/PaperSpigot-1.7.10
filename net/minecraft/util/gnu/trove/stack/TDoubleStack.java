package net.minecraft.util.gnu.trove.stack;

public interface TDoubleStack {
  double getNoEntryValue();
  
  void push(double paramDouble);
  
  double pop();
  
  double peek();
  
  int size();
  
  void clear();
  
  double[] toArray();
  
  void toArray(double[] paramArrayOfdouble);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\stack\TDoubleStack.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */