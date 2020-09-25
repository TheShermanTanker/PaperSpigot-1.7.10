/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ 
/*    */ public class SetIdleTimeoutCommand
/*    */   extends VanillaCommand
/*    */ {
/*    */   public SetIdleTimeoutCommand() {
/* 16 */     super("setidletimeout");
/* 17 */     this.description = "Sets the server's idle timeout";
/* 18 */     this.usageMessage = "/setidletimeout <Minutes until kick>";
/* 19 */     setPermission("bukkit.command.setidletimeout");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
/* 24 */     if (!testPermission(sender)) return true;
/*    */     
/* 26 */     if (args.length == 1) {
/*    */       int minutes;
/*    */       
/*    */       try {
/* 30 */         minutes = getInteger(sender, args[0], 0, 2147483647, true);
/* 31 */       } catch (NumberFormatException ex) {
/* 32 */         sender.sendMessage(ex.getMessage());
/* 33 */         return true;
/*    */       } 
/*    */       
/* 36 */       Bukkit.getServer().setIdleTimeout(minutes);
/*    */       
/* 38 */       Command.broadcastCommandMessage(sender, "Successfully set the idle timeout to " + minutes + " minutes.");
/* 39 */       return true;
/*    */     } 
/* 41 */     sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/* 42 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
/* 47 */     Validate.notNull(sender, "Sender cannot be null");
/* 48 */     Validate.notNull(args, "Arguments cannot be null");
/* 49 */     Validate.notNull(alias, "Alias cannot be null");
/*    */     
/* 51 */     return (List<String>)ImmutableList.of();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\SetIdleTimeoutCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */