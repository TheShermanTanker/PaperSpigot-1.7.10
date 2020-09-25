package com.avaje.ebean.event;

import java.util.Set;

public interface BeanPersistListener<T> {
  boolean inserted(T paramT);
  
  boolean updated(T paramT, Set<String> paramSet);
  
  boolean deleted(T paramT);
  
  void remoteInsert(Object paramObject);
  
  void remoteUpdate(Object paramObject);
  
  void remoteDelete(Object paramObject);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\event\BeanPersistListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */