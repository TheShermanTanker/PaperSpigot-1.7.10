/*     */ package net.minecraft.util.io.netty.channel.oio;
/*     */ 
/*     */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.util.io.netty.channel.Channel;
/*     */ import net.minecraft.util.io.netty.channel.ChannelMetadata;
/*     */ import net.minecraft.util.io.netty.channel.ChannelOption;
/*     */ import net.minecraft.util.io.netty.channel.ChannelOutboundBuffer;
/*     */ import net.minecraft.util.io.netty.channel.ChannelPipeline;
/*     */ import net.minecraft.util.io.netty.channel.FileRegion;
/*     */ import net.minecraft.util.io.netty.channel.socket.ChannelInputShutdownEvent;
/*     */ import net.minecraft.util.io.netty.util.internal.StringUtil;
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
/*     */ public abstract class AbstractOioByteChannel
/*     */   extends AbstractOioChannel
/*     */ {
/*     */   private volatile boolean inputShutdown;
/*  36 */   private static final ChannelMetadata METADATA = new ChannelMetadata(false);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected AbstractOioByteChannel(Channel parent) {
/*  42 */     super(parent);
/*     */   }
/*     */   
/*     */   protected boolean isInputShutdown() {
/*  46 */     return this.inputShutdown;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelMetadata metadata() {
/*  51 */     return METADATA;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean checkInputShutdown() {
/*  59 */     if (this.inputShutdown) {
/*     */       try {
/*  61 */         Thread.sleep(1000L);
/*  62 */       } catch (InterruptedException e) {}
/*     */ 
/*     */       
/*  65 */       return true;
/*     */     } 
/*  67 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doRead() {
/*  72 */     if (checkInputShutdown()) {
/*     */       return;
/*     */     }
/*     */     
/*  76 */     ChannelPipeline pipeline = pipeline();
/*     */ 
/*     */     
/*  79 */     ByteBuf byteBuf = alloc().buffer();
/*  80 */     boolean closed = false;
/*  81 */     boolean read = false;
/*  82 */     Throwable exception = null;
/*     */     try {
/*     */       do {
/*  85 */         int localReadAmount = doReadBytes(byteBuf);
/*  86 */         if (localReadAmount > 0) {
/*  87 */           read = true;
/*  88 */         } else if (localReadAmount < 0) {
/*  89 */           closed = true;
/*     */         } 
/*     */         
/*  92 */         int available = available();
/*  93 */         if (available <= 0) {
/*     */           break;
/*     */         }
/*     */         
/*  97 */         if (byteBuf.isWritable())
/*  98 */           continue;  int capacity = byteBuf.capacity();
/*  99 */         int maxCapacity = byteBuf.maxCapacity();
/* 100 */         if (capacity == maxCapacity) {
/* 101 */           if (read) {
/* 102 */             read = false;
/* 103 */             pipeline.fireChannelRead(byteBuf);
/* 104 */             byteBuf = alloc().buffer();
/*     */           } 
/*     */         } else {
/* 107 */           int writerIndex = byteBuf.writerIndex();
/* 108 */           if (writerIndex + available > maxCapacity) {
/* 109 */             byteBuf.capacity(maxCapacity);
/*     */           } else {
/* 111 */             byteBuf.ensureWritable(available);
/*     */           }
/*     */         
/*     */         } 
/* 115 */       } while (config().isAutoRead());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 121 */     catch (Throwable t) {
/* 122 */       exception = t;
/*     */     } finally {
/* 124 */       if (read) {
/* 125 */         pipeline.fireChannelRead(byteBuf);
/*     */       } else {
/*     */         
/* 128 */         byteBuf.release();
/*     */       } 
/*     */       
/* 131 */       pipeline.fireChannelReadComplete();
/* 132 */       if (exception != null) {
/* 133 */         if (exception instanceof java.io.IOException) {
/* 134 */           closed = true;
/* 135 */           pipeline().fireExceptionCaught(exception);
/*     */         } else {
/* 137 */           pipeline.fireExceptionCaught(exception);
/* 138 */           unsafe().close(voidPromise());
/*     */         } 
/*     */       }
/*     */       
/* 142 */       if (closed) {
/* 143 */         this.inputShutdown = true;
/* 144 */         if (isOpen()) {
/* 145 */           if (Boolean.TRUE.equals(config().getOption(ChannelOption.ALLOW_HALF_CLOSURE))) {
/* 146 */             pipeline.fireUserEventTriggered(ChannelInputShutdownEvent.INSTANCE);
/*     */           } else {
/* 148 */             unsafe().close(unsafe().voidPromise());
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWrite(ChannelOutboundBuffer in) throws Exception {
/*     */     while (true) {
/* 158 */       Object msg = in.current();
/*     */       
/* 160 */       if (msg instanceof ByteBuf) {
/* 161 */         ByteBuf buf = (ByteBuf)msg;
/* 162 */         while (buf.isReadable()) {
/* 163 */           doWriteBytes(buf);
/*     */         }
/* 165 */         in.remove(); continue;
/* 166 */       }  if (msg instanceof FileRegion) {
/* 167 */         doWriteFileRegion((FileRegion)msg);
/* 168 */         in.remove(); continue;
/*     */       } 
/* 170 */       in.remove(new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(msg)));
/*     */     } 
/*     */   }
/*     */   
/*     */   protected abstract int available();
/*     */   
/*     */   protected abstract int doReadBytes(ByteBuf paramByteBuf) throws Exception;
/*     */   
/*     */   protected abstract void doWriteBytes(ByteBuf paramByteBuf) throws Exception;
/*     */   
/*     */   protected abstract void doWriteFileRegion(FileRegion paramFileRegion) throws Exception;
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\oio\AbstractOioByteChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */