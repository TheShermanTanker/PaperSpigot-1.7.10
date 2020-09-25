/*    */ package org.fusesource.jansi;
/*    */ 
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.IOException;
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
/*    */ public class AnsiString
/*    */   implements CharSequence
/*    */ {
/*    */   private final CharSequence encoded;
/*    */   private final CharSequence plain;
/*    */   
/*    */   public AnsiString(CharSequence str) {
/* 38 */     assert str != null;
/* 39 */     this.encoded = str;
/* 40 */     this.plain = chew(str);
/*    */   }
/*    */   
/*    */   private CharSequence chew(CharSequence str) {
/* 44 */     assert str != null;
/*    */     
/* 46 */     ByteArrayOutputStream buff = new ByteArrayOutputStream();
/* 47 */     AnsiOutputStream out = new AnsiOutputStream(buff);
/*    */     
/*    */     try {
/* 50 */       out.write(str.toString().getBytes());
/* 51 */       out.flush();
/* 52 */       out.close();
/*    */     }
/* 54 */     catch (IOException e) {
/* 55 */       throw new RuntimeException(e);
/*    */     } 
/*    */     
/* 58 */     return new String(buff.toByteArray());
/*    */   }
/*    */   
/*    */   public CharSequence getEncoded() {
/* 62 */     return this.encoded;
/*    */   }
/*    */   
/*    */   public CharSequence getPlain() {
/* 66 */     return this.plain;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public char charAt(int index) {
/* 72 */     return getEncoded().charAt(index);
/*    */   }
/*    */   
/*    */   public CharSequence subSequence(int start, int end) {
/* 76 */     return getEncoded().subSequence(start, end);
/*    */   }
/*    */   
/*    */   public int length() {
/* 80 */     return getPlain().length();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object obj) {
/* 85 */     return getEncoded().equals(obj);
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 90 */     return getEncoded().hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 95 */     return getEncoded().toString();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\fusesource\jansi\AnsiString.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */