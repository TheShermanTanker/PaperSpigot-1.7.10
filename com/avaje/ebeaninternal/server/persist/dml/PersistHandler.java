package com.avaje.ebeaninternal.server.persist.dml;

import java.sql.SQLException;

public interface PersistHandler {
  String getBindLog();
  
  void bind() throws SQLException;
  
  void addBatch() throws SQLException;
  
  void execute() throws SQLException;
  
  void close() throws SQLException;
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\persist\dml\PersistHandler.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */