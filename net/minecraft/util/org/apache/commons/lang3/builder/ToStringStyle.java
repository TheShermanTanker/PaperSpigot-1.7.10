/*      */ package net.minecraft.util.org.apache.commons.lang3.builder;
/*      */ 
/*      */ import java.io.Serializable;
/*      */ import java.lang.reflect.Array;
/*      */ import java.util.Collection;
/*      */ import java.util.Map;
/*      */ import java.util.WeakHashMap;
/*      */ import net.minecraft.util.org.apache.commons.lang3.ClassUtils;
/*      */ import net.minecraft.util.org.apache.commons.lang3.ObjectUtils;
/*      */ import net.minecraft.util.org.apache.commons.lang3.SystemUtils;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public abstract class ToStringStyle
/*      */   implements Serializable
/*      */ {
/*      */   private static final long serialVersionUID = -2587890625525655916L;
/*   81 */   public static final ToStringStyle DEFAULT_STYLE = new DefaultToStringStyle();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*   95 */   public static final ToStringStyle MULTI_LINE_STYLE = new MultiLineToStringStyle();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  106 */   public static final ToStringStyle NO_FIELD_NAMES_STYLE = new NoFieldNameToStringStyle();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  118 */   public static final ToStringStyle SHORT_PREFIX_STYLE = new ShortPrefixToStringStyle();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  128 */   public static final ToStringStyle SIMPLE_STYLE = new SimpleToStringStyle();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  136 */   private static final ThreadLocal<WeakHashMap<Object, Object>> REGISTRY = new ThreadLocal<WeakHashMap<Object, Object>>();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static Map<Object, Object> getRegistry() {
/*  157 */     return REGISTRY.get();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static boolean isRegistered(Object value) {
/*  172 */     Map<Object, Object> m = getRegistry();
/*  173 */     return (m != null && m.containsKey(value));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void register(Object value) {
/*  186 */     if (value != null) {
/*  187 */       Map<Object, Object> m = getRegistry();
/*  188 */       if (m == null) {
/*  189 */         REGISTRY.set(new WeakHashMap<Object, Object>());
/*      */       }
/*  191 */       getRegistry().put(value, null);
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
/*      */   static void unregister(Object value) {
/*  208 */     if (value != null) {
/*  209 */       Map<Object, Object> m = getRegistry();
/*  210 */       if (m != null) {
/*  211 */         m.remove(value);
/*  212 */         if (m.isEmpty()) {
/*  213 */           REGISTRY.remove();
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean useFieldNames = true;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean useClassName = true;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean useShortClassName = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean useIdentityHashCode = true;
/*      */ 
/*      */ 
/*      */   
/*  242 */   private String contentStart = "[";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  247 */   private String contentEnd = "]";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  252 */   private String fieldNameValueSeparator = "=";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean fieldSeparatorAtStart = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean fieldSeparatorAtEnd = false;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  267 */   private String fieldSeparator = ",";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  272 */   private String arrayStart = "{";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  277 */   private String arraySeparator = ",";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean arrayContentDetail = true;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  287 */   private String arrayEnd = "}";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean defaultFullDetail = true;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  298 */   private String nullText = "<null>";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  303 */   private String sizeStartText = "<size=";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  308 */   private String sizeEndText = ">";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  313 */   private String summaryObjectStartText = "<";
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  318 */   private String summaryObjectEndText = ">";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void appendSuper(StringBuffer buffer, String superToString) {
/*  342 */     appendToString(buffer, superToString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void appendToString(StringBuffer buffer, String toString) {
/*  356 */     if (toString != null) {
/*  357 */       int pos1 = toString.indexOf(this.contentStart) + this.contentStart.length();
/*  358 */       int pos2 = toString.lastIndexOf(this.contentEnd);
/*  359 */       if (pos1 != pos2 && pos1 >= 0 && pos2 >= 0) {
/*  360 */         String data = toString.substring(pos1, pos2);
/*  361 */         if (this.fieldSeparatorAtStart) {
/*  362 */           removeLastFieldSeparator(buffer);
/*      */         }
/*  364 */         buffer.append(data);
/*  365 */         appendFieldSeparator(buffer);
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
/*      */   public void appendStart(StringBuffer buffer, Object object) {
/*  377 */     if (object != null) {
/*  378 */       appendClassName(buffer, object);
/*  379 */       appendIdentityHashCode(buffer, object);
/*  380 */       appendContentStart(buffer);
/*  381 */       if (this.fieldSeparatorAtStart) {
/*  382 */         appendFieldSeparator(buffer);
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
/*      */   public void appendEnd(StringBuffer buffer, Object object) {
/*  395 */     if (!this.fieldSeparatorAtEnd) {
/*  396 */       removeLastFieldSeparator(buffer);
/*      */     }
/*  398 */     appendContentEnd(buffer);
/*  399 */     unregister(object);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void removeLastFieldSeparator(StringBuffer buffer) {
/*  409 */     int len = buffer.length();
/*  410 */     int sepLen = this.fieldSeparator.length();
/*  411 */     if (len > 0 && sepLen > 0 && len >= sepLen) {
/*  412 */       boolean match = true;
/*  413 */       for (int i = 0; i < sepLen; i++) {
/*  414 */         if (buffer.charAt(len - 1 - i) != this.fieldSeparator.charAt(sepLen - 1 - i)) {
/*  415 */           match = false;
/*      */           break;
/*      */         } 
/*      */       } 
/*  419 */       if (match) {
/*  420 */         buffer.setLength(len - sepLen);
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
/*      */   public void append(StringBuffer buffer, String fieldName, Object value, Boolean fullDetail) {
/*  439 */     appendFieldStart(buffer, fieldName);
/*      */     
/*  441 */     if (value == null) {
/*  442 */       appendNullText(buffer, fieldName);
/*      */     } else {
/*      */       
/*  445 */       appendInternal(buffer, fieldName, value, isFullDetail(fullDetail));
/*      */     } 
/*      */     
/*  448 */     appendFieldEnd(buffer, fieldName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void appendInternal(StringBuffer buffer, String fieldName, Object value, boolean detail) {
/*  471 */     if (isRegistered(value) && !(value instanceof Number) && !(value instanceof Boolean) && !(value instanceof Character)) {
/*      */       
/*  473 */       appendCyclicObject(buffer, fieldName, value);
/*      */       
/*      */       return;
/*      */     } 
/*  477 */     register(value);
/*      */     
/*      */     try {
/*  480 */       if (value instanceof Collection) {
/*  481 */         if (detail) {
/*  482 */           appendDetail(buffer, fieldName, (Collection)value);
/*      */         } else {
/*  484 */           appendSummarySize(buffer, fieldName, ((Collection)value).size());
/*      */         }
/*      */       
/*  487 */       } else if (value instanceof Map) {
/*  488 */         if (detail) {
/*  489 */           appendDetail(buffer, fieldName, (Map<?, ?>)value);
/*      */         } else {
/*  491 */           appendSummarySize(buffer, fieldName, ((Map)value).size());
/*      */         }
/*      */       
/*  494 */       } else if (value instanceof long[]) {
/*  495 */         if (detail) {
/*  496 */           appendDetail(buffer, fieldName, (long[])value);
/*      */         } else {
/*  498 */           appendSummary(buffer, fieldName, (long[])value);
/*      */         }
/*      */       
/*  501 */       } else if (value instanceof int[]) {
/*  502 */         if (detail) {
/*  503 */           appendDetail(buffer, fieldName, (int[])value);
/*      */         } else {
/*  505 */           appendSummary(buffer, fieldName, (int[])value);
/*      */         }
/*      */       
/*  508 */       } else if (value instanceof short[]) {
/*  509 */         if (detail) {
/*  510 */           appendDetail(buffer, fieldName, (short[])value);
/*      */         } else {
/*  512 */           appendSummary(buffer, fieldName, (short[])value);
/*      */         }
/*      */       
/*  515 */       } else if (value instanceof byte[]) {
/*  516 */         if (detail) {
/*  517 */           appendDetail(buffer, fieldName, (byte[])value);
/*      */         } else {
/*  519 */           appendSummary(buffer, fieldName, (byte[])value);
/*      */         }
/*      */       
/*  522 */       } else if (value instanceof char[]) {
/*  523 */         if (detail) {
/*  524 */           appendDetail(buffer, fieldName, (char[])value);
/*      */         } else {
/*  526 */           appendSummary(buffer, fieldName, (char[])value);
/*      */         }
/*      */       
/*  529 */       } else if (value instanceof double[]) {
/*  530 */         if (detail) {
/*  531 */           appendDetail(buffer, fieldName, (double[])value);
/*      */         } else {
/*  533 */           appendSummary(buffer, fieldName, (double[])value);
/*      */         }
/*      */       
/*  536 */       } else if (value instanceof float[]) {
/*  537 */         if (detail) {
/*  538 */           appendDetail(buffer, fieldName, (float[])value);
/*      */         } else {
/*  540 */           appendSummary(buffer, fieldName, (float[])value);
/*      */         }
/*      */       
/*  543 */       } else if (value instanceof boolean[]) {
/*  544 */         if (detail) {
/*  545 */           appendDetail(buffer, fieldName, (boolean[])value);
/*      */         } else {
/*  547 */           appendSummary(buffer, fieldName, (boolean[])value);
/*      */         }
/*      */       
/*  550 */       } else if (value.getClass().isArray()) {
/*  551 */         if (detail) {
/*  552 */           appendDetail(buffer, fieldName, (Object[])value);
/*      */         } else {
/*  554 */           appendSummary(buffer, fieldName, (Object[])value);
/*      */         }
/*      */       
/*      */       }
/*  558 */       else if (detail) {
/*  559 */         appendDetail(buffer, fieldName, value);
/*      */       } else {
/*  561 */         appendSummary(buffer, fieldName, value);
/*      */       } 
/*      */     } finally {
/*      */       
/*  565 */       unregister(value);
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
/*      */   protected void appendCyclicObject(StringBuffer buffer, String fieldName, Object value) {
/*  582 */     ObjectUtils.identityToString(buffer, value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void appendDetail(StringBuffer buffer, String fieldName, Object value) {
/*  595 */     buffer.append(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void appendDetail(StringBuffer buffer, String fieldName, Collection<?> coll) {
/*  607 */     buffer.append(coll);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void appendDetail(StringBuffer buffer, String fieldName, Map<?, ?> map) {
/*  619 */     buffer.append(map);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void appendSummary(StringBuffer buffer, String fieldName, Object value) {
/*  632 */     buffer.append(this.summaryObjectStartText);
/*  633 */     buffer.append(getShortClassName(value.getClass()));
/*  634 */     buffer.append(this.summaryObjectEndText);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void append(StringBuffer buffer, String fieldName, long value) {
/*  648 */     appendFieldStart(buffer, fieldName);
/*  649 */     appendDetail(buffer, fieldName, value);
/*  650 */     appendFieldEnd(buffer, fieldName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void appendDetail(StringBuffer buffer, String fieldName, long value) {
/*  662 */     buffer.append(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void append(StringBuffer buffer, String fieldName, int value) {
/*  676 */     appendFieldStart(buffer, fieldName);
/*  677 */     appendDetail(buffer, fieldName, value);
/*  678 */     appendFieldEnd(buffer, fieldName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void appendDetail(StringBuffer buffer, String fieldName, int value) {
/*  690 */     buffer.append(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void append(StringBuffer buffer, String fieldName, short value) {
/*  704 */     appendFieldStart(buffer, fieldName);
/*  705 */     appendDetail(buffer, fieldName, value);
/*  706 */     appendFieldEnd(buffer, fieldName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void appendDetail(StringBuffer buffer, String fieldName, short value) {
/*  718 */     buffer.append(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void append(StringBuffer buffer, String fieldName, byte value) {
/*  732 */     appendFieldStart(buffer, fieldName);
/*  733 */     appendDetail(buffer, fieldName, value);
/*  734 */     appendFieldEnd(buffer, fieldName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void appendDetail(StringBuffer buffer, String fieldName, byte value) {
/*  746 */     buffer.append(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void append(StringBuffer buffer, String fieldName, char value) {
/*  760 */     appendFieldStart(buffer, fieldName);
/*  761 */     appendDetail(buffer, fieldName, value);
/*  762 */     appendFieldEnd(buffer, fieldName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void appendDetail(StringBuffer buffer, String fieldName, char value) {
/*  774 */     buffer.append(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void append(StringBuffer buffer, String fieldName, double value) {
/*  788 */     appendFieldStart(buffer, fieldName);
/*  789 */     appendDetail(buffer, fieldName, value);
/*  790 */     appendFieldEnd(buffer, fieldName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void appendDetail(StringBuffer buffer, String fieldName, double value) {
/*  802 */     buffer.append(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void append(StringBuffer buffer, String fieldName, float value) {
/*  816 */     appendFieldStart(buffer, fieldName);
/*  817 */     appendDetail(buffer, fieldName, value);
/*  818 */     appendFieldEnd(buffer, fieldName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void appendDetail(StringBuffer buffer, String fieldName, float value) {
/*  830 */     buffer.append(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void append(StringBuffer buffer, String fieldName, boolean value) {
/*  844 */     appendFieldStart(buffer, fieldName);
/*  845 */     appendDetail(buffer, fieldName, value);
/*  846 */     appendFieldEnd(buffer, fieldName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void appendDetail(StringBuffer buffer, String fieldName, boolean value) {
/*  858 */     buffer.append(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void append(StringBuffer buffer, String fieldName, Object[] array, Boolean fullDetail) {
/*  872 */     appendFieldStart(buffer, fieldName);
/*      */     
/*  874 */     if (array == null) {
/*  875 */       appendNullText(buffer, fieldName);
/*      */     }
/*  877 */     else if (isFullDetail(fullDetail)) {
/*  878 */       appendDetail(buffer, fieldName, array);
/*      */     } else {
/*      */       
/*  881 */       appendSummary(buffer, fieldName, array);
/*      */     } 
/*      */     
/*  884 */     appendFieldEnd(buffer, fieldName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void appendDetail(StringBuffer buffer, String fieldName, Object[] array) {
/*  899 */     buffer.append(this.arrayStart);
/*  900 */     for (int i = 0; i < array.length; i++) {
/*  901 */       Object item = array[i];
/*  902 */       if (i > 0) {
/*  903 */         buffer.append(this.arraySeparator);
/*      */       }
/*  905 */       if (item == null) {
/*  906 */         appendNullText(buffer, fieldName);
/*      */       } else {
/*      */         
/*  909 */         appendInternal(buffer, fieldName, item, this.arrayContentDetail);
/*      */       } 
/*      */     } 
/*  912 */     buffer.append(this.arrayEnd);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void reflectionAppendArrayDetail(StringBuffer buffer, String fieldName, Object array) {
/*  925 */     buffer.append(this.arrayStart);
/*  926 */     int length = Array.getLength(array);
/*  927 */     for (int i = 0; i < length; i++) {
/*  928 */       Object item = Array.get(array, i);
/*  929 */       if (i > 0) {
/*  930 */         buffer.append(this.arraySeparator);
/*      */       }
/*  932 */       if (item == null) {
/*  933 */         appendNullText(buffer, fieldName);
/*      */       } else {
/*      */         
/*  936 */         appendInternal(buffer, fieldName, item, this.arrayContentDetail);
/*      */       } 
/*      */     } 
/*  939 */     buffer.append(this.arrayEnd);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void appendSummary(StringBuffer buffer, String fieldName, Object[] array) {
/*  952 */     appendSummarySize(buffer, fieldName, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void append(StringBuffer buffer, String fieldName, long[] array, Boolean fullDetail) {
/*  968 */     appendFieldStart(buffer, fieldName);
/*      */     
/*  970 */     if (array == null) {
/*  971 */       appendNullText(buffer, fieldName);
/*      */     }
/*  973 */     else if (isFullDetail(fullDetail)) {
/*  974 */       appendDetail(buffer, fieldName, array);
/*      */     } else {
/*      */       
/*  977 */       appendSummary(buffer, fieldName, array);
/*      */     } 
/*      */     
/*  980 */     appendFieldEnd(buffer, fieldName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void appendDetail(StringBuffer buffer, String fieldName, long[] array) {
/*  993 */     buffer.append(this.arrayStart);
/*  994 */     for (int i = 0; i < array.length; i++) {
/*  995 */       if (i > 0) {
/*  996 */         buffer.append(this.arraySeparator);
/*      */       }
/*  998 */       appendDetail(buffer, fieldName, array[i]);
/*      */     } 
/* 1000 */     buffer.append(this.arrayEnd);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void appendSummary(StringBuffer buffer, String fieldName, long[] array) {
/* 1013 */     appendSummarySize(buffer, fieldName, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void append(StringBuffer buffer, String fieldName, int[] array, Boolean fullDetail) {
/* 1029 */     appendFieldStart(buffer, fieldName);
/*      */     
/* 1031 */     if (array == null) {
/* 1032 */       appendNullText(buffer, fieldName);
/*      */     }
/* 1034 */     else if (isFullDetail(fullDetail)) {
/* 1035 */       appendDetail(buffer, fieldName, array);
/*      */     } else {
/*      */       
/* 1038 */       appendSummary(buffer, fieldName, array);
/*      */     } 
/*      */     
/* 1041 */     appendFieldEnd(buffer, fieldName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void appendDetail(StringBuffer buffer, String fieldName, int[] array) {
/* 1054 */     buffer.append(this.arrayStart);
/* 1055 */     for (int i = 0; i < array.length; i++) {
/* 1056 */       if (i > 0) {
/* 1057 */         buffer.append(this.arraySeparator);
/*      */       }
/* 1059 */       appendDetail(buffer, fieldName, array[i]);
/*      */     } 
/* 1061 */     buffer.append(this.arrayEnd);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void appendSummary(StringBuffer buffer, String fieldName, int[] array) {
/* 1074 */     appendSummarySize(buffer, fieldName, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void append(StringBuffer buffer, String fieldName, short[] array, Boolean fullDetail) {
/* 1090 */     appendFieldStart(buffer, fieldName);
/*      */     
/* 1092 */     if (array == null) {
/* 1093 */       appendNullText(buffer, fieldName);
/*      */     }
/* 1095 */     else if (isFullDetail(fullDetail)) {
/* 1096 */       appendDetail(buffer, fieldName, array);
/*      */     } else {
/*      */       
/* 1099 */       appendSummary(buffer, fieldName, array);
/*      */     } 
/*      */     
/* 1102 */     appendFieldEnd(buffer, fieldName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void appendDetail(StringBuffer buffer, String fieldName, short[] array) {
/* 1115 */     buffer.append(this.arrayStart);
/* 1116 */     for (int i = 0; i < array.length; i++) {
/* 1117 */       if (i > 0) {
/* 1118 */         buffer.append(this.arraySeparator);
/*      */       }
/* 1120 */       appendDetail(buffer, fieldName, array[i]);
/*      */     } 
/* 1122 */     buffer.append(this.arrayEnd);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void appendSummary(StringBuffer buffer, String fieldName, short[] array) {
/* 1135 */     appendSummarySize(buffer, fieldName, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void append(StringBuffer buffer, String fieldName, byte[] array, Boolean fullDetail) {
/* 1151 */     appendFieldStart(buffer, fieldName);
/*      */     
/* 1153 */     if (array == null) {
/* 1154 */       appendNullText(buffer, fieldName);
/*      */     }
/* 1156 */     else if (isFullDetail(fullDetail)) {
/* 1157 */       appendDetail(buffer, fieldName, array);
/*      */     } else {
/*      */       
/* 1160 */       appendSummary(buffer, fieldName, array);
/*      */     } 
/*      */     
/* 1163 */     appendFieldEnd(buffer, fieldName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void appendDetail(StringBuffer buffer, String fieldName, byte[] array) {
/* 1176 */     buffer.append(this.arrayStart);
/* 1177 */     for (int i = 0; i < array.length; i++) {
/* 1178 */       if (i > 0) {
/* 1179 */         buffer.append(this.arraySeparator);
/*      */       }
/* 1181 */       appendDetail(buffer, fieldName, array[i]);
/*      */     } 
/* 1183 */     buffer.append(this.arrayEnd);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void appendSummary(StringBuffer buffer, String fieldName, byte[] array) {
/* 1196 */     appendSummarySize(buffer, fieldName, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void append(StringBuffer buffer, String fieldName, char[] array, Boolean fullDetail) {
/* 1212 */     appendFieldStart(buffer, fieldName);
/*      */     
/* 1214 */     if (array == null) {
/* 1215 */       appendNullText(buffer, fieldName);
/*      */     }
/* 1217 */     else if (isFullDetail(fullDetail)) {
/* 1218 */       appendDetail(buffer, fieldName, array);
/*      */     } else {
/*      */       
/* 1221 */       appendSummary(buffer, fieldName, array);
/*      */     } 
/*      */     
/* 1224 */     appendFieldEnd(buffer, fieldName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void appendDetail(StringBuffer buffer, String fieldName, char[] array) {
/* 1237 */     buffer.append(this.arrayStart);
/* 1238 */     for (int i = 0; i < array.length; i++) {
/* 1239 */       if (i > 0) {
/* 1240 */         buffer.append(this.arraySeparator);
/*      */       }
/* 1242 */       appendDetail(buffer, fieldName, array[i]);
/*      */     } 
/* 1244 */     buffer.append(this.arrayEnd);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void appendSummary(StringBuffer buffer, String fieldName, char[] array) {
/* 1257 */     appendSummarySize(buffer, fieldName, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void append(StringBuffer buffer, String fieldName, double[] array, Boolean fullDetail) {
/* 1273 */     appendFieldStart(buffer, fieldName);
/*      */     
/* 1275 */     if (array == null) {
/* 1276 */       appendNullText(buffer, fieldName);
/*      */     }
/* 1278 */     else if (isFullDetail(fullDetail)) {
/* 1279 */       appendDetail(buffer, fieldName, array);
/*      */     } else {
/*      */       
/* 1282 */       appendSummary(buffer, fieldName, array);
/*      */     } 
/*      */     
/* 1285 */     appendFieldEnd(buffer, fieldName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void appendDetail(StringBuffer buffer, String fieldName, double[] array) {
/* 1298 */     buffer.append(this.arrayStart);
/* 1299 */     for (int i = 0; i < array.length; i++) {
/* 1300 */       if (i > 0) {
/* 1301 */         buffer.append(this.arraySeparator);
/*      */       }
/* 1303 */       appendDetail(buffer, fieldName, array[i]);
/*      */     } 
/* 1305 */     buffer.append(this.arrayEnd);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void appendSummary(StringBuffer buffer, String fieldName, double[] array) {
/* 1318 */     appendSummarySize(buffer, fieldName, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void append(StringBuffer buffer, String fieldName, float[] array, Boolean fullDetail) {
/* 1334 */     appendFieldStart(buffer, fieldName);
/*      */     
/* 1336 */     if (array == null) {
/* 1337 */       appendNullText(buffer, fieldName);
/*      */     }
/* 1339 */     else if (isFullDetail(fullDetail)) {
/* 1340 */       appendDetail(buffer, fieldName, array);
/*      */     } else {
/*      */       
/* 1343 */       appendSummary(buffer, fieldName, array);
/*      */     } 
/*      */     
/* 1346 */     appendFieldEnd(buffer, fieldName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void appendDetail(StringBuffer buffer, String fieldName, float[] array) {
/* 1359 */     buffer.append(this.arrayStart);
/* 1360 */     for (int i = 0; i < array.length; i++) {
/* 1361 */       if (i > 0) {
/* 1362 */         buffer.append(this.arraySeparator);
/*      */       }
/* 1364 */       appendDetail(buffer, fieldName, array[i]);
/*      */     } 
/* 1366 */     buffer.append(this.arrayEnd);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void appendSummary(StringBuffer buffer, String fieldName, float[] array) {
/* 1379 */     appendSummarySize(buffer, fieldName, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void append(StringBuffer buffer, String fieldName, boolean[] array, Boolean fullDetail) {
/* 1395 */     appendFieldStart(buffer, fieldName);
/*      */     
/* 1397 */     if (array == null) {
/* 1398 */       appendNullText(buffer, fieldName);
/*      */     }
/* 1400 */     else if (isFullDetail(fullDetail)) {
/* 1401 */       appendDetail(buffer, fieldName, array);
/*      */     } else {
/*      */       
/* 1404 */       appendSummary(buffer, fieldName, array);
/*      */     } 
/*      */     
/* 1407 */     appendFieldEnd(buffer, fieldName);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void appendDetail(StringBuffer buffer, String fieldName, boolean[] array) {
/* 1420 */     buffer.append(this.arrayStart);
/* 1421 */     for (int i = 0; i < array.length; i++) {
/* 1422 */       if (i > 0) {
/* 1423 */         buffer.append(this.arraySeparator);
/*      */       }
/* 1425 */       appendDetail(buffer, fieldName, array[i]);
/*      */     } 
/* 1427 */     buffer.append(this.arrayEnd);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void appendSummary(StringBuffer buffer, String fieldName, boolean[] array) {
/* 1440 */     appendSummarySize(buffer, fieldName, array.length);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void appendClassName(StringBuffer buffer, Object object) {
/* 1452 */     if (this.useClassName && object != null) {
/* 1453 */       register(object);
/* 1454 */       if (this.useShortClassName) {
/* 1455 */         buffer.append(getShortClassName(object.getClass()));
/*      */       } else {
/* 1457 */         buffer.append(object.getClass().getName());
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
/*      */   protected void appendIdentityHashCode(StringBuffer buffer, Object object) {
/* 1469 */     if (isUseIdentityHashCode() && object != null) {
/* 1470 */       register(object);
/* 1471 */       buffer.append('@');
/* 1472 */       buffer.append(Integer.toHexString(System.identityHashCode(object)));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void appendContentStart(StringBuffer buffer) {
/* 1482 */     buffer.append(this.contentStart);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void appendContentEnd(StringBuffer buffer) {
/* 1491 */     buffer.append(this.contentEnd);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void appendNullText(StringBuffer buffer, String fieldName) {
/* 1503 */     buffer.append(this.nullText);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void appendFieldSeparator(StringBuffer buffer) {
/* 1512 */     buffer.append(this.fieldSeparator);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void appendFieldStart(StringBuffer buffer, String fieldName) {
/* 1522 */     if (this.useFieldNames && fieldName != null) {
/* 1523 */       buffer.append(fieldName);
/* 1524 */       buffer.append(this.fieldNameValueSeparator);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void appendFieldEnd(StringBuffer buffer, String fieldName) {
/* 1535 */     appendFieldSeparator(buffer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void appendSummarySize(StringBuffer buffer, String fieldName, int size) {
/* 1554 */     buffer.append(this.sizeStartText);
/* 1555 */     buffer.append(size);
/* 1556 */     buffer.append(this.sizeEndText);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isFullDetail(Boolean fullDetailRequest) {
/* 1574 */     if (fullDetailRequest == null) {
/* 1575 */       return this.defaultFullDetail;
/*      */     }
/* 1577 */     return fullDetailRequest.booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getShortClassName(Class<?> cls) {
/* 1590 */     return ClassUtils.getShortClassName(cls);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isUseClassName() {
/* 1604 */     return this.useClassName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setUseClassName(boolean useClassName) {
/* 1613 */     this.useClassName = useClassName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isUseShortClassName() {
/* 1625 */     return this.useShortClassName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setUseShortClassName(boolean useShortClassName) {
/* 1635 */     this.useShortClassName = useShortClassName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isUseIdentityHashCode() {
/* 1646 */     return this.useIdentityHashCode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setUseIdentityHashCode(boolean useIdentityHashCode) {
/* 1655 */     this.useIdentityHashCode = useIdentityHashCode;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isUseFieldNames() {
/* 1666 */     return this.useFieldNames;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setUseFieldNames(boolean useFieldNames) {
/* 1675 */     this.useFieldNames = useFieldNames;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isDefaultFullDetail() {
/* 1687 */     return this.defaultFullDetail;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setDefaultFullDetail(boolean defaultFullDetail) {
/* 1697 */     this.defaultFullDetail = defaultFullDetail;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isArrayContentDetail() {
/* 1708 */     return this.arrayContentDetail;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setArrayContentDetail(boolean arrayContentDetail) {
/* 1717 */     this.arrayContentDetail = arrayContentDetail;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getArrayStart() {
/* 1728 */     return this.arrayStart;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setArrayStart(String arrayStart) {
/* 1740 */     if (arrayStart == null) {
/* 1741 */       arrayStart = "";
/*      */     }
/* 1743 */     this.arrayStart = arrayStart;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getArrayEnd() {
/* 1754 */     return this.arrayEnd;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setArrayEnd(String arrayEnd) {
/* 1766 */     if (arrayEnd == null) {
/* 1767 */       arrayEnd = "";
/*      */     }
/* 1769 */     this.arrayEnd = arrayEnd;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getArraySeparator() {
/* 1780 */     return this.arraySeparator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setArraySeparator(String arraySeparator) {
/* 1792 */     if (arraySeparator == null) {
/* 1793 */       arraySeparator = "";
/*      */     }
/* 1795 */     this.arraySeparator = arraySeparator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getContentStart() {
/* 1806 */     return this.contentStart;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setContentStart(String contentStart) {
/* 1818 */     if (contentStart == null) {
/* 1819 */       contentStart = "";
/*      */     }
/* 1821 */     this.contentStart = contentStart;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getContentEnd() {
/* 1832 */     return this.contentEnd;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setContentEnd(String contentEnd) {
/* 1844 */     if (contentEnd == null) {
/* 1845 */       contentEnd = "";
/*      */     }
/* 1847 */     this.contentEnd = contentEnd;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getFieldNameValueSeparator() {
/* 1858 */     return this.fieldNameValueSeparator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setFieldNameValueSeparator(String fieldNameValueSeparator) {
/* 1870 */     if (fieldNameValueSeparator == null) {
/* 1871 */       fieldNameValueSeparator = "";
/*      */     }
/* 1873 */     this.fieldNameValueSeparator = fieldNameValueSeparator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getFieldSeparator() {
/* 1884 */     return this.fieldSeparator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setFieldSeparator(String fieldSeparator) {
/* 1896 */     if (fieldSeparator == null) {
/* 1897 */       fieldSeparator = "";
/*      */     }
/* 1899 */     this.fieldSeparator = fieldSeparator;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isFieldSeparatorAtStart() {
/* 1912 */     return this.fieldSeparatorAtStart;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setFieldSeparatorAtStart(boolean fieldSeparatorAtStart) {
/* 1923 */     this.fieldSeparatorAtStart = fieldSeparatorAtStart;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean isFieldSeparatorAtEnd() {
/* 1936 */     return this.fieldSeparatorAtEnd;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setFieldSeparatorAtEnd(boolean fieldSeparatorAtEnd) {
/* 1947 */     this.fieldSeparatorAtEnd = fieldSeparatorAtEnd;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getNullText() {
/* 1958 */     return this.nullText;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setNullText(String nullText) {
/* 1970 */     if (nullText == null) {
/* 1971 */       nullText = "";
/*      */     }
/* 1973 */     this.nullText = nullText;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getSizeStartText() {
/* 1987 */     return this.sizeStartText;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setSizeStartText(String sizeStartText) {
/* 2002 */     if (sizeStartText == null) {
/* 2003 */       sizeStartText = "";
/*      */     }
/* 2005 */     this.sizeStartText = sizeStartText;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getSizeEndText() {
/* 2019 */     return this.sizeEndText;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setSizeEndText(String sizeEndText) {
/* 2034 */     if (sizeEndText == null) {
/* 2035 */       sizeEndText = "";
/*      */     }
/* 2037 */     this.sizeEndText = sizeEndText;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getSummaryObjectStartText() {
/* 2051 */     return this.summaryObjectStartText;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setSummaryObjectStartText(String summaryObjectStartText) {
/* 2066 */     if (summaryObjectStartText == null) {
/* 2067 */       summaryObjectStartText = "";
/*      */     }
/* 2069 */     this.summaryObjectStartText = summaryObjectStartText;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected String getSummaryObjectEndText() {
/* 2083 */     return this.summaryObjectEndText;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setSummaryObjectEndText(String summaryObjectEndText) {
/* 2098 */     if (summaryObjectEndText == null) {
/* 2099 */       summaryObjectEndText = "";
/*      */     }
/* 2101 */     this.summaryObjectEndText = summaryObjectEndText;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static final class DefaultToStringStyle
/*      */     extends ToStringStyle
/*      */   {
/*      */     private static final long serialVersionUID = 1L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Object readResolve() {
/* 2136 */       return ToStringStyle.DEFAULT_STYLE;
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
/*      */   private static final class NoFieldNameToStringStyle
/*      */     extends ToStringStyle
/*      */   {
/*      */     private static final long serialVersionUID = 1L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     NoFieldNameToStringStyle() {
/* 2161 */       setUseFieldNames(false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Object readResolve() {
/* 2170 */       return ToStringStyle.NO_FIELD_NAMES_STYLE;
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
/*      */   private static final class ShortPrefixToStringStyle
/*      */     extends ToStringStyle
/*      */   {
/*      */     private static final long serialVersionUID = 1L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     ShortPrefixToStringStyle() {
/* 2195 */       setUseShortClassName(true);
/* 2196 */       setUseIdentityHashCode(false);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Object readResolve() {
/* 2204 */       return ToStringStyle.SHORT_PREFIX_STYLE;
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
/*      */   private static final class SimpleToStringStyle
/*      */     extends ToStringStyle
/*      */   {
/*      */     private static final long serialVersionUID = 1L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     SimpleToStringStyle() {
/* 2229 */       setUseClassName(false);
/* 2230 */       setUseIdentityHashCode(false);
/* 2231 */       setUseFieldNames(false);
/* 2232 */       setContentStart("");
/* 2233 */       setContentEnd("");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Object readResolve() {
/* 2241 */       return ToStringStyle.SIMPLE_STYLE;
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
/*      */   private static final class MultiLineToStringStyle
/*      */     extends ToStringStyle
/*      */   {
/*      */     private static final long serialVersionUID = 1L;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     MultiLineToStringStyle() {
/* 2265 */       setContentStart("[");
/* 2266 */       setFieldSeparator(SystemUtils.LINE_SEPARATOR + "  ");
/* 2267 */       setFieldSeparatorAtStart(true);
/* 2268 */       setContentEnd(SystemUtils.LINE_SEPARATOR + "]");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     private Object readResolve() {
/* 2277 */       return ToStringStyle.MULTI_LINE_STYLE;
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\net\minecraf\\util\org\apache\commons\lang3\builder\ToStringStyle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */