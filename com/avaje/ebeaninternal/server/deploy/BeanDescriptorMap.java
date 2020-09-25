package com.avaje.ebeaninternal.server.deploy;

import com.avaje.ebean.cache.ServerCacheManager;
import com.avaje.ebean.config.EncryptKey;
import com.avaje.ebeaninternal.server.deploy.id.IdBinder;

public interface BeanDescriptorMap {
  String getServerName();
  
  ServerCacheManager getCacheManager();
  
  <T> BeanDescriptor<T> getBeanDescriptor(Class<T> paramClass);
  
  EncryptKey getEncryptKey(String paramString1, String paramString2);
  
  IdBinder createIdBinder(BeanProperty[] paramArrayOfBeanProperty);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\BeanDescriptorMap.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */