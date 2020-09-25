/*    */ package org.bukkit.command.defaults;
/*    */ import java.util.Arrays;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class TellCommand extends VanillaCommand {
/*    */   public TellCommand() {
/* 12 */     super("tell");
/* 13 */     this.description = "Sends a private message to the given player";
/* 14 */     this.usageMessage = "/tell <player> <message>";
/* 15 */     setAliases(Arrays.asList(new String[] { "w", "msg" }));
/* 16 */     setPermission("bukkit.command.tell");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
/* 21 */     if (!testPermission(sender)) return true; 
/* 22 */     if (args.length < 2) {
/* 23 */       sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/* 24 */       return false;
/*    */     } 
/*    */     
/* 27 */     Player player = Bukkit.getPlayerExact(args[0]);
/*    */ 
/*    */     
/* 30 */     if (player == null || (sender instanceof Player && !((Player)sender).canSee(player))) {
/* 31 */       sender.sendMessage("There's no player by that name online.");
/*    */     } else {
/* 33 */       StringBuilder message = new StringBuilder();
/*    */       
/* 35 */       for (int i = 1; i < args.length; i++) {
/* 36 */         if (i > 1) message.append(" "); 
/* 37 */         message.append(args[i]);
/*    */       } 
/*    */       
/* 40 */       String result = ChatColor.GRAY + sender.getName() + " whispers " + message;
/*    */       
/* 42 */       sender.sendMessage("[" + sender.getName() + "->" + player.getName() + "] " + message);
/* 43 */       player.sendMessage(result);
/*    */     } 
/*    */     
/* 46 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
/* 53 */     if (args.length == 0)
/*    */     {
/* 55 */       return super.tabComplete(sender, alias, args);
/*    */     }
/* 57 */     return Collections.emptyList();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\TellCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */