/*    */ package net.minecraft.util.io.netty.channel.oio;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import net.minecraft.util.io.netty.channel.Channel;
/*    */ import net.minecraft.util.io.netty.channel.ChannelPipeline;
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
/*    */ public abstract class AbstractOioMessageChannel
/*    */   extends AbstractOioChannel
/*    */ {
/* 30 */   private final List<Object> readBuf = new ArrayList();
/*    */   
/*    */   protected AbstractOioMessageChannel(Channel parent) {
/* 33 */     super(parent);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void doRead() {
/* 38 */     ChannelPipeline pipeline = pipeline();
/* 39 */     boolean closed = false;
/* 40 */     Throwable exception = null;
/*    */     try {
/* 42 */       int localReadAmount = doReadMessages(this.readBuf);
/* 43 */       if (localReadAmount < 0) {
/* 44 */         closed = true;
/*    */       }
/* 46 */     } catch (Throwable t) {
/* 47 */       exception = t;
/*    */     } 
/*    */     
/* 50 */     int size = this.readBuf.size();
/* 51 */     for (int i = 0; i < size; i++) {
/* 52 */       pipeline.fireChannelRead(this.readBuf.get(i));
/*    */     }
/* 54 */     this.readBuf.clear();
/* 55 */     pipeline.fireChannelReadComplete();
/*    */     
/* 57 */     if (exception != null) {
/* 58 */       if (exception instanceof java.io.IOException) {
/* 59 */         closed = true;
/*    */       }
/*    */       
/* 62 */       pipeline().fireExceptionCaught(exception);
/*    */     } 
/*    */     
/* 65 */     if (closed && 
/* 66 */       isOpen())
/* 67 */       unsafe().close(unsafe().voidPromise()); 
/*    */   }
/*    */   
/*    */   protected abstract int doReadMessages(List<Object> paramList) throws Exception;
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\oio\AbstractOioMessageChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */