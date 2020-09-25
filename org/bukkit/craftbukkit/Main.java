/*     */ package org.bukkit.craftbukkit;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.lang.management.ManagementFactory;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import net.minecraft.server.v1_7_R4.MinecraftServer;
/*     */ import org.bukkit.craftbukkit.libs.jline.UnsupportedTerminal;
/*     */ import org.bukkit.craftbukkit.libs.joptsimple.OptionException;
/*     */ import org.bukkit.craftbukkit.libs.joptsimple.OptionParser;
/*     */ import org.bukkit.craftbukkit.libs.joptsimple.OptionSet;
/*     */ import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
/*     */ 
/*     */ public class Main {
/*     */   public static boolean useJline = true;
/*     */   
/*     */   public static void main(String[] args) throws IOException {
/*  23 */     OptionParser parser = new OptionParser()
/*     */       {
/*     */       
/*     */       };
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
/* 122 */     OptionSet options = null;
/*     */     
/*     */     try {
/* 125 */       options = parser.parse(args);
/* 126 */     } catch (OptionException ex) {
/* 127 */       Logger.getLogger(Main.class.getName()).log(Level.SEVERE, ex.getLocalizedMessage());
/*     */     } 
/*     */     
/* 130 */     if (options == null || options.has("?")) {
/*     */       try {
/* 132 */         parser.printHelpOn(System.out);
/* 133 */       } catch (IOException ex) {
/* 134 */         Logger.getLogger(Main.class.getName()).log(Level.SEVERE, (String)null, ex);
/*     */       } 
/* 136 */     } else if (options.has("v")) {
/* 137 */       System.out.println(CraftServer.class.getPackage().getImplementationVersion());
/*     */     } else {
/*     */       
/* 140 */       File lock = new File(".update-lock");
/* 141 */       if (!(new File("update-lock")).exists() && !lock.exists() && System.getProperty("IReallyKnowWhatIAmDoingThisUpdate") == null) {
/* 142 */         System.err.println("WARNING: This Minecraft update alters the way in which saved data is stored.");
/* 143 */         System.err.println("Please ensure your server is in the correct online/offline mode state, as the changes made are PERMANENT");
/* 144 */         System.err.println("If you are running in offline mode, but your BungeeCord is in online mode, it is imperative that BungeeCord support is enabled in spigot.yml and BungeeCord's config.yml");
/* 145 */         System.err.println("By typing `yes` you acknowledge that you have taken the necessary backups and are aware of this conversion");
/* 146 */         System.err.println("Please type yes to continue starting the server. You have been warned :)");
/* 147 */         System.err.println("See http://www.spigotmc.org/wiki/uuid-conversion/ if you have any questions and remember BACKUP BACKUP BACKUP");
/* 148 */         System.err.println("=================================================================================");
/* 149 */         System.err.println("Starting server in 10 seconds");
/* 150 */         lock.createNewFile();
/*     */         try {
/* 152 */           Thread.sleep(TimeUnit.SECONDS.toMillis(10L));
/* 153 */         } catch (InterruptedException ex) {}
/*     */       } 
/*     */       
/* 156 */       System.err.println("This PaperSpigot build only supports Minecraft 1.7.x and 1.8.x clients!\n*** It is imperative that backups be taken before running this build on your server! ***\nPlease report issues directly to Paper, and always ensure you're up-to-date!\n*** Any bug reports not running the very latest version of the software will be ignored ***\n\n");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 163 */         String jline_UnsupportedTerminal = new String(new char[] { 'j', 'l', 'i', 'n', 'e', '.', 'U', 'n', 's', 'u', 'p', 'p', 'o', 'r', 't', 'e', 'd', 'T', 'e', 'r', 'm', 'i', 'n', 'a', 'l' });
/* 164 */         String jline_terminal = new String(new char[] { 'j', 'l', 'i', 'n', 'e', '.', 't', 'e', 'r', 'm', 'i', 'n', 'a', 'l' });
/*     */         
/* 166 */         useJline = !jline_UnsupportedTerminal.equals(System.getProperty(jline_terminal));
/*     */         
/* 168 */         if (options.has("nojline")) {
/* 169 */           System.setProperty("user.language", "en");
/* 170 */           useJline = false;
/*     */         } 
/*     */         
/* 173 */         if (!useJline)
/*     */         {
/* 175 */           System.setProperty("org.bukkit.craftbukkit.libs.jline.terminal", UnsupportedTerminal.class.getName());
/*     */         }
/*     */ 
/*     */         
/* 179 */         if (options.has("noconsole")) {
/* 180 */           useConsole = false;
/*     */         }
/*     */ 
/*     */         
/* 184 */         int maxPermGen = 0;
/* 185 */         for (String s : ManagementFactory.getRuntimeMXBean().getInputArguments()) {
/*     */           
/* 187 */           if (s.startsWith("-XX:MaxPermSize")) {
/*     */             
/* 189 */             maxPermGen = Integer.parseInt(s.replaceAll("[^\\d]", ""));
/* 190 */             maxPermGen <<= 10 * "kmg".indexOf(Character.toLowerCase(s.charAt(s.length() - 1)));
/*     */           } 
/*     */         } 
/* 193 */         if (Float.parseFloat(System.getProperty("java.class.version")) < 52.0F && maxPermGen < 131072) {
/*     */           
/* 195 */           System.out.println("Warning, your max perm gen size is not set or less than 128mb. It is recommended you restart Java with the following argument: -XX:MaxPermSize=128M");
/* 196 */           System.out.println("Please see http://www.spigotmc.org/wiki/changing-permgen-size/ for more details and more in-depth instructions.");
/*     */         } 
/*     */         
/* 199 */         MinecraftServer.main(options);
/* 200 */       } catch (Throwable t) {
/* 201 */         t.printStackTrace();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   public static boolean useConsole = true;
/*     */   private static List<String> asList(String... params) {
/* 207 */     return Arrays.asList(params);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\Main.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */