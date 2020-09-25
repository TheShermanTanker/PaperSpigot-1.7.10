/*    */ package net.minecraft.util.com.google.common.io;
/*    */ 
/*    */ import java.io.Flushable;
/*    */ import java.io.IOException;
/*    */ import java.util.logging.Level;
/*    */ import java.util.logging.Logger;
/*    */ import net.minecraft.util.com.google.common.annotations.Beta;
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
/*    */ public final class Flushables
/*    */ {
/* 34 */   private static final Logger logger = Logger.getLogger(Flushables.class.getName());
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
/*    */   public static void flush(Flushable flushable, boolean swallowIOException) throws IOException {
/*    */     try {
/* 56 */       flushable.flush();
/* 57 */     } catch (IOException e) {
/* 58 */       if (swallowIOException) {
/* 59 */         logger.log(Level.WARNING, "IOException thrown while flushing Flushable.", e);
/*    */       } else {
/*    */         
/* 62 */         throw e;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void flushQuietly(Flushable flushable) {
/*    */     try {
/* 75 */       flush(flushable, true);
/* 76 */     } catch (IOException e) {
/* 77 */       logger.log(Level.SEVERE, "IOException should not have been thrown.", e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\io\Flushables.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */