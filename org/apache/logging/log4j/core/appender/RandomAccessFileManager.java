/*     */ package org.apache.logging.log4j.core.appender;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.io.Serializable;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.logging.log4j.core.Layout;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RandomAccessFileManager
/*     */   extends OutputStreamManager
/*     */ {
/*     */   static final int DEFAULT_BUFFER_SIZE = 262144;
/*  38 */   private static final RandomAccessFileManagerFactory FACTORY = new RandomAccessFileManagerFactory();
/*     */   
/*     */   private final boolean isImmediateFlush;
/*     */   private final String advertiseURI;
/*     */   private final RandomAccessFile randomAccessFile;
/*     */   private final ByteBuffer buffer;
/*  44 */   private final ThreadLocal<Boolean> isEndOfBatch = new ThreadLocal<Boolean>();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected RandomAccessFileManager(RandomAccessFile file, String fileName, OutputStream os, boolean immediateFlush, String advertiseURI, Layout<? extends Serializable> layout) {
/*  50 */     super(os, fileName, layout);
/*  51 */     this.isImmediateFlush = immediateFlush;
/*  52 */     this.randomAccessFile = file;
/*  53 */     this.advertiseURI = advertiseURI;
/*  54 */     this.isEndOfBatch.set(Boolean.FALSE);
/*     */ 
/*     */     
/*  57 */     this.buffer = ByteBuffer.allocate(262144);
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
/*     */   public static RandomAccessFileManager getFileManager(String fileName, boolean append, boolean isFlush, String advertiseURI, Layout<? extends Serializable> layout) {
/*  75 */     return (RandomAccessFileManager)getManager(fileName, new FactoryData(append, isFlush, advertiseURI, layout), FACTORY);
/*     */   }
/*     */ 
/*     */   
/*     */   public Boolean isEndOfBatch() {
/*  80 */     return this.isEndOfBatch.get();
/*     */   }
/*     */   
/*     */   public void setEndOfBatch(boolean isEndOfBatch) {
/*  84 */     this.isEndOfBatch.set(Boolean.valueOf(isEndOfBatch));
/*     */   }
/*     */ 
/*     */   
/*     */   protected synchronized void write(byte[] bytes, int offset, int length) {
/*  89 */     super.write(bytes, offset, length);
/*     */     
/*  91 */     int chunk = 0;
/*     */     do {
/*  93 */       if (length > this.buffer.remaining()) {
/*  94 */         flush();
/*     */       }
/*  96 */       chunk = Math.min(length, this.buffer.remaining());
/*  97 */       this.buffer.put(bytes, offset, chunk);
/*  98 */       offset += chunk;
/*  99 */       length -= chunk;
/* 100 */     } while (length > 0);
/*     */     
/* 102 */     if (this.isImmediateFlush || this.isEndOfBatch.get() == Boolean.TRUE) {
/* 103 */       flush();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void flush() {
/* 109 */     this.buffer.flip();
/*     */     try {
/* 111 */       this.randomAccessFile.write(this.buffer.array(), 0, this.buffer.limit());
/* 112 */     } catch (IOException ex) {
/* 113 */       String msg = "Error writing to RandomAccessFile " + getName();
/* 114 */       throw new AppenderLoggingException(msg, ex);
/*     */     } 
/* 116 */     this.buffer.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized void close() {
/* 121 */     flush();
/*     */     try {
/* 123 */       this.randomAccessFile.close();
/* 124 */     } catch (IOException ex) {
/* 125 */       LOGGER.error("Unable to close RandomAccessFile " + getName() + ". " + ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFileName() {
/* 136 */     return getName();
/*     */   }
/*     */ 
/*     */ 
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
/*     */ 
/*     */     
/*     */     public void write(byte[] b, int off, int len) throws IOException {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, String> getContentFormat() {
/* 159 */     Map<String, String> result = new HashMap<String, String>(super.getContentFormat());
/*     */     
/* 161 */     result.put("fileURI", this.advertiseURI);
/* 162 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class FactoryData
/*     */   {
/*     */     private final boolean append;
/*     */ 
/*     */     
/*     */     private final boolean immediateFlush;
/*     */ 
/*     */     
/*     */     private final String advertiseURI;
/*     */     
/*     */     private final Layout<? extends Serializable> layout;
/*     */ 
/*     */     
/*     */     public FactoryData(boolean append, boolean immediateFlush, String advertiseURI, Layout<? extends Serializable> layout) {
/* 181 */       this.append = append;
/* 182 */       this.immediateFlush = immediateFlush;
/* 183 */       this.advertiseURI = advertiseURI;
/* 184 */       this.layout = layout;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class RandomAccessFileManagerFactory
/*     */     implements ManagerFactory<RandomAccessFileManager, FactoryData>
/*     */   {
/*     */     private RandomAccessFileManagerFactory() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public RandomAccessFileManager createManager(String name, RandomAccessFileManager.FactoryData data) {
/* 203 */       File file = new File(name);
/* 204 */       File parent = file.getParentFile();
/* 205 */       if (null != parent && !parent.exists()) {
/* 206 */         parent.mkdirs();
/*     */       }
/* 208 */       if (!data.append) {
/* 209 */         file.delete();
/*     */       }
/*     */       
/* 212 */       OutputStream os = new RandomAccessFileManager.DummyOutputStream();
/*     */       
/*     */       try {
/* 215 */         RandomAccessFile raf = new RandomAccessFile(name, "rw");
/* 216 */         if (data.append) {
/* 217 */           raf.seek(raf.length());
/*     */         } else {
/* 219 */           raf.setLength(0L);
/*     */         } 
/* 221 */         return new RandomAccessFileManager(raf, name, os, data.immediateFlush, data.advertiseURI, data.layout);
/*     */       }
/* 223 */       catch (Exception ex) {
/* 224 */         AbstractManager.LOGGER.error("RandomAccessFileManager (" + name + ") " + ex);
/*     */         
/* 226 */         return null;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\RandomAccessFileManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */