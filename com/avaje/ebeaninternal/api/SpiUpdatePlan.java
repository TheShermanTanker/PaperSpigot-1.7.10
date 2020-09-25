package com.avaje.ebeaninternal.api;

import com.avaje.ebeaninternal.server.core.ConcurrencyMode;
import com.avaje.ebeaninternal.server.persist.dml.DmlHandler;
import com.avaje.ebeaninternal.server.persist.dmlbind.Bindable;
import java.sql.SQLException;
import java.util.Set;

public interface SpiUpdatePlan {
  boolean isEmptySetClause();
  
  void bindSet(DmlHandler paramDmlHandler, Object paramObject) throws SQLException;
  
  long getTimeCreated();
  
  Long getTimeLastUsed();
  
  Integer getKey();
  
  ConcurrencyMode getMode();
  
  String getSql();
  
  Bindable getSet();
  
  Set<String> getProperties();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\api\SpiUpdatePlan.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */