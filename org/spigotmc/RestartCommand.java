/*     */ package org.spigotmc;
/*     */ 
/*     */ import java.io.File;
/*     */ import net.minecraft.server.v1_7_R4.EntityPlayer;
/*     */ import net.minecraft.server.v1_7_R4.MinecraftServer;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandSender;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RestartCommand
/*     */   extends Command
/*     */ {
/*     */   public RestartCommand(String name) {
/*  15 */     super(name);
/*  16 */     this.description = "Restarts the server";
/*  17 */     this.usageMessage = "/restart";
/*  18 */     setPermission("bukkit.command.restart");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean execute(CommandSender sender, String currentAlias, String[] args) {
/*  24 */     if (testPermission(sender))
/*     */     {
/*  26 */       (MinecraftServer.getServer()).processQueue.add(new Runnable()
/*     */           {
/*     */             
/*     */             public void run()
/*     */             {
/*  31 */               RestartCommand.restart();
/*     */             }
/*     */           });
/*     */     }
/*  35 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void restart() {
/*  40 */     restart(new File(SpigotConfig.restartScript));
/*     */   }
/*     */ 
/*     */   
/*     */   public static void restart(final File script) {
/*  45 */     AsyncCatcher.enabled = false;
/*     */     
/*     */     try {
/*  48 */       if (script.isFile()) {
/*     */         
/*  50 */         System.out.println("Attempting to restart with " + SpigotConfig.restartScript);
/*     */ 
/*     */         
/*  53 */         WatchdogThread.doStop();
/*     */ 
/*     */         
/*  56 */         for (EntityPlayer p : (MinecraftServer.getServer().getPlayerList()).players)
/*     */         {
/*  58 */           p.playerConnection.disconnect(SpigotConfig.restartMessage);
/*     */         }
/*     */ 
/*     */         
/*     */         try {
/*  63 */           Thread.sleep(100L);
/*  64 */         } catch (InterruptedException ex) {}
/*     */ 
/*     */ 
/*     */         
/*  68 */         MinecraftServer.getServer().getServerConnection().b();
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/*  73 */           Thread.sleep(100L);
/*  74 */         } catch (InterruptedException ex) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/*  81 */           MinecraftServer.getServer().stop();
/*  82 */         } catch (Throwable t) {}
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  87 */         Thread shutdownHook = new Thread()
/*     */           {
/*     */ 
/*     */             
/*     */             public void run()
/*     */             {
/*     */               try {
/*  94 */                 String os = System.getProperty("os.name").toLowerCase();
/*  95 */                 if (os.contains("win")) {
/*     */                   
/*  97 */                   Runtime.getRuntime().exec("cmd /c start " + script.getPath());
/*     */                 } else {
/*     */                   
/* 100 */                   Runtime.getRuntime().exec(new String[] { "sh", this.val$script.getPath() });
/*     */                 
/*     */                 }
/*     */               
/*     */               }
/* 105 */               catch (Exception e) {
/*     */                 
/* 107 */                 e.printStackTrace();
/*     */               } 
/*     */             }
/*     */           };
/*     */         
/* 112 */         shutdownHook.setDaemon(true);
/* 113 */         Runtime.getRuntime().addShutdownHook(shutdownHook);
/*     */       } else {
/*     */         
/* 116 */         System.out.println("Startup script '" + SpigotConfig.restartScript + "' does not exist! Stopping server.");
/*     */       } 
/* 118 */       System.exit(0);
/* 119 */     } catch (Exception ex) {
/*     */       
/* 121 */       ex.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\spigotmc\RestartCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */