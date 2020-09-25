/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ public class SayCommand
/*    */   extends VanillaCommand
/*    */ {
/*    */   public SayCommand() {
/* 16 */     super("say");
/* 17 */     this.description = "Broadcasts the given message as the sender";
/* 18 */     this.usageMessage = "/say <message ...>";
/* 19 */     setPermission("bukkit.command.say");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
/* 24 */     if (!testPermission(sender)) return true; 
/* 25 */     if (args.length == 0) {
/* 26 */       sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/* 27 */       return false;
/*    */     } 
/*    */     
/* 30 */     StringBuilder message = new StringBuilder();
/* 31 */     message.append(ChatColor.LIGHT_PURPLE).append("[");
/* 32 */     if (sender instanceof org.bukkit.command.ConsoleCommandSender) {
/* 33 */       message.append("Server");
/* 34 */     } else if (sender instanceof Player) {
/* 35 */       message.append(((Player)sender).getDisplayName());
/*    */     } else {
/* 37 */       message.append(sender.getName());
/*    */     } 
/* 39 */     message.append(ChatColor.LIGHT_PURPLE).append("] ");
/*    */     
/* 41 */     if (args.length > 0) {
/* 42 */       message.append(args[0]);
/* 43 */       for (int i = 1; i < args.length; i++) {
/* 44 */         message.append(" ").append(args[i]);
/*    */       }
/*    */     } 
/*    */     
/* 48 */     Bukkit.broadcastMessage(message.toString());
/* 49 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
/* 54 */     Validate.notNull(sender, "Sender cannot be null");
/* 55 */     Validate.notNull(args, "Arguments cannot be null");
/*    */     
/* 57 */     if (args.length >= 1) {
/* 58 */       return super.tabComplete(sender, alias, args);
/*    */     }
/* 60 */     return (List<String>)ImmutableList.of();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\SayCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */