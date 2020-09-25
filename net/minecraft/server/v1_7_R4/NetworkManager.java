/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import java.net.SocketAddress;
/*     */ import java.util.Queue;
/*     */ import java.util.UUID;
/*     */ import javax.crypto.SecretKey;
/*     */ import net.minecraft.util.com.google.common.collect.Queues;
/*     */ import net.minecraft.util.com.google.common.util.concurrent.ThreadFactoryBuilder;
/*     */ import net.minecraft.util.com.mojang.authlib.properties.Property;
/*     */ import net.minecraft.util.io.netty.channel.Channel;
/*     */ import net.minecraft.util.io.netty.channel.ChannelFutureListener;
/*     */ import net.minecraft.util.io.netty.channel.ChannelHandler;
/*     */ import net.minecraft.util.io.netty.channel.ChannelHandlerContext;
/*     */ import net.minecraft.util.io.netty.channel.SimpleChannelInboundHandler;
/*     */ import net.minecraft.util.io.netty.channel.nio.NioEventLoopGroup;
/*     */ import net.minecraft.util.io.netty.util.AttributeKey;
/*     */ import net.minecraft.util.io.netty.util.concurrent.GenericFutureListener;
/*     */ import net.minecraft.util.org.apache.commons.lang3.Validate;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.Marker;
/*     */ import org.apache.logging.log4j.MarkerManager;
/*     */ import org.github.paperspigot.PaperSpigotConfig;
/*     */ import org.spigotmc.SpigotCompressor;
/*     */ import org.spigotmc.SpigotDecompressor;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NetworkManager
/*     */   extends SimpleChannelInboundHandler
/*     */ {
/*  33 */   private static final Logger i = LogManager.getLogger();
/*  34 */   public static final Marker a = MarkerManager.getMarker("NETWORK");
/*  35 */   public static final Marker b = MarkerManager.getMarker("NETWORK_PACKETS", a);
/*  36 */   public static final Marker c = MarkerManager.getMarker("NETWORK_STAT", a);
/*  37 */   public static final AttributeKey d = new AttributeKey("protocol");
/*  38 */   public static final AttributeKey e = new AttributeKey("receivable_packets");
/*  39 */   public static final AttributeKey f = new AttributeKey("sendable_packets");
/*  40 */   public static final NioEventLoopGroup g = new NioEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty Client IO #%d").setDaemon(true).build());
/*  41 */   public static final NetworkStatistics h = new NetworkStatistics();
/*     */   private final boolean j;
/*  43 */   private final Queue k = Queues.newConcurrentLinkedQueue();
/*  44 */   private final Queue l = Queues.newConcurrentLinkedQueue();
/*     */   
/*     */   private Channel m;
/*     */   
/*     */   public SocketAddress n;
/*     */   
/*     */   public UUID spoofedUUID;
/*     */   public Property[] spoofedProfile;
/*     */   public boolean preparing = true;
/*     */   private PacketListener o;
/*     */   private EnumProtocol p;
/*     */   private IChatBaseComponent q;
/*     */   private boolean r;
/*  57 */   public static final AttributeKey<Integer> protocolVersion = new AttributeKey("protocol_version");
/*  58 */   public static final ImmutableSet<Integer> SUPPORTED_VERSIONS = ImmutableSet.of(Integer.valueOf(4), Integer.valueOf(5), Integer.valueOf(47));
/*     */   public static final int CURRENT_VERSION = 5;
/*     */   
/*     */   public static int getVersion(Channel attr) {
/*  62 */     Integer ver = (Integer)attr.attr(protocolVersion).get();
/*  63 */     return (ver != null) ? ver.intValue() : 5;
/*     */   }
/*     */   
/*     */   public int getVersion() {
/*  67 */     return getVersion(this.m);
/*     */   }
/*     */ 
/*     */   
/*     */   public NetworkManager(boolean flag) {
/*  72 */     this.j = flag;
/*     */   }
/*     */   
/*     */   public void channelActive(ChannelHandlerContext channelhandlercontext) throws Exception {
/*  76 */     super.channelActive(channelhandlercontext);
/*  77 */     this.m = channelhandlercontext.channel();
/*  78 */     this.n = this.m.remoteAddress();
/*     */     
/*  80 */     this.preparing = false;
/*     */     
/*  82 */     a(EnumProtocol.HANDSHAKING);
/*     */   }
/*     */   
/*     */   public void a(EnumProtocol enumprotocol) {
/*  86 */     this.p = (EnumProtocol)this.m.attr(d).getAndSet(enumprotocol);
/*  87 */     this.m.attr(e).set(enumprotocol.a(this.j));
/*  88 */     this.m.attr(f).set(enumprotocol.b(this.j));
/*  89 */     this.m.config().setAutoRead(true);
/*  90 */     i.debug("Enabled auto read");
/*     */   }
/*     */   
/*     */   public void channelInactive(ChannelHandlerContext channelhandlercontext) {
/*  94 */     close(new ChatMessage("disconnect.endOfStream", new Object[0]));
/*     */   }
/*     */ 
/*     */   
/*     */   public void exceptionCaught(ChannelHandlerContext channelhandlercontext, Throwable throwable) {
/*     */     ChatMessage chatmessage;
/* 100 */     if (throwable instanceof net.minecraft.util.io.netty.handler.timeout.TimeoutException) {
/* 101 */       chatmessage = new ChatMessage("disconnect.timeout", new Object[0]);
/*     */     } else {
/* 103 */       chatmessage = new ChatMessage("disconnect.genericReason", new Object[] { "Internal Exception: " + throwable });
/*     */     } 
/*     */     
/* 106 */     close(chatmessage);
/* 107 */     if (MinecraftServer.getServer().isDebugging()) throwable.printStackTrace(); 
/*     */   }
/*     */   
/*     */   protected void a(ChannelHandlerContext channelhandlercontext, Packet packet) {
/* 111 */     if (this.m.isOpen()) {
/* 112 */       if (packet.a()) {
/* 113 */         packet.handle(this.o);
/*     */       } else {
/* 115 */         this.k.add(packet);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public void a(PacketListener packetlistener) {
/* 121 */     Validate.notNull(packetlistener, "packetListener", new Object[0]);
/* 122 */     i.debug("Set listener of {} to {}", new Object[] { this, packetlistener });
/* 123 */     this.o = packetlistener;
/*     */   }
/*     */   
/*     */   public void handle(Packet packet, GenericFutureListener... agenericfuturelistener) {
/* 127 */     if (this.m != null && this.m.isOpen()) {
/* 128 */       i();
/* 129 */       b(packet, agenericfuturelistener);
/*     */     } else {
/* 131 */       this.l.add(new QueuedPacket(packet, agenericfuturelistener));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void b(Packet packet, GenericFutureListener[] agenericfuturelistener) {
/* 136 */     EnumProtocol enumprotocol = EnumProtocol.a(packet);
/* 137 */     EnumProtocol enumprotocol1 = (EnumProtocol)this.m.attr(d).get();
/*     */     
/* 139 */     if (enumprotocol1 != enumprotocol) {
/* 140 */       i.debug("Disabled auto read");
/* 141 */       this.m.config().setAutoRead(false);
/*     */     } 
/*     */     
/* 144 */     if (this.m.eventLoop().inEventLoop()) {
/* 145 */       if (enumprotocol != enumprotocol1) {
/* 146 */         a(enumprotocol);
/*     */       }
/*     */       
/* 149 */       this.m.writeAndFlush(packet).addListeners(agenericfuturelistener).addListener((GenericFutureListener)ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
/*     */     } else {
/* 151 */       this.m.eventLoop().execute(new QueuedProtocolSwitch(this, enumprotocol, enumprotocol1, packet, agenericfuturelistener));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void i() {
/* 156 */     if (this.m != null && this.m.isOpen()) {
/*     */       QueuedPacket queuedpacket;
/*     */       
/* 159 */       while ((queuedpacket = this.l.poll()) != null) {
/* 160 */         b(QueuedPacket.a(queuedpacket), QueuedPacket.b(queuedpacket));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void a() {
/* 167 */     i();
/* 168 */     EnumProtocol enumprotocol = (EnumProtocol)this.m.attr(d).get();
/*     */     
/* 170 */     if (this.p != enumprotocol) {
/* 171 */       if (this.p != null) {
/* 172 */         this.o.a(this.p, enumprotocol);
/*     */       }
/*     */       
/* 175 */       this.p = enumprotocol;
/*     */     } 
/*     */     
/* 178 */     if (this.o != null) {
/*     */       Packet packet;
/*     */       
/* 181 */       for (int i = PaperSpigotConfig.maxPacketsPerPlayer; (packet = this.k.poll()) != null && i >= 0; i--) {
/*     */ 
/*     */ 
/*     */         
/* 185 */         if (isConnected() && this.m.config().isAutoRead())
/*     */         {
/*     */ 
/*     */           
/* 189 */           packet.handle(this.o);
/*     */         }
/*     */       } 
/* 192 */       this.o.a();
/*     */     } 
/*     */     
/* 195 */     this.m.flush();
/*     */   }
/*     */   
/*     */   public SocketAddress getSocketAddress() {
/* 199 */     return this.n;
/*     */   }
/*     */ 
/*     */   
/*     */   public void close(IChatBaseComponent ichatbasecomponent) {
/* 204 */     this.preparing = false;
/* 205 */     this.k.clear();
/* 206 */     this.l.clear();
/*     */     
/* 208 */     if (this.m.isOpen()) {
/* 209 */       this.m.close();
/* 210 */       this.q = ichatbasecomponent;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean c() {
/* 215 */     return (this.m instanceof net.minecraft.util.io.netty.channel.local.LocalChannel || this.m instanceof net.minecraft.util.io.netty.channel.local.LocalServerChannel);
/*     */   }
/*     */   
/*     */   public void a(SecretKey secretkey) {
/* 219 */     this.m.pipeline().addBefore("splitter", "decrypt", (ChannelHandler)new PacketDecrypter(MinecraftEncryption.a(2, secretkey)));
/* 220 */     this.m.pipeline().addBefore("prepender", "encrypt", (ChannelHandler)new PacketEncrypter(MinecraftEncryption.a(1, secretkey)));
/* 221 */     this.r = true;
/*     */   }
/*     */   
/*     */   public boolean isConnected() {
/* 225 */     return (this.m != null && this.m.isOpen());
/*     */   }
/*     */   
/*     */   public PacketListener getPacketListener() {
/* 229 */     return this.o;
/*     */   }
/*     */   
/*     */   public IChatBaseComponent f() {
/* 233 */     return this.q;
/*     */   }
/*     */   
/*     */   public void g() {
/* 237 */     this.m.config().setAutoRead(false);
/*     */   }
/*     */   
/*     */   protected void channelRead0(ChannelHandlerContext channelhandlercontext, Object object) {
/* 241 */     a(channelhandlercontext, (Packet)object);
/*     */   }
/*     */   
/*     */   static Channel a(NetworkManager networkmanager) {
/* 245 */     return networkmanager.m;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SocketAddress getRawAddress() {
/* 251 */     return this.m.remoteAddress();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void enableCompression() {
/* 259 */     if (this.m.pipeline().get("protocol_lib_decoder") != null) {
/* 260 */       this.m.pipeline().addBefore("protocol_lib_decoder", "decompress", (ChannelHandler)new SpigotDecompressor());
/*     */     } else {
/* 262 */       this.m.pipeline().addBefore("decoder", "decompress", (ChannelHandler)new SpigotDecompressor());
/*     */     } 
/*     */     
/* 265 */     this.m.pipeline().addBefore("encoder", "compress", (ChannelHandler)new SpigotCompressor());
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\NetworkManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */