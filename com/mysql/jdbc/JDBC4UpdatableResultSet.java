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
/*     */ public class JDBC4UpdatableResultSet
/*     */   extends UpdatableResultSet
/*     */ {
/*     */   public JDBC4UpdatableResultSet(String catalog, Field[] fields, RowData tuples, MySQLConnection conn, StatementImpl creatorStmt) throws SQLException {
/*  48 */     super(catalog, fields, tuples, conn, creatorStmt);
/*     */   }
/*     */   
/*     */   public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {
/*  52 */     throw new NotUpdatable();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {
/*  57 */     throw new NotUpdatable();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {
/*  62 */     throw new NotUpdatable();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {
/*  67 */     throw new NotUpdatable();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException {
/*  72 */     throw new NotUpdatable();
/*     */   }
/*     */   
/*     */   public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException {
/*  76 */     throw new NotUpdatable();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateCharacterStream(int columnIndex, Reader x) throws SQLException {
/*  81 */     throw new NotUpdatable();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
/*  87 */     throw new NotUpdatable();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateClob(int columnIndex, Reader reader) throws SQLException {
/*  92 */     throw new NotUpdatable();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {
/*  97 */     throw new NotUpdatable();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {
/* 102 */     throw new NotUpdatable();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
/* 107 */     updateNCharacterStream(columnIndex, x, (int)length);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateNClob(int columnIndex, Reader reader) throws SQLException {
/* 113 */     throw new NotUpdatable();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {
/* 118 */     throw new NotUpdatable();
/*     */   }
/*     */   
/*     */   public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {
/* 122 */     throw new NotUpdatable();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateRowId(int columnIndex, RowId x) throws SQLException {
/* 127 */     throw new NotUpdatable();
/*     */   }
/*     */   
/*     */   public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {
/* 131 */     updateAsciiStream(findColumn(columnLabel), x);
/*     */   }
/*     */   
/*     */   public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {
/* 135 */     updateAsciiStream(findColumn(columnLabel), x, length);
/*     */   }
/*     */   
/*     */   public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {
/* 139 */     updateBinaryStream(findColumn(columnLabel), x);
/*     */   }
/*     */   
/*     */   public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {
/* 143 */     updateBinaryStream(findColumn(columnLabel), x, length);
/*     */   }
/*     */   
/*     */   public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException {
/* 147 */     updateBlob(findColumn(columnLabel), inputStream);
/*     */   }
/*     */   
/*     */   public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException {
/* 151 */     updateBlob(findColumn(columnLabel), inputStream, length);
/*     */   }
/*     */   
/*     */   public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException {
/* 155 */     updateCharacterStream(findColumn(columnLabel), reader);
/*     */   }
/*     */   
/*     */   public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
/* 159 */     updateCharacterStream(findColumn(columnLabel), reader, length);
/*     */   }
/*     */   
/*     */   public void updateClob(String columnLabel, Reader reader) throws SQLException {
/* 163 */     updateClob(findColumn(columnLabel), reader);
/*     */   }
/*     */   
/*     */   public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {
/* 167 */     updateClob(findColumn(columnLabel), reader, length);
/*     */   }
/*     */   
/*     */   public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {
/* 171 */     updateNCharacterStream(findColumn(columnLabel), reader);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
/* 176 */     updateNCharacterStream(findColumn(columnLabel), reader, length);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateNClob(String columnLabel, Reader reader) throws SQLException {
/* 181 */     updateNClob(findColumn(columnLabel), reader);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {
/* 186 */     updateNClob(findColumn(columnLabel), reader, length);
/*     */   }
/*     */   
/*     */   public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {
/* 190 */     updateSQLXML(findColumn(columnLabel), xmlObject);
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
/*     */   public synchronized void updateNCharacterStream(int columnIndex, Reader x, int length) throws SQLException {
/* 213 */     String fieldEncoding = this.fields[columnIndex - 1].getCharacterSet();
/* 214 */     if (fieldEncoding == null || !fieldEncoding.equals("UTF-8")) {
/* 215 */       throw new SQLException("Can not call updateNCharacterStream() when field's character set isn't UTF-8");
/*     */     }
/*     */ 
/*     */     
/* 219 */     if (!this.onInsertRow) {
/* 220 */       if (!this.doingUpdates) {
/* 221 */         this.doingUpdates = true;
/* 222 */         syncUpdate();
/*     */       } 
/*     */       
/* 225 */       ((JDBC4PreparedStatement)this.updater).setNCharacterStream(columnIndex, x, length);
/*     */     } else {
/* 227 */       ((JDBC4PreparedStatement)this.inserter).setNCharacterStream(columnIndex, x, length);
/*     */       
/* 229 */       if (x == null) {
/* 230 */         this.thisRow.setColumnValue(columnIndex - 1, null);
/*     */       } else {
/* 232 */         this.thisRow.setColumnValue(columnIndex - 1, STREAM_DATA_MARKER);
/*     */       } 
/*     */     } 
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
/*     */   public synchronized void updateNCharacterStream(String columnName, Reader reader, int length) throws SQLException {
/* 256 */     updateNCharacterStream(findColumn(columnName), reader, length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
/* 264 */     String fieldEncoding = this.fields[columnIndex - 1].getCharacterSet();
/* 265 */     if (fieldEncoding == null || !fieldEncoding.equals("UTF-8")) {
/* 266 */       throw new SQLException("Can not call updateNClob() when field's character set isn't UTF-8");
/*     */     }
/*     */     
/* 269 */     if (nClob == null) {
/* 270 */       updateNull(columnIndex);
/*     */     } else {
/* 272 */       updateNCharacterStream(columnIndex, nClob.getCharacterStream(), (int)nClob.length());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateNClob(String columnName, NClob nClob) throws SQLException {
/* 282 */     updateNClob(findColumn(columnName), nClob);
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
/*     */   public synchronized void updateNString(int columnIndex, String x) throws SQLException {
/* 301 */     String fieldEncoding = this.fields[columnIndex - 1].getCharacterSet();
/* 302 */     if (fieldEncoding == null || !fieldEncoding.equals("UTF-8")) {
/* 303 */       throw new SQLException("Can not call updateNString() when field's character set isn't UTF-8");
/*     */     }
/*     */     
/* 306 */     if (!this.onInsertRow) {
/* 307 */       if (!this.doingUpdates) {
/* 308 */         this.doingUpdates = true;
/* 309 */         syncUpdate();
/*     */       } 
/*     */       
/* 312 */       ((JDBC4PreparedStatement)this.updater).setNString(columnIndex, x);
/*     */     } else {
/* 314 */       ((JDBC4PreparedStatement)this.inserter).setNString(columnIndex, x);
/*     */       
/* 316 */       if (x == null) {
/* 317 */         this.thisRow.setColumnValue(columnIndex - 1, null);
/*     */       } else {
/* 319 */         this.thisRow.setColumnValue(columnIndex - 1, StringUtils.getBytes(x, this.charConverter, fieldEncoding, this.connection.getServerCharacterEncoding(), this.connection.parserKnowsUnicode(), getExceptionInterceptor()));
/*     */       } 
/*     */     } 
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
/*     */   public synchronized void updateNString(String columnName, String x) throws SQLException {
/* 343 */     updateNString(findColumn(columnName), x);
/*     */   }
/*     */   
/*     */   public int getHoldability() throws SQLException {
/* 347 */     throw SQLError.notImplemented();
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
/* 363 */     String stringVal = getStringForNClob(columnIndex);
/*     */     
/* 365 */     if (stringVal == null) {
/* 366 */       return null;
/*     */     }
/*     */     
/* 369 */     return getNClobFromString(stringVal, columnIndex);
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
/* 388 */     String fieldEncoding = this.fields[columnIndex - 1].getCharacterSet();
/* 389 */     if (fieldEncoding == null || !fieldEncoding.equals("UTF-8")) {
/* 390 */       throw new SQLException("Can not call getNCharacterStream() when field's charset isn't UTF-8");
/*     */     }
/*     */ 
/*     */     
/* 394 */     return getCharacterStream(columnIndex);
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
/* 413 */     return getNCharacterStream(findColumn(columnName));
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
/* 428 */     String fieldEncoding = this.fields[columnIndex - 1].getCharacterSet();
/*     */     
/* 430 */     if (fieldEncoding == null || !fieldEncoding.equals("UTF-8")) {
/* 431 */       throw new SQLException("Can not call getNClob() when field's charset isn't UTF-8");
/*     */     }
/*     */ 
/*     */     
/* 435 */     if (!this.isBinaryEncoded) {
/* 436 */       String asString = getStringForNClob(columnIndex);
/*     */       
/* 438 */       if (asString == null) {
/* 439 */         return null;
/*     */       }
/*     */       
/* 442 */       return new JDBC4NClob(asString, getExceptionInterceptor());
/*     */     } 
/*     */     
/* 445 */     return getNativeNClob(columnIndex);
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
/* 460 */     return getNClob(findColumn(columnName));
/*     */   }
/*     */ 
/*     */   
/*     */   private final NClob getNClobFromString(String stringVal, int columnIndex) throws SQLException {
/* 465 */     return new JDBC4NClob(stringVal, getExceptionInterceptor());
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
/* 482 */     String fieldEncoding = this.fields[columnIndex - 1].getCharacterSet();
/*     */     
/* 484 */     if (fieldEncoding == null || !fieldEncoding.equals("UTF-8")) {
/* 485 */       throw new SQLException("Can not call getNString() when field's charset isn't UTF-8");
/*     */     }
/*     */ 
/*     */     
/* 489 */     return getString(columnIndex);
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
/* 507 */     return getNString(findColumn(columnName));
/*     */   }
/*     */   
/*     */   public RowId getRowId(int columnIndex) throws SQLException {
/* 511 */     throw SQLError.notImplemented();
/*     */   }
/*     */   
/*     */   public RowId getRowId(String columnLabel) throws SQLException {
/* 515 */     return getRowId(findColumn(columnLabel));
/*     */   }
/*     */   
/*     */   public SQLXML getSQLXML(int columnIndex) throws SQLException {
/* 519 */     return new JDBC4MysqlSQLXML(this, columnIndex, getExceptionInterceptor());
/*     */   }
/*     */   
/*     */   public SQLXML getSQLXML(String columnLabel) throws SQLException {
/* 523 */     return getSQLXML(findColumn(columnLabel));
/*     */   }
/*     */   
/*     */   private String getStringForNClob(int columnIndex) throws SQLException {
/* 527 */     String asString = null;
/*     */     
/* 529 */     String forcedEncoding = "UTF-8";
/*     */     
/*     */     try {
/* 532 */       byte[] asBytes = null;
/*     */       
/* 534 */       if (!this.isBinaryEncoded) {
/* 535 */         asBytes = getBytes(columnIndex);
/*     */       } else {
/* 537 */         asBytes = getNativeBytes(columnIndex, true);
/*     */       } 
/*     */       
/* 540 */       if (asBytes != null) {
/* 541 */         asString = new String(asBytes, forcedEncoding);
/*     */       }
/* 543 */     } catch (UnsupportedEncodingException uee) {
/* 544 */       throw SQLError.createSQLException("Unsupported character encoding " + forcedEncoding, "S1009", getExceptionInterceptor());
/*     */     } 
/*     */ 
/*     */     
/* 548 */     return asString;
/*     */   }
/*     */   
/*     */   public synchronized boolean isClosed() throws SQLException {
/* 552 */     return this.isClosed;
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
/*     */ 
/*     */   
/*     */   public boolean isWrapperFor(Class<?> iface) throws SQLException {
/* 578 */     checkClosed();
/*     */ 
/*     */ 
/*     */     
/* 582 */     return iface.isInstance(this);
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
/*     */   
/*     */   public <T> T unwrap(Class<T> iface) throws SQLException {
/*     */     try {
/* 608 */       return iface.cast(this);
/* 609 */     } catch (ClassCastException cce) {
/* 610 */       throw SQLError.createSQLException("Unable to unwrap to " + iface.toString(), "S1009", getExceptionInterceptor());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\JDBC4UpdatableResultSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */