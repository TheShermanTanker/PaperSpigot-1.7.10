/*    */ package net.minecraft.util.io.netty.channel.nio;
/*    */ 
/*    */ import java.nio.channels.spi.SelectorProvider;
/*    */ import java.util.concurrent.ThreadFactory;
/*    */ import net.minecraft.util.io.netty.channel.MultithreadEventLoopGroup;
/*    */ import net.minecraft.util.io.netty.util.concurrent.EventExecutor;
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
/*    */ public class NioEventLoopGroup
/*    */   extends MultithreadEventLoopGroup
/*    */ {
/*    */   public NioEventLoopGroup() {
/* 36 */     this(0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NioEventLoopGroup(int nThreads) {
/* 44 */     this(nThreads, null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NioEventLoopGroup(int nThreads, ThreadFactory threadFactory) {
/* 52 */     this(nThreads, threadFactory, SelectorProvider.provider());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NioEventLoopGroup(int nThreads, ThreadFactory threadFactory, SelectorProvider selectorProvider) {
/* 61 */     super(nThreads, threadFactory, new Object[] { selectorProvider });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setIoRatio(int ioRatio) {
/* 69 */     for (EventExecutor e : children()) {
/* 70 */       ((NioEventLoop)e).setIoRatio(ioRatio);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void rebuildSelectors() {
/* 79 */     for (EventExecutor e : children()) {
/* 80 */       ((NioEventLoop)e).rebuildSelector();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected EventExecutor newChild(ThreadFactory threadFactory, Object... args) throws Exception {
/* 87 */     return (EventExecutor)new NioEventLoop(this, threadFactory, (SelectorProvider)args[0]);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\nio\NioEventLoopGroup.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */