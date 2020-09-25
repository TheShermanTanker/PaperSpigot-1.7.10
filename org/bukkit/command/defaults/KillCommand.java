/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.entity.EntityDamageEvent;
/*    */ 
/*    */ public class KillCommand extends VanillaCommand {
/*    */   public KillCommand() {
/* 15 */     super("kill");
/* 16 */     this.description = "Commits suicide, only usable as a player";
/* 17 */     this.usageMessage = "/kill";
/* 18 */     setPermission("bukkit.command.kill");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
/* 23 */     if (!testPermission(sender)) return true;
/*    */     
/* 25 */     if (sender instanceof Player) {
/* 26 */       Player player = (Player)sender;
/*    */       
/* 28 */       EntityDamageEvent ede = new EntityDamageEvent((Entity)player, EntityDamageEvent.DamageCause.SUICIDE, 1000);
/* 29 */       Bukkit.getPluginManager().callEvent((Event)ede);
/* 30 */       if (ede.isCancelled()) return true;
/*    */       
/* 32 */       ede.getEntity().setLastDamageCause(ede);
/* 33 */       player.setHealth(0.0D);
/* 34 */       sender.sendMessage("Ouch. That look like it hurt.");
/*    */     } else {
/* 36 */       sender.sendMessage("You can only perform this command as a player");
/*    */     } 
/*    */     
/* 39 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
/* 44 */     Validate.notNull(sender, "Sender cannot be null");
/* 45 */     Validate.notNull(args, "Arguments cannot be null");
/* 46 */     Validate.notNull(alias, "Alias cannot be null");
/*    */     
/* 48 */     return (List<String>)ImmutableList.of();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\KillCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */