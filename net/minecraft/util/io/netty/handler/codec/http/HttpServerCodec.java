/*    */ package net.minecraft.util.io.netty.handler.codec.http;
/*    */ 
/*    */ import net.minecraft.util.io.netty.channel.ChannelInboundHandler;
/*    */ import net.minecraft.util.io.netty.channel.ChannelOutboundHandler;
/*    */ import net.minecraft.util.io.netty.channel.CombinedChannelDuplexHandler;
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
/*    */ 
/*    */ public final class HttpServerCodec
/*    */   extends CombinedChannelDuplexHandler<HttpRequestDecoder, HttpResponseEncoder>
/*    */ {
/*    */   public HttpServerCodec() {
/* 36 */     this(4096, 8192, 8192);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public HttpServerCodec(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize) {
/* 43 */     super((ChannelInboundHandler)new HttpRequestDecoder(maxInitialLineLength, maxHeaderSize, maxChunkSize), (ChannelOutboundHandler)new HttpResponseEncoder());
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\http\HttpServerCodec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */