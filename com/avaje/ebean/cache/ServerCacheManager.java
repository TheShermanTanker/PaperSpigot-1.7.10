package com.avaje.ebean.cache;

import com.avaje.ebean.EbeanServer;

public interface ServerCacheManager {
  void init(EbeanServer paramEbeanServer);
  
  void setCaching(Class<?> paramClass, boolean paramBoolean);
  
  boolean isBeanCaching(Class<?> paramClass);
  
  ServerCache getNaturalKeyCache(Class<?> paramClass);
  
  ServerCache getBeanCache(Class<?> paramClass);
  
  ServerCache getCollectionIdsCache(Class<?> paramClass, String paramString);
  
  ServerCache getQueryCache(Class<?> paramClass);
  
  void clear(Class<?> paramClass);
  
  void clearAll();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\cache\ServerCacheManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */