/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class PlaySoundCommand extends VanillaCommand {
/*    */   public PlaySoundCommand() {
/* 11 */     super("playsound");
/* 12 */     this.description = "Plays a sound to a given player";
/* 13 */     this.usageMessage = "/playsound <sound> <player> [x] [y] [z] [volume] [pitch] [minimumVolume]";
/* 14 */     setPermission("bukkit.command.playsound");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
/* 19 */     if (!testPermission(sender)) {
/* 20 */       return true;
/*    */     }
/*    */     
/* 23 */     if (args.length < 2) {
/* 24 */       sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/* 25 */       return false;
/*    */     } 
/* 27 */     String soundArg = args[0];
/* 28 */     String playerArg = args[1];
/*    */     
/* 30 */     Player player = Bukkit.getPlayerExact(playerArg);
/* 31 */     if (player == null) {
/* 32 */       sender.sendMessage(ChatColor.RED + "Can't find player " + playerArg);
/* 33 */       return false;
/*    */     } 
/*    */     
/* 36 */     Location location = player.getLocation();
/*    */     
/* 38 */     double x = Math.floor(location.getX());
/* 39 */     double y = Math.floor(location.getY() + 0.5D);
/* 40 */     double z = Math.floor(location.getZ());
/* 41 */     double volume = 1.0D;
/* 42 */     double pitch = 1.0D;
/* 43 */     double minimumVolume = 0.0D;
/*    */     
/* 45 */     switch (args.length) {
/*    */       
/*    */       default:
/* 48 */         minimumVolume = getDouble(sender, args[7], 0.0D, 1.0D);
/*    */       case 7:
/* 50 */         pitch = getDouble(sender, args[6], 0.0D, 2.0D);
/*    */       case 6:
/* 52 */         volume = getDouble(sender, args[5], 0.0D, 3.4028234663852886E38D);
/*    */       case 5:
/* 54 */         z = getRelativeDouble(z, sender, args[4]);
/*    */       case 4:
/* 56 */         y = getRelativeDouble(y, sender, args[3]);
/*    */       case 3:
/* 58 */         x = getRelativeDouble(x, sender, args[2]);
/*    */         break;
/*    */       case 2:
/*    */         break;
/*    */     } 
/* 63 */     double fixedVolume = (volume > 1.0D) ? (volume * 16.0D) : 16.0D;
/* 64 */     Location soundLocation = new Location(player.getWorld(), x, y, z);
/* 65 */     if (location.distanceSquared(soundLocation) > fixedVolume * fixedVolume) {
/* 66 */       if (minimumVolume <= 0.0D) {
/* 67 */         sender.sendMessage(ChatColor.RED + playerArg + " is too far away to hear the sound");
/* 68 */         return false;
/*    */       } 
/*    */       
/* 71 */       double deltaX = x - location.getX();
/* 72 */       double deltaY = y - location.getY();
/* 73 */       double deltaZ = z - location.getZ();
/* 74 */       double delta = Math.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ) / 2.0D;
/*    */       
/* 76 */       if (delta > 0.0D) {
/* 77 */         location.add(deltaX / delta, deltaY / delta, deltaZ / delta);
/*    */       }
/*    */       
/* 80 */       player.playSound(location, soundArg, (float)minimumVolume, (float)pitch);
/*    */     } else {
/* 82 */       player.playSound(soundLocation, soundArg, (float)volume, (float)pitch);
/*    */     } 
/* 84 */     sender.sendMessage(String.format("Played '%s' to %s", new Object[] { soundArg, playerArg }));
/* 85 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\PlaySoundCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */