/*     */ package net.minecraft.util.io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Set;
/*     */ import net.minecraft.util.io.netty.channel.Channel;
/*     */ import net.minecraft.util.io.netty.channel.ChannelFuture;
/*     */ import net.minecraft.util.io.netty.channel.ChannelFutureListener;
/*     */ import net.minecraft.util.io.netty.channel.ChannelHandler;
/*     */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*     */ import net.minecraft.util.io.netty.channel.ChannelPipeline;
/*     */ import net.minecraft.util.io.netty.channel.ChannelPromise;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.FullHttpRequest;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.FullHttpResponse;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.HttpContentCompressor;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.HttpHeaders;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.HttpObjectAggregator;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.HttpRequestDecoder;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.HttpResponseEncoder;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.HttpServerCodec;
/*     */ import net.minecraft.util.io.netty.util.concurrent.Future;
/*     */ import net.minecraft.util.io.netty.util.concurrent.GenericFutureListener;
/*     */ import net.minecraft.util.io.netty.util.internal.EmptyArrays;
/*     */ import net.minecraft.util.io.netty.util.internal.StringUtil;
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
/*     */ public abstract class WebSocketServerHandshaker
/*     */ {
/*  45 */   protected static final InternalLogger logger = InternalLoggerFactory.getInstance(WebSocketServerHandshaker.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String uri;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final String[] subprotocols;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final WebSocketVersion version;
/*     */ 
/*     */ 
/*     */   
/*     */   private final int maxFramePayloadLength;
/*     */ 
/*     */ 
/*     */   
/*     */   private String selectedSubprotocol;
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String SUB_PROTOCOL_WILDCARD = "*";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected WebSocketServerHandshaker(WebSocketVersion version, String uri, String subprotocols, int maxFramePayloadLength) {
/*  78 */     this.version = version;
/*  79 */     this.uri = uri;
/*  80 */     if (subprotocols != null) {
/*  81 */       String[] subprotocolArray = StringUtil.split(subprotocols, ',');
/*  82 */       for (int i = 0; i < subprotocolArray.length; i++) {
/*  83 */         subprotocolArray[i] = subprotocolArray[i].trim();
/*     */       }
/*  85 */       this.subprotocols = subprotocolArray;
/*     */     } else {
/*  87 */       this.subprotocols = EmptyArrays.EMPTY_STRINGS;
/*     */     } 
/*  89 */     this.maxFramePayloadLength = maxFramePayloadLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String uri() {
/*  96 */     return this.uri;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<String> subprotocols() {
/* 103 */     Set<String> ret = new LinkedHashSet<String>();
/* 104 */     Collections.addAll(ret, this.subprotocols);
/* 105 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WebSocketVersion version() {
/* 112 */     return this.version;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int maxFramePayloadLength() {
/* 121 */     return this.maxFramePayloadLength;
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
/*     */   public ChannelFuture handshake(Channel channel, FullHttpRequest req) {
/* 136 */     return handshake(channel, req, null, channel.newPromise());
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
/*     */   public final ChannelFuture handshake(Channel channel, FullHttpRequest req, HttpHeaders responseHeaders, final ChannelPromise promise) {
/* 158 */     if (logger.isDebugEnabled()) {
/* 159 */       logger.debug(String.format("%s WS Version %s server handshake", new Object[] { channel, version() }));
/*     */     }
/* 161 */     FullHttpResponse response = newHandshakeResponse(req, responseHeaders);
/* 162 */     channel.writeAndFlush(response).addListener((GenericFutureListener)new ChannelFutureListener()
/*     */         {
/*     */           public void operationComplete(ChannelFuture future) throws Exception {
/* 165 */             if (future.isSuccess()) {
/* 166 */               ChannelPipeline p = future.channel().pipeline();
/* 167 */               if (p.get(HttpObjectAggregator.class) != null) {
/* 168 */                 p.remove(HttpObjectAggregator.class);
/*     */               }
/* 170 */               if (p.get(HttpContentCompressor.class) != null) {
/* 171 */                 p.remove(HttpContentCompressor.class);
/*     */               }
/* 173 */               ChannelHandlerContext ctx = p.context(HttpRequestDecoder.class);
/* 174 */               if (ctx == null) {
/*     */                 
/* 176 */                 ctx = p.context(HttpServerCodec.class);
/* 177 */                 if (ctx == null) {
/* 178 */                   promise.setFailure(new IllegalStateException("No HttpDecoder and no HttpServerCodec in the pipeline"));
/*     */                   
/*     */                   return;
/*     */                 } 
/* 182 */                 p.addBefore(ctx.name(), "wsdecoder", (ChannelHandler)WebSocketServerHandshaker.this.newWebsocketDecoder());
/* 183 */                 p.replace(ctx.name(), "wsencoder", (ChannelHandler)WebSocketServerHandshaker.this.newWebSocketEncoder());
/*     */               } else {
/* 185 */                 p.replace(ctx.name(), "wsdecoder", (ChannelHandler)WebSocketServerHandshaker.this.newWebsocketDecoder());
/*     */                 
/* 187 */                 p.replace(HttpResponseEncoder.class, "wsencoder", (ChannelHandler)WebSocketServerHandshaker.this.newWebSocketEncoder());
/*     */               } 
/* 189 */               promise.setSuccess();
/*     */             } else {
/* 191 */               promise.setFailure(future.cause());
/*     */             } 
/*     */           }
/*     */         });
/* 195 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract FullHttpResponse newHandshakeResponse(FullHttpRequest paramFullHttpRequest, HttpHeaders paramHttpHeaders);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture close(Channel channel, CloseWebSocketFrame frame) {
/* 212 */     if (channel == null) {
/* 213 */       throw new NullPointerException("channel");
/*     */     }
/* 215 */     return close(channel, frame, channel.newPromise());
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
/*     */   public ChannelFuture close(Channel channel, CloseWebSocketFrame frame, ChannelPromise promise) {
/* 229 */     if (channel == null) {
/* 230 */       throw new NullPointerException("channel");
/*     */     }
/* 232 */     return channel.writeAndFlush(frame, promise).addListener((GenericFutureListener)ChannelFutureListener.CLOSE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected String selectSubprotocol(String requestedSubprotocols) {
/* 243 */     if (requestedSubprotocols == null || this.subprotocols.length == 0) {
/* 244 */       return null;
/*     */     }
/*     */     
/* 247 */     String[] requestedSubprotocolArray = StringUtil.split(requestedSubprotocols, ',');
/* 248 */     for (String p : requestedSubprotocolArray) {
/* 249 */       String requestedSubprotocol = p.trim();
/*     */       
/* 251 */       for (String supportedSubprotocol : this.subprotocols) {
/* 252 */         if ("*".equals(supportedSubprotocol) || requestedSubprotocol.equals(supportedSubprotocol)) {
/*     */           
/* 254 */           this.selectedSubprotocol = requestedSubprotocol;
/* 255 */           return requestedSubprotocol;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 261 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String selectedSubprotocol() {
/* 271 */     return this.selectedSubprotocol;
/*     */   }
/*     */   
/*     */   protected abstract WebSocketFrameDecoder newWebsocketDecoder();
/*     */   
/*     */   protected abstract WebSocketFrameEncoder newWebSocketEncoder();
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\http\websocketx\WebSocketServerHandshaker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */