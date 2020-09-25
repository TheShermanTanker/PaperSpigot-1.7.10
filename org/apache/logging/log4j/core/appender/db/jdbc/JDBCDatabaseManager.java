/*     */ package org.apache.logging.log4j.core.appender.db.jdbc;
/*     */ 
/*     */ import java.io.StringReader;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.appender.AppenderLoggingException;
/*     */ import org.apache.logging.log4j.core.appender.ManagerFactory;
/*     */ import org.apache.logging.log4j.core.appender.db.AbstractDatabaseManager;
/*     */ import org.apache.logging.log4j.core.helpers.Closer;
/*     */ import org.apache.logging.log4j.core.layout.PatternLayout;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class JDBCDatabaseManager
/*     */   extends AbstractDatabaseManager
/*     */ {
/*  38 */   private static final JDBCDatabaseManagerFactory FACTORY = new JDBCDatabaseManagerFactory();
/*     */   
/*     */   private final List<Column> columns;
/*     */   
/*     */   private final ConnectionSource connectionSource;
/*     */   
/*     */   private final String sqlStatement;
/*     */   private Connection connection;
/*     */   private PreparedStatement statement;
/*     */   
/*     */   private JDBCDatabaseManager(String name, int bufferSize, ConnectionSource connectionSource, String sqlStatement, List<Column> columns) {
/*  49 */     super(name, bufferSize);
/*  50 */     this.connectionSource = connectionSource;
/*  51 */     this.sqlStatement = sqlStatement;
/*  52 */     this.columns = columns;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void connectInternal() throws SQLException {
/*  57 */     this.connection = this.connectionSource.getConnection();
/*  58 */     this.statement = this.connection.prepareStatement(this.sqlStatement);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void disconnectInternal() throws SQLException {
/*     */     try {
/*  64 */       Closer.close(this.statement);
/*     */     } finally {
/*  66 */       Closer.close(this.connection);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void writeInternal(LogEvent event) {
/*  72 */     StringReader reader = null;
/*     */     try {
/*  74 */       if (!isConnected() || this.connection == null || this.connection.isClosed()) {
/*  75 */         throw new AppenderLoggingException("Cannot write logging event; JDBC manager not connected to the database.");
/*     */       }
/*     */ 
/*     */       
/*  79 */       int i = 1;
/*  80 */       for (Column column : this.columns) {
/*  81 */         if (column.isEventTimestamp) {
/*  82 */           this.statement.setTimestamp(i++, new Timestamp(event.getMillis())); continue;
/*     */         } 
/*  84 */         if (column.isClob) {
/*  85 */           reader = new StringReader(column.layout.toSerializable(event));
/*  86 */           if (column.isUnicode) {
/*  87 */             this.statement.setNClob(i++, reader); continue;
/*     */           } 
/*  89 */           this.statement.setClob(i++, reader);
/*     */           continue;
/*     */         } 
/*  92 */         if (column.isUnicode) {
/*  93 */           this.statement.setNString(i++, column.layout.toSerializable(event)); continue;
/*     */         } 
/*  95 */         this.statement.setString(i++, column.layout.toSerializable(event));
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 101 */       if (this.statement.executeUpdate() == 0) {
/* 102 */         throw new AppenderLoggingException("No records inserted in database table for log event in JDBC manager.");
/*     */       }
/*     */     }
/* 105 */     catch (SQLException e) {
/* 106 */       throw new AppenderLoggingException("Failed to insert record for log event in JDBC manager: " + e.getMessage(), e);
/*     */     } finally {
/*     */       
/* 109 */       Closer.closeSilent(reader);
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
/*     */   public static JDBCDatabaseManager getJDBCDatabaseManager(String name, int bufferSize, ConnectionSource connectionSource, String tableName, ColumnConfig[] columnConfigs) {
/* 128 */     return (JDBCDatabaseManager)AbstractDatabaseManager.getManager(name, new FactoryData(bufferSize, connectionSource, tableName, columnConfigs), FACTORY);
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class FactoryData
/*     */     extends AbstractDatabaseManager.AbstractFactoryData
/*     */   {
/*     */     private final ColumnConfig[] columnConfigs;
/*     */     
/*     */     private final ConnectionSource connectionSource;
/*     */     
/*     */     private final String tableName;
/*     */ 
/*     */     
/*     */     protected FactoryData(int bufferSize, ConnectionSource connectionSource, String tableName, ColumnConfig[] columnConfigs) {
/* 143 */       super(bufferSize);
/* 144 */       this.connectionSource = connectionSource;
/* 145 */       this.tableName = tableName;
/* 146 */       this.columnConfigs = columnConfigs;
/*     */     }
/*     */   }
/*     */   
/*     */   private static final class JDBCDatabaseManagerFactory
/*     */     implements ManagerFactory<JDBCDatabaseManager, FactoryData>
/*     */   {
/*     */     private JDBCDatabaseManagerFactory() {}
/*     */     
/*     */     public JDBCDatabaseManager createManager(String name, JDBCDatabaseManager.FactoryData data) {
/* 156 */       StringBuilder columnPart = new StringBuilder();
/* 157 */       StringBuilder valuePart = new StringBuilder();
/* 158 */       List<JDBCDatabaseManager.Column> columns = new ArrayList<JDBCDatabaseManager.Column>();
/* 159 */       int i = 0;
/* 160 */       for (ColumnConfig config : data.columnConfigs) {
/* 161 */         if (i++ > 0) {
/* 162 */           columnPart.append(',');
/* 163 */           valuePart.append(',');
/*     */         } 
/*     */         
/* 166 */         columnPart.append(config.getColumnName());
/*     */         
/* 168 */         if (config.getLiteralValue() != null) {
/* 169 */           valuePart.append(config.getLiteralValue());
/*     */         } else {
/* 171 */           columns.add(new JDBCDatabaseManager.Column(config.getLayout(), config.isEventTimestamp(), config.isUnicode(), config.isClob()));
/*     */ 
/*     */           
/* 174 */           valuePart.append('?');
/*     */         } 
/*     */       } 
/*     */       
/* 178 */       String sqlStatement = "INSERT INTO " + data.tableName + " (" + columnPart + ") VALUES (" + valuePart + ")";
/*     */ 
/*     */       
/* 181 */       return new JDBCDatabaseManager(name, data.getBufferSize(), data.connectionSource, sqlStatement, columns);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class Column
/*     */   {
/*     */     private final PatternLayout layout;
/*     */     
/*     */     private final boolean isEventTimestamp;
/*     */     
/*     */     private final boolean isUnicode;
/*     */     private final boolean isClob;
/*     */     
/*     */     private Column(PatternLayout layout, boolean isEventDate, boolean isUnicode, boolean isClob) {
/* 196 */       this.layout = layout;
/* 197 */       this.isEventTimestamp = isEventDate;
/* 198 */       this.isUnicode = isUnicode;
/* 199 */       this.isClob = isClob;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\db\jdbc\JDBCDatabaseManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */