package com.avaje.ebean;

import java.sql.Connection;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceException;
import javax.persistence.RollbackException;

public interface Transaction {
  public static final int READ_COMMITTED = 2;
  
  public static final int READ_UNCOMMITTED = 1;
  
  public static final int REPEATABLE_READ = 4;
  
  public static final int SERIALIZABLE = 8;
  
  boolean isReadOnly();
  
  void setReadOnly(boolean paramBoolean);
  
  void log(String paramString);
  
  void setLogLevel(LogLevel paramLogLevel);
  
  LogLevel getLogLevel();
  
  void setLoggingOn(boolean paramBoolean);
  
  void commit() throws RollbackException;
  
  void rollback() throws PersistenceException;
  
  void rollback(Throwable paramThrowable) throws PersistenceException;
  
  void end() throws PersistenceException;
  
  boolean isActive();
  
  void setPersistCascade(boolean paramBoolean);
  
  void setBatchMode(boolean paramBoolean);
  
  void setBatchSize(int paramInt);
  
  void setBatchGetGeneratedKeys(boolean paramBoolean);
  
  void setBatchFlushOnMixed(boolean paramBoolean);
  
  void setBatchFlushOnQuery(boolean paramBoolean);
  
  boolean isBatchFlushOnQuery();
  
  void flushBatch() throws PersistenceException, OptimisticLockException;
  
  void batchFlush() throws PersistenceException, OptimisticLockException;
  
  Connection getConnection();
  
  void addModification(String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3);
  
  void putUserObject(String paramString, Object paramObject);
  
  Object getUserObject(String paramString);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\Transaction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */