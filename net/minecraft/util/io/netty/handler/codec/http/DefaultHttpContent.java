/*    */ package net.minecraft.util.io.netty.handler.codec.http;
/*    */ 
/*    */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*    */ import net.minecraft.util.io.netty.buffer.ByteBufHolder;
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
/*    */ public class DefaultHttpContent
/*    */   extends DefaultHttpObject
/*    */   implements HttpContent
/*    */ {
/*    */   private final ByteBuf content;
/*    */   
/*    */   public DefaultHttpContent(ByteBuf content) {
/* 31 */     if (content == null) {
/* 32 */       throw new NullPointerException("content");
/*    */     }
/* 34 */     this.content = content;
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuf content() {
/* 39 */     return this.content;
/*    */   }
/*    */ 
/*    */   
/*    */   public HttpContent copy() {
/* 44 */     return new DefaultHttpContent(this.content.copy());
/*    */   }
/*    */ 
/*    */   
/*    */   public HttpContent duplicate() {
/* 49 */     return new DefaultHttpContent(this.content.duplicate());
/*    */   }
/*    */ 
/*    */   
/*    */   public int refCnt() {
/* 54 */     return this.content.refCnt();
/*    */   }
/*    */ 
/*    */   
/*    */   public HttpContent retain() {
/* 59 */     this.content.retain();
/* 60 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public HttpContent retain(int increment) {
/* 65 */     this.content.retain(increment);
/* 66 */     return this;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean release() {
/* 71 */     return this.content.release();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean release(int decrement) {
/* 76 */     return this.content.release(decrement);
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 81 */     return getClass().getSimpleName() + "(data: " + content() + ", getDecoderResult: " + getDecoderResult() + ')';
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\http\DefaultHttpContent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */