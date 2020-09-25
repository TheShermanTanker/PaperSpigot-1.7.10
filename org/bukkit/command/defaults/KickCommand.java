/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class KickCommand
/*    */   extends VanillaCommand
/*    */ {
/*    */   public KickCommand() {
/* 16 */     super("kick");
/* 17 */     this.description = "Removes the specified player from the server";
/* 18 */     this.usageMessage = "/kick <player> [reason ...]";
/* 19 */     setPermission("bukkit.command.kick");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
/* 24 */     if (!testPermission(sender)) return true; 
/* 25 */     if (args.length < 1 || args[0].length() == 0) {
/* 26 */       sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/* 27 */       return false;
/*    */     } 
/*    */     
/* 30 */     Player player = Bukkit.getPlayerExact(args[0]);
/*    */     
/* 32 */     if (player != null) {
/* 33 */       String reason = "Kicked by an operator.";
/*    */       
/* 35 */       if (args.length > 1) {
/* 36 */         reason = createString(args, 1);
/*    */       }
/*    */       
/* 39 */       player.kickPlayer(reason);
/* 40 */       Command.broadcastCommandMessage(sender, "Kicked player " + player.getName() + ". With reason:\n" + reason);
/*    */     } else {
/* 42 */       sender.sendMessage(args[0] + " not found.");
/*    */     } 
/*    */     
/* 45 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
/* 50 */     Validate.notNull(sender, "Sender cannot be null");
/* 51 */     Validate.notNull(args, "Arguments cannot be null");
/* 52 */     Validate.notNull(alias, "Alias cannot be null");
/*    */     
/* 54 */     if (args.length >= 1) {
/* 55 */       return super.tabComplete(sender, alias, args);
/*    */     }
/* 57 */     return (List<String>)ImmutableList.of();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\KickCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */