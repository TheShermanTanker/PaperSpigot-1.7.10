package com.avaje.ebeaninternal.server.core;

import com.avaje.ebean.QueryIterator;
import com.avaje.ebean.QueryResultVisitor;
import com.avaje.ebean.bean.BeanCollection;
import com.avaje.ebeaninternal.api.SpiQuery;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SpiOrmQueryRequest<T> {
  SpiQuery<T> getQuery();
  
  BeanDescriptor<?> getBeanDescriptor();
  
  void initTransIfRequired();
  
  void endTransIfRequired();
  
  void rollbackTransIfRequired();
  
  Object findId();
  
  int findRowCount();
  
  List<Object> findIds();
  
  void findVisit(QueryResultVisitor<T> paramQueryResultVisitor);
  
  QueryIterator<T> findIterate();
  
  List<T> findList();
  
  Set<?> findSet();
  
  Map<?, ?> findMap();
  
  BeanCollection<T> getFromQueryCache();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\core\SpiOrmQueryRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */