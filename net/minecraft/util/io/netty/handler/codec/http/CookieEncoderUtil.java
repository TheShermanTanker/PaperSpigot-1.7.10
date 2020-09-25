/*    */ package net.minecraft.util.io.netty.handler.codec.http;
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
/*    */ final class CookieEncoderUtil
/*    */ {
/*    */   static String stripTrailingSeparator(StringBuilder buf) {
/* 22 */     if (buf.length() > 0) {
/* 23 */       buf.setLength(buf.length() - 2);
/*    */     }
/* 25 */     return buf.toString();
/*    */   }
/*    */   
/*    */   static void add(StringBuilder sb, String name, String val) {
/* 29 */     if (val == null) {
/* 30 */       addQuoted(sb, name, "");
/*    */       
/*    */       return;
/*    */     } 
/* 34 */     for (int i = 0; i < val.length(); i++) {
/* 35 */       char c = val.charAt(i);
/* 36 */       switch (c) { case '\t': case ' ': case '"': case '(': case ')': case ',': case '/': case ':': case ';': case '<': case '=': case '>': case '?': case '@': case '[':
/*    */         case '\\':
/*    */         case ']':
/*    */         case '{':
/*    */         case '}':
/* 41 */           addQuoted(sb, name, val);
/*    */           return; }
/*    */ 
/*    */     
/*    */     } 
/* 46 */     addUnquoted(sb, name, val);
/*    */   }
/*    */   
/*    */   static void addUnquoted(StringBuilder sb, String name, String val) {
/* 50 */     sb.append(name);
/* 51 */     sb.append('=');
/* 52 */     sb.append(val);
/* 53 */     sb.append(';');
/* 54 */     sb.append(' ');
/*    */   }
/*    */   
/*    */   static void addQuoted(StringBuilder sb, String name, String val) {
/* 58 */     if (val == null) {
/* 59 */       val = "";
/*    */     }
/*    */     
/* 62 */     sb.append(name);
/* 63 */     sb.append('=');
/* 64 */     sb.append('"');
/* 65 */     sb.append(val.replace("\\", "\\\\").replace("\"", "\\\""));
/* 66 */     sb.append('"');
/* 67 */     sb.append(';');
/* 68 */     sb.append(' ');
/*    */   }
/*    */   
/*    */   static void add(StringBuilder sb, String name, long val) {
/* 72 */     sb.append(name);
/* 73 */     sb.append('=');
/* 74 */     sb.append(val);
/* 75 */     sb.append(';');
/* 76 */     sb.append(' ');
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\http\CookieEncoderUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */