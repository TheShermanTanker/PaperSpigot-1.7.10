/*     */ package net.minecraft.util.io.netty.channel.udt.nio;
/*     */ 
/*     */ import com.barchart.udt.TypeUDT;
/*     */ import com.barchart.udt.nio.ChannelUDT;
/*     */ import com.barchart.udt.nio.ServerSocketChannelUDT;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.channels.SelectableChannel;
/*     */ import net.minecraft.util.io.netty.channel.ChannelConfig;
/*     */ import net.minecraft.util.io.netty.channel.ChannelException;
/*     */ import net.minecraft.util.io.netty.channel.ChannelOutboundBuffer;
/*     */ import net.minecraft.util.io.netty.channel.nio.AbstractNioMessageChannel;
/*     */ import net.minecraft.util.io.netty.channel.udt.DefaultUdtServerChannelConfig;
/*     */ import net.minecraft.util.io.netty.channel.udt.UdtChannel;
/*     */ import net.minecraft.util.io.netty.channel.udt.UdtChannelConfig;
/*     */ import net.minecraft.util.io.netty.channel.udt.UdtServerChannel;
/*     */ import net.minecraft.util.io.netty.channel.udt.UdtServerChannelConfig;
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
/*     */ public abstract class NioUdtAcceptorChannel
/*     */   extends AbstractNioMessageChannel
/*     */   implements UdtServerChannel
/*     */ {
/*  39 */   protected static final InternalLogger logger = InternalLoggerFactory.getInstance(NioUdtAcceptorChannel.class);
/*     */   
/*     */   private final UdtServerChannelConfig config;
/*     */ 
/*     */   
/*     */   protected NioUdtAcceptorChannel(ServerSocketChannelUDT channelUDT) {
/*  45 */     super(null, (SelectableChannel)channelUDT, 16);
/*     */     try {
/*  47 */       channelUDT.configureBlocking(false);
/*  48 */       this.config = (UdtServerChannelConfig)new DefaultUdtServerChannelConfig((UdtChannel)this, (ChannelUDT)channelUDT, true);
/*  49 */     } catch (Exception e) {
/*     */       try {
/*  51 */         channelUDT.close();
/*  52 */       } catch (Exception e2) {
/*  53 */         if (logger.isWarnEnabled()) {
/*  54 */           logger.warn("Failed to close channel.", e2);
/*     */         }
/*     */       } 
/*  57 */       throw new ChannelException("Failed to configure channel.", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected NioUdtAcceptorChannel(TypeUDT type) {
/*  62 */     this(NioUdtProvider.newAcceptorChannelUDT(type));
/*     */   }
/*     */ 
/*     */   
/*     */   public UdtServerChannelConfig config() {
/*  67 */     return this.config;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doBind(SocketAddress localAddress) throws Exception {
/*  72 */     javaChannel().socket().bind(localAddress, this.config.getBacklog());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doClose() throws Exception {
/*  77 */     javaChannel().close();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
/*  83 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doDisconnect() throws Exception {
/*  88 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doFinishConnect() throws Exception {
/*  93 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   protected boolean doWriteMessage(Object msg, ChannelOutboundBuffer in) throws Exception {
/*  98 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/* 103 */     return javaChannel().socket().isBound();
/*     */   }
/*     */ 
/*     */   
/*     */   protected ServerSocketChannelUDT javaChannel() {
/* 108 */     return (ServerSocketChannelUDT)super.javaChannel();
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress localAddress0() {
/* 113 */     return javaChannel().socket().getLocalSocketAddress();
/*     */   }
/*     */   
/*     */   public InetSocketAddress localAddress() {
/* 117 */     return (InetSocketAddress)super.localAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public InetSocketAddress remoteAddress() {
/* 122 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress remoteAddress0() {
/* 127 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channe\\udt\nio\NioUdtAcceptorChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */