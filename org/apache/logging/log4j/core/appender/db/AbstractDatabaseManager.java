/*     */ package org.apache.logging.log4j.core.appender.db;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.appender.AbstractManager;
/*     */ import org.apache.logging.log4j.core.appender.ManagerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractDatabaseManager
/*     */   extends AbstractManager
/*     */ {
/*     */   private final ArrayList<LogEvent> buffer;
/*     */   private final int bufferSize;
/*     */   private boolean connected = false;
/*     */   
/*     */   protected AbstractDatabaseManager(String name, int bufferSize) {
/*  42 */     super(name);
/*  43 */     this.bufferSize = bufferSize;
/*  44 */     this.buffer = new ArrayList<LogEvent>(bufferSize + 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void connectInternal() throws Exception;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final synchronized void connect() {
/*  58 */     if (!isConnected()) {
/*     */       try {
/*  60 */         connectInternal();
/*  61 */         this.connected = true;
/*  62 */       } catch (Exception e) {
/*  63 */         LOGGER.error("Could not connect to database using logging manager [{}].", new Object[] { getName(), e });
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void disconnectInternal() throws Exception;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final synchronized void disconnect() {
/*  81 */     flush();
/*  82 */     if (isConnected()) {
/*     */       try {
/*  84 */         disconnectInternal();
/*  85 */       } catch (Exception e) {
/*  86 */         LOGGER.warn("Error while disconnecting from database using logging manager [{}].", new Object[] { getName(), e });
/*     */       } finally {
/*  88 */         this.connected = false;
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
/*     */   public final boolean isConnected() {
/* 100 */     return this.connected;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void writeInternal(LogEvent paramLogEvent);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final synchronized void flush() {
/* 116 */     if (isConnected() && this.buffer.size() > 0) {
/* 117 */       for (LogEvent event : this.buffer) {
/* 118 */         writeInternal(event);
/*     */       }
/* 120 */       this.buffer.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final synchronized void write(LogEvent event) {
/* 130 */     if (this.bufferSize > 0) {
/* 131 */       this.buffer.add(event);
/* 132 */       if (this.buffer.size() >= this.bufferSize || event.isEndOfBatch()) {
/* 133 */         flush();
/*     */       }
/*     */     } else {
/* 136 */       writeInternal(event);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public final void releaseSub() {
/* 142 */     disconnect();
/*     */   }
/*     */ 
/*     */   
/*     */   public final String toString() {
/* 147 */     return getName();
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
/*     */   protected static <M extends AbstractDatabaseManager, T extends AbstractFactoryData> M getManager(String name, T data, ManagerFactory<M, T> factory) {
/* 165 */     return (M)AbstractManager.getManager(name, factory, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected static abstract class AbstractFactoryData
/*     */   {
/*     */     private final int bufferSize;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected AbstractFactoryData(int bufferSize) {
/* 181 */       this.bufferSize = bufferSize;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int getBufferSize() {
/* 190 */       return this.bufferSize;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\db\AbstractDatabaseManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */