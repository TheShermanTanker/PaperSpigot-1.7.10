/*    */ package org.bukkit.command.defaults;
/*    */ import java.util.Collections;
/*    */ import java.util.List;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ public class TestForCommand extends VanillaCommand {
/*    */   public TestForCommand() {
/*  9 */     super("testfor");
/* 10 */     this.description = "Tests whether a specifed player is online";
/* 11 */     this.usageMessage = "/testfor <player>";
/* 12 */     setPermission("bukkit.command.testfor");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
/* 17 */     if (!testPermission(sender)) return true; 
/* 18 */     if (args.length < 1) {
/* 19 */       sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/* 20 */       return false;
/*    */     } 
/*    */     
/* 23 */     sender.sendMessage(ChatColor.RED + "/testfor is only usable by commandblocks with analog output.");
/* 24 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
/* 31 */     if (args.length == 0)
/*    */     {
/* 33 */       return super.tabComplete(sender, alias, args);
/*    */     }
/* 35 */     return Collections.emptyList();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\TestForCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */