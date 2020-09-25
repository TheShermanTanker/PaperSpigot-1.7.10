/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.command.BlockCommandSender;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.HumanEntity;
/*    */ import org.bukkit.util.StringUtil;
/*    */ 
/*    */ public class GameRuleCommand
/*    */   extends VanillaCommand {
/* 19 */   private static final List<String> GAMERULE_STATES = (List<String>)ImmutableList.of("true", "false");
/*    */   
/*    */   public GameRuleCommand() {
/* 22 */     super("gamerule");
/* 23 */     this.description = "Sets a server's game rules";
/* 24 */     this.usageMessage = "/gamerule <rule name> <value> OR /gamerule <rule name>";
/* 25 */     setPermission("bukkit.command.gamerule");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
/* 30 */     if (!testPermission(sender)) return true;
/*    */     
/* 32 */     if (args.length > 0) {
/* 33 */       String rule = args[0];
/* 34 */       World world = getGameWorld(sender);
/*    */       
/* 36 */       if (world.isGameRule(rule)) {
/* 37 */         if (args.length > 1) {
/* 38 */           String value = args[1];
/*    */           
/* 40 */           world.setGameRuleValue(rule, value);
/* 41 */           Command.broadcastCommandMessage(sender, "Game rule " + rule + " has been set to: " + value);
/*    */         } else {
/* 43 */           String value = world.getGameRuleValue(rule);
/* 44 */           sender.sendMessage(rule + " = " + value);
/*    */         } 
/*    */       } else {
/* 47 */         sender.sendMessage(ChatColor.RED + "No game rule called " + rule + " is available");
/*    */       } 
/*    */       
/* 50 */       return true;
/*    */     } 
/* 52 */     sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/* 53 */     sender.sendMessage("Rules: " + createString(getGameWorld(sender).getGameRules(), 0, ", "));
/*    */     
/* 55 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   private World getGameWorld(CommandSender sender) {
/* 60 */     if (sender instanceof HumanEntity) {
/* 61 */       World world = ((HumanEntity)sender).getWorld();
/* 62 */       if (world != null) {
/* 63 */         return world;
/*    */       }
/* 65 */     } else if (sender instanceof BlockCommandSender) {
/* 66 */       return ((BlockCommandSender)sender).getBlock().getWorld();
/*    */     } 
/*    */     
/* 69 */     return Bukkit.getWorlds().get(0);
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
/* 74 */     Validate.notNull(sender, "Sender cannot be null");
/* 75 */     Validate.notNull(args, "Arguments cannot be null");
/* 76 */     Validate.notNull(alias, "Alias cannot be null");
/*    */     
/* 78 */     if (args.length == 1) {
/* 79 */       return (List<String>)StringUtil.copyPartialMatches(args[0], Arrays.asList(getGameWorld(sender).getGameRules()), new ArrayList());
/*    */     }
/*    */     
/* 82 */     if (args.length == 2) {
/* 83 */       return (List<String>)StringUtil.copyPartialMatches(args[1], GAMERULE_STATES, new ArrayList(GAMERULE_STATES.size()));
/*    */     }
/*    */     
/* 86 */     return (List<String>)ImmutableList.of();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\GameRuleCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */