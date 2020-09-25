/*     */ package net.minecraft.server.v1_7_R4;
/*     */ 
/*     */ import java.math.BigInteger;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.util.UUID;
/*     */ import java.util.logging.Level;
/*     */ import net.minecraft.util.com.mojang.authlib.GameProfile;
/*     */ import net.minecraft.util.com.mojang.authlib.exceptions.AuthenticationUnavailableException;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.util.Waitable;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
/*     */ import org.bukkit.event.player.PlayerPreLoginEvent;
/*     */ 
/*     */ class ThreadPlayerLookupUUID extends Thread {
/*     */   final LoginListener a;
/*     */   
/*     */   ThreadPlayerLookupUUID(LoginListener loginlistener, String s) {
/*  20 */     super(s);
/*  21 */     this.a = loginlistener;
/*     */   }
/*     */   
/*     */   public void run() {
/*  25 */     GameProfile gameprofile = LoginListener.a(this.a);
/*     */ 
/*     */     
/*     */     try {
/*  29 */       if (!LoginListener.c(this.a).getOnlineMode()) {
/*     */         
/*  31 */         this.a.initUUID();
/*  32 */         fireLoginEvents();
/*     */         
/*     */         return;
/*     */       } 
/*  36 */       String s = (new BigInteger(MinecraftEncryption.a(LoginListener.b(this.a), LoginListener.c(this.a).K().getPublic(), LoginListener.d(this.a)))).toString(16);
/*     */       
/*  38 */       LoginListener.a(this.a, LoginListener.c(this.a).av().hasJoinedServer(new GameProfile((UUID)null, gameprofile.getName()), s));
/*  39 */       if (LoginListener.a(this.a) != null) {
/*  40 */         fireLoginEvents();
/*  41 */       } else if (LoginListener.c(this.a).N()) {
/*  42 */         LoginListener.e().warn("Failed to verify username but will let them in anyway!");
/*  43 */         LoginListener.a(this.a, this.a.a(gameprofile));
/*  44 */         LoginListener.a(this.a, EnumProtocolState.READY_TO_ACCEPT);
/*     */       } else {
/*  46 */         this.a.disconnect("Failed to verify username!");
/*  47 */         LoginListener.e().error("Username '" + LoginListener.a(this.a).getName() + "' tried to join with an invalid session");
/*     */       } 
/*  49 */     } catch (AuthenticationUnavailableException authenticationunavailableexception) {
/*  50 */       if (LoginListener.c(this.a).N()) {
/*  51 */         LoginListener.e().warn("Authentication servers are down but will let them in anyway!");
/*  52 */         LoginListener.a(this.a, this.a.a(gameprofile));
/*  53 */         LoginListener.a(this.a, EnumProtocolState.READY_TO_ACCEPT);
/*     */       } else {
/*  55 */         this.a.disconnect("Authentication servers are down. Please try again later, sorry!");
/*  56 */         LoginListener.e().error("Couldn't verify username because servers are unavailable");
/*     */       }
/*     */     
/*  59 */     } catch (Exception exception) {
/*  60 */       this.a.disconnect("Failed to verify username!");
/*  61 */       (LoginListener.c(this.a)).server.getLogger().log(Level.WARNING, "Exception verifying " + LoginListener.a(this.a).getName(), exception);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void fireLoginEvents() throws Exception {
/*  69 */     if (!this.a.networkManager.isConnected()) {
/*     */       return;
/*     */     }
/*     */     
/*  73 */     String playerName = LoginListener.a(this.a).getName();
/*  74 */     InetAddress address = ((InetSocketAddress)this.a.networkManager.getSocketAddress()).getAddress();
/*  75 */     UUID uniqueId = LoginListener.a(this.a).getId();
/*  76 */     final CraftServer server = (LoginListener.c(this.a)).server;
/*     */     
/*  78 */     AsyncPlayerPreLoginEvent asyncEvent = new AsyncPlayerPreLoginEvent(playerName, address, uniqueId);
/*  79 */     server.getPluginManager().callEvent((Event)asyncEvent);
/*     */     
/*  81 */     if ((PlayerPreLoginEvent.getHandlerList().getRegisteredListeners()).length != 0) {
/*  82 */       final PlayerPreLoginEvent event = new PlayerPreLoginEvent(playerName, address, uniqueId);
/*  83 */       if (asyncEvent.getResult() != PlayerPreLoginEvent.Result.ALLOWED) {
/*  84 */         event.disallow(asyncEvent.getResult(), asyncEvent.getKickMessage());
/*     */       }
/*  86 */       Waitable<PlayerPreLoginEvent.Result> waitable = new Waitable<PlayerPreLoginEvent.Result>()
/*     */         {
/*     */           protected PlayerPreLoginEvent.Result evaluate() {
/*  89 */             server.getPluginManager().callEvent((Event)event);
/*  90 */             return event.getResult();
/*     */           }
/*     */         };
/*  93 */       (LoginListener.c(this.a)).processQueue.add(waitable);
/*  94 */       if (waitable.get() != PlayerPreLoginEvent.Result.ALLOWED) {
/*  95 */         this.a.disconnect(event.getKickMessage());
/*     */         
/*     */         return;
/*     */       } 
/*  99 */     } else if (asyncEvent.getLoginResult() != AsyncPlayerPreLoginEvent.Result.ALLOWED) {
/* 100 */       this.a.disconnect(asyncEvent.getKickMessage());
/*     */ 
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 106 */     LoginListener.e().info("UUID of player " + LoginListener.a(this.a).getName() + " is " + LoginListener.a(this.a).getId());
/* 107 */     LoginListener.a(this.a, EnumProtocolState.READY_TO_ACCEPT);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraft\server\v1_7_R4\ThreadPlayerLookupUUID.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */