/*     */ package net.minecraft.util.org.apache.commons.lang3.builder;
/*     */ 
/*     */ import java.lang.reflect.AccessibleObject;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import net.minecraft.util.org.apache.commons.lang3.ArrayUtils;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HashCodeBuilder
/*     */   implements Builder<Integer>
/*     */ {
/* 108 */   private static final ThreadLocal<Set<IDKey>> REGISTRY = new ThreadLocal<Set<IDKey>>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final int iConstant;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Set<IDKey> getRegistry() {
/* 136 */     return REGISTRY.get();
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
/*     */   static boolean isRegistered(Object value) {
/* 151 */     Set<IDKey> registry = getRegistry();
/* 152 */     return (registry != null && registry.contains(new IDKey(value)));
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
/*     */   private static void reflectionAppend(Object object, Class<?> clazz, HashCodeBuilder builder, boolean useTransients, String[] excludeFields) {
/* 173 */     if (isRegistered(object)) {
/*     */       return;
/*     */     }
/*     */     try {
/* 177 */       register(object);
/* 178 */       Field[] fields = clazz.getDeclaredFields();
/* 179 */       AccessibleObject.setAccessible((AccessibleObject[])fields, true);
/* 180 */       for (Field field : fields) {
/* 181 */         if (!ArrayUtils.contains((Object[])excludeFields, field.getName()) && field.getName().indexOf('$') == -1 && (useTransients || !Modifier.isTransient(field.getModifiers())) && !Modifier.isStatic(field.getModifiers())) {
/*     */           
/*     */           try {
/*     */ 
/*     */             
/* 186 */             Object fieldValue = field.get(object);
/* 187 */             builder.append(fieldValue);
/* 188 */           } catch (IllegalAccessException e) {
/*     */ 
/*     */             
/* 191 */             throw new InternalError("Unexpected IllegalAccessException");
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } finally {
/* 196 */       unregister(object);
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
/*     */   public static int reflectionHashCode(int initialNonZeroOddNumber, int multiplierNonZeroOddNumber, Object object) {
/* 238 */     return reflectionHashCode(initialNonZeroOddNumber, multiplierNonZeroOddNumber, object, false, null, new String[0]);
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
/*     */ 
/*     */   
/*     */   public static int reflectionHashCode(int initialNonZeroOddNumber, int multiplierNonZeroOddNumber, Object object, boolean testTransients) {
/* 282 */     return reflectionHashCode(initialNonZeroOddNumber, multiplierNonZeroOddNumber, object, testTransients, null, new String[0]);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static <T> int reflectionHashCode(int initialNonZeroOddNumber, int multiplierNonZeroOddNumber, T object, boolean testTransients, Class<? super T> reflectUpToClass, String... excludeFields) {
/* 335 */     if (object == null) {
/* 336 */       throw new IllegalArgumentException("The object to build a hash code for must not be null");
/*     */     }
/* 338 */     HashCodeBuilder builder = new HashCodeBuilder(initialNonZeroOddNumber, multiplierNonZeroOddNumber);
/* 339 */     Class<?> clazz = object.getClass();
/* 340 */     reflectionAppend(object, clazz, builder, testTransients, excludeFields);
/* 341 */     while (clazz.getSuperclass() != null && clazz != reflectUpToClass) {
/* 342 */       clazz = clazz.getSuperclass();
/* 343 */       reflectionAppend(object, clazz, builder, testTransients, excludeFields);
/*     */     } 
/* 345 */     return builder.toHashCode();
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
/*     */   public static int reflectionHashCode(Object object, boolean testTransients) {
/* 381 */     return reflectionHashCode(17, 37, object, testTransients, null, new String[0]);
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
/*     */   public static int reflectionHashCode(Object object, Collection<String> excludeFields) {
/* 417 */     return reflectionHashCode(object, ReflectionToStringBuilder.toNoNullStringArray(excludeFields));
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
/*     */   public static int reflectionHashCode(Object object, String... excludeFields) {
/* 455 */     return reflectionHashCode(17, 37, object, false, null, excludeFields);
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
/*     */   static void register(Object value) {
/* 467 */     synchronized (HashCodeBuilder.class) {
/* 468 */       if (getRegistry() == null) {
/* 469 */         REGISTRY.set(new HashSet<IDKey>());
/*     */       }
/*     */     } 
/* 472 */     getRegistry().add(new IDKey(value));
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
/*     */   static void unregister(Object value) {
/* 488 */     Set<IDKey> registry = getRegistry();
/* 489 */     if (registry != null) {
/* 490 */       registry.remove(new IDKey(value));
/* 491 */       synchronized (HashCodeBuilder.class) {
/*     */         
/* 493 */         registry = getRegistry();
/* 494 */         if (registry != null && registry.isEmpty()) {
/* 495 */           REGISTRY.remove();
/*     */         }
/*     */       } 
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
/* 509 */   private int iTotal = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public HashCodeBuilder() {
/* 517 */     this.iConstant = 37;
/* 518 */     this.iTotal = 17;
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
/*     */   public HashCodeBuilder(int initialOddNumber, int multiplierOddNumber) {
/* 539 */     if (initialOddNumber % 2 == 0) {
/* 540 */       throw new IllegalArgumentException("HashCodeBuilder requires an odd initial value");
/*     */     }
/* 542 */     if (multiplierOddNumber % 2 == 0) {
/* 543 */       throw new IllegalArgumentException("HashCodeBuilder requires an odd multiplier");
/*     */     }
/* 545 */     this.iConstant = multiplierOddNumber;
/* 546 */     this.iTotal = initialOddNumber;
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
/*     */   public HashCodeBuilder append(boolean value) {
/* 571 */     this.iTotal = this.iTotal * this.iConstant + (value ? 0 : 1);
/* 572 */     return this;
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
/*     */   public HashCodeBuilder append(boolean[] array) {
/* 585 */     if (array == null) {
/* 586 */       this.iTotal *= this.iConstant;
/*     */     } else {
/* 588 */       for (boolean element : array) {
/* 589 */         append(element);
/*     */       }
/*     */     } 
/* 592 */     return this;
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
/*     */   public HashCodeBuilder append(byte value) {
/* 607 */     this.iTotal = this.iTotal * this.iConstant + value;
/* 608 */     return this;
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
/*     */   public HashCodeBuilder append(byte[] array) {
/* 623 */     if (array == null) {
/* 624 */       this.iTotal *= this.iConstant;
/*     */     } else {
/* 626 */       for (byte element : array) {
/* 627 */         append(element);
/*     */       }
/*     */     } 
/* 630 */     return this;
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
/*     */   public HashCodeBuilder append(char value) {
/* 643 */     this.iTotal = this.iTotal * this.iConstant + value;
/* 644 */     return this;
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
/*     */   public HashCodeBuilder append(char[] array) {
/* 657 */     if (array == null) {
/* 658 */       this.iTotal *= this.iConstant;
/*     */     } else {
/* 660 */       for (char element : array) {
/* 661 */         append(element);
/*     */       }
/*     */     } 
/* 664 */     return this;
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
/*     */   public HashCodeBuilder append(double value) {
/* 677 */     return append(Double.doubleToLongBits(value));
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
/*     */   public HashCodeBuilder append(double[] array) {
/* 690 */     if (array == null) {
/* 691 */       this.iTotal *= this.iConstant;
/*     */     } else {
/* 693 */       for (double element : array) {
/* 694 */         append(element);
/*     */       }
/*     */     } 
/* 697 */     return this;
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
/*     */   public HashCodeBuilder append(float value) {
/* 710 */     this.iTotal = this.iTotal * this.iConstant + Float.floatToIntBits(value);
/* 711 */     return this;
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
/*     */   public HashCodeBuilder append(float[] array) {
/* 724 */     if (array == null) {
/* 725 */       this.iTotal *= this.iConstant;
/*     */     } else {
/* 727 */       for (float element : array) {
/* 728 */         append(element);
/*     */       }
/*     */     } 
/* 731 */     return this;
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
/*     */   public HashCodeBuilder append(int value) {
/* 744 */     this.iTotal = this.iTotal * this.iConstant + value;
/* 745 */     return this;
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
/*     */   public HashCodeBuilder append(int[] array) {
/* 758 */     if (array == null) {
/* 759 */       this.iTotal *= this.iConstant;
/*     */     } else {
/* 761 */       for (int element : array) {
/* 762 */         append(element);
/*     */       }
/*     */     } 
/* 765 */     return this;
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
/*     */   public HashCodeBuilder append(long value) {
/* 782 */     this.iTotal = this.iTotal * this.iConstant + (int)(value ^ value >> 32L);
/* 783 */     return this;
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
/*     */   public HashCodeBuilder append(long[] array) {
/* 796 */     if (array == null) {
/* 797 */       this.iTotal *= this.iConstant;
/*     */     } else {
/* 799 */       for (long element : array) {
/* 800 */         append(element);
/*     */       }
/*     */     } 
/* 803 */     return this;
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
/*     */   public HashCodeBuilder append(Object object) {
/* 816 */     if (object == null) {
/* 817 */       this.iTotal *= this.iConstant;
/*     */     
/*     */     }
/* 820 */     else if (object.getClass().isArray()) {
/*     */ 
/*     */       
/* 823 */       if (object instanceof long[]) {
/* 824 */         append((long[])object);
/* 825 */       } else if (object instanceof int[]) {
/* 826 */         append((int[])object);
/* 827 */       } else if (object instanceof short[]) {
/* 828 */         append((short[])object);
/* 829 */       } else if (object instanceof char[]) {
/* 830 */         append((char[])object);
/* 831 */       } else if (object instanceof byte[]) {
/* 832 */         append((byte[])object);
/* 833 */       } else if (object instanceof double[]) {
/* 834 */         append((double[])object);
/* 835 */       } else if (object instanceof float[]) {
/* 836 */         append((float[])object);
/* 837 */       } else if (object instanceof boolean[]) {
/* 838 */         append((boolean[])object);
/*     */       } else {
/*     */         
/* 841 */         append((Object[])object);
/*     */       } 
/*     */     } else {
/* 844 */       this.iTotal = this.iTotal * this.iConstant + object.hashCode();
/*     */     } 
/*     */     
/* 847 */     return this;
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
/*     */   public HashCodeBuilder append(Object[] array) {
/* 860 */     if (array == null) {
/* 861 */       this.iTotal *= this.iConstant;
/*     */     } else {
/* 863 */       for (Object element : array) {
/* 864 */         append(element);
/*     */       }
/*     */     } 
/* 867 */     return this;
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
/*     */   public HashCodeBuilder append(short value) {
/* 880 */     this.iTotal = this.iTotal * this.iConstant + value;
/* 881 */     return this;
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
/*     */   public HashCodeBuilder append(short[] array) {
/* 894 */     if (array == null) {
/* 895 */       this.iTotal *= this.iConstant;
/*     */     } else {
/* 897 */       for (short element : array) {
/* 898 */         append(element);
/*     */       }
/*     */     } 
/* 901 */     return this;
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
/*     */   public HashCodeBuilder appendSuper(int superHashCode) {
/* 915 */     this.iTotal = this.iTotal * this.iConstant + superHashCode;
/* 916 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int toHashCode() {
/* 927 */     return this.iTotal;
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
/*     */   public Integer build() {
/* 939 */     return Integer.valueOf(toHashCode());
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
/*     */   public int hashCode() {
/* 953 */     return toHashCode();
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\org\apache\commons\lang3\builder\HashCodeBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */