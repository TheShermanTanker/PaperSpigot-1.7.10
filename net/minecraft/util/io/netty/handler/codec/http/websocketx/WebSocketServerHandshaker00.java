/*     */ package net.minecraft.util.io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import java.util.regex.Pattern;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.util.io.netty.buffer.Unpooled;
/*     */ import net.minecraft.util.io.netty.channel.Channel;
/*     */ import net.minecraft.util.io.netty.channel.ChannelFuture;
/*     */ import net.minecraft.util.io.netty.channel.ChannelPromise;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.DefaultFullHttpResponse;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.FullHttpRequest;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.FullHttpResponse;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.HttpHeaders;
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
/*     */ public class WebSocketServerHandshaker00
/*     */   extends WebSocketServerHandshaker
/*     */ {
/*  49 */   private static final Pattern BEGINNING_DIGIT = Pattern.compile("[^0-9]");
/*  50 */   private static final Pattern BEGINNING_SPACE = Pattern.compile("[^ ]");
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
/*     */   public WebSocketServerHandshaker00(String webSocketURL, String subprotocols, int maxFramePayloadLength) {
/*  65 */     super(WebSocketVersion.V00, webSocketURL, subprotocols, maxFramePayloadLength);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected FullHttpResponse newHandshakeResponse(FullHttpRequest req, HttpHeaders headers) {
/* 112 */     if (!"Upgrade".equalsIgnoreCase(req.headers().get("Connection")) || !"WebSocket".equalsIgnoreCase(req.headers().get("Upgrade")))
/*     */     {
/* 114 */       throw new WebSocketHandshakeException("not a WebSocket handshake request: missing upgrade");
/*     */     }
/*     */ 
/*     */     
/* 118 */     boolean isHixie76 = (req.headers().contains("Sec-WebSocket-Key1") && req.headers().contains("Sec-WebSocket-Key2"));
/*     */ 
/*     */     
/* 121 */     DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, new HttpResponseStatus(101, isHixie76 ? "WebSocket Protocol Handshake" : "Web Socket Protocol Handshake"));
/*     */     
/* 123 */     if (headers != null) {
/* 124 */       defaultFullHttpResponse.headers().add(headers);
/*     */     }
/*     */     
/* 127 */     defaultFullHttpResponse.headers().add("Upgrade", "WebSocket");
/* 128 */     defaultFullHttpResponse.headers().add("Connection", "Upgrade");
/*     */ 
/*     */     
/* 131 */     if (isHixie76) {
/*     */       
/* 133 */       defaultFullHttpResponse.headers().add("Sec-WebSocket-Origin", req.headers().get("Origin"));
/* 134 */       defaultFullHttpResponse.headers().add("Sec-WebSocket-Location", uri());
/* 135 */       String subprotocols = req.headers().get("Sec-WebSocket-Protocol");
/* 136 */       if (subprotocols != null) {
/* 137 */         String selectedSubprotocol = selectSubprotocol(subprotocols);
/* 138 */         if (selectedSubprotocol == null) {
/* 139 */           throw new WebSocketHandshakeException("Requested subprotocol(s) not supported: " + subprotocols);
/*     */         }
/* 141 */         defaultFullHttpResponse.headers().add("Sec-WebSocket-Protocol", selectedSubprotocol);
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 146 */       String key1 = req.headers().get("Sec-WebSocket-Key1");
/* 147 */       String key2 = req.headers().get("Sec-WebSocket-Key2");
/* 148 */       int a = (int)(Long.parseLong(BEGINNING_DIGIT.matcher(key1).replaceAll("")) / BEGINNING_SPACE.matcher(key1).replaceAll("").length());
/*     */       
/* 150 */       int b = (int)(Long.parseLong(BEGINNING_DIGIT.matcher(key2).replaceAll("")) / BEGINNING_SPACE.matcher(key2).replaceAll("").length());
/*     */       
/* 152 */       long c = req.content().readLong();
/* 153 */       ByteBuf input = Unpooled.buffer(16);
/* 154 */       input.writeInt(a);
/* 155 */       input.writeInt(b);
/* 156 */       input.writeLong(c);
/* 157 */       defaultFullHttpResponse.content().writeBytes(WebSocketUtil.md5(input.array()));
/*     */     } else {
/*     */       
/* 160 */       defaultFullHttpResponse.headers().add("WebSocket-Origin", req.headers().get("Origin"));
/* 161 */       defaultFullHttpResponse.headers().add("WebSocket-Location", uri());
/* 162 */       String protocol = req.headers().get("WebSocket-Protocol");
/* 163 */       if (protocol != null) {
/* 164 */         defaultFullHttpResponse.headers().add("WebSocket-Protocol", selectSubprotocol(protocol));
/*     */       }
/*     */     } 
/* 167 */     return (FullHttpResponse)defaultFullHttpResponse;
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
/*     */   public ChannelFuture close(Channel channel, CloseWebSocketFrame frame, ChannelPromise promise) {
/* 180 */     return channel.writeAndFlush(frame, promise);
/*     */   }
/*     */ 
/*     */   
/*     */   protected WebSocketFrameDecoder newWebsocketDecoder() {
/* 185 */     return new WebSocket00FrameDecoder(maxFramePayloadLength());
/*     */   }
/*     */ 
/*     */   
/*     */   protected WebSocketFrameEncoder newWebSocketEncoder() {
/* 190 */     return new WebSocket00FrameEncoder();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\http\websocketx\WebSocketServerHandshaker00.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */