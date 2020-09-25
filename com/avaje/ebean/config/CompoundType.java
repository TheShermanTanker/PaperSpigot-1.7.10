package com.avaje.ebean.config;

public interface CompoundType<V> {
  V create(Object[] paramArrayOfObject);
  
  CompoundTypeProperty<V, ?>[] getProperties();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\CompoundType.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */