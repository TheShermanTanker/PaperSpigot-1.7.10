/*     */ package net.minecraft.util.io.netty.channel.embedded;
/*     */ 
/*     */ import java.util.ArrayDeque;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import net.minecraft.util.io.netty.channel.Channel;
/*     */ import net.minecraft.util.io.netty.channel.ChannelFuture;
/*     */ import net.minecraft.util.io.netty.channel.ChannelPromise;
/*     */ import net.minecraft.util.io.netty.channel.EventLoop;
/*     */ import net.minecraft.util.io.netty.channel.EventLoopGroup;
/*     */ import net.minecraft.util.io.netty.util.concurrent.AbstractEventExecutor;
/*     */ import net.minecraft.util.io.netty.util.concurrent.EventExecutor;
/*     */ import net.minecraft.util.io.netty.util.concurrent.EventExecutorGroup;
/*     */ import net.minecraft.util.io.netty.util.concurrent.Future;
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
/*     */ final class EmbeddedEventLoop
/*     */   extends AbstractEventExecutor
/*     */   implements EventLoop
/*     */ {
/*  32 */   private final Queue<Runnable> tasks = new ArrayDeque<Runnable>(2);
/*     */ 
/*     */   
/*     */   public void execute(Runnable command) {
/*  36 */     if (command == null) {
/*  37 */       throw new NullPointerException("command");
/*     */     }
/*  39 */     this.tasks.add(command);
/*     */   }
/*     */   
/*     */   void runTasks() {
/*     */     while (true) {
/*  44 */       Runnable task = this.tasks.poll();
/*  45 */       if (task == null) {
/*     */         break;
/*     */       }
/*     */       
/*  49 */       task.run();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Future<?> shutdownGracefully(long quietPeriod, long timeout, TimeUnit unit) {
/*  55 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public Future<?> terminationFuture() {
/*  60 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void shutdown() {
/*  66 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isShuttingDown() {
/*  71 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isShutdown() {
/*  76 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTerminated() {
/*  81 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
/*  87 */     Thread.sleep(unit.toMillis(timeout));
/*  88 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture register(Channel channel) {
/*  93 */     return register(channel, channel.newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture register(Channel channel, ChannelPromise promise) {
/*  98 */     channel.unsafe().register(this, promise);
/*  99 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean inEventLoop() {
/* 104 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean inEventLoop(Thread thread) {
/* 109 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public EventLoop next() {
/* 114 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public EventLoopGroup parent() {
/* 119 */     return (EventLoopGroup)this;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\embedded\EmbeddedEventLoop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */