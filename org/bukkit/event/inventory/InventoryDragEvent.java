/*     */ package org.bukkit.event.inventory;
/*     */ 
/*     */ import com.google.common.collect.ImmutableSet;
/*     */ import java.util.Collections;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.event.HandlerList;
/*     */ import org.bukkit.inventory.InventoryView;
/*     */ import org.bukkit.inventory.ItemStack;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InventoryDragEvent
/*     */   extends InventoryInteractEvent
/*     */ {
/*  58 */   private static final HandlerList handlers = new HandlerList();
/*     */   private final DragType type;
/*     */   private final Map<Integer, ItemStack> addedItems;
/*     */   private final Set<Integer> containerSlots;
/*     */   private final ItemStack oldCursor;
/*     */   private ItemStack newCursor;
/*     */   
/*     */   public InventoryDragEvent(InventoryView what, ItemStack newCursor, ItemStack oldCursor, boolean right, Map<Integer, ItemStack> slots) {
/*  66 */     super(what);
/*     */     
/*  68 */     Validate.notNull(oldCursor);
/*  69 */     Validate.notNull(slots);
/*     */     
/*  71 */     this.type = right ? DragType.SINGLE : DragType.EVEN;
/*  72 */     this.newCursor = newCursor;
/*  73 */     this.oldCursor = oldCursor;
/*  74 */     this.addedItems = slots;
/*  75 */     ImmutableSet.Builder<Integer> b = ImmutableSet.builder();
/*  76 */     for (Integer slot : slots.keySet()) {
/*  77 */       b.add(Integer.valueOf(what.convertSlot(slot.intValue())));
/*     */     }
/*  79 */     this.containerSlots = (Set<Integer>)b.build();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<Integer, ItemStack> getNewItems() {
/*  88 */     return Collections.unmodifiableMap(this.addedItems);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<Integer> getRawSlots() {
/*  97 */     return this.addedItems.keySet();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Set<Integer> getInventorySlots() {
/* 107 */     return this.containerSlots;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getCursor() {
/* 117 */     return this.newCursor;
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
/*     */   public void setCursor(ItemStack newCursor) {
/* 130 */     this.newCursor = newCursor;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getOldCursor() {
/* 140 */     return this.oldCursor.clone();
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
/*     */   public DragType getType() {
/* 153 */     return this.type;
/*     */   }
/*     */ 
/*     */   
/*     */   public HandlerList getHandlers() {
/* 158 */     return handlers;
/*     */   }
/*     */   
/*     */   public static HandlerList getHandlerList() {
/* 162 */     return handlers;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\inventory\InventoryDragEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */