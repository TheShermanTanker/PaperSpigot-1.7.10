package com.mysql.jdbc;

import java.sql.SQLException;
import java.util.Properties;

public interface ConnectionPropertiesTransform {
  Properties transformProperties(Properties paramProperties) throws SQLException;
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\ConnectionPropertiesTransform.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */