/*     */ package net.minecraft.util.io.netty.channel.socket.oio;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.ServerSocket;
/*     */ import java.net.Socket;
/*     */ import java.net.SocketAddress;
/*     */ import java.net.SocketTimeoutException;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.locks.Lock;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import net.minecraft.util.io.netty.channel.Channel;
/*     */ import net.minecraft.util.io.netty.channel.ChannelConfig;
/*     */ import net.minecraft.util.io.netty.channel.ChannelException;
/*     */ import net.minecraft.util.io.netty.channel.ChannelMetadata;
/*     */ import net.minecraft.util.io.netty.channel.ChannelOutboundBuffer;
/*     */ import net.minecraft.util.io.netty.channel.oio.AbstractOioMessageChannel;
/*     */ import net.minecraft.util.io.netty.channel.socket.ServerSocketChannel;
/*     */ import net.minecraft.util.io.netty.channel.socket.ServerSocketChannelConfig;
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
/*     */ 
/*     */ public class OioServerSocketChannel
/*     */   extends AbstractOioMessageChannel
/*     */   implements ServerSocketChannel
/*     */ {
/*  44 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(OioServerSocketChannel.class);
/*     */ 
/*     */   
/*  47 */   private static final ChannelMetadata METADATA = new ChannelMetadata(false);
/*     */   
/*     */   private static ServerSocket newServerSocket() {
/*     */     try {
/*  51 */       return new ServerSocket();
/*  52 */     } catch (IOException e) {
/*  53 */       throw new ChannelException("failed to create a server socket", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   final ServerSocket socket;
/*  58 */   final Lock shutdownLock = new ReentrantLock();
/*     */ 
/*     */   
/*     */   private final OioServerSocketChannelConfig config;
/*     */ 
/*     */   
/*     */   public OioServerSocketChannel() {
/*  65 */     this(newServerSocket());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OioServerSocketChannel(ServerSocket socket) {
/*  74 */     super(null);
/*  75 */     if (socket == null) {
/*  76 */       throw new NullPointerException("socket");
/*     */     }
/*     */     
/*  79 */     boolean success = false;
/*     */     try {
/*  81 */       socket.setSoTimeout(1000);
/*  82 */       success = true;
/*  83 */     } catch (IOException e) {
/*  84 */       throw new ChannelException("Failed to set the server socket timeout.", e);
/*     */     } finally {
/*     */       
/*  87 */       if (!success) {
/*     */         try {
/*  89 */           socket.close();
/*  90 */         } catch (IOException e) {
/*  91 */           if (logger.isWarnEnabled()) {
/*  92 */             logger.warn("Failed to close a partially initialized socket.", e);
/*     */           }
/*     */         } 
/*     */       }
/*     */     } 
/*     */     
/*  98 */     this.socket = socket;
/*  99 */     this.config = new DefaultOioServerSocketChannelConfig(this, socket);
/*     */   }
/*     */ 
/*     */   
/*     */   public InetSocketAddress localAddress() {
/* 104 */     return (InetSocketAddress)super.localAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelMetadata metadata() {
/* 109 */     return METADATA;
/*     */   }
/*     */ 
/*     */   
/*     */   public OioServerSocketChannelConfig config() {
/* 114 */     return this.config;
/*     */   }
/*     */ 
/*     */   
/*     */   public InetSocketAddress remoteAddress() {
/* 119 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOpen() {
/* 124 */     return !this.socket.isClosed();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/* 129 */     return (isOpen() && this.socket.isBound());
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress localAddress0() {
/* 134 */     return this.socket.getLocalSocketAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doBind(SocketAddress localAddress) throws Exception {
/* 139 */     this.socket.bind(localAddress, this.config.getBacklog());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doClose() throws Exception {
/* 144 */     this.socket.close();
/*     */   }
/*     */ 
/*     */   
/*     */   protected int doReadMessages(List<Object> buf) throws Exception {
/* 149 */     if (this.socket.isClosed()) {
/* 150 */       return -1;
/*     */     }
/*     */     
/*     */     try {
/* 154 */       Socket s = this.socket.accept();
/*     */       try {
/* 156 */         if (s != null) {
/* 157 */           buf.add(new OioSocketChannel((Channel)this, s));
/* 158 */           return 1;
/*     */         } 
/* 160 */       } catch (Throwable t) {
/* 161 */         logger.warn("Failed to create a new channel from an accepted socket.", t);
/* 162 */         if (s != null) {
/*     */           try {
/* 164 */             s.close();
/* 165 */           } catch (Throwable t2) {
/* 166 */             logger.warn("Failed to close a socket.", t2);
/*     */           } 
/*     */         }
/*     */       } 
/* 170 */     } catch (SocketTimeoutException e) {}
/*     */ 
/*     */     
/* 173 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWrite(ChannelOutboundBuffer in) throws Exception {
/* 178 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
/* 184 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress remoteAddress0() {
/* 189 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doDisconnect() throws Exception {
/* 194 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\socket\oio\OioServerSocketChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */