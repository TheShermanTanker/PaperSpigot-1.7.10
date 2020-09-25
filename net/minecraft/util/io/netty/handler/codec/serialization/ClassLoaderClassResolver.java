/*    */ package net.minecraft.util.io.netty.handler.codec.serialization;
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
/*    */ class ClassLoaderClassResolver
/*    */   implements ClassResolver
/*    */ {
/*    */   private final ClassLoader classLoader;
/*    */   
/*    */   ClassLoaderClassResolver(ClassLoader classLoader) {
/* 23 */     this.classLoader = classLoader;
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<?> resolve(String className) throws ClassNotFoundException {
/*    */     try {
/* 29 */       return this.classLoader.loadClass(className);
/* 30 */     } catch (ClassNotFoundException e) {
/* 31 */       return Class.forName(className, false, this.classLoader);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\netty\handler\codec\serialization\ClassLoaderClassResolver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */