package com.avaje.ebeaninternal.api;

import com.avaje.ebean.Transaction;
import com.avaje.ebean.bean.PersistenceContext;
import com.avaje.ebeaninternal.server.persist.BatchControl;
import com.avaje.ebeaninternal.server.transaction.TransactionLogBuffer;
import java.sql.Connection;
import java.util.List;

public interface SpiTransaction extends Transaction {
  boolean isLogSql();
  
  boolean isLogSummary();
  
  void logInternal(String paramString);
  
  TransactionLogBuffer getLogBuffer();
  
  void registerDerivedRelationship(DerivedRelationshipData paramDerivedRelationshipData);
  
  List<DerivedRelationshipData> getDerivedRelationship(Object paramObject);
  
  void registerDeleteBean(Integer paramInteger);
  
  void unregisterDeleteBean(Integer paramInteger);
  
  boolean isRegisteredDeleteBean(Integer paramInteger);
  
  void unregisterBean(Object paramObject);
  
  boolean isRegisteredBean(Object paramObject);
  
  String getId();
  
  int getBatchSize();
  
  int depth(int paramInt);
  
  boolean isExplicit();
  
  TransactionEvent getEvent();
  
  boolean isPersistCascade();
  
  boolean isBatchThisRequest();
  
  BatchControl getBatchControl();
  
  void setBatchControl(BatchControl paramBatchControl);
  
  PersistenceContext getPersistenceContext();
  
  void setPersistenceContext(PersistenceContext paramPersistenceContext);
  
  Connection getInternalConnection();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\api\SpiTransaction.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */