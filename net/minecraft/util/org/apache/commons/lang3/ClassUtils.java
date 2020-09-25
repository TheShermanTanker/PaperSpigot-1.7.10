/*      */ package net.minecraft.util.org.apache.commons.lang3;
/*      */ 
/*      */ import java.lang.reflect.Method;
/*      */ import java.lang.reflect.Modifier;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import net.minecraft.util.org.apache.commons.lang3.mutable.MutableObject;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ClassUtils
/*      */ {
/*      */   public static final char PACKAGE_SEPARATOR_CHAR = '.';
/*      */   
/*      */   public enum Interfaces
/*      */   {
/*   53 */     INCLUDE, EXCLUDE;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   64 */   public static final String PACKAGE_SEPARATOR = String.valueOf('.');
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static final char INNER_CLASS_SEPARATOR_CHAR = '$';
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   74 */   public static final String INNER_CLASS_SEPARATOR = String.valueOf('$');
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   79 */   private static final Map<Class<?>, Class<?>> primitiveWrapperMap = new HashMap<Class<?>, Class<?>>();
/*      */   static {
/*   81 */     primitiveWrapperMap.put(boolean.class, Boolean.class);
/*   82 */     primitiveWrapperMap.put(byte.class, Byte.class);
/*   83 */     primitiveWrapperMap.put(char.class, Character.class);
/*   84 */     primitiveWrapperMap.put(short.class, Short.class);
/*   85 */     primitiveWrapperMap.put(int.class, Integer.class);
/*   86 */     primitiveWrapperMap.put(long.class, Long.class);
/*   87 */     primitiveWrapperMap.put(double.class, Double.class);
/*   88 */     primitiveWrapperMap.put(float.class, Float.class);
/*   89 */     primitiveWrapperMap.put(void.class, void.class);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   95 */   private static final Map<Class<?>, Class<?>> wrapperPrimitiveMap = new HashMap<Class<?>, Class<?>>();
/*      */   static {
/*   97 */     for (Class<?> primitiveClass : primitiveWrapperMap.keySet()) {
/*   98 */       Class<?> wrapperClass = primitiveWrapperMap.get(primitiveClass);
/*   99 */       if (!primitiveClass.equals(wrapperClass)) {
/*  100 */         wrapperPrimitiveMap.put(wrapperClass, primitiveClass);
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  119 */     Map<String, String> m = new HashMap<String, String>();
/*  120 */     m.put("int", "I");
/*  121 */     m.put("boolean", "Z");
/*  122 */     m.put("float", "F");
/*  123 */     m.put("long", "J");
/*  124 */     m.put("short", "S");
/*  125 */     m.put("byte", "B");
/*  126 */     m.put("double", "D");
/*  127 */     m.put("char", "C");
/*  128 */     m.put("void", "V");
/*  129 */     Map<String, String> r = new HashMap<String, String>();
/*  130 */     for (Map.Entry<String, String> e : m.entrySet()) {
/*  131 */       r.put(e.getValue(), e.getKey());
/*      */     }
/*  133 */     abbreviationMap = Collections.unmodifiableMap(m);
/*  134 */     reverseAbbreviationMap = Collections.unmodifiableMap(r);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final Map<String, String> abbreviationMap;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final Map<String, String> reverseAbbreviationMap;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getShortClassName(Object object, String valueIfNull) {
/*  159 */     if (object == null) {
/*  160 */       return valueIfNull;
/*      */     }
/*  162 */     return getShortClassName(object.getClass());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getShortClassName(Class<?> cls) {
/*  176 */     if (cls == null) {
/*  177 */       return "";
/*      */     }
/*  179 */     return getShortClassName(cls.getName());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getShortClassName(String className) {
/*  195 */     if (StringUtils.isEmpty(className)) {
/*  196 */       return "";
/*      */     }
/*      */     
/*  199 */     StringBuilder arrayPrefix = new StringBuilder();
/*      */ 
/*      */     
/*  202 */     if (className.startsWith("[")) {
/*  203 */       while (className.charAt(0) == '[') {
/*  204 */         className = className.substring(1);
/*  205 */         arrayPrefix.append("[]");
/*      */       } 
/*      */       
/*  208 */       if (className.charAt(0) == 'L' && className.charAt(className.length() - 1) == ';') {
/*  209 */         className = className.substring(1, className.length() - 1);
/*      */       }
/*      */       
/*  212 */       if (reverseAbbreviationMap.containsKey(className)) {
/*  213 */         className = reverseAbbreviationMap.get(className);
/*      */       }
/*      */     } 
/*      */     
/*  217 */     int lastDotIdx = className.lastIndexOf('.');
/*  218 */     int innerIdx = className.indexOf('$', (lastDotIdx == -1) ? 0 : (lastDotIdx + 1));
/*      */     
/*  220 */     String out = className.substring(lastDotIdx + 1);
/*  221 */     if (innerIdx != -1) {
/*  222 */       out = out.replace('$', '.');
/*      */     }
/*  224 */     return out + arrayPrefix;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getSimpleName(Class<?> cls) {
/*  236 */     if (cls == null) {
/*  237 */       return "";
/*      */     }
/*  239 */     return cls.getSimpleName();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getSimpleName(Object object, String valueIfNull) {
/*  252 */     if (object == null) {
/*  253 */       return valueIfNull;
/*      */     }
/*  255 */     return getSimpleName(object.getClass());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getPackageName(Object object, String valueIfNull) {
/*  268 */     if (object == null) {
/*  269 */       return valueIfNull;
/*      */     }
/*  271 */     return getPackageName(object.getClass());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getPackageName(Class<?> cls) {
/*  281 */     if (cls == null) {
/*  282 */       return "";
/*      */     }
/*  284 */     return getPackageName(cls.getName());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getPackageName(String className) {
/*  297 */     if (StringUtils.isEmpty(className)) {
/*  298 */       return "";
/*      */     }
/*      */ 
/*      */     
/*  302 */     while (className.charAt(0) == '[') {
/*  303 */       className = className.substring(1);
/*      */     }
/*      */     
/*  306 */     if (className.charAt(0) == 'L' && className.charAt(className.length() - 1) == ';') {
/*  307 */       className = className.substring(1);
/*      */     }
/*      */     
/*  310 */     int i = className.lastIndexOf('.');
/*  311 */     if (i == -1) {
/*  312 */       return "";
/*      */     }
/*  314 */     return className.substring(0, i);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static List<Class<?>> getAllSuperclasses(Class<?> cls) {
/*  327 */     if (cls == null) {
/*  328 */       return null;
/*      */     }
/*  330 */     List<Class<?>> classes = new ArrayList<Class<?>>();
/*  331 */     Class<?> superclass = cls.getSuperclass();
/*  332 */     while (superclass != null) {
/*  333 */       classes.add(superclass);
/*  334 */       superclass = superclass.getSuperclass();
/*      */     } 
/*  336 */     return classes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static List<Class<?>> getAllInterfaces(Class<?> cls) {
/*  353 */     if (cls == null) {
/*  354 */       return null;
/*      */     }
/*      */     
/*  357 */     LinkedHashSet<Class<?>> interfacesFound = new LinkedHashSet<Class<?>>();
/*  358 */     getAllInterfaces(cls, interfacesFound);
/*      */     
/*  360 */     return new ArrayList<Class<?>>(interfacesFound);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void getAllInterfaces(Class<?> cls, HashSet<Class<?>> interfacesFound) {
/*  370 */     while (cls != null) {
/*  371 */       Class<?>[] interfaces = cls.getInterfaces();
/*      */       
/*  373 */       for (Class<?> i : interfaces) {
/*  374 */         if (interfacesFound.add(i)) {
/*  375 */           getAllInterfaces(i, interfacesFound);
/*      */         }
/*      */       } 
/*      */       
/*  379 */       cls = cls.getSuperclass();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static List<Class<?>> convertClassNamesToClasses(List<String> classNames) {
/*  398 */     if (classNames == null) {
/*  399 */       return null;
/*      */     }
/*  401 */     List<Class<?>> classes = new ArrayList<Class<?>>(classNames.size());
/*  402 */     for (String className : classNames) {
/*      */       try {
/*  404 */         classes.add(Class.forName(className));
/*  405 */       } catch (Exception ex) {
/*  406 */         classes.add(null);
/*      */       } 
/*      */     } 
/*  409 */     return classes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static List<String> convertClassesToClassNames(List<Class<?>> classes) {
/*  425 */     if (classes == null) {
/*  426 */       return null;
/*      */     }
/*  428 */     List<String> classNames = new ArrayList<String>(classes.size());
/*  429 */     for (Class<?> cls : classes) {
/*  430 */       if (cls == null) {
/*  431 */         classNames.add(null); continue;
/*      */       } 
/*  433 */       classNames.add(cls.getName());
/*      */     } 
/*      */     
/*  436 */     return classNames;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isAssignable(Class<?>[] classArray, Class<?>... toClassArray) {
/*  478 */     return isAssignable(classArray, toClassArray, SystemUtils.isJavaVersionAtLeast(JavaVersion.JAVA_1_5));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isAssignable(Class<?>[] classArray, Class<?>[] toClassArray, boolean autoboxing) {
/*  514 */     if (!ArrayUtils.isSameLength((Object[])classArray, (Object[])toClassArray)) {
/*  515 */       return false;
/*      */     }
/*  517 */     if (classArray == null) {
/*  518 */       classArray = ArrayUtils.EMPTY_CLASS_ARRAY;
/*      */     }
/*  520 */     if (toClassArray == null) {
/*  521 */       toClassArray = ArrayUtils.EMPTY_CLASS_ARRAY;
/*      */     }
/*  523 */     for (int i = 0; i < classArray.length; i++) {
/*  524 */       if (!isAssignable(classArray[i], toClassArray[i], autoboxing)) {
/*  525 */         return false;
/*      */       }
/*      */     } 
/*  528 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isPrimitiveOrWrapper(Class<?> type) {
/*  542 */     if (type == null) {
/*  543 */       return false;
/*      */     }
/*  545 */     return (type.isPrimitive() || isPrimitiveWrapper(type));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isPrimitiveWrapper(Class<?> type) {
/*  559 */     return wrapperPrimitiveMap.containsKey(type);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isAssignable(Class<?> cls, Class<?> toClass) {
/*  594 */     return isAssignable(cls, toClass, SystemUtils.isJavaVersionAtLeast(JavaVersion.JAVA_1_5));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isAssignable(Class<?> cls, Class<?> toClass, boolean autoboxing) {
/*  625 */     if (toClass == null) {
/*  626 */       return false;
/*      */     }
/*      */     
/*  629 */     if (cls == null) {
/*  630 */       return !toClass.isPrimitive();
/*      */     }
/*      */     
/*  633 */     if (autoboxing) {
/*  634 */       if (cls.isPrimitive() && !toClass.isPrimitive()) {
/*  635 */         cls = primitiveToWrapper(cls);
/*  636 */         if (cls == null) {
/*  637 */           return false;
/*      */         }
/*      */       } 
/*  640 */       if (toClass.isPrimitive() && !cls.isPrimitive()) {
/*  641 */         cls = wrapperToPrimitive(cls);
/*  642 */         if (cls == null) {
/*  643 */           return false;
/*      */         }
/*      */       } 
/*      */     } 
/*  647 */     if (cls.equals(toClass)) {
/*  648 */       return true;
/*      */     }
/*  650 */     if (cls.isPrimitive()) {
/*  651 */       if (!toClass.isPrimitive()) {
/*  652 */         return false;
/*      */       }
/*  654 */       if (int.class.equals(cls)) {
/*  655 */         return (long.class.equals(toClass) || float.class.equals(toClass) || double.class.equals(toClass));
/*      */       }
/*      */ 
/*      */       
/*  659 */       if (long.class.equals(cls)) {
/*  660 */         return (float.class.equals(toClass) || double.class.equals(toClass));
/*      */       }
/*      */       
/*  663 */       if (boolean.class.equals(cls)) {
/*  664 */         return false;
/*      */       }
/*  666 */       if (double.class.equals(cls)) {
/*  667 */         return false;
/*      */       }
/*  669 */       if (float.class.equals(cls)) {
/*  670 */         return double.class.equals(toClass);
/*      */       }
/*  672 */       if (char.class.equals(cls)) {
/*  673 */         return (int.class.equals(toClass) || long.class.equals(toClass) || float.class.equals(toClass) || double.class.equals(toClass));
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  678 */       if (short.class.equals(cls)) {
/*  679 */         return (int.class.equals(toClass) || long.class.equals(toClass) || float.class.equals(toClass) || double.class.equals(toClass));
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  684 */       if (byte.class.equals(cls)) {
/*  685 */         return (short.class.equals(toClass) || int.class.equals(toClass) || long.class.equals(toClass) || float.class.equals(toClass) || double.class.equals(toClass));
/*      */       }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  692 */       return false;
/*      */     } 
/*  694 */     return toClass.isAssignableFrom(cls);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Class<?> primitiveToWrapper(Class<?> cls) {
/*  710 */     Class<?> convertedClass = cls;
/*  711 */     if (cls != null && cls.isPrimitive()) {
/*  712 */       convertedClass = primitiveWrapperMap.get(cls);
/*      */     }
/*  714 */     return convertedClass;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Class<?>[] primitivesToWrappers(Class<?>... classes) {
/*  728 */     if (classes == null) {
/*  729 */       return null;
/*      */     }
/*      */     
/*  732 */     if (classes.length == 0) {
/*  733 */       return classes;
/*      */     }
/*      */     
/*  736 */     Class<?>[] convertedClasses = new Class[classes.length];
/*  737 */     for (int i = 0; i < classes.length; i++) {
/*  738 */       convertedClasses[i] = primitiveToWrapper(classes[i]);
/*      */     }
/*  740 */     return convertedClasses;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Class<?> wrapperToPrimitive(Class<?> cls) {
/*  760 */     return wrapperPrimitiveMap.get(cls);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Class<?>[] wrappersToPrimitives(Class<?>... classes) {
/*  778 */     if (classes == null) {
/*  779 */       return null;
/*      */     }
/*      */     
/*  782 */     if (classes.length == 0) {
/*  783 */       return classes;
/*      */     }
/*      */     
/*  786 */     Class<?>[] convertedClasses = new Class[classes.length];
/*  787 */     for (int i = 0; i < classes.length; i++) {
/*  788 */       convertedClasses[i] = wrapperToPrimitive(classes[i]);
/*      */     }
/*  790 */     return convertedClasses;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isInnerClass(Class<?> cls) {
/*  803 */     return (cls != null && cls.getEnclosingClass() != null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Class<?> getClass(ClassLoader classLoader, String className, boolean initialize) throws ClassNotFoundException {
/*      */     try {
/*      */       Class<?> clazz;
/*  824 */       if (abbreviationMap.containsKey(className)) {
/*  825 */         String clsName = "[" + (String)abbreviationMap.get(className);
/*  826 */         clazz = Class.forName(clsName, initialize, classLoader).getComponentType();
/*      */       } else {
/*  828 */         clazz = Class.forName(toCanonicalName(className), initialize, classLoader);
/*      */       } 
/*  830 */       return clazz;
/*  831 */     } catch (ClassNotFoundException ex) {
/*      */       
/*  833 */       int lastDotIndex = className.lastIndexOf('.');
/*      */       
/*  835 */       if (lastDotIndex != -1) {
/*      */         try {
/*  837 */           return getClass(classLoader, className.substring(0, lastDotIndex) + '$' + className.substring(lastDotIndex + 1), initialize);
/*      */         
/*      */         }
/*  840 */         catch (ClassNotFoundException ex2) {}
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*  845 */       throw ex;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Class<?> getClass(ClassLoader classLoader, String className) throws ClassNotFoundException {
/*  862 */     return getClass(classLoader, className, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Class<?> getClass(String className) throws ClassNotFoundException {
/*  877 */     return getClass(className, true);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Class<?> getClass(String className, boolean initialize) throws ClassNotFoundException {
/*  892 */     ClassLoader contextCL = Thread.currentThread().getContextClassLoader();
/*  893 */     ClassLoader loader = (contextCL == null) ? ClassUtils.class.getClassLoader() : contextCL;
/*  894 */     return getClass(loader, className, initialize);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Method getPublicMethod(Class<?> cls, String methodName, Class<?>... parameterTypes) throws SecurityException, NoSuchMethodException {
/*  923 */     Method declaredMethod = cls.getMethod(methodName, parameterTypes);
/*  924 */     if (Modifier.isPublic(declaredMethod.getDeclaringClass().getModifiers())) {
/*  925 */       return declaredMethod;
/*      */     }
/*      */     
/*  928 */     List<Class<?>> candidateClasses = new ArrayList<Class<?>>();
/*  929 */     candidateClasses.addAll(getAllInterfaces(cls));
/*  930 */     candidateClasses.addAll(getAllSuperclasses(cls));
/*      */     
/*  932 */     for (Class<?> candidateClass : candidateClasses) {
/*  933 */       Method candidateMethod; if (!Modifier.isPublic(candidateClass.getModifiers())) {
/*      */         continue;
/*      */       }
/*      */       
/*      */       try {
/*  938 */         candidateMethod = candidateClass.getMethod(methodName, parameterTypes);
/*  939 */       } catch (NoSuchMethodException ex) {
/*      */         continue;
/*      */       } 
/*  942 */       if (Modifier.isPublic(candidateMethod.getDeclaringClass().getModifiers())) {
/*  943 */         return candidateMethod;
/*      */       }
/*      */     } 
/*      */     
/*  947 */     throw new NoSuchMethodException("Can't find a public method for " + methodName + " " + ArrayUtils.toString(parameterTypes));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String toCanonicalName(String className) {
/*  959 */     className = StringUtils.deleteWhitespace(className);
/*  960 */     if (className == null)
/*  961 */       throw new NullPointerException("className must not be null."); 
/*  962 */     if (className.endsWith("[]")) {
/*  963 */       StringBuilder classNameBuffer = new StringBuilder();
/*  964 */       while (className.endsWith("[]")) {
/*  965 */         className = className.substring(0, className.length() - 2);
/*  966 */         classNameBuffer.append("[");
/*      */       } 
/*  968 */       String abbreviation = abbreviationMap.get(className);
/*  969 */       if (abbreviation != null) {
/*  970 */         classNameBuffer.append(abbreviation);
/*      */       } else {
/*  972 */         classNameBuffer.append("L").append(className).append(";");
/*      */       } 
/*  974 */       className = classNameBuffer.toString();
/*      */     } 
/*  976 */     return className;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Class<?>[] toClass(Object... array) {
/*  990 */     if (array == null)
/*  991 */       return null; 
/*  992 */     if (array.length == 0) {
/*  993 */       return ArrayUtils.EMPTY_CLASS_ARRAY;
/*      */     }
/*  995 */     Class<?>[] classes = new Class[array.length];
/*  996 */     for (int i = 0; i < array.length; i++) {
/*  997 */       classes[i] = (array[i] == null) ? null : array[i].getClass();
/*      */     }
/*  999 */     return classes;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getShortCanonicalName(Object object, String valueIfNull) {
/* 1013 */     if (object == null) {
/* 1014 */       return valueIfNull;
/*      */     }
/* 1016 */     return getShortCanonicalName(object.getClass().getName());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getShortCanonicalName(Class<?> cls) {
/* 1027 */     if (cls == null) {
/* 1028 */       return "";
/*      */     }
/* 1030 */     return getShortCanonicalName(cls.getName());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getShortCanonicalName(String canonicalName) {
/* 1043 */     return getShortClassName(getCanonicalName(canonicalName));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getPackageCanonicalName(Object object, String valueIfNull) {
/* 1057 */     if (object == null) {
/* 1058 */       return valueIfNull;
/*      */     }
/* 1060 */     return getPackageCanonicalName(object.getClass().getName());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getPackageCanonicalName(Class<?> cls) {
/* 1071 */     if (cls == null) {
/* 1072 */       return "";
/*      */     }
/* 1074 */     return getPackageCanonicalName(cls.getName());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String getPackageCanonicalName(String canonicalName) {
/* 1088 */     return getPackageName(getCanonicalName(canonicalName));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String getCanonicalName(String className) {
/* 1108 */     className = StringUtils.deleteWhitespace(className);
/* 1109 */     if (className == null) {
/* 1110 */       return null;
/*      */     }
/* 1112 */     int dim = 0;
/* 1113 */     while (className.startsWith("[")) {
/* 1114 */       dim++;
/* 1115 */       className = className.substring(1);
/*      */     } 
/* 1117 */     if (dim < 1) {
/* 1118 */       return className;
/*      */     }
/* 1120 */     if (className.startsWith("L")) {
/* 1121 */       className = className.substring(1, className.endsWith(";") ? (className.length() - 1) : className.length());
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     }
/* 1127 */     else if (className.length() > 0) {
/* 1128 */       className = reverseAbbreviationMap.get(className.substring(0, 1));
/*      */     } 
/*      */     
/* 1131 */     StringBuilder canonicalClassNameBuffer = new StringBuilder(className);
/* 1132 */     for (int i = 0; i < dim; i++) {
/* 1133 */       canonicalClassNameBuffer.append("[]");
/*      */     }
/* 1135 */     return canonicalClassNameBuffer.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Iterable<Class<?>> hierarchy(Class<?> type) {
/* 1149 */     return hierarchy(type, Interfaces.EXCLUDE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Iterable<Class<?>> hierarchy(final Class<?> type, Interfaces interfacesBehavior) {
/* 1161 */     final Iterable<Class<?>> classes = new Iterable<Class<?>>()
/*      */       {
/*      */         public Iterator<Class<?>> iterator()
/*      */         {
/* 1165 */           final MutableObject<Class<?>> next = new MutableObject(type);
/* 1166 */           return new Iterator<Class<?>>()
/*      */             {
/*      */               public boolean hasNext()
/*      */               {
/* 1170 */                 return (next.getValue() != null);
/*      */               }
/*      */ 
/*      */               
/*      */               public Class<?> next() {
/* 1175 */                 Class<?> result = (Class)next.getValue();
/* 1176 */                 next.setValue(result.getSuperclass());
/* 1177 */                 return result;
/*      */               }
/*      */ 
/*      */               
/*      */               public void remove() {
/* 1182 */                 throw new UnsupportedOperationException();
/*      */               }
/*      */             };
/*      */         }
/*      */       };
/*      */ 
/*      */     
/* 1189 */     if (interfacesBehavior != Interfaces.INCLUDE) {
/* 1190 */       return classes;
/*      */     }
/* 1192 */     return new Iterable<Class<?>>()
/*      */       {
/*      */         public Iterator<Class<?>> iterator()
/*      */         {
/* 1196 */           final Set<Class<?>> seenInterfaces = new HashSet<Class<?>>();
/* 1197 */           final Iterator<Class<?>> wrapped = classes.iterator();
/*      */           
/* 1199 */           return new Iterator<Class<?>>() {
/* 1200 */               Iterator<Class<?>> interfaces = Collections.<Class<?>>emptySet().iterator();
/*      */ 
/*      */               
/*      */               public boolean hasNext() {
/* 1204 */                 return (this.interfaces.hasNext() || wrapped.hasNext());
/*      */               }
/*      */ 
/*      */               
/*      */               public Class<?> next() {
/* 1209 */                 if (this.interfaces.hasNext()) {
/* 1210 */                   Class<?> nextInterface = this.interfaces.next();
/* 1211 */                   seenInterfaces.add(nextInterface);
/* 1212 */                   return nextInterface;
/*      */                 } 
/* 1214 */                 Class<?> nextSuperclass = wrapped.next();
/* 1215 */                 Set<Class<?>> currentInterfaces = new LinkedHashSet<Class<?>>();
/* 1216 */                 walkInterfaces(currentInterfaces, nextSuperclass);
/* 1217 */                 this.interfaces = currentInterfaces.iterator();
/* 1218 */                 return nextSuperclass;
/*      */               }
/*      */               
/*      */               private void walkInterfaces(Set<Class<?>> addTo, Class<?> c) {
/* 1222 */                 for (Class<?> iface : c.getInterfaces()) {
/* 1223 */                   if (!seenInterfaces.contains(iface)) {
/* 1224 */                     addTo.add(iface);
/*      */                   }
/* 1226 */                   walkInterfaces(addTo, iface);
/*      */                 } 
/*      */               }
/*      */ 
/*      */               
/*      */               public void remove() {
/* 1232 */                 throw new UnsupportedOperationException();
/*      */               }
/*      */             };
/*      */         }
/*      */       };
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\org\apache\commons\lang3\ClassUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */