/*    */ package com.avaje.ebeaninternal.server.text.json;
/*    */ 
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
/*    */ public class WriteJsonBufferString
/*    */   implements WriteJsonBuffer
/*    */ {
/* 29 */   private final StringBuilder buffer = new StringBuilder(256);
/*    */ 
/*    */   
/*    */   public WriteJsonBufferString append(CharSequence csq) throws IOException {
/* 33 */     this.buffer.append(csq);
/* 34 */     return this;
/*    */   }
/*    */   
/*    */   public WriteJsonBufferString append(CharSequence csq, int start, int end) throws IOException {
/* 38 */     this.buffer.append(csq, start, end);
/* 39 */     return this;
/*    */   }
/*    */   
/*    */   public WriteJsonBufferString append(char c) throws IOException {
/* 43 */     this.buffer.append(c);
/* 44 */     return this;
/*    */   }
/*    */   
/*    */   public WriteJsonBufferString append(String content) {
/* 48 */     this.buffer.append(content);
/* 49 */     return this;
/*    */   }
/*    */   
/*    */   public String getBufferOutput() {
/* 53 */     return this.buffer.toString();
/*    */   }
/*    */   
/*    */   public String toString() {
/* 57 */     return this.buffer.toString();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\text\json\WriteJsonBufferString.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */