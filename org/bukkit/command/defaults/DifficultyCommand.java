/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.Difficulty;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.util.StringUtil;
/*    */ 
/*    */ public class DifficultyCommand extends VanillaCommand {
/* 16 */   private static final List<String> DIFFICULTY_NAMES = (List<String>)ImmutableList.of("peaceful", "easy", "normal", "hard");
/*    */   
/*    */   public DifficultyCommand() {
/* 19 */     super("difficulty");
/* 20 */     this.description = "Sets the game difficulty";
/* 21 */     this.usageMessage = "/difficulty <new difficulty> ";
/* 22 */     setPermission("bukkit.command.difficulty");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
/* 27 */     if (!testPermission(sender)) return true; 
/* 28 */     if (args.length != 1 || args[0].length() == 0) {
/* 29 */       sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/* 30 */       return false;
/*    */     } 
/*    */     
/* 33 */     Difficulty difficulty = Difficulty.getByValue(getDifficultyForString(sender, args[0]));
/*    */     
/* 35 */     if (Bukkit.isHardcore()) {
/* 36 */       difficulty = Difficulty.HARD;
/*    */     }
/*    */     
/* 39 */     ((World)Bukkit.getWorlds().get(0)).setDifficulty(difficulty);
/*    */     
/* 41 */     int levelCount = 1;
/* 42 */     if (Bukkit.getAllowNether()) {
/* 43 */       ((World)Bukkit.getWorlds().get(levelCount)).setDifficulty(difficulty);
/* 44 */       levelCount++;
/*    */     } 
/*    */     
/* 47 */     if (Bukkit.getAllowEnd()) {
/* 48 */       ((World)Bukkit.getWorlds().get(levelCount)).setDifficulty(difficulty);
/*    */     }
/*    */     
/* 51 */     Command.broadcastCommandMessage(sender, "Set difficulty to " + difficulty.toString());
/* 52 */     return true;
/*    */   }
/*    */   
/*    */   protected int getDifficultyForString(CommandSender sender, String name) {
/* 56 */     if (name.equalsIgnoreCase("peaceful") || name.equalsIgnoreCase("p"))
/* 57 */       return 0; 
/* 58 */     if (name.equalsIgnoreCase("easy") || name.equalsIgnoreCase("e"))
/* 59 */       return 1; 
/* 60 */     if (name.equalsIgnoreCase("normal") || name.equalsIgnoreCase("n"))
/* 61 */       return 2; 
/* 62 */     if (name.equalsIgnoreCase("hard") || name.equalsIgnoreCase("h")) {
/* 63 */       return 3;
/*    */     }
/* 65 */     return getInteger(sender, name, 0, 3);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
/* 71 */     Validate.notNull(sender, "Sender cannot be null");
/* 72 */     Validate.notNull(args, "Arguments cannot be null");
/* 73 */     Validate.notNull(alias, "Alias cannot be null");
/*    */     
/* 75 */     if (args.length == 1) {
/* 76 */       return (List<String>)StringUtil.copyPartialMatches(args[0], DIFFICULTY_NAMES, new ArrayList(DIFFICULTY_NAMES.size()));
/*    */     }
/*    */     
/* 79 */     return (List<String>)ImmutableList.of();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\DifficultyCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */