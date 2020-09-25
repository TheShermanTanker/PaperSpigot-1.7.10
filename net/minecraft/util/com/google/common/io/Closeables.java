/*    */ package net.minecraft.util.com.google.common.io;
/*    */ 
/*    */ import java.io.Closeable;
/*    */ import java.io.IOException;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
/*    */ import javax.annotation.Nullable;
/*    */ import net.minecraft.util.com.google.common.annotations.Beta;
/*    */ import net.minecraft.util.com.google.common.annotations.VisibleForTesting;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Beta
/*    */ public final class Closeables
/*    */ {
/*    */   @VisibleForTesting
/* 37 */   static final Logger logger = Logger.getLogger(Closeables.class.getName());
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void close(@Nullable Closeable closeable, boolean swallowIOException) throws IOException {
/* 73 */     if (closeable == null) {
/*    */       return;
/*    */     }
/*    */     try {
/* 77 */       closeable.close();
/* 78 */     } catch (IOException e) {
/* 79 */       if (swallowIOException) {
/* 80 */         logger.log(Level.WARNING, "IOException thrown while closing Closeable.", e);
/*    */       } else {
/*    */         
/* 83 */         throw e;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\io\Closeables.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */