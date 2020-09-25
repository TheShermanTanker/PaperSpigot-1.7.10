/*    */ package net.minecraft.util.io.netty.util.internal;
/*    */ 
/*    */ import java.lang.reflect.Method;
/*    */ import javassist.ClassClassPath;
/*    */ import javassist.ClassPath;
/*    */ import javassist.ClassPool;
/*    */ import javassist.CtClass;
/*    */ import javassist.NotFoundException;
/*    */ import net.minecraft.util.io.netty.util.internal.logging.InternalLogger;
/*    */ import net.minecraft.util.io.netty.util.internal.logging.InternalLoggerFactory;
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
/*    */ public final class JavassistTypeParameterMatcherGenerator
/*    */ {
/* 32 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(JavassistTypeParameterMatcherGenerator.class);
/*    */ 
/*    */   
/* 35 */   private static final ClassPool classPool = new ClassPool(true);
/*    */   
/*    */   static {
/* 38 */     classPool.appendClassPath((ClassPath)new ClassClassPath(NoOpTypeParameterMatcher.class));
/*    */   }
/*    */   
/*    */   public void appendClassPath(ClassPath classpath) {
/* 42 */     classPool.appendClassPath(classpath);
/*    */   }
/*    */   
/*    */   public void appendClassPath(String pathname) throws NotFoundException {
/* 46 */     classPool.appendClassPath(pathname);
/*    */   }
/*    */   
/*    */   public static TypeParameterMatcher generate(Class<?> type) {
/* 50 */     ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
/* 51 */     if (classLoader == null) {
/* 52 */       classLoader = ClassLoader.getSystemClassLoader();
/*    */     }
/* 54 */     return generate(type, classLoader);
/*    */   }
/*    */   
/*    */   public static TypeParameterMatcher generate(Class<?> type, ClassLoader classLoader) {
/* 58 */     String typeName = typeName(type);
/* 59 */     String className = "io.netty.util.internal.__matchers__." + typeName + "Matcher";
/*    */     
/*    */     try {
/* 62 */       return (TypeParameterMatcher)Class.forName(className, true, classLoader).newInstance();
/* 63 */     } catch (Exception e) {
/*    */ 
/*    */ 
/*    */       
/* 67 */       CtClass c = classPool.getAndRename(NoOpTypeParameterMatcher.class.getName(), className);
/* 68 */       c.setModifiers(c.getModifiers() | 0x10);
/* 69 */       c.getDeclaredMethod("match").setBody("{ return $1 instanceof " + typeName + "; }");
/* 70 */       byte[] byteCode = c.toBytecode();
/* 71 */       c.detach();
/* 72 */       Method method = ClassLoader.class.getDeclaredMethod("defineClass", new Class[] { String.class, byte[].class, int.class, int.class });
/*    */       
/* 74 */       method.setAccessible(true);
/*    */       
/* 76 */       Class<?> generated = (Class)method.invoke(classLoader, new Object[] { className, byteCode, Integer.valueOf(0), Integer.valueOf(byteCode.length) });
/* 77 */       if (type != Object.class) {
/* 78 */         logger.debug("Generated: {}", generated.getName());
/*    */       }
/*    */ 
/*    */       
/* 82 */       return (TypeParameterMatcher)generated.newInstance();
/* 83 */     } catch (RuntimeException e) {
/* 84 */       throw e;
/* 85 */     } catch (Exception e) {
/* 86 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   private static String typeName(Class<?> type) {
/* 91 */     if (type.isArray()) {
/* 92 */       return typeName(type.getComponentType()) + "[]";
/*    */     }
/*    */     
/* 95 */     return type.getName();
/*    */   }
/*    */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\nett\\util\internal\JavassistTypeParameterMatcherGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */