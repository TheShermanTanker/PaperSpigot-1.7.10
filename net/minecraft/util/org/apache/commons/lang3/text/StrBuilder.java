/*      */ package net.minecraft.util.org.apache.commons.lang3.text;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.Reader;
/*      */ import java.io.Serializable;
/*      */ import java.io.Writer;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import net.minecraft.util.org.apache.commons.lang3.ArrayUtils;
/*      */ import net.minecraft.util.org.apache.commons.lang3.ObjectUtils;
/*      */ import net.minecraft.util.org.apache.commons.lang3.SystemUtils;
/*      */ import net.minecraft.util.org.apache.commons.lang3.builder.Builder;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class StrBuilder
/*      */   implements CharSequence, Appendable, Serializable, Builder<String>
/*      */ {
/*      */   static final int CAPACITY = 32;
/*      */   private static final long serialVersionUID = 7628716375283629643L;
/*      */   protected char[] buffer;
/*      */   protected int size;
/*      */   private String newLine;
/*      */   private String nullText;
/*      */   
/*      */   public StrBuilder() {
/*  103 */     this(32);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder(int initialCapacity) {
/*  113 */     if (initialCapacity <= 0) {
/*  114 */       initialCapacity = 32;
/*      */     }
/*  116 */     this.buffer = new char[initialCapacity];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder(String str) {
/*  127 */     if (str == null) {
/*  128 */       this.buffer = new char[32];
/*      */     } else {
/*  130 */       this.buffer = new char[str.length() + 32];
/*  131 */       append(str);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getNewLineText() {
/*  142 */     return this.newLine;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder setNewLineText(String newLine) {
/*  152 */     this.newLine = newLine;
/*  153 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getNullText() {
/*  163 */     return this.nullText;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder setNullText(String nullText) {
/*  173 */     if (nullText != null && nullText.isEmpty()) {
/*  174 */       nullText = null;
/*      */     }
/*  176 */     this.nullText = nullText;
/*  177 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int length() {
/*  188 */     return this.size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder setLength(int length) {
/*  200 */     if (length < 0) {
/*  201 */       throw new StringIndexOutOfBoundsException(length);
/*      */     }
/*  203 */     if (length < this.size) {
/*  204 */       this.size = length;
/*  205 */     } else if (length > this.size) {
/*  206 */       ensureCapacity(length);
/*  207 */       int oldEnd = this.size;
/*  208 */       int newEnd = length;
/*  209 */       this.size = length;
/*  210 */       for (int i = oldEnd; i < newEnd; i++) {
/*  211 */         this.buffer[i] = Character.MIN_VALUE;
/*      */       }
/*      */     } 
/*  214 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int capacity() {
/*  224 */     return this.buffer.length;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder ensureCapacity(int capacity) {
/*  234 */     if (capacity > this.buffer.length) {
/*  235 */       char[] old = this.buffer;
/*  236 */       this.buffer = new char[capacity * 2];
/*  237 */       System.arraycopy(old, 0, this.buffer, 0, this.size);
/*      */     } 
/*  239 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder minimizeCapacity() {
/*  248 */     if (this.buffer.length > length()) {
/*  249 */       char[] old = this.buffer;
/*  250 */       this.buffer = new char[length()];
/*  251 */       System.arraycopy(old, 0, this.buffer, 0, this.size);
/*      */     } 
/*  253 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int size() {
/*  266 */     return this.size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isEmpty() {
/*  278 */     return (this.size == 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder clear() {
/*  293 */     this.size = 0;
/*  294 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public char charAt(int index) {
/*  309 */     if (index < 0 || index >= length()) {
/*  310 */       throw new StringIndexOutOfBoundsException(index);
/*      */     }
/*  312 */     return this.buffer[index];
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder setCharAt(int index, char ch) {
/*  326 */     if (index < 0 || index >= length()) {
/*  327 */       throw new StringIndexOutOfBoundsException(index);
/*      */     }
/*  329 */     this.buffer[index] = ch;
/*  330 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder deleteCharAt(int index) {
/*  343 */     if (index < 0 || index >= this.size) {
/*  344 */       throw new StringIndexOutOfBoundsException(index);
/*      */     }
/*  346 */     deleteImpl(index, index + 1, 1);
/*  347 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public char[] toCharArray() {
/*  357 */     if (this.size == 0) {
/*  358 */       return ArrayUtils.EMPTY_CHAR_ARRAY;
/*      */     }
/*  360 */     char[] chars = new char[this.size];
/*  361 */     System.arraycopy(this.buffer, 0, chars, 0, this.size);
/*  362 */     return chars;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public char[] toCharArray(int startIndex, int endIndex) {
/*  376 */     endIndex = validateRange(startIndex, endIndex);
/*  377 */     int len = endIndex - startIndex;
/*  378 */     if (len == 0) {
/*  379 */       return ArrayUtils.EMPTY_CHAR_ARRAY;
/*      */     }
/*  381 */     char[] chars = new char[len];
/*  382 */     System.arraycopy(this.buffer, startIndex, chars, 0, len);
/*  383 */     return chars;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public char[] getChars(char[] destination) {
/*  393 */     int len = length();
/*  394 */     if (destination == null || destination.length < len) {
/*  395 */       destination = new char[len];
/*      */     }
/*  397 */     System.arraycopy(this.buffer, 0, destination, 0, len);
/*  398 */     return destination;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void getChars(int startIndex, int endIndex, char[] destination, int destinationIndex) {
/*  412 */     if (startIndex < 0) {
/*  413 */       throw new StringIndexOutOfBoundsException(startIndex);
/*      */     }
/*  415 */     if (endIndex < 0 || endIndex > length()) {
/*  416 */       throw new StringIndexOutOfBoundsException(endIndex);
/*      */     }
/*  418 */     if (startIndex > endIndex) {
/*  419 */       throw new StringIndexOutOfBoundsException("end < start");
/*      */     }
/*  421 */     System.arraycopy(this.buffer, startIndex, destination, destinationIndex, endIndex - startIndex);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendNewLine() {
/*  435 */     if (this.newLine == null) {
/*  436 */       append(SystemUtils.LINE_SEPARATOR);
/*  437 */       return this;
/*      */     } 
/*  439 */     return append(this.newLine);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendNull() {
/*  448 */     if (this.nullText == null) {
/*  449 */       return this;
/*      */     }
/*  451 */     return append(this.nullText);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder append(Object obj) {
/*  462 */     if (obj == null) {
/*  463 */       return appendNull();
/*      */     }
/*  465 */     return append(obj.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder append(CharSequence seq) {
/*  478 */     if (seq == null) {
/*  479 */       return appendNull();
/*      */     }
/*  481 */     return append(seq.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder append(CharSequence seq, int startIndex, int length) {
/*  496 */     if (seq == null) {
/*  497 */       return appendNull();
/*      */     }
/*  499 */     return append(seq.toString(), startIndex, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder append(String str) {
/*  510 */     if (str == null) {
/*  511 */       return appendNull();
/*      */     }
/*  513 */     int strLen = str.length();
/*  514 */     if (strLen > 0) {
/*  515 */       int len = length();
/*  516 */       ensureCapacity(len + strLen);
/*  517 */       str.getChars(0, strLen, this.buffer, len);
/*  518 */       this.size += strLen;
/*      */     } 
/*  520 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder append(String str, int startIndex, int length) {
/*  534 */     if (str == null) {
/*  535 */       return appendNull();
/*      */     }
/*  537 */     if (startIndex < 0 || startIndex > str.length()) {
/*  538 */       throw new StringIndexOutOfBoundsException("startIndex must be valid");
/*      */     }
/*  540 */     if (length < 0 || startIndex + length > str.length()) {
/*  541 */       throw new StringIndexOutOfBoundsException("length must be valid");
/*      */     }
/*  543 */     if (length > 0) {
/*  544 */       int len = length();
/*  545 */       ensureCapacity(len + length);
/*  546 */       str.getChars(startIndex, startIndex + length, this.buffer, len);
/*  547 */       this.size += length;
/*      */     } 
/*  549 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder append(String format, Object... objs) {
/*  562 */     return append(String.format(format, objs));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder append(StringBuffer str) {
/*  573 */     if (str == null) {
/*  574 */       return appendNull();
/*      */     }
/*  576 */     int strLen = str.length();
/*  577 */     if (strLen > 0) {
/*  578 */       int len = length();
/*  579 */       ensureCapacity(len + strLen);
/*  580 */       str.getChars(0, strLen, this.buffer, len);
/*  581 */       this.size += strLen;
/*      */     } 
/*  583 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder append(StringBuffer str, int startIndex, int length) {
/*  596 */     if (str == null) {
/*  597 */       return appendNull();
/*      */     }
/*  599 */     if (startIndex < 0 || startIndex > str.length()) {
/*  600 */       throw new StringIndexOutOfBoundsException("startIndex must be valid");
/*      */     }
/*  602 */     if (length < 0 || startIndex + length > str.length()) {
/*  603 */       throw new StringIndexOutOfBoundsException("length must be valid");
/*      */     }
/*  605 */     if (length > 0) {
/*  606 */       int len = length();
/*  607 */       ensureCapacity(len + length);
/*  608 */       str.getChars(startIndex, startIndex + length, this.buffer, len);
/*  609 */       this.size += length;
/*      */     } 
/*  611 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder append(StringBuilder str) {
/*  623 */     if (str == null) {
/*  624 */       return appendNull();
/*      */     }
/*  626 */     int strLen = str.length();
/*  627 */     if (strLen > 0) {
/*  628 */       int len = length();
/*  629 */       ensureCapacity(len + strLen);
/*  630 */       str.getChars(0, strLen, this.buffer, len);
/*  631 */       this.size += strLen;
/*      */     } 
/*  633 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder append(StringBuilder str, int startIndex, int length) {
/*  647 */     if (str == null) {
/*  648 */       return appendNull();
/*      */     }
/*  650 */     if (startIndex < 0 || startIndex > str.length()) {
/*  651 */       throw new StringIndexOutOfBoundsException("startIndex must be valid");
/*      */     }
/*  653 */     if (length < 0 || startIndex + length > str.length()) {
/*  654 */       throw new StringIndexOutOfBoundsException("length must be valid");
/*      */     }
/*  656 */     if (length > 0) {
/*  657 */       int len = length();
/*  658 */       ensureCapacity(len + length);
/*  659 */       str.getChars(startIndex, startIndex + length, this.buffer, len);
/*  660 */       this.size += length;
/*      */     } 
/*  662 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder append(StrBuilder str) {
/*  673 */     if (str == null) {
/*  674 */       return appendNull();
/*      */     }
/*  676 */     int strLen = str.length();
/*  677 */     if (strLen > 0) {
/*  678 */       int len = length();
/*  679 */       ensureCapacity(len + strLen);
/*  680 */       System.arraycopy(str.buffer, 0, this.buffer, len, strLen);
/*  681 */       this.size += strLen;
/*      */     } 
/*  683 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder append(StrBuilder str, int startIndex, int length) {
/*  696 */     if (str == null) {
/*  697 */       return appendNull();
/*      */     }
/*  699 */     if (startIndex < 0 || startIndex > str.length()) {
/*  700 */       throw new StringIndexOutOfBoundsException("startIndex must be valid");
/*      */     }
/*  702 */     if (length < 0 || startIndex + length > str.length()) {
/*  703 */       throw new StringIndexOutOfBoundsException("length must be valid");
/*      */     }
/*  705 */     if (length > 0) {
/*  706 */       int len = length();
/*  707 */       ensureCapacity(len + length);
/*  708 */       str.getChars(startIndex, startIndex + length, this.buffer, len);
/*  709 */       this.size += length;
/*      */     } 
/*  711 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder append(char[] chars) {
/*  722 */     if (chars == null) {
/*  723 */       return appendNull();
/*      */     }
/*  725 */     int strLen = chars.length;
/*  726 */     if (strLen > 0) {
/*  727 */       int len = length();
/*  728 */       ensureCapacity(len + strLen);
/*  729 */       System.arraycopy(chars, 0, this.buffer, len, strLen);
/*  730 */       this.size += strLen;
/*      */     } 
/*  732 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder append(char[] chars, int startIndex, int length) {
/*  745 */     if (chars == null) {
/*  746 */       return appendNull();
/*      */     }
/*  748 */     if (startIndex < 0 || startIndex > chars.length) {
/*  749 */       throw new StringIndexOutOfBoundsException("Invalid startIndex: " + length);
/*      */     }
/*  751 */     if (length < 0 || startIndex + length > chars.length) {
/*  752 */       throw new StringIndexOutOfBoundsException("Invalid length: " + length);
/*      */     }
/*  754 */     if (length > 0) {
/*  755 */       int len = length();
/*  756 */       ensureCapacity(len + length);
/*  757 */       System.arraycopy(chars, startIndex, this.buffer, len, length);
/*  758 */       this.size += length;
/*      */     } 
/*  760 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder append(boolean value) {
/*  770 */     if (value) {
/*  771 */       ensureCapacity(this.size + 4);
/*  772 */       this.buffer[this.size++] = 't';
/*  773 */       this.buffer[this.size++] = 'r';
/*  774 */       this.buffer[this.size++] = 'u';
/*  775 */       this.buffer[this.size++] = 'e';
/*      */     } else {
/*  777 */       ensureCapacity(this.size + 5);
/*  778 */       this.buffer[this.size++] = 'f';
/*  779 */       this.buffer[this.size++] = 'a';
/*  780 */       this.buffer[this.size++] = 'l';
/*  781 */       this.buffer[this.size++] = 's';
/*  782 */       this.buffer[this.size++] = 'e';
/*      */     } 
/*  784 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder append(char ch) {
/*  796 */     int len = length();
/*  797 */     ensureCapacity(len + 1);
/*  798 */     this.buffer[this.size++] = ch;
/*  799 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder append(int value) {
/*  809 */     return append(String.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder append(long value) {
/*  819 */     return append(String.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder append(float value) {
/*  829 */     return append(String.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder append(double value) {
/*  839 */     return append(String.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendln(Object obj) {
/*  852 */     return append(obj).appendNewLine();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendln(String str) {
/*  864 */     return append(str).appendNewLine();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendln(String str, int startIndex, int length) {
/*  878 */     return append(str, startIndex, length).appendNewLine();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendln(String format, Object... objs) {
/*  891 */     return append(format, objs).appendNewLine();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendln(StringBuffer str) {
/*  903 */     return append(str).appendNewLine();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendln(StringBuilder str) {
/*  915 */     return append(str).appendNewLine();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendln(StringBuilder str, int startIndex, int length) {
/*  929 */     return append(str, startIndex, length).appendNewLine();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendln(StringBuffer str, int startIndex, int length) {
/*  943 */     return append(str, startIndex, length).appendNewLine();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendln(StrBuilder str) {
/*  955 */     return append(str).appendNewLine();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendln(StrBuilder str, int startIndex, int length) {
/*  969 */     return append(str, startIndex, length).appendNewLine();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendln(char[] chars) {
/*  981 */     return append(chars).appendNewLine();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendln(char[] chars, int startIndex, int length) {
/*  995 */     return append(chars, startIndex, length).appendNewLine();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendln(boolean value) {
/* 1006 */     return append(value).appendNewLine();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendln(char ch) {
/* 1017 */     return append(ch).appendNewLine();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendln(int value) {
/* 1028 */     return append(value).appendNewLine();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendln(long value) {
/* 1039 */     return append(value).appendNewLine();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendln(float value) {
/* 1050 */     return append(value).appendNewLine();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendln(double value) {
/* 1061 */     return append(value).appendNewLine();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public <T> StrBuilder appendAll(T... array) {
/* 1076 */     if (array != null && array.length > 0) {
/* 1077 */       for (T element : array) {
/* 1078 */         append(element);
/*      */       }
/*      */     }
/* 1081 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendAll(Iterable<?> iterable) {
/* 1094 */     if (iterable != null) {
/* 1095 */       for (Object o : iterable) {
/* 1096 */         append(o);
/*      */       }
/*      */     }
/* 1099 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendAll(Iterator<?> it) {
/* 1112 */     if (it != null) {
/* 1113 */       while (it.hasNext()) {
/* 1114 */         append(it.next());
/*      */       }
/*      */     }
/* 1117 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendWithSeparators(Object[] array, String separator) {
/* 1132 */     if (array != null && array.length > 0) {
/* 1133 */       separator = ObjectUtils.toString(separator);
/* 1134 */       append(array[0]);
/* 1135 */       for (int i = 1; i < array.length; i++) {
/* 1136 */         append(separator);
/* 1137 */         append(array[i]);
/*      */       } 
/*      */     } 
/* 1140 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendWithSeparators(Iterable<?> iterable, String separator) {
/* 1154 */     if (iterable != null) {
/* 1155 */       separator = ObjectUtils.toString(separator);
/* 1156 */       Iterator<?> it = iterable.iterator();
/* 1157 */       while (it.hasNext()) {
/* 1158 */         append(it.next());
/* 1159 */         if (it.hasNext()) {
/* 1160 */           append(separator);
/*      */         }
/*      */       } 
/*      */     } 
/* 1164 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendWithSeparators(Iterator<?> it, String separator) {
/* 1178 */     if (it != null) {
/* 1179 */       separator = ObjectUtils.toString(separator);
/* 1180 */       while (it.hasNext()) {
/* 1181 */         append(it.next());
/* 1182 */         if (it.hasNext()) {
/* 1183 */           append(separator);
/*      */         }
/*      */       } 
/*      */     } 
/* 1187 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendSeparator(String separator) {
/* 1212 */     return appendSeparator(separator, (String)null);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendSeparator(String standard, String defaultIfEmpty) {
/* 1243 */     String str = isEmpty() ? defaultIfEmpty : standard;
/* 1244 */     if (str != null) {
/* 1245 */       append(str);
/*      */     }
/* 1247 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendSeparator(char separator) {
/* 1270 */     if (size() > 0) {
/* 1271 */       append(separator);
/*      */     }
/* 1273 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendSeparator(char standard, char defaultIfEmpty) {
/* 1288 */     if (size() > 0) {
/* 1289 */       append(standard);
/*      */     } else {
/* 1291 */       append(defaultIfEmpty);
/*      */     } 
/* 1293 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendSeparator(String separator, int loopIndex) {
/* 1317 */     if (separator != null && loopIndex > 0) {
/* 1318 */       append(separator);
/*      */     }
/* 1320 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendSeparator(char separator, int loopIndex) {
/* 1344 */     if (loopIndex > 0) {
/* 1345 */       append(separator);
/*      */     }
/* 1347 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendPadding(int length, char padChar) {
/* 1359 */     if (length >= 0) {
/* 1360 */       ensureCapacity(this.size + length);
/* 1361 */       for (int i = 0; i < length; i++) {
/* 1362 */         this.buffer[this.size++] = padChar;
/*      */       }
/*      */     } 
/* 1365 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendFixedWidthPadLeft(Object obj, int width, char padChar) {
/* 1381 */     if (width > 0) {
/* 1382 */       ensureCapacity(this.size + width);
/* 1383 */       String str = (obj == null) ? getNullText() : obj.toString();
/* 1384 */       if (str == null) {
/* 1385 */         str = "";
/*      */       }
/* 1387 */       int strLen = str.length();
/* 1388 */       if (strLen >= width) {
/* 1389 */         str.getChars(strLen - width, strLen, this.buffer, this.size);
/*      */       } else {
/* 1391 */         int padLen = width - strLen;
/* 1392 */         for (int i = 0; i < padLen; i++) {
/* 1393 */           this.buffer[this.size + i] = padChar;
/*      */         }
/* 1395 */         str.getChars(0, strLen, this.buffer, this.size + padLen);
/*      */       } 
/* 1397 */       this.size += width;
/*      */     } 
/* 1399 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendFixedWidthPadLeft(int value, int width, char padChar) {
/* 1413 */     return appendFixedWidthPadLeft(String.valueOf(value), width, padChar);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendFixedWidthPadRight(Object obj, int width, char padChar) {
/* 1428 */     if (width > 0) {
/* 1429 */       ensureCapacity(this.size + width);
/* 1430 */       String str = (obj == null) ? getNullText() : obj.toString();
/* 1431 */       if (str == null) {
/* 1432 */         str = "";
/*      */       }
/* 1434 */       int strLen = str.length();
/* 1435 */       if (strLen >= width) {
/* 1436 */         str.getChars(0, width, this.buffer, this.size);
/*      */       } else {
/* 1438 */         int padLen = width - strLen;
/* 1439 */         str.getChars(0, strLen, this.buffer, this.size);
/* 1440 */         for (int i = 0; i < padLen; i++) {
/* 1441 */           this.buffer[this.size + strLen + i] = padChar;
/*      */         }
/*      */       } 
/* 1444 */       this.size += width;
/*      */     } 
/* 1446 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder appendFixedWidthPadRight(int value, int width, char padChar) {
/* 1460 */     return appendFixedWidthPadRight(String.valueOf(value), width, padChar);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder insert(int index, Object obj) {
/* 1474 */     if (obj == null) {
/* 1475 */       return insert(index, this.nullText);
/*      */     }
/* 1477 */     return insert(index, obj.toString());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder insert(int index, String str) {
/* 1490 */     validateIndex(index);
/* 1491 */     if (str == null) {
/* 1492 */       str = this.nullText;
/*      */     }
/* 1494 */     if (str != null) {
/* 1495 */       int strLen = str.length();
/* 1496 */       if (strLen > 0) {
/* 1497 */         int newSize = this.size + strLen;
/* 1498 */         ensureCapacity(newSize);
/* 1499 */         System.arraycopy(this.buffer, index, this.buffer, index + strLen, this.size - index);
/* 1500 */         this.size = newSize;
/* 1501 */         str.getChars(0, strLen, this.buffer, index);
/*      */       } 
/*      */     } 
/* 1504 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder insert(int index, char[] chars) {
/* 1517 */     validateIndex(index);
/* 1518 */     if (chars == null) {
/* 1519 */       return insert(index, this.nullText);
/*      */     }
/* 1521 */     int len = chars.length;
/* 1522 */     if (len > 0) {
/* 1523 */       ensureCapacity(this.size + len);
/* 1524 */       System.arraycopy(this.buffer, index, this.buffer, index + len, this.size - index);
/* 1525 */       System.arraycopy(chars, 0, this.buffer, index, len);
/* 1526 */       this.size += len;
/*      */     } 
/* 1528 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder insert(int index, char[] chars, int offset, int length) {
/* 1543 */     validateIndex(index);
/* 1544 */     if (chars == null) {
/* 1545 */       return insert(index, this.nullText);
/*      */     }
/* 1547 */     if (offset < 0 || offset > chars.length) {
/* 1548 */       throw new StringIndexOutOfBoundsException("Invalid offset: " + offset);
/*      */     }
/* 1550 */     if (length < 0 || offset + length > chars.length) {
/* 1551 */       throw new StringIndexOutOfBoundsException("Invalid length: " + length);
/*      */     }
/* 1553 */     if (length > 0) {
/* 1554 */       ensureCapacity(this.size + length);
/* 1555 */       System.arraycopy(this.buffer, index, this.buffer, index + length, this.size - index);
/* 1556 */       System.arraycopy(chars, offset, this.buffer, index, length);
/* 1557 */       this.size += length;
/*      */     } 
/* 1559 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder insert(int index, boolean value) {
/* 1571 */     validateIndex(index);
/* 1572 */     if (value) {
/* 1573 */       ensureCapacity(this.size + 4);
/* 1574 */       System.arraycopy(this.buffer, index, this.buffer, index + 4, this.size - index);
/* 1575 */       this.buffer[index++] = 't';
/* 1576 */       this.buffer[index++] = 'r';
/* 1577 */       this.buffer[index++] = 'u';
/* 1578 */       this.buffer[index] = 'e';
/* 1579 */       this.size += 4;
/*      */     } else {
/* 1581 */       ensureCapacity(this.size + 5);
/* 1582 */       System.arraycopy(this.buffer, index, this.buffer, index + 5, this.size - index);
/* 1583 */       this.buffer[index++] = 'f';
/* 1584 */       this.buffer[index++] = 'a';
/* 1585 */       this.buffer[index++] = 'l';
/* 1586 */       this.buffer[index++] = 's';
/* 1587 */       this.buffer[index] = 'e';
/* 1588 */       this.size += 5;
/*      */     } 
/* 1590 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder insert(int index, char value) {
/* 1602 */     validateIndex(index);
/* 1603 */     ensureCapacity(this.size + 1);
/* 1604 */     System.arraycopy(this.buffer, index, this.buffer, index + 1, this.size - index);
/* 1605 */     this.buffer[index] = value;
/* 1606 */     this.size++;
/* 1607 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder insert(int index, int value) {
/* 1619 */     return insert(index, String.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder insert(int index, long value) {
/* 1631 */     return insert(index, String.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder insert(int index, float value) {
/* 1643 */     return insert(index, String.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder insert(int index, double value) {
/* 1655 */     return insert(index, String.valueOf(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void deleteImpl(int startIndex, int endIndex, int len) {
/* 1668 */     System.arraycopy(this.buffer, endIndex, this.buffer, startIndex, this.size - endIndex);
/* 1669 */     this.size -= len;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder delete(int startIndex, int endIndex) {
/* 1682 */     endIndex = validateRange(startIndex, endIndex);
/* 1683 */     int len = endIndex - startIndex;
/* 1684 */     if (len > 0) {
/* 1685 */       deleteImpl(startIndex, endIndex, len);
/*      */     }
/* 1687 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder deleteAll(char ch) {
/* 1698 */     for (int i = 0; i < this.size; i++) {
/* 1699 */       if (this.buffer[i] == ch) {
/* 1700 */         int start = i; do {  }
/* 1701 */         while (++i < this.size && 
/* 1702 */           this.buffer[i] == ch);
/*      */ 
/*      */ 
/*      */         
/* 1706 */         int len = i - start;
/* 1707 */         deleteImpl(start, i, len);
/* 1708 */         i -= len;
/*      */       } 
/*      */     } 
/* 1711 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder deleteFirst(char ch) {
/* 1721 */     for (int i = 0; i < this.size; i++) {
/* 1722 */       if (this.buffer[i] == ch) {
/* 1723 */         deleteImpl(i, i + 1, 1);
/*      */         break;
/*      */       } 
/*      */     } 
/* 1727 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder deleteAll(String str) {
/* 1738 */     int len = (str == null) ? 0 : str.length();
/* 1739 */     if (len > 0) {
/* 1740 */       int index = indexOf(str, 0);
/* 1741 */       while (index >= 0) {
/* 1742 */         deleteImpl(index, index + len, len);
/* 1743 */         index = indexOf(str, index);
/*      */       } 
/*      */     } 
/* 1746 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder deleteFirst(String str) {
/* 1756 */     int len = (str == null) ? 0 : str.length();
/* 1757 */     if (len > 0) {
/* 1758 */       int index = indexOf(str, 0);
/* 1759 */       if (index >= 0) {
/* 1760 */         deleteImpl(index, index + len, len);
/*      */       }
/*      */     } 
/* 1763 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder deleteAll(StrMatcher matcher) {
/* 1778 */     return replace(matcher, null, 0, this.size, -1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder deleteFirst(StrMatcher matcher) {
/* 1792 */     return replace(matcher, null, 0, this.size, 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void replaceImpl(int startIndex, int endIndex, int removeLen, String insertStr, int insertLen) {
/* 1807 */     int newSize = this.size - removeLen + insertLen;
/* 1808 */     if (insertLen != removeLen) {
/* 1809 */       ensureCapacity(newSize);
/* 1810 */       System.arraycopy(this.buffer, endIndex, this.buffer, startIndex + insertLen, this.size - endIndex);
/* 1811 */       this.size = newSize;
/*      */     } 
/* 1813 */     if (insertLen > 0) {
/* 1814 */       insertStr.getChars(0, insertLen, this.buffer, startIndex);
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
/*      */   public StrBuilder replace(int startIndex, int endIndex, String replaceStr) {
/* 1830 */     endIndex = validateRange(startIndex, endIndex);
/* 1831 */     int insertLen = (replaceStr == null) ? 0 : replaceStr.length();
/* 1832 */     replaceImpl(startIndex, endIndex, endIndex - startIndex, replaceStr, insertLen);
/* 1833 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder replaceAll(char search, char replace) {
/* 1846 */     if (search != replace) {
/* 1847 */       for (int i = 0; i < this.size; i++) {
/* 1848 */         if (this.buffer[i] == search) {
/* 1849 */           this.buffer[i] = replace;
/*      */         }
/*      */       } 
/*      */     }
/* 1853 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder replaceFirst(char search, char replace) {
/* 1865 */     if (search != replace) {
/* 1866 */       for (int i = 0; i < this.size; i++) {
/* 1867 */         if (this.buffer[i] == search) {
/* 1868 */           this.buffer[i] = replace;
/*      */           break;
/*      */         } 
/*      */       } 
/*      */     }
/* 1873 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder replaceAll(String searchStr, String replaceStr) {
/* 1885 */     int searchLen = (searchStr == null) ? 0 : searchStr.length();
/* 1886 */     if (searchLen > 0) {
/* 1887 */       int replaceLen = (replaceStr == null) ? 0 : replaceStr.length();
/* 1888 */       int index = indexOf(searchStr, 0);
/* 1889 */       while (index >= 0) {
/* 1890 */         replaceImpl(index, index + searchLen, searchLen, replaceStr, replaceLen);
/* 1891 */         index = indexOf(searchStr, index + replaceLen);
/*      */       } 
/*      */     } 
/* 1894 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder replaceFirst(String searchStr, String replaceStr) {
/* 1905 */     int searchLen = (searchStr == null) ? 0 : searchStr.length();
/* 1906 */     if (searchLen > 0) {
/* 1907 */       int index = indexOf(searchStr, 0);
/* 1908 */       if (index >= 0) {
/* 1909 */         int replaceLen = (replaceStr == null) ? 0 : replaceStr.length();
/* 1910 */         replaceImpl(index, index + searchLen, searchLen, replaceStr, replaceLen);
/*      */       } 
/*      */     } 
/* 1913 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder replaceAll(StrMatcher matcher, String replaceStr) {
/* 1929 */     return replace(matcher, replaceStr, 0, this.size, -1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder replaceFirst(StrMatcher matcher, String replaceStr) {
/* 1944 */     return replace(matcher, replaceStr, 0, this.size, 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder replace(StrMatcher matcher, String replaceStr, int startIndex, int endIndex, int replaceCount) {
/* 1967 */     endIndex = validateRange(startIndex, endIndex);
/* 1968 */     return replaceImpl(matcher, replaceStr, startIndex, endIndex, replaceCount);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private StrBuilder replaceImpl(StrMatcher matcher, String replaceStr, int from, int to, int replaceCount) {
/* 1989 */     if (matcher == null || this.size == 0) {
/* 1990 */       return this;
/*      */     }
/* 1992 */     int replaceLen = (replaceStr == null) ? 0 : replaceStr.length();
/* 1993 */     char[] buf = this.buffer;
/* 1994 */     for (int i = from; i < to && replaceCount != 0; i++) {
/* 1995 */       int removeLen = matcher.isMatch(buf, i, from, to);
/* 1996 */       if (removeLen > 0) {
/* 1997 */         replaceImpl(i, i + removeLen, removeLen, replaceStr, replaceLen);
/* 1998 */         to = to - removeLen + replaceLen;
/* 1999 */         i = i + replaceLen - 1;
/* 2000 */         if (replaceCount > 0) {
/* 2001 */           replaceCount--;
/*      */         }
/*      */       } 
/*      */     } 
/* 2005 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder reverse() {
/* 2015 */     if (this.size == 0) {
/* 2016 */       return this;
/*      */     }
/*      */     
/* 2019 */     int half = this.size / 2;
/* 2020 */     char[] buf = this.buffer;
/* 2021 */     for (int leftIdx = 0, rightIdx = this.size - 1; leftIdx < half; leftIdx++, rightIdx--) {
/* 2022 */       char swap = buf[leftIdx];
/* 2023 */       buf[leftIdx] = buf[rightIdx];
/* 2024 */       buf[rightIdx] = swap;
/*      */     } 
/* 2026 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrBuilder trim() {
/* 2037 */     if (this.size == 0) {
/* 2038 */       return this;
/*      */     }
/* 2040 */     int len = this.size;
/* 2041 */     char[] buf = this.buffer;
/* 2042 */     int pos = 0;
/* 2043 */     while (pos < len && buf[pos] <= ' ') {
/* 2044 */       pos++;
/*      */     }
/* 2046 */     while (pos < len && buf[len - 1] <= ' ') {
/* 2047 */       len--;
/*      */     }
/* 2049 */     if (len < this.size) {
/* 2050 */       delete(len, this.size);
/*      */     }
/* 2052 */     if (pos > 0) {
/* 2053 */       delete(0, pos);
/*      */     }
/* 2055 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean startsWith(String str) {
/* 2068 */     if (str == null) {
/* 2069 */       return false;
/*      */     }
/* 2071 */     int len = str.length();
/* 2072 */     if (len == 0) {
/* 2073 */       return true;
/*      */     }
/* 2075 */     if (len > this.size) {
/* 2076 */       return false;
/*      */     }
/* 2078 */     for (int i = 0; i < len; i++) {
/* 2079 */       if (this.buffer[i] != str.charAt(i)) {
/* 2080 */         return false;
/*      */       }
/*      */     } 
/* 2083 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean endsWith(String str) {
/* 2095 */     if (str == null) {
/* 2096 */       return false;
/*      */     }
/* 2098 */     int len = str.length();
/* 2099 */     if (len == 0) {
/* 2100 */       return true;
/*      */     }
/* 2102 */     if (len > this.size) {
/* 2103 */       return false;
/*      */     }
/* 2105 */     int pos = this.size - len;
/* 2106 */     for (int i = 0; i < len; i++, pos++) {
/* 2107 */       if (this.buffer[pos] != str.charAt(i)) {
/* 2108 */         return false;
/*      */       }
/*      */     } 
/* 2111 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CharSequence subSequence(int startIndex, int endIndex) {
/* 2121 */     if (startIndex < 0) {
/* 2122 */       throw new StringIndexOutOfBoundsException(startIndex);
/*      */     }
/* 2124 */     if (endIndex > this.size) {
/* 2125 */       throw new StringIndexOutOfBoundsException(endIndex);
/*      */     }
/* 2127 */     if (startIndex > endIndex) {
/* 2128 */       throw new StringIndexOutOfBoundsException(endIndex - startIndex);
/*      */     }
/* 2130 */     return substring(startIndex, endIndex);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String substring(int start) {
/* 2141 */     return substring(start, this.size);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String substring(int startIndex, int endIndex) {
/* 2158 */     endIndex = validateRange(startIndex, endIndex);
/* 2159 */     return new String(this.buffer, startIndex, endIndex - startIndex);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String leftString(int length) {
/* 2175 */     if (length <= 0)
/* 2176 */       return ""; 
/* 2177 */     if (length >= this.size) {
/* 2178 */       return new String(this.buffer, 0, this.size);
/*      */     }
/* 2180 */     return new String(this.buffer, 0, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String rightString(int length) {
/* 2197 */     if (length <= 0)
/* 2198 */       return ""; 
/* 2199 */     if (length >= this.size) {
/* 2200 */       return new String(this.buffer, 0, this.size);
/*      */     }
/* 2202 */     return new String(this.buffer, this.size - length, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String midString(int index, int length) {
/* 2223 */     if (index < 0) {
/* 2224 */       index = 0;
/*      */     }
/* 2226 */     if (length <= 0 || index >= this.size) {
/* 2227 */       return "";
/*      */     }
/* 2229 */     if (this.size <= index + length) {
/* 2230 */       return new String(this.buffer, index, this.size - index);
/*      */     }
/* 2232 */     return new String(this.buffer, index, length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean contains(char ch) {
/* 2243 */     char[] thisBuf = this.buffer;
/* 2244 */     for (int i = 0; i < this.size; i++) {
/* 2245 */       if (thisBuf[i] == ch) {
/* 2246 */         return true;
/*      */       }
/*      */     } 
/* 2249 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean contains(String str) {
/* 2259 */     return (indexOf(str, 0) >= 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean contains(StrMatcher matcher) {
/* 2274 */     return (indexOf(matcher, 0) >= 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int indexOf(char ch) {
/* 2285 */     return indexOf(ch, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int indexOf(char ch, int startIndex) {
/* 2296 */     startIndex = (startIndex < 0) ? 0 : startIndex;
/* 2297 */     if (startIndex >= this.size) {
/* 2298 */       return -1;
/*      */     }
/* 2300 */     char[] thisBuf = this.buffer;
/* 2301 */     for (int i = startIndex; i < this.size; i++) {
/* 2302 */       if (thisBuf[i] == ch) {
/* 2303 */         return i;
/*      */       }
/*      */     } 
/* 2306 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int indexOf(String str) {
/* 2318 */     return indexOf(str, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int indexOf(String str, int startIndex) {
/* 2332 */     startIndex = (startIndex < 0) ? 0 : startIndex;
/* 2333 */     if (str == null || startIndex >= this.size) {
/* 2334 */       return -1;
/*      */     }
/* 2336 */     int strLen = str.length();
/* 2337 */     if (strLen == 1) {
/* 2338 */       return indexOf(str.charAt(0), startIndex);
/*      */     }
/* 2340 */     if (strLen == 0) {
/* 2341 */       return startIndex;
/*      */     }
/* 2343 */     if (strLen > this.size) {
/* 2344 */       return -1;
/*      */     }
/* 2346 */     char[] thisBuf = this.buffer;
/* 2347 */     int len = this.size - strLen + 1;
/*      */     
/* 2349 */     for (int i = startIndex; i < len; i++) {
/* 2350 */       int j = 0; while (true) { if (j < strLen) {
/* 2351 */           if (str.charAt(j) != thisBuf[i + j])
/*      */             break;  j++;
/*      */           continue;
/*      */         } 
/* 2355 */         return i; }
/*      */     
/* 2357 */     }  return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int indexOf(StrMatcher matcher) {
/* 2371 */     return indexOf(matcher, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int indexOf(StrMatcher matcher, int startIndex) {
/* 2387 */     startIndex = (startIndex < 0) ? 0 : startIndex;
/* 2388 */     if (matcher == null || startIndex >= this.size) {
/* 2389 */       return -1;
/*      */     }
/* 2391 */     int len = this.size;
/* 2392 */     char[] buf = this.buffer;
/* 2393 */     for (int i = startIndex; i < len; i++) {
/* 2394 */       if (matcher.isMatch(buf, i, startIndex, len) > 0) {
/* 2395 */         return i;
/*      */       }
/*      */     } 
/* 2398 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int lastIndexOf(char ch) {
/* 2409 */     return lastIndexOf(ch, this.size - 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int lastIndexOf(char ch, int startIndex) {
/* 2420 */     startIndex = (startIndex >= this.size) ? (this.size - 1) : startIndex;
/* 2421 */     if (startIndex < 0) {
/* 2422 */       return -1;
/*      */     }
/* 2424 */     for (int i = startIndex; i >= 0; i--) {
/* 2425 */       if (this.buffer[i] == ch) {
/* 2426 */         return i;
/*      */       }
/*      */     } 
/* 2429 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int lastIndexOf(String str) {
/* 2441 */     return lastIndexOf(str, this.size - 1);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int lastIndexOf(String str, int startIndex) {
/* 2455 */     startIndex = (startIndex >= this.size) ? (this.size - 1) : startIndex;
/* 2456 */     if (str == null || startIndex < 0) {
/* 2457 */       return -1;
/*      */     }
/* 2459 */     int strLen = str.length();
/* 2460 */     if (strLen > 0 && strLen <= this.size) {
/* 2461 */       if (strLen == 1) {
/* 2462 */         return lastIndexOf(str.charAt(0), startIndex);
/*      */       }
/*      */ 
/*      */       
/* 2466 */       for (int i = startIndex - strLen + 1; i >= 0; i--) {
/* 2467 */         int j = 0; while (true) { if (j < strLen) {
/* 2468 */             if (str.charAt(j) != this.buffer[i + j])
/*      */               break;  j++;
/*      */             continue;
/*      */           } 
/* 2472 */           return i; }
/*      */       
/*      */       } 
/* 2475 */     } else if (strLen == 0) {
/* 2476 */       return startIndex;
/*      */     } 
/* 2478 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int lastIndexOf(StrMatcher matcher) {
/* 2492 */     return lastIndexOf(matcher, this.size);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int lastIndexOf(StrMatcher matcher, int startIndex) {
/* 2508 */     startIndex = (startIndex >= this.size) ? (this.size - 1) : startIndex;
/* 2509 */     if (matcher == null || startIndex < 0) {
/* 2510 */       return -1;
/*      */     }
/* 2512 */     char[] buf = this.buffer;
/* 2513 */     int endIndex = startIndex + 1;
/* 2514 */     for (int i = startIndex; i >= 0; i--) {
/* 2515 */       if (matcher.isMatch(buf, i, 0, endIndex) > 0) {
/* 2516 */         return i;
/*      */       }
/*      */     } 
/* 2519 */     return -1;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StrTokenizer asTokenizer() {
/* 2556 */     return new StrBuilderTokenizer();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Reader asReader() {
/* 2580 */     return new StrBuilderReader();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Writer asWriter() {
/* 2605 */     return new StrBuilderWriter();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equalsIgnoreCase(StrBuilder other) {
/* 2647 */     if (this == other) {
/* 2648 */       return true;
/*      */     }
/* 2650 */     if (this.size != other.size) {
/* 2651 */       return false;
/*      */     }
/* 2653 */     char[] thisBuf = this.buffer;
/* 2654 */     char[] otherBuf = other.buffer;
/* 2655 */     for (int i = this.size - 1; i >= 0; i--) {
/* 2656 */       char c1 = thisBuf[i];
/* 2657 */       char c2 = otherBuf[i];
/* 2658 */       if (c1 != c2 && Character.toUpperCase(c1) != Character.toUpperCase(c2)) {
/* 2659 */         return false;
/*      */       }
/*      */     } 
/* 2662 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(StrBuilder other) {
/* 2673 */     if (this == other) {
/* 2674 */       return true;
/*      */     }
/* 2676 */     if (this.size != other.size) {
/* 2677 */       return false;
/*      */     }
/* 2679 */     char[] thisBuf = this.buffer;
/* 2680 */     char[] otherBuf = other.buffer;
/* 2681 */     for (int i = this.size - 1; i >= 0; i--) {
/* 2682 */       if (thisBuf[i] != otherBuf[i]) {
/* 2683 */         return false;
/*      */       }
/*      */     } 
/* 2686 */     return true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean equals(Object obj) {
/* 2698 */     if (obj instanceof StrBuilder) {
/* 2699 */       return equals((StrBuilder)obj);
/*      */     }
/* 2701 */     return false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 2711 */     char[] buf = this.buffer;
/* 2712 */     int hash = 0;
/* 2713 */     for (int i = this.size - 1; i >= 0; i--) {
/* 2714 */       hash = 31 * hash + buf[i];
/*      */     }
/* 2716 */     return hash;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/* 2731 */     return new String(this.buffer, 0, this.size);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StringBuffer toStringBuffer() {
/* 2741 */     return (new StringBuffer(this.size)).append(this.buffer, 0, this.size);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StringBuilder toStringBuilder() {
/* 2752 */     return (new StringBuilder(this.size)).append(this.buffer, 0, this.size);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String build() {
/* 2763 */     return toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected int validateRange(int startIndex, int endIndex) {
/* 2777 */     if (startIndex < 0) {
/* 2778 */       throw new StringIndexOutOfBoundsException(startIndex);
/*      */     }
/* 2780 */     if (endIndex > this.size) {
/* 2781 */       endIndex = this.size;
/*      */     }
/* 2783 */     if (startIndex > endIndex) {
/* 2784 */       throw new StringIndexOutOfBoundsException("end < start");
/*      */     }
/* 2786 */     return endIndex;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void validateIndex(int index) {
/* 2796 */     if (index < 0 || index > this.size) {
/* 2797 */       throw new StringIndexOutOfBoundsException(index);
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
/*      */   class StrBuilderTokenizer
/*      */     extends StrTokenizer
/*      */   {
/*      */     protected List<String> tokenize(char[] chars, int offset, int count) {
/* 2817 */       if (chars == null) {
/* 2818 */         return super.tokenize(StrBuilder.this.buffer, 0, StrBuilder.this.size());
/*      */       }
/* 2820 */       return super.tokenize(chars, offset, count);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public String getContent() {
/* 2826 */       String str = super.getContent();
/* 2827 */       if (str == null) {
/* 2828 */         return StrBuilder.this.toString();
/*      */       }
/* 2830 */       return str;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   class StrBuilderReader
/*      */     extends Reader
/*      */   {
/*      */     private int pos;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private int mark;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void close() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int read() {
/* 2860 */       if (!ready()) {
/* 2861 */         return -1;
/*      */       }
/* 2863 */       return StrBuilder.this.charAt(this.pos++);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public int read(char[] b, int off, int len) {
/* 2869 */       if (off < 0 || len < 0 || off > b.length || off + len > b.length || off + len < 0)
/*      */       {
/* 2871 */         throw new IndexOutOfBoundsException();
/*      */       }
/* 2873 */       if (len == 0) {
/* 2874 */         return 0;
/*      */       }
/* 2876 */       if (this.pos >= StrBuilder.this.size()) {
/* 2877 */         return -1;
/*      */       }
/* 2879 */       if (this.pos + len > StrBuilder.this.size()) {
/* 2880 */         len = StrBuilder.this.size() - this.pos;
/*      */       }
/* 2882 */       StrBuilder.this.getChars(this.pos, this.pos + len, b, off);
/* 2883 */       this.pos += len;
/* 2884 */       return len;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public long skip(long n) {
/* 2890 */       if (this.pos + n > StrBuilder.this.size()) {
/* 2891 */         n = (StrBuilder.this.size() - this.pos);
/*      */       }
/* 2893 */       if (n < 0L) {
/* 2894 */         return 0L;
/*      */       }
/* 2896 */       this.pos = (int)(this.pos + n);
/* 2897 */       return n;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean ready() {
/* 2903 */       return (this.pos < StrBuilder.this.size());
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean markSupported() {
/* 2909 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void mark(int readAheadLimit) {
/* 2915 */       this.mark = this.pos;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void reset() {
/* 2921 */       this.pos = this.mark;
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
/*      */   class StrBuilderWriter
/*      */     extends Writer
/*      */   {
/*      */     public void close() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void flush() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void write(int c) {
/* 2953 */       StrBuilder.this.append((char)c);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void write(char[] cbuf) {
/* 2959 */       StrBuilder.this.append(cbuf);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void write(char[] cbuf, int off, int len) {
/* 2965 */       StrBuilder.this.append(cbuf, off, len);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void write(String str) {
/* 2971 */       StrBuilder.this.append(str);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void write(String str, int off, int len) {
/* 2977 */       StrBuilder.this.append(str, off, len);
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\org\apache\commons\lang3\text\StrBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */