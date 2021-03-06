package com.avaje.ebeaninternal.api;

import com.avaje.ebean.bean.BeanCollection;
import com.avaje.ebean.bean.EntityBeanIntercept;
import com.avaje.ebean.bean.ObjectGraphNode;
import com.avaje.ebean.bean.PersistenceContext;
import com.avaje.ebeaninternal.server.core.OrmQueryRequest;

public interface LoadContext {
  int getSecondaryQueriesMinBatchSize(OrmQueryRequest<?> paramOrmQueryRequest, int paramInt);
  
  void executeSecondaryQueries(OrmQueryRequest<?> paramOrmQueryRequest, int paramInt);
  
  void registerSecondaryQueries(SpiQuery<?> paramSpiQuery);
  
  ObjectGraphNode getObjectGraphNode(String paramString);
  
  PersistenceContext getPersistenceContext();
  
  void setPersistenceContext(PersistenceContext paramPersistenceContext);
  
  void register(String paramString, EntityBeanIntercept paramEntityBeanIntercept);
  
  void register(String paramString, BeanCollection<?> paramBeanCollection);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\api\LoadContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */