/*     */ package org.apache.logging.log4j.core.pattern;
/*     */ 
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Modifier;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.apache.logging.log4j.core.config.Configuration;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginManager;
/*     */ import org.apache.logging.log4j.core.config.plugins.PluginType;
/*     */ import org.apache.logging.log4j.core.helpers.Strings;
/*     */ import org.apache.logging.log4j.status.StatusLogger;
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
/*     */ public final class PatternParser
/*     */ {
/*     */   private static final char ESCAPE_CHAR = '%';
/*     */   
/*     */   private enum ParserState
/*     */   {
/*  53 */     LITERAL_STATE,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  58 */     CONVERTER_STATE,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  63 */     DOT_STATE,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  68 */     MIN_STATE,
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  73 */     MAX_STATE;
/*     */   }
/*     */   
/*  76 */   private static final Logger LOGGER = (Logger)StatusLogger.getLogger();
/*     */ 
/*     */   
/*     */   private static final int BUF_SIZE = 32;
/*     */ 
/*     */   
/*     */   private static final int DECIMAL = 10;
/*     */ 
/*     */   
/*     */   private final Configuration config;
/*     */ 
/*     */   
/*     */   private final Map<String, Class<PatternConverter>> converterRules;
/*     */ 
/*     */   
/*     */   public PatternParser(String converterKey) {
/*  92 */     this(null, converterKey, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PatternParser(Configuration config, String converterKey, Class<?> expected) {
/* 102 */     this(config, converterKey, expected, null);
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
/*     */   public PatternParser(Configuration config, String converterKey, Class<?> expectedClass, Class<?> filterClass) {
/* 114 */     this.config = config;
/* 115 */     PluginManager manager = new PluginManager(converterKey, expectedClass);
/* 116 */     manager.collectPlugins();
/* 117 */     Map<String, PluginType<?>> plugins = manager.getPlugins();
/* 118 */     Map<String, Class<PatternConverter>> converters = new HashMap<String, Class<PatternConverter>>();
/*     */     
/* 120 */     for (PluginType<?> type : plugins.values()) {
/*     */       
/*     */       try {
/* 123 */         Class<PatternConverter> clazz = type.getPluginClass();
/* 124 */         if (filterClass != null && !filterClass.isAssignableFrom(clazz)) {
/*     */           continue;
/*     */         }
/* 127 */         ConverterKeys keys = clazz.<ConverterKeys>getAnnotation(ConverterKeys.class);
/* 128 */         if (keys != null) {
/* 129 */           for (String key : keys.value()) {
/* 130 */             converters.put(key, clazz);
/*     */           }
/*     */         }
/* 133 */       } catch (Exception ex) {
/* 134 */         LOGGER.error("Error processing plugin " + type.getElementName(), ex);
/*     */       } 
/*     */     } 
/* 137 */     this.converterRules = converters;
/*     */   }
/*     */   
/*     */   public List<PatternFormatter> parse(String pattern) {
/* 141 */     return parse(pattern, false);
/*     */   }
/*     */   
/*     */   public List<PatternFormatter> parse(String pattern, boolean alwaysWriteExceptions) {
/* 145 */     List<PatternFormatter> list = new ArrayList<PatternFormatter>();
/* 146 */     List<PatternConverter> converters = new ArrayList<PatternConverter>();
/* 147 */     List<FormattingInfo> fields = new ArrayList<FormattingInfo>();
/*     */     
/* 149 */     parse(pattern, converters, fields);
/*     */     
/* 151 */     Iterator<FormattingInfo> fieldIter = fields.iterator();
/* 152 */     boolean handlesThrowable = false;
/*     */     
/* 154 */     for (PatternConverter converter : converters) {
/*     */       LogEventPatternConverter pc; FormattingInfo field;
/* 156 */       if (converter instanceof LogEventPatternConverter) {
/* 157 */         pc = (LogEventPatternConverter)converter;
/* 158 */         handlesThrowable |= pc.handlesThrowable();
/*     */       } else {
/* 160 */         pc = new LiteralPatternConverter(this.config, "");
/*     */       } 
/*     */ 
/*     */       
/* 164 */       if (fieldIter.hasNext()) {
/* 165 */         field = fieldIter.next();
/*     */       } else {
/* 167 */         field = FormattingInfo.getDefault();
/*     */       } 
/* 169 */       list.add(new PatternFormatter(pc, field));
/*     */     } 
/* 171 */     if (alwaysWriteExceptions && !handlesThrowable) {
/* 172 */       LogEventPatternConverter pc = ExtendedThrowablePatternConverter.newInstance(null);
/* 173 */       list.add(new PatternFormatter(pc, FormattingInfo.getDefault()));
/*     */     } 
/* 175 */     return list;
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
/*     */   private static int extractConverter(char lastChar, String pattern, int i, StringBuilder convBuf, StringBuilder currentLiteral) {
/* 197 */     convBuf.setLength(0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 204 */     if (!Character.isUnicodeIdentifierStart(lastChar)) {
/* 205 */       return i;
/*     */     }
/*     */     
/* 208 */     convBuf.append(lastChar);
/*     */     
/* 210 */     while (i < pattern.length() && Character.isUnicodeIdentifierPart(pattern.charAt(i))) {
/* 211 */       convBuf.append(pattern.charAt(i));
/* 212 */       currentLiteral.append(pattern.charAt(i));
/* 213 */       i++;
/*     */     } 
/*     */     
/* 216 */     return i;
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
/*     */   private static int extractOptions(String pattern, int i, List<String> options) {
/* 228 */     while (i < pattern.length() && pattern.charAt(i) == '{') {
/* 229 */       int end, begin = i++;
/*     */       
/* 231 */       int depth = 0;
/*     */       do {
/* 233 */         end = pattern.indexOf('}', i);
/* 234 */         if (end == -1)
/* 235 */           continue;  int next = pattern.indexOf("{", i);
/* 236 */         if (next != -1 && next < end) {
/* 237 */           i = end + 1;
/* 238 */           depth++;
/* 239 */         } else if (depth > 0) {
/* 240 */           depth--;
/*     */         }
/*     */       
/* 243 */       } while (depth > 0);
/*     */       
/* 245 */       if (end == -1) {
/*     */         break;
/*     */       }
/*     */       
/* 249 */       String r = pattern.substring(begin + 1, end);
/* 250 */       options.add(r);
/* 251 */       i = end + 1;
/*     */     } 
/*     */     
/* 254 */     return i;
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
/*     */   public void parse(String pattern, List<PatternConverter> patternConverters, List<FormattingInfo> formattingInfos) {
/* 266 */     if (pattern == null) {
/* 267 */       throw new NullPointerException("pattern");
/*     */     }
/*     */     
/* 270 */     StringBuilder currentLiteral = new StringBuilder(32);
/*     */     
/* 272 */     int patternLength = pattern.length();
/* 273 */     ParserState state = ParserState.LITERAL_STATE;
/*     */     
/* 275 */     int i = 0;
/* 276 */     FormattingInfo formattingInfo = FormattingInfo.getDefault();
/*     */     
/* 278 */     while (i < patternLength) {
/* 279 */       char c = pattern.charAt(i++);
/*     */       
/* 281 */       switch (state) {
/*     */ 
/*     */         
/*     */         case LITERAL_STATE:
/* 285 */           if (i == patternLength) {
/* 286 */             currentLiteral.append(c);
/*     */             
/*     */             continue;
/*     */           } 
/*     */           
/* 291 */           if (c == '%') {
/*     */             
/* 293 */             switch (pattern.charAt(i)) {
/*     */               case '%':
/* 295 */                 currentLiteral.append(c);
/* 296 */                 i++;
/*     */                 continue;
/*     */             } 
/*     */ 
/*     */ 
/*     */             
/* 302 */             if (currentLiteral.length() != 0) {
/* 303 */               patternConverters.add(new LiteralPatternConverter(this.config, currentLiteral.toString()));
/*     */               
/* 305 */               formattingInfos.add(FormattingInfo.getDefault());
/*     */             } 
/*     */             
/* 308 */             currentLiteral.setLength(0);
/* 309 */             currentLiteral.append(c);
/* 310 */             state = ParserState.CONVERTER_STATE;
/* 311 */             formattingInfo = FormattingInfo.getDefault();
/*     */             continue;
/*     */           } 
/* 314 */           currentLiteral.append(c);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case CONVERTER_STATE:
/* 320 */           currentLiteral.append(c);
/*     */           
/* 322 */           switch (c) {
/*     */             case '-':
/* 324 */               formattingInfo = new FormattingInfo(true, formattingInfo.getMinLength(), formattingInfo.getMaxLength());
/*     */               continue;
/*     */ 
/*     */ 
/*     */             
/*     */             case '.':
/* 330 */               state = ParserState.DOT_STATE;
/*     */               continue;
/*     */           } 
/*     */ 
/*     */           
/* 335 */           if (c >= '0' && c <= '9') {
/* 336 */             formattingInfo = new FormattingInfo(formattingInfo.isLeftAligned(), c - 48, formattingInfo.getMaxLength());
/*     */             
/* 338 */             state = ParserState.MIN_STATE; continue;
/*     */           } 
/* 340 */           i = finalizeConverter(c, pattern, i, currentLiteral, formattingInfo, this.converterRules, patternConverters, formattingInfos);
/*     */ 
/*     */ 
/*     */           
/* 344 */           state = ParserState.LITERAL_STATE;
/* 345 */           formattingInfo = FormattingInfo.getDefault();
/* 346 */           currentLiteral.setLength(0);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case MIN_STATE:
/* 353 */           currentLiteral.append(c);
/*     */           
/* 355 */           if (c >= '0' && c <= '9') {
/*     */             
/* 357 */             formattingInfo = new FormattingInfo(formattingInfo.isLeftAligned(), formattingInfo.getMinLength() * 10 + c - 48, formattingInfo.getMaxLength());
/*     */             continue;
/*     */           } 
/* 360 */           if (c == '.') {
/* 361 */             state = ParserState.DOT_STATE; continue;
/*     */           } 
/* 363 */           i = finalizeConverter(c, pattern, i, currentLiteral, formattingInfo, this.converterRules, patternConverters, formattingInfos);
/*     */           
/* 365 */           state = ParserState.LITERAL_STATE;
/* 366 */           formattingInfo = FormattingInfo.getDefault();
/* 367 */           currentLiteral.setLength(0);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case DOT_STATE:
/* 373 */           currentLiteral.append(c);
/*     */           
/* 375 */           if (c >= '0' && c <= '9') {
/* 376 */             formattingInfo = new FormattingInfo(formattingInfo.isLeftAligned(), formattingInfo.getMinLength(), c - 48);
/*     */             
/* 378 */             state = ParserState.MAX_STATE; continue;
/*     */           } 
/* 380 */           LOGGER.error("Error occurred in position " + i + ".\n Was expecting digit, instead got char \"" + c + "\".");
/*     */ 
/*     */           
/* 383 */           state = ParserState.LITERAL_STATE;
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         case MAX_STATE:
/* 389 */           currentLiteral.append(c);
/*     */           
/* 391 */           if (c >= '0' && c <= '9') {
/*     */             
/* 393 */             formattingInfo = new FormattingInfo(formattingInfo.isLeftAligned(), formattingInfo.getMinLength(), formattingInfo.getMaxLength() * 10 + c - 48);
/*     */             
/*     */             continue;
/*     */           } 
/* 397 */           i = finalizeConverter(c, pattern, i, currentLiteral, formattingInfo, this.converterRules, patternConverters, formattingInfos);
/*     */           
/* 399 */           state = ParserState.LITERAL_STATE;
/* 400 */           formattingInfo = FormattingInfo.getDefault();
/* 401 */           currentLiteral.setLength(0);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     } 
/* 409 */     if (currentLiteral.length() != 0) {
/* 410 */       patternConverters.add(new LiteralPatternConverter(this.config, currentLiteral.toString()));
/* 411 */       formattingInfos.add(FormattingInfo.getDefault());
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PatternConverter createConverter(String converterId, StringBuilder currentLiteral, Map<String, Class<PatternConverter>> rules, List<String> options) {
/* 428 */     String converterName = converterId;
/* 429 */     Class<PatternConverter> converterClass = null;
/*     */     
/* 431 */     for (int i = converterId.length(); i > 0 && converterClass == null; i--) {
/* 432 */       converterName = converterName.substring(0, i);
/*     */       
/* 434 */       if (converterClass == null && rules != null) {
/* 435 */         converterClass = rules.get(converterName);
/*     */       }
/*     */     } 
/*     */     
/* 439 */     if (converterClass == null) {
/* 440 */       LOGGER.error("Unrecognized format specifier [" + converterId + "]");
/* 441 */       return null;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 446 */     Method[] methods = converterClass.getDeclaredMethods();
/* 447 */     Method newInstanceMethod = null;
/* 448 */     for (Method method : methods) {
/* 449 */       if (Modifier.isStatic(method.getModifiers()) && method.getDeclaringClass().equals(converterClass) && method.getName().equals("newInstance"))
/*     */       {
/* 451 */         if (newInstanceMethod == null) {
/* 452 */           newInstanceMethod = method;
/* 453 */         } else if (method.getReturnType().equals(newInstanceMethod.getReturnType())) {
/* 454 */           LOGGER.error("Class " + converterClass + " cannot contain multiple static newInstance methods");
/* 455 */           return null;
/*     */         } 
/*     */       }
/*     */     } 
/* 459 */     if (newInstanceMethod == null) {
/* 460 */       LOGGER.error("Class " + converterClass + " does not contain a static newInstance method");
/* 461 */       return null;
/*     */     } 
/*     */     
/* 464 */     Class<?>[] parmTypes = newInstanceMethod.getParameterTypes();
/* 465 */     Object[] parms = (parmTypes.length > 0) ? new Object[parmTypes.length] : null;
/*     */     
/* 467 */     if (parms != null) {
/* 468 */       int j = 0;
/* 469 */       boolean errors = false;
/* 470 */       for (Class<?> clazz : parmTypes) {
/* 471 */         if (clazz.isArray() && clazz.getName().equals("[Ljava.lang.String;")) {
/* 472 */           String[] optionsArray = options.<String>toArray(new String[options.size()]);
/* 473 */           parms[j] = optionsArray;
/* 474 */         } else if (clazz.isAssignableFrom(Configuration.class)) {
/* 475 */           parms[j] = this.config;
/*     */         } else {
/* 477 */           LOGGER.error("Unknown parameter type " + clazz.getName() + " for static newInstance method of " + converterClass.getName());
/*     */           
/* 479 */           errors = true;
/*     */         } 
/* 481 */         j++;
/*     */       } 
/* 483 */       if (errors) {
/* 484 */         return null;
/*     */       }
/*     */     } 
/*     */     
/*     */     try {
/* 489 */       Object newObj = newInstanceMethod.invoke(null, parms);
/*     */       
/* 491 */       if (newObj instanceof PatternConverter) {
/* 492 */         currentLiteral.delete(0, currentLiteral.length() - converterId.length() - converterName.length());
/*     */ 
/*     */         
/* 495 */         return (PatternConverter)newObj;
/*     */       } 
/* 497 */       LOGGER.warn("Class " + converterClass.getName() + " does not extend PatternConverter.");
/*     */     }
/* 499 */     catch (Exception ex) {
/* 500 */       LOGGER.error("Error creating converter for " + converterId, ex);
/*     */     } 
/*     */     
/* 503 */     return null;
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
/*     */   private int finalizeConverter(char c, String pattern, int i, StringBuilder currentLiteral, FormattingInfo formattingInfo, Map<String, Class<PatternConverter>> rules, List<PatternConverter> patternConverters, List<FormattingInfo> formattingInfos) {
/* 523 */     StringBuilder convBuf = new StringBuilder();
/* 524 */     i = extractConverter(c, pattern, i, convBuf, currentLiteral);
/*     */     
/* 526 */     String converterId = convBuf.toString();
/*     */     
/* 528 */     List<String> options = new ArrayList<String>();
/* 529 */     i = extractOptions(pattern, i, options);
/*     */     
/* 531 */     PatternConverter pc = createConverter(converterId, currentLiteral, rules, options);
/*     */     
/* 533 */     if (pc == null) {
/*     */       StringBuilder msg;
/*     */       
/* 536 */       if (Strings.isEmpty(converterId)) {
/* 537 */         msg = new StringBuilder("Empty conversion specifier starting at position ");
/*     */       } else {
/* 539 */         msg = new StringBuilder("Unrecognized conversion specifier [");
/* 540 */         msg.append(converterId);
/* 541 */         msg.append("] starting at position ");
/*     */       } 
/*     */       
/* 544 */       msg.append(Integer.toString(i));
/* 545 */       msg.append(" in conversion pattern.");
/*     */       
/* 547 */       LOGGER.error(msg.toString());
/*     */       
/* 549 */       patternConverters.add(new LiteralPatternConverter(this.config, currentLiteral.toString()));
/* 550 */       formattingInfos.add(FormattingInfo.getDefault());
/*     */     } else {
/* 552 */       patternConverters.add(pc);
/* 553 */       formattingInfos.add(formattingInfo);
/*     */       
/* 555 */       if (currentLiteral.length() > 0) {
/* 556 */         patternConverters.add(new LiteralPatternConverter(this.config, currentLiteral.toString()));
/* 557 */         formattingInfos.add(FormattingInfo.getDefault());
/*     */       } 
/*     */     } 
/*     */     
/* 561 */     currentLiteral.setLength(0);
/*     */     
/* 563 */     return i;
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\core\pattern\PatternParser.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */