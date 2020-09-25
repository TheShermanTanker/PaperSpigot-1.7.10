package com.avaje.ebeaninternal.server.lib.sql;

public interface DataSourceNotify {
  void notifyDataSourceUp(String paramString);
  
  void notifyDataSourceDown(String paramString);
  
  void notifyWarning(String paramString1, String paramString2);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\lib\sql\DataSourceNotify.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */