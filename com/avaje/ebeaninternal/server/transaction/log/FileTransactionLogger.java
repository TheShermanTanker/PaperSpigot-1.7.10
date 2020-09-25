/*     */ package com.avaje.ebeaninternal.server.transaction.log;
/*     */ 
/*     */ import com.avaje.ebeaninternal.server.transaction.TransactionLogBuffer;
/*     */ import com.avaje.ebeaninternal.server.transaction.TransactionLogWriter;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.persistence.PersistenceException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FileTransactionLogger
/*     */   implements Runnable, TransactionLogWriter
/*     */ {
/*  52 */   private static final Logger logger = Logger.getLogger(FileTransactionLogger.class.getName());
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String atString = "        at ";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  62 */   private final String newLinePlaceholder = "\\r\\n";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  68 */   private final int maxStackTraceLines = 5;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  74 */   private final ConcurrentLinkedQueue<TransactionLogBuffer> logBufferQueue = new ConcurrentLinkedQueue<TransactionLogBuffer>();
/*     */   
/*  76 */   private final Object queueMonitor = new Object();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Thread logWriterThread;
/*     */ 
/*     */ 
/*     */   
/*     */   private final String threadName;
/*     */ 
/*     */ 
/*     */   
/*     */   private final String filepath;
/*     */ 
/*     */ 
/*     */   
/*  93 */   private final String deliminator = ", ";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String logFileName;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String logFileSuffix;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private volatile boolean shutdown;
/*     */ 
/*     */ 
/*     */   
/*     */   private volatile boolean shutdownComplete;
/*     */ 
/*     */ 
/*     */   
/*     */   private PrintStream out;
/*     */ 
/*     */ 
/*     */   
/*     */   private String currentPath;
/*     */ 
/*     */ 
/*     */   
/*     */   private int fileCounter;
/*     */ 
/*     */ 
/*     */   
/*     */   private long maxBytesPerFile;
/*     */ 
/*     */ 
/*     */   
/*     */   private long bytesWritten;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FileTransactionLogger(String threadName, String dir, String logFileName, int maxBytesPerFile) {
/* 138 */     this(threadName, dir, logFileName, "log", maxBytesPerFile);
/*     */   }
/*     */   
/*     */   public FileTransactionLogger(String threadName, String dir, String logFileName, String suffix, int maxBytesPerFile) {
/* 142 */     this.threadName = threadName;
/* 143 */     this.logFileName = logFileName;
/* 144 */     this.logFileSuffix = "." + suffix;
/* 145 */     this.maxBytesPerFile = maxBytesPerFile;
/*     */ 
/*     */     
/*     */     try {
/* 149 */       this.filepath = makeDirIfRequired(dir);
/*     */       
/* 151 */       switchFile(LogTime.nextDay());
/*     */     }
/* 153 */     catch (Exception e) {
/* 154 */       System.out.println("FATAL ERROR: init of FileLogger: " + e.getMessage());
/* 155 */       System.err.println("FATAL ERROR: init of FileLogger: " + e.getMessage());
/* 156 */       throw new RuntimeException(e);
/*     */     } 
/*     */     
/* 159 */     this.logWriterThread = new Thread(this, threadName);
/* 160 */     this.logWriterThread.setDaemon(true);
/*     */   }
/*     */   
/*     */   protected void finalize() throws Throwable {
/* 164 */     close();
/* 165 */     super.finalize();
/*     */   }
/*     */   
/*     */   public void start() {
/* 169 */     this.logWriterThread.start();
/*     */   }
/*     */ 
/*     */   
/*     */   public void shutdown() {
/* 174 */     this.shutdown = true;
/*     */     
/* 176 */     synchronized (this.logWriterThread) {
/*     */       
/*     */       try {
/* 179 */         this.logWriterThread.wait(20000L);
/* 180 */         logger.fine("Shutdown LogBufferWriter " + this.threadName + " shutdownComplete:" + this.shutdownComplete);
/*     */       }
/* 182 */       catch (InterruptedException e) {
/* 183 */         logger.fine("InterruptedException:" + e);
/*     */       } 
/*     */     } 
/*     */     
/* 187 */     if (!this.shutdownComplete) {
/* 188 */       String m = "WARNING: Shutdown of LogBufferWriter " + this.threadName + " not completed.";
/* 189 */       System.err.println(m);
/* 190 */       logger.warning(m);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/* 197 */     int missCount = 0;
/*     */     
/* 199 */     while (!this.shutdown || missCount < 10) {
/* 200 */       if (missCount > 50) {
/*     */         
/* 202 */         if (this.out != null) {
/* 203 */           this.out.flush();
/*     */         }
/*     */         try {
/* 206 */           Thread.sleep(20L);
/* 207 */         } catch (InterruptedException e) {
/* 208 */           logger.log(Level.INFO, "Interrupted TxnLogBufferWriter", e);
/*     */         } 
/*     */       } 
/* 211 */       synchronized (this.queueMonitor) {
/* 212 */         if (this.logBufferQueue.isEmpty()) {
/* 213 */           missCount++;
/*     */         } else {
/* 215 */           TransactionLogBuffer buffer = this.logBufferQueue.remove();
/* 216 */           write(buffer);
/* 217 */           missCount = 0;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 222 */     close();
/* 223 */     this.shutdownComplete = true;
/*     */     
/* 225 */     synchronized (this.logWriterThread) {
/* 226 */       this.logWriterThread.notifyAll();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void log(TransactionLogBuffer logBuffer) {
/* 231 */     this.logBufferQueue.add(logBuffer);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void write(TransactionLogBuffer logBuffer) {
/* 237 */     LogTime logTime = LogTime.get();
/* 238 */     if (logTime.isNextDay()) {
/* 239 */       logTime = LogTime.nextDay();
/* 240 */       switchFile(logTime);
/*     */     } 
/*     */     
/* 243 */     if (this.bytesWritten > this.maxBytesPerFile) {
/* 244 */       this.fileCounter++;
/* 245 */       switchFile(logTime);
/*     */     } 
/*     */     
/* 248 */     String txnId = logBuffer.getTransactionId();
/*     */     
/* 250 */     List<TransactionLogBuffer.LogEntry> messages = logBuffer.messages();
/* 251 */     for (int i = 0; i < messages.size(); i++) {
/* 252 */       TransactionLogBuffer.LogEntry msg = messages.get(i);
/* 253 */       printMessage(logTime, txnId, msg);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void printMessage(LogTime logTime, String txnId, TransactionLogBuffer.LogEntry logEntry) {
/* 259 */     String msg = logEntry.getMsg();
/* 260 */     int len = msg.length();
/* 261 */     if (len == 0) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/* 266 */     this.bytesWritten += 16L;
/* 267 */     this.bytesWritten += len;
/*     */     
/* 269 */     if (txnId != null) {
/* 270 */       this.bytesWritten += 7L;
/* 271 */       this.bytesWritten += txnId.length();
/* 272 */       this.out.append("txn[");
/* 273 */       this.out.append(txnId);
/* 274 */       this.out.append("]");
/* 275 */       this.out.append(", ");
/*     */     } 
/*     */     
/* 278 */     this.out.append(logTime.getTimestamp(logEntry.getTimestamp()));
/* 279 */     this.out.append(", ");
/* 280 */     this.out.append(msg).append(" ");
/* 281 */     this.out.append("\n");
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
/*     */   protected void printThrowable(StringBuilder sb, Throwable e, boolean isCause) {
/* 295 */     if (e != null) {
/* 296 */       if (isCause) {
/* 297 */         sb.append("Caused by: ");
/*     */       }
/* 299 */       sb.append(e.getClass().getName());
/* 300 */       sb.append(":");
/* 301 */       sb.append(e.getMessage()).append("\\r\\n");
/*     */       
/* 303 */       StackTraceElement[] ste = e.getStackTrace();
/* 304 */       int outputStackLines = ste.length;
/* 305 */       int notShownCount = 0;
/* 306 */       if (ste.length > 5) {
/* 307 */         outputStackLines = 5;
/* 308 */         notShownCount = ste.length - outputStackLines;
/*     */       } 
/* 310 */       for (int i = 0; i < outputStackLines; i++) {
/* 311 */         sb.append("        at ");
/* 312 */         sb.append(ste[i].toString()).append("\\r\\n");
/*     */       } 
/* 314 */       if (notShownCount > 0) {
/* 315 */         sb.append("        ... ");
/* 316 */         sb.append(notShownCount);
/* 317 */         sb.append(" more").append("\\r\\n");
/*     */       } 
/* 319 */       Throwable cause = e.getCause();
/* 320 */       if (cause != null) {
/* 321 */         printThrowable(sb, cause, true);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private String newFileName(LogTime logTime) {
/* 327 */     return this.filepath + File.separator + this.logFileName + logTime.getYMD() + "-" + this.fileCounter + this.logFileSuffix;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void switchFile(LogTime logTime) {
/*     */     try {
/* 336 */       long currentFileLength = 0L;
/* 337 */       String newFilePath = null;
/*     */ 
/*     */       
/*     */       do {
/* 341 */         newFilePath = newFileName(logTime);
/* 342 */         File f = new File(newFilePath);
/* 343 */         if (!f.exists()) {
/* 344 */           currentFileLength = 0L;
/*     */         }
/* 346 */         else if (f.length() < this.maxBytesPerFile * 0.8D) {
/* 347 */           currentFileLength = f.length();
/*     */         } else {
/* 349 */           this.fileCounter++;
/* 350 */           newFilePath = null;
/*     */         }
/*     */       
/* 353 */       } while (newFilePath == null);
/*     */       
/* 355 */       if (!newFilePath.equals(this.currentPath)) {
/* 356 */         PrintStream newOut = new PrintStream(new BufferedOutputStream(new FileOutputStream(newFilePath, true)));
/*     */         
/* 358 */         close();
/*     */         
/* 360 */         this.bytesWritten = currentFileLength;
/* 361 */         this.currentPath = newFilePath;
/* 362 */         this.out = newOut;
/*     */       }
/*     */     
/* 365 */     } catch (IOException e) {
/* 366 */       e.printStackTrace();
/* 367 */       logger.log(Level.SEVERE, "Error switch log file", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void close() {
/* 375 */     if (this.out != null) {
/* 376 */       this.out.flush();
/* 377 */       this.out.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String makeDirIfRequired(String dir) {
/* 386 */     File f = new File(dir);
/* 387 */     if (f.exists()) {
/* 388 */       if (!f.isDirectory()) {
/* 389 */         String msg = "Transaction logs directory is a file? " + dir;
/* 390 */         throw new PersistenceException(msg);
/*     */       }
/*     */     
/* 393 */     } else if (!f.mkdirs()) {
/* 394 */       String msg = "Failed to create transaction logs directory " + dir;
/* 395 */       logger.log(Level.SEVERE, msg);
/*     */     } 
/*     */     
/* 398 */     return dir;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\transaction\log\FileTransactionLogger.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */