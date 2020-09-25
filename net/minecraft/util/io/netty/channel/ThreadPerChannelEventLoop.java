/*    */ package net.minecraft.util.io.netty.channel;
/*    */ 
/*    */ import net.minecraft.util.io.netty.util.concurrent.Future;
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
/*    */ public class ThreadPerChannelEventLoop
/*    */   extends SingleThreadEventLoop
/*    */ {
/*    */   private final ThreadPerChannelEventLoopGroup parent;
/*    */   private Channel ch;
/*    */   
/*    */   public ThreadPerChannelEventLoop(ThreadPerChannelEventLoopGroup parent) {
/* 29 */     super(parent, parent.threadFactory, true);
/* 30 */     this.parent = parent;
/*    */   }
/*    */ 
/*    */   
/*    */   public ChannelFuture register(Channel channel, ChannelPromise promise) {
/* 35 */     return super.register(channel, promise).addListener(new ChannelFutureListener()
/*    */         {
/*    */           public void operationComplete(ChannelFuture future) throws Exception
/*    */           {
/* 39 */             if (future.isSuccess()) {
/* 40 */               ThreadPerChannelEventLoop.this.ch = future.channel();
/*    */             } else {
/* 42 */               ThreadPerChannelEventLoop.this.deregister();
/*    */             } 
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   protected void run() {
/*    */     while (true) {
/* 51 */       Runnable task = takeTask();
/* 52 */       if (task != null) {
/* 53 */         task.run();
/* 54 */         updateLastExecutionTime();
/*    */       } 
/*    */       
/* 57 */       Channel ch = this.ch;
/* 58 */       if (isShuttingDown()) {
/* 59 */         if (ch != null) {
/* 60 */           ch.unsafe().close(ch.unsafe().voidPromise());
/*    */         }
/* 62 */         if (confirmShutdown())
/*    */           break; 
/*    */         continue;
/*    */       } 
/* 66 */       if (ch != null)
/*    */       {
/* 68 */         if (!ch.isRegistered()) {
/* 69 */           runAllTasks();
/* 70 */           deregister();
/*    */         } 
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected void deregister() {
/* 78 */     this.ch = null;
/* 79 */     this.parent.activeChildren.remove(this);
/* 80 */     this.parent.idleChildren.add(this);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\ThreadPerChannelEventLoop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */