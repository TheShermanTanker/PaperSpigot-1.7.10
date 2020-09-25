package com.avaje.ebeaninternal.server.persist;

import com.avaje.ebeaninternal.api.SpiTransaction;
import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import com.avaje.ebeaninternal.server.core.PersistRequestCallableSql;
import com.avaje.ebeaninternal.server.core.PersistRequestOrmUpdate;
import com.avaje.ebeaninternal.server.core.PersistRequestUpdateSql;

public interface PersistExecute {
  BatchControl createBatchControl(SpiTransaction paramSpiTransaction);
  
  <T> void executeInsertBean(PersistRequestBean<T> paramPersistRequestBean);
  
  <T> void executeUpdateBean(PersistRequestBean<T> paramPersistRequestBean);
  
  <T> void executeDeleteBean(PersistRequestBean<T> paramPersistRequestBean);
  
  int executeOrmUpdate(PersistRequestOrmUpdate paramPersistRequestOrmUpdate);
  
  int executeSqlCallable(PersistRequestCallableSql paramPersistRequestCallableSql);
  
  int executeSqlUpdate(PersistRequestUpdateSql paramPersistRequestUpdateSql);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\persist\PersistExecute.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */