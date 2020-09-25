/*    */ package net.minecraft.util.io.netty.handler.codec.http;
/*    */ 
/*    */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.util.io.netty.buffer.ByteBufHolder;
/*    */ import net.minecraft.util.io.netty.buffer.Unpooled;
/*    */ import net.minecraft.util.io.netty.handler.codec.DecoderResult;
/*    */ import net.minecraft.util.io.netty.util.ReferenceCounted;
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
/*    */ public interface LastHttpContent
/*    */   extends HttpContent
/*    */ {
/* 30 */   public static final LastHttpContent EMPTY_LAST_CONTENT = new LastHttpContent()
/*    */     {
/*    */       public ByteBuf content()
/*    */       {
/* 34 */         return Unpooled.EMPTY_BUFFER;
/*    */       }
/*    */ 
/*    */       
/*    */       public LastHttpContent copy() {
/* 39 */         return EMPTY_LAST_CONTENT;
/*    */       }
/*    */ 
/*    */       
/*    */       public LastHttpContent duplicate() {
/* 44 */         return this;
/*    */       }
/*    */ 
/*    */       
/*    */       public HttpHeaders trailingHeaders() {
/* 49 */         return HttpHeaders.EMPTY_HEADERS;
/*    */       }
/*    */ 
/*    */       
/*    */       public DecoderResult getDecoderResult() {
/* 54 */         return DecoderResult.SUCCESS;
/*    */       }
/*    */ 
/*    */       
/*    */       public void setDecoderResult(DecoderResult result) {
/* 59 */         throw new UnsupportedOperationException("read only");
/*    */       }
/*    */ 
/*    */       
/*    */       public int refCnt() {
/* 64 */         return 1;
/*    */       }
/*    */ 
/*    */       
/*    */       public LastHttpContent retain() {
/* 69 */         return this;
/*    */       }
/*    */ 
/*    */       
/*    */       public LastHttpContent retain(int increment) {
/* 74 */         return this;
/*    */       }
/*    */ 
/*    */       
/*    */       public boolean release() {
/* 79 */         return false;
/*    */       }
/*    */ 
/*    */       
/*    */       public boolean release(int decrement) {
/* 84 */         return false;
/*    */       }
/*    */     };
/*    */   
/*    */   HttpHeaders trailingHeaders();
/*    */   
/*    */   LastHttpContent copy();
/*    */   
/*    */   LastHttpContent retain(int paramInt);
/*    */   
/*    */   LastHttpContent retain();
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\http\LastHttpContent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */