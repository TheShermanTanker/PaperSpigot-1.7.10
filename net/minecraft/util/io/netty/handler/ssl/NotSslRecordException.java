/*    */ package net.minecraft.util.io.netty.handler.ssl;
/*    */ 
/*    */ import javax.net.ssl.SSLException;
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
/*    */ public class NotSslRecordException
/*    */   extends SSLException
/*    */ {
/*    */   private static final long serialVersionUID = -4316784434770656841L;
/*    */   
/*    */   public NotSslRecordException() {
/* 33 */     super("");
/*    */   }
/*    */   
/*    */   public NotSslRecordException(String message) {
/* 37 */     super(message);
/*    */   }
/*    */   
/*    */   public NotSslRecordException(Throwable cause) {
/* 41 */     super(cause);
/*    */   }
/*    */   
/*    */   public NotSslRecordException(String message, Throwable cause) {
/* 45 */     super(message, cause);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\ssl\NotSslRecordException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */