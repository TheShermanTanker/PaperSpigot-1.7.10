/*     */ package org.bukkit.command.defaults;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.player.PlayerTeleportEvent;
/*     */ 
/*     */ public class TeleportCommand
/*     */   extends VanillaCommand
/*     */ {
/*     */   public TeleportCommand() {
/*  19 */     super("tp");
/*  20 */     this.description = "Teleports the given player (or yourself) to another player or coordinates";
/*  21 */     this.usageMessage = "/tp [player] <target> and/or <x> <y> <z>";
/*  22 */     setPermission("bukkit.command.teleport");
/*     */   }
/*     */   
/*     */   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
/*     */     Player player;
/*  27 */     if (!testPermission(sender)) return true; 
/*  28 */     if (args.length < 1 || args.length > 4) {
/*  29 */       sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/*  30 */       return false;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  35 */     if (args.length == 1 || args.length == 3) {
/*  36 */       if (sender instanceof Player) {
/*  37 */         player = (Player)sender;
/*     */       } else {
/*  39 */         sender.sendMessage("Please provide a player!");
/*  40 */         return true;
/*     */       } 
/*     */     } else {
/*  43 */       player = Bukkit.getPlayerExact(args[0]);
/*     */     } 
/*     */     
/*  46 */     if (player == null) {
/*  47 */       sender.sendMessage("Player not found: " + args[0]);
/*  48 */       return true;
/*     */     } 
/*     */     
/*  51 */     if (args.length < 3) {
/*  52 */       Player target = Bukkit.getPlayerExact(args[args.length - 1]);
/*  53 */       if (target == null) {
/*  54 */         sender.sendMessage("Can't find player " + args[args.length - 1] + ". No tp.");
/*  55 */         return true;
/*     */       } 
/*  57 */       player.teleport((Entity)target, PlayerTeleportEvent.TeleportCause.COMMAND);
/*  58 */       Command.broadcastCommandMessage(sender, "Teleported " + player.getDisplayName() + " to " + target.getDisplayName());
/*  59 */     } else if (player.getWorld() != null) {
/*  60 */       Location playerLocation = player.getLocation();
/*  61 */       double x = getCoordinate(sender, playerLocation.getX(), args[args.length - 3]);
/*  62 */       double y = getCoordinate(sender, playerLocation.getY(), args[args.length - 2], 0, 0);
/*  63 */       double z = getCoordinate(sender, playerLocation.getZ(), args[args.length - 1]);
/*     */       
/*  65 */       if (x == -3.0000001E7D || y == -3.0000001E7D || z == -3.0000001E7D) {
/*  66 */         sender.sendMessage("Please provide a valid location!");
/*  67 */         return true;
/*     */       } 
/*     */       
/*  70 */       playerLocation.setX(x);
/*  71 */       playerLocation.setY(y);
/*  72 */       playerLocation.setZ(z);
/*     */       
/*  74 */       player.teleport(playerLocation, PlayerTeleportEvent.TeleportCause.COMMAND);
/*  75 */       Command.broadcastCommandMessage(sender, String.format("Teleported %s to %.2f, %.2f, %.2f", new Object[] { player.getDisplayName(), Double.valueOf(x), Double.valueOf(y), Double.valueOf(z) }));
/*     */     } 
/*  77 */     return true;
/*     */   }
/*     */   
/*     */   private double getCoordinate(CommandSender sender, double current, String input) {
/*  81 */     return getCoordinate(sender, current, input, -30000000, 30000000);
/*     */   }
/*     */   
/*     */   private double getCoordinate(CommandSender sender, double current, String input, int min, int max) {
/*  85 */     boolean relative = input.startsWith("~");
/*  86 */     double result = relative ? current : 0.0D;
/*     */     
/*  88 */     if (!relative || input.length() > 1) {
/*  89 */       boolean exact = input.contains(".");
/*  90 */       if (relative) input = input.substring(1);
/*     */       
/*  92 */       double testResult = getDouble(sender, input);
/*  93 */       if (testResult == -3.0000001E7D) {
/*  94 */         return -3.0000001E7D;
/*     */       }
/*  96 */       result += testResult;
/*     */       
/*  98 */       if (!exact && !relative) result += 0.5D; 
/*     */     } 
/* 100 */     if (min != 0 || max != 0) {
/* 101 */       if (result < min) {
/* 102 */         result = -3.0000001E7D;
/*     */       }
/*     */       
/* 105 */       if (result > max) {
/* 106 */         result = -3.0000001E7D;
/*     */       }
/*     */     } 
/*     */     
/* 110 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
/* 115 */     Validate.notNull(sender, "Sender cannot be null");
/* 116 */     Validate.notNull(args, "Arguments cannot be null");
/* 117 */     Validate.notNull(alias, "Alias cannot be null");
/*     */     
/* 119 */     if (args.length == 1 || args.length == 2) {
/* 120 */       return super.tabComplete(sender, alias, args);
/*     */     }
/* 122 */     return (List<String>)ImmutableList.of();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\TeleportCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */