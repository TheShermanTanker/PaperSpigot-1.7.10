/*    */ package net.minecraft.util.io.netty.channel;
/*    */ 
/*    */ import java.util.concurrent.ThreadFactory;
/*    */ import net.minecraft.util.io.netty.util.concurrent.EventExecutor;
/*    */ import net.minecraft.util.io.netty.util.concurrent.EventExecutorGroup;
/*    */ import net.minecraft.util.io.netty.util.concurrent.SingleThreadEventExecutor;
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
/*    */ public abstract class SingleThreadEventLoop
/*    */   extends SingleThreadEventExecutor
/*    */   implements EventLoop
/*    */ {
/*    */   protected SingleThreadEventLoop(EventLoopGroup parent, ThreadFactory threadFactory, boolean addTaskWakesUp) {
/* 33 */     super(parent, threadFactory, addTaskWakesUp);
/*    */   }
/*    */ 
/*    */   
/*    */   public EventLoopGroup parent() {
/* 38 */     return (EventLoopGroup)super.parent();
/*    */   }
/*    */ 
/*    */   
/*    */   public EventLoop next() {
/* 43 */     return (EventLoop)super.next();
/*    */   }
/*    */ 
/*    */   
/*    */   public ChannelFuture register(Channel channel) {
/* 48 */     return register(channel, channel.newPromise());
/*    */   }
/*    */ 
/*    */   
/*    */   public ChannelFuture register(Channel channel, ChannelPromise promise) {
/* 53 */     if (channel == null) {
/* 54 */       throw new NullPointerException("channel");
/*    */     }
/* 56 */     if (promise == null) {
/* 57 */       throw new NullPointerException("promise");
/*    */     }
/*    */     
/* 60 */     channel.unsafe().register(this, promise);
/* 61 */     return promise;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\SingleThreadEventLoop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */