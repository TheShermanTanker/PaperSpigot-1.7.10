/*     */ package net.minecraft.util.io.netty.channel.nio;
/*     */ 
/*     */ import java.nio.channels.SelectableChannel;
/*     */ import java.nio.channels.SelectionKey;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBufAllocator;
/*     */ import net.minecraft.util.io.netty.channel.AbstractChannel;
/*     */ import net.minecraft.util.io.netty.channel.Channel;
/*     */ import net.minecraft.util.io.netty.channel.ChannelConfig;
/*     */ import net.minecraft.util.io.netty.channel.ChannelOption;
/*     */ import net.minecraft.util.io.netty.channel.ChannelOutboundBuffer;
/*     */ import net.minecraft.util.io.netty.channel.ChannelPipeline;
/*     */ import net.minecraft.util.io.netty.channel.FileRegion;
/*     */ import net.minecraft.util.io.netty.channel.RecvByteBufAllocator;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractNioByteChannel
/*     */   extends AbstractNioChannel
/*     */ {
/*     */   protected AbstractNioByteChannel(Channel parent, SelectableChannel ch) {
/*  46 */     super(parent, ch, 1);
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractNioChannel.AbstractNioUnsafe newUnsafe() {
/*  51 */     return new NioByteUnsafe();
/*     */   }
/*     */   
/*     */   private final class NioByteUnsafe
/*     */     extends AbstractNioChannel.AbstractNioUnsafe {
/*     */     private RecvByteBufAllocator.Handle allocHandle;
/*     */     
/*     */     public void read() {
/*  59 */       assert AbstractNioByteChannel.this.eventLoop().inEventLoop();
/*  60 */       SelectionKey key = AbstractNioByteChannel.this.selectionKey();
/*  61 */       ChannelConfig config = AbstractNioByteChannel.this.config();
/*  62 */       if (!config.isAutoRead()) {
/*  63 */         int interestOps = key.interestOps();
/*  64 */         if ((interestOps & AbstractNioByteChannel.this.readInterestOp) != 0)
/*     */         {
/*  66 */           key.interestOps(interestOps & (AbstractNioByteChannel.this.readInterestOp ^ 0xFFFFFFFF));
/*     */         }
/*     */       } 
/*     */       
/*  70 */       ChannelPipeline pipeline = AbstractNioByteChannel.this.pipeline();
/*     */       
/*  72 */       RecvByteBufAllocator.Handle allocHandle = this.allocHandle;
/*  73 */       if (allocHandle == null) {
/*  74 */         this.allocHandle = allocHandle = config.getRecvByteBufAllocator().newHandle();
/*     */       }
/*     */       
/*  77 */       ByteBufAllocator allocator = config.getAllocator();
/*  78 */       int maxMessagesPerRead = config.getMaxMessagesPerRead();
/*     */       
/*  80 */       boolean closed = false;
/*  81 */       Throwable exception = null;
/*  82 */       ByteBuf byteBuf = null;
/*  83 */       int messages = 0;
/*     */       try {
/*     */         do {
/*  86 */           byteBuf = allocHandle.allocate(allocator);
/*  87 */           int localReadAmount = AbstractNioByteChannel.this.doReadBytes(byteBuf);
/*  88 */           if (localReadAmount == 0) {
/*  89 */             byteBuf.release();
/*  90 */             byteBuf = null;
/*     */             break;
/*     */           } 
/*  93 */           if (localReadAmount < 0) {
/*  94 */             closed = true;
/*  95 */             byteBuf.release();
/*  96 */             byteBuf = null;
/*     */             
/*     */             break;
/*     */           } 
/* 100 */           pipeline.fireChannelRead(byteBuf);
/* 101 */           allocHandle.record(localReadAmount);
/* 102 */           byteBuf = null;
/* 103 */         } while (++messages != maxMessagesPerRead);
/*     */ 
/*     */       
/*     */       }
/* 107 */       catch (Throwable t) {
/* 108 */         exception = t;
/*     */       } finally {
/* 110 */         if (byteBuf != null) {
/* 111 */           if (byteBuf.isReadable()) {
/* 112 */             pipeline.fireChannelRead(byteBuf);
/*     */           } else {
/* 114 */             byteBuf.release();
/*     */           } 
/*     */         }
/*     */         
/* 118 */         pipeline.fireChannelReadComplete();
/*     */         
/* 120 */         if (exception != null) {
/* 121 */           if (exception instanceof java.io.IOException) {
/* 122 */             closed = true;
/*     */           }
/*     */           
/* 125 */           AbstractNioByteChannel.this.pipeline().fireExceptionCaught(exception);
/*     */         } 
/*     */         
/* 128 */         if (closed) {
/* 129 */           AbstractNioByteChannel.this.setInputShutdown();
/* 130 */           if (AbstractNioByteChannel.this.isOpen())
/* 131 */             if (Boolean.TRUE.equals(AbstractNioByteChannel.this.config().getOption(ChannelOption.ALLOW_HALF_CLOSURE))) {
/* 132 */               key.interestOps(key.interestOps() & (AbstractNioByteChannel.this.readInterestOp ^ 0xFFFFFFFF));
/* 133 */               pipeline.fireUserEventTriggered(ChannelInputShutdownEvent.INSTANCE);
/*     */             } else {
/* 135 */               close(voidPromise());
/*     */             }  
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/*     */     private NioByteUnsafe() {}
/*     */   }
/*     */   
/*     */   protected void doWrite(ChannelOutboundBuffer in) throws Exception {
/* 145 */     int writeSpinCount = -1;
/*     */     
/*     */     while (true) {
/* 148 */       Object msg = in.current();
/* 149 */       if (msg == null) {
/*     */         
/* 151 */         clearOpWrite();
/*     */         
/*     */         break;
/*     */       } 
/* 155 */       if (msg instanceof ByteBuf) {
/* 156 */         ByteBuf buf = (ByteBuf)msg;
/* 157 */         int readableBytes = buf.readableBytes();
/* 158 */         if (readableBytes == 0) {
/* 159 */           in.remove();
/*     */           continue;
/*     */         } 
/* 162 */         if (!buf.isDirect()) {
/* 163 */           ByteBufAllocator alloc = alloc();
/* 164 */           if (alloc.isDirectBufferPooled()) {
/*     */ 
/*     */ 
/*     */             
/* 168 */             buf = alloc.directBuffer(readableBytes).writeBytes(buf);
/* 169 */             in.current(buf);
/*     */           } 
/*     */         } 
/* 172 */         boolean done = false;
/* 173 */         long flushedAmount = 0L;
/* 174 */         if (writeSpinCount == -1) {
/* 175 */           writeSpinCount = config().getWriteSpinCount();
/*     */         }
/* 177 */         for (int i = writeSpinCount - 1; i >= 0; i--) {
/* 178 */           int localFlushedAmount = doWriteBytes(buf);
/* 179 */           if (localFlushedAmount == 0) {
/*     */             break;
/*     */           }
/*     */           
/* 183 */           flushedAmount += localFlushedAmount;
/* 184 */           if (!buf.isReadable()) {
/* 185 */             done = true;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/* 190 */         in.progress(flushedAmount);
/*     */         
/* 192 */         if (done) {
/* 193 */           in.remove();
/*     */           continue;
/*     */         } 
/* 196 */         setOpWrite();
/*     */         break;
/*     */       } 
/* 199 */       if (msg instanceof FileRegion) {
/* 200 */         FileRegion region = (FileRegion)msg;
/* 201 */         boolean done = false;
/* 202 */         long flushedAmount = 0L;
/* 203 */         if (writeSpinCount == -1) {
/* 204 */           writeSpinCount = config().getWriteSpinCount();
/*     */         }
/* 206 */         for (int i = writeSpinCount - 1; i >= 0; i--) {
/* 207 */           long localFlushedAmount = doWriteFileRegion(region);
/* 208 */           if (localFlushedAmount == 0L) {
/*     */             break;
/*     */           }
/*     */           
/* 212 */           flushedAmount += localFlushedAmount;
/* 213 */           if (region.transfered() >= region.count()) {
/* 214 */             done = true;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/* 219 */         in.progress(flushedAmount);
/*     */         
/* 221 */         if (done) {
/* 222 */           in.remove();
/*     */           continue;
/*     */         } 
/* 225 */         setOpWrite();
/*     */         
/*     */         break;
/*     */       } 
/* 229 */       throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(msg));
/*     */     } 
/*     */   }
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
/*     */   protected final void setOpWrite() {
/* 255 */     SelectionKey key = selectionKey();
/* 256 */     int interestOps = key.interestOps();
/* 257 */     if ((interestOps & 0x4) == 0) {
/* 258 */       key.interestOps(interestOps | 0x4);
/*     */     }
/*     */   }
/*     */   
/*     */   protected final void clearOpWrite() {
/* 263 */     SelectionKey key = selectionKey();
/* 264 */     int interestOps = key.interestOps();
/* 265 */     if ((interestOps & 0x4) != 0)
/* 266 */       key.interestOps(interestOps & 0xFFFFFFFB); 
/*     */   }
/*     */   
/*     */   protected abstract long doWriteFileRegion(FileRegion paramFileRegion) throws Exception;
/*     */   
/*     */   protected abstract int doReadBytes(ByteBuf paramByteBuf) throws Exception;
/*     */   
/*     */   protected abstract int doWriteBytes(ByteBuf paramByteBuf) throws Exception;
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\nio\AbstractNioByteChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */