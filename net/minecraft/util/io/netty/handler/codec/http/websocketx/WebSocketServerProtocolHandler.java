/*     */ package net.minecraft.util.io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.util.io.netty.buffer.Unpooled;
/*     */ import net.minecraft.util.io.netty.channel.ChannelFutureListener;
/*     */ import net.minecraft.util.io.netty.channel.ChannelHandler;
/*     */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*     */ import net.minecraft.util.io.netty.channel.ChannelInboundHandlerAdapter;
/*     */ import net.minecraft.util.io.netty.channel.ChannelPipeline;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.DefaultFullHttpResponse;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.HttpResponseStatus;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.HttpVersion;
/*     */ import net.minecraft.util.io.netty.util.AttributeKey;
/*     */ import net.minecraft.util.io.netty.util.concurrent.GenericFutureListener;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WebSocketServerProtocolHandler
/*     */   extends WebSocketProtocolHandler
/*     */ {
/*     */   public enum ServerHandshakeStateEvent
/*     */   {
/*  60 */     HANDSHAKE_COMPLETE;
/*     */   }
/*     */   
/*  63 */   private static final AttributeKey<WebSocketServerHandshaker> HANDSHAKER_ATTR_KEY = new AttributeKey(WebSocketServerHandshaker.class.getName());
/*     */   
/*     */   private final String websocketPath;
/*     */   
/*     */   private final String subprotocols;
/*     */   private final boolean allowExtensions;
/*     */   
/*     */   public WebSocketServerProtocolHandler(String websocketPath) {
/*  71 */     this(websocketPath, null, false);
/*     */   }
/*     */   
/*     */   public WebSocketServerProtocolHandler(String websocketPath, String subprotocols) {
/*  75 */     this(websocketPath, subprotocols, false);
/*     */   }
/*     */   
/*     */   public WebSocketServerProtocolHandler(String websocketPath, String subprotocols, boolean allowExtensions) {
/*  79 */     this.websocketPath = websocketPath;
/*  80 */     this.subprotocols = subprotocols;
/*  81 */     this.allowExtensions = allowExtensions;
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext ctx) {
/*  86 */     ChannelPipeline cp = ctx.pipeline();
/*  87 */     if (cp.get(WebSocketServerProtocolHandshakeHandler.class) == null)
/*     */     {
/*  89 */       ctx.pipeline().addBefore(ctx.name(), WebSocketServerProtocolHandshakeHandler.class.getName(), (ChannelHandler)new WebSocketServerProtocolHandshakeHandler(this.websocketPath, this.subprotocols, this.allowExtensions));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void decode(ChannelHandlerContext ctx, WebSocketFrame frame, List<Object> out) throws Exception {
/*  96 */     if (frame instanceof CloseWebSocketFrame) {
/*  97 */       WebSocketServerHandshaker handshaker = getHandshaker(ctx);
/*  98 */       frame.retain();
/*  99 */       handshaker.close(ctx.channel(), (CloseWebSocketFrame)frame);
/*     */       return;
/*     */     } 
/* 102 */     super.decode(ctx, frame, out);
/*     */   }
/*     */ 
/*     */   
/*     */   public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
/* 107 */     if (cause instanceof WebSocketHandshakeException) {
/* 108 */       DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST, Unpooled.wrappedBuffer(cause.getMessage().getBytes()));
/*     */       
/* 110 */       ctx.channel().writeAndFlush(defaultFullHttpResponse).addListener((GenericFutureListener)ChannelFutureListener.CLOSE);
/*     */     } else {
/* 112 */       ctx.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   static WebSocketServerHandshaker getHandshaker(ChannelHandlerContext ctx) {
/* 117 */     return (WebSocketServerHandshaker)ctx.attr(HANDSHAKER_ATTR_KEY).get();
/*     */   }
/*     */   
/*     */   static void setHandshaker(ChannelHandlerContext ctx, WebSocketServerHandshaker handshaker) {
/* 121 */     ctx.attr(HANDSHAKER_ATTR_KEY).set(handshaker);
/*     */   }
/*     */   
/*     */   static ChannelHandler forbiddenHttpRequestResponder() {
/* 125 */     return (ChannelHandler)new ChannelInboundHandlerAdapter()
/*     */       {
/*     */         public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
/* 128 */           if (msg instanceof net.minecraft.util.io.netty.handler.codec.http.FullHttpRequest) {
/* 129 */             DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FORBIDDEN);
/*     */             
/* 131 */             ctx.channel().writeAndFlush(defaultFullHttpResponse);
/*     */           } else {
/* 133 */             ctx.fireChannelRead(msg);
/*     */           } 
/*     */         }
/*     */       };
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\http\websocketx\WebSocketServerProtocolHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */