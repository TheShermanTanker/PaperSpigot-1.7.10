package com.avaje.ebean;

import java.sql.CallableStatement;
import java.sql.SQLException;

public interface CallableSql {
  String getLabel();
  
  CallableSql setLabel(String paramString);
  
  int getTimeout();
  
  String getSql();
  
  CallableSql setTimeout(int paramInt);
  
  CallableSql setSql(String paramString);
  
  CallableSql bind(int paramInt, Object paramObject);
  
  CallableSql setParameter(int paramInt, Object paramObject);
  
  CallableSql registerOut(int paramInt1, int paramInt2);
  
  Object getObject(int paramInt);
  
  boolean executeOverride(CallableStatement paramCallableStatement) throws SQLException;
  
  CallableSql addModification(String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\CallableSql.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */