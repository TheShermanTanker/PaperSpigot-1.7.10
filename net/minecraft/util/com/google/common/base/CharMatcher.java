/*      */ package net.minecraft.util.com.google.common.base;
/*      */ 
/*      */ import java.util.Arrays;
/*      */ import java.util.BitSet;
/*      */ import javax.annotation.CheckReturnValue;
/*      */ import net.minecraft.util.com.google.common.annotations.Beta;
/*      */ import net.minecraft.util.com.google.common.annotations.GwtCompatible;
/*      */ import net.minecraft.util.com.google.common.annotations.GwtIncompatible;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ @Beta
/*      */ @GwtCompatible(emulated = true)
/*      */ public abstract class CharMatcher
/*      */   implements Predicate<Character>
/*      */ {
/*   66 */   public static final CharMatcher BREAKING_WHITESPACE = new CharMatcher()
/*      */     {
/*      */       public boolean matches(char c) {
/*   69 */         switch (c) {
/*      */           case '\t':
/*      */           case '\n':
/*      */           case '\013':
/*      */           case '\f':
/*      */           case '\r':
/*      */           case ' ':
/*      */           case '':
/*      */           case ' ':
/*      */           case ' ':
/*      */           case ' ':
/*      */           case ' ':
/*      */           case '　':
/*   82 */             return true;
/*      */           case ' ':
/*   84 */             return false;
/*      */         } 
/*   86 */         return (c >= ' ' && c <= ' ');
/*      */       }
/*      */ 
/*      */ 
/*      */       
/*      */       public String toString() {
/*   92 */         return "CharMatcher.BREAKING_WHITESPACE";
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   99 */   public static final CharMatcher ASCII = inRange(false, '', "CharMatcher.ASCII"); private static final String ZEROES = "0٠۰߀०০੦૦୦௦౦೦൦๐໐༠၀႐០᠐᥆᧐᭐᮰᱀᱐꘠꣐꤀꩐０";
/*      */   private static final String NINES;
/*      */   
/*      */   private static class RangesMatcher extends CharMatcher { private final char[] rangeStarts;
/*      */     private final char[] rangeEnds;
/*      */     
/*      */     RangesMatcher(String description, char[] rangeStarts, char[] rangeEnds) {
/*  106 */       super(description);
/*  107 */       this.rangeStarts = rangeStarts;
/*  108 */       this.rangeEnds = rangeEnds;
/*  109 */       Preconditions.checkArgument((rangeStarts.length == rangeEnds.length));
/*  110 */       for (int i = 0; i < rangeStarts.length; i++) {
/*  111 */         Preconditions.checkArgument((rangeStarts[i] <= rangeEnds[i]));
/*  112 */         if (i + 1 < rangeStarts.length) {
/*  113 */           Preconditions.checkArgument((rangeEnds[i] < rangeStarts[i + 1]));
/*      */         }
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean matches(char c) {
/*  120 */       int index = Arrays.binarySearch(this.rangeStarts, c);
/*  121 */       if (index >= 0) {
/*  122 */         return true;
/*      */       }
/*  124 */       index = (index ^ 0xFFFFFFFF) - 1;
/*  125 */       return (index >= 0 && c <= this.rangeEnds[index]);
/*      */     } }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/*  137 */     StringBuilder builder = new StringBuilder("0٠۰߀०০੦૦୦௦౦೦൦๐໐༠၀႐០᠐᥆᧐᭐᮰᱀᱐꘠꣐꤀꩐０".length());
/*  138 */     for (int i = 0; i < "0٠۰߀०০੦૦୦௦౦೦൦๐໐༠၀႐០᠐᥆᧐᭐᮰᱀᱐꘠꣐꤀꩐０".length(); i++) {
/*  139 */       builder.append((char)("0٠۰߀०০੦૦୦௦౦೦൦๐໐༠၀႐០᠐᥆᧐᭐᮰᱀᱐꘠꣐꤀꩐０".charAt(i) + 9));
/*      */     }
/*  141 */     NINES = builder.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  149 */   public static final CharMatcher DIGIT = new RangesMatcher("CharMatcher.DIGIT", "0٠۰߀०০੦૦୦௦౦೦൦๐໐༠၀႐០᠐᥆᧐᭐᮰᱀᱐꘠꣐꤀꩐０".toCharArray(), NINES.toCharArray());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  156 */   public static final CharMatcher JAVA_DIGIT = new CharMatcher("CharMatcher.JAVA_DIGIT") {
/*      */       public boolean matches(char c) {
/*  158 */         return Character.isDigit(c);
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  167 */   public static final CharMatcher JAVA_LETTER = new CharMatcher("CharMatcher.JAVA_LETTER") {
/*      */       public boolean matches(char c) {
/*  169 */         return Character.isLetter(c);
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  177 */   public static final CharMatcher JAVA_LETTER_OR_DIGIT = new CharMatcher("CharMatcher.JAVA_LETTER_OR_DIGIT")
/*      */     {
/*      */       public boolean matches(char c) {
/*  180 */         return Character.isLetterOrDigit(c);
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  188 */   public static final CharMatcher JAVA_UPPER_CASE = new CharMatcher("CharMatcher.JAVA_UPPER_CASE")
/*      */     {
/*      */       public boolean matches(char c) {
/*  191 */         return Character.isUpperCase(c);
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  199 */   public static final CharMatcher JAVA_LOWER_CASE = new CharMatcher("CharMatcher.JAVA_LOWER_CASE")
/*      */     {
/*      */       public boolean matches(char c) {
/*  202 */         return Character.isLowerCase(c);
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  210 */   public static final CharMatcher JAVA_ISO_CONTROL = inRange(false, '\037').or(inRange('', '')).withToString("CharMatcher.JAVA_ISO_CONTROL");
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  220 */   public static final CharMatcher INVISIBLE = new RangesMatcher("CharMatcher.INVISIBLE", "\000­؀۝܏ ᠎   ⁪　?﻿￹￺".toCharArray(), "  ­؄۝܏ ᠎‏ ⁤⁯　﻿￹￻".toCharArray());
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static String showCharacter(char c) {
/*  227 */     String hex = "0123456789ABCDEF";
/*  228 */     char[] tmp = { '\\', 'u', Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE, Character.MIN_VALUE };
/*  229 */     for (int i = 0; i < 4; i++) {
/*  230 */       tmp[5 - i] = hex.charAt(c & 0xF);
/*  231 */       c = (char)(c >> 4);
/*      */     } 
/*  233 */     return String.copyValueOf(tmp);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  245 */   public static final CharMatcher SINGLE_WIDTH = new RangesMatcher("CharMatcher.SINGLE_WIDTH", "\000־א׳؀ݐ฀Ḁ℀ﭐﹰ｡".toCharArray(), "ӹ־ת״ۿݿ๿₯℺﷿﻿ￜ".toCharArray());
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  250 */   public static final CharMatcher ANY = new FastMatcher("CharMatcher.ANY")
/*      */     {
/*      */       public boolean matches(char c) {
/*  253 */         return true;
/*      */       }
/*      */       
/*      */       public int indexIn(CharSequence sequence) {
/*  257 */         return (sequence.length() == 0) ? -1 : 0;
/*      */       }
/*      */       
/*      */       public int indexIn(CharSequence sequence, int start) {
/*  261 */         int length = sequence.length();
/*  262 */         Preconditions.checkPositionIndex(start, length);
/*  263 */         return (start == length) ? -1 : start;
/*      */       }
/*      */       
/*      */       public int lastIndexIn(CharSequence sequence) {
/*  267 */         return sequence.length() - 1;
/*      */       }
/*      */       
/*      */       public boolean matchesAllOf(CharSequence sequence) {
/*  271 */         Preconditions.checkNotNull(sequence);
/*  272 */         return true;
/*      */       }
/*      */       
/*      */       public boolean matchesNoneOf(CharSequence sequence) {
/*  276 */         return (sequence.length() == 0);
/*      */       }
/*      */       
/*      */       public String removeFrom(CharSequence sequence) {
/*  280 */         Preconditions.checkNotNull(sequence);
/*  281 */         return "";
/*      */       }
/*      */       
/*      */       public String replaceFrom(CharSequence sequence, char replacement) {
/*  285 */         char[] array = new char[sequence.length()];
/*  286 */         Arrays.fill(array, replacement);
/*  287 */         return new String(array);
/*      */       }
/*      */       
/*      */       public String replaceFrom(CharSequence sequence, CharSequence replacement) {
/*  291 */         StringBuilder retval = new StringBuilder(sequence.length() * replacement.length());
/*  292 */         for (int i = 0; i < sequence.length(); i++) {
/*  293 */           retval.append(replacement);
/*      */         }
/*  295 */         return retval.toString();
/*      */       }
/*      */       
/*      */       public String collapseFrom(CharSequence sequence, char replacement) {
/*  299 */         return (sequence.length() == 0) ? "" : String.valueOf(replacement);
/*      */       }
/*      */       
/*      */       public String trimFrom(CharSequence sequence) {
/*  303 */         Preconditions.checkNotNull(sequence);
/*  304 */         return "";
/*      */       }
/*      */       
/*      */       public int countIn(CharSequence sequence) {
/*  308 */         return sequence.length();
/*      */       }
/*      */       
/*      */       public CharMatcher and(CharMatcher other) {
/*  312 */         return Preconditions.<CharMatcher>checkNotNull(other);
/*      */       }
/*      */       
/*      */       public CharMatcher or(CharMatcher other) {
/*  316 */         Preconditions.checkNotNull(other);
/*  317 */         return this;
/*      */       }
/*      */       
/*      */       public CharMatcher negate() {
/*  321 */         return NONE;
/*      */       }
/*      */     };
/*      */ 
/*      */   
/*  326 */   public static final CharMatcher NONE = new FastMatcher("CharMatcher.NONE")
/*      */     {
/*      */       public boolean matches(char c) {
/*  329 */         return false;
/*      */       }
/*      */       
/*      */       public int indexIn(CharSequence sequence) {
/*  333 */         Preconditions.checkNotNull(sequence);
/*  334 */         return -1;
/*      */       }
/*      */       
/*      */       public int indexIn(CharSequence sequence, int start) {
/*  338 */         int length = sequence.length();
/*  339 */         Preconditions.checkPositionIndex(start, length);
/*  340 */         return -1;
/*      */       }
/*      */       
/*      */       public int lastIndexIn(CharSequence sequence) {
/*  344 */         Preconditions.checkNotNull(sequence);
/*  345 */         return -1;
/*      */       }
/*      */       
/*      */       public boolean matchesAllOf(CharSequence sequence) {
/*  349 */         return (sequence.length() == 0);
/*      */       }
/*      */       
/*      */       public boolean matchesNoneOf(CharSequence sequence) {
/*  353 */         Preconditions.checkNotNull(sequence);
/*  354 */         return true;
/*      */       }
/*      */       
/*      */       public String removeFrom(CharSequence sequence) {
/*  358 */         return sequence.toString();
/*      */       }
/*      */       
/*      */       public String replaceFrom(CharSequence sequence, char replacement) {
/*  362 */         return sequence.toString();
/*      */       }
/*      */       
/*      */       public String replaceFrom(CharSequence sequence, CharSequence replacement) {
/*  366 */         Preconditions.checkNotNull(replacement);
/*  367 */         return sequence.toString();
/*      */       }
/*      */       
/*      */       public String collapseFrom(CharSequence sequence, char replacement) {
/*  371 */         return sequence.toString();
/*      */       }
/*      */       
/*      */       public String trimFrom(CharSequence sequence) {
/*  375 */         return sequence.toString();
/*      */       }
/*      */ 
/*      */       
/*      */       public String trimLeadingFrom(CharSequence sequence) {
/*  380 */         return sequence.toString();
/*      */       }
/*      */ 
/*      */       
/*      */       public String trimTrailingFrom(CharSequence sequence) {
/*  385 */         return sequence.toString();
/*      */       }
/*      */       
/*      */       public int countIn(CharSequence sequence) {
/*  389 */         Preconditions.checkNotNull(sequence);
/*  390 */         return 0;
/*      */       }
/*      */       
/*      */       public CharMatcher and(CharMatcher other) {
/*  394 */         Preconditions.checkNotNull(other);
/*  395 */         return this;
/*      */       }
/*      */       
/*      */       public CharMatcher or(CharMatcher other) {
/*  399 */         return Preconditions.<CharMatcher>checkNotNull(other);
/*      */       }
/*      */       
/*      */       public CharMatcher negate() {
/*  403 */         return ANY;
/*      */       }
/*      */     };
/*      */ 
/*      */   
/*      */   final String description;
/*      */   
/*      */   private static final int DISTINCT_CHARS = 65536;
/*      */   
/*      */   public static CharMatcher is(final char match) {
/*  413 */     String description = "CharMatcher.is('" + showCharacter(match) + "')";
/*  414 */     return new FastMatcher(description) {
/*      */         public boolean matches(char c) {
/*  416 */           return (c == match);
/*      */         }
/*      */         
/*      */         public String replaceFrom(CharSequence sequence, char replacement) {
/*  420 */           return sequence.toString().replace(match, replacement);
/*      */         }
/*      */         
/*      */         public CharMatcher and(CharMatcher other) {
/*  424 */           return other.matches(match) ? this : NONE;
/*      */         }
/*      */         
/*      */         public CharMatcher or(CharMatcher other) {
/*  428 */           return other.matches(match) ? other : super.or(other);
/*      */         }
/*      */         
/*      */         public CharMatcher negate() {
/*  432 */           return isNot(match);
/*      */         }
/*      */ 
/*      */         
/*      */         @GwtIncompatible("java.util.BitSet")
/*      */         void setBits(BitSet table) {
/*  438 */           table.set(match);
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CharMatcher isNot(final char match) {
/*  449 */     String description = "CharMatcher.isNot('" + showCharacter(match) + "')";
/*  450 */     return new FastMatcher(description) {
/*      */         public boolean matches(char c) {
/*  452 */           return (c != match);
/*      */         }
/*      */         
/*      */         public CharMatcher and(CharMatcher other) {
/*  456 */           return other.matches(match) ? super.and(other) : other;
/*      */         }
/*      */         
/*      */         public CharMatcher or(CharMatcher other) {
/*  460 */           return other.matches(match) ? ANY : this;
/*      */         }
/*      */ 
/*      */         
/*      */         @GwtIncompatible("java.util.BitSet")
/*      */         void setBits(BitSet table) {
/*  466 */           table.set(0, match);
/*  467 */           table.set(match + 1, 65536);
/*      */         }
/*      */         
/*      */         public CharMatcher negate() {
/*  471 */           return is(match);
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CharMatcher anyOf(CharSequence sequence) {
/*  481 */     switch (sequence.length()) {
/*      */       case 0:
/*  483 */         return NONE;
/*      */       case 1:
/*  485 */         return is(sequence.charAt(0));
/*      */       case 2:
/*  487 */         return isEither(sequence.charAt(0), sequence.charAt(1));
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  492 */     final char[] chars = sequence.toString().toCharArray();
/*  493 */     Arrays.sort(chars);
/*  494 */     StringBuilder description = new StringBuilder("CharMatcher.anyOf(\"");
/*  495 */     for (char c : chars) {
/*  496 */       description.append(showCharacter(c));
/*      */     }
/*  498 */     description.append("\")");
/*  499 */     return new CharMatcher(description.toString()) {
/*      */         public boolean matches(char c) {
/*  501 */           return (Arrays.binarySearch(chars, c) >= 0);
/*      */         }
/*      */ 
/*      */         
/*      */         @GwtIncompatible("java.util.BitSet")
/*      */         void setBits(BitSet table) {
/*  507 */           for (char c : chars) {
/*  508 */             table.set(c);
/*      */           }
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static CharMatcher isEither(final char match1, final char match2) {
/*  517 */     String description = "CharMatcher.anyOf(\"" + showCharacter(match1) + showCharacter(match2) + "\")";
/*      */     
/*  519 */     return new FastMatcher(description) {
/*      */         public boolean matches(char c) {
/*  521 */           return (c == match1 || c == match2);
/*      */         }
/*      */         
/*      */         @GwtIncompatible("java.util.BitSet")
/*      */         void setBits(BitSet table) {
/*  526 */           table.set(match1);
/*  527 */           table.set(match2);
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CharMatcher noneOf(CharSequence sequence) {
/*  537 */     return anyOf(sequence).negate();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CharMatcher inRange(char startInclusive, char endInclusive) {
/*  548 */     Preconditions.checkArgument((endInclusive >= startInclusive));
/*  549 */     String description = "CharMatcher.inRange('" + showCharacter(startInclusive) + "', '" + showCharacter(endInclusive) + "')";
/*      */ 
/*      */     
/*  552 */     return inRange(startInclusive, endInclusive, description);
/*      */   }
/*      */ 
/*      */   
/*      */   static CharMatcher inRange(final char startInclusive, final char endInclusive, String description) {
/*  557 */     return new FastMatcher(description) {
/*      */         public boolean matches(char c) {
/*  559 */           return (startInclusive <= c && c <= endInclusive);
/*      */         }
/*      */         
/*      */         @GwtIncompatible("java.util.BitSet")
/*      */         void setBits(BitSet table) {
/*  564 */           table.set(startInclusive, endInclusive + 1);
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CharMatcher forPredicate(final Predicate<? super Character> predicate) {
/*  574 */     Preconditions.checkNotNull(predicate);
/*  575 */     if (predicate instanceof CharMatcher) {
/*  576 */       return (CharMatcher)predicate;
/*      */     }
/*  578 */     String description = "CharMatcher.forPredicate(" + predicate + ")";
/*  579 */     return new CharMatcher(description) {
/*      */         public boolean matches(char c) {
/*  581 */           return predicate.apply(Character.valueOf(c));
/*      */         }
/*      */         
/*      */         public boolean apply(Character character) {
/*  585 */           return predicate.apply(Preconditions.checkNotNull(character));
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   CharMatcher(String description) {
/*  599 */     this.description = description;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected CharMatcher() {
/*  607 */     this.description = super.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CharMatcher negate() {
/*  621 */     return new NegatedMatcher(this);
/*      */   }
/*      */   
/*      */   private static class NegatedMatcher extends CharMatcher {
/*      */     final CharMatcher original;
/*      */     
/*      */     NegatedMatcher(String toString, CharMatcher original) {
/*  628 */       super(toString);
/*  629 */       this.original = original;
/*      */     }
/*      */     
/*      */     NegatedMatcher(CharMatcher original) {
/*  633 */       this(original + ".negate()", original);
/*      */     }
/*      */     
/*      */     public boolean matches(char c) {
/*  637 */       return !this.original.matches(c);
/*      */     }
/*      */     
/*      */     public boolean matchesAllOf(CharSequence sequence) {
/*  641 */       return this.original.matchesNoneOf(sequence);
/*      */     }
/*      */     
/*      */     public boolean matchesNoneOf(CharSequence sequence) {
/*  645 */       return this.original.matchesAllOf(sequence);
/*      */     }
/*      */     
/*      */     public int countIn(CharSequence sequence) {
/*  649 */       return sequence.length() - this.original.countIn(sequence);
/*      */     }
/*      */ 
/*      */     
/*      */     @GwtIncompatible("java.util.BitSet")
/*      */     void setBits(BitSet table) {
/*  655 */       BitSet tmp = new BitSet();
/*  656 */       this.original.setBits(tmp);
/*  657 */       tmp.flip(0, 65536);
/*  658 */       table.or(tmp);
/*      */     }
/*      */     
/*      */     public CharMatcher negate() {
/*  662 */       return this.original;
/*      */     }
/*      */ 
/*      */     
/*      */     CharMatcher withToString(String description) {
/*  667 */       return new NegatedMatcher(description, this.original);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CharMatcher and(CharMatcher other) {
/*  675 */     return new And(this, Preconditions.<CharMatcher>checkNotNull(other));
/*      */   }
/*      */   
/*      */   private static class And extends CharMatcher {
/*      */     final CharMatcher first;
/*      */     final CharMatcher second;
/*      */     
/*      */     And(CharMatcher a, CharMatcher b) {
/*  683 */       this(a, b, "CharMatcher.and(" + a + ", " + b + ")");
/*      */     }
/*      */     
/*      */     And(CharMatcher a, CharMatcher b, String description) {
/*  687 */       super(description);
/*  688 */       this.first = Preconditions.<CharMatcher>checkNotNull(a);
/*  689 */       this.second = Preconditions.<CharMatcher>checkNotNull(b);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean matches(char c) {
/*  694 */       return (this.first.matches(c) && this.second.matches(c));
/*      */     }
/*      */ 
/*      */     
/*      */     @GwtIncompatible("java.util.BitSet")
/*      */     void setBits(BitSet table) {
/*  700 */       BitSet tmp1 = new BitSet();
/*  701 */       this.first.setBits(tmp1);
/*  702 */       BitSet tmp2 = new BitSet();
/*  703 */       this.second.setBits(tmp2);
/*  704 */       tmp1.and(tmp2);
/*  705 */       table.or(tmp1);
/*      */     }
/*      */ 
/*      */     
/*      */     CharMatcher withToString(String description) {
/*  710 */       return new And(this.first, this.second, description);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CharMatcher or(CharMatcher other) {
/*  718 */     return new Or(this, Preconditions.<CharMatcher>checkNotNull(other));
/*      */   }
/*      */   
/*      */   private static class Or extends CharMatcher {
/*      */     final CharMatcher first;
/*      */     final CharMatcher second;
/*      */     
/*      */     Or(CharMatcher a, CharMatcher b, String description) {
/*  726 */       super(description);
/*  727 */       this.first = Preconditions.<CharMatcher>checkNotNull(a);
/*  728 */       this.second = Preconditions.<CharMatcher>checkNotNull(b);
/*      */     }
/*      */     
/*      */     Or(CharMatcher a, CharMatcher b) {
/*  732 */       this(a, b, "CharMatcher.or(" + a + ", " + b + ")");
/*      */     }
/*      */ 
/*      */     
/*      */     @GwtIncompatible("java.util.BitSet")
/*      */     void setBits(BitSet table) {
/*  738 */       this.first.setBits(table);
/*  739 */       this.second.setBits(table);
/*      */     }
/*      */ 
/*      */     
/*      */     public boolean matches(char c) {
/*  744 */       return (this.first.matches(c) || this.second.matches(c));
/*      */     }
/*      */ 
/*      */     
/*      */     CharMatcher withToString(String description) {
/*  749 */       return new Or(this.first, this.second, description);
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
/*      */   public CharMatcher precomputed() {
/*  763 */     return Platform.precomputeCharMatcher(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   CharMatcher withToString(String description) {
/*  773 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @GwtIncompatible("java.util.BitSet")
/*      */   CharMatcher precomputedInternal() {
/*  790 */     BitSet table = new BitSet();
/*  791 */     setBits(table);
/*  792 */     int totalCharacters = table.cardinality();
/*  793 */     if (totalCharacters * 2 <= 65536) {
/*  794 */       return precomputedPositive(totalCharacters, table, this.description);
/*      */     }
/*      */     
/*  797 */     table.flip(0, 65536);
/*  798 */     int negatedCharacters = 65536 - totalCharacters;
/*  799 */     String suffix = ".negate()";
/*  800 */     String negatedDescription = this.description.endsWith(suffix) ? this.description.substring(0, this.description.length() - suffix.length()) : (this.description + suffix);
/*      */ 
/*      */     
/*  803 */     return new NegatedFastMatcher(toString(), precomputedPositive(negatedCharacters, table, negatedDescription));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static abstract class FastMatcher
/*      */     extends CharMatcher
/*      */   {
/*      */     FastMatcher() {}
/*      */ 
/*      */ 
/*      */     
/*      */     FastMatcher(String description) {
/*  817 */       super(description);
/*      */     }
/*      */ 
/*      */     
/*      */     public final CharMatcher precomputed() {
/*  822 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     public CharMatcher negate() {
/*  827 */       return new CharMatcher.NegatedFastMatcher(this);
/*      */     }
/*      */   }
/*      */   
/*      */   static final class NegatedFastMatcher extends NegatedMatcher {
/*      */     NegatedFastMatcher(CharMatcher original) {
/*  833 */       super(original);
/*      */     }
/*      */     
/*      */     NegatedFastMatcher(String toString, CharMatcher original) {
/*  837 */       super(toString, original);
/*      */     }
/*      */ 
/*      */     
/*      */     public final CharMatcher precomputed() {
/*  842 */       return this;
/*      */     }
/*      */ 
/*      */     
/*      */     CharMatcher withToString(String description) {
/*  847 */       return new NegatedFastMatcher(description, this.original);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @GwtIncompatible("java.util.BitSet")
/*      */   private static CharMatcher precomputedPositive(int totalCharacters, BitSet table, String description) {
/*      */     char c1;
/*      */     char c2;
/*  859 */     switch (totalCharacters) {
/*      */       case 0:
/*  861 */         return NONE;
/*      */       case 1:
/*  863 */         return is((char)table.nextSetBit(0));
/*      */       case 2:
/*  865 */         c1 = (char)table.nextSetBit(0);
/*  866 */         c2 = (char)table.nextSetBit(c1 + 1);
/*  867 */         return isEither(c1, c2);
/*      */     } 
/*  869 */     return isSmall(totalCharacters, table.length()) ? SmallCharMatcher.from(table, description) : new BitSetMatcher(table, description);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean isSmall(int totalCharacters, int tableLength) {
/*  876 */     return (totalCharacters <= 1023 && tableLength > totalCharacters * 4 * 16);
/*      */   }
/*      */   
/*      */   @GwtIncompatible("java.util.BitSet")
/*      */   private static class BitSetMatcher
/*      */     extends FastMatcher
/*      */   {
/*      */     private final BitSet table;
/*      */     
/*      */     private BitSetMatcher(BitSet table, String description) {
/*  886 */       super(description);
/*  887 */       if (table.length() + 64 < table.size()) {
/*  888 */         table = (BitSet)table.clone();
/*      */       }
/*      */       
/*  891 */       this.table = table;
/*      */     }
/*      */     
/*      */     public boolean matches(char c) {
/*  895 */       return this.table.get(c);
/*      */     }
/*      */ 
/*      */     
/*      */     void setBits(BitSet bitSet) {
/*  900 */       bitSet.or(this.table);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @GwtIncompatible("java.util.BitSet")
/*      */   void setBits(BitSet table) {
/*  909 */     for (int c = 65535; c >= 0; c--) {
/*  910 */       if (matches((char)c)) {
/*  911 */         table.set(c);
/*      */       }
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
/*      */   public boolean matchesAnyOf(CharSequence sequence) {
/*  930 */     return !matchesNoneOf(sequence);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean matchesAllOf(CharSequence sequence) {
/*  944 */     for (int i = sequence.length() - 1; i >= 0; i--) {
/*  945 */       if (!matches(sequence.charAt(i))) {
/*  946 */         return false;
/*      */       }
/*      */     } 
/*  949 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean matchesNoneOf(CharSequence sequence) {
/*  964 */     return (indexIn(sequence) == -1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int indexIn(CharSequence sequence) {
/*  978 */     int length = sequence.length();
/*  979 */     for (int i = 0; i < length; i++) {
/*  980 */       if (matches(sequence.charAt(i))) {
/*  981 */         return i;
/*      */       }
/*      */     } 
/*  984 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int indexIn(CharSequence sequence, int start) {
/* 1003 */     int length = sequence.length();
/* 1004 */     Preconditions.checkPositionIndex(start, length);
/* 1005 */     for (int i = start; i < length; i++) {
/* 1006 */       if (matches(sequence.charAt(i))) {
/* 1007 */         return i;
/*      */       }
/*      */     } 
/* 1010 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int lastIndexIn(CharSequence sequence) {
/* 1024 */     for (int i = sequence.length() - 1; i >= 0; i--) {
/* 1025 */       if (matches(sequence.charAt(i))) {
/* 1026 */         return i;
/*      */       }
/*      */     } 
/* 1029 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int countIn(CharSequence sequence) {
/* 1036 */     int count = 0;
/* 1037 */     for (int i = 0; i < sequence.length(); i++) {
/* 1038 */       if (matches(sequence.charAt(i))) {
/* 1039 */         count++;
/*      */       }
/*      */     } 
/* 1042 */     return count;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @CheckReturnValue
/*      */   public String removeFrom(CharSequence sequence) {
/* 1055 */     String string = sequence.toString();
/* 1056 */     int pos = indexIn(string);
/* 1057 */     if (pos == -1) {
/* 1058 */       return string;
/*      */     }
/*      */     
/* 1061 */     char[] chars = string.toCharArray();
/* 1062 */     int spread = 1;
/*      */ 
/*      */     
/*      */     while (true) {
/* 1066 */       pos++;
/*      */       
/* 1068 */       while (pos != chars.length) {
/*      */ 
/*      */         
/* 1071 */         if (matches(chars[pos]))
/*      */         
/*      */         { 
/*      */ 
/*      */ 
/*      */           
/* 1077 */           spread++; continue; }  chars[pos - spread] = chars[pos]; pos++;
/*      */       }  break;
/* 1079 */     }  return new String(chars, 0, pos - spread);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @CheckReturnValue
/*      */   public String retainFrom(CharSequence sequence) {
/* 1092 */     return negate().removeFrom(sequence);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @CheckReturnValue
/*      */   public String replaceFrom(CharSequence sequence, char replacement) {
/* 1114 */     String string = sequence.toString();
/* 1115 */     int pos = indexIn(string);
/* 1116 */     if (pos == -1) {
/* 1117 */       return string;
/*      */     }
/* 1119 */     char[] chars = string.toCharArray();
/* 1120 */     chars[pos] = replacement;
/* 1121 */     for (int i = pos + 1; i < chars.length; i++) {
/* 1122 */       if (matches(chars[i])) {
/* 1123 */         chars[i] = replacement;
/*      */       }
/*      */     } 
/* 1126 */     return new String(chars);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @CheckReturnValue
/*      */   public String replaceFrom(CharSequence sequence, CharSequence replacement) {
/* 1147 */     int replacementLen = replacement.length();
/* 1148 */     if (replacementLen == 0) {
/* 1149 */       return removeFrom(sequence);
/*      */     }
/* 1151 */     if (replacementLen == 1) {
/* 1152 */       return replaceFrom(sequence, replacement.charAt(0));
/*      */     }
/*      */     
/* 1155 */     String string = sequence.toString();
/* 1156 */     int pos = indexIn(string);
/* 1157 */     if (pos == -1) {
/* 1158 */       return string;
/*      */     }
/*      */     
/* 1161 */     int len = string.length();
/* 1162 */     StringBuilder buf = new StringBuilder(len * 3 / 2 + 16);
/*      */     
/* 1164 */     int oldpos = 0;
/*      */     do {
/* 1166 */       buf.append(string, oldpos, pos);
/* 1167 */       buf.append(replacement);
/* 1168 */       oldpos = pos + 1;
/* 1169 */       pos = indexIn(string, oldpos);
/* 1170 */     } while (pos != -1);
/*      */     
/* 1172 */     buf.append(string, oldpos, len);
/* 1173 */     return buf.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @CheckReturnValue
/*      */   public String trimFrom(CharSequence sequence) {
/* 1192 */     int len = sequence.length();
/*      */     
/*      */     int first;
/*      */     
/* 1196 */     for (first = 0; first < len && 
/* 1197 */       matches(sequence.charAt(first)); first++);
/*      */     
/*      */     int last;
/*      */     
/* 1201 */     for (last = len - 1; last > first && 
/* 1202 */       matches(sequence.charAt(last)); last--);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1207 */     return sequence.subSequence(first, last + 1).toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @CheckReturnValue
/*      */   public String trimLeadingFrom(CharSequence sequence) {
/* 1220 */     int len = sequence.length();
/* 1221 */     for (int first = 0; first < len; first++) {
/* 1222 */       if (!matches(sequence.charAt(first))) {
/* 1223 */         return sequence.subSequence(first, len).toString();
/*      */       }
/*      */     } 
/* 1226 */     return "";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @CheckReturnValue
/*      */   public String trimTrailingFrom(CharSequence sequence) {
/* 1239 */     int len = sequence.length();
/* 1240 */     for (int last = len - 1; last >= 0; last--) {
/* 1241 */       if (!matches(sequence.charAt(last))) {
/* 1242 */         return sequence.subSequence(0, last + 1).toString();
/*      */       }
/*      */     } 
/* 1245 */     return "";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @CheckReturnValue
/*      */   public String collapseFrom(CharSequence sequence, char replacement) {
/* 1269 */     int len = sequence.length();
/* 1270 */     for (int i = 0; i < len; i++) {
/* 1271 */       char c = sequence.charAt(i);
/* 1272 */       if (matches(c)) {
/* 1273 */         if (c == replacement && (i == len - 1 || !matches(sequence.charAt(i + 1)))) {
/*      */ 
/*      */           
/* 1276 */           i++;
/*      */         } else {
/* 1278 */           StringBuilder builder = (new StringBuilder(len)).append(sequence.subSequence(0, i)).append(replacement);
/*      */ 
/*      */           
/* 1281 */           return finishCollapseFrom(sequence, i + 1, len, replacement, builder, true);
/*      */         } 
/*      */       }
/*      */     } 
/*      */     
/* 1286 */     return sequence.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @CheckReturnValue
/*      */   public String trimAndCollapseFrom(CharSequence sequence, char replacement) {
/* 1297 */     int len = sequence.length();
/*      */     
/*      */     int first;
/*      */     
/* 1301 */     for (first = 0; first < len && matches(sequence.charAt(first)); first++); int last;
/* 1302 */     for (last = len - 1; last > first && matches(sequence.charAt(last)); last--);
/*      */     
/* 1304 */     return (first == 0 && last == len - 1) ? collapseFrom(sequence, replacement) : finishCollapseFrom(sequence, first, last + 1, replacement, new StringBuilder(last + 1 - first), false);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String finishCollapseFrom(CharSequence sequence, int start, int end, char replacement, StringBuilder builder, boolean inMatchingGroup) {
/* 1315 */     for (int i = start; i < end; i++) {
/* 1316 */       char c = sequence.charAt(i);
/* 1317 */       if (matches(c)) {
/* 1318 */         if (!inMatchingGroup) {
/* 1319 */           builder.append(replacement);
/* 1320 */           inMatchingGroup = true;
/*      */         } 
/*      */       } else {
/* 1323 */         builder.append(c);
/* 1324 */         inMatchingGroup = false;
/*      */       } 
/*      */     } 
/* 1327 */     return builder.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public boolean apply(Character character) {
/* 1337 */     return matches(character.charValue());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1346 */     return this.description;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1360 */   public static final CharMatcher WHITESPACE = new FastMatcher("WHITESPACE")
/*      */     {
/*      */       private static final String TABLE = "\t　\n\t\t\t \t\t  \t\t\t\t\t᠎\t \t\t\t   \t\t\t\r\t\t  \t \t  \t\t\t\f \t\t \t\t  \t\t\t\013\t\t\t\t\t\t  \t";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       public boolean matches(char c) {
/* 1372 */         return ("\t　\n\t\t\t \t\t  \t\t\t\t\t᠎\t \t\t\t   \t\t\t\r\t\t  \t \t  \t\t\t\f \t\t \t\t  \t\t\t\013\t\t\t\t\t\t  \t".charAt(-844444961 * c >>> 26) == c);
/*      */       }
/*      */ 
/*      */       
/*      */       @GwtIncompatible("java.util.BitSet")
/*      */       void setBits(BitSet table) {
/* 1378 */         for (int i = 0; i < "\t　\n\t\t\t \t\t  \t\t\t\t\t᠎\t \t\t\t   \t\t\t\r\t\t  \t \t  \t\t\t\f \t\t \t\t  \t\t\t\013\t\t\t\t\t\t  \t".length(); i++)
/* 1379 */           table.set("\t　\n\t\t\t \t\t  \t\t\t\t\t᠎\t \t\t\t   \t\t\t\r\t\t  \t \t  \t\t\t\f \t\t \t\t  \t\t\t\013\t\t\t\t\t\t  \t".charAt(i)); 
/*      */       }
/*      */     };
/*      */   
/*      */   public abstract boolean matches(char paramChar);
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\com\google\common\base\CharMatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */