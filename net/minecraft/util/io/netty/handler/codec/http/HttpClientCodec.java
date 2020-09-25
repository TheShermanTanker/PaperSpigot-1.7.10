/*     */ package net.minecraft.util.io.netty.handler.codec.http;
/*     */ 
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.List;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.atomic.AtomicLong;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*     */ import net.minecraft.util.io.netty.channel.ChannelInboundHandler;
/*     */ import net.minecraft.util.io.netty.channel.ChannelOutboundHandler;
/*     */ import net.minecraft.util.io.netty.channel.CombinedChannelDuplexHandler;
/*     */ import net.minecraft.util.io.netty.handler.codec.PrematureChannelClosureException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class HttpClientCodec
/*     */   extends CombinedChannelDuplexHandler<HttpResponseDecoder, HttpRequestEncoder>
/*     */ {
/*  47 */   private final Queue<HttpMethod> queue = new ArrayDeque<HttpMethod>();
/*     */ 
/*     */   
/*     */   private boolean done;
/*     */   
/*  52 */   private final AtomicLong requestResponseCounter = new AtomicLong();
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean failOnMissingResponse;
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpClientCodec() {
/*  61 */     this(4096, 8192, 8192, false);
/*     */   }
/*     */   
/*     */   public void setSingleDecode(boolean singleDecode) {
/*  65 */     ((HttpResponseDecoder)inboundHandler()).setSingleDecode(singleDecode);
/*     */   }
/*     */   
/*     */   public boolean isSingleDecode() {
/*  69 */     return ((HttpResponseDecoder)inboundHandler()).isSingleDecode();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpClientCodec(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize) {
/*  76 */     this(maxInitialLineLength, maxHeaderSize, maxChunkSize, false);
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpClientCodec(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize, boolean failOnMissingResponse) {
/*  81 */     init((ChannelInboundHandler)new Decoder(maxInitialLineLength, maxHeaderSize, maxChunkSize), (ChannelOutboundHandler)new Encoder());
/*  82 */     this.failOnMissingResponse = failOnMissingResponse;
/*     */   }
/*     */   
/*     */   private final class Encoder
/*     */     extends HttpRequestEncoder {
/*     */     private Encoder() {}
/*     */     
/*     */     protected void encode(ChannelHandlerContext ctx, Object msg, List<Object> out) throws Exception {
/*  90 */       if (msg instanceof HttpRequest && !HttpClientCodec.this.done) {
/*  91 */         HttpClientCodec.this.queue.offer(((HttpRequest)msg).getMethod());
/*     */       }
/*     */       
/*  94 */       super.encode(ctx, msg, out);
/*     */       
/*  96 */       if (HttpClientCodec.this.failOnMissingResponse)
/*     */       {
/*  98 */         if (msg instanceof LastHttpContent)
/*     */         {
/* 100 */           HttpClientCodec.this.requestResponseCounter.incrementAndGet();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private final class Decoder extends HttpResponseDecoder {
/*     */     Decoder(int maxInitialLineLength, int maxHeaderSize, int maxChunkSize) {
/* 108 */       super(maxInitialLineLength, maxHeaderSize, maxChunkSize);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected void decode(ChannelHandlerContext ctx, ByteBuf buffer, List<Object> out) throws Exception {
/* 114 */       if (HttpClientCodec.this.done) {
/* 115 */         int readable = actualReadableBytes();
/* 116 */         if (readable == 0) {
/*     */           return;
/*     */         }
/*     */ 
/*     */         
/* 121 */         out.add(buffer.readBytes(readable));
/*     */       } else {
/* 123 */         int oldSize = out.size();
/* 124 */         super.decode(ctx, buffer, out);
/* 125 */         if (HttpClientCodec.this.failOnMissingResponse) {
/* 126 */           int size = out.size();
/* 127 */           for (int i = oldSize; i < size; i++) {
/* 128 */             decrement(out.get(i));
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     private void decrement(Object msg) {
/* 135 */       if (msg == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 140 */       if (msg instanceof LastHttpContent) {
/* 141 */         HttpClientCodec.this.requestResponseCounter.decrementAndGet();
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean isContentAlwaysEmpty(HttpMessage msg) {
/* 147 */       int statusCode = ((HttpResponse)msg).getStatus().code();
/* 148 */       if (statusCode == 100)
/*     */       {
/* 150 */         return true;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 155 */       HttpMethod method = HttpClientCodec.this.queue.poll();
/*     */       
/* 157 */       char firstChar = method.name().charAt(0);
/* 158 */       switch (firstChar) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case 'H':
/* 164 */           if (HttpMethod.HEAD.equals(method)) {
/* 165 */             return true;
/*     */           }
/*     */           break;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case 'C':
/* 183 */           if (statusCode == 200 && 
/* 184 */             HttpMethod.CONNECT.equals(method)) {
/*     */             
/* 186 */             HttpClientCodec.this.done = true;
/* 187 */             HttpClientCodec.this.queue.clear();
/* 188 */             return true;
/*     */           } 
/*     */           break;
/*     */       } 
/*     */ 
/*     */       
/* 194 */       return super.isContentAlwaysEmpty(msg);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void channelInactive(ChannelHandlerContext ctx) throws Exception {
/* 200 */       super.channelInactive(ctx);
/*     */       
/* 202 */       if (HttpClientCodec.this.failOnMissingResponse) {
/* 203 */         long missingResponses = HttpClientCodec.this.requestResponseCounter.get();
/* 204 */         if (missingResponses > 0L)
/* 205 */           ctx.fireExceptionCaught((Throwable)new PrematureChannelClosureException("channel gone inactive with " + missingResponses + " missing response(s)")); 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\http\HttpClientCodec.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */