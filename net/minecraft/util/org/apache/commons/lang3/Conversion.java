/*      */ package net.minecraft.util.org.apache.commons.lang3;
/*      */ 
/*      */ import java.util.UUID;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class Conversion
/*      */ {
/*      */   public static int hexDigitToInt(char hexDigit) {
/*   79 */     int digit = Character.digit(hexDigit, 16);
/*   80 */     if (digit < 0) {
/*   81 */       throw new IllegalArgumentException("Cannot interpret '" + hexDigit + "' as a hexadecimal digit");
/*      */     }
/*      */ 
/*      */     
/*   85 */     return digit;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int hexDigitMsb0ToInt(char hexDigit) {
/*  101 */     switch (hexDigit) {
/*      */       case '0':
/*  103 */         return 0;
/*      */       case '1':
/*  105 */         return 8;
/*      */       case '2':
/*  107 */         return 4;
/*      */       case '3':
/*  109 */         return 12;
/*      */       case '4':
/*  111 */         return 2;
/*      */       case '5':
/*  113 */         return 10;
/*      */       case '6':
/*  115 */         return 6;
/*      */       case '7':
/*  117 */         return 14;
/*      */       case '8':
/*  119 */         return 1;
/*      */       case '9':
/*  121 */         return 9;
/*      */       case 'A':
/*      */       case 'a':
/*  124 */         return 5;
/*      */       case 'B':
/*      */       case 'b':
/*  127 */         return 13;
/*      */       case 'C':
/*      */       case 'c':
/*  130 */         return 3;
/*      */       case 'D':
/*      */       case 'd':
/*  133 */         return 11;
/*      */       case 'E':
/*      */       case 'e':
/*  136 */         return 7;
/*      */       case 'F':
/*      */       case 'f':
/*  139 */         return 15;
/*      */     } 
/*  141 */     throw new IllegalArgumentException("Cannot interpret '" + hexDigit + "' as a hexadecimal digit");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[] hexDigitToBinary(char hexDigit) {
/*  161 */     switch (hexDigit) {
/*      */       case '0':
/*  163 */         return new boolean[] { false, false, false, false };
/*      */       case '1':
/*  165 */         return new boolean[] { true, false, false, false };
/*      */       case '2':
/*  167 */         return new boolean[] { false, true, false, false };
/*      */       case '3':
/*  169 */         return new boolean[] { true, true, false, false };
/*      */       case '4':
/*  171 */         return new boolean[] { false, false, true, false };
/*      */       case '5':
/*  173 */         return new boolean[] { true, false, true, false };
/*      */       case '6':
/*  175 */         return new boolean[] { false, true, true, false };
/*      */       case '7':
/*  177 */         return new boolean[] { true, true, true, false };
/*      */       case '8':
/*  179 */         return new boolean[] { false, false, false, true };
/*      */       case '9':
/*  181 */         return new boolean[] { true, false, false, true };
/*      */       case 'A':
/*      */       case 'a':
/*  184 */         return new boolean[] { false, true, false, true };
/*      */       case 'B':
/*      */       case 'b':
/*  187 */         return new boolean[] { true, true, false, true };
/*      */       case 'C':
/*      */       case 'c':
/*  190 */         return new boolean[] { false, false, true, true };
/*      */       case 'D':
/*      */       case 'd':
/*  193 */         return new boolean[] { true, false, true, true };
/*      */       case 'E':
/*      */       case 'e':
/*  196 */         return new boolean[] { false, true, true, true };
/*      */       case 'F':
/*      */       case 'f':
/*  199 */         return new boolean[] { true, true, true, true };
/*      */     } 
/*  201 */     throw new IllegalArgumentException("Cannot interpret '" + hexDigit + "' as a hexadecimal digit");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[] hexDigitMsb0ToBinary(char hexDigit) {
/*  221 */     switch (hexDigit) {
/*      */       case '0':
/*  223 */         return new boolean[] { false, false, false, false };
/*      */       case '1':
/*  225 */         return new boolean[] { false, false, false, true };
/*      */       case '2':
/*  227 */         return new boolean[] { false, false, true, false };
/*      */       case '3':
/*  229 */         return new boolean[] { false, false, true, true };
/*      */       case '4':
/*  231 */         return new boolean[] { false, true, false, false };
/*      */       case '5':
/*  233 */         return new boolean[] { false, true, false, true };
/*      */       case '6':
/*  235 */         return new boolean[] { false, true, true, false };
/*      */       case '7':
/*  237 */         return new boolean[] { false, true, true, true };
/*      */       case '8':
/*  239 */         return new boolean[] { true, false, false, false };
/*      */       case '9':
/*  241 */         return new boolean[] { true, false, false, true };
/*      */       case 'A':
/*      */       case 'a':
/*  244 */         return new boolean[] { true, false, true, false };
/*      */       case 'B':
/*      */       case 'b':
/*  247 */         return new boolean[] { true, false, true, true };
/*      */       case 'C':
/*      */       case 'c':
/*  250 */         return new boolean[] { true, true, false, false };
/*      */       case 'D':
/*      */       case 'd':
/*  253 */         return new boolean[] { true, true, false, true };
/*      */       case 'E':
/*      */       case 'e':
/*  256 */         return new boolean[] { true, true, true, false };
/*      */       case 'F':
/*      */       case 'f':
/*  259 */         return new boolean[] { true, true, true, true };
/*      */     } 
/*  261 */     throw new IllegalArgumentException("Cannot interpret '" + hexDigit + "' as a hexadecimal digit");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char binaryToHexDigit(boolean[] src) {
/*  282 */     return binaryToHexDigit(src, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char binaryToHexDigit(boolean[] src, int srcPos) {
/*  301 */     if (src.length == 0) {
/*  302 */       throw new IllegalArgumentException("Cannot convert an empty array.");
/*      */     }
/*  304 */     if (src.length > srcPos + 3 && src[srcPos + 3]) {
/*  305 */       if (src.length > srcPos + 2 && src[srcPos + 2]) {
/*  306 */         if (src.length > srcPos + 1 && src[srcPos + 1]) {
/*  307 */           if (src[srcPos]) {
/*  308 */             return 'f';
/*      */           }
/*  310 */           return 'e';
/*      */         } 
/*      */         
/*  313 */         if (src[srcPos]) {
/*  314 */           return 'd';
/*      */         }
/*  316 */         return 'c';
/*      */       } 
/*      */ 
/*      */       
/*  320 */       if (src.length > srcPos + 1 && src[srcPos + 1]) {
/*  321 */         if (src[srcPos]) {
/*  322 */           return 'b';
/*      */         }
/*  324 */         return 'a';
/*      */       } 
/*      */       
/*  327 */       if (src[srcPos]) {
/*  328 */         return '9';
/*      */       }
/*  330 */       return '8';
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  335 */     if (src.length > srcPos + 2 && src[srcPos + 2]) {
/*  336 */       if (src.length > srcPos + 1 && src[srcPos + 1]) {
/*  337 */         if (src[srcPos]) {
/*  338 */           return '7';
/*      */         }
/*  340 */         return '6';
/*      */       } 
/*      */       
/*  343 */       if (src[srcPos]) {
/*  344 */         return '5';
/*      */       }
/*  346 */       return '4';
/*      */     } 
/*      */ 
/*      */     
/*  350 */     if (src.length > srcPos + 1 && src[srcPos + 1]) {
/*  351 */       if (src[srcPos]) {
/*  352 */         return '3';
/*      */       }
/*  354 */       return '2';
/*      */     } 
/*      */     
/*  357 */     if (src[srcPos]) {
/*  358 */       return '1';
/*      */     }
/*  360 */     return '0';
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char binaryToHexDigitMsb0_4bits(boolean[] src) {
/*  383 */     return binaryToHexDigitMsb0_4bits(src, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char binaryToHexDigitMsb0_4bits(boolean[] src, int srcPos) {
/*  404 */     if (src.length > 8) {
/*  405 */       throw new IllegalArgumentException("src.length>8: src.length=" + src.length);
/*      */     }
/*  407 */     if (src.length - srcPos < 4) {
/*  408 */       throw new IllegalArgumentException("src.length-srcPos<4: src.length=" + src.length + ", srcPos=" + srcPos);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*  413 */     if (src[srcPos + 3]) {
/*  414 */       if (src[srcPos + 2]) {
/*  415 */         if (src[srcPos + 1]) {
/*  416 */           if (src[srcPos]) {
/*  417 */             return 'f';
/*      */           }
/*  419 */           return '7';
/*      */         } 
/*      */         
/*  422 */         if (src[srcPos]) {
/*  423 */           return 'b';
/*      */         }
/*  425 */         return '3';
/*      */       } 
/*      */ 
/*      */       
/*  429 */       if (src[srcPos + 1]) {
/*  430 */         if (src[srcPos]) {
/*  431 */           return 'd';
/*      */         }
/*  433 */         return '5';
/*      */       } 
/*      */       
/*  436 */       if (src[srcPos]) {
/*  437 */         return '9';
/*      */       }
/*  439 */       return '1';
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  444 */     if (src[srcPos + 2]) {
/*  445 */       if (src[srcPos + 1]) {
/*  446 */         if (src[srcPos]) {
/*  447 */           return 'e';
/*      */         }
/*  449 */         return '6';
/*      */       } 
/*      */       
/*  452 */       if (src[srcPos]) {
/*  453 */         return 'a';
/*      */       }
/*  455 */       return '2';
/*      */     } 
/*      */ 
/*      */     
/*  459 */     if (src[srcPos + 1]) {
/*  460 */       if (src[srcPos]) {
/*  461 */         return 'c';
/*      */       }
/*  463 */       return '4';
/*      */     } 
/*      */     
/*  466 */     if (src[srcPos]) {
/*  467 */       return '8';
/*      */     }
/*  469 */     return '0';
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char binaryBeMsb0ToHexDigit(boolean[] src) {
/*  492 */     return binaryBeMsb0ToHexDigit(src, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char binaryBeMsb0ToHexDigit(boolean[] src, int srcPos) {
/*  512 */     if (src.length == 0) {
/*  513 */       throw new IllegalArgumentException("Cannot convert an empty array.");
/*      */     }
/*  515 */     int beSrcPos = src.length - 1 - srcPos;
/*  516 */     int srcLen = Math.min(4, beSrcPos + 1);
/*  517 */     boolean[] paddedSrc = new boolean[4];
/*  518 */     System.arraycopy(src, beSrcPos + 1 - srcLen, paddedSrc, 4 - srcLen, srcLen);
/*  519 */     src = paddedSrc;
/*  520 */     srcPos = 0;
/*  521 */     if (src[srcPos]) {
/*  522 */       if (src.length > srcPos + 1 && src[srcPos + 1]) {
/*  523 */         if (src.length > srcPos + 2 && src[srcPos + 2]) {
/*  524 */           if (src.length > srcPos + 3 && src[srcPos + 3]) {
/*  525 */             return 'f';
/*      */           }
/*  527 */           return 'e';
/*      */         } 
/*      */         
/*  530 */         if (src.length > srcPos + 3 && src[srcPos + 3]) {
/*  531 */           return 'd';
/*      */         }
/*  533 */         return 'c';
/*      */       } 
/*      */ 
/*      */       
/*  537 */       if (src.length > srcPos + 2 && src[srcPos + 2]) {
/*  538 */         if (src.length > srcPos + 3 && src[srcPos + 3]) {
/*  539 */           return 'b';
/*      */         }
/*  541 */         return 'a';
/*      */       } 
/*      */       
/*  544 */       if (src.length > srcPos + 3 && src[srcPos + 3]) {
/*  545 */         return '9';
/*      */       }
/*  547 */       return '8';
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  552 */     if (src.length > srcPos + 1 && src[srcPos + 1]) {
/*  553 */       if (src.length > srcPos + 2 && src[srcPos + 2]) {
/*  554 */         if (src.length > srcPos + 3 && src[srcPos + 3]) {
/*  555 */           return '7';
/*      */         }
/*  557 */         return '6';
/*      */       } 
/*      */       
/*  560 */       if (src.length > srcPos + 3 && src[srcPos + 3]) {
/*  561 */         return '5';
/*      */       }
/*  563 */       return '4';
/*      */     } 
/*      */ 
/*      */     
/*  567 */     if (src.length > srcPos + 2 && src[srcPos + 2]) {
/*  568 */       if (src.length > srcPos + 3 && src[srcPos + 3]) {
/*  569 */         return '3';
/*      */       }
/*  571 */       return '2';
/*      */     } 
/*      */     
/*  574 */     if (src.length > srcPos + 3 && src[srcPos + 3]) {
/*  575 */       return '1';
/*      */     }
/*  577 */     return '0';
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char intToHexDigit(int nibble) {
/*  603 */     char c = Character.forDigit(nibble, 16);
/*  604 */     if (c == '\000') {
/*  605 */       throw new IllegalArgumentException("nibble value not between 0 and 15: " + nibble);
/*      */     }
/*  607 */     return c;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static char intToHexDigitMsb0(int nibble) {
/*  629 */     switch (nibble) {
/*      */       case 0:
/*  631 */         return '0';
/*      */       case 1:
/*  633 */         return '8';
/*      */       case 2:
/*  635 */         return '4';
/*      */       case 3:
/*  637 */         return 'c';
/*      */       case 4:
/*  639 */         return '2';
/*      */       case 5:
/*  641 */         return 'a';
/*      */       case 6:
/*  643 */         return '6';
/*      */       case 7:
/*  645 */         return 'e';
/*      */       case 8:
/*  647 */         return '1';
/*      */       case 9:
/*  649 */         return '9';
/*      */       case 10:
/*  651 */         return '5';
/*      */       case 11:
/*  653 */         return 'd';
/*      */       case 12:
/*  655 */         return '3';
/*      */       case 13:
/*  657 */         return 'b';
/*      */       case 14:
/*  659 */         return '7';
/*      */       case 15:
/*  661 */         return 'f';
/*      */     } 
/*  663 */     throw new IllegalArgumentException("nibble value not between 0 and 15: " + nibble);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long intArrayToLong(int[] src, int srcPos, long dstInit, int dstPos, int nInts) {
/*  685 */     if ((src.length == 0 && srcPos == 0) || 0 == nInts) {
/*  686 */       return dstInit;
/*      */     }
/*  688 */     if ((nInts - 1) * 32 + dstPos >= 64) {
/*  689 */       throw new IllegalArgumentException("(nInts-1)*32+dstPos is greather or equal to than 64");
/*      */     }
/*      */     
/*  692 */     long out = dstInit;
/*  693 */     int shift = 0;
/*  694 */     for (int i = 0; i < nInts; i++) {
/*  695 */       shift = i * 32 + dstPos;
/*  696 */       long bits = (0xFFFFFFFFL & src[i + srcPos]) << shift;
/*  697 */       long mask = 4294967295L << shift;
/*  698 */       out = out & (mask ^ 0xFFFFFFFFFFFFFFFFL) | bits;
/*      */     } 
/*  700 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long shortArrayToLong(short[] src, int srcPos, long dstInit, int dstPos, int nShorts) {
/*  722 */     if ((src.length == 0 && srcPos == 0) || 0 == nShorts) {
/*  723 */       return dstInit;
/*      */     }
/*  725 */     if ((nShorts - 1) * 16 + dstPos >= 64) {
/*  726 */       throw new IllegalArgumentException("(nShorts-1)*16+dstPos is greather or equal to than 64");
/*      */     }
/*      */     
/*  729 */     long out = dstInit;
/*  730 */     int shift = 0;
/*  731 */     for (int i = 0; i < nShorts; i++) {
/*  732 */       shift = i * 16 + dstPos;
/*  733 */       long bits = (0xFFFFL & src[i + srcPos]) << shift;
/*  734 */       long mask = 65535L << shift;
/*  735 */       out = out & (mask ^ 0xFFFFFFFFFFFFFFFFL) | bits;
/*      */     } 
/*  737 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int shortArrayToInt(short[] src, int srcPos, int dstInit, int dstPos, int nShorts) {
/*  759 */     if ((src.length == 0 && srcPos == 0) || 0 == nShorts) {
/*  760 */       return dstInit;
/*      */     }
/*  762 */     if ((nShorts - 1) * 16 + dstPos >= 32) {
/*  763 */       throw new IllegalArgumentException("(nShorts-1)*16+dstPos is greather or equal to than 32");
/*      */     }
/*      */     
/*  766 */     int out = dstInit;
/*  767 */     int shift = 0;
/*  768 */     for (int i = 0; i < nShorts; i++) {
/*  769 */       shift = i * 16 + dstPos;
/*  770 */       int bits = (0xFFFF & src[i + srcPos]) << shift;
/*  771 */       int mask = 65535 << shift;
/*  772 */       out = out & (mask ^ 0xFFFFFFFF) | bits;
/*      */     } 
/*  774 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long byteArrayToLong(byte[] src, int srcPos, long dstInit, int dstPos, int nBytes) {
/*  796 */     if ((src.length == 0 && srcPos == 0) || 0 == nBytes) {
/*  797 */       return dstInit;
/*      */     }
/*  799 */     if ((nBytes - 1) * 8 + dstPos >= 64) {
/*  800 */       throw new IllegalArgumentException("(nBytes-1)*8+dstPos is greather or equal to than 64");
/*      */     }
/*      */     
/*  803 */     long out = dstInit;
/*  804 */     int shift = 0;
/*  805 */     for (int i = 0; i < nBytes; i++) {
/*  806 */       shift = i * 8 + dstPos;
/*  807 */       long bits = (0xFFL & src[i + srcPos]) << shift;
/*  808 */       long mask = 255L << shift;
/*  809 */       out = out & (mask ^ 0xFFFFFFFFFFFFFFFFL) | bits;
/*      */     } 
/*  811 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int byteArrayToInt(byte[] src, int srcPos, int dstInit, int dstPos, int nBytes) {
/*  832 */     if ((src.length == 0 && srcPos == 0) || 0 == nBytes) {
/*  833 */       return dstInit;
/*      */     }
/*  835 */     if ((nBytes - 1) * 8 + dstPos >= 32) {
/*  836 */       throw new IllegalArgumentException("(nBytes-1)*8+dstPos is greather or equal to than 32");
/*      */     }
/*      */     
/*  839 */     int out = dstInit;
/*  840 */     int shift = 0;
/*  841 */     for (int i = 0; i < nBytes; i++) {
/*  842 */       shift = i * 8 + dstPos;
/*  843 */       int bits = (0xFF & src[i + srcPos]) << shift;
/*  844 */       int mask = 255 << shift;
/*  845 */       out = out & (mask ^ 0xFFFFFFFF) | bits;
/*      */     } 
/*  847 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short byteArrayToShort(byte[] src, int srcPos, short dstInit, int dstPos, int nBytes) {
/*  869 */     if ((src.length == 0 && srcPos == 0) || 0 == nBytes) {
/*  870 */       return dstInit;
/*      */     }
/*  872 */     if ((nBytes - 1) * 8 + dstPos >= 16) {
/*  873 */       throw new IllegalArgumentException("(nBytes-1)*8+dstPos is greather or equal to than 16");
/*      */     }
/*      */     
/*  876 */     short out = dstInit;
/*  877 */     int shift = 0;
/*  878 */     for (int i = 0; i < nBytes; i++) {
/*  879 */       shift = i * 8 + dstPos;
/*  880 */       int bits = (0xFF & src[i + srcPos]) << shift;
/*  881 */       int mask = 255 << shift;
/*  882 */       out = (short)(out & (mask ^ 0xFFFFFFFF) | bits);
/*      */     } 
/*  884 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long hexToLong(String src, int srcPos, long dstInit, int dstPos, int nHex) {
/*  903 */     if (0 == nHex) {
/*  904 */       return dstInit;
/*      */     }
/*  906 */     if ((nHex - 1) * 4 + dstPos >= 64) {
/*  907 */       throw new IllegalArgumentException("(nHexs-1)*4+dstPos is greather or equal to than 64");
/*      */     }
/*      */     
/*  910 */     long out = dstInit;
/*  911 */     int shift = 0;
/*  912 */     for (int i = 0; i < nHex; i++) {
/*  913 */       shift = i * 4 + dstPos;
/*  914 */       long bits = (0xFL & hexDigitToInt(src.charAt(i + srcPos))) << shift;
/*  915 */       long mask = 15L << shift;
/*  916 */       out = out & (mask ^ 0xFFFFFFFFFFFFFFFFL) | bits;
/*      */     } 
/*  918 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int hexToInt(String src, int srcPos, int dstInit, int dstPos, int nHex) {
/*  937 */     if (0 == nHex) {
/*  938 */       return dstInit;
/*      */     }
/*  940 */     if ((nHex - 1) * 4 + dstPos >= 32) {
/*  941 */       throw new IllegalArgumentException("(nHexs-1)*4+dstPos is greather or equal to than 32");
/*      */     }
/*      */     
/*  944 */     int out = dstInit;
/*  945 */     int shift = 0;
/*  946 */     for (int i = 0; i < nHex; i++) {
/*  947 */       shift = i * 4 + dstPos;
/*  948 */       int bits = (0xF & hexDigitToInt(src.charAt(i + srcPos))) << shift;
/*  949 */       int mask = 15 << shift;
/*  950 */       out = out & (mask ^ 0xFFFFFFFF) | bits;
/*      */     } 
/*  952 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short hexToShort(String src, int srcPos, short dstInit, int dstPos, int nHex) {
/*  971 */     if (0 == nHex) {
/*  972 */       return dstInit;
/*      */     }
/*  974 */     if ((nHex - 1) * 4 + dstPos >= 16) {
/*  975 */       throw new IllegalArgumentException("(nHexs-1)*4+dstPos is greather or equal to than 16");
/*      */     }
/*      */     
/*  978 */     short out = dstInit;
/*  979 */     int shift = 0;
/*  980 */     for (int i = 0; i < nHex; i++) {
/*  981 */       shift = i * 4 + dstPos;
/*  982 */       int bits = (0xF & hexDigitToInt(src.charAt(i + srcPos))) << shift;
/*  983 */       int mask = 15 << shift;
/*  984 */       out = (short)(out & (mask ^ 0xFFFFFFFF) | bits);
/*      */     } 
/*  986 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte hexToByte(String src, int srcPos, byte dstInit, int dstPos, int nHex) {
/* 1005 */     if (0 == nHex) {
/* 1006 */       return dstInit;
/*      */     }
/* 1008 */     if ((nHex - 1) * 4 + dstPos >= 8) {
/* 1009 */       throw new IllegalArgumentException("(nHexs-1)*4+dstPos is greather or equal to than 8");
/*      */     }
/*      */     
/* 1012 */     byte out = dstInit;
/* 1013 */     int shift = 0;
/* 1014 */     for (int i = 0; i < nHex; i++) {
/* 1015 */       shift = i * 4 + dstPos;
/* 1016 */       int bits = (0xF & hexDigitToInt(src.charAt(i + srcPos))) << shift;
/* 1017 */       int mask = 15 << shift;
/* 1018 */       out = (byte)(out & (mask ^ 0xFFFFFFFF) | bits);
/*      */     } 
/* 1020 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static long binaryToLong(boolean[] src, int srcPos, long dstInit, int dstPos, int nBools) {
/* 1042 */     if ((src.length == 0 && srcPos == 0) || 0 == nBools) {
/* 1043 */       return dstInit;
/*      */     }
/* 1045 */     if (nBools - 1 + dstPos >= 64) {
/* 1046 */       throw new IllegalArgumentException("nBools-1+dstPos is greather or equal to than 64");
/*      */     }
/*      */     
/* 1049 */     long out = dstInit;
/* 1050 */     int shift = 0;
/* 1051 */     for (int i = 0; i < nBools; i++) {
/* 1052 */       shift = i * 1 + dstPos;
/* 1053 */       long bits = (src[i + srcPos] ? 1L : 0L) << shift;
/* 1054 */       long mask = 1L << shift;
/* 1055 */       out = out & (mask ^ 0xFFFFFFFFFFFFFFFFL) | bits;
/*      */     } 
/* 1057 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int binaryToInt(boolean[] src, int srcPos, int dstInit, int dstPos, int nBools) {
/* 1078 */     if ((src.length == 0 && srcPos == 0) || 0 == nBools) {
/* 1079 */       return dstInit;
/*      */     }
/* 1081 */     if (nBools - 1 + dstPos >= 32) {
/* 1082 */       throw new IllegalArgumentException("nBools-1+dstPos is greather or equal to than 32");
/*      */     }
/*      */     
/* 1085 */     int out = dstInit;
/* 1086 */     int shift = 0;
/* 1087 */     for (int i = 0; i < nBools; i++) {
/* 1088 */       shift = i * 1 + dstPos;
/* 1089 */       int bits = (src[i + srcPos] ? 1 : 0) << shift;
/* 1090 */       int mask = 1 << shift;
/* 1091 */       out = out & (mask ^ 0xFFFFFFFF) | bits;
/*      */     } 
/* 1093 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short binaryToShort(boolean[] src, int srcPos, short dstInit, int dstPos, int nBools) {
/* 1115 */     if ((src.length == 0 && srcPos == 0) || 0 == nBools) {
/* 1116 */       return dstInit;
/*      */     }
/* 1118 */     if (nBools - 1 + dstPos >= 16) {
/* 1119 */       throw new IllegalArgumentException("nBools-1+dstPos is greather or equal to than 16");
/*      */     }
/*      */     
/* 1122 */     short out = dstInit;
/* 1123 */     int shift = 0;
/* 1124 */     for (int i = 0; i < nBools; i++) {
/* 1125 */       shift = i * 1 + dstPos;
/* 1126 */       int bits = (src[i + srcPos] ? 1 : 0) << shift;
/* 1127 */       int mask = 1 << shift;
/* 1128 */       out = (short)(out & (mask ^ 0xFFFFFFFF) | bits);
/*      */     } 
/* 1130 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte binaryToByte(boolean[] src, int srcPos, byte dstInit, int dstPos, int nBools) {
/* 1152 */     if ((src.length == 0 && srcPos == 0) || 0 == nBools) {
/* 1153 */       return dstInit;
/*      */     }
/* 1155 */     if (nBools - 1 + dstPos >= 8) {
/* 1156 */       throw new IllegalArgumentException("nBools-1+dstPos is greather or equal to than 8");
/*      */     }
/* 1158 */     byte out = dstInit;
/* 1159 */     int shift = 0;
/* 1160 */     for (int i = 0; i < nBools; i++) {
/* 1161 */       shift = i * 1 + dstPos;
/* 1162 */       int bits = (src[i + srcPos] ? 1 : 0) << shift;
/* 1163 */       int mask = 1 << shift;
/* 1164 */       out = (byte)(out & (mask ^ 0xFFFFFFFF) | bits);
/*      */     } 
/* 1166 */     return out;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static int[] longToIntArray(long src, int srcPos, int[] dst, int dstPos, int nInts) {
/* 1187 */     if (0 == nInts) {
/* 1188 */       return dst;
/*      */     }
/* 1190 */     if ((nInts - 1) * 32 + srcPos >= 64) {
/* 1191 */       throw new IllegalArgumentException("(nInts-1)*32+srcPos is greather or equal to than 64");
/*      */     }
/*      */     
/* 1194 */     int shift = 0;
/* 1195 */     for (int i = 0; i < nInts; i++) {
/* 1196 */       shift = i * 32 + srcPos;
/* 1197 */       dst[dstPos + i] = (int)(0xFFFFFFFFFFFFFFFFL & src >> shift);
/*      */     } 
/* 1199 */     return dst;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short[] longToShortArray(long src, int srcPos, short[] dst, int dstPos, int nShorts) {
/* 1221 */     if (0 == nShorts) {
/* 1222 */       return dst;
/*      */     }
/* 1224 */     if ((nShorts - 1) * 16 + srcPos >= 64) {
/* 1225 */       throw new IllegalArgumentException("(nShorts-1)*16+srcPos is greather or equal to than 64");
/*      */     }
/*      */     
/* 1228 */     int shift = 0;
/* 1229 */     for (int i = 0; i < nShorts; i++) {
/* 1230 */       shift = i * 16 + srcPos;
/* 1231 */       dst[dstPos + i] = (short)(int)(0xFFFFL & src >> shift);
/*      */     } 
/* 1233 */     return dst;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static short[] intToShortArray(int src, int srcPos, short[] dst, int dstPos, int nShorts) {
/* 1255 */     if (0 == nShorts) {
/* 1256 */       return dst;
/*      */     }
/* 1258 */     if ((nShorts - 1) * 16 + srcPos >= 32) {
/* 1259 */       throw new IllegalArgumentException("(nShorts-1)*16+srcPos is greather or equal to than 32");
/*      */     }
/*      */     
/* 1262 */     int shift = 0;
/* 1263 */     for (int i = 0; i < nShorts; i++) {
/* 1264 */       shift = i * 16 + srcPos;
/* 1265 */       dst[dstPos + i] = (short)(0xFFFF & src >> shift);
/*      */     } 
/* 1267 */     return dst;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[] longToByteArray(long src, int srcPos, byte[] dst, int dstPos, int nBytes) {
/* 1289 */     if (0 == nBytes) {
/* 1290 */       return dst;
/*      */     }
/* 1292 */     if ((nBytes - 1) * 8 + srcPos >= 64) {
/* 1293 */       throw new IllegalArgumentException("(nBytes-1)*8+srcPos is greather or equal to than 64");
/*      */     }
/*      */     
/* 1296 */     int shift = 0;
/* 1297 */     for (int i = 0; i < nBytes; i++) {
/* 1298 */       shift = i * 8 + srcPos;
/* 1299 */       dst[dstPos + i] = (byte)(int)(0xFFL & src >> shift);
/*      */     } 
/* 1301 */     return dst;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[] intToByteArray(int src, int srcPos, byte[] dst, int dstPos, int nBytes) {
/* 1322 */     if (0 == nBytes) {
/* 1323 */       return dst;
/*      */     }
/* 1325 */     if ((nBytes - 1) * 8 + srcPos >= 32) {
/* 1326 */       throw new IllegalArgumentException("(nBytes-1)*8+srcPos is greather or equal to than 32");
/*      */     }
/*      */     
/* 1329 */     int shift = 0;
/* 1330 */     for (int i = 0; i < nBytes; i++) {
/* 1331 */       shift = i * 8 + srcPos;
/* 1332 */       dst[dstPos + i] = (byte)(0xFF & src >> shift);
/*      */     } 
/* 1334 */     return dst;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[] shortToByteArray(short src, int srcPos, byte[] dst, int dstPos, int nBytes) {
/* 1356 */     if (0 == nBytes) {
/* 1357 */       return dst;
/*      */     }
/* 1359 */     if ((nBytes - 1) * 8 + srcPos >= 16) {
/* 1360 */       throw new IllegalArgumentException("(nBytes-1)*8+srcPos is greather or equal to than 16");
/*      */     }
/*      */     
/* 1363 */     int shift = 0;
/* 1364 */     for (int i = 0; i < nBytes; i++) {
/* 1365 */       shift = i * 8 + srcPos;
/* 1366 */       dst[dstPos + i] = (byte)(0xFF & src >> shift);
/*      */     } 
/* 1368 */     return dst;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String longToHex(long src, int srcPos, String dstInit, int dstPos, int nHexs) {
/* 1388 */     if (0 == nHexs) {
/* 1389 */       return dstInit;
/*      */     }
/* 1391 */     if ((nHexs - 1) * 4 + srcPos >= 64) {
/* 1392 */       throw new IllegalArgumentException("(nHexs-1)*4+srcPos is greather or equal to than 64");
/*      */     }
/*      */     
/* 1395 */     StringBuilder sb = new StringBuilder(dstInit);
/* 1396 */     int shift = 0;
/* 1397 */     int append = sb.length();
/* 1398 */     for (int i = 0; i < nHexs; i++) {
/* 1399 */       shift = i * 4 + srcPos;
/* 1400 */       int bits = (int)(0xFL & src >> shift);
/* 1401 */       if (dstPos + i == append) {
/* 1402 */         append++;
/* 1403 */         sb.append(intToHexDigit(bits));
/*      */       } else {
/* 1405 */         sb.setCharAt(dstPos + i, intToHexDigit(bits));
/*      */       } 
/*      */     } 
/* 1408 */     return sb.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String intToHex(int src, int srcPos, String dstInit, int dstPos, int nHexs) {
/* 1428 */     if (0 == nHexs) {
/* 1429 */       return dstInit;
/*      */     }
/* 1431 */     if ((nHexs - 1) * 4 + srcPos >= 32) {
/* 1432 */       throw new IllegalArgumentException("(nHexs-1)*4+srcPos is greather or equal to than 32");
/*      */     }
/*      */     
/* 1435 */     StringBuilder sb = new StringBuilder(dstInit);
/* 1436 */     int shift = 0;
/* 1437 */     int append = sb.length();
/* 1438 */     for (int i = 0; i < nHexs; i++) {
/* 1439 */       shift = i * 4 + srcPos;
/* 1440 */       int bits = 0xF & src >> shift;
/* 1441 */       if (dstPos + i == append) {
/* 1442 */         append++;
/* 1443 */         sb.append(intToHexDigit(bits));
/*      */       } else {
/* 1445 */         sb.setCharAt(dstPos + i, intToHexDigit(bits));
/*      */       } 
/*      */     } 
/* 1448 */     return sb.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String shortToHex(short src, int srcPos, String dstInit, int dstPos, int nHexs) {
/* 1468 */     if (0 == nHexs) {
/* 1469 */       return dstInit;
/*      */     }
/* 1471 */     if ((nHexs - 1) * 4 + srcPos >= 16) {
/* 1472 */       throw new IllegalArgumentException("(nHexs-1)*4+srcPos is greather or equal to than 16");
/*      */     }
/*      */     
/* 1475 */     StringBuilder sb = new StringBuilder(dstInit);
/* 1476 */     int shift = 0;
/* 1477 */     int append = sb.length();
/* 1478 */     for (int i = 0; i < nHexs; i++) {
/* 1479 */       shift = i * 4 + srcPos;
/* 1480 */       int bits = 0xF & src >> shift;
/* 1481 */       if (dstPos + i == append) {
/* 1482 */         append++;
/* 1483 */         sb.append(intToHexDigit(bits));
/*      */       } else {
/* 1485 */         sb.setCharAt(dstPos + i, intToHexDigit(bits));
/*      */       } 
/*      */     } 
/* 1488 */     return sb.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static String byteToHex(byte src, int srcPos, String dstInit, int dstPos, int nHexs) {
/* 1508 */     if (0 == nHexs) {
/* 1509 */       return dstInit;
/*      */     }
/* 1511 */     if ((nHexs - 1) * 4 + srcPos >= 8) {
/* 1512 */       throw new IllegalArgumentException("(nHexs-1)*4+srcPos is greather or equal to than 8");
/*      */     }
/*      */     
/* 1515 */     StringBuilder sb = new StringBuilder(dstInit);
/* 1516 */     int shift = 0;
/* 1517 */     int append = sb.length();
/* 1518 */     for (int i = 0; i < nHexs; i++) {
/* 1519 */       shift = i * 4 + srcPos;
/* 1520 */       int bits = 0xF & src >> shift;
/* 1521 */       if (dstPos + i == append) {
/* 1522 */         append++;
/* 1523 */         sb.append(intToHexDigit(bits));
/*      */       } else {
/* 1525 */         sb.setCharAt(dstPos + i, intToHexDigit(bits));
/*      */       } 
/*      */     } 
/* 1528 */     return sb.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[] longToBinary(long src, int srcPos, boolean[] dst, int dstPos, int nBools) {
/* 1550 */     if (0 == nBools) {
/* 1551 */       return dst;
/*      */     }
/* 1553 */     if (nBools - 1 + srcPos >= 64) {
/* 1554 */       throw new IllegalArgumentException("nBools-1+srcPos is greather or equal to than 64");
/*      */     }
/*      */     
/* 1557 */     int shift = 0;
/* 1558 */     for (int i = 0; i < nBools; i++) {
/* 1559 */       shift = i * 1 + srcPos;
/* 1560 */       dst[dstPos + i] = ((0x1L & src >> shift) != 0L);
/*      */     } 
/* 1562 */     return dst;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[] intToBinary(int src, int srcPos, boolean[] dst, int dstPos, int nBools) {
/* 1584 */     if (0 == nBools) {
/* 1585 */       return dst;
/*      */     }
/* 1587 */     if (nBools - 1 + srcPos >= 32) {
/* 1588 */       throw new IllegalArgumentException("nBools-1+srcPos is greather or equal to than 32");
/*      */     }
/*      */     
/* 1591 */     int shift = 0;
/* 1592 */     for (int i = 0; i < nBools; i++) {
/* 1593 */       shift = i * 1 + srcPos;
/* 1594 */       dst[dstPos + i] = ((0x1 & src >> shift) != 0);
/*      */     } 
/* 1596 */     return dst;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[] shortToBinary(short src, int srcPos, boolean[] dst, int dstPos, int nBools) {
/* 1618 */     if (0 == nBools) {
/* 1619 */       return dst;
/*      */     }
/* 1621 */     if (nBools - 1 + srcPos >= 16) {
/* 1622 */       throw new IllegalArgumentException("nBools-1+srcPos is greather or equal to than 16");
/*      */     }
/*      */     
/* 1625 */     int shift = 0;
/* 1626 */     assert (nBools - 1) * 1 < 16 - srcPos;
/* 1627 */     for (int i = 0; i < nBools; i++) {
/* 1628 */       shift = i * 1 + srcPos;
/* 1629 */       dst[dstPos + i] = ((0x1 & src >> shift) != 0);
/*      */     } 
/* 1631 */     return dst;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static boolean[] byteToBinary(byte src, int srcPos, boolean[] dst, int dstPos, int nBools) {
/* 1653 */     if (0 == nBools) {
/* 1654 */       return dst;
/*      */     }
/* 1656 */     if (nBools - 1 + srcPos >= 8) {
/* 1657 */       throw new IllegalArgumentException("nBools-1+srcPos is greather or equal to than 8");
/*      */     }
/* 1659 */     int shift = 0;
/* 1660 */     for (int i = 0; i < nBools; i++) {
/* 1661 */       shift = i * 1 + srcPos;
/* 1662 */       dst[dstPos + i] = ((0x1 & src >> shift) != 0);
/*      */     } 
/* 1664 */     return dst;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static byte[] uuidToByteArray(UUID src, byte[] dst, int dstPos, int nBytes) {
/* 1684 */     if (0 == nBytes) {
/* 1685 */       return dst;
/*      */     }
/* 1687 */     if (nBytes > 16) {
/* 1688 */       throw new IllegalArgumentException("nBytes is greather than 16");
/*      */     }
/* 1690 */     longToByteArray(src.getMostSignificantBits(), 0, dst, dstPos, (nBytes > 8) ? 8 : nBytes);
/* 1691 */     if (nBytes >= 8) {
/* 1692 */       longToByteArray(src.getLeastSignificantBits(), 0, dst, dstPos + 8, nBytes - 8);
/*      */     }
/* 1694 */     return dst;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static UUID byteArrayToUuid(byte[] src, int srcPos) {
/* 1711 */     if (src.length - srcPos < 16) {
/* 1712 */       throw new IllegalArgumentException("Need at least 16 bytes for UUID");
/*      */     }
/* 1714 */     return new UUID(byteArrayToLong(src, srcPos, 0L, 0, 8), byteArrayToLong(src, srcPos + 8, 0L, 0, 8));
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\org\apache\commons\lang3\Conversion.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */