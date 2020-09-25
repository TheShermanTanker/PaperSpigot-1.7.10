package com.avaje.ebean;

public interface AdminLogging {
  void setLogLevel(LogLevel paramLogLevel);
  
  LogLevel getLogLevel();
  
  boolean isDebugGeneratedSql();
  
  void setDebugGeneratedSql(boolean paramBoolean);
  
  boolean isDebugLazyLoad();
  
  void setDebugLazyLoad(boolean paramBoolean);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\AdminLogging.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */