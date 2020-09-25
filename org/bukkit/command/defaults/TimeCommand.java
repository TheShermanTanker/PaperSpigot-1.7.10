/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.util.StringUtil;
/*    */ 
/*    */ public class TimeCommand
/*    */   extends VanillaCommand
/*    */ {
/* 17 */   private static final List<String> TABCOMPLETE_ADD_SET = (List<String>)ImmutableList.of("add", "set");
/* 18 */   private static final List<String> TABCOMPLETE_DAY_NIGHT = (List<String>)ImmutableList.of("day", "night");
/*    */   
/*    */   public TimeCommand() {
/* 21 */     super("time");
/* 22 */     this.description = "Changes the time on each world";
/* 23 */     this.usageMessage = "/time set <value>\n/time add <value>";
/* 24 */     setPermission("bukkit.command.time.add;bukkit.command.time.set");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
/* 29 */     if (args.length < 2) {
/* 30 */       sender.sendMessage(ChatColor.RED + "Incorrect usage. Correct usage:\n" + this.usageMessage);
/* 31 */       return false;
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 36 */     if (args[0].equals("set")) {
/* 37 */       int value; if (!sender.hasPermission("bukkit.command.time.set")) {
/* 38 */         sender.sendMessage(ChatColor.RED + "You don't have permission to set the time");
/* 39 */         return true;
/*    */       } 
/*    */       
/* 42 */       if (args[1].equals("day")) {
/* 43 */         value = 0;
/* 44 */       } else if (args[1].equals("night")) {
/* 45 */         value = 12500;
/*    */       } else {
/* 47 */         value = getInteger(sender, args[1], 0);
/*    */       } 
/*    */       
/* 50 */       for (World world : Bukkit.getWorlds()) {
/* 51 */         world.setTime(value);
/*    */       }
/*    */       
/* 54 */       Command.broadcastCommandMessage(sender, "Set time to " + value);
/* 55 */     } else if (args[0].equals("add")) {
/* 56 */       if (!sender.hasPermission("bukkit.command.time.add")) {
/* 57 */         sender.sendMessage(ChatColor.RED + "You don't have permission to set the time");
/* 58 */         return true;
/*    */       } 
/*    */       
/* 61 */       int value = getInteger(sender, args[1], 0);
/*    */       
/* 63 */       for (World world : Bukkit.getWorlds()) {
/* 64 */         world.setFullTime(world.getFullTime() + value);
/*    */       }
/*    */       
/* 67 */       Command.broadcastCommandMessage(sender, "Added " + value + " to time");
/*    */     } else {
/* 69 */       sender.sendMessage("Unknown method. Usage: " + this.usageMessage);
/*    */     } 
/*    */     
/* 72 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
/* 77 */     Validate.notNull(sender, "Sender cannot be null");
/* 78 */     Validate.notNull(args, "Arguments cannot be null");
/* 79 */     Validate.notNull(alias, "Alias cannot be null");
/*    */     
/* 81 */     if (args.length == 1)
/* 82 */       return (List<String>)StringUtil.copyPartialMatches(args[0], TABCOMPLETE_ADD_SET, new ArrayList(TABCOMPLETE_ADD_SET.size())); 
/* 83 */     if (args.length == 2 && args[0].equalsIgnoreCase("set")) {
/* 84 */       return (List<String>)StringUtil.copyPartialMatches(args[1], TABCOMPLETE_DAY_NIGHT, new ArrayList(TABCOMPLETE_DAY_NIGHT.size()));
/*    */     }
/* 86 */     return (List<String>)ImmutableList.of();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\TimeCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */