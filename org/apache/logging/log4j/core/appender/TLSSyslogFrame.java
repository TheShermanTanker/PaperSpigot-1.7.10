/*    */ package org.apache.logging.log4j.core.appender;
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
/*    */ public class TLSSyslogFrame
/*    */ {
/*    */   public static final char SPACE = ' ';
/*    */   private String message;
/*    */   private int messageLengthInBytes;
/*    */   
/*    */   public TLSSyslogFrame(String message) {
/* 29 */     setMessage(message);
/*    */   }
/*    */   
/*    */   public String getMessage() {
/* 33 */     return this.message;
/*    */   }
/*    */   
/*    */   public void setMessage(String message) {
/* 37 */     this.message = message;
/* 38 */     setLengthInBytes();
/*    */   }
/*    */   
/*    */   private void setLengthInBytes() {
/* 42 */     this.messageLengthInBytes = this.message.length();
/*    */   }
/*    */   
/*    */   public byte[] getBytes() {
/* 46 */     String frame = toString();
/* 47 */     return frame.getBytes();
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 52 */     String length = Integer.toString(this.messageLengthInBytes);
/* 53 */     return length + ' ' + this.message;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object frame) {
/* 58 */     return super.equals(frame);
/*    */   }
/*    */   
/*    */   public boolean equals(TLSSyslogFrame frame) {
/* 62 */     return (isLengthEquals(frame) && isMessageEquals(frame));
/*    */   }
/*    */   
/*    */   private boolean isLengthEquals(TLSSyslogFrame frame) {
/* 66 */     return (this.messageLengthInBytes == frame.messageLengthInBytes);
/*    */   }
/*    */   
/*    */   private boolean isMessageEquals(TLSSyslogFrame frame) {
/* 70 */     return this.message.equals(frame.message);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\appender\TLSSyslogFrame.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */