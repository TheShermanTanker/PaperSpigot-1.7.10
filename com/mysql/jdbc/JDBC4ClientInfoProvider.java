package com.mysql.jdbc;

import java.sql.Connection;
import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.util.Properties;

public interface JDBC4ClientInfoProvider {
  void initialize(Connection paramConnection, Properties paramProperties) throws SQLException;
  
  void destroy() throws SQLException;
  
  Properties getClientInfo(Connection paramConnection) throws SQLException;
  
  String getClientInfo(Connection paramConnection, String paramString) throws SQLException;
  
  void setClientInfo(Connection paramConnection, Properties paramProperties) throws SQLClientInfoException;
  
  void setClientInfo(Connection paramConnection, String paramString1, String paramString2) throws SQLClientInfoException;
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\JDBC4ClientInfoProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */