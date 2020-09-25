package com.avaje.ebean.event;

import com.avaje.ebean.Transaction;

public abstract class TransactionEventListenerAdapter implements TransactionEventListener {
  public void postTransactionCommit(Transaction tx) {}
  
  public void postTransactionRollback(Transaction tx, Throwable cause) {}
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\event\TransactionEventListenerAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */