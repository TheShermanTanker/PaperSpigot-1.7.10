/*     */ package net.minecraft.util.io.netty.channel.sctp.oio;
/*     */ 
/*     */ import com.sun.nio.sctp.Association;
/*     */ import com.sun.nio.sctp.MessageInfo;
/*     */ import com.sun.nio.sctp.NotificationHandler;
/*     */ import com.sun.nio.sctp.SctpChannel;
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.SocketAddress;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.channels.SelectionKey;
/*     */ import java.nio.channels.Selector;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import net.minecraft.util.io.netty.buffer.ByteBuf;
/*     */ import net.minecraft.util.io.netty.channel.Channel;
/*     */ import net.minecraft.util.io.netty.channel.ChannelConfig;
/*     */ import net.minecraft.util.io.netty.channel.ChannelException;
/*     */ import net.minecraft.util.io.netty.channel.ChannelFuture;
/*     */ import net.minecraft.util.io.netty.channel.ChannelMetadata;
/*     */ import net.minecraft.util.io.netty.channel.ChannelOutboundBuffer;
/*     */ import net.minecraft.util.io.netty.channel.ChannelPromise;
/*     */ import net.minecraft.util.io.netty.channel.RecvByteBufAllocator;
/*     */ import net.minecraft.util.io.netty.channel.oio.AbstractOioMessageChannel;
/*     */ import net.minecraft.util.io.netty.channel.sctp.DefaultSctpChannelConfig;
/*     */ import net.minecraft.util.io.netty.channel.sctp.SctpChannel;
/*     */ import net.minecraft.util.io.netty.channel.sctp.SctpChannelConfig;
/*     */ import net.minecraft.util.io.netty.channel.sctp.SctpMessage;
/*     */ import net.minecraft.util.io.netty.channel.sctp.SctpNotificationHandler;
/*     */ import net.minecraft.util.io.netty.channel.sctp.SctpServerChannel;
/*     */ import net.minecraft.util.io.netty.util.internal.PlatformDependent;
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
/*     */ public class OioSctpChannel
/*     */   extends AbstractOioMessageChannel
/*     */   implements SctpChannel
/*     */ {
/*  63 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(OioSctpChannel.class);
/*     */ 
/*     */   
/*  66 */   private static final ChannelMetadata METADATA = new ChannelMetadata(false);
/*     */   
/*     */   private final SctpChannel ch;
/*     */   
/*     */   private final SctpChannelConfig config;
/*     */   
/*     */   private final Selector readSelector;
/*     */   
/*     */   private final Selector writeSelector;
/*     */   private final Selector connectSelector;
/*     */   private final NotificationHandler<?> notificationHandler;
/*     */   private RecvByteBufAllocator.Handle allocHandle;
/*     */   
/*     */   private static SctpChannel openChannel() {
/*     */     try {
/*  81 */       return SctpChannel.open();
/*  82 */     } catch (IOException e) {
/*  83 */       throw new ChannelException("Failed to open a sctp channel.", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OioSctpChannel() {
/*  91 */     this(openChannel());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OioSctpChannel(SctpChannel ch) {
/* 100 */     this((Channel)null, ch);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OioSctpChannel(Channel parent, SctpChannel ch) {
/* 111 */     super(parent);
/* 112 */     this.ch = ch;
/* 113 */     boolean success = false;
/*     */     try {
/* 115 */       ch.configureBlocking(false);
/* 116 */       this.readSelector = Selector.open();
/* 117 */       this.writeSelector = Selector.open();
/* 118 */       this.connectSelector = Selector.open();
/*     */       
/* 120 */       ch.register(this.readSelector, 1);
/* 121 */       ch.register(this.writeSelector, 4);
/* 122 */       ch.register(this.connectSelector, 8);
/*     */       
/* 124 */       this.config = (SctpChannelConfig)new DefaultSctpChannelConfig(this, ch);
/* 125 */       this.notificationHandler = (NotificationHandler<?>)new SctpNotificationHandler(this);
/* 126 */       success = true;
/* 127 */     } catch (Exception e) {
/* 128 */       throw new ChannelException("failed to initialize a sctp channel", e);
/*     */     } finally {
/* 130 */       if (!success) {
/*     */         try {
/* 132 */           ch.close();
/* 133 */         } catch (IOException e) {
/* 134 */           logger.warn("Failed to close a sctp channel.", e);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public InetSocketAddress localAddress() {
/* 142 */     return (InetSocketAddress)super.localAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public InetSocketAddress remoteAddress() {
/* 147 */     return (InetSocketAddress)super.remoteAddress();
/*     */   }
/*     */ 
/*     */   
/*     */   public SctpServerChannel parent() {
/* 152 */     return (SctpServerChannel)super.parent();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelMetadata metadata() {
/* 157 */     return METADATA;
/*     */   }
/*     */ 
/*     */   
/*     */   public SctpChannelConfig config() {
/* 162 */     return this.config;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOpen() {
/* 167 */     return this.ch.isOpen();
/*     */   }
/*     */ 
/*     */   
/*     */   protected int doReadMessages(List<Object> msgs) throws Exception {
/* 172 */     if (!this.readSelector.isOpen()) {
/* 173 */       return 0;
/*     */     }
/*     */     
/* 176 */     int readMessages = 0;
/*     */     
/* 178 */     int selectedKeys = this.readSelector.select(1000L);
/* 179 */     boolean keysSelected = (selectedKeys > 0);
/*     */     
/* 181 */     if (!keysSelected) {
/* 182 */       return readMessages;
/*     */     }
/*     */     
/* 185 */     Set<SelectionKey> reableKeys = this.readSelector.selectedKeys();
/*     */     try {
/* 187 */       for (SelectionKey ignored : reableKeys) {
/* 188 */         RecvByteBufAllocator.Handle allocHandle = this.allocHandle;
/* 189 */         if (allocHandle == null) {
/* 190 */           this.allocHandle = allocHandle = config().getRecvByteBufAllocator().newHandle();
/*     */         }
/* 192 */         ByteBuf buffer = allocHandle.allocate(config().getAllocator());
/* 193 */         boolean free = true;
/*     */         
/*     */         try {
/* 196 */           ByteBuffer data = buffer.nioBuffer(buffer.writerIndex(), buffer.writableBytes());
/* 197 */           MessageInfo messageInfo = this.ch.receive(data, (Object)null, this.notificationHandler);
/* 198 */           if (messageInfo == null) {
/* 199 */             return readMessages;
/*     */           }
/*     */           
/* 202 */           data.flip();
/* 203 */           msgs.add(new SctpMessage(messageInfo, buffer.writerIndex(buffer.writerIndex() + data.remaining())));
/* 204 */           free = false;
/* 205 */           readMessages++;
/* 206 */         } catch (Throwable cause) {
/* 207 */           PlatformDependent.throwException(cause);
/*     */         } finally {
/* 209 */           int bytesRead = buffer.readableBytes();
/* 210 */           allocHandle.record(bytesRead);
/* 211 */           if (free) {
/* 212 */             buffer.release();
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } finally {
/* 217 */       reableKeys.clear();
/*     */     } 
/* 219 */     return readMessages;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWrite(ChannelOutboundBuffer in) throws Exception {
/* 224 */     if (!this.writeSelector.isOpen()) {
/*     */       return;
/*     */     }
/* 227 */     int size = in.size();
/* 228 */     int selectedKeys = this.writeSelector.select(1000L);
/* 229 */     if (selectedKeys > 0) {
/* 230 */       Set<SelectionKey> writableKeys = this.writeSelector.selectedKeys();
/* 231 */       if (writableKeys.isEmpty()) {
/*     */         return;
/*     */       }
/* 234 */       Iterator<SelectionKey> writableKeysIt = writableKeys.iterator();
/* 235 */       int written = 0; do {
/*     */         ByteBuffer nioData;
/* 237 */         if (written == size) {
/*     */           return;
/*     */         }
/*     */         
/* 241 */         writableKeysIt.next();
/* 242 */         writableKeysIt.remove();
/*     */         
/* 244 */         SctpMessage packet = (SctpMessage)in.current();
/* 245 */         if (packet == null) {
/*     */           return;
/*     */         }
/*     */         
/* 249 */         ByteBuf data = packet.content();
/* 250 */         int dataLen = data.readableBytes();
/*     */ 
/*     */         
/* 253 */         if (data.nioBufferCount() != -1) {
/* 254 */           nioData = data.nioBuffer();
/*     */         } else {
/* 256 */           nioData = ByteBuffer.allocate(dataLen);
/* 257 */           data.getBytes(data.readerIndex(), nioData);
/* 258 */           nioData.flip();
/*     */         } 
/*     */         
/* 261 */         MessageInfo mi = MessageInfo.createOutgoing(association(), null, packet.streamIdentifier());
/* 262 */         mi.payloadProtocolID(packet.protocolIdentifier());
/* 263 */         mi.streamNumber(packet.streamIdentifier());
/*     */         
/* 265 */         this.ch.send(nioData, mi);
/* 266 */         written++;
/* 267 */         in.remove();
/*     */       }
/* 269 */       while (writableKeysIt.hasNext());
/*     */       return;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Association association() {
/*     */     try {
/* 279 */       return this.ch.association();
/* 280 */     } catch (IOException e) {
/* 281 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isActive() {
/* 287 */     return (isOpen() && association() != null);
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress localAddress0() {
/*     */     try {
/* 293 */       Iterator<SocketAddress> i = this.ch.getAllLocalAddresses().iterator();
/* 294 */       if (i.hasNext()) {
/* 295 */         return i.next();
/*     */       }
/* 297 */     } catch (IOException e) {}
/*     */ 
/*     */     
/* 300 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<InetSocketAddress> allLocalAddresses() {
/*     */     try {
/* 306 */       Set<SocketAddress> allLocalAddresses = this.ch.getAllLocalAddresses();
/* 307 */       Set<InetSocketAddress> addresses = new LinkedHashSet<InetSocketAddress>(allLocalAddresses.size());
/* 308 */       for (SocketAddress socketAddress : allLocalAddresses) {
/* 309 */         addresses.add((InetSocketAddress)socketAddress);
/*     */       }
/* 311 */       return addresses;
/* 312 */     } catch (Throwable t) {
/* 313 */       return Collections.emptySet();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected SocketAddress remoteAddress0() {
/*     */     try {
/* 320 */       Iterator<SocketAddress> i = this.ch.getRemoteAddresses().iterator();
/* 321 */       if (i.hasNext()) {
/* 322 */         return i.next();
/*     */       }
/* 324 */     } catch (IOException e) {}
/*     */ 
/*     */     
/* 327 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<InetSocketAddress> allRemoteAddresses() {
/*     */     try {
/* 333 */       Set<SocketAddress> allLocalAddresses = this.ch.getRemoteAddresses();
/* 334 */       Set<InetSocketAddress> addresses = new LinkedHashSet<InetSocketAddress>(allLocalAddresses.size());
/* 335 */       for (SocketAddress socketAddress : allLocalAddresses) {
/* 336 */         addresses.add((InetSocketAddress)socketAddress);
/*     */       }
/* 338 */       return addresses;
/* 339 */     } catch (Throwable t) {
/* 340 */       return Collections.emptySet();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doBind(SocketAddress localAddress) throws Exception {
/* 346 */     this.ch.bind(localAddress);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void doConnect(SocketAddress remoteAddress, SocketAddress localAddress) throws Exception {
/* 352 */     if (localAddress != null) {
/* 353 */       this.ch.bind(localAddress);
/*     */     }
/*     */     
/* 356 */     boolean success = false;
/*     */     try {
/* 358 */       this.ch.connect(remoteAddress);
/* 359 */       boolean finishConnect = false;
/* 360 */       while (!finishConnect) {
/* 361 */         if (this.connectSelector.select(1000L) >= 0) {
/* 362 */           Set<SelectionKey> selectionKeys = this.connectSelector.selectedKeys();
/* 363 */           for (SelectionKey key : selectionKeys) {
/* 364 */             if (key.isConnectable()) {
/* 365 */               selectionKeys.clear();
/* 366 */               finishConnect = true;
/*     */               break;
/*     */             } 
/*     */           } 
/* 370 */           selectionKeys.clear();
/*     */         } 
/*     */       } 
/* 373 */       success = this.ch.finishConnect();
/*     */     } finally {
/* 375 */       if (!success) {
/* 376 */         doClose();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doDisconnect() throws Exception {
/* 383 */     doClose();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doClose() throws Exception {
/* 388 */     closeSelector("read", this.readSelector);
/* 389 */     closeSelector("write", this.writeSelector);
/* 390 */     closeSelector("connect", this.connectSelector);
/* 391 */     this.ch.close();
/*     */   }
/*     */   
/*     */   private static void closeSelector(String selectorName, Selector selector) {
/*     */     try {
/* 396 */       selector.close();
/* 397 */     } catch (IOException e) {
/* 398 */       logger.warn("Failed to close a " + selectorName + " selector.", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture bindAddress(InetAddress localAddress) {
/* 404 */     return bindAddress(localAddress, newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture bindAddress(final InetAddress localAddress, final ChannelPromise promise) {
/* 409 */     if (eventLoop().inEventLoop()) {
/*     */       try {
/* 411 */         this.ch.bindAddress(localAddress);
/* 412 */         promise.setSuccess();
/* 413 */       } catch (Throwable t) {
/* 414 */         promise.setFailure(t);
/*     */       } 
/*     */     } else {
/* 417 */       eventLoop().execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 420 */               OioSctpChannel.this.bindAddress(localAddress, promise);
/*     */             }
/*     */           });
/*     */     } 
/* 424 */     return (ChannelFuture)promise;
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture unbindAddress(InetAddress localAddress) {
/* 429 */     return unbindAddress(localAddress, newPromise());
/*     */   }
/*     */ 
/*     */   
/*     */   public ChannelFuture unbindAddress(final InetAddress localAddress, final ChannelPromise promise) {
/* 434 */     if (eventLoop().inEventLoop()) {
/*     */       try {
/* 436 */         this.ch.unbindAddress(localAddress);
/* 437 */         promise.setSuccess();
/* 438 */       } catch (Throwable t) {
/* 439 */         promise.setFailure(t);
/*     */       } 
/*     */     } else {
/* 442 */       eventLoop().execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 445 */               OioSctpChannel.this.unbindAddress(localAddress, promise);
/*     */             }
/*     */           });
/*     */     } 
/* 449 */     return (ChannelFuture)promise;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\sctp\oio\OioSctpChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */