/*    */ package org.bukkit.craftbukkit.v1_7_R4.help;
/*    */ 
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.help.HelpTopic;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CustomHelpTopic
/*    */   extends HelpTopic
/*    */ {
/*    */   private final String permissionNode;
/*    */   
/*    */   public CustomHelpTopic(String name, String shortText, String fullText, String permissionNode) {
/* 14 */     this.permissionNode = permissionNode;
/* 15 */     this.name = name;
/* 16 */     this.shortText = shortText;
/* 17 */     this.fullText = shortText + "\n" + fullText;
/*    */   }
/*    */   
/*    */   public boolean canSee(CommandSender sender) {
/* 21 */     if (sender instanceof org.bukkit.command.ConsoleCommandSender) {
/* 22 */       return true;
/*    */     }
/*    */     
/* 25 */     if (!this.permissionNode.equals("")) {
/* 26 */       return sender.hasPermission(this.permissionNode);
/*    */     }
/* 28 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\help\CustomHelpTopic.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */