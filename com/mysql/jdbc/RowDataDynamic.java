/*     */ package com.mysql.jdbc;
/*     */ 
/*     */ import com.mysql.jdbc.profiler.ProfilerEvent;
/*     */ import com.mysql.jdbc.profiler.ProfilerEventHandler;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RowDataDynamic
/*     */   implements RowData
/*     */ {
/*     */   private int columnCount;
/*     */   private Field[] metadata;
/*     */   
/*     */   class OperationNotSupportedException
/*     */     extends SQLException
/*     */   {
/*     */     OperationNotSupportedException() {
/*  45 */       super(Messages.getString("RowDataDynamic.10"), "S1009");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  54 */   private int index = -1;
/*     */ 
/*     */ 
/*     */   
/*     */   private MysqlIO io;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isAfterEnd = false;
/*     */ 
/*     */   
/*     */   private boolean noMoreRows = false;
/*     */ 
/*     */   
/*     */   private boolean isBinaryEncoded = false;
/*     */ 
/*     */   
/*     */   private ResultSetRow nextRow;
/*     */ 
/*     */   
/*     */   private ResultSetImpl owner;
/*     */ 
/*     */   
/*     */   private boolean streamerClosed = false;
/*     */ 
/*     */   
/*     */   private boolean wasEmpty = false;
/*     */ 
/*     */   
/*     */   private boolean useBufferRowExplicit;
/*     */ 
/*     */   
/*     */   private boolean moreResultsExisted;
/*     */ 
/*     */   
/*     */   private ExceptionInterceptor exceptionInterceptor;
/*     */ 
/*     */ 
/*     */   
/*     */   public RowDataDynamic(MysqlIO io, int colCount, Field[] fields, boolean isBinaryEncoded) throws SQLException {
/*  94 */     this.io = io;
/*  95 */     this.columnCount = colCount;
/*  96 */     this.isBinaryEncoded = isBinaryEncoded;
/*  97 */     this.metadata = fields;
/*  98 */     this.exceptionInterceptor = this.io.getExceptionInterceptor();
/*  99 */     this.useBufferRowExplicit = MysqlIO.useBufferRowExplicit(this.metadata);
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
/*     */   public void addRow(ResultSetRow row) throws SQLException {
/* 111 */     notSupported();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void afterLast() throws SQLException {
/* 121 */     notSupported();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void beforeFirst() throws SQLException {
/* 131 */     notSupported();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void beforeLast() throws SQLException {
/* 141 */     notSupported();
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
/*     */   public void close() throws SQLException {
/* 156 */     Object mutex = this;
/*     */     
/* 158 */     MySQLConnection conn = null;
/*     */     
/* 160 */     if (this.owner != null) {
/* 161 */       conn = this.owner.connection;
/*     */       
/* 163 */       if (conn != null) {
/* 164 */         mutex = conn.getMutex();
/*     */       }
/*     */     } 
/*     */     
/* 168 */     boolean hadMore = false;
/* 169 */     int howMuchMore = 0;
/*     */     
/* 171 */     synchronized (mutex) {
/*     */       
/* 173 */       while (next() != null) {
/* 174 */         hadMore = true;
/* 175 */         howMuchMore++;
/*     */         
/* 177 */         if (howMuchMore % 100 == 0) {
/* 178 */           Thread.yield();
/*     */         }
/*     */       } 
/*     */       
/* 182 */       if (conn != null) {
/* 183 */         if (!conn.getClobberStreamingResults() && conn.getNetTimeoutForStreamingResults() > 0) {
/*     */           
/* 185 */           String oldValue = conn.getServerVariable("net_write_timeout");
/*     */ 
/*     */           
/* 188 */           if (oldValue == null || oldValue.length() == 0) {
/* 189 */             oldValue = "60";
/*     */           }
/*     */           
/* 192 */           this.io.clearInputStream();
/*     */           
/* 194 */           Statement stmt = null;
/*     */           
/*     */           try {
/* 197 */             stmt = conn.createStatement();
/* 198 */             ((StatementImpl)stmt).executeSimpleNonQuery(conn, "SET net_write_timeout=" + oldValue);
/*     */           } finally {
/* 200 */             if (stmt != null) {
/* 201 */               stmt.close();
/*     */             }
/*     */           } 
/*     */         } 
/*     */         
/* 206 */         if (conn.getUseUsageAdvisor() && 
/* 207 */           hadMore) {
/*     */           
/* 209 */           ProfilerEventHandler eventSink = ProfilerEventHandlerFactory.getInstance(conn);
/*     */ 
/*     */           
/* 212 */           eventSink.consumeEvent(new ProfilerEvent((byte)0, "", (this.owner.owningStatement == null) ? "N/A" : this.owner.owningStatement.currentCatalog, this.owner.connectionId, (this.owner.owningStatement == null) ? -1 : this.owner.owningStatement.getId(), -1, System.currentTimeMillis(), 0L, Constants.MILLIS_I18N, null, null, Messages.getString("RowDataDynamic.2") + howMuchMore + Messages.getString("RowDataDynamic.3") + Messages.getString("RowDataDynamic.4") + Messages.getString("RowDataDynamic.5") + Messages.getString("RowDataDynamic.6") + this.owner.pointOfOrigin));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 243 */     this.metadata = null;
/* 244 */     this.owner = null;
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
/*     */   public ResultSetRow getAt(int ind) throws SQLException {
/* 257 */     notSupported();
/*     */     
/* 259 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCurrentRowNumber() throws SQLException {
/* 270 */     notSupported();
/*     */     
/* 272 */     return -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResultSetInternalMethods getOwner() {
/* 279 */     return this.owner;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasNext() throws SQLException {
/* 290 */     boolean hasNext = (this.nextRow != null);
/*     */     
/* 292 */     if (!hasNext && !this.streamerClosed) {
/* 293 */       this.io.closeStreamer(this);
/* 294 */       this.streamerClosed = true;
/*     */     } 
/*     */     
/* 297 */     return hasNext;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAfterLast() throws SQLException {
/* 308 */     return this.isAfterEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isBeforeFirst() throws SQLException {
/* 319 */     return (this.index < 0);
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
/*     */   public boolean isDynamic() {
/* 331 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEmpty() throws SQLException {
/* 342 */     notSupported();
/*     */     
/* 344 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFirst() throws SQLException {
/* 355 */     notSupported();
/*     */     
/* 357 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLast() throws SQLException {
/* 368 */     notSupported();
/*     */     
/* 370 */     return false;
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
/*     */   public void moveRowRelative(int rows) throws SQLException {
/* 382 */     notSupported();
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
/*     */   public ResultSetRow next() throws SQLException {
/* 395 */     nextRecord();
/*     */     
/* 397 */     if (this.nextRow == null && !this.streamerClosed && !this.moreResultsExisted) {
/* 398 */       this.io.closeStreamer(this);
/* 399 */       this.streamerClosed = true;
/*     */     } 
/*     */     
/* 402 */     if (this.nextRow != null && 
/* 403 */       this.index != Integer.MAX_VALUE) {
/* 404 */       this.index++;
/*     */     }
/*     */ 
/*     */     
/* 408 */     return this.nextRow;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void nextRecord() throws SQLException {
/*     */     try {
/* 415 */       if (!this.noMoreRows) {
/* 416 */         this.nextRow = this.io.nextRow(this.metadata, this.columnCount, this.isBinaryEncoded, 1007, true, this.useBufferRowExplicit, true, null);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 421 */         if (this.nextRow == null) {
/* 422 */           this.noMoreRows = true;
/* 423 */           this.isAfterEnd = true;
/* 424 */           this.moreResultsExisted = this.io.tackOnMoreStreamingResults(this.owner);
/*     */           
/* 426 */           if (this.index == -1) {
/* 427 */             this.wasEmpty = true;
/*     */           }
/*     */         } 
/*     */       } else {
/* 431 */         this.isAfterEnd = true;
/*     */       } 
/* 433 */     } catch (SQLException sqlEx) {
/* 434 */       if (sqlEx instanceof StreamingNotifiable) {
/* 435 */         ((StreamingNotifiable)sqlEx).setWasStreamingResults();
/*     */       }
/*     */ 
/*     */       
/* 439 */       throw sqlEx;
/* 440 */     } catch (Exception ex) {
/* 441 */       String exceptionType = ex.getClass().getName();
/* 442 */       String exceptionMessage = ex.getMessage();
/*     */       
/* 444 */       exceptionMessage = exceptionMessage + Messages.getString("RowDataDynamic.7");
/* 445 */       exceptionMessage = exceptionMessage + Util.stackTraceToString(ex);
/*     */       
/* 447 */       SQLException sqlEx = SQLError.createSQLException(Messages.getString("RowDataDynamic.8") + exceptionType + Messages.getString("RowDataDynamic.9") + exceptionMessage, "S1000", this.exceptionInterceptor);
/*     */ 
/*     */ 
/*     */       
/* 451 */       sqlEx.initCause(ex);
/*     */       
/* 453 */       throw sqlEx;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void notSupported() throws SQLException {
/* 458 */     throw new OperationNotSupportedException();
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
/*     */   public void removeRow(int ind) throws SQLException {
/* 470 */     notSupported();
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
/*     */   public void setCurrentRow(int rowNumber) throws SQLException {
/* 482 */     notSupported();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setOwner(ResultSetImpl rs) {
/* 489 */     this.owner = rs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 498 */     return -1;
/*     */   }
/*     */   
/*     */   public boolean wasEmpty() {
/* 502 */     return this.wasEmpty;
/*     */   }
/*     */   
/*     */   public void setMetadata(Field[] metadata) {
/* 506 */     this.metadata = metadata;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\RowDataDynamic.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */