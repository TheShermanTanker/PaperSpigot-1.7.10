/*     */ package net.minecraft.util.io.netty.channel.socket.oio;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.Socket;
/*     */ import java.net.SocketAddress;
/*     */ import java.net.SocketTimeoutException;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.util.io.netty.channel.Channel;
/*     */ import net.minecraft.util.io.netty.channel.ChannelConfig;
/*     */ import net.minecraft.util.io.netty.channel.ChannelException;
/*     */ import net.minecraft.util.io.netty.channel.ChannelFuture;
/*     */ import net.minecraft.util.io.netty.channel.ChannelPromise;
/*     */ import net.minecraft.util.io.netty.channel.ConnectTimeoutException;
/*     */ import net.minecraft.util.io.netty.channel.EventLoop;
/*     */ import net.minecraft.util.io.netty.channel.oio.OioByteStreamChannel;
/*     */ import net.minecraft.util.io.netty.channel.socket.ServerSocketChannel;
/*     */ import net.minecraft.util.io.netty.channel.socket.SocketChannel;
/*     */ import net.minecraft.util.io.netty.channel.socket.SocketChannelConfig;
/*     */ import net.minecraft.util.io.netty.util.internal.logging.InternalLogger;
/*     */ import net.minecraft.util.io.netty.util.internal.logging.InternalLoggerFactory;
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
/*     */ public class OioSocketChannel
/*     */   extends OioByteStreamChannel
/*     */   implements SocketChannel
/*     */ {
/*  43 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(OioSocketChannel.class);
/*     */ 
/*     */   
/*     */   private final Socket socket;
/*     */ 
/*     */   
/*     */   private final OioSocketChannelConfig config;
/*     */ 
/*     */   
/*     */   public OioSocketChannel() {
/*  53 */     this(new Socket());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OioSocketChannel(Socket socket) {
/*  62 */     this((Channel)null, socket);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OioSocketChannel(Channel parent, Socket socket) {
/*  73 */     super(parent);
/*  74 */     this.socket = socket;
/*  75 */     this.config = new DefaultOioSocketChannelConfig(this, socket);
/*     */     
/*  77 */     boolean success = false;
/*     */     try {
/*  79 */       if (socket.isConnected()) {
/*  80 */         activate(socket.getInputStream(), socket.getOutputStream());
/*     */       }
/*  82 */       socket.setSoTimeout(1000);
/*  83 */       success = true;
/*  84 */     } catch (Exception e) {
/*  85 */       throw new ChannelException("failed to initialize a socket", e);
/*     */     } finally {
/*  87 */       if (!success) {
/*     */         try {
/*  89 */           socket.close();
/*  90 */         } catch (IOException e) {
/*  91 */           logger.warn("Failed to close a socket.", e);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ServerSocketChannel parent() {
/*  99 */     return (ServerSocketChannel)super.parent();
/*     */   }
/*     */ 
/*     */   
/*     */   public OioSocketChannelConfig config() {
/* 104 */     return this.config;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOpen() {
/* 109 */     return !this.socket.isClosed();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/* 114 */     return (!this.socket.isClosed() && this.socket.isConnected());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInputShutdown() {
/* 119 */     return super.isInputShutdown();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOutputShutdown() {
/* 124 */     return (this.socket.isOutputShutdown() || !isActive());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture shutdownOutput() {
/* 129 */     return shutdownOutput(newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   protected int doReadBytes(ByteBuf buf) throws Exception {
/* 134 */     if (this.socket.isClosed()) {
/* 135 */       return -1;
/*     */     }
/*     */     try {
/* 138 */       return super.doReadBytes(buf);
/* 139 */     } catch (SocketTimeoutException e) {
/* 140 */       return 0;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture shutdownOutput(final ChannelPromise future) {
/* 146 */     EventLoop loop = eventLoop();
/* 147 */     if (loop.inEventLoop()) {
/*     */       try {
/* 149 */         this.socket.shutdownOutput();
/* 150 */         future.setSuccess();
/* 151 */       } catch (Throwable t) {
/* 152 */         future.setFailure(t);
/*     */       } 
/*     */     } else {
/* 155 */       loop.execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 158 */               OioSocketChannel.this.shutdownOutput(future);
/*     */             }
/*     */           });
/*     */     } 
/* 162 */     return (ChannelFuture)future;
/*     */   }
/*     */ 
/*     */   
/*     */   public InetSocketAddress localAddress() {
/* 167 */     return (InetSocketAddress)super.localAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public InetSocketAddress remoteAddress() {
/* 172 */     return (InetSocketAddress)super.remoteAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress localAddress0() {
/* 177 */     return this.socket.getLocalSocketAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress remoteAddress0() {
/* 182 */     return this.socket.getRemoteSocketAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doBind(SocketAddress localAddress) throws Exception {
/* 187 */     this.socket.bind(localAddress);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
/* 193 */     if (localAddress != null) {
/* 194 */       this.socket.bind(localAddress);
/*     */     }
/*     */     
/* 197 */     boolean success = false;
/*     */     try {
/* 199 */       this.socket.connect(remoteAddress, config().getConnectTimeoutMillis());
/* 200 */       activate(this.socket.getInputStream(), this.socket.getOutputStream());
/* 201 */       success = true;
/* 202 */     } catch (SocketTimeoutException e) {
/* 203 */       ConnectTimeoutException cause = new ConnectTimeoutException("connection timed out: " + remoteAddress);
/* 204 */       cause.setStackTrace(e.getStackTrace());
/* 205 */       throw cause;
/*     */     } finally {
/* 207 */       if (!success) {
/* 208 */         doClose();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doDisconnect() throws Exception {
/* 215 */     doClose();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doClose() throws Exception {
/* 220 */     this.socket.close();
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean checkInputShutdown() {
/* 225 */     if (isInputShutdown()) {
/*     */       try {
/* 227 */         Thread.sleep(config().getSoTimeout());
/* 228 */       } catch (Throwable e) {}
/*     */ 
/*     */       
/* 231 */       return true;
/*     */     } 
/* 233 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\socket\oio\OioSocketChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */