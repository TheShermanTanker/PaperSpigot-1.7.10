/*     */ package net.minecraft.util.io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import net.minecraft.util.io.netty.handler.codec.http.DefaultFullHttpResponse;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.FullHttpRequest;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.FullHttpResponse;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.HttpHeaders;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.HttpResponseStatus;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.HttpVersion;
/*     */ import net.minecraft.util.io.netty.util.CharsetUtil;
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
/*     */ public class WebSocketServerHandshaker13
/*     */   extends WebSocketServerHandshaker
/*     */ {
/*     */   public static final String WEBSOCKET_13_ACCEPT_GUID = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
/*     */   private final boolean allowExtensions;
/*     */   
/*     */   public WebSocketServerHandshaker13(String webSocketURL, String subprotocols, boolean allowExtensions, int maxFramePayloadLength) {
/*  57 */     super(WebSocketVersion.V13, webSocketURL, subprotocols, maxFramePayloadLength);
/*  58 */     this.allowExtensions = allowExtensions;
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
/*     */   protected FullHttpResponse newHandshakeResponse(FullHttpRequest req, HttpHeaders headers) {
/*  97 */     DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.SWITCHING_PROTOCOLS);
/*  98 */     if (headers != null) {
/*  99 */       defaultFullHttpResponse.headers().add(headers);
/*     */     }
/*     */     
/* 102 */     String key = req.headers().get("Sec-WebSocket-Key");
/* 103 */     if (key == null) {
/* 104 */       throw new WebSocketHandshakeException("not a WebSocket request: missing key");
/*     */     }
/* 106 */     String acceptSeed = key + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
/* 107 */     byte[] sha1 = WebSocketUtil.sha1(acceptSeed.getBytes(CharsetUtil.US_ASCII));
/* 108 */     String accept = WebSocketUtil.base64(sha1);
/*     */     
/* 110 */     if (logger.isDebugEnabled()) {
/* 111 */       logger.debug(String.format("WS Version 13 Server Handshake key: %s. Response: %s.", new Object[] { key, accept }));
/*     */     }
/*     */     
/* 114 */     defaultFullHttpResponse.headers().add("Upgrade", "WebSocket".toLowerCase());
/* 115 */     defaultFullHttpResponse.headers().add("Connection", "Upgrade");
/* 116 */     defaultFullHttpResponse.headers().add("Sec-WebSocket-Accept", accept);
/* 117 */     String subprotocols = req.headers().get("Sec-WebSocket-Protocol");
/* 118 */     if (subprotocols != null) {
/* 119 */       String selectedSubprotocol = selectSubprotocol(subprotocols);
/* 120 */       if (selectedSubprotocol == null) {
/* 121 */         throw new WebSocketHandshakeException("Requested subprotocol(s) not supported: " + subprotocols);
/*     */       }
/*     */       
/* 124 */       defaultFullHttpResponse.headers().add("Sec-WebSocket-Protocol", selectedSubprotocol);
/*     */     } 
/*     */     
/* 127 */     return (FullHttpResponse)defaultFullHttpResponse;
/*     */   }
/*     */ 
/*     */   
/*     */   protected WebSocketFrameDecoder newWebsocketDecoder() {
/* 132 */     return new WebSocket13FrameDecoder(true, this.allowExtensions, maxFramePayloadLength());
/*     */   }
/*     */ 
/*     */   
/*     */   protected WebSocketFrameEncoder newWebSocketEncoder() {
/* 137 */     return new WebSocket13FrameEncoder(false);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\http\websocketx\WebSocketServerHandshaker13.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */