/*      */ package com.mysql.jdbc;
/*      */ 
/*      */ import com.mysql.jdbc.log.Log;
/*      */ import com.mysql.jdbc.log.StandardLogger;
/*      */ import java.io.Serializable;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.lang.reflect.Field;
/*      */ import java.sql.DriverPropertyInfo;
/*      */ import java.sql.SQLException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.Map;
/*      */ import java.util.Properties;
/*      */ import java.util.TreeMap;
/*      */ import javax.naming.RefAddr;
/*      */ import javax.naming.Reference;
/*      */ import javax.naming.StringRefAddr;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class ConnectionPropertiesImpl
/*      */   implements Serializable, ConnectionProperties
/*      */ {
/*      */   private static final long serialVersionUID = 4257801713007640580L;
/*      */   
/*      */   class BooleanConnectionProperty
/*      */     extends ConnectionProperty
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 2540132501709159404L;
/*      */     
/*      */     BooleanConnectionProperty(String propertyNameToSet, boolean defaultValueToSet, String descriptionToSet, String sinceVersionToSet, String category, int orderInCategory) {
/*   72 */       super(propertyNameToSet, Boolean.valueOf(defaultValueToSet), null, 0, 0, descriptionToSet, sinceVersionToSet, category, orderInCategory);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     String[] getAllowableValues() {
/*   81 */       return new String[] { "true", "false", "yes", "no" };
/*      */     }
/*      */     
/*      */     boolean getValueAsBoolean() {
/*   85 */       return ((Boolean)this.valueAsObject).booleanValue();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean hasValueConstraints() {
/*   92 */       return true;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void initializeFrom(String extractedValue) throws SQLException {
/*   99 */       if (extractedValue != null) {
/*  100 */         validateStringValues(extractedValue);
/*      */         
/*  102 */         this.valueAsObject = Boolean.valueOf((extractedValue.equalsIgnoreCase("TRUE") || extractedValue.equalsIgnoreCase("YES")));
/*      */       }
/*      */       else {
/*      */         
/*  106 */         this.valueAsObject = this.defaultValue;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean isRangeBased() {
/*  114 */       return false;
/*      */     }
/*      */     
/*      */     void setValue(boolean valueFlag) {
/*  118 */       this.valueAsObject = Boolean.valueOf(valueFlag);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   abstract class ConnectionProperty
/*      */     implements Serializable
/*      */   {
/*      */     String[] allowableValues;
/*      */     
/*      */     String categoryName;
/*      */     
/*      */     Object defaultValue;
/*      */     
/*      */     int lowerBound;
/*      */     
/*      */     int order;
/*      */     
/*      */     String propertyName;
/*      */     
/*      */     String sinceVersion;
/*      */     
/*      */     int upperBound;
/*      */     
/*      */     Object valueAsObject;
/*      */     
/*      */     boolean required;
/*      */     
/*      */     String description;
/*      */ 
/*      */     
/*      */     public ConnectionProperty() {}
/*      */     
/*      */     ConnectionProperty(String propertyNameToSet, Object defaultValueToSet, String[] allowableValuesToSet, int lowerBoundToSet, int upperBoundToSet, String descriptionToSet, String sinceVersionToSet, String category, int orderInCategory) {
/*  152 */       this.description = descriptionToSet;
/*  153 */       this.propertyName = propertyNameToSet;
/*  154 */       this.defaultValue = defaultValueToSet;
/*  155 */       this.valueAsObject = defaultValueToSet;
/*  156 */       this.allowableValues = allowableValuesToSet;
/*  157 */       this.lowerBound = lowerBoundToSet;
/*  158 */       this.upperBound = upperBoundToSet;
/*  159 */       this.required = false;
/*  160 */       this.sinceVersion = sinceVersionToSet;
/*  161 */       this.categoryName = category;
/*  162 */       this.order = orderInCategory;
/*      */     }
/*      */     
/*      */     String[] getAllowableValues() {
/*  166 */       return this.allowableValues;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     String getCategoryName() {
/*  173 */       return this.categoryName;
/*      */     }
/*      */     
/*      */     Object getDefaultValue() {
/*  177 */       return this.defaultValue;
/*      */     }
/*      */     
/*      */     int getLowerBound() {
/*  181 */       return this.lowerBound;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int getOrder() {
/*  188 */       return this.order;
/*      */     }
/*      */     
/*      */     String getPropertyName() {
/*  192 */       return this.propertyName;
/*      */     }
/*      */     
/*      */     int getUpperBound() {
/*  196 */       return this.upperBound;
/*      */     }
/*      */     
/*      */     Object getValueAsObject() {
/*  200 */       return this.valueAsObject;
/*      */     }
/*      */     
/*      */     abstract boolean hasValueConstraints();
/*      */     
/*      */     void initializeFrom(Properties extractFrom) throws SQLException {
/*  206 */       String extractedValue = extractFrom.getProperty(getPropertyName());
/*  207 */       extractFrom.remove(getPropertyName());
/*  208 */       initializeFrom(extractedValue);
/*      */     }
/*      */     
/*      */     void initializeFrom(Reference ref) throws SQLException {
/*  212 */       RefAddr refAddr = ref.get(getPropertyName());
/*      */       
/*  214 */       if (refAddr != null) {
/*  215 */         String refContentAsString = (String)refAddr.getContent();
/*      */         
/*  217 */         initializeFrom(refContentAsString);
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     abstract void initializeFrom(String param1String) throws SQLException;
/*      */ 
/*      */     
/*      */     abstract boolean isRangeBased();
/*      */ 
/*      */     
/*      */     void setCategoryName(String categoryName) {
/*  230 */       this.categoryName = categoryName;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void setOrder(int order) {
/*  238 */       this.order = order;
/*      */     }
/*      */     
/*      */     void setValueAsObject(Object obj) {
/*  242 */       this.valueAsObject = obj;
/*      */     }
/*      */     
/*      */     void storeTo(Reference ref) {
/*  246 */       if (getValueAsObject() != null) {
/*  247 */         ref.add(new StringRefAddr(getPropertyName(), getValueAsObject().toString()));
/*      */       }
/*      */     }
/*      */ 
/*      */     
/*      */     DriverPropertyInfo getAsDriverPropertyInfo() {
/*  253 */       DriverPropertyInfo dpi = new DriverPropertyInfo(this.propertyName, null);
/*  254 */       dpi.choices = getAllowableValues();
/*  255 */       dpi.value = (this.valueAsObject != null) ? this.valueAsObject.toString() : null;
/*  256 */       dpi.required = this.required;
/*  257 */       dpi.description = this.description;
/*      */       
/*  259 */       return dpi;
/*      */     }
/*      */ 
/*      */     
/*      */     void validateStringValues(String valueToValidate) throws SQLException {
/*  264 */       String[] validateAgainst = getAllowableValues();
/*      */       
/*  266 */       if (valueToValidate == null) {
/*      */         return;
/*      */       }
/*      */       
/*  270 */       if (validateAgainst == null || validateAgainst.length == 0) {
/*      */         return;
/*      */       }
/*      */       
/*  274 */       for (int i = 0; i < validateAgainst.length; i++) {
/*  275 */         if (validateAgainst[i] != null && validateAgainst[i].equalsIgnoreCase(valueToValidate)) {
/*      */           return;
/*      */         }
/*      */       } 
/*      */ 
/*      */       
/*  281 */       StringBuffer errorMessageBuf = new StringBuffer();
/*      */       
/*  283 */       errorMessageBuf.append("The connection property '");
/*  284 */       errorMessageBuf.append(getPropertyName());
/*  285 */       errorMessageBuf.append("' only accepts values of the form: ");
/*      */       
/*  287 */       if (validateAgainst.length != 0) {
/*  288 */         errorMessageBuf.append("'");
/*  289 */         errorMessageBuf.append(validateAgainst[0]);
/*  290 */         errorMessageBuf.append("'");
/*      */         
/*  292 */         for (int j = 1; j < validateAgainst.length - 1; j++) {
/*  293 */           errorMessageBuf.append(", ");
/*  294 */           errorMessageBuf.append("'");
/*  295 */           errorMessageBuf.append(validateAgainst[j]);
/*  296 */           errorMessageBuf.append("'");
/*      */         } 
/*      */         
/*  299 */         errorMessageBuf.append(" or '");
/*  300 */         errorMessageBuf.append(validateAgainst[validateAgainst.length - 1]);
/*      */         
/*  302 */         errorMessageBuf.append("'");
/*      */       } 
/*      */       
/*  305 */       errorMessageBuf.append(". The value '");
/*  306 */       errorMessageBuf.append(valueToValidate);
/*  307 */       errorMessageBuf.append("' is not in this set.");
/*      */       
/*  309 */       throw SQLError.createSQLException(errorMessageBuf.toString(), "S1009", ConnectionPropertiesImpl.this.getExceptionInterceptor());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   class IntegerConnectionProperty
/*      */     extends ConnectionProperty
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = -3004305481796850832L;
/*      */     
/*      */     int multiplier;
/*      */     
/*      */     public IntegerConnectionProperty(String propertyNameToSet, Object defaultValueToSet, String[] allowableValuesToSet, int lowerBoundToSet, int upperBoundToSet, String descriptionToSet, String sinceVersionToSet, String category, int orderInCategory) {
/*  323 */       super(propertyNameToSet, defaultValueToSet, allowableValuesToSet, lowerBoundToSet, upperBoundToSet, descriptionToSet, sinceVersionToSet, category, orderInCategory);
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  328 */       this.multiplier = 1;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     IntegerConnectionProperty(String propertyNameToSet, int defaultValueToSet, int lowerBoundToSet, int upperBoundToSet, String descriptionToSet, String sinceVersionToSet, String category, int orderInCategory) {
/*  334 */       super(propertyNameToSet, new Integer(defaultValueToSet), null, lowerBoundToSet, upperBoundToSet, descriptionToSet, sinceVersionToSet, category, orderInCategory);
/*      */       this.multiplier = 1;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     IntegerConnectionProperty(String propertyNameToSet, int defaultValueToSet, String descriptionToSet, String sinceVersionToSet, String category, int orderInCategory) {
/*  352 */       this(propertyNameToSet, defaultValueToSet, 0, 0, descriptionToSet, sinceVersionToSet, category, orderInCategory);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     String[] getAllowableValues() {
/*  360 */       return null;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int getLowerBound() {
/*  367 */       return this.lowerBound;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     int getUpperBound() {
/*  374 */       return this.upperBound;
/*      */     }
/*      */     
/*      */     int getValueAsInt() {
/*  378 */       return ((Integer)this.valueAsObject).intValue();
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean hasValueConstraints() {
/*  385 */       return false;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void initializeFrom(String extractedValue) throws SQLException {
/*  392 */       if (extractedValue != null) {
/*      */         
/*      */         try {
/*  395 */           int intValue = Double.valueOf(extractedValue).intValue();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  406 */           this.valueAsObject = new Integer(intValue * this.multiplier);
/*  407 */         } catch (NumberFormatException nfe) {
/*  408 */           throw SQLError.createSQLException("The connection property '" + getPropertyName() + "' only accepts integer values. The value '" + extractedValue + "' can not be converted to an integer.", "S1009", ConnectionPropertiesImpl.this.getExceptionInterceptor());
/*      */         
/*      */         }
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  416 */         this.valueAsObject = this.defaultValue;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean isRangeBased() {
/*  424 */       return (getUpperBound() != getLowerBound());
/*      */     }
/*      */     
/*      */     void setValue(int valueFlag) {
/*  428 */       this.valueAsObject = new Integer(valueFlag);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public class LongConnectionProperty
/*      */     extends IntegerConnectionProperty
/*      */   {
/*      */     private static final long serialVersionUID = 6068572984340480895L;
/*      */ 
/*      */     
/*      */     LongConnectionProperty(String propertyNameToSet, long defaultValueToSet, long lowerBoundToSet, long upperBoundToSet, String descriptionToSet, String sinceVersionToSet, String category, int orderInCategory) {
/*  440 */       super(propertyNameToSet, new Long(defaultValueToSet), (String[])null, (int)lowerBoundToSet, (int)upperBoundToSet, descriptionToSet, sinceVersionToSet, category, orderInCategory);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     LongConnectionProperty(String propertyNameToSet, long defaultValueToSet, String descriptionToSet, String sinceVersionToSet, String category, int orderInCategory) {
/*  449 */       this(propertyNameToSet, defaultValueToSet, 0L, 0L, descriptionToSet, sinceVersionToSet, category, orderInCategory);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void setValue(long value) {
/*  456 */       this.valueAsObject = new Long(value);
/*      */     }
/*      */     
/*      */     long getValueAsLong() {
/*  460 */       return ((Long)this.valueAsObject).longValue();
/*      */     }
/*      */     
/*      */     void initializeFrom(String extractedValue) throws SQLException {
/*  464 */       if (extractedValue != null) {
/*      */         
/*      */         try {
/*  467 */           long longValue = Double.valueOf(extractedValue).longValue();
/*      */           
/*  469 */           this.valueAsObject = new Long(longValue);
/*  470 */         } catch (NumberFormatException nfe) {
/*  471 */           throw SQLError.createSQLException("The connection property '" + getPropertyName() + "' only accepts long integer values. The value '" + extractedValue + "' can not be converted to a long integer.", "S1009", ConnectionPropertiesImpl.this.getExceptionInterceptor());
/*      */         
/*      */         }
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */         
/*  479 */         this.valueAsObject = this.defaultValue;
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   class MemorySizeConnectionProperty
/*      */     extends IntegerConnectionProperty
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 7351065128998572656L;
/*      */     
/*      */     private String valueAsString;
/*      */     
/*      */     MemorySizeConnectionProperty(String propertyNameToSet, int defaultValueToSet, int lowerBoundToSet, int upperBoundToSet, String descriptionToSet, String sinceVersionToSet, String category, int orderInCategory) {
/*  494 */       super(propertyNameToSet, defaultValueToSet, lowerBoundToSet, upperBoundToSet, descriptionToSet, sinceVersionToSet, category, orderInCategory);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     void initializeFrom(String extractedValue) throws SQLException {
/*  500 */       this.valueAsString = extractedValue;
/*      */       
/*  502 */       if (extractedValue != null) {
/*  503 */         if (extractedValue.endsWith("k") || extractedValue.endsWith("K") || extractedValue.endsWith("kb") || extractedValue.endsWith("Kb") || extractedValue.endsWith("kB")) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  508 */           this.multiplier = 1024;
/*  509 */           int indexOfK = StringUtils.indexOfIgnoreCase(extractedValue, "k");
/*      */           
/*  511 */           extractedValue = extractedValue.substring(0, indexOfK);
/*  512 */         } else if (extractedValue.endsWith("m") || extractedValue.endsWith("M") || extractedValue.endsWith("G") || extractedValue.endsWith("mb") || extractedValue.endsWith("Mb") || extractedValue.endsWith("mB")) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  518 */           this.multiplier = 1048576;
/*  519 */           int indexOfM = StringUtils.indexOfIgnoreCase(extractedValue, "m");
/*      */           
/*  521 */           extractedValue = extractedValue.substring(0, indexOfM);
/*  522 */         } else if (extractedValue.endsWith("g") || extractedValue.endsWith("G") || extractedValue.endsWith("gb") || extractedValue.endsWith("Gb") || extractedValue.endsWith("gB")) {
/*      */ 
/*      */ 
/*      */ 
/*      */           
/*  527 */           this.multiplier = 1073741824;
/*  528 */           int indexOfG = StringUtils.indexOfIgnoreCase(extractedValue, "g");
/*      */           
/*  530 */           extractedValue = extractedValue.substring(0, indexOfG);
/*      */         } 
/*      */       }
/*      */       
/*  534 */       super.initializeFrom(extractedValue);
/*      */     }
/*      */     
/*      */     void setValue(String value) throws SQLException {
/*  538 */       initializeFrom(value);
/*      */     }
/*      */     
/*      */     String getValueAsString() {
/*  542 */       return this.valueAsString;
/*      */     }
/*      */   }
/*      */   
/*      */   class StringConnectionProperty
/*      */     extends ConnectionProperty
/*      */     implements Serializable
/*      */   {
/*      */     private static final long serialVersionUID = 5432127962785948272L;
/*      */     
/*      */     StringConnectionProperty(String propertyNameToSet, String defaultValueToSet, String descriptionToSet, String sinceVersionToSet, String category, int orderInCategory) {
/*  553 */       this(propertyNameToSet, defaultValueToSet, (String[])null, descriptionToSet, sinceVersionToSet, category, orderInCategory);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     StringConnectionProperty(String propertyNameToSet, String defaultValueToSet, String[] allowableValuesToSet, String descriptionToSet, String sinceVersionToSet, String category, int orderInCategory) {
/*  571 */       super(propertyNameToSet, defaultValueToSet, allowableValuesToSet, 0, 0, descriptionToSet, sinceVersionToSet, category, orderInCategory);
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     String getValueAsString() {
/*  577 */       return (String)this.valueAsObject;
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean hasValueConstraints() {
/*  584 */       return (this.allowableValues != null && this.allowableValues.length > 0);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     void initializeFrom(String extractedValue) throws SQLException {
/*  592 */       if (extractedValue != null) {
/*  593 */         validateStringValues(extractedValue);
/*      */         
/*  595 */         this.valueAsObject = extractedValue;
/*      */       } else {
/*  597 */         this.valueAsObject = this.defaultValue;
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*      */     boolean isRangeBased() {
/*  605 */       return false;
/*      */     }
/*      */     
/*      */     void setValue(String valueFlag) {
/*  609 */       this.valueAsObject = valueFlag;
/*      */     }
/*      */   }
/*      */   
/*  613 */   private static final String CONNECTION_AND_AUTH_CATEGORY = Messages.getString("ConnectionProperties.categoryConnectionAuthentication");
/*      */   
/*  615 */   private static final String NETWORK_CATEGORY = Messages.getString("ConnectionProperties.categoryNetworking");
/*      */   
/*  617 */   private static final String DEBUGING_PROFILING_CATEGORY = Messages.getString("ConnectionProperties.categoryDebuggingProfiling");
/*      */   
/*  619 */   private static final String HA_CATEGORY = Messages.getString("ConnectionProperties.categorryHA");
/*      */   
/*  621 */   private static final String MISC_CATEGORY = Messages.getString("ConnectionProperties.categoryMisc");
/*      */   
/*  623 */   private static final String PERFORMANCE_CATEGORY = Messages.getString("ConnectionProperties.categoryPerformance");
/*      */   
/*  625 */   private static final String SECURITY_CATEGORY = Messages.getString("ConnectionProperties.categorySecurity");
/*      */   
/*  627 */   private static final String[] PROPERTY_CATEGORIES = new String[] { CONNECTION_AND_AUTH_CATEGORY, NETWORK_CATEGORY, HA_CATEGORY, SECURITY_CATEGORY, PERFORMANCE_CATEGORY, DEBUGING_PROFILING_CATEGORY, MISC_CATEGORY };
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  632 */   private static final ArrayList PROPERTY_LIST = new ArrayList();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  637 */   private static final String STANDARD_LOGGER_NAME = StandardLogger.class.getName();
/*      */   
/*      */   protected static final String ZERO_DATETIME_BEHAVIOR_CONVERT_TO_NULL = "convertToNull";
/*      */   
/*      */   protected static final String ZERO_DATETIME_BEHAVIOR_EXCEPTION = "exception";
/*      */   
/*      */   protected static final String ZERO_DATETIME_BEHAVIOR_ROUND = "round";
/*      */   
/*      */   static {
/*      */     try {
/*  647 */       Field[] declaredFields = ConnectionPropertiesImpl.class.getDeclaredFields();
/*      */ 
/*      */       
/*  650 */       for (int i = 0; i < declaredFields.length; i++) {
/*  651 */         if (ConnectionProperty.class.isAssignableFrom(declaredFields[i].getType()))
/*      */         {
/*  653 */           PROPERTY_LIST.add(declaredFields[i]);
/*      */         }
/*      */       } 
/*  656 */     } catch (Exception ex) {
/*  657 */       RuntimeException rtEx = new RuntimeException();
/*  658 */       rtEx.initCause(ex);
/*      */       
/*  660 */       throw rtEx;
/*      */     } 
/*      */   }
/*      */   
/*      */   public ExceptionInterceptor getExceptionInterceptor() {
/*  665 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected static DriverPropertyInfo[] exposeAsDriverPropertyInfo(Properties info, int slotsToReserve) throws SQLException {
/*  684 */     return (new ConnectionPropertiesImpl() {  }).exposeAsDriverPropertyInfoInternal(info, slotsToReserve);
/*      */   }
/*      */ 
/*      */   
/*  688 */   private BooleanConnectionProperty allowLoadLocalInfile = new BooleanConnectionProperty("allowLoadLocalInfile", true, Messages.getString("ConnectionProperties.loadDataLocal"), "3.0.3", SECURITY_CATEGORY, 2147483647);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  694 */   private BooleanConnectionProperty allowMultiQueries = new BooleanConnectionProperty("allowMultiQueries", false, Messages.getString("ConnectionProperties.allowMultiQueries"), "3.1.1", SECURITY_CATEGORY, 1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  700 */   private BooleanConnectionProperty allowNanAndInf = new BooleanConnectionProperty("allowNanAndInf", false, Messages.getString("ConnectionProperties.allowNANandINF"), "3.1.5", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  706 */   private BooleanConnectionProperty allowUrlInLocalInfile = new BooleanConnectionProperty("allowUrlInLocalInfile", false, Messages.getString("ConnectionProperties.allowUrlInLoadLocal"), "3.1.4", SECURITY_CATEGORY, 2147483647);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  712 */   private BooleanConnectionProperty alwaysSendSetIsolation = new BooleanConnectionProperty("alwaysSendSetIsolation", true, Messages.getString("ConnectionProperties.alwaysSendSetIsolation"), "3.1.7", PERFORMANCE_CATEGORY, 2147483647);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  718 */   private BooleanConnectionProperty autoClosePStmtStreams = new BooleanConnectionProperty("autoClosePStmtStreams", false, Messages.getString("ConnectionProperties.autoClosePstmtStreams"), "3.1.12", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  726 */   private BooleanConnectionProperty autoDeserialize = new BooleanConnectionProperty("autoDeserialize", false, Messages.getString("ConnectionProperties.autoDeserialize"), "3.1.5", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  732 */   private BooleanConnectionProperty autoGenerateTestcaseScript = new BooleanConnectionProperty("autoGenerateTestcaseScript", false, Messages.getString("ConnectionProperties.autoGenerateTestcaseScript"), "3.1.9", DEBUGING_PROFILING_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean autoGenerateTestcaseScriptAsBoolean = false;
/*      */ 
/*      */   
/*  739 */   private BooleanConnectionProperty autoReconnect = new BooleanConnectionProperty("autoReconnect", false, Messages.getString("ConnectionProperties.autoReconnect"), "1.1", HA_CATEGORY, 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  745 */   private BooleanConnectionProperty autoReconnectForPools = new BooleanConnectionProperty("autoReconnectForPools", false, Messages.getString("ConnectionProperties.autoReconnectForPools"), "3.1.3", HA_CATEGORY, 1);
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean autoReconnectForPoolsAsBoolean = false;
/*      */ 
/*      */ 
/*      */   
/*  753 */   private MemorySizeConnectionProperty blobSendChunkSize = new MemorySizeConnectionProperty("blobSendChunkSize", 1048576, 1, 2147483647, Messages.getString("ConnectionProperties.blobSendChunkSize"), "3.1.9", PERFORMANCE_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  761 */   private BooleanConnectionProperty autoSlowLog = new BooleanConnectionProperty("autoSlowLog", true, Messages.getString("ConnectionProperties.autoSlowLog"), "5.1.4", DEBUGING_PROFILING_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  766 */   private BooleanConnectionProperty blobsAreStrings = new BooleanConnectionProperty("blobsAreStrings", false, "Should the driver always treat BLOBs as Strings - specifically to work around dubious metadata returned by the server for GROUP BY clauses?", "5.0.8", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  772 */   private BooleanConnectionProperty functionsNeverReturnBlobs = new BooleanConnectionProperty("functionsNeverReturnBlobs", false, "Should the driver always treat data from functions returning BLOBs as Strings - specifically to work around dubious metadata returned by the server for GROUP BY clauses?", "5.0.8", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  778 */   private BooleanConnectionProperty cacheCallableStatements = new BooleanConnectionProperty("cacheCallableStmts", false, Messages.getString("ConnectionProperties.cacheCallableStatements"), "3.1.2", PERFORMANCE_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  783 */   private BooleanConnectionProperty cachePreparedStatements = new BooleanConnectionProperty("cachePrepStmts", false, Messages.getString("ConnectionProperties.cachePrepStmts"), "3.0.10", PERFORMANCE_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  789 */   private BooleanConnectionProperty cacheResultSetMetadata = new BooleanConnectionProperty("cacheResultSetMetadata", false, Messages.getString("ConnectionProperties.cacheRSMetadata"), "3.1.1", PERFORMANCE_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean cacheResultSetMetaDataAsBoolean;
/*      */ 
/*      */ 
/*      */   
/*  797 */   private BooleanConnectionProperty cacheServerConfiguration = new BooleanConnectionProperty("cacheServerConfiguration", false, Messages.getString("ConnectionProperties.cacheServerConfiguration"), "3.1.5", PERFORMANCE_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  803 */   private IntegerConnectionProperty callableStatementCacheSize = new IntegerConnectionProperty("callableStmtCacheSize", 100, 0, 2147483647, Messages.getString("ConnectionProperties.callableStmtCacheSize"), "3.1.2", PERFORMANCE_CATEGORY, 5);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  811 */   private BooleanConnectionProperty capitalizeTypeNames = new BooleanConnectionProperty("capitalizeTypeNames", true, Messages.getString("ConnectionProperties.capitalizeTypeNames"), "2.0.7", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  817 */   private StringConnectionProperty characterEncoding = new StringConnectionProperty("characterEncoding", null, Messages.getString("ConnectionProperties.characterEncoding"), "1.1g", MISC_CATEGORY, 5);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  823 */   private String characterEncodingAsString = null;
/*      */   
/*  825 */   private StringConnectionProperty characterSetResults = new StringConnectionProperty("characterSetResults", null, Messages.getString("ConnectionProperties.characterSetResults"), "3.0.13", MISC_CATEGORY, 6);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  830 */   private StringConnectionProperty clientInfoProvider = new StringConnectionProperty("clientInfoProvider", "com.mysql.jdbc.JDBC4CommentClientInfoProvider", Messages.getString("ConnectionProperties.clientInfoProvider"), "5.1.0", DEBUGING_PROFILING_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  836 */   private BooleanConnectionProperty clobberStreamingResults = new BooleanConnectionProperty("clobberStreamingResults", false, Messages.getString("ConnectionProperties.clobberStreamingResults"), "3.0.9", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  842 */   private StringConnectionProperty clobCharacterEncoding = new StringConnectionProperty("clobCharacterEncoding", null, Messages.getString("ConnectionProperties.clobCharacterEncoding"), "5.0.0", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  848 */   private BooleanConnectionProperty compensateOnDuplicateKeyUpdateCounts = new BooleanConnectionProperty("compensateOnDuplicateKeyUpdateCounts", false, Messages.getString("ConnectionProperties.compensateOnDuplicateKeyUpdateCounts"), "5.1.7", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  853 */   private StringConnectionProperty connectionCollation = new StringConnectionProperty("connectionCollation", null, Messages.getString("ConnectionProperties.connectionCollation"), "3.0.13", MISC_CATEGORY, 7);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  859 */   private StringConnectionProperty connectionLifecycleInterceptors = new StringConnectionProperty("connectionLifecycleInterceptors", null, Messages.getString("ConnectionProperties.connectionLifecycleInterceptors"), "5.1.4", CONNECTION_AND_AUTH_CATEGORY, 2147483647);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  865 */   private IntegerConnectionProperty connectTimeout = new IntegerConnectionProperty("connectTimeout", 0, 0, 2147483647, Messages.getString("ConnectionProperties.connectTimeout"), "3.0.1", CONNECTION_AND_AUTH_CATEGORY, 9);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  870 */   private BooleanConnectionProperty continueBatchOnError = new BooleanConnectionProperty("continueBatchOnError", true, Messages.getString("ConnectionProperties.continueBatchOnError"), "3.0.3", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  876 */   private BooleanConnectionProperty createDatabaseIfNotExist = new BooleanConnectionProperty("createDatabaseIfNotExist", false, Messages.getString("ConnectionProperties.createDatabaseIfNotExist"), "3.1.9", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  882 */   private IntegerConnectionProperty defaultFetchSize = new IntegerConnectionProperty("defaultFetchSize", 0, Messages.getString("ConnectionProperties.defaultFetchSize"), "3.1.9", PERFORMANCE_CATEGORY, -2147483648);
/*      */   
/*  884 */   private BooleanConnectionProperty detectServerPreparedStmts = new BooleanConnectionProperty("useServerPrepStmts", false, Messages.getString("ConnectionProperties.useServerPrepStmts"), "3.1.0", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  890 */   private BooleanConnectionProperty dontTrackOpenResources = new BooleanConnectionProperty("dontTrackOpenResources", false, Messages.getString("ConnectionProperties.dontTrackOpenResources"), "3.1.7", PERFORMANCE_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  896 */   private BooleanConnectionProperty dumpQueriesOnException = new BooleanConnectionProperty("dumpQueriesOnException", false, Messages.getString("ConnectionProperties.dumpQueriesOnException"), "3.1.3", DEBUGING_PROFILING_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  902 */   private BooleanConnectionProperty dynamicCalendars = new BooleanConnectionProperty("dynamicCalendars", false, Messages.getString("ConnectionProperties.dynamicCalendars"), "3.1.5", PERFORMANCE_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  908 */   private BooleanConnectionProperty elideSetAutoCommits = new BooleanConnectionProperty("elideSetAutoCommits", false, Messages.getString("ConnectionProperties.eliseSetAutoCommit"), "3.1.3", PERFORMANCE_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  914 */   private BooleanConnectionProperty emptyStringsConvertToZero = new BooleanConnectionProperty("emptyStringsConvertToZero", true, Messages.getString("ConnectionProperties.emptyStringsConvertToZero"), "3.1.8", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  919 */   private BooleanConnectionProperty emulateLocators = new BooleanConnectionProperty("emulateLocators", false, Messages.getString("ConnectionProperties.emulateLocators"), "3.1.0", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */   
/*  923 */   private BooleanConnectionProperty emulateUnsupportedPstmts = new BooleanConnectionProperty("emulateUnsupportedPstmts", true, Messages.getString("ConnectionProperties.emulateUnsupportedPstmts"), "3.1.7", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  929 */   private BooleanConnectionProperty enablePacketDebug = new BooleanConnectionProperty("enablePacketDebug", false, Messages.getString("ConnectionProperties.enablePacketDebug"), "3.1.3", DEBUGING_PROFILING_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  935 */   private BooleanConnectionProperty enableQueryTimeouts = new BooleanConnectionProperty("enableQueryTimeouts", true, Messages.getString("ConnectionProperties.enableQueryTimeouts"), "5.0.6", PERFORMANCE_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  942 */   private BooleanConnectionProperty explainSlowQueries = new BooleanConnectionProperty("explainSlowQueries", false, Messages.getString("ConnectionProperties.explainSlowQueries"), "3.1.2", DEBUGING_PROFILING_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  948 */   private StringConnectionProperty exceptionInterceptors = new StringConnectionProperty("exceptionInterceptors", null, Messages.getString("ConnectionProperties.exceptionInterceptors"), "5.1.8", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  955 */   private BooleanConnectionProperty failOverReadOnly = new BooleanConnectionProperty("failOverReadOnly", true, Messages.getString("ConnectionProperties.failoverReadOnly"), "3.0.12", HA_CATEGORY, 2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  961 */   private BooleanConnectionProperty gatherPerformanceMetrics = new BooleanConnectionProperty("gatherPerfMetrics", false, Messages.getString("ConnectionProperties.gatherPerfMetrics"), "3.1.2", DEBUGING_PROFILING_CATEGORY, 1);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  967 */   private BooleanConnectionProperty generateSimpleParameterMetadata = new BooleanConnectionProperty("generateSimpleParameterMetadata", false, Messages.getString("ConnectionProperties.generateSimpleParameterMetadata"), "5.0.5", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */   
/*      */   private boolean highAvailabilityAsBoolean = false;
/*      */   
/*  972 */   private BooleanConnectionProperty holdResultsOpenOverStatementClose = new BooleanConnectionProperty("holdResultsOpenOverStatementClose", false, Messages.getString("ConnectionProperties.holdRSOpenOverStmtClose"), "3.1.7", PERFORMANCE_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  978 */   private BooleanConnectionProperty includeInnodbStatusInDeadlockExceptions = new BooleanConnectionProperty("includeInnodbStatusInDeadlockExceptions", false, "Include the output of \"SHOW ENGINE INNODB STATUS\" in exception messages when deadlock exceptions are detected?", "5.0.7", DEBUGING_PROFILING_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  984 */   private BooleanConnectionProperty ignoreNonTxTables = new BooleanConnectionProperty("ignoreNonTxTables", false, Messages.getString("ConnectionProperties.ignoreNonTxTables"), "3.0.9", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  990 */   private IntegerConnectionProperty initialTimeout = new IntegerConnectionProperty("initialTimeout", 2, 1, 2147483647, Messages.getString("ConnectionProperties.initialTimeout"), "1.1", HA_CATEGORY, 5);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  995 */   private BooleanConnectionProperty isInteractiveClient = new BooleanConnectionProperty("interactiveClient", false, Messages.getString("ConnectionProperties.interactiveClient"), "3.1.0", CONNECTION_AND_AUTH_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1001 */   private BooleanConnectionProperty jdbcCompliantTruncation = new BooleanConnectionProperty("jdbcCompliantTruncation", true, Messages.getString("ConnectionProperties.jdbcCompliantTruncation"), "3.1.2", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1007 */   private boolean jdbcCompliantTruncationForReads = this.jdbcCompliantTruncation.getValueAsBoolean();
/*      */ 
/*      */   
/* 1010 */   protected MemorySizeConnectionProperty largeRowSizeThreshold = new MemorySizeConnectionProperty("largeRowSizeThreshold", 2048, 0, 2147483647, Messages.getString("ConnectionProperties.largeRowSizeThreshold"), "5.1.1", PERFORMANCE_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1015 */   private StringConnectionProperty loadBalanceStrategy = new StringConnectionProperty("loadBalanceStrategy", "random", null, Messages.getString("ConnectionProperties.loadBalanceStrategy"), "5.0.6", PERFORMANCE_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1022 */   private IntegerConnectionProperty loadBalanceBlacklistTimeout = new IntegerConnectionProperty("loadBalanceBlacklistTimeout", 0, 0, 2147483647, Messages.getString("ConnectionProperties.loadBalanceBlacklistTimeout"), "5.1.0", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1028 */   private IntegerConnectionProperty loadBalancePingTimeout = new IntegerConnectionProperty("loadBalancePingTimeout", 0, 0, 2147483647, Messages.getString("ConnectionProperties.loadBalancePingTimeout"), "5.1.13", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1034 */   private BooleanConnectionProperty loadBalanceValidateConnectionOnSwapServer = new BooleanConnectionProperty("loadBalanceValidateConnectionOnSwapServer", false, Messages.getString("ConnectionProperties.loadBalanceValidateConnectionOnSwapServer"), "5.1.13", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1040 */   private StringConnectionProperty loadBalanceConnectionGroup = new StringConnectionProperty("loadBalanceConnectionGroup", null, Messages.getString("ConnectionProperties.loadBalanceConnectionGroup"), "5.1.13", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1046 */   private StringConnectionProperty loadBalanceExceptionChecker = new StringConnectionProperty("loadBalanceExceptionChecker", "com.mysql.jdbc.StandardLoadBalanceExceptionChecker", null, Messages.getString("ConnectionProperties.loadBalanceExceptionChecker"), "5.1.13", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1053 */   private StringConnectionProperty loadBalanceSQLStateFailover = new StringConnectionProperty("loadBalanceSQLStateFailover", null, Messages.getString("ConnectionProperties.loadBalanceSQLStateFailover"), "5.1.13", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1059 */   private StringConnectionProperty loadBalanceSQLExceptionSubclassFailover = new StringConnectionProperty("loadBalanceSQLExceptionSubclassFailover", null, Messages.getString("ConnectionProperties.loadBalanceSQLExceptionSubclassFailover"), "5.1.13", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1065 */   private BooleanConnectionProperty loadBalanceEnableJMX = new BooleanConnectionProperty("loadBalanceEnableJMX", false, Messages.getString("ConnectionProperties.loadBalanceEnableJMX"), "5.1.13", MISC_CATEGORY, 2147483647);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1071 */   private StringConnectionProperty loadBalanceAutoCommitStatementRegex = new StringConnectionProperty("loadBalanceAutoCommitStatementRegex", null, Messages.getString("ConnectionProperties.loadBalanceAutoCommitStatementRegex"), "5.1.15", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1077 */   private IntegerConnectionProperty loadBalanceAutoCommitStatementThreshold = new IntegerConnectionProperty("loadBalanceAutoCommitStatementThreshold", 0, 0, 2147483647, Messages.getString("ConnectionProperties.loadBalanceAutoCommitStatementThreshold"), "5.1.15", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1084 */   private StringConnectionProperty localSocketAddress = new StringConnectionProperty("localSocketAddress", null, Messages.getString("ConnectionProperties.localSocketAddress"), "5.0.5", CONNECTION_AND_AUTH_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */   
/* 1088 */   private MemorySizeConnectionProperty locatorFetchBufferSize = new MemorySizeConnectionProperty("locatorFetchBufferSize", 1048576, 0, 2147483647, Messages.getString("ConnectionProperties.locatorFetchBufferSize"), "3.2.1", PERFORMANCE_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1096 */   private StringConnectionProperty loggerClassName = new StringConnectionProperty("logger", STANDARD_LOGGER_NAME, Messages.getString("ConnectionProperties.logger", new Object[] { Log.class.getName(), STANDARD_LOGGER_NAME }), "3.1.1", DEBUGING_PROFILING_CATEGORY, 0);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1102 */   private BooleanConnectionProperty logSlowQueries = new BooleanConnectionProperty("logSlowQueries", false, Messages.getString("ConnectionProperties.logSlowQueries"), "3.1.2", DEBUGING_PROFILING_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1108 */   private BooleanConnectionProperty logXaCommands = new BooleanConnectionProperty("logXaCommands", false, Messages.getString("ConnectionProperties.logXaCommands"), "5.0.5", DEBUGING_PROFILING_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1114 */   private BooleanConnectionProperty maintainTimeStats = new BooleanConnectionProperty("maintainTimeStats", true, Messages.getString("ConnectionProperties.maintainTimeStats"), "3.1.9", PERFORMANCE_CATEGORY, 2147483647);
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean maintainTimeStatsAsBoolean = true;
/*      */ 
/*      */ 
/*      */   
/* 1122 */   private IntegerConnectionProperty maxQuerySizeToLog = new IntegerConnectionProperty("maxQuerySizeToLog", 2048, 0, 2147483647, Messages.getString("ConnectionProperties.maxQuerySizeToLog"), "3.1.3", DEBUGING_PROFILING_CATEGORY, 4);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1130 */   private IntegerConnectionProperty maxReconnects = new IntegerConnectionProperty("maxReconnects", 3, 1, 2147483647, Messages.getString("ConnectionProperties.maxReconnects"), "1.1", HA_CATEGORY, 4);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1138 */   private IntegerConnectionProperty retriesAllDown = new IntegerConnectionProperty("retriesAllDown", 120, 0, 2147483647, Messages.getString("ConnectionProperties.retriesAllDown"), "5.1.6", HA_CATEGORY, 4);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1146 */   private IntegerConnectionProperty maxRows = new IntegerConnectionProperty("maxRows", -1, -1, 2147483647, Messages.getString("ConnectionProperties.maxRows"), Messages.getString("ConnectionProperties.allVersions"), MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1151 */   private int maxRowsAsInt = -1;
/*      */   
/* 1153 */   private IntegerConnectionProperty metadataCacheSize = new IntegerConnectionProperty("metadataCacheSize", 50, 1, 2147483647, Messages.getString("ConnectionProperties.metadataCacheSize"), "3.1.1", PERFORMANCE_CATEGORY, 5);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1161 */   private IntegerConnectionProperty netTimeoutForStreamingResults = new IntegerConnectionProperty("netTimeoutForStreamingResults", 600, 0, 2147483647, Messages.getString("ConnectionProperties.netTimeoutForStreamingResults"), "5.1.0", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1167 */   private BooleanConnectionProperty noAccessToProcedureBodies = new BooleanConnectionProperty("noAccessToProcedureBodies", false, "When determining procedure parameter types for CallableStatements, and the connected user  can't access procedure bodies through \"SHOW CREATE PROCEDURE\" or select on mysql.proc  should the driver instead create basic metadata (all parameters reported as IN VARCHARs, but allowing registerOutParameter() to be called on them anyway) instead  of throwing an exception?", "5.0.3", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1177 */   private BooleanConnectionProperty noDatetimeStringSync = new BooleanConnectionProperty("noDatetimeStringSync", false, Messages.getString("ConnectionProperties.noDatetimeStringSync"), "3.1.7", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1183 */   private BooleanConnectionProperty noTimezoneConversionForTimeType = new BooleanConnectionProperty("noTimezoneConversionForTimeType", false, Messages.getString("ConnectionProperties.noTzConversionForTimeType"), "5.0.0", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1189 */   private BooleanConnectionProperty nullCatalogMeansCurrent = new BooleanConnectionProperty("nullCatalogMeansCurrent", true, Messages.getString("ConnectionProperties.nullCatalogMeansCurrent"), "3.1.8", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1195 */   private BooleanConnectionProperty nullNamePatternMatchesAll = new BooleanConnectionProperty("nullNamePatternMatchesAll", true, Messages.getString("ConnectionProperties.nullNamePatternMatchesAll"), "3.1.8", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1201 */   private IntegerConnectionProperty packetDebugBufferSize = new IntegerConnectionProperty("packetDebugBufferSize", 20, 0, 2147483647, Messages.getString("ConnectionProperties.packetDebugBufferSize"), "3.1.3", DEBUGING_PROFILING_CATEGORY, 7);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1209 */   private BooleanConnectionProperty padCharsWithSpace = new BooleanConnectionProperty("padCharsWithSpace", false, Messages.getString("ConnectionProperties.padCharsWithSpace"), "5.0.6", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1217 */   private BooleanConnectionProperty paranoid = new BooleanConnectionProperty("paranoid", false, Messages.getString("ConnectionProperties.paranoid"), "3.0.1", SECURITY_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1223 */   private BooleanConnectionProperty pedantic = new BooleanConnectionProperty("pedantic", false, Messages.getString("ConnectionProperties.pedantic"), "3.0.0", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */   
/* 1227 */   private BooleanConnectionProperty pinGlobalTxToPhysicalConnection = new BooleanConnectionProperty("pinGlobalTxToPhysicalConnection", false, Messages.getString("ConnectionProperties.pinGlobalTxToPhysicalConnection"), "5.0.1", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */   
/* 1231 */   private BooleanConnectionProperty populateInsertRowWithDefaultValues = new BooleanConnectionProperty("populateInsertRowWithDefaultValues", false, Messages.getString("ConnectionProperties.populateInsertRowWithDefaultValues"), "5.0.5", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1236 */   private IntegerConnectionProperty preparedStatementCacheSize = new IntegerConnectionProperty("prepStmtCacheSize", 25, 0, 2147483647, Messages.getString("ConnectionProperties.prepStmtCacheSize"), "3.0.10", PERFORMANCE_CATEGORY, 10);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1241 */   private IntegerConnectionProperty preparedStatementCacheSqlLimit = new IntegerConnectionProperty("prepStmtCacheSqlLimit", 256, 1, 2147483647, Messages.getString("ConnectionProperties.prepStmtCacheSqlLimit"), "3.0.10", PERFORMANCE_CATEGORY, 11);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1249 */   private BooleanConnectionProperty processEscapeCodesForPrepStmts = new BooleanConnectionProperty("processEscapeCodesForPrepStmts", true, Messages.getString("ConnectionProperties.processEscapeCodesForPrepStmts"), "3.1.12", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1256 */   private StringConnectionProperty profilerEventHandler = new StringConnectionProperty("profilerEventHandler", "com.mysql.jdbc.profiler.LoggingProfilerEventHandler", Messages.getString("ConnectionProperties.profilerEventHandler"), "5.1.6", DEBUGING_PROFILING_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1262 */   private StringConnectionProperty profileSql = new StringConnectionProperty("profileSql", null, Messages.getString("ConnectionProperties.profileSqlDeprecated"), "2.0.14", DEBUGING_PROFILING_CATEGORY, 3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1268 */   private BooleanConnectionProperty profileSQL = new BooleanConnectionProperty("profileSQL", false, Messages.getString("ConnectionProperties.profileSQL"), "3.1.0", DEBUGING_PROFILING_CATEGORY, 1);
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean profileSQLAsBoolean = false;
/*      */ 
/*      */ 
/*      */   
/* 1276 */   private StringConnectionProperty propertiesTransform = new StringConnectionProperty("propertiesTransform", null, Messages.getString("ConnectionProperties.connectionPropertiesTransform"), "3.1.4", CONNECTION_AND_AUTH_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1282 */   private IntegerConnectionProperty queriesBeforeRetryMaster = new IntegerConnectionProperty("queriesBeforeRetryMaster", 50, 1, 2147483647, Messages.getString("ConnectionProperties.queriesBeforeRetryMaster"), "3.0.2", HA_CATEGORY, 7);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1290 */   private BooleanConnectionProperty queryTimeoutKillsConnection = new BooleanConnectionProperty("queryTimeoutKillsConnection", false, Messages.getString("ConnectionProperties.queryTimeoutKillsConnection"), "5.1.9", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */   
/* 1294 */   private BooleanConnectionProperty reconnectAtTxEnd = new BooleanConnectionProperty("reconnectAtTxEnd", false, Messages.getString("ConnectionProperties.reconnectAtTxEnd"), "3.0.10", HA_CATEGORY, 4);
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean reconnectTxAtEndAsBoolean = false;
/*      */ 
/*      */   
/* 1301 */   private BooleanConnectionProperty relaxAutoCommit = new BooleanConnectionProperty("relaxAutoCommit", false, Messages.getString("ConnectionProperties.relaxAutoCommit"), "2.0.13", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1307 */   private IntegerConnectionProperty reportMetricsIntervalMillis = new IntegerConnectionProperty("reportMetricsIntervalMillis", 30000, 0, 2147483647, Messages.getString("ConnectionProperties.reportMetricsIntervalMillis"), "3.1.2", DEBUGING_PROFILING_CATEGORY, 3);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1315 */   private BooleanConnectionProperty requireSSL = new BooleanConnectionProperty("requireSSL", false, Messages.getString("ConnectionProperties.requireSSL"), "3.1.0", SECURITY_CATEGORY, 3);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1320 */   private StringConnectionProperty resourceId = new StringConnectionProperty("resourceId", null, Messages.getString("ConnectionProperties.resourceId"), "5.0.1", HA_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1327 */   private IntegerConnectionProperty resultSetSizeThreshold = new IntegerConnectionProperty("resultSetSizeThreshold", 100, Messages.getString("ConnectionProperties.resultSetSizeThreshold"), "5.0.5", DEBUGING_PROFILING_CATEGORY, -2147483648);
/*      */ 
/*      */   
/* 1330 */   private BooleanConnectionProperty retainStatementAfterResultSetClose = new BooleanConnectionProperty("retainStatementAfterResultSetClose", false, Messages.getString("ConnectionProperties.retainStatementAfterResultSetClose"), "3.1.11", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1336 */   private BooleanConnectionProperty rewriteBatchedStatements = new BooleanConnectionProperty("rewriteBatchedStatements", false, Messages.getString("ConnectionProperties.rewriteBatchedStatements"), "3.1.13", PERFORMANCE_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1342 */   private BooleanConnectionProperty rollbackOnPooledClose = new BooleanConnectionProperty("rollbackOnPooledClose", true, Messages.getString("ConnectionProperties.rollbackOnPooledClose"), "3.0.15", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1348 */   private BooleanConnectionProperty roundRobinLoadBalance = new BooleanConnectionProperty("roundRobinLoadBalance", false, Messages.getString("ConnectionProperties.roundRobinLoadBalance"), "3.1.2", HA_CATEGORY, 5);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1354 */   private BooleanConnectionProperty runningCTS13 = new BooleanConnectionProperty("runningCTS13", false, Messages.getString("ConnectionProperties.runningCTS13"), "3.1.7", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1360 */   private IntegerConnectionProperty secondsBeforeRetryMaster = new IntegerConnectionProperty("secondsBeforeRetryMaster", 30, 1, 2147483647, Messages.getString("ConnectionProperties.secondsBeforeRetryMaster"), "3.0.2", HA_CATEGORY, 8);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1368 */   private IntegerConnectionProperty selfDestructOnPingSecondsLifetime = new IntegerConnectionProperty("selfDestructOnPingSecondsLifetime", 0, 0, 2147483647, Messages.getString("ConnectionProperties.selfDestructOnPingSecondsLifetime"), "5.1.6", HA_CATEGORY, 2147483647);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1376 */   private IntegerConnectionProperty selfDestructOnPingMaxOperations = new IntegerConnectionProperty("selfDestructOnPingMaxOperations", 0, 0, 2147483647, Messages.getString("ConnectionProperties.selfDestructOnPingMaxOperations"), "5.1.6", HA_CATEGORY, 2147483647);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1384 */   private StringConnectionProperty serverTimezone = new StringConnectionProperty("serverTimezone", null, Messages.getString("ConnectionProperties.serverTimezone"), "3.0.2", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1390 */   private StringConnectionProperty sessionVariables = new StringConnectionProperty("sessionVariables", null, Messages.getString("ConnectionProperties.sessionVariables"), "3.1.8", MISC_CATEGORY, 2147483647);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1395 */   private IntegerConnectionProperty slowQueryThresholdMillis = new IntegerConnectionProperty("slowQueryThresholdMillis", 2000, 0, 2147483647, Messages.getString("ConnectionProperties.slowQueryThresholdMillis"), "3.1.2", DEBUGING_PROFILING_CATEGORY, 9);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1403 */   private LongConnectionProperty slowQueryThresholdNanos = new LongConnectionProperty("slowQueryThresholdNanos", 0L, Messages.getString("ConnectionProperties.slowQueryThresholdNanos"), "5.0.7", DEBUGING_PROFILING_CATEGORY, 10);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1411 */   private StringConnectionProperty socketFactoryClassName = new StringConnectionProperty("socketFactory", StandardSocketFactory.class.getName(), Messages.getString("ConnectionProperties.socketFactory"), "3.0.3", CONNECTION_AND_AUTH_CATEGORY, 4);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1417 */   private IntegerConnectionProperty socketTimeout = new IntegerConnectionProperty("socketTimeout", 0, 0, 2147483647, Messages.getString("ConnectionProperties.socketTimeout"), "3.0.1", CONNECTION_AND_AUTH_CATEGORY, 10);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1425 */   private StringConnectionProperty statementInterceptors = new StringConnectionProperty("statementInterceptors", null, Messages.getString("ConnectionProperties.statementInterceptors"), "5.1.1", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */   
/* 1428 */   private BooleanConnectionProperty strictFloatingPoint = new BooleanConnectionProperty("strictFloatingPoint", false, Messages.getString("ConnectionProperties.strictFloatingPoint"), "3.0.0", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1433 */   private BooleanConnectionProperty strictUpdates = new BooleanConnectionProperty("strictUpdates", true, Messages.getString("ConnectionProperties.strictUpdates"), "3.0.4", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1439 */   private BooleanConnectionProperty overrideSupportsIntegrityEnhancementFacility = new BooleanConnectionProperty("overrideSupportsIntegrityEnhancementFacility", false, Messages.getString("ConnectionProperties.overrideSupportsIEF"), "3.1.12", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1445 */   private BooleanConnectionProperty tcpNoDelay = new BooleanConnectionProperty("tcpNoDelay", Boolean.valueOf("true").booleanValue(), Messages.getString("ConnectionProperties.tcpNoDelay"), "5.0.7", NETWORK_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1451 */   private BooleanConnectionProperty tcpKeepAlive = new BooleanConnectionProperty("tcpKeepAlive", Boolean.valueOf("true").booleanValue(), Messages.getString("ConnectionProperties.tcpKeepAlive"), "5.0.7", NETWORK_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1457 */   private IntegerConnectionProperty tcpRcvBuf = new IntegerConnectionProperty("tcpRcvBuf", Integer.parseInt("0"), 0, 2147483647, Messages.getString("ConnectionProperties.tcpSoRcvBuf"), "5.0.7", NETWORK_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1464 */   private IntegerConnectionProperty tcpSndBuf = new IntegerConnectionProperty("tcpSndBuf", Integer.parseInt("0"), 0, 2147483647, Messages.getString("ConnectionProperties.tcpSoSndBuf"), "5.0.7", NETWORK_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1471 */   private IntegerConnectionProperty tcpTrafficClass = new IntegerConnectionProperty("tcpTrafficClass", Integer.parseInt("0"), 0, 255, Messages.getString("ConnectionProperties.tcpTrafficClass"), "5.0.7", NETWORK_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1478 */   private BooleanConnectionProperty tinyInt1isBit = new BooleanConnectionProperty("tinyInt1isBit", true, Messages.getString("ConnectionProperties.tinyInt1isBit"), "3.0.16", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1484 */   private BooleanConnectionProperty traceProtocol = new BooleanConnectionProperty("traceProtocol", false, Messages.getString("ConnectionProperties.traceProtocol"), "3.1.2", DEBUGING_PROFILING_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1489 */   private BooleanConnectionProperty treatUtilDateAsTimestamp = new BooleanConnectionProperty("treatUtilDateAsTimestamp", true, Messages.getString("ConnectionProperties.treatUtilDateAsTimestamp"), "5.0.5", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1494 */   private BooleanConnectionProperty transformedBitIsBoolean = new BooleanConnectionProperty("transformedBitIsBoolean", false, Messages.getString("ConnectionProperties.transformedBitIsBoolean"), "3.1.9", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1500 */   private BooleanConnectionProperty useBlobToStoreUTF8OutsideBMP = new BooleanConnectionProperty("useBlobToStoreUTF8OutsideBMP", false, Messages.getString("ConnectionProperties.useBlobToStoreUTF8OutsideBMP"), "5.1.3", MISC_CATEGORY, 128);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1506 */   private StringConnectionProperty utf8OutsideBmpExcludedColumnNamePattern = new StringConnectionProperty("utf8OutsideBmpExcludedColumnNamePattern", null, Messages.getString("ConnectionProperties.utf8OutsideBmpExcludedColumnNamePattern"), "5.1.3", MISC_CATEGORY, 129);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1512 */   private StringConnectionProperty utf8OutsideBmpIncludedColumnNamePattern = new StringConnectionProperty("utf8OutsideBmpIncludedColumnNamePattern", null, Messages.getString("ConnectionProperties.utf8OutsideBmpIncludedColumnNamePattern"), "5.1.3", MISC_CATEGORY, 129);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1518 */   private BooleanConnectionProperty useCompression = new BooleanConnectionProperty("useCompression", false, Messages.getString("ConnectionProperties.useCompression"), "3.0.17", CONNECTION_AND_AUTH_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1524 */   private BooleanConnectionProperty useColumnNamesInFindColumn = new BooleanConnectionProperty("useColumnNamesInFindColumn", false, Messages.getString("ConnectionProperties.useColumnNamesInFindColumn"), "5.1.7", MISC_CATEGORY, 2147483647);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1530 */   private StringConnectionProperty useConfigs = new StringConnectionProperty("useConfigs", null, Messages.getString("ConnectionProperties.useConfigs"), "3.1.5", CONNECTION_AND_AUTH_CATEGORY, 2147483647);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1536 */   private BooleanConnectionProperty useCursorFetch = new BooleanConnectionProperty("useCursorFetch", false, Messages.getString("ConnectionProperties.useCursorFetch"), "5.0.0", PERFORMANCE_CATEGORY, 2147483647);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1542 */   private BooleanConnectionProperty useDynamicCharsetInfo = new BooleanConnectionProperty("useDynamicCharsetInfo", true, Messages.getString("ConnectionProperties.useDynamicCharsetInfo"), "5.0.6", PERFORMANCE_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1548 */   private BooleanConnectionProperty useDirectRowUnpack = new BooleanConnectionProperty("useDirectRowUnpack", true, "Use newer result set row unpacking code that skips a copy from network buffers  to a MySQL packet instance and instead reads directly into the result set row data buffers.", "5.1.1", PERFORMANCE_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1554 */   private BooleanConnectionProperty useFastIntParsing = new BooleanConnectionProperty("useFastIntParsing", true, Messages.getString("ConnectionProperties.useFastIntParsing"), "3.1.4", PERFORMANCE_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1560 */   private BooleanConnectionProperty useFastDateParsing = new BooleanConnectionProperty("useFastDateParsing", true, Messages.getString("ConnectionProperties.useFastDateParsing"), "5.0.5", PERFORMANCE_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1566 */   private BooleanConnectionProperty useHostsInPrivileges = new BooleanConnectionProperty("useHostsInPrivileges", true, Messages.getString("ConnectionProperties.useHostsInPrivileges"), "3.0.2", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1571 */   private BooleanConnectionProperty useInformationSchema = new BooleanConnectionProperty("useInformationSchema", false, Messages.getString("ConnectionProperties.useInformationSchema"), "5.0.0", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1576 */   private BooleanConnectionProperty useJDBCCompliantTimezoneShift = new BooleanConnectionProperty("useJDBCCompliantTimezoneShift", false, Messages.getString("ConnectionProperties.useJDBCCompliantTimezoneShift"), "5.0.0", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1583 */   private BooleanConnectionProperty useLocalSessionState = new BooleanConnectionProperty("useLocalSessionState", false, Messages.getString("ConnectionProperties.useLocalSessionState"), "3.1.7", PERFORMANCE_CATEGORY, 5);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1589 */   private BooleanConnectionProperty useLocalTransactionState = new BooleanConnectionProperty("useLocalTransactionState", false, Messages.getString("ConnectionProperties.useLocalTransactionState"), "5.1.7", PERFORMANCE_CATEGORY, 6);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1595 */   private BooleanConnectionProperty useLegacyDatetimeCode = new BooleanConnectionProperty("useLegacyDatetimeCode", true, Messages.getString("ConnectionProperties.useLegacyDatetimeCode"), "5.1.6", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1601 */   private BooleanConnectionProperty useNanosForElapsedTime = new BooleanConnectionProperty("useNanosForElapsedTime", false, Messages.getString("ConnectionProperties.useNanosForElapsedTime"), "5.0.7", DEBUGING_PROFILING_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1608 */   private BooleanConnectionProperty useOldAliasMetadataBehavior = new BooleanConnectionProperty("useOldAliasMetadataBehavior", false, Messages.getString("ConnectionProperties.useOldAliasMetadataBehavior"), "5.0.4", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1616 */   private BooleanConnectionProperty useOldUTF8Behavior = new BooleanConnectionProperty("useOldUTF8Behavior", false, Messages.getString("ConnectionProperties.useOldUtf8Behavior"), "3.1.6", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean useOldUTF8BehaviorAsBoolean = false;
/*      */ 
/*      */ 
/*      */   
/* 1624 */   private BooleanConnectionProperty useOnlyServerErrorMessages = new BooleanConnectionProperty("useOnlyServerErrorMessages", true, Messages.getString("ConnectionProperties.useOnlyServerErrorMessages"), "3.0.15", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1630 */   private BooleanConnectionProperty useReadAheadInput = new BooleanConnectionProperty("useReadAheadInput", true, Messages.getString("ConnectionProperties.useReadAheadInput"), "3.1.5", PERFORMANCE_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1636 */   private BooleanConnectionProperty useSqlStateCodes = new BooleanConnectionProperty("useSqlStateCodes", true, Messages.getString("ConnectionProperties.useSqlStateCodes"), "3.1.3", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1642 */   private BooleanConnectionProperty useSSL = new BooleanConnectionProperty("useSSL", false, Messages.getString("ConnectionProperties.useSSL"), "3.0.2", SECURITY_CATEGORY, 2);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1648 */   private BooleanConnectionProperty useSSPSCompatibleTimezoneShift = new BooleanConnectionProperty("useSSPSCompatibleTimezoneShift", false, Messages.getString("ConnectionProperties.useSSPSCompatibleTimezoneShift"), "5.0.5", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1654 */   private BooleanConnectionProperty useStreamLengthsInPrepStmts = new BooleanConnectionProperty("useStreamLengthsInPrepStmts", true, Messages.getString("ConnectionProperties.useStreamLengthsInPrepStmts"), "3.0.2", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1660 */   private BooleanConnectionProperty useTimezone = new BooleanConnectionProperty("useTimezone", false, Messages.getString("ConnectionProperties.useTimezone"), "3.0.2", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1666 */   private BooleanConnectionProperty useUltraDevWorkAround = new BooleanConnectionProperty("ultraDevHack", false, Messages.getString("ConnectionProperties.ultraDevHack"), "2.0.3", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1672 */   private BooleanConnectionProperty useUnbufferedInput = new BooleanConnectionProperty("useUnbufferedInput", true, Messages.getString("ConnectionProperties.useUnbufferedInput"), "3.0.11", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1677 */   private BooleanConnectionProperty useUnicode = new BooleanConnectionProperty("useUnicode", true, Messages.getString("ConnectionProperties.useUnicode"), "1.1g", MISC_CATEGORY, 0);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean useUnicodeAsBoolean = true;
/*      */ 
/*      */ 
/*      */   
/* 1686 */   private BooleanConnectionProperty useUsageAdvisor = new BooleanConnectionProperty("useUsageAdvisor", false, Messages.getString("ConnectionProperties.useUsageAdvisor"), "3.1.1", DEBUGING_PROFILING_CATEGORY, 10);
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean useUsageAdvisorAsBoolean = false;
/*      */ 
/*      */ 
/*      */   
/* 1694 */   private BooleanConnectionProperty yearIsDateType = new BooleanConnectionProperty("yearIsDateType", true, Messages.getString("ConnectionProperties.yearIsDateType"), "3.1.9", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1700 */   private StringConnectionProperty zeroDateTimeBehavior = new StringConnectionProperty("zeroDateTimeBehavior", "exception", new String[] { "exception", "round", "convertToNull" }, Messages.getString("ConnectionProperties.zeroDateTimeBehavior", new Object[] { "exception", "round", "convertToNull" }), "3.1.4", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1710 */   private BooleanConnectionProperty useJvmCharsetConverters = new BooleanConnectionProperty("useJvmCharsetConverters", false, Messages.getString("ConnectionProperties.useJvmCharsetConverters"), "5.0.1", PERFORMANCE_CATEGORY, -2147483648);
/*      */ 
/*      */   
/* 1713 */   private BooleanConnectionProperty useGmtMillisForDatetimes = new BooleanConnectionProperty("useGmtMillisForDatetimes", false, Messages.getString("ConnectionProperties.useGmtMillisForDatetimes"), "3.1.12", MISC_CATEGORY, -2147483648);
/*      */   
/* 1715 */   private BooleanConnectionProperty dumpMetadataOnColumnNotFound = new BooleanConnectionProperty("dumpMetadataOnColumnNotFound", false, Messages.getString("ConnectionProperties.dumpMetadataOnColumnNotFound"), "3.1.13", DEBUGING_PROFILING_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */   
/* 1719 */   private StringConnectionProperty clientCertificateKeyStoreUrl = new StringConnectionProperty("clientCertificateKeyStoreUrl", null, Messages.getString("ConnectionProperties.clientCertificateKeyStoreUrl"), "5.1.0", SECURITY_CATEGORY, 5);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1724 */   private StringConnectionProperty trustCertificateKeyStoreUrl = new StringConnectionProperty("trustCertificateKeyStoreUrl", null, Messages.getString("ConnectionProperties.trustCertificateKeyStoreUrl"), "5.1.0", SECURITY_CATEGORY, 8);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1729 */   private StringConnectionProperty clientCertificateKeyStoreType = new StringConnectionProperty("clientCertificateKeyStoreType", "JKS", Messages.getString("ConnectionProperties.clientCertificateKeyStoreType"), "5.1.0", SECURITY_CATEGORY, 6);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1734 */   private StringConnectionProperty clientCertificateKeyStorePassword = new StringConnectionProperty("clientCertificateKeyStorePassword", null, Messages.getString("ConnectionProperties.clientCertificateKeyStorePassword"), "5.1.0", SECURITY_CATEGORY, 7);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1739 */   private StringConnectionProperty trustCertificateKeyStoreType = new StringConnectionProperty("trustCertificateKeyStoreType", "JKS", Messages.getString("ConnectionProperties.trustCertificateKeyStoreType"), "5.1.0", SECURITY_CATEGORY, 9);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1744 */   private StringConnectionProperty trustCertificateKeyStorePassword = new StringConnectionProperty("trustCertificateKeyStorePassword", null, Messages.getString("ConnectionProperties.trustCertificateKeyStorePassword"), "5.1.0", SECURITY_CATEGORY, 10);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1749 */   private BooleanConnectionProperty verifyServerCertificate = new BooleanConnectionProperty("verifyServerCertificate", true, Messages.getString("ConnectionProperties.verifyServerCertificate"), "5.1.6", SECURITY_CATEGORY, 4);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1755 */   private BooleanConnectionProperty useAffectedRows = new BooleanConnectionProperty("useAffectedRows", false, Messages.getString("ConnectionProperties.useAffectedRows"), "5.1.7", MISC_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1760 */   private StringConnectionProperty passwordCharacterEncoding = new StringConnectionProperty("passwordCharacterEncoding", null, Messages.getString("ConnectionProperties.passwordCharacterEncoding"), "5.1.7", SECURITY_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/* 1765 */   private IntegerConnectionProperty maxAllowedPacket = new IntegerConnectionProperty("maxAllowedPacket", -1, Messages.getString("ConnectionProperties.maxAllowedPacket"), "5.1.8", NETWORK_CATEGORY, -2147483648);
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected DriverPropertyInfo[] exposeAsDriverPropertyInfoInternal(Properties info, int slotsToReserve) throws SQLException {
/* 1771 */     initializeProperties(info);
/*      */     
/* 1773 */     int numProperties = PROPERTY_LIST.size();
/*      */     
/* 1775 */     int listSize = numProperties + slotsToReserve;
/*      */     
/* 1777 */     DriverPropertyInfo[] driverProperties = new DriverPropertyInfo[listSize];
/*      */     
/* 1779 */     for (int i = slotsToReserve; i < listSize; i++) {
/* 1780 */       Field propertyField = PROPERTY_LIST.get(i - slotsToReserve);
/*      */ 
/*      */       
/*      */       try {
/* 1784 */         ConnectionProperty propToExpose = (ConnectionProperty)propertyField.get(this);
/*      */ 
/*      */         
/* 1787 */         if (info != null) {
/* 1788 */           propToExpose.initializeFrom(info);
/*      */         }
/*      */ 
/*      */         
/* 1792 */         driverProperties[i] = propToExpose.getAsDriverPropertyInfo();
/* 1793 */       } catch (IllegalAccessException iae) {
/* 1794 */         throw SQLError.createSQLException(Messages.getString("ConnectionProperties.InternalPropertiesFailure"), "S1000", getExceptionInterceptor());
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1799 */     return driverProperties;
/*      */   }
/*      */ 
/*      */   
/*      */   protected Properties exposeAsProperties(Properties info) throws SQLException {
/* 1804 */     if (info == null) {
/* 1805 */       info = new Properties();
/*      */     }
/*      */     
/* 1808 */     int numPropertiesToSet = PROPERTY_LIST.size();
/*      */     
/* 1810 */     for (int i = 0; i < numPropertiesToSet; i++) {
/* 1811 */       Field propertyField = PROPERTY_LIST.get(i);
/*      */ 
/*      */       
/*      */       try {
/* 1815 */         ConnectionProperty propToGet = (ConnectionProperty)propertyField.get(this);
/*      */ 
/*      */         
/* 1818 */         Object propValue = propToGet.getValueAsObject();
/*      */         
/* 1820 */         if (propValue != null) {
/* 1821 */           info.setProperty(propToGet.getPropertyName(), propValue.toString());
/*      */         }
/*      */       }
/* 1824 */       catch (IllegalAccessException iae) {
/* 1825 */         throw SQLError.createSQLException("Internal properties failure", "S1000", getExceptionInterceptor());
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 1830 */     return info;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String exposeAsXml() throws SQLException {
/* 1837 */     StringBuffer xmlBuf = new StringBuffer();
/* 1838 */     xmlBuf.append("<ConnectionProperties>");
/*      */     
/* 1840 */     int numPropertiesToSet = PROPERTY_LIST.size();
/*      */     
/* 1842 */     int numCategories = PROPERTY_CATEGORIES.length;
/*      */     
/* 1844 */     Map<Object, Object> propertyListByCategory = new HashMap<Object, Object>();
/*      */     
/* 1846 */     for (int i = 0; i < numCategories; i++) {
/* 1847 */       propertyListByCategory.put(PROPERTY_CATEGORIES[i], new Map[] { new TreeMap<Object, Object>(), new TreeMap<Object, Object>() });
/*      */     } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1857 */     StringConnectionProperty userProp = new StringConnectionProperty("user", null, Messages.getString("ConnectionProperties.Username"), Messages.getString("ConnectionProperties.allVersions"), CONNECTION_AND_AUTH_CATEGORY, -2147483647);
/*      */ 
/*      */ 
/*      */     
/* 1861 */     StringConnectionProperty passwordProp = new StringConnectionProperty("password", null, Messages.getString("ConnectionProperties.Password"), Messages.getString("ConnectionProperties.allVersions"), CONNECTION_AND_AUTH_CATEGORY, -2147483646);
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1866 */     Map[] connectionSortMaps = (Map[])propertyListByCategory.get(CONNECTION_AND_AUTH_CATEGORY);
/*      */     
/* 1868 */     TreeMap<Object, Object> userMap = new TreeMap<Object, Object>();
/* 1869 */     userMap.put(userProp.getPropertyName(), userProp);
/*      */     
/* 1871 */     connectionSortMaps[0].put(new Integer(userProp.getOrder()), userMap);
/*      */     
/* 1873 */     TreeMap<Object, Object> passwordMap = new TreeMap<Object, Object>();
/* 1874 */     passwordMap.put(passwordProp.getPropertyName(), passwordProp);
/*      */     
/* 1876 */     connectionSortMaps[0].put(new Integer(passwordProp.getOrder()), passwordMap);
/*      */ 
/*      */     
/*      */     try {
/* 1880 */       for (int k = 0; k < numPropertiesToSet; k++) {
/* 1881 */         Field propertyField = PROPERTY_LIST.get(k);
/*      */         
/* 1883 */         ConnectionProperty propToGet = (ConnectionProperty)propertyField.get(this);
/*      */         
/* 1885 */         Map[] sortMaps = (Map[])propertyListByCategory.get(propToGet.getCategoryName());
/*      */         
/* 1887 */         int orderInCategory = propToGet.getOrder();
/*      */         
/* 1889 */         if (orderInCategory == Integer.MIN_VALUE) {
/* 1890 */           sortMaps[1].put(propToGet.getPropertyName(), propToGet);
/*      */         } else {
/* 1892 */           Integer order = new Integer(orderInCategory);
/*      */           
/* 1894 */           Map<Object, Object> orderMap = sortMaps[0].get(order);
/*      */           
/* 1896 */           if (orderMap == null) {
/* 1897 */             orderMap = new TreeMap<Object, Object>();
/* 1898 */             sortMaps[0].put(order, orderMap);
/*      */           } 
/*      */           
/* 1901 */           orderMap.put(propToGet.getPropertyName(), propToGet);
/*      */         } 
/*      */       } 
/*      */       
/* 1905 */       for (int j = 0; j < numCategories; j++) {
/* 1906 */         Map[] sortMaps = (Map[])propertyListByCategory.get(PROPERTY_CATEGORIES[j]);
/*      */         
/* 1908 */         Iterator<Map> orderedIter = sortMaps[0].values().iterator();
/* 1909 */         Iterator<ConnectionProperty> alphaIter = sortMaps[1].values().iterator();
/*      */         
/* 1911 */         xmlBuf.append("\n <PropertyCategory name=\"");
/* 1912 */         xmlBuf.append(PROPERTY_CATEGORIES[j]);
/* 1913 */         xmlBuf.append("\">");
/*      */         
/* 1915 */         while (orderedIter.hasNext()) {
/* 1916 */           Iterator<ConnectionProperty> orderedAlphaIter = ((Map)orderedIter.next()).values().iterator();
/*      */           
/* 1918 */           while (orderedAlphaIter.hasNext()) {
/* 1919 */             ConnectionProperty propToGet = orderedAlphaIter.next();
/*      */ 
/*      */             
/* 1922 */             xmlBuf.append("\n  <Property name=\"");
/* 1923 */             xmlBuf.append(propToGet.getPropertyName());
/* 1924 */             xmlBuf.append("\" required=\"");
/* 1925 */             xmlBuf.append(propToGet.required ? "Yes" : "No");
/*      */             
/* 1927 */             xmlBuf.append("\" default=\"");
/*      */             
/* 1929 */             if (propToGet.getDefaultValue() != null) {
/* 1930 */               xmlBuf.append(propToGet.getDefaultValue());
/*      */             }
/*      */             
/* 1933 */             xmlBuf.append("\" sortOrder=\"");
/* 1934 */             xmlBuf.append(propToGet.getOrder());
/* 1935 */             xmlBuf.append("\" since=\"");
/* 1936 */             xmlBuf.append(propToGet.sinceVersion);
/* 1937 */             xmlBuf.append("\">\n");
/* 1938 */             xmlBuf.append("    ");
/* 1939 */             xmlBuf.append(propToGet.description);
/* 1940 */             xmlBuf.append("\n  </Property>");
/*      */           } 
/*      */         } 
/*      */         
/* 1944 */         while (alphaIter.hasNext()) {
/* 1945 */           ConnectionProperty propToGet = alphaIter.next();
/*      */ 
/*      */           
/* 1948 */           xmlBuf.append("\n  <Property name=\"");
/* 1949 */           xmlBuf.append(propToGet.getPropertyName());
/* 1950 */           xmlBuf.append("\" required=\"");
/* 1951 */           xmlBuf.append(propToGet.required ? "Yes" : "No");
/*      */           
/* 1953 */           xmlBuf.append("\" default=\"");
/*      */           
/* 1955 */           if (propToGet.getDefaultValue() != null) {
/* 1956 */             xmlBuf.append(propToGet.getDefaultValue());
/*      */           }
/*      */           
/* 1959 */           xmlBuf.append("\" sortOrder=\"alpha\" since=\"");
/* 1960 */           xmlBuf.append(propToGet.sinceVersion);
/* 1961 */           xmlBuf.append("\">\n");
/* 1962 */           xmlBuf.append("    ");
/* 1963 */           xmlBuf.append(propToGet.description);
/* 1964 */           xmlBuf.append("\n  </Property>");
/*      */         } 
/*      */         
/* 1967 */         xmlBuf.append("\n </PropertyCategory>");
/*      */       } 
/* 1969 */     } catch (IllegalAccessException iae) {
/* 1970 */       throw SQLError.createSQLException("Internal properties failure", "S1000", getExceptionInterceptor());
/*      */     } 
/*      */ 
/*      */     
/* 1974 */     xmlBuf.append("\n</ConnectionProperties>");
/*      */     
/* 1976 */     return xmlBuf.toString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getAllowLoadLocalInfile() {
/* 1983 */     return this.allowLoadLocalInfile.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getAllowMultiQueries() {
/* 1990 */     return this.allowMultiQueries.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getAllowNanAndInf() {
/* 1997 */     return this.allowNanAndInf.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getAllowUrlInLocalInfile() {
/* 2004 */     return this.allowUrlInLocalInfile.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getAlwaysSendSetIsolation() {
/* 2011 */     return this.alwaysSendSetIsolation.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getAutoDeserialize() {
/* 2018 */     return this.autoDeserialize.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getAutoGenerateTestcaseScript() {
/* 2025 */     return this.autoGenerateTestcaseScriptAsBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getAutoReconnectForPools() {
/* 2032 */     return this.autoReconnectForPoolsAsBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getBlobSendChunkSize() {
/* 2039 */     return this.blobSendChunkSize.getValueAsInt();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getCacheCallableStatements() {
/* 2046 */     return this.cacheCallableStatements.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getCachePreparedStatements() {
/* 2053 */     return ((Boolean)this.cachePreparedStatements.getValueAsObject()).booleanValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getCacheResultSetMetadata() {
/* 2061 */     return this.cacheResultSetMetaDataAsBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getCacheServerConfiguration() {
/* 2068 */     return this.cacheServerConfiguration.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getCallableStatementCacheSize() {
/* 2075 */     return this.callableStatementCacheSize.getValueAsInt();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getCapitalizeTypeNames() {
/* 2082 */     return this.capitalizeTypeNames.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getCharacterSetResults() {
/* 2089 */     return this.characterSetResults.getValueAsString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getClobberStreamingResults() {
/* 2096 */     return this.clobberStreamingResults.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getClobCharacterEncoding() {
/* 2103 */     return this.clobCharacterEncoding.getValueAsString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getConnectionCollation() {
/* 2110 */     return this.connectionCollation.getValueAsString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getConnectTimeout() {
/* 2117 */     return this.connectTimeout.getValueAsInt();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getContinueBatchOnError() {
/* 2124 */     return this.continueBatchOnError.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getCreateDatabaseIfNotExist() {
/* 2131 */     return this.createDatabaseIfNotExist.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getDefaultFetchSize() {
/* 2138 */     return this.defaultFetchSize.getValueAsInt();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getDontTrackOpenResources() {
/* 2145 */     return this.dontTrackOpenResources.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getDumpQueriesOnException() {
/* 2152 */     return this.dumpQueriesOnException.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getDynamicCalendars() {
/* 2159 */     return this.dynamicCalendars.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getElideSetAutoCommits() {
/* 2166 */     return this.elideSetAutoCommits.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getEmptyStringsConvertToZero() {
/* 2173 */     return this.emptyStringsConvertToZero.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getEmulateLocators() {
/* 2180 */     return this.emulateLocators.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getEmulateUnsupportedPstmts() {
/* 2187 */     return this.emulateUnsupportedPstmts.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getEnablePacketDebug() {
/* 2194 */     return this.enablePacketDebug.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getEncoding() {
/* 2201 */     return this.characterEncodingAsString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getExplainSlowQueries() {
/* 2208 */     return this.explainSlowQueries.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getFailOverReadOnly() {
/* 2215 */     return this.failOverReadOnly.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getGatherPerformanceMetrics() {
/* 2222 */     return this.gatherPerformanceMetrics.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected boolean getHighAvailability() {
/* 2231 */     return this.highAvailabilityAsBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getHoldResultsOpenOverStatementClose() {
/* 2238 */     return this.holdResultsOpenOverStatementClose.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getIgnoreNonTxTables() {
/* 2245 */     return this.ignoreNonTxTables.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getInitialTimeout() {
/* 2252 */     return this.initialTimeout.getValueAsInt();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getInteractiveClient() {
/* 2259 */     return this.isInteractiveClient.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getIsInteractiveClient() {
/* 2266 */     return this.isInteractiveClient.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getJdbcCompliantTruncation() {
/* 2273 */     return this.jdbcCompliantTruncation.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getLocatorFetchBufferSize() {
/* 2280 */     return this.locatorFetchBufferSize.getValueAsInt();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getLogger() {
/* 2287 */     return this.loggerClassName.getValueAsString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getLoggerClassName() {
/* 2294 */     return this.loggerClassName.getValueAsString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getLogSlowQueries() {
/* 2301 */     return this.logSlowQueries.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getMaintainTimeStats() {
/* 2308 */     return this.maintainTimeStatsAsBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxQuerySizeToLog() {
/* 2315 */     return this.maxQuerySizeToLog.getValueAsInt();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxReconnects() {
/* 2322 */     return this.maxReconnects.getValueAsInt();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaxRows() {
/* 2329 */     return this.maxRowsAsInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMetadataCacheSize() {
/* 2336 */     return this.metadataCacheSize.getValueAsInt();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getNoDatetimeStringSync() {
/* 2343 */     return this.noDatetimeStringSync.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getNullCatalogMeansCurrent() {
/* 2350 */     return this.nullCatalogMeansCurrent.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getNullNamePatternMatchesAll() {
/* 2357 */     return this.nullNamePatternMatchesAll.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getPacketDebugBufferSize() {
/* 2364 */     return this.packetDebugBufferSize.getValueAsInt();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getParanoid() {
/* 2371 */     return this.paranoid.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getPedantic() {
/* 2378 */     return this.pedantic.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getPreparedStatementCacheSize() {
/* 2385 */     return ((Integer)this.preparedStatementCacheSize.getValueAsObject()).intValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getPreparedStatementCacheSqlLimit() {
/* 2393 */     return ((Integer)this.preparedStatementCacheSqlLimit.getValueAsObject()).intValue();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getProfileSql() {
/* 2401 */     return this.profileSQLAsBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getProfileSQL() {
/* 2408 */     return this.profileSQL.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPropertiesTransform() {
/* 2415 */     return this.propertiesTransform.getValueAsString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getQueriesBeforeRetryMaster() {
/* 2422 */     return this.queriesBeforeRetryMaster.getValueAsInt();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getReconnectAtTxEnd() {
/* 2429 */     return this.reconnectTxAtEndAsBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getRelaxAutoCommit() {
/* 2436 */     return this.relaxAutoCommit.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getReportMetricsIntervalMillis() {
/* 2443 */     return this.reportMetricsIntervalMillis.getValueAsInt();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getRequireSSL() {
/* 2450 */     return this.requireSSL.getValueAsBoolean();
/*      */   }
/*      */   
/*      */   public boolean getRetainStatementAfterResultSetClose() {
/* 2454 */     return this.retainStatementAfterResultSetClose.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getRollbackOnPooledClose() {
/* 2461 */     return this.rollbackOnPooledClose.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getRoundRobinLoadBalance() {
/* 2468 */     return this.roundRobinLoadBalance.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getRunningCTS13() {
/* 2475 */     return this.runningCTS13.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSecondsBeforeRetryMaster() {
/* 2482 */     return this.secondsBeforeRetryMaster.getValueAsInt();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getServerTimezone() {
/* 2489 */     return this.serverTimezone.getValueAsString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getSessionVariables() {
/* 2496 */     return this.sessionVariables.getValueAsString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSlowQueryThresholdMillis() {
/* 2503 */     return this.slowQueryThresholdMillis.getValueAsInt();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getSocketFactoryClassName() {
/* 2510 */     return this.socketFactoryClassName.getValueAsString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getSocketTimeout() {
/* 2517 */     return this.socketTimeout.getValueAsInt();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getStrictFloatingPoint() {
/* 2524 */     return this.strictFloatingPoint.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getStrictUpdates() {
/* 2531 */     return this.strictUpdates.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getTinyInt1isBit() {
/* 2538 */     return this.tinyInt1isBit.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getTraceProtocol() {
/* 2545 */     return this.traceProtocol.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getTransformedBitIsBoolean() {
/* 2552 */     return this.transformedBitIsBoolean.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getUseCompression() {
/* 2559 */     return this.useCompression.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getUseFastIntParsing() {
/* 2566 */     return this.useFastIntParsing.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getUseHostsInPrivileges() {
/* 2573 */     return this.useHostsInPrivileges.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getUseInformationSchema() {
/* 2580 */     return this.useInformationSchema.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getUseLocalSessionState() {
/* 2587 */     return this.useLocalSessionState.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getUseOldUTF8Behavior() {
/* 2594 */     return this.useOldUTF8BehaviorAsBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getUseOnlyServerErrorMessages() {
/* 2601 */     return this.useOnlyServerErrorMessages.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getUseReadAheadInput() {
/* 2608 */     return this.useReadAheadInput.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getUseServerPreparedStmts() {
/* 2615 */     return this.detectServerPreparedStmts.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getUseSqlStateCodes() {
/* 2622 */     return this.useSqlStateCodes.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getUseSSL() {
/* 2629 */     return this.useSSL.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getUseStreamLengthsInPrepStmts() {
/* 2636 */     return this.useStreamLengthsInPrepStmts.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getUseTimezone() {
/* 2643 */     return this.useTimezone.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getUseUltraDevWorkAround() {
/* 2650 */     return this.useUltraDevWorkAround.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getUseUnbufferedInput() {
/* 2657 */     return this.useUnbufferedInput.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getUseUnicode() {
/* 2664 */     return this.useUnicodeAsBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getUseUsageAdvisor() {
/* 2671 */     return this.useUsageAdvisorAsBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getYearIsDateType() {
/* 2678 */     return this.yearIsDateType.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getZeroDateTimeBehavior() {
/* 2685 */     return this.zeroDateTimeBehavior.getValueAsString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void initializeFromRef(Reference ref) throws SQLException {
/* 2699 */     int numPropertiesToSet = PROPERTY_LIST.size();
/*      */     
/* 2701 */     for (int i = 0; i < numPropertiesToSet; i++) {
/* 2702 */       Field propertyField = PROPERTY_LIST.get(i);
/*      */ 
/*      */       
/*      */       try {
/* 2706 */         ConnectionProperty propToSet = (ConnectionProperty)propertyField.get(this);
/*      */ 
/*      */         
/* 2709 */         if (ref != null) {
/* 2710 */           propToSet.initializeFrom(ref);
/*      */         }
/* 2712 */       } catch (IllegalAccessException iae) {
/* 2713 */         throw SQLError.createSQLException("Internal properties failure", "S1000", getExceptionInterceptor());
/*      */       } 
/*      */     } 
/*      */ 
/*      */     
/* 2718 */     postInitialization();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void initializeProperties(Properties info) throws SQLException {
/* 2731 */     if (info != null) {
/*      */       
/* 2733 */       String profileSqlLc = info.getProperty("profileSql");
/*      */       
/* 2735 */       if (profileSqlLc != null) {
/* 2736 */         info.put("profileSQL", profileSqlLc);
/*      */       }
/*      */       
/* 2739 */       Properties infoCopy = (Properties)info.clone();
/*      */       
/* 2741 */       infoCopy.remove("HOST");
/* 2742 */       infoCopy.remove("user");
/* 2743 */       infoCopy.remove("password");
/* 2744 */       infoCopy.remove("DBNAME");
/* 2745 */       infoCopy.remove("PORT");
/* 2746 */       infoCopy.remove("profileSql");
/*      */       
/* 2748 */       int numPropertiesToSet = PROPERTY_LIST.size();
/*      */       
/* 2750 */       for (int i = 0; i < numPropertiesToSet; i++) {
/* 2751 */         Field propertyField = PROPERTY_LIST.get(i);
/*      */ 
/*      */         
/*      */         try {
/* 2755 */           ConnectionProperty propToSet = (ConnectionProperty)propertyField.get(this);
/*      */ 
/*      */           
/* 2758 */           propToSet.initializeFrom(infoCopy);
/* 2759 */         } catch (IllegalAccessException iae) {
/* 2760 */           throw SQLError.createSQLException(Messages.getString("ConnectionProperties.unableToInitDriverProperties") + iae.toString(), "S1000", getExceptionInterceptor());
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 2767 */       postInitialization();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void postInitialization() throws SQLException {
/* 2774 */     if (this.profileSql.getValueAsObject() != null) {
/* 2775 */       this.profileSQL.initializeFrom(this.profileSql.getValueAsObject().toString());
/*      */     }
/*      */ 
/*      */     
/* 2779 */     this.reconnectTxAtEndAsBoolean = ((Boolean)this.reconnectAtTxEnd.getValueAsObject()).booleanValue();
/*      */ 
/*      */ 
/*      */     
/* 2783 */     if (getMaxRows() == 0)
/*      */     {
/*      */       
/* 2786 */       this.maxRows.setValueAsObject(Constants.integerValueOf(-1));
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2792 */     String testEncoding = getEncoding();
/*      */     
/* 2794 */     if (testEncoding != null) {
/*      */       
/*      */       try {
/*      */         
/* 2798 */         String testString = "abc";
/* 2799 */         testString.getBytes(testEncoding);
/* 2800 */       } catch (UnsupportedEncodingException UE) {
/* 2801 */         throw SQLError.createSQLException(Messages.getString("ConnectionProperties.unsupportedCharacterEncoding", new Object[] { testEncoding }), "0S100", getExceptionInterceptor());
/*      */       } 
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 2810 */     if (((Boolean)this.cacheResultSetMetadata.getValueAsObject()).booleanValue()) {
/*      */       
/*      */       try {
/* 2813 */         Class.forName("java.util.LinkedHashMap");
/* 2814 */       } catch (ClassNotFoundException cnfe) {
/* 2815 */         this.cacheResultSetMetadata.setValue(false);
/*      */       } 
/*      */     }
/*      */     
/* 2819 */     this.cacheResultSetMetaDataAsBoolean = this.cacheResultSetMetadata.getValueAsBoolean();
/*      */     
/* 2821 */     this.useUnicodeAsBoolean = this.useUnicode.getValueAsBoolean();
/* 2822 */     this.characterEncodingAsString = (String)this.characterEncoding.getValueAsObject();
/*      */     
/* 2824 */     this.highAvailabilityAsBoolean = this.autoReconnect.getValueAsBoolean();
/* 2825 */     this.autoReconnectForPoolsAsBoolean = this.autoReconnectForPools.getValueAsBoolean();
/*      */     
/* 2827 */     this.maxRowsAsInt = ((Integer)this.maxRows.getValueAsObject()).intValue();
/*      */     
/* 2829 */     this.profileSQLAsBoolean = this.profileSQL.getValueAsBoolean();
/* 2830 */     this.useUsageAdvisorAsBoolean = this.useUsageAdvisor.getValueAsBoolean();
/*      */     
/* 2832 */     this.useOldUTF8BehaviorAsBoolean = this.useOldUTF8Behavior.getValueAsBoolean();
/*      */     
/* 2834 */     this.autoGenerateTestcaseScriptAsBoolean = this.autoGenerateTestcaseScript.getValueAsBoolean();
/*      */     
/* 2836 */     this.maintainTimeStatsAsBoolean = this.maintainTimeStats.getValueAsBoolean();
/*      */     
/* 2838 */     this.jdbcCompliantTruncationForReads = getJdbcCompliantTruncation();
/*      */     
/* 2840 */     if (getUseCursorFetch())
/*      */     {
/*      */       
/* 2843 */       setDetectServerPreparedStmts(true);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAllowLoadLocalInfile(boolean property) {
/* 2851 */     this.allowLoadLocalInfile.setValue(property);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAllowMultiQueries(boolean property) {
/* 2858 */     this.allowMultiQueries.setValue(property);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAllowNanAndInf(boolean flag) {
/* 2865 */     this.allowNanAndInf.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAllowUrlInLocalInfile(boolean flag) {
/* 2872 */     this.allowUrlInLocalInfile.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAlwaysSendSetIsolation(boolean flag) {
/* 2879 */     this.alwaysSendSetIsolation.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAutoDeserialize(boolean flag) {
/* 2886 */     this.autoDeserialize.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAutoGenerateTestcaseScript(boolean flag) {
/* 2893 */     this.autoGenerateTestcaseScript.setValue(flag);
/* 2894 */     this.autoGenerateTestcaseScriptAsBoolean = this.autoGenerateTestcaseScript.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAutoReconnect(boolean flag) {
/* 2902 */     this.autoReconnect.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAutoReconnectForConnectionPools(boolean property) {
/* 2909 */     this.autoReconnectForPools.setValue(property);
/* 2910 */     this.autoReconnectForPoolsAsBoolean = this.autoReconnectForPools.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAutoReconnectForPools(boolean flag) {
/* 2918 */     this.autoReconnectForPools.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setBlobSendChunkSize(String value) throws SQLException {
/* 2925 */     this.blobSendChunkSize.setValue(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCacheCallableStatements(boolean flag) {
/* 2932 */     this.cacheCallableStatements.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCachePreparedStatements(boolean flag) {
/* 2939 */     this.cachePreparedStatements.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCacheResultSetMetadata(boolean property) {
/* 2946 */     this.cacheResultSetMetadata.setValue(property);
/* 2947 */     this.cacheResultSetMetaDataAsBoolean = this.cacheResultSetMetadata.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCacheServerConfiguration(boolean flag) {
/* 2955 */     this.cacheServerConfiguration.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCallableStatementCacheSize(int size) {
/* 2962 */     this.callableStatementCacheSize.setValue(size);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCapitalizeDBMDTypes(boolean property) {
/* 2969 */     this.capitalizeTypeNames.setValue(property);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCapitalizeTypeNames(boolean flag) {
/* 2976 */     this.capitalizeTypeNames.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCharacterEncoding(String encoding) {
/* 2983 */     this.characterEncoding.setValue(encoding);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCharacterSetResults(String characterSet) {
/* 2990 */     this.characterSetResults.setValue(characterSet);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setClobberStreamingResults(boolean flag) {
/* 2997 */     this.clobberStreamingResults.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setClobCharacterEncoding(String encoding) {
/* 3004 */     this.clobCharacterEncoding.setValue(encoding);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setConnectionCollation(String collation) {
/* 3011 */     this.connectionCollation.setValue(collation);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setConnectTimeout(int timeoutMs) {
/* 3018 */     this.connectTimeout.setValue(timeoutMs);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setContinueBatchOnError(boolean property) {
/* 3025 */     this.continueBatchOnError.setValue(property);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCreateDatabaseIfNotExist(boolean flag) {
/* 3032 */     this.createDatabaseIfNotExist.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDefaultFetchSize(int n) {
/* 3039 */     this.defaultFetchSize.setValue(n);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDetectServerPreparedStmts(boolean property) {
/* 3046 */     this.detectServerPreparedStmts.setValue(property);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDontTrackOpenResources(boolean flag) {
/* 3053 */     this.dontTrackOpenResources.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDumpQueriesOnException(boolean flag) {
/* 3060 */     this.dumpQueriesOnException.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDynamicCalendars(boolean flag) {
/* 3067 */     this.dynamicCalendars.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setElideSetAutoCommits(boolean flag) {
/* 3074 */     this.elideSetAutoCommits.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEmptyStringsConvertToZero(boolean flag) {
/* 3081 */     this.emptyStringsConvertToZero.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEmulateLocators(boolean property) {
/* 3088 */     this.emulateLocators.setValue(property);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEmulateUnsupportedPstmts(boolean flag) {
/* 3095 */     this.emulateUnsupportedPstmts.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEnablePacketDebug(boolean flag) {
/* 3102 */     this.enablePacketDebug.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEncoding(String property) {
/* 3109 */     this.characterEncoding.setValue(property);
/* 3110 */     this.characterEncodingAsString = this.characterEncoding.getValueAsString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setExplainSlowQueries(boolean flag) {
/* 3118 */     this.explainSlowQueries.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setFailOverReadOnly(boolean flag) {
/* 3125 */     this.failOverReadOnly.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setGatherPerformanceMetrics(boolean flag) {
/* 3132 */     this.gatherPerformanceMetrics.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void setHighAvailability(boolean property) {
/* 3141 */     this.autoReconnect.setValue(property);
/* 3142 */     this.highAvailabilityAsBoolean = this.autoReconnect.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setHoldResultsOpenOverStatementClose(boolean flag) {
/* 3149 */     this.holdResultsOpenOverStatementClose.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setIgnoreNonTxTables(boolean property) {
/* 3156 */     this.ignoreNonTxTables.setValue(property);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setInitialTimeout(int property) {
/* 3163 */     this.initialTimeout.setValue(property);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setIsInteractiveClient(boolean property) {
/* 3170 */     this.isInteractiveClient.setValue(property);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setJdbcCompliantTruncation(boolean flag) {
/* 3177 */     this.jdbcCompliantTruncation.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLocatorFetchBufferSize(String value) throws SQLException {
/* 3184 */     this.locatorFetchBufferSize.setValue(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLogger(String property) {
/* 3191 */     this.loggerClassName.setValueAsObject(property);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLoggerClassName(String className) {
/* 3198 */     this.loggerClassName.setValue(className);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLogSlowQueries(boolean flag) {
/* 3205 */     this.logSlowQueries.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMaintainTimeStats(boolean flag) {
/* 3212 */     this.maintainTimeStats.setValue(flag);
/* 3213 */     this.maintainTimeStatsAsBoolean = this.maintainTimeStats.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMaxQuerySizeToLog(int sizeInBytes) {
/* 3221 */     this.maxQuerySizeToLog.setValue(sizeInBytes);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMaxReconnects(int property) {
/* 3228 */     this.maxReconnects.setValue(property);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMaxRows(int property) {
/* 3235 */     this.maxRows.setValue(property);
/* 3236 */     this.maxRowsAsInt = this.maxRows.getValueAsInt();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMetadataCacheSize(int value) {
/* 3243 */     this.metadataCacheSize.setValue(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNoDatetimeStringSync(boolean flag) {
/* 3250 */     this.noDatetimeStringSync.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNullCatalogMeansCurrent(boolean value) {
/* 3257 */     this.nullCatalogMeansCurrent.setValue(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNullNamePatternMatchesAll(boolean value) {
/* 3264 */     this.nullNamePatternMatchesAll.setValue(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPacketDebugBufferSize(int size) {
/* 3271 */     this.packetDebugBufferSize.setValue(size);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setParanoid(boolean property) {
/* 3278 */     this.paranoid.setValue(property);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPedantic(boolean property) {
/* 3285 */     this.pedantic.setValue(property);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPreparedStatementCacheSize(int cacheSize) {
/* 3292 */     this.preparedStatementCacheSize.setValue(cacheSize);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPreparedStatementCacheSqlLimit(int cacheSqlLimit) {
/* 3299 */     this.preparedStatementCacheSqlLimit.setValue(cacheSqlLimit);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setProfileSql(boolean property) {
/* 3306 */     this.profileSQL.setValue(property);
/* 3307 */     this.profileSQLAsBoolean = this.profileSQL.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setProfileSQL(boolean flag) {
/* 3314 */     this.profileSQL.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPropertiesTransform(String value) {
/* 3321 */     this.propertiesTransform.setValue(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setQueriesBeforeRetryMaster(int property) {
/* 3328 */     this.queriesBeforeRetryMaster.setValue(property);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setReconnectAtTxEnd(boolean property) {
/* 3335 */     this.reconnectAtTxEnd.setValue(property);
/* 3336 */     this.reconnectTxAtEndAsBoolean = this.reconnectAtTxEnd.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRelaxAutoCommit(boolean property) {
/* 3344 */     this.relaxAutoCommit.setValue(property);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setReportMetricsIntervalMillis(int millis) {
/* 3351 */     this.reportMetricsIntervalMillis.setValue(millis);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRequireSSL(boolean property) {
/* 3358 */     this.requireSSL.setValue(property);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRetainStatementAfterResultSetClose(boolean flag) {
/* 3365 */     this.retainStatementAfterResultSetClose.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRollbackOnPooledClose(boolean flag) {
/* 3372 */     this.rollbackOnPooledClose.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRoundRobinLoadBalance(boolean flag) {
/* 3379 */     this.roundRobinLoadBalance.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRunningCTS13(boolean flag) {
/* 3386 */     this.runningCTS13.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSecondsBeforeRetryMaster(int property) {
/* 3393 */     this.secondsBeforeRetryMaster.setValue(property);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setServerTimezone(String property) {
/* 3400 */     this.serverTimezone.setValue(property);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSessionVariables(String variables) {
/* 3407 */     this.sessionVariables.setValue(variables);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSlowQueryThresholdMillis(int millis) {
/* 3414 */     this.slowQueryThresholdMillis.setValue(millis);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSocketFactoryClassName(String property) {
/* 3421 */     this.socketFactoryClassName.setValue(property);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSocketTimeout(int property) {
/* 3428 */     this.socketTimeout.setValue(property);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setStrictFloatingPoint(boolean property) {
/* 3435 */     this.strictFloatingPoint.setValue(property);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setStrictUpdates(boolean property) {
/* 3442 */     this.strictUpdates.setValue(property);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTinyInt1isBit(boolean flag) {
/* 3449 */     this.tinyInt1isBit.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTraceProtocol(boolean flag) {
/* 3456 */     this.traceProtocol.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTransformedBitIsBoolean(boolean flag) {
/* 3463 */     this.transformedBitIsBoolean.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseCompression(boolean property) {
/* 3470 */     this.useCompression.setValue(property);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseFastIntParsing(boolean flag) {
/* 3477 */     this.useFastIntParsing.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseHostsInPrivileges(boolean property) {
/* 3484 */     this.useHostsInPrivileges.setValue(property);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseInformationSchema(boolean flag) {
/* 3491 */     this.useInformationSchema.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseLocalSessionState(boolean flag) {
/* 3498 */     this.useLocalSessionState.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseOldUTF8Behavior(boolean flag) {
/* 3505 */     this.useOldUTF8Behavior.setValue(flag);
/* 3506 */     this.useOldUTF8BehaviorAsBoolean = this.useOldUTF8Behavior.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseOnlyServerErrorMessages(boolean flag) {
/* 3514 */     this.useOnlyServerErrorMessages.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseReadAheadInput(boolean flag) {
/* 3521 */     this.useReadAheadInput.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseServerPreparedStmts(boolean flag) {
/* 3528 */     this.detectServerPreparedStmts.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseSqlStateCodes(boolean flag) {
/* 3535 */     this.useSqlStateCodes.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseSSL(boolean property) {
/* 3542 */     this.useSSL.setValue(property);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseStreamLengthsInPrepStmts(boolean property) {
/* 3549 */     this.useStreamLengthsInPrepStmts.setValue(property);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseTimezone(boolean property) {
/* 3556 */     this.useTimezone.setValue(property);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseUltraDevWorkAround(boolean property) {
/* 3563 */     this.useUltraDevWorkAround.setValue(property);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseUnbufferedInput(boolean flag) {
/* 3570 */     this.useUnbufferedInput.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseUnicode(boolean flag) {
/* 3577 */     this.useUnicode.setValue(flag);
/* 3578 */     this.useUnicodeAsBoolean = this.useUnicode.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseUsageAdvisor(boolean useUsageAdvisorFlag) {
/* 3585 */     this.useUsageAdvisor.setValue(useUsageAdvisorFlag);
/* 3586 */     this.useUsageAdvisorAsBoolean = this.useUsageAdvisor.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setYearIsDateType(boolean flag) {
/* 3594 */     this.yearIsDateType.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setZeroDateTimeBehavior(String behavior) {
/* 3601 */     this.zeroDateTimeBehavior.setValue(behavior);
/*      */   }
/*      */   
/*      */   protected void storeToRef(Reference ref) throws SQLException {
/* 3605 */     int numPropertiesToSet = PROPERTY_LIST.size();
/*      */     
/* 3607 */     for (int i = 0; i < numPropertiesToSet; i++) {
/* 3608 */       Field propertyField = PROPERTY_LIST.get(i);
/*      */ 
/*      */       
/*      */       try {
/* 3612 */         ConnectionProperty propToStore = (ConnectionProperty)propertyField.get(this);
/*      */ 
/*      */         
/* 3615 */         if (ref != null) {
/* 3616 */           propToStore.storeTo(ref);
/*      */         }
/* 3618 */       } catch (IllegalAccessException iae) {
/* 3619 */         throw SQLError.createSQLException(Messages.getString("ConnectionProperties.errorNotExpected"), getExceptionInterceptor());
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean useUnbufferedInput() {
/* 3628 */     return this.useUnbufferedInput.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getUseCursorFetch() {
/* 3635 */     return this.useCursorFetch.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseCursorFetch(boolean flag) {
/* 3642 */     this.useCursorFetch.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getOverrideSupportsIntegrityEnhancementFacility() {
/* 3649 */     return this.overrideSupportsIntegrityEnhancementFacility.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setOverrideSupportsIntegrityEnhancementFacility(boolean flag) {
/* 3656 */     this.overrideSupportsIntegrityEnhancementFacility.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getNoTimezoneConversionForTimeType() {
/* 3663 */     return this.noTimezoneConversionForTimeType.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNoTimezoneConversionForTimeType(boolean flag) {
/* 3670 */     this.noTimezoneConversionForTimeType.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getUseJDBCCompliantTimezoneShift() {
/* 3677 */     return this.useJDBCCompliantTimezoneShift.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseJDBCCompliantTimezoneShift(boolean flag) {
/* 3684 */     this.useJDBCCompliantTimezoneShift.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getAutoClosePStmtStreams() {
/* 3691 */     return this.autoClosePStmtStreams.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAutoClosePStmtStreams(boolean flag) {
/* 3698 */     this.autoClosePStmtStreams.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getProcessEscapeCodesForPrepStmts() {
/* 3705 */     return this.processEscapeCodesForPrepStmts.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setProcessEscapeCodesForPrepStmts(boolean flag) {
/* 3712 */     this.processEscapeCodesForPrepStmts.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getUseGmtMillisForDatetimes() {
/* 3719 */     return this.useGmtMillisForDatetimes.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseGmtMillisForDatetimes(boolean flag) {
/* 3726 */     this.useGmtMillisForDatetimes.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getDumpMetadataOnColumnNotFound() {
/* 3733 */     return this.dumpMetadataOnColumnNotFound.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDumpMetadataOnColumnNotFound(boolean flag) {
/* 3740 */     this.dumpMetadataOnColumnNotFound.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getResourceId() {
/* 3747 */     return this.resourceId.getValueAsString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setResourceId(String resourceId) {
/* 3754 */     this.resourceId.setValue(resourceId);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getRewriteBatchedStatements() {
/* 3761 */     return this.rewriteBatchedStatements.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRewriteBatchedStatements(boolean flag) {
/* 3768 */     this.rewriteBatchedStatements.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getJdbcCompliantTruncationForReads() {
/* 3775 */     return this.jdbcCompliantTruncationForReads;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setJdbcCompliantTruncationForReads(boolean jdbcCompliantTruncationForReads) {
/* 3783 */     this.jdbcCompliantTruncationForReads = jdbcCompliantTruncationForReads;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getUseJvmCharsetConverters() {
/* 3790 */     return this.useJvmCharsetConverters.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseJvmCharsetConverters(boolean flag) {
/* 3797 */     this.useJvmCharsetConverters.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getPinGlobalTxToPhysicalConnection() {
/* 3804 */     return this.pinGlobalTxToPhysicalConnection.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPinGlobalTxToPhysicalConnection(boolean flag) {
/* 3811 */     this.pinGlobalTxToPhysicalConnection.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setGatherPerfMetrics(boolean flag) {
/* 3823 */     setGatherPerformanceMetrics(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getGatherPerfMetrics() {
/* 3830 */     return getGatherPerformanceMetrics();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUltraDevHack(boolean flag) {
/* 3837 */     setUseUltraDevWorkAround(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getUltraDevHack() {
/* 3844 */     return getUseUltraDevWorkAround();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setInteractiveClient(boolean property) {
/* 3851 */     setIsInteractiveClient(property);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSocketFactory(String name) {
/* 3858 */     setSocketFactoryClassName(name);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getSocketFactory() {
/* 3865 */     return getSocketFactoryClassName();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseServerPrepStmts(boolean flag) {
/* 3872 */     setUseServerPreparedStmts(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getUseServerPrepStmts() {
/* 3879 */     return getUseServerPreparedStmts();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCacheCallableStmts(boolean flag) {
/* 3886 */     setCacheCallableStatements(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getCacheCallableStmts() {
/* 3893 */     return getCacheCallableStatements();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCachePrepStmts(boolean flag) {
/* 3900 */     setCachePreparedStatements(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getCachePrepStmts() {
/* 3907 */     return getCachePreparedStatements();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCallableStmtCacheSize(int cacheSize) {
/* 3914 */     setCallableStatementCacheSize(cacheSize);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getCallableStmtCacheSize() {
/* 3921 */     return getCallableStatementCacheSize();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPrepStmtCacheSize(int cacheSize) {
/* 3928 */     setPreparedStatementCacheSize(cacheSize);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getPrepStmtCacheSize() {
/* 3935 */     return getPreparedStatementCacheSize();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPrepStmtCacheSqlLimit(int sqlLimit) {
/* 3942 */     setPreparedStatementCacheSqlLimit(sqlLimit);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getPrepStmtCacheSqlLimit() {
/* 3949 */     return getPreparedStatementCacheSqlLimit();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getNoAccessToProcedureBodies() {
/* 3956 */     return this.noAccessToProcedureBodies.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNoAccessToProcedureBodies(boolean flag) {
/* 3963 */     this.noAccessToProcedureBodies.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getUseOldAliasMetadataBehavior() {
/* 3970 */     return this.useOldAliasMetadataBehavior.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseOldAliasMetadataBehavior(boolean flag) {
/* 3977 */     this.useOldAliasMetadataBehavior.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getClientCertificateKeyStorePassword() {
/* 3984 */     return this.clientCertificateKeyStorePassword.getValueAsString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setClientCertificateKeyStorePassword(String value) {
/* 3992 */     this.clientCertificateKeyStorePassword.setValue(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getClientCertificateKeyStoreType() {
/* 3999 */     return this.clientCertificateKeyStoreType.getValueAsString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setClientCertificateKeyStoreType(String value) {
/* 4007 */     this.clientCertificateKeyStoreType.setValue(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getClientCertificateKeyStoreUrl() {
/* 4014 */     return this.clientCertificateKeyStoreUrl.getValueAsString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setClientCertificateKeyStoreUrl(String value) {
/* 4022 */     this.clientCertificateKeyStoreUrl.setValue(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getTrustCertificateKeyStorePassword() {
/* 4029 */     return this.trustCertificateKeyStorePassword.getValueAsString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTrustCertificateKeyStorePassword(String value) {
/* 4037 */     this.trustCertificateKeyStorePassword.setValue(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getTrustCertificateKeyStoreType() {
/* 4044 */     return this.trustCertificateKeyStoreType.getValueAsString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTrustCertificateKeyStoreType(String value) {
/* 4052 */     this.trustCertificateKeyStoreType.setValue(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getTrustCertificateKeyStoreUrl() {
/* 4059 */     return this.trustCertificateKeyStoreUrl.getValueAsString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTrustCertificateKeyStoreUrl(String value) {
/* 4067 */     this.trustCertificateKeyStoreUrl.setValue(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getUseSSPSCompatibleTimezoneShift() {
/* 4074 */     return this.useSSPSCompatibleTimezoneShift.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseSSPSCompatibleTimezoneShift(boolean flag) {
/* 4081 */     this.useSSPSCompatibleTimezoneShift.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getTreatUtilDateAsTimestamp() {
/* 4088 */     return this.treatUtilDateAsTimestamp.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTreatUtilDateAsTimestamp(boolean flag) {
/* 4095 */     this.treatUtilDateAsTimestamp.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getUseFastDateParsing() {
/* 4102 */     return this.useFastDateParsing.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseFastDateParsing(boolean flag) {
/* 4109 */     this.useFastDateParsing.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getLocalSocketAddress() {
/* 4116 */     return this.localSocketAddress.getValueAsString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLocalSocketAddress(String address) {
/* 4123 */     this.localSocketAddress.setValue(address);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseConfigs(String configs) {
/* 4130 */     this.useConfigs.setValue(configs);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getUseConfigs() {
/* 4137 */     return this.useConfigs.getValueAsString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getGenerateSimpleParameterMetadata() {
/* 4145 */     return this.generateSimpleParameterMetadata.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setGenerateSimpleParameterMetadata(boolean flag) {
/* 4152 */     this.generateSimpleParameterMetadata.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getLogXaCommands() {
/* 4159 */     return this.logXaCommands.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLogXaCommands(boolean flag) {
/* 4166 */     this.logXaCommands.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getResultSetSizeThreshold() {
/* 4173 */     return this.resultSetSizeThreshold.getValueAsInt();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setResultSetSizeThreshold(int threshold) {
/* 4180 */     this.resultSetSizeThreshold.setValue(threshold);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getNetTimeoutForStreamingResults() {
/* 4187 */     return this.netTimeoutForStreamingResults.getValueAsInt();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setNetTimeoutForStreamingResults(int value) {
/* 4194 */     this.netTimeoutForStreamingResults.setValue(value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getEnableQueryTimeouts() {
/* 4201 */     return this.enableQueryTimeouts.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setEnableQueryTimeouts(boolean flag) {
/* 4208 */     this.enableQueryTimeouts.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getPadCharsWithSpace() {
/* 4215 */     return this.padCharsWithSpace.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPadCharsWithSpace(boolean flag) {
/* 4222 */     this.padCharsWithSpace.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean getUseDynamicCharsetInfo() {
/* 4229 */     return this.useDynamicCharsetInfo.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUseDynamicCharsetInfo(boolean flag) {
/* 4236 */     this.useDynamicCharsetInfo.setValue(flag);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getClientInfoProvider() {
/* 4243 */     return this.clientInfoProvider.getValueAsString();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setClientInfoProvider(String classname) {
/* 4250 */     this.clientInfoProvider.setValue(classname);
/*      */   }
/*      */   
/*      */   public boolean getPopulateInsertRowWithDefaultValues() {
/* 4254 */     return this.populateInsertRowWithDefaultValues.getValueAsBoolean();
/*      */   }
/*      */   
/*      */   public void setPopulateInsertRowWithDefaultValues(boolean flag) {
/* 4258 */     this.populateInsertRowWithDefaultValues.setValue(flag);
/*      */   }
/*      */   
/*      */   public String getLoadBalanceStrategy() {
/* 4262 */     return this.loadBalanceStrategy.getValueAsString();
/*      */   }
/*      */   
/*      */   public void setLoadBalanceStrategy(String strategy) {
/* 4266 */     this.loadBalanceStrategy.setValue(strategy);
/*      */   }
/*      */   
/*      */   public boolean getTcpNoDelay() {
/* 4270 */     return this.tcpNoDelay.getValueAsBoolean();
/*      */   }
/*      */   
/*      */   public void setTcpNoDelay(boolean flag) {
/* 4274 */     this.tcpNoDelay.setValue(flag);
/*      */   }
/*      */   
/*      */   public boolean getTcpKeepAlive() {
/* 4278 */     return this.tcpKeepAlive.getValueAsBoolean();
/*      */   }
/*      */   
/*      */   public void setTcpKeepAlive(boolean flag) {
/* 4282 */     this.tcpKeepAlive.setValue(flag);
/*      */   }
/*      */   
/*      */   public int getTcpRcvBuf() {
/* 4286 */     return this.tcpRcvBuf.getValueAsInt();
/*      */   }
/*      */   
/*      */   public void setTcpRcvBuf(int bufSize) {
/* 4290 */     this.tcpRcvBuf.setValue(bufSize);
/*      */   }
/*      */   
/*      */   public int getTcpSndBuf() {
/* 4294 */     return this.tcpSndBuf.getValueAsInt();
/*      */   }
/*      */   
/*      */   public void setTcpSndBuf(int bufSize) {
/* 4298 */     this.tcpSndBuf.setValue(bufSize);
/*      */   }
/*      */   
/*      */   public int getTcpTrafficClass() {
/* 4302 */     return this.tcpTrafficClass.getValueAsInt();
/*      */   }
/*      */   
/*      */   public void setTcpTrafficClass(int classFlags) {
/* 4306 */     this.tcpTrafficClass.setValue(classFlags);
/*      */   }
/*      */   
/*      */   public boolean getUseNanosForElapsedTime() {
/* 4310 */     return this.useNanosForElapsedTime.getValueAsBoolean();
/*      */   }
/*      */   
/*      */   public void setUseNanosForElapsedTime(boolean flag) {
/* 4314 */     this.useNanosForElapsedTime.setValue(flag);
/*      */   }
/*      */   
/*      */   public long getSlowQueryThresholdNanos() {
/* 4318 */     return this.slowQueryThresholdNanos.getValueAsLong();
/*      */   }
/*      */   
/*      */   public void setSlowQueryThresholdNanos(long nanos) {
/* 4322 */     this.slowQueryThresholdNanos.setValue(nanos);
/*      */   }
/*      */   
/*      */   public String getStatementInterceptors() {
/* 4326 */     return this.statementInterceptors.getValueAsString();
/*      */   }
/*      */   
/*      */   public void setStatementInterceptors(String value) {
/* 4330 */     this.statementInterceptors.setValue(value);
/*      */   }
/*      */   
/*      */   public boolean getUseDirectRowUnpack() {
/* 4334 */     return this.useDirectRowUnpack.getValueAsBoolean();
/*      */   }
/*      */   
/*      */   public void setUseDirectRowUnpack(boolean flag) {
/* 4338 */     this.useDirectRowUnpack.setValue(flag);
/*      */   }
/*      */   
/*      */   public String getLargeRowSizeThreshold() {
/* 4342 */     return this.largeRowSizeThreshold.getValueAsString();
/*      */   }
/*      */   
/*      */   public void setLargeRowSizeThreshold(String value) {
/*      */     try {
/* 4347 */       this.largeRowSizeThreshold.setValue(value);
/* 4348 */     } catch (SQLException sqlEx) {
/* 4349 */       RuntimeException ex = new RuntimeException(sqlEx.getMessage());
/* 4350 */       ex.initCause(sqlEx);
/*      */       
/* 4352 */       throw ex;
/*      */     } 
/*      */   }
/*      */   
/*      */   public boolean getUseBlobToStoreUTF8OutsideBMP() {
/* 4357 */     return this.useBlobToStoreUTF8OutsideBMP.getValueAsBoolean();
/*      */   }
/*      */   
/*      */   public void setUseBlobToStoreUTF8OutsideBMP(boolean flag) {
/* 4361 */     this.useBlobToStoreUTF8OutsideBMP.setValue(flag);
/*      */   }
/*      */   
/*      */   public String getUtf8OutsideBmpExcludedColumnNamePattern() {
/* 4365 */     return this.utf8OutsideBmpExcludedColumnNamePattern.getValueAsString();
/*      */   }
/*      */   
/*      */   public void setUtf8OutsideBmpExcludedColumnNamePattern(String regexPattern) {
/* 4369 */     this.utf8OutsideBmpExcludedColumnNamePattern.setValue(regexPattern);
/*      */   }
/*      */   
/*      */   public String getUtf8OutsideBmpIncludedColumnNamePattern() {
/* 4373 */     return this.utf8OutsideBmpIncludedColumnNamePattern.getValueAsString();
/*      */   }
/*      */   
/*      */   public void setUtf8OutsideBmpIncludedColumnNamePattern(String regexPattern) {
/* 4377 */     this.utf8OutsideBmpIncludedColumnNamePattern.setValue(regexPattern);
/*      */   }
/*      */   
/*      */   public boolean getIncludeInnodbStatusInDeadlockExceptions() {
/* 4381 */     return this.includeInnodbStatusInDeadlockExceptions.getValueAsBoolean();
/*      */   }
/*      */   
/*      */   public void setIncludeInnodbStatusInDeadlockExceptions(boolean flag) {
/* 4385 */     this.includeInnodbStatusInDeadlockExceptions.setValue(flag);
/*      */   }
/*      */   
/*      */   public boolean getBlobsAreStrings() {
/* 4389 */     return this.blobsAreStrings.getValueAsBoolean();
/*      */   }
/*      */   
/*      */   public void setBlobsAreStrings(boolean flag) {
/* 4393 */     this.blobsAreStrings.setValue(flag);
/*      */   }
/*      */   
/*      */   public boolean getFunctionsNeverReturnBlobs() {
/* 4397 */     return this.functionsNeverReturnBlobs.getValueAsBoolean();
/*      */   }
/*      */   
/*      */   public void setFunctionsNeverReturnBlobs(boolean flag) {
/* 4401 */     this.functionsNeverReturnBlobs.setValue(flag);
/*      */   }
/*      */   
/*      */   public boolean getAutoSlowLog() {
/* 4405 */     return this.autoSlowLog.getValueAsBoolean();
/*      */   }
/*      */   
/*      */   public void setAutoSlowLog(boolean flag) {
/* 4409 */     this.autoSlowLog.setValue(flag);
/*      */   }
/*      */   
/*      */   public String getConnectionLifecycleInterceptors() {
/* 4413 */     return this.connectionLifecycleInterceptors.getValueAsString();
/*      */   }
/*      */   
/*      */   public void setConnectionLifecycleInterceptors(String interceptors) {
/* 4417 */     this.connectionLifecycleInterceptors.setValue(interceptors);
/*      */   }
/*      */   
/*      */   public String getProfilerEventHandler() {
/* 4421 */     return this.profilerEventHandler.getValueAsString();
/*      */   }
/*      */   
/*      */   public void setProfilerEventHandler(String handler) {
/* 4425 */     this.profilerEventHandler.setValue(handler);
/*      */   }
/*      */   
/*      */   public boolean getVerifyServerCertificate() {
/* 4429 */     return this.verifyServerCertificate.getValueAsBoolean();
/*      */   }
/*      */   
/*      */   public void setVerifyServerCertificate(boolean flag) {
/* 4433 */     this.verifyServerCertificate.setValue(flag);
/*      */   }
/*      */   
/*      */   public boolean getUseLegacyDatetimeCode() {
/* 4437 */     return this.useLegacyDatetimeCode.getValueAsBoolean();
/*      */   }
/*      */   
/*      */   public void setUseLegacyDatetimeCode(boolean flag) {
/* 4441 */     this.useLegacyDatetimeCode.setValue(flag);
/*      */   }
/*      */   
/*      */   public int getSelfDestructOnPingSecondsLifetime() {
/* 4445 */     return this.selfDestructOnPingSecondsLifetime.getValueAsInt();
/*      */   }
/*      */   
/*      */   public void setSelfDestructOnPingSecondsLifetime(int seconds) {
/* 4449 */     this.selfDestructOnPingSecondsLifetime.setValue(seconds);
/*      */   }
/*      */   
/*      */   public int getSelfDestructOnPingMaxOperations() {
/* 4453 */     return this.selfDestructOnPingMaxOperations.getValueAsInt();
/*      */   }
/*      */   
/*      */   public void setSelfDestructOnPingMaxOperations(int maxOperations) {
/* 4457 */     this.selfDestructOnPingMaxOperations.setValue(maxOperations);
/*      */   }
/*      */   
/*      */   public boolean getUseColumnNamesInFindColumn() {
/* 4461 */     return this.useColumnNamesInFindColumn.getValueAsBoolean();
/*      */   }
/*      */   
/*      */   public void setUseColumnNamesInFindColumn(boolean flag) {
/* 4465 */     this.useColumnNamesInFindColumn.setValue(flag);
/*      */   }
/*      */   
/*      */   public boolean getUseLocalTransactionState() {
/* 4469 */     return this.useLocalTransactionState.getValueAsBoolean();
/*      */   }
/*      */   
/*      */   public void setUseLocalTransactionState(boolean flag) {
/* 4473 */     this.useLocalTransactionState.setValue(flag);
/*      */   }
/*      */   
/*      */   public boolean getCompensateOnDuplicateKeyUpdateCounts() {
/* 4477 */     return this.compensateOnDuplicateKeyUpdateCounts.getValueAsBoolean();
/*      */   }
/*      */   
/*      */   public void setCompensateOnDuplicateKeyUpdateCounts(boolean flag) {
/* 4481 */     this.compensateOnDuplicateKeyUpdateCounts.setValue(flag);
/*      */   }
/*      */   
/*      */   public int getLoadBalanceBlacklistTimeout() {
/* 4485 */     return this.loadBalanceBlacklistTimeout.getValueAsInt();
/*      */   }
/*      */   
/*      */   public void setLoadBalanceBlacklistTimeout(int loadBalanceBlacklistTimeout) {
/* 4489 */     this.loadBalanceBlacklistTimeout.setValue(loadBalanceBlacklistTimeout);
/*      */   }
/*      */   
/*      */   public int getLoadBalancePingTimeout() {
/* 4493 */     return this.loadBalancePingTimeout.getValueAsInt();
/*      */   }
/*      */   
/*      */   public void setLoadBalancePingTimeout(int loadBalancePingTimeout) {
/* 4497 */     this.loadBalancePingTimeout.setValue(loadBalancePingTimeout);
/*      */   }
/*      */   
/*      */   public void setRetriesAllDown(int retriesAllDown) {
/* 4501 */     this.retriesAllDown.setValue(retriesAllDown);
/*      */   }
/*      */   
/*      */   public int getRetriesAllDown() {
/* 4505 */     return this.retriesAllDown.getValueAsInt();
/*      */   }
/*      */   
/*      */   public void setUseAffectedRows(boolean flag) {
/* 4509 */     this.useAffectedRows.setValue(flag);
/*      */   }
/*      */   
/*      */   public boolean getUseAffectedRows() {
/* 4513 */     return this.useAffectedRows.getValueAsBoolean();
/*      */   }
/*      */   
/*      */   public void setPasswordCharacterEncoding(String characterSet) {
/* 4517 */     this.passwordCharacterEncoding.setValue(characterSet);
/*      */   }
/*      */   
/*      */   public String getPasswordCharacterEncoding() {
/* 4521 */     return this.passwordCharacterEncoding.getValueAsString();
/*      */   }
/*      */   
/*      */   public void setExceptionInterceptors(String exceptionInterceptors) {
/* 4525 */     this.exceptionInterceptors.setValue(exceptionInterceptors);
/*      */   }
/*      */   
/*      */   public String getExceptionInterceptors() {
/* 4529 */     return this.exceptionInterceptors.getValueAsString();
/*      */   }
/*      */   
/*      */   public void setMaxAllowedPacket(int max) {
/* 4533 */     this.maxAllowedPacket.setValue(max);
/*      */   }
/*      */   
/*      */   public int getMaxAllowedPacket() {
/* 4537 */     return this.maxAllowedPacket.getValueAsInt();
/*      */   }
/*      */   
/*      */   public boolean getQueryTimeoutKillsConnection() {
/* 4541 */     return this.queryTimeoutKillsConnection.getValueAsBoolean();
/*      */   }
/*      */   
/*      */   public void setQueryTimeoutKillsConnection(boolean queryTimeoutKillsConnection) {
/* 4545 */     this.queryTimeoutKillsConnection.setValue(queryTimeoutKillsConnection);
/*      */   }
/*      */   
/*      */   public boolean getLoadBalanceValidateConnectionOnSwapServer() {
/* 4549 */     return this.loadBalanceValidateConnectionOnSwapServer.getValueAsBoolean();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setLoadBalanceValidateConnectionOnSwapServer(boolean loadBalanceValidateConnectionOnSwapServer) {
/* 4554 */     this.loadBalanceValidateConnectionOnSwapServer.setValue(loadBalanceValidateConnectionOnSwapServer);
/*      */   }
/*      */ 
/*      */   
/*      */   public String getLoadBalanceConnectionGroup() {
/* 4559 */     return this.loadBalanceConnectionGroup.getValueAsString();
/*      */   }
/*      */   
/*      */   public void setLoadBalanceConnectionGroup(String loadBalanceConnectionGroup) {
/* 4563 */     this.loadBalanceConnectionGroup.setValue(loadBalanceConnectionGroup);
/*      */   }
/*      */   
/*      */   public String getLoadBalanceExceptionChecker() {
/* 4567 */     return this.loadBalanceExceptionChecker.getValueAsString();
/*      */   }
/*      */   
/*      */   public void setLoadBalanceExceptionChecker(String loadBalanceExceptionChecker) {
/* 4571 */     this.loadBalanceExceptionChecker.setValue(loadBalanceExceptionChecker);
/*      */   }
/*      */   
/*      */   public String getLoadBalanceSQLStateFailover() {
/* 4575 */     return this.loadBalanceSQLStateFailover.getValueAsString();
/*      */   }
/*      */   
/*      */   public void setLoadBalanceSQLStateFailover(String loadBalanceSQLStateFailover) {
/* 4579 */     this.loadBalanceSQLStateFailover.setValue(loadBalanceSQLStateFailover);
/*      */   }
/*      */   
/*      */   public String getLoadBalanceSQLExceptionSubclassFailover() {
/* 4583 */     return this.loadBalanceSQLExceptionSubclassFailover.getValueAsString();
/*      */   }
/*      */   
/*      */   public void setLoadBalanceSQLExceptionSubclassFailover(String loadBalanceSQLExceptionSubclassFailover) {
/* 4587 */     this.loadBalanceSQLExceptionSubclassFailover.setValue(loadBalanceSQLExceptionSubclassFailover);
/*      */   }
/*      */   
/*      */   public boolean getLoadBalanceEnableJMX() {
/* 4591 */     return this.loadBalanceEnableJMX.getValueAsBoolean();
/*      */   }
/*      */   
/*      */   public void setLoadBalanceEnableJMX(boolean loadBalanceEnableJMX) {
/* 4595 */     this.loadBalanceEnableJMX.setValue(loadBalanceEnableJMX);
/*      */   }
/*      */   
/*      */   public void setLoadBalanceAutoCommitStatementThreshold(int loadBalanceAutoCommitStatementThreshold) {
/* 4599 */     this.loadBalanceAutoCommitStatementThreshold.setValue(loadBalanceAutoCommitStatementThreshold);
/*      */   }
/*      */   
/*      */   public int getLoadBalanceAutoCommitStatementThreshold() {
/* 4603 */     return this.loadBalanceAutoCommitStatementThreshold.getValueAsInt();
/*      */   }
/*      */   
/*      */   public void setLoadBalanceAutoCommitStatementRegex(String loadBalanceAutoCommitStatementRegex) {
/* 4607 */     this.loadBalanceAutoCommitStatementRegex.setValue(loadBalanceAutoCommitStatementRegex);
/*      */   }
/*      */   
/*      */   public String getLoadBalanceAutoCommitStatementRegex() {
/* 4611 */     return this.loadBalanceAutoCommitStatementRegex.getValueAsString();
/*      */   }
/*      */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\com\mysql\jdbc\ConnectionPropertiesImpl.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */