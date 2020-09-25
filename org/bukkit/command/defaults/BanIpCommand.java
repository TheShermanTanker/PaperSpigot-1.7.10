/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.List;
/*    */ import java.util.regex.Pattern;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.BanList;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class BanIpCommand
/*    */   extends VanillaCommand
/*    */ {
/* 18 */   public static final Pattern ipValidity = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
/*    */   
/*    */   public BanIpCommand() {
/* 21 */     super("ban-ip");
/* 22 */     this.description = "Prevents the specified IP address from using this server";
/* 23 */     this.usageMessage = "/ban-ip <address|player> [reason ...]";
/* 24 */     setPermission("bukkit.command.ban.ip");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
/* 29 */     if (!testPermission(sender)) return true; 
/* 30 */     if (args.length < 1) {
/* 31 */       sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/* 32 */       return false;
/*    */     } 
/*    */     
/* 35 */     String reason = (args.length > 0) ? StringUtils.join((Object[])args, ' ', 1, args.length) : null;
/*    */     
/* 37 */     if (ipValidity.matcher(args[0]).matches()) {
/* 38 */       processIPBan(args[0], sender, reason);
/*    */     } else {
/* 40 */       Player player = Bukkit.getPlayer(args[0]);
/*    */       
/* 42 */       if (player == null) {
/* 43 */         sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/* 44 */         return false;
/*    */       } 
/*    */       
/* 47 */       processIPBan(player.getAddress().getAddress().getHostAddress(), sender, reason);
/*    */     } 
/*    */     
/* 50 */     return true;
/*    */   }
/*    */   
/*    */   private void processIPBan(String ip, CommandSender sender, String reason) {
/* 54 */     Bukkit.getBanList(BanList.Type.IP).addBan(ip, reason, null, sender.getName());
/*    */ 
/*    */     
/* 57 */     for (Player player : Bukkit.getOnlinePlayers()) {
/* 58 */       if (player.getAddress().getAddress().getHostAddress().equals(ip)) {
/* 59 */         player.kickPlayer("You have been IP banned.");
/*    */       }
/*    */     } 
/*    */     
/* 63 */     Command.broadcastCommandMessage(sender, "Banned IP Address " + ip);
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
/* 68 */     Validate.notNull(sender, "Sender cannot be null");
/* 69 */     Validate.notNull(args, "Arguments cannot be null");
/* 70 */     Validate.notNull(alias, "Alias cannot be null");
/*    */     
/* 72 */     if (args.length == 1) {
/* 73 */       return super.tabComplete(sender, alias, args);
/*    */     }
/* 75 */     return (List<String>)ImmutableList.of();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\BanIpCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */