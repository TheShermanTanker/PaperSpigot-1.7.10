/*    */ package net.md_5.bungee.api.chat;
/*    */ 
/*    */ 
/*    */ public final class HoverEvent {
/*    */   private final Action action;
/*    */   private final BaseComponent[] value;
/*    */   
/*  8 */   public String toString() { return "HoverEvent(action=" + getAction() + ", value=" + Arrays.deepToString((Object[])getValue()) + ")"; } @ConstructorProperties({"action", "value"})
/*  9 */   public HoverEvent(Action action, BaseComponent[] value) { this.action = action; this.value = value; }
/*    */ 
/*    */   
/*    */   public Action getAction() {
/* 13 */     return this.action; } public BaseComponent[] getValue() {
/* 14 */     return this.value;
/*    */   }
/*    */   
/*    */   public enum Action
/*    */   {
/* 19 */     SHOW_TEXT,
/* 20 */     SHOW_ACHIEVEMENT,
/* 21 */     SHOW_ITEM;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\md_5\bungee\api\chat\HoverEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */