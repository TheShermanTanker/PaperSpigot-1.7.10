package com.avaje.ebeaninternal.server.persist;

import java.sql.SQLException;

public interface BatchPostExecute {
  void checkRowCount(int paramInt) throws SQLException;
  
  void setGeneratedKey(Object paramObject);
  
  void postExecute() throws SQLException;
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\persist\BatchPostExecute.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */