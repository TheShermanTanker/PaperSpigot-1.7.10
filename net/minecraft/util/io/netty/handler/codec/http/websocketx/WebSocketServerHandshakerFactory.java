/*     */ package net.minecraft.util.io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import net.minecraft.util.io.netty.channel.Channel;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.DefaultHttpResponse;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.HttpRequest;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.HttpResponseStatus;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.HttpVersion;
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
/*     */ public class WebSocketServerHandshakerFactory
/*     */ {
/*     */   private final String webSocketURL;
/*     */   private final String subprotocols;
/*     */   private final boolean allowExtensions;
/*     */   private final int maxFramePayloadLength;
/*     */   
/*     */   public WebSocketServerHandshakerFactory(String webSocketURL, String subprotocols, boolean allowExtensions) {
/*  53 */     this(webSocketURL, subprotocols, allowExtensions, 65536);
/*     */   }
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
/*     */   public WebSocketServerHandshakerFactory(String webSocketURL, String subprotocols, boolean allowExtensions, int maxFramePayloadLength) {
/*  73 */     this.webSocketURL = webSocketURL;
/*  74 */     this.subprotocols = subprotocols;
/*  75 */     this.allowExtensions = allowExtensions;
/*  76 */     this.maxFramePayloadLength = maxFramePayloadLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WebSocketServerHandshaker newHandshaker(HttpRequest req) {
/*  87 */     String version = req.headers().get("Sec-WebSocket-Version");
/*  88 */     if (version != null) {
/*  89 */       if (version.equals(WebSocketVersion.V13.toHttpHeaderValue()))
/*     */       {
/*  91 */         return new WebSocketServerHandshaker13(this.webSocketURL, this.subprotocols, this.allowExtensions, this.maxFramePayloadLength);
/*     */       }
/*  93 */       if (version.equals(WebSocketVersion.V08.toHttpHeaderValue()))
/*     */       {
/*  95 */         return new WebSocketServerHandshaker08(this.webSocketURL, this.subprotocols, this.allowExtensions, this.maxFramePayloadLength);
/*     */       }
/*  97 */       if (version.equals(WebSocketVersion.V07.toHttpHeaderValue()))
/*     */       {
/*  99 */         return new WebSocketServerHandshaker07(this.webSocketURL, this.subprotocols, this.allowExtensions, this.maxFramePayloadLength);
/*     */       }
/*     */       
/* 102 */       return null;
/*     */     } 
/*     */ 
/*     */     
/* 106 */     return new WebSocketServerHandshaker00(this.webSocketURL, this.subprotocols, this.maxFramePayloadLength);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void sendUnsupportedWebSocketVersionResponse(Channel channel) {
/* 117 */     DefaultHttpResponse defaultHttpResponse = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.UPGRADE_REQUIRED);
/*     */ 
/*     */     
/* 120 */     defaultHttpResponse.headers().set("Sec-WebSocket-Version", WebSocketVersion.V13.toHttpHeaderValue());
/* 121 */     channel.write(defaultHttpResponse);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\http\websocketx\WebSocketServerHandshakerFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */