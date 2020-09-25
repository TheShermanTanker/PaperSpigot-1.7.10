/*     */ package com.avaje.ebeaninternal.server.type;
/*     */ 
/*     */ import com.avaje.ebean.text.TextException;
/*     */ import com.avaje.ebeaninternal.server.text.json.WriteJsonBuffer;
/*     */ import java.io.IOException;
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
/*     */ public class EscapeJson
/*     */ {
/*     */   public static String escapeQuote(String value) {
/*  33 */     if (value == null) {
/*  34 */       return "null";
/*     */     }
/*     */     
/*  37 */     StringBuilder sb = new StringBuilder(value.length() + 2);
/*  38 */     sb.append("\"");
/*  39 */     escapeAppend(value, sb);
/*  40 */     sb.append("\"");
/*  41 */     return sb.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String escape(String s) {
/*  49 */     if (s == null) {
/*  50 */       return null;
/*     */     }
/*     */     
/*  53 */     StringBuilder sb = new StringBuilder();
/*  54 */     escapeAppend(s, sb);
/*  55 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void escape(String value, WriteJsonBuffer sb) {
/*  60 */     if (value == null) {
/*  61 */       sb.append("null");
/*     */     } else {
/*  63 */       escapeAppend(value, (Appendable)sb);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static void escapeQuote(String value, WriteJsonBuffer sb) {
/*  68 */     if (value == null) {
/*  69 */       sb.append("null");
/*     */     } else {
/*  71 */       sb.append("\"");
/*  72 */       escapeAppend(value, (Appendable)sb);
/*  73 */       sb.append("\"");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void escapeAppend(String s, Appendable sb) {
/*     */     try {
/*  84 */       for (int i = 0; i < s.length(); i++) {
/*  85 */         char ch = s.charAt(i);
/*  86 */         switch (ch) {
/*     */           case '"':
/*  88 */             sb.append("\\\"");
/*     */             break;
/*     */           case '\\':
/*  91 */             sb.append("\\\\");
/*     */             break;
/*     */           case '\b':
/*  94 */             sb.append("\\b");
/*     */             break;
/*     */           case '\f':
/*  97 */             sb.append("\\f");
/*     */             break;
/*     */           case '\n':
/* 100 */             sb.append("\\n");
/*     */             break;
/*     */           case '\r':
/* 103 */             sb.append("\\r");
/*     */             break;
/*     */           case '\t':
/* 106 */             sb.append("\\t");
/*     */             break;
/*     */           case '/':
/* 109 */             sb.append("\\/");
/*     */             break;
/*     */           default:
/* 112 */             if ((ch >= '\000' && ch <= '\037') || (ch >= '' && ch <= '') || (ch >= ' ' && ch <= '⃿')) {
/*     */ 
/*     */               
/* 115 */               String hs = Integer.toHexString(ch);
/* 116 */               sb.append("\\u");
/* 117 */               for (int j = 0; j < 4 - hs.length(); j++) {
/* 118 */                 sb.append('0');
/*     */               }
/* 120 */               sb.append(hs.toUpperCase()); break;
/*     */             } 
/* 122 */             sb.append(ch);
/*     */             break;
/*     */         } 
/*     */       } 
/* 126 */     } catch (IOException e) {
/* 127 */       throw new TextException(e);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\EscapeJson.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */