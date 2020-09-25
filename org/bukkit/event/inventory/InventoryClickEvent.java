/*     */ package org.bukkit.event.inventory;
/*     */ 
/*     */ import org.bukkit.event.HandlerList;
/*     */ import org.bukkit.inventory.Inventory;
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
/*     */ public class InventoryClickEvent
/*     */   extends InventoryInteractEvent
/*     */ {
/*  47 */   private static final HandlerList handlers = new HandlerList();
/*     */   private final ClickType click;
/*     */   private final InventoryAction action;
/*     */   private final Inventory clickedInventory;
/*     */   private InventoryType.SlotType slot_type;
/*     */   private int whichSlot;
/*     */   private int rawSlot;
/*  54 */   private ItemStack current = null;
/*  55 */   private int hotbarKey = -1;
/*     */   
/*     */   @Deprecated
/*     */   public InventoryClickEvent(InventoryView view, InventoryType.SlotType type, int slot, boolean right, boolean shift) {
/*  59 */     this(view, type, slot, right ? (shift ? ClickType.SHIFT_RIGHT : ClickType.RIGHT) : (shift ? ClickType.SHIFT_LEFT : ClickType.LEFT), InventoryAction.SWAP_WITH_CURSOR);
/*     */   }
/*     */   
/*     */   public InventoryClickEvent(InventoryView view, InventoryType.SlotType type, int slot, ClickType click, InventoryAction action) {
/*  63 */     super(view);
/*  64 */     this.slot_type = type;
/*  65 */     this.rawSlot = slot;
/*  66 */     if (slot < 0) {
/*  67 */       this.clickedInventory = null;
/*  68 */     } else if (view.getTopInventory() != null && slot < view.getTopInventory().getSize()) {
/*  69 */       this.clickedInventory = view.getTopInventory();
/*     */     } else {
/*  71 */       this.clickedInventory = view.getBottomInventory();
/*     */     } 
/*  73 */     this.whichSlot = view.convertSlot(slot);
/*  74 */     this.click = click;
/*  75 */     this.action = action;
/*     */   }
/*     */   
/*     */   public InventoryClickEvent(InventoryView view, InventoryType.SlotType type, int slot, ClickType click, InventoryAction action, int key) {
/*  79 */     this(view, type, slot, click, action);
/*  80 */     this.hotbarKey = key;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Inventory getClickedInventory() {
/*  88 */     return this.clickedInventory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public InventoryType.SlotType getSlotType() {
/*  97 */     return this.slot_type;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getCursor() {
/* 106 */     return getView().getCursor();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getCurrentItem() {
/* 115 */     if (this.slot_type == InventoryType.SlotType.OUTSIDE) {
/* 116 */       return this.current;
/*     */     }
/* 118 */     return getView().getItem(this.rawSlot);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRightClick() {
/* 129 */     return this.click.isRightClick();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLeftClick() {
/* 140 */     return this.click.isLeftClick();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isShiftClick() {
/* 151 */     return this.click.isShiftClick();
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
/*     */   public void setCursor(ItemStack stack) {
/* 165 */     getView().setCursor(stack);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCurrentItem(ItemStack stack) {
/* 174 */     if (this.slot_type == InventoryType.SlotType.OUTSIDE) {
/* 175 */       this.current = stack;
/*     */     } else {
/* 177 */       getView().setItem(this.rawSlot, stack);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSlot() {
/* 189 */     return this.whichSlot;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRawSlot() {
/* 199 */     return this.rawSlot;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHotbarButton() {
/* 210 */     return this.hotbarKey;
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
/*     */   public InventoryAction getAction() {
/* 223 */     return this.action;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ClickType getClick() {
/* 234 */     return this.click;
/*     */   }
/*     */ 
/*     */   
/*     */   public HandlerList getHandlers() {
/* 239 */     return handlers;
/*     */   }
/*     */   
/*     */   public static HandlerList getHandlerList() {
/* 243 */     return handlers;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\inventory\InventoryClickEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */