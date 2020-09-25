/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.BanList;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class BanCommand
/*    */   extends VanillaCommand
/*    */ {
/*    */   public BanCommand() {
/* 18 */     super("ban");
/* 19 */     this.description = "Prevents the specified player from using this server";
/* 20 */     this.usageMessage = "/ban <player> [reason ...]";
/* 21 */     setPermission("bukkit.command.ban.player");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
/* 26 */     if (!testPermission(sender)) return true; 
/* 27 */     if (args.length == 0) {
/* 28 */       sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/* 29 */       return false;
/*    */     } 
/*    */     
/* 32 */     String reason = (args.length > 0) ? StringUtils.join((Object[])args, ' ', 1, args.length) : null;
/* 33 */     Bukkit.getBanList(BanList.Type.NAME).addBan(args[0], reason, null, sender.getName());
/*    */     
/* 35 */     Player player = Bukkit.getPlayer(args[0]);
/* 36 */     if (player != null) {
/* 37 */       player.kickPlayer("Banned by admin.");
/*    */     }
/*    */     
/* 40 */     Command.broadcastCommandMessage(sender, "Banned player " + args[0]);
/* 41 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
/* 46 */     Validate.notNull(sender, "Sender cannot be null");
/* 47 */     Validate.notNull(args, "Arguments cannot be null");
/* 48 */     Validate.notNull(alias, "Alias cannot be null");
/*    */     
/* 50 */     if (args.length >= 1) {
/* 51 */       return super.tabComplete(sender, alias, args);
/*    */     }
/* 53 */     return (List<String>)ImmutableList.of();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\BanCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */