/*      */ package net.minecraft.util.com.google.common.util.concurrent;
/*      */ 
/*      */ import java.lang.reflect.Constructor;
/*      */ import java.lang.reflect.InvocationTargetException;
/*      */ import java.lang.reflect.UndeclaredThrowableException;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.CancellationException;
/*      */ import java.util.concurrent.CountDownLatch;
/*      */ import java.util.concurrent.ExecutionException;
/*      */ import java.util.concurrent.Executor;
/*      */ import java.util.concurrent.Future;
/*      */ import java.util.concurrent.TimeUnit;
/*      */ import java.util.concurrent.TimeoutException;
/*      */ import java.util.concurrent.atomic.AtomicInteger;
/*      */ import java.util.logging.Level;
/*      */ import java.util.logging.Logger;
/*      */ import javax.annotation.Nullable;
/*      */ import net.minecraft.util.com.google.common.annotations.Beta;
/*      */ import net.minecraft.util.com.google.common.base.Function;
/*      */ import net.minecraft.util.com.google.common.base.Optional;
/*      */ import net.minecraft.util.com.google.common.base.Preconditions;
/*      */ import net.minecraft.util.com.google.common.collect.ImmutableCollection;
/*      */ import net.minecraft.util.com.google.common.collect.ImmutableList;
/*      */ import net.minecraft.util.com.google.common.collect.Lists;
/*      */ import net.minecraft.util.com.google.common.collect.Ordering;
/*      */ import net.minecraft.util.com.google.common.collect.Sets;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*      */   public static <V, X extends Exception> CheckedFuture<V, X> makeChecked(ListenableFuture<V> future, Function<Exception, X> mapper) {
/*   88 */     return new MappingCheckedFuture<V, X>((ListenableFuture<V>)Preconditions.checkNotNull(future), mapper);
/*      */   }
/*      */   
/*      */   private static abstract class ImmediateFuture<V> implements ListenableFuture<V> {
/*      */     private ImmediateFuture() {}
/*      */     
/*   94 */     private static final Logger log = Logger.getLogger(ImmediateFuture.class.getName());
/*      */ 
/*      */ 
/*      */     
/*      */     public void addListener(Runnable listener, Executor executor) {
/*   99 */       Preconditions.checkNotNull(listener, "Runnable was null.");
/*  100 */       Preconditions.checkNotNull(executor, "Executor was null.");
/*      */       try {
/*  102 */         executor.execute(listener);
/*  103 */       } catch (RuntimeException e) {
/*      */ 
/*      */         
/*  106 */         log.log(Level.SEVERE, "RuntimeException while executing runnable " + listener + " with executor " + executor, e);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean cancel(boolean mayInterruptIfRunning) {
/*  113 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public V get(long timeout, TimeUnit unit) throws ExecutionException {
/*  121 */       Preconditions.checkNotNull(unit);
/*  122 */       return get();
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isCancelled() {
/*  127 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isDone() {
/*  132 */       return true;
/*      */     }
/*      */     
/*      */     public abstract V get() throws ExecutionException;
/*      */   }
/*      */   
/*      */   private static class ImmediateSuccessfulFuture<V>
/*      */     extends ImmediateFuture<V> {
/*      */     ImmediateSuccessfulFuture(@Nullable V value) {
/*  141 */       this.value = value;
/*      */     }
/*      */     @Nullable
/*      */     private final V value;
/*      */     public V get() {
/*  146 */       return this.value;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class ImmediateSuccessfulCheckedFuture<V, X extends Exception>
/*      */     extends ImmediateFuture<V> implements CheckedFuture<V, X> {
/*      */     @Nullable
/*      */     private final V value;
/*      */     
/*      */     ImmediateSuccessfulCheckedFuture(@Nullable V value) {
/*  156 */       this.value = value;
/*      */     }
/*      */ 
/*      */     
/*      */     public V get() {
/*  161 */       return this.value;
/*      */     }
/*      */ 
/*      */     
/*      */     public V checkedGet() {
/*  166 */       return this.value;
/*      */     }
/*      */ 
/*      */     
/*      */     public V checkedGet(long timeout, TimeUnit unit) {
/*  171 */       Preconditions.checkNotNull(unit);
/*  172 */       return this.value;
/*      */     }
/*      */   }
/*      */   
/*      */   private static class ImmediateFailedFuture<V>
/*      */     extends ImmediateFuture<V> {
/*      */     private final Throwable thrown;
/*      */     
/*      */     ImmediateFailedFuture(Throwable thrown) {
/*  181 */       this.thrown = thrown;
/*      */     }
/*      */ 
/*      */     
/*      */     public V get() throws ExecutionException {
/*  186 */       throw new ExecutionException(this.thrown);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class ImmediateCancelledFuture<V>
/*      */     extends ImmediateFuture<V> {
/*      */     private final CancellationException thrown;
/*      */     
/*      */     ImmediateCancelledFuture() {
/*  195 */       this.thrown = new CancellationException("Immediate cancelled future.");
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean isCancelled() {
/*  200 */       return true;
/*      */     }
/*      */ 
/*      */     
/*      */     public V get() {
/*  205 */       throw AbstractFuture.cancellationExceptionWithCause("Task was cancelled.", this.thrown);
/*      */     }
/*      */   }
/*      */   
/*      */   private static class ImmediateFailedCheckedFuture<V, X extends Exception>
/*      */     extends ImmediateFuture<V>
/*      */     implements CheckedFuture<V, X>
/*      */   {
/*      */     private final X thrown;
/*      */     
/*      */     ImmediateFailedCheckedFuture(X thrown) {
/*  216 */       this.thrown = thrown;
/*      */     }
/*      */ 
/*      */     
/*      */     public V get() throws ExecutionException {
/*  221 */       throw new ExecutionException(this.thrown);
/*      */     }
/*      */ 
/*      */     
/*      */     public V checkedGet() throws X {
/*  226 */       throw this.thrown;
/*      */     }
/*      */ 
/*      */     
/*      */     public V checkedGet(long timeout, TimeUnit unit) throws X {
/*  231 */       Preconditions.checkNotNull(unit);
/*  232 */       throw this.thrown;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <V> ListenableFuture<V> immediateFuture(@Nullable V value) {
/*  243 */     return new ImmediateSuccessfulFuture<V>(value);
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
/*  256 */     return new ImmediateSuccessfulCheckedFuture<V, X>(value);
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
/*      */   public static <V> ListenableFuture<V> immediateFailedFuture(Throwable throwable) {
/*  270 */     Preconditions.checkNotNull(throwable);
/*  271 */     return new ImmediateFailedFuture<V>(throwable);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <V> ListenableFuture<V> immediateCancelledFuture() {
/*  281 */     return new ImmediateCancelledFuture<V>();
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
/*      */   public static <V, X extends Exception> CheckedFuture<V, X> immediateFailedCheckedFuture(X exception) {
/*  296 */     Preconditions.checkNotNull(exception);
/*  297 */     return new ImmediateFailedCheckedFuture<V, X>(exception);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <V> ListenableFuture<V> withFallback(ListenableFuture<? extends V> input, FutureFallback<? extends V> fallback) {
/*  375 */     return withFallback(input, fallback, MoreExecutors.sameThreadExecutor());
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <V> ListenableFuture<V> withFallback(ListenableFuture<? extends V> input, FutureFallback<? extends V> fallback, Executor executor) {
/*  439 */     Preconditions.checkNotNull(fallback);
/*  440 */     return new FallbackFuture<V>(input, fallback, executor);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class FallbackFuture<V>
/*      */     extends AbstractFuture<V>
/*      */   {
/*      */     private volatile ListenableFuture<? extends V> running;
/*      */ 
/*      */ 
/*      */     
/*      */     FallbackFuture(ListenableFuture<? extends V> input, final FutureFallback<? extends V> fallback, Executor executor) {
/*  454 */       this.running = input;
/*  455 */       Futures.addCallback(this.running, new FutureCallback<V>()
/*      */           {
/*      */             public void onSuccess(V value) {
/*  458 */               Futures.FallbackFuture.this.set(value);
/*      */             }
/*      */ 
/*      */             
/*      */             public void onFailure(Throwable t) {
/*  463 */               if (Futures.FallbackFuture.this.isCancelled()) {
/*      */                 return;
/*      */               }
/*      */               try {
/*  467 */                 Futures.FallbackFuture.this.running = fallback.create(t);
/*  468 */                 if (Futures.FallbackFuture.this.isCancelled()) {
/*  469 */                   Futures.FallbackFuture.this.running.cancel(Futures.FallbackFuture.this.wasInterrupted());
/*      */                   return;
/*      */                 } 
/*  472 */                 Futures.addCallback(Futures.FallbackFuture.this.running, new FutureCallback<V>()
/*      */                     {
/*      */                       public void onSuccess(V value) {
/*  475 */                         Futures.FallbackFuture.this.set(value);
/*      */                       }
/*      */ 
/*      */                       
/*      */                       public void onFailure(Throwable t) {
/*  480 */                         if (Futures.FallbackFuture.this.running.isCancelled()) {
/*  481 */                           Futures.FallbackFuture.this.cancel(false);
/*      */                         } else {
/*  483 */                           Futures.FallbackFuture.this.setException(t);
/*      */                         } 
/*      */                       }
/*      */                     },  MoreExecutors.sameThreadExecutor());
/*  487 */               } catch (Throwable e) {
/*  488 */                 Futures.FallbackFuture.this.setException(e);
/*      */               } 
/*      */             }
/*      */           }executor);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean cancel(boolean mayInterruptIfRunning) {
/*  496 */       if (super.cancel(mayInterruptIfRunning)) {
/*  497 */         this.running.cancel(mayInterruptIfRunning);
/*  498 */         return true;
/*      */       } 
/*  500 */       return false;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <I, O> ListenableFuture<O> transform(ListenableFuture<I> input, AsyncFunction<? super I, ? extends O> function) {
/*  561 */     return transform(input, function, MoreExecutors.sameThreadExecutor());
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
/*      */   public static <I, O> ListenableFuture<O> transform(ListenableFuture<I> input, AsyncFunction<? super I, ? extends O> function, Executor executor) {
/*  606 */     ChainingListenableFuture<I, O> output = new ChainingListenableFuture<I, O>(function, input);
/*      */     
/*  608 */     input.addListener(output, executor);
/*  609 */     return output;
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
/*      */   
/*      */   public static <I, O> ListenableFuture<O> transform(ListenableFuture<I> input, Function<? super I, ? extends O> function) {
/*  667 */     return transform(input, function, MoreExecutors.sameThreadExecutor());
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
/*      */   public static <I, O> ListenableFuture<O> transform(ListenableFuture<I> input, final Function<? super I, ? extends O> function, Executor executor) {
/*  709 */     Preconditions.checkNotNull(function);
/*  710 */     AsyncFunction<I, O> wrapperFunction = new AsyncFunction<I, O>()
/*      */       {
/*      */         public ListenableFuture<O> apply(I input) {
/*  713 */           O output = (O)function.apply(input);
/*  714 */           return Futures.immediateFuture(output);
/*      */         }
/*      */       };
/*  717 */     return transform(input, wrapperFunction, executor);
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
/*      */   public static <I, O> Future<O> lazyTransform(final Future<I> input, final Function<? super I, ? extends O> function) {
/*  745 */     Preconditions.checkNotNull(input);
/*  746 */     Preconditions.checkNotNull(function);
/*  747 */     return new Future<O>()
/*      */       {
/*      */         public boolean cancel(boolean mayInterruptIfRunning)
/*      */         {
/*  751 */           return input.cancel(mayInterruptIfRunning);
/*      */         }
/*      */ 
/*      */         
/*      */         public boolean isCancelled() {
/*  756 */           return input.isCancelled();
/*      */         }
/*      */ 
/*      */         
/*      */         public boolean isDone() {
/*  761 */           return input.isDone();
/*      */         }
/*      */ 
/*      */         
/*      */         public O get() throws InterruptedException, ExecutionException {
/*  766 */           return applyTransformation(input.get());
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         public O get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
/*  772 */           return applyTransformation(input.get(timeout, unit));
/*      */         }
/*      */         
/*      */         private O applyTransformation(I input) throws ExecutionException {
/*      */           try {
/*  777 */             return (O)function.apply(input);
/*  778 */           } catch (Throwable t) {
/*  779 */             throw new ExecutionException(t);
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
/*      */   private static class ChainingListenableFuture<I, O>
/*      */     extends AbstractFuture<O>
/*      */     implements Runnable
/*      */   {
/*      */     private AsyncFunction<? super I, ? extends O> function;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private ListenableFuture<? extends I> inputFuture;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private volatile ListenableFuture<? extends O> outputFuture;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  811 */     private final CountDownLatch outputCreated = new CountDownLatch(1);
/*      */ 
/*      */ 
/*      */     
/*      */     private ChainingListenableFuture(AsyncFunction<? super I, ? extends O> function, ListenableFuture<? extends I> inputFuture) {
/*  816 */       this.function = (AsyncFunction<? super I, ? extends O>)Preconditions.checkNotNull(function);
/*  817 */       this.inputFuture = (ListenableFuture<? extends I>)Preconditions.checkNotNull(inputFuture);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean cancel(boolean mayInterruptIfRunning) {
/*  826 */       if (super.cancel(mayInterruptIfRunning)) {
/*      */ 
/*      */         
/*  829 */         cancel(this.inputFuture, mayInterruptIfRunning);
/*  830 */         cancel(this.outputFuture, mayInterruptIfRunning);
/*  831 */         return true;
/*      */       } 
/*  833 */       return false;
/*      */     }
/*      */ 
/*      */     
/*      */     private void cancel(@Nullable Future<?> future, boolean mayInterruptIfRunning) {
/*  838 */       if (future != null) {
/*  839 */         future.cancel(mayInterruptIfRunning);
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     public void run() {
/*      */       try {
/*      */         I sourceResult;
/*      */         try {
/*  848 */           sourceResult = Uninterruptibles.getUninterruptibly((Future)this.inputFuture);
/*  849 */         } catch (CancellationException e) {
/*      */ 
/*      */ 
/*      */           
/*  853 */           cancel(false);
/*      */           return;
/*  855 */         } catch (ExecutionException e) {
/*      */           
/*  857 */           setException(e.getCause());
/*      */           
/*      */           return;
/*      */         } 
/*  861 */         final ListenableFuture<? extends O> outputFuture = this.outputFuture = this.function.apply(sourceResult);
/*      */         
/*  863 */         if (isCancelled()) {
/*  864 */           outputFuture.cancel(wasInterrupted());
/*  865 */           this.outputFuture = null;
/*      */           return;
/*      */         } 
/*  868 */         outputFuture.addListener(new Runnable()
/*      */             {
/*      */               public void run() {
/*      */                 try {
/*  872 */                   Futures.ChainingListenableFuture.this.set(Uninterruptibles.getUninterruptibly(outputFuture));
/*  873 */                 } catch (CancellationException e) {
/*      */ 
/*      */ 
/*      */                   
/*  877 */                   Futures.ChainingListenableFuture.this.cancel(false);
/*      */                   return;
/*  879 */                 } catch (ExecutionException e) {
/*      */                   
/*  881 */                   Futures.ChainingListenableFuture.this.setException(e.getCause());
/*      */                 } finally {
/*      */                   
/*  884 */                   Futures.ChainingListenableFuture.this.outputFuture = null;
/*      */                 } 
/*      */               }
/*      */             },  MoreExecutors.sameThreadExecutor());
/*  888 */       } catch (UndeclaredThrowableException e) {
/*      */         
/*  890 */         setException(e.getCause());
/*  891 */       } catch (Throwable t) {
/*      */ 
/*      */         
/*  894 */         setException(t);
/*      */       } finally {
/*      */         
/*  897 */         this.function = null;
/*  898 */         this.inputFuture = null;
/*      */         
/*  900 */         this.outputCreated.countDown();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static <V> ListenableFuture<V> dereference(ListenableFuture<? extends ListenableFuture<? extends V>> nested) {
/*  928 */     return transform(nested, (AsyncFunction)DEREFERENCER);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  934 */   private static final AsyncFunction<ListenableFuture<Object>, Object> DEREFERENCER = new AsyncFunction<ListenableFuture<Object>, Object>()
/*      */     {
/*      */       public ListenableFuture<Object> apply(ListenableFuture<Object> input) {
/*  937 */         return input;
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  960 */     return listFuture(ImmutableList.copyOf((Object[])futures), true, MoreExecutors.sameThreadExecutor());
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
/*  983 */     return listFuture(ImmutableList.copyOf(futures), true, MoreExecutors.sameThreadExecutor());
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
/*      */   public static <V> ListenableFuture<V> nonCancellationPropagating(ListenableFuture<V> future) {
/*  997 */     return new NonCancellationPropagatingFuture<V>(future);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class NonCancellationPropagatingFuture<V>
/*      */     extends AbstractFuture<V>
/*      */   {
/*      */     NonCancellationPropagatingFuture(final ListenableFuture<V> delegate) {
/* 1006 */       Preconditions.checkNotNull(delegate);
/* 1007 */       Futures.addCallback(delegate, new FutureCallback<V>()
/*      */           {
/*      */             public void onSuccess(V result) {
/* 1010 */               Futures.NonCancellationPropagatingFuture.this.set(result);
/*      */             }
/*      */ 
/*      */             
/*      */             public void onFailure(Throwable t) {
/* 1015 */               if (delegate.isCancelled()) {
/* 1016 */                 Futures.NonCancellationPropagatingFuture.this.cancel(false);
/*      */               } else {
/* 1018 */                 Futures.NonCancellationPropagatingFuture.this.setException(t);
/*      */               } 
/*      */             }
/*      */           },  MoreExecutors.sameThreadExecutor());
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
/*      */   @Beta
/*      */   public static <V> ListenableFuture<List<V>> successfulAsList(ListenableFuture<? extends V>... futures) {
/* 1043 */     return listFuture(ImmutableList.copyOf((Object[])futures), false, MoreExecutors.sameThreadExecutor());
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
/*      */   public static <V> ListenableFuture<List<V>> successfulAsList(Iterable<? extends ListenableFuture<? extends V>> futures) {
/* 1065 */     return listFuture(ImmutableList.copyOf(futures), false, MoreExecutors.sameThreadExecutor());
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
/*      */   public static <V> void addCallback(ListenableFuture<V> future, FutureCallback<? super V> callback) {
/* 1120 */     addCallback(future, callback, MoreExecutors.sameThreadExecutor());
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
/*      */   public static <V> void addCallback(final ListenableFuture<V> future, final FutureCallback<? super V> callback, Executor executor) {
/* 1162 */     Preconditions.checkNotNull(callback);
/* 1163 */     Runnable callbackListener = new Runnable()
/*      */       {
/*      */         public void run()
/*      */         {
/*      */           V value;
/*      */           
/*      */           try {
/* 1170 */             value = Uninterruptibles.getUninterruptibly(future);
/* 1171 */           } catch (ExecutionException e) {
/* 1172 */             callback.onFailure(e.getCause());
/*      */             return;
/* 1174 */           } catch (RuntimeException e) {
/* 1175 */             callback.onFailure(e);
/*      */             return;
/* 1177 */           } catch (Error e) {
/* 1178 */             callback.onFailure(e);
/*      */             return;
/*      */           } 
/* 1181 */           callback.onSuccess(value);
/*      */         }
/*      */       };
/* 1184 */     future.addListener(callbackListener, executor);
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
/*      */   public static <V, X extends Exception> V get(Future<V> future, Class<X> exceptionClass) throws X {
/* 1236 */     Preconditions.checkNotNull(future);
/* 1237 */     Preconditions.checkArgument(!RuntimeException.class.isAssignableFrom(exceptionClass), "Futures.get exception type (%s) must not be a RuntimeException", new Object[] { exceptionClass });
/*      */ 
/*      */     
/*      */     try {
/* 1241 */       return future.get();
/* 1242 */     } catch (InterruptedException e) {
/* 1243 */       Thread.currentThread().interrupt();
/* 1244 */       throw newWithCause(exceptionClass, e);
/* 1245 */     } catch (ExecutionException e) {
/* 1246 */       wrapAndThrowExceptionOrError(e.getCause(), exceptionClass);
/* 1247 */       throw (X)new AssertionError();
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
/*      */   public static <V, X extends Exception> V get(Future<V> future, long timeout, TimeUnit unit, Class<X> exceptionClass) throws X {
/* 1302 */     Preconditions.checkNotNull(future);
/* 1303 */     Preconditions.checkNotNull(unit);
/* 1304 */     Preconditions.checkArgument(!RuntimeException.class.isAssignableFrom(exceptionClass), "Futures.get exception type (%s) must not be a RuntimeException", new Object[] { exceptionClass });
/*      */ 
/*      */     
/*      */     try {
/* 1308 */       return future.get(timeout, unit);
/* 1309 */     } catch (InterruptedException e) {
/* 1310 */       Thread.currentThread().interrupt();
/* 1311 */       throw newWithCause(exceptionClass, e);
/* 1312 */     } catch (TimeoutException e) {
/* 1313 */       throw newWithCause(exceptionClass, e);
/* 1314 */     } catch (ExecutionException e) {
/* 1315 */       wrapAndThrowExceptionOrError(e.getCause(), exceptionClass);
/* 1316 */       throw (X)new AssertionError();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private static <X extends Exception> void wrapAndThrowExceptionOrError(Throwable cause, Class<X> exceptionClass) throws X {
/* 1322 */     if (cause instanceof Error) {
/* 1323 */       throw (X)new ExecutionError((Error)cause);
/*      */     }
/* 1325 */     if (cause instanceof RuntimeException) {
/* 1326 */       throw (X)new UncheckedExecutionException(cause);
/*      */     }
/* 1328 */     throw newWithCause(exceptionClass, cause);
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
/*      */   public static <V> V getUnchecked(Future<V> future) {
/* 1369 */     Preconditions.checkNotNull(future);
/*      */     try {
/* 1371 */       return Uninterruptibles.getUninterruptibly(future);
/* 1372 */     } catch (ExecutionException e) {
/* 1373 */       wrapAndThrowUnchecked(e.getCause());
/* 1374 */       throw new AssertionError();
/*      */     } 
/*      */   }
/*      */   
/*      */   private static void wrapAndThrowUnchecked(Throwable cause) {
/* 1379 */     if (cause instanceof Error) {
/* 1380 */       throw new ExecutionError((Error)cause);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1387 */     throw new UncheckedExecutionException(cause);
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
/* 1411 */     List<Constructor<X>> constructors = (List)Arrays.asList(exceptionClass.getConstructors());
/*      */     
/* 1413 */     for (Constructor<X> constructor : preferringStrings(constructors)) {
/* 1414 */       Exception exception = newFromConstructor(constructor, cause);
/* 1415 */       if (exception != null) {
/* 1416 */         if (exception.getCause() == null) {
/* 1417 */           exception.initCause(cause);
/*      */         }
/* 1419 */         return (X)exception;
/*      */       } 
/*      */     } 
/* 1422 */     throw new IllegalArgumentException("No appropriate constructor for exception of type " + exceptionClass + " in response to chained exception", cause);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static <X extends Exception> List<Constructor<X>> preferringStrings(List<Constructor<X>> constructors) {
/* 1429 */     return WITH_STRING_PARAM_FIRST.sortedCopy(constructors);
/*      */   }
/*      */   
/* 1432 */   private static final Ordering<Constructor<?>> WITH_STRING_PARAM_FIRST = Ordering.natural().onResultOf(new Function<Constructor<?>, Boolean>()
/*      */       {
/*      */         public Boolean apply(Constructor<?> input) {
/* 1435 */           return Boolean.valueOf(Arrays.<Class<?>>asList(input.getParameterTypes()).contains(String.class));
/*      */         }
/*      */       }).reverse();
/*      */   
/*      */   @Nullable
/*      */   private static <X> X newFromConstructor(Constructor<X> constructor, Throwable cause) {
/* 1441 */     Class<?>[] paramTypes = constructor.getParameterTypes();
/* 1442 */     Object[] params = new Object[paramTypes.length];
/* 1443 */     for (int i = 0; i < paramTypes.length; i++) {
/* 1444 */       Class<?> paramType = paramTypes[i];
/* 1445 */       if (paramType.equals(String.class)) {
/* 1446 */         params[i] = cause.toString();
/* 1447 */       } else if (paramType.equals(Throwable.class)) {
/* 1448 */         params[i] = cause;
/*      */       } else {
/* 1450 */         return null;
/*      */       } 
/*      */     } 
/*      */     try {
/* 1454 */       return constructor.newInstance(params);
/* 1455 */     } catch (IllegalArgumentException e) {
/* 1456 */       return null;
/* 1457 */     } catch (InstantiationException e) {
/* 1458 */       return null;
/* 1459 */     } catch (IllegalAccessException e) {
/* 1460 */       return null;
/* 1461 */     } catch (InvocationTargetException e) {
/* 1462 */       return null;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class CombinedFuture<V, C>
/*      */     extends AbstractFuture<C>
/*      */   {
/* 1471 */     private static final Logger logger = Logger.getLogger(CombinedFuture.class.getName());
/*      */     
/*      */     ImmutableCollection<? extends ListenableFuture<? extends V>> futures;
/*      */     
/*      */     final boolean allMustSucceed;
/*      */     final AtomicInteger remaining;
/*      */     Futures.FutureCombiner<V, C> combiner;
/*      */     List<Optional<V>> values;
/* 1479 */     final Object seenExceptionsLock = new Object();
/*      */ 
/*      */     
/*      */     Set<Throwable> seenExceptions;
/*      */ 
/*      */     
/*      */     CombinedFuture(ImmutableCollection<? extends ListenableFuture<? extends V>> futures, boolean allMustSucceed, Executor listenerExecutor, Futures.FutureCombiner<V, C> combiner) {
/* 1486 */       this.futures = futures;
/* 1487 */       this.allMustSucceed = allMustSucceed;
/* 1488 */       this.remaining = new AtomicInteger(futures.size());
/* 1489 */       this.combiner = combiner;
/* 1490 */       this.values = Lists.newArrayListWithCapacity(futures.size());
/* 1491 */       init(listenerExecutor);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     protected void init(Executor listenerExecutor) {
/* 1499 */       addListener(new Runnable()
/*      */           {
/*      */             public void run()
/*      */             {
/* 1503 */               if (Futures.CombinedFuture.this.isCancelled()) {
/* 1504 */                 for (ListenableFuture<?> future : (Iterable<ListenableFuture<?>>)Futures.CombinedFuture.this.futures) {
/* 1505 */                   future.cancel(Futures.CombinedFuture.this.wasInterrupted());
/*      */                 }
/*      */               }
/*      */ 
/*      */               
/* 1510 */               Futures.CombinedFuture.this.futures = null;
/*      */ 
/*      */ 
/*      */               
/* 1514 */               Futures.CombinedFuture.this.values = null;
/*      */ 
/*      */               
/* 1517 */               Futures.CombinedFuture.this.combiner = null;
/*      */             }
/*      */           },  MoreExecutors.sameThreadExecutor());
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1524 */       if (this.futures.isEmpty()) {
/* 1525 */         set(this.combiner.combine((List<Optional<V>>)ImmutableList.of()));
/*      */         
/*      */         return;
/*      */       } 
/*      */       int i;
/* 1530 */       for (i = 0; i < this.futures.size(); i++) {
/* 1531 */         this.values.add(null);
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1542 */       i = 0;
/* 1543 */       for (ListenableFuture<? extends V> listenable : this.futures) {
/* 1544 */         final int index = i++;
/* 1545 */         listenable.addListener(new Runnable()
/*      */             {
/*      */               public void run() {
/* 1548 */                 Futures.CombinedFuture.this.setOneValue(index, listenable);
/*      */               }
/*      */             }listenerExecutor);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void setExceptionAndMaybeLog(Throwable throwable) {
/* 1561 */       boolean visibleFromOutputFuture = false;
/* 1562 */       boolean firstTimeSeeingThisException = true;
/* 1563 */       if (this.allMustSucceed) {
/*      */ 
/*      */         
/* 1566 */         visibleFromOutputFuture = setException(throwable);
/*      */         
/* 1568 */         synchronized (this.seenExceptionsLock) {
/* 1569 */           if (this.seenExceptions == null) {
/* 1570 */             this.seenExceptions = Sets.newHashSet();
/*      */           }
/* 1572 */           firstTimeSeeingThisException = this.seenExceptions.add(throwable);
/*      */         } 
/*      */       } 
/*      */       
/* 1576 */       if (throwable instanceof Error || (this.allMustSucceed && !visibleFromOutputFuture && firstTimeSeeingThisException))
/*      */       {
/* 1578 */         logger.log(Level.SEVERE, "input future failed.", throwable);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private void setOneValue(int index, Future<? extends V> future) {
/* 1586 */       List<Optional<V>> localValues = this.values;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1594 */       if (isDone() || localValues == null)
/*      */       {
/*      */ 
/*      */ 
/*      */         
/* 1599 */         Preconditions.checkState((this.allMustSucceed || isCancelled()), "Future was done before all dependencies completed");
/*      */       }
/*      */ 
/*      */       
/*      */       try {
/* 1604 */         Preconditions.checkState(future.isDone(), "Tried to set value from future which is not done");
/*      */         
/* 1606 */         V returnValue = Uninterruptibles.getUninterruptibly((Future)future);
/* 1607 */         if (localValues != null) {
/* 1608 */           localValues.set(index, Optional.fromNullable(returnValue));
/*      */         }
/* 1610 */       } catch (CancellationException e) {
/* 1611 */         if (this.allMustSucceed)
/*      */         {
/*      */           
/* 1614 */           cancel(false);
/*      */         }
/* 1616 */       } catch (ExecutionException e) {
/* 1617 */         setExceptionAndMaybeLog(e.getCause());
/* 1618 */       } catch (Throwable t) {
/* 1619 */         setExceptionAndMaybeLog(t);
/*      */       } finally {
/* 1621 */         int newRemaining = this.remaining.decrementAndGet();
/* 1622 */         Preconditions.checkState((newRemaining >= 0), "Less than 0 remaining futures");
/* 1623 */         if (newRemaining == 0) {
/* 1624 */           Futures.FutureCombiner<V, C> localCombiner = this.combiner;
/* 1625 */           if (localCombiner != null && localValues != null) {
/* 1626 */             set(localCombiner.combine(localValues));
/*      */           } else {
/* 1628 */             Preconditions.checkState(isDone());
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
/*      */   private static <V> ListenableFuture<List<V>> listFuture(ImmutableList<ListenableFuture<? extends V>> futures, boolean allMustSucceed, Executor listenerExecutor) {
/* 1640 */     return new CombinedFuture<V, List<V>>((ImmutableCollection<? extends ListenableFuture<? extends V>>)futures, allMustSucceed, listenerExecutor, new FutureCombiner<V, List<V>>()
/*      */         {
/*      */           
/*      */           public List<V> combine(List<Optional<V>> values)
/*      */           {
/* 1645 */             List<V> result = Lists.newArrayList();
/* 1646 */             for (Optional<V> element : values) {
/* 1647 */               result.add((element != null) ? (V)element.orNull() : null);
/*      */             }
/* 1649 */             return Collections.unmodifiableList(result);
/*      */           }
/*      */         });
/*      */   }
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
/*      */     MappingCheckedFuture(ListenableFuture<V> delegate, Function<Exception, X> mapper) {
/* 1665 */       super(delegate);
/*      */       
/* 1667 */       this.mapper = (Function<Exception, X>)Preconditions.checkNotNull(mapper);
/*      */     }
/*      */ 
/*      */     
/*      */     protected X mapException(Exception e) {
/* 1672 */       return (X)this.mapper.apply(e);
/*      */     }
/*      */   }
/*      */   
/*      */   private static interface FutureCombiner<V, C> {
/*      */     C combine(List<Optional<V>> param1List);
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\commo\\util\concurrent\Futures.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */