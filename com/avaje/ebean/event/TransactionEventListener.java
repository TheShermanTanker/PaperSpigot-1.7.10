package com.avaje.ebean.event;

import com.avaje.ebean.Transaction;

public interface TransactionEventListener {
  void postTransactionCommit(Transaction paramTransaction);
  
  void postTransactionRollback(Transaction paramTransaction, Throwable paramThrowable);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\event\TransactionEventListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */