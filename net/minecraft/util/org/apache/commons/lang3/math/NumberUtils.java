/*      */ package net.minecraft.util.org.apache.commons.lang3.math;
/*      */ 
/*      */ import java.lang.reflect.Array;
/*      */ import java.math.BigDecimal;
/*      */ import java.math.BigInteger;
/*      */ import net.minecraft.util.org.apache.commons.lang3.StringUtils;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class NumberUtils
/*      */ {
/*   34 */   public static final Long LONG_ZERO = Long.valueOf(0L);
/*      */   
/*   36 */   public static final Long LONG_ONE = Long.valueOf(1L);
/*      */   
/*   38 */   public static final Long LONG_MINUS_ONE = Long.valueOf(-1L);
/*      */   
/*   40 */   public static final Integer INTEGER_ZERO = Integer.valueOf(0);
/*      */   
/*   42 */   public static final Integer INTEGER_ONE = Integer.valueOf(1);
/*      */   
/*   44 */   public static final Integer INTEGER_MINUS_ONE = Integer.valueOf(-1);
/*      */   
/*   46 */   public static final Short SHORT_ZERO = Short.valueOf((short)0);
/*      */   
/*   48 */   public static final Short SHORT_ONE = Short.valueOf((short)1);
/*      */   
/*   50 */   public static final Short SHORT_MINUS_ONE = Short.valueOf((short)-1);
/*      */   
/*   52 */   public static final Byte BYTE_ZERO = Byte.valueOf((byte)0);
/*      */   
/*   54 */   public static final Byte BYTE_ONE = Byte.valueOf((byte)1);
/*      */   
/*   56 */   public static final Byte BYTE_MINUS_ONE = Byte.valueOf((byte)-1);
/*      */   
/*   58 */   public static final Double DOUBLE_ZERO = Double.valueOf(0.0D);
/*      */   
/*   60 */   public static final Double DOUBLE_ONE = Double.valueOf(1.0D);
/*      */   
/*   62 */   public static final Double DOUBLE_MINUS_ONE = Double.valueOf(-1.0D);
/*      */   
/*   64 */   public static final Float FLOAT_ZERO = Float.valueOf(0.0F);
/*      */   
/*   66 */   public static final Float FLOAT_ONE = Float.valueOf(1.0F);
/*      */   
/*   68 */   public static final Float FLOAT_MINUS_ONE = Float.valueOf(-1.0F);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int toInt(String str) {
/*  100 */     return toInt(str, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int toInt(String str, int defaultValue) {
/*  121 */     if (str == null) {
/*  122 */       return defaultValue;
/*      */     }
/*      */     try {
/*  125 */       return Integer.parseInt(str);
/*  126 */     } catch (NumberFormatException nfe) {
/*  127 */       return defaultValue;
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
/*      */   public static long toLong(String str) {
/*  149 */     return toLong(str, 0L);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long toLong(String str, long defaultValue) {
/*  170 */     if (str == null) {
/*  171 */       return defaultValue;
/*      */     }
/*      */     try {
/*  174 */       return Long.parseLong(str);
/*  175 */     } catch (NumberFormatException nfe) {
/*  176 */       return defaultValue;
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
/*      */   public static float toFloat(String str) {
/*  199 */     return toFloat(str, 0.0F);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float toFloat(String str, float defaultValue) {
/*  222 */     if (str == null) {
/*  223 */       return defaultValue;
/*      */     }
/*      */     try {
/*  226 */       return Float.parseFloat(str);
/*  227 */     } catch (NumberFormatException nfe) {
/*  228 */       return defaultValue;
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
/*      */   public static double toDouble(String str) {
/*  251 */     return toDouble(str, 0.0D);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double toDouble(String str, double defaultValue) {
/*  274 */     if (str == null) {
/*  275 */       return defaultValue;
/*      */     }
/*      */     try {
/*  278 */       return Double.parseDouble(str);
/*  279 */     } catch (NumberFormatException nfe) {
/*  280 */       return defaultValue;
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
/*      */   public static byte toByte(String str) {
/*  303 */     return toByte(str, (byte)0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte toByte(String str, byte defaultValue) {
/*  324 */     if (str == null) {
/*  325 */       return defaultValue;
/*      */     }
/*      */     try {
/*  328 */       return Byte.parseByte(str);
/*  329 */     } catch (NumberFormatException nfe) {
/*  330 */       return defaultValue;
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
/*      */   public static short toShort(String str) {
/*  352 */     return toShort(str, (short)0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short toShort(String str, short defaultValue) {
/*  373 */     if (str == null) {
/*  374 */       return defaultValue;
/*      */     }
/*      */     try {
/*  377 */       return Short.parseShort(str);
/*  378 */     } catch (NumberFormatException nfe) {
/*  379 */       return defaultValue;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Number createNumber(String str) throws NumberFormatException {
/*      */     String mant, dec, exp;
/*  451 */     if (str == null) {
/*  452 */       return null;
/*      */     }
/*  454 */     if (StringUtils.isBlank(str)) {
/*  455 */       throw new NumberFormatException("A blank string is not a valid number");
/*      */     }
/*      */     
/*  458 */     String[] hex_prefixes = { "0x", "0X", "-0x", "-0X", "#", "-#" };
/*  459 */     int pfxLen = 0;
/*  460 */     for (String pfx : hex_prefixes) {
/*  461 */       if (str.startsWith(pfx)) {
/*  462 */         pfxLen += pfx.length();
/*      */         break;
/*      */       } 
/*      */     } 
/*  466 */     if (pfxLen > 0) {
/*  467 */       char firstSigDigit = Character.MIN_VALUE;
/*  468 */       for (int i = pfxLen; i < str.length(); ) {
/*  469 */         firstSigDigit = str.charAt(i);
/*  470 */         if (firstSigDigit == '0') {
/*  471 */           pfxLen++;
/*      */           
/*      */           i++;
/*      */         } 
/*      */       } 
/*  476 */       int hexDigits = str.length() - pfxLen;
/*  477 */       if (hexDigits > 16 || (hexDigits == 16 && firstSigDigit > '7')) {
/*  478 */         return createBigInteger(str);
/*      */       }
/*  480 */       if (hexDigits > 8 || (hexDigits == 8 && firstSigDigit > '7')) {
/*  481 */         return createLong(str);
/*      */       }
/*  483 */       return createInteger(str);
/*      */     } 
/*  485 */     char lastChar = str.charAt(str.length() - 1);
/*      */ 
/*      */ 
/*      */     
/*  489 */     int decPos = str.indexOf('.');
/*  490 */     int expPos = str.indexOf('e') + str.indexOf('E') + 1;
/*      */ 
/*      */ 
/*      */     
/*  494 */     int numDecimals = 0;
/*  495 */     if (decPos > -1) {
/*      */       
/*  497 */       if (expPos > -1) {
/*  498 */         if (expPos < decPos || expPos > str.length()) {
/*  499 */           throw new NumberFormatException(str + " is not a valid number.");
/*      */         }
/*  501 */         dec = str.substring(decPos + 1, expPos);
/*      */       } else {
/*  503 */         dec = str.substring(decPos + 1);
/*      */       } 
/*  505 */       mant = str.substring(0, decPos);
/*  506 */       numDecimals = dec.length();
/*      */     } else {
/*  508 */       if (expPos > -1) {
/*  509 */         if (expPos > str.length()) {
/*  510 */           throw new NumberFormatException(str + " is not a valid number.");
/*      */         }
/*  512 */         mant = str.substring(0, expPos);
/*      */       } else {
/*  514 */         mant = str;
/*      */       } 
/*  516 */       dec = null;
/*      */     } 
/*  518 */     if (!Character.isDigit(lastChar) && lastChar != '.') {
/*  519 */       if (expPos > -1 && expPos < str.length() - 1) {
/*  520 */         exp = str.substring(expPos + 1, str.length() - 1);
/*      */       } else {
/*  522 */         exp = null;
/*      */       } 
/*      */       
/*  525 */       String numeric = str.substring(0, str.length() - 1);
/*  526 */       boolean bool = (isAllZeros(mant) && isAllZeros(exp));
/*  527 */       switch (lastChar) {
/*      */         case 'L':
/*      */         case 'l':
/*  530 */           if (dec == null && exp == null && ((numeric.charAt(0) == '-' && isDigits(numeric.substring(1))) || isDigits(numeric))) {
/*      */             
/*      */             try {
/*      */               
/*  534 */               return createLong(numeric);
/*  535 */             } catch (NumberFormatException nfe) {
/*      */ 
/*      */               
/*  538 */               return createBigInteger(numeric);
/*      */             } 
/*      */           }
/*  541 */           throw new NumberFormatException(str + " is not a valid number.");
/*      */         case 'F':
/*      */         case 'f':
/*      */           try {
/*  545 */             Float f = createFloat(numeric);
/*  546 */             if (!f.isInfinite() && (f.floatValue() != 0.0F || bool))
/*      */             {
/*      */               
/*  549 */               return f;
/*      */             }
/*      */           }
/*  552 */           catch (NumberFormatException nfe) {}
/*      */ 
/*      */ 
/*      */         
/*      */         case 'D':
/*      */         case 'd':
/*      */           try {
/*  559 */             Double d = createDouble(numeric);
/*  560 */             if (!d.isInfinite() && (d.floatValue() != 0.0D || bool)) {
/*  561 */               return d;
/*      */             }
/*  563 */           } catch (NumberFormatException nfe) {}
/*      */ 
/*      */           
/*      */           try {
/*  567 */             return createBigDecimal(numeric);
/*  568 */           } catch (NumberFormatException e) {
/*      */             break;
/*      */           } 
/*      */       } 
/*      */       
/*  573 */       throw new NumberFormatException(str + " is not a valid number.");
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  579 */     if (expPos > -1 && expPos < str.length() - 1) {
/*  580 */       exp = str.substring(expPos + 1, str.length());
/*      */     } else {
/*  582 */       exp = null;
/*      */     } 
/*  584 */     if (dec == null && exp == null) {
/*      */       
/*      */       try {
/*  587 */         return createInteger(str);
/*  588 */       } catch (NumberFormatException nfe) {
/*      */ 
/*      */         
/*      */         try {
/*  592 */           return createLong(str);
/*  593 */         } catch (NumberFormatException numberFormatException) {
/*      */ 
/*      */           
/*  596 */           return createBigInteger(str);
/*      */         } 
/*      */       } 
/*      */     }
/*  600 */     boolean allZeros = (isAllZeros(mant) && isAllZeros(exp));
/*      */     try {
/*  602 */       if (numDecimals <= 7) {
/*  603 */         Float f = createFloat(str);
/*  604 */         if (!f.isInfinite() && (f.floatValue() != 0.0F || allZeros)) {
/*  605 */           return f;
/*      */         }
/*      */       } 
/*  608 */     } catch (NumberFormatException nfe) {}
/*      */ 
/*      */     
/*      */     try {
/*  612 */       if (numDecimals <= 16) {
/*  613 */         Double d = createDouble(str);
/*  614 */         if (!d.isInfinite() && (d.doubleValue() != 0.0D || allZeros)) {
/*  615 */           return d;
/*      */         }
/*      */       } 
/*  618 */     } catch (NumberFormatException nfe) {}
/*      */ 
/*      */ 
/*      */     
/*  622 */     return createBigDecimal(str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isAllZeros(String str) {
/*  634 */     if (str == null) {
/*  635 */       return true;
/*      */     }
/*  637 */     for (int i = str.length() - 1; i >= 0; i--) {
/*  638 */       if (str.charAt(i) != '0') {
/*  639 */         return false;
/*      */       }
/*      */     } 
/*  642 */     return (str.length() > 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Float createFloat(String str) {
/*  656 */     if (str == null) {
/*  657 */       return null;
/*      */     }
/*  659 */     return Float.valueOf(str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Double createDouble(String str) {
/*  672 */     if (str == null) {
/*  673 */       return null;
/*      */     }
/*  675 */     return Double.valueOf(str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Integer createInteger(String str) {
/*  689 */     if (str == null) {
/*  690 */       return null;
/*      */     }
/*      */     
/*  693 */     return Integer.decode(str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static Long createLong(String str) {
/*  707 */     if (str == null) {
/*  708 */       return null;
/*      */     }
/*  710 */     return Long.decode(str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BigInteger createBigInteger(String str) {
/*  724 */     if (str == null) {
/*  725 */       return null;
/*      */     }
/*  727 */     int pos = 0;
/*  728 */     int radix = 10;
/*  729 */     boolean negate = false;
/*  730 */     if (str.startsWith("-")) {
/*  731 */       negate = true;
/*  732 */       pos = 1;
/*      */     } 
/*  734 */     if (str.startsWith("0x", pos) || str.startsWith("0x", pos)) {
/*  735 */       radix = 16;
/*  736 */       pos += 2;
/*  737 */     } else if (str.startsWith("#", pos)) {
/*  738 */       radix = 16;
/*  739 */       pos++;
/*  740 */     } else if (str.startsWith("0", pos) && str.length() > pos + 1) {
/*  741 */       radix = 8;
/*  742 */       pos++;
/*      */     } 
/*      */     
/*  745 */     BigInteger value = new BigInteger(str.substring(pos), radix);
/*  746 */     return negate ? value.negate() : value;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static BigDecimal createBigDecimal(String str) {
/*  759 */     if (str == null) {
/*  760 */       return null;
/*      */     }
/*      */     
/*  763 */     if (StringUtils.isBlank(str)) {
/*  764 */       throw new NumberFormatException("A blank string is not a valid number");
/*      */     }
/*  766 */     if (str.trim().startsWith("--"))
/*      */     {
/*      */ 
/*      */ 
/*      */       
/*  771 */       throw new NumberFormatException(str + " is not a valid number.");
/*      */     }
/*  773 */     return new BigDecimal(str);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long min(long[] array) {
/*  788 */     validateArray(array);
/*      */ 
/*      */     
/*  791 */     long min = array[0];
/*  792 */     for (int i = 1; i < array.length; i++) {
/*  793 */       if (array[i] < min) {
/*  794 */         min = array[i];
/*      */       }
/*      */     } 
/*      */     
/*  798 */     return min;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int min(int[] array) {
/*  811 */     validateArray(array);
/*      */ 
/*      */     
/*  814 */     int min = array[0];
/*  815 */     for (int j = 1; j < array.length; j++) {
/*  816 */       if (array[j] < min) {
/*  817 */         min = array[j];
/*      */       }
/*      */     } 
/*      */     
/*  821 */     return min;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short min(short[] array) {
/*  834 */     validateArray(array);
/*      */ 
/*      */     
/*  837 */     short min = array[0];
/*  838 */     for (int i = 1; i < array.length; i++) {
/*  839 */       if (array[i] < min) {
/*  840 */         min = array[i];
/*      */       }
/*      */     } 
/*      */     
/*  844 */     return min;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte min(byte[] array) {
/*  857 */     validateArray(array);
/*      */ 
/*      */     
/*  860 */     byte min = array[0];
/*  861 */     for (int i = 1; i < array.length; i++) {
/*  862 */       if (array[i] < min) {
/*  863 */         min = array[i];
/*      */       }
/*      */     } 
/*      */     
/*  867 */     return min;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double min(double[] array) {
/*  881 */     validateArray(array);
/*      */ 
/*      */     
/*  884 */     double min = array[0];
/*  885 */     for (int i = 1; i < array.length; i++) {
/*  886 */       if (Double.isNaN(array[i])) {
/*  887 */         return Double.NaN;
/*      */       }
/*  889 */       if (array[i] < min) {
/*  890 */         min = array[i];
/*      */       }
/*      */     } 
/*      */     
/*  894 */     return min;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float min(float[] array) {
/*  908 */     validateArray(array);
/*      */ 
/*      */     
/*  911 */     float min = array[0];
/*  912 */     for (int i = 1; i < array.length; i++) {
/*  913 */       if (Float.isNaN(array[i])) {
/*  914 */         return Float.NaN;
/*      */       }
/*  916 */       if (array[i] < min) {
/*  917 */         min = array[i];
/*      */       }
/*      */     } 
/*      */     
/*  921 */     return min;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long max(long[] array) {
/*  936 */     validateArray(array);
/*      */ 
/*      */     
/*  939 */     long max = array[0];
/*  940 */     for (int j = 1; j < array.length; j++) {
/*  941 */       if (array[j] > max) {
/*  942 */         max = array[j];
/*      */       }
/*      */     } 
/*      */     
/*  946 */     return max;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int max(int[] array) {
/*  959 */     validateArray(array);
/*      */ 
/*      */     
/*  962 */     int max = array[0];
/*  963 */     for (int j = 1; j < array.length; j++) {
/*  964 */       if (array[j] > max) {
/*  965 */         max = array[j];
/*      */       }
/*      */     } 
/*      */     
/*  969 */     return max;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short max(short[] array) {
/*  982 */     validateArray(array);
/*      */ 
/*      */     
/*  985 */     short max = array[0];
/*  986 */     for (int i = 1; i < array.length; i++) {
/*  987 */       if (array[i] > max) {
/*  988 */         max = array[i];
/*      */       }
/*      */     } 
/*      */     
/*  992 */     return max;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte max(byte[] array) {
/* 1005 */     validateArray(array);
/*      */ 
/*      */     
/* 1008 */     byte max = array[0];
/* 1009 */     for (int i = 1; i < array.length; i++) {
/* 1010 */       if (array[i] > max) {
/* 1011 */         max = array[i];
/*      */       }
/*      */     } 
/*      */     
/* 1015 */     return max;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double max(double[] array) {
/* 1029 */     validateArray(array);
/*      */ 
/*      */     
/* 1032 */     double max = array[0];
/* 1033 */     for (int j = 1; j < array.length; j++) {
/* 1034 */       if (Double.isNaN(array[j])) {
/* 1035 */         return Double.NaN;
/*      */       }
/* 1037 */       if (array[j] > max) {
/* 1038 */         max = array[j];
/*      */       }
/*      */     } 
/*      */     
/* 1042 */     return max;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float max(float[] array) {
/* 1056 */     validateArray(array);
/*      */ 
/*      */     
/* 1059 */     float max = array[0];
/* 1060 */     for (int j = 1; j < array.length; j++) {
/* 1061 */       if (Float.isNaN(array[j])) {
/* 1062 */         return Float.NaN;
/*      */       }
/* 1064 */       if (array[j] > max) {
/* 1065 */         max = array[j];
/*      */       }
/*      */     } 
/*      */     
/* 1069 */     return max;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void validateArray(Object array) {
/* 1079 */     if (array == null)
/* 1080 */       throw new IllegalArgumentException("The Array must not be null"); 
/* 1081 */     if (Array.getLength(array) == 0) {
/* 1082 */       throw new IllegalArgumentException("Array cannot be empty.");
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
/*      */   public static long min(long a, long b, long c) {
/* 1097 */     if (b < a) {
/* 1098 */       a = b;
/*      */     }
/* 1100 */     if (c < a) {
/* 1101 */       a = c;
/*      */     }
/* 1103 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int min(int a, int b, int c) {
/* 1115 */     if (b < a) {
/* 1116 */       a = b;
/*      */     }
/* 1118 */     if (c < a) {
/* 1119 */       a = c;
/*      */     }
/* 1121 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short min(short a, short b, short c) {
/* 1133 */     if (b < a) {
/* 1134 */       a = b;
/*      */     }
/* 1136 */     if (c < a) {
/* 1137 */       a = c;
/*      */     }
/* 1139 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte min(byte a, byte b, byte c) {
/* 1151 */     if (b < a) {
/* 1152 */       a = b;
/*      */     }
/* 1154 */     if (c < a) {
/* 1155 */       a = c;
/*      */     }
/* 1157 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double min(double a, double b, double c) {
/* 1173 */     return Math.min(Math.min(a, b), c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float min(float a, float b, float c) {
/* 1189 */     return Math.min(Math.min(a, b), c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long max(long a, long b, long c) {
/* 1203 */     if (b > a) {
/* 1204 */       a = b;
/*      */     }
/* 1206 */     if (c > a) {
/* 1207 */       a = c;
/*      */     }
/* 1209 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int max(int a, int b, int c) {
/* 1221 */     if (b > a) {
/* 1222 */       a = b;
/*      */     }
/* 1224 */     if (c > a) {
/* 1225 */       a = c;
/*      */     }
/* 1227 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short max(short a, short b, short c) {
/* 1239 */     if (b > a) {
/* 1240 */       a = b;
/*      */     }
/* 1242 */     if (c > a) {
/* 1243 */       a = c;
/*      */     }
/* 1245 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte max(byte a, byte b, byte c) {
/* 1257 */     if (b > a) {
/* 1258 */       a = b;
/*      */     }
/* 1260 */     if (c > a) {
/* 1261 */       a = c;
/*      */     }
/* 1263 */     return a;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static double max(double a, double b, double c) {
/* 1279 */     return Math.max(Math.max(a, b), c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static float max(float a, float b, float c) {
/* 1295 */     return Math.max(Math.max(a, b), c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isDigits(String str) {
/* 1310 */     if (StringUtils.isEmpty(str)) {
/* 1311 */       return false;
/*      */     }
/* 1313 */     for (int i = 0; i < str.length(); i++) {
/* 1314 */       if (!Character.isDigit(str.charAt(i))) {
/* 1315 */         return false;
/*      */       }
/*      */     } 
/* 1318 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean isNumber(String str) {
/* 1335 */     if (StringUtils.isEmpty(str)) {
/* 1336 */       return false;
/*      */     }
/* 1338 */     char[] chars = str.toCharArray();
/* 1339 */     int sz = chars.length;
/* 1340 */     boolean hasExp = false;
/* 1341 */     boolean hasDecPoint = false;
/* 1342 */     boolean allowSigns = false;
/* 1343 */     boolean foundDigit = false;
/*      */     
/* 1345 */     int start = (chars[0] == '-') ? 1 : 0;
/* 1346 */     if (sz > start + 1 && chars[start] == '0' && chars[start + 1] == 'x') {
/* 1347 */       int j = start + 2;
/* 1348 */       if (j == sz) {
/* 1349 */         return false;
/*      */       }
/*      */       
/* 1352 */       for (; j < chars.length; j++) {
/* 1353 */         if ((chars[j] < '0' || chars[j] > '9') && (chars[j] < 'a' || chars[j] > 'f') && (chars[j] < 'A' || chars[j] > 'F'))
/*      */         {
/*      */           
/* 1356 */           return false;
/*      */         }
/*      */       } 
/* 1359 */       return true;
/*      */     } 
/* 1361 */     sz--;
/*      */     
/* 1363 */     int i = start;
/*      */ 
/*      */     
/* 1366 */     while (i < sz || (i < sz + 1 && allowSigns && !foundDigit)) {
/* 1367 */       if (chars[i] >= '0' && chars[i] <= '9') {
/* 1368 */         foundDigit = true;
/* 1369 */         allowSigns = false;
/*      */       }
/* 1371 */       else if (chars[i] == '.') {
/* 1372 */         if (hasDecPoint || hasExp)
/*      */         {
/* 1374 */           return false;
/*      */         }
/* 1376 */         hasDecPoint = true;
/* 1377 */       } else if (chars[i] == 'e' || chars[i] == 'E') {
/*      */         
/* 1379 */         if (hasExp)
/*      */         {
/* 1381 */           return false;
/*      */         }
/* 1383 */         if (!foundDigit) {
/* 1384 */           return false;
/*      */         }
/* 1386 */         hasExp = true;
/* 1387 */         allowSigns = true;
/* 1388 */       } else if (chars[i] == '+' || chars[i] == '-') {
/* 1389 */         if (!allowSigns) {
/* 1390 */           return false;
/*      */         }
/* 1392 */         allowSigns = false;
/* 1393 */         foundDigit = false;
/*      */       } else {
/* 1395 */         return false;
/*      */       } 
/* 1397 */       i++;
/*      */     } 
/* 1399 */     if (i < chars.length) {
/* 1400 */       if (chars[i] >= '0' && chars[i] <= '9')
/*      */       {
/* 1402 */         return true;
/*      */       }
/* 1404 */       if (chars[i] == 'e' || chars[i] == 'E')
/*      */       {
/* 1406 */         return false;
/*      */       }
/* 1408 */       if (chars[i] == '.') {
/* 1409 */         if (hasDecPoint || hasExp)
/*      */         {
/* 1411 */           return false;
/*      */         }
/*      */         
/* 1414 */         return foundDigit;
/*      */       } 
/* 1416 */       if (!allowSigns && (chars[i] == 'd' || chars[i] == 'D' || chars[i] == 'f' || chars[i] == 'F'))
/*      */       {
/*      */ 
/*      */ 
/*      */         
/* 1421 */         return foundDigit;
/*      */       }
/* 1423 */       if (chars[i] == 'l' || chars[i] == 'L')
/*      */       {
/*      */         
/* 1426 */         return (foundDigit && !hasExp && !hasDecPoint);
/*      */       }
/*      */       
/* 1429 */       return false;
/*      */     } 
/*      */ 
/*      */     
/* 1433 */     return (!allowSigns && foundDigit);
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\org\apache\commons\lang3\math\NumberUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */