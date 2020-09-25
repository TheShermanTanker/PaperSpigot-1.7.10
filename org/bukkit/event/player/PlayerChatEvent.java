/*     */ package org.bukkit.event.player;
/*     */ 
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Warning;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.Cancellable;
/*     */ import org.bukkit.event.HandlerList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Deprecated
/*     */ @Warning(reason = "Listening to this event forces chat to wait for the main thread, delaying chat messages.")
/*     */ public class PlayerChatEvent
/*     */   extends PlayerEvent
/*     */   implements Cancellable
/*     */ {
/*  25 */   private static final HandlerList handlers = new HandlerList();
/*     */   private boolean cancel = false;
/*     */   private String message;
/*     */   private String format;
/*     */   private final Set<Player> recipients;
/*     */   
/*     */   public PlayerChatEvent(Player player, String message) {
/*  32 */     super(player);
/*  33 */     this.message = message;
/*  34 */     this.format = "<%1$s> %2$s";
/*  35 */     this.recipients = new HashSet<Player>(player.getServer().getOnlinePlayers());
/*     */   }
/*     */   
/*     */   public PlayerChatEvent(Player player, String message, String format, Set<Player> recipients) {
/*  39 */     super(player);
/*  40 */     this.message = message;
/*  41 */     this.format = format;
/*  42 */     this.recipients = recipients;
/*     */   }
/*     */   
/*     */   public boolean isCancelled() {
/*  46 */     return this.cancel;
/*     */   }
/*     */   
/*     */   public void setCancelled(boolean cancel) {
/*  50 */     this.cancel = cancel;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMessage() {
/*  59 */     return this.message;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMessage(String message) {
/*  68 */     this.message = message;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPlayer(Player player) {
/*  78 */     Validate.notNull(player, "Player cannot be null");
/*  79 */     this.player = player;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFormat() {
/*  88 */     return this.format;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFormat(String format) {
/*     */     try {
/*  99 */       String.format(format, new Object[] { this.player, this.message });
/* 100 */     } catch (RuntimeException ex) {
/* 101 */       ex.fillInStackTrace();
/* 102 */       throw ex;
/*     */     } 
/*     */     
/* 105 */     this.format = format;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<Player> getRecipients() {
/* 114 */     return this.recipients;
/*     */   }
/*     */ 
/*     */   
/*     */   public HandlerList getHandlers() {
/* 119 */     return handlers;
/*     */   }
/*     */   
/*     */   public static HandlerList getHandlerList() {
/* 123 */     return handlers;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\player\PlayerChatEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */