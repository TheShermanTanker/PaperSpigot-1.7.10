/*     */ package com.avaje.ebeaninternal.server.text.csv;
/*     */ 
/*     */ import com.avaje.ebean.EbeanServer;
/*     */ import com.avaje.ebean.bean.EntityBean;
/*     */ import com.avaje.ebean.text.StringParser;
/*     */ import com.avaje.ebean.text.TextException;
/*     */ import com.avaje.ebean.text.TimeStringParser;
/*     */ import com.avaje.ebean.text.csv.CsvCallback;
/*     */ import com.avaje.ebean.text.csv.CsvReader;
/*     */ import com.avaje.ebean.text.csv.DefaultCsvCallback;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanDescriptor;
/*     */ import com.avaje.ebeaninternal.server.deploy.BeanPropertyAssocOne;
/*     */ import com.avaje.ebeaninternal.server.el.ElPropertyValue;
/*     */ import java.io.Reader;
/*     */ import java.text.DateFormat;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
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
/*     */ public class TCsvReader<T>
/*     */   implements CsvReader<T>
/*     */ {
/*  54 */   private static final TimeStringParser TIME_PARSER = new TimeStringParser();
/*     */   
/*     */   private final EbeanServer server;
/*     */   
/*     */   private final BeanDescriptor<T> descriptor;
/*     */   
/*  60 */   private final List<CsvColumn> columnList = new ArrayList<CsvColumn>();
/*     */   
/*  62 */   private final CsvColumn ignoreColumn = new CsvColumn();
/*     */   
/*     */   private boolean treatEmptyStringAsNull = true;
/*     */   
/*     */   private boolean hasHeader;
/*     */   
/*  68 */   private int logInfoFrequency = 1000;
/*     */   
/*  70 */   private String defaultTimeFormat = "HH:mm:ss";
/*  71 */   private String defaultDateFormat = "yyyy-MM-dd";
/*  72 */   private String defaultTimestampFormat = "yyyy-MM-dd hh:mm:ss.fffffffff";
/*  73 */   private Locale defaultLocale = Locale.getDefault();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  78 */   protected int persistBatchSize = 30;
/*     */ 
/*     */   
/*     */   private boolean addPropertiesFromHeader;
/*     */ 
/*     */ 
/*     */   
/*     */   public TCsvReader(EbeanServer server, BeanDescriptor<T> descriptor) {
/*  86 */     this.server = server;
/*  87 */     this.descriptor = descriptor;
/*     */   }
/*     */   
/*     */   public void setDefaultLocale(Locale defaultLocale) {
/*  91 */     this.defaultLocale = defaultLocale;
/*     */   }
/*     */   
/*     */   public void setDefaultTimeFormat(String defaultTimeFormat) {
/*  95 */     this.defaultTimeFormat = defaultTimeFormat;
/*     */   }
/*     */   
/*     */   public void setDefaultDateFormat(String defaultDateFormat) {
/*  99 */     this.defaultDateFormat = defaultDateFormat;
/*     */   }
/*     */   
/*     */   public void setDefaultTimestampFormat(String defaultTimestampFormat) {
/* 103 */     this.defaultTimestampFormat = defaultTimestampFormat;
/*     */   }
/*     */   
/*     */   public void setPersistBatchSize(int persistBatchSize) {
/* 107 */     this.persistBatchSize = persistBatchSize;
/*     */   }
/*     */   
/*     */   public void setIgnoreHeader() {
/* 111 */     setHasHeader(true, false);
/*     */   }
/*     */   
/*     */   public void setAddPropertiesFromHeader() {
/* 115 */     setHasHeader(true, true);
/*     */   }
/*     */   
/*     */   public void setHasHeader(boolean hasHeader, boolean addPropertiesFromHeader) {
/* 119 */     this.hasHeader = hasHeader;
/* 120 */     this.addPropertiesFromHeader = addPropertiesFromHeader;
/*     */   }
/*     */   
/*     */   public void setLogInfoFrequency(int logInfoFrequency) {
/* 124 */     this.logInfoFrequency = logInfoFrequency;
/*     */   }
/*     */   
/*     */   public void addIgnore() {
/* 128 */     this.columnList.add(this.ignoreColumn);
/*     */   }
/*     */   
/*     */   public void addProperty(String propertyName) {
/* 132 */     addProperty(propertyName, null);
/*     */   }
/*     */   
/*     */   public void addReference(String propertyName) {
/* 136 */     addProperty(propertyName, null, true);
/*     */   }
/*     */   
/*     */   public void addProperty(String propertyName, StringParser parser) {
/* 140 */     addProperty(propertyName, parser, false);
/*     */   }
/*     */   
/*     */   public void addDateTime(String propertyName, String dateTimeFormat) {
/* 144 */     addDateTime(propertyName, dateTimeFormat, Locale.getDefault());
/*     */   }
/*     */ 
/*     */   
/*     */   public void addDateTime(String propertyName, String dateTimeFormat, Locale locale) {
/* 149 */     ElPropertyValue elProp = this.descriptor.getElGetValue(propertyName);
/* 150 */     if (!elProp.isDateTimeCapable()) {
/* 151 */       throw new TextException("Property " + propertyName + " is not DateTime capable");
/*     */     }
/*     */     
/* 154 */     if (dateTimeFormat == null) {
/* 155 */       dateTimeFormat = getDefaultDateTimeFormat(elProp.getJdbcType());
/*     */     }
/*     */     
/* 158 */     if (locale == null) {
/* 159 */       locale = this.defaultLocale;
/*     */     }
/*     */     
/* 162 */     SimpleDateFormat sdf = new SimpleDateFormat(dateTimeFormat, locale);
/* 163 */     DateTimeParser parser = new DateTimeParser(sdf, dateTimeFormat, elProp);
/*     */     
/* 165 */     CsvColumn column = new CsvColumn(elProp, parser, false);
/* 166 */     this.columnList.add(column);
/*     */   }
/*     */   
/*     */   private String getDefaultDateTimeFormat(int jdbcType) {
/* 170 */     switch (jdbcType) {
/*     */       case 92:
/* 172 */         return this.defaultTimeFormat;
/*     */       case 91:
/* 174 */         return this.defaultDateFormat;
/*     */       case 93:
/* 176 */         return this.defaultTimestampFormat;
/*     */     } 
/*     */     
/* 179 */     throw new RuntimeException("Expected java.sql.Types TIME,DATE or TIMESTAMP but got [" + jdbcType + "]");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addProperty(String propertyName, StringParser parser, boolean reference) {
/* 185 */     ElPropertyValue elProp = this.descriptor.getElGetValue(propertyName);
/* 186 */     if (parser == null) {
/* 187 */       parser = elProp.getStringParser();
/*     */     }
/* 189 */     CsvColumn column = new CsvColumn(elProp, parser, reference);
/* 190 */     this.columnList.add(column);
/*     */   }
/*     */   
/*     */   public void process(Reader reader) throws Exception {
/* 194 */     DefaultCsvCallback<T> callback = new DefaultCsvCallback(this.persistBatchSize, this.logInfoFrequency);
/* 195 */     process(reader, (CsvCallback<T>)callback);
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
/*     */   
/*     */   public void process(Reader reader, CsvCallback<T> callback) throws Exception {
/*     */     // Byte code:
/*     */     //   0: aload_1
/*     */     //   1: ifnonnull -> 14
/*     */     //   4: new java/lang/NullPointerException
/*     */     //   7: dup
/*     */     //   8: ldc 'reader is null?'
/*     */     //   10: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   13: athrow
/*     */     //   14: aload_2
/*     */     //   15: ifnonnull -> 28
/*     */     //   18: new java/lang/NullPointerException
/*     */     //   21: dup
/*     */     //   22: ldc 'callback is null?'
/*     */     //   24: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   27: athrow
/*     */     //   28: new com/avaje/ebeaninternal/server/text/csv/CsvUtilReader
/*     */     //   31: dup
/*     */     //   32: aload_1
/*     */     //   33: invokespecial <init> : (Ljava/io/Reader;)V
/*     */     //   36: astore_3
/*     */     //   37: aload_2
/*     */     //   38: aload_0
/*     */     //   39: getfield server : Lcom/avaje/ebean/EbeanServer;
/*     */     //   42: invokeinterface begin : (Lcom/avaje/ebean/EbeanServer;)V
/*     */     //   47: iconst_0
/*     */     //   48: istore #4
/*     */     //   50: aload_0
/*     */     //   51: getfield hasHeader : Z
/*     */     //   54: ifeq -> 84
/*     */     //   57: aload_3
/*     */     //   58: invokevirtual readNext : ()[Ljava/lang/String;
/*     */     //   61: astore #5
/*     */     //   63: aload_0
/*     */     //   64: getfield addPropertiesFromHeader : Z
/*     */     //   67: ifeq -> 76
/*     */     //   70: aload_0
/*     */     //   71: aload #5
/*     */     //   73: invokespecial addPropertiesFromHeader : ([Ljava/lang/String;)V
/*     */     //   76: aload_2
/*     */     //   77: aload #5
/*     */     //   79: invokeinterface readHeader : ([Ljava/lang/String;)V
/*     */     //   84: iinc #4, 1
/*     */     //   87: aload_3
/*     */     //   88: invokevirtual readNext : ()[Ljava/lang/String;
/*     */     //   91: astore #5
/*     */     //   93: aload #5
/*     */     //   95: ifnonnull -> 104
/*     */     //   98: iinc #4, -1
/*     */     //   101: goto -> 245
/*     */     //   104: aload_2
/*     */     //   105: iload #4
/*     */     //   107: aload #5
/*     */     //   109: invokeinterface processLine : (I[Ljava/lang/String;)Z
/*     */     //   114: ifeq -> 242
/*     */     //   117: aload #5
/*     */     //   119: arraylength
/*     */     //   120: aload_0
/*     */     //   121: getfield columnList : Ljava/util/List;
/*     */     //   124: invokeinterface size : ()I
/*     */     //   129: if_icmpeq -> 220
/*     */     //   132: new java/lang/StringBuilder
/*     */     //   135: dup
/*     */     //   136: invokespecial <init> : ()V
/*     */     //   139: ldc_w 'Error at line '
/*     */     //   142: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   145: iload #4
/*     */     //   147: invokevirtual append : (I)Ljava/lang/StringBuilder;
/*     */     //   150: ldc_w '. Expected ['
/*     */     //   153: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   156: aload_0
/*     */     //   157: getfield columnList : Ljava/util/List;
/*     */     //   160: invokeinterface size : ()I
/*     */     //   165: invokevirtual append : (I)Ljava/lang/StringBuilder;
/*     */     //   168: ldc_w '] columns '
/*     */     //   171: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   174: ldc_w 'but instead we have ['
/*     */     //   177: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   180: aload #5
/*     */     //   182: arraylength
/*     */     //   183: invokevirtual append : (I)Ljava/lang/StringBuilder;
/*     */     //   186: ldc_w '].  Line['
/*     */     //   189: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   192: aload #5
/*     */     //   194: invokestatic toString : ([Ljava/lang/Object;)Ljava/lang/String;
/*     */     //   197: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   200: ldc ']'
/*     */     //   202: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
/*     */     //   205: invokevirtual toString : ()Ljava/lang/String;
/*     */     //   208: astore #6
/*     */     //   210: new com/avaje/ebean/text/TextException
/*     */     //   213: dup
/*     */     //   214: aload #6
/*     */     //   216: invokespecial <init> : (Ljava/lang/String;)V
/*     */     //   219: athrow
/*     */     //   220: aload_0
/*     */     //   221: iload #4
/*     */     //   223: aload #5
/*     */     //   225: invokevirtual buildBeanFromLineContent : (I[Ljava/lang/String;)Ljava/lang/Object;
/*     */     //   228: astore #6
/*     */     //   230: aload_2
/*     */     //   231: iload #4
/*     */     //   233: aload #5
/*     */     //   235: aload #6
/*     */     //   237: invokeinterface processBean : (I[Ljava/lang/String;Ljava/lang/Object;)V
/*     */     //   242: goto -> 84
/*     */     //   245: aload_2
/*     */     //   246: iload #4
/*     */     //   248: invokeinterface end : (I)V
/*     */     //   253: goto -> 271
/*     */     //   256: astore #5
/*     */     //   258: aload_2
/*     */     //   259: iload #4
/*     */     //   261: aload #5
/*     */     //   263: invokeinterface endWithError : (ILjava/lang/Exception;)V
/*     */     //   268: aload #5
/*     */     //   270: athrow
/*     */     //   271: return
/*     */     // Line number table:
/*     */     //   Java source line number -> byte code offset
/*     */     //   #200	-> 0
/*     */     //   #201	-> 4
/*     */     //   #203	-> 14
/*     */     //   #204	-> 18
/*     */     //   #207	-> 28
/*     */     //   #209	-> 37
/*     */     //   #211	-> 47
/*     */     //   #213	-> 50
/*     */     //   #214	-> 57
/*     */     //   #215	-> 63
/*     */     //   #216	-> 70
/*     */     //   #218	-> 76
/*     */     //   #223	-> 84
/*     */     //   #224	-> 87
/*     */     //   #225	-> 93
/*     */     //   #226	-> 98
/*     */     //   #227	-> 101
/*     */     //   #230	-> 104
/*     */     //   #232	-> 117
/*     */     //   #234	-> 132
/*     */     //   #236	-> 210
/*     */     //   #239	-> 220
/*     */     //   #241	-> 230
/*     */     //   #244	-> 242
/*     */     //   #246	-> 245
/*     */     //   #253	-> 253
/*     */     //   #248	-> 256
/*     */     //   #251	-> 258
/*     */     //   #252	-> 268
/*     */     //   #254	-> 271
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	descriptor
/*     */     //   63	21	5	line	[Ljava/lang/String;
/*     */     //   210	10	6	msg	Ljava/lang/String;
/*     */     //   230	12	6	bean	Ljava/lang/Object;
/*     */     //   93	149	5	line	[Ljava/lang/String;
/*     */     //   258	13	5	e	Ljava/lang/Exception;
/*     */     //   0	272	0	this	Lcom/avaje/ebeaninternal/server/text/csv/TCsvReader;
/*     */     //   0	272	1	reader	Ljava/io/Reader;
/*     */     //   0	272	2	callback	Lcom/avaje/ebean/text/csv/CsvCallback;
/*     */     //   37	235	3	utilReader	Lcom/avaje/ebeaninternal/server/text/csv/CsvUtilReader;
/*     */     //   50	222	4	row	I
/*     */     // Local variable type table:
/*     */     //   start	length	slot	name	signature
/*     */     //   230	12	6	bean	TT;
/*     */     //   0	272	0	this	Lcom/avaje/ebeaninternal/server/text/csv/TCsvReader<TT;>;
/*     */     //   0	272	2	callback	Lcom/avaje/ebean/text/csv/CsvCallback<TT;>;
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   84	253	256	java/lang/Exception
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
/*     */   
/*     */   private void addPropertiesFromHeader(String[] line) {
/* 257 */     for (int i = 0; i < line.length; i++) {
/* 258 */       ElPropertyValue elProp = this.descriptor.getElGetValue(line[i]);
/* 259 */       if (elProp == null) {
/* 260 */         throw new TextException("Property [" + line[i] + "] not found");
/*     */       }
/*     */       
/* 263 */       if (92 == elProp.getJdbcType()) {
/* 264 */         addProperty(line[i], (StringParser)TIME_PARSER);
/*     */       }
/* 266 */       else if (isDateTimeType(elProp.getJdbcType())) {
/* 267 */         addDateTime(line[i], null, null);
/*     */       }
/* 269 */       else if (elProp.isAssocProperty()) {
/* 270 */         BeanPropertyAssocOne<?> assocOne = (BeanPropertyAssocOne)elProp.getBeanProperty();
/* 271 */         String idProp = assocOne.getBeanDescriptor().getIdBinder().getIdProperty();
/* 272 */         addReference(line[i] + "." + idProp);
/*     */       } else {
/* 274 */         addProperty(line[i]);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean isDateTimeType(int t) {
/* 280 */     if (t == 93 || t == 91 || t == 92) {
/* 281 */       return true;
/*     */     }
/* 283 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected T buildBeanFromLineContent(int row, String[] line) {
/*     */     try {
/* 290 */       EntityBean entityBean = this.descriptor.createEntityBean();
/* 291 */       EntityBean entityBean1 = entityBean;
/*     */       
/* 293 */       int columnPos = 0;
/* 294 */       for (; columnPos < line.length; columnPos++) {
/* 295 */         convertAndSetColumn(columnPos, line[columnPos], entityBean);
/*     */       }
/*     */       
/* 298 */       return (T)entityBean1;
/*     */     }
/* 300 */     catch (RuntimeException e) {
/* 301 */       String msg = "Error at line: " + row + " line[" + Arrays.toString((Object[])line) + "]";
/* 302 */       throw new RuntimeException(msg, e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void convertAndSetColumn(int columnPos, String strValue, Object bean) {
/* 308 */     strValue = strValue.trim();
/*     */     
/* 310 */     if (strValue.length() == 0 && this.treatEmptyStringAsNull) {
/*     */       return;
/*     */     }
/*     */     
/* 314 */     CsvColumn c = this.columnList.get(columnPos);
/* 315 */     c.convertAndSet(strValue, bean);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class CsvColumn
/*     */   {
/*     */     private final ElPropertyValue elProp;
/*     */     
/*     */     private final StringParser parser;
/*     */     
/*     */     private final boolean ignore;
/*     */     
/*     */     private final boolean reference;
/*     */ 
/*     */     
/*     */     private CsvColumn() {
/* 332 */       this.elProp = null;
/* 333 */       this.parser = null;
/* 334 */       this.reference = false;
/* 335 */       this.ignore = true;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public CsvColumn(ElPropertyValue elProp, StringParser parser, boolean reference) {
/* 342 */       this.elProp = elProp;
/* 343 */       this.parser = parser;
/* 344 */       this.reference = reference;
/* 345 */       this.ignore = false;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void convertAndSet(String strValue, Object bean) {
/* 353 */       if (!this.ignore) {
/* 354 */         Object value = this.parser.parse(strValue);
/* 355 */         this.elProp.elSetValue(bean, value, true, this.reference);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private static class DateTimeParser
/*     */     implements StringParser
/*     */   {
/*     */     private final DateFormat dateFormat;
/*     */     
/*     */     private final ElPropertyValue elProp;
/*     */     
/*     */     private final String format;
/*     */ 
/*     */     
/*     */     DateTimeParser(DateFormat dateFormat, String format, ElPropertyValue elProp) {
/* 372 */       this.dateFormat = dateFormat;
/* 373 */       this.elProp = elProp;
/* 374 */       this.format = format;
/*     */     }
/*     */     
/*     */     public Object parse(String value) {
/*     */       try {
/* 379 */         Date dt = this.dateFormat.parse(value);
/* 380 */         return this.elProp.parseDateTime(dt.getTime());
/*     */       }
/* 382 */       catch (ParseException e) {
/* 383 */         throw new TextException("Error parsing [" + value + "] using format[" + this.format + "]", e);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\text\csv\TCsvReader.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */