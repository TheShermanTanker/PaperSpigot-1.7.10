/*     */ package org.apache.logging.log4j.message;
/*     */ 
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.Date;
/*     */ import java.util.HashSet;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
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
/*     */ public class ParameterizedMessage
/*     */   implements Message
/*     */ {
/*     */   public static final String RECURSION_PREFIX = "[...";
/*     */   public static final String RECURSION_SUFFIX = "...]";
/*     */   public static final String ERROR_PREFIX = "[!!!";
/*     */   public static final String ERROR_SEPARATOR = "=>";
/*     */   public static final String ERROR_MSG_SEPARATOR = ":";
/*     */   public static final String ERROR_SUFFIX = "!!!]";
/*     */   private static final long serialVersionUID = -665975803997290697L;
/*     */   private static final int HASHVAL = 31;
/*     */   private static final char DELIM_START = '{';
/*     */   private static final char DELIM_STOP = '}';
/*     */   private static final char ESCAPE_CHAR = '\\';
/*     */   private final String messagePattern;
/*     */   private final String[] stringArgs;
/*     */   private transient Object[] argArray;
/*     */   private transient String formattedMessage;
/*     */   private transient Throwable throwable;
/*     */   
/*     */   public ParameterizedMessage(String messagePattern, String[] stringArgs, Throwable throwable) {
/*  85 */     this.messagePattern = messagePattern;
/*  86 */     this.stringArgs = stringArgs;
/*  87 */     this.throwable = throwable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ParameterizedMessage(String messagePattern, Object[] objectArgs, Throwable throwable) {
/*  98 */     this.messagePattern = messagePattern;
/*  99 */     this.throwable = throwable;
/* 100 */     this.stringArgs = parseArguments(objectArgs);
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
/*     */   public ParameterizedMessage(String messagePattern, Object[] arguments) {
/* 116 */     this.messagePattern = messagePattern;
/* 117 */     this.stringArgs = parseArguments(arguments);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ParameterizedMessage(String messagePattern, Object arg) {
/* 126 */     this(messagePattern, new Object[] { arg });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ParameterizedMessage(String messagePattern, Object arg1, Object arg2) {
/* 136 */     this(messagePattern, new Object[] { arg1, arg2 });
/*     */   }
/*     */   private String[] parseArguments(Object[] arguments) {
/*     */     String[] strArgs;
/* 140 */     if (arguments == null) {
/* 141 */       return null;
/*     */     }
/* 143 */     int argsCount = countArgumentPlaceholders(this.messagePattern);
/* 144 */     int resultArgCount = arguments.length;
/* 145 */     if (argsCount < arguments.length && 
/* 146 */       this.throwable == null && arguments[arguments.length - 1] instanceof Throwable) {
/* 147 */       this.throwable = (Throwable)arguments[arguments.length - 1];
/* 148 */       resultArgCount--;
/*     */     } 
/*     */     
/* 151 */     this.argArray = new Object[resultArgCount];
/* 152 */     for (int i = 0; i < resultArgCount; i++) {
/* 153 */       this.argArray[i] = arguments[i];
/*     */     }
/*     */ 
/*     */     
/* 157 */     if (argsCount == 1 && this.throwable == null && arguments.length > 1) {
/*     */       
/* 159 */       strArgs = new String[1];
/* 160 */       strArgs[0] = deepToString(arguments);
/*     */     } else {
/* 162 */       strArgs = new String[resultArgCount];
/* 163 */       for (int j = 0; j < strArgs.length; j++) {
/* 164 */         strArgs[j] = deepToString(arguments[j]);
/*     */       }
/*     */     } 
/* 167 */     return strArgs;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFormattedMessage() {
/* 176 */     if (this.formattedMessage == null) {
/* 177 */       this.formattedMessage = formatMessage(this.messagePattern, this.stringArgs);
/*     */     }
/* 179 */     return this.formattedMessage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFormat() {
/* 188 */     return this.messagePattern;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object[] getParameters() {
/* 197 */     if (this.argArray != null) {
/* 198 */       return this.argArray;
/*     */     }
/* 200 */     return (Object[])this.stringArgs;
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
/*     */   public Throwable getThrowable() {
/* 214 */     return this.throwable;
/*     */   }
/*     */   
/*     */   protected String formatMessage(String msgPattern, String[] sArgs) {
/* 218 */     return format(msgPattern, (Object[])sArgs);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 223 */     if (this == o) {
/* 224 */       return true;
/*     */     }
/* 226 */     if (o == null || getClass() != o.getClass()) {
/* 227 */       return false;
/*     */     }
/*     */     
/* 230 */     ParameterizedMessage that = (ParameterizedMessage)o;
/*     */     
/* 232 */     if ((this.messagePattern != null) ? !this.messagePattern.equals(that.messagePattern) : (that.messagePattern != null)) {
/* 233 */       return false;
/*     */     }
/* 235 */     if (!Arrays.equals((Object[])this.stringArgs, (Object[])that.stringArgs)) {
/* 236 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 240 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 245 */     int result = (this.messagePattern != null) ? this.messagePattern.hashCode() : 0;
/* 246 */     result = 31 * result + ((this.stringArgs != null) ? Arrays.hashCode((Object[])this.stringArgs) : 0);
/* 247 */     return result;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String format(String messagePattern, Object[] arguments) {
/* 258 */     if (messagePattern == null || arguments == null || arguments.length == 0) {
/* 259 */       return messagePattern;
/*     */     }
/*     */     
/* 262 */     StringBuilder result = new StringBuilder();
/* 263 */     int escapeCounter = 0;
/* 264 */     int currentArgument = 0;
/* 265 */     for (int i = 0; i < messagePattern.length(); i++) {
/* 266 */       char curChar = messagePattern.charAt(i);
/* 267 */       if (curChar == '\\') {
/* 268 */         escapeCounter++;
/*     */       }
/* 270 */       else if (curChar == '{' && 
/* 271 */         i < messagePattern.length() - 1 && 
/* 272 */         messagePattern.charAt(i + 1) == '}') {
/*     */         
/* 274 */         int escapedEscapes = escapeCounter / 2;
/* 275 */         for (int j = 0; j < escapedEscapes; j++) {
/* 276 */           result.append('\\');
/*     */         }
/*     */         
/* 279 */         if (escapeCounter % 2 == 1) {
/*     */ 
/*     */           
/* 282 */           result.append('{');
/* 283 */           result.append('}');
/*     */         } else {
/*     */           
/* 286 */           if (currentArgument < arguments.length) {
/* 287 */             result.append(arguments[currentArgument]);
/*     */           } else {
/* 289 */             result.append('{').append('}');
/*     */           } 
/* 291 */           currentArgument++;
/*     */         } 
/* 293 */         i++;
/* 294 */         escapeCounter = 0;
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 301 */         if (escapeCounter > 0) {
/* 302 */           for (int j = 0; j < escapeCounter; j++) {
/* 303 */             result.append('\\');
/*     */           }
/* 305 */           escapeCounter = 0;
/*     */         } 
/* 307 */         result.append(curChar);
/*     */       } 
/*     */     } 
/* 310 */     return result.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static int countArgumentPlaceholders(String messagePattern) {
/* 320 */     if (messagePattern == null) {
/* 321 */       return 0;
/*     */     }
/*     */     
/* 324 */     int delim = messagePattern.indexOf('{');
/*     */     
/* 326 */     if (delim == -1)
/*     */     {
/* 328 */       return 0;
/*     */     }
/* 330 */     int result = 0;
/* 331 */     boolean isEscaped = false;
/* 332 */     for (int i = 0; i < messagePattern.length(); i++) {
/* 333 */       char curChar = messagePattern.charAt(i);
/* 334 */       if (curChar == '\\') {
/* 335 */         isEscaped = !isEscaped;
/* 336 */       } else if (curChar == '{') {
/* 337 */         if (!isEscaped && 
/* 338 */           i < messagePattern.length() - 1 && 
/* 339 */           messagePattern.charAt(i + 1) == '}') {
/* 340 */           result++;
/* 341 */           i++;
/*     */         } 
/*     */ 
/*     */         
/* 345 */         isEscaped = false;
/*     */       } else {
/* 347 */         isEscaped = false;
/*     */       } 
/*     */     } 
/* 350 */     return result;
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
/*     */   public static String deepToString(Object o) {
/* 370 */     if (o == null) {
/* 371 */       return null;
/*     */     }
/* 373 */     if (o instanceof String) {
/* 374 */       return (String)o;
/*     */     }
/* 376 */     StringBuilder str = new StringBuilder();
/* 377 */     Set<String> dejaVu = new HashSet<String>();
/* 378 */     recursiveDeepToString(o, str, dejaVu);
/* 379 */     return str.toString();
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
/*     */   private static void recursiveDeepToString(Object o, StringBuilder str, Set<String> dejaVu) {
/* 404 */     if (o == null) {
/* 405 */       str.append("null");
/*     */       return;
/*     */     } 
/* 408 */     if (o instanceof String) {
/* 409 */       str.append(o);
/*     */       
/*     */       return;
/*     */     } 
/* 413 */     Class<?> oClass = o.getClass();
/* 414 */     if (oClass.isArray()) {
/* 415 */       if (oClass == byte[].class) {
/* 416 */         str.append(Arrays.toString((byte[])o));
/* 417 */       } else if (oClass == short[].class) {
/* 418 */         str.append(Arrays.toString((short[])o));
/* 419 */       } else if (oClass == int[].class) {
/* 420 */         str.append(Arrays.toString((int[])o));
/* 421 */       } else if (oClass == long[].class) {
/* 422 */         str.append(Arrays.toString((long[])o));
/* 423 */       } else if (oClass == float[].class) {
/* 424 */         str.append(Arrays.toString((float[])o));
/* 425 */       } else if (oClass == double[].class) {
/* 426 */         str.append(Arrays.toString((double[])o));
/* 427 */       } else if (oClass == boolean[].class) {
/* 428 */         str.append(Arrays.toString((boolean[])o));
/* 429 */       } else if (oClass == char[].class) {
/* 430 */         str.append(Arrays.toString((char[])o));
/*     */       } else {
/*     */         
/* 433 */         String id = identityToString(o);
/* 434 */         if (dejaVu.contains(id)) {
/* 435 */           str.append("[...").append(id).append("...]");
/*     */         } else {
/* 437 */           dejaVu.add(id);
/* 438 */           Object[] oArray = (Object[])o;
/* 439 */           str.append("[");
/* 440 */           boolean first = true;
/* 441 */           for (Object current : oArray) {
/* 442 */             if (first) {
/* 443 */               first = false;
/*     */             } else {
/* 445 */               str.append(", ");
/*     */             } 
/* 447 */             recursiveDeepToString(current, str, new HashSet<String>(dejaVu));
/*     */           } 
/* 449 */           str.append("]");
/*     */         }
/*     */       
/*     */       } 
/* 453 */     } else if (o instanceof Map) {
/*     */       
/* 455 */       String id = identityToString(o);
/* 456 */       if (dejaVu.contains(id)) {
/* 457 */         str.append("[...").append(id).append("...]");
/*     */       } else {
/* 459 */         dejaVu.add(id);
/* 460 */         Map<?, ?> oMap = (Map<?, ?>)o;
/* 461 */         str.append("{");
/* 462 */         boolean isFirst = true;
/* 463 */         for (Map.Entry<?, ?> o1 : oMap.entrySet()) {
/* 464 */           Map.Entry<?, ?> current = o1;
/* 465 */           if (isFirst) {
/* 466 */             isFirst = false;
/*     */           } else {
/* 468 */             str.append(", ");
/*     */           } 
/* 470 */           Object key = current.getKey();
/* 471 */           Object value = current.getValue();
/* 472 */           recursiveDeepToString(key, str, new HashSet<String>(dejaVu));
/* 473 */           str.append("=");
/* 474 */           recursiveDeepToString(value, str, new HashSet<String>(dejaVu));
/*     */         } 
/* 476 */         str.append("}");
/*     */       } 
/* 478 */     } else if (o instanceof Collection) {
/*     */       
/* 480 */       String id = identityToString(o);
/* 481 */       if (dejaVu.contains(id)) {
/* 482 */         str.append("[...").append(id).append("...]");
/*     */       } else {
/* 484 */         dejaVu.add(id);
/* 485 */         Collection<?> oCol = (Collection)o;
/* 486 */         str.append("[");
/* 487 */         boolean isFirst = true;
/* 488 */         for (Object anOCol : oCol) {
/* 489 */           if (isFirst) {
/* 490 */             isFirst = false;
/*     */           } else {
/* 492 */             str.append(", ");
/*     */           } 
/* 494 */           recursiveDeepToString(anOCol, str, new HashSet<String>(dejaVu));
/*     */         } 
/* 496 */         str.append("]");
/*     */       } 
/* 498 */     } else if (o instanceof Date) {
/* 499 */       Date date = (Date)o;
/* 500 */       SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
/*     */       
/* 502 */       str.append(format.format(date));
/*     */     } else {
/*     */       
/*     */       try {
/* 506 */         str.append(o.toString());
/* 507 */       } catch (Throwable t) {
/* 508 */         str.append("[!!!");
/* 509 */         str.append(identityToString(o));
/* 510 */         str.append("=>");
/* 511 */         String msg = t.getMessage();
/* 512 */         String className = t.getClass().getName();
/* 513 */         str.append(className);
/* 514 */         if (!className.equals(msg)) {
/* 515 */           str.append(":");
/* 516 */           str.append(msg);
/*     */         } 
/* 518 */         str.append("!!!]");
/*     */       } 
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String identityToString(Object obj) {
/* 542 */     if (obj == null) {
/* 543 */       return null;
/*     */     }
/* 545 */     return obj.getClass().getName() + "@" + Integer.toHexString(System.identityHashCode(obj));
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 550 */     return "ParameterizedMessage[messagePattern=" + this.messagePattern + ", stringArgs=" + Arrays.toString((Object[])this.stringArgs) + ", throwable=" + this.throwable + "]";
/*     */   }
/*     */ }


/* Location:              D:\Paper-1.7.10\PaperSpigot-1.7.10-R0.1-SNAPSHOT-latest.jar!\org\apache\logging\log4j\message\ParameterizedMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */