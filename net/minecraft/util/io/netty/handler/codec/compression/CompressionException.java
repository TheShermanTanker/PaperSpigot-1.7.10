/*    */ package net.minecraft.util.io.netty.handler.codec.compression;
/*    */ 
/*    */ import net.minecraft.util.io.netty.handler.codec.EncoderException;
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
/*    */ public class CompressionException
/*    */   extends EncoderException
/*    */ {
/*    */   private static final long serialVersionUID = 5603413481274811897L;
/*    */   
/*    */   public CompressionException() {}
/*    */   
/*    */   public CompressionException(String message, Throwable cause) {
/* 37 */     super(message, cause);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CompressionException(String message) {
/* 44 */     super(message);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CompressionException(Throwable cause) {
/* 51 */     super(cause);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\compression\CompressionException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */