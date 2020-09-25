/*     */ package com.mysql.jdbc;
/*     */ 
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.sql.NClob;
/*     */ import java.sql.RowId;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.SQLXML;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JDBC4ResultSet
/*     */   extends ResultSetImpl
/*     */ {
/*     */   public JDBC4ResultSet(long updateCount, long updateID, MySQLConnection conn, StatementImpl creatorStmt) {
/*  48 */     super(updateCount, updateID, conn, creatorStmt);
/*     */   }
/*     */ 
/*     */   
/*     */   public JDBC4ResultSet(String catalog, Field[] fields, RowData tuples, MySQLConnection conn, StatementImpl creatorStmt) throws SQLException {
/*  53 */     super(catalog, fields, tuples, conn, creatorStmt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Reader getNCharacterStream(int columnIndex) throws SQLException {
/*  72 */     checkColumnBounds(columnIndex);
/*     */     
/*  74 */     String fieldEncoding = this.fields[columnIndex - 1].getCharacterSet();
/*  75 */     if (fieldEncoding == null || !fieldEncoding.equals("UTF-8")) {
/*  76 */       throw new SQLException("Can not call getNCharacterStream() when field's charset isn't UTF-8");
/*     */     }
/*     */     
/*  79 */     return getCharacterStream(columnIndex);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Reader getNCharacterStream(String columnName) throws SQLException {
/*  98 */     return getNCharacterStream(findColumn(columnName));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NClob getNClob(int columnIndex) throws SQLException {
/* 113 */     checkColumnBounds(columnIndex);
/*     */     
/* 115 */     String fieldEncoding = this.fields[columnIndex - 1].getCharacterSet();
/* 116 */     if (fieldEncoding == null || !fieldEncoding.equals("UTF-8")) {
/* 117 */       throw new SQLException("Can not call getNClob() when field's charset isn't UTF-8");
/*     */     }
/*     */     
/* 120 */     if (!this.isBinaryEncoded) {
/* 121 */       String asString = getStringForNClob(columnIndex);
/*     */       
/* 123 */       if (asString == null) {
/* 124 */         return null;
/*     */       }
/*     */       
/* 127 */       return new JDBC4NClob(asString, getExceptionInterceptor());
/*     */     } 
/*     */     
/* 130 */     return getNativeNClob(columnIndex);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NClob getNClob(String columnName) throws SQLException {
/* 145 */     return getNClob(findColumn(columnName));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected NClob getNativeNClob(int columnIndex) throws SQLException {
/* 161 */     String stringVal = getStringForNClob(columnIndex);
/*     */     
/* 163 */     if (stringVal == null) {
/* 164 */       return null;
/*     */     }
/*     */     
/* 167 */     return getNClobFromString(stringVal, columnIndex);
/*     */   }
/*     */   
/*     */   private String getStringForNClob(int columnIndex) throws SQLException {
/* 171 */     String asString = null;
/*     */     
/* 173 */     String forcedEncoding = "UTF-8";
/*     */     
/*     */     try {
/* 176 */       byte[] asBytes = null;
/*     */       
/* 178 */       if (!this.isBinaryEncoded) {
/* 179 */         asBytes = getBytes(columnIndex);
/*     */       } else {
/* 181 */         asBytes = getNativeBytes(columnIndex, true);
/*     */       } 
/*     */       
/* 184 */       if (asBytes != null) {
/* 185 */         asString = new String(asBytes, forcedEncoding);
/*     */       }
/* 187 */     } catch (UnsupportedEncodingException uee) {
/* 188 */       throw SQLError.createSQLException("Unsupported character encoding " + forcedEncoding, "S1009", getExceptionInterceptor());
/*     */     } 
/*     */ 
/*     */     
/* 192 */     return asString;
/*     */   }
/*     */ 
/*     */   
/*     */   private final NClob getNClobFromString(String stringVal, int columnIndex) throws SQLException {
/* 197 */     return new JDBC4NClob(stringVal, getExceptionInterceptor());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNString(int columnIndex) throws SQLException {
/* 214 */     checkColumnBounds(columnIndex);
/*     */     
/* 216 */     String fieldEncoding = this.fields[columnIndex - 1].getCharacterSet();
/* 217 */     if (fieldEncoding == null || !fieldEncoding.equals("UTF-8")) {
/* 218 */       throw new SQLException("Can not call getNString() when field's charset isn't UTF-8");
/*     */     }
/*     */     
/* 221 */     return getString(columnIndex);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getNString(String columnName) throws SQLException {
/* 239 */     return getNString(findColumn(columnName));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateNCharacterStream(int columnIndex, Reader x, int length) throws SQLException {
/* 263 */     throw new NotUpdatable();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateNCharacterStream(String columnName, Reader reader, int length) throws SQLException {
/* 285 */     updateNCharacterStream(findColumn(columnName), reader, length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateNClob(String columnName, NClob nClob) throws SQLException {
/* 292 */     updateNClob(findColumn(columnName), nClob);
/*     */   }
/*     */   
/*     */   public void updateRowId(int columnIndex, RowId x) throws SQLException {
/* 296 */     throw new NotUpdatable();
/*     */   }
/*     */   
/*     */   public void updateRowId(String columnName, RowId x) throws SQLException {
/* 300 */     updateRowId(findColumn(columnName), x);
/*     */   }
/*     */   
/*     */   public int getHoldability() throws SQLException {
/* 304 */     throw SQLError.notImplemented();
/*     */   }
/*     */   
/*     */   public RowId getRowId(int columnIndex) throws SQLException {
/* 308 */     throw SQLError.notImplemented();
/*     */   }
/*     */   
/*     */   public RowId getRowId(String columnLabel) throws SQLException {
/* 312 */     return getRowId(findColumn(columnLabel));
/*     */   }
/*     */   
/*     */   public SQLXML getSQLXML(int columnIndex) throws SQLException {
/* 316 */     checkColumnBounds(columnIndex);
/*     */     
/* 318 */     return new JDBC4MysqlSQLXML(this, columnIndex, getExceptionInterceptor());
/*     */   }
/*     */   
/*     */   public SQLXML getSQLXML(String columnLabel) throws SQLException {
/* 322 */     return getSQLXML(findColumn(columnLabel));
/*     */   }
/*     */   
/*     */   public synchronized boolean isClosed() throws SQLException {
/* 326 */     return this.isClosed;
/*     */   }
/*     */   
/*     */   public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {
/* 330 */     throw new NotUpdatable();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {
/* 335 */     updateAsciiStream(findColumn(columnLabel), x);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {
/* 340 */     throw new NotUpdatable();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {
/* 345 */     updateAsciiStream(findColumn(columnLabel), x, length);
/*     */   }
/*     */   
/*     */   public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {
/* 349 */     throw new NotUpdatable();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {
/* 354 */     updateBinaryStream(findColumn(columnLabel), x);
/*     */   }
/*     */   
/*     */   public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {
/* 358 */     throw new NotUpdatable();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {
/* 363 */     updateBinaryStream(findColumn(columnLabel), x, length);
/*     */   }
/*     */   
/*     */   public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException {
/* 367 */     throw new NotUpdatable();
/*     */   }
/*     */   
/*     */   public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException {
/* 371 */     updateBlob(findColumn(columnLabel), inputStream);
/*     */   }
/*     */   
/*     */   public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException {
/* 375 */     throw new NotUpdatable();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException {
/* 380 */     updateBlob(findColumn(columnLabel), inputStream, length);
/*     */   }
/*     */   
/*     */   public void updateCharacterStream(int columnIndex, Reader x) throws SQLException {
/* 384 */     throw new NotUpdatable();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException {
/* 389 */     updateCharacterStream(findColumn(columnLabel), reader);
/*     */   }
/*     */   
/*     */   public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
/* 393 */     throw new NotUpdatable();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
/* 398 */     updateCharacterStream(findColumn(columnLabel), reader, length);
/*     */   }
/*     */   
/*     */   public void updateClob(int columnIndex, Reader reader) throws SQLException {
/* 402 */     throw new NotUpdatable();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateClob(String columnLabel, Reader reader) throws SQLException {
/* 407 */     updateClob(findColumn(columnLabel), reader);
/*     */   }
/*     */   
/*     */   public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {
/* 411 */     throw new NotUpdatable();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {
/* 416 */     updateClob(findColumn(columnLabel), reader, length);
/*     */   }
/*     */   
/*     */   public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {
/* 420 */     throw new NotUpdatable();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {
/* 425 */     updateNCharacterStream(findColumn(columnLabel), reader);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
/* 430 */     throw new NotUpdatable();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
/* 435 */     updateNCharacterStream(findColumn(columnLabel), reader, length);
/*     */   }
/*     */   
/*     */   public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
/* 439 */     throw new NotUpdatable();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateNClob(int columnIndex, Reader reader) throws SQLException {
/* 444 */     throw new NotUpdatable();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateNClob(String columnLabel, Reader reader) throws SQLException {
/* 449 */     updateNClob(findColumn(columnLabel), reader);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {
/* 454 */     throw new NotUpdatable();
/*     */   }
/*     */   
/*     */   public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {
/* 458 */     updateNClob(findColumn(columnLabel), reader, length);
/*     */   }
/*     */   
/*     */   public void updateNString(int columnIndex, String nString) throws SQLException {
/* 462 */     throw new NotUpdatable();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateNString(String columnLabel, String nString) throws SQLException {
/* 467 */     updateNString(findColumn(columnLabel), nString);
/*     */   }
/*     */   
/*     */   public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {
/* 471 */     throw new NotUpdatable();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {
/* 476 */     updateSQLXML(findColumn(columnLabel), xmlObject);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isWrapperFor(Class<?> iface) throws SQLException {
/* 496 */     checkClosed();
/*     */ 
/*     */ 
/*     */     
/* 500 */     return iface.isInstance(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T unwrap(Class<T> iface) throws SQLException {
/*     */     try {
/* 521 */       return iface.cast(this);
/* 522 */     } catch (ClassCastException cce) {
/* 523 */       throw SQLError.createSQLException("Unable to unwrap to " + iface.toString(), "S1009", getExceptionInterceptor());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\JDBC4ResultSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */