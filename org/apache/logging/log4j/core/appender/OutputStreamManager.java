/*     */ package org.apache.logging.log4j.core.appender;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
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
/*     */ public class OutputStreamManager
/*     */   extends AbstractManager
/*     */ {
/*     */   private volatile OutputStream os;
/*     */   private final byte[] footer;
/*     */   private final byte[] header;
/*     */   
/*     */   protected OutputStreamManager(OutputStream os, String streamName, Layout<?> layout) {
/*  36 */     super(streamName);
/*  37 */     this.os = os;
/*  38 */     if (layout != null) {
/*  39 */       this.footer = layout.getFooter();
/*  40 */       this.header = layout.getHeader();
/*  41 */       if (this.header != null) {
/*     */         try {
/*  43 */           this.os.write(this.header, 0, this.header.length);
/*  44 */         } catch (IOException ioe) {
/*  45 */           LOGGER.error("Unable to write header", ioe);
/*     */         } 
/*     */       }
/*     */     } else {
/*  49 */       this.footer = null;
/*  50 */       this.header = null;
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
/*     */   public static <T> OutputStreamManager getManager(String name, T data, ManagerFactory<? extends OutputStreamManager, T> factory) {
/*  65 */     return AbstractManager.<OutputStreamManager, T>getManager(name, (ManagerFactory)factory, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void releaseSub() {
/*  73 */     if (this.footer != null) {
/*  74 */       write(this.footer);
/*     */     }
/*  76 */     close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOpen() {
/*  84 */     return (getCount() > 0);
/*     */   }
/*     */   
/*     */   protected OutputStream getOutputStream() {
/*  88 */     return this.os;
/*     */   }
/*     */   
/*     */   protected void setOutputStream(OutputStream os) {
/*  92 */     if (this.header != null) {
/*     */       try {
/*  94 */         os.write(this.header, 0, this.header.length);
/*  95 */         this.os = os;
/*  96 */       } catch (IOException ioe) {
/*  97 */         LOGGER.error("Unable to write header", ioe);
/*     */       } 
/*     */     } else {
/* 100 */       this.os = os;
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
/*     */   protected synchronized void write(byte[] bytes, int offset, int length) {
/*     */     try {
/* 115 */       this.os.write(bytes, offset, length);
/* 116 */     } catch (IOException ex) {
/* 117 */       String msg = "Error writing to stream " + getName();
/* 118 */       throw new AppenderLoggingException(msg, ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void write(byte[] bytes) {
/* 129 */     write(bytes, 0, bytes.length);
/*     */   }
/*     */   
/*     */   protected synchronized void close() {
/* 133 */     OutputStream stream = this.os;
/* 134 */     if (stream == System.out || stream == System.err) {
/*     */       return;
/*     */     }
/*     */     try {
/* 138 */       stream.close();
/* 139 */     } catch (IOException ex) {
/* 140 */       LOGGER.error("Unable to close stream " + getName() + ". " + ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void flush() {
/*     */     try {
/* 149 */       this.os.flush();
/* 150 */     } catch (IOException ex) {
/* 151 */       String msg = "Error flushing stream " + getName();
/* 152 */       throw new AppenderLoggingException(msg, ex);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\OutputStreamManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */