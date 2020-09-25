/*     */ package org.apache.logging.log4j.core.appender.rolling;
/*     */ 
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.util.concurrent.Semaphore;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.Layout;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.appender.FileManager;
/*     */ import org.apache.logging.log4j.core.appender.ManagerFactory;
/*     */ import org.apache.logging.log4j.core.appender.rolling.helper.AbstractAction;
/*     */ import org.apache.logging.log4j.core.appender.rolling.helper.Action;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RollingFileManager
/*     */   extends FileManager
/*     */ {
/*  40 */   private static RollingFileManagerFactory factory = new RollingFileManagerFactory();
/*     */   
/*     */   private long size;
/*     */   private long initialTime;
/*     */   private final PatternProcessor patternProcessor;
/*  45 */   private final Semaphore semaphore = new Semaphore(1);
/*     */   
/*     */   private final TriggeringPolicy policy;
/*     */   
/*     */   private final RolloverStrategy strategy;
/*     */   
/*     */   protected RollingFileManager(String fileName, String pattern, OutputStream os, boolean append, long size, long time, TriggeringPolicy policy, RolloverStrategy strategy, String advertiseURI, Layout<? extends Serializable> layout) {
/*  52 */     super(fileName, os, append, false, advertiseURI, layout);
/*  53 */     this.size = size;
/*  54 */     this.initialTime = time;
/*  55 */     this.policy = policy;
/*  56 */     this.strategy = strategy;
/*  57 */     this.patternProcessor = new PatternProcessor(pattern);
/*  58 */     policy.initialize(this);
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
/*     */   public static RollingFileManager getFileManager(String fileName, String pattern, boolean append, boolean bufferedIO, TriggeringPolicy policy, RolloverStrategy strategy, String advertiseURI, Layout<? extends Serializable> layout) {
/*  78 */     return (RollingFileManager)getManager(fileName, new FactoryData(pattern, append, bufferedIO, policy, strategy, advertiseURI, layout), factory);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected synchronized void write(byte[] bytes, int offset, int length) {
/*  84 */     this.size += length;
/*  85 */     super.write(bytes, offset, length);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getFileSize() {
/*  93 */     return this.size;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getFileTime() {
/* 101 */     return this.initialTime;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void checkRollover(LogEvent event) {
/* 109 */     if (this.policy.isTriggeringEvent(event) && rollover(this.strategy)) {
/*     */       try {
/* 111 */         this.size = 0L;
/* 112 */         this.initialTime = System.currentTimeMillis();
/* 113 */         createFileAfterRollover();
/* 114 */       } catch (IOException ex) {
/* 115 */         LOGGER.error("FileManager (" + getFileName() + ") " + ex);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   protected void createFileAfterRollover() throws IOException {
/* 121 */     OutputStream os = new FileOutputStream(getFileName(), isAppend());
/* 122 */     setOutputStream(os);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PatternProcessor getPatternProcessor() {
/* 130 */     return this.patternProcessor;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean rollover(RolloverStrategy strategy) {
/*     */     try {
/* 137 */       this.semaphore.acquire();
/* 138 */     } catch (InterruptedException ie) {
/* 139 */       LOGGER.error("Thread interrupted while attempting to check rollover", ie);
/* 140 */       return false;
/*     */     } 
/*     */     
/* 143 */     boolean success = false;
/* 144 */     Thread thread = null;
/*     */     
/*     */     try {
/* 147 */       RolloverDescription descriptor = strategy.rollover(this);
/*     */       
/* 149 */       if (descriptor != null) {
/*     */         
/* 151 */         close();
/*     */         
/* 153 */         if (descriptor.getSynchronous() != null) {
/*     */           
/*     */           try {
/* 156 */             success = descriptor.getSynchronous().execute();
/* 157 */           } catch (Exception ex) {
/* 158 */             LOGGER.error("Error in synchronous task", ex);
/*     */           } 
/*     */         }
/*     */         
/* 162 */         if (success && descriptor.getAsynchronous() != null) {
/* 163 */           thread = new Thread((Runnable)new AsyncAction(descriptor.getAsynchronous(), this));
/* 164 */           thread.start();
/*     */         } 
/* 166 */         return true;
/*     */       } 
/* 168 */       return false;
/*     */     } finally {
/* 170 */       if (thread == null) {
/* 171 */         this.semaphore.release();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class AsyncAction
/*     */     extends AbstractAction
/*     */   {
/*     */     private final Action action;
/*     */ 
/*     */ 
/*     */     
/*     */     private final RollingFileManager manager;
/*     */ 
/*     */ 
/*     */     
/*     */     public AsyncAction(Action act, RollingFileManager manager) {
/* 191 */       this.action = act;
/* 192 */       this.manager = manager;
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
/*     */     public boolean execute() throws IOException {
/*     */       try {
/* 206 */         return this.action.execute();
/*     */       } finally {
/* 208 */         this.manager.semaphore.release();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void close() {
/* 217 */       this.action.close();
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public boolean isComplete() {
/* 227 */       return this.action.isComplete();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class FactoryData
/*     */   {
/*     */     private final String pattern;
/*     */ 
/*     */     
/*     */     private final boolean append;
/*     */ 
/*     */     
/*     */     private final boolean bufferedIO;
/*     */ 
/*     */     
/*     */     private final TriggeringPolicy policy;
/*     */     
/*     */     private final RolloverStrategy strategy;
/*     */     
/*     */     private final String advertiseURI;
/*     */     
/*     */     private final Layout<? extends Serializable> layout;
/*     */ 
/*     */     
/*     */     public FactoryData(String pattern, boolean append, boolean bufferedIO, TriggeringPolicy policy, RolloverStrategy strategy, String advertiseURI, Layout<? extends Serializable> layout) {
/* 254 */       this.pattern = pattern;
/* 255 */       this.append = append;
/* 256 */       this.bufferedIO = bufferedIO;
/* 257 */       this.policy = policy;
/* 258 */       this.strategy = strategy;
/* 259 */       this.advertiseURI = advertiseURI;
/* 260 */       this.layout = layout;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class RollingFileManagerFactory
/*     */     implements ManagerFactory<RollingFileManager, FactoryData>
/*     */   {
/*     */     private RollingFileManagerFactory() {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public RollingFileManager createManager(String name, RollingFileManager.FactoryData data) {
/* 277 */       File file = new File(name);
/* 278 */       File parent = file.getParentFile();
/* 279 */       if (null != parent && !parent.exists()) {
/* 280 */         parent.mkdirs();
/*     */       }
/*     */       try {
/* 283 */         file.createNewFile();
/* 284 */       } catch (IOException ioe) {
/* 285 */         RollingFileManager.LOGGER.error("Unable to create file " + name, ioe);
/* 286 */         return null;
/*     */       } 
/* 288 */       long size = data.append ? file.length() : 0L;
/* 289 */       long time = file.lastModified();
/*     */ 
/*     */       
/*     */       try {
/* 293 */         OutputStream os = new FileOutputStream(name, data.append);
/* 294 */         if (data.bufferedIO) {
/* 295 */           os = new BufferedOutputStream(os);
/*     */         }
/* 297 */         return new RollingFileManager(name, data.pattern, os, data.append, size, time, data.policy, data.strategy, data.advertiseURI, data.layout);
/*     */       }
/* 299 */       catch (FileNotFoundException ex) {
/* 300 */         RollingFileManager.LOGGER.error("FileManager (" + name + ") " + ex);
/*     */         
/* 302 */         return null;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\rolling\RollingFileManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */