package com.avaje.ebean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public interface SqlRow extends Serializable, Map<String, Object> {
  Iterator<String> keys();
  
  Object remove(Object paramObject);
  
  Object get(Object paramObject);
  
  Object put(String paramString, Object paramObject);
  
  Object set(String paramString, Object paramObject);
  
  Boolean getBoolean(String paramString);
  
  UUID getUUID(String paramString);
  
  Integer getInteger(String paramString);
  
  BigDecimal getBigDecimal(String paramString);
  
  Long getLong(String paramString);
  
  Double getDouble(String paramString);
  
  Float getFloat(String paramString);
  
  String getString(String paramString);
  
  Date getUtilDate(String paramString);
  
  Date getDate(String paramString);
  
  Timestamp getTimestamp(String paramString);
  
  String toString();
  
  void clear();
  
  boolean containsKey(Object paramObject);
  
  boolean containsValue(Object paramObject);
  
  Set<Map.Entry<String, Object>> entrySet();
  
  boolean isEmpty();
  
  Set<String> keySet();
  
  void putAll(Map<? extends String, ? extends Object> paramMap);
  
  int size();
  
  Collection<Object> values();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\SqlRow.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */