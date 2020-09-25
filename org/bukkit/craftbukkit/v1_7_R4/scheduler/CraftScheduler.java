/*     */ package org.bukkit.craftbukkit.v1_7_R4.scheduler;
/*     */ 
/*     */ import com.google.common.util.concurrent.ThreadFactoryBuilder;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Comparator;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.PriorityQueue;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.Future;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ import java.util.logging.Level;
/*     */ import org.apache.commons.lang.Validate;
/*     */ import org.bukkit.plugin.IllegalPluginAccessException;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ import org.bukkit.scheduler.BukkitScheduler;
/*     */ import org.bukkit.scheduler.BukkitTask;
/*     */ import org.bukkit.scheduler.BukkitWorker;
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
/*     */ public class CraftScheduler
/*     */   implements BukkitScheduler
/*     */ {
/*  48 */   private final AtomicInteger ids = new AtomicInteger(1);
/*     */ 
/*     */ 
/*     */   
/*  52 */   private volatile CraftTask head = new CraftTask();
/*     */ 
/*     */ 
/*     */   
/*  56 */   private final AtomicReference<CraftTask> tail = new AtomicReference<CraftTask>(this.head);
/*     */ 
/*     */ 
/*     */   
/*  60 */   private final PriorityQueue<CraftTask> pending = new PriorityQueue<CraftTask>(10, new Comparator<CraftTask>()
/*     */       {
/*     */         public int compare(CraftTask o1, CraftTask o2) {
/*  63 */           return (int)(o1.getNextRun() - o2.getNextRun());
/*     */         }
/*     */       });
/*     */ 
/*     */ 
/*     */   
/*  69 */   private final List<CraftTask> temp = new ArrayList<CraftTask>();
/*     */ 
/*     */ 
/*     */   
/*  73 */   private final ConcurrentHashMap<Integer, CraftTask> runners = new ConcurrentHashMap<Integer, CraftTask>();
/*  74 */   private volatile int currentTick = -1;
/*  75 */   private final Executor executor = Executors.newCachedThreadPool((new ThreadFactoryBuilder()).setNameFormat("Craft Scheduler Thread - %1$d").build());
/*  76 */   private CraftAsyncDebugger debugHead = new CraftAsyncDebugger(-1, null, null) { StringBuilder debugTo(StringBuilder string) { return string; } }
/*  77 */   ; private CraftAsyncDebugger debugTail = this.debugHead;
/*     */ 
/*     */ 
/*     */   
/*  81 */   private static final int RECENT_TICKS = 30;
/*     */ 
/*     */   
/*     */   public int scheduleSyncDelayedTask(Plugin plugin, Runnable task) {
/*  85 */     return scheduleSyncDelayedTask(plugin, task, 0L);
/*     */   }
/*     */   
/*     */   public BukkitTask runTask(Plugin plugin, Runnable runnable) {
/*  89 */     return runTaskLater(plugin, runnable, 0L);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public int scheduleAsyncDelayedTask(Plugin plugin, Runnable task) {
/*  94 */     return scheduleAsyncDelayedTask(plugin, task, 0L);
/*     */   }
/*     */   
/*     */   public BukkitTask runTaskAsynchronously(Plugin plugin, Runnable runnable) {
/*  98 */     return runTaskLaterAsynchronously(plugin, runnable, 0L);
/*     */   }
/*     */   
/*     */   public int scheduleSyncDelayedTask(Plugin plugin, Runnable task, long delay) {
/* 102 */     return scheduleSyncRepeatingTask(plugin, task, delay, -1L);
/*     */   }
/*     */   
/*     */   public BukkitTask runTaskLater(Plugin plugin, Runnable runnable, long delay) {
/* 106 */     return runTaskTimer(plugin, runnable, delay, -1L);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public int scheduleAsyncDelayedTask(Plugin plugin, Runnable task, long delay) {
/* 111 */     return scheduleAsyncRepeatingTask(plugin, task, delay, -1L);
/*     */   }
/*     */   
/*     */   public BukkitTask runTaskLaterAsynchronously(Plugin plugin, Runnable runnable, long delay) {
/* 115 */     return runTaskTimerAsynchronously(plugin, runnable, delay, -1L);
/*     */   }
/*     */   
/*     */   public int scheduleSyncRepeatingTask(Plugin plugin, Runnable runnable, long delay, long period) {
/* 119 */     return runTaskTimer(plugin, runnable, delay, period).getTaskId();
/*     */   }
/*     */   
/*     */   public BukkitTask runTaskTimer(Plugin plugin, Runnable runnable, long delay, long period) {
/* 123 */     validate(plugin, runnable);
/* 124 */     if (delay < 0L) {
/* 125 */       delay = 0L;
/*     */     }
/* 127 */     if (period == 0L) {
/* 128 */       period = 1L;
/* 129 */     } else if (period < -1L) {
/* 130 */       period = -1L;
/*     */     } 
/* 132 */     return handle(new CraftTask(plugin, runnable, nextId(), period), delay);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public int scheduleAsyncRepeatingTask(Plugin plugin, Runnable runnable, long delay, long period) {
/* 137 */     return runTaskTimerAsynchronously(plugin, runnable, delay, period).getTaskId();
/*     */   }
/*     */   
/*     */   public BukkitTask runTaskTimerAsynchronously(Plugin plugin, Runnable runnable, long delay, long period) {
/* 141 */     validate(plugin, runnable);
/* 142 */     if (delay < 0L) {
/* 143 */       delay = 0L;
/*     */     }
/* 145 */     if (period == 0L) {
/* 146 */       period = 1L;
/* 147 */     } else if (period < -1L) {
/* 148 */       period = -1L;
/*     */     } 
/* 150 */     return handle(new CraftAsyncTask(this.runners, plugin, runnable, nextId(), period), delay);
/*     */   }
/*     */   
/*     */   public <T> Future<T> callSyncMethod(Plugin plugin, Callable<T> task) {
/* 154 */     validate(plugin, task);
/* 155 */     CraftFuture<T> future = new CraftFuture<T>(task, plugin, nextId());
/* 156 */     handle(future, 0L);
/* 157 */     return future;
/*     */   }
/*     */   
/*     */   public void cancelTask(final int taskId) {
/* 161 */     if (taskId <= 0) {
/*     */       return;
/*     */     }
/* 164 */     CraftTask task = this.runners.get(Integer.valueOf(taskId));
/* 165 */     if (task != null) {
/* 166 */       task.cancel0();
/*     */     }
/* 168 */     task = new CraftTask(new Runnable()
/*     */         {
/*     */           public void run() {
/* 171 */             if (!check(CraftScheduler.this.temp))
/* 172 */               check(CraftScheduler.this.pending); 
/*     */           }
/*     */           
/*     */           private boolean check(Iterable<CraftTask> collection) {
/* 176 */             Iterator<CraftTask> tasks = collection.iterator();
/* 177 */             while (tasks.hasNext()) {
/* 178 */               CraftTask task = tasks.next();
/* 179 */               if (task.getTaskId() == taskId) {
/* 180 */                 task.cancel0();
/* 181 */                 tasks.remove();
/* 182 */                 if (task.isSync()) {
/* 183 */                   CraftScheduler.this.runners.remove(Integer.valueOf(taskId));
/*     */                 }
/* 185 */                 return true;
/*     */               } 
/*     */             } 
/* 188 */             return false; }
/*     */         });
/* 190 */     handle(task, 0L);
/* 191 */     for (CraftTask taskPending = this.head.getNext(); taskPending != null; taskPending = taskPending.getNext()) {
/* 192 */       if (taskPending == task) {
/*     */         return;
/*     */       }
/* 195 */       if (taskPending.getTaskId() == taskId) {
/* 196 */         taskPending.cancel0();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void cancelTasks(final Plugin plugin) {
/* 202 */     Validate.notNull(plugin, "Cannot cancel tasks of null plugin");
/* 203 */     CraftTask task = new CraftTask(new Runnable()
/*     */         {
/*     */           public void run() {
/* 206 */             check(CraftScheduler.this.pending);
/* 207 */             check(CraftScheduler.this.temp);
/*     */           }
/*     */           void check(Iterable<CraftTask> collection) {
/* 210 */             Iterator<CraftTask> tasks = collection.iterator();
/* 211 */             while (tasks.hasNext()) {
/* 212 */               CraftTask task = tasks.next();
/* 213 */               if (task.getOwner().equals(plugin)) {
/* 214 */                 task.cancel0();
/* 215 */                 tasks.remove();
/* 216 */                 if (task.isSync()) {
/* 217 */                   CraftScheduler.this.runners.remove(Integer.valueOf(task.getTaskId()));
/*     */                 }
/*     */               } 
/*     */             } 
/*     */           }
/*     */         });
/* 223 */     handle(task, 0L);
/* 224 */     for (CraftTask taskPending = this.head.getNext(); taskPending != null; taskPending = taskPending.getNext()) {
/* 225 */       if (taskPending == task) {
/*     */         return;
/*     */       }
/* 228 */       if (taskPending.getTaskId() != -1 && taskPending.getOwner().equals(plugin)) {
/* 229 */         taskPending.cancel0();
/*     */       }
/*     */     } 
/* 232 */     for (CraftTask runner : this.runners.values()) {
/* 233 */       if (runner.getOwner().equals(plugin)) {
/* 234 */         runner.cancel0();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public void cancelAllTasks() {
/* 240 */     CraftTask task = new CraftTask(new Runnable()
/*     */         {
/*     */           public void run() {
/* 243 */             Iterator<CraftTask> it = CraftScheduler.this.runners.values().iterator();
/* 244 */             while (it.hasNext()) {
/* 245 */               CraftTask task = it.next();
/* 246 */               task.cancel0();
/* 247 */               if (task.isSync()) {
/* 248 */                 it.remove();
/*     */               }
/*     */             } 
/* 251 */             CraftScheduler.this.pending.clear();
/* 252 */             CraftScheduler.this.temp.clear();
/*     */           }
/*     */         });
/* 255 */     handle(task, 0L);
/* 256 */     for (CraftTask taskPending = this.head.getNext(); taskPending != null && 
/* 257 */       taskPending != task; taskPending = taskPending.getNext())
/*     */     {
/*     */       
/* 260 */       taskPending.cancel0();
/*     */     }
/* 262 */     for (CraftTask runner : this.runners.values()) {
/* 263 */       runner.cancel0();
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isCurrentlyRunning(int taskId) {
/* 268 */     CraftTask task = this.runners.get(Integer.valueOf(taskId));
/* 269 */     if (task == null || task.isSync()) {
/* 270 */       return false;
/*     */     }
/* 272 */     CraftAsyncTask asyncTask = (CraftAsyncTask)task;
/* 273 */     synchronized (asyncTask.getWorkers()) {
/* 274 */       return asyncTask.getWorkers().isEmpty();
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isQueued(int taskId) {
/* 279 */     if (taskId <= 0)
/* 280 */       return false; 
/*     */     CraftTask task;
/* 282 */     for (task = this.head.getNext(); task != null; task = task.getNext()) {
/* 283 */       if (task.getTaskId() == taskId) {
/* 284 */         return (task.getPeriod() >= -1L);
/*     */       }
/*     */     } 
/* 287 */     task = this.runners.get(Integer.valueOf(taskId));
/* 288 */     return (task != null && task.getPeriod() >= -1L);
/*     */   }
/*     */   
/*     */   public List<BukkitWorker> getActiveWorkers() {
/* 292 */     ArrayList<BukkitWorker> workers = new ArrayList<BukkitWorker>();
/* 293 */     for (CraftTask taskObj : this.runners.values()) {
/*     */       
/* 295 */       if (taskObj.isSync()) {
/*     */         continue;
/*     */       }
/* 298 */       CraftAsyncTask task = (CraftAsyncTask)taskObj;
/* 299 */       synchronized (task.getWorkers()) {
/*     */         
/* 301 */         workers.addAll(task.getWorkers());
/*     */       } 
/*     */     } 
/* 304 */     return workers;
/*     */   }
/*     */   
/*     */   public List<BukkitTask> getPendingTasks() {
/* 308 */     ArrayList<CraftTask> truePending = new ArrayList<CraftTask>();
/* 309 */     for (CraftTask task = this.head.getNext(); task != null; task = task.getNext()) {
/* 310 */       if (task.getTaskId() != -1)
/*     */       {
/* 312 */         truePending.add(task);
/*     */       }
/*     */     } 
/*     */     
/* 316 */     ArrayList<BukkitTask> pending = new ArrayList<BukkitTask>();
/* 317 */     for (CraftTask craftTask : this.runners.values()) {
/* 318 */       if (craftTask.getPeriod() >= -1L) {
/* 319 */         pending.add(craftTask);
/*     */       }
/*     */     } 
/*     */     
/* 323 */     for (CraftTask craftTask : truePending) {
/* 324 */       if (craftTask.getPeriod() >= -1L && !pending.contains(craftTask)) {
/* 325 */         pending.add(craftTask);
/*     */       }
/*     */     } 
/* 328 */     return pending;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mainThreadHeartbeat(int currentTick) {
/* 335 */     this.currentTick = currentTick;
/* 336 */     List<CraftTask> temp = this.temp;
/* 337 */     parsePending();
/* 338 */     while (isReady(currentTick)) {
/* 339 */       CraftTask task = this.pending.remove();
/* 340 */       if (task.getPeriod() < -1L) {
/* 341 */         if (task.isSync()) {
/* 342 */           this.runners.remove(Integer.valueOf(task.getTaskId()), task);
/*     */         }
/* 344 */         parsePending();
/*     */         continue;
/*     */       } 
/* 347 */       if (task.isSync()) {
/*     */         try {
/* 349 */           task.timings.startTiming();
/* 350 */           task.run();
/* 351 */           task.timings.stopTiming();
/* 352 */         } catch (Throwable throwable) {
/* 353 */           task.getOwner().getLogger().log(Level.WARNING, String.format("Task #%s for %s generated an exception", new Object[] { Integer.valueOf(task.getTaskId()), task.getOwner().getDescription().getFullName() }), throwable);
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 361 */         parsePending();
/*     */       } else {
/* 363 */         this.debugTail = this.debugTail.setNext(new CraftAsyncDebugger(currentTick + RECENT_TICKS, task.getOwner(), task.getTaskClass()));
/* 364 */         this.executor.execute(task);
/*     */       } 
/*     */ 
/*     */       
/* 368 */       long period = task.getPeriod();
/* 369 */       if (period > 0L) {
/* 370 */         task.setNextRun(currentTick + period);
/* 371 */         temp.add(task); continue;
/* 372 */       }  if (task.isSync()) {
/* 373 */         this.runners.remove(Integer.valueOf(task.getTaskId()));
/*     */       }
/*     */     } 
/* 376 */     this.pending.addAll(temp);
/* 377 */     temp.clear();
/* 378 */     this.debugHead = this.debugHead.getNextHead(currentTick);
/*     */   }
/*     */   
/*     */   private void addTask(CraftTask task) {
/* 382 */     AtomicReference<CraftTask> tail = this.tail;
/* 383 */     CraftTask tailTask = tail.get();
/* 384 */     while (!tail.compareAndSet(tailTask, task)) {
/* 385 */       tailTask = tail.get();
/*     */     }
/* 387 */     tailTask.setNext(task);
/*     */   }
/*     */   
/*     */   private CraftTask handle(CraftTask task, long delay) {
/* 391 */     task.setNextRun(this.currentTick + delay);
/* 392 */     addTask(task);
/* 393 */     return task;
/*     */   }
/*     */   
/*     */   private static void validate(Plugin plugin, Object task) {
/* 397 */     Validate.notNull(plugin, "Plugin cannot be null");
/* 398 */     Validate.notNull(task, "Task cannot be null");
/* 399 */     if (!plugin.isEnabled()) {
/* 400 */       throw new IllegalPluginAccessException("Plugin attempted to register task while disabled");
/*     */     }
/*     */   }
/*     */   
/*     */   private int nextId() {
/* 405 */     return this.ids.incrementAndGet();
/*     */   }
/*     */   
/*     */   private void parsePending() {
/* 409 */     CraftTask head = this.head;
/* 410 */     CraftTask task = head.getNext();
/* 411 */     CraftTask lastTask = head;
/* 412 */     for (; task != null; task = (lastTask = task).getNext()) {
/* 413 */       if (task.getTaskId() == -1) {
/* 414 */         task.run();
/* 415 */       } else if (task.getPeriod() >= -1L) {
/* 416 */         this.pending.add(task);
/* 417 */         this.runners.put(Integer.valueOf(task.getTaskId()), task);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 422 */     for (task = head; task != lastTask; task = head) {
/* 423 */       head = task.getNext();
/* 424 */       task.setNext(null);
/*     */     } 
/* 426 */     this.head = lastTask;
/*     */   }
/*     */   
/*     */   private boolean isReady(int currentTick) {
/* 430 */     return (!this.pending.isEmpty() && ((CraftTask)this.pending.peek()).getNextRun() <= currentTick);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 435 */     int debugTick = this.currentTick;
/* 436 */     StringBuilder string = (new StringBuilder("Recent tasks from ")).append(debugTick - RECENT_TICKS).append('-').append(debugTick).append('{');
/* 437 */     this.debugHead.debugTo(string);
/* 438 */     return string.append('}').toString();
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public int scheduleSyncDelayedTask(Plugin plugin, BukkitRunnable task, long delay) {
/* 444 */     return scheduleSyncDelayedTask(plugin, (Runnable)task, delay);
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public int scheduleSyncDelayedTask(Plugin plugin, BukkitRunnable task) {
/* 450 */     return scheduleSyncDelayedTask(plugin, (Runnable)task);
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public int scheduleSyncRepeatingTask(Plugin plugin, BukkitRunnable task, long delay, long period) {
/* 456 */     return scheduleSyncRepeatingTask(plugin, (Runnable)task, delay, period);
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public BukkitTask runTask(Plugin plugin, BukkitRunnable task) throws IllegalArgumentException {
/* 462 */     return runTask(plugin, (Runnable)task);
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public BukkitTask runTaskAsynchronously(Plugin plugin, BukkitRunnable task) throws IllegalArgumentException {
/* 468 */     return runTaskAsynchronously(plugin, (Runnable)task);
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public BukkitTask runTaskLater(Plugin plugin, BukkitRunnable task, long delay) throws IllegalArgumentException {
/* 474 */     return runTaskLater(plugin, (Runnable)task, delay);
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public BukkitTask runTaskLaterAsynchronously(Plugin plugin, BukkitRunnable task, long delay) throws IllegalArgumentException {
/* 480 */     return runTaskLaterAsynchronously(plugin, (Runnable)task, delay);
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public BukkitTask runTaskTimer(Plugin plugin, BukkitRunnable task, long delay, long period) throws IllegalArgumentException {
/* 486 */     return runTaskTimer(plugin, (Runnable)task, delay, period);
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public BukkitTask runTaskTimerAsynchronously(Plugin plugin, BukkitRunnable task, long delay, long period) throws IllegalArgumentException {
/* 492 */     return runTaskTimerAsynchronously(plugin, (Runnable)task, delay, period);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\v1_7_R4\scheduler\CraftScheduler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */