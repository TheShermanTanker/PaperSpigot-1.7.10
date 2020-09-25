/*    */ package com.avaje.ebeaninternal.server.text.json;
/*    */ 
/*    */ import com.avaje.ebean.text.TextException;
/*    */ import java.io.IOException;
/*    */ import java.io.Writer;
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
/*    */ public class WriteJsonBufferWriter
/*    */   implements WriteJsonBuffer
/*    */ {
/*    */   private final Writer buffer;
/*    */   
/*    */   public WriteJsonBufferWriter(Writer buffer) {
/* 32 */     this.buffer = buffer;
/*    */   }
/*    */   
/*    */   public WriteJsonBufferWriter append(String content) {
/*    */     try {
/* 37 */       this.buffer.write(content);
/* 38 */       return this;
/* 39 */     } catch (IOException e) {
/* 40 */       throw new TextException(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   public WriteJsonBufferWriter append(CharSequence csq) throws IOException {
/* 45 */     return append(csq, 0, csq.length());
/*    */   }
/*    */   
/*    */   public WriteJsonBufferWriter append(CharSequence csq, int start, int end) throws IOException {
/* 49 */     for (int i = start; i < end; i++) {
/* 50 */       this.buffer.append(csq.charAt(i));
/*    */     }
/* 52 */     return this;
/*    */   }
/*    */   
/*    */   public WriteJsonBufferWriter append(char c) throws IOException {
/*    */     try {
/* 57 */       this.buffer.write(c);
/* 58 */       return this;
/* 59 */     } catch (IOException e) {
/* 60 */       throw new TextException(e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\text\json\WriteJsonBufferWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */