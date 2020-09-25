/*      */ package com.google.common.util.concurrent;
/*      */ 
/*      */ import com.google.common.annotations.Beta;
/*      */ import com.google.common.base.Function;
/*      */ import com.google.common.base.Preconditions;
/*      */ import com.google.common.collect.ImmutableList;
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.collect.Ordering;
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.lang.reflect.UndeclaredThrowableException;
/*      */ import java.util.Arrays;
/*      */ import java.util.List;
/*      */ import java.util.concurrent.BlockingQueue;
/*      */ import java.util.concurrent.CancellationException;
/*      */ import java.util.concurrent.CountDownLatch;
/*      */ import java.util.concurrent.ExecutionException;
/*      */ import java.util.concurrent.Executor;
/*      */ import java.util.concurrent.Future;
/*      */ import java.util.concurrent.LinkedBlockingQueue;
/*      */ import java.util.concurrent.TimeUnit;
/*      */ import java.util.concurrent.TimeoutException;
/*      */ import java.util.concurrent.atomic.AtomicInteger;
/*      */ import javax.annotation.Nullable;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ @Beta
/*      */ public final class Futures
/*      */ {
/*      */   @Deprecated
/*      */   public static <V> UninterruptibleFuture<V> makeUninterruptible(final Future<V> future) {
/*   80 */     Preconditions.checkNotNull(future);
/*   81 */     if (future instanceof UninterruptibleFuture) {
/*   82 */       return (UninterruptibleFuture<V>)future;
/*      */     }
/*   84 */     return new UninterruptibleFuture<V>()
/*      */       {
/*      */         public boolean cancel(boolean mayInterruptIfRunning) {
/*   87 */           return future.cancel(mayInterruptIfRunning);
/*      */         }
/*      */         
/*      */         public boolean isCancelled() {
/*   91 */           return future.isCancelled();
/*      */         }
/*      */         
/*      */         public boolean isDone() {
/*   95 */           return future.isDone();
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         public V get(long timeout, TimeUnit unit) throws TimeoutException, ExecutionException {
/*  101 */           return Uninterruptibles.getUninterruptibly(future, timeout, unit);
/*      */         }
/*      */ 
/*      */         
/*      */         public V get() throws ExecutionException {
/*  106 */           return Uninterruptibles.getUninterruptibly(future);
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static <V> ListenableFuture<V> makeListenable(Future<V> future) {
/*  136 */     return JdkFutureAdapters.listenInPoolThread(future);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static <V, X extends Exception> CheckedFuture<V, X> makeChecked(Future<V> future, Function<Exception, X> mapper) {
/*  164 */     return new MappingCheckedFuture<V, X>(makeListenable(future), mapper);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <V, X extends Exception> CheckedFuture<V, X> makeChecked(ListenableFuture<V> future, Function<Exception, X> mapper) {
/*  181 */     return new MappingCheckedFuture<V, X>((ListenableFuture<V>)Preconditions.checkNotNull(future), mapper);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <V> ListenableFuture<V> immediateFuture(@Nullable V value) {
/*  191 */     SettableFuture<V> future = SettableFuture.create();
/*  192 */     future.set(value);
/*  193 */     return future;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <V, X extends Exception> CheckedFuture<V, X> immediateCheckedFuture(@Nullable V value) {
/*  206 */     SettableFuture<V> future = SettableFuture.create();
/*  207 */     future.set(value);
/*  208 */     return makeChecked(future, new Function<Exception, X>()
/*      */         {
/*      */           public X apply(Exception e) {
/*  211 */             throw new AssertionError("impossible");
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <V> ListenableFuture<V> immediateFailedFuture(Throwable throwable) {
/*  229 */     Preconditions.checkNotNull(throwable);
/*  230 */     SettableFuture<V> future = SettableFuture.create();
/*  231 */     future.setException(throwable);
/*  232 */     return future;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <V, X extends Exception> CheckedFuture<V, X> immediateFailedCheckedFuture(final X exception) {
/*  249 */     Preconditions.checkNotNull(exception);
/*  250 */     return makeChecked(immediateFailedFuture((Throwable)exception), new Function<Exception, X>()
/*      */         {
/*      */           public X apply(Exception e)
/*      */           {
/*  254 */             return (X)exception;
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <I, O> ListenableFuture<O> chain(ListenableFuture<I> input, Function<? super I, ? extends ListenableFuture<? extends O>> function) {
/*  313 */     return chain(input, function, MoreExecutors.sameThreadExecutor());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <I, O> ListenableFuture<O> chain(ListenableFuture<I> input, Function<? super I, ? extends ListenableFuture<? extends O>> function, Executor exec) {
/*  365 */     ChainingListenableFuture<I, O> chain = new ChainingListenableFuture<I, O>(function, input);
/*      */     
/*  367 */     input.addListener(chain, exec);
/*  368 */     return chain;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <I, O> ListenableFuture<O> transform(ListenableFuture<I> future, Function<? super I, ? extends O> function) {
/*  420 */     return transform(future, function, MoreExecutors.sameThreadExecutor());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <I, O> ListenableFuture<O> transform(ListenableFuture<I> future, final Function<? super I, ? extends O> function, Executor exec) {
/*  473 */     Preconditions.checkNotNull(function);
/*  474 */     Function<I, ListenableFuture<O>> wrapperFunction = new Function<I, ListenableFuture<O>>()
/*      */       {
/*      */         public ListenableFuture<O> apply(I input) {
/*  477 */           O output = (O)function.apply(input);
/*  478 */           return Futures.immediateFuture(output);
/*      */         }
/*      */       };
/*  481 */     return chain(future, wrapperFunction, exec);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Beta
/*      */   public static <I, O> Future<O> lazyTransform(final Future<I> future, final Function<? super I, ? extends O> function) {
/*  510 */     Preconditions.checkNotNull(future);
/*  511 */     Preconditions.checkNotNull(function);
/*  512 */     return new Future<O>()
/*      */       {
/*      */         public boolean cancel(boolean mayInterruptIfRunning)
/*      */         {
/*  516 */           return future.cancel(mayInterruptIfRunning);
/*      */         }
/*      */ 
/*      */         
/*      */         public boolean isCancelled() {
/*  521 */           return future.isCancelled();
/*      */         }
/*      */ 
/*      */         
/*      */         public boolean isDone() {
/*  526 */           return future.isDone();
/*      */         }
/*      */ 
/*      */         
/*      */         public O get() throws InterruptedException, ExecutionException {
/*  531 */           return applyTransformation(future.get());
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         public O get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
/*  537 */           return applyTransformation(future.get(timeout, unit));
/*      */         }
/*      */         
/*      */         private O applyTransformation(I input) throws ExecutionException {
/*      */           try {
/*  542 */             return (O)function.apply(input);
/*  543 */           } catch (Throwable t) {
/*  544 */             throw new ExecutionException(t);
/*      */           } 
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public static <I, O> Future<O> transform(final Future<I> future, final Function<? super I, ? extends O> function) {
/*  591 */     if (future instanceof ListenableFuture) {
/*  592 */       return transform((ListenableFuture<I>)future, function);
/*      */     }
/*  594 */     Preconditions.checkNotNull(future);
/*  595 */     Preconditions.checkNotNull(function);
/*  596 */     return new Future<O>()
/*      */       {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  619 */         private final Object lock = new Object();
/*      */         private boolean set = false;
/*  621 */         private O value = null;
/*  622 */         private ExecutionException exception = null;
/*      */ 
/*      */         
/*      */         public O get() throws InterruptedException, ExecutionException {
/*  626 */           return apply(future.get());
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         public O get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
/*  632 */           return apply(future.get(timeout, unit));
/*      */         }
/*      */         
/*      */         private O apply(I raw) throws ExecutionException {
/*  636 */           synchronized (this.lock) {
/*  637 */             if (!this.set) {
/*      */               try {
/*  639 */                 this.value = (O)function.apply(raw);
/*  640 */               } catch (RuntimeException e) {
/*  641 */                 this.exception = new ExecutionException(e);
/*  642 */               } catch (Error e) {
/*  643 */                 this.exception = new ExecutionException(e);
/*      */               } 
/*  645 */               this.set = true;
/*      */             } 
/*      */             
/*  648 */             if (this.exception != null) {
/*  649 */               throw this.exception;
/*      */             }
/*  651 */             return this.value;
/*      */           } 
/*      */         }
/*      */ 
/*      */         
/*      */         public boolean cancel(boolean mayInterruptIfRunning) {
/*  657 */           return future.cancel(mayInterruptIfRunning);
/*      */         }
/*      */ 
/*      */         
/*      */         public boolean isCancelled() {
/*  662 */           return future.isCancelled();
/*      */         }
/*      */ 
/*      */         
/*      */         public boolean isDone() {
/*  667 */           return future.isDone();
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class ChainingListenableFuture<I, O>
/*      */     extends AbstractFuture<O>
/*      */     implements Runnable
/*      */   {
/*      */     private Function<? super I, ? extends ListenableFuture<? extends O>> function;
/*      */ 
/*      */ 
/*      */     
/*      */     private ListenableFuture<? extends I> inputFuture;
/*      */ 
/*      */     
/*      */     private volatile ListenableFuture<? extends O> outputFuture;
/*      */ 
/*      */     
/*  689 */     private final BlockingQueue<Boolean> mayInterruptIfRunningChannel = new LinkedBlockingQueue<Boolean>(1);
/*      */     
/*  691 */     private final CountDownLatch outputCreated = new CountDownLatch(1);
/*      */ 
/*      */ 
/*      */     
/*      */     private ChainingListenableFuture(Function<? super I, ? extends ListenableFuture<? extends O>> function, ListenableFuture<? extends I> inputFuture) {
/*  696 */       this.function = (Function<? super I, ? extends ListenableFuture<? extends O>>)Preconditions.checkNotNull(function);
/*  697 */       this.inputFuture = (ListenableFuture<? extends I>)Preconditions.checkNotNull(inputFuture);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public O get() throws InterruptedException, ExecutionException {
/*  707 */       if (!isDone()) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  712 */         ListenableFuture<? extends I> inputFuture = this.inputFuture;
/*  713 */         if (inputFuture != null) {
/*  714 */           inputFuture.get();
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  720 */         this.outputCreated.await();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  727 */         ListenableFuture<? extends O> outputFuture = this.outputFuture;
/*  728 */         if (outputFuture != null) {
/*  729 */           outputFuture.get();
/*      */         }
/*      */       } 
/*  732 */       return super.get();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public O get(long timeout, TimeUnit unit) throws TimeoutException, ExecutionException, InterruptedException {
/*  743 */       if (!isDone()) {
/*      */ 
/*      */         
/*  746 */         if (unit != TimeUnit.NANOSECONDS) {
/*  747 */           timeout = TimeUnit.NANOSECONDS.convert(timeout, unit);
/*  748 */           unit = TimeUnit.NANOSECONDS;
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  755 */         ListenableFuture<? extends I> inputFuture = this.inputFuture;
/*  756 */         if (inputFuture != null) {
/*  757 */           long l = System.nanoTime();
/*  758 */           inputFuture.get(timeout, unit);
/*  759 */           timeout -= Math.max(0L, System.nanoTime() - l);
/*      */         } 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  765 */         long start = System.nanoTime();
/*  766 */         if (!this.outputCreated.await(timeout, unit)) {
/*  767 */           throw new TimeoutException();
/*      */         }
/*  769 */         timeout -= Math.max(0L, System.nanoTime() - start);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  776 */         ListenableFuture<? extends O> outputFuture = this.outputFuture;
/*  777 */         if (outputFuture != null) {
/*  778 */           outputFuture.get(timeout, unit);
/*      */         }
/*      */       } 
/*  781 */       return super.get(timeout, unit);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean cancel(boolean mayInterruptIfRunning) {
/*  790 */       if (super.cancel(mayInterruptIfRunning)) {
/*      */ 
/*      */         
/*  793 */         Uninterruptibles.putUninterruptibly(this.mayInterruptIfRunningChannel, Boolean.valueOf(mayInterruptIfRunning));
/*  794 */         cancel(this.inputFuture, mayInterruptIfRunning);
/*  795 */         cancel(this.outputFuture, mayInterruptIfRunning);
/*  796 */         return true;
/*      */       } 
/*  798 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     private void cancel(@Nullable Future<?> future, boolean mayInterruptIfRunning) {
/*  803 */       if (future != null) {
/*  804 */         future.cancel(mayInterruptIfRunning);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void run() {
/*      */       try {
/*      */         I sourceResult;
/*      */         try {
/*  813 */           sourceResult = Uninterruptibles.getUninterruptibly((Future)this.inputFuture);
/*  814 */         } catch (CancellationException e) {
/*      */ 
/*      */ 
/*      */           
/*  818 */           cancel(false);
/*      */           return;
/*  820 */         } catch (ExecutionException e) {
/*      */           
/*  822 */           setException(e.getCause());
/*      */           
/*      */           return;
/*      */         } 
/*  826 */         final ListenableFuture<? extends O> outputFuture = this.outputFuture = (ListenableFuture<? extends O>)this.function.apply(sourceResult);
/*      */         
/*  828 */         if (isCancelled()) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  834 */           outputFuture.cancel(((Boolean)Uninterruptibles.<Boolean>takeUninterruptibly(this.mayInterruptIfRunningChannel)).booleanValue());
/*      */           
/*  836 */           this.outputFuture = null;
/*      */           return;
/*      */         } 
/*  839 */         outputFuture.addListener(new Runnable()
/*      */             {
/*      */ 
/*      */               
/*      */               public void run()
/*      */               {
/*      */                 try {
/*  846 */                   Futures.ChainingListenableFuture.this.set(Uninterruptibles.getUninterruptibly(outputFuture));
/*  847 */                 } catch (CancellationException e) {
/*      */ 
/*      */ 
/*      */                   
/*  851 */                   Futures.ChainingListenableFuture.this.cancel(false);
/*      */                   return;
/*  853 */                 } catch (ExecutionException e) {
/*      */                   
/*  855 */                   Futures.ChainingListenableFuture.this.setException(e.getCause());
/*      */                 } finally {
/*      */                   
/*  858 */                   Futures.ChainingListenableFuture.this.outputFuture = null;
/*      */                 } 
/*      */               }
/*      */             },  MoreExecutors.sameThreadExecutor());
/*  862 */       } catch (UndeclaredThrowableException e) {
/*      */         
/*  864 */         setException(e.getCause());
/*  865 */       } catch (RuntimeException e) {
/*      */ 
/*      */         
/*  868 */         setException(e);
/*  869 */       } catch (Error e) {
/*      */         
/*  871 */         setException(e);
/*      */       } finally {
/*      */         
/*  874 */         this.function = null;
/*  875 */         this.inputFuture = null;
/*      */         
/*  877 */         this.outputCreated.countDown();
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Beta
/*      */   public static <V> ListenableFuture<List<V>> allAsList(ListenableFuture<? extends V>... futures) {
/*  901 */     return new ListFuture<V>(ImmutableList.copyOf((Object[])futures), true, MoreExecutors.sameThreadExecutor());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Beta
/*      */   public static <V> ListenableFuture<List<V>> allAsList(Iterable<? extends ListenableFuture<? extends V>> futures) {
/*  924 */     return new ListFuture<V>(ImmutableList.copyOf(futures), true, MoreExecutors.sameThreadExecutor());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Beta
/*      */   public static <V> ListenableFuture<List<V>> successfulAsList(ListenableFuture<? extends V>... futures) {
/*  944 */     return new ListFuture<V>(ImmutableList.copyOf((Object[])futures), false, MoreExecutors.sameThreadExecutor());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Beta
/*      */   public static <V> ListenableFuture<List<V>> successfulAsList(Iterable<? extends ListenableFuture<? extends V>> futures) {
/*  964 */     return new ListFuture<V>(ImmutableList.copyOf(futures), false, MoreExecutors.sameThreadExecutor());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <V> void addCallback(ListenableFuture<V> future, FutureCallback<? super V> callback) {
/* 1012 */     addCallback(future, callback, MoreExecutors.sameThreadExecutor());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <V> void addCallback(final ListenableFuture<V> future, final FutureCallback<? super V> callback, Executor executor) {
/* 1064 */     Preconditions.checkNotNull(callback);
/* 1065 */     Runnable callbackListener = new Runnable()
/*      */       {
/*      */         
/*      */         public void run()
/*      */         {
/*      */           try {
/* 1071 */             V value = Uninterruptibles.getUninterruptibly(future);
/* 1072 */             callback.onSuccess(value);
/* 1073 */           } catch (ExecutionException e) {
/* 1074 */             callback.onFailure(e.getCause());
/* 1075 */           } catch (RuntimeException e) {
/* 1076 */             callback.onFailure(e);
/* 1077 */           } catch (Error e) {
/* 1078 */             callback.onFailure(e);
/*      */           } 
/*      */         }
/*      */       };
/* 1082 */     future.addListener(callbackListener, executor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Beta
/*      */   public static <V, X extends Exception> V get(Future<V> future, Class<X> exceptionClass) throws X {
/* 1135 */     Preconditions.checkNotNull(future);
/* 1136 */     Preconditions.checkArgument(!RuntimeException.class.isAssignableFrom(exceptionClass), "Futures.get exception type (%s) must not be a RuntimeException", new Object[] { exceptionClass });
/*      */ 
/*      */     
/*      */     try {
/* 1140 */       return future.get();
/* 1141 */     } catch (InterruptedException e) {
/* 1142 */       Thread.currentThread().interrupt();
/* 1143 */       throw newWithCause(exceptionClass, e);
/* 1144 */     } catch (ExecutionException e) {
/* 1145 */       wrapAndThrowExceptionOrError(e.getCause(), exceptionClass);
/* 1146 */       throw (X)new AssertionError();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Beta
/*      */   public static <V, X extends Exception> V get(Future<V> future, long timeout, TimeUnit unit, Class<X> exceptionClass) throws X {
/* 1202 */     Preconditions.checkNotNull(future);
/* 1203 */     Preconditions.checkNotNull(unit);
/* 1204 */     Preconditions.checkArgument(!RuntimeException.class.isAssignableFrom(exceptionClass), "Futures.get exception type (%s) must not be a RuntimeException", new Object[] { exceptionClass });
/*      */ 
/*      */     
/*      */     try {
/* 1208 */       return future.get(timeout, unit);
/* 1209 */     } catch (InterruptedException e) {
/* 1210 */       Thread.currentThread().interrupt();
/* 1211 */       throw newWithCause(exceptionClass, e);
/* 1212 */     } catch (TimeoutException e) {
/* 1213 */       throw newWithCause(exceptionClass, e);
/* 1214 */     } catch (ExecutionException e) {
/* 1215 */       wrapAndThrowExceptionOrError(e.getCause(), exceptionClass);
/* 1216 */       throw (X)new AssertionError();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static <X extends Exception> void wrapAndThrowExceptionOrError(Throwable cause, Class<X> exceptionClass) throws X {
/* 1222 */     if (cause instanceof Error) {
/* 1223 */       throw (X)new ExecutionError((Error)cause);
/*      */     }
/* 1225 */     if (cause instanceof RuntimeException) {
/* 1226 */       throw (X)new UncheckedExecutionException(cause);
/*      */     }
/* 1228 */     throw newWithCause(exceptionClass, cause);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Beta
/*      */   public static <V> V getUnchecked(Future<V> future) {
/* 1270 */     Preconditions.checkNotNull(future);
/*      */     try {
/* 1272 */       return Uninterruptibles.getUninterruptibly(future);
/* 1273 */     } catch (ExecutionException e) {
/* 1274 */       wrapAndThrowUnchecked(e.getCause());
/* 1275 */       throw new AssertionError();
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void wrapAndThrowUnchecked(Throwable cause) {
/* 1280 */     if (cause instanceof Error) {
/* 1281 */       throw new ExecutionError((Error)cause);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1288 */     throw new UncheckedExecutionException(cause);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static <X extends Exception> X newWithCause(Class<X> exceptionClass, Throwable cause) {
/* 1312 */     List<Constructor<X>> constructors = (List)Arrays.asList(exceptionClass.getConstructors());
/*      */     
/* 1314 */     for (Constructor<X> constructor : preferringStrings(constructors)) {
/* 1315 */       Exception exception = newFromConstructor(constructor, cause);
/* 1316 */       if (exception != null) {
/* 1317 */         if (exception.getCause() == null) {
/* 1318 */           exception.initCause(cause);
/*      */         }
/* 1320 */         return (X)exception;
/*      */       } 
/*      */     } 
/* 1323 */     throw new IllegalArgumentException("No appropriate constructor for exception of type " + exceptionClass + " in response to chained exception", cause);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static <X extends Exception> List<Constructor<X>> preferringStrings(List<Constructor<X>> constructors) {
/* 1330 */     return WITH_STRING_PARAM_FIRST.sortedCopy(constructors);
/*      */   }
/*      */   
/* 1333 */   private static final Ordering<Constructor<?>> WITH_STRING_PARAM_FIRST = Ordering.natural().onResultOf(new Function<Constructor<?>, Boolean>()
/*      */       {
/*      */         public Boolean apply(Constructor<?> input) {
/* 1336 */           return Boolean.valueOf(Arrays.<Class<?>>asList(input.getParameterTypes()).contains(String.class));
/*      */         }
/*      */       }).reverse();
/*      */   
/*      */   @Nullable
/*      */   private static <X> X newFromConstructor(Constructor<X> constructor, Throwable cause) {
/* 1342 */     Class<?>[] paramTypes = constructor.getParameterTypes();
/* 1343 */     Object[] params = new Object[paramTypes.length];
/* 1344 */     for (int i = 0; i < paramTypes.length; i++) {
/* 1345 */       Class<?> paramType = paramTypes[i];
/* 1346 */       if (paramType.equals(String.class)) {
/* 1347 */         params[i] = cause.toString();
/* 1348 */       } else if (paramType.equals(Throwable.class)) {
/* 1349 */         params[i] = cause;
/*      */       } else {
/* 1351 */         return null;
/*      */       } 
/*      */     } 
/*      */     try {
/* 1355 */       return constructor.newInstance(params);
/* 1356 */     } catch (IllegalArgumentException e) {
/* 1357 */       return null;
/* 1358 */     } catch (InstantiationException e) {
/* 1359 */       return null;
/* 1360 */     } catch (IllegalAccessException e) {
/* 1361 */       return null;
/* 1362 */     } catch (InvocationTargetException e) {
/* 1363 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class ListFuture<V>
/*      */     extends AbstractFuture<List<V>>
/*      */   {
/*      */     ImmutableList<? extends ListenableFuture<? extends V>> futures;
/*      */ 
/*      */ 
/*      */     
/*      */     final boolean allMustSucceed;
/*      */ 
/*      */ 
/*      */     
/*      */     final AtomicInteger remaining;
/*      */ 
/*      */ 
/*      */     
/*      */     List<V> values;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     ListFuture(ImmutableList<? extends ListenableFuture<? extends V>> futures, boolean allMustSucceed, Executor listenerExecutor) {
/* 1391 */       this.futures = futures;
/* 1392 */       this.values = Lists.newArrayListWithCapacity(futures.size());
/* 1393 */       this.allMustSucceed = allMustSucceed;
/* 1394 */       this.remaining = new AtomicInteger(futures.size());
/*      */       
/* 1396 */       init(listenerExecutor);
/*      */     }
/*      */ 
/*      */     
/*      */     private void init(Executor listenerExecutor) {
/* 1401 */       addListener(new Runnable()
/*      */           {
/*      */             
/*      */             public void run()
/*      */             {
/* 1406 */               Futures.ListFuture.this.values = null;
/*      */ 
/*      */               
/* 1409 */               Futures.ListFuture.this.futures = null;
/*      */             }
/*      */           },  MoreExecutors.sameThreadExecutor());
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1416 */       if (this.futures.isEmpty()) {
/* 1417 */         set(Lists.newArrayList(this.values));
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/* 1422 */       for (int i = 0; i < this.futures.size(); i++) {
/* 1423 */         this.values.add(null);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1433 */       ImmutableList<? extends ListenableFuture<? extends V>> localFutures = this.futures;
/* 1434 */       for (int j = 0; j < localFutures.size(); j++) {
/* 1435 */         final ListenableFuture<? extends V> listenable = (ListenableFuture<? extends V>)localFutures.get(j);
/* 1436 */         final int index = j;
/* 1437 */         listenable.addListener(new Runnable()
/*      */             {
/*      */               public void run() {
/* 1440 */                 Futures.ListFuture.this.setOneValue(index, listenable);
/*      */               }
/*      */             }listenerExecutor);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void setOneValue(int index, Future<? extends V> future) {
/* 1450 */       List<V> localValues = this.values;
/* 1451 */       if (isDone() || localValues == null) {
/*      */ 
/*      */ 
/*      */         
/* 1455 */         Preconditions.checkState(this.allMustSucceed, "Future was done before all dependencies completed");
/*      */         
/*      */         return;
/*      */       } 
/*      */       
/*      */       try {
/* 1461 */         Preconditions.checkState(future.isDone(), "Tried to set value from future which is not done");
/*      */         
/* 1463 */         localValues.set(index, Uninterruptibles.getUninterruptibly((Future)future));
/* 1464 */       } catch (CancellationException e) {
/* 1465 */         if (this.allMustSucceed)
/*      */         {
/*      */ 
/*      */ 
/*      */           
/* 1470 */           cancel(false);
/*      */         }
/* 1472 */       } catch (ExecutionException e) {
/* 1473 */         if (this.allMustSucceed)
/*      */         {
/*      */           
/* 1476 */           setException(e.getCause());
/*      */         }
/* 1478 */       } catch (RuntimeException e) {
/* 1479 */         if (this.allMustSucceed) {
/* 1480 */           setException(e);
/*      */         }
/* 1482 */       } catch (Error e) {
/*      */         
/* 1484 */         setException(e);
/*      */       } finally {
/* 1486 */         int newRemaining = this.remaining.decrementAndGet();
/* 1487 */         Preconditions.checkState((newRemaining >= 0), "Less than 0 remaining futures");
/* 1488 */         if (newRemaining == 0) {
/* 1489 */           localValues = this.values;
/* 1490 */           if (localValues != null) {
/* 1491 */             set(Lists.newArrayList(localValues));
/*      */           } else {
/* 1493 */             Preconditions.checkState(isDone());
/*      */           } 
/*      */         } 
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public List<V> get() throws InterruptedException, ExecutionException {
/* 1501 */       callAllGets();
/*      */ 
/*      */ 
/*      */       
/* 1505 */       return super.get();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void callAllGets() throws InterruptedException {
/* 1514 */       ImmutableList<? extends ListenableFuture<? extends V>> immutableList = this.futures;
/* 1515 */       if (immutableList != null && !isDone()) {
/* 1516 */         for (ListenableFuture<? extends V> future : immutableList) {
/*      */ 
/*      */ 
/*      */           
/* 1520 */           while (!future.isDone()) {
/*      */             try {
/* 1522 */               future.get();
/* 1523 */             } catch (Error e) {
/* 1524 */               throw e;
/* 1525 */             } catch (InterruptedException e) {
/* 1526 */               throw e;
/* 1527 */             } catch (Throwable e) {
/*      */               
/* 1529 */               if (this.allMustSucceed) {
/*      */                 return;
/*      */               }
/*      */             } 
/*      */           } 
/*      */         } 
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class MappingCheckedFuture<V, X extends Exception>
/*      */     extends AbstractCheckedFuture<V, X>
/*      */   {
/*      */     final Function<Exception, X> mapper;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     MappingCheckedFuture(ListenableFuture<V> delegate, Function<Exception, X> mapper) {
/* 1552 */       super(delegate);
/*      */       
/* 1554 */       this.mapper = (Function<Exception, X>)Preconditions.checkNotNull(mapper);
/*      */     }
/*      */ 
/*      */     
/*      */     protected X mapException(Exception e) {
/* 1559 */       return (X)this.mapper.apply(e);
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\commo\\util\concurrent\Futures.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */