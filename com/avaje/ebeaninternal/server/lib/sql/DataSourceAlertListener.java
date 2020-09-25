package com.avaje.ebeaninternal.server.lib.sql;

public interface DataSourceAlertListener {
  void dataSourceDown(String paramString);
  
  void dataSourceUp(String paramString);
  
  void warning(String paramString1, String paramString2);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\lib\sql\DataSourceAlertListener.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */