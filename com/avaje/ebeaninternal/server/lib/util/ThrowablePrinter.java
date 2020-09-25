/*     */ package com.avaje.ebeaninternal.server.lib.util;
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
/*     */ public class ThrowablePrinter
/*     */ {
/*     */   private static final String atString = "        at ";
/*  31 */   private String newLineChar = "\\r\\n";
/*     */   
/*  33 */   private int maxStackTraceLines = 3;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxStackTraceLines(int maxStackTraceLines) {
/*  40 */     this.maxStackTraceLines = maxStackTraceLines;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNewLineChar(String newLineChar) {
/*  49 */     this.newLineChar = newLineChar;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String print(Throwable e) {
/*  59 */     StringBuffer sb = new StringBuffer();
/*  60 */     printThrowable(sb, e, false);
/*     */     
/*  62 */     String line = sb.toString();
/*  63 */     line = StringHelper.replaceString(line, "\r", "\\r");
/*  64 */     line = StringHelper.replaceString(line, "\n", "\\n");
/*     */     
/*  66 */     return line;
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
/*     */   protected void printThrowable(StringBuffer sb, Throwable e, boolean isCause) {
/*  78 */     if (e != null) {
/*  79 */       if (isCause) {
/*  80 */         sb.append("Caused by: ");
/*     */       }
/*  82 */       sb.append(e.getClass().getName());
/*  83 */       sb.append(":");
/*  84 */       sb.append(e.getMessage()).append(this.newLineChar);
/*     */       
/*  86 */       StackTraceElement[] ste = e.getStackTrace();
/*  87 */       int outputStackLines = ste.length;
/*  88 */       int notShownCount = 0;
/*  89 */       if (ste.length > this.maxStackTraceLines) {
/*  90 */         outputStackLines = this.maxStackTraceLines;
/*  91 */         notShownCount = ste.length - outputStackLines;
/*     */       } 
/*  93 */       for (int i = 0; i < outputStackLines; i++) {
/*  94 */         sb.append("        at ");
/*  95 */         sb.append(ste[i].toString()).append(this.newLineChar);
/*     */       } 
/*  97 */       if (notShownCount > 0) {
/*  98 */         sb.append("        ... ");
/*  99 */         sb.append(notShownCount);
/* 100 */         sb.append(" more").append(this.newLineChar);
/*     */       } 
/* 102 */       Throwable cause = e.getCause();
/* 103 */       if (cause != null)
/* 104 */         printThrowable(sb, cause, true); 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\li\\util\ThrowablePrinter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */