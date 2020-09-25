/*     */ package org.spigotmc;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.command.defaults.TimingsCommand;
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
/*     */ public class CustomTimingsHandler
/*     */ {
/*  24 */   private static Queue<CustomTimingsHandler> HANDLERS = new ConcurrentLinkedQueue<CustomTimingsHandler>();
/*     */   
/*     */   private final String name;
/*     */   private final CustomTimingsHandler parent;
/*  28 */   private long count = 0L;
/*  29 */   private long start = 0L;
/*  30 */   private long timingDepth = 0L;
/*  31 */   private long totalTime = 0L;
/*  32 */   private long curTickTotal = 0L;
/*  33 */   private long violations = 0L;
/*     */ 
/*     */   
/*     */   public CustomTimingsHandler(String name) {
/*  37 */     this(name, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public CustomTimingsHandler(String name, CustomTimingsHandler parent) {
/*  42 */     this.name = name;
/*  43 */     this.parent = parent;
/*  44 */     HANDLERS.add(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void printTimings(PrintStream printStream) {
/*  54 */     printStream.println("Minecraft");
/*  55 */     for (CustomTimingsHandler timings : HANDLERS) {
/*     */       
/*  57 */       long time = timings.totalTime;
/*  58 */       long count = timings.count;
/*  59 */       if (count == 0L) {
/*     */         continue;
/*     */       }
/*     */       
/*  63 */       long avg = time / count;
/*     */       
/*  65 */       printStream.println("    " + timings.name + " Time: " + time + " Count: " + count + " Avg: " + avg + " Violations: " + timings.violations);
/*     */     } 
/*  67 */     printStream.println("# Version " + Bukkit.getVersion());
/*  68 */     int entities = 0;
/*  69 */     int livingEntities = 0;
/*  70 */     for (World world : Bukkit.getWorlds()) {
/*     */       
/*  72 */       entities += world.getEntities().size();
/*  73 */       livingEntities += world.getLivingEntities().size();
/*     */     } 
/*  75 */     printStream.println("# Entities " + entities);
/*  76 */     printStream.println("# LivingEntities " + livingEntities);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void reload() {
/*  84 */     if (Bukkit.getPluginManager().useTimings())
/*     */     {
/*  86 */       for (CustomTimingsHandler timings : HANDLERS)
/*     */       {
/*  88 */         timings.reset();
/*     */       }
/*     */     }
/*  91 */     TimingsCommand.timingStart = System.nanoTime();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void tick() {
/* 100 */     if (Bukkit.getPluginManager().useTimings())
/*     */     {
/* 102 */       for (CustomTimingsHandler timings : HANDLERS) {
/*     */         
/* 104 */         if (timings.curTickTotal > 50000000L)
/*     */         {
/* 106 */           timings.violations = (long)(timings.violations + Math.ceil((timings.curTickTotal / 50000000L)));
/*     */         }
/* 108 */         timings.curTickTotal = 0L;
/* 109 */         timings.timingDepth = 0L;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void startTiming() {
/* 120 */     if (Bukkit.getPluginManager().useTimings() && ++this.timingDepth == 1L) {
/*     */       
/* 122 */       this.start = System.nanoTime();
/* 123 */       if (this.parent != null && ++this.parent.timingDepth == 1L)
/*     */       {
/* 125 */         this.parent.start = this.start;
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void stopTiming() {
/* 135 */     if (Bukkit.getPluginManager().useTimings()) {
/*     */       
/* 137 */       if (--this.timingDepth != 0L || this.start == 0L) {
/*     */         return;
/*     */       }
/*     */       
/* 141 */       long diff = System.nanoTime() - this.start;
/* 142 */       this.totalTime += diff;
/* 143 */       this.curTickTotal += diff;
/* 144 */       this.count++;
/* 145 */       this.start = 0L;
/* 146 */       if (this.parent != null)
/*     */       {
/* 148 */         this.parent.stopTiming();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/* 158 */     this.count = 0L;
/* 159 */     this.violations = 0L;
/* 160 */     this.curTickTotal = 0L;
/* 161 */     this.totalTime = 0L;
/* 162 */     this.start = 0L;
/* 163 */     this.timingDepth = 0L;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\spigotmc\CustomTimingsHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */