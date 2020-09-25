/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class SpawnpointCommand
/*    */   extends VanillaCommand
/*    */ {
/*    */   public SpawnpointCommand() {
/* 18 */     super("spawnpoint");
/* 19 */     this.description = "Sets a player's spawn point";
/* 20 */     this.usageMessage = "/spawnpoint OR /spawnpoint <player> OR /spawnpoint <player> <x> <y> <z>";
/* 21 */     setPermission("bukkit.command.spawnpoint");
/*    */   }
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
/*    */     Player player;
/* 26 */     if (!testPermission(sender)) return true;
/*    */ 
/*    */ 
/*    */     
/* 30 */     if (args.length == 0) {
/* 31 */       if (sender instanceof Player) {
/* 32 */         player = (Player)sender;
/*    */       } else {
/* 34 */         sender.sendMessage("Please provide a player!");
/* 35 */         return true;
/*    */       } 
/*    */     } else {
/* 38 */       player = Bukkit.getPlayerExact(args[0]);
/* 39 */       if (player == null) {
/* 40 */         sender.sendMessage("Can't find player " + args[0]);
/* 41 */         return true;
/*    */       } 
/*    */     } 
/*    */     
/* 45 */     World world = player.getWorld();
/*    */     
/* 47 */     if (args.length == 4) {
/* 48 */       if (world != null) {
/* 49 */         int x, y, z, pos = 1;
/*    */         
/*    */         try {
/* 52 */           x = getInteger(sender, args[pos++], -30000000, 30000000, true);
/* 53 */           y = getInteger(sender, args[pos++], 0, world.getMaxHeight());
/* 54 */           z = getInteger(sender, args[pos], -30000000, 30000000, true);
/* 55 */         } catch (NumberFormatException ex) {
/* 56 */           sender.sendMessage(ex.getMessage());
/* 57 */           return true;
/*    */         } 
/*    */         
/* 60 */         player.setBedSpawnLocation(new Location(world, x, y, z), true);
/* 61 */         Command.broadcastCommandMessage(sender, "Set " + player.getDisplayName() + "'s spawnpoint to " + x + ", " + y + ", " + z);
/*    */       } 
/* 63 */     } else if (args.length <= 1) {
/* 64 */       Location location = player.getLocation();
/* 65 */       player.setBedSpawnLocation(location, true);
/* 66 */       Command.broadcastCommandMessage(sender, "Set " + player.getDisplayName() + "'s spawnpoint to " + location.getX() + ", " + location.getY() + ", " + location.getZ());
/*    */     } else {
/* 68 */       sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/* 69 */       return false;
/*    */     } 
/*    */     
/* 72 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
/* 77 */     Validate.notNull(sender, "Sender cannot be null");
/* 78 */     Validate.notNull(args, "Arguments cannot be null");
/* 79 */     Validate.notNull(alias, "Alias cannot be null");
/*    */     
/* 81 */     if (args.length == 1) {
/* 82 */       return super.tabComplete(sender, alias, args);
/*    */     }
/*    */     
/* 85 */     return (List<String>)ImmutableList.of();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\SpawnpointCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */