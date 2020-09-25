/*     */ package org.bukkit.event.player;
/*     */ 
/*     */ import java.net.InetAddress;
/*     */ import java.util.UUID;
/*     */ import org.bukkit.Warning;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.HandlerList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ @Warning(reason = "This event causes a login thread to synchronize with the main thread")
/*     */ public class PlayerPreLoginEvent
/*     */   extends Event
/*     */ {
/*  20 */   private static final HandlerList handlers = new HandlerList();
/*     */   private Result result;
/*     */   private String message;
/*     */   private final String name;
/*     */   private final InetAddress ipAddress;
/*     */   private final UUID uniqueId;
/*     */   
/*     */   @Deprecated
/*     */   public PlayerPreLoginEvent(String name, InetAddress ipAddress) {
/*  29 */     this(name, ipAddress, null);
/*     */   }
/*     */   
/*     */   public PlayerPreLoginEvent(String name, InetAddress ipAddress, UUID uniqueId) {
/*  33 */     this.result = Result.ALLOWED;
/*  34 */     this.message = "";
/*  35 */     this.name = name;
/*  36 */     this.ipAddress = ipAddress;
/*  37 */     this.uniqueId = uniqueId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Result getResult() {
/*  46 */     return this.result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setResult(Result result) {
/*  55 */     this.result = result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getKickMessage() {
/*  65 */     return this.message;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setKickMessage(String message) {
/*  74 */     this.message = message;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void allow() {
/*  81 */     this.result = Result.ALLOWED;
/*  82 */     this.message = "";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void disallow(Result result, String message) {
/*  92 */     this.result = result;
/*  93 */     this.message = message;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 102 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InetAddress getAddress() {
/* 111 */     return this.ipAddress;
/*     */   }
/*     */ 
/*     */   
/*     */   public HandlerList getHandlers() {
/* 116 */     return handlers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UUID getUniqueId() {
/* 125 */     return this.uniqueId;
/*     */   }
/*     */   
/*     */   public static HandlerList getHandlerList() {
/* 129 */     return handlers;
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
/* 140 */     ALLOWED,
/*     */ 
/*     */ 
/*     */     
/* 144 */     KICK_FULL,
/*     */ 
/*     */ 
/*     */     
/* 148 */     KICK_BANNED,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 153 */     KICK_WHITELIST,
/*     */ 
/*     */ 
/*     */     
/* 157 */     KICK_OTHER;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\player\PlayerPreLoginEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */