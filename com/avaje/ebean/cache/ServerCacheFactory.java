package com.avaje.ebean.cache;

import com.avaje.ebean.EbeanServer;

public interface ServerCacheFactory {
  void init(EbeanServer paramEbeanServer);
  
  ServerCache createCache(String paramString, ServerCacheOptions paramServerCacheOptions);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\cache\ServerCacheFactory.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */