/*    */ package org.bukkit.command.defaults;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.bukkit.command.Command;
/*    */ 
/*    */ public abstract class BukkitCommand
/*    */   extends Command {
/*    */   protected BukkitCommand(String name) {
/*  9 */     super(name);
/*    */   }
/*    */   
/*    */   protected BukkitCommand(String name, String description, String usageMessage, List<String> aliases) {
/* 13 */     super(name, description, usageMessage, aliases);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\BukkitCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */