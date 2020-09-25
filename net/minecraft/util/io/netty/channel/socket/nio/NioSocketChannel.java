/*     */ package net.minecraft.util.io.netty.channel.socket.nio;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.SelectableChannel;
/*     */ import java.nio.channels.SocketChannel;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.util.io.netty.channel.Channel;
/*     */ import net.minecraft.util.io.netty.channel.ChannelConfig;
/*     */ import net.minecraft.util.io.netty.channel.ChannelException;
/*     */ import net.minecraft.util.io.netty.channel.ChannelFuture;
/*     */ import net.minecraft.util.io.netty.channel.ChannelMetadata;
/*     */ import net.minecraft.util.io.netty.channel.ChannelOutboundBuffer;
/*     */ import net.minecraft.util.io.netty.channel.ChannelPromise;
/*     */ import net.minecraft.util.io.netty.channel.FileRegion;
/*     */ import net.minecraft.util.io.netty.channel.nio.AbstractNioByteChannel;
/*     */ import net.minecraft.util.io.netty.channel.nio.NioEventLoop;
/*     */ import net.minecraft.util.io.netty.channel.socket.DefaultSocketChannelConfig;
/*     */ import net.minecraft.util.io.netty.channel.socket.ServerSocketChannel;
/*     */ import net.minecraft.util.io.netty.channel.socket.SocketChannel;
/*     */ import net.minecraft.util.io.netty.channel.socket.SocketChannelConfig;
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
/*     */ public class NioSocketChannel
/*     */   extends AbstractNioByteChannel
/*     */   implements SocketChannel
/*     */ {
/*  44 */   private static final ChannelMetadata METADATA = new ChannelMetadata(false);
/*     */   
/*     */   private static SocketChannel newSocket() {
/*     */     try {
/*  48 */       return SocketChannel.open();
/*  49 */     } catch (IOException e) {
/*  50 */       throw new ChannelException("Failed to open a socket.", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private final SocketChannelConfig config;
/*     */ 
/*     */   
/*     */   public NioSocketChannel() {
/*  60 */     this(newSocket());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NioSocketChannel(SocketChannel socket) {
/*  67 */     this((Channel)null, socket);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NioSocketChannel(Channel parent, SocketChannel socket) {
/*  77 */     super(parent, socket);
/*  78 */     this.config = (SocketChannelConfig)new DefaultSocketChannelConfig(this, socket.socket());
/*     */   }
/*     */ 
/*     */   
/*     */   public ServerSocketChannel parent() {
/*  83 */     return (ServerSocketChannel)super.parent();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelMetadata metadata() {
/*  88 */     return METADATA;
/*     */   }
/*     */ 
/*     */   
/*     */   public SocketChannelConfig config() {
/*  93 */     return this.config;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketChannel javaChannel() {
/*  98 */     return (SocketChannel)super.javaChannel();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/* 103 */     SocketChannel ch = javaChannel();
/* 104 */     return (ch.isOpen() && ch.isConnected());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInputShutdown() {
/* 109 */     return super.isInputShutdown();
/*     */   }
/*     */ 
/*     */   
/*     */   public InetSocketAddress localAddress() {
/* 114 */     return (InetSocketAddress)super.localAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public InetSocketAddress remoteAddress() {
/* 119 */     return (InetSocketAddress)super.remoteAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOutputShutdown() {
/* 124 */     return (javaChannel().socket().isOutputShutdown() || !isActive());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture shutdownOutput() {
/* 129 */     return shutdownOutput(newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture shutdownOutput(final ChannelPromise promise) {
/* 134 */     NioEventLoop nioEventLoop = eventLoop();
/* 135 */     if (nioEventLoop.inEventLoop()) {
/*     */       try {
/* 137 */         javaChannel().socket().shutdownOutput();
/* 138 */         promise.setSuccess();
/* 139 */       } catch (Throwable t) {
/* 140 */         promise.setFailure(t);
/*     */       } 
/*     */     } else {
/* 143 */       nioEventLoop.execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 146 */               NioSocketChannel.this.shutdownOutput(promise);
/*     */             }
/*     */           });
/*     */     } 
/* 150 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress localAddress0() {
/* 155 */     return javaChannel().socket().getLocalSocketAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress remoteAddress0() {
/* 160 */     return javaChannel().socket().getRemoteSocketAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doBind(SocketAddress localAddress) throws Exception {
/* 165 */     javaChannel().socket().bind(localAddress);
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
/* 170 */     if (localAddress != null) {
/* 171 */       javaChannel().socket().bind(localAddress);
/*     */     }
/*     */     
/* 174 */     boolean success = false;
/*     */     try {
/* 176 */       boolean connected = javaChannel().connect(remoteAddress);
/* 177 */       if (!connected) {
/* 178 */         selectionKey().interestOps(8);
/*     */       }
/* 180 */       success = true;
/* 181 */       return connected;
/*     */     } finally {
/* 183 */       if (!success) {
/* 184 */         doClose();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doFinishConnect() throws Exception {
/* 191 */     if (!javaChannel().finishConnect()) {
/* 192 */       throw new Error();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doDisconnect() throws Exception {
/* 198 */     doClose();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doClose() throws Exception {
/* 203 */     javaChannel().close();
/*     */   }
/*     */ 
/*     */   
/*     */   protected int doReadBytes(ByteBuf byteBuf) throws Exception {
/* 208 */     return byteBuf.writeBytes(javaChannel(), byteBuf.writableBytes());
/*     */   }
/*     */ 
/*     */   
/*     */   protected int doWriteBytes(ByteBuf buf) throws Exception {
/* 213 */     int expectedWrittenBytes = buf.readableBytes();
/* 214 */     int writtenBytes = buf.readBytes(javaChannel(), expectedWrittenBytes);
/* 215 */     return writtenBytes;
/*     */   }
/*     */ 
/*     */   
/*     */   protected long doWriteFileRegion(FileRegion region) throws Exception {
/* 220 */     long position = region.transfered();
/* 221 */     long writtenBytes = region.transferTo(javaChannel(), position);
/* 222 */     return writtenBytes;
/*     */   }
/*     */   
/*     */   protected void doWrite(ChannelOutboundBuffer in) throws Exception {
/*     */     int msgCount;
/*     */     long writtenBytes;
/*     */     while (true) {
/* 229 */       msgCount = in.size();
/* 230 */       if (msgCount <= 1) {
/* 231 */         super.doWrite(in);
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 236 */       ByteBuffer[] nioBuffers = in.nioBuffers();
/* 237 */       if (nioBuffers == null) {
/* 238 */         super.doWrite(in);
/*     */         
/*     */         return;
/*     */       } 
/* 242 */       int nioBufferCnt = in.nioBufferCount();
/* 243 */       long expectedWrittenBytes = in.nioBufferSize();
/*     */       
/* 245 */       SocketChannel ch = javaChannel();
/* 246 */       writtenBytes = 0L;
/* 247 */       boolean done = false; int j;
/* 248 */       for (j = config().getWriteSpinCount() - 1; j >= 0; j--) {
/* 249 */         long localWrittenBytes = ch.write(nioBuffers, 0, nioBufferCnt);
/* 250 */         if (localWrittenBytes == 0L) {
/*     */           break;
/*     */         }
/* 253 */         expectedWrittenBytes -= localWrittenBytes;
/* 254 */         writtenBytes += localWrittenBytes;
/* 255 */         if (expectedWrittenBytes == 0L) {
/* 256 */           done = true;
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 261 */       if (done) {
/*     */         
/* 263 */         for (j = msgCount; j > 0; j--) {
/* 264 */           in.remove();
/*     */         }
/*     */ 
/*     */         
/* 268 */         if (in.isEmpty()) {
/* 269 */           clearOpWrite();
/*     */           return;
/*     */         } 
/*     */         continue;
/*     */       } 
/*     */       break;
/*     */     } 
/* 276 */     for (int i = msgCount; i > 0; i--) {
/* 277 */       ByteBuf buf = (ByteBuf)in.current();
/* 278 */       int readerIndex = buf.readerIndex();
/* 279 */       int readableBytes = buf.writerIndex() - readerIndex;
/*     */       
/* 281 */       if (readableBytes < writtenBytes)
/* 282 */       { in.progress(readableBytes);
/* 283 */         in.remove();
/* 284 */         writtenBytes -= readableBytes; }
/* 285 */       else { if (readableBytes > writtenBytes) {
/* 286 */           buf.readerIndex(readerIndex + (int)writtenBytes);
/* 287 */           in.progress(writtenBytes);
/*     */           break;
/*     */         } 
/* 290 */         in.progress(readableBytes);
/* 291 */         in.remove();
/*     */         
/*     */         break; }
/*     */     
/*     */     } 
/* 296 */     setOpWrite();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\socket\nio\NioSocketChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */