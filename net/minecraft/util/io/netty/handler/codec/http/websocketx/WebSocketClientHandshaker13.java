/*     */ package net.minecraft.util.io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import java.net.URI;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.DefaultFullHttpRequest;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.FullHttpRequest;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.FullHttpResponse;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.HttpHeaders;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.HttpMethod;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.HttpResponseStatus;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.HttpVersion;
/*     */ import net.minecraft.util.io.netty.util.CharsetUtil;
/*     */ import net.minecraft.util.io.netty.util.internal.logging.InternalLogger;
/*     */ import net.minecraft.util.io.netty.util.internal.logging.InternalLoggerFactory;
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
/*     */ public class WebSocketClientHandshaker13
/*     */   extends WebSocketClientHandshaker
/*     */ {
/*  42 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(WebSocketClientHandshaker13.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String MAGIC_GUID = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String expectedChallengeResponseString;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean allowExtensions;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WebSocketClientHandshaker13(URI webSocketURL, WebSocketVersion version, String subprotocol, boolean allowExtensions, HttpHeaders customHeaders, int maxFramePayloadLength) {
/*  69 */     super(webSocketURL, version, subprotocol, customHeaders, maxFramePayloadLength);
/*  70 */     this.allowExtensions = allowExtensions;
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
/*     */   protected FullHttpRequest newHandshakeRequest() {
/*  94 */     URI wsURL = uri();
/*  95 */     String path = wsURL.getPath();
/*  96 */     if (wsURL.getQuery() != null && !wsURL.getQuery().isEmpty()) {
/*  97 */       path = wsURL.getPath() + '?' + wsURL.getQuery();
/*     */     }
/*     */     
/* 100 */     if (path == null || path.isEmpty()) {
/* 101 */       path = "/";
/*     */     }
/*     */ 
/*     */     
/* 105 */     byte[] nonce = WebSocketUtil.randomBytes(16);
/* 106 */     String key = WebSocketUtil.base64(nonce);
/*     */     
/* 108 */     String acceptSeed = key + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
/* 109 */     byte[] sha1 = WebSocketUtil.sha1(acceptSeed.getBytes(CharsetUtil.US_ASCII));
/* 110 */     this.expectedChallengeResponseString = WebSocketUtil.base64(sha1);
/*     */     
/* 112 */     if (logger.isDebugEnabled()) {
/* 113 */       logger.debug(String.format("WS Version 13 Client Handshake key: %s. Expected response: %s.", new Object[] { key, this.expectedChallengeResponseString }));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 118 */     int wsPort = wsURL.getPort();
/*     */ 
/*     */     
/* 121 */     if (wsPort == -1) {
/* 122 */       if ("wss".equals(wsURL.getScheme())) {
/* 123 */         wsPort = 443;
/*     */       } else {
/* 125 */         wsPort = 80;
/*     */       } 
/*     */     }
/*     */     
/* 129 */     DefaultFullHttpRequest defaultFullHttpRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, path);
/* 130 */     HttpHeaders headers = defaultFullHttpRequest.headers();
/*     */     
/* 132 */     headers.add("Upgrade", "WebSocket".toLowerCase()).add("Connection", "Upgrade").add("Sec-WebSocket-Key", key).add("Host", wsURL.getHost() + ':' + wsPort);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 137 */     String originValue = "http://" + wsURL.getHost();
/* 138 */     if (wsPort != 80 && wsPort != 443)
/*     */     {
/*     */       
/* 141 */       originValue = originValue + ':' + wsPort;
/*     */     }
/* 143 */     headers.add("Sec-WebSocket-Origin", originValue);
/*     */     
/* 145 */     String expectedSubprotocol = expectedSubprotocol();
/* 146 */     if (expectedSubprotocol != null && !expectedSubprotocol.isEmpty()) {
/* 147 */       headers.add("Sec-WebSocket-Protocol", expectedSubprotocol);
/*     */     }
/*     */     
/* 150 */     headers.add("Sec-WebSocket-Version", "13");
/*     */     
/* 152 */     if (this.customHeaders != null) {
/* 153 */       headers.add(this.customHeaders);
/*     */     }
/* 155 */     return (FullHttpRequest)defaultFullHttpRequest;
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
/*     */   protected void verify(FullHttpResponse response) {
/* 177 */     HttpResponseStatus status = HttpResponseStatus.SWITCHING_PROTOCOLS;
/* 178 */     HttpHeaders headers = response.headers();
/*     */     
/* 180 */     if (!response.getStatus().equals(status)) {
/* 181 */       throw new WebSocketHandshakeException("Invalid handshake response getStatus: " + response.getStatus());
/*     */     }
/*     */     
/* 184 */     String upgrade = headers.get("Upgrade");
/* 185 */     if (!"WebSocket".equalsIgnoreCase(upgrade)) {
/* 186 */       throw new WebSocketHandshakeException("Invalid handshake response upgrade: " + upgrade);
/*     */     }
/*     */     
/* 189 */     String connection = headers.get("Connection");
/* 190 */     if (!"Upgrade".equalsIgnoreCase(connection)) {
/* 191 */       throw new WebSocketHandshakeException("Invalid handshake response connection: " + connection);
/*     */     }
/*     */     
/* 194 */     String accept = headers.get("Sec-WebSocket-Accept");
/* 195 */     if (accept == null || !accept.equals(this.expectedChallengeResponseString)) {
/* 196 */       throw new WebSocketHandshakeException(String.format("Invalid challenge. Actual: %s. Expected: %s", new Object[] { accept, this.expectedChallengeResponseString }));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected WebSocketFrameDecoder newWebsocketDecoder() {
/* 203 */     return new WebSocket13FrameDecoder(false, this.allowExtensions, maxFramePayloadLength());
/*     */   }
/*     */ 
/*     */   
/*     */   protected WebSocketFrameEncoder newWebSocketEncoder() {
/* 208 */     return new WebSocket13FrameEncoder(true);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\http\websocketx\WebSocketClientHandshaker13.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */