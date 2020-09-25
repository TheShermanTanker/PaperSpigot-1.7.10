/*     */ package com.mysql.jdbc;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.sql.Blob;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BlobFromLocator
/*     */   implements Blob
/*     */ {
/*  56 */   private List primaryKeyColumns = null;
/*     */   
/*  58 */   private List primaryKeyValues = null;
/*     */ 
/*     */   
/*     */   private ResultSetImpl creatorResultSet;
/*     */   
/*  63 */   private String blobColumnName = null;
/*     */   
/*  65 */   private String tableName = null;
/*     */   
/*  67 */   private int numColsInResultSet = 0;
/*     */   
/*  69 */   private int numPrimaryKeys = 0;
/*     */ 
/*     */   
/*     */   private String quotedId;
/*     */ 
/*     */   
/*     */   private ExceptionInterceptor exceptionInterceptor;
/*     */ 
/*     */ 
/*     */   
/*     */   BlobFromLocator(ResultSetImpl creatorResultSetToSet, int blobColumnIndex, ExceptionInterceptor exceptionInterceptor) throws SQLException {
/*  80 */     this.exceptionInterceptor = exceptionInterceptor;
/*  81 */     this.creatorResultSet = creatorResultSetToSet;
/*     */     
/*  83 */     this.numColsInResultSet = this.creatorResultSet.fields.length;
/*  84 */     this.quotedId = this.creatorResultSet.connection.getMetaData().getIdentifierQuoteString();
/*     */ 
/*     */     
/*  87 */     if (this.numColsInResultSet > 1) {
/*  88 */       this.primaryKeyColumns = new ArrayList();
/*  89 */       this.primaryKeyValues = new ArrayList();
/*     */       
/*  91 */       for (int i = 0; i < this.numColsInResultSet; i++) {
/*  92 */         if (this.creatorResultSet.fields[i].isPrimaryKey()) {
/*  93 */           StringBuffer keyName = new StringBuffer();
/*  94 */           keyName.append(this.quotedId);
/*     */           
/*  96 */           String originalColumnName = this.creatorResultSet.fields[i].getOriginalName();
/*     */ 
/*     */           
/*  99 */           if (originalColumnName != null && originalColumnName.length() > 0) {
/*     */             
/* 101 */             keyName.append(originalColumnName);
/*     */           } else {
/* 103 */             keyName.append(this.creatorResultSet.fields[i].getName());
/*     */           } 
/*     */ 
/*     */           
/* 107 */           keyName.append(this.quotedId);
/*     */           
/* 109 */           this.primaryKeyColumns.add(keyName.toString());
/* 110 */           this.primaryKeyValues.add(this.creatorResultSet.getString(i + 1));
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       
/* 115 */       notEnoughInformationInQuery();
/*     */     } 
/*     */     
/* 118 */     this.numPrimaryKeys = this.primaryKeyColumns.size();
/*     */     
/* 120 */     if (this.numPrimaryKeys == 0) {
/* 121 */       notEnoughInformationInQuery();
/*     */     }
/*     */     
/* 124 */     if (this.creatorResultSet.fields[0].getOriginalTableName() != null) {
/* 125 */       StringBuffer tableNameBuffer = new StringBuffer();
/*     */       
/* 127 */       String databaseName = this.creatorResultSet.fields[0].getDatabaseName();
/*     */ 
/*     */       
/* 130 */       if (databaseName != null && databaseName.length() > 0) {
/* 131 */         tableNameBuffer.append(this.quotedId);
/* 132 */         tableNameBuffer.append(databaseName);
/* 133 */         tableNameBuffer.append(this.quotedId);
/* 134 */         tableNameBuffer.append('.');
/*     */       } 
/*     */       
/* 137 */       tableNameBuffer.append(this.quotedId);
/* 138 */       tableNameBuffer.append(this.creatorResultSet.fields[0].getOriginalTableName());
/*     */       
/* 140 */       tableNameBuffer.append(this.quotedId);
/*     */       
/* 142 */       this.tableName = tableNameBuffer.toString();
/*     */     } else {
/* 144 */       StringBuffer tableNameBuffer = new StringBuffer();
/*     */       
/* 146 */       tableNameBuffer.append(this.quotedId);
/* 147 */       tableNameBuffer.append(this.creatorResultSet.fields[0].getTableName());
/*     */       
/* 149 */       tableNameBuffer.append(this.quotedId);
/*     */       
/* 151 */       this.tableName = tableNameBuffer.toString();
/*     */     } 
/*     */     
/* 154 */     this.blobColumnName = this.quotedId + this.creatorResultSet.getString(blobColumnIndex) + this.quotedId;
/*     */   }
/*     */ 
/*     */   
/*     */   private void notEnoughInformationInQuery() throws SQLException {
/* 159 */     throw SQLError.createSQLException("Emulated BLOB locators must come from a ResultSet with only one table selected, and all primary keys selected", "S1000", this.exceptionInterceptor);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OutputStream setBinaryStream(long indexToWriteAt) throws SQLException {
/* 169 */     throw SQLError.notImplemented();
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
/*     */   public InputStream getBinaryStream() throws SQLException {
/* 182 */     return new BufferedInputStream(new LocatorInputStream(), this.creatorResultSet.connection.getLocatorFetchBufferSize());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int setBytes(long writeAt, byte[] bytes, int offset, int length) throws SQLException {
/* 191 */     PreparedStatement pStmt = null;
/*     */     
/* 193 */     if (offset + length > bytes.length) {
/* 194 */       length = bytes.length - offset;
/*     */     }
/*     */     
/* 197 */     byte[] bytesToWrite = new byte[length];
/* 198 */     System.arraycopy(bytes, offset, bytesToWrite, 0, length);
/*     */ 
/*     */     
/* 201 */     StringBuffer query = new StringBuffer("UPDATE ");
/* 202 */     query.append(this.tableName);
/* 203 */     query.append(" SET ");
/* 204 */     query.append(this.blobColumnName);
/* 205 */     query.append(" = INSERT(");
/* 206 */     query.append(this.blobColumnName);
/* 207 */     query.append(", ");
/* 208 */     query.append(writeAt);
/* 209 */     query.append(", ");
/* 210 */     query.append(length);
/* 211 */     query.append(", ?) WHERE ");
/*     */     
/* 213 */     query.append(this.primaryKeyColumns.get(0));
/* 214 */     query.append(" = ?");
/*     */     int i;
/* 216 */     for (i = 1; i < this.numPrimaryKeys; i++) {
/* 217 */       query.append(" AND ");
/* 218 */       query.append(this.primaryKeyColumns.get(i));
/* 219 */       query.append(" = ?");
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 224 */       pStmt = this.creatorResultSet.connection.prepareStatement(query.toString());
/*     */ 
/*     */       
/* 227 */       pStmt.setBytes(1, bytesToWrite);
/*     */       
/* 229 */       for (i = 0; i < this.numPrimaryKeys; i++) {
/* 230 */         pStmt.setString(i + 2, this.primaryKeyValues.get(i));
/*     */       }
/*     */       
/* 233 */       int rowsUpdated = pStmt.executeUpdate();
/*     */       
/* 235 */       if (rowsUpdated != 1) {
/* 236 */         throw SQLError.createSQLException("BLOB data not found! Did primary keys change?", "S1000", this.exceptionInterceptor);
/*     */       }
/*     */     }
/*     */     finally {
/*     */       
/* 241 */       if (pStmt != null) {
/*     */         try {
/* 243 */           pStmt.close();
/* 244 */         } catch (SQLException sqlEx) {}
/*     */ 
/*     */ 
/*     */         
/* 248 */         pStmt = null;
/*     */       } 
/*     */     } 
/*     */     
/* 252 */     return (int)length();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int setBytes(long writeAt, byte[] bytes) throws SQLException {
/* 259 */     return setBytes(writeAt, bytes, 0, bytes.length);
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
/*     */   public byte[] getBytes(long pos, int length) throws SQLException {
/* 278 */     PreparedStatement pStmt = null;
/*     */ 
/*     */     
/*     */     try {
/* 282 */       pStmt = createGetBytesStatement();
/*     */       
/* 284 */       return getBytesInternal(pStmt, pos, length);
/*     */     } finally {
/* 286 */       if (pStmt != null) {
/*     */         try {
/* 288 */           pStmt.close();
/* 289 */         } catch (SQLException sqlEx) {}
/*     */ 
/*     */ 
/*     */         
/* 293 */         pStmt = null;
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
/*     */   public long length() throws SQLException {
/* 308 */     ResultSet blobRs = null;
/* 309 */     PreparedStatement pStmt = null;
/*     */ 
/*     */     
/* 312 */     StringBuffer query = new StringBuffer("SELECT LENGTH(");
/* 313 */     query.append(this.blobColumnName);
/* 314 */     query.append(") FROM ");
/* 315 */     query.append(this.tableName);
/* 316 */     query.append(" WHERE ");
/*     */     
/* 318 */     query.append(this.primaryKeyColumns.get(0));
/* 319 */     query.append(" = ?");
/*     */     int i;
/* 321 */     for (i = 1; i < this.numPrimaryKeys; i++) {
/* 322 */       query.append(" AND ");
/* 323 */       query.append(this.primaryKeyColumns.get(i));
/* 324 */       query.append(" = ?");
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 329 */       pStmt = this.creatorResultSet.connection.prepareStatement(query.toString());
/*     */ 
/*     */       
/* 332 */       for (i = 0; i < this.numPrimaryKeys; i++) {
/* 333 */         pStmt.setString(i + 1, this.primaryKeyValues.get(i));
/*     */       }
/*     */       
/* 336 */       blobRs = pStmt.executeQuery();
/*     */       
/* 338 */       if (blobRs.next()) {
/* 339 */         return blobRs.getLong(1);
/*     */       }
/*     */       
/* 342 */       throw SQLError.createSQLException("BLOB data not found! Did primary keys change?", "S1000", this.exceptionInterceptor);
/*     */     }
/*     */     finally {
/*     */       
/* 346 */       if (blobRs != null) {
/*     */         try {
/* 348 */           blobRs.close();
/* 349 */         } catch (SQLException sqlEx) {}
/*     */ 
/*     */ 
/*     */         
/* 353 */         blobRs = null;
/*     */       } 
/*     */       
/* 356 */       if (pStmt != null) {
/*     */         try {
/* 358 */           pStmt.close();
/* 359 */         } catch (SQLException sqlEx) {}
/*     */ 
/*     */ 
/*     */         
/* 363 */         pStmt = null;
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
/*     */   public long position(Blob pattern, long start) throws SQLException {
/* 383 */     return position(pattern.getBytes(0L, (int)pattern.length()), start);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long position(byte[] pattern, long start) throws SQLException {
/* 390 */     ResultSet blobRs = null;
/* 391 */     PreparedStatement pStmt = null;
/*     */ 
/*     */     
/* 394 */     StringBuffer query = new StringBuffer("SELECT LOCATE(");
/* 395 */     query.append("?, ");
/* 396 */     query.append(this.blobColumnName);
/* 397 */     query.append(", ");
/* 398 */     query.append(start);
/* 399 */     query.append(") FROM ");
/* 400 */     query.append(this.tableName);
/* 401 */     query.append(" WHERE ");
/*     */     
/* 403 */     query.append(this.primaryKeyColumns.get(0));
/* 404 */     query.append(" = ?");
/*     */     int i;
/* 406 */     for (i = 1; i < this.numPrimaryKeys; i++) {
/* 407 */       query.append(" AND ");
/* 408 */       query.append(this.primaryKeyColumns.get(i));
/* 409 */       query.append(" = ?");
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 414 */       pStmt = this.creatorResultSet.connection.prepareStatement(query.toString());
/*     */       
/* 416 */       pStmt.setBytes(1, pattern);
/*     */       
/* 418 */       for (i = 0; i < this.numPrimaryKeys; i++) {
/* 419 */         pStmt.setString(i + 2, this.primaryKeyValues.get(i));
/*     */       }
/*     */       
/* 422 */       blobRs = pStmt.executeQuery();
/*     */       
/* 424 */       if (blobRs.next()) {
/* 425 */         return blobRs.getLong(1);
/*     */       }
/*     */       
/* 428 */       throw SQLError.createSQLException("BLOB data not found! Did primary keys change?", "S1000", this.exceptionInterceptor);
/*     */     }
/*     */     finally {
/*     */       
/* 432 */       if (blobRs != null) {
/*     */         try {
/* 434 */           blobRs.close();
/* 435 */         } catch (SQLException sqlEx) {}
/*     */ 
/*     */ 
/*     */         
/* 439 */         blobRs = null;
/*     */       } 
/*     */       
/* 442 */       if (pStmt != null) {
/*     */         try {
/* 444 */           pStmt.close();
/* 445 */         } catch (SQLException sqlEx) {}
/*     */ 
/*     */ 
/*     */         
/* 449 */         pStmt = null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void truncate(long length) throws SQLException {
/* 458 */     PreparedStatement pStmt = null;
/*     */ 
/*     */     
/* 461 */     StringBuffer query = new StringBuffer("UPDATE ");
/* 462 */     query.append(this.tableName);
/* 463 */     query.append(" SET ");
/* 464 */     query.append(this.blobColumnName);
/* 465 */     query.append(" = LEFT(");
/* 466 */     query.append(this.blobColumnName);
/* 467 */     query.append(", ");
/* 468 */     query.append(length);
/* 469 */     query.append(") WHERE ");
/*     */     
/* 471 */     query.append(this.primaryKeyColumns.get(0));
/* 472 */     query.append(" = ?");
/*     */     int i;
/* 474 */     for (i = 1; i < this.numPrimaryKeys; i++) {
/* 475 */       query.append(" AND ");
/* 476 */       query.append(this.primaryKeyColumns.get(i));
/* 477 */       query.append(" = ?");
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 482 */       pStmt = this.creatorResultSet.connection.prepareStatement(query.toString());
/*     */ 
/*     */       
/* 485 */       for (i = 0; i < this.numPrimaryKeys; i++) {
/* 486 */         pStmt.setString(i + 1, this.primaryKeyValues.get(i));
/*     */       }
/*     */       
/* 489 */       int rowsUpdated = pStmt.executeUpdate();
/*     */       
/* 491 */       if (rowsUpdated != 1) {
/* 492 */         throw SQLError.createSQLException("BLOB data not found! Did primary keys change?", "S1000", this.exceptionInterceptor);
/*     */       }
/*     */     }
/*     */     finally {
/*     */       
/* 497 */       if (pStmt != null) {
/*     */         try {
/* 499 */           pStmt.close();
/* 500 */         } catch (SQLException sqlEx) {}
/*     */ 
/*     */ 
/*     */         
/* 504 */         pStmt = null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   PreparedStatement createGetBytesStatement() throws SQLException {
/* 510 */     StringBuffer query = new StringBuffer("SELECT SUBSTRING(");
/*     */     
/* 512 */     query.append(this.blobColumnName);
/* 513 */     query.append(", ");
/* 514 */     query.append("?");
/* 515 */     query.append(", ");
/* 516 */     query.append("?");
/* 517 */     query.append(") FROM ");
/* 518 */     query.append(this.tableName);
/* 519 */     query.append(" WHERE ");
/*     */     
/* 521 */     query.append(this.primaryKeyColumns.get(0));
/* 522 */     query.append(" = ?");
/*     */     
/* 524 */     for (int i = 1; i < this.numPrimaryKeys; i++) {
/* 525 */       query.append(" AND ");
/* 526 */       query.append(this.primaryKeyColumns.get(i));
/* 527 */       query.append(" = ?");
/*     */     } 
/*     */     
/* 530 */     return this.creatorResultSet.connection.prepareStatement(query.toString());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   byte[] getBytesInternal(PreparedStatement pStmt, long pos, int length) throws SQLException {
/* 537 */     ResultSet blobRs = null;
/*     */ 
/*     */     
/*     */     try {
/* 541 */       pStmt.setLong(1, pos);
/* 542 */       pStmt.setInt(2, length);
/*     */       
/* 544 */       for (int i = 0; i < this.numPrimaryKeys; i++) {
/* 545 */         pStmt.setString(i + 3, this.primaryKeyValues.get(i));
/*     */       }
/*     */       
/* 548 */       blobRs = pStmt.executeQuery();
/*     */       
/* 550 */       if (blobRs.next()) {
/* 551 */         return ((ResultSetImpl)blobRs).getBytes(1, true);
/*     */       }
/*     */       
/* 554 */       throw SQLError.createSQLException("BLOB data not found! Did primary keys change?", "S1000", this.exceptionInterceptor);
/*     */     }
/*     */     finally {
/*     */       
/* 558 */       if (blobRs != null) {
/*     */         try {
/* 560 */           blobRs.close();
/* 561 */         } catch (SQLException sqlEx) {}
/*     */ 
/*     */ 
/*     */         
/* 565 */         blobRs = null;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   class LocatorInputStream extends InputStream {
/* 571 */     long currentPositionInBlob = 0L;
/*     */     
/* 573 */     long length = 0L;
/*     */     
/* 575 */     PreparedStatement pStmt = null;
/*     */     
/*     */     LocatorInputStream() throws SQLException {
/* 578 */       this.length = BlobFromLocator.this.length();
/* 579 */       this.pStmt = BlobFromLocator.this.createGetBytesStatement();
/*     */     }
/*     */     
/*     */     LocatorInputStream(long pos, long len) throws SQLException {
/* 583 */       this.length = pos + len;
/* 584 */       this.currentPositionInBlob = pos;
/* 585 */       long blobLength = BlobFromLocator.this.length();
/*     */       
/* 587 */       if (pos + len > blobLength) {
/* 588 */         throw SQLError.createSQLException(Messages.getString("Blob.invalidStreamLength", new Object[] { new Long(blobLength), new Long(pos), new Long(len) }), "S1009", BlobFromLocator.this.exceptionInterceptor);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 594 */       if (pos < 1L) {
/* 595 */         throw SQLError.createSQLException(Messages.getString("Blob.invalidStreamPos"), "S1009", BlobFromLocator.this.exceptionInterceptor);
/*     */       }
/*     */ 
/*     */       
/* 599 */       if (pos > blobLength) {
/* 600 */         throw SQLError.createSQLException(Messages.getString("Blob.invalidStreamPos"), "S1009", BlobFromLocator.this.exceptionInterceptor);
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     public int read() throws IOException {
/* 606 */       if (this.currentPositionInBlob + 1L > this.length) {
/* 607 */         return -1;
/*     */       }
/*     */       
/*     */       try {
/* 611 */         byte[] asBytes = BlobFromLocator.this.getBytesInternal(this.pStmt, this.currentPositionInBlob++ + 1L, 1);
/*     */ 
/*     */         
/* 614 */         if (asBytes == null) {
/* 615 */           return -1;
/*     */         }
/*     */         
/* 618 */         return asBytes[0];
/* 619 */       } catch (SQLException sqlEx) {
/* 620 */         throw new IOException(sqlEx.toString());
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int read(byte[] b, int off, int len) throws IOException {
/* 630 */       if (this.currentPositionInBlob + 1L > this.length) {
/* 631 */         return -1;
/*     */       }
/*     */       
/*     */       try {
/* 635 */         byte[] asBytes = BlobFromLocator.this.getBytesInternal(this.pStmt, this.currentPositionInBlob + 1L, len);
/*     */ 
/*     */         
/* 638 */         if (asBytes == null) {
/* 639 */           return -1;
/*     */         }
/*     */         
/* 642 */         System.arraycopy(asBytes, 0, b, off, asBytes.length);
/*     */         
/* 644 */         this.currentPositionInBlob += asBytes.length;
/*     */         
/* 646 */         return asBytes.length;
/* 647 */       } catch (SQLException sqlEx) {
/* 648 */         throw new IOException(sqlEx.toString());
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int read(byte[] b) throws IOException {
/* 658 */       if (this.currentPositionInBlob + 1L > this.length) {
/* 659 */         return -1;
/*     */       }
/*     */       
/*     */       try {
/* 663 */         byte[] asBytes = BlobFromLocator.this.getBytesInternal(this.pStmt, this.currentPositionInBlob + 1L, b.length);
/*     */ 
/*     */         
/* 666 */         if (asBytes == null) {
/* 667 */           return -1;
/*     */         }
/*     */         
/* 670 */         System.arraycopy(asBytes, 0, b, 0, asBytes.length);
/*     */         
/* 672 */         this.currentPositionInBlob += asBytes.length;
/*     */         
/* 674 */         return asBytes.length;
/* 675 */       } catch (SQLException sqlEx) {
/* 676 */         throw new IOException(sqlEx.toString());
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void close() throws IOException {
/* 686 */       if (this.pStmt != null) {
/*     */         try {
/* 688 */           this.pStmt.close();
/* 689 */         } catch (SQLException sqlEx) {
/* 690 */           throw new IOException(sqlEx.toString());
/*     */         } 
/*     */       }
/*     */       
/* 694 */       super.close();
/*     */     }
/*     */   }
/*     */   
/*     */   public void free() throws SQLException {
/* 699 */     this.creatorResultSet = null;
/* 700 */     this.primaryKeyColumns = null;
/* 701 */     this.primaryKeyValues = null;
/*     */   }
/*     */   
/*     */   public InputStream getBinaryStream(long pos, long length) throws SQLException {
/* 705 */     return new LocatorInputStream(pos, length);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\BlobFromLocator.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */