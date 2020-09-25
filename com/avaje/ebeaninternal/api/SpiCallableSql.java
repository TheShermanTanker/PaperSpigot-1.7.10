package com.avaje.ebeaninternal.api;

import com.avaje.ebean.CallableSql;

public interface SpiCallableSql extends CallableSql {
  BindParams getBindParams();
  
  TransactionEventTable getTransactionEventTable();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\api\SpiCallableSql.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */