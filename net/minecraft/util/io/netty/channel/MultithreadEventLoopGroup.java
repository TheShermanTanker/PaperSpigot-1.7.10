/*    */ package net.minecraft.util.io.netty.channel;
/*    */ 
/*    */ import java.util.concurrent.ThreadFactory;
/*    */ import net.minecraft.util.io.netty.util.concurrent.DefaultThreadFactory;
/*    */ import net.minecraft.util.io.netty.util.concurrent.EventExecutor;
/*    */ import net.minecraft.util.io.netty.util.concurrent.MultithreadEventExecutorGroup;
/*    */ import net.minecraft.util.io.netty.util.internal.SystemPropertyUtil;
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
/*    */ public abstract class MultithreadEventLoopGroup
/*    */   extends MultithreadEventExecutorGroup
/*    */   implements EventLoopGroup
/*    */ {
/* 32 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(MultithreadEventLoopGroup.class);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 37 */   private static final int DEFAULT_EVENT_LOOP_THREADS = Math.max(1, SystemPropertyUtil.getInt("io.netty.eventLoopThreads", Runtime.getRuntime().availableProcessors() * 2));
/*    */   
/*    */   static {
/* 40 */     if (logger.isDebugEnabled()) {
/* 41 */       logger.debug("-Dio.netty.eventLoopThreads: {}", Integer.valueOf(DEFAULT_EVENT_LOOP_THREADS));
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected MultithreadEventLoopGroup(int nThreads, ThreadFactory threadFactory, Object... args) {
/* 49 */     super((nThreads == 0) ? DEFAULT_EVENT_LOOP_THREADS : nThreads, threadFactory, args);
/*    */   }
/*    */ 
/*    */   
/*    */   protected ThreadFactory newDefaultThreadFactory() {
/* 54 */     return (ThreadFactory)new DefaultThreadFactory(getClass(), 10);
/*    */   }
/*    */ 
/*    */   
/*    */   public EventLoop next() {
/* 59 */     return (EventLoop)super.next();
/*    */   }
/*    */ 
/*    */   
/*    */   public ChannelFuture register(Channel channel) {
/* 64 */     return next().register(channel);
/*    */   }
/*    */ 
/*    */   
/*    */   public ChannelFuture register(Channel channel, ChannelPromise promise) {
/* 69 */     return next().register(channel, promise);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\MultithreadEventLoopGroup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */