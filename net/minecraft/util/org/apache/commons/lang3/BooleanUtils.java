/*      */ package net.minecraft.util.org.apache.commons.lang3;
/*      */ 
/*      */ import net.minecraft.util.org.apache.commons.lang3.math.NumberUtils;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class BooleanUtils
/*      */ {
/*      */   public static Boolean negate(Boolean bool) {
/*   64 */     if (bool == null) {
/*   65 */       return null;
/*      */     }
/*   67 */     return bool.booleanValue() ? Boolean.FALSE : Boolean.TRUE;
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
/*      */   public static boolean isTrue(Boolean bool) {
/*   87 */     return Boolean.TRUE.equals(bool);
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
/*      */   public static boolean isNotTrue(Boolean bool) {
/*  105 */     return !isTrue(bool);
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
/*      */   public static boolean isFalse(Boolean bool) {
/*  123 */     return Boolean.FALSE.equals(bool);
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
/*      */   public static boolean isNotFalse(Boolean bool) {
/*  141 */     return !isFalse(bool);
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
/*      */   public static boolean toBoolean(Boolean bool) {
/*  159 */     return (bool != null && bool.booleanValue());
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
/*      */   public static boolean toBooleanDefaultIfNull(Boolean bool, boolean valueIfNull) {
/*  176 */     if (bool == null) {
/*  177 */       return valueIfNull;
/*      */     }
/*  179 */     return bool.booleanValue();
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
/*      */   public static boolean toBoolean(int value) {
/*  199 */     return (value != 0);
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
/*      */   public static Boolean toBooleanObject(int value) {
/*  217 */     return (value == 0) ? Boolean.FALSE : Boolean.TRUE;
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
/*      */   public static Boolean toBooleanObject(Integer value) {
/*  239 */     if (value == null) {
/*  240 */       return null;
/*      */     }
/*  242 */     return (value.intValue() == 0) ? Boolean.FALSE : Boolean.TRUE;
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
/*      */   public static boolean toBoolean(int value, int trueValue, int falseValue) {
/*  262 */     if (value == trueValue) {
/*  263 */       return true;
/*      */     }
/*  265 */     if (value == falseValue) {
/*  266 */       return false;
/*      */     }
/*      */     
/*  269 */     throw new IllegalArgumentException("The Integer did not match either specified value");
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
/*      */   public static boolean toBoolean(Integer value, Integer trueValue, Integer falseValue) {
/*  290 */     if (value == null) {
/*  291 */       if (trueValue == null) {
/*  292 */         return true;
/*      */       }
/*  294 */       if (falseValue == null)
/*  295 */         return false; 
/*      */     } else {
/*  297 */       if (value.equals(trueValue))
/*  298 */         return true; 
/*  299 */       if (value.equals(falseValue)) {
/*  300 */         return false;
/*      */       }
/*      */     } 
/*  303 */     throw new IllegalArgumentException("The Integer did not match either specified value");
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
/*      */   public static Boolean toBooleanObject(int value, int trueValue, int falseValue, int nullValue) {
/*  325 */     if (value == trueValue) {
/*  326 */       return Boolean.TRUE;
/*      */     }
/*  328 */     if (value == falseValue) {
/*  329 */       return Boolean.FALSE;
/*      */     }
/*  331 */     if (value == nullValue) {
/*  332 */       return null;
/*      */     }
/*      */     
/*  335 */     throw new IllegalArgumentException("The Integer did not match any specified value");
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
/*      */   public static Boolean toBooleanObject(Integer value, Integer trueValue, Integer falseValue, Integer nullValue) {
/*  357 */     if (value == null) {
/*  358 */       if (trueValue == null) {
/*  359 */         return Boolean.TRUE;
/*      */       }
/*  361 */       if (falseValue == null) {
/*  362 */         return Boolean.FALSE;
/*      */       }
/*  364 */       if (nullValue == null)
/*  365 */         return null; 
/*      */     } else {
/*  367 */       if (value.equals(trueValue))
/*  368 */         return Boolean.TRUE; 
/*  369 */       if (value.equals(falseValue))
/*  370 */         return Boolean.FALSE; 
/*  371 */       if (value.equals(nullValue)) {
/*  372 */         return null;
/*      */       }
/*      */     } 
/*  375 */     throw new IllegalArgumentException("The Integer did not match any specified value");
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
/*      */   public static int toInteger(boolean bool) {
/*  393 */     return bool ? 1 : 0;
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
/*      */   public static Integer toIntegerObject(boolean bool) {
/*  409 */     return bool ? NumberUtils.INTEGER_ONE : NumberUtils.INTEGER_ZERO;
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
/*      */   public static Integer toIntegerObject(Boolean bool) {
/*  427 */     if (bool == null) {
/*  428 */       return null;
/*      */     }
/*  430 */     return bool.booleanValue() ? NumberUtils.INTEGER_ONE : NumberUtils.INTEGER_ZERO;
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
/*      */   public static int toInteger(boolean bool, int trueValue, int falseValue) {
/*  447 */     return bool ? trueValue : falseValue;
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
/*      */   public static int toInteger(Boolean bool, int trueValue, int falseValue, int nullValue) {
/*  466 */     if (bool == null) {
/*  467 */       return nullValue;
/*      */     }
/*  469 */     return bool.booleanValue() ? trueValue : falseValue;
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
/*      */   public static Integer toIntegerObject(boolean bool, Integer trueValue, Integer falseValue) {
/*  486 */     return bool ? trueValue : falseValue;
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
/*      */   public static Integer toIntegerObject(Boolean bool, Integer trueValue, Integer falseValue, Integer nullValue) {
/*  505 */     if (bool == null) {
/*  506 */       return nullValue;
/*      */     }
/*  508 */     return bool.booleanValue() ? trueValue : falseValue;
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
/*      */   public static Boolean toBooleanObject(String str) {
/*      */     char ch0;
/*      */     char ch1;
/*      */     char ch2;
/*      */     char ch3;
/*      */     char ch4;
/*  552 */     if (str == "true") {
/*  553 */       return Boolean.TRUE;
/*      */     }
/*  555 */     if (str == null) {
/*  556 */       return null;
/*      */     }
/*  558 */     switch (str.length()) {
/*      */       case 1:
/*  560 */         ch0 = str.charAt(0);
/*  561 */         if (ch0 == 'y' || ch0 == 'Y' || ch0 == 't' || ch0 == 'T')
/*      */         {
/*  563 */           return Boolean.TRUE;
/*      */         }
/*  565 */         if (ch0 == 'n' || ch0 == 'N' || ch0 == 'f' || ch0 == 'F')
/*      */         {
/*  567 */           return Boolean.FALSE;
/*      */         }
/*      */         break;
/*      */       
/*      */       case 2:
/*  572 */         ch0 = str.charAt(0);
/*  573 */         ch1 = str.charAt(1);
/*  574 */         if ((ch0 == 'o' || ch0 == 'O') && (ch1 == 'n' || ch1 == 'N'))
/*      */         {
/*  576 */           return Boolean.TRUE;
/*      */         }
/*  578 */         if ((ch0 == 'n' || ch0 == 'N') && (ch1 == 'o' || ch1 == 'O'))
/*      */         {
/*  580 */           return Boolean.FALSE;
/*      */         }
/*      */         break;
/*      */       
/*      */       case 3:
/*  585 */         ch0 = str.charAt(0);
/*  586 */         ch1 = str.charAt(1);
/*  587 */         ch2 = str.charAt(2);
/*  588 */         if ((ch0 == 'y' || ch0 == 'Y') && (ch1 == 'e' || ch1 == 'E') && (ch2 == 's' || ch2 == 'S'))
/*      */         {
/*      */           
/*  591 */           return Boolean.TRUE;
/*      */         }
/*  593 */         if ((ch0 == 'o' || ch0 == 'O') && (ch1 == 'f' || ch1 == 'F') && (ch2 == 'f' || ch2 == 'F'))
/*      */         {
/*      */           
/*  596 */           return Boolean.FALSE;
/*      */         }
/*      */         break;
/*      */       
/*      */       case 4:
/*  601 */         ch0 = str.charAt(0);
/*  602 */         ch1 = str.charAt(1);
/*  603 */         ch2 = str.charAt(2);
/*  604 */         ch3 = str.charAt(3);
/*  605 */         if ((ch0 == 't' || ch0 == 'T') && (ch1 == 'r' || ch1 == 'R') && (ch2 == 'u' || ch2 == 'U') && (ch3 == 'e' || ch3 == 'E'))
/*      */         {
/*      */ 
/*      */           
/*  609 */           return Boolean.TRUE;
/*      */         }
/*      */         break;
/*      */       
/*      */       case 5:
/*  614 */         ch0 = str.charAt(0);
/*  615 */         ch1 = str.charAt(1);
/*  616 */         ch2 = str.charAt(2);
/*  617 */         ch3 = str.charAt(3);
/*  618 */         ch4 = str.charAt(4);
/*  619 */         if ((ch0 == 'f' || ch0 == 'F') && (ch1 == 'a' || ch1 == 'A') && (ch2 == 'l' || ch2 == 'L') && (ch3 == 's' || ch3 == 'S') && (ch4 == 'e' || ch4 == 'E'))
/*      */         {
/*      */ 
/*      */ 
/*      */           
/*  624 */           return Boolean.FALSE;
/*      */         }
/*      */         break;
/*      */     } 
/*      */ 
/*      */     
/*  630 */     return null;
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
/*      */   public static Boolean toBooleanObject(String str, String trueString, String falseString, String nullString) {
/*  653 */     if (str == null) {
/*  654 */       if (trueString == null) {
/*  655 */         return Boolean.TRUE;
/*      */       }
/*  657 */       if (falseString == null) {
/*  658 */         return Boolean.FALSE;
/*      */       }
/*  660 */       if (nullString == null)
/*  661 */         return null; 
/*      */     } else {
/*  663 */       if (str.equals(trueString))
/*  664 */         return Boolean.TRUE; 
/*  665 */       if (str.equals(falseString))
/*  666 */         return Boolean.FALSE; 
/*  667 */       if (str.equals(nullString)) {
/*  668 */         return null;
/*      */       }
/*      */     } 
/*  671 */     throw new IllegalArgumentException("The String did not match any specified value");
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
/*      */   public static boolean toBoolean(String str) {
/*  706 */     return (toBooleanObject(str) == Boolean.TRUE);
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
/*      */   public static boolean toBoolean(String str, String trueString, String falseString) {
/*  724 */     if (str == trueString)
/*  725 */       return true; 
/*  726 */     if (str == falseString)
/*  727 */       return false; 
/*  728 */     if (str != null) {
/*  729 */       if (str.equals(trueString))
/*  730 */         return true; 
/*  731 */       if (str.equals(falseString)) {
/*  732 */         return false;
/*      */       }
/*      */     } 
/*      */     
/*  736 */     throw new IllegalArgumentException("The String did not match either specified value");
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
/*      */   public static String toStringTrueFalse(Boolean bool) {
/*  755 */     return toString(bool, "true", "false", null);
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
/*      */   public static String toStringOnOff(Boolean bool) {
/*  772 */     return toString(bool, "on", "off", null);
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
/*      */   public static String toStringYesNo(Boolean bool) {
/*  789 */     return toString(bool, "yes", "no", null);
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
/*      */   public static String toString(Boolean bool, String trueString, String falseString, String nullString) {
/*  808 */     if (bool == null) {
/*  809 */       return nullString;
/*      */     }
/*  811 */     return bool.booleanValue() ? trueString : falseString;
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
/*      */   public static String toStringTrueFalse(boolean bool) {
/*  829 */     return toString(bool, "true", "false");
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
/*      */   public static String toStringOnOff(boolean bool) {
/*  845 */     return toString(bool, "on", "off");
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
/*      */   public static String toStringYesNo(boolean bool) {
/*  861 */     return toString(bool, "yes", "no");
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
/*      */   public static String toString(boolean bool, String trueString, String falseString) {
/*  878 */     return bool ? trueString : falseString;
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
/*      */   public static boolean and(boolean... array) {
/*  902 */     if (array == null) {
/*  903 */       throw new IllegalArgumentException("The Array must not be null");
/*      */     }
/*  905 */     if (array.length == 0) {
/*  906 */       throw new IllegalArgumentException("Array is empty");
/*      */     }
/*  908 */     for (boolean element : array) {
/*  909 */       if (!element) {
/*  910 */         return false;
/*      */       }
/*      */     } 
/*  913 */     return true;
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
/*      */   public static Boolean and(Boolean... array) {
/*  936 */     if (array == null) {
/*  937 */       throw new IllegalArgumentException("The Array must not be null");
/*      */     }
/*  939 */     if (array.length == 0) {
/*  940 */       throw new IllegalArgumentException("Array is empty");
/*      */     }
/*      */     try {
/*  943 */       boolean[] primitive = ArrayUtils.toPrimitive(array);
/*  944 */       return and(primitive) ? Boolean.TRUE : Boolean.FALSE;
/*  945 */     } catch (NullPointerException ex) {
/*  946 */       throw new IllegalArgumentException("The array must not contain any null elements");
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
/*      */   public static boolean or(boolean... array) {
/*  970 */     if (array == null) {
/*  971 */       throw new IllegalArgumentException("The Array must not be null");
/*      */     }
/*  973 */     if (array.length == 0) {
/*  974 */       throw new IllegalArgumentException("Array is empty");
/*      */     }
/*  976 */     for (boolean element : array) {
/*  977 */       if (element) {
/*  978 */         return true;
/*      */       }
/*      */     } 
/*  981 */     return false;
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
/*      */   public static Boolean or(Boolean... array) {
/* 1005 */     if (array == null) {
/* 1006 */       throw new IllegalArgumentException("The Array must not be null");
/*      */     }
/* 1008 */     if (array.length == 0) {
/* 1009 */       throw new IllegalArgumentException("Array is empty");
/*      */     }
/*      */     try {
/* 1012 */       boolean[] primitive = ArrayUtils.toPrimitive(array);
/* 1013 */       return or(primitive) ? Boolean.TRUE : Boolean.FALSE;
/* 1014 */     } catch (NullPointerException ex) {
/* 1015 */       throw new IllegalArgumentException("The array must not contain any null elements");
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
/*      */   public static boolean xor(boolean... array) {
/* 1038 */     if (array == null) {
/* 1039 */       throw new IllegalArgumentException("The Array must not be null");
/*      */     }
/* 1041 */     if (array.length == 0) {
/* 1042 */       throw new IllegalArgumentException("Array is empty");
/*      */     }
/*      */ 
/*      */     
/* 1046 */     boolean result = false;
/* 1047 */     for (boolean element : array) {
/* 1048 */       result ^= element;
/*      */     }
/*      */     
/* 1051 */     return result;
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
/*      */   public static Boolean xor(Boolean... array) {
/* 1070 */     if (array == null) {
/* 1071 */       throw new IllegalArgumentException("The Array must not be null");
/*      */     }
/* 1073 */     if (array.length == 0) {
/* 1074 */       throw new IllegalArgumentException("Array is empty");
/*      */     }
/*      */     try {
/* 1077 */       boolean[] primitive = ArrayUtils.toPrimitive(array);
/* 1078 */       return xor(primitive) ? Boolean.TRUE : Boolean.FALSE;
/* 1079 */     } catch (NullPointerException ex) {
/* 1080 */       throw new IllegalArgumentException("The array must not contain any null elements");
/*      */     } 
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\org\apache\commons\lang3\BooleanUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */