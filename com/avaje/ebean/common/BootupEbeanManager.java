package com.avaje.ebean.common;

import com.avaje.ebean.EbeanServer;
import com.avaje.ebean.config.ServerConfig;

public interface BootupEbeanManager {
  EbeanServer createServer(ServerConfig paramServerConfig);
  
  EbeanServer createServer(String paramString);
  
  void shutdown();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\common\BootupEbeanManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */