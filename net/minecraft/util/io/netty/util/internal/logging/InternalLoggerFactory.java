/*    */ package net.minecraft.util.io.netty.util.internal.logging;
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
/*    */ public abstract class InternalLoggerFactory
/*    */ {
/*    */   private static volatile InternalLoggerFactory defaultFactory;
/*    */   
/*    */   static {
/*    */     InternalLoggerFactory internalLoggerFactory;
/* 36 */     String name = InternalLoggerFactory.class.getName();
/*    */     
/*    */     try {
/* 39 */       internalLoggerFactory = new Slf4JLoggerFactory(true);
/* 40 */       internalLoggerFactory.newInstance(name).debug("Using SLF4J as the default logging framework");
/* 41 */       defaultFactory = internalLoggerFactory;
/* 42 */     } catch (Throwable t1) {
/*    */       try {
/* 44 */         internalLoggerFactory = new Log4JLoggerFactory();
/* 45 */         internalLoggerFactory.newInstance(name).debug("Using Log4J as the default logging framework");
/* 46 */       } catch (Throwable t2) {
/* 47 */         internalLoggerFactory = new JdkLoggerFactory();
/* 48 */         internalLoggerFactory.newInstance(name).debug("Using java.util.logging as the default logging framework");
/*    */       } 
/*    */     } 
/*    */     
/* 52 */     defaultFactory = internalLoggerFactory;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static InternalLoggerFactory getDefaultFactory() {
/* 60 */     return defaultFactory;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void setDefaultFactory(InternalLoggerFactory defaultFactory) {
/* 67 */     if (defaultFactory == null) {
/* 68 */       throw new NullPointerException("defaultFactory");
/*    */     }
/* 70 */     InternalLoggerFactory.defaultFactory = defaultFactory;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static InternalLogger getInstance(Class<?> clazz) {
/* 77 */     return getInstance(clazz.getName());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static InternalLogger getInstance(String name) {
/* 84 */     return getDefaultFactory().newInstance(name);
/*    */   }
/*    */   
/*    */   protected abstract InternalLogger newInstance(String paramString);
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\nett\\util\internal\logging\InternalLoggerFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */