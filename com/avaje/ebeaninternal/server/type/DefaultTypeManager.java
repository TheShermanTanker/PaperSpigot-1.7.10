/*     */ package com.avaje.ebeaninternal.server.type;
/*     */ 
/*     */ import com.avaje.ebean.annotation.EnumMapping;
/*     */ import com.avaje.ebean.annotation.EnumValue;
/*     */ import com.avaje.ebean.config.CompoundType;
/*     */ import com.avaje.ebean.config.CompoundTypeProperty;
/*     */ import com.avaje.ebean.config.ScalarTypeConverter;
/*     */ import com.avaje.ebean.config.ServerConfig;
/*     */ import com.avaje.ebeaninternal.api.ClassUtil;
/*     */ import com.avaje.ebeaninternal.server.core.BootupClasses;
/*     */ import com.avaje.ebeaninternal.server.lib.util.StringHelper;
/*     */ import com.avaje.ebeaninternal.server.type.reflect.CheckImmutable;
/*     */ import com.avaje.ebeaninternal.server.type.reflect.CheckImmutableResponse;
/*     */ import com.avaje.ebeaninternal.server.type.reflect.ImmutableMeta;
/*     */ import com.avaje.ebeaninternal.server.type.reflect.ImmutableMetaFactory;
/*     */ import com.avaje.ebeaninternal.server.type.reflect.KnownImmutable;
/*     */ import com.avaje.ebeaninternal.server.type.reflect.ReflectionBasedCompoundType;
/*     */ import com.avaje.ebeaninternal.server.type.reflect.ReflectionBasedCompoundTypeProperty;
/*     */ import com.avaje.ebeaninternal.server.type.reflect.ReflectionBasedTypeBuilder;
/*     */ import java.lang.reflect.Field;
/*     */ import java.math.BigDecimal;
/*     */ import java.math.BigInteger;
/*     */ import java.net.URI;
/*     */ import java.net.URL;
/*     */ import java.sql.Date;
/*     */ import java.sql.Time;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Calendar;
/*     */ import java.util.Currency;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.TimeZone;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import org.joda.time.DateMidnight;
/*     */ import org.joda.time.DateTime;
/*     */ import org.joda.time.LocalDate;
/*     */ import org.joda.time.LocalDateTime;
/*     */ import org.joda.time.LocalTime;
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
/*     */ public final class DefaultTypeManager
/*     */   implements TypeManager, KnownImmutable
/*     */ {
/*  78 */   private static final Logger logger = Logger.getLogger(DefaultTypeManager.class.getName());
/*     */   
/*     */   private final ConcurrentHashMap<Class<?>, CtCompoundType<?>> compoundTypeMap;
/*     */   
/*     */   private final ConcurrentHashMap<Class<?>, ScalarType<?>> typeMap;
/*     */   
/*     */   private final ConcurrentHashMap<Integer, ScalarType<?>> nativeMap;
/*     */   
/*     */   private final DefaultTypeFactory extraTypeFactory;
/*     */   
/*  88 */   private final ScalarType<?> charType = new ScalarTypeChar();
/*     */   
/*  90 */   private final ScalarType<?> charArrayType = new ScalarTypeCharArray();
/*     */   
/*  92 */   private final ScalarType<?> longVarcharType = new ScalarTypeLongVarchar();
/*     */   
/*  94 */   private final ScalarType<?> clobType = new ScalarTypeClob();
/*     */   
/*  96 */   private final ScalarType<?> byteType = new ScalarTypeByte();
/*     */   
/*  98 */   private final ScalarType<?> binaryType = new ScalarTypeBytesBinary();
/*     */   
/* 100 */   private final ScalarType<?> blobType = new ScalarTypeBytesBlob();
/*     */   
/* 102 */   private final ScalarType<?> varbinaryType = new ScalarTypeBytesVarbinary();
/*     */   
/* 104 */   private final ScalarType<?> longVarbinaryType = new ScalarTypeBytesLongVarbinary();
/*     */   
/* 106 */   private final ScalarType<?> shortType = new ScalarTypeShort();
/*     */   
/* 108 */   private final ScalarType<?> integerType = new ScalarTypeInteger();
/*     */   
/* 110 */   private final ScalarType<?> longType = new ScalarTypeLong();
/*     */   
/* 112 */   private final ScalarType<?> doubleType = new ScalarTypeDouble();
/*     */   
/* 114 */   private final ScalarType<?> floatType = new ScalarTypeFloat();
/*     */   
/* 116 */   private final ScalarType<?> bigDecimalType = new ScalarTypeBigDecimal();
/*     */   
/* 118 */   private final ScalarType<?> timeType = new ScalarTypeTime();
/*     */   
/* 120 */   private final ScalarType<?> dateType = new ScalarTypeDate();
/*     */   
/* 122 */   private final ScalarType<?> timestampType = new ScalarTypeTimestamp();
/*     */   
/* 124 */   private final ScalarType<?> uuidType = new ScalarTypeUUID();
/* 125 */   private final ScalarType<?> urlType = new ScalarTypeURL();
/* 126 */   private final ScalarType<?> uriType = new ScalarTypeURI();
/* 127 */   private final ScalarType<?> localeType = new ScalarTypeLocale();
/* 128 */   private final ScalarType<?> currencyType = new ScalarTypeCurrency();
/* 129 */   private final ScalarType<?> timeZoneType = new ScalarTypeTimeZone();
/*     */   
/* 131 */   private final ScalarType<?> stringType = new ScalarTypeString();
/*     */   
/* 133 */   private final ScalarType<?> classType = new ScalarTypeClass();
/*     */   
/* 135 */   private final ScalarTypeLongToTimestamp longToTimestamp = new ScalarTypeLongToTimestamp();
/*     */   
/* 137 */   private final List<ScalarType<?>> customScalarTypes = new ArrayList<ScalarType<?>>();
/*     */   
/*     */   private final CheckImmutable checkImmutable;
/*     */   
/* 141 */   private final ImmutableMetaFactory immutableMetaFactory = new ImmutableMetaFactory();
/*     */ 
/*     */ 
/*     */   
/*     */   private final ReflectionBasedTypeBuilder reflectScalarBuilder;
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultTypeManager(ServerConfig config, BootupClasses bootupClasses) {
/* 150 */     int clobType = (config == null) ? 2005 : config.getDatabasePlatform().getClobDbType();
/* 151 */     int blobType = (config == null) ? 2004 : config.getDatabasePlatform().getBlobDbType();
/*     */     
/* 153 */     this.checkImmutable = new CheckImmutable(this);
/* 154 */     this.reflectScalarBuilder = new ReflectionBasedTypeBuilder(this);
/*     */     
/* 156 */     this.compoundTypeMap = new ConcurrentHashMap<Class<?>, CtCompoundType<?>>();
/* 157 */     this.typeMap = new ConcurrentHashMap<Class<?>, ScalarType<?>>();
/* 158 */     this.nativeMap = new ConcurrentHashMap<Integer, ScalarType<?>>();
/*     */     
/* 160 */     this.extraTypeFactory = new DefaultTypeFactory(config);
/*     */     
/* 162 */     initialiseStandard(clobType, blobType);
/* 163 */     initialiseJodaTypes();
/*     */     
/* 165 */     if (bootupClasses != null) {
/* 166 */       initialiseCustomScalarTypes(bootupClasses);
/* 167 */       initialiseScalarConverters(bootupClasses);
/* 168 */       initialiseCompoundTypes(bootupClasses);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isKnownImmutable(Class<?> cls) {
/* 174 */     if (cls == null)
/*     */     {
/* 176 */       return true;
/*     */     }
/*     */     
/* 179 */     if (cls.isPrimitive() || Object.class.equals(cls)) {
/* 180 */       return true;
/*     */     }
/*     */     
/* 183 */     ScalarDataReader<?> scalarDataReader = getScalarDataReader(cls);
/* 184 */     return (scalarDataReader != null);
/*     */   }
/*     */   
/*     */   public CheckImmutableResponse checkImmutable(Class<?> cls) {
/* 188 */     return this.checkImmutable.checkImmutable(cls);
/*     */   }
/*     */   
/*     */   private ScalarType<?> register(ScalarType<?> st) {
/* 192 */     add(st);
/* 193 */     logger.fine("Registering ScalarType for " + st.getType() + " implemented using reflection");
/* 194 */     return st;
/*     */   }
/*     */ 
/*     */   
/*     */   public ScalarDataReader<?> recursiveCreateScalarDataReader(Class<?> cls) {
/* 199 */     ScalarDataReader<?> scalarReader = getScalarDataReader(cls);
/* 200 */     if (scalarReader != null) {
/* 201 */       return scalarReader;
/*     */     }
/*     */     
/* 204 */     ImmutableMeta meta = this.immutableMetaFactory.createImmutableMeta(cls);
/*     */     
/* 206 */     if (!meta.isCompoundType()) {
/* 207 */       return register(this.reflectScalarBuilder.buildScalarType(meta));
/*     */     }
/*     */     
/* 210 */     ReflectionBasedCompoundType compoundType = this.reflectScalarBuilder.buildCompound(meta);
/* 211 */     Class<?> compoundTypeClass = compoundType.getCompoundType();
/*     */     
/* 213 */     return createCompoundScalarDataReader(compoundTypeClass, (CompoundType<?>)compoundType, " using reflection");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ScalarType<?> recursiveCreateScalarTypes(Class<?> cls) {
/* 219 */     ScalarType<?> scalarType = getScalarType(cls);
/* 220 */     if (scalarType != null) {
/* 221 */       return scalarType;
/*     */     }
/*     */     
/* 224 */     ImmutableMeta meta = this.immutableMetaFactory.createImmutableMeta(cls);
/*     */     
/* 226 */     if (!meta.isCompoundType()) {
/* 227 */       return register(this.reflectScalarBuilder.buildScalarType(meta));
/*     */     }
/*     */     
/* 230 */     throw new RuntimeException("Not allowed compound types here");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(ScalarType<?> scalarType) {
/* 238 */     this.typeMap.put(scalarType.getType(), scalarType);
/* 239 */     logAdd(scalarType);
/*     */   }
/*     */   
/*     */   protected void logAdd(ScalarType<?> scalarType) {
/* 243 */     if (logger.isLoggable(Level.FINE)) {
/* 244 */       String msg = "ScalarType register [" + scalarType.getClass().getName() + "]";
/* 245 */       msg = msg + " for [" + scalarType.getType().getName() + "]";
/* 246 */       logger.fine(msg);
/*     */     } 
/*     */   }
/*     */   
/*     */   public CtCompoundType<?> getCompoundType(Class<?> type) {
/* 251 */     return this.compoundTypeMap.get(type);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ScalarType<?> getScalarType(int jdbcType) {
/* 258 */     return this.nativeMap.get(Integer.valueOf(jdbcType));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> ScalarType<T> getScalarType(Class<T> type) {
/* 266 */     return (ScalarType<T>)this.typeMap.get(type);
/*     */   }
/*     */ 
/*     */   
/*     */   public ScalarDataReader<?> getScalarDataReader(Class<?> propertyType, int sqlType) {
/* 271 */     if (sqlType == 0) {
/* 272 */       return recursiveCreateScalarDataReader(propertyType);
/*     */     }
/*     */     
/* 275 */     for (int i = 0; i < this.customScalarTypes.size(); i++) {
/* 276 */       ScalarType<?> customScalarType = this.customScalarTypes.get(i);
/*     */       
/* 278 */       if (sqlType == customScalarType.getJdbcType() && propertyType.equals(customScalarType.getType()))
/*     */       {
/* 280 */         return customScalarType;
/*     */       }
/*     */     } 
/*     */     
/* 284 */     String msg = "Unable to find a custom ScalarType with type [" + propertyType + "] and java.sql.Type [" + sqlType + "]";
/* 285 */     throw new RuntimeException(msg);
/*     */   }
/*     */   
/*     */   public ScalarDataReader<?> getScalarDataReader(Class<?> type) {
/* 289 */     ScalarDataReader<?> reader = this.typeMap.get(type);
/* 290 */     if (reader == null) {
/* 291 */       reader = this.compoundTypeMap.get(type);
/*     */     }
/* 293 */     return reader;
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
/*     */   public <T> ScalarType<T> getScalarType(Class<T> type, int jdbcType) {
/* 309 */     ScalarType<?> scalarType = getLobTypes(jdbcType);
/* 310 */     if (scalarType != null)
/*     */     {
/* 312 */       return (ScalarType)scalarType;
/*     */     }
/*     */     
/* 315 */     scalarType = this.typeMap.get(type);
/* 316 */     if (scalarType != null && (
/* 317 */       jdbcType == 0 || scalarType.getJdbcType() == jdbcType))
/*     */     {
/* 319 */       return (ScalarType)scalarType;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 327 */     if (type.equals(Date.class)) {
/* 328 */       return (ScalarType)this.extraTypeFactory.createUtilDate(jdbcType);
/*     */     }
/*     */     
/* 331 */     if (type.equals(Calendar.class)) {
/* 332 */       return (ScalarType)this.extraTypeFactory.createCalendar(jdbcType);
/*     */     }
/*     */     
/* 335 */     String msg = "Unmatched ScalarType for " + type + " jdbcType:" + jdbcType;
/* 336 */     throw new RuntimeException(msg);
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
/*     */   private ScalarType<?> getLobTypes(int jdbcType) {
/* 349 */     return getScalarType(jdbcType);
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
/*     */   public Object convert(Object value, int toJdbcType) {
/* 361 */     if (value == null) {
/* 362 */       return null;
/*     */     }
/* 364 */     ScalarType<?> type = this.nativeMap.get(Integer.valueOf(toJdbcType));
/* 365 */     if (type != null) {
/* 366 */       return type.toJdbcType(value);
/*     */     }
/* 368 */     return value;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isIntegerType(String s) {
/*     */     try {
/* 374 */       Integer.parseInt(s);
/* 375 */       return true;
/* 376 */     } catch (NumberFormatException e) {
/* 377 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ScalarType<?> createEnumScalarType2(Class<?> enumType) {
/* 389 */     boolean integerType = true;
/*     */     
/* 391 */     Map<String, String> nameValueMap = new HashMap<String, String>();
/*     */     
/* 393 */     Field[] fields = enumType.getDeclaredFields();
/* 394 */     for (int i = 0; i < fields.length; i++) {
/* 395 */       EnumValue enumValue = fields[i].<EnumValue>getAnnotation(EnumValue.class);
/* 396 */       if (enumValue != null) {
/* 397 */         nameValueMap.put(fields[i].getName(), enumValue.value());
/* 398 */         if (integerType && !isIntegerType(enumValue.value()))
/*     */         {
/* 400 */           integerType = false;
/*     */         }
/*     */       } 
/*     */     } 
/* 404 */     if (nameValueMap.isEmpty())
/*     */     {
/* 406 */       return null;
/*     */     }
/*     */     
/* 409 */     return createEnumScalarType(enumType, nameValueMap, integerType, 0);
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
/*     */   public ScalarType<?> createEnumScalarType(Class<?> enumType) {
/* 424 */     EnumMapping enumMapping = enumType.<EnumMapping>getAnnotation(EnumMapping.class);
/* 425 */     if (enumMapping == null)
/*     */     {
/* 427 */       return createEnumScalarType2(enumType);
/*     */     }
/*     */     
/* 430 */     String nameValuePairs = enumMapping.nameValuePairs();
/* 431 */     boolean integerType = enumMapping.integerType();
/* 432 */     int dbColumnLength = enumMapping.length();
/*     */     
/* 434 */     Map<String, String> nameValueMap = StringHelper.delimitedToMap(nameValuePairs, ",", "=");
/*     */     
/* 436 */     return createEnumScalarType(enumType, nameValueMap, integerType, dbColumnLength);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private ScalarType<?> createEnumScalarType(Class<Enum> enumType, Map<String, String> nameValueMap, boolean integerType, int dbColumnLength) {
/* 446 */     EnumToDbValueMap<?> beanDbMap = EnumToDbValueMap.create(integerType);
/*     */     
/* 448 */     int maxValueLen = 0;
/*     */     
/* 450 */     Iterator<Map.Entry> it = nameValueMap.entrySet().iterator();
/* 451 */     while (it.hasNext()) {
/* 452 */       Map.Entry entry = it.next();
/* 453 */       String name = (String)entry.getKey();
/* 454 */       String value = (String)entry.getValue();
/*     */       
/* 456 */       maxValueLen = Math.max(maxValueLen, value.length());
/*     */       
/* 458 */       Object enumValue = Enum.valueOf(enumType, name.trim());
/* 459 */       beanDbMap.add(enumValue, value.trim());
/*     */     } 
/*     */     
/* 462 */     if (dbColumnLength == 0 && !integerType) {
/* 463 */       dbColumnLength = maxValueLen;
/*     */     }
/*     */     
/* 466 */     return new ScalarTypeEnumWithMapping(beanDbMap, enumType, dbColumnLength);
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
/*     */   protected void initialiseCustomScalarTypes(BootupClasses bootupClasses) {
/* 479 */     this.customScalarTypes.add(this.longToTimestamp);
/*     */     
/* 481 */     List<Class<?>> foundTypes = bootupClasses.getScalarTypes();
/*     */     
/* 483 */     for (int i = 0; i < foundTypes.size(); i++) {
/* 484 */       Class<?> cls = foundTypes.get(i);
/*     */       
/*     */       try {
/* 487 */         ScalarType<?> scalarType = (ScalarType)cls.newInstance();
/* 488 */         add(scalarType);
/*     */         
/* 490 */         this.customScalarTypes.add(scalarType);
/*     */       }
/* 492 */       catch (Exception e) {
/* 493 */         String msg = "Error loading ScalarType [" + cls.getName() + "]";
/* 494 */         logger.log(Level.SEVERE, msg, e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initialiseScalarConverters(BootupClasses bootupClasses) {
/* 502 */     List<Class<?>> foundTypes = bootupClasses.getScalarConverters();
/*     */     
/* 504 */     for (int i = 0; i < foundTypes.size(); i++) {
/* 505 */       Class<?> cls = foundTypes.get(i);
/*     */       
/*     */       try {
/* 508 */         Class<?>[] paramTypes = TypeReflectHelper.getParams(cls, ScalarTypeConverter.class);
/* 509 */         if (paramTypes.length != 2) {
/* 510 */           throw new IllegalStateException("Expected 2 generics paramtypes but got: " + Arrays.toString(paramTypes));
/*     */         }
/*     */         
/* 513 */         Class<?> logicalType = paramTypes[0];
/* 514 */         Class<?> persistType = paramTypes[1];
/*     */         
/* 516 */         ScalarType<?> wrappedType = getScalarType(persistType);
/* 517 */         if (wrappedType == null) {
/* 518 */           throw new IllegalStateException("Could not find ScalarType for: " + paramTypes[1]);
/*     */         }
/*     */         
/* 521 */         ScalarTypeConverter<?, ?> converter = (ScalarTypeConverter)cls.newInstance();
/* 522 */         ScalarTypeWrapper<Object, Object> stw = new ScalarTypeWrapper<Object, Object>(logicalType, wrappedType, converter);
/*     */         
/* 524 */         logger.fine("Register ScalarTypeWrapper from " + logicalType + " -> " + persistType + " using:" + cls);
/*     */         
/* 526 */         add(stw);
/*     */       }
/* 528 */       catch (Exception e) {
/* 529 */         String msg = "Error loading ScalarType [" + cls.getName() + "]";
/* 530 */         logger.log(Level.SEVERE, msg, e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initialiseCompoundTypes(BootupClasses bootupClasses) {
/* 538 */     ArrayList<Class<?>> compoundTypes = bootupClasses.getCompoundTypes();
/* 539 */     for (int j = 0; j < compoundTypes.size(); j++) {
/*     */       
/* 541 */       Class<?> type = compoundTypes.get(j);
/*     */       
/*     */       try {
/* 544 */         Class<?>[] paramTypes = TypeReflectHelper.getParams(type, CompoundType.class);
/* 545 */         if (paramTypes.length != 1) {
/* 546 */           throw new RuntimeException("Expecting 1 generic paramter type but got " + Arrays.toString(paramTypes) + " for " + type);
/*     */         }
/*     */         
/* 549 */         Class<?> compoundTypeClass = paramTypes[0];
/*     */         
/* 551 */         CompoundType<?> compoundType = (CompoundType)type.newInstance();
/* 552 */         createCompoundScalarDataReader(compoundTypeClass, compoundType, "");
/*     */       }
/* 554 */       catch (Exception e) {
/* 555 */         String msg = "Error initialising component " + type;
/* 556 */         throw new RuntimeException(msg, e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected CtCompoundType createCompoundScalarDataReader(Class<?> compoundTypeClass, CompoundType<?> compoundType, String info) {
/* 564 */     CtCompoundType<?> ctCompoundType = this.compoundTypeMap.get(compoundTypeClass);
/* 565 */     if (ctCompoundType != null) {
/* 566 */       logger.info("Already registered compound type " + compoundTypeClass);
/* 567 */       return ctCompoundType;
/*     */     } 
/*     */     
/* 570 */     CompoundTypeProperty[] arrayOfCompoundTypeProperty = compoundType.getProperties();
/*     */     
/* 572 */     ScalarDataReader[] dataReaders = new ScalarDataReader[arrayOfCompoundTypeProperty.length];
/*     */     
/* 574 */     for (int i = 0; i < arrayOfCompoundTypeProperty.length; i++) {
/*     */       
/* 576 */       Class<?> propertyType = getCompoundPropertyType(arrayOfCompoundTypeProperty[i]);
/*     */       
/* 578 */       ScalarDataReader<?> scalarDataReader = getScalarDataReader(propertyType, arrayOfCompoundTypeProperty[i].getDbType());
/* 579 */       if (scalarDataReader == null) {
/* 580 */         throw new RuntimeException("Could not find ScalarDataReader for " + propertyType);
/*     */       }
/*     */       
/* 583 */       dataReaders[i] = scalarDataReader;
/*     */     } 
/*     */     
/* 586 */     CtCompoundType<?> ctType = new CtCompoundType(compoundTypeClass, compoundType, dataReaders);
/*     */     
/* 588 */     logger.fine("Registering CompoundType " + compoundTypeClass + " " + info);
/* 589 */     this.compoundTypeMap.put(compoundTypeClass, ctType);
/*     */     
/* 591 */     return ctType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Class<?> getCompoundPropertyType(CompoundTypeProperty<?, ?> prop) {
/* 599 */     if (prop instanceof ReflectionBasedCompoundTypeProperty) {
/* 600 */       return ((ReflectionBasedCompoundTypeProperty)prop).getPropertyType();
/*     */     }
/*     */ 
/*     */     
/* 604 */     Class<?>[] propParamTypes = TypeReflectHelper.getParams(prop.getClass(), CompoundTypeProperty.class);
/* 605 */     if (propParamTypes.length != 2) {
/* 606 */       throw new RuntimeException("Expecting 2 generic paramter types but got " + Arrays.toString(propParamTypes) + " for " + prop.getClass());
/*     */     }
/*     */ 
/*     */     
/* 610 */     return propParamTypes[1];
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initialiseJodaTypes() {
/* 620 */     if (ClassUtil.isPresent("org.joda.time.LocalDateTime", getClass())) {
/*     */       
/* 622 */       logger.fine("Registering Joda data types");
/* 623 */       this.typeMap.put(LocalDateTime.class, new ScalarTypeJodaLocalDateTime());
/* 624 */       this.typeMap.put(LocalDate.class, new ScalarTypeJodaLocalDate());
/* 625 */       this.typeMap.put(LocalTime.class, new ScalarTypeJodaLocalTime());
/* 626 */       this.typeMap.put(DateTime.class, new ScalarTypeJodaDateTime());
/* 627 */       this.typeMap.put(DateMidnight.class, new ScalarTypeJodaDateMidnight());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initialiseStandard(int platformClobType, int platformBlobType) {
/* 637 */     ScalarType<?> utilDateType = this.extraTypeFactory.createUtilDate();
/* 638 */     this.typeMap.put(Date.class, utilDateType);
/*     */     
/* 640 */     ScalarType<?> calType = this.extraTypeFactory.createCalendar();
/* 641 */     this.typeMap.put(Calendar.class, calType);
/*     */     
/* 643 */     ScalarType<?> mathBigIntType = this.extraTypeFactory.createMathBigInteger();
/* 644 */     this.typeMap.put(BigInteger.class, mathBigIntType);
/*     */     
/* 646 */     ScalarType<?> booleanType = this.extraTypeFactory.createBoolean();
/* 647 */     this.typeMap.put(Boolean.class, booleanType);
/* 648 */     this.typeMap.put(boolean.class, booleanType);
/*     */ 
/*     */     
/* 651 */     this.nativeMap.put(Integer.valueOf(16), booleanType);
/* 652 */     if (booleanType.getJdbcType() == -7)
/*     */     {
/* 654 */       this.nativeMap.put(Integer.valueOf(-7), booleanType);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 662 */     this.typeMap.put(Locale.class, this.localeType);
/* 663 */     this.typeMap.put(Currency.class, this.currencyType);
/* 664 */     this.typeMap.put(TimeZone.class, this.timeZoneType);
/* 665 */     this.typeMap.put(UUID.class, this.uuidType);
/* 666 */     this.typeMap.put(URL.class, this.urlType);
/* 667 */     this.typeMap.put(URI.class, this.uriType);
/*     */ 
/*     */     
/* 670 */     this.typeMap.put(char[].class, this.charArrayType);
/* 671 */     this.typeMap.put(char.class, this.charType);
/* 672 */     this.typeMap.put(String.class, this.stringType);
/* 673 */     this.nativeMap.put(Integer.valueOf(12), this.stringType);
/* 674 */     this.nativeMap.put(Integer.valueOf(1), this.stringType);
/* 675 */     this.nativeMap.put(Integer.valueOf(-1), this.longVarcharType);
/*     */ 
/*     */     
/* 678 */     this.typeMap.put(Class.class, this.classType);
/*     */     
/* 680 */     if (platformClobType == 2005) {
/* 681 */       this.nativeMap.put(Integer.valueOf(2005), this.clobType);
/*     */     } else {
/*     */       
/* 684 */       ScalarType<?> platClobScalarType = this.nativeMap.get(Integer.valueOf(platformClobType));
/* 685 */       if (platClobScalarType == null) {
/* 686 */         throw new IllegalArgumentException("Type for dbPlatform clobType [" + this.clobType + "] not found.");
/*     */       }
/* 688 */       this.nativeMap.put(Integer.valueOf(2005), platClobScalarType);
/*     */     } 
/*     */ 
/*     */     
/* 692 */     this.typeMap.put(byte[].class, this.varbinaryType);
/* 693 */     this.nativeMap.put(Integer.valueOf(-2), this.binaryType);
/* 694 */     this.nativeMap.put(Integer.valueOf(-3), this.varbinaryType);
/* 695 */     this.nativeMap.put(Integer.valueOf(-4), this.longVarbinaryType);
/*     */     
/* 697 */     if (platformBlobType == 2004) {
/* 698 */       this.nativeMap.put(Integer.valueOf(2004), this.blobType);
/*     */     } else {
/*     */       
/* 701 */       ScalarType<?> platBlobScalarType = this.nativeMap.get(Integer.valueOf(platformBlobType));
/* 702 */       if (platBlobScalarType == null) {
/* 703 */         throw new IllegalArgumentException("Type for dbPlatform blobType [" + this.blobType + "] not found.");
/*     */       }
/* 705 */       this.nativeMap.put(Integer.valueOf(2004), platBlobScalarType);
/*     */     } 
/*     */ 
/*     */     
/* 709 */     this.typeMap.put(Byte.class, this.byteType);
/* 710 */     this.typeMap.put(byte.class, this.byteType);
/* 711 */     this.nativeMap.put(Integer.valueOf(-6), this.byteType);
/*     */     
/* 713 */     this.typeMap.put(Short.class, this.shortType);
/* 714 */     this.typeMap.put(short.class, this.shortType);
/* 715 */     this.nativeMap.put(Integer.valueOf(5), this.shortType);
/*     */     
/* 717 */     this.typeMap.put(Integer.class, this.integerType);
/* 718 */     this.typeMap.put(int.class, this.integerType);
/* 719 */     this.nativeMap.put(Integer.valueOf(4), this.integerType);
/*     */     
/* 721 */     this.typeMap.put(Long.class, this.longType);
/* 722 */     this.typeMap.put(long.class, this.longType);
/* 723 */     this.nativeMap.put(Integer.valueOf(-5), this.longType);
/*     */     
/* 725 */     this.typeMap.put(Double.class, this.doubleType);
/* 726 */     this.typeMap.put(double.class, this.doubleType);
/* 727 */     this.nativeMap.put(Integer.valueOf(6), this.doubleType);
/* 728 */     this.nativeMap.put(Integer.valueOf(8), this.doubleType);
/*     */     
/* 730 */     this.typeMap.put(Float.class, this.floatType);
/* 731 */     this.typeMap.put(float.class, this.floatType);
/* 732 */     this.nativeMap.put(Integer.valueOf(7), this.floatType);
/*     */     
/* 734 */     this.typeMap.put(BigDecimal.class, this.bigDecimalType);
/* 735 */     this.nativeMap.put(Integer.valueOf(3), this.bigDecimalType);
/* 736 */     this.nativeMap.put(Integer.valueOf(2), this.bigDecimalType);
/*     */ 
/*     */     
/* 739 */     this.typeMap.put(Time.class, this.timeType);
/* 740 */     this.nativeMap.put(Integer.valueOf(92), this.timeType);
/* 741 */     this.typeMap.put(Date.class, this.dateType);
/* 742 */     this.nativeMap.put(Integer.valueOf(91), this.dateType);
/* 743 */     this.typeMap.put(Timestamp.class, this.timestampType);
/* 744 */     this.nativeMap.put(Integer.valueOf(93), this.timestampType);
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\avaje\ebeaninternal\server\type\DefaultTypeManager.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */