package com.avaje.ebean;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SqlQuery extends Serializable {
  void cancel();
  
  List<SqlRow> findList();
  
  Set<SqlRow> findSet();
  
  Map<?, SqlRow> findMap();
  
  SqlRow findUnique();
  
  SqlFutureList findFutureList();
  
  SqlQuery setParameter(String paramString, Object paramObject);
  
  SqlQuery setParameter(int paramInt, Object paramObject);
  
  SqlQuery setListener(SqlQueryListener paramSqlQueryListener);
  
  SqlQuery setFirstRow(int paramInt);
  
  SqlQuery setMaxRows(int paramInt);
  
  SqlQuery setBackgroundFetchAfter(int paramInt);
  
  SqlQuery setMapKey(String paramString);
  
  SqlQuery setTimeout(int paramInt);
  
  SqlQuery setBufferFetchSizeHint(int paramInt);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebean\SqlQuery.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */