/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.BanEntry;
/*    */ import org.bukkit.BanList;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.util.StringUtil;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BanListCommand
/*    */   extends VanillaCommand
/*    */ {
/* 19 */   private static final List<String> BANLIST_TYPES = (List<String>)ImmutableList.of("ips", "players");
/*    */   
/*    */   public BanListCommand() {
/* 22 */     super("banlist");
/* 23 */     this.description = "View all players banned from this server";
/* 24 */     this.usageMessage = "/banlist [ips|players]";
/* 25 */     setPermission("bukkit.command.ban.list");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
/* 30 */     if (!testPermission(sender)) return true;
/*    */     
/* 32 */     BanList.Type banType = BanList.Type.NAME;
/* 33 */     if (args.length > 0) {
/* 34 */       if (args[0].equalsIgnoreCase("ips")) {
/* 35 */         banType = BanList.Type.IP;
/* 36 */       } else if (!args[0].equalsIgnoreCase("players")) {
/* 37 */         sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/* 38 */         return false;
/*    */       } 
/*    */     }
/*    */     
/* 42 */     StringBuilder message = new StringBuilder();
/* 43 */     BanEntry[] banlist = (BanEntry[])Bukkit.getBanList(banType).getBanEntries().toArray((Object[])new BanEntry[0]);
/*    */     
/* 45 */     for (int x = 0; x < banlist.length; x++) {
/* 46 */       if (x != 0) {
/* 47 */         if (x == banlist.length - 1) {
/* 48 */           message.append(" and ");
/*    */         } else {
/* 50 */           message.append(", ");
/*    */         } 
/*    */       }
/*    */       
/* 54 */       message.append(banlist[x].getTarget());
/*    */     } 
/*    */     
/* 57 */     sender.sendMessage("There are " + banlist.length + " total banned players:");
/* 58 */     sender.sendMessage(message.toString());
/* 59 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
/* 64 */     Validate.notNull(sender, "Sender cannot be null");
/* 65 */     Validate.notNull(args, "Arguments cannot be null");
/* 66 */     Validate.notNull(alias, "Alias cannot be null");
/*    */     
/* 68 */     if (args.length == 1) {
/* 69 */       return (List<String>)StringUtil.copyPartialMatches(args[0], BANLIST_TYPES, new ArrayList(BANLIST_TYPES.size()));
/*    */     }
/* 71 */     return (List<String>)ImmutableList.of();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\BanListCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */