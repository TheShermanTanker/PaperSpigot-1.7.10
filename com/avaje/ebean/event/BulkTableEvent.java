package com.avaje.ebean.event;

public interface BulkTableEvent {
  String getTableName();
  
  boolean isInsert();
  
  boolean isUpdate();
  
  boolean isDelete();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\event\BulkTableEvent.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */