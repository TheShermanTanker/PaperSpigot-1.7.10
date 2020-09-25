/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import com.google.common.collect.ImmutableList;
/*    */ import java.util.List;
/*    */ import org.apache.commons.lang.Validate;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ public class SaveCommand
/*    */   extends VanillaCommand
/*    */ {
/*    */   public SaveCommand() {
/* 15 */     super("save-all");
/* 16 */     this.description = "Saves the server to disk";
/* 17 */     this.usageMessage = "/save-all";
/* 18 */     setPermission("bukkit.command.save.perform");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
/* 23 */     if (!testPermission(sender)) return true;
/*    */     
/* 25 */     Command.broadcastCommandMessage(sender, "Forcing save..");
/*    */     
/* 27 */     Bukkit.savePlayers();
/*    */     
/* 29 */     for (World world : Bukkit.getWorlds()) {
/* 30 */       world.save();
/*    */     }
/*    */     
/* 33 */     Command.broadcastCommandMessage(sender, "Save complete.");
/*    */     
/* 35 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
/* 40 */     Validate.notNull(sender, "Sender cannot be null");
/* 41 */     Validate.notNull(args, "Arguments cannot be null");
/* 42 */     Validate.notNull(alias, "Alias cannot be null");
/*    */     
/* 44 */     return (List<String>)ImmutableList.of();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\SaveCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */