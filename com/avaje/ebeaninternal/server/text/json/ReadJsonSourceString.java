/*    */ package com.avaje.ebeaninternal.server.text.json;
/*    */ 
/*    */ import com.avaje.ebean.text.TextException;
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
/*    */ public class ReadJsonSourceString
/*    */   implements ReadJsonSource
/*    */ {
/*    */   private final String source;
/*    */   private final int sourceLength;
/*    */   private int pos;
/*    */   
/*    */   public ReadJsonSourceString(String source) {
/* 31 */     this.source = source;
/* 32 */     this.sourceLength = source.length();
/*    */   }
/*    */   
/*    */   public String getErrorHelp() {
/* 36 */     int prev = this.pos - 50;
/* 37 */     if (prev < 0) {
/* 38 */       prev = 0;
/*    */     }
/* 40 */     String c = this.source.substring(prev, this.pos);
/* 41 */     return "pos:" + this.pos + " precedingcontent:" + c;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 45 */     return this.source;
/*    */   }
/*    */   
/*    */   public int pos() {
/* 49 */     return this.pos;
/*    */   }
/*    */   
/*    */   public void back() {
/* 53 */     this.pos--;
/*    */   }
/*    */   
/*    */   public char nextChar(String eofMsg) {
/* 57 */     if (this.pos >= this.sourceLength) {
/* 58 */       throw new TextException(eofMsg + " at pos:" + this.pos);
/*    */     }
/* 60 */     return this.source.charAt(this.pos++);
/*    */   }
/*    */   
/*    */   public void ignoreWhiteSpace() {
/*    */     while (true) {
/* 65 */       char c = this.source.charAt(this.pos);
/* 66 */       if (Character.isWhitespace(c)) {
/* 67 */         this.pos++;
/*    */         continue;
/*    */       } 
/*    */       break;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\text\json\ReadJsonSourceString.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */