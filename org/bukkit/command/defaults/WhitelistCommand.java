/*     */ package org.bukkit.command.defaults;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.OfflinePlayer;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.util.StringUtil;
/*     */ 
/*     */ public class WhitelistCommand
/*     */   extends VanillaCommand
/*     */ {
/*  17 */   private static final List<String> WHITELIST_SUBCOMMANDS = (List<String>)ImmutableList.of("add", "remove", "on", "off", "list", "reload");
/*     */   
/*     */   public WhitelistCommand() {
/*  20 */     super("whitelist");
/*  21 */     this.description = "Manages the list of players allowed to use this server";
/*  22 */     this.usageMessage = "/whitelist (add|remove) <player>\n/whitelist (on|off|list|reload)";
/*  23 */     setPermission("bukkit.command.whitelist.reload;bukkit.command.whitelist.enable;bukkit.command.whitelist.disable;bukkit.command.whitelist.list;bukkit.command.whitelist.add;bukkit.command.whitelist.remove");
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
/*  28 */     if (!testPermission(sender)) return true;
/*     */     
/*  30 */     if (args.length == 1) {
/*  31 */       if (args[0].equalsIgnoreCase("reload")) {
/*  32 */         if (badPerm(sender, "reload")) return true;
/*     */         
/*  34 */         Bukkit.reloadWhitelist();
/*  35 */         Command.broadcastCommandMessage(sender, "Reloaded white-list from file");
/*  36 */         return true;
/*  37 */       }  if (args[0].equalsIgnoreCase("on")) {
/*  38 */         if (badPerm(sender, "enable")) return true;
/*     */         
/*  40 */         Bukkit.setWhitelist(true);
/*  41 */         Command.broadcastCommandMessage(sender, "Turned on white-listing");
/*  42 */         return true;
/*  43 */       }  if (args[0].equalsIgnoreCase("off")) {
/*  44 */         if (badPerm(sender, "disable")) return true;
/*     */         
/*  46 */         Bukkit.setWhitelist(false);
/*  47 */         Command.broadcastCommandMessage(sender, "Turned off white-listing");
/*  48 */         return true;
/*  49 */       }  if (args[0].equalsIgnoreCase("list")) {
/*  50 */         if (badPerm(sender, "list")) return true;
/*     */         
/*  52 */         StringBuilder result = new StringBuilder();
/*     */         
/*  54 */         for (OfflinePlayer player : Bukkit.getWhitelistedPlayers()) {
/*  55 */           if (result.length() > 0) {
/*  56 */             result.append(", ");
/*     */           }
/*     */           
/*  59 */           result.append(player.getName());
/*     */         } 
/*     */         
/*  62 */         sender.sendMessage("White-listed players: " + result.toString());
/*  63 */         return true;
/*     */       } 
/*  65 */     } else if (args.length == 2) {
/*  66 */       if (args[0].equalsIgnoreCase("add")) {
/*  67 */         if (badPerm(sender, "add")) return true;
/*     */         
/*  69 */         Bukkit.getOfflinePlayer(args[1]).setWhitelisted(true);
/*     */         
/*  71 */         Command.broadcastCommandMessage(sender, "Added " + args[1] + " to white-list");
/*  72 */         return true;
/*  73 */       }  if (args[0].equalsIgnoreCase("remove")) {
/*  74 */         if (badPerm(sender, "remove")) return true;
/*     */         
/*  76 */         Bukkit.getOfflinePlayer(args[1]).setWhitelisted(false);
/*     */         
/*  78 */         Command.broadcastCommandMessage(sender, "Removed " + args[1] + " from white-list");
/*  79 */         return true;
/*     */       } 
/*     */     } 
/*     */     
/*  83 */     sender.sendMessage(ChatColor.RED + "Correct command usage:\n" + this.usageMessage);
/*  84 */     return false;
/*     */   }
/*     */   
/*     */   private boolean badPerm(CommandSender sender, String perm) {
/*  88 */     if (!sender.hasPermission("bukkit.command.whitelist." + perm)) {
/*  89 */       sender.sendMessage(ChatColor.RED + "You do not have permission to perform this action.");
/*  90 */       return true;
/*     */     } 
/*     */     
/*  93 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
/*  98 */     Validate.notNull(sender, "Sender cannot be null");
/*  99 */     Validate.notNull(args, "Arguments cannot be null");
/* 100 */     Validate.notNull(alias, "Alias cannot be null");
/*     */     
/* 102 */     if (args.length == 1)
/* 103 */       return (List<String>)StringUtil.copyPartialMatches(args[0], WHITELIST_SUBCOMMANDS, new ArrayList(WHITELIST_SUBCOMMANDS.size())); 
/* 104 */     if (args.length == 2) {
/* 105 */       if (args[0].equalsIgnoreCase("add")) {
/* 106 */         List<String> completions = new ArrayList<String>();
/* 107 */         for (OfflinePlayer player : Bukkit.getOnlinePlayers()) {
/* 108 */           String name = player.getName();
/* 109 */           if (StringUtil.startsWithIgnoreCase(name, args[1]) && !player.isWhitelisted()) {
/* 110 */             completions.add(name);
/*     */           }
/*     */         } 
/* 113 */         return completions;
/* 114 */       }  if (args[0].equalsIgnoreCase("remove")) {
/* 115 */         List<String> completions = new ArrayList<String>();
/* 116 */         for (OfflinePlayer player : Bukkit.getWhitelistedPlayers()) {
/* 117 */           String name = player.getName();
/* 118 */           if (StringUtil.startsWithIgnoreCase(name, args[1])) {
/* 119 */             completions.add(name);
/*     */           }
/*     */         } 
/* 122 */         return completions;
/*     */       } 
/*     */     } 
/* 125 */     return (List<String>)ImmutableList.of();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\WhitelistCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */