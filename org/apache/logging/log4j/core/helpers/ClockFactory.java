/*    */ package org.apache.logging.log4j.core.helpers;
/*    */ 
/*    */ import org.apache.logging.log4j.status.StatusLogger;
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
/*    */ public final class ClockFactory
/*    */ {
/*    */   public static final String PROPERTY_NAME = "log4j.Clock";
/* 31 */   private static final StatusLogger LOGGER = StatusLogger.getLogger();
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
/*    */   public static Clock getClock() {
/* 58 */     return createClock();
/*    */   }
/*    */   
/*    */   private static Clock createClock() {
/* 62 */     String userRequest = System.getProperty("log4j.Clock");
/* 63 */     if (userRequest == null || "SystemClock".equals(userRequest)) {
/* 64 */       LOGGER.debug("Using default SystemClock for timestamps");
/* 65 */       return new SystemClock();
/*    */     } 
/* 67 */     if (CachedClock.class.getName().equals(userRequest) || "CachedClock".equals(userRequest)) {
/*    */       
/* 69 */       LOGGER.debug("Using specified CachedClock for timestamps");
/* 70 */       return CachedClock.instance();
/*    */     } 
/* 72 */     if (CoarseCachedClock.class.getName().equals(userRequest) || "CoarseCachedClock".equals(userRequest)) {
/*    */       
/* 74 */       LOGGER.debug("Using specified CoarseCachedClock for timestamps");
/* 75 */       return CoarseCachedClock.instance();
/*    */     } 
/*    */     try {
/* 78 */       Clock result = (Clock)Class.forName(userRequest).newInstance();
/* 79 */       LOGGER.debug("Using {} for timestamps", new Object[] { userRequest });
/* 80 */       return result;
/* 81 */     } catch (Exception e) {
/* 82 */       String fmt = "Could not create {}: {}, using default SystemClock for timestamps";
/* 83 */       LOGGER.error("Could not create {}: {}, using default SystemClock for timestamps", new Object[] { userRequest, e });
/* 84 */       return new SystemClock();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\helpers\ClockFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */