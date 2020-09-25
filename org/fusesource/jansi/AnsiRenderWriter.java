/*    */ package org.fusesource.jansi;
/*    */ 
/*    */ import java.io.OutputStream;
/*    */ import java.io.PrintWriter;
/*    */ import java.io.Writer;
/*    */ import java.util.Locale;
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
/*    */ public class AnsiRenderWriter
/*    */   extends PrintWriter
/*    */ {
/*    */   public AnsiRenderWriter(OutputStream out) {
/* 38 */     super(out);
/*    */   }
/*    */   
/*    */   public AnsiRenderWriter(OutputStream out, boolean autoFlush) {
/* 42 */     super(out, autoFlush);
/*    */   }
/*    */   
/*    */   public AnsiRenderWriter(Writer out) {
/* 46 */     super(out);
/*    */   }
/*    */   
/*    */   public AnsiRenderWriter(Writer out, boolean autoFlush) {
/* 50 */     super(out, autoFlush);
/*    */   }
/*    */ 
/*    */   
/*    */   public void write(String s) {
/* 55 */     if (AnsiRenderer.test(s)) {
/* 56 */       super.write(AnsiRenderer.render(s));
/*    */     } else {
/*    */       
/* 59 */       super.write(s);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PrintWriter format(String format, Object... args) {
/* 69 */     print(String.format(format, args));
/* 70 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public PrintWriter format(Locale l, String format, Object... args) {
/* 75 */     print(String.format(l, format, args));
/* 76 */     return this;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\fusesource\jansi\AnsiRenderWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */