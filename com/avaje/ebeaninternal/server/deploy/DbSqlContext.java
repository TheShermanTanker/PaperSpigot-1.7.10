package com.avaje.ebeaninternal.server.deploy;

public interface DbSqlContext {
  void addJoin(String paramString1, String paramString2, TableJoinColumn[] paramArrayOfTableJoinColumn, String paramString3, String paramString4);
  
  void pushSecondaryTableAlias(String paramString);
  
  void pushTableAlias(String paramString);
  
  void popTableAlias();
  
  void addEncryptedProp(BeanProperty paramBeanProperty);
  
  BeanProperty[] getEncryptedProps();
  
  DbSqlContext append(char paramChar);
  
  DbSqlContext append(String paramString);
  
  String peekTableAlias();
  
  void appendRawColumn(String paramString);
  
  void appendColumn(String paramString1, String paramString2);
  
  void appendColumn(String paramString);
  
  void appendFormulaSelect(String paramString);
  
  void appendFormulaJoin(String paramString, boolean paramBoolean);
  
  int length();
  
  String getContent();
  
  String peekJoin();
  
  void pushJoin(String paramString);
  
  void popJoin();
  
  String getTableAlias(String paramString);
  
  String getTableAliasManyWhere(String paramString);
  
  String getRelativePrefix(String paramString);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\deploy\DbSqlContext.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */