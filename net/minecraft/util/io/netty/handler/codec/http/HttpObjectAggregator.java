/*     */ package net.minecraft.util.io.netty.handler.codec.http;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.util.io.netty.buffer.CompositeByteBuf;
/*     */ import net.minecraft.util.io.netty.buffer.Unpooled;
/*     */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*     */ import net.minecraft.util.io.netty.handler.codec.DecoderResult;
/*     */ import net.minecraft.util.io.netty.handler.codec.MessageToMessageDecoder;
/*     */ import net.minecraft.util.io.netty.handler.codec.TooLongFrameException;
/*     */ import net.minecraft.util.io.netty.util.CharsetUtil;
/*     */ import net.minecraft.util.io.netty.util.ReferenceCountUtil;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HttpObjectAggregator
/*     */   extends MessageToMessageDecoder<HttpObject>
/*     */ {
/*     */   public static final int DEFAULT_MAX_COMPOSITEBUFFER_COMPONENTS = 1024;
/*  52 */   private static final ByteBuf CONTINUE = Unpooled.unreleasableBuffer(Unpooled.copiedBuffer("HTTP/1.1 100 Continue\r\n\r\n", CharsetUtil.US_ASCII));
/*     */   
/*     */   private final int maxContentLength;
/*     */   
/*     */   private FullHttpMessage currentMessage;
/*     */   
/*     */   private boolean tooLongFrameFound;
/*  59 */   private int maxCumulationBufferComponents = 1024;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ChannelHandlerContext ctx;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HttpObjectAggregator(int maxContentLength) {
/*  71 */     if (maxContentLength <= 0) {
/*  72 */       throw new IllegalArgumentException("maxContentLength must be a positive integer: " + maxContentLength);
/*     */     }
/*     */ 
/*     */     
/*  76 */     this.maxContentLength = maxContentLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final int getMaxCumulationBufferComponents() {
/*  86 */     return this.maxCumulationBufferComponents;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void setMaxCumulationBufferComponents(int maxCumulationBufferComponents) {
/*  97 */     if (maxCumulationBufferComponents < 2) {
/*  98 */       throw new IllegalArgumentException("maxCumulationBufferComponents: " + maxCumulationBufferComponents + " (expected: >= 2)");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 103 */     if (this.ctx == null) {
/* 104 */       this.maxCumulationBufferComponents = maxCumulationBufferComponents;
/*     */     } else {
/* 106 */       throw new IllegalStateException("decoder properties cannot be changed once the decoder is added to a pipeline.");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void decode(ChannelHandlerContext ctx, HttpObject msg, List<Object> out) throws Exception {
/* 113 */     FullHttpMessage currentMessage = this.currentMessage;
/*     */     
/* 115 */     if (msg instanceof HttpMessage) {
/* 116 */       this.tooLongFrameFound = false;
/* 117 */       assert currentMessage == null;
/*     */       
/* 119 */       HttpMessage m = (HttpMessage)msg;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 126 */       if (HttpHeaders.is100ContinueExpected(m)) {
/* 127 */         ctx.writeAndFlush(CONTINUE.duplicate());
/*     */       }
/*     */       
/* 130 */       if (!m.getDecoderResult().isSuccess()) {
/* 131 */         HttpHeaders.removeTransferEncodingChunked(m);
/* 132 */         this.currentMessage = null;
/* 133 */         out.add(ReferenceCountUtil.retain(m));
/*     */         return;
/*     */       } 
/* 136 */       if (msg instanceof HttpRequest) {
/* 137 */         HttpRequest header = (HttpRequest)msg;
/* 138 */         this.currentMessage = currentMessage = new DefaultFullHttpRequest(header.getProtocolVersion(), header.getMethod(), header.getUri(), (ByteBuf)Unpooled.compositeBuffer(this.maxCumulationBufferComponents));
/*     */       }
/* 140 */       else if (msg instanceof HttpResponse) {
/* 141 */         HttpResponse header = (HttpResponse)msg;
/* 142 */         this.currentMessage = currentMessage = new DefaultFullHttpResponse(header.getProtocolVersion(), header.getStatus(), (ByteBuf)Unpooled.compositeBuffer(this.maxCumulationBufferComponents));
/*     */       }
/*     */       else {
/*     */         
/* 146 */         throw new Error();
/*     */       } 
/*     */       
/* 149 */       currentMessage.headers().set(m.headers());
/*     */ 
/*     */       
/* 152 */       HttpHeaders.removeTransferEncodingChunked(currentMessage);
/* 153 */     } else if (msg instanceof HttpContent) {
/* 154 */       boolean last; if (this.tooLongFrameFound) {
/* 155 */         if (msg instanceof LastHttpContent) {
/* 156 */           this.currentMessage = null;
/*     */         }
/*     */         
/*     */         return;
/*     */       } 
/* 161 */       assert currentMessage != null;
/*     */ 
/*     */       
/* 164 */       HttpContent chunk = (HttpContent)msg;
/* 165 */       CompositeByteBuf content = (CompositeByteBuf)currentMessage.content();
/*     */       
/* 167 */       if (content.readableBytes() > this.maxContentLength - chunk.content().readableBytes()) {
/* 168 */         this.tooLongFrameFound = true;
/*     */ 
/*     */         
/* 171 */         currentMessage.release();
/* 172 */         this.currentMessage = null;
/*     */         
/* 174 */         throw new TooLongFrameException("HTTP content length exceeded " + this.maxContentLength + " bytes.");
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 180 */       if (chunk.content().isReadable()) {
/* 181 */         chunk.retain();
/* 182 */         content.addComponent(chunk.content());
/* 183 */         content.writerIndex(content.writerIndex() + chunk.content().readableBytes());
/*     */       } 
/*     */ 
/*     */       
/* 187 */       if (!chunk.getDecoderResult().isSuccess()) {
/* 188 */         currentMessage.setDecoderResult(DecoderResult.failure(chunk.getDecoderResult().cause()));
/*     */         
/* 190 */         last = true;
/*     */       } else {
/* 192 */         last = chunk instanceof LastHttpContent;
/*     */       } 
/*     */       
/* 195 */       if (last) {
/* 196 */         this.currentMessage = null;
/*     */ 
/*     */         
/* 199 */         if (chunk instanceof LastHttpContent) {
/* 200 */           LastHttpContent trailer = (LastHttpContent)chunk;
/* 201 */           currentMessage.headers().add(trailer.trailingHeaders());
/*     */         } 
/*     */ 
/*     */         
/* 205 */         currentMessage.headers().set("Content-Length", String.valueOf(content.readableBytes()));
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 210 */         out.add(currentMessage);
/*     */       } 
/*     */     } else {
/* 213 */       throw new Error();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext ctx) throws Exception {
/* 219 */     super.channelInactive(ctx);
/*     */ 
/*     */     
/* 222 */     if (this.currentMessage != null) {
/* 223 */       this.currentMessage.release();
/* 224 */       this.currentMessage = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
/* 230 */     this.ctx = ctx;
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
/* 235 */     super.handlerRemoved(ctx);
/*     */ 
/*     */     
/* 238 */     if (this.currentMessage != null) {
/* 239 */       this.currentMessage.release();
/* 240 */       this.currentMessage = null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\http\HttpObjectAggregator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */