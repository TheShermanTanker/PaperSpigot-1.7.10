/*    */ package org.bukkit.event;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Event
/*    */ {
/*    */   private String name;
/*    */   private final boolean async;
/*    */   
/*    */   public Event() {
/* 20 */     this(false);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Event(boolean isAsync) {
/* 31 */     this.async = isAsync;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getEventName() {
/* 42 */     if (this.name == null) {
/* 43 */       this.name = getClass().getSimpleName();
/*    */     }
/* 45 */     return this.name;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public abstract HandlerList getHandlers();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final boolean isAsynchronous() {
/* 73 */     return this.async;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public enum Result
/*    */   {
/* 83 */     DENY,
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 88 */     DEFAULT,
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 94 */     ALLOW;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\Event.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */