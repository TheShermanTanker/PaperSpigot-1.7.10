/*     */ package org.bukkit.craftbukkit.v1_7_R4.scheduler;
/*     */ 
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.lang.UnhandledException;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitWorker;
/*     */ 
/*     */ 
/*     */ class CraftAsyncTask
/*     */   extends CraftTask
/*     */ {
/*  14 */   private final LinkedList<BukkitWorker> workers = new LinkedList<BukkitWorker>();
/*     */   private final Map<Integer, CraftTask> runners;
/*     */   
/*     */   CraftAsyncTask(Map<Integer, CraftTask> runners, Plugin plugin, Runnable task, int id, long delay) {
/*  18 */     super(plugin, task, id, delay);
/*  19 */     this.runners = runners;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSync() {
/*  24 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public void run() {
/*  29 */     final Thread thread = Thread.currentThread();
/*  30 */     synchronized (this.workers) {
/*  31 */       if (getPeriod() == -2L) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/*  36 */       this.workers.add(new BukkitWorker()
/*     */           {
/*     */             public Thread getThread() {
/*  39 */               return thread;
/*     */             }
/*     */             
/*     */             public int getTaskId() {
/*  43 */               return CraftAsyncTask.this.getTaskId();
/*     */             }
/*     */             
/*     */             public Plugin getOwner() {
/*  47 */               return CraftAsyncTask.this.getOwner();
/*     */             }
/*     */           });
/*     */     } 
/*  51 */     Throwable thrown = null;
/*     */     try {
/*  53 */       super.run();
/*  54 */     } catch (Throwable t) {
/*  55 */       thrown = t;
/*  56 */       throw new UnhandledException(String.format("Plugin %s generated an exception while executing task %s", new Object[] { getOwner().getDescription().getFullName(), Integer.valueOf(getTaskId()) }), thrown);
/*     */ 
/*     */     
/*     */     }
/*     */     finally {
/*     */ 
/*     */ 
/*     */       
/*  64 */       synchronized (this.workers) {
/*     */         try {
/*  66 */           Iterator<BukkitWorker> workers = this.workers.iterator();
/*  67 */           boolean removed = false;
/*  68 */           while (workers.hasNext()) {
/*  69 */             if (((BukkitWorker)workers.next()).getThread() == thread) {
/*  70 */               workers.remove();
/*  71 */               removed = true;
/*     */               break;
/*     */             } 
/*     */           } 
/*  75 */           if (!removed) {
/*  76 */             throw new IllegalStateException(String.format("Unable to remove worker %s on task %s for %s", new Object[] { thread.getName(), Integer.valueOf(getTaskId()), getOwner().getDescription().getFullName() }), thrown);
/*     */ 
/*     */           
/*     */           }
/*     */         
/*     */         }
/*     */         finally {
/*     */ 
/*     */           
/*  85 */           if (getPeriod() < 0L && this.workers.isEmpty())
/*     */           {
/*     */             
/*  88 */             this.runners.remove(Integer.valueOf(getTaskId()));
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   LinkedList<BukkitWorker> getWorkers() {
/*  96 */     return this.workers;
/*     */   }
/*     */   
/*     */   boolean cancel0() {
/* 100 */     synchronized (this.workers) {
/*     */       
/* 102 */       setPeriod(-2L);
/* 103 */       if (this.workers.isEmpty()) {
/* 104 */         this.runners.remove(Integer.valueOf(getTaskId()));
/*     */       }
/*     */     } 
/* 107 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\scheduler\CraftAsyncTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */