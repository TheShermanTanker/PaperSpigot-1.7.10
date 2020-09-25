package com.avaje.ebeaninternal.server.transaction;

public interface TransactionLogWriter {
  void log(TransactionLogBuffer paramTransactionLogBuffer);
  
  void shutdown();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\transaction\TransactionLogWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */