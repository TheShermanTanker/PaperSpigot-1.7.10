/*     */ package org.apache.logging.log4j.core.appender;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.concurrent.locks.Lock;
/*     */ import java.util.concurrent.locks.ReadWriteLock;
/*     */ import java.util.concurrent.locks.ReentrantReadWriteLock;
/*     */ import org.apache.logging.log4j.core.Filter;
/*     */ import org.apache.logging.log4j.core.Layout;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractOutputStreamAppender
/*     */   extends AbstractAppender
/*     */ {
/*     */   protected final boolean immediateFlush;
/*     */   private volatile OutputStreamManager manager;
/*  46 */   private final ReadWriteLock rwLock = new ReentrantReadWriteLock();
/*  47 */   private final Lock readLock = this.rwLock.readLock();
/*  48 */   private final Lock writeLock = this.rwLock.writeLock();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractOutputStreamAppender(String name, Layout<? extends Serializable> layout, Filter filter, boolean ignoreExceptions, boolean immediateFlush, OutputStreamManager manager) {
/*  61 */     super(name, filter, layout, ignoreExceptions);
/*  62 */     this.manager = manager;
/*  63 */     this.immediateFlush = immediateFlush;
/*     */   }
/*     */   
/*     */   protected OutputStreamManager getManager() {
/*  67 */     return this.manager;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void replaceManager(OutputStreamManager newManager) {
/*  72 */     this.writeLock.lock();
/*     */     try {
/*  74 */       OutputStreamManager old = this.manager;
/*  75 */       this.manager = newManager;
/*  76 */       old.release();
/*     */     } finally {
/*  78 */       this.writeLock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void start() {
/*  85 */     if (getLayout() == null) {
/*  86 */       LOGGER.error("No layout set for the appender named [" + getName() + "].");
/*     */     }
/*  88 */     if (this.manager == null) {
/*  89 */       LOGGER.error("No OutputStreamManager set for the appender named [" + getName() + "].");
/*     */     }
/*  91 */     super.start();
/*     */   }
/*     */ 
/*     */   
/*     */   public void stop() {
/*  96 */     super.stop();
/*  97 */     this.manager.release();
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
/*     */   public void append(LogEvent event) {
/* 109 */     this.readLock.lock();
/*     */     try {
/* 111 */       byte[] bytes = getLayout().toByteArray(event);
/* 112 */       if (bytes.length > 0) {
/* 113 */         this.manager.write(bytes);
/* 114 */         if (this.immediateFlush || event.isEndOfBatch()) {
/* 115 */           this.manager.flush();
/*     */         }
/*     */       } 
/* 118 */     } catch (AppenderLoggingException ex) {
/* 119 */       error("Unable to write to stream " + this.manager.getName() + " for appender " + getName());
/* 120 */       throw ex;
/*     */     } finally {
/* 122 */       this.readLock.unlock();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\AbstractOutputStreamAppender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */