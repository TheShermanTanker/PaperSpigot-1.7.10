package com.avaje.ebeaninternal.server.core;

import com.avaje.ebean.QueryIterator;
import com.avaje.ebean.bean.BeanCollection;
import com.avaje.ebeaninternal.api.BeanIdList;

public interface OrmQueryEngine {
  <T> T findId(OrmQueryRequest<T> paramOrmQueryRequest);
  
  <T> BeanCollection<T> findMany(OrmQueryRequest<T> paramOrmQueryRequest);
  
  <T> QueryIterator<T> findIterate(OrmQueryRequest<T> paramOrmQueryRequest);
  
  <T> int findRowCount(OrmQueryRequest<T> paramOrmQueryRequest);
  
  <T> BeanIdList findIds(OrmQueryRequest<T> paramOrmQueryRequest);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\core\OrmQueryEngine.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */