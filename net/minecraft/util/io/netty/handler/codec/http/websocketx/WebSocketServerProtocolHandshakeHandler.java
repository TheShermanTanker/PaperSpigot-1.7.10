/*    */ package net.minecraft.util.io.netty.handler.codec.http.websocketx;
/*    */ 
/*    */ import net.minecraft.util.io.netty.channel.ChannelFuture;
/*    */ import net.minecraft.util.io.netty.channel.ChannelFutureListener;
/*    */ import net.minecraft.util.io.netty.channel.ChannelHandler;
/*    */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*    */ import net.minecraft.util.io.netty.channel.ChannelInboundHandlerAdapter;
/*    */ import net.minecraft.util.io.netty.channel.ChannelPipeline;
/*    */ import net.minecraft.util.io.netty.handler.codec.http.DefaultFullHttpResponse;
/*    */ import net.minecraft.util.io.netty.handler.codec.http.FullHttpRequest;
/*    */ import net.minecraft.util.io.netty.handler.codec.http.HttpHeaders;
/*    */ import net.minecraft.util.io.netty.handler.codec.http.HttpMessage;
/*    */ import net.minecraft.util.io.netty.handler.codec.http.HttpMethod;
/*    */ import net.minecraft.util.io.netty.handler.codec.http.HttpRequest;
/*    */ import net.minecraft.util.io.netty.handler.codec.http.HttpResponse;
/*    */ import net.minecraft.util.io.netty.handler.codec.http.HttpResponseStatus;
/*    */ import net.minecraft.util.io.netty.handler.codec.http.HttpVersion;
/*    */ import net.minecraft.util.io.netty.handler.ssl.SslHandler;
/*    */ import net.minecraft.util.io.netty.util.concurrent.Future;
/*    */ import net.minecraft.util.io.netty.util.concurrent.GenericFutureListener;
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
/*    */ class WebSocketServerProtocolHandshakeHandler
/*    */   extends ChannelInboundHandlerAdapter
/*    */ {
/*    */   private final String websocketPath;
/*    */   private final String subprotocols;
/*    */   private final boolean allowExtensions;
/*    */   
/*    */   public WebSocketServerProtocolHandshakeHandler(String websocketPath, String subprotocols, boolean allowExtensions) {
/* 47 */     this.websocketPath = websocketPath;
/* 48 */     this.subprotocols = subprotocols;
/* 49 */     this.allowExtensions = allowExtensions;
/*    */   }
/*    */ 
/*    */   
/*    */   public void channelRead(final ChannelHandlerContext ctx, Object msg) throws Exception {
/* 54 */     FullHttpRequest req = (FullHttpRequest)msg;
/* 55 */     if (req.getMethod() != HttpMethod.GET) {
/* 56 */       sendHttpResponse(ctx, (HttpRequest)req, (HttpResponse)new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FORBIDDEN));
/*    */       
/*    */       return;
/*    */     } 
/* 60 */     WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(getWebSocketLocation(ctx.pipeline(), (HttpRequest)req, this.websocketPath), this.subprotocols, this.allowExtensions);
/*    */     
/* 62 */     WebSocketServerHandshaker handshaker = wsFactory.newHandshaker((HttpRequest)req);
/* 63 */     if (handshaker == null) {
/* 64 */       WebSocketServerHandshakerFactory.sendUnsupportedWebSocketVersionResponse(ctx.channel());
/*    */     } else {
/* 66 */       ChannelFuture handshakeFuture = handshaker.handshake(ctx.channel(), req);
/* 67 */       handshakeFuture.addListener((GenericFutureListener)new ChannelFutureListener()
/*    */           {
/*    */             public void operationComplete(ChannelFuture future) throws Exception {
/* 70 */               if (!future.isSuccess()) {
/* 71 */                 ctx.fireExceptionCaught(future.cause());
/*    */               } else {
/* 73 */                 ctx.fireUserEventTriggered(WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE);
/*    */               } 
/*    */             }
/*    */           });
/*    */       
/* 78 */       WebSocketServerProtocolHandler.setHandshaker(ctx, handshaker);
/* 79 */       ctx.pipeline().replace((ChannelHandler)this, "WS403Responder", WebSocketServerProtocolHandler.forbiddenHttpRequestResponder());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private static void sendHttpResponse(ChannelHandlerContext ctx, HttpRequest req, HttpResponse res) {
/* 85 */     ChannelFuture f = ctx.channel().writeAndFlush(res);
/* 86 */     if (!HttpHeaders.isKeepAlive((HttpMessage)req) || res.getStatus().code() != 200) {
/* 87 */       f.addListener((GenericFutureListener)ChannelFutureListener.CLOSE);
/*    */     }
/*    */   }
/*    */   
/*    */   private static String getWebSocketLocation(ChannelPipeline cp, HttpRequest req, String path) {
/* 92 */     String protocol = "ws";
/* 93 */     if (cp.get(SslHandler.class) != null)
/*    */     {
/* 95 */       protocol = "wss";
/*    */     }
/* 97 */     return protocol + "://" + req.headers().get("Host") + path;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\http\websocketx\WebSocketServerProtocolHandshakeHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */