/*      */ package net.minecraft.util.org.apache.commons.lang3.time;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.Serializable;
/*      */ import java.text.DateFormatSymbols;
/*      */ import java.text.FieldPosition;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Calendar;
/*      */ import java.util.Date;
/*      */ import java.util.GregorianCalendar;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.TimeZone;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import java.util.concurrent.ConcurrentMap;
/*      */ import net.minecraft.util.org.apache.commons.lang3.Validate;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class FastDatePrinter
/*      */   implements DatePrinter, Serializable
/*      */ {
/*      */   private static final long serialVersionUID = 1L;
/*      */   public static final int FULL = 0;
/*      */   public static final int LONG = 1;
/*      */   public static final int MEDIUM = 2;
/*      */   public static final int SHORT = 3;
/*      */   private final String mPattern;
/*      */   private final TimeZone mTimeZone;
/*      */   private final Locale mLocale;
/*      */   private transient Rule[] mRules;
/*      */   private transient int mMaxLengthEstimate;
/*      */   
/*      */   protected FastDatePrinter(String pattern, TimeZone timeZone, Locale locale) {
/*  138 */     this.mPattern = pattern;
/*  139 */     this.mTimeZone = timeZone;
/*  140 */     this.mLocale = locale;
/*      */     
/*  142 */     init();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void init() {
/*  149 */     List<Rule> rulesList = parsePattern();
/*  150 */     this.mRules = rulesList.<Rule>toArray(new Rule[rulesList.size()]);
/*      */     
/*  152 */     int len = 0;
/*  153 */     for (int i = this.mRules.length; --i >= 0;) {
/*  154 */       len += this.mRules[i].estimateLength();
/*      */     }
/*      */     
/*  157 */     this.mMaxLengthEstimate = len;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected List<Rule> parsePattern() {
/*  169 */     DateFormatSymbols symbols = new DateFormatSymbols(this.mLocale);
/*  170 */     List<Rule> rules = new ArrayList<Rule>();
/*      */     
/*  172 */     String[] ERAs = symbols.getEras();
/*  173 */     String[] months = symbols.getMonths();
/*  174 */     String[] shortMonths = symbols.getShortMonths();
/*  175 */     String[] weekdays = symbols.getWeekdays();
/*  176 */     String[] shortWeekdays = symbols.getShortWeekdays();
/*  177 */     String[] AmPmStrings = symbols.getAmPmStrings();
/*      */     
/*  179 */     int length = this.mPattern.length();
/*  180 */     int[] indexRef = new int[1];
/*      */     
/*  182 */     for (int i = 0; i < length; i++) {
/*  183 */       Rule rule; String sub; indexRef[0] = i;
/*  184 */       String token = parseToken(this.mPattern, indexRef);
/*  185 */       i = indexRef[0];
/*      */       
/*  187 */       int tokenLen = token.length();
/*  188 */       if (tokenLen == 0) {
/*      */         break;
/*      */       }
/*      */ 
/*      */       
/*  193 */       char c = token.charAt(0);
/*      */       
/*  195 */       switch (c) {
/*      */         case 'G':
/*  197 */           rule = new TextField(0, ERAs);
/*      */           break;
/*      */         case 'y':
/*  200 */           if (tokenLen == 2) {
/*  201 */             rule = TwoDigitYearField.INSTANCE; break;
/*      */           } 
/*  203 */           rule = selectNumberRule(1, (tokenLen < 4) ? 4 : tokenLen);
/*      */           break;
/*      */         
/*      */         case 'M':
/*  207 */           if (tokenLen >= 4) {
/*  208 */             rule = new TextField(2, months); break;
/*  209 */           }  if (tokenLen == 3) {
/*  210 */             rule = new TextField(2, shortMonths); break;
/*  211 */           }  if (tokenLen == 2) {
/*  212 */             rule = TwoDigitMonthField.INSTANCE; break;
/*      */           } 
/*  214 */           rule = UnpaddedMonthField.INSTANCE;
/*      */           break;
/*      */         
/*      */         case 'd':
/*  218 */           rule = selectNumberRule(5, tokenLen);
/*      */           break;
/*      */         case 'h':
/*  221 */           rule = new TwelveHourField(selectNumberRule(10, tokenLen));
/*      */           break;
/*      */         case 'H':
/*  224 */           rule = selectNumberRule(11, tokenLen);
/*      */           break;
/*      */         case 'm':
/*  227 */           rule = selectNumberRule(12, tokenLen);
/*      */           break;
/*      */         case 's':
/*  230 */           rule = selectNumberRule(13, tokenLen);
/*      */           break;
/*      */         case 'S':
/*  233 */           rule = selectNumberRule(14, tokenLen);
/*      */           break;
/*      */         case 'E':
/*  236 */           rule = new TextField(7, (tokenLen < 4) ? shortWeekdays : weekdays);
/*      */           break;
/*      */         case 'D':
/*  239 */           rule = selectNumberRule(6, tokenLen);
/*      */           break;
/*      */         case 'F':
/*  242 */           rule = selectNumberRule(8, tokenLen);
/*      */           break;
/*      */         case 'w':
/*  245 */           rule = selectNumberRule(3, tokenLen);
/*      */           break;
/*      */         case 'W':
/*  248 */           rule = selectNumberRule(4, tokenLen);
/*      */           break;
/*      */         case 'a':
/*  251 */           rule = new TextField(9, AmPmStrings);
/*      */           break;
/*      */         case 'k':
/*  254 */           rule = new TwentyFourHourField(selectNumberRule(11, tokenLen));
/*      */           break;
/*      */         case 'K':
/*  257 */           rule = selectNumberRule(10, tokenLen);
/*      */           break;
/*      */         case 'z':
/*  260 */           if (tokenLen >= 4) {
/*  261 */             rule = new TimeZoneNameRule(this.mTimeZone, this.mLocale, 1); break;
/*      */           } 
/*  263 */           rule = new TimeZoneNameRule(this.mTimeZone, this.mLocale, 0);
/*      */           break;
/*      */         
/*      */         case 'Z':
/*  267 */           if (tokenLen == 1) {
/*  268 */             rule = TimeZoneNumberRule.INSTANCE_NO_COLON; break;
/*      */           } 
/*  270 */           rule = TimeZoneNumberRule.INSTANCE_COLON;
/*      */           break;
/*      */         
/*      */         case '\'':
/*  274 */           sub = token.substring(1);
/*  275 */           if (sub.length() == 1) {
/*  276 */             rule = new CharacterLiteral(sub.charAt(0)); break;
/*      */           } 
/*  278 */           rule = new StringLiteral(sub);
/*      */           break;
/*      */         
/*      */         default:
/*  282 */           throw new IllegalArgumentException("Illegal pattern component: " + token);
/*      */       } 
/*      */       
/*  285 */       rules.add(rule);
/*      */     } 
/*      */     
/*  288 */     return rules;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String parseToken(String pattern, int[] indexRef) {
/*  299 */     StringBuilder buf = new StringBuilder();
/*      */     
/*  301 */     int i = indexRef[0];
/*  302 */     int length = pattern.length();
/*      */     
/*  304 */     char c = pattern.charAt(i);
/*  305 */     if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) {
/*      */ 
/*      */       
/*  308 */       buf.append(c);
/*      */       
/*  310 */       while (i + 1 < length) {
/*  311 */         char peek = pattern.charAt(i + 1);
/*  312 */         if (peek == c) {
/*  313 */           buf.append(c);
/*  314 */           i++;
/*      */         }
/*      */       
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  321 */       buf.append('\'');
/*      */       
/*  323 */       boolean inLiteral = false;
/*      */       
/*  325 */       for (; i < length; i++) {
/*  326 */         c = pattern.charAt(i);
/*      */         
/*  328 */         if (c == '\'')
/*  329 */         { if (i + 1 < length && pattern.charAt(i + 1) == '\'') {
/*      */             
/*  331 */             i++;
/*  332 */             buf.append(c);
/*      */           } else {
/*  334 */             inLiteral = !inLiteral;
/*      */           }  }
/*  336 */         else { if (!inLiteral && ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))) {
/*      */             
/*  338 */             i--;
/*      */             break;
/*      */           } 
/*  341 */           buf.append(c); }
/*      */       
/*      */       } 
/*      */     } 
/*      */     
/*  346 */     indexRef[0] = i;
/*  347 */     return buf.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected NumberRule selectNumberRule(int field, int padding) {
/*  358 */     switch (padding) {
/*      */       case 1:
/*  360 */         return new UnpaddedNumberField(field);
/*      */       case 2:
/*  362 */         return new TwoDigitNumberField(field);
/*      */     } 
/*  364 */     return new PaddedNumberField(field, padding);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
/*  381 */     if (obj instanceof Date)
/*  382 */       return format((Date)obj, toAppendTo); 
/*  383 */     if (obj instanceof Calendar)
/*  384 */       return format((Calendar)obj, toAppendTo); 
/*  385 */     if (obj instanceof Long) {
/*  386 */       return format(((Long)obj).longValue(), toAppendTo);
/*      */     }
/*  388 */     throw new IllegalArgumentException("Unknown class: " + ((obj == null) ? "<null>" : obj.getClass().getName()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String format(long millis) {
/*  398 */     Calendar c = newCalendar();
/*  399 */     c.setTimeInMillis(millis);
/*  400 */     return applyRulesToString(c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private String applyRulesToString(Calendar c) {
/*  409 */     return applyRules(c, new StringBuffer(this.mMaxLengthEstimate)).toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private GregorianCalendar newCalendar() {
/*  418 */     return new GregorianCalendar(this.mTimeZone, this.mLocale);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String format(Date date) {
/*  426 */     Calendar c = newCalendar();
/*  427 */     c.setTime(date);
/*  428 */     return applyRulesToString(c);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String format(Calendar calendar) {
/*  436 */     return format(calendar, new StringBuffer(this.mMaxLengthEstimate)).toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StringBuffer format(long millis, StringBuffer buf) {
/*  444 */     return format(new Date(millis), buf);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StringBuffer format(Date date, StringBuffer buf) {
/*  452 */     Calendar c = newCalendar();
/*  453 */     c.setTime(date);
/*  454 */     return applyRules(c, buf);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public StringBuffer format(Calendar calendar, StringBuffer buf) {
/*  462 */     return applyRules(calendar, buf);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected StringBuffer applyRules(Calendar calendar, StringBuffer buf) {
/*  474 */     for (Rule rule : this.mRules) {
/*  475 */       rule.appendTo(buf, calendar);
/*      */     }
/*  477 */     return buf;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPattern() {
/*  487 */     return this.mPattern;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public TimeZone getTimeZone() {
/*  495 */     return this.mTimeZone;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Locale getLocale() {
/*  503 */     return this.mLocale;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxLengthEstimate() {
/*  516 */     return this.mMaxLengthEstimate;
/*      */   }
/*      */ 
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
/*  529 */     if (!(obj instanceof FastDatePrinter)) {
/*  530 */       return false;
/*      */     }
/*  532 */     FastDatePrinter other = (FastDatePrinter)obj;
/*  533 */     return (this.mPattern.equals(other.mPattern) && this.mTimeZone.equals(other.mTimeZone) && this.mLocale.equals(other.mLocale));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int hashCode() {
/*  545 */     return this.mPattern.hashCode() + 13 * (this.mTimeZone.hashCode() + 13 * this.mLocale.hashCode());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String toString() {
/*  555 */     return "FastDatePrinter[" + this.mPattern + "," + this.mLocale + "," + this.mTimeZone.getID() + "]";
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
/*  569 */     in.defaultReadObject();
/*  570 */     init();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static interface Rule
/*      */   {
/*      */     int estimateLength();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void appendTo(StringBuffer param1StringBuffer, Calendar param1Calendar);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static interface NumberRule
/*      */     extends Rule
/*      */   {
/*      */     void appendTo(StringBuffer param1StringBuffer, int param1Int);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class CharacterLiteral
/*      */     implements Rule
/*      */   {
/*      */     private final char mValue;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     CharacterLiteral(char value) {
/*  621 */       this.mValue = value;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int estimateLength() {
/*  629 */       return 1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void appendTo(StringBuffer buffer, Calendar calendar) {
/*  637 */       buffer.append(this.mValue);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class StringLiteral
/*      */     implements Rule
/*      */   {
/*      */     private final String mValue;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     StringLiteral(String value) {
/*  654 */       this.mValue = value;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int estimateLength() {
/*  662 */       return this.mValue.length();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void appendTo(StringBuffer buffer, Calendar calendar) {
/*  670 */       buffer.append(this.mValue);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class TextField
/*      */     implements Rule
/*      */   {
/*      */     private final int mField;
/*      */ 
/*      */ 
/*      */     
/*      */     private final String[] mValues;
/*      */ 
/*      */ 
/*      */     
/*      */     TextField(int field, String[] values) {
/*  689 */       this.mField = field;
/*  690 */       this.mValues = values;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int estimateLength() {
/*  698 */       int max = 0;
/*  699 */       for (int i = this.mValues.length; --i >= 0; ) {
/*  700 */         int len = this.mValues[i].length();
/*  701 */         if (len > max) {
/*  702 */           max = len;
/*      */         }
/*      */       } 
/*  705 */       return max;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void appendTo(StringBuffer buffer, Calendar calendar) {
/*  713 */       buffer.append(this.mValues[calendar.get(this.mField)]);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class UnpaddedNumberField
/*      */     implements NumberRule
/*      */   {
/*      */     private final int mField;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     UnpaddedNumberField(int field) {
/*  729 */       this.mField = field;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int estimateLength() {
/*  737 */       return 4;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void appendTo(StringBuffer buffer, Calendar calendar) {
/*  745 */       appendTo(buffer, calendar.get(this.mField));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final void appendTo(StringBuffer buffer, int value) {
/*  753 */       if (value < 10) {
/*  754 */         buffer.append((char)(value + 48));
/*  755 */       } else if (value < 100) {
/*  756 */         buffer.append((char)(value / 10 + 48));
/*  757 */         buffer.append((char)(value % 10 + 48));
/*      */       } else {
/*  759 */         buffer.append(Integer.toString(value));
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class UnpaddedMonthField
/*      */     implements NumberRule
/*      */   {
/*  768 */     static final UnpaddedMonthField INSTANCE = new UnpaddedMonthField();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int estimateLength() {
/*  783 */       return 2;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void appendTo(StringBuffer buffer, Calendar calendar) {
/*  791 */       appendTo(buffer, calendar.get(2) + 1);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final void appendTo(StringBuffer buffer, int value) {
/*  799 */       if (value < 10) {
/*  800 */         buffer.append((char)(value + 48));
/*      */       } else {
/*  802 */         buffer.append((char)(value / 10 + 48));
/*  803 */         buffer.append((char)(value % 10 + 48));
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class PaddedNumberField
/*      */     implements NumberRule
/*      */   {
/*      */     private final int mField;
/*      */ 
/*      */     
/*      */     private final int mSize;
/*      */ 
/*      */ 
/*      */     
/*      */     PaddedNumberField(int field, int size) {
/*  822 */       if (size < 3)
/*      */       {
/*  824 */         throw new IllegalArgumentException();
/*      */       }
/*  826 */       this.mField = field;
/*  827 */       this.mSize = size;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int estimateLength() {
/*  835 */       return 4;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void appendTo(StringBuffer buffer, Calendar calendar) {
/*  843 */       appendTo(buffer, calendar.get(this.mField));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final void appendTo(StringBuffer buffer, int value) {
/*  851 */       if (value < 100) {
/*  852 */         for (int i = this.mSize; --i >= 2;) {
/*  853 */           buffer.append('0');
/*      */         }
/*  855 */         buffer.append((char)(value / 10 + 48));
/*  856 */         buffer.append((char)(value % 10 + 48));
/*      */       } else {
/*      */         int digits;
/*  859 */         if (value < 1000) {
/*  860 */           digits = 3;
/*      */         } else {
/*  862 */           Validate.isTrue((value > -1), "Negative values should not be possible", value);
/*  863 */           digits = Integer.toString(value).length();
/*      */         } 
/*  865 */         for (int i = this.mSize; --i >= digits;) {
/*  866 */           buffer.append('0');
/*      */         }
/*  868 */         buffer.append(Integer.toString(value));
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class TwoDigitNumberField
/*      */     implements NumberRule
/*      */   {
/*      */     private final int mField;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     TwoDigitNumberField(int field) {
/*  885 */       this.mField = field;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int estimateLength() {
/*  893 */       return 2;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void appendTo(StringBuffer buffer, Calendar calendar) {
/*  901 */       appendTo(buffer, calendar.get(this.mField));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final void appendTo(StringBuffer buffer, int value) {
/*  909 */       if (value < 100) {
/*  910 */         buffer.append((char)(value / 10 + 48));
/*  911 */         buffer.append((char)(value % 10 + 48));
/*      */       } else {
/*  913 */         buffer.append(Integer.toString(value));
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class TwoDigitYearField
/*      */     implements NumberRule
/*      */   {
/*  922 */     static final TwoDigitYearField INSTANCE = new TwoDigitYearField();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int estimateLength() {
/*  936 */       return 2;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void appendTo(StringBuffer buffer, Calendar calendar) {
/*  944 */       appendTo(buffer, calendar.get(1) % 100);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final void appendTo(StringBuffer buffer, int value) {
/*  952 */       buffer.append((char)(value / 10 + 48));
/*  953 */       buffer.append((char)(value % 10 + 48));
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private static class TwoDigitMonthField
/*      */     implements NumberRule
/*      */   {
/*  961 */     static final TwoDigitMonthField INSTANCE = new TwoDigitMonthField();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int estimateLength() {
/*  975 */       return 2;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void appendTo(StringBuffer buffer, Calendar calendar) {
/*  983 */       appendTo(buffer, calendar.get(2) + 1);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public final void appendTo(StringBuffer buffer, int value) {
/*  991 */       buffer.append((char)(value / 10 + 48));
/*  992 */       buffer.append((char)(value % 10 + 48));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class TwelveHourField
/*      */     implements NumberRule
/*      */   {
/*      */     private final FastDatePrinter.NumberRule mRule;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     TwelveHourField(FastDatePrinter.NumberRule rule) {
/* 1009 */       this.mRule = rule;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int estimateLength() {
/* 1017 */       return this.mRule.estimateLength();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void appendTo(StringBuffer buffer, Calendar calendar) {
/* 1025 */       int value = calendar.get(10);
/* 1026 */       if (value == 0) {
/* 1027 */         value = calendar.getLeastMaximum(10) + 1;
/*      */       }
/* 1029 */       this.mRule.appendTo(buffer, value);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void appendTo(StringBuffer buffer, int value) {
/* 1037 */       this.mRule.appendTo(buffer, value);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class TwentyFourHourField
/*      */     implements NumberRule
/*      */   {
/*      */     private final FastDatePrinter.NumberRule mRule;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     TwentyFourHourField(FastDatePrinter.NumberRule rule) {
/* 1054 */       this.mRule = rule;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int estimateLength() {
/* 1062 */       return this.mRule.estimateLength();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void appendTo(StringBuffer buffer, Calendar calendar) {
/* 1070 */       int value = calendar.get(11);
/* 1071 */       if (value == 0) {
/* 1072 */         value = calendar.getMaximum(11) + 1;
/*      */       }
/* 1074 */       this.mRule.appendTo(buffer, value);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void appendTo(StringBuffer buffer, int value) {
/* 1082 */       this.mRule.appendTo(buffer, value);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/* 1088 */   private static ConcurrentMap<TimeZoneDisplayKey, String> cTimeZoneDisplayCache = new ConcurrentHashMap<TimeZoneDisplayKey, String>(7);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static String getTimeZoneDisplay(TimeZone tz, boolean daylight, int style, Locale locale) {
/* 1100 */     TimeZoneDisplayKey key = new TimeZoneDisplayKey(tz, daylight, style, locale);
/* 1101 */     String value = cTimeZoneDisplayCache.get(key);
/* 1102 */     if (value == null) {
/*      */       
/* 1104 */       value = tz.getDisplayName(daylight, style, locale);
/* 1105 */       String prior = cTimeZoneDisplayCache.putIfAbsent(key, value);
/* 1106 */       if (prior != null) {
/* 1107 */         value = prior;
/*      */       }
/*      */     } 
/* 1110 */     return value;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class TimeZoneNameRule
/*      */     implements Rule
/*      */   {
/*      */     private final Locale mLocale;
/*      */ 
/*      */     
/*      */     private final int mStyle;
/*      */ 
/*      */     
/*      */     private final String mStandard;
/*      */     
/*      */     private final String mDaylight;
/*      */ 
/*      */     
/*      */     TimeZoneNameRule(TimeZone timeZone, Locale locale, int style) {
/* 1130 */       this.mLocale = locale;
/* 1131 */       this.mStyle = style;
/*      */       
/* 1133 */       this.mStandard = FastDatePrinter.getTimeZoneDisplay(timeZone, false, style, locale);
/* 1134 */       this.mDaylight = FastDatePrinter.getTimeZoneDisplay(timeZone, true, style, locale);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int estimateLength() {
/* 1145 */       return Math.max(this.mStandard.length(), this.mDaylight.length());
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void appendTo(StringBuffer buffer, Calendar calendar) {
/* 1153 */       TimeZone zone = calendar.getTimeZone();
/* 1154 */       if (zone.useDaylightTime() && calendar.get(16) != 0) {
/*      */         
/* 1156 */         buffer.append(FastDatePrinter.getTimeZoneDisplay(zone, true, this.mStyle, this.mLocale));
/*      */       } else {
/* 1158 */         buffer.append(FastDatePrinter.getTimeZoneDisplay(zone, false, this.mStyle, this.mLocale));
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static class TimeZoneNumberRule
/*      */     implements Rule
/*      */   {
/* 1168 */     static final TimeZoneNumberRule INSTANCE_COLON = new TimeZoneNumberRule(true);
/* 1169 */     static final TimeZoneNumberRule INSTANCE_NO_COLON = new TimeZoneNumberRule(false);
/*      */ 
/*      */ 
/*      */     
/*      */     final boolean mColon;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     TimeZoneNumberRule(boolean colon) {
/* 1179 */       this.mColon = colon;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int estimateLength() {
/* 1187 */       return 5;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public void appendTo(StringBuffer buffer, Calendar calendar) {
/* 1195 */       int offset = calendar.get(15) + calendar.get(16);
/*      */       
/* 1197 */       if (offset < 0) {
/* 1198 */         buffer.append('-');
/* 1199 */         offset = -offset;
/*      */       } else {
/* 1201 */         buffer.append('+');
/*      */       } 
/*      */       
/* 1204 */       int hours = offset / 3600000;
/* 1205 */       buffer.append((char)(hours / 10 + 48));
/* 1206 */       buffer.append((char)(hours % 10 + 48));
/*      */       
/* 1208 */       if (this.mColon) {
/* 1209 */         buffer.append(':');
/*      */       }
/*      */       
/* 1212 */       int minutes = offset / 60000 - 60 * hours;
/* 1213 */       buffer.append((char)(minutes / 10 + 48));
/* 1214 */       buffer.append((char)(minutes % 10 + 48));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static class TimeZoneDisplayKey
/*      */   {
/*      */     private final TimeZone mTimeZone;
/*      */ 
/*      */ 
/*      */     
/*      */     private final int mStyle;
/*      */ 
/*      */ 
/*      */     
/*      */     private final Locale mLocale;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     TimeZoneDisplayKey(TimeZone timeZone, boolean daylight, int style, Locale locale) {
/* 1237 */       this.mTimeZone = timeZone;
/* 1238 */       if (daylight) {
/* 1239 */         style |= Integer.MIN_VALUE;
/*      */       }
/* 1241 */       this.mStyle = style;
/* 1242 */       this.mLocale = locale;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public int hashCode() {
/* 1250 */       return (this.mStyle * 31 + this.mLocale.hashCode()) * 31 + this.mTimeZone.hashCode();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     public boolean equals(Object obj) {
/* 1258 */       if (this == obj) {
/* 1259 */         return true;
/*      */       }
/* 1261 */       if (obj instanceof TimeZoneDisplayKey) {
/* 1262 */         TimeZoneDisplayKey other = (TimeZoneDisplayKey)obj;
/* 1263 */         return (this.mTimeZone.equals(other.mTimeZone) && this.mStyle == other.mStyle && this.mLocale.equals(other.mLocale));
/*      */       } 
/*      */ 
/*      */ 
/*      */       
/* 1268 */       return false;
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\org\apache\commons\lang3\time\FastDatePrinter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */