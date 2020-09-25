package com.avaje.ebean.config.dbplatform;

import com.avaje.ebeaninternal.api.SpiQuery;

public interface SqlLimitRequest {
  boolean isDistinct();
  
  int getFirstRow();
  
  int getMaxRows();
  
  String getDbSql();
  
  String getDbOrderBy();
  
  SpiQuery<?> getOrmQuery();
  
  DatabasePlatform getDbPlatform();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\dbplatform\SqlLimitRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */