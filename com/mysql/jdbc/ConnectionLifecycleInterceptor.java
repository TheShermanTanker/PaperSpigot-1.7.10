package com.mysql.jdbc;

import java.sql.SQLException;
import java.sql.Savepoint;

public interface ConnectionLifecycleInterceptor extends Extension {
  void close() throws SQLException;
  
  boolean commit() throws SQLException;
  
  boolean rollback() throws SQLException;
  
  boolean rollback(Savepoint paramSavepoint) throws SQLException;
  
  boolean setAutoCommit(boolean paramBoolean) throws SQLException;
  
  boolean setCatalog(String paramString) throws SQLException;
  
  boolean transactionBegun() throws SQLException;
  
  boolean transactionCompleted() throws SQLException;
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\ConnectionLifecycleInterceptor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */