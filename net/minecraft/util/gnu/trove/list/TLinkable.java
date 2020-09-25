package net.minecraft.util.gnu.trove.list;

import java.io.Serializable;

public interface TLinkable<T extends TLinkable> extends Serializable {
  public static final long serialVersionUID = 997545054865482562L;
  
  T getNext();
  
  T getPrevious();
  
  void setNext(T paramT);
  
  void setPrevious(T paramT);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\gnu\trove\list\TLinkable.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */