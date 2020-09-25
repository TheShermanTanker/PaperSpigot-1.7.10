/*    */ package com.avaje.ebeaninternal.server.text.json;
/*    */ 
/*    */ import com.avaje.ebean.text.TextException;
/*    */ import java.io.BufferedReader;
/*    */ import java.io.IOException;
/*    */ import java.io.Reader;
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
/*    */ public class ReadJsonSourceReader
/*    */   implements ReadJsonSource
/*    */ {
/*    */   private final Reader reader;
/*    */   private char[] localBuffer;
/*    */   private int totalPos;
/*    */   private int localPos;
/*    */   private int localPosEnd;
/*    */   
/*    */   public ReadJsonSourceReader(Reader reader, int localBufferSize, int bufferSize) {
/* 39 */     this.reader = new BufferedReader(reader, bufferSize);
/* 40 */     this.localBuffer = new char[localBufferSize];
/*    */   }
/*    */   
/*    */   public String toString() {
/* 44 */     return String.valueOf(this.localBuffer);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getErrorHelp() {
/* 50 */     int prev = this.localPos - 30;
/* 51 */     if (prev < 0) {
/* 52 */       prev = 0;
/*    */     }
/* 54 */     String c = new String(this.localBuffer, prev, this.localPos - prev);
/* 55 */     return "pos:" + pos() + " preceding:" + c;
/*    */   }
/*    */   
/*    */   public int pos() {
/* 59 */     return this.totalPos + this.localPos;
/*    */   }
/*    */ 
/*    */   
/*    */   public void ignoreWhiteSpace() {
/*    */     while (true) {
/* 65 */       char c = nextChar("EOF ignoring whitespace");
/* 66 */       if (!Character.isWhitespace(c)) {
/* 67 */         this.localPos--;
/*    */         return;
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public void back() {
/* 74 */     this.localPos--;
/*    */   }
/*    */   
/*    */   public char nextChar(String eofMsg) {
/* 78 */     if (this.localPos >= this.localPosEnd && 
/* 79 */       !loadLocalBuffer()) {
/* 80 */       throw new TextException(eofMsg + " at pos:" + (this.totalPos + this.localPos));
/*    */     }
/*    */     
/* 83 */     return this.localBuffer[this.localPos++];
/*    */   }
/*    */   
/*    */   private boolean loadLocalBuffer() {
/*    */     try {
/* 88 */       this.localPosEnd = this.reader.read(this.localBuffer);
/* 89 */       if (this.localPosEnd > 0) {
/* 90 */         this.totalPos += this.localPos;
/* 91 */         this.localPos = 0;
/* 92 */         return true;
/*    */       } 
/* 94 */       this.localBuffer = null;
/* 95 */       return false;
/*    */     
/*    */     }
/* 98 */     catch (IOException e) {
/* 99 */       throw new TextException(e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\text\json\ReadJsonSourceReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */