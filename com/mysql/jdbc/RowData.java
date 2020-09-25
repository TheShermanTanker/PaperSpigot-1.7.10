package com.mysql.jdbc;

import java.sql.SQLException;

public interface RowData {
  public static final int RESULT_SET_SIZE_UNKNOWN = -1;
  
  void addRow(ResultSetRow paramResultSetRow) throws SQLException;
  
  void afterLast() throws SQLException;
  
  void beforeFirst() throws SQLException;
  
  void beforeLast() throws SQLException;
  
  void close() throws SQLException;
  
  ResultSetRow getAt(int paramInt) throws SQLException;
  
  int getCurrentRowNumber() throws SQLException;
  
  ResultSetInternalMethods getOwner();
  
  boolean hasNext() throws SQLException;
  
  boolean isAfterLast() throws SQLException;
  
  boolean isBeforeFirst() throws SQLException;
  
  boolean isDynamic() throws SQLException;
  
  boolean isEmpty() throws SQLException;
  
  boolean isFirst() throws SQLException;
  
  boolean isLast() throws SQLException;
  
  void moveRowRelative(int paramInt) throws SQLException;
  
  ResultSetRow next() throws SQLException;
  
  void removeRow(int paramInt) throws SQLException;
  
  void setCurrentRow(int paramInt) throws SQLException;
  
  void setOwner(ResultSetImpl paramResultSetImpl);
  
  int size() throws SQLException;
  
  boolean wasEmpty();
  
  void setMetadata(Field[] paramArrayOfField);
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\RowData.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */