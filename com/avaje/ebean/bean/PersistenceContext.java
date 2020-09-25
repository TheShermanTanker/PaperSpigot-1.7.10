package com.avaje.ebean.bean;

public interface PersistenceContext {
  void put(Object paramObject1, Object paramObject2);
  
  Object putIfAbsent(Object paramObject1, Object paramObject2);
  
  Object get(Class<?> paramClass, Object paramObject);
  
  void clear();
  
  void clear(Class<?> paramClass);
  
  void clear(Class<?> paramClass, Object paramObject);
  
  int size(Class<?> paramClass);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\bean\PersistenceContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */