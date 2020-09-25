/*     */ package org.apache.logging.log4j.core.layout;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.SortedMap;
/*     */ import java.util.TreeMap;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.logging.log4j.Level;
/*     */ import org.apache.logging.log4j.LoggingException;
/*     */ import org.apache.logging.log4j.core.LogEvent;
/*     */ import org.apache.logging.log4j.core.appender.TLSSyslogFrame;
/*     */ import org.apache.logging.log4j.core.config.Configuration;
/*     */ import org.apache.logging.log4j.core.config.plugins.Plugin;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginConfiguration;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginElement;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginFactory;
/*     */ import org.apache.logging.log4j.core.helpers.Booleans;
/*     */ import org.apache.logging.log4j.core.helpers.Charsets;
/*     */ import org.apache.logging.log4j.core.helpers.Integers;
/*     */ import org.apache.logging.log4j.core.helpers.NetUtils;
/*     */ import org.apache.logging.log4j.core.helpers.Strings;
/*     */ import org.apache.logging.log4j.core.net.Facility;
/*     */ import org.apache.logging.log4j.core.net.Priority;
/*     */ import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
/*     */ import org.apache.logging.log4j.core.pattern.PatternConverter;
/*     */ import org.apache.logging.log4j.core.pattern.PatternFormatter;
/*     */ import org.apache.logging.log4j.core.pattern.PatternParser;
/*     */ import org.apache.logging.log4j.core.pattern.ThrowablePatternConverter;
/*     */ import org.apache.logging.log4j.message.Message;
/*     */ import org.apache.logging.log4j.message.StructuredDataId;
/*     */ import org.apache.logging.log4j.message.StructuredDataMessage;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Plugin(name = "RFC5424Layout", category = "Core", elementType = "layout", printObject = true)
/*     */ public class RFC5424Layout
/*     */   extends AbstractStringLayout
/*     */ {
/*     */   private static final String LF = "\n";
/*     */   public static final int DEFAULT_ENTERPRISE_NUMBER = 18060;
/*     */   public static final String DEFAULT_ID = "Audit";
/*  79 */   public static final Pattern NEWLINE_PATTERN = Pattern.compile("\\r?\\n");
/*     */ 
/*     */ 
/*     */   
/*  83 */   public static final Pattern PARAM_VALUE_ESCAPE_PATTERN = Pattern.compile("[\\\"\\]\\\\]");
/*     */   
/*     */   protected static final String DEFAULT_MDCID = "mdc";
/*     */   
/*     */   private static final int TWO_DIGITS = 10;
/*     */   
/*     */   private static final int THREE_DIGITS = 100;
/*     */   private static final int MILLIS_PER_MINUTE = 60000;
/*     */   private static final int MINUTES_PER_HOUR = 60;
/*     */   private static final String COMPONENT_KEY = "RFC5424-Converter";
/*     */   private final Facility facility;
/*     */   private final String defaultId;
/*     */   private final int enterpriseNumber;
/*     */   private final boolean includeMDC;
/*     */   private final String mdcId;
/*     */   private final StructuredDataId mdcSDID;
/*     */   private final String localHostName;
/*     */   private final String appName;
/*     */   private final String messageId;
/*     */   private final String configName;
/*     */   private final String mdcPrefix;
/*     */   private final String eventPrefix;
/*     */   private final List<String> mdcExcludes;
/*     */   private final List<String> mdcIncludes;
/*     */   private final List<String> mdcRequired;
/*     */   private final ListChecker checker;
/* 109 */   private final ListChecker noopChecker = new NoopChecker();
/*     */   
/*     */   private final boolean includeNewLine;
/*     */   private final String escapeNewLine;
/*     */   private final boolean useTLSMessageFormat;
/* 114 */   private long lastTimestamp = -1L;
/*     */ 
/*     */   
/*     */   private String timestamppStr;
/*     */ 
/*     */   
/*     */   private final List<PatternFormatter> exceptionFormatters;
/*     */   
/*     */   private final Map<String, FieldFormatter> fieldFormatters;
/*     */ 
/*     */   
/*     */   private RFC5424Layout(Configuration config, Facility facility, String id, int ein, boolean includeMDC, boolean includeNL, String escapeNL, String mdcId, String mdcPrefix, String eventPrefix, String appName, String messageId, String excludes, String includes, String required, Charset charset, String exceptionPattern, boolean useTLSMessageFormat, LoggerFields[] loggerFields) {
/* 126 */     super(charset);
/* 127 */     PatternParser exceptionParser = createPatternParser(config, (Class)ThrowablePatternConverter.class);
/* 128 */     this.exceptionFormatters = (exceptionPattern == null) ? null : exceptionParser.parse(exceptionPattern, false);
/* 129 */     this.facility = facility;
/* 130 */     this.defaultId = (id == null) ? "Audit" : id;
/* 131 */     this.enterpriseNumber = ein;
/* 132 */     this.includeMDC = includeMDC;
/* 133 */     this.includeNewLine = includeNL;
/* 134 */     this.escapeNewLine = (escapeNL == null) ? null : Matcher.quoteReplacement(escapeNL);
/* 135 */     this.mdcId = mdcId;
/* 136 */     this.mdcSDID = new StructuredDataId(mdcId, this.enterpriseNumber, null, null);
/* 137 */     this.mdcPrefix = mdcPrefix;
/* 138 */     this.eventPrefix = eventPrefix;
/* 139 */     this.appName = appName;
/* 140 */     this.messageId = messageId;
/* 141 */     this.useTLSMessageFormat = useTLSMessageFormat;
/* 142 */     this.localHostName = NetUtils.getLocalHostname();
/* 143 */     ListChecker c = null;
/* 144 */     if (excludes != null) {
/* 145 */       String[] array = excludes.split(",");
/* 146 */       if (array.length > 0) {
/* 147 */         c = new ExcludeChecker();
/* 148 */         this.mdcExcludes = new ArrayList<String>(array.length);
/* 149 */         for (String str : array) {
/* 150 */           this.mdcExcludes.add(str.trim());
/*     */         }
/*     */       } else {
/* 153 */         this.mdcExcludes = null;
/*     */       } 
/*     */     } else {
/* 156 */       this.mdcExcludes = null;
/*     */     } 
/* 158 */     if (includes != null) {
/* 159 */       String[] array = includes.split(",");
/* 160 */       if (array.length > 0) {
/* 161 */         c = new IncludeChecker();
/* 162 */         this.mdcIncludes = new ArrayList<String>(array.length);
/* 163 */         for (String str : array) {
/* 164 */           this.mdcIncludes.add(str.trim());
/*     */         }
/*     */       } else {
/* 167 */         this.mdcIncludes = null;
/*     */       } 
/*     */     } else {
/* 170 */       this.mdcIncludes = null;
/*     */     } 
/* 172 */     if (required != null) {
/* 173 */       String[] array = required.split(",");
/* 174 */       if (array.length > 0) {
/* 175 */         this.mdcRequired = new ArrayList<String>(array.length);
/* 176 */         for (String str : array) {
/* 177 */           this.mdcRequired.add(str.trim());
/*     */         }
/*     */       } else {
/* 180 */         this.mdcRequired = null;
/*     */       } 
/*     */     } else {
/*     */       
/* 184 */       this.mdcRequired = null;
/*     */     } 
/* 186 */     this.checker = (c != null) ? c : this.noopChecker;
/* 187 */     String name = (config == null) ? null : config.getName();
/* 188 */     this.configName = (name != null && name.length() > 0) ? name : null;
/* 189 */     this.fieldFormatters = createFieldFormatters(loggerFields, config);
/*     */   }
/*     */ 
/*     */   
/*     */   private Map<String, FieldFormatter> createFieldFormatters(LoggerFields[] loggerFields, Configuration config) {
/* 194 */     Map<String, FieldFormatter> sdIdMap = new HashMap<String, FieldFormatter>();
/*     */     
/* 196 */     if (loggerFields != null) {
/* 197 */       for (LoggerFields lField : loggerFields) {
/* 198 */         StructuredDataId key = (lField.getSdId() == null) ? this.mdcSDID : lField.getSdId();
/* 199 */         Map<String, List<PatternFormatter>> sdParams = new HashMap<String, List<PatternFormatter>>();
/* 200 */         Map<String, String> fields = lField.getMap();
/* 201 */         if (!fields.isEmpty()) {
/* 202 */           PatternParser fieldParser = createPatternParser(config, null);
/*     */           
/* 204 */           for (Map.Entry<String, String> entry : fields.entrySet()) {
/* 205 */             List<PatternFormatter> formatters = fieldParser.parse(entry.getValue(), false);
/* 206 */             sdParams.put(entry.getKey(), formatters);
/*     */           } 
/* 208 */           FieldFormatter fieldFormatter = new FieldFormatter(sdParams, lField.getDiscardIfAllFieldsAreEmpty());
/*     */           
/* 210 */           sdIdMap.put(key.toString(), fieldFormatter);
/*     */         } 
/*     */       } 
/*     */     }
/* 214 */     return (sdIdMap.size() > 0) ? sdIdMap : null;
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
/*     */   private static PatternParser createPatternParser(Configuration config, Class<? extends PatternConverter> filterClass) {
/* 226 */     if (config == null) {
/* 227 */       return new PatternParser(config, "Converter", LogEventPatternConverter.class, filterClass);
/*     */     }
/* 229 */     PatternParser parser = (PatternParser)config.getComponent("RFC5424-Converter");
/* 230 */     if (parser == null) {
/* 231 */       parser = new PatternParser(config, "Converter", ThrowablePatternConverter.class);
/* 232 */       config.addComponent("RFC5424-Converter", parser);
/* 233 */       parser = (PatternParser)config.getComponent("RFC5424-Converter");
/*     */     } 
/* 235 */     return parser;
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
/*     */   public Map<String, String> getContentFormat() {
/* 247 */     Map<String, String> result = new HashMap<String, String>();
/* 248 */     result.put("structured", "true");
/* 249 */     result.put("formatType", "RFC5424");
/* 250 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toSerializable(LogEvent event) {
/* 261 */     StringBuilder buf = new StringBuilder();
/* 262 */     appendPriority(buf, event.getLevel());
/* 263 */     appendTimestamp(buf, event.getMillis());
/* 264 */     appendSpace(buf);
/* 265 */     appendHostName(buf);
/* 266 */     appendSpace(buf);
/* 267 */     appendAppName(buf);
/* 268 */     appendSpace(buf);
/* 269 */     appendProcessId(buf);
/* 270 */     appendSpace(buf);
/* 271 */     appendMessageId(buf, event.getMessage());
/* 272 */     appendSpace(buf);
/* 273 */     appendStructuredElements(buf, event);
/* 274 */     appendMessage(buf, event);
/* 275 */     if (this.useTLSMessageFormat) {
/* 276 */       return (new TLSSyslogFrame(buf.toString())).toString();
/*     */     }
/* 278 */     return buf.toString();
/*     */   }
/*     */   
/*     */   private void appendPriority(StringBuilder buffer, Level logLevel) {
/* 282 */     buffer.append("<");
/* 283 */     buffer.append(Priority.getPriority(this.facility, logLevel));
/* 284 */     buffer.append(">1 ");
/*     */   }
/*     */   
/*     */   private void appendTimestamp(StringBuilder buffer, long milliseconds) {
/* 288 */     buffer.append(computeTimeStampString(milliseconds));
/*     */   }
/*     */   
/*     */   private void appendSpace(StringBuilder buffer) {
/* 292 */     buffer.append(" ");
/*     */   }
/*     */   
/*     */   private void appendHostName(StringBuilder buffer) {
/* 296 */     buffer.append(this.localHostName);
/*     */   }
/*     */   
/*     */   private void appendAppName(StringBuilder buffer) {
/* 300 */     if (this.appName != null) {
/* 301 */       buffer.append(this.appName);
/* 302 */     } else if (this.configName != null) {
/* 303 */       buffer.append(this.configName);
/*     */     } else {
/* 305 */       buffer.append("-");
/*     */     } 
/*     */   }
/*     */   
/*     */   private void appendProcessId(StringBuilder buffer) {
/* 310 */     buffer.append(getProcId());
/*     */   }
/*     */   
/*     */   private void appendMessageId(StringBuilder buffer, Message message) {
/* 314 */     boolean isStructured = message instanceof StructuredDataMessage;
/* 315 */     String type = isStructured ? ((StructuredDataMessage)message).getType() : null;
/* 316 */     if (type != null) {
/* 317 */       buffer.append(type);
/* 318 */     } else if (this.messageId != null) {
/* 319 */       buffer.append(this.messageId);
/*     */     } else {
/* 321 */       buffer.append("-");
/*     */     } 
/*     */   }
/*     */   
/*     */   private void appendMessage(StringBuilder buffer, LogEvent event) {
/* 326 */     Message message = event.getMessage();
/* 327 */     String text = message.getFormat();
/*     */     
/* 329 */     if (text != null && text.length() > 0) {
/* 330 */       buffer.append(" ").append(escapeNewlines(text, this.escapeNewLine));
/*     */     }
/*     */     
/* 333 */     if (this.exceptionFormatters != null && event.getThrown() != null) {
/* 334 */       StringBuilder exception = new StringBuilder("\n");
/* 335 */       for (PatternFormatter formatter : this.exceptionFormatters) {
/* 336 */         formatter.format(event, exception);
/*     */       }
/* 338 */       buffer.append(escapeNewlines(exception.toString(), this.escapeNewLine));
/*     */     } 
/* 340 */     if (this.includeNewLine) {
/* 341 */       buffer.append("\n");
/*     */     }
/*     */   }
/*     */   
/*     */   private void appendStructuredElements(StringBuilder buffer, LogEvent event) {
/* 346 */     Message message = event.getMessage();
/* 347 */     boolean isStructured = message instanceof StructuredDataMessage;
/*     */     
/* 349 */     if (!isStructured && this.fieldFormatters != null && this.fieldFormatters.size() == 0 && !this.includeMDC) {
/* 350 */       buffer.append("-");
/*     */       
/*     */       return;
/*     */     } 
/* 354 */     Map<String, StructuredDataElement> sdElements = new HashMap<String, StructuredDataElement>();
/* 355 */     Map<String, String> contextMap = event.getContextMap();
/*     */     
/* 357 */     if (this.mdcRequired != null) {
/* 358 */       checkRequired(contextMap);
/*     */     }
/*     */     
/* 361 */     if (this.fieldFormatters != null) {
/* 362 */       for (Map.Entry<String, FieldFormatter> sdElement : this.fieldFormatters.entrySet()) {
/* 363 */         String sdId = sdElement.getKey();
/* 364 */         StructuredDataElement elem = ((FieldFormatter)sdElement.getValue()).format(event);
/* 365 */         sdElements.put(sdId, elem);
/*     */       } 
/*     */     }
/*     */     
/* 369 */     if (this.includeMDC && contextMap.size() > 0) {
/* 370 */       if (sdElements.containsKey(this.mdcSDID.toString())) {
/* 371 */         StructuredDataElement union = sdElements.get(this.mdcSDID.toString());
/* 372 */         union.union(contextMap);
/* 373 */         sdElements.put(this.mdcSDID.toString(), union);
/*     */       } else {
/* 375 */         StructuredDataElement formattedContextMap = new StructuredDataElement(contextMap, false);
/* 376 */         sdElements.put(this.mdcSDID.toString(), formattedContextMap);
/*     */       } 
/*     */     }
/*     */     
/* 380 */     if (isStructured) {
/* 381 */       StructuredDataMessage data = (StructuredDataMessage)message;
/* 382 */       Map<String, String> map = data.getData();
/* 383 */       StructuredDataId id = data.getId();
/*     */       
/* 385 */       if (sdElements.containsKey(id.toString())) {
/* 386 */         StructuredDataElement union = sdElements.get(id.toString());
/* 387 */         union.union(map);
/* 388 */         sdElements.put(id.toString(), union);
/*     */       } else {
/* 390 */         StructuredDataElement formattedData = new StructuredDataElement(map, false);
/* 391 */         sdElements.put(id.toString(), formattedData);
/*     */       } 
/*     */     } 
/*     */     
/* 395 */     if (sdElements.size() == 0) {
/* 396 */       buffer.append("-");
/*     */       
/*     */       return;
/*     */     } 
/* 400 */     for (Map.Entry<String, StructuredDataElement> entry : sdElements.entrySet()) {
/* 401 */       formatStructuredElement(entry.getKey(), this.mdcPrefix, entry.getValue(), buffer, this.checker);
/*     */     }
/*     */   }
/*     */   
/*     */   private String escapeNewlines(String text, String escapeNewLine) {
/* 406 */     if (null == escapeNewLine) {
/* 407 */       return text;
/*     */     }
/* 409 */     return NEWLINE_PATTERN.matcher(text).replaceAll(escapeNewLine);
/*     */   }
/*     */   
/*     */   protected String getProcId() {
/* 413 */     return "-";
/*     */   }
/*     */   
/*     */   protected List<String> getMdcExcludes() {
/* 417 */     return this.mdcExcludes;
/*     */   }
/*     */   
/*     */   protected List<String> getMdcIncludes() {
/* 421 */     return this.mdcIncludes;
/*     */   }
/*     */   
/*     */   private String computeTimeStampString(long now) {
/*     */     long last;
/* 426 */     synchronized (this) {
/* 427 */       last = this.lastTimestamp;
/* 428 */       if (now == this.lastTimestamp) {
/* 429 */         return this.timestamppStr;
/*     */       }
/*     */     } 
/*     */     
/* 433 */     StringBuilder buffer = new StringBuilder();
/* 434 */     Calendar cal = new GregorianCalendar();
/* 435 */     cal.setTimeInMillis(now);
/* 436 */     buffer.append(Integer.toString(cal.get(1)));
/* 437 */     buffer.append("-");
/* 438 */     pad(cal.get(2) + 1, 10, buffer);
/* 439 */     buffer.append("-");
/* 440 */     pad(cal.get(5), 10, buffer);
/* 441 */     buffer.append("T");
/* 442 */     pad(cal.get(11), 10, buffer);
/* 443 */     buffer.append(":");
/* 444 */     pad(cal.get(12), 10, buffer);
/* 445 */     buffer.append(":");
/* 446 */     pad(cal.get(13), 10, buffer);
/*     */     
/* 448 */     int millis = cal.get(14);
/* 449 */     if (millis != 0) {
/* 450 */       buffer.append('.');
/* 451 */       pad(millis, 100, buffer);
/*     */     } 
/*     */     
/* 454 */     int tzmin = (cal.get(15) + cal.get(16)) / 60000;
/* 455 */     if (tzmin == 0) {
/* 456 */       buffer.append("Z");
/*     */     } else {
/* 458 */       if (tzmin < 0) {
/* 459 */         tzmin = -tzmin;
/* 460 */         buffer.append("-");
/*     */       } else {
/* 462 */         buffer.append("+");
/*     */       } 
/* 464 */       int tzhour = tzmin / 60;
/* 465 */       tzmin -= tzhour * 60;
/* 466 */       pad(tzhour, 10, buffer);
/* 467 */       buffer.append(":");
/* 468 */       pad(tzmin, 10, buffer);
/*     */     } 
/* 470 */     synchronized (this) {
/* 471 */       if (last == this.lastTimestamp) {
/* 472 */         this.lastTimestamp = now;
/* 473 */         this.timestamppStr = buffer.toString();
/*     */       } 
/*     */     } 
/* 476 */     return buffer.toString();
/*     */   }
/*     */   
/*     */   private void pad(int val, int max, StringBuilder buf) {
/* 480 */     while (max > 1) {
/* 481 */       if (val < max) {
/* 482 */         buf.append("0");
/*     */       }
/* 484 */       max /= 10;
/*     */     } 
/* 486 */     buf.append(Integer.toString(val));
/*     */   }
/*     */ 
/*     */   
/*     */   private void formatStructuredElement(String id, String prefix, StructuredDataElement data, StringBuilder sb, ListChecker checker) {
/* 491 */     if ((id == null && this.defaultId == null) || data.discard()) {
/*     */       return;
/*     */     }
/*     */     
/* 495 */     sb.append("[");
/* 496 */     sb.append(id);
/* 497 */     if (!this.mdcSDID.toString().equals(id)) {
/* 498 */       appendMap(prefix, data.getFields(), sb, this.noopChecker);
/*     */     } else {
/* 500 */       appendMap(prefix, data.getFields(), sb, checker);
/*     */     } 
/* 502 */     sb.append("]");
/*     */   }
/*     */   
/*     */   private String getId(StructuredDataId id) {
/* 506 */     StringBuilder sb = new StringBuilder();
/* 507 */     if (id == null || id.getName() == null) {
/* 508 */       sb.append(this.defaultId);
/*     */     } else {
/* 510 */       sb.append(id.getName());
/*     */     } 
/* 512 */     int ein = (id != null) ? id.getEnterpriseNumber() : this.enterpriseNumber;
/* 513 */     if (ein < 0) {
/* 514 */       ein = this.enterpriseNumber;
/*     */     }
/* 516 */     if (ein >= 0) {
/* 517 */       sb.append("@").append(ein);
/*     */     }
/* 519 */     return sb.toString();
/*     */   }
/*     */   
/*     */   private void checkRequired(Map<String, String> map) {
/* 523 */     for (String key : this.mdcRequired) {
/* 524 */       String value = map.get(key);
/* 525 */       if (value == null) {
/* 526 */         throw new LoggingException("Required key " + key + " is missing from the " + this.mdcId);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void appendMap(String prefix, Map<String, String> map, StringBuilder sb, ListChecker checker) {
/* 533 */     SortedMap<String, String> sorted = new TreeMap<String, String>(map);
/* 534 */     for (Map.Entry<String, String> entry : sorted.entrySet()) {
/* 535 */       if (checker.check(entry.getKey()) && entry.getValue() != null) {
/* 536 */         sb.append(" ");
/* 537 */         if (prefix != null) {
/* 538 */           sb.append(prefix);
/*     */         }
/* 540 */         sb.append(escapeNewlines(escapeSDParams(entry.getKey()), this.escapeNewLine)).append("=\"").append(escapeNewlines(escapeSDParams(entry.getValue()), this.escapeNewLine)).append("\"");
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String escapeSDParams(String value) {
/* 547 */     return PARAM_VALUE_ESCAPE_PATTERN.matcher(value).replaceAll("\\\\$0");
/*     */   }
/*     */ 
/*     */   
/*     */   private static interface ListChecker
/*     */   {
/*     */     boolean check(String param1String);
/*     */   }
/*     */ 
/*     */   
/*     */   private class IncludeChecker
/*     */     implements ListChecker
/*     */   {
/*     */     private IncludeChecker() {}
/*     */     
/*     */     public boolean check(String key) {
/* 563 */       return RFC5424Layout.this.mdcIncludes.contains(key);
/*     */     }
/*     */   }
/*     */   
/*     */   private class ExcludeChecker
/*     */     implements ListChecker
/*     */   {
/*     */     private ExcludeChecker() {}
/*     */     
/*     */     public boolean check(String key) {
/* 573 */       return !RFC5424Layout.this.mdcExcludes.contains(key);
/*     */     }
/*     */   }
/*     */   
/*     */   private class NoopChecker
/*     */     implements ListChecker
/*     */   {
/*     */     private NoopChecker() {}
/*     */     
/*     */     public boolean check(String key) {
/* 583 */       return true;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 589 */     StringBuilder sb = new StringBuilder();
/* 590 */     sb.append("facility=").append(this.facility.name());
/* 591 */     sb.append(" appName=").append(this.appName);
/* 592 */     sb.append(" defaultId=").append(this.defaultId);
/* 593 */     sb.append(" enterpriseNumber=").append(this.enterpriseNumber);
/* 594 */     sb.append(" newLine=").append(this.includeNewLine);
/* 595 */     sb.append(" includeMDC=").append(this.includeMDC);
/* 596 */     sb.append(" messageId=").append(this.messageId);
/* 597 */     return sb.toString();
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
/*     */   @PluginFactory
/*     */   public static RFC5424Layout createLayout(@PluginAttribute("facility") String facility, @PluginAttribute("id") String id, @PluginAttribute("enterpriseNumber") String ein, @PluginAttribute("includeMDC") String includeMDC, @PluginAttribute("mdcId") String mdcId, @PluginAttribute("mdcPrefix") String mdcPrefix, @PluginAttribute("eventPrefix") String eventPrefix, @PluginAttribute("newLine") String includeNL, @PluginAttribute("newLineEscape") String escapeNL, @PluginAttribute("appName") String appName, @PluginAttribute("messageId") String msgId, @PluginAttribute("mdcExcludes") String excludes, @PluginAttribute("mdcIncludes") String includes, @PluginAttribute("mdcRequired") String required, @PluginAttribute("exceptionPattern") String exceptionPattern, @PluginAttribute("useTLSMessageFormat") String useTLSMessageFormat, @PluginElement("LoggerFields") LoggerFields[] loggerFields, @PluginConfiguration Configuration config) {
/* 644 */     Charset charset = Charsets.UTF_8;
/* 645 */     if (includes != null && excludes != null) {
/* 646 */       LOGGER.error("mdcIncludes and mdcExcludes are mutually exclusive. Includes wil be ignored");
/* 647 */       includes = null;
/*     */     } 
/* 649 */     Facility f = Facility.toFacility(facility, Facility.LOCAL0);
/* 650 */     int enterpriseNumber = Integers.parseInt(ein, 18060);
/* 651 */     boolean isMdc = Booleans.parseBoolean(includeMDC, true);
/* 652 */     boolean includeNewLine = Boolean.parseBoolean(includeNL);
/* 653 */     boolean useTlsMessageFormat = Booleans.parseBoolean(useTLSMessageFormat, false);
/* 654 */     if (mdcId == null) {
/* 655 */       mdcId = "mdc";
/*     */     }
/*     */     
/* 658 */     return new RFC5424Layout(config, f, id, enterpriseNumber, isMdc, includeNewLine, escapeNL, mdcId, mdcPrefix, eventPrefix, appName, msgId, excludes, includes, required, charset, exceptionPattern, useTlsMessageFormat, loggerFields);
/*     */   }
/*     */ 
/*     */   
/*     */   private class FieldFormatter
/*     */   {
/*     */     private final Map<String, List<PatternFormatter>> delegateMap;
/*     */     
/*     */     private final boolean discardIfEmpty;
/*     */     
/*     */     public FieldFormatter(Map<String, List<PatternFormatter>> fieldMap, boolean discardIfEmpty) {
/* 669 */       this.discardIfEmpty = discardIfEmpty;
/* 670 */       this.delegateMap = fieldMap;
/*     */     }
/*     */     
/*     */     public RFC5424Layout.StructuredDataElement format(LogEvent event) {
/* 674 */       Map<String, String> map = new HashMap<String, String>();
/*     */       
/* 676 */       for (Map.Entry<String, List<PatternFormatter>> entry : this.delegateMap.entrySet()) {
/* 677 */         StringBuilder buffer = new StringBuilder();
/* 678 */         for (PatternFormatter formatter : entry.getValue()) {
/* 679 */           formatter.format(event, buffer);
/*     */         }
/* 681 */         map.put(entry.getKey(), buffer.toString());
/*     */       } 
/* 683 */       return new RFC5424Layout.StructuredDataElement(map, this.discardIfEmpty);
/*     */     }
/*     */   }
/*     */   
/*     */   private class StructuredDataElement
/*     */   {
/*     */     private final Map<String, String> fields;
/*     */     private final boolean discardIfEmpty;
/*     */     
/*     */     public StructuredDataElement(Map<String, String> fields, boolean discardIfEmpty) {
/* 693 */       this.discardIfEmpty = discardIfEmpty;
/* 694 */       this.fields = fields;
/*     */     }
/*     */     
/*     */     boolean discard() {
/* 698 */       if (!this.discardIfEmpty) {
/* 699 */         return false;
/*     */       }
/* 701 */       boolean foundNotEmptyValue = false;
/* 702 */       for (Map.Entry<String, String> entry : this.fields.entrySet()) {
/* 703 */         if (Strings.isNotEmpty(entry.getValue())) {
/* 704 */           foundNotEmptyValue = true;
/*     */           break;
/*     */         } 
/*     */       } 
/* 708 */       return !foundNotEmptyValue;
/*     */     }
/*     */     
/*     */     void union(Map<String, String> fields) {
/* 712 */       this.fields.putAll(fields);
/*     */     }
/*     */     
/*     */     Map<String, String> getFields() {
/* 716 */       return this.fields;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\layout\RFC5424Layout.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */