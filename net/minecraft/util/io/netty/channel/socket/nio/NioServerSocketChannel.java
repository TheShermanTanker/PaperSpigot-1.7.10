/*     */ package net.minecraft.util.io.netty.channel.socket.nio;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.channels.SelectableChannel;
/*     */ import java.nio.channels.ServerSocketChannel;
/*     */ import java.nio.channels.SocketChannel;
/*     */ import java.util.List;
/*     */ import net.minecraft.util.io.netty.channel.Channel;
/*     */ import net.minecraft.util.io.netty.channel.ChannelConfig;
/*     */ import net.minecraft.util.io.netty.channel.ChannelException;
/*     */ import net.minecraft.util.io.netty.channel.ChannelMetadata;
/*     */ import net.minecraft.util.io.netty.channel.ChannelOutboundBuffer;
/*     */ import net.minecraft.util.io.netty.channel.nio.AbstractNioMessageChannel;
/*     */ import net.minecraft.util.io.netty.channel.socket.DefaultServerSocketChannelConfig;
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
/*     */ public class NioServerSocketChannel
/*     */   extends AbstractNioMessageChannel
/*     */   implements ServerSocketChannel
/*     */ {
/*  42 */   private static final ChannelMetadata METADATA = new ChannelMetadata(false);
/*     */   
/*  44 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(NioServerSocketChannel.class);
/*     */   
/*     */   private static ServerSocketChannel newSocket() {
/*     */     try {
/*  48 */       return ServerSocketChannel.open();
/*  49 */     } catch (IOException e) {
/*  50 */       throw new ChannelException("Failed to open a server socket.", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private final ServerSocketChannelConfig config;
/*     */ 
/*     */ 
/*     */   
/*     */   public NioServerSocketChannel() {
/*  61 */     super(null, newSocket(), 16);
/*  62 */     this.config = (ServerSocketChannelConfig)new DefaultServerSocketChannelConfig(this, javaChannel().socket());
/*     */   }
/*     */ 
/*     */   
/*     */   public InetSocketAddress localAddress() {
/*  67 */     return (InetSocketAddress)super.localAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelMetadata metadata() {
/*  72 */     return METADATA;
/*     */   }
/*     */ 
/*     */   
/*     */   public ServerSocketChannelConfig config() {
/*  77 */     return this.config;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/*  82 */     return javaChannel().socket().isBound();
/*     */   }
/*     */ 
/*     */   
/*     */   public InetSocketAddress remoteAddress() {
/*  87 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected ServerSocketChannel javaChannel() {
/*  92 */     return (ServerSocketChannel)super.javaChannel();
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress localAddress0() {
/*  97 */     return javaChannel().socket().getLocalSocketAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doBind(SocketAddress localAddress) throws Exception {
/* 102 */     javaChannel().socket().bind(localAddress, this.config.getBacklog());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doClose() throws Exception {
/* 107 */     javaChannel().close();
/*     */   }
/*     */ 
/*     */   
/*     */   protected int doReadMessages(List<Object> buf) throws Exception {
/* 112 */     SocketChannel ch = javaChannel().accept();
/*     */     
/*     */     try {
/* 115 */       if (ch != null) {
/* 116 */         buf.add(new NioSocketChannel((Channel)this, ch));
/* 117 */         return 1;
/*     */       } 
/* 119 */     } catch (Throwable t) {
/* 120 */       logger.warn("Failed to create a new channel from an accepted socket.", t);
/*     */       
/*     */       try {
/* 123 */         ch.close();
/* 124 */       } catch (Throwable t2) {
/* 125 */         logger.warn("Failed to close a socket.", t2);
/*     */       } 
/*     */     } 
/*     */     
/* 129 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
/* 136 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doFinishConnect() throws Exception {
/* 141 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress remoteAddress0() {
/* 146 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doDisconnect() throws Exception {
/* 151 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean doWriteMessage(Object msg, ChannelOutboundBuffer in) throws Exception {
/* 156 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\socket\nio\NioServerSocketChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */