/*      */ package net.minecraft.util.org.apache.commons.lang3;
/*      */ 
/*      */ import java.lang.reflect.Array;
/*      */ import java.util.Arrays;
/*      */ import java.util.BitSet;
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
/*      */ import net.minecraft.util.org.apache.commons.lang3.builder.EqualsBuilder;
/*      */ import net.minecraft.util.org.apache.commons.lang3.builder.HashCodeBuilder;
/*      */ import net.minecraft.util.org.apache.commons.lang3.builder.ToStringBuilder;
/*      */ import net.minecraft.util.org.apache.commons.lang3.builder.ToStringStyle;
/*      */ import net.minecraft.util.org.apache.commons.lang3.mutable.MutableInt;
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
/*      */ public class ArrayUtils
/*      */ {
/*   49 */   public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];
/*      */ 
/*      */ 
/*      */   
/*   53 */   public static final Class<?>[] EMPTY_CLASS_ARRAY = new Class[0];
/*      */ 
/*      */ 
/*      */   
/*   57 */   public static final String[] EMPTY_STRING_ARRAY = new String[0];
/*      */ 
/*      */ 
/*      */   
/*   61 */   public static final long[] EMPTY_LONG_ARRAY = new long[0];
/*      */ 
/*      */ 
/*      */   
/*   65 */   public static final Long[] EMPTY_LONG_OBJECT_ARRAY = new Long[0];
/*      */ 
/*      */ 
/*      */   
/*   69 */   public static final int[] EMPTY_INT_ARRAY = new int[0];
/*      */ 
/*      */ 
/*      */   
/*   73 */   public static final Integer[] EMPTY_INTEGER_OBJECT_ARRAY = new Integer[0];
/*      */ 
/*      */ 
/*      */   
/*   77 */   public static final short[] EMPTY_SHORT_ARRAY = new short[0];
/*      */ 
/*      */ 
/*      */   
/*   81 */   public static final Short[] EMPTY_SHORT_OBJECT_ARRAY = new Short[0];
/*      */ 
/*      */ 
/*      */   
/*   85 */   public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
/*      */ 
/*      */ 
/*      */   
/*   89 */   public static final Byte[] EMPTY_BYTE_OBJECT_ARRAY = new Byte[0];
/*      */ 
/*      */ 
/*      */   
/*   93 */   public static final double[] EMPTY_DOUBLE_ARRAY = new double[0];
/*      */ 
/*      */ 
/*      */   
/*   97 */   public static final Double[] EMPTY_DOUBLE_OBJECT_ARRAY = new Double[0];
/*      */ 
/*      */ 
/*      */   
/*  101 */   public static final float[] EMPTY_FLOAT_ARRAY = new float[0];
/*      */ 
/*      */ 
/*      */   
/*  105 */   public static final Float[] EMPTY_FLOAT_OBJECT_ARRAY = new Float[0];
/*      */ 
/*      */ 
/*      */   
/*  109 */   public static final boolean[] EMPTY_BOOLEAN_ARRAY = new boolean[0];
/*      */ 
/*      */ 
/*      */   
/*  113 */   public static final Boolean[] EMPTY_BOOLEAN_OBJECT_ARRAY = new Boolean[0];
/*      */ 
/*      */ 
/*      */   
/*  117 */   public static final char[] EMPTY_CHAR_ARRAY = new char[0];
/*      */ 
/*      */ 
/*      */   
/*  121 */   public static final Character[] EMPTY_CHARACTER_OBJECT_ARRAY = new Character[0];
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
/*      */   public static final int INDEX_NOT_FOUND = -1;
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
/*      */   public static String toString(Object array) {
/*  159 */     return toString(array, "{}");
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
/*      */   public static String toString(Object array, String stringIfNull) {
/*  175 */     if (array == null) {
/*  176 */       return stringIfNull;
/*      */     }
/*  178 */     return (new ToStringBuilder(array, ToStringStyle.SIMPLE_STYLE)).append(array).toString();
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
/*      */   public static int hashCode(Object array) {
/*  190 */     return (new HashCodeBuilder()).append(array).toHashCode();
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
/*      */   @Deprecated
/*      */   public static boolean isEquals(Object array1, Object array2) {
/*  207 */     return (new EqualsBuilder()).append(array1, array2).isEquals();
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
/*      */   public static Map<Object, Object> toMap(Object[] array) {
/*  238 */     if (array == null) {
/*  239 */       return null;
/*      */     }
/*  241 */     Map<Object, Object> map = new HashMap<Object, Object>((int)(array.length * 1.5D));
/*  242 */     for (int i = 0; i < array.length; i++) {
/*  243 */       Object object = array[i];
/*  244 */       if (object instanceof Map.Entry) {
/*  245 */         Map.Entry<?, ?> entry = (Map.Entry<?, ?>)object;
/*  246 */         map.put(entry.getKey(), entry.getValue());
/*  247 */       } else if (object instanceof Object[]) {
/*  248 */         Object[] entry = (Object[])object;
/*  249 */         if (entry.length < 2) {
/*  250 */           throw new IllegalArgumentException("Array element " + i + ", '" + object + "', has a length less than 2");
/*      */         }
/*      */ 
/*      */         
/*  254 */         map.put(entry[0], entry[1]);
/*      */       } else {
/*  256 */         throw new IllegalArgumentException("Array element " + i + ", '" + object + "', is neither of type Map.Entry nor an Array");
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/*  261 */     return map;
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
/*      */   
/*      */   public static <T> T[] toArray(T... items) {
/*  304 */     return items;
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
/*      */   public static <T> T[] clone(T[] array) {
/*  323 */     if (array == null) {
/*  324 */       return null;
/*      */     }
/*  326 */     return (T[])array.clone();
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
/*      */   public static long[] clone(long[] array) {
/*  339 */     if (array == null) {
/*  340 */       return null;
/*      */     }
/*  342 */     return (long[])array.clone();
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
/*      */   public static int[] clone(int[] array) {
/*  355 */     if (array == null) {
/*  356 */       return null;
/*      */     }
/*  358 */     return (int[])array.clone();
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
/*      */   public static short[] clone(short[] array) {
/*  371 */     if (array == null) {
/*  372 */       return null;
/*      */     }
/*  374 */     return (short[])array.clone();
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
/*      */   public static char[] clone(char[] array) {
/*  387 */     if (array == null) {
/*  388 */       return null;
/*      */     }
/*  390 */     return (char[])array.clone();
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
/*      */   public static byte[] clone(byte[] array) {
/*  403 */     if (array == null) {
/*  404 */       return null;
/*      */     }
/*  406 */     return (byte[])array.clone();
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
/*      */   public static double[] clone(double[] array) {
/*  419 */     if (array == null) {
/*  420 */       return null;
/*      */     }
/*  422 */     return (double[])array.clone();
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
/*      */   public static float[] clone(float[] array) {
/*  435 */     if (array == null) {
/*  436 */       return null;
/*      */     }
/*  438 */     return (float[])array.clone();
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
/*      */   public static boolean[] clone(boolean[] array) {
/*  451 */     if (array == null) {
/*  452 */       return null;
/*      */     }
/*  454 */     return (boolean[])array.clone();
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
/*      */   public static Object[] nullToEmpty(Object[] array) {
/*  473 */     if (array == null || array.length == 0) {
/*  474 */       return EMPTY_OBJECT_ARRAY;
/*      */     }
/*  476 */     return array;
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
/*      */   public static Class<?>[] nullToEmpty(Class<?>[] array) {
/*  493 */     if (array == null || array.length == 0) {
/*  494 */       return EMPTY_CLASS_ARRAY;
/*      */     }
/*  496 */     return array;
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
/*      */   public static String[] nullToEmpty(String[] array) {
/*  513 */     if (array == null || array.length == 0) {
/*  514 */       return EMPTY_STRING_ARRAY;
/*      */     }
/*  516 */     return array;
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
/*      */   public static long[] nullToEmpty(long[] array) {
/*  533 */     if (array == null || array.length == 0) {
/*  534 */       return EMPTY_LONG_ARRAY;
/*      */     }
/*  536 */     return array;
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
/*      */   public static int[] nullToEmpty(int[] array) {
/*  553 */     if (array == null || array.length == 0) {
/*  554 */       return EMPTY_INT_ARRAY;
/*      */     }
/*  556 */     return array;
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
/*      */   public static short[] nullToEmpty(short[] array) {
/*  573 */     if (array == null || array.length == 0) {
/*  574 */       return EMPTY_SHORT_ARRAY;
/*      */     }
/*  576 */     return array;
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
/*      */   public static char[] nullToEmpty(char[] array) {
/*  593 */     if (array == null || array.length == 0) {
/*  594 */       return EMPTY_CHAR_ARRAY;
/*      */     }
/*  596 */     return array;
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
/*      */   public static byte[] nullToEmpty(byte[] array) {
/*  613 */     if (array == null || array.length == 0) {
/*  614 */       return EMPTY_BYTE_ARRAY;
/*      */     }
/*  616 */     return array;
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
/*      */   public static double[] nullToEmpty(double[] array) {
/*  633 */     if (array == null || array.length == 0) {
/*  634 */       return EMPTY_DOUBLE_ARRAY;
/*      */     }
/*  636 */     return array;
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
/*      */   public static float[] nullToEmpty(float[] array) {
/*  653 */     if (array == null || array.length == 0) {
/*  654 */       return EMPTY_FLOAT_ARRAY;
/*      */     }
/*  656 */     return array;
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
/*      */   public static boolean[] nullToEmpty(boolean[] array) {
/*  673 */     if (array == null || array.length == 0) {
/*  674 */       return EMPTY_BOOLEAN_ARRAY;
/*      */     }
/*  676 */     return array;
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
/*      */   public static Long[] nullToEmpty(Long[] array) {
/*  693 */     if (array == null || array.length == 0) {
/*  694 */       return EMPTY_LONG_OBJECT_ARRAY;
/*      */     }
/*  696 */     return array;
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
/*      */   public static Integer[] nullToEmpty(Integer[] array) {
/*  713 */     if (array == null || array.length == 0) {
/*  714 */       return EMPTY_INTEGER_OBJECT_ARRAY;
/*      */     }
/*  716 */     return array;
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
/*      */   public static Short[] nullToEmpty(Short[] array) {
/*  733 */     if (array == null || array.length == 0) {
/*  734 */       return EMPTY_SHORT_OBJECT_ARRAY;
/*      */     }
/*  736 */     return array;
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
/*      */   public static Character[] nullToEmpty(Character[] array) {
/*  753 */     if (array == null || array.length == 0) {
/*  754 */       return EMPTY_CHARACTER_OBJECT_ARRAY;
/*      */     }
/*  756 */     return array;
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
/*      */   public static Byte[] nullToEmpty(Byte[] array) {
/*  773 */     if (array == null || array.length == 0) {
/*  774 */       return EMPTY_BYTE_OBJECT_ARRAY;
/*      */     }
/*  776 */     return array;
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
/*      */   public static Double[] nullToEmpty(Double[] array) {
/*  793 */     if (array == null || array.length == 0) {
/*  794 */       return EMPTY_DOUBLE_OBJECT_ARRAY;
/*      */     }
/*  796 */     return array;
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
/*      */   public static Float[] nullToEmpty(Float[] array) {
/*  813 */     if (array == null || array.length == 0) {
/*  814 */       return EMPTY_FLOAT_OBJECT_ARRAY;
/*      */     }
/*  816 */     return array;
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
/*      */   public static Boolean[] nullToEmpty(Boolean[] array) {
/*  833 */     if (array == null || array.length == 0) {
/*  834 */       return EMPTY_BOOLEAN_OBJECT_ARRAY;
/*      */     }
/*  836 */     return array;
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
/*      */   public static <T> T[] subarray(T[] array, int startIndexInclusive, int endIndexExclusive) {
/*  871 */     if (array == null) {
/*  872 */       return null;
/*      */     }
/*  874 */     if (startIndexInclusive < 0) {
/*  875 */       startIndexInclusive = 0;
/*      */     }
/*  877 */     if (endIndexExclusive > array.length) {
/*  878 */       endIndexExclusive = array.length;
/*      */     }
/*  880 */     int newSize = endIndexExclusive - startIndexInclusive;
/*  881 */     Class<?> type = array.getClass().getComponentType();
/*  882 */     if (newSize <= 0) {
/*      */       
/*  884 */       T[] emptyArray = (T[])Array.newInstance(type, 0);
/*  885 */       return emptyArray;
/*      */     } 
/*      */ 
/*      */     
/*  889 */     T[] subarray = (T[])Array.newInstance(type, newSize);
/*  890 */     System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
/*  891 */     return subarray;
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
/*      */   public static long[] subarray(long[] array, int startIndexInclusive, int endIndexExclusive) {
/*  915 */     if (array == null) {
/*  916 */       return null;
/*      */     }
/*  918 */     if (startIndexInclusive < 0) {
/*  919 */       startIndexInclusive = 0;
/*      */     }
/*  921 */     if (endIndexExclusive > array.length) {
/*  922 */       endIndexExclusive = array.length;
/*      */     }
/*  924 */     int newSize = endIndexExclusive - startIndexInclusive;
/*  925 */     if (newSize <= 0) {
/*  926 */       return EMPTY_LONG_ARRAY;
/*      */     }
/*      */     
/*  929 */     long[] subarray = new long[newSize];
/*  930 */     System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
/*  931 */     return subarray;
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
/*      */   public static int[] subarray(int[] array, int startIndexInclusive, int endIndexExclusive) {
/*  955 */     if (array == null) {
/*  956 */       return null;
/*      */     }
/*  958 */     if (startIndexInclusive < 0) {
/*  959 */       startIndexInclusive = 0;
/*      */     }
/*  961 */     if (endIndexExclusive > array.length) {
/*  962 */       endIndexExclusive = array.length;
/*      */     }
/*  964 */     int newSize = endIndexExclusive - startIndexInclusive;
/*  965 */     if (newSize <= 0) {
/*  966 */       return EMPTY_INT_ARRAY;
/*      */     }
/*      */     
/*  969 */     int[] subarray = new int[newSize];
/*  970 */     System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
/*  971 */     return subarray;
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
/*      */   public static short[] subarray(short[] array, int startIndexInclusive, int endIndexExclusive) {
/*  995 */     if (array == null) {
/*  996 */       return null;
/*      */     }
/*  998 */     if (startIndexInclusive < 0) {
/*  999 */       startIndexInclusive = 0;
/*      */     }
/* 1001 */     if (endIndexExclusive > array.length) {
/* 1002 */       endIndexExclusive = array.length;
/*      */     }
/* 1004 */     int newSize = endIndexExclusive - startIndexInclusive;
/* 1005 */     if (newSize <= 0) {
/* 1006 */       return EMPTY_SHORT_ARRAY;
/*      */     }
/*      */     
/* 1009 */     short[] subarray = new short[newSize];
/* 1010 */     System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
/* 1011 */     return subarray;
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
/*      */   public static char[] subarray(char[] array, int startIndexInclusive, int endIndexExclusive) {
/* 1035 */     if (array == null) {
/* 1036 */       return null;
/*      */     }
/* 1038 */     if (startIndexInclusive < 0) {
/* 1039 */       startIndexInclusive = 0;
/*      */     }
/* 1041 */     if (endIndexExclusive > array.length) {
/* 1042 */       endIndexExclusive = array.length;
/*      */     }
/* 1044 */     int newSize = endIndexExclusive - startIndexInclusive;
/* 1045 */     if (newSize <= 0) {
/* 1046 */       return EMPTY_CHAR_ARRAY;
/*      */     }
/*      */     
/* 1049 */     char[] subarray = new char[newSize];
/* 1050 */     System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
/* 1051 */     return subarray;
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
/*      */   public static byte[] subarray(byte[] array, int startIndexInclusive, int endIndexExclusive) {
/* 1075 */     if (array == null) {
/* 1076 */       return null;
/*      */     }
/* 1078 */     if (startIndexInclusive < 0) {
/* 1079 */       startIndexInclusive = 0;
/*      */     }
/* 1081 */     if (endIndexExclusive > array.length) {
/* 1082 */       endIndexExclusive = array.length;
/*      */     }
/* 1084 */     int newSize = endIndexExclusive - startIndexInclusive;
/* 1085 */     if (newSize <= 0) {
/* 1086 */       return EMPTY_BYTE_ARRAY;
/*      */     }
/*      */     
/* 1089 */     byte[] subarray = new byte[newSize];
/* 1090 */     System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
/* 1091 */     return subarray;
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
/*      */   public static double[] subarray(double[] array, int startIndexInclusive, int endIndexExclusive) {
/* 1115 */     if (array == null) {
/* 1116 */       return null;
/*      */     }
/* 1118 */     if (startIndexInclusive < 0) {
/* 1119 */       startIndexInclusive = 0;
/*      */     }
/* 1121 */     if (endIndexExclusive > array.length) {
/* 1122 */       endIndexExclusive = array.length;
/*      */     }
/* 1124 */     int newSize = endIndexExclusive - startIndexInclusive;
/* 1125 */     if (newSize <= 0) {
/* 1126 */       return EMPTY_DOUBLE_ARRAY;
/*      */     }
/*      */     
/* 1129 */     double[] subarray = new double[newSize];
/* 1130 */     System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
/* 1131 */     return subarray;
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
/*      */   public static float[] subarray(float[] array, int startIndexInclusive, int endIndexExclusive) {
/* 1155 */     if (array == null) {
/* 1156 */       return null;
/*      */     }
/* 1158 */     if (startIndexInclusive < 0) {
/* 1159 */       startIndexInclusive = 0;
/*      */     }
/* 1161 */     if (endIndexExclusive > array.length) {
/* 1162 */       endIndexExclusive = array.length;
/*      */     }
/* 1164 */     int newSize = endIndexExclusive - startIndexInclusive;
/* 1165 */     if (newSize <= 0) {
/* 1166 */       return EMPTY_FLOAT_ARRAY;
/*      */     }
/*      */     
/* 1169 */     float[] subarray = new float[newSize];
/* 1170 */     System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
/* 1171 */     return subarray;
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
/*      */   public static boolean[] subarray(boolean[] array, int startIndexInclusive, int endIndexExclusive) {
/* 1195 */     if (array == null) {
/* 1196 */       return null;
/*      */     }
/* 1198 */     if (startIndexInclusive < 0) {
/* 1199 */       startIndexInclusive = 0;
/*      */     }
/* 1201 */     if (endIndexExclusive > array.length) {
/* 1202 */       endIndexExclusive = array.length;
/*      */     }
/* 1204 */     int newSize = endIndexExclusive - startIndexInclusive;
/* 1205 */     if (newSize <= 0) {
/* 1206 */       return EMPTY_BOOLEAN_ARRAY;
/*      */     }
/*      */     
/* 1209 */     boolean[] subarray = new boolean[newSize];
/* 1210 */     System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
/* 1211 */     return subarray;
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
/*      */   public static boolean isSameLength(Object[] array1, Object[] array2) {
/* 1228 */     if ((array1 == null && array2 != null && array2.length > 0) || (array2 == null && array1 != null && array1.length > 0) || (array1 != null && array2 != null && array1.length != array2.length))
/*      */     {
/*      */       
/* 1231 */       return false;
/*      */     }
/* 1233 */     return true;
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
/*      */   public static boolean isSameLength(long[] array1, long[] array2) {
/* 1246 */     if ((array1 == null && array2 != null && array2.length > 0) || (array2 == null && array1 != null && array1.length > 0) || (array1 != null && array2 != null && array1.length != array2.length))
/*      */     {
/*      */       
/* 1249 */       return false;
/*      */     }
/* 1251 */     return true;
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
/*      */   public static boolean isSameLength(int[] array1, int[] array2) {
/* 1264 */     if ((array1 == null && array2 != null && array2.length > 0) || (array2 == null && array1 != null && array1.length > 0) || (array1 != null && array2 != null && array1.length != array2.length))
/*      */     {
/*      */       
/* 1267 */       return false;
/*      */     }
/* 1269 */     return true;
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
/*      */   public static boolean isSameLength(short[] array1, short[] array2) {
/* 1282 */     if ((array1 == null && array2 != null && array2.length > 0) || (array2 == null && array1 != null && array1.length > 0) || (array1 != null && array2 != null && array1.length != array2.length))
/*      */     {
/*      */       
/* 1285 */       return false;
/*      */     }
/* 1287 */     return true;
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
/*      */   public static boolean isSameLength(char[] array1, char[] array2) {
/* 1300 */     if ((array1 == null && array2 != null && array2.length > 0) || (array2 == null && array1 != null && array1.length > 0) || (array1 != null && array2 != null && array1.length != array2.length))
/*      */     {
/*      */       
/* 1303 */       return false;
/*      */     }
/* 1305 */     return true;
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
/*      */   public static boolean isSameLength(byte[] array1, byte[] array2) {
/* 1318 */     if ((array1 == null && array2 != null && array2.length > 0) || (array2 == null && array1 != null && array1.length > 0) || (array1 != null && array2 != null && array1.length != array2.length))
/*      */     {
/*      */       
/* 1321 */       return false;
/*      */     }
/* 1323 */     return true;
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
/*      */   public static boolean isSameLength(double[] array1, double[] array2) {
/* 1336 */     if ((array1 == null && array2 != null && array2.length > 0) || (array2 == null && array1 != null && array1.length > 0) || (array1 != null && array2 != null && array1.length != array2.length))
/*      */     {
/*      */       
/* 1339 */       return false;
/*      */     }
/* 1341 */     return true;
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
/*      */   public static boolean isSameLength(float[] array1, float[] array2) {
/* 1354 */     if ((array1 == null && array2 != null && array2.length > 0) || (array2 == null && array1 != null && array1.length > 0) || (array1 != null && array2 != null && array1.length != array2.length))
/*      */     {
/*      */       
/* 1357 */       return false;
/*      */     }
/* 1359 */     return true;
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
/*      */   public static boolean isSameLength(boolean[] array1, boolean[] array2) {
/* 1372 */     if ((array1 == null && array2 != null && array2.length > 0) || (array2 == null && array1 != null && array1.length > 0) || (array1 != null && array2 != null && array1.length != array2.length))
/*      */     {
/*      */       
/* 1375 */       return false;
/*      */     }
/* 1377 */     return true;
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
/*      */   public static int getLength(Object array) {
/* 1402 */     if (array == null) {
/* 1403 */       return 0;
/*      */     }
/* 1405 */     return Array.getLength(array);
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
/*      */   public static boolean isSameType(Object array1, Object array2) {
/* 1418 */     if (array1 == null || array2 == null) {
/* 1419 */       throw new IllegalArgumentException("The Array must not be null");
/*      */     }
/* 1421 */     return array1.getClass().getName().equals(array2.getClass().getName());
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
/*      */   public static void reverse(Object[] array) {
/* 1436 */     if (array == null) {
/*      */       return;
/*      */     }
/* 1439 */     reverse(array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void reverse(long[] array) {
/* 1450 */     if (array == null) {
/*      */       return;
/*      */     }
/* 1453 */     reverse(array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void reverse(int[] array) {
/* 1464 */     if (array == null) {
/*      */       return;
/*      */     }
/* 1467 */     reverse(array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void reverse(short[] array) {
/* 1478 */     if (array == null) {
/*      */       return;
/*      */     }
/* 1481 */     reverse(array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void reverse(char[] array) {
/* 1492 */     if (array == null) {
/*      */       return;
/*      */     }
/* 1495 */     reverse(array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void reverse(byte[] array) {
/* 1506 */     if (array == null) {
/*      */       return;
/*      */     }
/* 1509 */     reverse(array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void reverse(double[] array) {
/* 1520 */     if (array == null) {
/*      */       return;
/*      */     }
/* 1523 */     reverse(array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void reverse(float[] array) {
/* 1534 */     if (array == null) {
/*      */       return;
/*      */     }
/* 1537 */     reverse(array, 0, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void reverse(boolean[] array) {
/* 1548 */     if (array == null) {
/*      */       return;
/*      */     }
/* 1551 */     reverse(array, 0, array.length);
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
/*      */   public static void reverse(boolean[] array, int startIndexInclusive, int endIndexExclusive) {
/* 1574 */     if (array == null) {
/*      */       return;
/*      */     }
/* 1577 */     int i = (startIndexInclusive < 0) ? 0 : startIndexInclusive;
/* 1578 */     int j = Math.min(array.length, endIndexExclusive) - 1;
/*      */     
/* 1580 */     while (j > i) {
/* 1581 */       boolean tmp = array[j];
/* 1582 */       array[j] = array[i];
/* 1583 */       array[i] = tmp;
/* 1584 */       j--;
/* 1585 */       i++;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void reverse(byte[] array, int startIndexInclusive, int endIndexExclusive) {
/* 1609 */     if (array == null) {
/*      */       return;
/*      */     }
/* 1612 */     int i = (startIndexInclusive < 0) ? 0 : startIndexInclusive;
/* 1613 */     int j = Math.min(array.length, endIndexExclusive) - 1;
/*      */     
/* 1615 */     while (j > i) {
/* 1616 */       byte tmp = array[j];
/* 1617 */       array[j] = array[i];
/* 1618 */       array[i] = tmp;
/* 1619 */       j--;
/* 1620 */       i++;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void reverse(char[] array, int startIndexInclusive, int endIndexExclusive) {
/* 1644 */     if (array == null) {
/*      */       return;
/*      */     }
/* 1647 */     int i = (startIndexInclusive < 0) ? 0 : startIndexInclusive;
/* 1648 */     int j = Math.min(array.length, endIndexExclusive) - 1;
/*      */     
/* 1650 */     while (j > i) {
/* 1651 */       char tmp = array[j];
/* 1652 */       array[j] = array[i];
/* 1653 */       array[i] = tmp;
/* 1654 */       j--;
/* 1655 */       i++;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void reverse(double[] array, int startIndexInclusive, int endIndexExclusive) {
/* 1679 */     if (array == null) {
/*      */       return;
/*      */     }
/* 1682 */     int i = (startIndexInclusive < 0) ? 0 : startIndexInclusive;
/* 1683 */     int j = Math.min(array.length, endIndexExclusive) - 1;
/*      */     
/* 1685 */     while (j > i) {
/* 1686 */       double tmp = array[j];
/* 1687 */       array[j] = array[i];
/* 1688 */       array[i] = tmp;
/* 1689 */       j--;
/* 1690 */       i++;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void reverse(float[] array, int startIndexInclusive, int endIndexExclusive) {
/* 1714 */     if (array == null) {
/*      */       return;
/*      */     }
/* 1717 */     int i = (startIndexInclusive < 0) ? 0 : startIndexInclusive;
/* 1718 */     int j = Math.min(array.length, endIndexExclusive) - 1;
/*      */     
/* 1720 */     while (j > i) {
/* 1721 */       float tmp = array[j];
/* 1722 */       array[j] = array[i];
/* 1723 */       array[i] = tmp;
/* 1724 */       j--;
/* 1725 */       i++;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void reverse(int[] array, int startIndexInclusive, int endIndexExclusive) {
/* 1749 */     if (array == null) {
/*      */       return;
/*      */     }
/* 1752 */     int i = (startIndexInclusive < 0) ? 0 : startIndexInclusive;
/* 1753 */     int j = Math.min(array.length, endIndexExclusive) - 1;
/*      */     
/* 1755 */     while (j > i) {
/* 1756 */       int tmp = array[j];
/* 1757 */       array[j] = array[i];
/* 1758 */       array[i] = tmp;
/* 1759 */       j--;
/* 1760 */       i++;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void reverse(long[] array, int startIndexInclusive, int endIndexExclusive) {
/* 1784 */     if (array == null) {
/*      */       return;
/*      */     }
/* 1787 */     int i = (startIndexInclusive < 0) ? 0 : startIndexInclusive;
/* 1788 */     int j = Math.min(array.length, endIndexExclusive) - 1;
/*      */     
/* 1790 */     while (j > i) {
/* 1791 */       long tmp = array[j];
/* 1792 */       array[j] = array[i];
/* 1793 */       array[i] = tmp;
/* 1794 */       j--;
/* 1795 */       i++;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void reverse(Object[] array, int startIndexInclusive, int endIndexExclusive) {
/* 1819 */     if (array == null) {
/*      */       return;
/*      */     }
/* 1822 */     int i = (startIndexInclusive < 0) ? 0 : startIndexInclusive;
/* 1823 */     int j = Math.min(array.length, endIndexExclusive) - 1;
/*      */     
/* 1825 */     while (j > i) {
/* 1826 */       Object tmp = array[j];
/* 1827 */       array[j] = array[i];
/* 1828 */       array[i] = tmp;
/* 1829 */       j--;
/* 1830 */       i++;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void reverse(short[] array, int startIndexInclusive, int endIndexExclusive) {
/* 1854 */     if (array == null) {
/*      */       return;
/*      */     }
/* 1857 */     int i = (startIndexInclusive < 0) ? 0 : startIndexInclusive;
/* 1858 */     int j = Math.min(array.length, endIndexExclusive) - 1;
/*      */     
/* 1860 */     while (j > i) {
/* 1861 */       short tmp = array[j];
/* 1862 */       array[j] = array[i];
/* 1863 */       array[i] = tmp;
/* 1864 */       j--;
/* 1865 */       i++;
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
/*      */   
/*      */   public static int indexOf(Object[] array, Object objectToFind) {
/* 1885 */     return indexOf(array, objectToFind, 0);
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
/*      */   public static int indexOf(Object[] array, Object objectToFind, int startIndex) {
/* 1903 */     if (array == null) {
/* 1904 */       return -1;
/*      */     }
/* 1906 */     if (startIndex < 0) {
/* 1907 */       startIndex = 0;
/*      */     }
/* 1909 */     if (objectToFind == null) {
/* 1910 */       for (int i = startIndex; i < array.length; i++) {
/* 1911 */         if (array[i] == null) {
/* 1912 */           return i;
/*      */         }
/*      */       } 
/* 1915 */     } else if (array.getClass().getComponentType().isInstance(objectToFind)) {
/* 1916 */       for (int i = startIndex; i < array.length; i++) {
/* 1917 */         if (objectToFind.equals(array[i])) {
/* 1918 */           return i;
/*      */         }
/*      */       } 
/*      */     } 
/* 1922 */     return -1;
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
/*      */   public static int lastIndexOf(Object[] array, Object objectToFind) {
/* 1936 */     return lastIndexOf(array, objectToFind, 2147483647);
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
/*      */   public static int lastIndexOf(Object[] array, Object objectToFind, int startIndex) {
/* 1954 */     if (array == null) {
/* 1955 */       return -1;
/*      */     }
/* 1957 */     if (startIndex < 0)
/* 1958 */       return -1; 
/* 1959 */     if (startIndex >= array.length) {
/* 1960 */       startIndex = array.length - 1;
/*      */     }
/* 1962 */     if (objectToFind == null) {
/* 1963 */       for (int i = startIndex; i >= 0; i--) {
/* 1964 */         if (array[i] == null) {
/* 1965 */           return i;
/*      */         }
/*      */       } 
/* 1968 */     } else if (array.getClass().getComponentType().isInstance(objectToFind)) {
/* 1969 */       for (int i = startIndex; i >= 0; i--) {
/* 1970 */         if (objectToFind.equals(array[i])) {
/* 1971 */           return i;
/*      */         }
/*      */       } 
/*      */     } 
/* 1975 */     return -1;
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
/*      */   public static boolean contains(Object[] array, Object objectToFind) {
/* 1988 */     return (indexOf(array, objectToFind) != -1);
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
/*      */   public static int indexOf(long[] array, long valueToFind) {
/* 2004 */     return indexOf(array, valueToFind, 0);
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
/*      */   public static int indexOf(long[] array, long valueToFind, int startIndex) {
/* 2022 */     if (array == null) {
/* 2023 */       return -1;
/*      */     }
/* 2025 */     if (startIndex < 0) {
/* 2026 */       startIndex = 0;
/*      */     }
/* 2028 */     for (int i = startIndex; i < array.length; i++) {
/* 2029 */       if (valueToFind == array[i]) {
/* 2030 */         return i;
/*      */       }
/*      */     } 
/* 2033 */     return -1;
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
/*      */   public static int lastIndexOf(long[] array, long valueToFind) {
/* 2047 */     return lastIndexOf(array, valueToFind, 2147483647);
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
/*      */   public static int lastIndexOf(long[] array, long valueToFind, int startIndex) {
/* 2065 */     if (array == null) {
/* 2066 */       return -1;
/*      */     }
/* 2068 */     if (startIndex < 0)
/* 2069 */       return -1; 
/* 2070 */     if (startIndex >= array.length) {
/* 2071 */       startIndex = array.length - 1;
/*      */     }
/* 2073 */     for (int i = startIndex; i >= 0; i--) {
/* 2074 */       if (valueToFind == array[i]) {
/* 2075 */         return i;
/*      */       }
/*      */     } 
/* 2078 */     return -1;
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
/*      */   public static boolean contains(long[] array, long valueToFind) {
/* 2091 */     return (indexOf(array, valueToFind) != -1);
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
/*      */   public static int indexOf(int[] array, int valueToFind) {
/* 2107 */     return indexOf(array, valueToFind, 0);
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
/*      */   public static int indexOf(int[] array, int valueToFind, int startIndex) {
/* 2125 */     if (array == null) {
/* 2126 */       return -1;
/*      */     }
/* 2128 */     if (startIndex < 0) {
/* 2129 */       startIndex = 0;
/*      */     }
/* 2131 */     for (int i = startIndex; i < array.length; i++) {
/* 2132 */       if (valueToFind == array[i]) {
/* 2133 */         return i;
/*      */       }
/*      */     } 
/* 2136 */     return -1;
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
/*      */   public static int lastIndexOf(int[] array, int valueToFind) {
/* 2150 */     return lastIndexOf(array, valueToFind, 2147483647);
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
/*      */   public static int lastIndexOf(int[] array, int valueToFind, int startIndex) {
/* 2168 */     if (array == null) {
/* 2169 */       return -1;
/*      */     }
/* 2171 */     if (startIndex < 0)
/* 2172 */       return -1; 
/* 2173 */     if (startIndex >= array.length) {
/* 2174 */       startIndex = array.length - 1;
/*      */     }
/* 2176 */     for (int i = startIndex; i >= 0; i--) {
/* 2177 */       if (valueToFind == array[i]) {
/* 2178 */         return i;
/*      */       }
/*      */     } 
/* 2181 */     return -1;
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
/*      */   public static boolean contains(int[] array, int valueToFind) {
/* 2194 */     return (indexOf(array, valueToFind) != -1);
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
/*      */   public static int indexOf(short[] array, short valueToFind) {
/* 2210 */     return indexOf(array, valueToFind, 0);
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
/*      */   public static int indexOf(short[] array, short valueToFind, int startIndex) {
/* 2228 */     if (array == null) {
/* 2229 */       return -1;
/*      */     }
/* 2231 */     if (startIndex < 0) {
/* 2232 */       startIndex = 0;
/*      */     }
/* 2234 */     for (int i = startIndex; i < array.length; i++) {
/* 2235 */       if (valueToFind == array[i]) {
/* 2236 */         return i;
/*      */       }
/*      */     } 
/* 2239 */     return -1;
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
/*      */   public static int lastIndexOf(short[] array, short valueToFind) {
/* 2253 */     return lastIndexOf(array, valueToFind, 2147483647);
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
/*      */   public static int lastIndexOf(short[] array, short valueToFind, int startIndex) {
/* 2271 */     if (array == null) {
/* 2272 */       return -1;
/*      */     }
/* 2274 */     if (startIndex < 0)
/* 2275 */       return -1; 
/* 2276 */     if (startIndex >= array.length) {
/* 2277 */       startIndex = array.length - 1;
/*      */     }
/* 2279 */     for (int i = startIndex; i >= 0; i--) {
/* 2280 */       if (valueToFind == array[i]) {
/* 2281 */         return i;
/*      */       }
/*      */     } 
/* 2284 */     return -1;
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
/*      */   public static boolean contains(short[] array, short valueToFind) {
/* 2297 */     return (indexOf(array, valueToFind) != -1);
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
/*      */   public static int indexOf(char[] array, char valueToFind) {
/* 2314 */     return indexOf(array, valueToFind, 0);
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
/*      */   public static int indexOf(char[] array, char valueToFind, int startIndex) {
/* 2333 */     if (array == null) {
/* 2334 */       return -1;
/*      */     }
/* 2336 */     if (startIndex < 0) {
/* 2337 */       startIndex = 0;
/*      */     }
/* 2339 */     for (int i = startIndex; i < array.length; i++) {
/* 2340 */       if (valueToFind == array[i]) {
/* 2341 */         return i;
/*      */       }
/*      */     } 
/* 2344 */     return -1;
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
/*      */   public static int lastIndexOf(char[] array, char valueToFind) {
/* 2359 */     return lastIndexOf(array, valueToFind, 2147483647);
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
/*      */   public static int lastIndexOf(char[] array, char valueToFind, int startIndex) {
/* 2378 */     if (array == null) {
/* 2379 */       return -1;
/*      */     }
/* 2381 */     if (startIndex < 0)
/* 2382 */       return -1; 
/* 2383 */     if (startIndex >= array.length) {
/* 2384 */       startIndex = array.length - 1;
/*      */     }
/* 2386 */     for (int i = startIndex; i >= 0; i--) {
/* 2387 */       if (valueToFind == array[i]) {
/* 2388 */         return i;
/*      */       }
/*      */     } 
/* 2391 */     return -1;
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
/*      */   public static boolean contains(char[] array, char valueToFind) {
/* 2405 */     return (indexOf(array, valueToFind) != -1);
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
/*      */   public static int indexOf(byte[] array, byte valueToFind) {
/* 2421 */     return indexOf(array, valueToFind, 0);
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
/*      */   public static int indexOf(byte[] array, byte valueToFind, int startIndex) {
/* 2439 */     if (array == null) {
/* 2440 */       return -1;
/*      */     }
/* 2442 */     if (startIndex < 0) {
/* 2443 */       startIndex = 0;
/*      */     }
/* 2445 */     for (int i = startIndex; i < array.length; i++) {
/* 2446 */       if (valueToFind == array[i]) {
/* 2447 */         return i;
/*      */       }
/*      */     } 
/* 2450 */     return -1;
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
/*      */   public static int lastIndexOf(byte[] array, byte valueToFind) {
/* 2464 */     return lastIndexOf(array, valueToFind, 2147483647);
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
/*      */   public static int lastIndexOf(byte[] array, byte valueToFind, int startIndex) {
/* 2482 */     if (array == null) {
/* 2483 */       return -1;
/*      */     }
/* 2485 */     if (startIndex < 0)
/* 2486 */       return -1; 
/* 2487 */     if (startIndex >= array.length) {
/* 2488 */       startIndex = array.length - 1;
/*      */     }
/* 2490 */     for (int i = startIndex; i >= 0; i--) {
/* 2491 */       if (valueToFind == array[i]) {
/* 2492 */         return i;
/*      */       }
/*      */     } 
/* 2495 */     return -1;
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
/*      */   public static boolean contains(byte[] array, byte valueToFind) {
/* 2508 */     return (indexOf(array, valueToFind) != -1);
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
/*      */   public static int indexOf(double[] array, double valueToFind) {
/* 2524 */     return indexOf(array, valueToFind, 0);
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
/*      */   public static int indexOf(double[] array, double valueToFind, double tolerance) {
/* 2541 */     return indexOf(array, valueToFind, 0, tolerance);
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
/*      */   public static int indexOf(double[] array, double valueToFind, int startIndex) {
/* 2559 */     if (isEmpty(array)) {
/* 2560 */       return -1;
/*      */     }
/* 2562 */     if (startIndex < 0) {
/* 2563 */       startIndex = 0;
/*      */     }
/* 2565 */     for (int i = startIndex; i < array.length; i++) {
/* 2566 */       if (valueToFind == array[i]) {
/* 2567 */         return i;
/*      */       }
/*      */     } 
/* 2570 */     return -1;
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
/*      */   public static int indexOf(double[] array, double valueToFind, int startIndex, double tolerance) {
/* 2591 */     if (isEmpty(array)) {
/* 2592 */       return -1;
/*      */     }
/* 2594 */     if (startIndex < 0) {
/* 2595 */       startIndex = 0;
/*      */     }
/* 2597 */     double min = valueToFind - tolerance;
/* 2598 */     double max = valueToFind + tolerance;
/* 2599 */     for (int i = startIndex; i < array.length; i++) {
/* 2600 */       if (array[i] >= min && array[i] <= max) {
/* 2601 */         return i;
/*      */       }
/*      */     } 
/* 2604 */     return -1;
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
/*      */   public static int lastIndexOf(double[] array, double valueToFind) {
/* 2618 */     return lastIndexOf(array, valueToFind, 2147483647);
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
/*      */   public static int lastIndexOf(double[] array, double valueToFind, double tolerance) {
/* 2635 */     return lastIndexOf(array, valueToFind, 2147483647, tolerance);
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
/*      */   public static int lastIndexOf(double[] array, double valueToFind, int startIndex) {
/* 2653 */     if (isEmpty(array)) {
/* 2654 */       return -1;
/*      */     }
/* 2656 */     if (startIndex < 0)
/* 2657 */       return -1; 
/* 2658 */     if (startIndex >= array.length) {
/* 2659 */       startIndex = array.length - 1;
/*      */     }
/* 2661 */     for (int i = startIndex; i >= 0; i--) {
/* 2662 */       if (valueToFind == array[i]) {
/* 2663 */         return i;
/*      */       }
/*      */     } 
/* 2666 */     return -1;
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
/*      */   public static int lastIndexOf(double[] array, double valueToFind, int startIndex, double tolerance) {
/* 2687 */     if (isEmpty(array)) {
/* 2688 */       return -1;
/*      */     }
/* 2690 */     if (startIndex < 0)
/* 2691 */       return -1; 
/* 2692 */     if (startIndex >= array.length) {
/* 2693 */       startIndex = array.length - 1;
/*      */     }
/* 2695 */     double min = valueToFind - tolerance;
/* 2696 */     double max = valueToFind + tolerance;
/* 2697 */     for (int i = startIndex; i >= 0; i--) {
/* 2698 */       if (array[i] >= min && array[i] <= max) {
/* 2699 */         return i;
/*      */       }
/*      */     } 
/* 2702 */     return -1;
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
/*      */   public static boolean contains(double[] array, double valueToFind) {
/* 2715 */     return (indexOf(array, valueToFind) != -1);
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
/*      */   public static boolean contains(double[] array, double valueToFind, double tolerance) {
/* 2732 */     return (indexOf(array, valueToFind, 0, tolerance) != -1);
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
/*      */   public static int indexOf(float[] array, float valueToFind) {
/* 2748 */     return indexOf(array, valueToFind, 0);
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
/*      */   public static int indexOf(float[] array, float valueToFind, int startIndex) {
/* 2766 */     if (isEmpty(array)) {
/* 2767 */       return -1;
/*      */     }
/* 2769 */     if (startIndex < 0) {
/* 2770 */       startIndex = 0;
/*      */     }
/* 2772 */     for (int i = startIndex; i < array.length; i++) {
/* 2773 */       if (valueToFind == array[i]) {
/* 2774 */         return i;
/*      */       }
/*      */     } 
/* 2777 */     return -1;
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
/*      */   public static int lastIndexOf(float[] array, float valueToFind) {
/* 2791 */     return lastIndexOf(array, valueToFind, 2147483647);
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
/*      */   public static int lastIndexOf(float[] array, float valueToFind, int startIndex) {
/* 2809 */     if (isEmpty(array)) {
/* 2810 */       return -1;
/*      */     }
/* 2812 */     if (startIndex < 0)
/* 2813 */       return -1; 
/* 2814 */     if (startIndex >= array.length) {
/* 2815 */       startIndex = array.length - 1;
/*      */     }
/* 2817 */     for (int i = startIndex; i >= 0; i--) {
/* 2818 */       if (valueToFind == array[i]) {
/* 2819 */         return i;
/*      */       }
/*      */     } 
/* 2822 */     return -1;
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
/*      */   public static boolean contains(float[] array, float valueToFind) {
/* 2835 */     return (indexOf(array, valueToFind) != -1);
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
/*      */   public static int indexOf(boolean[] array, boolean valueToFind) {
/* 2851 */     return indexOf(array, valueToFind, 0);
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
/*      */   public static int indexOf(boolean[] array, boolean valueToFind, int startIndex) {
/* 2870 */     if (isEmpty(array)) {
/* 2871 */       return -1;
/*      */     }
/* 2873 */     if (startIndex < 0) {
/* 2874 */       startIndex = 0;
/*      */     }
/* 2876 */     for (int i = startIndex; i < array.length; i++) {
/* 2877 */       if (valueToFind == array[i]) {
/* 2878 */         return i;
/*      */       }
/*      */     } 
/* 2881 */     return -1;
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
/*      */   public static int lastIndexOf(boolean[] array, boolean valueToFind) {
/* 2896 */     return lastIndexOf(array, valueToFind, 2147483647);
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
/*      */   public static int lastIndexOf(boolean[] array, boolean valueToFind, int startIndex) {
/* 2914 */     if (isEmpty(array)) {
/* 2915 */       return -1;
/*      */     }
/* 2917 */     if (startIndex < 0)
/* 2918 */       return -1; 
/* 2919 */     if (startIndex >= array.length) {
/* 2920 */       startIndex = array.length - 1;
/*      */     }
/* 2922 */     for (int i = startIndex; i >= 0; i--) {
/* 2923 */       if (valueToFind == array[i]) {
/* 2924 */         return i;
/*      */       }
/*      */     } 
/* 2927 */     return -1;
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
/*      */   public static boolean contains(boolean[] array, boolean valueToFind) {
/* 2940 */     return (indexOf(array, valueToFind) != -1);
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
/*      */   public static char[] toPrimitive(Character[] array) {
/* 2958 */     if (array == null)
/* 2959 */       return null; 
/* 2960 */     if (array.length == 0) {
/* 2961 */       return EMPTY_CHAR_ARRAY;
/*      */     }
/* 2963 */     char[] result = new char[array.length];
/* 2964 */     for (int i = 0; i < array.length; i++) {
/* 2965 */       result[i] = array[i].charValue();
/*      */     }
/* 2967 */     return result;
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
/*      */   public static char[] toPrimitive(Character[] array, char valueForNull) {
/* 2980 */     if (array == null)
/* 2981 */       return null; 
/* 2982 */     if (array.length == 0) {
/* 2983 */       return EMPTY_CHAR_ARRAY;
/*      */     }
/* 2985 */     char[] result = new char[array.length];
/* 2986 */     for (int i = 0; i < array.length; i++) {
/* 2987 */       Character b = array[i];
/* 2988 */       result[i] = (b == null) ? valueForNull : b.charValue();
/*      */     } 
/* 2990 */     return result;
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
/*      */   public static Character[] toObject(char[] array) {
/* 3002 */     if (array == null)
/* 3003 */       return null; 
/* 3004 */     if (array.length == 0) {
/* 3005 */       return EMPTY_CHARACTER_OBJECT_ARRAY;
/*      */     }
/* 3007 */     Character[] result = new Character[array.length];
/* 3008 */     for (int i = 0; i < array.length; i++) {
/* 3009 */       result[i] = Character.valueOf(array[i]);
/*      */     }
/* 3011 */     return result;
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
/*      */   public static long[] toPrimitive(Long[] array) {
/* 3026 */     if (array == null)
/* 3027 */       return null; 
/* 3028 */     if (array.length == 0) {
/* 3029 */       return EMPTY_LONG_ARRAY;
/*      */     }
/* 3031 */     long[] result = new long[array.length];
/* 3032 */     for (int i = 0; i < array.length; i++) {
/* 3033 */       result[i] = array[i].longValue();
/*      */     }
/* 3035 */     return result;
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
/*      */   public static long[] toPrimitive(Long[] array, long valueForNull) {
/* 3048 */     if (array == null)
/* 3049 */       return null; 
/* 3050 */     if (array.length == 0) {
/* 3051 */       return EMPTY_LONG_ARRAY;
/*      */     }
/* 3053 */     long[] result = new long[array.length];
/* 3054 */     for (int i = 0; i < array.length; i++) {
/* 3055 */       Long b = array[i];
/* 3056 */       result[i] = (b == null) ? valueForNull : b.longValue();
/*      */     } 
/* 3058 */     return result;
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
/*      */   public static Long[] toObject(long[] array) {
/* 3070 */     if (array == null)
/* 3071 */       return null; 
/* 3072 */     if (array.length == 0) {
/* 3073 */       return EMPTY_LONG_OBJECT_ARRAY;
/*      */     }
/* 3075 */     Long[] result = new Long[array.length];
/* 3076 */     for (int i = 0; i < array.length; i++) {
/* 3077 */       result[i] = Long.valueOf(array[i]);
/*      */     }
/* 3079 */     return result;
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
/*      */   public static int[] toPrimitive(Integer[] array) {
/* 3094 */     if (array == null)
/* 3095 */       return null; 
/* 3096 */     if (array.length == 0) {
/* 3097 */       return EMPTY_INT_ARRAY;
/*      */     }
/* 3099 */     int[] result = new int[array.length];
/* 3100 */     for (int i = 0; i < array.length; i++) {
/* 3101 */       result[i] = array[i].intValue();
/*      */     }
/* 3103 */     return result;
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
/*      */   public static int[] toPrimitive(Integer[] array, int valueForNull) {
/* 3116 */     if (array == null)
/* 3117 */       return null; 
/* 3118 */     if (array.length == 0) {
/* 3119 */       return EMPTY_INT_ARRAY;
/*      */     }
/* 3121 */     int[] result = new int[array.length];
/* 3122 */     for (int i = 0; i < array.length; i++) {
/* 3123 */       Integer b = array[i];
/* 3124 */       result[i] = (b == null) ? valueForNull : b.intValue();
/*      */     } 
/* 3126 */     return result;
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
/*      */   public static Integer[] toObject(int[] array) {
/* 3138 */     if (array == null)
/* 3139 */       return null; 
/* 3140 */     if (array.length == 0) {
/* 3141 */       return EMPTY_INTEGER_OBJECT_ARRAY;
/*      */     }
/* 3143 */     Integer[] result = new Integer[array.length];
/* 3144 */     for (int i = 0; i < array.length; i++) {
/* 3145 */       result[i] = Integer.valueOf(array[i]);
/*      */     }
/* 3147 */     return result;
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
/*      */   public static short[] toPrimitive(Short[] array) {
/* 3162 */     if (array == null)
/* 3163 */       return null; 
/* 3164 */     if (array.length == 0) {
/* 3165 */       return EMPTY_SHORT_ARRAY;
/*      */     }
/* 3167 */     short[] result = new short[array.length];
/* 3168 */     for (int i = 0; i < array.length; i++) {
/* 3169 */       result[i] = array[i].shortValue();
/*      */     }
/* 3171 */     return result;
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
/*      */   public static short[] toPrimitive(Short[] array, short valueForNull) {
/* 3184 */     if (array == null)
/* 3185 */       return null; 
/* 3186 */     if (array.length == 0) {
/* 3187 */       return EMPTY_SHORT_ARRAY;
/*      */     }
/* 3189 */     short[] result = new short[array.length];
/* 3190 */     for (int i = 0; i < array.length; i++) {
/* 3191 */       Short b = array[i];
/* 3192 */       result[i] = (b == null) ? valueForNull : b.shortValue();
/*      */     } 
/* 3194 */     return result;
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
/*      */   public static Short[] toObject(short[] array) {
/* 3206 */     if (array == null)
/* 3207 */       return null; 
/* 3208 */     if (array.length == 0) {
/* 3209 */       return EMPTY_SHORT_OBJECT_ARRAY;
/*      */     }
/* 3211 */     Short[] result = new Short[array.length];
/* 3212 */     for (int i = 0; i < array.length; i++) {
/* 3213 */       result[i] = Short.valueOf(array[i]);
/*      */     }
/* 3215 */     return result;
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
/*      */   public static byte[] toPrimitive(Byte[] array) {
/* 3230 */     if (array == null)
/* 3231 */       return null; 
/* 3232 */     if (array.length == 0) {
/* 3233 */       return EMPTY_BYTE_ARRAY;
/*      */     }
/* 3235 */     byte[] result = new byte[array.length];
/* 3236 */     for (int i = 0; i < array.length; i++) {
/* 3237 */       result[i] = array[i].byteValue();
/*      */     }
/* 3239 */     return result;
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
/*      */   public static byte[] toPrimitive(Byte[] array, byte valueForNull) {
/* 3252 */     if (array == null)
/* 3253 */       return null; 
/* 3254 */     if (array.length == 0) {
/* 3255 */       return EMPTY_BYTE_ARRAY;
/*      */     }
/* 3257 */     byte[] result = new byte[array.length];
/* 3258 */     for (int i = 0; i < array.length; i++) {
/* 3259 */       Byte b = array[i];
/* 3260 */       result[i] = (b == null) ? valueForNull : b.byteValue();
/*      */     } 
/* 3262 */     return result;
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
/*      */   public static Byte[] toObject(byte[] array) {
/* 3274 */     if (array == null)
/* 3275 */       return null; 
/* 3276 */     if (array.length == 0) {
/* 3277 */       return EMPTY_BYTE_OBJECT_ARRAY;
/*      */     }
/* 3279 */     Byte[] result = new Byte[array.length];
/* 3280 */     for (int i = 0; i < array.length; i++) {
/* 3281 */       result[i] = Byte.valueOf(array[i]);
/*      */     }
/* 3283 */     return result;
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
/*      */   public static double[] toPrimitive(Double[] array) {
/* 3298 */     if (array == null)
/* 3299 */       return null; 
/* 3300 */     if (array.length == 0) {
/* 3301 */       return EMPTY_DOUBLE_ARRAY;
/*      */     }
/* 3303 */     double[] result = new double[array.length];
/* 3304 */     for (int i = 0; i < array.length; i++) {
/* 3305 */       result[i] = array[i].doubleValue();
/*      */     }
/* 3307 */     return result;
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
/*      */   public static double[] toPrimitive(Double[] array, double valueForNull) {
/* 3320 */     if (array == null)
/* 3321 */       return null; 
/* 3322 */     if (array.length == 0) {
/* 3323 */       return EMPTY_DOUBLE_ARRAY;
/*      */     }
/* 3325 */     double[] result = new double[array.length];
/* 3326 */     for (int i = 0; i < array.length; i++) {
/* 3327 */       Double b = array[i];
/* 3328 */       result[i] = (b == null) ? valueForNull : b.doubleValue();
/*      */     } 
/* 3330 */     return result;
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
/*      */   public static Double[] toObject(double[] array) {
/* 3342 */     if (array == null)
/* 3343 */       return null; 
/* 3344 */     if (array.length == 0) {
/* 3345 */       return EMPTY_DOUBLE_OBJECT_ARRAY;
/*      */     }
/* 3347 */     Double[] result = new Double[array.length];
/* 3348 */     for (int i = 0; i < array.length; i++) {
/* 3349 */       result[i] = Double.valueOf(array[i]);
/*      */     }
/* 3351 */     return result;
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
/*      */   public static float[] toPrimitive(Float[] array) {
/* 3366 */     if (array == null)
/* 3367 */       return null; 
/* 3368 */     if (array.length == 0) {
/* 3369 */       return EMPTY_FLOAT_ARRAY;
/*      */     }
/* 3371 */     float[] result = new float[array.length];
/* 3372 */     for (int i = 0; i < array.length; i++) {
/* 3373 */       result[i] = array[i].floatValue();
/*      */     }
/* 3375 */     return result;
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
/*      */   public static float[] toPrimitive(Float[] array, float valueForNull) {
/* 3388 */     if (array == null)
/* 3389 */       return null; 
/* 3390 */     if (array.length == 0) {
/* 3391 */       return EMPTY_FLOAT_ARRAY;
/*      */     }
/* 3393 */     float[] result = new float[array.length];
/* 3394 */     for (int i = 0; i < array.length; i++) {
/* 3395 */       Float b = array[i];
/* 3396 */       result[i] = (b == null) ? valueForNull : b.floatValue();
/*      */     } 
/* 3398 */     return result;
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
/*      */   public static Float[] toObject(float[] array) {
/* 3410 */     if (array == null)
/* 3411 */       return null; 
/* 3412 */     if (array.length == 0) {
/* 3413 */       return EMPTY_FLOAT_OBJECT_ARRAY;
/*      */     }
/* 3415 */     Float[] result = new Float[array.length];
/* 3416 */     for (int i = 0; i < array.length; i++) {
/* 3417 */       result[i] = Float.valueOf(array[i]);
/*      */     }
/* 3419 */     return result;
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
/*      */   public static boolean[] toPrimitive(Boolean[] array) {
/* 3434 */     if (array == null)
/* 3435 */       return null; 
/* 3436 */     if (array.length == 0) {
/* 3437 */       return EMPTY_BOOLEAN_ARRAY;
/*      */     }
/* 3439 */     boolean[] result = new boolean[array.length];
/* 3440 */     for (int i = 0; i < array.length; i++) {
/* 3441 */       result[i] = array[i].booleanValue();
/*      */     }
/* 3443 */     return result;
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
/*      */   public static boolean[] toPrimitive(Boolean[] array, boolean valueForNull) {
/* 3456 */     if (array == null)
/* 3457 */       return null; 
/* 3458 */     if (array.length == 0) {
/* 3459 */       return EMPTY_BOOLEAN_ARRAY;
/*      */     }
/* 3461 */     boolean[] result = new boolean[array.length];
/* 3462 */     for (int i = 0; i < array.length; i++) {
/* 3463 */       Boolean b = array[i];
/* 3464 */       result[i] = (b == null) ? valueForNull : b.booleanValue();
/*      */     } 
/* 3466 */     return result;
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
/*      */   public static Boolean[] toObject(boolean[] array) {
/* 3478 */     if (array == null)
/* 3479 */       return null; 
/* 3480 */     if (array.length == 0) {
/* 3481 */       return EMPTY_BOOLEAN_OBJECT_ARRAY;
/*      */     }
/* 3483 */     Boolean[] result = new Boolean[array.length];
/* 3484 */     for (int i = 0; i < array.length; i++) {
/* 3485 */       result[i] = array[i] ? Boolean.TRUE : Boolean.FALSE;
/*      */     }
/* 3487 */     return result;
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
/*      */   public static boolean isEmpty(Object[] array) {
/* 3499 */     return (array == null || array.length == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isEmpty(long[] array) {
/* 3510 */     return (array == null || array.length == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isEmpty(int[] array) {
/* 3521 */     return (array == null || array.length == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isEmpty(short[] array) {
/* 3532 */     return (array == null || array.length == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isEmpty(char[] array) {
/* 3543 */     return (array == null || array.length == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isEmpty(byte[] array) {
/* 3554 */     return (array == null || array.length == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isEmpty(double[] array) {
/* 3565 */     return (array == null || array.length == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isEmpty(float[] array) {
/* 3576 */     return (array == null || array.length == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isEmpty(boolean[] array) {
/* 3587 */     return (array == null || array.length == 0);
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
/*      */   public static <T> boolean isNotEmpty(T[] array) {
/* 3600 */     return (array != null && array.length != 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isNotEmpty(long[] array) {
/* 3611 */     return (array != null && array.length != 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isNotEmpty(int[] array) {
/* 3622 */     return (array != null && array.length != 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isNotEmpty(short[] array) {
/* 3633 */     return (array != null && array.length != 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isNotEmpty(char[] array) {
/* 3644 */     return (array != null && array.length != 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isNotEmpty(byte[] array) {
/* 3655 */     return (array != null && array.length != 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isNotEmpty(double[] array) {
/* 3666 */     return (array != null && array.length != 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isNotEmpty(float[] array) {
/* 3677 */     return (array != null && array.length != 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isNotEmpty(boolean[] array) {
/* 3688 */     return (array != null && array.length != 0);
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
/*      */   public static <T> T[] addAll(T[] array1, T... array2) {
/* 3716 */     if (array1 == null)
/* 3717 */       return clone(array2); 
/* 3718 */     if (array2 == null) {
/* 3719 */       return clone(array1);
/*      */     }
/* 3721 */     Class<?> type1 = array1.getClass().getComponentType();
/*      */ 
/*      */     
/* 3724 */     T[] joinedArray = (T[])Array.newInstance(type1, array1.length + array2.length);
/* 3725 */     System.arraycopy(array1, 0, joinedArray, 0, array1.length);
/*      */     try {
/* 3727 */       System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
/* 3728 */     } catch (ArrayStoreException ase) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 3735 */       Class<?> type2 = array2.getClass().getComponentType();
/* 3736 */       if (!type1.isAssignableFrom(type2)) {
/* 3737 */         throw new IllegalArgumentException("Cannot store " + type2.getName() + " in an array of " + type1.getName(), ase);
/*      */       }
/*      */       
/* 3740 */       throw ase;
/*      */     } 
/* 3742 */     return joinedArray;
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
/*      */   public static boolean[] addAll(boolean[] array1, boolean... array2) {
/* 3763 */     if (array1 == null)
/* 3764 */       return clone(array2); 
/* 3765 */     if (array2 == null) {
/* 3766 */       return clone(array1);
/*      */     }
/* 3768 */     boolean[] joinedArray = new boolean[array1.length + array2.length];
/* 3769 */     System.arraycopy(array1, 0, joinedArray, 0, array1.length);
/* 3770 */     System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
/* 3771 */     return joinedArray;
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
/*      */   public static char[] addAll(char[] array1, char... array2) {
/* 3792 */     if (array1 == null)
/* 3793 */       return clone(array2); 
/* 3794 */     if (array2 == null) {
/* 3795 */       return clone(array1);
/*      */     }
/* 3797 */     char[] joinedArray = new char[array1.length + array2.length];
/* 3798 */     System.arraycopy(array1, 0, joinedArray, 0, array1.length);
/* 3799 */     System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
/* 3800 */     return joinedArray;
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
/*      */   public static byte[] addAll(byte[] array1, byte... array2) {
/* 3821 */     if (array1 == null)
/* 3822 */       return clone(array2); 
/* 3823 */     if (array2 == null) {
/* 3824 */       return clone(array1);
/*      */     }
/* 3826 */     byte[] joinedArray = new byte[array1.length + array2.length];
/* 3827 */     System.arraycopy(array1, 0, joinedArray, 0, array1.length);
/* 3828 */     System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
/* 3829 */     return joinedArray;
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
/*      */   public static short[] addAll(short[] array1, short... array2) {
/* 3850 */     if (array1 == null)
/* 3851 */       return clone(array2); 
/* 3852 */     if (array2 == null) {
/* 3853 */       return clone(array1);
/*      */     }
/* 3855 */     short[] joinedArray = new short[array1.length + array2.length];
/* 3856 */     System.arraycopy(array1, 0, joinedArray, 0, array1.length);
/* 3857 */     System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
/* 3858 */     return joinedArray;
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
/*      */   public static int[] addAll(int[] array1, int... array2) {
/* 3879 */     if (array1 == null)
/* 3880 */       return clone(array2); 
/* 3881 */     if (array2 == null) {
/* 3882 */       return clone(array1);
/*      */     }
/* 3884 */     int[] joinedArray = new int[array1.length + array2.length];
/* 3885 */     System.arraycopy(array1, 0, joinedArray, 0, array1.length);
/* 3886 */     System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
/* 3887 */     return joinedArray;
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
/*      */   public static long[] addAll(long[] array1, long... array2) {
/* 3908 */     if (array1 == null)
/* 3909 */       return clone(array2); 
/* 3910 */     if (array2 == null) {
/* 3911 */       return clone(array1);
/*      */     }
/* 3913 */     long[] joinedArray = new long[array1.length + array2.length];
/* 3914 */     System.arraycopy(array1, 0, joinedArray, 0, array1.length);
/* 3915 */     System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
/* 3916 */     return joinedArray;
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
/*      */   public static float[] addAll(float[] array1, float... array2) {
/* 3937 */     if (array1 == null)
/* 3938 */       return clone(array2); 
/* 3939 */     if (array2 == null) {
/* 3940 */       return clone(array1);
/*      */     }
/* 3942 */     float[] joinedArray = new float[array1.length + array2.length];
/* 3943 */     System.arraycopy(array1, 0, joinedArray, 0, array1.length);
/* 3944 */     System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
/* 3945 */     return joinedArray;
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
/*      */   public static double[] addAll(double[] array1, double... array2) {
/* 3966 */     if (array1 == null)
/* 3967 */       return clone(array2); 
/* 3968 */     if (array2 == null) {
/* 3969 */       return clone(array1);
/*      */     }
/* 3971 */     double[] joinedArray = new double[array1.length + array2.length];
/* 3972 */     System.arraycopy(array1, 0, joinedArray, 0, array1.length);
/* 3973 */     System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
/* 3974 */     return joinedArray;
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
/*      */   public static <T> T[] add(T[] array, T element) {
/*      */     Class<?> type;
/* 4008 */     if (array != null) {
/* 4009 */       type = array.getClass();
/* 4010 */     } else if (element != null) {
/* 4011 */       type = element.getClass();
/*      */     } else {
/* 4013 */       throw new IllegalArgumentException("Arguments cannot both be null");
/*      */     } 
/*      */ 
/*      */     
/* 4017 */     T[] newArray = (T[])copyArrayGrow1(array, type);
/* 4018 */     newArray[newArray.length - 1] = element;
/* 4019 */     return newArray;
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
/*      */   public static boolean[] add(boolean[] array, boolean element) {
/* 4044 */     boolean[] newArray = (boolean[])copyArrayGrow1(array, boolean.class);
/* 4045 */     newArray[newArray.length - 1] = element;
/* 4046 */     return newArray;
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
/*      */   public static byte[] add(byte[] array, byte element) {
/* 4071 */     byte[] newArray = (byte[])copyArrayGrow1(array, byte.class);
/* 4072 */     newArray[newArray.length - 1] = element;
/* 4073 */     return newArray;
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
/*      */   public static char[] add(char[] array, char element) {
/* 4098 */     char[] newArray = (char[])copyArrayGrow1(array, char.class);
/* 4099 */     newArray[newArray.length - 1] = element;
/* 4100 */     return newArray;
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
/*      */   public static double[] add(double[] array, double element) {
/* 4125 */     double[] newArray = (double[])copyArrayGrow1(array, double.class);
/* 4126 */     newArray[newArray.length - 1] = element;
/* 4127 */     return newArray;
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
/*      */   public static float[] add(float[] array, float element) {
/* 4152 */     float[] newArray = (float[])copyArrayGrow1(array, float.class);
/* 4153 */     newArray[newArray.length - 1] = element;
/* 4154 */     return newArray;
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
/*      */   public static int[] add(int[] array, int element) {
/* 4179 */     int[] newArray = (int[])copyArrayGrow1(array, int.class);
/* 4180 */     newArray[newArray.length - 1] = element;
/* 4181 */     return newArray;
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
/*      */   public static long[] add(long[] array, long element) {
/* 4206 */     long[] newArray = (long[])copyArrayGrow1(array, long.class);
/* 4207 */     newArray[newArray.length - 1] = element;
/* 4208 */     return newArray;
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
/*      */   public static short[] add(short[] array, short element) {
/* 4233 */     short[] newArray = (short[])copyArrayGrow1(array, short.class);
/* 4234 */     newArray[newArray.length - 1] = element;
/* 4235 */     return newArray;
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
/*      */   private static Object copyArrayGrow1(Object array, Class<?> newArrayComponentType) {
/* 4248 */     if (array != null) {
/* 4249 */       int arrayLength = Array.getLength(array);
/* 4250 */       Object newArray = Array.newInstance(array.getClass().getComponentType(), arrayLength + 1);
/* 4251 */       System.arraycopy(array, 0, newArray, 0, arrayLength);
/* 4252 */       return newArray;
/*      */     } 
/* 4254 */     return Array.newInstance(newArrayComponentType, 1);
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
/*      */   public static <T> T[] add(T[] array, int index, T element) {
/* 4288 */     Class<?> clss = null;
/* 4289 */     if (array != null) {
/* 4290 */       clss = array.getClass().getComponentType();
/* 4291 */     } else if (element != null) {
/* 4292 */       clss = element.getClass();
/*      */     } else {
/* 4294 */       throw new IllegalArgumentException("Array and element cannot both be null");
/*      */     } 
/*      */     
/* 4297 */     T[] newArray = (T[])add(array, index, element, clss);
/* 4298 */     return newArray;
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
/*      */   public static boolean[] add(boolean[] array, int index, boolean element) {
/* 4329 */     return (boolean[])add(array, index, Boolean.valueOf(element), boolean.class);
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
/*      */   public static char[] add(char[] array, int index, char element) {
/* 4361 */     return (char[])add(array, index, Character.valueOf(element), char.class);
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
/*      */   public static byte[] add(byte[] array, int index, byte element) {
/* 4392 */     return (byte[])add(array, index, Byte.valueOf(element), byte.class);
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
/*      */   public static short[] add(short[] array, int index, short element) {
/* 4423 */     return (short[])add(array, index, Short.valueOf(element), short.class);
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
/*      */   public static int[] add(int[] array, int index, int element) {
/* 4454 */     return (int[])add(array, index, Integer.valueOf(element), int.class);
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
/*      */   public static long[] add(long[] array, int index, long element) {
/* 4485 */     return (long[])add(array, index, Long.valueOf(element), long.class);
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
/*      */   public static float[] add(float[] array, int index, float element) {
/* 4516 */     return (float[])add(array, index, Float.valueOf(element), float.class);
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
/*      */   public static double[] add(double[] array, int index, double element) {
/* 4547 */     return (double[])add(array, index, Double.valueOf(element), double.class);
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
/*      */   private static Object add(Object array, int index, Object element, Class<?> clss) {
/* 4562 */     if (array == null) {
/* 4563 */       if (index != 0) {
/* 4564 */         throw new IndexOutOfBoundsException("Index: " + index + ", Length: 0");
/*      */       }
/* 4566 */       Object joinedArray = Array.newInstance(clss, 1);
/* 4567 */       Array.set(joinedArray, 0, element);
/* 4568 */       return joinedArray;
/*      */     } 
/* 4570 */     int length = Array.getLength(array);
/* 4571 */     if (index > length || index < 0) {
/* 4572 */       throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + length);
/*      */     }
/* 4574 */     Object result = Array.newInstance(clss, length + 1);
/* 4575 */     System.arraycopy(array, 0, result, 0, index);
/* 4576 */     Array.set(result, index, element);
/* 4577 */     if (index < length) {
/* 4578 */       System.arraycopy(array, index, result, index + 1, length - index);
/*      */     }
/* 4580 */     return result;
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
/*      */   public static <T> T[] remove(T[] array, int index) {
/* 4614 */     return (T[])remove(array, index);
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
/*      */   public static <T> T[] removeElement(T[] array, Object element) {
/* 4644 */     int index = indexOf((Object[])array, element);
/* 4645 */     if (index == -1) {
/* 4646 */       return clone(array);
/*      */     }
/* 4648 */     return remove(array, index);
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
/*      */   public static boolean[] remove(boolean[] array, int index) {
/* 4680 */     return (boolean[])remove(array, index);
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
/*      */   public static boolean[] removeElement(boolean[] array, boolean element) {
/* 4709 */     int index = indexOf(array, element);
/* 4710 */     if (index == -1) {
/* 4711 */       return clone(array);
/*      */     }
/* 4713 */     return remove(array, index);
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
/*      */   public static byte[] remove(byte[] array, int index) {
/* 4745 */     return (byte[])remove(array, index);
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
/*      */   public static byte[] removeElement(byte[] array, byte element) {
/* 4774 */     int index = indexOf(array, element);
/* 4775 */     if (index == -1) {
/* 4776 */       return clone(array);
/*      */     }
/* 4778 */     return remove(array, index);
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
/*      */   public static char[] remove(char[] array, int index) {
/* 4810 */     return (char[])remove(array, index);
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
/*      */   public static char[] removeElement(char[] array, char element) {
/* 4839 */     int index = indexOf(array, element);
/* 4840 */     if (index == -1) {
/* 4841 */       return clone(array);
/*      */     }
/* 4843 */     return remove(array, index);
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
/*      */   public static double[] remove(double[] array, int index) {
/* 4875 */     return (double[])remove(array, index);
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
/*      */   public static double[] removeElement(double[] array, double element) {
/* 4904 */     int index = indexOf(array, element);
/* 4905 */     if (index == -1) {
/* 4906 */       return clone(array);
/*      */     }
/* 4908 */     return remove(array, index);
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
/*      */   public static float[] remove(float[] array, int index) {
/* 4940 */     return (float[])remove(array, index);
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
/*      */   public static float[] removeElement(float[] array, float element) {
/* 4969 */     int index = indexOf(array, element);
/* 4970 */     if (index == -1) {
/* 4971 */       return clone(array);
/*      */     }
/* 4973 */     return remove(array, index);
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
/*      */   public static int[] remove(int[] array, int index) {
/* 5005 */     return (int[])remove(array, index);
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
/*      */   public static int[] removeElement(int[] array, int element) {
/* 5034 */     int index = indexOf(array, element);
/* 5035 */     if (index == -1) {
/* 5036 */       return clone(array);
/*      */     }
/* 5038 */     return remove(array, index);
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
/*      */   public static long[] remove(long[] array, int index) {
/* 5070 */     return (long[])remove(array, index);
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
/*      */   public static long[] removeElement(long[] array, long element) {
/* 5099 */     int index = indexOf(array, element);
/* 5100 */     if (index == -1) {
/* 5101 */       return clone(array);
/*      */     }
/* 5103 */     return remove(array, index);
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
/*      */   public static short[] remove(short[] array, int index) {
/* 5135 */     return (short[])remove(array, index);
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
/*      */   public static short[] removeElement(short[] array, short element) {
/* 5164 */     int index = indexOf(array, element);
/* 5165 */     if (index == -1) {
/* 5166 */       return clone(array);
/*      */     }
/* 5168 */     return remove(array, index);
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
/*      */   private static Object remove(Object array, int index) {
/* 5193 */     int length = getLength(array);
/* 5194 */     if (index < 0 || index >= length) {
/* 5195 */       throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + length);
/*      */     }
/*      */     
/* 5198 */     Object result = Array.newInstance(array.getClass().getComponentType(), length - 1);
/* 5199 */     System.arraycopy(array, 0, result, 0, index);
/* 5200 */     if (index < length - 1) {
/* 5201 */       System.arraycopy(array, index + 1, result, index, length - index - 1);
/*      */     }
/*      */     
/* 5204 */     return result;
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
/*      */   public static <T> T[] removeAll(T[] array, int... indices) {
/* 5235 */     return (T[])removeAll(array, clone(indices));
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
/*      */   public static <T> T[] removeElements(T[] array, T... values) {
/* 5267 */     if (isEmpty((Object[])array) || isEmpty((Object[])values)) {
/* 5268 */       return clone(array);
/*      */     }
/* 5270 */     HashMap<T, MutableInt> occurrences = new HashMap<T, MutableInt>(values.length);
/* 5271 */     for (T v : values) {
/* 5272 */       MutableInt count = occurrences.get(v);
/* 5273 */       if (count == null) {
/* 5274 */         occurrences.put(v, new MutableInt(1));
/*      */       } else {
/* 5276 */         count.increment();
/*      */       } 
/*      */     } 
/* 5279 */     BitSet toRemove = new BitSet();
/* 5280 */     for (Map.Entry<T, MutableInt> e : occurrences.entrySet()) {
/* 5281 */       T v = e.getKey();
/* 5282 */       int found = 0;
/* 5283 */       for (int i = 0, ct = ((MutableInt)e.getValue()).intValue(); i < ct; i++) {
/* 5284 */         found = indexOf((Object[])array, v, found);
/* 5285 */         if (found < 0) {
/*      */           break;
/*      */         }
/* 5288 */         toRemove.set(found++);
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 5293 */     T[] result = (T[])removeAll(array, toRemove);
/* 5294 */     return result;
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
/*      */   public static byte[] removeAll(byte[] array, int... indices) {
/* 5327 */     return (byte[])removeAll(array, clone(indices));
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
/*      */   public static byte[] removeElements(byte[] array, byte... values) {
/* 5358 */     if (isEmpty(array) || isEmpty(values)) {
/* 5359 */       return clone(array);
/*      */     }
/* 5361 */     HashMap<Byte, MutableInt> occurrences = new HashMap<Byte, MutableInt>(values.length);
/* 5362 */     for (byte v : values) {
/* 5363 */       Byte boxed = Byte.valueOf(v);
/* 5364 */       MutableInt count = occurrences.get(boxed);
/* 5365 */       if (count == null) {
/* 5366 */         occurrences.put(boxed, new MutableInt(1));
/*      */       } else {
/* 5368 */         count.increment();
/*      */       } 
/*      */     } 
/* 5371 */     BitSet toRemove = new BitSet();
/* 5372 */     for (Map.Entry<Byte, MutableInt> e : occurrences.entrySet()) {
/* 5373 */       Byte v = e.getKey();
/* 5374 */       int found = 0;
/* 5375 */       for (int i = 0, ct = ((MutableInt)e.getValue()).intValue(); i < ct; i++) {
/* 5376 */         found = indexOf(array, v.byteValue(), found);
/* 5377 */         if (found < 0) {
/*      */           break;
/*      */         }
/* 5380 */         toRemove.set(found++);
/*      */       } 
/*      */     } 
/* 5383 */     return (byte[])removeAll(array, toRemove);
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
/*      */   public static short[] removeAll(short[] array, int... indices) {
/* 5416 */     return (short[])removeAll(array, clone(indices));
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
/*      */   public static short[] removeElements(short[] array, short... values) {
/* 5447 */     if (isEmpty(array) || isEmpty(values)) {
/* 5448 */       return clone(array);
/*      */     }
/* 5450 */     HashMap<Short, MutableInt> occurrences = new HashMap<Short, MutableInt>(values.length);
/* 5451 */     for (short v : values) {
/* 5452 */       Short boxed = Short.valueOf(v);
/* 5453 */       MutableInt count = occurrences.get(boxed);
/* 5454 */       if (count == null) {
/* 5455 */         occurrences.put(boxed, new MutableInt(1));
/*      */       } else {
/* 5457 */         count.increment();
/*      */       } 
/*      */     } 
/* 5460 */     BitSet toRemove = new BitSet();
/* 5461 */     for (Map.Entry<Short, MutableInt> e : occurrences.entrySet()) {
/* 5462 */       Short v = e.getKey();
/* 5463 */       int found = 0;
/* 5464 */       for (int i = 0, ct = ((MutableInt)e.getValue()).intValue(); i < ct; i++) {
/* 5465 */         found = indexOf(array, v.shortValue(), found);
/* 5466 */         if (found < 0) {
/*      */           break;
/*      */         }
/* 5469 */         toRemove.set(found++);
/*      */       } 
/*      */     } 
/* 5472 */     return (short[])removeAll(array, toRemove);
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
/*      */   public static int[] removeAll(int[] array, int... indices) {
/* 5505 */     return (int[])removeAll(array, clone(indices));
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
/*      */   public static int[] removeElements(int[] array, int... values) {
/* 5536 */     if (isEmpty(array) || isEmpty(values)) {
/* 5537 */       return clone(array);
/*      */     }
/* 5539 */     HashMap<Integer, MutableInt> occurrences = new HashMap<Integer, MutableInt>(values.length);
/* 5540 */     for (int v : values) {
/* 5541 */       Integer boxed = Integer.valueOf(v);
/* 5542 */       MutableInt count = occurrences.get(boxed);
/* 5543 */       if (count == null) {
/* 5544 */         occurrences.put(boxed, new MutableInt(1));
/*      */       } else {
/* 5546 */         count.increment();
/*      */       } 
/*      */     } 
/* 5549 */     BitSet toRemove = new BitSet();
/* 5550 */     for (Map.Entry<Integer, MutableInt> e : occurrences.entrySet()) {
/* 5551 */       Integer v = e.getKey();
/* 5552 */       int found = 0;
/* 5553 */       for (int i = 0, ct = ((MutableInt)e.getValue()).intValue(); i < ct; i++) {
/* 5554 */         found = indexOf(array, v.intValue(), found);
/* 5555 */         if (found < 0) {
/*      */           break;
/*      */         }
/* 5558 */         toRemove.set(found++);
/*      */       } 
/*      */     } 
/* 5561 */     return (int[])removeAll(array, toRemove);
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
/*      */   public static char[] removeAll(char[] array, int... indices) {
/* 5594 */     return (char[])removeAll(array, clone(indices));
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
/*      */   public static char[] removeElements(char[] array, char... values) {
/* 5625 */     if (isEmpty(array) || isEmpty(values)) {
/* 5626 */       return clone(array);
/*      */     }
/* 5628 */     HashMap<Character, MutableInt> occurrences = new HashMap<Character, MutableInt>(values.length);
/* 5629 */     for (char v : values) {
/* 5630 */       Character boxed = Character.valueOf(v);
/* 5631 */       MutableInt count = occurrences.get(boxed);
/* 5632 */       if (count == null) {
/* 5633 */         occurrences.put(boxed, new MutableInt(1));
/*      */       } else {
/* 5635 */         count.increment();
/*      */       } 
/*      */     } 
/* 5638 */     BitSet toRemove = new BitSet();
/* 5639 */     for (Map.Entry<Character, MutableInt> e : occurrences.entrySet()) {
/* 5640 */       Character v = e.getKey();
/* 5641 */       int found = 0;
/* 5642 */       for (int i = 0, ct = ((MutableInt)e.getValue()).intValue(); i < ct; i++) {
/* 5643 */         found = indexOf(array, v.charValue(), found);
/* 5644 */         if (found < 0) {
/*      */           break;
/*      */         }
/* 5647 */         toRemove.set(found++);
/*      */       } 
/*      */     } 
/* 5650 */     return (char[])removeAll(array, toRemove);
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
/*      */   public static long[] removeAll(long[] array, int... indices) {
/* 5683 */     return (long[])removeAll(array, clone(indices));
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
/*      */   public static long[] removeElements(long[] array, long... values) {
/* 5714 */     if (isEmpty(array) || isEmpty(values)) {
/* 5715 */       return clone(array);
/*      */     }
/* 5717 */     HashMap<Long, MutableInt> occurrences = new HashMap<Long, MutableInt>(values.length);
/* 5718 */     for (long v : values) {
/* 5719 */       Long boxed = Long.valueOf(v);
/* 5720 */       MutableInt count = occurrences.get(boxed);
/* 5721 */       if (count == null) {
/* 5722 */         occurrences.put(boxed, new MutableInt(1));
/*      */       } else {
/* 5724 */         count.increment();
/*      */       } 
/*      */     } 
/* 5727 */     BitSet toRemove = new BitSet();
/* 5728 */     for (Map.Entry<Long, MutableInt> e : occurrences.entrySet()) {
/* 5729 */       Long v = e.getKey();
/* 5730 */       int found = 0;
/* 5731 */       for (int i = 0, ct = ((MutableInt)e.getValue()).intValue(); i < ct; i++) {
/* 5732 */         found = indexOf(array, v.longValue(), found);
/* 5733 */         if (found < 0) {
/*      */           break;
/*      */         }
/* 5736 */         toRemove.set(found++);
/*      */       } 
/*      */     } 
/* 5739 */     return (long[])removeAll(array, toRemove);
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
/*      */   public static float[] removeAll(float[] array, int... indices) {
/* 5772 */     return (float[])removeAll(array, clone(indices));
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
/*      */   public static float[] removeElements(float[] array, float... values) {
/* 5803 */     if (isEmpty(array) || isEmpty(values)) {
/* 5804 */       return clone(array);
/*      */     }
/* 5806 */     HashMap<Float, MutableInt> occurrences = new HashMap<Float, MutableInt>(values.length);
/* 5807 */     for (float v : values) {
/* 5808 */       Float boxed = Float.valueOf(v);
/* 5809 */       MutableInt count = occurrences.get(boxed);
/* 5810 */       if (count == null) {
/* 5811 */         occurrences.put(boxed, new MutableInt(1));
/*      */       } else {
/* 5813 */         count.increment();
/*      */       } 
/*      */     } 
/* 5816 */     BitSet toRemove = new BitSet();
/* 5817 */     for (Map.Entry<Float, MutableInt> e : occurrences.entrySet()) {
/* 5818 */       Float v = e.getKey();
/* 5819 */       int found = 0;
/* 5820 */       for (int i = 0, ct = ((MutableInt)e.getValue()).intValue(); i < ct; i++) {
/* 5821 */         found = indexOf(array, v.floatValue(), found);
/* 5822 */         if (found < 0) {
/*      */           break;
/*      */         }
/* 5825 */         toRemove.set(found++);
/*      */       } 
/*      */     } 
/* 5828 */     return (float[])removeAll(array, toRemove);
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
/*      */   public static double[] removeAll(double[] array, int... indices) {
/* 5861 */     return (double[])removeAll(array, clone(indices));
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
/*      */   public static double[] removeElements(double[] array, double... values) {
/* 5892 */     if (isEmpty(array) || isEmpty(values)) {
/* 5893 */       return clone(array);
/*      */     }
/* 5895 */     HashMap<Double, MutableInt> occurrences = new HashMap<Double, MutableInt>(values.length);
/* 5896 */     for (double v : values) {
/* 5897 */       Double boxed = Double.valueOf(v);
/* 5898 */       MutableInt count = occurrences.get(boxed);
/* 5899 */       if (count == null) {
/* 5900 */         occurrences.put(boxed, new MutableInt(1));
/*      */       } else {
/* 5902 */         count.increment();
/*      */       } 
/*      */     } 
/* 5905 */     BitSet toRemove = new BitSet();
/* 5906 */     for (Map.Entry<Double, MutableInt> e : occurrences.entrySet()) {
/* 5907 */       Double v = e.getKey();
/* 5908 */       int found = 0;
/* 5909 */       for (int i = 0, ct = ((MutableInt)e.getValue()).intValue(); i < ct; i++) {
/* 5910 */         found = indexOf(array, v.doubleValue(), found);
/* 5911 */         if (found < 0) {
/*      */           break;
/*      */         }
/* 5914 */         toRemove.set(found++);
/*      */       } 
/*      */     } 
/* 5917 */     return (double[])removeAll(array, toRemove);
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
/*      */   public static boolean[] removeAll(boolean[] array, int... indices) {
/* 5946 */     return (boolean[])removeAll(array, clone(indices));
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
/*      */   public static boolean[] removeElements(boolean[] array, boolean... values) {
/* 5977 */     if (isEmpty(array) || isEmpty(values)) {
/* 5978 */       return clone(array);
/*      */     }
/* 5980 */     HashMap<Boolean, MutableInt> occurrences = new HashMap<Boolean, MutableInt>(2);
/* 5981 */     for (boolean v : values) {
/* 5982 */       Boolean boxed = Boolean.valueOf(v);
/* 5983 */       MutableInt count = occurrences.get(boxed);
/* 5984 */       if (count == null) {
/* 5985 */         occurrences.put(boxed, new MutableInt(1));
/*      */       } else {
/* 5987 */         count.increment();
/*      */       } 
/*      */     } 
/* 5990 */     BitSet toRemove = new BitSet();
/* 5991 */     for (Map.Entry<Boolean, MutableInt> e : occurrences.entrySet()) {
/* 5992 */       Boolean v = e.getKey();
/* 5993 */       int found = 0;
/* 5994 */       for (int i = 0, ct = ((MutableInt)e.getValue()).intValue(); i < ct; i++) {
/* 5995 */         found = indexOf(array, v.booleanValue(), found);
/* 5996 */         if (found < 0) {
/*      */           break;
/*      */         }
/* 5999 */         toRemove.set(found++);
/*      */       } 
/*      */     } 
/* 6002 */     return (boolean[])removeAll(array, toRemove);
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
/*      */   static Object removeAll(Object array, int... indices) {
/* 6014 */     int length = getLength(array);
/* 6015 */     int diff = 0;
/*      */     
/* 6017 */     if (isNotEmpty(indices)) {
/* 6018 */       Arrays.sort(indices);
/*      */       
/* 6020 */       int i = indices.length;
/* 6021 */       int prevIndex = length;
/* 6022 */       while (--i >= 0) {
/* 6023 */         int index = indices[i];
/* 6024 */         if (index < 0 || index >= length) {
/* 6025 */           throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + length);
/*      */         }
/* 6027 */         if (index >= prevIndex) {
/*      */           continue;
/*      */         }
/* 6030 */         diff++;
/* 6031 */         prevIndex = index;
/*      */       } 
/*      */     } 
/* 6034 */     Object result = Array.newInstance(array.getClass().getComponentType(), length - diff);
/* 6035 */     if (diff < length) {
/* 6036 */       int end = length;
/* 6037 */       int dest = length - diff;
/* 6038 */       for (int i = indices.length - 1; i >= 0; i--) {
/* 6039 */         int index = indices[i];
/* 6040 */         if (end - index > 1) {
/* 6041 */           int cp = end - index - 1;
/* 6042 */           dest -= cp;
/* 6043 */           System.arraycopy(array, index + 1, result, dest, cp);
/*      */         } 
/*      */         
/* 6046 */         end = index;
/*      */       } 
/* 6048 */       if (end > 0) {
/* 6049 */         System.arraycopy(array, 0, result, 0, end);
/*      */       }
/*      */     } 
/* 6052 */     return result;
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
/*      */   static Object removeAll(Object array, BitSet indices) {
/* 6065 */     int srcLength = getLength(array);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 6072 */     int removals = indices.cardinality();
/* 6073 */     Object result = Array.newInstance(array.getClass().getComponentType(), srcLength - removals);
/* 6074 */     int srcIndex = 0;
/* 6075 */     int destIndex = 0;
/*      */     
/*      */     int set;
/* 6078 */     while ((set = indices.nextSetBit(srcIndex)) != -1) {
/* 6079 */       int i = set - srcIndex;
/* 6080 */       if (i > 0) {
/* 6081 */         System.arraycopy(array, srcIndex, result, destIndex, i);
/* 6082 */         destIndex += i;
/*      */       } 
/* 6084 */       srcIndex = indices.nextClearBit(set);
/*      */     } 
/* 6086 */     int count = srcLength - srcIndex;
/* 6087 */     if (count > 0) {
/* 6088 */       System.arraycopy(array, srcIndex, result, destIndex, count);
/*      */     }
/* 6090 */     return result;
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\org\apache\commons\lang3\ArrayUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */