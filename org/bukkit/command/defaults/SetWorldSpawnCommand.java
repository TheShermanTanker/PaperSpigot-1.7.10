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
/*    */ public class SetWorldSpawnCommand
/*    */   extends VanillaCommand
/*    */ {
/*    */   public SetWorldSpawnCommand() {
/* 18 */     super("setworldspawn");
/* 19 */     this.description = "Sets a worlds's spawn point. If no coordinates are specified, the player's coordinates will be used.";
/* 20 */     this.usageMessage = "/setworldspawn OR /setworldspawn <x> <y> <z>";
/* 21 */     setPermission("bukkit.command.setworldspawn");
/*    */   }
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
/*    */     World world;
/*    */     int x, y, z;
/* 26 */     if (!testPermission(sender)) return true;
/*    */     
/* 28 */     Player player = null;
/*    */     
/* 30 */     if (sender instanceof Player) {
/* 31 */       player = (Player)sender;
/* 32 */       world = player.getWorld();
/*    */     } else {
/* 34 */       world = Bukkit.getWorlds().get(0);
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 39 */     if (args.length == 0) {
/* 40 */       if (player == null) {
/* 41 */         sender.sendMessage("You can only perform this command as a player");
/* 42 */         return true;
/*    */       } 
/*    */       
/* 45 */       Location location = player.getLocation();
/*    */       
/* 47 */       x = location.getBlockX();
/* 48 */       y = location.getBlockY();
/* 49 */       z = location.getBlockZ();
/* 50 */     } else if (args.length == 3) {
/*    */       try {
/* 52 */         x = getInteger(sender, args[0], -30000000, 30000000, true);
/* 53 */         y = getInteger(sender, args[1], 0, world.getMaxHeight(), true);
/* 54 */         z = getInteger(sender, args[2], -30000000, 30000000, true);
/* 55 */       } catch (NumberFormatException ex) {
/* 56 */         sender.sendMessage(ex.getMessage());
/* 57 */         return true;
/*    */       } 
/*    */     } else {
/* 60 */       sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/* 61 */       return false;
/*    */     } 
/*    */     
/* 64 */     world.setSpawnLocation(x, y, z);
/*    */     
/* 66 */     Command.broadcastCommandMessage(sender, "Set world " + world.getName() + "'s spawnpoint to (" + x + ", " + y + ", " + z + ")");
/* 67 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
/* 73 */     Validate.notNull(sender, "Sender cannot be null");
/* 74 */     Validate.notNull(args, "Arguments cannot be null");
/* 75 */     Validate.notNull(alias, "Alias cannot be null");
/*    */     
/* 77 */     return (List<String>)ImmutableList.of();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\SetWorldSpawnCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */