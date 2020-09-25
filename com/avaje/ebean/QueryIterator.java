package com.avaje.ebean;

import java.io.Closeable;
import java.util.Iterator;

public interface QueryIterator<T> extends Iterator<T>, Closeable {
  boolean hasNext();
  
  T next();
  
  void remove();
  
  void close();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\QueryIterator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */