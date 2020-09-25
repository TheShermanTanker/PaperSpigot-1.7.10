/*    */ package org.bukkit.plugin;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RegisteredServiceProvider<T>
/*    */   implements Comparable<RegisteredServiceProvider<?>>
/*    */ {
/*    */   private Class<T> service;
/*    */   private Plugin plugin;
/*    */   private T provider;
/*    */   private ServicePriority priority;
/*    */   
/*    */   public RegisteredServiceProvider(Class<T> service, T provider, ServicePriority priority, Plugin plugin) {
/* 17 */     this.service = service;
/* 18 */     this.plugin = plugin;
/* 19 */     this.provider = provider;
/* 20 */     this.priority = priority;
/*    */   }
/*    */   
/*    */   public Class<T> getService() {
/* 24 */     return this.service;
/*    */   }
/*    */   
/*    */   public Plugin getPlugin() {
/* 28 */     return this.plugin;
/*    */   }
/*    */   
/*    */   public T getProvider() {
/* 32 */     return this.provider;
/*    */   }
/*    */   
/*    */   public ServicePriority getPriority() {
/* 36 */     return this.priority;
/*    */   }
/*    */   
/*    */   public int compareTo(RegisteredServiceProvider<?> other) {
/* 40 */     if (this.priority.ordinal() == other.getPriority().ordinal()) {
/* 41 */       return 0;
/*    */     }
/* 43 */     return (this.priority.ordinal() < other.getPriority().ordinal()) ? 1 : -1;
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\plugin\RegisteredServiceProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */