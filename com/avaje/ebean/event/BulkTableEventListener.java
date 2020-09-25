package com.avaje.ebean.event;

import java.util.Set;

public interface BulkTableEventListener {
  Set<String> registeredTables();
  
  void process(BulkTableEvent paramBulkTableEvent);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\event\BulkTableEventListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */