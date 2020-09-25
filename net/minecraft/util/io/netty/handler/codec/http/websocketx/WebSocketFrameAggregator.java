/*     */ package net.minecraft.util.io.netty.handler.codec.http.websocketx;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.util.io.netty.buffer.CompositeByteBuf;
/*     */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*     */ import net.minecraft.util.io.netty.handler.codec.MessageToMessageDecoder;
/*     */ import net.minecraft.util.io.netty.handler.codec.TooLongFrameException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WebSocketFrameAggregator
/*     */   extends MessageToMessageDecoder<WebSocketFrame>
/*     */ {
/*     */   private final int maxFrameSize;
/*     */   private WebSocketFrame currentFrame;
/*     */   private boolean tooLongFrameFound;
/*     */   
/*     */   public WebSocketFrameAggregator(int maxFrameSize) {
/*  44 */     if (maxFrameSize < 1) {
/*  45 */       throw new IllegalArgumentException("maxFrameSize must be > 0");
/*     */     }
/*  47 */     this.maxFrameSize = maxFrameSize;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void decode(ChannelHandlerContext ctx, WebSocketFrame msg, List<Object> out) throws Exception {
/*  52 */     if (this.currentFrame == null) {
/*  53 */       this.tooLongFrameFound = false;
/*  54 */       if (msg.isFinalFragment()) {
/*  55 */         out.add(msg.retain());
/*     */         return;
/*     */       } 
/*  58 */       CompositeByteBuf compositeByteBuf = ctx.alloc().compositeBuffer().addComponent(msg.content().retain());
/*  59 */       compositeByteBuf.writerIndex(compositeByteBuf.writerIndex() + msg.content().readableBytes());
/*     */       
/*  61 */       if (msg instanceof TextWebSocketFrame) {
/*  62 */         this.currentFrame = new TextWebSocketFrame(true, msg.rsv(), (ByteBuf)compositeByteBuf);
/*  63 */       } else if (msg instanceof BinaryWebSocketFrame) {
/*  64 */         this.currentFrame = new BinaryWebSocketFrame(true, msg.rsv(), (ByteBuf)compositeByteBuf);
/*     */       } else {
/*  66 */         throw new IllegalStateException("WebSocket frame was not of type TextWebSocketFrame or BinaryWebSocketFrame");
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*  71 */     if (msg instanceof ContinuationWebSocketFrame) {
/*  72 */       if (this.tooLongFrameFound) {
/*  73 */         if (msg.isFinalFragment()) {
/*  74 */           this.currentFrame = null;
/*     */         }
/*     */         return;
/*     */       } 
/*  78 */       CompositeByteBuf content = (CompositeByteBuf)this.currentFrame.content();
/*  79 */       if (content.readableBytes() > this.maxFrameSize - msg.content().readableBytes()) {
/*  80 */         this.tooLongFrameFound = true;
/*  81 */         throw new TooLongFrameException("WebSocketFrame length exceeded " + content + " bytes.");
/*     */       } 
/*     */ 
/*     */       
/*  85 */       content.addComponent(msg.content().retain());
/*  86 */       content.writerIndex(content.writerIndex() + msg.content().readableBytes());
/*     */       
/*  88 */       if (msg.isFinalFragment()) {
/*  89 */         WebSocketFrame currentFrame = this.currentFrame;
/*  90 */         this.currentFrame = null;
/*  91 */         out.add(currentFrame);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  99 */     out.add(msg.retain());
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext ctx) throws Exception {
/* 104 */     super.channelInactive(ctx);
/*     */ 
/*     */     
/* 107 */     if (this.currentFrame != null) {
/* 108 */       this.currentFrame.release();
/* 109 */       this.currentFrame = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
/* 115 */     super.handlerRemoved(ctx);
/*     */ 
/*     */     
/* 118 */     if (this.currentFrame != null) {
/* 119 */       this.currentFrame.release();
/* 120 */       this.currentFrame = null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\http\websocketx\WebSocketFrameAggregator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */