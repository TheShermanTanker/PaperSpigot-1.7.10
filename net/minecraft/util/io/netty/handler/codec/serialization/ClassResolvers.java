/*    */ package net.minecraft.util.io.netty.handler.codec.serialization;
/*    */ 
/*    */ import java.lang.ref.Reference;
/*    */ import java.util.HashMap;
/*    */ import net.minecraft.util.io.netty.util.internal.PlatformDependent;
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
/*    */ public final class ClassResolvers
/*    */ {
/*    */   public static ClassResolver cacheDisabled(ClassLoader classLoader) {
/* 31 */     return new ClassLoaderClassResolver(defaultClassLoader(classLoader));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ClassResolver weakCachingResolver(ClassLoader classLoader) {
/* 42 */     return new CachingClassResolver(new ClassLoaderClassResolver(defaultClassLoader(classLoader)), new WeakReferenceMap<String, Class<?>>(new HashMap<String, Reference<Class<?>>>()));
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
/*    */   
/*    */   public static ClassResolver softCachingResolver(ClassLoader classLoader) {
/* 55 */     return new CachingClassResolver(new ClassLoaderClassResolver(defaultClassLoader(classLoader)), new SoftReferenceMap<String, Class<?>>(new HashMap<String, Reference<Class<?>>>()));
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
/*    */   
/*    */   public static ClassResolver weakCachingConcurrentResolver(ClassLoader classLoader) {
/* 68 */     return new CachingClassResolver(new ClassLoaderClassResolver(defaultClassLoader(classLoader)), new WeakReferenceMap<String, Class<?>>(PlatformDependent.newConcurrentHashMap()));
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
/*    */ 
/*    */   
/*    */   public static ClassResolver softCachingConcurrentResolver(ClassLoader classLoader) {
/* 82 */     return new CachingClassResolver(new ClassLoaderClassResolver(defaultClassLoader(classLoader)), new SoftReferenceMap<String, Class<?>>(PlatformDependent.newConcurrentHashMap()));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   static ClassLoader defaultClassLoader(ClassLoader classLoader) {
/* 89 */     if (classLoader != null) {
/* 90 */       return classLoader;
/*    */     }
/*    */     
/* 93 */     ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
/* 94 */     if (contextClassLoader != null) {
/* 95 */       return contextClassLoader;
/*    */     }
/*    */     
/* 98 */     return ClassResolvers.class.getClassLoader();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\serialization\ClassResolvers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */