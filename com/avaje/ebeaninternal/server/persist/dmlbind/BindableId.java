package com.avaje.ebeaninternal.server.persist.dmlbind;

import com.avaje.ebeaninternal.server.core.PersistRequestBean;

public interface BindableId extends Bindable {
  boolean isEmpty();
  
  boolean isConcatenated();
  
  String getIdentityColumn();
  
  boolean deriveConcatenatedId(PersistRequestBean<?> paramPersistRequestBean);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\persist\dmlbind\BindableId.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */