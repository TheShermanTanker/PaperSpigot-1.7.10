/*    */ package net.minecraft.util.io.netty.channel;
/*    */ 
/*    */ import net.minecraft.util.io.netty.util.internal.logging.InternalLogger;
/*    */ import net.minecraft.util.io.netty.util.internal.logging.InternalLoggerFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Sharable
/*    */ public abstract class ChannelInitializer<C extends Channel>
/*    */   extends ChannelInboundHandlerAdapter
/*    */ {
/* 52 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(ChannelInitializer.class);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected abstract void initChannel(C paramC) throws Exception;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final void channelRegistered(ChannelHandlerContext ctx) throws Exception {
/* 67 */     boolean removed = false;
/* 68 */     boolean success = false;
/*    */     try {
/* 70 */       initChannel((C)ctx.channel());
/* 71 */       ctx.pipeline().remove(this);
/* 72 */       removed = true;
/* 73 */       ctx.fireChannelRegistered();
/* 74 */       success = true;
/* 75 */     } catch (Throwable t) {
/* 76 */       logger.warn("Failed to initialize a channel. Closing: " + ctx.channel(), t);
/*    */     } finally {
/* 78 */       if (!removed) {
/* 79 */         ctx.pipeline().remove(this);
/*    */       }
/* 81 */       if (!success)
/* 82 */         ctx.close(); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\ChannelInitializer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */