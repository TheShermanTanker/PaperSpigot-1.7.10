package com.avaje.ebean;

import java.util.List;
import java.util.concurrent.Future;

public interface FutureIds<T> extends Future<List<Object>> {
  Query<T> getQuery();
  
  List<Object> getPartialIds();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\FutureIds.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */