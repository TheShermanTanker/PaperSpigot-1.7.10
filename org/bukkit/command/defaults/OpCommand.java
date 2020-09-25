/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collections;
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
/*    */ public class OpCommand
/*    */   extends VanillaCommand
/*    */ {
/*    */   public OpCommand() {
/* 20 */     super("op");
/* 21 */     this.description = "Gives the specified player operator status";
/* 22 */     this.usageMessage = "/op <player>";
/* 23 */     setPermission("bukkit.command.op.give");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
/* 28 */     if (!testPermission(sender)) return true; 
/* 29 */     if (args.length != 1 || args[0].length() == 0) {
/* 30 */       sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/* 31 */       return false;
/*    */     } 
/*    */     
/* 34 */     OfflinePlayer player = Bukkit.getOfflinePlayer(args[0]);
/* 35 */     player.setOp(true);
/*    */     
/* 37 */     Command.broadcastCommandMessage(sender, "Opped " + args[0]);
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
/* 48 */       if (!(sender instanceof Player)) {
/* 49 */         return (List<String>)ImmutableList.of();
/*    */       }
/*    */       
/* 52 */       String lastWord = args[0];
/* 53 */       if (lastWord.length() == 0) {
/* 54 */         return (List<String>)ImmutableList.of();
/*    */       }
/*    */       
/* 57 */       Player senderPlayer = (Player)sender;
/*    */       
/* 59 */       ArrayList<String> matchedPlayers = new ArrayList<String>();
/* 60 */       for (Player player : sender.getServer().getOnlinePlayers()) {
/* 61 */         String name = player.getName();
/* 62 */         if (!senderPlayer.canSee(player) || player.isOp()) {
/*    */           continue;
/*    */         }
/* 65 */         if (StringUtil.startsWithIgnoreCase(name, lastWord)) {
/* 66 */           matchedPlayers.add(name);
/*    */         }
/*    */       } 
/*    */       
/* 70 */       Collections.sort(matchedPlayers, String.CASE_INSENSITIVE_ORDER);
/* 71 */       return matchedPlayers;
/*    */     } 
/* 73 */     return (List<String>)ImmutableList.of();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\OpCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */