/*    */ package org.bukkit.craftbukkit.v1_7_R4.command;
/*    */ 
/*    */ import java.util.EnumMap;
/*    */ import java.util.Map;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.ConsoleCommandSender;
/*    */ import org.bukkit.craftbukkit.libs.jline.Terminal;
/*    */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*    */ import org.fusesource.jansi.Ansi;
/*    */ 
/*    */ 
/*    */ public class ColouredConsoleSender
/*    */   extends CraftConsoleCommandSender
/*    */ {
/*    */   private final Terminal terminal;
/* 17 */   private final Map<ChatColor, String> replacements = new EnumMap<ChatColor, String>(ChatColor.class);
/* 18 */   private final ChatColor[] colors = ChatColor.values();
/*    */ 
/*    */   
/*    */   protected ColouredConsoleSender() {
/* 22 */     this.terminal = ((CraftServer)getServer()).getReader().getTerminal();
/*    */     
/* 24 */     this.replacements.put(ChatColor.BLACK, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.BLACK).boldOff().toString());
/* 25 */     this.replacements.put(ChatColor.DARK_BLUE, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.BLUE).boldOff().toString());
/* 26 */     this.replacements.put(ChatColor.DARK_GREEN, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.GREEN).boldOff().toString());
/* 27 */     this.replacements.put(ChatColor.DARK_AQUA, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.CYAN).boldOff().toString());
/* 28 */     this.replacements.put(ChatColor.DARK_RED, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.RED).boldOff().toString());
/* 29 */     this.replacements.put(ChatColor.DARK_PURPLE, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.MAGENTA).boldOff().toString());
/* 30 */     this.replacements.put(ChatColor.GOLD, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.YELLOW).boldOff().toString());
/* 31 */     this.replacements.put(ChatColor.GRAY, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.WHITE).boldOff().toString());
/* 32 */     this.replacements.put(ChatColor.DARK_GRAY, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.BLACK).bold().toString());
/* 33 */     this.replacements.put(ChatColor.BLUE, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.BLUE).bold().toString());
/* 34 */     this.replacements.put(ChatColor.GREEN, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.GREEN).bold().toString());
/* 35 */     this.replacements.put(ChatColor.AQUA, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.CYAN).bold().toString());
/* 36 */     this.replacements.put(ChatColor.RED, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.RED).bold().toString());
/* 37 */     this.replacements.put(ChatColor.LIGHT_PURPLE, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.MAGENTA).bold().toString());
/* 38 */     this.replacements.put(ChatColor.YELLOW, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.YELLOW).bold().toString());
/* 39 */     this.replacements.put(ChatColor.WHITE, Ansi.ansi().a(Ansi.Attribute.RESET).fg(Ansi.Color.WHITE).bold().toString());
/* 40 */     this.replacements.put(ChatColor.MAGIC, Ansi.ansi().a(Ansi.Attribute.BLINK_SLOW).toString());
/* 41 */     this.replacements.put(ChatColor.BOLD, Ansi.ansi().a(Ansi.Attribute.UNDERLINE_DOUBLE).toString());
/* 42 */     this.replacements.put(ChatColor.STRIKETHROUGH, Ansi.ansi().a(Ansi.Attribute.STRIKETHROUGH_ON).toString());
/* 43 */     this.replacements.put(ChatColor.UNDERLINE, Ansi.ansi().a(Ansi.Attribute.UNDERLINE).toString());
/* 44 */     this.replacements.put(ChatColor.ITALIC, Ansi.ansi().a(Ansi.Attribute.ITALIC).toString());
/* 45 */     this.replacements.put(ChatColor.RESET, Ansi.ansi().a(Ansi.Attribute.RESET).toString());
/*    */   }
/*    */ 
/*    */   
/*    */   public void sendMessage(String message) {
/* 50 */     if (this.terminal.isAnsiSupported()) {
/* 51 */       if (!this.conversationTracker.isConversingModaly()) {
/* 52 */         String result = message;
/* 53 */         for (ChatColor color : this.colors) {
/* 54 */           if (this.replacements.containsKey(color)) {
/* 55 */             result = result.replaceAll("(?i)" + color.toString(), this.replacements.get(color));
/*    */           } else {
/* 57 */             result = result.replaceAll("(?i)" + color.toString(), "");
/*    */           } 
/*    */         } 
/* 60 */         System.out.println(result + Ansi.ansi().reset().toString());
/*    */       } 
/*    */     } else {
/* 63 */       super.sendMessage(message);
/*    */     } 
/*    */   }
/*    */   
/*    */   public static ConsoleCommandSender getInstance() {
/* 68 */     if (Bukkit.getConsoleSender() != null) {
/* 69 */       return Bukkit.getConsoleSender();
/*    */     }
/* 71 */     return new ColouredConsoleSender();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\command\ColouredConsoleSender.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */