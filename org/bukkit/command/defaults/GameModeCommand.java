/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.GameMode;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.util.StringUtil;
/*    */ 
/*    */ public class GameModeCommand
/*    */   extends VanillaCommand
/*    */ {
/* 18 */   private static final List<String> GAMEMODE_NAMES = (List<String>)ImmutableList.of("adventure", "creative", "survival");
/*    */   
/*    */   public GameModeCommand() {
/* 21 */     super("gamemode");
/* 22 */     this.description = "Changes the player to a specific game mode";
/* 23 */     this.usageMessage = "/gamemode <mode> [player]";
/* 24 */     setPermission("bukkit.command.gamemode");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
/* 29 */     if (!testPermission(sender)) return true; 
/* 30 */     if (args.length == 0) {
/* 31 */       sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/* 32 */       return false;
/*    */     } 
/*    */     
/* 35 */     String modeArg = args[0];
/* 36 */     String playerArg = sender.getName();
/*    */     
/* 38 */     if (args.length == 2) {
/* 39 */       playerArg = args[1];
/*    */     }
/*    */     
/* 42 */     Player player = Bukkit.getPlayerExact(playerArg);
/*    */     
/* 44 */     if (player != null) {
/* 45 */       int value = -1;
/*    */       
/*    */       try {
/* 48 */         value = Integer.parseInt(modeArg);
/* 49 */       } catch (NumberFormatException ex) {}
/*    */       
/* 51 */       GameMode mode = GameMode.getByValue(value);
/*    */       
/* 53 */       if (mode == null) {
/* 54 */         if (modeArg.equalsIgnoreCase("creative") || modeArg.equalsIgnoreCase("c")) {
/* 55 */           mode = GameMode.CREATIVE;
/* 56 */         } else if (modeArg.equalsIgnoreCase("adventure") || modeArg.equalsIgnoreCase("a")) {
/* 57 */           mode = GameMode.ADVENTURE;
/*    */         } else {
/* 59 */           mode = GameMode.SURVIVAL;
/*    */         } 
/*    */       }
/*    */       
/* 63 */       if (mode != player.getGameMode()) {
/* 64 */         player.setGameMode(mode);
/*    */         
/* 66 */         if (mode != player.getGameMode()) {
/* 67 */           sender.sendMessage("Game mode change for " + player.getName() + " failed!");
/*    */         }
/* 69 */         else if (player == sender) {
/* 70 */           Command.broadcastCommandMessage(sender, "Set own game mode to " + mode.toString() + " mode");
/*    */         } else {
/* 72 */           Command.broadcastCommandMessage(sender, "Set " + player.getName() + "'s game mode to " + mode.toString() + " mode");
/*    */         } 
/*    */       } else {
/*    */         
/* 76 */         sender.sendMessage(player.getName() + " already has game mode " + mode.getValue());
/*    */       } 
/*    */     } else {
/* 79 */       sender.sendMessage("Can't find player " + playerArg);
/*    */     } 
/*    */     
/* 82 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
/* 87 */     Validate.notNull(sender, "Sender cannot be null");
/* 88 */     Validate.notNull(args, "Arguments cannot be null");
/* 89 */     Validate.notNull(alias, "Alias cannot be null");
/*    */     
/* 91 */     if (args.length == 1)
/* 92 */       return (List<String>)StringUtil.copyPartialMatches(args[0], GAMEMODE_NAMES, new ArrayList(GAMEMODE_NAMES.size())); 
/* 93 */     if (args.length == 2) {
/* 94 */       return super.tabComplete(sender, alias, args);
/*    */     }
/* 96 */     return (List<String>)ImmutableList.of();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\GameModeCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */