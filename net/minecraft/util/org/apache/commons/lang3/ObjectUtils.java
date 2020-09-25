/*     */ package net.minecraft.util.org.apache.commons.lang3;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Array;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.lang.reflect.Method;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.TreeSet;
/*     */ import net.minecraft.util.org.apache.commons.lang3.exception.CloneFailedException;
/*     */ import net.minecraft.util.org.apache.commons.lang3.mutable.MutableInt;
/*     */ import net.minecraft.util.org.apache.commons.lang3.text.StrBuilder;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ObjectUtils
/*     */ {
/*  63 */   public static final Null NULL = new Null();
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
/*     */   public static <T> T defaultIfNull(T object, T defaultValue) {
/*  96 */     return (object != null) ? object : defaultValue;
/*     */   }
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> T firstNonNull(T... values) {
/* 122 */     if (values != null) {
/* 123 */       for (T val : values) {
/* 124 */         if (val != null) {
/* 125 */           return val;
/*     */         }
/*     */       } 
/*     */     }
/* 129 */     return null;
/*     */   }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static boolean equals(Object object1, Object object2) {
/* 157 */     if (object1 == object2) {
/* 158 */       return true;
/*     */     }
/* 160 */     if (object1 == null || object2 == null) {
/* 161 */       return false;
/*     */     }
/* 163 */     return object1.equals(object2);
/*     */   }
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
/*     */   
/*     */   public static boolean notEqual(Object object1, Object object2) {
/* 186 */     return !equals(object1, object2);
/*     */   }
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
/*     */   @Deprecated
/*     */   public static int hashCode(Object obj) {
/* 207 */     return (obj == null) ? 0 : obj.hashCode();
/*     */   }
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static int hashCodeMulti(Object... objects) {
/* 234 */     int hash = 1;
/* 235 */     if (objects != null) {
/* 236 */       for (Object object : objects) {
/* 237 */         hash = hash * 31 + hashCode(object);
/*     */       }
/*     */     }
/* 240 */     return hash;
/*     */   }
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
/*     */   public static String identityToString(Object object) {
/* 262 */     if (object == null) {
/* 263 */       return null;
/*     */     }
/* 265 */     StringBuilder builder = new StringBuilder();
/* 266 */     identityToString(builder, object);
/* 267 */     return builder.toString();
/*     */   }
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
/*     */   public static void identityToString(Appendable appendable, Object object) throws IOException {
/* 287 */     if (object == null) {
/* 288 */       throw new NullPointerException("Cannot get the toString of a null identity");
/*     */     }
/* 290 */     appendable.append(object.getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(object)));
/*     */   }
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
/*     */   public static void identityToString(StrBuilder builder, Object object) {
/* 311 */     if (object == null) {
/* 312 */       throw new NullPointerException("Cannot get the toString of a null identity");
/*     */     }
/* 314 */     builder.append(object.getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(object)));
/*     */   }
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
/*     */   public static void identityToString(StringBuffer buffer, Object object) {
/* 335 */     if (object == null) {
/* 336 */       throw new NullPointerException("Cannot get the toString of a null identity");
/*     */     }
/* 338 */     buffer.append(object.getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(object)));
/*     */   }
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
/*     */   public static void identityToString(StringBuilder builder, Object object) {
/* 359 */     if (object == null) {
/* 360 */       throw new NullPointerException("Cannot get the toString of a null identity");
/*     */     }
/* 362 */     builder.append(object.getClass().getName()).append('@').append(Integer.toHexString(System.identityHashCode(object)));
/*     */   }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static String toString(Object obj) {
/* 391 */     return (obj == null) ? "" : obj.toString();
/*     */   }
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
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public static String toString(Object obj, String nullStr) {
/* 417 */     return (obj == null) ? nullStr : obj.toString();
/*     */   }
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
/*     */   public static <T extends Comparable<? super T>> T min(T... values) {
/* 436 */     T result = null;
/* 437 */     if (values != null) {
/* 438 */       for (T value : values) {
/* 439 */         if (compare(value, result, true) < 0) {
/* 440 */           result = value;
/*     */         }
/*     */       } 
/*     */     }
/* 444 */     return result;
/*     */   }
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
/*     */   public static <T extends Comparable<? super T>> T max(T... values) {
/* 461 */     T result = null;
/* 462 */     if (values != null) {
/* 463 */       for (T value : values) {
/* 464 */         if (compare(value, result, false) > 0) {
/* 465 */           result = value;
/*     */         }
/*     */       } 
/*     */     }
/* 469 */     return result;
/*     */   }
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
/*     */   public static <T extends Comparable<? super T>> int compare(T c1, T c2) {
/* 483 */     return compare(c1, c2, false);
/*     */   }
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
/*     */   public static <T extends Comparable<? super T>> int compare(T c1, T c2, boolean nullGreater) {
/* 500 */     if (c1 == c2)
/* 501 */       return 0; 
/* 502 */     if (c1 == null)
/* 503 */       return nullGreater ? 1 : -1; 
/* 504 */     if (c2 == null) {
/* 505 */       return nullGreater ? -1 : 1;
/*     */     }
/* 507 */     return c1.compareTo(c2);
/*     */   }
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
/*     */   public static <T extends Comparable<? super T>> T median(T... items) {
/* 521 */     Validate.notEmpty(items);
/* 522 */     Validate.noNullElements(items);
/* 523 */     TreeSet<T> sort = new TreeSet<T>();
/* 524 */     Collections.addAll(sort, items);
/*     */ 
/*     */     
/* 527 */     return (T)sort.toArray()[(sort.size() - 1) / 2];
/*     */   }
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
/*     */   public static <T> T median(Comparator<T> comparator, T... items) {
/* 543 */     Validate.notEmpty(items, "null/empty items", new Object[0]);
/* 544 */     Validate.noNullElements(items);
/* 545 */     Validate.notNull(comparator, "null comparator", new Object[0]);
/* 546 */     TreeSet<T> sort = new TreeSet<T>(comparator);
/* 547 */     Collections.addAll(sort, items);
/*     */ 
/*     */     
/* 550 */     T result = (T)sort.toArray()[(sort.size() - 1) / 2];
/* 551 */     return result;
/*     */   }
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
/*     */   public static <T> T mode(T... items) {
/* 565 */     if (ArrayUtils.isNotEmpty(items)) {
/* 566 */       HashMap<T, MutableInt> occurrences = new HashMap<T, MutableInt>(items.length);
/* 567 */       for (T t : items) {
/* 568 */         MutableInt count = occurrences.get(t);
/* 569 */         if (count == null) {
/* 570 */           occurrences.put(t, new MutableInt(1));
/*     */         } else {
/* 572 */           count.increment();
/*     */         } 
/*     */       } 
/* 575 */       T result = null;
/* 576 */       int max = 0;
/* 577 */       for (Map.Entry<T, MutableInt> e : occurrences.entrySet()) {
/* 578 */         int cmp = ((MutableInt)e.getValue()).intValue();
/* 579 */         if (cmp == max) {
/* 580 */           result = null; continue;
/* 581 */         }  if (cmp > max) {
/* 582 */           max = cmp;
/* 583 */           result = e.getKey();
/*     */         } 
/*     */       } 
/* 586 */       return result;
/*     */     } 
/* 588 */     return null;
/*     */   }
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
/*     */   public static <T> T clone(T obj) {
/* 603 */     if (obj instanceof Cloneable) {
/*     */       Object result;
/* 605 */       if (obj.getClass().isArray()) {
/* 606 */         Class<?> componentType = obj.getClass().getComponentType();
/* 607 */         if (!componentType.isPrimitive()) {
/* 608 */           result = ((Object[])obj).clone();
/*     */         } else {
/* 610 */           int length = Array.getLength(obj);
/* 611 */           result = Array.newInstance(componentType, length);
/* 612 */           while (length-- > 0) {
/* 613 */             Array.set(result, length, Array.get(obj, length));
/*     */           }
/*     */         } 
/*     */       } else {
/*     */         try {
/* 618 */           Method clone = obj.getClass().getMethod("clone", new Class[0]);
/* 619 */           result = clone.invoke(obj, new Object[0]);
/* 620 */         } catch (NoSuchMethodException e) {
/* 621 */           throw new CloneFailedException("Cloneable type " + obj.getClass().getName() + " has no clone method", e);
/*     */         
/*     */         }
/* 624 */         catch (IllegalAccessException e) {
/* 625 */           throw new CloneFailedException("Cannot clone Cloneable type " + obj.getClass().getName(), e);
/*     */         }
/* 627 */         catch (InvocationTargetException e) {
/* 628 */           throw new CloneFailedException("Exception cloning Cloneable type " + obj.getClass().getName(), e.getCause());
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 633 */       T checked = (T)result;
/* 634 */       return checked;
/*     */     } 
/*     */     
/* 637 */     return null;
/*     */   }
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
/*     */   public static <T> T cloneIfPossible(T obj) {
/* 657 */     T clone = clone(obj);
/* 658 */     return (clone == null) ? obj : clone;
/*     */   }
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
/*     */   public static class Null
/*     */     implements Serializable
/*     */   {
/*     */     private static final long serialVersionUID = 7092611880189329093L;
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
/*     */     private Object readResolve() {
/* 697 */       return ObjectUtils.NULL;
/*     */     }
/*     */   }
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
/*     */   
/*     */   public static boolean CONST(boolean v) {
/* 740 */     return v;
/*     */   }
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
/*     */   public static byte CONST(byte v) {
/* 759 */     return v;
/*     */   }
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
/*     */   
/*     */   public static byte CONST_BYTE(int v) throws IllegalArgumentException {
/* 782 */     if (v < -128 || v > 127) {
/* 783 */       throw new IllegalArgumentException("Supplied value must be a valid byte literal between -128 and 127: [" + v + "]");
/*     */     }
/* 785 */     return (byte)v;
/*     */   }
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
/*     */   public static char CONST(char v) {
/* 805 */     return v;
/*     */   }
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
/*     */   public static short CONST(short v) {
/* 824 */     return v;
/*     */   }
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
/*     */   
/*     */   public static short CONST_SHORT(int v) throws IllegalArgumentException {
/* 847 */     if (v < -32768 || v > 32767) {
/* 848 */       throw new IllegalArgumentException("Supplied value must be a valid byte literal between -32768 and 32767: [" + v + "]");
/*     */     }
/* 850 */     return (short)v;
/*     */   }
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
/*     */   public static int CONST(int v) {
/* 871 */     return v;
/*     */   }
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
/*     */   public static long CONST(long v) {
/* 890 */     return v;
/*     */   }
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
/*     */   public static float CONST(float v) {
/* 909 */     return v;
/*     */   }
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
/*     */   public static double CONST(double v) {
/* 928 */     return v;
/*     */   }
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
/*     */   public static <T> T CONST(T v) {
/* 948 */     return v;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\org\apache\commons\lang3\ObjectUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */