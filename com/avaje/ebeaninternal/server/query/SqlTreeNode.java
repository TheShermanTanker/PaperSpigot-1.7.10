package com.avaje.ebeaninternal.server.query;

import com.avaje.ebeaninternal.server.deploy.DbReadContext;
import com.avaje.ebeaninternal.server.deploy.DbSqlContext;
import java.sql.SQLException;
import java.util.List;

public interface SqlTreeNode {
  public static final char NEW_LINE = '\n';
  
  public static final String PERIOD = ".";
  
  public static final String COMMA = ", ";
  
  public static final int NORMAL = 0;
  
  public static final int SHARED = 1;
  
  public static final int READONLY = 2;
  
  void buildSelectExpressionChain(List<String> paramList);
  
  void appendSelect(DbSqlContext paramDbSqlContext, boolean paramBoolean);
  
  void appendFrom(DbSqlContext paramDbSqlContext, boolean paramBoolean);
  
  void appendWhere(DbSqlContext paramDbSqlContext);
  
  void load(DbReadContext paramDbReadContext, Object paramObject) throws SQLException;
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\query\SqlTreeNode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */