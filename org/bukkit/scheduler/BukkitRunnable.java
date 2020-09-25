/*     */ package org.bukkit.scheduler;
/*     */ 
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ 
/*     */ public abstract class BukkitRunnable
/*     */   implements Runnable
/*     */ {
/*  10 */   private int taskId = -1;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void cancel() throws IllegalStateException {
/*  18 */     Bukkit.getScheduler().cancelTask(getTaskId());
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
/*     */   public synchronized BukkitTask runTask(Plugin plugin) throws IllegalArgumentException, IllegalStateException {
/*  31 */     checkState();
/*  32 */     return setupId(Bukkit.getScheduler().runTask(plugin, this));
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
/*     */   public synchronized BukkitTask runTaskAsynchronously(Plugin plugin) throws IllegalArgumentException, IllegalStateException {
/*  48 */     checkState();
/*  49 */     return setupId(Bukkit.getScheduler().runTaskAsynchronously(plugin, this));
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
/*     */   public synchronized BukkitTask runTaskLater(Plugin plugin, long delay) throws IllegalArgumentException, IllegalStateException {
/*  63 */     checkState();
/*  64 */     return setupId(Bukkit.getScheduler().runTaskLater(plugin, this, delay));
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
/*     */   public synchronized BukkitTask runTaskLaterAsynchronously(Plugin plugin, long delay) throws IllegalArgumentException, IllegalStateException {
/*  82 */     checkState();
/*  83 */     return setupId(Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, this, delay));
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
/*     */   public synchronized BukkitTask runTaskTimer(Plugin plugin, long delay, long period) throws IllegalArgumentException, IllegalStateException {
/*  99 */     checkState();
/* 100 */     return setupId(Bukkit.getScheduler().runTaskTimer(plugin, this, delay, period));
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
/*     */   public synchronized BukkitTask runTaskTimerAsynchronously(Plugin plugin, long delay, long period) throws IllegalArgumentException, IllegalStateException {
/* 121 */     checkState();
/* 122 */     return setupId(Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, this, delay, period));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized int getTaskId() throws IllegalStateException {
/* 132 */     int id = this.taskId;
/* 133 */     if (id == -1) {
/* 134 */       throw new IllegalStateException("Not scheduled yet");
/*     */     }
/* 136 */     return id;
/*     */   }
/*     */   
/*     */   private void checkState() {
/* 140 */     if (this.taskId != -1) {
/* 141 */       throw new IllegalStateException("Already scheduled as " + this.taskId);
/*     */     }
/*     */   }
/*     */   
/*     */   private BukkitTask setupId(BukkitTask task) {
/* 146 */     this.taskId = task.getTaskId();
/* 147 */     return task;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\scheduler\BukkitRunnable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */