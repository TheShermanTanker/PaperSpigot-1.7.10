/*     */ package org.bukkit.event.inventory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum ClickType
/*     */ {
/*  11 */   LEFT,
/*     */ 
/*     */ 
/*     */   
/*  15 */   SHIFT_LEFT,
/*     */ 
/*     */ 
/*     */   
/*  19 */   RIGHT,
/*     */ 
/*     */ 
/*     */   
/*  23 */   SHIFT_RIGHT,
/*     */ 
/*     */ 
/*     */   
/*  27 */   WINDOW_BORDER_LEFT,
/*     */ 
/*     */ 
/*     */   
/*  31 */   WINDOW_BORDER_RIGHT,
/*     */ 
/*     */ 
/*     */   
/*  35 */   MIDDLE,
/*     */ 
/*     */ 
/*     */   
/*  39 */   NUMBER_KEY,
/*     */ 
/*     */ 
/*     */   
/*  43 */   DOUBLE_CLICK,
/*     */ 
/*     */ 
/*     */   
/*  47 */   DROP,
/*     */ 
/*     */ 
/*     */   
/*  51 */   CONTROL_DROP,
/*     */ 
/*     */ 
/*     */   
/*  55 */   CREATIVE,
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   UNKNOWN;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isKeyboardClick() {
/*  74 */     return (this == NUMBER_KEY || this == DROP || this == CONTROL_DROP);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCreativeAction() {
/*  85 */     return (this == MIDDLE || this == CREATIVE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isRightClick() {
/*  94 */     return (this == RIGHT || this == SHIFT_RIGHT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLeftClick() {
/* 103 */     return (this == LEFT || this == SHIFT_LEFT || this == DOUBLE_CLICK || this == CREATIVE);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isShiftClick() {
/* 113 */     return (this == SHIFT_LEFT || this == SHIFT_RIGHT || this == CONTROL_DROP);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\inventory\ClickType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */