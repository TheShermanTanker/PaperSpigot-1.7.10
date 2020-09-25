/*     */ package org.bukkit.event.server;
/*     */ 
/*     */ import java.net.InetAddress;
/*     */ import java.util.Iterator;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.HandlerList;
/*     */ import org.bukkit.util.CachedServerIcon;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ServerListPingEvent
/*     */   extends ServerEvent
/*     */   implements Iterable<Player>
/*     */ {
/*     */   private static final int MAGIC_PLAYER_COUNT = -2147483648;
/*  17 */   private static final HandlerList handlers = new HandlerList();
/*     */   private final InetAddress address;
/*     */   private String motd;
/*     */   private final int numPlayers;
/*     */   private int maxPlayers;
/*     */   
/*     */   public ServerListPingEvent(InetAddress address, String motd, int numPlayers, int maxPlayers) {
/*  24 */     Validate.isTrue((numPlayers >= 0), "Cannot have negative number of players online", numPlayers);
/*  25 */     this.address = address;
/*  26 */     this.motd = motd;
/*  27 */     this.numPlayers = numPlayers;
/*  28 */     this.maxPlayers = maxPlayers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected ServerListPingEvent(InetAddress address, String motd, int maxPlayers) {
/*  37 */     this.numPlayers = Integer.MIN_VALUE;
/*  38 */     this.address = address;
/*  39 */     this.motd = motd;
/*  40 */     this.maxPlayers = maxPlayers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InetAddress getAddress() {
/*  49 */     return this.address;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMotd() {
/*  58 */     return this.motd;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMotd(String motd) {
/*  67 */     this.motd = motd;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNumPlayers() {
/*  76 */     int numPlayers = this.numPlayers;
/*  77 */     if (numPlayers == Integer.MIN_VALUE) {
/*  78 */       numPlayers = 0;
/*  79 */       for (Player player : this) {
/*  80 */         numPlayers++;
/*     */       }
/*     */     } 
/*  83 */     return numPlayers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxPlayers() {
/*  92 */     return this.maxPlayers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMaxPlayers(int maxPlayers) {
/* 101 */     this.maxPlayers = maxPlayers;
/*     */   }
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
/*     */   public void setServerIcon(CachedServerIcon icon) throws IllegalArgumentException, UnsupportedOperationException {
/* 115 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */   
/*     */   public HandlerList getHandlers() {
/* 120 */     return handlers;
/*     */   }
/*     */   
/*     */   public static HandlerList getHandlerList() {
/* 124 */     return handlers;
/*     */   }
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
/*     */   public Iterator<Player> iterator() throws UnsupportedOperationException {
/* 140 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\server\ServerListPingEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */