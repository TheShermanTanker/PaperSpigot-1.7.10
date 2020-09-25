/*    */ package net.minecraft.util.io.netty.handler.codec.http;
/*    */ 
/*    */ import net.minecraft.util.io.netty.handler.codec.DecoderResult;
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
/*    */ public class DefaultHttpObject
/*    */   implements HttpObject
/*    */ {
/* 22 */   private DecoderResult decoderResult = DecoderResult.SUCCESS;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public DecoderResult getDecoderResult() {
/* 30 */     return this.decoderResult;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setDecoderResult(DecoderResult decoderResult) {
/* 35 */     if (decoderResult == null) {
/* 36 */       throw new NullPointerException("decoderResult");
/*    */     }
/* 38 */     this.decoderResult = decoderResult;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\http\DefaultHttpObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */