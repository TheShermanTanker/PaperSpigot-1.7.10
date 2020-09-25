/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.util.StringUtil;
/*    */ 
/*    */ public class PardonIpCommand
/*    */   extends VanillaCommand
/*    */ {
/*    */   public PardonIpCommand() {
/* 17 */     super("pardon-ip");
/* 18 */     this.description = "Allows the specified IP address to use this server";
/* 19 */     this.usageMessage = "/pardon-ip <address>";
/* 20 */     setPermission("bukkit.command.unban.ip");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
/* 25 */     if (!testPermission(sender)) return true; 
/* 26 */     if (args.length != 1) {
/* 27 */       sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/* 28 */       return false;
/*    */     } 
/*    */     
/* 31 */     if (BanIpCommand.ipValidity.matcher(args[0]).matches()) {
/* 32 */       Bukkit.unbanIP(args[0]);
/* 33 */       Command.broadcastCommandMessage(sender, "Pardoned ip " + args[0]);
/*    */     } else {
/* 35 */       sender.sendMessage("Invalid ip");
/*    */     } 
/*    */     
/* 38 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
/* 43 */     Validate.notNull(sender, "Sender cannot be null");
/* 44 */     Validate.notNull(args, "Arguments cannot be null");
/* 45 */     Validate.notNull(alias, "Alias cannot be null");
/*    */     
/* 47 */     if (args.length == 1) {
/* 48 */       return (List<String>)StringUtil.copyPartialMatches(args[0], Bukkit.getIPBans(), new ArrayList());
/*    */     }
/* 50 */     return (List<String>)ImmutableList.of();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\PardonIpCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */