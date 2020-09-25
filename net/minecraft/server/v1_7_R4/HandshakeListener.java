/*     */ package net.minecraft.server.v1_7_R4;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import net.minecraft.util.com.mojang.authlib.properties.Property;
/*     */ import net.minecraft.util.com.mojang.util.UUIDTypeAdapter;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.Gson;
/*     */ import org.spigotmc.SpigotConfig;
/*     */ 
/*     */ public class HandshakeListener implements PacketHandshakingInListener {
/*  14 */   private static final Gson gson = new Gson();
/*     */   
/*  16 */   private static final HashMap<InetAddress, Long> throttleTracker = new HashMap<InetAddress, Long>();
/*  17 */   private static int throttleCounter = 0;
/*     */   
/*     */   private final MinecraftServer a;
/*     */   
/*     */   private final NetworkManager b;
/*     */   
/*     */   public HandshakeListener(MinecraftServer minecraftserver, NetworkManager networkmanager) {
/*  24 */     this.a = minecraftserver;
/*  25 */     this.b = networkmanager;
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(PacketHandshakingInSetProtocol packethandshakinginsetprotocol) {
/*  30 */     if (NetworkManager.SUPPORTED_VERSIONS.contains(Integer.valueOf(packethandshakinginsetprotocol.d())))
/*     */     {
/*  32 */       NetworkManager.a(this.b).attr(NetworkManager.protocolVersion).set(Integer.valueOf(packethandshakinginsetprotocol.d()));
/*     */     }
/*     */     
/*  35 */     switch (ProtocolOrdinalWrapper.a[packethandshakinginsetprotocol.c().ordinal()]) {
/*     */       case 1:
/*  37 */         this.b.a(EnumProtocol.LOGIN);
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/*  42 */           long currentTime = System.currentTimeMillis();
/*  43 */           long connectionThrottle = (MinecraftServer.getServer()).server.getConnectionThrottle();
/*  44 */           InetAddress address = ((InetSocketAddress)this.b.getSocketAddress()).getAddress();
/*     */           
/*  46 */           synchronized (throttleTracker) {
/*  47 */             if (throttleTracker.containsKey(address) && !"127.0.0.1".equals(address.getHostAddress()) && currentTime - ((Long)throttleTracker.get(address)).longValue() < connectionThrottle) {
/*  48 */               throttleTracker.put(address, Long.valueOf(currentTime));
/*  49 */               ChatComponentText chatcomponenttext = new ChatComponentText("Connection throttled! Please wait before reconnecting.");
/*  50 */               this.b.handle(new PacketLoginOutDisconnect(chatcomponenttext), new net.minecraft.util.io.netty.util.concurrent.GenericFutureListener[0]);
/*  51 */               this.b.close(chatcomponenttext);
/*     */               
/*     */               return;
/*     */             } 
/*  55 */             throttleTracker.put(address, Long.valueOf(currentTime));
/*  56 */             throttleCounter++;
/*  57 */             if (throttleCounter > 200) {
/*  58 */               throttleCounter = 0;
/*     */ 
/*     */               
/*  61 */               Iterator<Map.Entry<InetAddress, Long>> iter = throttleTracker.entrySet().iterator();
/*  62 */               while (iter.hasNext()) {
/*  63 */                 Map.Entry<InetAddress, Long> entry = iter.next();
/*  64 */                 if (((Long)entry.getValue()).longValue() > connectionThrottle) {
/*  65 */                   iter.remove();
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           } 
/*  70 */         } catch (Throwable t) {
/*  71 */           LogManager.getLogger().debug("Failed to check connection throttle", t);
/*     */         } 
/*     */ 
/*     */         
/*  75 */         if (packethandshakinginsetprotocol.d() > 5 && packethandshakinginsetprotocol.d() != 47) {
/*  76 */           ChatComponentText chatcomponenttext = new ChatComponentText(MessageFormat.format(SpigotConfig.outdatedServerMessage, new Object[] { "1.7.10" }));
/*  77 */           this.b.handle(new PacketLoginOutDisconnect(chatcomponenttext), new net.minecraft.util.io.netty.util.concurrent.GenericFutureListener[0]);
/*  78 */           this.b.close(chatcomponenttext);
/*  79 */         } else if (packethandshakinginsetprotocol.d() < 4) {
/*  80 */           ChatComponentText chatcomponenttext = new ChatComponentText(MessageFormat.format(SpigotConfig.outdatedClientMessage, new Object[] { "1.7.10" }));
/*  81 */           this.b.handle(new PacketLoginOutDisconnect(chatcomponenttext), new net.minecraft.util.io.netty.util.concurrent.GenericFutureListener[0]);
/*  82 */           this.b.close(chatcomponenttext);
/*     */         } else {
/*  84 */           this.b.a(new LoginListener(this.a, this.b));
/*     */           
/*  86 */           if (SpigotConfig.bungee) {
/*  87 */             String[] split = packethandshakinginsetprotocol.b.split("\000");
/*  88 */             if (split.length == 3 || split.length == 4) {
/*  89 */               packethandshakinginsetprotocol.b = split[0];
/*  90 */               this.b.n = new InetSocketAddress(split[1], ((InetSocketAddress)this.b.getSocketAddress()).getPort());
/*  91 */               this.b.spoofedUUID = UUIDTypeAdapter.fromString(split[2]);
/*     */             } else {
/*     */               
/*  94 */               ChatComponentText chatcomponenttext = new ChatComponentText("If you wish to use IP forwarding, please enable it in your BungeeCord config as well!");
/*  95 */               this.b.handle(new PacketLoginOutDisconnect(chatcomponenttext), new net.minecraft.util.io.netty.util.concurrent.GenericFutureListener[0]);
/*  96 */               this.b.close(chatcomponenttext);
/*     */               return;
/*     */             } 
/*  99 */             if (split.length == 4)
/*     */             {
/* 101 */               this.b.spoofedProfile = (Property[])gson.fromJson(split[3], Property[].class);
/*     */             }
/*     */           } 
/*     */           
/* 105 */           ((LoginListener)this.b.getPacketListener()).hostname = packethandshakinginsetprotocol.b + ":" + packethandshakinginsetprotocol.c;
/*     */         } 
/*     */         return;
/*     */       
/*     */       case 2:
/* 110 */         this.b.a(EnumProtocol.STATUS);
/* 111 */         this.b.a(new PacketStatusListener(this.a, this.b));
/*     */         return;
/*     */     } 
/*     */     
/* 115 */     throw new UnsupportedOperationException("Invalid intention " + packethandshakinginsetprotocol.c());
/*     */   }
/*     */ 
/*     */   
/*     */   public void a(IChatBaseComponent ichatbasecomponent) {}
/*     */   
/*     */   public void a(EnumProtocol enumprotocol, EnumProtocol enumprotocol1) {
/* 122 */     if (enumprotocol1 != EnumProtocol.LOGIN && enumprotocol1 != EnumProtocol.STATUS)
/* 123 */       throw new UnsupportedOperationException("Invalid state " + enumprotocol1); 
/*     */   }
/*     */   
/*     */   public void a() {}
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\HandshakeListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */