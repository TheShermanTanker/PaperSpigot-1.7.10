package com.avaje.ebeaninternal.server.deploy.generatedproperty;

import com.avaje.ebeaninternal.server.deploy.BeanProperty;

public interface GeneratedProperty {
  Object getInsertValue(BeanProperty paramBeanProperty, Object paramObject);
  
  Object getUpdateValue(BeanProperty paramBeanProperty, Object paramObject);
  
  boolean includeInUpdate();
  
  boolean includeInInsert();
  
  boolean isDDLNotNullable();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\generatedproperty\GeneratedProperty.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */