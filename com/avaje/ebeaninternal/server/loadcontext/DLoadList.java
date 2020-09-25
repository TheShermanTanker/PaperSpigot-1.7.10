package com.avaje.ebeaninternal.server.loadcontext;

import java.util.List;

public interface DLoadList<T> {
  int add(T paramT);
  
  List<T> getNextBatch(int paramInt);
  
  void removeEntry(int paramInt);
  
  List<T> getLoadBatch(int paramInt1, int paramInt2);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\loadcontext\DLoadList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */