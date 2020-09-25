/*     */ package net.minecraft.util.io.netty.channel.socket.nio;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.NetworkInterface;
/*     */ import java.net.SocketAddress;
/*     */ import java.net.SocketException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.DatagramChannel;
/*     */ import java.nio.channels.MembershipKey;
/*     */ import java.nio.channels.SelectableChannel;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBufAllocator;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBufHolder;
/*     */ import net.minecraft.util.io.netty.channel.AddressedEnvelope;
/*     */ import net.minecraft.util.io.netty.channel.ChannelConfig;
/*     */ import net.minecraft.util.io.netty.channel.ChannelException;
/*     */ import net.minecraft.util.io.netty.channel.ChannelFuture;
/*     */ import net.minecraft.util.io.netty.channel.ChannelMetadata;
/*     */ import net.minecraft.util.io.netty.channel.ChannelOption;
/*     */ import net.minecraft.util.io.netty.channel.ChannelOutboundBuffer;
/*     */ import net.minecraft.util.io.netty.channel.ChannelPromise;
/*     */ import net.minecraft.util.io.netty.channel.DefaultAddressedEnvelope;
/*     */ import net.minecraft.util.io.netty.channel.RecvByteBufAllocator;
/*     */ import net.minecraft.util.io.netty.channel.nio.AbstractNioMessageChannel;
/*     */ import net.minecraft.util.io.netty.channel.socket.DatagramChannel;
/*     */ import net.minecraft.util.io.netty.channel.socket.DatagramChannelConfig;
/*     */ import net.minecraft.util.io.netty.channel.socket.DatagramPacket;
/*     */ import net.minecraft.util.io.netty.channel.socket.InternetProtocolFamily;
/*     */ import net.minecraft.util.io.netty.util.internal.PlatformDependent;
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
/*     */ public final class NioDatagramChannel
/*     */   extends AbstractNioMessageChannel
/*     */   implements DatagramChannel
/*     */ {
/*  64 */   private static final ChannelMetadata METADATA = new ChannelMetadata(true);
/*     */   
/*     */   private final DatagramChannelConfig config;
/*  67 */   private final Map<InetAddress, List<MembershipKey>> memberships = new HashMap<InetAddress, List<MembershipKey>>();
/*     */   
/*     */   private RecvByteBufAllocator.Handle allocHandle;
/*     */ 
/*     */   
/*     */   private static DatagramChannel newSocket() {
/*     */     try {
/*  74 */       return DatagramChannel.open();
/*  75 */     } catch (IOException e) {
/*  76 */       throw new ChannelException("Failed to open a socket.", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static DatagramChannel newSocket(InternetProtocolFamily ipFamily) {
/*  81 */     if (ipFamily == null) {
/*  82 */       return newSocket();
/*     */     }
/*     */     
/*  85 */     if (PlatformDependent.javaVersion() < 7) {
/*  86 */       throw new UnsupportedOperationException();
/*     */     }
/*     */     
/*     */     try {
/*  90 */       return DatagramChannel.open(ProtocolFamilyConverter.convert(ipFamily));
/*  91 */     } catch (IOException e) {
/*  92 */       throw new ChannelException("Failed to open a socket.", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NioDatagramChannel() {
/* 100 */     this(newSocket());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NioDatagramChannel(InternetProtocolFamily ipFamily) {
/* 108 */     this(newSocket(ipFamily));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NioDatagramChannel(DatagramChannel socket) {
/* 115 */     super(null, socket, 1);
/* 116 */     this.config = (DatagramChannelConfig)new NioDatagramChannelConfig(this, socket);
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelMetadata metadata() {
/* 121 */     return METADATA;
/*     */   }
/*     */ 
/*     */   
/*     */   public DatagramChannelConfig config() {
/* 126 */     return this.config;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/* 131 */     DatagramChannel ch = javaChannel();
/* 132 */     return (ch.isOpen() && ((((Boolean)this.config.getOption(ChannelOption.DATAGRAM_CHANNEL_ACTIVE_ON_REGISTRATION)).booleanValue() && isRegistered()) || ch.socket().isBound()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isConnected() {
/* 139 */     return javaChannel().isConnected();
/*     */   }
/*     */ 
/*     */   
/*     */   protected DatagramChannel javaChannel() {
/* 144 */     return (DatagramChannel)super.javaChannel();
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress localAddress0() {
/* 149 */     return javaChannel().socket().getLocalSocketAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress remoteAddress0() {
/* 154 */     return javaChannel().socket().getRemoteSocketAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doBind(SocketAddress localAddress) throws Exception {
/* 159 */     javaChannel().socket().bind(localAddress);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
/* 165 */     if (localAddress != null) {
/* 166 */       javaChannel().socket().bind(localAddress);
/*     */     }
/*     */     
/* 169 */     boolean success = false;
/*     */     try {
/* 171 */       javaChannel().connect(remoteAddress);
/* 172 */       success = true;
/* 173 */       return true;
/*     */     } finally {
/* 175 */       if (!success) {
/* 176 */         doClose();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doFinishConnect() throws Exception {
/* 183 */     throw new Error();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doDisconnect() throws Exception {
/* 188 */     javaChannel().disconnect();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doClose() throws Exception {
/* 193 */     javaChannel().close();
/*     */   }
/*     */ 
/*     */   
/*     */   protected int doReadMessages(List<Object> buf) throws Exception {
/* 198 */     DatagramChannel ch = javaChannel();
/* 199 */     DatagramChannelConfig config = config();
/* 200 */     RecvByteBufAllocator.Handle allocHandle = this.allocHandle;
/* 201 */     if (allocHandle == null) {
/* 202 */       this.allocHandle = allocHandle = config.getRecvByteBufAllocator().newHandle();
/*     */     }
/* 204 */     ByteBuf data = allocHandle.allocate(config.getAllocator());
/* 205 */     boolean free = true;
/*     */     try {
/* 207 */       ByteBuffer nioData = data.nioBuffer(data.writerIndex(), data.writableBytes());
/*     */       
/* 209 */       InetSocketAddress remoteAddress = (InetSocketAddress)ch.receive(nioData);
/* 210 */       if (remoteAddress == null) {
/* 211 */         return 0;
/*     */       }
/*     */       
/* 214 */       int readBytes = nioData.position();
/* 215 */       data.writerIndex(data.writerIndex() + readBytes);
/* 216 */       allocHandle.record(readBytes);
/*     */       
/* 218 */       buf.add(new DatagramPacket(data, localAddress(), remoteAddress));
/* 219 */       free = false;
/* 220 */       return 1;
/* 221 */     } catch (Throwable cause) {
/* 222 */       PlatformDependent.throwException(cause);
/* 223 */       return -1;
/*     */     } finally {
/* 225 */       if (free)
/* 226 */         data.release(); 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected boolean doWriteMessage(Object msg, ChannelOutboundBuffer in) throws Exception {
/*     */     SocketAddress remoteAddress;
/*     */     Object m;
/*     */     ByteBuf data;
/*     */     ByteBuffer nioData;
/*     */     int writtenBytes;
/* 236 */     if (msg instanceof AddressedEnvelope) {
/*     */       
/* 238 */       AddressedEnvelope<Object, SocketAddress> envelope = (AddressedEnvelope<Object, SocketAddress>)msg;
/* 239 */       remoteAddress = envelope.recipient();
/* 240 */       m = envelope.content();
/*     */     } else {
/* 242 */       m = msg;
/* 243 */       remoteAddress = null;
/*     */     } 
/*     */     
/* 246 */     if (m instanceof ByteBufHolder) {
/* 247 */       data = ((ByteBufHolder)m).content();
/* 248 */     } else if (m instanceof ByteBuf) {
/* 249 */       data = (ByteBuf)m;
/*     */     } else {
/* 251 */       throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(msg));
/*     */     } 
/*     */     
/* 254 */     int dataLen = data.readableBytes();
/* 255 */     if (dataLen == 0) {
/* 256 */       return true;
/*     */     }
/*     */     
/* 259 */     ByteBufAllocator alloc = alloc();
/* 260 */     boolean needsCopy = (data.nioBufferCount() != 1);
/* 261 */     if (!needsCopy && 
/* 262 */       !data.isDirect() && alloc.isDirectBufferPooled()) {
/* 263 */       needsCopy = true;
/*     */     }
/*     */ 
/*     */     
/* 267 */     if (!needsCopy) {
/* 268 */       nioData = data.nioBuffer();
/*     */     } else {
/* 270 */       data = alloc.directBuffer(dataLen).writeBytes(data);
/* 271 */       nioData = data.nioBuffer();
/*     */     } 
/*     */ 
/*     */     
/* 275 */     if (remoteAddress != null) {
/* 276 */       writtenBytes = javaChannel().send(nioData, remoteAddress);
/*     */     } else {
/* 278 */       writtenBytes = javaChannel().write(nioData);
/*     */     } 
/*     */     
/* 281 */     boolean done = (writtenBytes > 0);
/* 282 */     if (needsCopy)
/*     */     {
/*     */       
/* 285 */       if (remoteAddress == null) {
/*     */         
/* 287 */         in.current(data);
/*     */       }
/* 289 */       else if (!done) {
/*     */         
/* 291 */         in.current(new DefaultAddressedEnvelope(data, remoteAddress));
/*     */       } else {
/*     */         
/* 294 */         in.current(data);
/*     */       } 
/*     */     }
/*     */     
/* 298 */     return done;
/*     */   }
/*     */ 
/*     */   
/*     */   public InetSocketAddress localAddress() {
/* 303 */     return (InetSocketAddress)super.localAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public InetSocketAddress remoteAddress() {
/* 308 */     return (InetSocketAddress)super.remoteAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture joinGroup(InetAddress multicastAddress) {
/* 313 */     return joinGroup(multicastAddress, newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture joinGroup(InetAddress multicastAddress, ChannelPromise promise) {
/*     */     try {
/* 319 */       return joinGroup(multicastAddress, NetworkInterface.getByInetAddress(localAddress().getAddress()), (InetAddress)null, promise);
/*     */ 
/*     */     
/*     */     }
/* 323 */     catch (SocketException e) {
/* 324 */       promise.setFailure(e);
/*     */       
/* 326 */       return (ChannelFuture)promise;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture joinGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface) {
/* 332 */     return joinGroup(multicastAddress, networkInterface, newPromise());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture joinGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface, ChannelPromise promise) {
/* 339 */     return joinGroup(multicastAddress.getAddress(), networkInterface, (InetAddress)null, promise);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture joinGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source) {
/* 345 */     return joinGroup(multicastAddress, networkInterface, source, newPromise());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture joinGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source, ChannelPromise promise) {
/* 352 */     if (PlatformDependent.javaVersion() >= 7) {
/* 353 */       if (multicastAddress == null) {
/* 354 */         throw new NullPointerException("multicastAddress");
/*     */       }
/*     */       
/* 357 */       if (networkInterface == null) {
/* 358 */         throw new NullPointerException("networkInterface");
/*     */       }
/*     */       
/*     */       try {
/*     */         MembershipKey key;
/* 363 */         if (source == null) {
/* 364 */           key = javaChannel().join(multicastAddress, networkInterface);
/*     */         } else {
/* 366 */           key = javaChannel().join(multicastAddress, networkInterface, source);
/*     */         } 
/*     */         
/* 369 */         synchronized (this) {
/* 370 */           List<MembershipKey> keys = this.memberships.get(multicastAddress);
/* 371 */           if (keys == null) {
/* 372 */             keys = new ArrayList<MembershipKey>();
/* 373 */             this.memberships.put(multicastAddress, keys);
/*     */           } 
/* 375 */           keys.add(key);
/*     */         } 
/*     */         
/* 378 */         promise.setSuccess();
/* 379 */       } catch (Throwable e) {
/* 380 */         promise.setFailure(e);
/*     */       } 
/*     */     } else {
/* 383 */       throw new UnsupportedOperationException();
/*     */     } 
/* 385 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture leaveGroup(InetAddress multicastAddress) {
/* 390 */     return leaveGroup(multicastAddress, newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture leaveGroup(InetAddress multicastAddress, ChannelPromise promise) {
/*     */     try {
/* 396 */       return leaveGroup(multicastAddress, NetworkInterface.getByInetAddress(localAddress().getAddress()), (InetAddress)null, promise);
/*     */     }
/* 398 */     catch (SocketException e) {
/* 399 */       promise.setFailure(e);
/*     */       
/* 401 */       return (ChannelFuture)promise;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture leaveGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface) {
/* 407 */     return leaveGroup(multicastAddress, networkInterface, newPromise());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture leaveGroup(InetSocketAddress multicastAddress, NetworkInterface networkInterface, ChannelPromise promise) {
/* 414 */     return leaveGroup(multicastAddress.getAddress(), networkInterface, (InetAddress)null, promise);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture leaveGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source) {
/* 420 */     return leaveGroup(multicastAddress, networkInterface, source, newPromise());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture leaveGroup(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress source, ChannelPromise promise) {
/* 427 */     if (PlatformDependent.javaVersion() < 7) {
/* 428 */       throw new UnsupportedOperationException();
/*     */     }
/* 430 */     if (multicastAddress == null) {
/* 431 */       throw new NullPointerException("multicastAddress");
/*     */     }
/* 433 */     if (networkInterface == null) {
/* 434 */       throw new NullPointerException("networkInterface");
/*     */     }
/*     */     
/* 437 */     synchronized (this) {
/* 438 */       if (this.memberships != null) {
/* 439 */         List<MembershipKey> keys = this.memberships.get(multicastAddress);
/* 440 */         if (keys != null) {
/* 441 */           Iterator<MembershipKey> keyIt = keys.iterator();
/*     */           
/* 443 */           while (keyIt.hasNext()) {
/* 444 */             MembershipKey key = keyIt.next();
/* 445 */             if (networkInterface.equals(key.networkInterface()) && ((
/* 446 */               source == null && key.sourceAddress() == null) || (source != null && source.equals(key.sourceAddress())))) {
/*     */               
/* 448 */               key.drop();
/* 449 */               keyIt.remove();
/*     */             } 
/*     */           } 
/*     */           
/* 453 */           if (keys.isEmpty()) {
/* 454 */             this.memberships.remove(multicastAddress);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 460 */     promise.setSuccess();
/* 461 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture block(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress sourceToBlock) {
/* 471 */     return block(multicastAddress, networkInterface, sourceToBlock, newPromise());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture block(InetAddress multicastAddress, NetworkInterface networkInterface, InetAddress sourceToBlock, ChannelPromise promise) {
/* 481 */     if (PlatformDependent.javaVersion() < 7) {
/* 482 */       throw new UnsupportedOperationException();
/*     */     }
/* 484 */     if (multicastAddress == null) {
/* 485 */       throw new NullPointerException("multicastAddress");
/*     */     }
/* 487 */     if (sourceToBlock == null) {
/* 488 */       throw new NullPointerException("sourceToBlock");
/*     */     }
/*     */     
/* 491 */     if (networkInterface == null) {
/* 492 */       throw new NullPointerException("networkInterface");
/*     */     }
/* 494 */     synchronized (this) {
/* 495 */       if (this.memberships != null) {
/* 496 */         List<MembershipKey> keys = this.memberships.get(multicastAddress);
/* 497 */         for (MembershipKey key : keys) {
/* 498 */           if (networkInterface.equals(key.networkInterface())) {
/*     */             try {
/* 500 */               key.block(sourceToBlock);
/* 501 */             } catch (IOException e) {
/* 502 */               promise.setFailure(e);
/*     */             } 
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/* 508 */     promise.setSuccess();
/* 509 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture block(InetAddress multicastAddress, InetAddress sourceToBlock) {
/* 519 */     return block(multicastAddress, sourceToBlock, newPromise());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ChannelFuture block(InetAddress multicastAddress, InetAddress sourceToBlock, ChannelPromise promise) {
/*     */     try {
/* 530 */       return block(multicastAddress, NetworkInterface.getByInetAddress(localAddress().getAddress()), sourceToBlock, promise);
/*     */ 
/*     */     
/*     */     }
/* 534 */     catch (SocketException e) {
/* 535 */       promise.setFailure(e);
/*     */       
/* 537 */       return (ChannelFuture)promise;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\socket\nio\NioDatagramChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */