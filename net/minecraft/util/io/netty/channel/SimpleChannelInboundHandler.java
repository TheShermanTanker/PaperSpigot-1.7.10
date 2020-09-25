/*     */ package net.minecraft.util.io.netty.channel;
/*     */ 
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
/*     */ public abstract class SimpleChannelInboundHandler<I>
/*     */   extends ChannelInboundHandlerAdapter
/*     */ {
/*     */   private final TypeParameterMatcher matcher;
/*     */   private final boolean autoRelease;
/*     */   
/*     */   protected SimpleChannelInboundHandler() {
/*  50 */     this(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SimpleChannelInboundHandler(boolean autoRelease) {
/*  60 */     this.matcher = TypeParameterMatcher.find(this, SimpleChannelInboundHandler.class, "I");
/*  61 */     this.autoRelease = autoRelease;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SimpleChannelInboundHandler(Class<? extends I> inboundMessageType) {
/*  68 */     this(inboundMessageType, true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected SimpleChannelInboundHandler(Class<? extends I> inboundMessageType, boolean autoRelease) {
/*  79 */     this.matcher = TypeParameterMatcher.get(inboundMessageType);
/*  80 */     this.autoRelease = autoRelease;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean acceptInboundMessage(Object msg) throws Exception {
/*  88 */     return this.matcher.match(msg);
/*     */   }
/*     */ 
/*     */   
/*     */   public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
/*  93 */     boolean release = true;
/*     */     try {
/*  95 */       if (acceptInboundMessage(msg)) {
/*     */         
/*  97 */         I imsg = (I)msg;
/*  98 */         channelRead0(ctx, imsg);
/*     */       } else {
/* 100 */         release = false;
/* 101 */         ctx.fireChannelRead(msg);
/*     */       } 
/*     */     } finally {
/* 104 */       if (this.autoRelease && release)
/* 105 */         ReferenceCountUtil.release(msg); 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected abstract void channelRead0(ChannelHandlerContext paramChannelHandlerContext, I paramI) throws Exception;
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\SimpleChannelInboundHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */