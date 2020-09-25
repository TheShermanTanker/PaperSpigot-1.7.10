/*    */ package net.minecraft.util.io.netty.channel;
/*    */ 
/*    */ import java.net.SocketAddress;
/*    */ import net.minecraft.util.io.netty.util.ReferenceCountUtil;
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
/*    */ public abstract class AbstractServerChannel
/*    */   extends AbstractChannel
/*    */   implements ServerChannel
/*    */ {
/* 35 */   private static final ChannelMetadata METADATA = new ChannelMetadata(false);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected AbstractServerChannel() {
/* 41 */     super(null);
/*    */   }
/*    */ 
/*    */   
/*    */   public ChannelMetadata metadata() {
/* 46 */     return METADATA;
/*    */   }
/*    */ 
/*    */   
/*    */   public SocketAddress remoteAddress() {
/* 51 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   protected SocketAddress remoteAddress0() {
/* 56 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void doDisconnect() throws Exception {
/* 61 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ 
/*    */   
/*    */   protected AbstractChannel.AbstractUnsafe newUnsafe() {
/* 66 */     return new DefaultServerUnsafe();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void doWrite(ChannelOutboundBuffer in) throws Exception {
/* 71 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   private final class DefaultServerUnsafe
/*    */     extends AbstractChannel.AbstractUnsafe {
/*    */     public void write(Object msg, ChannelPromise promise) {
/* 77 */       ReferenceCountUtil.release(msg);
/* 78 */       reject(promise);
/*    */     }
/*    */ 
/*    */     
/*    */     private DefaultServerUnsafe() {}
/*    */ 
/*    */     
/*    */     public void flush() {}
/*    */     
/*    */     public void connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
/* 88 */       reject(promise);
/*    */     }
/*    */     
/*    */     private void reject(ChannelPromise promise) {
/* 92 */       promise.setFailure(new UnsupportedOperationException());
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\AbstractServerChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */