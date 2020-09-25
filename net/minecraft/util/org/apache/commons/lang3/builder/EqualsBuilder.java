/*     */ package net.minecraft.util.org.apache.commons.lang3.builder;
/*     */ 
/*     */ import java.lang.reflect.AccessibleObject;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.Collection;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import net.minecraft.util.org.apache.commons.lang3.ArrayUtils;
/*     */ import net.minecraft.util.org.apache.commons.lang3.tuple.Pair;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EqualsBuilder
/*     */   implements Builder<Boolean>
/*     */ {
/*  92 */   private static final ThreadLocal<Set<Pair<IDKey, IDKey>>> REGISTRY = new ThreadLocal<Set<Pair<IDKey, IDKey>>>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   static Set<Pair<IDKey, IDKey>> getRegistry() {
/* 121 */     return REGISTRY.get();
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
/*     */   static Pair<IDKey, IDKey> getRegisterPair(Object lhs, Object rhs) {
/* 135 */     IDKey left = new IDKey(lhs);
/* 136 */     IDKey right = new IDKey(rhs);
/* 137 */     return Pair.of(left, right);
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
/*     */   static boolean isRegistered(Object lhs, Object rhs) {
/* 154 */     Set<Pair<IDKey, IDKey>> registry = getRegistry();
/* 155 */     Pair<IDKey, IDKey> pair = getRegisterPair(lhs, rhs);
/* 156 */     Pair<IDKey, IDKey> swappedPair = Pair.of(pair.getLeft(), pair.getRight());
/*     */     
/* 158 */     return (registry != null && (registry.contains(pair) || registry.contains(swappedPair)));
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
/*     */   static void register(Object lhs, Object rhs) {
/* 172 */     synchronized (EqualsBuilder.class) {
/* 173 */       if (getRegistry() == null) {
/* 174 */         REGISTRY.set(new HashSet<Pair<IDKey, IDKey>>());
/*     */       }
/*     */     } 
/*     */     
/* 178 */     Set<Pair<IDKey, IDKey>> registry = getRegistry();
/* 179 */     Pair<IDKey, IDKey> pair = getRegisterPair(lhs, rhs);
/* 180 */     registry.add(pair);
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
/*     */   static void unregister(Object lhs, Object rhs) {
/* 196 */     Set<Pair<IDKey, IDKey>> registry = getRegistry();
/* 197 */     if (registry != null) {
/* 198 */       Pair<IDKey, IDKey> pair = getRegisterPair(lhs, rhs);
/* 199 */       registry.remove(pair);
/* 200 */       synchronized (EqualsBuilder.class) {
/*     */         
/* 202 */         registry = getRegistry();
/* 203 */         if (registry != null && registry.isEmpty()) {
/* 204 */           REGISTRY.remove();
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isEquals = true;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean reflectionEquals(Object lhs, Object rhs, Collection<String> excludeFields) {
/* 248 */     return reflectionEquals(lhs, rhs, ReflectionToStringBuilder.toNoNullStringArray(excludeFields));
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
/*     */   public static boolean reflectionEquals(Object lhs, Object rhs, String... excludeFields) {
/* 271 */     return reflectionEquals(lhs, rhs, false, null, excludeFields);
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
/*     */   public static boolean reflectionEquals(Object lhs, Object rhs, boolean testTransients) {
/* 295 */     return reflectionEquals(lhs, rhs, testTransients, null, new String[0]);
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
/*     */   public static boolean reflectionEquals(Object lhs, Object rhs, boolean testTransients, Class<?> reflectUpToClass, String... excludeFields) {
/*     */     Class<?> testClass;
/* 326 */     if (lhs == rhs) {
/* 327 */       return true;
/*     */     }
/* 329 */     if (lhs == null || rhs == null) {
/* 330 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 336 */     Class<?> lhsClass = lhs.getClass();
/* 337 */     Class<?> rhsClass = rhs.getClass();
/*     */     
/* 339 */     if (lhsClass.isInstance(rhs)) {
/* 340 */       testClass = lhsClass;
/* 341 */       if (!rhsClass.isInstance(lhs))
/*     */       {
/* 343 */         testClass = rhsClass;
/*     */       }
/* 345 */     } else if (rhsClass.isInstance(lhs)) {
/* 346 */       testClass = rhsClass;
/* 347 */       if (!lhsClass.isInstance(rhs))
/*     */       {
/* 349 */         testClass = lhsClass;
/*     */       }
/*     */     } else {
/*     */       
/* 353 */       return false;
/*     */     } 
/* 355 */     EqualsBuilder equalsBuilder = new EqualsBuilder();
/*     */     try {
/* 357 */       if (testClass.isArray()) {
/* 358 */         equalsBuilder.append(lhs, rhs);
/*     */       } else {
/* 360 */         reflectionAppend(lhs, rhs, testClass, equalsBuilder, testTransients, excludeFields);
/* 361 */         while (testClass.getSuperclass() != null && testClass != reflectUpToClass) {
/* 362 */           testClass = testClass.getSuperclass();
/* 363 */           reflectionAppend(lhs, rhs, testClass, equalsBuilder, testTransients, excludeFields);
/*     */         } 
/*     */       } 
/* 366 */     } catch (IllegalArgumentException e) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 372 */       return false;
/*     */     } 
/* 374 */     return equalsBuilder.isEquals();
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
/*     */   private static void reflectionAppend(Object lhs, Object rhs, Class<?> clazz, EqualsBuilder builder, boolean useTransients, String[] excludeFields) {
/* 396 */     if (isRegistered(lhs, rhs)) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/* 401 */       register(lhs, rhs);
/* 402 */       Field[] fields = clazz.getDeclaredFields();
/* 403 */       AccessibleObject.setAccessible((AccessibleObject[])fields, true);
/* 404 */       for (int i = 0; i < fields.length && builder.isEquals; i++) {
/* 405 */         Field f = fields[i];
/* 406 */         if (!ArrayUtils.contains((Object[])excludeFields, f.getName()) && f.getName().indexOf('$') == -1 && (useTransients || !Modifier.isTransient(f.getModifiers())) && !Modifier.isStatic(f.getModifiers())) {
/*     */           
/*     */           try {
/*     */ 
/*     */             
/* 411 */             builder.append(f.get(lhs), f.get(rhs));
/* 412 */           } catch (IllegalAccessException e) {
/*     */ 
/*     */             
/* 415 */             throw new InternalError("Unexpected IllegalAccessException");
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } finally {
/* 420 */       unregister(lhs, rhs);
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
/*     */   public EqualsBuilder appendSuper(boolean superEquals) {
/* 434 */     if (!this.isEquals) {
/* 435 */       return this;
/*     */     }
/* 437 */     this.isEquals = superEquals;
/* 438 */     return this;
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
/*     */   public EqualsBuilder append(Object lhs, Object rhs) {
/* 452 */     if (!this.isEquals) {
/* 453 */       return this;
/*     */     }
/* 455 */     if (lhs == rhs) {
/* 456 */       return this;
/*     */     }
/* 458 */     if (lhs == null || rhs == null) {
/* 459 */       setEquals(false);
/* 460 */       return this;
/*     */     } 
/* 462 */     Class<?> lhsClass = lhs.getClass();
/* 463 */     if (!lhsClass.isArray()) {
/*     */       
/* 465 */       this.isEquals = lhs.equals(rhs);
/* 466 */     } else if (lhs.getClass() != rhs.getClass()) {
/*     */       
/* 468 */       setEquals(false);
/*     */ 
/*     */     
/*     */     }
/* 472 */     else if (lhs instanceof long[]) {
/* 473 */       append((long[])lhs, (long[])rhs);
/* 474 */     } else if (lhs instanceof int[]) {
/* 475 */       append((int[])lhs, (int[])rhs);
/* 476 */     } else if (lhs instanceof short[]) {
/* 477 */       append((short[])lhs, (short[])rhs);
/* 478 */     } else if (lhs instanceof char[]) {
/* 479 */       append((char[])lhs, (char[])rhs);
/* 480 */     } else if (lhs instanceof byte[]) {
/* 481 */       append((byte[])lhs, (byte[])rhs);
/* 482 */     } else if (lhs instanceof double[]) {
/* 483 */       append((double[])lhs, (double[])rhs);
/* 484 */     } else if (lhs instanceof float[]) {
/* 485 */       append((float[])lhs, (float[])rhs);
/* 486 */     } else if (lhs instanceof boolean[]) {
/* 487 */       append((boolean[])lhs, (boolean[])rhs);
/*     */     } else {
/*     */       
/* 490 */       append((Object[])lhs, (Object[])rhs);
/*     */     } 
/* 492 */     return this;
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
/*     */   public EqualsBuilder append(long lhs, long rhs) {
/* 507 */     if (!this.isEquals) {
/* 508 */       return this;
/*     */     }
/* 510 */     this.isEquals = (lhs == rhs);
/* 511 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EqualsBuilder append(int lhs, int rhs) {
/* 522 */     if (!this.isEquals) {
/* 523 */       return this;
/*     */     }
/* 525 */     this.isEquals = (lhs == rhs);
/* 526 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EqualsBuilder append(short lhs, short rhs) {
/* 537 */     if (!this.isEquals) {
/* 538 */       return this;
/*     */     }
/* 540 */     this.isEquals = (lhs == rhs);
/* 541 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EqualsBuilder append(char lhs, char rhs) {
/* 552 */     if (!this.isEquals) {
/* 553 */       return this;
/*     */     }
/* 555 */     this.isEquals = (lhs == rhs);
/* 556 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EqualsBuilder append(byte lhs, byte rhs) {
/* 567 */     if (!this.isEquals) {
/* 568 */       return this;
/*     */     }
/* 570 */     this.isEquals = (lhs == rhs);
/* 571 */     return this;
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
/*     */   public EqualsBuilder append(double lhs, double rhs) {
/* 588 */     if (!this.isEquals) {
/* 589 */       return this;
/*     */     }
/* 591 */     return append(Double.doubleToLongBits(lhs), Double.doubleToLongBits(rhs));
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
/*     */   public EqualsBuilder append(float lhs, float rhs) {
/* 608 */     if (!this.isEquals) {
/* 609 */       return this;
/*     */     }
/* 611 */     return append(Float.floatToIntBits(lhs), Float.floatToIntBits(rhs));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public EqualsBuilder append(boolean lhs, boolean rhs) {
/* 622 */     if (!this.isEquals) {
/* 623 */       return this;
/*     */     }
/* 625 */     this.isEquals = (lhs == rhs);
/* 626 */     return this;
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
/*     */   public EqualsBuilder append(Object[] lhs, Object[] rhs) {
/* 640 */     if (!this.isEquals) {
/* 641 */       return this;
/*     */     }
/* 643 */     if (lhs == rhs) {
/* 644 */       return this;
/*     */     }
/* 646 */     if (lhs == null || rhs == null) {
/* 647 */       setEquals(false);
/* 648 */       return this;
/*     */     } 
/* 650 */     if (lhs.length != rhs.length) {
/* 651 */       setEquals(false);
/* 652 */       return this;
/*     */     } 
/* 654 */     for (int i = 0; i < lhs.length && this.isEquals; i++) {
/* 655 */       append(lhs[i], rhs[i]);
/*     */     }
/* 657 */     return this;
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
/*     */   public EqualsBuilder append(long[] lhs, long[] rhs) {
/* 671 */     if (!this.isEquals) {
/* 672 */       return this;
/*     */     }
/* 674 */     if (lhs == rhs) {
/* 675 */       return this;
/*     */     }
/* 677 */     if (lhs == null || rhs == null) {
/* 678 */       setEquals(false);
/* 679 */       return this;
/*     */     } 
/* 681 */     if (lhs.length != rhs.length) {
/* 682 */       setEquals(false);
/* 683 */       return this;
/*     */     } 
/* 685 */     for (int i = 0; i < lhs.length && this.isEquals; i++) {
/* 686 */       append(lhs[i], rhs[i]);
/*     */     }
/* 688 */     return this;
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
/*     */   public EqualsBuilder append(int[] lhs, int[] rhs) {
/* 702 */     if (!this.isEquals) {
/* 703 */       return this;
/*     */     }
/* 705 */     if (lhs == rhs) {
/* 706 */       return this;
/*     */     }
/* 708 */     if (lhs == null || rhs == null) {
/* 709 */       setEquals(false);
/* 710 */       return this;
/*     */     } 
/* 712 */     if (lhs.length != rhs.length) {
/* 713 */       setEquals(false);
/* 714 */       return this;
/*     */     } 
/* 716 */     for (int i = 0; i < lhs.length && this.isEquals; i++) {
/* 717 */       append(lhs[i], rhs[i]);
/*     */     }
/* 719 */     return this;
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
/*     */   public EqualsBuilder append(short[] lhs, short[] rhs) {
/* 733 */     if (!this.isEquals) {
/* 734 */       return this;
/*     */     }
/* 736 */     if (lhs == rhs) {
/* 737 */       return this;
/*     */     }
/* 739 */     if (lhs == null || rhs == null) {
/* 740 */       setEquals(false);
/* 741 */       return this;
/*     */     } 
/* 743 */     if (lhs.length != rhs.length) {
/* 744 */       setEquals(false);
/* 745 */       return this;
/*     */     } 
/* 747 */     for (int i = 0; i < lhs.length && this.isEquals; i++) {
/* 748 */       append(lhs[i], rhs[i]);
/*     */     }
/* 750 */     return this;
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
/*     */   public EqualsBuilder append(char[] lhs, char[] rhs) {
/* 764 */     if (!this.isEquals) {
/* 765 */       return this;
/*     */     }
/* 767 */     if (lhs == rhs) {
/* 768 */       return this;
/*     */     }
/* 770 */     if (lhs == null || rhs == null) {
/* 771 */       setEquals(false);
/* 772 */       return this;
/*     */     } 
/* 774 */     if (lhs.length != rhs.length) {
/* 775 */       setEquals(false);
/* 776 */       return this;
/*     */     } 
/* 778 */     for (int i = 0; i < lhs.length && this.isEquals; i++) {
/* 779 */       append(lhs[i], rhs[i]);
/*     */     }
/* 781 */     return this;
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
/*     */   public EqualsBuilder append(byte[] lhs, byte[] rhs) {
/* 795 */     if (!this.isEquals) {
/* 796 */       return this;
/*     */     }
/* 798 */     if (lhs == rhs) {
/* 799 */       return this;
/*     */     }
/* 801 */     if (lhs == null || rhs == null) {
/* 802 */       setEquals(false);
/* 803 */       return this;
/*     */     } 
/* 805 */     if (lhs.length != rhs.length) {
/* 806 */       setEquals(false);
/* 807 */       return this;
/*     */     } 
/* 809 */     for (int i = 0; i < lhs.length && this.isEquals; i++) {
/* 810 */       append(lhs[i], rhs[i]);
/*     */     }
/* 812 */     return this;
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
/*     */   public EqualsBuilder append(double[] lhs, double[] rhs) {
/* 826 */     if (!this.isEquals) {
/* 827 */       return this;
/*     */     }
/* 829 */     if (lhs == rhs) {
/* 830 */       return this;
/*     */     }
/* 832 */     if (lhs == null || rhs == null) {
/* 833 */       setEquals(false);
/* 834 */       return this;
/*     */     } 
/* 836 */     if (lhs.length != rhs.length) {
/* 837 */       setEquals(false);
/* 838 */       return this;
/*     */     } 
/* 840 */     for (int i = 0; i < lhs.length && this.isEquals; i++) {
/* 841 */       append(lhs[i], rhs[i]);
/*     */     }
/* 843 */     return this;
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
/*     */   public EqualsBuilder append(float[] lhs, float[] rhs) {
/* 857 */     if (!this.isEquals) {
/* 858 */       return this;
/*     */     }
/* 860 */     if (lhs == rhs) {
/* 861 */       return this;
/*     */     }
/* 863 */     if (lhs == null || rhs == null) {
/* 864 */       setEquals(false);
/* 865 */       return this;
/*     */     } 
/* 867 */     if (lhs.length != rhs.length) {
/* 868 */       setEquals(false);
/* 869 */       return this;
/*     */     } 
/* 871 */     for (int i = 0; i < lhs.length && this.isEquals; i++) {
/* 872 */       append(lhs[i], rhs[i]);
/*     */     }
/* 874 */     return this;
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
/*     */   public EqualsBuilder append(boolean[] lhs, boolean[] rhs) {
/* 888 */     if (!this.isEquals) {
/* 889 */       return this;
/*     */     }
/* 891 */     if (lhs == rhs) {
/* 892 */       return this;
/*     */     }
/* 894 */     if (lhs == null || rhs == null) {
/* 895 */       setEquals(false);
/* 896 */       return this;
/*     */     } 
/* 898 */     if (lhs.length != rhs.length) {
/* 899 */       setEquals(false);
/* 900 */       return this;
/*     */     } 
/* 902 */     for (int i = 0; i < lhs.length && this.isEquals; i++) {
/* 903 */       append(lhs[i], rhs[i]);
/*     */     }
/* 905 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEquals() {
/* 915 */     return this.isEquals;
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
/*     */   public Boolean build() {
/* 929 */     return Boolean.valueOf(isEquals());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setEquals(boolean isEquals) {
/* 939 */     this.isEquals = isEquals;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reset() {
/* 947 */     this.isEquals = true;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\org\apache\commons\lang3\builder\EqualsBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */