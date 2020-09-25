/*     */ package org.apache.logging.log4j.core.impl;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Scanner;
/*     */ import org.apache.logging.log4j.core.helpers.Constants;
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
/*     */ public final class ThrowableFormatOptions
/*     */ {
/*     */   private static final int DEFAULT_LINES = 2147483647;
/*  35 */   protected static final ThrowableFormatOptions DEFAULT = new ThrowableFormatOptions();
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String FULL = "full";
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String NONE = "none";
/*     */ 
/*     */ 
/*     */   
/*     */   private static final String SHORT = "short";
/*     */ 
/*     */ 
/*     */   
/*     */   private final int lines;
/*     */ 
/*     */ 
/*     */   
/*     */   private final String separator;
/*     */ 
/*     */   
/*     */   private final List<String> packages;
/*     */ 
/*     */   
/*     */   public static final String CLASS_NAME = "short.className";
/*     */ 
/*     */   
/*     */   public static final String METHOD_NAME = "short.methodName";
/*     */ 
/*     */   
/*     */   public static final String LINE_NUMBER = "short.lineNumber";
/*     */ 
/*     */   
/*     */   public static final String FILE_NAME = "short.fileName";
/*     */ 
/*     */   
/*     */   public static final String MESSAGE = "short.message";
/*     */ 
/*     */   
/*     */   public static final String LOCALIZED_MESSAGE = "short.localizedMessage";
/*     */ 
/*     */ 
/*     */   
/*     */   protected ThrowableFormatOptions(int lines, String separator, List<String> packages) {
/*  81 */     this.lines = lines;
/*  82 */     this.separator = (separator == null) ? Constants.LINE_SEP : separator;
/*  83 */     this.packages = packages;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ThrowableFormatOptions(List<String> packages) {
/*  91 */     this(2147483647, null, packages);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ThrowableFormatOptions() {
/*  98 */     this(2147483647, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getLines() {
/* 106 */     return this.lines;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSeparator() {
/* 114 */     return this.separator;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getPackages() {
/* 122 */     return this.packages;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean allLines() {
/* 130 */     return (this.lines == Integer.MAX_VALUE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean anyLines() {
/* 138 */     return (this.lines > 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int minLines(int maxLines) {
/* 147 */     return (this.lines > maxLines) ? maxLines : this.lines;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean hasPackages() {
/* 155 */     return (this.packages != null && !this.packages.isEmpty());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 163 */     StringBuilder s = new StringBuilder();
/* 164 */     s.append("{").append(allLines() ? "full" : ((this.lines == 2) ? "short" : (anyLines() ? String.valueOf(this.lines) : "none"))).append("}");
/* 165 */     s.append("{separator(").append(this.separator).append(")}");
/* 166 */     if (hasPackages()) {
/* 167 */       s.append("{filters(");
/* 168 */       for (String p : this.packages) {
/* 169 */         s.append(p).append(",");
/*     */       }
/* 171 */       s.deleteCharAt(s.length() - 1);
/* 172 */       s.append(")}");
/*     */     } 
/* 174 */     return s.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ThrowableFormatOptions newInstance(String[] options) {
/* 182 */     if (options == null || options.length == 0) {
/* 183 */       return DEFAULT;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 191 */     if (options.length == 1 && options[0] != null && options[0].length() > 0) {
/* 192 */       String[] opts = options[0].split(",", 2);
/* 193 */       String first = opts[0].trim();
/* 194 */       Scanner scanner = new Scanner(first);
/* 195 */       if (opts.length > 1 && (first.equalsIgnoreCase("full") || first.equalsIgnoreCase("short") || first.equalsIgnoreCase("none") || scanner.hasNextInt())) {
/* 196 */         options = new String[] { first, opts[1].trim() };
/*     */       }
/* 198 */       scanner.close();
/*     */     } 
/*     */     
/* 201 */     int lines = DEFAULT.lines;
/* 202 */     String separator = DEFAULT.separator;
/* 203 */     List<String> packages = DEFAULT.packages;
/* 204 */     for (String rawOption : options) {
/* 205 */       if (rawOption != null) {
/* 206 */         String option = rawOption.trim();
/* 207 */         if (!option.isEmpty())
/*     */         {
/* 209 */           if (option.startsWith("separator(") && option.endsWith(")")) {
/* 210 */             separator = option.substring("separator(".length(), option.length() - 1);
/* 211 */           } else if (option.startsWith("filters(") && option.endsWith(")")) {
/* 212 */             String filterStr = option.substring("filters(".length(), option.length() - 1);
/* 213 */             if (filterStr.length() > 0) {
/* 214 */               String[] array = filterStr.split(",");
/* 215 */               if (array.length > 0) {
/* 216 */                 packages = new ArrayList<String>(array.length);
/* 217 */                 for (String token : array) {
/* 218 */                   token = token.trim();
/* 219 */                   if (token.length() > 0) {
/* 220 */                     packages.add(token);
/*     */                   }
/*     */                 } 
/*     */               } 
/*     */             } 
/* 225 */           } else if (option.equalsIgnoreCase("none")) {
/* 226 */             lines = 0;
/* 227 */           } else if (option.equalsIgnoreCase("short") || option.equalsIgnoreCase("short.className") || option.equalsIgnoreCase("short.methodName") || option.equalsIgnoreCase("short.lineNumber") || option.equalsIgnoreCase("short.fileName") || option.equalsIgnoreCase("short.message") || option.equalsIgnoreCase("short.localizedMessage")) {
/*     */ 
/*     */ 
/*     */             
/* 231 */             lines = 2;
/* 232 */           } else if (!option.equalsIgnoreCase("full")) {
/* 233 */             lines = Integer.parseInt(option);
/*     */           }  } 
/*     */       } 
/*     */     } 
/* 237 */     return new ThrowableFormatOptions(lines, separator, packages);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\impl\ThrowableFormatOptions.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */