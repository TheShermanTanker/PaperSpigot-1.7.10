package org.apache.logging.log4j.core.appender.db.nosql;

public interface NoSQLObject<W> {
  void set(String paramString, Object paramObject);
  
  void set(String paramString, NoSQLObject<W> paramNoSQLObject);
  
  void set(String paramString, Object[] paramArrayOfObject);
  
  void set(String paramString, NoSQLObject<W>[] paramArrayOfNoSQLObject);
  
  W unwrap();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\db\nosql\NoSQLObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */