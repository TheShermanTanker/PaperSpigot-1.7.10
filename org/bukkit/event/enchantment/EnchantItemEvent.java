/*     */ package org.bukkit.event.enchantment;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.Cancellable;
/*     */ import org.bukkit.event.HandlerList;
/*     */ import org.bukkit.event.inventory.InventoryEvent;
/*     */ import org.bukkit.inventory.InventoryView;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EnchantItemEvent
/*     */   extends InventoryEvent
/*     */   implements Cancellable
/*     */ {
/*  20 */   private static final HandlerList handlers = new HandlerList();
/*     */   private final Block table;
/*     */   private final ItemStack item;
/*     */   private int level;
/*     */   private boolean cancelled;
/*     */   private final Map<Enchantment, Integer> enchants;
/*     */   private final Player enchanter;
/*     */   private int button;
/*     */   
/*     */   public EnchantItemEvent(Player enchanter, InventoryView view, Block table, ItemStack item, int level, Map<Enchantment, Integer> enchants, int i) {
/*  30 */     super(view);
/*  31 */     this.enchanter = enchanter;
/*  32 */     this.table = table;
/*  33 */     this.item = item;
/*  34 */     this.level = level;
/*  35 */     this.enchants = new HashMap<Enchantment, Integer>(enchants);
/*  36 */     this.cancelled = false;
/*  37 */     this.button = i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Player getEnchanter() {
/*  46 */     return this.enchanter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Block getEnchantBlock() {
/*  55 */     return this.table;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getItem() {
/*  64 */     return this.item;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getExpLevelCost() {
/*  73 */     return this.level;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExpLevelCost(int level) {
/*  82 */     this.level = level;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<Enchantment, Integer> getEnchantsToAdd() {
/*  93 */     return this.enchants;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int whichButton() {
/* 102 */     return this.button;
/*     */   }
/*     */   
/*     */   public boolean isCancelled() {
/* 106 */     return this.cancelled;
/*     */   }
/*     */   
/*     */   public void setCancelled(boolean cancel) {
/* 110 */     this.cancelled = cancel;
/*     */   }
/*     */ 
/*     */   
/*     */   public HandlerList getHandlers() {
/* 115 */     return handlers;
/*     */   }
/*     */   
/*     */   public static HandlerList getHandlerList() {
/* 119 */     return handlers;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\enchantment\EnchantItemEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */