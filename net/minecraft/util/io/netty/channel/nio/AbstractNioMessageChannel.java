/*     */ package net.minecraft.util.io.netty.channel.nio;
/*     */ 
/*     */ import java.nio.channels.SelectableChannel;
/*     */ import java.nio.channels.SelectionKey;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import net.minecraft.util.io.netty.channel.AbstractChannel;
/*     */ import net.minecraft.util.io.netty.channel.Channel;
/*     */ import net.minecraft.util.io.netty.channel.ChannelConfig;
/*     */ import net.minecraft.util.io.netty.channel.ChannelOutboundBuffer;
/*     */ import net.minecraft.util.io.netty.channel.ChannelPipeline;
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
/*     */ public abstract class AbstractNioMessageChannel
/*     */   extends AbstractNioChannel
/*     */ {
/*     */   protected AbstractNioMessageChannel(Channel parent, SelectableChannel ch, int readInterestOp) {
/*  39 */     super(parent, ch, readInterestOp);
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractNioChannel.AbstractNioUnsafe newUnsafe() {
/*  44 */     return new NioMessageUnsafe();
/*     */   }
/*     */   
/*     */   private final class NioMessageUnsafe extends AbstractNioChannel.AbstractNioUnsafe {
/*     */     private NioMessageUnsafe() {
/*  49 */       this.readBuf = new ArrayList();
/*     */     }
/*     */     private final List<Object> readBuf;
/*     */     public void read() {
/*  53 */       assert AbstractNioMessageChannel.this.eventLoop().inEventLoop();
/*  54 */       SelectionKey key = AbstractNioMessageChannel.this.selectionKey();
/*  55 */       if (!AbstractNioMessageChannel.this.config().isAutoRead()) {
/*  56 */         int interestOps = key.interestOps();
/*  57 */         if ((interestOps & AbstractNioMessageChannel.this.readInterestOp) != 0)
/*     */         {
/*  59 */           key.interestOps(interestOps & (AbstractNioMessageChannel.this.readInterestOp ^ 0xFFFFFFFF));
/*     */         }
/*     */       } 
/*     */       
/*  63 */       ChannelConfig config = AbstractNioMessageChannel.this.config();
/*  64 */       int maxMessagesPerRead = config.getMaxMessagesPerRead();
/*  65 */       boolean autoRead = config.isAutoRead();
/*  66 */       ChannelPipeline pipeline = AbstractNioMessageChannel.this.pipeline();
/*  67 */       boolean closed = false;
/*  68 */       Throwable exception = null;
/*     */       try {
/*     */         do {
/*  71 */           int localRead = AbstractNioMessageChannel.this.doReadMessages(this.readBuf);
/*  72 */           if (localRead == 0) {
/*     */             break;
/*     */           }
/*  75 */           if (localRead < 0) {
/*  76 */             closed = true;
/*     */             
/*     */             break;
/*     */           } 
/*  80 */         } while ((((this.readBuf.size() >= maxMessagesPerRead) ? 1 : 0) | (!autoRead ? 1 : 0)) == 0);
/*     */ 
/*     */       
/*     */       }
/*  84 */       catch (Throwable t) {
/*  85 */         exception = t;
/*     */       } 
/*     */       
/*  88 */       int size = this.readBuf.size();
/*  89 */       for (int i = 0; i < size; i++) {
/*  90 */         pipeline.fireChannelRead(this.readBuf.get(i));
/*     */       }
/*  92 */       this.readBuf.clear();
/*  93 */       pipeline.fireChannelReadComplete();
/*     */       
/*  95 */       if (exception != null) {
/*  96 */         if (exception instanceof java.io.IOException)
/*     */         {
/*     */           
/*  99 */           closed = !(AbstractNioMessageChannel.this instanceof net.minecraft.util.io.netty.channel.ServerChannel);
/*     */         }
/*     */         
/* 102 */         pipeline.fireExceptionCaught(exception);
/*     */       } 
/*     */       
/* 105 */       if (closed && 
/* 106 */         AbstractNioMessageChannel.this.isOpen()) {
/* 107 */         close(voidPromise());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doWrite(ChannelOutboundBuffer in) throws Exception {
/* 115 */     SelectionKey key = selectionKey();
/* 116 */     int interestOps = key.interestOps();
/*     */     
/*     */     while (true) {
/* 119 */       Object msg = in.current();
/* 120 */       if (msg == null) {
/*     */         
/* 122 */         if ((interestOps & 0x4) != 0) {
/* 123 */           key.interestOps(interestOps & 0xFFFFFFFB);
/*     */         }
/*     */         
/*     */         break;
/*     */       } 
/* 128 */       boolean done = false;
/* 129 */       for (int i = config().getWriteSpinCount() - 1; i >= 0; i--) {
/* 130 */         if (doWriteMessage(msg, in)) {
/* 131 */           done = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 136 */       if (done) {
/* 137 */         in.remove();
/*     */         continue;
/*     */       } 
/* 140 */       if ((interestOps & 0x4) == 0)
/* 141 */         key.interestOps(interestOps | 0x4); 
/*     */       break;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected abstract int doReadMessages(List<Object> paramList) throws Exception;
/*     */   
/*     */   protected abstract boolean doWriteMessage(Object paramObject, ChannelOutboundBuffer paramChannelOutboundBuffer) throws Exception;
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\nio\AbstractNioMessageChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */