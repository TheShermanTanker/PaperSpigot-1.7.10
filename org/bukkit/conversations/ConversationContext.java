/*    */ package org.bukkit.conversations;
/*    */ 
/*    */ import java.util.Map;
/*    */ import org.bukkit.plugin.Plugin;
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
/*    */ 
/*    */ public class ConversationContext
/*    */ {
/*    */   private Conversable forWhom;
/*    */   private Map<Object, Object> sessionData;
/*    */   private Plugin plugin;
/*    */   
/*    */   public ConversationContext(Plugin plugin, Conversable forWhom, Map<Object, Object> initialSessionData) {
/* 25 */     this.plugin = plugin;
/* 26 */     this.forWhom = forWhom;
/* 27 */     this.sessionData = initialSessionData;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Plugin getPlugin() {
/* 36 */     return this.plugin;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Conversable getForWhom() {
/* 45 */     return this.forWhom;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Map<Object, Object> getAllSessionData() {
/* 53 */     return this.sessionData;
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
/*    */   public Object getSessionData(Object key) {
/* 65 */     return this.sessionData.get(key);
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
/*    */   public void setSessionData(Object key, Object value) {
/* 77 */     this.sessionData.put(key, value);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\conversations\ConversationContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */