/*     */ package org.spigotmc;
/*     */ 
/*     */ import java.lang.management.ManagementFactory;
/*     */ import java.lang.management.MonitorInfo;
/*     */ import java.lang.management.ThreadInfo;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import net.minecraft.server.v1_7_R4.MinecraftServer;
/*     */ import net.minecraft.server.v1_7_R4.World;
/*     */ import org.bukkit.Bukkit;
/*     */ 
/*     */ public class WatchdogThread
/*     */   extends Thread
/*     */ {
/*     */   private static WatchdogThread instance;
/*     */   private final long timeoutTime;
/*     */   private final boolean restart;
/*     */   private volatile long lastTick;
/*     */   private volatile boolean stopping;
/*     */   
/*     */   private WatchdogThread(long timeoutTime, boolean restart) {
/*  22 */     super("Spigot Watchdog Thread");
/*  23 */     this.timeoutTime = timeoutTime;
/*  24 */     this.restart = restart;
/*     */   }
/*     */ 
/*     */   
/*     */   public static void doStart(int timeoutTime, boolean restart) {
/*  29 */     if (instance == null) {
/*     */       
/*  31 */       instance = new WatchdogThread(timeoutTime * 1000L, restart);
/*  32 */       instance.start();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void tick() {
/*  38 */     instance.lastTick = System.currentTimeMillis();
/*     */   }
/*     */ 
/*     */   
/*     */   public static void doStop() {
/*  43 */     if (instance != null)
/*     */     {
/*  45 */       instance.stopping = true;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void run() {
/*  52 */     while (!this.stopping) {
/*     */ 
/*     */       
/*  55 */       if (this.lastTick != 0L && System.currentTimeMillis() > this.lastTick + this.timeoutTime) {
/*     */         
/*  57 */         Logger log = Bukkit.getServer().getLogger();
/*  58 */         log.log(Level.SEVERE, "The server has stopped responding!");
/*  59 */         log.log(Level.SEVERE, "Please report this to http://www.spigotmc.org/");
/*  60 */         log.log(Level.SEVERE, "Be sure to include ALL relevant console errors and Minecraft crash reports");
/*  61 */         log.log(Level.SEVERE, "Spigot version: " + Bukkit.getServer().getVersion());
/*     */         
/*  63 */         if (World.haveWeSilencedAPhysicsCrash) {
/*     */           
/*  65 */           log.log(Level.SEVERE, "------------------------------");
/*  66 */           log.log(Level.SEVERE, "During the run of the server, a physics stackoverflow was supressed");
/*  67 */           log.log(Level.SEVERE, "near " + World.blockLocation);
/*     */         } 
/*     */         
/*  70 */         log.log(Level.SEVERE, "------------------------------");
/*  71 */         log.log(Level.SEVERE, "Server thread dump (Look for plugins here before reporting to Spigot!):");
/*  72 */         dumpThread(ManagementFactory.getThreadMXBean().getThreadInfo((MinecraftServer.getServer()).primaryThread.getId(), 2147483647), log);
/*  73 */         log.log(Level.SEVERE, "------------------------------");
/*     */         
/*  75 */         log.log(Level.SEVERE, "Entire Thread Dump:");
/*  76 */         ThreadInfo[] threads = ManagementFactory.getThreadMXBean().dumpAllThreads(true, true);
/*  77 */         for (ThreadInfo thread : threads)
/*     */         {
/*  79 */           dumpThread(thread, log);
/*     */         }
/*  81 */         log.log(Level.SEVERE, "------------------------------");
/*     */         
/*  83 */         if (this.restart)
/*     */         {
/*  85 */           RestartCommand.restart();
/*     */         }
/*     */         
/*     */         break;
/*     */       } 
/*     */       
/*     */       try {
/*  92 */         sleep(10000L);
/*  93 */       } catch (InterruptedException ex) {
/*     */         
/*  95 */         interrupt();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void dumpThread(ThreadInfo thread, Logger log) {
/* 102 */     log.log(Level.SEVERE, "------------------------------");
/*     */     
/* 104 */     log.log(Level.SEVERE, "Current Thread: " + thread.getThreadName());
/* 105 */     log.log(Level.SEVERE, "\tPID: " + thread.getThreadId() + " | Suspended: " + thread.isSuspended() + " | Native: " + thread.isInNative() + " | State: " + thread.getThreadState());
/*     */ 
/*     */ 
/*     */     
/* 109 */     if ((thread.getLockedMonitors()).length != 0) {
/*     */       
/* 111 */       log.log(Level.SEVERE, "\tThread is waiting on monitor(s):");
/* 112 */       for (MonitorInfo monitor : thread.getLockedMonitors())
/*     */       {
/* 114 */         log.log(Level.SEVERE, "\t\tLocked on:" + monitor.getLockedStackFrame());
/*     */       }
/*     */     } 
/* 117 */     log.log(Level.SEVERE, "\tStack:");
/*     */     
/* 119 */     for (StackTraceElement stack : thread.getStackTrace())
/*     */     {
/* 121 */       log.log(Level.SEVERE, "\t\t" + stack);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\spigotmc\WatchdogThread.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */