/*     */ package org.apache.logging.log4j.core.appender;
/*     */ 
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.nio.channels.FileChannel;
/*     */ import java.nio.channels.FileLock;
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
/*     */ public class FileManager
/*     */   extends OutputStreamManager
/*     */ {
/*  39 */   private static final FileManagerFactory FACTORY = new FileManagerFactory();
/*     */   
/*     */   private final boolean isAppend;
/*     */   
/*     */   private final boolean isLocking;
/*     */   private final String advertiseURI;
/*     */   
/*     */   protected FileManager(String fileName, OutputStream os, boolean append, boolean locking, String advertiseURI, Layout<? extends Serializable> layout) {
/*  47 */     super(os, fileName, layout);
/*  48 */     this.isAppend = append;
/*  49 */     this.isLocking = locking;
/*  50 */     this.advertiseURI = advertiseURI;
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
/*     */   public static FileManager getFileManager(String fileName, boolean append, boolean locking, boolean bufferedIO, String advertiseURI, Layout<? extends Serializable> layout) {
/*  67 */     if (locking && bufferedIO) {
/*  68 */       locking = false;
/*     */     }
/*  70 */     return (FileManager)getManager(fileName, new FactoryData(append, locking, bufferedIO, advertiseURI, layout), FACTORY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected synchronized void write(byte[] bytes, int offset, int length) {
/*  77 */     if (this.isLocking) {
/*  78 */       FileChannel channel = ((FileOutputStream)getOutputStream()).getChannel();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/*  87 */         FileLock lock = channel.lock(0L, Long.MAX_VALUE, false);
/*     */         try {
/*  89 */           super.write(bytes, offset, length);
/*     */         } finally {
/*  91 */           lock.release();
/*     */         } 
/*  93 */       } catch (IOException ex) {
/*  94 */         throw new AppenderLoggingException("Unable to obtain lock on " + getName(), ex);
/*     */       } 
/*     */     } else {
/*     */       
/*  98 */       super.write(bytes, offset, length);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFileName() {
/* 107 */     return getName();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isAppend() {
/* 115 */     return this.isAppend;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLocking() {
/* 123 */     return this.isLocking;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<String, String> getContentFormat() {
/* 133 */     Map<String, String> result = new HashMap<String, String>(super.getContentFormat());
/* 134 */     result.put("fileURI", this.advertiseURI);
/* 135 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class FactoryData
/*     */   {
/*     */     private final boolean append;
/*     */ 
/*     */     
/*     */     private final boolean locking;
/*     */ 
/*     */     
/*     */     private final boolean bufferedIO;
/*     */ 
/*     */     
/*     */     private final String advertiseURI;
/*     */     
/*     */     private final Layout<? extends Serializable> layout;
/*     */ 
/*     */     
/*     */     public FactoryData(boolean append, boolean locking, boolean bufferedIO, String advertiseURI, Layout<? extends Serializable> layout) {
/* 157 */       this.append = append;
/* 158 */       this.locking = locking;
/* 159 */       this.bufferedIO = bufferedIO;
/* 160 */       this.advertiseURI = advertiseURI;
/* 161 */       this.layout = layout;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static class FileManagerFactory
/*     */     implements ManagerFactory<FileManager, FactoryData>
/*     */   {
/*     */     private FileManagerFactory() {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public FileManager createManager(String name, FileManager.FactoryData data) {
/* 178 */       File file = new File(name);
/* 179 */       File parent = file.getParentFile();
/* 180 */       if (null != parent && !parent.exists()) {
/* 181 */         parent.mkdirs();
/*     */       }
/*     */ 
/*     */       
/*     */       try {
/* 186 */         OutputStream os = new FileOutputStream(name, data.append);
/* 187 */         if (data.bufferedIO) {
/* 188 */           os = new BufferedOutputStream(os);
/*     */         }
/* 190 */         return new FileManager(name, os, data.append, data.locking, data.advertiseURI, data.layout);
/* 191 */       } catch (FileNotFoundException ex) {
/* 192 */         AbstractManager.LOGGER.error("FileManager (" + name + ") " + ex);
/*     */         
/* 194 */         return null;
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\FileManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */