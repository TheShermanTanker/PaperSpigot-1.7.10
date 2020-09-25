/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class SeedCommand
/*    */   extends VanillaCommand {
/*    */   public SeedCommand() {
/* 14 */     super("seed");
/* 15 */     this.description = "Shows the world seed";
/* 16 */     this.usageMessage = "/seed";
/* 17 */     setPermission("bukkit.command.seed");
/*    */   }
/*    */   
/*    */   public boolean execute(CommandSender sender, String commandLabel, String[] args) {
/*    */     long seed;
/* 22 */     if (!testPermission(sender)) return true;
/*    */     
/* 24 */     if (sender instanceof Player) {
/* 25 */       seed = ((Player)sender).getWorld().getSeed();
/*    */     } else {
/* 27 */       seed = ((World)Bukkit.getWorlds().get(0)).getSeed();
/*    */     } 
/* 29 */     sender.sendMessage("Seed: " + seed);
/* 30 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
/* 35 */     Validate.notNull(sender, "Sender cannot be null");
/* 36 */     Validate.notNull(args, "Arguments cannot be null");
/* 37 */     Validate.notNull(alias, "Alias cannot be null");
/*    */     
/* 39 */     return (List<String>)ImmutableList.of();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\SeedCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */