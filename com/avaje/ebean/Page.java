package com.avaje.ebean;

import java.util.List;

public interface Page<T> {
  List<T> getList();
  
  int getTotalRowCount();
  
  int getTotalPageCount();
  
  int getPageIndex();
  
  boolean hasNext();
  
  boolean hasPrev();
  
  Page<T> next();
  
  Page<T> prev();
  
  String getDisplayXtoYofZ(String paramString1, String paramString2);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\Page.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */