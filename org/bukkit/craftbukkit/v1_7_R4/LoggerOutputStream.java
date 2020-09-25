/*    */ package org.bukkit.craftbukkit.v1_7_R4;
/*    */ 
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.IOException;
/*    */ import org.apache.logging.log4j.Level;
/*    */ import org.apache.logging.log4j.Logger;
/*    */ 
/*    */ public class LoggerOutputStream extends ByteArrayOutputStream {
/*  9 */   private final String separator = System.getProperty("line.separator");
/*    */   
/*    */   private final Logger logger;
/*    */   private final Level level;
/*    */   
/*    */   public LoggerOutputStream(Logger logger, Level level) {
/* 15 */     this.logger = logger;
/* 16 */     this.level = level;
/*    */   }
/*    */ 
/*    */   
/*    */   public void flush() throws IOException {
/* 21 */     synchronized (this) {
/* 22 */       super.flush();
/* 23 */       String record = toString();
/* 24 */       reset();
/*    */       
/* 26 */       if (record.length() > 0 && !record.equals(this.separator))
/* 27 */         this.logger.log(this.level, record); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\LoggerOutputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */