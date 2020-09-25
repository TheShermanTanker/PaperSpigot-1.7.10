/*     */ package net.minecraft.util.io.netty.handler.codec.spdy;
/*     */ 
/*     */ import java.util.List;
/*     */ import javax.net.ssl.SSLEngine;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.util.io.netty.channel.ChannelHandler;
/*     */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*     */ import net.minecraft.util.io.netty.channel.ChannelInboundHandler;
/*     */ import net.minecraft.util.io.netty.channel.ChannelPipeline;
/*     */ import net.minecraft.util.io.netty.handler.codec.ByteToMessageDecoder;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.HttpObjectAggregator;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.HttpRequestDecoder;
/*     */ import net.minecraft.util.io.netty.handler.codec.http.HttpResponseEncoder;
/*     */ import net.minecraft.util.io.netty.handler.ssl.SslHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SpdyOrHttpChooser
/*     */   extends ByteToMessageDecoder
/*     */ {
/*     */   private final int maxSpdyContentLength;
/*     */   private final int maxHttpContentLength;
/*     */   
/*     */   public enum SelectedProtocol
/*     */   {
/*  42 */     SPDY_2,
/*  43 */     SPDY_3,
/*  44 */     HTTP_1_1,
/*  45 */     HTTP_1_0,
/*  46 */     UNKNOWN;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SpdyOrHttpChooser(int maxSpdyContentLength, int maxHttpContentLength) {
/*  53 */     this.maxSpdyContentLength = maxSpdyContentLength;
/*  54 */     this.maxHttpContentLength = maxHttpContentLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract SelectedProtocol getProtocol(SSLEngine paramSSLEngine);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
/*  66 */     if (initPipeline(ctx))
/*     */     {
/*     */       
/*  69 */       ctx.pipeline().remove((ChannelHandler)this);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean initPipeline(ChannelHandlerContext ctx) {
/*  75 */     SslHandler handler = (SslHandler)ctx.pipeline().get(SslHandler.class);
/*  76 */     if (handler == null)
/*     */     {
/*  78 */       throw new IllegalStateException("SslHandler is needed for SPDY");
/*     */     }
/*     */     
/*  81 */     SelectedProtocol protocol = getProtocol(handler.engine());
/*  82 */     switch (protocol) {
/*     */       
/*     */       case UNKNOWN:
/*  85 */         return false;
/*     */       case SPDY_2:
/*  87 */         addSpdyHandlers(ctx, 2);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  99 */         return true;case SPDY_3: addSpdyHandlers(ctx, 3); return true;case HTTP_1_0: case HTTP_1_1: addHttpHandlers(ctx); return true;
/*     */     } 
/*     */     throw new IllegalStateException("Unknown SelectedProtocol");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void addSpdyHandlers(ChannelHandlerContext ctx, int version) {
/* 106 */     ChannelPipeline pipeline = ctx.pipeline();
/* 107 */     pipeline.addLast("spdyDecoder", (ChannelHandler)new SpdyFrameDecoder(version));
/* 108 */     pipeline.addLast("spdyEncoder", (ChannelHandler)new SpdyFrameEncoder(version));
/* 109 */     pipeline.addLast("spdySessionHandler", (ChannelHandler)new SpdySessionHandler(version, true));
/* 110 */     pipeline.addLast("spdyHttpEncoder", (ChannelHandler)new SpdyHttpEncoder(version));
/* 111 */     pipeline.addLast("spdyHttpDecoder", (ChannelHandler)new SpdyHttpDecoder(version, this.maxSpdyContentLength));
/* 112 */     pipeline.addLast("spdyStreamIdHandler", (ChannelHandler)new SpdyHttpResponseStreamIdHandler());
/* 113 */     pipeline.addLast("httpRquestHandler", (ChannelHandler)createHttpRequestHandlerForSpdy());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addHttpHandlers(ChannelHandlerContext ctx) {
/* 120 */     ChannelPipeline pipeline = ctx.pipeline();
/* 121 */     pipeline.addLast("httpRquestDecoder", (ChannelHandler)new HttpRequestDecoder());
/* 122 */     pipeline.addLast("httpResponseEncoder", (ChannelHandler)new HttpResponseEncoder());
/* 123 */     pipeline.addLast("httpChunkAggregator", (ChannelHandler)new HttpObjectAggregator(this.maxHttpContentLength));
/* 124 */     pipeline.addLast("httpRquestHandler", (ChannelHandler)createHttpRequestHandlerForHttp());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract ChannelInboundHandler createHttpRequestHandlerForHttp();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ChannelInboundHandler createHttpRequestHandlerForSpdy() {
/* 143 */     return createHttpRequestHandlerForHttp();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\spdy\SpdyOrHttpChooser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */