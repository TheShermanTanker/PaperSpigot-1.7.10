/*     */ package net.minecraft.util.io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import java.net.URI;
/*     */ import net.minecraft.util.io.netty.channel.Channel;
/*     */ import net.minecraft.util.io.netty.channel.ChannelFuture;
/*     */ import net.minecraft.util.io.netty.channel.ChannelFutureListener;
/*     */ import net.minecraft.util.io.netty.channel.ChannelHandler;
/*     */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*     */ import net.minecraft.util.io.netty.channel.ChannelPipeline;
/*     */ import net.minecraft.util.io.netty.channel.ChannelPromise;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.FullHttpRequest;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.FullHttpResponse;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.HttpClientCodec;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.HttpContentDecompressor;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.HttpHeaders;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.HttpRequestEncoder;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.HttpResponseDecoder;
/*     */ import net.minecraft.util.io.netty.util.concurrent.Future;
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
/*     */ public abstract class WebSocketClientHandshaker
/*     */ {
/*     */   private final URI uri;
/*     */   private final WebSocketVersion version;
/*     */   private boolean handshakeComplete;
/*     */   private final String expectedSubprotocol;
/*     */   private String actualSubprotocol;
/*     */   protected final HttpHeaders customHeaders;
/*     */   private final int maxFramePayloadLength;
/*     */   
/*     */   protected WebSocketClientHandshaker(URI uri, WebSocketVersion version, String subprotocol, HttpHeaders customHeaders, int maxFramePayloadLength) {
/*  70 */     this.uri = uri;
/*  71 */     this.version = version;
/*  72 */     this.expectedSubprotocol = subprotocol;
/*  73 */     this.customHeaders = customHeaders;
/*  74 */     this.maxFramePayloadLength = maxFramePayloadLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public URI uri() {
/*  81 */     return this.uri;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WebSocketVersion version() {
/*  88 */     return this.version;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int maxFramePayloadLength() {
/*  95 */     return this.maxFramePayloadLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHandshakeComplete() {
/* 102 */     return this.handshakeComplete;
/*     */   }
/*     */   
/*     */   private void setHandshakeComplete() {
/* 106 */     this.handshakeComplete = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String expectedSubprotocol() {
/* 113 */     return this.expectedSubprotocol;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String actualSubprotocol() {
/* 121 */     return this.actualSubprotocol;
/*     */   }
/*     */   
/*     */   private void setActualSubprotocol(String actualSubprotocol) {
/* 125 */     this.actualSubprotocol = actualSubprotocol;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture handshake(Channel channel) {
/* 135 */     if (channel == null) {
/* 136 */       throw new NullPointerException("channel");
/*     */     }
/* 138 */     return handshake(channel, channel.newPromise());
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
/*     */   public final ChannelFuture handshake(Channel channel, final ChannelPromise promise) {
/* 150 */     FullHttpRequest request = newHandshakeRequest();
/*     */     
/* 152 */     HttpResponseDecoder decoder = (HttpResponseDecoder)channel.pipeline().get(HttpResponseDecoder.class);
/* 153 */     if (decoder == null) {
/* 154 */       HttpClientCodec codec = (HttpClientCodec)channel.pipeline().get(HttpClientCodec.class);
/* 155 */       if (codec == null) {
/* 156 */         promise.setFailure(new IllegalStateException("ChannelPipeline does not contain a HttpResponseDecoder or HttpClientCodec"));
/*     */         
/* 158 */         return (ChannelFuture)promise;
/*     */       } 
/* 160 */       codec.setSingleDecode(true);
/*     */     } else {
/* 162 */       decoder.setSingleDecode(true);
/*     */     } 
/* 164 */     channel.writeAndFlush(request).addListener((GenericFutureListener)new ChannelFutureListener()
/*     */         {
/*     */           public void operationComplete(ChannelFuture future) {
/* 167 */             if (future.isSuccess()) {
/* 168 */               ChannelPipeline p = future.channel().pipeline();
/* 169 */               ChannelHandlerContext ctx = p.context(HttpRequestEncoder.class);
/* 170 */               if (ctx == null) {
/* 171 */                 ctx = p.context(HttpClientCodec.class);
/*     */               }
/* 173 */               if (ctx == null) {
/* 174 */                 promise.setFailure(new IllegalStateException("ChannelPipeline does not contain a HttpRequestEncoder or HttpClientCodec"));
/*     */                 
/*     */                 return;
/*     */               } 
/* 178 */               p.addAfter(ctx.name(), "ws-encoder", (ChannelHandler)WebSocketClientHandshaker.this.newWebSocketEncoder());
/*     */               
/* 180 */               promise.setSuccess();
/*     */             } else {
/* 182 */               promise.setFailure(future.cause());
/*     */             } 
/*     */           }
/*     */         });
/* 186 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract FullHttpRequest newHandshakeRequest();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void finishHandshake(Channel channel, FullHttpResponse response) {
/* 203 */     verify(response);
/* 204 */     setActualSubprotocol(response.headers().get("Sec-WebSocket-Protocol"));
/* 205 */     setHandshakeComplete();
/*     */     
/* 207 */     ChannelPipeline p = channel.pipeline();
/*     */     
/* 209 */     HttpContentDecompressor decompressor = (HttpContentDecompressor)p.get(HttpContentDecompressor.class);
/* 210 */     if (decompressor != null) {
/* 211 */       p.remove((ChannelHandler)decompressor);
/*     */     }
/*     */     
/* 214 */     ChannelHandlerContext ctx = p.context(HttpResponseDecoder.class);
/* 215 */     if (ctx == null) {
/* 216 */       ctx = p.context(HttpClientCodec.class);
/* 217 */       if (ctx == null) {
/* 218 */         throw new IllegalStateException("ChannelPipeline does not contain a HttpRequestEncoder or HttpClientCodec");
/*     */       }
/*     */       
/* 221 */       p.replace(ctx.name(), "ws-decoder", (ChannelHandler)newWebsocketDecoder());
/*     */     } else {
/* 223 */       if (p.get(HttpRequestEncoder.class) != null) {
/* 224 */         p.remove(HttpRequestEncoder.class);
/*     */       }
/* 226 */       p.replace(ctx.name(), "ws-decoder", (ChannelHandler)newWebsocketDecoder());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void verify(FullHttpResponse paramFullHttpResponse);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract WebSocketFrameDecoder newWebsocketDecoder();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract WebSocketFrameEncoder newWebSocketEncoder();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture close(Channel channel, CloseWebSocketFrame frame) {
/* 255 */     if (channel == null) {
/* 256 */       throw new NullPointerException("channel");
/*     */     }
/* 258 */     return close(channel, frame, channel.newPromise());
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
/* 272 */     if (channel == null) {
/* 273 */       throw new NullPointerException("channel");
/*     */     }
/* 275 */     return channel.writeAndFlush(frame, promise);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\http\websocketx\WebSocketClientHandshaker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */