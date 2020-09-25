package com.avaje.ebean.config;

public interface ExternalTransactionManager {
  void setTransactionManager(Object paramObject);
  
  Object getCurrentTransaction();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\ExternalTransactionManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */