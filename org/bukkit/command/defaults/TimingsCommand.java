/*     */ package org.bukkit.command.defaults;
/*     */ 
/*     */ import com.google.common.collect.ImmutableList;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import java.net.URLEncoder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.plugin.SimplePluginManager;
/*     */ import org.bukkit.util.StringUtil;
/*     */ import org.spigotmc.CustomTimingsHandler;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TimingsCommand
/*     */   extends BukkitCommand
/*     */ {
/*  36 */   private static final List<String> TIMINGS_SUBCOMMANDS = (List<String>)ImmutableList.of("report", "reset", "on", "off", "paste");
/*  37 */   public static long timingStart = 0L;
/*     */   
/*     */   public TimingsCommand(String name) {
/*  40 */     super(name);
/*  41 */     this.description = "Manages Spigot Timings data to see performance of the server.";
/*  42 */     this.usageMessage = "/timings <reset|report|on|off|paste>";
/*  43 */     setPermission("bukkit.command.timings");
/*     */   }
/*     */ 
/*     */   
/*     */   public void executeSpigotTimings(CommandSender sender, String[] args) {
/*  48 */     if ("on".equals(args[0])) {
/*     */       
/*  50 */       ((SimplePluginManager)Bukkit.getPluginManager()).useTimings(true);
/*  51 */       CustomTimingsHandler.reload();
/*  52 */       sender.sendMessage("Enabled Timings & Reset"); return;
/*     */     } 
/*  54 */     if ("off".equals(args[0])) {
/*     */       
/*  56 */       ((SimplePluginManager)Bukkit.getPluginManager()).useTimings(false);
/*  57 */       sender.sendMessage("Disabled Timings");
/*     */       
/*     */       return;
/*     */     } 
/*  61 */     if (!Bukkit.getPluginManager().useTimings()) {
/*     */       
/*  63 */       sender.sendMessage("Please enable timings by typing /timings on");
/*     */       
/*     */       return;
/*     */     } 
/*  67 */     boolean paste = "paste".equals(args[0]);
/*  68 */     if ("reset".equals(args[0])) {
/*  69 */       CustomTimingsHandler.reload();
/*  70 */       sender.sendMessage("Timings reset");
/*  71 */     } else if ("merged".equals(args[0]) || "report".equals(args[0]) || paste) {
/*  72 */       long sampleTime = System.nanoTime() - timingStart;
/*  73 */       int index = 0;
/*  74 */       File timingFolder = new File("timings");
/*  75 */       timingFolder.mkdirs();
/*  76 */       File timings = new File(timingFolder, "timings.txt");
/*  77 */       ByteArrayOutputStream bout = paste ? new ByteArrayOutputStream() : null;
/*  78 */       for (; timings.exists(); timings = new File(timingFolder, "timings" + ++index + ".txt"));
/*  79 */       PrintStream fileTimings = null;
/*     */       
/*  81 */       try { fileTimings = paste ? new PrintStream(bout) : new PrintStream(timings);
/*     */         
/*  83 */         CustomTimingsHandler.printTimings(fileTimings);
/*  84 */         fileTimings.println("Sample time " + sampleTime + " (" + (sampleTime / 1.0E9D) + "s)");
/*     */         
/*  86 */         fileTimings.println("<spigotConfig>");
/*  87 */         fileTimings.println(Bukkit.spigot().getConfig().saveToString());
/*  88 */         fileTimings.println("</spigotConfig>");
/*     */         
/*  90 */         if (paste) {
/*     */           
/*  92 */           (new PasteThread(sender, bout)).start();
/*     */           
/*     */           return;
/*     */         } 
/*  96 */         sender.sendMessage("Timings written to " + timings.getPath());
/*  97 */         sender.sendMessage("Paste contents of file into form at http://aikar.co/timings.php to read results."); }
/*     */       
/*  99 */       catch (IOException e) {  }
/*     */       finally
/* 101 */       { if (fileTimings != null) {
/* 102 */           fileTimings.close();
/*     */         } }
/*     */     
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
/* 111 */     if (!testPermission(sender)) return true; 
/* 112 */     if (args.length < 1) {
/* 113 */       sender.sendMessage(ChatColor.RED + "Usage: " + this.usageMessage);
/* 114 */       return false;
/*     */     } 
/* 116 */     executeSpigotTimings(sender, args); return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> tabComplete(CommandSender sender, String alias, String[] args) {
/* 190 */     Validate.notNull(sender, "Sender cannot be null");
/* 191 */     Validate.notNull(args, "Arguments cannot be null");
/* 192 */     Validate.notNull(alias, "Alias cannot be null");
/*     */     
/* 194 */     if (args.length == 1) {
/* 195 */       return (List<String>)StringUtil.copyPartialMatches(args[0], TIMINGS_SUBCOMMANDS, new ArrayList(TIMINGS_SUBCOMMANDS.size()));
/*     */     }
/* 197 */     return (List<String>)ImmutableList.of();
/*     */   }
/*     */ 
/*     */   
/*     */   private static class PasteThread
/*     */     extends Thread
/*     */   {
/*     */     private final CommandSender sender;
/*     */     
/*     */     private final ByteArrayOutputStream bout;
/*     */     
/*     */     public PasteThread(CommandSender sender, ByteArrayOutputStream bout) {
/* 209 */       super("Timings paste thread");
/* 210 */       this.sender = sender;
/* 211 */       this.bout = bout;
/*     */     }
/*     */ 
/*     */     
/*     */     public synchronized void start() {
/* 216 */       if (this.sender instanceof org.bukkit.command.RemoteConsoleCommandSender) {
/* 217 */         run();
/*     */       } else {
/* 219 */         super.start();
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void run() {
/*     */       try {
/* 228 */         HttpURLConnection con = (HttpURLConnection)(new URL("http://paste.ubuntu.com/")).openConnection();
/* 229 */         con.setDoOutput(true);
/* 230 */         con.setRequestMethod("POST");
/* 231 */         con.setInstanceFollowRedirects(false);
/*     */         
/* 233 */         OutputStream out = con.getOutputStream();
/* 234 */         out.write("poster=Spigot&syntax=text&content=".getBytes("UTF-8"));
/* 235 */         out.write(URLEncoder.encode(this.bout.toString("UTF-8"), "UTF-8").getBytes("UTF-8"));
/* 236 */         out.close();
/* 237 */         con.getInputStream().close();
/*     */         
/* 239 */         String location = con.getHeaderField("Location");
/* 240 */         String pasteID = location.substring("http://paste.ubuntu.com/".length(), location.length() - 1);
/* 241 */         this.sender.sendMessage(ChatColor.GREEN + "View timings results can be viewed at http://aikar.co/timings.php?url=" + pasteID);
/* 242 */       } catch (IOException ex) {
/*     */         
/* 244 */         this.sender.sendMessage(ChatColor.RED + "Error pasting timings, check your console for more information");
/* 245 */         Bukkit.getServer().getLogger().log(Level.WARNING, "Could not paste timings", ex);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\command\defaults\TimingsCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */