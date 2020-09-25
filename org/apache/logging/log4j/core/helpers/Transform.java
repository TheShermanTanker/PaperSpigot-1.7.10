/*     */ package org.apache.logging.log4j.core.helpers;
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
/*     */ public final class Transform
/*     */ {
/*     */   private static final String CDATA_START = "<![CDATA[";
/*     */   private static final String CDATA_END = "]]>";
/*     */   private static final String CDATA_PSEUDO_END = "]]&gt;";
/*     */   private static final String CDATA_EMBEDED_END = "]]>]]&gt;<![CDATA[";
/*  29 */   private static final int CDATA_END_LEN = "]]>".length();
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
/*     */   public static String escapeHtmlTags(String input) {
/*  47 */     if (Strings.isEmpty(input) || (input.indexOf('"') == -1 && input.indexOf('&') == -1 && input.indexOf('<') == -1 && input.indexOf('>') == -1))
/*     */     {
/*     */ 
/*     */ 
/*     */       
/*  52 */       return input;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  58 */     StringBuilder buf = new StringBuilder(input.length() + 6);
/*  59 */     char ch = ' ';
/*     */     
/*  61 */     int len = input.length();
/*  62 */     for (int i = 0; i < len; i++) {
/*  63 */       ch = input.charAt(i);
/*  64 */       if (ch > '>') {
/*  65 */         buf.append(ch);
/*  66 */       } else if (ch == '<') {
/*  67 */         buf.append("&lt;");
/*  68 */       } else if (ch == '>') {
/*  69 */         buf.append("&gt;");
/*  70 */       } else if (ch == '&') {
/*  71 */         buf.append("&amp;");
/*  72 */       } else if (ch == '"') {
/*  73 */         buf.append("&quot;");
/*     */       } else {
/*  75 */         buf.append(ch);
/*     */       } 
/*     */     } 
/*  78 */     return buf.toString();
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
/*     */   public static void appendEscapingCDATA(StringBuilder buf, String str) {
/*  91 */     if (str != null) {
/*  92 */       int end = str.indexOf("]]>");
/*  93 */       if (end < 0) {
/*  94 */         buf.append(str);
/*     */       } else {
/*  96 */         int start = 0;
/*  97 */         while (end > -1) {
/*  98 */           buf.append(str.substring(start, end));
/*  99 */           buf.append("]]>]]&gt;<![CDATA[");
/* 100 */           start = end + CDATA_END_LEN;
/* 101 */           if (start < str.length()) {
/* 102 */             end = str.indexOf("]]>", start);
/*     */             continue;
/*     */           } 
/*     */           return;
/*     */         } 
/* 107 */         buf.append(str.substring(start));
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String escapeJsonControlCharacters(String input) {
/* 125 */     if (Strings.isEmpty(input) || (input.indexOf('"') == -1 && input.indexOf('\\') == -1 && input.indexOf('/') == -1 && input.indexOf('\b') == -1 && input.indexOf('\f') == -1 && input.indexOf('\n') == -1 && input.indexOf('\r') == -1 && input.indexOf('\t') == -1))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 134 */       return input;
/*     */     }
/*     */     
/* 137 */     StringBuilder buf = new StringBuilder(input.length() + 6);
/*     */     
/* 139 */     int len = input.length();
/* 140 */     for (int i = 0; i < len; i++) {
/* 141 */       char ch = input.charAt(i);
/* 142 */       String escBs = "\\\\";
/* 143 */       switch (ch) {
/*     */         case '"':
/* 145 */           buf.append("\\\\");
/* 146 */           buf.append(ch);
/*     */           break;
/*     */         case '\\':
/* 149 */           buf.append("\\\\");
/* 150 */           buf.append(ch);
/*     */           break;
/*     */         case '/':
/* 153 */           buf.append("\\\\");
/* 154 */           buf.append(ch);
/*     */           break;
/*     */         case '\b':
/* 157 */           buf.append("\\\\");
/* 158 */           buf.append('b');
/*     */           break;
/*     */         case '\f':
/* 161 */           buf.append("\\\\");
/* 162 */           buf.append('f');
/*     */           break;
/*     */         case '\n':
/* 165 */           buf.append("\\\\");
/* 166 */           buf.append('n');
/*     */           break;
/*     */         case '\r':
/* 169 */           buf.append("\\\\");
/* 170 */           buf.append('r');
/*     */           break;
/*     */         case '\t':
/* 173 */           buf.append("\\\\");
/* 174 */           buf.append('t');
/*     */           break;
/*     */         default:
/* 177 */           buf.append(ch); break;
/*     */       } 
/*     */     } 
/* 180 */     return buf.toString();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\helpers\Transform.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */