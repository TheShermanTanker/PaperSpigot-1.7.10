/*     */ package net.minecraft.util.io.netty.handler.codec;
/*     */ 
/*     */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.util.io.netty.buffer.Unpooled;
/*     */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*     */ import net.minecraft.util.io.netty.channel.ChannelOutboundHandlerAdapter;
/*     */ import net.minecraft.util.io.netty.channel.ChannelPromise;
/*     */ import net.minecraft.util.io.netty.util.ReferenceCountUtil;
/*     */ import net.minecraft.util.io.netty.util.internal.TypeParameterMatcher;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class MessageToByteEncoder<I>
/*     */   extends ChannelOutboundHandlerAdapter
/*     */ {
/*     */   private final TypeParameterMatcher matcher;
/*     */   private final boolean preferDirect;
/*     */   
/*     */   protected MessageToByteEncoder() {
/*  55 */     this(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MessageToByteEncoder(Class<? extends I> outboundMessageType) {
/*  62 */     this(outboundMessageType, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MessageToByteEncoder(boolean preferDirect) {
/*  73 */     this.matcher = TypeParameterMatcher.find(this, MessageToByteEncoder.class, "I");
/*  74 */     this.preferDirect = preferDirect;
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
/*     */   protected MessageToByteEncoder(Class<? extends I> outboundMessageType, boolean preferDirect) {
/*  86 */     this.matcher = TypeParameterMatcher.get(outboundMessageType);
/*  87 */     this.preferDirect = preferDirect;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean acceptOutboundMessage(Object msg) throws Exception {
/*  95 */     return this.matcher.match(msg);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
/* 100 */     ByteBuf buf = null;
/*     */     try {
/* 102 */       if (acceptOutboundMessage(msg)) {
/*     */         
/* 104 */         I cast = (I)msg;
/* 105 */         if (this.preferDirect) {
/* 106 */           buf = ctx.alloc().ioBuffer();
/*     */         } else {
/* 108 */           buf = ctx.alloc().heapBuffer();
/*     */         } 
/*     */         try {
/* 111 */           encode(ctx, cast, buf);
/*     */         } finally {
/* 113 */           ReferenceCountUtil.release(cast);
/*     */         } 
/*     */         
/* 116 */         if (buf.isReadable()) {
/* 117 */           ctx.write(buf, promise);
/*     */         } else {
/* 119 */           buf.release();
/* 120 */           ctx.write(Unpooled.EMPTY_BUFFER, promise);
/*     */         } 
/* 122 */         buf = null;
/*     */       } else {
/* 124 */         ctx.write(msg, promise);
/*     */       } 
/* 126 */     } catch (EncoderException e) {
/* 127 */       throw e;
/* 128 */     } catch (Throwable e) {
/* 129 */       throw new EncoderException(e);
/*     */     } finally {
/* 131 */       if (buf != null)
/* 132 */         buf.release(); 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected abstract void encode(ChannelHandlerContext paramChannelHandlerContext, I paramI, ByteBuf paramByteBuf) throws Exception;
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\MessageToByteEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */