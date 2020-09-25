/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.util.StringUtil;
/*    */ 
/*    */ public class WeatherCommand
/*    */   extends VanillaCommand {
/* 17 */   private static final List<String> WEATHER_TYPES = (List<String>)ImmutableList.of("clear", "rain", "thunder");
/*    */   
/*    */   public WeatherCommand() {
/* 20 */     super("weather");
/* 21 */     this.description = "Changes the weather";
/* 22 */     this.usageMessage = "/weather <clear/rain/thunder> [duration in seconds]";
/* 23 */     setPermission("bukkit.command.weather");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
/* 28 */     if (!testPermission(sender)) return true; 
/* 29 */     if (args.length == 0) {
/* 30 */       sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/* 31 */       return false;
/*    */     } 
/*    */     
/* 34 */     int duration = (300 + (new Random()).nextInt(600)) * 20;
/* 35 */     if (args.length >= 2) {
/* 36 */       duration = getInteger(sender, args[1], 1, 1000000) * 20;
/*    */     }
/*    */     
/* 39 */     World world = Bukkit.getWorlds().get(0);
/*    */     
/* 41 */     world.setWeatherDuration(duration);
/* 42 */     world.setThunderDuration(duration);
/*    */     
/* 44 */     if ("clear".equalsIgnoreCase(args[0])) {
/* 45 */       world.setStorm(false);
/* 46 */       world.setThundering(false);
/* 47 */       Command.broadcastCommandMessage(sender, "Changed weather to clear for " + (duration / 20) + " seconds.");
/* 48 */     } else if ("rain".equalsIgnoreCase(args[0])) {
/* 49 */       world.setStorm(true);
/* 50 */       world.setThundering(false);
/* 51 */       Command.broadcastCommandMessage(sender, "Changed weather to rainy for " + (duration / 20) + " seconds.");
/* 52 */     } else if ("thunder".equalsIgnoreCase(args[0])) {
/* 53 */       world.setStorm(true);
/* 54 */       world.setThundering(true);
/* 55 */       Command.broadcastCommandMessage(sender, "Changed weather to thundering " + (duration / 20) + " seconds.");
/*    */     } 
/*    */     
/* 58 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
/* 63 */     Validate.notNull(sender, "Sender cannot be null");
/* 64 */     Validate.notNull(args, "Arguments cannot be null");
/* 65 */     Validate.notNull(alias, "Alias cannot be null");
/*    */     
/* 67 */     if (args.length == 1) {
/* 68 */       return (List<String>)StringUtil.copyPartialMatches(args[0], WEATHER_TYPES, new ArrayList(WEATHER_TYPES.size()));
/*    */     }
/*    */     
/* 71 */     return (List<String>)ImmutableList.of();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\WeatherCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */