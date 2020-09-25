/*     */ package org.bukkit.event.player;
/*     */ 
/*     */ import java.net.InetAddress;
/*     */ import java.util.UUID;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.HandlerList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AsyncPlayerPreLoginEvent
/*     */   extends Event
/*     */ {
/*  15 */   private static final HandlerList handlers = new HandlerList();
/*     */   private Result result;
/*     */   private String message;
/*     */   private final String name;
/*     */   private final InetAddress ipAddress;
/*     */   private final UUID uniqueId;
/*     */   
/*     */   @Deprecated
/*     */   public AsyncPlayerPreLoginEvent(String name, InetAddress ipAddress) {
/*  24 */     this(name, ipAddress, null);
/*     */   }
/*     */   
/*     */   public AsyncPlayerPreLoginEvent(String name, InetAddress ipAddress, UUID uniqueId) {
/*  28 */     super(true);
/*  29 */     this.result = Result.ALLOWED;
/*  30 */     this.message = "";
/*  31 */     this.name = name;
/*  32 */     this.ipAddress = ipAddress;
/*  33 */     this.uniqueId = uniqueId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Result getLoginResult() {
/*  42 */     return this.result;
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
/*     */   @Deprecated
/*     */   public PlayerPreLoginEvent.Result getResult() {
/*  55 */     return (this.result == null) ? null : this.result.old();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLoginResult(Result result) {
/*  64 */     this.result = result;
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
/*     */   @Deprecated
/*     */   public void setResult(PlayerPreLoginEvent.Result result) {
/*  77 */     this.result = (result == null) ? null : Result.valueOf(result.name());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getKickMessage() {
/*  87 */     return this.message;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setKickMessage(String message) {
/*  96 */     this.message = message;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void allow() {
/* 103 */     this.result = Result.ALLOWED;
/* 104 */     this.message = "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void disallow(Result result, String message) {
/* 114 */     this.result = result;
/* 115 */     this.message = message;
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
/*     */   @Deprecated
/*     */   public void disallow(PlayerPreLoginEvent.Result result, String message) {
/* 129 */     this.result = (result == null) ? null : Result.valueOf(result.name());
/* 130 */     this.message = message;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 139 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InetAddress getAddress() {
/* 148 */     return this.ipAddress;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UUID getUniqueId() {
/* 157 */     return this.uniqueId;
/*     */   }
/*     */ 
/*     */   
/*     */   public HandlerList getHandlers() {
/* 162 */     return handlers;
/*     */   }
/*     */   
/*     */   public static HandlerList getHandlerList() {
/* 166 */     return handlers;
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
/* 177 */     ALLOWED,
/*     */ 
/*     */ 
/*     */     
/* 181 */     KICK_FULL,
/*     */ 
/*     */ 
/*     */     
/* 185 */     KICK_BANNED,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 190 */     KICK_WHITELIST,
/*     */ 
/*     */ 
/*     */     
/* 194 */     KICK_OTHER;
/*     */     
/*     */     @Deprecated
/*     */     private PlayerPreLoginEvent.Result old() {
/* 198 */       return PlayerPreLoginEvent.Result.valueOf(name());
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\player\AsyncPlayerPreLoginEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */