/*    */ package org.apache.logging.log4j.core.helpers;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.LineNumberReader;
/*    */ import java.io.PrintWriter;
/*    */ import java.io.StringReader;
/*    */ import java.io.StringWriter;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
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
/*    */ public class Throwables
/*    */ {
/*    */   public static List<String> toStringList(Throwable throwable) {
/* 41 */     StringWriter sw = new StringWriter();
/* 42 */     PrintWriter pw = new PrintWriter(sw);
/*    */     try {
/* 44 */       throwable.printStackTrace(pw);
/* 45 */     } catch (RuntimeException ex) {}
/*    */ 
/*    */     
/* 48 */     pw.flush();
/* 49 */     LineNumberReader reader = new LineNumberReader(new StringReader(sw.toString()));
/* 50 */     ArrayList<String> lines = new ArrayList<String>();
/*    */     try {
/* 52 */       String line = reader.readLine();
/* 53 */       while (line != null) {
/* 54 */         lines.add(line);
/* 55 */         line = reader.readLine();
/*    */       } 
/* 57 */     } catch (IOException ex) {
/* 58 */       if (ex instanceof java.io.InterruptedIOException) {
/* 59 */         Thread.currentThread().interrupt();
/*    */       }
/* 61 */       lines.add(ex.toString());
/*    */     } 
/* 63 */     return lines;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\helpers\Throwables.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */