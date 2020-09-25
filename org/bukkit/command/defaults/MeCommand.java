/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ public class MeCommand extends VanillaCommand {
/*    */   public MeCommand() {
/*  9 */     super("me");
/* 10 */     this.description = "Performs the specified action in chat";
/* 11 */     this.usageMessage = "/me <action>";
/* 12 */     setPermission("bukkit.command.me");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
/* 17 */     if (!testPermission(sender)) return true; 
/* 18 */     if (args.length < 1) {
/* 19 */       sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/* 20 */       return false;
/*    */     } 
/*    */     
/* 23 */     StringBuilder message = new StringBuilder();
/* 24 */     message.append(sender.getName());
/*    */     
/* 26 */     for (String arg : args) {
/* 27 */       message.append(" ");
/* 28 */       message.append(arg);
/*    */     } 
/*    */     
/* 31 */     Bukkit.broadcastMessage("* " + message.toString());
/*    */     
/* 33 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\MeCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */