package org.apache.logging.log4j.core.appender.db.nosql;

public interface NoSQLProvider<C extends NoSQLConnection<?, ? extends NoSQLObject<?>>> {
  C getConnection();
  
  String toString();
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\db\nosql\NoSQLProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */