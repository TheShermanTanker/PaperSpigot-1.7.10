/*    */ package org.bukkit.event.player;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PlayerChatTabCompleteEvent
/*    */   extends PlayerEvent
/*    */ {
/* 13 */   private static final HandlerList handlers = new HandlerList();
/*    */   private final String message;
/*    */   private final String lastToken;
/*    */   private final Collection<String> completions;
/*    */   
/*    */   public PlayerChatTabCompleteEvent(Player who, String message, Collection<String> completions) {
/* 19 */     super(who);
/* 20 */     Validate.notNull(message, "Message cannot be null");
/* 21 */     Validate.notNull(completions, "Completions cannot be null");
/* 22 */     this.message = message;
/* 23 */     int i = message.lastIndexOf(' ');
/* 24 */     if (i < 0) {
/* 25 */       this.lastToken = message;
/*    */     } else {
/* 27 */       this.lastToken = message.substring(i + 1);
/*    */     } 
/* 29 */     this.completions = completions;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getChatMessage() {
/* 38 */     return this.message;
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
/*    */   public String getLastToken() {
/* 50 */     return this.lastToken;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Collection<String> getTabCompletions() {
/* 59 */     return this.completions;
/*    */   }
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 64 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 68 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\event\player\PlayerChatTabCompleteEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */