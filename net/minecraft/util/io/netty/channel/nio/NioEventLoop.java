/*     */ package net.minecraft.util.io.netty.channel.nio;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Field;
/*     */ import java.nio.channels.CancelledKeyException;
/*     */ import java.nio.channels.SelectableChannel;
/*     */ import java.nio.channels.SelectionKey;
/*     */ import java.nio.channels.Selector;
/*     */ import java.nio.channels.spi.SelectorProvider;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.ConcurrentModificationException;
/*     */ import java.util.Iterator;
/*     */ import java.util.Queue;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.atomic.AtomicBoolean;
/*     */ import net.minecraft.util.io.netty.channel.ChannelException;
/*     */ import net.minecraft.util.io.netty.channel.EventLoopException;
/*     */ import net.minecraft.util.io.netty.channel.EventLoopGroup;
/*     */ import net.minecraft.util.io.netty.channel.SingleThreadEventLoop;
/*     */ import net.minecraft.util.io.netty.util.internal.SystemPropertyUtil;
/*     */ import net.minecraft.util.io.netty.util.internal.logging.InternalLogger;
/*     */ import net.minecraft.util.io.netty.util.internal.logging.InternalLoggerFactory;
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
/*     */ public final class NioEventLoop
/*     */   extends SingleThreadEventLoop
/*     */ {
/*  52 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(NioEventLoop.class);
/*     */   
/*     */   private static final int CLEANUP_INTERVAL = 256;
/*     */   
/*  56 */   private static final boolean DISABLE_KEYSET_OPTIMIZATION = SystemPropertyUtil.getBoolean("io.netty.noKeySetOptimization", false);
/*     */   
/*     */   private static final int MIN_PREMATURE_SELECTOR_RETURNS = 3;
/*     */   
/*     */   private static final int SELECTOR_AUTO_REBUILD_THRESHOLD;
/*     */   
/*     */   Selector selector;
/*     */   
/*     */   private SelectedSelectionKeySet selectedKeys;
/*     */   private final SelectorProvider provider;
/*     */   
/*     */   static {
/*  68 */     String key = "sun.nio.ch.bugLevel";
/*     */     try {
/*  70 */       String buglevel = System.getProperty(key);
/*  71 */       if (buglevel == null) {
/*  72 */         System.setProperty(key, "");
/*     */       }
/*  74 */     } catch (SecurityException e) {
/*  75 */       if (logger.isDebugEnabled()) {
/*  76 */         logger.debug("Unable to get/set System Property: {}", key, e);
/*     */       }
/*     */     } 
/*     */     
/*  80 */     int selectorAutoRebuildThreshold = SystemPropertyUtil.getInt("io.netty.selectorAutoRebuildThreshold", 512);
/*  81 */     if (selectorAutoRebuildThreshold < 3) {
/*  82 */       selectorAutoRebuildThreshold = 0;
/*     */     }
/*     */     
/*  85 */     SELECTOR_AUTO_REBUILD_THRESHOLD = selectorAutoRebuildThreshold;
/*     */     
/*  87 */     if (logger.isDebugEnabled()) {
/*  88 */       logger.debug("-Dio.netty.noKeySetOptimization: {}", Boolean.valueOf(DISABLE_KEYSET_OPTIMIZATION));
/*  89 */       logger.debug("-Dio.netty.selectorAutoRebuildThreshold: {}", Integer.valueOf(SELECTOR_AUTO_REBUILD_THRESHOLD));
/*     */     } 
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
/* 107 */   private final AtomicBoolean wakenUp = new AtomicBoolean();
/*     */   
/*     */   private boolean oldWakenUp;
/* 110 */   private volatile int ioRatio = 50;
/*     */   private int cancelledKeys;
/*     */   private boolean needsToSelectAgain;
/*     */   
/*     */   NioEventLoop(NioEventLoopGroup parent, ThreadFactory threadFactory, SelectorProvider selectorProvider) {
/* 115 */     super((EventLoopGroup)parent, threadFactory, false);
/* 116 */     if (selectorProvider == null) {
/* 117 */       throw new NullPointerException("selectorProvider");
/*     */     }
/* 119 */     this.provider = selectorProvider;
/* 120 */     this.selector = openSelector();
/*     */   }
/*     */   
/*     */   private Selector openSelector() {
/*     */     Selector selector;
/*     */     try {
/* 126 */       selector = this.provider.openSelector();
/* 127 */     } catch (IOException e) {
/* 128 */       throw new ChannelException("failed to open a new selector", e);
/*     */     } 
/*     */     
/* 131 */     if (DISABLE_KEYSET_OPTIMIZATION) {
/* 132 */       return selector;
/*     */     }
/*     */     
/*     */     try {
/* 136 */       SelectedSelectionKeySet selectedKeySet = new SelectedSelectionKeySet();
/*     */       
/* 138 */       Class<?> selectorImplClass = Class.forName("sun.nio.ch.SelectorImpl", false, ClassLoader.getSystemClassLoader());
/*     */       
/* 140 */       selectorImplClass.isAssignableFrom(selector.getClass());
/* 141 */       Field selectedKeysField = selectorImplClass.getDeclaredField("selectedKeys");
/* 142 */       Field publicSelectedKeysField = selectorImplClass.getDeclaredField("publicSelectedKeys");
/*     */       
/* 144 */       selectedKeysField.setAccessible(true);
/* 145 */       publicSelectedKeysField.setAccessible(true);
/*     */       
/* 147 */       selectedKeysField.set(selector, selectedKeySet);
/* 148 */       publicSelectedKeysField.set(selector, selectedKeySet);
/*     */       
/* 150 */       this.selectedKeys = selectedKeySet;
/* 151 */       logger.trace("Instrumented an optimized java.util.Set into: {}", selector);
/* 152 */     } catch (Throwable t) {
/* 153 */       this.selectedKeys = null;
/* 154 */       logger.trace("Failed to instrument an optimized java.util.Set into: {}", selector, t);
/*     */     } 
/*     */     
/* 157 */     return selector;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected Queue<Runnable> newTaskQueue() {
/* 163 */     return new ConcurrentLinkedQueue<Runnable>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void register(SelectableChannel ch, int interestOps, NioTask<?> task) {
/* 172 */     if (ch == null) {
/* 173 */       throw new NullPointerException("ch");
/*     */     }
/* 175 */     if (interestOps == 0) {
/* 176 */       throw new IllegalArgumentException("interestOps must be non-zero.");
/*     */     }
/* 178 */     if ((interestOps & (ch.validOps() ^ 0xFFFFFFFF)) != 0) {
/* 179 */       throw new IllegalArgumentException("invalid interestOps: " + interestOps + "(validOps: " + ch.validOps() + ')');
/*     */     }
/*     */     
/* 182 */     if (task == null) {
/* 183 */       throw new NullPointerException("task");
/*     */     }
/*     */     
/* 186 */     if (isShutdown()) {
/* 187 */       throw new IllegalStateException("event loop shut down");
/*     */     }
/*     */     
/*     */     try {
/* 191 */       ch.register(this.selector, interestOps, task);
/* 192 */     } catch (Exception e) {
/* 193 */       throw new EventLoopException("failed to register a channel", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIoRatio() {
/* 201 */     return this.ioRatio;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIoRatio(int ioRatio) {
/* 209 */     if (ioRatio <= 0 || ioRatio >= 100) {
/* 210 */       throw new IllegalArgumentException("ioRatio: " + ioRatio + " (expected: 0 < ioRatio < 100)");
/*     */     }
/* 212 */     this.ioRatio = ioRatio;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void rebuildSelector() {
/*     */     Selector newSelector;
/* 220 */     if (!inEventLoop()) {
/* 221 */       execute(new Runnable()
/*     */           {
/*     */             public void run() {
/* 224 */               NioEventLoop.this.rebuildSelector();
/*     */             }
/*     */           });
/*     */       
/*     */       return;
/*     */     } 
/* 230 */     Selector oldSelector = this.selector;
/*     */ 
/*     */     
/* 233 */     if (oldSelector == null) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 238 */       newSelector = openSelector();
/* 239 */     } catch (Exception e) {
/* 240 */       logger.warn("Failed to create a new Selector.", e);
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 245 */     int nChannels = 0;
/*     */     while (true) {
/*     */       try {
/* 248 */         for (SelectionKey key : oldSelector.keys()) {
/* 249 */           Object a = key.attachment();
/*     */           try {
/* 251 */             if (key.channel().keyFor(newSelector) != null) {
/*     */               continue;
/*     */             }
/*     */             
/* 255 */             int interestOps = key.interestOps();
/* 256 */             key.cancel();
/* 257 */             key.channel().register(newSelector, interestOps, a);
/* 258 */             nChannels++;
/* 259 */           } catch (Exception e) {
/* 260 */             logger.warn("Failed to re-register a Channel to the new Selector.", e);
/* 261 */             if (a instanceof AbstractNioChannel) {
/* 262 */               AbstractNioChannel ch = (AbstractNioChannel)a;
/* 263 */               ch.unsafe().close(ch.unsafe().voidPromise());
/*     */               continue;
/*     */             } 
/* 266 */             NioTask<SelectableChannel> task = (NioTask<SelectableChannel>)a;
/* 267 */             invokeChannelUnregistered(task, key, e);
/*     */           } 
/*     */         } 
/*     */         break;
/* 271 */       } catch (ConcurrentModificationException e) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 279 */     this.selector = newSelector;
/*     */ 
/*     */     
/*     */     try {
/* 283 */       oldSelector.close();
/* 284 */     } catch (Throwable t) {
/* 285 */       if (logger.isWarnEnabled()) {
/* 286 */         logger.warn("Failed to close the old Selector.", t);
/*     */       }
/*     */     } 
/*     */     
/* 290 */     logger.info("Migrated " + nChannels + " channel(s) to the new Selector.");
/*     */   }
/*     */ 
/*     */   
/*     */   protected void run() {
/*     */     while (true) {
/* 296 */       this.oldWakenUp = this.wakenUp.getAndSet(false);
/*     */       try {
/* 298 */         if (hasTasks()) {
/* 299 */           selectNow();
/*     */         } else {
/* 301 */           select();
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
/* 331 */           if (this.wakenUp.get()) {
/* 332 */             this.selector.wakeup();
/*     */           }
/*     */         } 
/*     */         
/* 336 */         this.cancelledKeys = 0;
/*     */         
/* 338 */         long ioStartTime = System.nanoTime();
/* 339 */         this.needsToSelectAgain = false;
/* 340 */         if (this.selectedKeys != null) {
/* 341 */           processSelectedKeysOptimized(this.selectedKeys.flip());
/*     */         } else {
/* 343 */           processSelectedKeysPlain(this.selector.selectedKeys());
/*     */         } 
/* 345 */         long ioTime = System.nanoTime() - ioStartTime;
/*     */         
/* 347 */         int ioRatio = this.ioRatio;
/* 348 */         runAllTasks(ioTime * (100 - ioRatio) / ioRatio);
/*     */         
/* 350 */         if (isShuttingDown()) {
/* 351 */           closeAll();
/* 352 */           if (confirmShutdown()) {
/*     */             break;
/*     */           }
/*     */         } 
/* 356 */       } catch (Throwable t) {
/* 357 */         logger.warn("Unexpected exception in the selector loop.", t);
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 362 */           Thread.sleep(1000L);
/* 363 */         } catch (InterruptedException e) {}
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void cleanup() {
/*     */     try {
/* 373 */       this.selector.close();
/* 374 */     } catch (IOException e) {
/* 375 */       logger.warn("Failed to close a selector.", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   void cancel(SelectionKey key) {
/* 380 */     key.cancel();
/* 381 */     this.cancelledKeys++;
/* 382 */     if (this.cancelledKeys >= 256) {
/* 383 */       this.cancelledKeys = 0;
/* 384 */       this.needsToSelectAgain = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected Runnable pollTask() {
/* 390 */     Runnable task = super.pollTask();
/* 391 */     if (this.needsToSelectAgain) {
/* 392 */       selectAgain();
/*     */     }
/* 394 */     return task;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void processSelectedKeysPlain(Set<SelectionKey> selectedKeys) {
/* 401 */     if (selectedKeys.isEmpty()) {
/*     */       return;
/*     */     }
/*     */     
/* 405 */     Iterator<SelectionKey> i = selectedKeys.iterator();
/*     */     while (true) {
/* 407 */       SelectionKey k = i.next();
/* 408 */       Object a = k.attachment();
/* 409 */       i.remove();
/*     */       
/* 411 */       if (a instanceof AbstractNioChannel) {
/* 412 */         processSelectedKey(k, (AbstractNioChannel)a);
/*     */       } else {
/*     */         
/* 415 */         NioTask<SelectableChannel> task = (NioTask<SelectableChannel>)a;
/* 416 */         processSelectedKey(k, task);
/*     */       } 
/*     */       
/* 419 */       if (!i.hasNext()) {
/*     */         break;
/*     */       }
/*     */       
/* 423 */       if (this.needsToSelectAgain) {
/* 424 */         selectAgain();
/* 425 */         selectedKeys = this.selector.selectedKeys();
/*     */ 
/*     */         
/* 428 */         if (selectedKeys.isEmpty()) {
/*     */           break;
/*     */         }
/* 431 */         i = selectedKeys.iterator();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void processSelectedKeysOptimized(SelectionKey[] selectedKeys) {
/* 438 */     for (int i = 0;; i++) {
/* 439 */       SelectionKey k = selectedKeys[i];
/* 440 */       if (k == null) {
/*     */         break;
/*     */       }
/*     */       
/* 444 */       Object a = k.attachment();
/*     */       
/* 446 */       if (a instanceof AbstractNioChannel) {
/* 447 */         processSelectedKey(k, (AbstractNioChannel)a);
/*     */       } else {
/*     */         
/* 450 */         NioTask<SelectableChannel> task = (NioTask<SelectableChannel>)a;
/* 451 */         processSelectedKey(k, task);
/*     */       } 
/*     */       
/* 454 */       if (this.needsToSelectAgain) {
/* 455 */         selectAgain();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 461 */         selectedKeys = this.selectedKeys.flip();
/* 462 */         i = -1;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void processSelectedKey(SelectionKey k, AbstractNioChannel ch) {
/* 468 */     AbstractNioChannel.NioUnsafe unsafe = ch.unsafe();
/* 469 */     if (!k.isValid()) {
/*     */       
/* 471 */       unsafe.close(unsafe.voidPromise());
/*     */       
/*     */       return;
/*     */     } 
/*     */     try {
/* 476 */       int readyOps = k.readyOps();
/*     */ 
/*     */       
/* 479 */       if ((readyOps & 0x11) != 0 || readyOps == 0) {
/* 480 */         unsafe.read();
/* 481 */         if (!ch.isOpen()) {
/*     */           return;
/*     */         }
/*     */       } 
/*     */       
/* 486 */       if ((readyOps & 0x4) != 0)
/*     */       {
/* 488 */         ch.unsafe().forceFlush();
/*     */       }
/* 490 */       if ((readyOps & 0x8) != 0) {
/*     */ 
/*     */         
/* 493 */         int ops = k.interestOps();
/* 494 */         ops &= 0xFFFFFFF7;
/* 495 */         k.interestOps(ops);
/*     */         
/* 497 */         unsafe.finishConnect();
/*     */       } 
/* 499 */     } catch (CancelledKeyException e) {
/* 500 */       unsafe.close(unsafe.voidPromise());
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void processSelectedKey(SelectionKey k, NioTask<SelectableChannel> task) {
/* 505 */     int state = 0;
/*     */     try {
/* 507 */       task.channelReady(k.channel(), k);
/* 508 */       state = 1;
/* 509 */     } catch (Exception e) {
/* 510 */       k.cancel();
/* 511 */       invokeChannelUnregistered(task, k, e);
/* 512 */       state = 2;
/*     */     } finally {
/* 514 */       switch (state) {
/*     */         case 0:
/* 516 */           k.cancel();
/* 517 */           invokeChannelUnregistered(task, k, (Throwable)null);
/*     */           break;
/*     */         case 1:
/* 520 */           if (!k.isValid()) {
/* 521 */             invokeChannelUnregistered(task, k, (Throwable)null);
/*     */           }
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void closeAll() {
/* 529 */     selectAgain();
/* 530 */     Set<SelectionKey> keys = this.selector.keys();
/* 531 */     Collection<AbstractNioChannel> channels = new ArrayList<AbstractNioChannel>(keys.size());
/* 532 */     for (SelectionKey k : keys) {
/* 533 */       Object a = k.attachment();
/* 534 */       if (a instanceof AbstractNioChannel) {
/* 535 */         channels.add((AbstractNioChannel)a); continue;
/*     */       } 
/* 537 */       k.cancel();
/*     */       
/* 539 */       NioTask<SelectableChannel> task = (NioTask<SelectableChannel>)a;
/* 540 */       invokeChannelUnregistered(task, k, (Throwable)null);
/*     */     } 
/*     */ 
/*     */     
/* 544 */     for (AbstractNioChannel ch : channels) {
/* 545 */       ch.unsafe().close(ch.unsafe().voidPromise());
/*     */     }
/*     */   }
/*     */   
/*     */   private static void invokeChannelUnregistered(NioTask<SelectableChannel> task, SelectionKey k, Throwable cause) {
/*     */     try {
/* 551 */       task.channelUnregistered(k.channel(), cause);
/* 552 */     } catch (Exception e) {
/* 553 */       logger.warn("Unexpected exception while running NioTask.channelUnregistered()", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void wakeup(boolean inEventLoop) {
/* 559 */     if (!inEventLoop && this.wakenUp.compareAndSet(false, true)) {
/* 560 */       this.selector.wakeup();
/*     */     }
/*     */   }
/*     */   
/*     */   void selectNow() throws IOException {
/*     */     try {
/* 566 */       this.selector.selectNow();
/*     */     } finally {
/*     */       
/* 569 */       if (this.wakenUp.get()) {
/* 570 */         this.selector.wakeup();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private void select() throws IOException {
/* 576 */     Selector selector = this.selector;
/*     */     try {
/* 578 */       int selectCnt = 0;
/* 579 */       long currentTimeNanos = System.nanoTime();
/* 580 */       long selectDeadLineNanos = currentTimeNanos + delayNanos(currentTimeNanos);
/*     */       while (true) {
/* 582 */         long timeoutMillis = (selectDeadLineNanos - currentTimeNanos + 500000L) / 1000000L;
/* 583 */         if (timeoutMillis <= 0L) {
/* 584 */           if (selectCnt == 0) {
/* 585 */             selector.selectNow();
/* 586 */             selectCnt = 1;
/*     */           } 
/*     */           
/*     */           break;
/*     */         } 
/* 591 */         int selectedKeys = selector.select(timeoutMillis);
/* 592 */         selectCnt++;
/*     */         
/* 594 */         if (selectedKeys != 0 || this.oldWakenUp || this.wakenUp.get() || hasTasks()) {
/*     */           break;
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 601 */         if (SELECTOR_AUTO_REBUILD_THRESHOLD > 0 && selectCnt >= SELECTOR_AUTO_REBUILD_THRESHOLD) {
/*     */ 
/*     */ 
/*     */           
/* 605 */           logger.warn("Selector.select() returned prematurely {} times in a row; rebuilding selector.", Integer.valueOf(selectCnt));
/*     */ 
/*     */ 
/*     */           
/* 609 */           rebuildSelector();
/* 610 */           selector = this.selector;
/*     */ 
/*     */           
/* 613 */           selector.selectNow();
/* 614 */           selectCnt = 1;
/*     */           
/*     */           break;
/*     */         } 
/* 618 */         currentTimeNanos = System.nanoTime();
/*     */       } 
/*     */       
/* 621 */       if (selectCnt > 3 && 
/* 622 */         logger.isDebugEnabled()) {
/* 623 */         logger.debug("Selector.select() returned prematurely {} times in a row.", Integer.valueOf(selectCnt - 1));
/*     */       }
/*     */     }
/* 626 */     catch (CancelledKeyException e) {
/* 627 */       if (logger.isDebugEnabled()) {
/* 628 */         logger.debug(CancelledKeyException.class.getSimpleName() + " raised by a Selector - JDK bug?", e);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void selectAgain() {
/* 635 */     this.needsToSelectAgain = false;
/*     */     try {
/* 637 */       this.selector.selectNow();
/* 638 */     } catch (Throwable t) {
/* 639 */       logger.warn("Failed to update SelectionKeys.", t);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\channel\nio\NioEventLoop.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */