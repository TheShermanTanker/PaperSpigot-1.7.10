package org.bukkit.scheduler;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import org.bukkit.plugin.Plugin;

public interface BukkitScheduler {
  int scheduleSyncDelayedTask(Plugin paramPlugin, Runnable paramRunnable, long paramLong);
  
  @Deprecated
  int scheduleSyncDelayedTask(Plugin paramPlugin, BukkitRunnable paramBukkitRunnable, long paramLong);
  
  int scheduleSyncDelayedTask(Plugin paramPlugin, Runnable paramRunnable);
  
  @Deprecated
  int scheduleSyncDelayedTask(Plugin paramPlugin, BukkitRunnable paramBukkitRunnable);
  
  int scheduleSyncRepeatingTask(Plugin paramPlugin, Runnable paramRunnable, long paramLong1, long paramLong2);
  
  @Deprecated
  int scheduleSyncRepeatingTask(Plugin paramPlugin, BukkitRunnable paramBukkitRunnable, long paramLong1, long paramLong2);
  
  @Deprecated
  int scheduleAsyncDelayedTask(Plugin paramPlugin, Runnable paramRunnable, long paramLong);
  
  @Deprecated
  int scheduleAsyncDelayedTask(Plugin paramPlugin, Runnable paramRunnable);
  
  @Deprecated
  int scheduleAsyncRepeatingTask(Plugin paramPlugin, Runnable paramRunnable, long paramLong1, long paramLong2);
  
  <T> Future<T> callSyncMethod(Plugin paramPlugin, Callable<T> paramCallable);
  
  void cancelTask(int paramInt);
  
  void cancelTasks(Plugin paramPlugin);
  
  void cancelAllTasks();
  
  boolean isCurrentlyRunning(int paramInt);
  
  boolean isQueued(int paramInt);
  
  List<BukkitWorker> getActiveWorkers();
  
  List<BukkitTask> getPendingTasks();
  
  BukkitTask runTask(Plugin paramPlugin, Runnable paramRunnable) throws IllegalArgumentException;
  
  @Deprecated
  BukkitTask runTask(Plugin paramPlugin, BukkitRunnable paramBukkitRunnable) throws IllegalArgumentException;
  
  BukkitTask runTaskAsynchronously(Plugin paramPlugin, Runnable paramRunnable) throws IllegalArgumentException;
  
  @Deprecated
  BukkitTask runTaskAsynchronously(Plugin paramPlugin, BukkitRunnable paramBukkitRunnable) throws IllegalArgumentException;
  
  BukkitTask runTaskLater(Plugin paramPlugin, Runnable paramRunnable, long paramLong) throws IllegalArgumentException;
  
  @Deprecated
  BukkitTask runTaskLater(Plugin paramPlugin, BukkitRunnable paramBukkitRunnable, long paramLong) throws IllegalArgumentException;
  
  BukkitTask runTaskLaterAsynchronously(Plugin paramPlugin, Runnable paramRunnable, long paramLong) throws IllegalArgumentException;
  
  @Deprecated
  BukkitTask runTaskLaterAsynchronously(Plugin paramPlugin, BukkitRunnable paramBukkitRunnable, long paramLong) throws IllegalArgumentException;
  
  BukkitTask runTaskTimer(Plugin paramPlugin, Runnable paramRunnable, long paramLong1, long paramLong2) throws IllegalArgumentException;
  
  @Deprecated
  BukkitTask runTaskTimer(Plugin paramPlugin, BukkitRunnable paramBukkitRunnable, long paramLong1, long paramLong2) throws IllegalArgumentException;
  
  BukkitTask runTaskTimerAsynchronously(Plugin paramPlugin, Runnable paramRunnable, long paramLong1, long paramLong2) throws IllegalArgumentException;
  
  @Deprecated
  BukkitTask runTaskTimerAsynchronously(Plugin paramPlugin, BukkitRunnable paramBukkitRunnable, long paramLong1, long paramLong2) throws IllegalArgumentException;
}


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\scheduler\BukkitScheduler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */