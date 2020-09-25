/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.OfflinePlayer;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.util.StringUtil;
/*    */ 
/*    */ public class DeopCommand
/*    */   extends VanillaCommand
/*    */ {
/*    */   public DeopCommand() {
/* 19 */     super("deop");
/* 20 */     this.description = "Takes the specified player's operator status";
/* 21 */     this.usageMessage = "/deop <player>";
/* 22 */     setPermission("bukkit.command.op.take");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
/* 27 */     if (!testPermission(sender)) return true; 
/* 28 */     if (args.length != 1 || args[0].length() == 0) {
/* 29 */       sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/* 30 */       return false;
/*    */     } 
/*    */     
/* 33 */     OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
/* 34 */     player.setOp(false);
/*    */     
/* 36 */     if (player instanceof Player) {
/* 37 */       ((Player)player).sendMessage(ChatColor.YELLOW + "You are no longer op!");
/*    */     }
/*    */     
/* 40 */     Command.broadcastCommandMessage(sender, "De-opped " + args[0]);
/* 41 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
/* 46 */     Validate.notNull(sender, "Sender cannot be null");
/* 47 */     Validate.notNull(args, "Arguments cannot be null");
/* 48 */     Validate.notNull(alias, "Alias cannot be null");
/*    */     
/* 50 */     if (args.length == 1) {
/* 51 */       List<String> completions = new ArrayList<String>();
/* 52 */       for (OfflinePlayer player : Bukkit.getOperators()) {
/* 53 */         String playerName = player.getName();
/* 54 */         if (StringUtil.startsWithIgnoreCase(playerName, args[0])) {
/* 55 */           completions.add(playerName);
/*    */         }
/*    */       } 
/* 58 */       return completions;
/*    */     } 
/* 60 */     return (List<String>)ImmutableList.of();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\DeopCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */