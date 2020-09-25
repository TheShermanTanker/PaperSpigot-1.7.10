/*     */ package org.apache.logging.log4j.core.appender.rolling;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.io.Serializable;
/*     */ import java.nio.ByteBuffer;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.Layout;
/*     */ import org.apache.logging.log4j.core.appender.AppenderLoggingException;
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
/*     */ public class RollingRandomAccessFileManager
/*     */   extends RollingFileManager
/*     */ {
/*     */   static final int DEFAULT_BUFFER_SIZE = 262144;
/*  38 */   private static final RollingRandomAccessFileManagerFactory FACTORY = new RollingRandomAccessFileManagerFactory();
/*     */   
/*     */   private final boolean isImmediateFlush;
/*     */   private RandomAccessFile randomAccessFile;
/*     */   private final ByteBuffer buffer;
/*  43 */   private final ThreadLocal<Boolean> isEndOfBatch = new ThreadLocal<Boolean>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public RollingRandomAccessFileManager(RandomAccessFile raf, String fileName, String pattern, OutputStream os, boolean append, boolean immediateFlush, long size, long time, TriggeringPolicy policy, RolloverStrategy strategy, String advertiseURI, Layout<? extends Serializable> layout) {
/*  50 */     super(fileName, pattern, os, append, size, time, policy, strategy, advertiseURI, layout);
/*  51 */     this.isImmediateFlush = immediateFlush;
/*  52 */     this.randomAccessFile = raf;
/*  53 */     this.isEndOfBatch.set(Boolean.FALSE);
/*     */ 
/*     */     
/*  56 */     this.buffer = ByteBuffer.allocate(262144);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static RollingRandomAccessFileManager getRollingRandomAccessFileManager(String fileName, String filePattern, boolean isAppend, boolean immediateFlush, TriggeringPolicy policy, RolloverStrategy strategy, String advertiseURI, Layout<? extends Serializable> layout) {
/*  62 */     return (RollingRandomAccessFileManager)getManager(fileName, new FactoryData(filePattern, isAppend, immediateFlush, policy, strategy, advertiseURI, layout), FACTORY);
/*     */   }
/*     */ 
/*     */   
/*     */   public Boolean isEndOfBatch() {
/*  67 */     return this.isEndOfBatch.get();
/*     */   }
/*     */   
/*     */   public void setEndOfBatch(boolean isEndOfBatch) {
/*  71 */     this.isEndOfBatch.set(Boolean.valueOf(isEndOfBatch));
/*     */   }
/*     */ 
/*     */   
/*     */   protected synchronized void write(byte[] bytes, int offset, int length) {
/*  76 */     super.write(bytes, offset, length);
/*     */     
/*  78 */     int chunk = 0;
/*     */     do {
/*  80 */       if (length > this.buffer.remaining()) {
/*  81 */         flush();
/*     */       }
/*  83 */       chunk = Math.min(length, this.buffer.remaining());
/*  84 */       this.buffer.put(bytes, offset, chunk);
/*  85 */       offset += chunk;
/*  86 */       length -= chunk;
/*  87 */     } while (length > 0);
/*     */     
/*  89 */     if (this.isImmediateFlush || this.isEndOfBatch.get() == Boolean.TRUE) {
/*  90 */       flush();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void createFileAfterRollover() throws IOException {
/*  96 */     this.randomAccessFile = new RandomAccessFile(getFileName(), "rw");
/*  97 */     if (isAppend()) {
/*  98 */       this.randomAccessFile.seek(this.randomAccessFile.length());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void flush() {
/* 104 */     this.buffer.flip();
/*     */     try {
/* 106 */       this.randomAccessFile.write(this.buffer.array(), 0, this.buffer.limit());
/* 107 */     } catch (IOException ex) {
/* 108 */       String msg = "Error writing to RandomAccessFile " + getName();
/* 109 */       throw new AppenderLoggingException(msg, ex);
/*     */     } 
/* 111 */     this.buffer.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void close() {
/* 116 */     flush();
/*     */     try {
/* 118 */       this.randomAccessFile.close();
/* 119 */     } catch (IOException ex) {
/* 120 */       LOGGER.error("Unable to close RandomAccessFile " + getName() + ". " + ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class RollingRandomAccessFileManagerFactory
/*     */     implements ManagerFactory<RollingRandomAccessFileManager, FactoryData>
/*     */   {
/*     */     private RollingRandomAccessFileManagerFactory() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public RollingRandomAccessFileManager createManager(String name, RollingRandomAccessFileManager.FactoryData data) {
/* 139 */       File file = new File(name);
/* 140 */       File parent = file.getParentFile();
/* 141 */       if (null != parent && !parent.exists()) {
/* 142 */         parent.mkdirs();
/*     */       }
/*     */       
/* 145 */       if (!data.append) {
/* 146 */         file.delete();
/*     */       }
/* 148 */       long size = data.append ? file.length() : 0L;
/* 149 */       long time = file.exists() ? file.lastModified() : System.currentTimeMillis();
/*     */       
/* 151 */       RandomAccessFile raf = null;
/*     */       try {
/* 153 */         raf = new RandomAccessFile(name, "rw");
/* 154 */         if (data.append) {
/* 155 */           long length = raf.length();
/* 156 */           RollingRandomAccessFileManager.LOGGER.trace("RandomAccessFile {} seek to {}", new Object[] { name, Long.valueOf(length) });
/* 157 */           raf.seek(length);
/*     */         } else {
/* 159 */           RollingRandomAccessFileManager.LOGGER.trace("RandomAccessFile {} set length to 0", new Object[] { name });
/* 160 */           raf.setLength(0L);
/*     */         } 
/* 162 */         return new RollingRandomAccessFileManager(raf, name, data.pattern, new RollingRandomAccessFileManager.DummyOutputStream(), data.append, data.immediateFlush, size, time, data.policy, data.strategy, data.advertiseURI, data.layout);
/*     */       }
/* 164 */       catch (IOException ex) {
/* 165 */         RollingRandomAccessFileManager.LOGGER.error("Cannot access RandomAccessFile {}) " + ex);
/* 166 */         if (raf != null) {
/*     */           try {
/* 168 */             raf.close();
/* 169 */           } catch (IOException e) {
/* 170 */             RollingRandomAccessFileManager.LOGGER.error("Cannot close RandomAccessFile {}", new Object[] { name, e });
/*     */           } 
/*     */         }
/*     */         
/* 174 */         return null;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class DummyOutputStream
/*     */     extends OutputStream
/*     */   {
/*     */     public void write(int b) throws IOException {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void write(byte[] b, int off, int len) throws IOException {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class FactoryData
/*     */   {
/*     */     private final String pattern;
/*     */     
/*     */     private final boolean append;
/*     */     
/*     */     private final boolean immediateFlush;
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
/*     */     public FactoryData(String pattern, boolean append, boolean immediateFlush, TriggeringPolicy policy, RolloverStrategy strategy, String advertiseURI, Layout<? extends Serializable> layout) {
/* 211 */       this.pattern = pattern;
/* 212 */       this.append = append;
/* 213 */       this.immediateFlush = immediateFlush;
/* 214 */       this.policy = policy;
/* 215 */       this.strategy = strategy;
/* 216 */       this.advertiseURI = advertiseURI;
/* 217 */       this.layout = layout;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\rolling\RollingRandomAccessFileManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */