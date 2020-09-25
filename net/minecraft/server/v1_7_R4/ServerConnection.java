/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.net.InetAddress;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import net.minecraft.util.com.google.common.util.concurrent.ThreadFactoryBuilder;
/*     */ import net.minecraft.util.io.netty.bootstrap.ServerBootstrap;
/*     */ import net.minecraft.util.io.netty.channel.ChannelFuture;
/*     */ import net.minecraft.util.io.netty.channel.ChannelHandler;
/*     */ import net.minecraft.util.io.netty.channel.EventLoopGroup;
/*     */ import net.minecraft.util.io.netty.channel.nio.NioEventLoopGroup;
/*     */ import net.minecraft.util.io.netty.channel.socket.nio.NioServerSocketChannel;
/*     */ import net.minecraft.util.io.netty.util.concurrent.GenericFutureListener;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.spigotmc.SpigotConfig;
/*     */ 
/*     */ public class ServerConnection {
/*  21 */   private static final Logger b = LogManager.getLogger();
/*  22 */   private static final NioEventLoopGroup c = new NioEventLoopGroup(0, (new ThreadFactoryBuilder()).setNameFormat("Netty IO #%d").setDaemon(true).build());
/*     */   private final MinecraftServer d;
/*     */   public volatile boolean a;
/*  25 */   private final List e = Collections.synchronizedList(new ArrayList());
/*  26 */   private final List f = Collections.synchronizedList(new ArrayList());
/*     */   
/*     */   public ServerConnection(MinecraftServer minecraftserver) {
/*  29 */     this.d = minecraftserver;
/*  30 */     this.a = true;
/*     */   }
/*     */   
/*     */   public void a(InetAddress inetaddress, int i) {
/*  34 */     List list = this.e;
/*     */     
/*  36 */     synchronized (this.e) {
/*  37 */       this.e.add(((ServerBootstrap)((ServerBootstrap)(new ServerBootstrap()).channel(NioServerSocketChannel.class)).childHandler((ChannelHandler)new ServerConnectionChannel(this)).group((EventLoopGroup)c).localAddress(inetaddress, i)).bind().syncUninterruptibly());
/*     */     } 
/*     */   }
/*     */   
/*     */   public void b() {
/*  42 */     this.a = false;
/*  43 */     Iterator<ChannelFuture> iterator = this.e.iterator();
/*     */     
/*  45 */     while (iterator.hasNext()) {
/*  46 */       ChannelFuture channelfuture = iterator.next();
/*     */       
/*  48 */       channelfuture.channel().close().syncUninterruptibly();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void c() {
/*  53 */     List list = this.f;
/*     */     
/*  55 */     synchronized (this.f) {
/*     */ 
/*     */       
/*  58 */       if (SpigotConfig.playerShuffle > 0 && MinecraftServer.currentTick % SpigotConfig.playerShuffle == 0)
/*     */       {
/*  60 */         Collections.shuffle(this.f);
/*     */       }
/*     */       
/*  63 */       Iterator<NetworkManager> iterator = this.f.iterator();
/*     */       
/*  65 */       while (iterator.hasNext()) {
/*  66 */         NetworkManager networkmanager = iterator.next();
/*     */         
/*  68 */         if (!networkmanager.isConnected()) {
/*     */ 
/*     */           
/*  71 */           if (networkmanager.preparing)
/*     */             continue; 
/*  73 */           iterator.remove();
/*  74 */           if (networkmanager.f() != null) {
/*  75 */             networkmanager.getPacketListener().a(networkmanager.f()); continue;
/*  76 */           }  if (networkmanager.getPacketListener() != null)
/*  77 */             networkmanager.getPacketListener().a(new ChatComponentText("Disconnected")); 
/*     */           continue;
/*     */         } 
/*     */         try {
/*  81 */           networkmanager.a();
/*  82 */         } catch (Exception exception) {
/*  83 */           if (networkmanager.c()) {
/*  84 */             CrashReport crashreport = CrashReport.a(exception, "Ticking memory connection");
/*  85 */             CrashReportSystemDetails crashreportsystemdetails = crashreport.a("Ticking connection");
/*     */             
/*  87 */             crashreportsystemdetails.a("Connection", new CrashReportServerConnection(this, networkmanager));
/*  88 */             throw new ReportedException(crashreport);
/*     */           } 
/*     */           
/*  91 */           b.warn("Failed to handle packet for " + networkmanager.getSocketAddress(), exception);
/*  92 */           ChatComponentText chatcomponenttext = new ChatComponentText("Internal server error");
/*     */           
/*  94 */           networkmanager.handle(new PacketPlayOutKickDisconnect(chatcomponenttext), new GenericFutureListener[] { new ServerConnectionFuture(this, networkmanager, chatcomponenttext) });
/*  95 */           networkmanager.g();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public MinecraftServer d() {
/* 103 */     return this.d;
/*     */   }
/*     */   
/*     */   static List a(ServerConnection serverconnection) {
/* 107 */     return serverconnection.f;
/*     */   }
/*     */   
/*     */   static MinecraftServer b(ServerConnection serverconnection) {
/* 111 */     return serverconnection.d;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ServerConnection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */