/*     */ package net.minecraft.util.io.netty.handler.codec;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*     */ import net.minecraft.util.io.netty.channel.ChannelOutboundHandlerAdapter;
/*     */ import net.minecraft.util.io.netty.channel.ChannelPromise;
/*     */ import net.minecraft.util.io.netty.util.ReferenceCountUtil;
/*     */ import net.minecraft.util.io.netty.util.internal.RecyclableArrayList;
/*     */ import net.minecraft.util.io.netty.util.internal.StringUtil;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class MessageToMessageEncoder<I>
/*     */   extends ChannelOutboundHandlerAdapter
/*     */ {
/*     */   private final TypeParameterMatcher matcher;
/*     */   
/*     */   protected MessageToMessageEncoder() {
/*  60 */     this.matcher = TypeParameterMatcher.find(this, MessageToMessageEncoder.class, "I");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected MessageToMessageEncoder(Class<? extends I> outboundMessageType) {
/*  69 */     this.matcher = TypeParameterMatcher.get(outboundMessageType);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean acceptOutboundMessage(Object msg) throws Exception {
/*  77 */     return this.matcher.match(msg);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
/*  82 */     RecyclableArrayList out = null;
/*     */     try {
/*  84 */       if (acceptOutboundMessage(msg)) {
/*  85 */         out = RecyclableArrayList.newInstance();
/*     */         
/*  87 */         I cast = (I)msg;
/*     */         try {
/*  89 */           encode(ctx, cast, (List<Object>)out);
/*     */         } finally {
/*  91 */           ReferenceCountUtil.release(cast);
/*     */         } 
/*     */         
/*  94 */         if (out.isEmpty()) {
/*  95 */           out.recycle();
/*  96 */           out = null;
/*     */           
/*  98 */           throw new EncoderException(StringUtil.simpleClassName(this) + " must produce at least one message.");
/*     */         } 
/*     */       } else {
/*     */         
/* 102 */         ctx.write(msg, promise);
/*     */       } 
/* 104 */     } catch (EncoderException e) {
/* 105 */       throw e;
/* 106 */     } catch (Throwable t) {
/* 107 */       throw new EncoderException(t);
/*     */     } finally {
/* 109 */       if (out != null) {
/* 110 */         int sizeMinusOne = out.size() - 1;
/* 111 */         if (sizeMinusOne >= 0) {
/* 112 */           for (int i = 0; i < sizeMinusOne; i++) {
/* 113 */             ctx.write(out.get(i));
/*     */           }
/* 115 */           ctx.write(out.get(sizeMinusOne), promise);
/*     */         } 
/* 117 */         out.recycle();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected abstract void encode(ChannelHandlerContext paramChannelHandlerContext, I paramI, List<Object> paramList) throws Exception;
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\MessageToMessageEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */