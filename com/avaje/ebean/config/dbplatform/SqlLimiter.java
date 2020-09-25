package com.avaje.ebean.config.dbplatform;

public interface SqlLimiter {
  public static final char NEW_LINE = '\n';
  
  public static final char CARRIAGE_RETURN = '\r';
  
  SqlLimitResponse limit(SqlLimitRequest paramSqlLimitRequest);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\config\dbplatform\SqlLimiter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */