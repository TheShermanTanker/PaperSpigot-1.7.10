/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class ExpCommand
/*    */   extends VanillaCommand
/*    */ {
/*    */   public ExpCommand() {
/* 16 */     super("xp");
/* 17 */     this.description = "Gives the specified player a certain amount of experience. Specify <amount>L to give levels instead, with a negative amount resulting in taking levels.";
/* 18 */     this.usageMessage = "/xp <amount> [player] OR /xp <amount>L [player]";
/* 19 */     setPermission("bukkit.command.xp");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
/* 24 */     if (!testPermission(sender)) return true;
/*    */     
/* 26 */     if (args.length > 0) {
/* 27 */       String inputAmount = args[0];
/* 28 */       Player player = null;
/*    */       
/* 30 */       boolean isLevel = (inputAmount.endsWith("l") || inputAmount.endsWith("L"));
/* 31 */       if (isLevel && inputAmount.length() > 1) {
/* 32 */         inputAmount = inputAmount.substring(0, inputAmount.length() - 1);
/*    */       }
/*    */       
/* 35 */       int amount = getInteger(sender, inputAmount, -2147483648, 2147483647);
/* 36 */       boolean isTaking = (amount < 0);
/*    */       
/* 38 */       if (isTaking) {
/* 39 */         amount *= -1;
/*    */       }
/*    */       
/* 42 */       if (args.length > 1) {
/* 43 */         player = Bukkit.getPlayer(args[1]);
/* 44 */       } else if (sender instanceof Player) {
/* 45 */         player = (Player)sender;
/*    */       } 
/*    */       
/* 48 */       if (player != null) {
/* 49 */         if (isLevel) {
/* 50 */           if (isTaking) {
/* 51 */             player.giveExpLevels(-amount);
/* 52 */             Command.broadcastCommandMessage(sender, "Taken " + amount + " level(s) from " + player.getName());
/*    */           } else {
/* 54 */             player.giveExpLevels(amount);
/* 55 */             Command.broadcastCommandMessage(sender, "Given " + amount + " level(s) to " + player.getName());
/*    */           } 
/*    */         } else {
/* 58 */           if (isTaking) {
/* 59 */             sender.sendMessage(ChatColor.RED + "Taking experience can only be done by levels, cannot give players negative experience points");
/* 60 */             return false;
/*    */           } 
/* 62 */           player.giveExp(amount);
/* 63 */           Command.broadcastCommandMessage(sender, "Given " + amount + " experience to " + player.getName());
/*    */         } 
/*    */       } else {
/*    */         
/* 67 */         sender.sendMessage("Can't find player, was one provided?\n" + ChatColor.RED + "Usage: " + this.usageMessage);
/* 68 */         return false;
/*    */       } 
/*    */       
/* 71 */       return true;
/*    */     } 
/*    */     
/* 74 */     sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/* 75 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
/* 80 */     Validate.notNull(sender, "Sender cannot be null");
/* 81 */     Validate.notNull(args, "Arguments cannot be null");
/* 82 */     Validate.notNull(alias, "Alias cannot be null");
/*    */     
/* 84 */     if (args.length == 2) {
/* 85 */       return super.tabComplete(sender, alias, args);
/*    */     }
/* 87 */     return (List<String>)ImmutableList.of();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\ExpCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */