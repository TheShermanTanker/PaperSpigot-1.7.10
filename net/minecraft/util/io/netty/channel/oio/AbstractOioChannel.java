/*     */ package net.minecraft.util.io.netty.channel.oio;
/*     */ 
/*     */ import java.net.ConnectException;
/*     */ import java.net.SocketAddress;
/*     */ import net.minecraft.util.io.netty.channel.AbstractChannel;
/*     */ import net.minecraft.util.io.netty.channel.Channel;
/*     */ import net.minecraft.util.io.netty.channel.ChannelPromise;
/*     */ import net.minecraft.util.io.netty.channel.EventLoop;
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
/*     */ public abstract class AbstractOioChannel
/*     */   extends AbstractChannel
/*     */ {
/*     */   protected static final int SO_TIMEOUT = 1000;
/*     */   private boolean readInProgress;
/*     */   
/*  36 */   private final Runnable readTask = new Runnable()
/*     */     {
/*     */       public void run() {
/*  39 */         AbstractOioChannel.this.readInProgress = false;
/*  40 */         AbstractOioChannel.this.doRead();
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractOioChannel(Channel parent) {
/*  48 */     super(parent);
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractChannel.AbstractUnsafe newUnsafe() {
/*  53 */     return new DefaultOioUnsafe();
/*     */   }
/*     */   private final class DefaultOioUnsafe extends AbstractChannel.AbstractUnsafe { private DefaultOioUnsafe() {
/*  56 */       super(AbstractOioChannel.this);
/*     */     }
/*     */ 
/*     */     
/*     */     public void connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
/*  61 */       if (!ensureOpen(promise)) {
/*     */         return;
/*     */       }
/*     */       
/*  65 */       if (!promise.setUncancellable()) {
/*  66 */         close(voidPromise());
/*     */         
/*     */         return;
/*     */       } 
/*     */       try {
/*  71 */         boolean wasActive = AbstractOioChannel.this.isActive();
/*  72 */         AbstractOioChannel.this.doConnect(remoteAddress, localAddress);
/*  73 */         promise.setSuccess();
/*  74 */         if (!wasActive && AbstractOioChannel.this.isActive()) {
/*  75 */           AbstractOioChannel.this.pipeline().fireChannelActive();
/*     */         }
/*  77 */       } catch (Throwable t) {
/*  78 */         if (t instanceof ConnectException) {
/*  79 */           Throwable newT = new ConnectException(t.getMessage() + ": " + remoteAddress);
/*  80 */           newT.setStackTrace(t.getStackTrace());
/*  81 */           t = newT;
/*     */         } 
/*  83 */         closeIfClosed();
/*  84 */         promise.setFailure(t);
/*     */       } 
/*     */     } }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isCompatible(EventLoop loop) {
/*  91 */     return loop instanceof net.minecraft.util.io.netty.channel.ThreadPerChannelEventLoop;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doBeginRead() throws Exception {
/* 102 */     if (this.readInProgress) {
/*     */       return;
/*     */     }
/*     */     
/* 106 */     this.readInProgress = true;
/* 107 */     eventLoop().execute(this.readTask);
/*     */   }
/*     */   
/*     */   protected abstract void doConnect(SocketAddress paramSocketAddress1, SocketAddress paramSocketAddress2) throws Exception;
/*     */   
/*     */   protected abstract void doRead();
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\oio\AbstractOioChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */