/*     */ package org.apache.logging.log4j.core.appender.db.nosql;
/*     */ 
/*     */ import java.util.Date;
/*     */ import java.util.Map;
/*     */ import org.apache.logging.log4j.Marker;
/*     */ import org.apache.logging.log4j.ThreadContext;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.appender.AppenderLoggingException;
/*     */ import org.apache.logging.log4j.core.appender.ManagerFactory;
/*     */ import org.apache.logging.log4j.core.appender.db.AbstractDatabaseManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class NoSQLDatabaseManager<W>
/*     */   extends AbstractDatabaseManager
/*     */ {
/*  34 */   private static final NoSQLDatabaseManagerFactory FACTORY = new NoSQLDatabaseManagerFactory();
/*     */   
/*     */   private final NoSQLProvider<NoSQLConnection<W, ? extends NoSQLObject<W>>> provider;
/*     */   
/*     */   private NoSQLConnection<W, ? extends NoSQLObject<W>> connection;
/*     */ 
/*     */   
/*     */   private NoSQLDatabaseManager(String name, int bufferSize, NoSQLProvider<NoSQLConnection<W, ? extends NoSQLObject<W>>> provider) {
/*  42 */     super(name, bufferSize);
/*  43 */     this.provider = provider;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void connectInternal() {
/*  48 */     this.connection = this.provider.getConnection();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void disconnectInternal() {
/*  53 */     if (this.connection != null && !this.connection.isClosed()) {
/*  54 */       this.connection.close();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void writeInternal(LogEvent event) {
/*  60 */     if (!isConnected() || this.connection == null || this.connection.isClosed()) {
/*  61 */       throw new AppenderLoggingException("Cannot write logging event; NoSQL manager not connected to the database.");
/*     */     }
/*     */ 
/*     */     
/*  65 */     NoSQLObject<W> entity = this.connection.createObject();
/*  66 */     entity.set("level", event.getLevel());
/*  67 */     entity.set("loggerName", event.getLoggerName());
/*  68 */     entity.set("message", (event.getMessage() == null) ? null : event.getMessage().getFormattedMessage());
/*     */     
/*  70 */     StackTraceElement source = event.getSource();
/*  71 */     if (source == null) {
/*  72 */       entity.set("source", (Object)null);
/*     */     } else {
/*  74 */       entity.set("source", convertStackTraceElement(source));
/*     */     } 
/*     */     
/*  77 */     Marker marker = event.getMarker();
/*  78 */     if (marker == null) {
/*  79 */       entity.set("marker", (Object)null);
/*     */     } else {
/*  81 */       NoSQLObject<W> originalMarkerEntity = this.connection.createObject();
/*  82 */       NoSQLObject<W> markerEntity = originalMarkerEntity;
/*  83 */       markerEntity.set("name", marker.getName());
/*  84 */       while (marker.getParent() != null) {
/*  85 */         marker = marker.getParent();
/*  86 */         NoSQLObject<W> parentMarkerEntity = this.connection.createObject();
/*  87 */         parentMarkerEntity.set("name", marker.getName());
/*  88 */         markerEntity.set("parent", parentMarkerEntity);
/*  89 */         markerEntity = parentMarkerEntity;
/*     */       } 
/*  91 */       entity.set("marker", originalMarkerEntity);
/*     */     } 
/*     */     
/*  94 */     entity.set("threadName", event.getThreadName());
/*  95 */     entity.set("millis", Long.valueOf(event.getMillis()));
/*  96 */     entity.set("date", new Date(event.getMillis()));
/*     */ 
/*     */     
/*  99 */     Throwable thrown = event.getThrown();
/* 100 */     if (thrown == null) {
/* 101 */       entity.set("thrown", (Object)null);
/*     */     } else {
/* 103 */       NoSQLObject<W> originalExceptionEntity = this.connection.createObject();
/* 104 */       NoSQLObject<W> exceptionEntity = originalExceptionEntity;
/* 105 */       exceptionEntity.set("type", thrown.getClass().getName());
/* 106 */       exceptionEntity.set("message", thrown.getMessage());
/* 107 */       exceptionEntity.set("stackTrace", convertStackTrace(thrown.getStackTrace()));
/* 108 */       while (thrown.getCause() != null) {
/* 109 */         thrown = thrown.getCause();
/* 110 */         NoSQLObject<W> causingExceptionEntity = this.connection.createObject();
/* 111 */         causingExceptionEntity.set("type", thrown.getClass().getName());
/* 112 */         causingExceptionEntity.set("message", thrown.getMessage());
/* 113 */         causingExceptionEntity.set("stackTrace", convertStackTrace(thrown.getStackTrace()));
/* 114 */         exceptionEntity.set("cause", causingExceptionEntity);
/* 115 */         exceptionEntity = causingExceptionEntity;
/*     */       } 
/*     */       
/* 118 */       entity.set("thrown", originalExceptionEntity);
/*     */     } 
/*     */     
/* 121 */     Map<String, String> contextMap = event.getContextMap();
/* 122 */     if (contextMap == null) {
/* 123 */       entity.set("contextMap", (Object)null);
/*     */     } else {
/* 125 */       NoSQLObject<W> contextMapEntity = this.connection.createObject();
/* 126 */       for (Map.Entry<String, String> entry : contextMap.entrySet()) {
/* 127 */         contextMapEntity.set(entry.getKey(), entry.getValue());
/*     */       }
/* 129 */       entity.set("contextMap", contextMapEntity);
/*     */     } 
/*     */     
/* 132 */     ThreadContext.ContextStack contextStack = event.getContextStack();
/* 133 */     if (contextStack == null) {
/* 134 */       entity.set("contextStack", (Object)null);
/*     */     } else {
/* 136 */       entity.set("contextStack", contextStack.asList().toArray());
/*     */     } 
/*     */     
/* 139 */     this.connection.insertObject(entity);
/*     */   }
/*     */   
/*     */   private NoSQLObject<W>[] convertStackTrace(StackTraceElement[] stackTrace) {
/* 143 */     NoSQLObject<W>[] stackTraceEntities = this.connection.createList(stackTrace.length);
/* 144 */     for (int i = 0; i < stackTrace.length; i++) {
/* 145 */       stackTraceEntities[i] = convertStackTraceElement(stackTrace[i]);
/*     */     }
/* 147 */     return stackTraceEntities;
/*     */   }
/*     */   
/*     */   private NoSQLObject<W> convertStackTraceElement(StackTraceElement element) {
/* 151 */     NoSQLObject<W> elementEntity = this.connection.createObject();
/* 152 */     elementEntity.set("className", element.getClassName());
/* 153 */     elementEntity.set("methodName", element.getMethodName());
/* 154 */     elementEntity.set("fileName", element.getFileName());
/* 155 */     elementEntity.set("lineNumber", Integer.valueOf(element.getLineNumber()));
/* 156 */     return elementEntity;
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
/*     */   public static NoSQLDatabaseManager<?> getNoSQLDatabaseManager(String name, int bufferSize, NoSQLProvider<?> provider) {
/* 169 */     return (NoSQLDatabaseManager)AbstractDatabaseManager.getManager(name, new FactoryData(bufferSize, provider), FACTORY);
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class FactoryData
/*     */     extends AbstractDatabaseManager.AbstractFactoryData
/*     */   {
/*     */     private final NoSQLProvider<?> provider;
/*     */     
/*     */     protected FactoryData(int bufferSize, NoSQLProvider<?> provider) {
/* 179 */       super(bufferSize);
/* 180 */       this.provider = provider;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static final class NoSQLDatabaseManagerFactory
/*     */     implements ManagerFactory<NoSQLDatabaseManager<?>, FactoryData>
/*     */   {
/*     */     private NoSQLDatabaseManagerFactory() {}
/*     */ 
/*     */     
/*     */     public NoSQLDatabaseManager<?> createManager(String name, NoSQLDatabaseManager.FactoryData data) {
/* 192 */       return new NoSQLDatabaseManager(name, data.getBufferSize(), data.provider);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\db\nosql\NoSQLDatabaseManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */