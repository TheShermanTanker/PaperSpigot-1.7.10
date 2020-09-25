/*     */ package org.apache.logging.log4j.core.pattern;
/*     */ 
/*     */ import java.io.PrintWriter;
/*     */ import java.io.StringWriter;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.helpers.Constants;
/*     */ import org.apache.logging.log4j.core.impl.ThrowableFormatOptions;
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
/*     */ @Plugin(name = "ThrowablePatternConverter", category = "Converter")
/*     */ @ConverterKeys({"ex", "throwable", "exception"})
/*     */ public class ThrowablePatternConverter
/*     */   extends LogEventPatternConverter
/*     */ {
/*     */   private String rawOption;
/*     */   protected final ThrowableFormatOptions options;
/*     */   
/*     */   protected ThrowablePatternConverter(String name, String style, String[] options) {
/*  51 */     super(name, style);
/*  52 */     this.options = ThrowableFormatOptions.newInstance(options);
/*  53 */     if (options != null && options.length > 0) {
/*  54 */       this.rawOption = options[0];
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
/*     */   public static ThrowablePatternConverter newInstance(String[] options) {
/*  66 */     return new ThrowablePatternConverter("Throwable", "throwable", options);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void format(LogEvent event, StringBuilder buffer) {
/*  74 */     Throwable t = event.getThrown();
/*     */     
/*  76 */     if (isSubShortOption()) {
/*  77 */       formatSubShortOption(t, buffer);
/*     */     }
/*  79 */     else if (t != null && this.options.anyLines()) {
/*  80 */       formatOption(t, buffer);
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isSubShortOption() {
/*  85 */     return ("short.message".equalsIgnoreCase(this.rawOption) || "short.localizedMessage".equalsIgnoreCase(this.rawOption) || "short.fileName".equalsIgnoreCase(this.rawOption) || "short.lineNumber".equalsIgnoreCase(this.rawOption) || "short.methodName".equalsIgnoreCase(this.rawOption) || "short.className".equalsIgnoreCase(this.rawOption));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void formatSubShortOption(Throwable t, StringBuilder buffer) {
/*  95 */     StackTraceElement throwingMethod = null;
/*     */ 
/*     */     
/*  98 */     if (t != null) {
/*  99 */       StackTraceElement[] trace = t.getStackTrace();
/* 100 */       if (trace != null && trace.length > 0) {
/* 101 */         throwingMethod = trace[0];
/*     */       }
/*     */     } 
/*     */     
/* 105 */     if (t != null && throwingMethod != null) {
/* 106 */       String toAppend = "";
/*     */       
/* 108 */       if ("short.className".equalsIgnoreCase(this.rawOption)) {
/* 109 */         toAppend = throwingMethod.getClassName();
/*     */       }
/* 111 */       else if ("short.methodName".equalsIgnoreCase(this.rawOption)) {
/* 112 */         toAppend = throwingMethod.getMethodName();
/*     */       }
/* 114 */       else if ("short.lineNumber".equalsIgnoreCase(this.rawOption)) {
/* 115 */         toAppend = String.valueOf(throwingMethod.getLineNumber());
/*     */       }
/* 117 */       else if ("short.message".equalsIgnoreCase(this.rawOption)) {
/* 118 */         toAppend = t.getMessage();
/*     */       }
/* 120 */       else if ("short.localizedMessage".equalsIgnoreCase(this.rawOption)) {
/* 121 */         toAppend = t.getLocalizedMessage();
/*     */       }
/* 123 */       else if ("short.fileName".equalsIgnoreCase(this.rawOption)) {
/* 124 */         toAppend = throwingMethod.getFileName();
/*     */       } 
/*     */       
/* 127 */       int len = buffer.length();
/* 128 */       if (len > 0 && !Character.isWhitespace(buffer.charAt(len - 1))) {
/* 129 */         buffer.append(" ");
/*     */       }
/* 131 */       buffer.append(toAppend);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void formatOption(Throwable throwable, StringBuilder buffer) {
/* 136 */     StringWriter w = new StringWriter();
/*     */     
/* 138 */     throwable.printStackTrace(new PrintWriter(w));
/* 139 */     int len = buffer.length();
/* 140 */     if (len > 0 && !Character.isWhitespace(buffer.charAt(len - 1))) {
/* 141 */       buffer.append(' ');
/*     */     }
/* 143 */     if (!this.options.allLines() || !Constants.LINE_SEP.equals(this.options.getSeparator())) {
/* 144 */       StringBuilder sb = new StringBuilder();
/* 145 */       String[] array = w.toString().split(Constants.LINE_SEP);
/* 146 */       int limit = this.options.minLines(array.length) - 1;
/* 147 */       for (int i = 0; i <= limit; i++) {
/* 148 */         sb.append(array[i]);
/* 149 */         if (i < limit) {
/* 150 */           sb.append(this.options.getSeparator());
/*     */         }
/*     */       } 
/* 153 */       buffer.append(sb.toString());
/*     */     } else {
/*     */       
/* 156 */       buffer.append(w.toString());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean handlesThrowable() {
/* 167 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\pattern\ThrowablePatternConverter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */