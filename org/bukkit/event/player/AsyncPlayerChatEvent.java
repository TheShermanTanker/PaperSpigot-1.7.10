/*     */ package org.bukkit.event.player;
/*     */ 
/*     */ import java.util.IllegalFormatException;
/*     */ import java.util.Set;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AsyncPlayerChatEvent
/*     */   extends PlayerEvent
/*     */   implements Cancellable
/*     */ {
/*  27 */   private static final HandlerList handlers = new HandlerList();
/*     */   private boolean cancel = false;
/*     */   private String message;
/*  30 */   private String format = "<%1$s> %2$s";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final Set<Player> recipients;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AsyncPlayerChatEvent(boolean async, Player who, String message, Set<Player> players) {
/*  42 */     super(who, async);
/*  43 */     this.message = message;
/*  44 */     this.recipients = players;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getMessage() {
/*  54 */     return this.message;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMessage(String message) {
/*  64 */     this.message = message;
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
/*     */   public String getFormat() {
/*  78 */     return this.format;
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFormat(String format) throws IllegalFormatException, NullPointerException {
/*     */     try {
/*  98 */       String.format(format, new Object[] { this.player, this.message });
/*  99 */     } catch (RuntimeException ex) {
/* 100 */       ex.fillInStackTrace();
/* 101 */       throw ex;
/*     */     } 
/*     */     
/* 104 */     this.format = format;
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
/*     */   
/*     */   public Set<Player> getRecipients() {
/* 121 */     return this.recipients;
/*     */   }
/*     */   
/*     */   public boolean isCancelled() {
/* 125 */     return this.cancel;
/*     */   }
/*     */   
/*     */   public void setCancelled(boolean cancel) {
/* 129 */     this.cancel = cancel;
/*     */   }
/*     */ 
/*     */   
/*     */   public HandlerList getHandlers() {
/* 134 */     return handlers;
/*     */   }
/*     */   
/*     */   public static HandlerList getHandlerList() {
/* 138 */     return handlers;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\player\AsyncPlayerChatEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */