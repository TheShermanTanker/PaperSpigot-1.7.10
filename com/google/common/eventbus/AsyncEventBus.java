/*    */ package com.google.common.eventbus;
/*    */ 
/*    */ import com.google.common.annotations.Beta;
/*    */ import java.util.concurrent.ConcurrentLinkedQueue;
/*    */ import java.util.concurrent.Executor;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Beta
/*    */ public class AsyncEventBus
/*    */   extends EventBus
/*    */ {
/*    */   private final Executor executor;
/* 35 */   private final ConcurrentLinkedQueue<EventBus.EventWithHandler> eventsToDispatch = new ConcurrentLinkedQueue<EventBus.EventWithHandler>();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AsyncEventBus(String identifier, Executor executor) {
/* 48 */     super(identifier);
/* 49 */     this.executor = executor;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AsyncEventBus(Executor executor) {
/* 61 */     this.executor = executor;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void enqueueEvent(Object event, EventHandler handler) {
/* 66 */     this.eventsToDispatch.offer(new EventBus.EventWithHandler(event, handler));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void dispatchQueuedEvents() {
/*    */     while (true) {
/* 76 */       EventBus.EventWithHandler eventWithHandler = this.eventsToDispatch.poll();
/* 77 */       if (eventWithHandler == null) {
/*    */         break;
/*    */       }
/*    */       
/* 81 */       dispatch(eventWithHandler.event, eventWithHandler.handler);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void dispatch(final Object event, final EventHandler handler) {
/* 90 */     this.executor.execute(new Runnable()
/*    */         {
/*    */           public void run()
/*    */           {
/* 94 */             AsyncEventBus.this.dispatch(event, handler);
/*    */           }
/*    */         });
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\eventbus\AsyncEventBus.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */