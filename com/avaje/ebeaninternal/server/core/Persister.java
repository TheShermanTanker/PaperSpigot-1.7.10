package com.avaje.ebeaninternal.server.core;

import com.avaje.ebean.CallableSql;
import com.avaje.ebean.SqlUpdate;
import com.avaje.ebean.Transaction;
import com.avaje.ebean.Update;
import java.util.Collection;
import java.util.Set;

public interface Persister {
  void forceUpdate(Object paramObject, Set<String> paramSet, Transaction paramTransaction, boolean paramBoolean1, boolean paramBoolean2);
  
  void forceInsert(Object paramObject, Transaction paramTransaction);
  
  void save(Object paramObject, Transaction paramTransaction);
  
  void saveManyToManyAssociations(Object paramObject, String paramString, Transaction paramTransaction);
  
  void saveAssociation(Object paramObject, String paramString, Transaction paramTransaction);
  
  int deleteManyToManyAssociations(Object paramObject, String paramString, Transaction paramTransaction);
  
  int delete(Class<?> paramClass, Object paramObject, Transaction paramTransaction);
  
  void delete(Object paramObject, Transaction paramTransaction);
  
  void deleteMany(Class<?> paramClass, Collection<?> paramCollection, Transaction paramTransaction);
  
  int executeOrmUpdate(Update<?> paramUpdate, Transaction paramTransaction);
  
  int executeSqlUpdate(SqlUpdate paramSqlUpdate, Transaction paramTransaction);
  
  int executeCallable(CallableSql paramCallableSql, Transaction paramTransaction);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\core\Persister.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */