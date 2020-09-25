/*     */ package org.bukkit.event.player;
/*     */ 
/*     */ import java.net.InetAddress;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.HandlerList;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlayerLoginEvent
/*     */   extends PlayerEvent
/*     */ {
/*  12 */   private static final HandlerList handlers = new HandlerList();
/*     */   private final InetAddress address;
/*     */   private final String hostname;
/*  15 */   private Result result = Result.ALLOWED;
/*  16 */   private String message = "";
/*     */ 
/*     */   
/*     */   private final InetAddress realAddress;
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public PlayerLoginEvent(Player player) {
/*  24 */     this(player, "", (InetAddress)null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public PlayerLoginEvent(Player player, String hostname) {
/*  32 */     this(player, hostname, (InetAddress)null);
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
/*     */   public PlayerLoginEvent(Player player, String hostname, InetAddress address, InetAddress realAddress) {
/*  45 */     super(player);
/*  46 */     this.hostname = hostname;
/*  47 */     this.address = address;
/*     */     
/*  49 */     this.realAddress = realAddress;
/*     */   }
/*     */   
/*     */   public PlayerLoginEvent(Player player, String hostname, InetAddress address) {
/*  53 */     this(player, hostname, address, address);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public PlayerLoginEvent(Player player, Result result, String message) {
/*  63 */     this(player, "", null, result, message, null);
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
/*     */   public PlayerLoginEvent(Player player, String hostname, InetAddress address, Result result, String message, InetAddress realAddress) {
/*  77 */     this(player, hostname, address, realAddress);
/*  78 */     this.result = result;
/*  79 */     this.message = message;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InetAddress getRealAddress() {
/*  89 */     return this.realAddress;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Result getResult() {
/*  99 */     return this.result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setResult(Result result) {
/* 108 */     this.result = result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getKickMessage() {
/* 118 */     return this.message;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setKickMessage(String message) {
/* 127 */     this.message = message;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHostname() {
/* 137 */     return this.hostname;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void allow() {
/* 144 */     this.result = Result.ALLOWED;
/* 145 */     this.message = "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void disallow(Result result, String message) {
/* 155 */     this.result = result;
/* 156 */     this.message = message;
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
/*     */   public InetAddress getAddress() {
/* 168 */     return this.address;
/*     */   }
/*     */ 
/*     */   
/*     */   public HandlerList getHandlers() {
/* 173 */     return handlers;
/*     */   }
/*     */   
/*     */   public static HandlerList getHandlerList() {
/* 177 */     return handlers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public enum Result
/*     */   {
/* 188 */     ALLOWED,
/*     */ 
/*     */ 
/*     */     
/* 192 */     KICK_FULL,
/*     */ 
/*     */ 
/*     */     
/* 196 */     KICK_BANNED,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 201 */     KICK_WHITELIST,
/*     */ 
/*     */ 
/*     */     
/* 205 */     KICK_OTHER;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\player\PlayerLoginEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */