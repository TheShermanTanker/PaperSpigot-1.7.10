/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class ListCommand
/*    */   extends VanillaCommand
/*    */ {
/*    */   public ListCommand() {
/* 15 */     super("list");
/* 16 */     this.description = "Lists all online players";
/* 17 */     this.usageMessage = "/list";
/* 18 */     setPermission("bukkit.command.list");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
/* 23 */     if (!testPermission(sender)) return true;
/*    */     
/* 25 */     StringBuilder online = new StringBuilder();
/*    */     
/* 27 */     Collection<? extends Player> players = Bukkit.getOnlinePlayers();
/*    */     
/* 29 */     for (Player player : players) {
/*    */       
/* 31 */       if (sender instanceof Player && !((Player)sender).canSee(player)) {
/*    */         continue;
/*    */       }
/* 34 */       if (online.length() > 0) {
/* 35 */         online.append(", ");
/*    */       }
/*    */       
/* 38 */       online.append(player.getDisplayName());
/*    */     } 
/*    */     
/* 41 */     sender.sendMessage("There are " + players.size() + "/" + Bukkit.getMaxPlayers() + " players online:\n" + online.toString());
/*    */     
/* 43 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
/* 48 */     Validate.notNull(sender, "Sender cannot be null");
/* 49 */     Validate.notNull(args, "Arguments cannot be null");
/* 50 */     Validate.notNull(alias, "Alias cannot be null");
/*    */     
/* 52 */     return (List<String>)ImmutableList.of();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\ListCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */