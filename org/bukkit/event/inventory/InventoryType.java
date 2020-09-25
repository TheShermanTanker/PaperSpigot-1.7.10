/*     */ package org.bukkit.event.inventory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum InventoryType
/*     */ {
/*   9 */   CHEST(27, "Chest"),
/*     */ 
/*     */ 
/*     */   
/*  13 */   DISPENSER(9, "Dispenser"),
/*     */ 
/*     */ 
/*     */   
/*  17 */   DROPPER(9, "Dropper"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  22 */   FURNACE(3, "Furnace"),
/*     */ 
/*     */ 
/*     */   
/*  26 */   WORKBENCH(10, "Crafting"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  31 */   CRAFTING(5, "Crafting"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  36 */   ENCHANTING(1, "Enchanting"),
/*     */ 
/*     */ 
/*     */   
/*  40 */   BREWING(4, "Brewing"),
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  45 */   PLAYER(36, "Player"),
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  51 */   CREATIVE(9, "Creative"),
/*     */ 
/*     */ 
/*     */   
/*  55 */   MERCHANT(3, "Villager"),
/*     */ 
/*     */ 
/*     */   
/*  59 */   ENDER_CHEST(27, "Ender Chest"),
/*     */ 
/*     */ 
/*     */   
/*  63 */   ANVIL(3, "Repairing"),
/*     */ 
/*     */ 
/*     */   
/*  67 */   BEACON(1, "container.beacon"),
/*     */ 
/*     */ 
/*     */   
/*  71 */   HOPPER(5, "Item Hopper");
/*     */   
/*     */   private final int size;
/*     */   
/*     */   private final String title;
/*     */   
/*     */   InventoryType(int defaultSize, String defaultTitle) {
/*  78 */     this.size = defaultSize;
/*  79 */     this.title = defaultTitle;
/*     */   }
/*     */   
/*     */   public int getDefaultSize() {
/*  83 */     return this.size;
/*     */   }
/*     */   
/*     */   public String getDefaultTitle() {
/*  87 */     return this.title;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public enum SlotType
/*     */   {
/*  94 */     RESULT,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 100 */     CRAFTING,
/*     */ 
/*     */ 
/*     */     
/* 104 */     ARMOR,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 109 */     CONTAINER,
/*     */ 
/*     */ 
/*     */     
/* 113 */     QUICKBAR,
/*     */ 
/*     */ 
/*     */     
/* 117 */     OUTSIDE,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 122 */     FUEL;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\inventory\InventoryType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */