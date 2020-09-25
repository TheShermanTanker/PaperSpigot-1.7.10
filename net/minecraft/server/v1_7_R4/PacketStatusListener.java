/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.net.InetSocketAddress;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.NoSuchElementException;
/*     */ import net.minecraft.util.com.mojang.authlib.GameProfile;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.util.CraftIconCache;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.server.ServerListPingEvent;
/*     */ import org.bukkit.util.CachedServerIcon;
/*     */ import org.spigotmc.SpigotConfig;
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
/*     */ public class PacketStatusListener
/*     */   implements PacketStatusInListener
/*     */ {
/*     */   private final MinecraftServer minecraftServer;
/*     */   private final NetworkManager networkManager;
/*     */   private static final int WAITING = 0;
/*     */   private static final int PING = 1;
/*     */   private static final int DONE = 2;
/*     */   private int state;
/*     */   
/*     */   public void a(IChatBaseComponent ichatbasecomponent) {}
/*     */   
/*     */   public PacketStatusListener(MinecraftServer minecraftserver, NetworkManager networkmanager) {
/*  40 */     this.state = 0;
/*     */     this.minecraftServer = minecraftserver;
/*     */     this.networkManager = networkmanager;
/*     */   }
/*     */   public void a(PacketStatusInStart packetstatusinstart) {
/*  45 */     if (this.state != 0) {
/*  46 */       this.networkManager.close(null);
/*     */       return;
/*     */     } 
/*  49 */     this.state = 1;
/*     */ 
/*     */     
/*  52 */     final Object[] players = (this.minecraftServer.getPlayerList()).players.toArray();
/*     */     class ServerListPingEvent extends ServerListPingEvent {
/*  54 */       CraftIconCache icon = PacketStatusListener.this.minecraftServer.server.getServerIcon();
/*     */       
/*     */       ServerListPingEvent() {
/*  57 */         super(((InetSocketAddress)PacketStatusListener.this.networkManager.getSocketAddress()).getAddress(), PacketStatusListener.this.minecraftServer.getMotd(), PacketStatusListener.this.minecraftServer.getPlayerList().getMaxPlayers());
/*     */       }
/*     */ 
/*     */       
/*     */       public void setServerIcon(CachedServerIcon icon) {
/*  62 */         if (!(icon instanceof CraftIconCache)) {
/*  63 */           throw new IllegalArgumentException(icon + " was not created by " + CraftServer.class);
/*     */         }
/*  65 */         this.icon = (CraftIconCache)icon;
/*     */       }
/*     */ 
/*     */       
/*     */       public Iterator<Player> iterator() throws UnsupportedOperationException {
/*  70 */         return new Iterator<Player>() {
/*     */             int i;
/*  72 */             int ret = Integer.MIN_VALUE;
/*     */             
/*     */             EntityPlayer player;
/*     */             
/*     */             public boolean hasNext() {
/*  77 */               if (this.player != null) {
/*  78 */                 return true;
/*     */               }
/*  80 */               Object[] currentPlayers = players;
/*  81 */               for (int length = currentPlayers.length, i = this.i; i < length; i++) {
/*  82 */                 EntityPlayer player = (EntityPlayer)currentPlayers[i];
/*  83 */                 if (player != null) {
/*  84 */                   this.i = i + 1;
/*  85 */                   this.player = player;
/*  86 */                   return true;
/*     */                 } 
/*     */               } 
/*  89 */               return false;
/*     */             }
/*     */ 
/*     */             
/*     */             public Player next() {
/*  94 */               if (!hasNext()) {
/*  95 */                 throw new NoSuchElementException();
/*     */               }
/*  97 */               EntityPlayer player = this.player;
/*  98 */               this.player = null;
/*  99 */               this.ret = this.i - 1;
/* 100 */               return (Player)player.getBukkitEntity();
/*     */             }
/*     */ 
/*     */             
/*     */             public void remove() {
/* 105 */               Object[] currentPlayers = players;
/* 106 */               int i = this.ret;
/* 107 */               if (i < 0 || currentPlayers[i] == null) {
/* 108 */                 throw new IllegalStateException();
/*     */               }
/* 110 */               currentPlayers[i] = null;
/*     */             }
/*     */           };
/*     */       }
/*     */     };
/*     */     
/* 116 */     ServerListPingEvent event = new ServerListPingEvent();
/* 117 */     this.minecraftServer.server.getPluginManager().callEvent((Event)event);
/*     */     
/* 119 */     List<GameProfile> profiles = new ArrayList<GameProfile>(players.length);
/* 120 */     for (Object player : players) {
/* 121 */       if (player != null) {
/* 122 */         profiles.add(((EntityPlayer)player).getProfile());
/*     */       }
/*     */     } 
/*     */     
/* 126 */     ServerPingPlayerSample playerSample = new ServerPingPlayerSample(event.getMaxPlayers(), profiles.size());
/*     */     
/* 128 */     if (!profiles.isEmpty()) {
/*     */       
/* 130 */       Collections.shuffle(profiles);
/* 131 */       profiles = profiles.subList(0, Math.min(profiles.size(), SpigotConfig.playerSample));
/*     */     } 
/*     */     
/* 134 */     playerSample.a(profiles.<GameProfile>toArray(new GameProfile[profiles.size()]));
/*     */     
/* 136 */     ServerPing ping = new ServerPing();
/* 137 */     ping.setFavicon(event.icon.value);
/* 138 */     ping.setMOTD(new ChatComponentText(event.getMotd()));
/* 139 */     ping.setPlayerSample(playerSample);
/* 140 */     ping.setServerInfo(new ServerPingServerData(this.minecraftServer.getServerModName() + " " + this.minecraftServer.getVersion(), this.networkManager.getVersion()));
/*     */     
/* 142 */     this.networkManager.handle(new PacketStatusOutServerInfo(ping), new net.minecraft.util.io.netty.util.concurrent.GenericFutureListener[0]);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void a(PacketStatusInPing packetstatusinping) {
/* 148 */     if (this.state != 1) {
/* 149 */       this.networkManager.close(null);
/*     */       return;
/*     */     } 
/* 152 */     this.state = 2;
/*     */     
/* 154 */     this.networkManager.handle(new PacketStatusOutPong(packetstatusinping.c()), new net.minecraft.util.io.netty.util.concurrent.GenericFutureListener[0]);
/*     */   }
/*     */   
/*     */   public void a(EnumProtocol enumprotocol, EnumProtocol enumprotocol1) {
/*     */     if (enumprotocol1 != EnumProtocol.STATUS)
/*     */       throw new UnsupportedOperationException("Unexpected change in protocol to " + enumprotocol1); 
/*     */   }
/*     */   
/*     */   public void a() {}
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\PacketStatusListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */