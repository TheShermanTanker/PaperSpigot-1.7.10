/*    */ package net.md_5.bungee.api.chat;
/*    */ 
/*    */ import java.beans.ConstructorProperties;
/*    */ 
/*    */ public final class ClickEvent {
/*    */   private final Action action;
/*    */   private final String value;
/*    */   
/*  9 */   public String toString() { return "ClickEvent(action=" + getAction() + ", value=" + getValue() + ")"; } @ConstructorProperties({"action", "value"})
/* 10 */   public ClickEvent(Action action, String value) { this.action = action; this.value = value; }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Action getAction() {
/* 17 */     return this.action;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String getValue() {
/* 23 */     return this.value;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public enum Action
/*    */   {
/* 32 */     OPEN_URL,
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 37 */     OPEN_FILE,
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 42 */     RUN_COMMAND,
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 48 */     SUGGEST_COMMAND;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\md_5\bungee\api\chat\ClickEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */