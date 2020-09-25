/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.BanList;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.OfflinePlayer;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.util.StringUtil;
/*    */ 
/*    */ public class PardonCommand
/*    */   extends VanillaCommand
/*    */ {
/*    */   public PardonCommand() {
/* 19 */     super("pardon");
/* 20 */     this.description = "Allows the specified player to use this server";
/* 21 */     this.usageMessage = "/pardon <player>";
/* 22 */     setPermission("bukkit.command.unban.player");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
/* 27 */     if (!testPermission(sender)) return true; 
/* 28 */     if (args.length != 1) {
/* 29 */       sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/* 30 */       return false;
/*    */     } 
/*    */     
/* 33 */     Bukkit.getBanList(BanList.Type.NAME).pardon(args[0]);
/* 34 */     Command.broadcastCommandMessage(sender, "Pardoned " + args[0]);
/* 35 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
/* 40 */     Validate.notNull(sender, "Sender cannot be null");
/* 41 */     Validate.notNull(args, "Arguments cannot be null");
/* 42 */     Validate.notNull(alias, "Alias cannot be null");
/*    */     
/* 44 */     if (args.length == 1) {
/* 45 */       List<String> completions = new ArrayList<String>();
/* 46 */       for (OfflinePlayer player : Bukkit.getBannedPlayers()) {
/* 47 */         String name = player.getName();
/* 48 */         if (StringUtil.startsWithIgnoreCase(name, args[0])) {
/* 49 */           completions.add(name);
/*    */         }
/*    */       } 
/* 52 */       return completions;
/*    */     } 
/* 54 */     return (List<String>)ImmutableList.of();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\PardonCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */