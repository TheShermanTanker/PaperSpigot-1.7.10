/*     */ package com.google.common.io;
/*     */ 
/*     */ import com.google.common.annotations.Beta;
/*     */ import java.io.Closeable;
/*     */ import java.io.IOException;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import javax.annotation.Nullable;
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
/*     */ @Beta
/*     */ public final class Closeables
/*     */ {
/*  36 */   private static final Logger logger = Logger.getLogger(Closeables.class.getName());
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
/*     */ 
/*     */ 
/*     */   
/*     */   public static void close(@Nullable Closeable closeable, boolean swallowIOException) throws IOException {
/*  75 */     if (closeable == null) {
/*     */       return;
/*     */     }
/*     */     try {
/*  79 */       closeable.close();
/*  80 */     } catch (IOException e) {
/*  81 */       if (swallowIOException) {
/*  82 */         logger.log(Level.WARNING, "IOException thrown while closing Closeable.", e);
/*     */       } else {
/*     */         
/*  85 */         throw e;
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
/*     */   public static void closeQuietly(@Nullable Closeable closeable) {
/*     */     try {
/*  98 */       close(closeable, true);
/*  99 */     } catch (IOException e) {
/* 100 */       logger.log(Level.SEVERE, "IOException should not have been thrown.", e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\io\Closeables.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */