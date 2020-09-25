/*      */ package com.google.common.base;
/*      */ 
/*      */ import com.google.common.annotations.Beta;
/*      */ import com.google.common.annotations.GwtCompatible;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.List;
/*      */ import javax.annotation.CheckReturnValue;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*      */ @GwtCompatible
/*      */ public abstract class CharMatcher
/*      */   implements Predicate<Character>
/*      */ {
/*      */   private static final String BREAKING_WHITESPACE_CHARS = "\t\n\013\f\r     　";
/*      */   private static final String NON_BREAKING_WHITESPACE_CHARS = " ᠎ ";
/*   71 */   public static final CharMatcher WHITESPACE = anyOf("\t\n\013\f\r     　 ᠎ ").or(inRange(' ', ' ')).precomputed();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   83 */   public static final CharMatcher BREAKING_WHITESPACE = anyOf("\t\n\013\f\r     　").or(inRange(' ', ' ')).or(inRange(' ', ' ')).precomputed();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   92 */   public static final CharMatcher ASCII = inRange(false, '');
/*      */ 
/*      */ 
/*      */   
/*      */   public static final CharMatcher DIGIT;
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/*  101 */     CharMatcher digit = inRange('0', '9');
/*  102 */     String zeroes = "٠۰߀०০੦૦୦௦౦೦൦๐໐༠၀႐០᠐᥆᧐᭐᮰᱀᱐꘠꣐꤀꩐０";
/*      */ 
/*      */ 
/*      */     
/*  106 */     for (char base : zeroes.toCharArray()) {
/*  107 */       digit = digit.or(inRange(base, (char)(base + 9)));
/*      */     }
/*  109 */     DIGIT = digit.precomputed();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  118 */   public static final CharMatcher JAVA_WHITESPACE = inRange('\t', '\r').or(inRange('\034', ' ')).or(is(' ')).or(is('᠎')).or(inRange(' ', ' ')).or(inRange(' ', '​')).or(inRange(' ', ' ')).or(is(' ')).or(is('　')).precomputed();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  134 */   public static final CharMatcher JAVA_DIGIT = new CharMatcher() {
/*      */       public boolean matches(char c) {
/*  136 */         return Character.isDigit(c);
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  145 */   public static final CharMatcher JAVA_LETTER = new CharMatcher() {
/*      */       public boolean matches(char c) {
/*  147 */         return Character.isLetter(c);
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  155 */   public static final CharMatcher JAVA_LETTER_OR_DIGIT = new CharMatcher() {
/*      */       public boolean matches(char c) {
/*  157 */         return Character.isLetterOrDigit(c);
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  165 */   public static final CharMatcher JAVA_UPPER_CASE = new CharMatcher() {
/*      */       public boolean matches(char c) {
/*  167 */         return Character.isUpperCase(c);
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  175 */   public static final CharMatcher JAVA_LOWER_CASE = new CharMatcher() {
/*      */       public boolean matches(char c) {
/*  177 */         return Character.isLowerCase(c);
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  185 */   public static final CharMatcher JAVA_ISO_CONTROL = inRange(false, '\037').or(inRange('', ''));
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  193 */   public static final CharMatcher INVISIBLE = inRange(false, ' ').or(inRange('', ' ')).or(is('­')).or(inRange('؀', '؃')).or(anyOf("۝܏ ឴឵᠎")).or(inRange(' ', '‏')).or(inRange(' ', ' ')).or(inRange(' ', '⁤')).or(inRange('⁪', '⁯')).or(is('　')).or(inRange('?', '')).or(anyOf("﻿￹￺￻")).precomputed();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  215 */   public static final CharMatcher SINGLE_WIDTH = inRange(false, 'ӹ').or(is('־')).or(inRange('א', 'ת')).or(is('׳')).or(is('״')).or(inRange('؀', 'ۿ')).or(inRange('ݐ', 'ݿ')).or(inRange('฀', '๿')).or(inRange('Ḁ', '₯')).or(inRange('℀', '℺')).or(inRange('ﭐ', '﷿')).or(inRange('ﹰ', '﻿')).or(inRange('｡', 'ￜ')).precomputed();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  231 */   public static final CharMatcher ANY = new CharMatcher()
/*      */     {
/*      */       public boolean matches(char c) {
/*  234 */         return true;
/*      */       }
/*      */       
/*      */       public int indexIn(CharSequence sequence) {
/*  238 */         return (sequence.length() == 0) ? -1 : 0;
/*      */       }
/*      */       
/*      */       public int indexIn(CharSequence sequence, int start) {
/*  242 */         int length = sequence.length();
/*  243 */         Preconditions.checkPositionIndex(start, length);
/*  244 */         return (start == length) ? -1 : start;
/*      */       }
/*      */       
/*      */       public int lastIndexIn(CharSequence sequence) {
/*  248 */         return sequence.length() - 1;
/*      */       }
/*      */       
/*      */       public boolean matchesAllOf(CharSequence sequence) {
/*  252 */         Preconditions.checkNotNull(sequence);
/*  253 */         return true;
/*      */       }
/*      */       
/*      */       public boolean matchesNoneOf(CharSequence sequence) {
/*  257 */         return (sequence.length() == 0);
/*      */       }
/*      */       
/*      */       public String removeFrom(CharSequence sequence) {
/*  261 */         Preconditions.checkNotNull(sequence);
/*  262 */         return "";
/*      */       }
/*      */       
/*      */       public String replaceFrom(CharSequence sequence, char replacement) {
/*  266 */         char[] array = new char[sequence.length()];
/*  267 */         Arrays.fill(array, replacement);
/*  268 */         return new String(array);
/*      */       }
/*      */       
/*      */       public String replaceFrom(CharSequence sequence, CharSequence replacement) {
/*  272 */         StringBuilder retval = new StringBuilder(sequence.length() * replacement.length());
/*  273 */         for (int i = 0; i < sequence.length(); i++) {
/*  274 */           retval.append(replacement);
/*      */         }
/*  276 */         return retval.toString();
/*      */       }
/*      */       
/*      */       public String collapseFrom(CharSequence sequence, char replacement) {
/*  280 */         return (sequence.length() == 0) ? "" : String.valueOf(replacement);
/*      */       }
/*      */       
/*      */       public String trimFrom(CharSequence sequence) {
/*  284 */         Preconditions.checkNotNull(sequence);
/*  285 */         return "";
/*      */       }
/*      */       
/*      */       public int countIn(CharSequence sequence) {
/*  289 */         return sequence.length();
/*      */       }
/*      */       
/*      */       public CharMatcher and(CharMatcher other) {
/*  293 */         return Preconditions.<CharMatcher>checkNotNull(other);
/*      */       }
/*      */       
/*      */       public CharMatcher or(CharMatcher other) {
/*  297 */         Preconditions.checkNotNull(other);
/*  298 */         return this;
/*      */       }
/*      */       
/*      */       public CharMatcher negate() {
/*  302 */         return NONE;
/*      */       }
/*      */       
/*      */       public CharMatcher precomputed() {
/*  306 */         return this;
/*      */       }
/*      */     };
/*      */ 
/*      */   
/*  311 */   public static final CharMatcher NONE = new CharMatcher()
/*      */     {
/*      */       public boolean matches(char c) {
/*  314 */         return false;
/*      */       }
/*      */       
/*      */       public int indexIn(CharSequence sequence) {
/*  318 */         Preconditions.checkNotNull(sequence);
/*  319 */         return -1;
/*      */       }
/*      */       
/*      */       public int indexIn(CharSequence sequence, int start) {
/*  323 */         int length = sequence.length();
/*  324 */         Preconditions.checkPositionIndex(start, length);
/*  325 */         return -1;
/*      */       }
/*      */       
/*      */       public int lastIndexIn(CharSequence sequence) {
/*  329 */         Preconditions.checkNotNull(sequence);
/*  330 */         return -1;
/*      */       }
/*      */       
/*      */       public boolean matchesAllOf(CharSequence sequence) {
/*  334 */         return (sequence.length() == 0);
/*      */       }
/*      */       
/*      */       public boolean matchesNoneOf(CharSequence sequence) {
/*  338 */         Preconditions.checkNotNull(sequence);
/*  339 */         return true;
/*      */       }
/*      */       
/*      */       public String removeFrom(CharSequence sequence) {
/*  343 */         return sequence.toString();
/*      */       }
/*      */       
/*      */       public String replaceFrom(CharSequence sequence, char replacement) {
/*  347 */         return sequence.toString();
/*      */       }
/*      */       
/*      */       public String replaceFrom(CharSequence sequence, CharSequence replacement) {
/*  351 */         Preconditions.checkNotNull(replacement);
/*  352 */         return sequence.toString();
/*      */       }
/*      */       
/*      */       public String collapseFrom(CharSequence sequence, char replacement) {
/*  356 */         return sequence.toString();
/*      */       }
/*      */       
/*      */       public String trimFrom(CharSequence sequence) {
/*  360 */         return sequence.toString();
/*      */       }
/*      */       
/*      */       public int countIn(CharSequence sequence) {
/*  364 */         Preconditions.checkNotNull(sequence);
/*  365 */         return 0;
/*      */       }
/*      */       
/*      */       public CharMatcher and(CharMatcher other) {
/*  369 */         Preconditions.checkNotNull(other);
/*  370 */         return this;
/*      */       }
/*      */       
/*      */       public CharMatcher or(CharMatcher other) {
/*  374 */         return Preconditions.<CharMatcher>checkNotNull(other);
/*      */       }
/*      */       
/*      */       public CharMatcher negate() {
/*  378 */         return ANY;
/*      */       }
/*      */       
/*      */       void setBits(CharMatcher.LookupTable table) {}
/*      */       
/*      */       public CharMatcher precomputed() {
/*  384 */         return this;
/*      */       }
/*      */     };
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CharMatcher is(final char match) {
/*  394 */     return new CharMatcher() {
/*      */         public boolean matches(char c) {
/*  396 */           return (c == match);
/*      */         }
/*      */         
/*      */         public String replaceFrom(CharSequence sequence, char replacement) {
/*  400 */           return sequence.toString().replace(match, replacement);
/*      */         }
/*      */         
/*      */         public CharMatcher and(CharMatcher other) {
/*  404 */           return other.matches(match) ? this : NONE;
/*      */         }
/*      */         
/*      */         public CharMatcher or(CharMatcher other) {
/*  408 */           return other.matches(match) ? other : super.or(other);
/*      */         }
/*      */         
/*      */         public CharMatcher negate() {
/*  412 */           return isNot(match);
/*      */         }
/*      */         
/*      */         void setBits(CharMatcher.LookupTable table) {
/*  416 */           table.set(match);
/*      */         }
/*      */         
/*      */         public CharMatcher precomputed() {
/*  420 */           return this;
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
/*  431 */     return new CharMatcher() {
/*      */         public boolean matches(char c) {
/*  433 */           return (c != match);
/*      */         }
/*      */         
/*      */         public CharMatcher and(CharMatcher other) {
/*  437 */           return other.matches(match) ? super.and(other) : other;
/*      */         }
/*      */         
/*      */         public CharMatcher or(CharMatcher other) {
/*  441 */           return other.matches(match) ? ANY : this;
/*      */         }
/*      */         
/*      */         public CharMatcher negate() {
/*  445 */           return is(match);
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CharMatcher anyOf(CharSequence sequence) {
/*      */     final char match1, match2;
/*  455 */     switch (sequence.length()) {
/*      */       case 0:
/*  457 */         return NONE;
/*      */       case 1:
/*  459 */         return is(sequence.charAt(0));
/*      */       case 2:
/*  461 */         match1 = sequence.charAt(0);
/*  462 */         match2 = sequence.charAt(1);
/*  463 */         return new CharMatcher() {
/*      */             public boolean matches(char c) {
/*  465 */               return (c == match1 || c == match2);
/*      */             }
/*      */             
/*      */             void setBits(CharMatcher.LookupTable table) {
/*  469 */               table.set(match1);
/*  470 */               table.set(match2);
/*      */             }
/*      */             
/*      */             public CharMatcher precomputed() {
/*  474 */               return this;
/*      */             }
/*      */           };
/*      */     } 
/*      */     
/*  479 */     final char[] chars = sequence.toString().toCharArray();
/*  480 */     Arrays.sort(chars);
/*      */     
/*  482 */     return new CharMatcher() {
/*      */         public boolean matches(char c) {
/*  484 */           return (Arrays.binarySearch(chars, c) >= 0);
/*      */         }
/*      */         
/*      */         void setBits(CharMatcher.LookupTable table) {
/*  488 */           for (char c : chars) {
/*  489 */             table.set(c);
/*      */           }
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CharMatcher noneOf(CharSequence sequence) {
/*  500 */     return anyOf(sequence).negate();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CharMatcher inRange(final char startInclusive, final char endInclusive) {
/*  511 */     Preconditions.checkArgument((endInclusive >= startInclusive));
/*  512 */     return new CharMatcher() {
/*      */         public boolean matches(char c) {
/*  514 */           return (startInclusive <= c && c <= endInclusive);
/*      */         }
/*      */         
/*      */         void setBits(CharMatcher.LookupTable table) {
/*  518 */           char c = startInclusive;
/*      */           
/*  520 */           do { table.set(c);
/*  521 */             c = (char)(c + 1); } while (c != endInclusive);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*      */         public CharMatcher precomputed() {
/*  528 */           return this;
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static CharMatcher forPredicate(final Predicate<? super Character> predicate) {
/*  538 */     Preconditions.checkNotNull(predicate);
/*  539 */     if (predicate instanceof CharMatcher) {
/*  540 */       return (CharMatcher)predicate;
/*      */     }
/*  542 */     return new CharMatcher() {
/*      */         public boolean matches(char c) {
/*  544 */           return predicate.apply(Character.valueOf(c));
/*      */         }
/*      */         
/*      */         public boolean apply(Character character) {
/*  548 */           return predicate.apply(Preconditions.checkNotNull(character));
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
/*      */ 
/*      */   
/*      */   public CharMatcher negate() {
/*  564 */     final CharMatcher original = this;
/*  565 */     return new CharMatcher() {
/*      */         public boolean matches(char c) {
/*  567 */           return !original.matches(c);
/*      */         }
/*      */         
/*      */         public boolean matchesAllOf(CharSequence sequence) {
/*  571 */           return original.matchesNoneOf(sequence);
/*      */         }
/*      */         
/*      */         public boolean matchesNoneOf(CharSequence sequence) {
/*  575 */           return original.matchesAllOf(sequence);
/*      */         }
/*      */         
/*      */         public int countIn(CharSequence sequence) {
/*  579 */           return sequence.length() - original.countIn(sequence);
/*      */         }
/*      */         
/*      */         public CharMatcher negate() {
/*  583 */           return original;
/*      */         }
/*      */       };
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CharMatcher and(CharMatcher other) {
/*  592 */     return new And(Arrays.asList(new CharMatcher[] { this, Preconditions.<CharMatcher>checkNotNull(other) }));
/*      */   }
/*      */   
/*      */   private static class And extends CharMatcher {
/*      */     List<CharMatcher> components;
/*      */     
/*      */     And(List<CharMatcher> components) {
/*  599 */       this.components = components;
/*      */     }
/*      */     
/*      */     public boolean matches(char c) {
/*  603 */       for (CharMatcher matcher : this.components) {
/*  604 */         if (!matcher.matches(c)) {
/*  605 */           return false;
/*      */         }
/*      */       } 
/*  608 */       return true;
/*      */     }
/*      */     
/*      */     public CharMatcher and(CharMatcher other) {
/*  612 */       List<CharMatcher> newComponents = new ArrayList<CharMatcher>(this.components);
/*  613 */       newComponents.add(Preconditions.checkNotNull(other));
/*  614 */       return new And(newComponents);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CharMatcher or(CharMatcher other) {
/*  622 */     return new Or(Arrays.asList(new CharMatcher[] { this, Preconditions.<CharMatcher>checkNotNull(other) }));
/*      */   }
/*      */   
/*      */   private static class Or extends CharMatcher {
/*      */     List<CharMatcher> components;
/*      */     
/*      */     Or(List<CharMatcher> components) {
/*  629 */       this.components = components;
/*      */     }
/*      */     
/*      */     public boolean matches(char c) {
/*  633 */       for (CharMatcher matcher : this.components) {
/*  634 */         if (matcher.matches(c)) {
/*  635 */           return true;
/*      */         }
/*      */       } 
/*  638 */       return false;
/*      */     }
/*      */     
/*      */     public CharMatcher or(CharMatcher other) {
/*  642 */       List<CharMatcher> newComponents = new ArrayList<CharMatcher>(this.components);
/*  643 */       newComponents.add(Preconditions.checkNotNull(other));
/*  644 */       return new Or(newComponents);
/*      */     }
/*      */     
/*      */     void setBits(CharMatcher.LookupTable table) {
/*  648 */       for (CharMatcher matcher : this.components) {
/*  649 */         matcher.setBits(table);
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
/*      */   public CharMatcher precomputed() {
/*  664 */     return Platform.precomputeCharMatcher(this);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   CharMatcher precomputedInternal() {
/*  679 */     final LookupTable table = new LookupTable();
/*  680 */     setBits(table);
/*      */     
/*  682 */     return new CharMatcher() {
/*      */         public boolean matches(char c) {
/*  684 */           return table.get(c);
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*      */         public CharMatcher precomputed() {
/*  690 */           return this;
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
/*      */   void setBits(LookupTable table) {
/*  703 */     char c = Character.MIN_VALUE;
/*      */     
/*  705 */     do { if (!matches(c))
/*  706 */         continue;  table.set(c);
/*      */       
/*  708 */       c = (char)(c + 1); } while (c != Character.MAX_VALUE);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class LookupTable
/*      */   {
/*  721 */     int[] data = new int[2048];
/*      */     
/*      */     void set(char index) {
/*  724 */       this.data[index >> 5] = this.data[index >> 5] | 1 << index;
/*      */     }
/*      */     
/*      */     boolean get(char index) {
/*  728 */       return ((this.data[index >> 5] & 1 << index) != 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private LookupTable() {}
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean matchesAnyOf(CharSequence sequence) {
/*  746 */     return !matchesNoneOf(sequence);
/*      */   }
/*      */ 
/*      */ 
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
/*  760 */     for (int i = sequence.length() - 1; i >= 0; i--) {
/*  761 */       if (!matches(sequence.charAt(i))) {
/*  762 */         return false;
/*      */       }
/*      */     } 
/*  765 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
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
/*  780 */     return (indexIn(sequence) == -1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  796 */     int length = sequence.length();
/*  797 */     for (int i = 0; i < length; i++) {
/*  798 */       if (matches(sequence.charAt(i))) {
/*  799 */         return i;
/*      */       }
/*      */     } 
/*  802 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  821 */     int length = sequence.length();
/*  822 */     Preconditions.checkPositionIndex(start, length);
/*  823 */     for (int i = start; i < length; i++) {
/*  824 */       if (matches(sequence.charAt(i))) {
/*  825 */         return i;
/*      */       }
/*      */     } 
/*  828 */     return -1;
/*      */   }
/*      */ 
/*      */ 
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
/*  842 */     for (int i = sequence.length() - 1; i >= 0; i--) {
/*  843 */       if (matches(sequence.charAt(i))) {
/*  844 */         return i;
/*      */       }
/*      */     } 
/*  847 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int countIn(CharSequence sequence) {
/*  854 */     int count = 0;
/*  855 */     for (int i = 0; i < sequence.length(); i++) {
/*  856 */       if (matches(sequence.charAt(i))) {
/*  857 */         count++;
/*      */       }
/*      */     } 
/*  860 */     return count;
/*      */   }
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
/*  873 */     String string = sequence.toString();
/*  874 */     int pos = indexIn(string);
/*  875 */     if (pos == -1) {
/*  876 */       return string;
/*      */     }
/*      */     
/*  879 */     char[] chars = string.toCharArray();
/*  880 */     int spread = 1;
/*      */ 
/*      */     
/*      */     while (true) {
/*  884 */       pos++;
/*      */       
/*  886 */       while (pos != chars.length) {
/*      */ 
/*      */         
/*  889 */         if (matches(chars[pos]))
/*      */         
/*      */         { 
/*      */ 
/*      */ 
/*      */           
/*  895 */           spread++; continue; }  chars[pos - spread] = chars[pos]; pos++;
/*      */       }  break;
/*  897 */     }  return new String(chars, 0, pos - spread);
/*      */   }
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
/*  910 */     return negate().removeFrom(sequence);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  932 */     String string = sequence.toString();
/*  933 */     int pos = indexIn(string);
/*  934 */     if (pos == -1) {
/*  935 */       return string;
/*      */     }
/*  937 */     char[] chars = string.toCharArray();
/*  938 */     chars[pos] = replacement;
/*  939 */     for (int i = pos + 1; i < chars.length; i++) {
/*  940 */       if (matches(chars[i])) {
/*  941 */         chars[i] = replacement;
/*      */       }
/*      */     } 
/*  944 */     return new String(chars);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/*  965 */     int replacementLen = replacement.length();
/*  966 */     if (replacementLen == 0) {
/*  967 */       return removeFrom(sequence);
/*      */     }
/*  969 */     if (replacementLen == 1) {
/*  970 */       return replaceFrom(sequence, replacement.charAt(0));
/*      */     }
/*      */     
/*  973 */     String string = sequence.toString();
/*  974 */     int pos = indexIn(string);
/*  975 */     if (pos == -1) {
/*  976 */       return string;
/*      */     }
/*      */     
/*  979 */     int len = string.length();
/*  980 */     StringBuilder buf = new StringBuilder(len * 3 / 2 + 16);
/*      */     
/*  982 */     int oldpos = 0;
/*      */     do {
/*  984 */       buf.append(string, oldpos, pos);
/*  985 */       buf.append(replacement);
/*  986 */       oldpos = pos + 1;
/*  987 */       pos = indexIn(string, oldpos);
/*  988 */     } while (pos != -1);
/*      */     
/*  990 */     buf.append(string, oldpos, len);
/*  991 */     return buf.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 1010 */     int len = sequence.length();
/*      */     
/*      */     int first;
/*      */     
/* 1014 */     for (first = 0; first < len && 
/* 1015 */       matches(sequence.charAt(first)); first++);
/*      */     
/*      */     int last;
/*      */     
/* 1019 */     for (last = len - 1; last > first && 
/* 1020 */       matches(sequence.charAt(last)); last--);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1025 */     return sequence.subSequence(first, last + 1).toString();
/*      */   }
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
/* 1038 */     int len = sequence.length();
/*      */     
/*      */     int first;
/* 1041 */     for (first = 0; first < len && 
/* 1042 */       matches(sequence.charAt(first)); first++);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1047 */     return sequence.subSequence(first, len).toString();
/*      */   }
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
/* 1060 */     int len = sequence.length();
/*      */     
/*      */     int last;
/* 1063 */     for (last = len - 1; last >= 0 && 
/* 1064 */       matches(sequence.charAt(last)); last--);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1069 */     return sequence.subSequence(0, last + 1).toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
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
/* 1092 */     int first = indexIn(sequence);
/* 1093 */     if (first == -1) {
/* 1094 */       return sequence.toString();
/*      */     }
/*      */ 
/*      */     
/* 1098 */     StringBuilder builder = (new StringBuilder(sequence.length())).append(sequence.subSequence(0, first)).append(replacement);
/*      */ 
/*      */     
/* 1101 */     boolean in = true;
/* 1102 */     for (int i = first + 1; i < sequence.length(); i++) {
/* 1103 */       char c = sequence.charAt(i);
/* 1104 */       if (apply(Character.valueOf(c))) {
/* 1105 */         if (!in) {
/* 1106 */           builder.append(replacement);
/* 1107 */           in = true;
/*      */         } 
/*      */       } else {
/* 1110 */         builder.append(c);
/* 1111 */         in = false;
/*      */       } 
/*      */     } 
/* 1114 */     return builder.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   @CheckReturnValue
/*      */   public String trimAndCollapseFrom(CharSequence sequence, char replacement) {
/* 1124 */     int first = negate().indexIn(sequence);
/* 1125 */     if (first == -1) {
/* 1126 */       return "";
/*      */     }
/* 1128 */     StringBuilder builder = new StringBuilder(sequence.length());
/* 1129 */     boolean inMatchingGroup = false;
/* 1130 */     for (int i = first; i < sequence.length(); i++) {
/* 1131 */       char c = sequence.charAt(i);
/* 1132 */       if (apply(Character.valueOf(c))) {
/* 1133 */         inMatchingGroup = true;
/*      */       } else {
/* 1135 */         if (inMatchingGroup) {
/* 1136 */           builder.append(replacement);
/* 1137 */           inMatchingGroup = false;
/*      */         } 
/* 1139 */         builder.append(c);
/*      */       } 
/*      */     } 
/* 1142 */     return builder.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean apply(Character character) {
/* 1153 */     return matches(character.charValue());
/*      */   }
/*      */   
/*      */   public abstract boolean matches(char paramChar);
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\google\common\base\CharMatcher.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */