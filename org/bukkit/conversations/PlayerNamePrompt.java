/*    */ package org.bukkit.conversations;
/*    */ 
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class PlayerNamePrompt
/*    */   extends ValidatingPrompt
/*    */ {
/*    */   private Plugin plugin;
/*    */   
/*    */   public PlayerNamePrompt(Plugin plugin) {
/* 15 */     this.plugin = plugin;
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isInputValid(ConversationContext context, String input) {
/* 20 */     return (this.plugin.getServer().getPlayer(input) != null);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected Prompt acceptValidatedInput(ConversationContext context, String input) {
/* 26 */     return acceptValidatedInput(context, this.plugin.getServer().getPlayer(input));
/*    */   }
/*    */   
/*    */   protected abstract Prompt acceptValidatedInput(ConversationContext paramConversationContext, Player paramPlayer);
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\conversations\PlayerNamePrompt.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */