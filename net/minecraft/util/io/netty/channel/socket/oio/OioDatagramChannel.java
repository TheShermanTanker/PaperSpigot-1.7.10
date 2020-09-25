/*     */ package net.minecraft.util.io.netty.channel.socket.oio;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.DatagramPacket;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.MulticastSocket;
/*     */ import java.net.NetworkInterface;
/*     */ import java.net.SocketAddress;
/*     */ import java.net.SocketException;
/*     */ import java.net.SocketTimeoutException;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBufHolder;
/*     */ import net.minecraft.util.io.netty.channel.AddressedEnvelope;
/*     */ import net.minecraft.util.io.netty.channel.ChannelConfig;
/*     */ import net.minecraft.util.io.netty.channel.ChannelException;
/*     */ import net.minecraft.util.io.netty.channel.ChannelFuture;
/*     */ import net.minecraft.util.io.netty.channel.ChannelMetadata;
/*     */ import net.minecraft.util.io.netty.channel.ChannelOption;
/*     */ import net.minecraft.util.io.netty.channel.ChannelOutboundBuffer;
/*     */ import net.minecraft.util.io.netty.channel.ChannelPromise;
/*     */ import net.minecraft.util.io.netty.channel.RecvByteBufAllocator;
/*     */ import net.minecraft.util.io.netty.channel.oio.AbstractOioMessageChannel;
/*     */ import net.minecraft.util.io.netty.channel.socket.DatagramChannel;
/*     */ import net.minecraft.util.io.netty.channel.socket.DatagramChannelConfig;
/*     */ import net.minecraft.util.io.netty.channel.socket.DatagramPacket;
/*     */ import net.minecraft.util.io.netty.channel.socket.DefaultDatagramChannelConfig;
/*     */ import net.minecraft.util.io.netty.util.internal.EmptyArrays;
/*     */ import net.minecraft.util.io.netty.util.internal.PlatformDependent;
/*     */ import net.minecraft.util.io.netty.util.internal.StringUtil;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OioDatagramChannel
/*     */   extends AbstractOioMessageChannel
/*     */   implements DatagramChannel
/*     */ {
/*  61 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(OioDatagramChannel.class);
/*     */   
/*  63 */   private static final ChannelMetadata METADATA = new ChannelMetadata(true);
/*     */   
/*     */   private final MulticastSocket socket;
/*     */   private final DatagramChannelConfig config;
/*  67 */   private final DatagramPacket tmpPacket = new DatagramPacket(EmptyArrays.EMPTY_BYTES, 0);
/*     */   
/*     */   private RecvByteBufAllocator.Handle allocHandle;
/*     */   
/*     */   private static MulticastSocket newSocket() {
/*     */     try {
/*  73 */       return new MulticastSocket(null);
/*  74 */     } catch (Exception e) {
/*  75 */       throw new ChannelException("failed to create a new socket", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OioDatagramChannel() {
/*  83 */     this(newSocket());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OioDatagramChannel(MulticastSocket socket) {
/*  92 */     super(null);
/*     */     
/*  94 */     boolean success = false;
/*     */     try {
/*  96 */       socket.setSoTimeout(1000);
/*  97 */       socket.setBroadcast(false);
/*  98 */       success = true;
/*  99 */     } catch (SocketException e) {
/* 100 */       throw new ChannelException("Failed to configure the datagram socket timeout.", e);
/*     */     } finally {
/*     */       
/* 103 */       if (!success) {
/* 104 */         socket.close();
/*     */       }
/*     */     } 
/*     */     
/* 108 */     this.socket = socket;
/* 109 */     this.config = (DatagramChannelConfig)new DefaultDatagramChannelConfig(this, socket);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelMetadata metadata() {
/* 114 */     return METADATA;
/*     */   }
/*     */ 
/*     */   
/*     */   public DatagramChannelConfig config() {
/* 119 */     return this.config;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOpen() {
/* 124 */     return !this.socket.isClosed();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/* 129 */     return ((isOpen() && ((Boolean)this.config.getOption(ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION)).booleanValue() && isRegistered()) || this.socket.isBound());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isConnected() {
/* 135 */     return this.socket.isConnected();
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress localAddress0() {
/* 140 */     return this.socket.getLocalSocketAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress remoteAddress0() {
/* 145 */     return this.socket.getRemoteSocketAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doBind(SocketAddress localAddress) throws Exception {
/* 150 */     this.socket.bind(localAddress);
/*     */   }
/*     */ 
/*     */   
/*     */   public InetSocketAddress localAddress() {
/* 155 */     return (InetSocketAddress)super.localAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public InetSocketAddress remoteAddress() {
/* 160 */     return (InetSocketAddress)super.remoteAddress();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
/* 166 */     if (localAddress != null) {
/* 167 */       this.socket.bind(localAddress);
/*     */     }
/*     */     
/* 170 */     boolean success = false;
/*     */     try {
/* 172 */       this.socket.connect(remoteAddress);
/* 173 */       success = true;
/*     */     } finally {
/* 175 */       if (!success) {
/*     */         try {
/* 177 */           this.socket.close();
/* 178 */         } catch (Throwable t) {
/* 179 */           logger.warn("Failed to close a socket.", t);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doDisconnect() throws Exception {
/* 187 */     this.socket.disconnect();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doClose() throws Exception {
/* 192 */     this.socket.close();
/*     */   }
/*     */ 
/*     */   
/*     */   protected int doReadMessages(List<Object> buf) throws Exception {
/* 197 */     DatagramChannelConfig config = config();
/* 198 */     RecvByteBufAllocator.Handle allocHandle = this.allocHandle;
/* 199 */     if (allocHandle == null) {
/* 200 */       this.allocHandle = allocHandle = config.getRecvByteBufAllocator().newHandle();
/*     */     }
/*     */     
/* 203 */     ByteBuf data = config.getAllocator().heapBuffer(allocHandle.guess());
/* 204 */     boolean free = true;
/*     */     try {
/* 206 */       this.tmpPacket.setData(data.array(), data.arrayOffset(), data.capacity());
/* 207 */       this.socket.receive(this.tmpPacket);
/*     */       
/* 209 */       InetSocketAddress remoteAddr = (InetSocketAddress)this.tmpPacket.getSocketAddress();
/* 210 */       if (remoteAddr == null) {
/* 211 */         remoteAddr = remoteAddress();
/*     */       }
/*     */       
/* 214 */       int readBytes = this.tmpPacket.getLength();
/* 215 */       allocHandle.record(readBytes);
/* 216 */       buf.add(new DatagramPacket(data.writerIndex(readBytes), localAddress(), remoteAddr));
/* 217 */       free = false;
/* 218 */       return 1;
/* 219 */     } catch (SocketTimeoutException e) {
/*     */       
/* 221 */       return 0;
/* 222 */     } catch (SocketException e) {
/* 223 */       if (!e.getMessage().toLowerCase(Locale.US).contains("socket closed")) {
/* 224 */         throw e;
/*     */       }
/* 226 */       return -1;
/* 227 */     } catch (Throwable cause) {
/* 228 */       PlatformDependent.throwException(cause);
/* 229 */       return -1;
/*     */     } finally {
/* 231 */       if (free)
/* 232 */         data.release(); 
/*     */     } 
/*     */   }
/*     */   protected void doWrite(ChannelOutboundBuffer in) throws Exception {
/*     */     while (true) {
/*     */       SocketAddress remoteAddress;
/*     */       Object m;
/*     */       ByteBuf data;
/* 240 */       Object o = in.current();
/* 241 */       if (o == null) {
/*     */         break;
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 248 */       if (o instanceof AddressedEnvelope) {
/*     */         
/* 250 */         AddressedEnvelope<Object, SocketAddress> envelope = (AddressedEnvelope<Object, SocketAddress>)o;
/* 251 */         remoteAddress = envelope.recipient();
/* 252 */         m = envelope.content();
/*     */       } else {
/* 254 */         m = o;
/* 255 */         remoteAddress = null;
/*     */       } 
/*     */       
/* 258 */       if (m instanceof ByteBufHolder) {
/* 259 */         data = ((ByteBufHolder)m).content();
/* 260 */       } else if (m instanceof ByteBuf) {
/* 261 */         data = (ByteBuf)m;
/*     */       } else {
/* 263 */         throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(o));
/*     */       } 
/*     */       
/* 266 */       int length = data.readableBytes();
/* 267 */       if (remoteAddress != null) {
/* 268 */         this.tmpPacket.setSocketAddress(remoteAddress);
/*     */       }
/* 270 */       if (data.hasArray()) {
/* 271 */         this.tmpPacket.setData(data.array(), data.arrayOffset() + data.readerIndex(), length);
/*     */       } else {
/* 273 */         byte[] tmp = new byte[length];
/* 274 */         data.getBytes(data.readerIndex(), tmp);
/* 275 */         this.tmpPacket.setData(tmp);
/*     */       } 
/* 277 */       this.socket.send(this.tmpPacket);
/* 278 */       in.remove();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture joinGroup(InetAddress multicastAddress) {
/* 284 */     return joinGroup(multicastAddress, newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture joinGroup(InetAddress multicastAddress, ChannelPromise promise) {
/* 289 */     ensureBound();
/*     */     try {
/* 291 */       this.socket.joinGroup(multicastAddress);
/* 292 */       promise.setSuccess();
/* 293 */     } catch (IOException e) {
/* 294 */       promise.setFailure(e);
/*     */     } 
/* 296 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture joinGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface) {
/* 301 */     return joinGroup(multicastAddress, networkInterface, newPromise());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture joinGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface, ChannelPromise promise) {
/* 308 */     ensureBound();
/*     */     try {
/* 310 */       this.socket.joinGroup(multicastAddress, networkInterface);
/* 311 */       promise.setSuccess();
/* 312 */     } catch (IOException e) {
/* 313 */       promise.setFailure(e);
/*     */     } 
/* 315 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture joinGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source) {
/* 321 */     return newFailedFuture(new UnsupportedOperationException());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture joinGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source, ChannelPromise promise) {
/* 328 */     promise.setFailure(new UnsupportedOperationException());
/* 329 */     return (ChannelFuture)promise;
/*     */   }
/*     */   
/*     */   private void ensureBound() {
/* 333 */     if (!isActive()) {
/* 334 */       throw new IllegalStateException(DatagramChannel.class.getName() + " must be bound to join a group.");
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture leaveGroup(InetAddress multicastAddress) {
/* 342 */     return leaveGroup(multicastAddress, newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture leaveGroup(InetAddress multicastAddress, ChannelPromise promise) {
/*     */     try {
/* 348 */       this.socket.leaveGroup(multicastAddress);
/* 349 */       promise.setSuccess();
/* 350 */     } catch (IOException e) {
/* 351 */       promise.setFailure(e);
/*     */     } 
/* 353 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture leaveGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface) {
/* 359 */     return leaveGroup(multicastAddress, networkInterface, newPromise());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture leaveGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface, ChannelPromise promise) {
/*     */     try {
/* 367 */       this.socket.leaveGroup(multicastAddress, networkInterface);
/* 368 */       promise.setSuccess();
/* 369 */     } catch (IOException e) {
/* 370 */       promise.setFailure(e);
/*     */     } 
/* 372 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture leaveGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source) {
/* 378 */     return newFailedFuture(new UnsupportedOperationException());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture leaveGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source, ChannelPromise promise) {
/* 385 */     promise.setFailure(new UnsupportedOperationException());
/* 386 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture block(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress sourceToBlock) {
/* 392 */     return newFailedFuture(new UnsupportedOperationException());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture block(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress sourceToBlock, ChannelPromise promise) {
/* 399 */     promise.setFailure(new UnsupportedOperationException());
/* 400 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture block(InetAddress multicastAddress, InetAddress sourceToBlock) {
/* 406 */     return newFailedFuture(new UnsupportedOperationException());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture block(InetAddress multicastAddress, InetAddress sourceToBlock, ChannelPromise promise) {
/* 412 */     promise.setFailure(new UnsupportedOperationException());
/* 413 */     return (ChannelFuture)promise;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\socket\oio\OioDatagramChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */