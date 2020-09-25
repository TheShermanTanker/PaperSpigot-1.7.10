/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class ToggleDownfallCommand
/*    */   extends VanillaCommand
/*    */ {
/*    */   public ToggleDownfallCommand() {
/* 17 */     super("toggledownfall");
/* 18 */     this.description = "Toggles rain on/off on a given world";
/* 19 */     this.usageMessage = "/toggledownfall";
/* 20 */     setPermission("bukkit.command.toggledownfall");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
/* 25 */     if (!testPermission(sender)) return true;
/*    */     
/* 27 */     World world = null;
/*    */     
/* 29 */     if (args.length == 1) {
/* 30 */       world = Bukkit.getWorld(args[0]);
/*    */       
/* 32 */       if (world == null) {
/* 33 */         sender.sendMessage(ChatColor.RED + "No world exists with the name '" + args[0] + "'");
/* 34 */         return true;
/*    */       } 
/* 36 */     } else if (sender instanceof Player) {
/* 37 */       world = ((Player)sender).getWorld();
/*    */     } else {
/* 39 */       world = Bukkit.getWorlds().get(0);
/*    */     } 
/*    */     
/* 42 */     Command.broadcastCommandMessage(sender, "Toggling downfall " + (world.hasStorm() ? "off" : "on") + " for world '" + world.getName() + "'");
/* 43 */     world.setStorm(!world.hasStorm());
/*    */     
/* 45 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
/* 50 */     Validate.notNull(sender, "Sender cannot be null");
/* 51 */     Validate.notNull(args, "Arguments cannot be null");
/* 52 */     Validate.notNull(alias, "Alias cannot be null");
/*    */     
/* 54 */     return (List<String>)ImmutableList.of();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\ToggleDownfallCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */