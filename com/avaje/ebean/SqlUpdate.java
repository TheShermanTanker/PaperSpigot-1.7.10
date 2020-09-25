package com.avaje.ebean;

public interface SqlUpdate {
  int execute();
  
  boolean isAutoTableMod();
  
  SqlUpdate setAutoTableMod(boolean paramBoolean);
  
  String getLabel();
  
  SqlUpdate setLabel(String paramString);
  
  String getSql();
  
  int getTimeout();
  
  SqlUpdate setTimeout(int paramInt);
  
  SqlUpdate setParameter(int paramInt, Object paramObject);
  
  SqlUpdate setNull(int paramInt1, int paramInt2);
  
  SqlUpdate setNullParameter(int paramInt1, int paramInt2);
  
  SqlUpdate setParameter(String paramString, Object paramObject);
  
  SqlUpdate setNull(String paramString, int paramInt);
  
  SqlUpdate setNullParameter(String paramString, int paramInt);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\SqlUpdate.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */