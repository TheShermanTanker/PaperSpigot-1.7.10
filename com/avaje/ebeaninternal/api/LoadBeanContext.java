package com.avaje.ebeaninternal.api;

import com.avaje.ebean.bean.PersistenceContext;
import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;

public interface LoadBeanContext extends LoadSecondaryQuery {
  void configureQuery(SpiQuery<?> paramSpiQuery, String paramString);
  
  String getFullPath();
  
  PersistenceContext getPersistenceContext();
  
  BeanDescriptor<?> getBeanDescriptor();
  
  int getBatchSize();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\api\LoadBeanContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */