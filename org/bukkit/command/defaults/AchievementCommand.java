/*     */ package org.bukkit.command.defaults;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Achievement;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Statistic;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.EntityType;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.Event;
/*     */ import org.bukkit.event.player.PlayerAchievementAwardedEvent;
/*     */ import org.bukkit.event.player.PlayerStatisticIncrementEvent;
/*     */ 
/*     */ public class AchievementCommand
/*     */   extends VanillaCommand
/*     */ {
/*     */   public AchievementCommand() {
/*  25 */     super("achievement");
/*  26 */     this.description = "Gives the specified player an achievement or changes a statistic value. Use '*' to give all achievements.";
/*  27 */     this.usageMessage = "/achievement give <stat_name> [player]";
/*  28 */     setPermission("bukkit.command.achievement");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
/*  33 */     if (!testPermission(sender)) return true;
/*     */     
/*  35 */     if (args.length < 2) {
/*  36 */       sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/*  37 */       return false;
/*     */     } 
/*     */     
/*  40 */     if (!args[0].equalsIgnoreCase("give")) {
/*  41 */       sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/*  42 */       return false;
/*     */     } 
/*     */     
/*  45 */     String statisticString = args[1];
/*  46 */     Player player = null;
/*     */     
/*  48 */     if (args.length > 2) {
/*  49 */       player = Bukkit.getPlayer(args[1]);
/*  50 */     } else if (sender instanceof Player) {
/*  51 */       player = (Player)sender;
/*     */     } 
/*     */     
/*  54 */     if (player == null) {
/*  55 */       sender.sendMessage("You must specify which player you wish to perform this action on.");
/*  56 */       return true;
/*     */     } 
/*     */     
/*  59 */     if (statisticString.equals("*")) {
/*  60 */       for (Achievement achievement1 : Achievement.values()) {
/*  61 */         if (!player.hasAchievement(achievement1)) {
/*     */ 
/*     */           
/*  64 */           PlayerAchievementAwardedEvent event = new PlayerAchievementAwardedEvent(player, achievement1);
/*  65 */           Bukkit.getServer().getPluginManager().callEvent((Event)event);
/*  66 */           if (!event.isCancelled())
/*  67 */             player.awardAchievement(achievement1); 
/*     */         } 
/*     */       } 
/*  70 */       Command.broadcastCommandMessage(sender, String.format("Successfully given all achievements to %s", new Object[] { player.getName() }));
/*  71 */       return true;
/*     */     } 
/*     */     
/*  74 */     Achievement achievement = Bukkit.getUnsafe().getAchievementFromInternalName(statisticString);
/*  75 */     Statistic statistic = Bukkit.getUnsafe().getStatisticFromInternalName(statisticString);
/*     */     
/*  77 */     if (achievement != null) {
/*  78 */       if (player.hasAchievement(achievement)) {
/*  79 */         sender.sendMessage(String.format("%s already has achievement %s", new Object[] { player.getName(), statisticString }));
/*  80 */         return true;
/*     */       } 
/*     */       
/*  83 */       PlayerAchievementAwardedEvent event = new PlayerAchievementAwardedEvent(player, achievement);
/*  84 */       Bukkit.getServer().getPluginManager().callEvent((Event)event);
/*  85 */       if (event.isCancelled()) {
/*  86 */         sender.sendMessage(String.format("Unable to award %s the achievement %s", new Object[] { player.getName(), statisticString }));
/*  87 */         return true;
/*     */       } 
/*  89 */       player.awardAchievement(achievement);
/*     */       
/*  91 */       Command.broadcastCommandMessage(sender, String.format("Successfully given %s the stat %s", new Object[] { player.getName(), statisticString }));
/*  92 */       return true;
/*     */     } 
/*     */     
/*  95 */     if (statistic == null) {
/*  96 */       sender.sendMessage(String.format("Unknown achievement or statistic '%s'", new Object[] { statisticString }));
/*  97 */       return true;
/*     */     } 
/*     */     
/* 100 */     if (statistic.getType() == Statistic.Type.UNTYPED) {
/* 101 */       PlayerStatisticIncrementEvent event = new PlayerStatisticIncrementEvent(player, statistic, player.getStatistic(statistic), player.getStatistic(statistic) + 1);
/* 102 */       Bukkit.getServer().getPluginManager().callEvent((Event)event);
/* 103 */       if (event.isCancelled()) {
/* 104 */         sender.sendMessage(String.format("Unable to increment %s for %s", new Object[] { statisticString, player.getName() }));
/* 105 */         return true;
/*     */       } 
/* 107 */       player.incrementStatistic(statistic);
/* 108 */       Command.broadcastCommandMessage(sender, String.format("Successfully given %s the stat %s", new Object[] { player.getName(), statisticString }));
/* 109 */       return true;
/*     */     } 
/*     */     
/* 112 */     if (statistic.getType() == Statistic.Type.ENTITY) {
/* 113 */       EntityType entityType = EntityType.fromName(statisticString.substring(statisticString.lastIndexOf(".") + 1));
/*     */       
/* 115 */       if (entityType == null) {
/* 116 */         sender.sendMessage(String.format("Unknown achievement or statistic '%s'", new Object[] { statisticString }));
/* 117 */         return true;
/*     */       } 
/*     */       
/* 120 */       PlayerStatisticIncrementEvent event = new PlayerStatisticIncrementEvent(player, statistic, player.getStatistic(statistic), player.getStatistic(statistic) + 1, entityType);
/* 121 */       Bukkit.getServer().getPluginManager().callEvent((Event)event);
/* 122 */       if (event.isCancelled()) {
/* 123 */         sender.sendMessage(String.format("Unable to increment %s for %s", new Object[] { statisticString, player.getName() }));
/* 124 */         return true;
/*     */       } 
/*     */       
/*     */       try {
/* 128 */         player.incrementStatistic(statistic, entityType);
/* 129 */       } catch (IllegalArgumentException e) {
/* 130 */         sender.sendMessage(String.format("Unknown achievement or statistic '%s'", new Object[] { statisticString }));
/* 131 */         return true;
/*     */       } 
/*     */     } else {
/*     */       int id;
/*     */       try {
/* 136 */         id = getInteger(sender, statisticString.substring(statisticString.lastIndexOf(".") + 1), 0, 2147483647, true);
/* 137 */       } catch (NumberFormatException e) {
/* 138 */         sender.sendMessage(e.getMessage());
/* 139 */         return true;
/*     */       } 
/*     */       
/* 142 */       Material material = Material.getMaterial(id);
/*     */       
/* 144 */       if (material == null) {
/* 145 */         sender.sendMessage(String.format("Unknown achievement or statistic '%s'", new Object[] { statisticString }));
/* 146 */         return true;
/*     */       } 
/*     */       
/* 149 */       PlayerStatisticIncrementEvent event = new PlayerStatisticIncrementEvent(player, statistic, player.getStatistic(statistic), player.getStatistic(statistic) + 1, material);
/* 150 */       Bukkit.getServer().getPluginManager().callEvent((Event)event);
/* 151 */       if (event.isCancelled()) {
/* 152 */         sender.sendMessage(String.format("Unable to increment %s for %s", new Object[] { statisticString, player.getName() }));
/* 153 */         return true;
/*     */       } 
/*     */       
/*     */       try {
/* 157 */         player.incrementStatistic(statistic, material);
/* 158 */       } catch (IllegalArgumentException e) {
/* 159 */         sender.sendMessage(String.format("Unknown achievement or statistic '%s'", new Object[] { statisticString }));
/* 160 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/* 164 */     Command.broadcastCommandMessage(sender, String.format("Successfully given %s the stat %s", new Object[] { player.getName(), statisticString }));
/* 165 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
/* 170 */     Validate.notNull(sender, "Sender cannot be null");
/* 171 */     Validate.notNull(args, "Arguments cannot be null");
/* 172 */     Validate.notNull(alias, "Alias cannot be null");
/*     */     
/* 174 */     if (args.length == 1) {
/* 175 */       return Arrays.asList(new String[] { "give" });
/*     */     }
/*     */     
/* 178 */     if (args.length == 2) {
/* 179 */       return Bukkit.getUnsafe().tabCompleteInternalStatisticOrAchievementName(args[1], new ArrayList());
/*     */     }
/*     */     
/* 182 */     if (args.length == 3) {
/* 183 */       return super.tabComplete(sender, alias, args);
/*     */     }
/* 185 */     return (List<String>)ImmutableList.of();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\AchievementCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */