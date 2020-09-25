/*     */ package net.minecraft.util.io.netty.util.internal;
/*     */ 
/*     */ import java.lang.reflect.Array;
/*     */ import java.lang.reflect.GenericArrayType;
/*     */ import java.lang.reflect.ParameterizedType;
/*     */ import java.lang.reflect.Type;
/*     */ import java.lang.reflect.TypeVariable;
/*     */ import java.util.HashMap;
/*     */ import java.util.IdentityHashMap;
/*     */ import java.util.Map;
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
/*     */ public abstract class TypeParameterMatcher
/*     */ {
/*  30 */   private static final TypeParameterMatcher NOOP = new NoOpTypeParameterMatcher();
/*  31 */   private static final Object TEST_OBJECT = new Object();
/*     */   
/*  33 */   private static final ThreadLocal<Map<Class<?>, TypeParameterMatcher>> getCache = new ThreadLocal<Map<Class<?>, TypeParameterMatcher>>()
/*     */     {
/*     */       protected Map<Class<?>, TypeParameterMatcher> initialValue()
/*     */       {
/*  37 */         return new IdentityHashMap<Class<?>, TypeParameterMatcher>();
/*     */       }
/*     */     };
/*     */   
/*     */   public static TypeParameterMatcher get(Class<?> parameterType) {
/*  42 */     Map<Class<?>, TypeParameterMatcher> getCache = TypeParameterMatcher.getCache.get();
/*     */     
/*  44 */     TypeParameterMatcher matcher = getCache.get(parameterType);
/*  45 */     if (matcher == null) {
/*  46 */       if (parameterType == Object.class) {
/*  47 */         matcher = NOOP;
/*  48 */       } else if (PlatformDependent.hasJavassist()) {
/*     */         try {
/*  50 */           matcher = JavassistTypeParameterMatcherGenerator.generate(parameterType);
/*  51 */           matcher.match(TEST_OBJECT);
/*  52 */         } catch (IllegalAccessError e) {
/*     */           
/*  54 */           matcher = null;
/*  55 */         } catch (Exception e) {
/*     */           
/*  57 */           matcher = null;
/*     */         } 
/*     */       } 
/*     */       
/*  61 */       if (matcher == null) {
/*  62 */         matcher = new ReflectiveMatcher(parameterType);
/*     */       }
/*     */       
/*  65 */       getCache.put(parameterType, matcher);
/*     */     } 
/*     */     
/*  68 */     return matcher;
/*     */   }
/*     */   
/*  71 */   private static final ThreadLocal<Map<Class<?>, Map<String, TypeParameterMatcher>>> findCache = new ThreadLocal<Map<Class<?>, Map<String, TypeParameterMatcher>>>()
/*     */     {
/*     */       protected Map<Class<?>, Map<String, TypeParameterMatcher>> initialValue()
/*     */       {
/*  75 */         return new IdentityHashMap<Class<?>, Map<String, TypeParameterMatcher>>();
/*     */       }
/*     */     };
/*     */ 
/*     */ 
/*     */   
/*     */   public static TypeParameterMatcher find(Object object, Class<?> parameterizedSuperclass, String typeParamName) {
/*  82 */     Map<Class<?>, Map<String, TypeParameterMatcher>> findCache = TypeParameterMatcher.findCache.get();
/*  83 */     Class<?> thisClass = object.getClass();
/*     */     
/*  85 */     Map<String, TypeParameterMatcher> map = findCache.get(thisClass);
/*  86 */     if (map == null) {
/*  87 */       map = new HashMap<String, TypeParameterMatcher>();
/*  88 */       findCache.put(thisClass, map);
/*     */     } 
/*     */     
/*  91 */     TypeParameterMatcher matcher = map.get(typeParamName);
/*  92 */     if (matcher == null) {
/*  93 */       matcher = get(find0(object, parameterizedSuperclass, typeParamName));
/*  94 */       map.put(typeParamName, matcher);
/*     */     } 
/*     */     
/*  97 */     return matcher;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static Class<?> find0(Object object, Class<?> parameterizedSuperclass, String typeParamName) {
/* 103 */     Class<?> thisClass = object.getClass();
/* 104 */     Class<?> currentClass = thisClass;
/*     */     while (true) {
/* 106 */       while (currentClass.getSuperclass() == parameterizedSuperclass) {
/* 107 */         int typeParamIndex = -1;
/* 108 */         TypeVariable[] arrayOfTypeVariable = currentClass.getSuperclass().getTypeParameters();
/* 109 */         for (int i = 0; i < arrayOfTypeVariable.length; i++) {
/* 110 */           if (typeParamName.equals(arrayOfTypeVariable[i].getName())) {
/* 111 */             typeParamIndex = i;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/* 116 */         if (typeParamIndex < 0) {
/* 117 */           throw new IllegalStateException("unknown type parameter '" + typeParamName + "': " + parameterizedSuperclass);
/*     */         }
/*     */ 
/*     */         
/* 121 */         Type genericSuperType = currentClass.getGenericSuperclass();
/* 122 */         if (!(genericSuperType instanceof ParameterizedType)) {
/* 123 */           return Object.class;
/*     */         }
/*     */         
/* 126 */         Type[] actualTypeParams = ((ParameterizedType)genericSuperType).getActualTypeArguments();
/*     */         
/* 128 */         Type actualTypeParam = actualTypeParams[typeParamIndex];
/* 129 */         if (actualTypeParam instanceof ParameterizedType) {
/* 130 */           actualTypeParam = ((ParameterizedType)actualTypeParam).getRawType();
/*     */         }
/* 132 */         if (actualTypeParam instanceof Class) {
/* 133 */           return (Class)actualTypeParam;
/*     */         }
/* 135 */         if (actualTypeParam instanceof GenericArrayType) {
/* 136 */           Type componentType = ((GenericArrayType)actualTypeParam).getGenericComponentType();
/* 137 */           if (componentType instanceof ParameterizedType) {
/* 138 */             componentType = ((ParameterizedType)componentType).getRawType();
/*     */           }
/* 140 */           if (componentType instanceof Class) {
/* 141 */             return Array.newInstance((Class)componentType, 0).getClass();
/*     */           }
/*     */         } 
/* 144 */         if (actualTypeParam instanceof TypeVariable) {
/*     */           
/* 146 */           TypeVariable<?> v = (TypeVariable)actualTypeParam;
/* 147 */           currentClass = thisClass;
/* 148 */           if (!(v.getGenericDeclaration() instanceof Class)) {
/* 149 */             return Object.class;
/*     */           }
/*     */           
/* 152 */           parameterizedSuperclass = (Class)v.getGenericDeclaration();
/* 153 */           typeParamName = v.getName();
/* 154 */           if (parameterizedSuperclass.isAssignableFrom(thisClass)) {
/*     */             continue;
/*     */           }
/* 157 */           return Object.class;
/*     */         } 
/*     */ 
/*     */         
/* 161 */         return fail(thisClass, typeParamName);
/*     */       } 
/* 163 */       currentClass = currentClass.getSuperclass();
/* 164 */       if (currentClass == null) {
/* 165 */         return fail(thisClass, typeParamName);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Class<?> fail(Class<?> type, String typeParamName) {
/* 171 */     throw new IllegalStateException("cannot determine the type of the type parameter '" + typeParamName + "': " + type);
/*     */   }
/*     */   
/*     */   public abstract boolean match(Object paramObject);
/*     */   
/*     */   private static final class ReflectiveMatcher
/*     */     extends TypeParameterMatcher {
/*     */     private final Class<?> type;
/*     */     
/*     */     ReflectiveMatcher(Class<?> type) {
/* 181 */       this.type = type;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean match(Object msg) {
/* 186 */       return this.type.isInstance(msg);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\io\nett\\util\internal\TypeParameterMatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */