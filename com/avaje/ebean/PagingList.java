package com.avaje.ebean;

import java.util.List;
import java.util.concurrent.Future;

public interface PagingList<T> {
  void refresh();
  
  PagingList<T> setFetchAhead(boolean paramBoolean);
  
  Future<Integer> getFutureRowCount();
  
  List<T> getAsList();
  
  int getPageSize();
  
  int getTotalRowCount();
  
  int getTotalPageCount();
  
  Page<T> getPage(int paramInt);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\PagingList.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */