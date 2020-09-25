/*     */ package org.bukkit.craftbukkit.libs.com.google.gson.internal;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Type;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.Map;
/*     */ import java.util.Queue;
/*     */ import java.util.Set;
/*     */ import java.util.SortedSet;
/*     */ import java.util.TreeSet;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.InstanceCreator;
/*     */ import org.bukkit.craftbukkit.libs.com.google.gson.reflect.TypeToken;
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
/*     */ 
/*     */ 
/*     */ public final class ConstructorConstructor
/*     */ {
/*     */   private final Map<Type, InstanceCreator<?>> instanceCreators;
/*     */   
/*     */   public ConstructorConstructor(Map<Type, InstanceCreator<?>> instanceCreators) {
/*  43 */     this.instanceCreators = instanceCreators;
/*     */   }
/*     */   
/*     */   public ConstructorConstructor() {
/*  47 */     this(Collections.emptyMap());
/*     */   }
/*     */   
/*     */   public <T> ObjectConstructor<T> getConstructor(TypeToken<T> typeToken) {
/*  51 */     final Type type = typeToken.getType();
/*  52 */     Class<? super T> rawType = typeToken.getRawType();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  57 */     final InstanceCreator<T> creator = (InstanceCreator<T>)this.instanceCreators.get(type);
/*  58 */     if (creator != null) {
/*  59 */       return new ObjectConstructor<T>() {
/*     */           public T construct() {
/*  61 */             return (T)creator.createInstance(type);
/*     */           }
/*     */         };
/*     */     }
/*     */     
/*  66 */     ObjectConstructor<T> defaultConstructor = newDefaultConstructor(rawType);
/*  67 */     if (defaultConstructor != null) {
/*  68 */       return defaultConstructor;
/*     */     }
/*     */     
/*  71 */     ObjectConstructor<T> defaultImplementation = newDefaultImplementationConstructor(rawType);
/*  72 */     if (defaultImplementation != null) {
/*  73 */       return defaultImplementation;
/*     */     }
/*     */ 
/*     */     
/*  77 */     return newUnsafeAllocator(type, rawType);
/*     */   }
/*     */   
/*     */   private <T> ObjectConstructor<T> newDefaultConstructor(Class<? super T> rawType) {
/*     */     try {
/*  82 */       final Constructor<? super T> constructor = rawType.getDeclaredConstructor(new Class[0]);
/*  83 */       if (!constructor.isAccessible()) {
/*  84 */         constructor.setAccessible(true);
/*     */       }
/*  86 */       return new ObjectConstructor<T>()
/*     */         {
/*     */           public T construct() {
/*     */             try {
/*  90 */               Object[] args = null;
/*  91 */               return constructor.newInstance(args);
/*  92 */             } catch (InstantiationException e) {
/*     */               
/*  94 */               throw new RuntimeException("Failed to invoke " + constructor + " with no args", e);
/*  95 */             } catch (InvocationTargetException e) {
/*     */ 
/*     */               
/*  98 */               throw new RuntimeException("Failed to invoke " + constructor + " with no args", e.getTargetException());
/*     */             }
/* 100 */             catch (IllegalAccessException e) {
/* 101 */               throw new AssertionError(e);
/*     */             } 
/*     */           }
/*     */         };
/* 105 */     } catch (NoSuchMethodException e) {
/* 106 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private <T> ObjectConstructor<T> newDefaultImplementationConstructor(Class<? super T> rawType) {
/* 116 */     if (Collection.class.isAssignableFrom(rawType)) {
/* 117 */       if (SortedSet.class.isAssignableFrom(rawType))
/* 118 */         return new ObjectConstructor<T>() {
/*     */             public T construct() {
/* 120 */               return (T)new TreeSet();
/*     */             }
/*     */           }; 
/* 123 */       if (Set.class.isAssignableFrom(rawType))
/* 124 */         return new ObjectConstructor<T>() {
/*     */             public T construct() {
/* 126 */               return (T)new LinkedHashSet();
/*     */             }
/*     */           }; 
/* 129 */       if (Queue.class.isAssignableFrom(rawType)) {
/* 130 */         return new ObjectConstructor<T>() {
/*     */             public T construct() {
/* 132 */               return (T)new LinkedList();
/*     */             }
/*     */           };
/*     */       }
/* 136 */       return new ObjectConstructor<T>() {
/*     */           public T construct() {
/* 138 */             return (T)new ArrayList();
/*     */           }
/*     */         };
/*     */     } 
/*     */ 
/*     */     
/* 144 */     if (Map.class.isAssignableFrom(rawType)) {
/* 145 */       return new ObjectConstructor<T>() {
/*     */           public T construct() {
/* 147 */             return (T)new LinkedHashMap<Object, Object>();
/*     */           }
/*     */         };
/*     */     }
/*     */ 
/*     */     
/* 153 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   private <T> ObjectConstructor<T> newUnsafeAllocator(final Type type, final Class<? super T> rawType) {
/* 158 */     return new ObjectConstructor<T>() {
/* 159 */         private final UnsafeAllocator unsafeAllocator = UnsafeAllocator.create();
/*     */         
/*     */         public T construct() {
/*     */           try {
/* 163 */             Object newInstance = this.unsafeAllocator.newInstance(rawType);
/* 164 */             return (T)newInstance;
/* 165 */           } catch (Exception e) {
/* 166 */             throw new RuntimeException("Unable to invoke no-args constructor for " + type + ". " + "Register an InstanceCreator with Gson for this type may fix this problem.", e);
/*     */           } 
/*     */         }
/*     */       };
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 174 */     return this.instanceCreators.toString();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\bukkit\craftbukkit\libs\com\google\gson\internal\ConstructorConstructor.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */