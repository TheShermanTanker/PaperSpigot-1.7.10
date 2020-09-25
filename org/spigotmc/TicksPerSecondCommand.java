/*    */ package org.spigotmc;
/*    */ 
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ 
/*    */ public class TicksPerSecondCommand
/*    */   extends Command
/*    */ {
/*    */   public TicksPerSecondCommand(String name) {
/* 14 */     super(name);
/* 15 */     this.description = "Gets the current ticks per second for the server";
/* 16 */     this.usageMessage = "/tps";
/* 17 */     setPermission("bukkit.command.tps");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
/* 23 */     if (!testPermission(sender))
/*    */     {
/* 25 */       return true;
/*    */     }
/*    */ 
/*    */     
/* 29 */     double[] tps = Bukkit.spigot().getTPS();
/* 30 */     String[] tpsAvg = new String[tps.length];
/*    */     
/* 32 */     for (int i = 0; i < tps.length; i++) {
/* 33 */       tpsAvg[i] = format(tps[i]);
/*    */     }
/*    */     
/* 36 */     sender.sendMessage(ChatColor.GOLD + "TPS from last 1m, 5m, 15m: " + StringUtils.join((Object[])tpsAvg, ", "));
/*    */ 
/*    */     
/* 39 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   private static String format(double tps) {
/* 44 */     return ((tps > 18.0D) ? ChatColor.GREEN : ((tps > 16.0D) ? ChatColor.YELLOW : ChatColor.RED)).toString() + ((tps > 20.0D) ? "*" : "") + Math.min(Math.round(tps * 100.0D) / 100.0D, 20.0D);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\spigotmc\TicksPerSecondCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */