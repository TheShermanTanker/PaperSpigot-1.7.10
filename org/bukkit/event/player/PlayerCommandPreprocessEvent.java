/*     */ package org.bukkit.event.player;
/*     */ 
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.Validate;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlayerCommandPreprocessEvent
/*     */   extends PlayerEvent
/*     */   implements Cancellable
/*     */ {
/*  49 */   private static final HandlerList handlers = new HandlerList();
/*     */   private boolean cancel = false;
/*     */   private String message;
/*  52 */   private String format = "<%1$s> %2$s";
/*     */   private final Set<Player> recipients;
/*     */   
/*     */   public PlayerCommandPreprocessEvent(Player player, String message) {
/*  56 */     super(player);
/*  57 */     this.recipients = new HashSet<Player>(player.getServer().getOnlinePlayers());
/*  58 */     this.message = message;
/*     */   }
/*     */   
/*     */   public PlayerCommandPreprocessEvent(Player player, String message, Set<Player> recipients) {
/*  62 */     super(player);
/*  63 */     this.recipients = recipients;
/*  64 */     this.message = message;
/*     */   }
/*     */   
/*     */   public boolean isCancelled() {
/*  68 */     return this.cancel;
/*     */   }
/*     */   
/*     */   public void setCancelled(boolean cancel) {
/*  72 */     this.cancel = cancel;
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
/*     */   public String getMessage() {
/*  84 */     return this.message;
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
/*     */   public void setMessage(String command) throws IllegalArgumentException {
/*  97 */     Validate.notNull(command, "Command cannot be null");
/*  98 */     Validate.notEmpty(command, "Command cannot be empty");
/*  99 */     this.message = command;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPlayer(Player player) throws IllegalArgumentException {
/* 109 */     Validate.notNull(player, "Player cannot be null");
/* 110 */     this.player = player;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public String getFormat() {
/* 122 */     return this.format;
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
/*     */   public void setFormat(String format) {
/*     */     try {
/* 136 */       String.format(format, new Object[] { this.player, this.message });
/* 137 */     } catch (RuntimeException ex) {
/* 138 */       ex.fillInStackTrace();
/* 139 */       throw ex;
/*     */     } 
/*     */     
/* 142 */     this.format = format;
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
/*     */   @Deprecated
/*     */   public Set<Player> getRecipients() {
/* 161 */     return this.recipients;
/*     */   }
/*     */ 
/*     */   
/*     */   public HandlerList getHandlers() {
/* 166 */     return handlers;
/*     */   }
/*     */   
/*     */   public static HandlerList getHandlerList() {
/* 170 */     return handlers;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\player\PlayerCommandPreprocessEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */