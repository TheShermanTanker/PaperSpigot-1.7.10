/*    */ package org.bukkit.craftbukkit.v1_7_R4.command;
/*    */ 
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import java.util.concurrent.ExecutionException;
/*    */ import java.util.logging.Level;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.craftbukkit.libs.jline.console.completer.Completer;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.util.Waitable;
/*    */ 
/*    */ public class ConsoleCommandCompleter implements Completer {
/*    */   private final CraftServer server;
/*    */   
/*    */   public ConsoleCommandCompleter(CraftServer server) {
/* 16 */     this.server = server;
/*    */   }
/*    */   
/*    */   public int complete(final String buffer, int cursor, List<CharSequence> candidates) {
/* 20 */     Waitable<List<String>> waitable = new Waitable<List<String>>()
/*    */       {
/*    */         protected List<String> evaluate() {
/* 23 */           return ConsoleCommandCompleter.this.server.getCommandMap().tabComplete((CommandSender)ConsoleCommandCompleter.this.server.getConsoleSender(), buffer);
/*    */         }
/*    */       };
/* 26 */     (this.server.getServer()).processQueue.add(waitable);
/*    */     try {
/* 28 */       List<String> offers = (List<String>)waitable.get();
/* 29 */       if (offers == null) {
/* 30 */         return cursor;
/*    */       }
/* 32 */       candidates.addAll((Collection)offers);
/*    */       
/* 34 */       int lastSpace = buffer.lastIndexOf(' ');
/* 35 */       if (lastSpace == -1) {
/* 36 */         return cursor - buffer.length();
/*    */       }
/* 38 */       return cursor - buffer.length() - lastSpace - 1;
/*    */     }
/* 40 */     catch (ExecutionException e) {
/* 41 */       this.server.getLogger().log(Level.WARNING, "Unhandled exception when tab completing", e);
/* 42 */     } catch (InterruptedException e) {
/* 43 */       Thread.currentThread().interrupt();
/*    */     } 
/* 45 */     return cursor;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\command\ConsoleCommandCompleter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */