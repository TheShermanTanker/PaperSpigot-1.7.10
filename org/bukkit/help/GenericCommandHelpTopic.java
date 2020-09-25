/*    */ package org.bukkit.help;
/*    */ 
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
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
/*    */ public class GenericCommandHelpTopic
/*    */   extends HelpTopic
/*    */ {
/*    */   protected Command command;
/*    */   
/*    */   public GenericCommandHelpTopic(Command command) {
/* 23 */     this.command = command;
/*    */     
/* 25 */     if (command.getLabel().startsWith("/")) {
/* 26 */       this.name = command.getLabel();
/*    */     } else {
/* 28 */       this.name = "/" + command.getLabel();
/*    */     } 
/*    */ 
/*    */     
/* 32 */     int i = command.getDescription().indexOf("\n");
/* 33 */     if (i > 1) {
/* 34 */       this.shortText = command.getDescription().substring(0, i - 1);
/*    */     } else {
/* 36 */       this.shortText = command.getDescription();
/*    */     } 
/*    */ 
/*    */     
/* 40 */     StringBuffer sb = new StringBuffer();
/*    */     
/* 42 */     sb.append(ChatColor.GOLD);
/* 43 */     sb.append("Description: ");
/* 44 */     sb.append(ChatColor.WHITE);
/* 45 */     sb.append(command.getDescription());
/*    */     
/* 47 */     sb.append("\n");
/*    */     
/* 49 */     sb.append(ChatColor.GOLD);
/* 50 */     sb.append("Usage: ");
/* 51 */     sb.append(ChatColor.WHITE);
/* 52 */     sb.append(command.getUsage().replace("<command>", this.name.substring(1)));
/*    */     
/* 54 */     if (command.getAliases().size() > 0) {
/* 55 */       sb.append("\n");
/* 56 */       sb.append(ChatColor.GOLD);
/* 57 */       sb.append("Aliases: ");
/* 58 */       sb.append(ChatColor.WHITE);
/* 59 */       sb.append(ChatColor.WHITE + StringUtils.join(command.getAliases(), ", "));
/*    */     } 
/* 61 */     this.fullText = sb.toString();
/*    */   }
/*    */   
/*    */   public boolean canSee(CommandSender sender) {
/* 65 */     if (!this.command.isRegistered() && !(this.command instanceof org.bukkit.command.defaults.VanillaCommand))
/*    */     {
/* 67 */       return false;
/*    */     }
/*    */     
/* 70 */     if (sender instanceof org.bukkit.command.ConsoleCommandSender) {
/* 71 */       return true;
/*    */     }
/*    */     
/* 74 */     if (this.amendedPermission != null) {
/* 75 */       return sender.hasPermission(this.amendedPermission);
/*    */     }
/* 77 */     return this.command.testPermissionSilent(sender);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\help\GenericCommandHelpTopic.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */