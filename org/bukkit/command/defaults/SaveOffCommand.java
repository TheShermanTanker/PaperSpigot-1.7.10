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
/*    */ public class SaveOffCommand
/*    */   extends VanillaCommand
/*    */ {
/*    */   public SaveOffCommand() {
/* 15 */     super("save-off");
/* 16 */     this.description = "Disables server autosaving";
/* 17 */     this.usageMessage = "/save-off";
/* 18 */     setPermission("bukkit.command.save.disable");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
/* 23 */     if (!testPermission(sender)) return true;
/*    */     
/* 25 */     for (World world : Bukkit.getWorlds()) {
/* 26 */       world.setAutoSave(false);
/*    */     }
/*    */     
/* 29 */     Command.broadcastCommandMessage(sender, "Disabled level saving..");
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


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\SaveOffCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */