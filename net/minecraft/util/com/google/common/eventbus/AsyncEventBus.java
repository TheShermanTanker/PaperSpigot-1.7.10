/*     */ package net.minecraft.util.com.google.common.eventbus;
/*     */ 
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*     */ import java.util.concurrent.Executor;
/*     */ import net.minecraft.util.com.google.common.annotations.Beta;
/*     */ import net.minecraft.util.com.google.common.base.Preconditions;
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
/*     */ @Beta
/*     */ public class AsyncEventBus
/*     */   extends EventBus
/*     */ {
/*     */   private final Executor executor;
/*  38 */   private final ConcurrentLinkedQueue<EventBus.EventWithSubscriber> eventsToDispatch = new ConcurrentLinkedQueue<EventBus.EventWithSubscriber>();
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
/*     */   public AsyncEventBus(String identifier, Executor executor) {
/*  51 */     super(identifier);
/*  52 */     this.executor = (Executor)Preconditions.checkNotNull(executor);
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
/*     */   public AsyncEventBus(Executor executor, SubscriberExceptionHandler subscriberExceptionHandler) {
/*  67 */     super(subscriberExceptionHandler);
/*  68 */     this.executor = (Executor)Preconditions.checkNotNull(executor);
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
/*     */   public AsyncEventBus(Executor executor) {
/*  80 */     super("default");
/*  81 */     this.executor = (Executor)Preconditions.checkNotNull(executor);
/*     */   }
/*     */ 
/*     */   
/*     */   void enqueueEvent(Object event, EventSubscriber subscriber) {
/*  86 */     this.eventsToDispatch.offer(new EventBus.EventWithSubscriber(event, subscriber));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void dispatchQueuedEvents() {
/*     */     while (true) {
/*  97 */       EventBus.EventWithSubscriber eventWithSubscriber = this.eventsToDispatch.poll();
/*  98 */       if (eventWithSubscriber == null) {
/*     */         break;
/*     */       }
/*     */       
/* 102 */       dispatch(eventWithSubscriber.event, eventWithSubscriber.subscriber);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void dispatch(final Object event, final EventSubscriber subscriber) {
/* 111 */     Preconditions.checkNotNull(event);
/* 112 */     Preconditions.checkNotNull(subscriber);
/* 113 */     this.executor.execute(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/* 117 */             AsyncEventBus.this.dispatch(event, subscriber);
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\eventbus\AsyncEventBus.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */