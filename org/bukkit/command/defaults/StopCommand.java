/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ public class StopCommand
/*    */   extends VanillaCommand
/*    */ {
/*    */   public StopCommand() {
/* 17 */     super("stop");
/* 18 */     this.description = "Stops the server with optional reason";
/* 19 */     this.usageMessage = "/stop [reason]";
/* 20 */     setPermission("bukkit.command.stop");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
/* 25 */     if (!testPermission(sender)) return true;
/*    */     
/* 27 */     Command.broadcastCommandMessage(sender, "Stopping the server..");
/* 28 */     Bukkit.shutdown();
/*    */     
/* 30 */     String reason = createString(args, 0);
/* 31 */     if (StringUtils.isNotEmpty(reason)) {
/* 32 */       for (Player player : Bukkit.getOnlinePlayers()) {
/* 33 */         player.kickPlayer(reason);
/*    */       }
/*    */     }
/*    */     
/* 37 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
/* 42 */     Validate.notNull(sender, "Sender cannot be null");
/* 43 */     Validate.notNull(args, "Arguments cannot be null");
/* 44 */     Validate.notNull(alias, "Alias cannot be null");
/*    */     
/* 46 */     return (List<String>)ImmutableList.of();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\StopCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */