package com.avaje.ebeaninternal.server.persist.dmlbind;

import com.avaje.ebeaninternal.api.DerivedRelationshipData;
import com.avaje.ebeaninternal.server.core.PersistRequestBean;
import com.avaje.ebeaninternal.server.deploy.BeanProperty;
import java.sql.SQLException;

public interface BindableRequest {
  void setIdValue(Object paramObject);
  
  Object bind(Object paramObject, BeanProperty paramBeanProperty, String paramString, boolean paramBoolean) throws SQLException;
  
  Object bind(String paramString, Object paramObject, int paramInt) throws SQLException;
  
  Object bindNoLog(Object paramObject, int paramInt, String paramString) throws SQLException;
  
  Object bindNoLog(Object paramObject, BeanProperty paramBeanProperty, String paramString, boolean paramBoolean) throws SQLException;
  
  boolean isIncluded(BeanProperty paramBeanProperty);
  
  boolean isIncludedWhere(BeanProperty paramBeanProperty);
  
  void registerUpdateGenValue(BeanProperty paramBeanProperty, Object paramObject1, Object paramObject2);
  
  void registerAdditionalProperty(String paramString);
  
  PersistRequestBean<?> getPersistRequest();
  
  void registerDerivedRelationship(DerivedRelationshipData paramDerivedRelationshipData);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\persist\dmlbind\BindableRequest.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */