/*    */ package net.minecraft.util.com.google.common.reflect;
/*    */ 
/*    */ import java.lang.reflect.InvocationHandler;
/*    */ import java.lang.reflect.Proxy;
/*    */ import net.minecraft.util.com.google.common.annotations.Beta;
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
/*    */ 
/*    */ @Beta
/*    */ public final class Reflection
/*    */ {
/*    */   public static String getPackageName(Class<?> clazz) {
/* 41 */     return getPackageName(clazz.getName());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String getPackageName(String classFullName) {
/* 50 */     int lastDot = classFullName.lastIndexOf('.');
/* 51 */     return (lastDot < 0) ? "" : classFullName.substring(0, lastDot);
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
/*    */ 
/*    */   
/*    */   public static void initialize(Class<?>... classes) {
/* 67 */     for (Class<?> clazz : classes) {
/*    */       try {
/* 69 */         Class.forName(clazz.getName(), true, clazz.getClassLoader());
/* 70 */       } catch (ClassNotFoundException e) {
/* 71 */         throw new AssertionError(e);
/*    */       } 
/*    */     } 
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
/*    */   
/*    */   public static <T> T newProxy(Class<T> interfaceType, InvocationHandler handler) {
/* 88 */     Preconditions.checkNotNull(handler);
/* 89 */     Preconditions.checkArgument(interfaceType.isInterface(), "%s is not an interface", new Object[] { interfaceType });
/* 90 */     Object object = Proxy.newProxyInstance(interfaceType.getClassLoader(), new Class[] { interfaceType }, handler);
/*    */ 
/*    */ 
/*    */     
/* 94 */     return interfaceType.cast(object);
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\reflect\Reflection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */