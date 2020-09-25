package com.avaje.ebeaninternal.server.el;

import java.util.Comparator;

public interface ElComparator<T> extends Comparator<T> {
  int compare(T paramT1, T paramT2);
  
  int compareValue(Object paramObject, T paramT);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\el\ElComparator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */