/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.GameMode;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.util.StringUtil;
/*    */ 
/*    */ public class DefaultGameModeCommand
/*    */   extends VanillaCommand
/*    */ {
/* 16 */   private static final List<String> GAMEMODE_NAMES = (List<String>)ImmutableList.of("adventure", "creative", "survival");
/*    */   
/*    */   public DefaultGameModeCommand() {
/* 19 */     super("defaultgamemode");
/* 20 */     this.description = "Set the default gamemode";
/* 21 */     this.usageMessage = "/defaultgamemode <mode>";
/* 22 */     setPermission("bukkit.command.defaultgamemode");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(CommandSender sender, String commandLabel, String[] args) {
/* 27 */     if (!testPermission(sender)) return true; 
/* 28 */     if (args.length == 0) {
/* 29 */       sender.sendMessage("Usage: " + this.usageMessage);
/* 30 */       return false;
/*    */     } 
/*    */     
/* 33 */     String modeArg = args[0];
/* 34 */     int value = -1;
/*    */     
/*    */     try {
/* 37 */       value = Integer.parseInt(modeArg);
/* 38 */     } catch (NumberFormatException ex) {}
/*    */     
/* 40 */     GameMode mode = GameMode.getByValue(value);
/*    */     
/* 42 */     if (mode == null) {
/* 43 */       if (modeArg.equalsIgnoreCase("creative") || modeArg.equalsIgnoreCase("c")) {
/* 44 */         mode = GameMode.CREATIVE;
/* 45 */       } else if (modeArg.equalsIgnoreCase("adventure") || modeArg.equalsIgnoreCase("a")) {
/* 46 */         mode = GameMode.ADVENTURE;
/*    */       } else {
/* 48 */         mode = GameMode.SURVIVAL;
/*    */       } 
/*    */     }
/*    */     
/* 52 */     Bukkit.getServer().setDefaultGameMode(mode);
/* 53 */     Command.broadcastCommandMessage(sender, "Default game mode set to " + mode.toString().toLowerCase());
/*    */     
/* 55 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
/* 60 */     Validate.notNull(sender, "Sender cannot be null");
/* 61 */     Validate.notNull(args, "Arguments cannot be null");
/* 62 */     Validate.notNull(alias, "Alias cannot be null");
/*    */     
/* 64 */     if (args.length == 1) {
/* 65 */       return (List<String>)StringUtil.copyPartialMatches(args[0], GAMEMODE_NAMES, new ArrayList(GAMEMODE_NAMES.size()));
/*    */     }
/*    */     
/* 68 */     return (List<String>)ImmutableList.of();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\DefaultGameModeCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */