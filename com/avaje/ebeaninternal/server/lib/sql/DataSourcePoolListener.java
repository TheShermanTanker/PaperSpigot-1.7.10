package com.avaje.ebeaninternal.server.lib.sql;

import java.sql.Connection;

public interface DataSourcePoolListener {
  void onAfterBorrowConnection(Connection paramConnection);
  
  void onBeforeReturnConnection(Connection paramConnection);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\lib\sql\DataSourcePoolListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */