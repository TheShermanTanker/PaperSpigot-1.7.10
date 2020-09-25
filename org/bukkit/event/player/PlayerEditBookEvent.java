/*     */ package org.bukkit.event.player;
/*     */ 
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.Cancellable;
/*     */ import org.bukkit.event.HandlerList;
/*     */ import org.bukkit.inventory.meta.BookMeta;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ 
/*     */ public class PlayerEditBookEvent
/*     */   extends PlayerEvent
/*     */   implements Cancellable
/*     */ {
/*  15 */   private static final HandlerList handlers = new HandlerList();
/*     */   
/*     */   private final BookMeta previousBookMeta;
/*     */   private final int slot;
/*     */   private BookMeta newBookMeta;
/*     */   private boolean isSigning;
/*     */   private boolean cancel;
/*     */   
/*     */   public PlayerEditBookEvent(Player who, int slot, BookMeta previousBookMeta, BookMeta newBookMeta, boolean isSigning) {
/*  24 */     super(who);
/*     */     
/*  26 */     Validate.isTrue((slot >= 0 && slot <= 8), "Slot must be in range 0-8 inclusive");
/*  27 */     Validate.notNull(previousBookMeta, "Previous book meta must not be null");
/*  28 */     Validate.notNull(newBookMeta, "New book meta must not be null");
/*     */     
/*  30 */     Bukkit.getItemFactory().equals((ItemMeta)previousBookMeta, (ItemMeta)newBookMeta);
/*     */     
/*  32 */     this.previousBookMeta = previousBookMeta;
/*  33 */     this.newBookMeta = newBookMeta;
/*  34 */     this.slot = slot;
/*  35 */     this.isSigning = isSigning;
/*  36 */     this.cancel = false;
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
/*     */   public BookMeta getPreviousBookMeta() {
/*  48 */     return this.previousBookMeta.clone();
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
/*     */   public BookMeta getNewBookMeta() {
/*  61 */     return this.newBookMeta.clone();
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
/*     */   public int getSlot() {
/*  73 */     return this.slot;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNewBookMeta(BookMeta newBookMeta) throws IllegalArgumentException {
/*  83 */     Validate.notNull(newBookMeta, "New book meta must not be null");
/*  84 */     Bukkit.getItemFactory().equals((ItemMeta)newBookMeta, null);
/*  85 */     this.newBookMeta = newBookMeta.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSigning() {
/*  95 */     return this.isSigning;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSigning(boolean signing) {
/* 105 */     this.isSigning = signing;
/*     */   }
/*     */ 
/*     */   
/*     */   public HandlerList getHandlers() {
/* 110 */     return handlers;
/*     */   }
/*     */   
/*     */   public static HandlerList getHandlerList() {
/* 114 */     return handlers;
/*     */   }
/*     */   
/*     */   public boolean isCancelled() {
/* 118 */     return this.cancel;
/*     */   }
/*     */   
/*     */   public void setCancelled(boolean cancel) {
/* 122 */     this.cancel = cancel;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\player\PlayerEditBookEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */