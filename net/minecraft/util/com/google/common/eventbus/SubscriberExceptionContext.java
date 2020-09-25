/*    */ package net.minecraft.util.com.google.common.eventbus;
/*    */ 
/*    */ import java.lang.reflect.Method;
/*    */ import net.minecraft.util.com.google.common.base.Preconditions;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SubscriberExceptionContext
/*    */ {
/*    */   private final EventBus eventBus;
/*    */   private final Object event;
/*    */   private final Object subscriber;
/*    */   private final Method subscriberMethod;
/*    */   
/*    */   SubscriberExceptionContext(EventBus eventBus, Object event, Object subscriber, Method subscriberMethod) {
/* 42 */     this.eventBus = (EventBus)Preconditions.checkNotNull(eventBus);
/* 43 */     this.event = Preconditions.checkNotNull(event);
/* 44 */     this.subscriber = Preconditions.checkNotNull(subscriber);
/* 45 */     this.subscriberMethod = (Method)Preconditions.checkNotNull(subscriberMethod);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public EventBus getEventBus() {
/* 53 */     return this.eventBus;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object getEvent() {
/* 60 */     return this.event;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Object getSubscriber() {
/* 67 */     return this.subscriber;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Method getSubscriberMethod() {
/* 74 */     return this.subscriberMethod;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\eventbus\SubscriberExceptionContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */