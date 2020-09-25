package com.avaje.ebeaninternal.api;

import com.avaje.ebean.bean.ObjectGraphNode;
import com.avaje.ebean.bean.PersistenceContext;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocMany;

public interface LoadManyContext extends LoadSecondaryQuery {
  void configureQuery(SpiQuery<?> paramSpiQuery);
  
  String getFullPath();
  
  ObjectGraphNode getObjectGraphNode();
  
  PersistenceContext getPersistenceContext();
  
  int getBatchSize();
  
  BeanDescriptor<?> getBeanDescriptor();
  
  BeanPropertyAssocMany<?> getBeanProperty();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\api\LoadManyContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */